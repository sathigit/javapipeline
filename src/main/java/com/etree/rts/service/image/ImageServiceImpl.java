package com.etree.rts.service.image;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.etree.rts.config.RTSProperties;
import com.etree.rts.constant.Constants;
import com.etree.rts.constant.FileType;
import com.etree.rts.constant.StatusCode;
import com.etree.rts.dao.image.ImageDAO;
import com.etree.rts.dao.supplier.SupplierDAO;
import com.etree.rts.dao.template.TemplateDAO;
import com.etree.rts.dao.templateconfig.TemplateFileConfigDAO;
import com.etree.rts.domain.image.Image;
import com.etree.rts.domain.supplier.Supplier;
import com.etree.rts.domain.template.Template;
import com.etree.rts.mapper.image.ImageMapper;
import com.etree.rts.model.image.ImageModel;
import com.etree.rts.response.Response;
import com.etree.rts.utils.CommonUtils;
import com.etree.rts.utils.FileUtils;

@Service("imageService")
public class ImageServiceImpl implements ImageService, Constants {

	private static final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

	@Autowired
	ImageDAO imageDAO;

	@Autowired
	ImageMapper imageMapper;

	@Autowired
	RTSProperties rtsProperties;

	@Autowired
	TemplateDAO templateDAO;

	@Autowired
	SupplierDAO supplierDAO;
	@Autowired
	DataAsync dataAsync;

	@Autowired
	TemplateFileConfigDAO templateFileConfigDAO;

	public ImageServiceImpl() {
		// TODO
	}

	public Response saveImage(ImageModel imageModel, MultipartFile file) {
		Response response = CommonUtils.getResponseObject("File Upload");
		try {
			Template template = null;
			try {
				template = templateDAO.getTemplateBySupplier(imageModel.getSupplierId());
			} catch (Exception e) {
				logger.info("Exception in saveImage:" + e.getMessage());
			}
			if (template == null) {
				response.setMessage("Supplier is not associated to any template");
				response.setStatus(StatusCode.ERROR.name());
				return response;
			}

			FileType fileType = FileType.valueOf(FilenameUtils.getExtension(file.getOriginalFilename()).toUpperCase());
			switch (fileType) {
			case CSV:
				return saveCSVFile(imageModel, file);
			case XLS:
				return saveXLSFile(imageModel, file);
			case XLSX:
				return saveXLSXFile(imageModel, file);
			default:
				response.setStatus(StatusCode.ERROR.name());
				response.setErrors("The ." + file.getContentType() + " is not supported");
				return response;
			}
		} catch (Exception ex) {
			logger.info("Exception Service:" + ex.getMessage());
		}
		response.setStatus(StatusCode.ERROR.name());
		response.setErrors("The ." + FilenameUtils.getExtension(file.getOriginalFilename()) + " is not supported");
		return response;
	}

	public Image getFileObject(ImageModel imageModel, MultipartFile file) {
		Image image = new Image();
		try {
			BeanUtils.copyProperties(imageModel, image);
			image.setImageId(CommonUtils.getRandomUUID());
			image.setName(file.getOriginalFilename());
			image.setType(file.getContentType());
			image.setSize(file.getSize());
			image.setIsActive(false);
			image.setPath(rtsProperties.getImagePath() + "/" + image.getUserId() + "/" + image.getSupplierId() + "/"
					+ file.getOriginalFilename());
			FileUtils.createDir(rtsProperties.getImagePath() + "/" + image.getUserId() + "/" + image.getSupplierId());
			try {
				byte[] bytes = file.getBytes();
				Path path = Paths.get(rtsProperties.getImagePath() + "/" + image.getUserId() + "/"
						+ image.getSupplierId() + "/" + file.getOriginalFilename());
				Files.write(path, bytes);
			} catch (Exception e) {
				logger.info("Exception in getFileObject:" + e.getMessage());
			}
		} catch (Exception ex) {
			logger.info("Exception Service:" + ex.getMessage());
		}
		return image;
	}

