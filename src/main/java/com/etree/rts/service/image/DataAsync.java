package com.etree.rts.service.image;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.etree.rts.config.RTSProperties;
import com.etree.rts.constant.FileImportType;
import com.etree.rts.dao.image.ImageDAO;
import com.etree.rts.dao.product.ProductDAO;
import com.etree.rts.dao.user.UserDAO;
import com.etree.rts.domain.image.Image;
import com.etree.rts.domain.image.SyncMessage;
import com.etree.rts.domain.template.Template;
import com.etree.rts.domain.user.User;
import com.etree.rts.model.templateconfig.TemplateFileConfigAttributeModel;
import com.etree.rts.model.templateconfig.TemplateFileConfigModel;
import com.etree.rts.service.product.ProductService;
import com.etree.rts.service.templateconfig.TemplateFileConfigService;
import com.etree.rts.utils.DateUtility;

@Configuration
@EnableScheduling
@EnableAsync
public class DataAsync {
	private static final Logger logger = LoggerFactory.getLogger(DataAsync.class);
	@Autowired
	ProductService productService;

	@Autowired
	ProductDAO productDAO;

	@Autowired
	RTSProperties rtsProperties;

	@Autowired
	ImageDAO imageDAO;

	@Autowired
	TemplateFileConfigService templateFileConfigService;

	@Autowired
	SseEmitter sseEmitter;

	@Autowired
	UserDAO userDAO;
	
