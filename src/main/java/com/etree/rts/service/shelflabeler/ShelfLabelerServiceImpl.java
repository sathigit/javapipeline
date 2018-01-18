package com.etree.rts.service.shelflabeler;

import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.etree.rts.constant.FileType;
import com.etree.rts.constant.StatusCode;
import com.etree.rts.dao.shelflabeler.ShelfLabelerDAO;
import com.etree.rts.dao.slo.ShelfLabelerOrgDAO;
import com.etree.rts.domain.role.Role;
import com.etree.rts.domain.shelfLabeler.ShelfLabeler;
import com.etree.rts.mapper.shelflabeler.ShelfLabelerMapper;
import com.etree.rts.model.shelfLabeler.ShelfLabelerModel;
import com.etree.rts.response.Response;
import com.etree.rts.utils.CommonUtils;
import com.etree.rts.utils.FileUtils;

@Service("shelfLabelerService")
public class ShelfLabelerServiceImpl implements ShelfLabelerService {

	private static final Logger logger = LoggerFactory.getLogger(ShelfLabelerService.class);

	@Autowired
	ShelfLabelerDAO shelfLabelerDAO;

	@Autowired
	ShelfLabelerOrgDAO shelfLabelerOrgDAO;

	@Autowired
	ShelfLabelerMapper shelfLabelerMapper;

	@Override
	public Response saveShelfLabel(ShelfLabelerModel shelfLabelerModel, MultipartFile file) {
		Response response = CommonUtils.getResponseObject("File Upload");
		try {
			FileType fileType = FileType.valueOf(FilenameUtils.getExtension(file.getOriginalFilename()).toUpperCase());
			switch (fileType) {
			case XML:
				return saveXMLFile(shelfLabelerModel, file);
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

	private Response saveXMLFile(ShelfLabelerModel shelfLabelerModel, MultipartFile file) {
		try {
			Response response = CommonUtils.getResponseObject("Add XML file");
			ShelfLabeler shelfLabeler = getFileObject(shelfLabelerModel, file);
			response = shelfLabelerDAO.saveShelfLabel(shelfLabeler);

			return response;
		} catch (Exception ex) {
			logger.info("Exception Service:" + ex.getMessage());
		}
		return null;
	}

	private ShelfLabeler getFileObject(ShelfLabelerModel shelfLabelerModel, MultipartFile file) {
		ShelfLabeler shelfLabeler = new ShelfLabeler();
		try {
			BeanUtils.copyProperties(shelfLabelerModel, shelfLabeler);
			shelfLabeler.setFileName(file.getOriginalFilename());
			shelfLabeler.setShelfLabelerId(CommonUtils.getRandomUUID());
			shelfLabeler.setIsActive(true);
			int version = shelfLabelerDAO.getFileMaxVersion(shelfLabelerModel.getName());// Need
																							// to
																							// get
																							// the
																							// file
																							// ShelfLabeler
																							// object
																							// and
																							// check
																							// for
																							// version
			shelfLabeler.setVersion(version + 1);
			try {
				byte[] bytes = file.getBytes();
				shelfLabeler.setContent(bytes);
			} catch (Exception e) {
				logger.info("Exception in getFileObject:" + e.getMessage());
			}
		} catch (Exception ex) {
			logger.info("Exception Service:" + ex.getMessage());
		}
		return shelfLabeler;

	}

	@Override
	public List<ShelfLabelerModel> getShelfLabels() {
		try {
			List<ShelfLabeler> shelfLabelers = shelfLabelerDAO.getShelfLabels();
			return shelfLabelerMapper.entityList(shelfLabelers);
		} catch (Exception ex) {
			logger.info("Exception getSuppliers:", ex);
		}
		return null;
	}

	@Override
	public List<ShelfLabelerModel> getShelfLabels(String name) {
		try {
			List<ShelfLabeler> shelfLabelers = shelfLabelerDAO.getShelfLabels(name);
			return shelfLabelerMapper.entityList(shelfLabelers);
		} catch (Exception ex) {
			logger.info("Exception getSuppliers:", ex);
		}
		return null;
	}

	@Override
	public ShelfLabelerModel getShelfLabel(String name) {
		try {
			ShelfLabeler shelfLabeler = shelfLabelerDAO.getShelfLabel(name);
			shelfLabeler.setContent(FileUtils.encryptOrDecrypt(shelfLabeler.getContent(),
					shelfLabelerOrgDAO.getOrgIdByName(name), true));
			// shelflabeler.setContent(FileUtils.encrypt(shelflabeler.getContent(),
			// organizationId));
			// byte[] decrypt = FileUtils.decrypt(shelflabeler.getContent(),
			// organizationId);
			// String s = new String(decrypt);
			// System.out.println("Text Decryted : " + s);
			return shelfLabelerMapper.entity(shelfLabeler);
		} catch (Exception e) {
			logger.info("Exception getImage:", e);
			throw new RuntimeException("FAIL!");
		}
	}

	@Override
	public List<ShelfLabelerModel> getShelfLabelsByVersion() {
		try {
			List<ShelfLabeler> shelfLabelers = shelfLabelerDAO.getShelfLabelsByVersion();
			return shelfLabelerMapper.entityList(shelfLabelers);
		} catch (Exception ex) {
			logger.info("Exception getSuppliers:", ex);
		}
		return null;
	}

	@Override
	public List<ShelfLabelerModel> getShelfLabelsByOrg(String organizationId) {
		try {
			List<ShelfLabeler> shelfLabelers = shelfLabelerDAO.getShelfLabelsByOrg(organizationId);
			if (shelfLabelers != null && !shelfLabelers.isEmpty()) {
				for (ShelfLabeler shelfLabeler : shelfLabelers) {
					shelfLabeler
							.setContent(FileUtils.encryptOrDecrypt(shelfLabeler.getContent(), organizationId, true));
				}
				// for (ShelfLabeler shelfLabeler : shelfLabelers) {
				// shelfLabeler
				// .setContent(FileUtils.encryptOrDecrypt(shelfLabeler.getContent(),
				// organizationId, false));
				// }
			}
			return shelfLabelerMapper.entityList(shelfLabelers);
		} catch (Exception ex) {
			logger.info("Exception getShelfLabelsByOrg:", ex);
		}
		return null;
	}

	@Override
	public List<ShelfLabelerModel> getShelfLabelsByLabellerKey(String labellerKey) {
		try {
			List<ShelfLabeler> shelfLabelers = shelfLabelerDAO.getShelfLabelsByLabellerKey(labellerKey);
			if (shelfLabelers != null && !shelfLabelers.isEmpty()) {
				for (ShelfLabeler shelfLabeler : shelfLabelers) {
					shelfLabeler.setContent(FileUtils.encryptOrDecrypt(shelfLabeler.getContent(), labellerKey, true));
				}
			}
			return shelfLabelerMapper.entityList(shelfLabelers);
		} catch (Exception ex) {
			logger.info("Exception getShelfLabelsByOrg:", ex);
		}
		return null;
	}

	@Override
	public boolean isShelfLabellerNameExist(String name) throws Exception {
		try {
			return shelfLabelerDAO.isShelfLabellerNameExist(name);
			
		} catch (Exception ex) {
			logger.info("Exception ShelfLabellerNameExist:", ex);
		}
		return (Boolean) null;
		
	}
}