	public Response saveCSVFile(ImageModel imageModel, MultipartFile file) {
		try {
			Response response = CommonUtils.getResponseObject("Add csv file");
			Image image = getFileObject(imageModel, file);
			Supplier supplier = supplierDAO.getSupplier(imageModel.getSupplierId());
			Template template = templateDAO.getTemplateBySupplier(imageModel.getSupplierId());
			BufferedReader br = new BufferedReader(new FileReader(image.getPath()));
			String line = "";
			String[] itemDataIdentifiers = template.getDataIdentifier().split(",");
			while ((line = br.readLine()) != null) {
				if (line.length() > 0) {
					String[] csvColumns = line.split(",");
					if (csvColumns != null && csvColumns.length > 0) {
						List listCsv = Arrays.asList(csvColumns);
						if (!FileUtils.containsCaseInsensitive(template.getUniqueIdentifier(), listCsv)) {
							response.setStatus(StatusCode.ERROR.name());
							response.setErrors("UniqueIdentifier[" + template.getUniqueIdentifier()
									+ "] from template is not matching with uploaded csv file for the supplier - "
									+ supplier.getName() + "");
							return response;
						}
						if (itemDataIdentifiers.length != csvColumns.length) {
							response.setStatus(StatusCode.ERROR.name());
							response.setErrors(
									"The data Identifiers are not mattching from template against uploaded csv file. ");
							return response;
						}
						for (String key : itemDataIdentifiers) {
							if (!FileUtils.containsCaseInsensitive(key, listCsv)) {
								response.setStatus(StatusCode.ERROR.name());
								response.setErrors(
										"The data Identifiers are not mattching from template against uploaded csv file. ");
								return response;
							}
						}
						response = imageDAO.saveImage(image);
					}
					break;
				}
			}
			dataAsync.generateStockAndProductsCSV(image, template);
			return response;
		} catch (Exception ex) {
			logger.info("Exception Service:" + ex.getMessage());
		}
		return null;
	}

	public Response saveXLSFile(ImageModel imageModel, MultipartFile file) {
		try {
			Response response = CommonUtils.getResponseObject("Add csv file");
			Image image = getFileObject(imageModel, file);
			Supplier supplier = supplierDAO.getSupplier(imageModel.getSupplierId());
			Template template = templateDAO.getTemplateBySupplier(imageModel.getSupplierId());
			String[] itemDataIdentifiers = template.getDataIdentifier().split(",");
			HSSFRow row = null;
			int rowNo = 0;
			File excel = new File(image.getPath());
			FileInputStream fis = new FileInputStream(excel);
			HSSFWorkbook wb = new HSSFWorkbook(fis);
			HSSFSheet ws = wb.getSheetAt(0);
			boolean isColumnsMatched = false;
			List<String> itemDataIdentifiersList = Arrays.asList(template.getDataIdentifier().split(","));
			int rowNum = ws.getLastRowNum() + 1;
			for (int i = 0; i < rowNum; i++) {
				row = ws.getRow(i);
				int colNum = ws.getRow(i).getLastCellNum();
				if (colNum == itemDataIdentifiers.length) {
					List<String> listColumns = new ArrayList<>();
					for (int j = 0; j < colNum; j++)
						listColumns.add(row.getCell(j).toString());
					List<String> commonList = (List<String>) CollectionUtils.retainAll(itemDataIdentifiersList,
							listColumns);
					System.out.println(commonList.size());
					if (commonList.size() == itemDataIdentifiersList.size()) {
						if (!FileUtils.containsCaseInsensitive(template.getUniqueIdentifier(), listColumns)) {
							response.setStatus(StatusCode.ERROR.name());
							response.setErrors("UniqueIdentifier[" + template.getUniqueIdentifier()
									+ "] from template is not matching with uploaded csv file for the supplier - "
									+ supplier.getName() + "");
							return response;
						}
						isColumnsMatched = true;
						rowNo = i;
						break;
					}
				}
			}

			if (!isColumnsMatched) {
				response.setStatus(StatusCode.ERROR.name());
				response.setErrors("The data Identifiers are not mattching from template against uploaded csv file. ");
				return response;
			}
			response = imageDAO.saveImage(image);
			dataAsync.generateStockAndProductsXLS(image, template, row, rowNo);
			return response;
		} catch (Exception ex) {
			logger.info("Exception Service:" + ex.getMessage());
		}
		return null;
	}