	@Async
	public void generateStockAndProductsCSV(Image image, Template template) {
		try {
			logger.info("triggerCampaign started");
			User user = userDAO.getUser(image.getUserId());
			productService.syncProductsIntermediate(user.getOrganizationId());
			logger.info("triggerCampaign ended");

			TemplateFileConfigModel stockTFCM = getTFCM(image.getSupplierId(), FileImportType.STOCK.name());
			TemplateFileConfigModel newProductTFCM = getTFCM(image.getSupplierId(), FileImportType.NEWPRODUCT.name());
			String COMMA_DELIMITER = ",";
			String NEW_LINE_SEPARATOR = "\n";

			StringBuffer stockHeaderForKorona = getCSVHeader(stockTFCM);
			StringBuffer newProductHeaderForKorona = getCSVHeader(newProductTFCM);

			Map<String, Integer> stockColumnIndexes = new LinkedHashMap<String, Integer>();
			Map<String, Integer> newProductColumnIndexes = new LinkedHashMap<String, Integer>();
			StringBuilder stockOrderBuilder = new StringBuilder();
			stockOrderBuilder.append(stockHeaderForKorona.toString());
			stockOrderBuilder.append(NEW_LINE_SEPARATOR);

			StringBuilder newProductBuilder = new StringBuilder();
			newProductBuilder.append(newProductHeaderForKorona.toString());
			newProductBuilder.append(NEW_LINE_SEPARATOR);

			StringBuilder stBuf = new StringBuilder();
			BufferedReader br = new BufferedReader(new FileReader(image.getPath()));
			String line = "";
			int uniqueIdentifier = -1;
			while ((line = br.readLine()) != null) {
				if (line.length() > 0) {
					String[] csvColumns = line.split(",");
					if (csvColumns != null && csvColumns.length > 0) {
						if (uniqueIdentifier == -1) {
							List<String> listCsv = Arrays.asList(csvColumns);
							if (stockTFCM != null && stockTFCM.getTemplateFileConfigAttributeModels() != null
									&& stockTFCM.getTemplateFileConfigAttributeModels().size() > 0) {
								for (TemplateFileConfigAttributeModel tfcam : stockTFCM
										.getTemplateFileConfigAttributeModels())
									stockColumnIndexes.put(tfcam.getSupplierColumn(),
											listCsv.indexOf(tfcam.getSupplierColumn()));
							}
							if (newProductTFCM != null && newProductTFCM.getTemplateFileConfigAttributeModels() != null
									&& newProductTFCM.getTemplateFileConfigAttributeModels().size() > 0) {
								for (TemplateFileConfigAttributeModel tfcam : newProductTFCM
										.getTemplateFileConfigAttributeModels())
									newProductColumnIndexes.put(tfcam.getSupplierColumn(),
											listCsv.indexOf(tfcam.getSupplierColumn()));
							}
							uniqueIdentifier = listCsv.indexOf(template.getUniqueIdentifier());
							continue;
						}
						try {
							for (Map.Entry<String, Integer> entry : stockColumnIndexes.entrySet()) {
								if (entry.getValue() != -1)
									stBuf.append(csvColumns[entry.getValue()]);
								stBuf.append(COMMA_DELIMITER);
							}
							stBuf.append(NEW_LINE_SEPARATOR);
							stockOrderBuilder.append(stBuf.toString());
							stBuf.setLength(0);
						} catch (Exception e) {
							// TODO: handle exception
						}
						try {
							if (!productDAO.isArticleCodeExist(csvColumns[uniqueIdentifier], image.getUserId())) {
								stBuf.append(NEW_LINE_SEPARATOR);
								for (Map.Entry<String, Integer> entry : newProductColumnIndexes.entrySet()) {
									if (entry.getValue() != -1) {
										stBuf.append(csvColumns[entry.getValue()]);
									} else if (entry.getKey().equalsIgnoreCase("ARTICLE_TAGS")) {
										stBuf.append(DateUtility.getDateByStringFormat(new Date(),
												DateUtility.DATE_FORMAT_YYYYMMDDHHMMSS));
									}
									stBuf.append(COMMA_DELIMITER);
								}
								stBuf.append(NEW_LINE_SEPARATOR);
								newProductBuilder.append(stBuf.toString());
							}
							stBuf.setLength(0);
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
				}
			}
			genarateFiles(image, stockOrderBuilder, newProductBuilder);
		} catch (

		Exception e) {
			logger.error("triggerCampaign:Error : ", e);
		}
	}

	private StringBuffer getCSVHeader(TemplateFileConfigModel stockTFCM) {
		StringBuffer stockHeaderForKorona = new StringBuffer();
		if (stockTFCM != null && stockTFCM.getTemplateFileConfigAttributeModels() != null
				&& stockTFCM.getTemplateFileConfigAttributeModels().size() > 0) {
			for (TemplateFileConfigAttributeModel tfcam : stockTFCM.getTemplateFileConfigAttributeModels())
				stockHeaderForKorona.append(tfcam.getKoronaColumn() + ",");
		}
		return stockHeaderForKorona;
	}

	private TemplateFileConfigModel getTFCM(String supplierId, String type) throws Exception {
		return templateFileConfigService.getTemplateFileConfigBySupplier(supplierId, type);
	}

	@Async
	public void generateStockAndProductsXLS(Image image, Template template, HSSFRow row, int rowNo) {
		try {
			logger.info("triggerCampaign started");
			User user = userDAO.getUser(image.getUserId());
			productService.syncProductsIntermediate(user.getOrganizationId());
			logger.info("triggerCampaign ended");

			String COMMA_DELIMITER = ",";
			String NEW_LINE_SEPARATOR = "\n";
			TemplateFileConfigModel stockTFCM = getTFCM(image.getSupplierId(), FileImportType.STOCK.name());
			TemplateFileConfigModel newProductTFCM = getTFCM(image.getSupplierId(), FileImportType.NEWPRODUCT.name());

			StringBuffer stockHeaderForKorona = getCSVHeader(stockTFCM);
			StringBuffer newProductHeaderForKorona = getCSVHeader(newProductTFCM);

			Map<String, Integer> stockColumnIndexes = new LinkedHashMap<String, Integer>();
			Map<String, Integer> newProductColumnIndexes = new LinkedHashMap<String, Integer>();

			StringBuilder stockOrderBuilder = new StringBuilder();
			stockOrderBuilder.append(stockHeaderForKorona.toString());
			stockOrderBuilder.append(NEW_LINE_SEPARATOR);

			StringBuilder newProductBuilder = new StringBuilder();
			newProductBuilder.append(newProductHeaderForKorona.toString());
			newProductBuilder.append(NEW_LINE_SEPARATOR);

			StringBuilder stBuf = new StringBuilder();

			int uniqueIdentifier = -1;
			if (true) {
				int colNum = row.getLastCellNum();
				List<String> listColumns = new ArrayList<>();
				for (int j = 0; j < colNum; j++)
					listColumns.add(row.getCell(j).toString());

				uniqueIdentifier = listColumns.indexOf(template.getUniqueIdentifier());
				if (stockTFCM != null && stockTFCM.getTemplateFileConfigAttributeModels() != null
						&& stockTFCM.getTemplateFileConfigAttributeModels().size() > 0) {
					for (TemplateFileConfigAttributeModel tfcam : stockTFCM.getTemplateFileConfigAttributeModels())
						stockColumnIndexes.put(tfcam.getSupplierColumn(),
								listColumns.indexOf(tfcam.getSupplierColumn()));
				}
				if (newProductTFCM != null && newProductTFCM.getTemplateFileConfigAttributeModels() != null
						&& newProductTFCM.getTemplateFileConfigAttributeModels().size() > 0) {
					for (TemplateFileConfigAttributeModel tfcam : newProductTFCM.getTemplateFileConfigAttributeModels())
						newProductColumnIndexes.put(tfcam.getSupplierColumn(),
								listColumns.indexOf(tfcam.getSupplierColumn()));
				}
			}
			File excel = new File(image.getPath());
			FileInputStream fis = new FileInputStream(excel);
			HSSFWorkbook wb = new HSSFWorkbook(fis);
			HSSFSheet ws = wb.getSheetAt(0);
			int rowNum = ws.getLastRowNum() + 1;
			for (int i = rowNo + 1; i < rowNum; i++) {
				try {
					row = ws.getRow(i);
					for (Map.Entry<String, Integer> entry : stockColumnIndexes.entrySet()) {
						if (entry.getValue() != -1)
							stBuf.append(row.getCell(entry.getValue()));
						stBuf.append(COMMA_DELIMITER);
					}
					stBuf.append(NEW_LINE_SEPARATOR);
					stockOrderBuilder.append(stBuf.toString());
					stBuf.setLength(0);
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					if (!productDAO.isArticleCodeExist(row.getCell(uniqueIdentifier).toString(), image.getUserId())) {
						stBuf.append(NEW_LINE_SEPARATOR);
						for (Map.Entry<String, Integer> entry : newProductColumnIndexes.entrySet()) {
							if (entry.getValue() != -1) {
								stBuf.append(row.getCell(entry.getValue()));
							} else if (entry.getKey().equalsIgnoreCase("ARTICLE_TAGS")) {
								stBuf.append(DateUtility.getDateByStringFormat(new Date(),
										DateUtility.DATE_FORMAT_YYYYMMDDHHMMSS));
							}
							stBuf.append(COMMA_DELIMITER);
						}
						stBuf.append(NEW_LINE_SEPARATOR);
						newProductBuilder.append(stBuf.toString());
					}
					stBuf.setLength(0);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			genarateFiles(image, stockOrderBuilder, newProductBuilder);
		} catch (Exception e) {
			logger.error("triggerCampaign:Error : ", e);
		}
	}

	void genarateFiles(Image image, StringBuilder stockOrderBuilder, StringBuilder newProductBuilder)
			throws IOException, Exception {
		SyncMessage syncMessage = new SyncMessage();
		try {
			String path = rtsProperties.getImagePath() + "/" + image.getUserId() + "/" + image.getSupplierId() + "/"
					+ image.getSupplierId() + "_";
			String stockOrderPath = path + "Stock_Order.csv";
			String newProductPath = path + "New_Product.csv";
			File fileStockOrder = new File(stockOrderPath);
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileStockOrder))) {
				writer.write(stockOrderBuilder.toString());
			}
			File fileNewProduct = new File(newProductPath);
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileNewProduct))) {
				writer.write(newProductBuilder.toString());
			}
			image.setStockOrderPath(stockOrderPath);
			image.setNewProductPath(newProductPath);
			image.setIsActive(true);
			imageDAO.updateImage(image);
			syncMessage.setUserId(image.getUserId());
			syncMessage.setMessage("File " + image.getName() + " validation complete.");
			sseEmitter.send(syncMessage);
		} catch (Exception e1) {
			syncMessage.setMessage("File " + image.getName() + " validation failed");
			sseEmitter.send(syncMessage);
		}
	}

	@Async
	public void generateStockAndProductsXLSX(Image image, Template template, XSSFRow row, int rowNo) {
		try {
			logger.info("triggerCampaign started");
			User user = userDAO.getUser(image.getUserId());
			productService.syncProductsIntermediate(user.getOrganizationId());
			logger.info("triggerCampaign ended");

			String COMMA_DELIMITER = ",";
			String NEW_LINE_SEPARATOR = "\n";
			TemplateFileConfigModel stockTFCM = getTFCM(image.getSupplierId(), FileImportType.STOCK.name());
			TemplateFileConfigModel newProductTFCM = getTFCM(image.getSupplierId(), FileImportType.NEWPRODUCT.name());

			StringBuffer stockHeaderForKorona = getCSVHeader(stockTFCM);
			StringBuffer newProductHeaderForKorona = getCSVHeader(newProductTFCM);

			Map<String, Integer> stockColumnIndexes = new LinkedHashMap<String, Integer>();
			Map<String, Integer> newProductColumnIndexes = new LinkedHashMap<String, Integer>();

			StringBuilder stockOrderBuilder = new StringBuilder();
			stockOrderBuilder.append(stockHeaderForKorona.toString());
			stockOrderBuilder.append(NEW_LINE_SEPARATOR);

			StringBuilder newProductBuilder = new StringBuilder();
			newProductBuilder.append(newProductHeaderForKorona.toString());
			newProductBuilder.append(NEW_LINE_SEPARATOR);

			StringBuilder stBuf = new StringBuilder();

			int uniqueIdentifier = -1;
			if (true) {
				int colNum = row.getLastCellNum();
				List<String> listColumns = new ArrayList<>();
				for (int j = 0; j < colNum; j++)
					listColumns.add(row.getCell(j).toString());

				uniqueIdentifier = listColumns.indexOf(template.getUniqueIdentifier());
				if (stockTFCM != null && stockTFCM.getTemplateFileConfigAttributeModels() != null
						&& stockTFCM.getTemplateFileConfigAttributeModels().size() > 0) {
					for (TemplateFileConfigAttributeModel tfcam : stockTFCM.getTemplateFileConfigAttributeModels())
						stockColumnIndexes.put(tfcam.getSupplierColumn(),
								listColumns.indexOf(tfcam.getSupplierColumn()));
				}
				if (newProductTFCM != null && newProductTFCM.getTemplateFileConfigAttributeModels() != null
						&& newProductTFCM.getTemplateFileConfigAttributeModels().size() > 0) {
					for (TemplateFileConfigAttributeModel tfcam : newProductTFCM.getTemplateFileConfigAttributeModels())
						newProductColumnIndexes.put(tfcam.getSupplierColumn(),
								listColumns.indexOf(tfcam.getSupplierColumn()));
				}
			}
			File excel = new File(image.getPath());
			FileInputStream fis = new FileInputStream(excel);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet ws = wb.getSheetAt(0);
			int rowNum = ws.getLastRowNum() + 1;
			for (int i = rowNo + 1; i < rowNum; i++) {
				try {
					row = ws.getRow(i);
					for (Map.Entry<String, Integer> entry : stockColumnIndexes.entrySet()) {
						if (entry.getValue() != -1)
							stBuf.append(row.getCell(entry.getValue()));
						stBuf.append(COMMA_DELIMITER);
					}
					stBuf.append(NEW_LINE_SEPARATOR);
					stockOrderBuilder.append(stBuf.toString());
					stBuf.setLength(0);
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					if (!productDAO.isArticleCodeExist(row.getCell(uniqueIdentifier).toString(), image.getUserId())) {
						stBuf.append(NEW_LINE_SEPARATOR);
						for (Map.Entry<String, Integer> entry : newProductColumnIndexes.entrySet()) {
							if (entry.getValue() != -1) {
								stBuf.append(row.getCell(entry.getValue()));
							} else if (entry.getKey().equalsIgnoreCase("ARTICLE_TAGS")) {
								stBuf.append(DateUtility.getDateByStringFormat(new Date(),
										DateUtility.DATE_FORMAT_YYYYMMDDHHMMSS));
							}
							stBuf.append(COMMA_DELIMITER);
						}
						stBuf.append(NEW_LINE_SEPARATOR);
						newProductBuilder.append(stBuf.toString());
					}
					stBuf.setLength(0);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			genarateFiles(image, stockOrderBuilder, newProductBuilder);
		} catch (Exception e) {
			logger.error("triggerCampaign:Error : ", e);
		}
	}
}