	public Response saveXLSXFile(ImageModel imageModel, MultipartFile file) {
		try {
			Response response = CommonUtils.getResponseObject("Add csv file");
			Image image = getFileObject(imageModel, file);
			Supplier supplier = supplierDAO.getSupplier(imageModel.getSupplierId());
			Template template = templateDAO.getTemplateBySupplier(imageModel.getSupplierId());
			String[] itemDataIdentifiers = template.getDataIdentifier().split(",");
			XSSFRow row = null;
			int rowNo = 0;
			File excel = new File(image.getPath());
			FileInputStream fis = new FileInputStream(excel);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet ws = wb.getSheetAt(0);
			boolean isColumnsMatched = false;
			List<String> itemDataIdentifiersList = Arrays.asList(template.getDataIdentifier().split(","));
			int rowNum = ws.getLastRowNum() + 1;
			for (int i = 0; i < rowNum; i++) {
				row = ws.getRow(i);
				int colNum = ws.getRow(i).getLastCellNum();
				if (colNum == itemDataIdentifiers.length) {
					List<String> listColumns = new ArrayList<>();
					for (int j = 0; j < colNum; j++)
						listColumns.add(row.getCell(j).toString());
					List<String> commonList = (List<String>) CollectionUtils.retainAll(itemDataIdentifiersList,
							listColumns);
					System.out.println(commonList.size());
					if (commonList.size() == itemDataIdentifiersList.size()) {
						if (!FileUtils.containsCaseInsensitive(template.getUniqueIdentifier(), listColumns)) {
							response.setStatus(StatusCode.ERROR.name());
							response.setErrors("UniqueIdentifier[" + template.getUniqueIdentifier()
									+ "] from template is not matching with uploaded csv file for the supplier - "
									+ supplier.getName() + "");
							return response;
						}
						isColumnsMatched = true;
						rowNo = i;
						break;
					}
				}
			}

			if (!isColumnsMatched) {
				response.setStatus(StatusCode.ERROR.name());
				response.setErrors("The data Identifiers are not mattching from template against uploaded csv file. ");
				return response;
			}
			response = imageDAO.saveImage(image);
			dataAsync.generateStockAndProductsXLSX(image, template, row, rowNo);
			return response;
		} catch (Exception ex) {
			logger.info("Exception Service:" + ex.getMessage());
		}
		return null;
	}

	public Response updateImage(ImageModel imageModel, MultipartFile file) {
		try {
			Image image = imageDAO.getImage(imageModel.getImageId());
			String pathToDelete = image.getPath();
			image.setName(file.getOriginalFilename());
			image.setType(file.getContentType());
			image.setSize(file.getSize());
			image.setPath(rtsProperties.getImagePath() + "/" + image.getUserId() + "/" + file.getOriginalFilename());
			FileUtils.createDir(rtsProperties.getImagePath() + "/" + image.getUserId());
			try {
				byte[] bytes = file.getBytes();
				Path path = Paths
						.get(rtsProperties.getImagePath() + "/" + image.getUserId() + "/" + file.getOriginalFilename());
				Files.write(path, bytes);
				/**
				 * Delete a file after update
				 */
				FileUtils.deleteFile(pathToDelete);
			} catch (Exception e) {
				Response response = CommonUtils.getResponseObject("Error while uploading image");
				response.setStatus(StatusCode.ERROR.name());
				response.setErrors(e.getMessage());
				return response;
			}
			Response response = imageDAO.updateImage(image);
			return response;
		} catch (Exception ex) {
			logger.info("Exception Service:" + ex.getMessage());
		}
		return null;
	}

	public Resource getImage(String imageId) {
		try {
			Image image = imageDAO.getImage(imageId);
			Path file = Paths.get(image.getPath());
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("FAIL!");
			}
		} catch (Exception e) {
			logger.info("Exception getImage:", e);
			throw new RuntimeException("FAIL!");
		}
	}

	public List<ImageModel> getImagesByUser(String userId) throws Exception {
		try {
			List<Image> images = imageDAO.getImagesByUser(userId);
			return imageMapper.entityList(images);
		} catch (Exception ex) {
			logger.info("Exception getImagesByUser:", ex);
		}
		return null;
	}

	public List<ImageModel> getImagesByUserAndCategory(ImageModel imageModel) throws Exception {
		try {
			Image image = new Image();
			BeanUtils.copyProperties(imageModel, image);
			List<Image> images = imageDAO.getImagesByUserAndCategory(image);
			return imageMapper.entityList(images);
		} catch (Exception ex) {
			logger.info("Exception getImagesByUser:", ex);
		}
		return null;
	}

	public Resource getImageForStock(String imageId) {
		try {
			Image image = imageDAO.getImage(imageId);
			Path file = Paths.get(image.getStockOrderPath());
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("FAIL!");
			}
		} catch (Exception e) {
			logger.info("Exception getImageForStock:", e);
			throw new RuntimeException("FAIL!");
		}
	}

	public Resource getImageForNewOrder(String imageId) {
		try {
			Image image = imageDAO.getImage(imageId);
			Path file = Paths.get(image.getNewProductPath());
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("FAIL!");
			}
		} catch (Exception e) {
			logger.info("Exception getImageForNewOrder:", e);
			throw new RuntimeException("FAIL!");
		}
	}
}
