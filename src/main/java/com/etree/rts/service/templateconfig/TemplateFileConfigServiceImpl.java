package com.etree.rts.service.templateconfig;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etree.rts.constant.Constants;
import com.etree.rts.constant.StatusCode;
import com.etree.rts.dao.templateconfig.TemplateFileConfigDAO;
import com.etree.rts.domain.template.Template;
import com.etree.rts.domain.templateconfig.TemplateFileConfig;
import com.etree.rts.domain.templateconfig.TemplateFileConfigAttribute;
import com.etree.rts.mapper.templateconfig.TemplateFileConfigAttributeMapper;
import com.etree.rts.mapper.templateconfig.TemplateFileConfigMapper;
import com.etree.rts.model.templateconfig.TemplateFileConfigAttributeModel;
import com.etree.rts.model.templateconfig.TemplateFileConfigModel;
import com.etree.rts.response.Response;
import com.etree.rts.utils.CommonUtils;

@Service("templateFileConfigService")
public class TemplateFileConfigServiceImpl implements TemplateFileConfigService, Constants {

	private static final Logger logger = LoggerFactory.getLogger(TemplateFileConfigServiceImpl.class);

	@Autowired
	TemplateFileConfigDAO templateFileConfigDAO;

	@Autowired
	TemplateFileConfigMapper templateFileConfigMapper;

	@Autowired
	TemplateFileConfigAttributeMapper templateFileConfigAttributeMapper;

	public TemplateFileConfigServiceImpl() {
		// TODO
	}

	@Override
	public Response addTemplateFileConfig(TemplateFileConfigModel templateFileConfigModel) throws Exception {
		try {
			TemplateFileConfig templateFileConfig = new TemplateFileConfig();
			BeanUtils.copyProperties(templateFileConfigModel, templateFileConfig);
			templateFileConfig.setTemplateId(CommonUtils.getRandomUUID());
			templateFileConfig.setIsActive(true);
			Response response = templateFileConfigDAO.addTemplateFileConfig(templateFileConfig);
			if (response.getStatus().equals(StatusCode.SUCCESS.name())) {
				if (templateFileConfigModel.getTemplateFileConfigAttributeModels() != null
						&& templateFileConfigModel.getTemplateFileConfigAttributeModels().size() > 0) {
					for (TemplateFileConfigAttributeModel templateFileConfigAttributeModel : templateFileConfigModel
							.getTemplateFileConfigAttributeModels()) {
						TemplateFileConfigAttribute templateFileConfigAttribute = new TemplateFileConfigAttribute();
						templateFileConfigAttributeModel.setTemplateId(templateFileConfig.getTemplateId());
						BeanUtils.copyProperties(templateFileConfigAttributeModel, templateFileConfigAttribute);
						templateFileConfigDAO.addTemplateFileConfigAttribute(templateFileConfigAttribute);
					}
				}
			}
			return response;
		} catch (Exception ex) {
			logger.info("Exception in addTemplateFileConfig:" + ex.getMessage());
		}
		return null;
	}

	@Override
	public List<TemplateFileConfigModel> getTemplateFileConfigs(String userId) {
		try {
			List<TemplateFileConfigModel> templateFileConfigModels = null;
			List<TemplateFileConfig> templateFileConfigs = templateFileConfigDAO.getTemplateFileConfigs(userId);
			if (templateFileConfigs != null && templateFileConfigs.size() > 0) {
				templateFileConfigModels = templateFileConfigMapper.entityList(templateFileConfigs);
				for (TemplateFileConfigModel templateFileConfigModel : templateFileConfigModels) {
					List<TemplateFileConfigAttribute> templateFileConfigAttributes = templateFileConfigDAO
							.getTemplateFileConfigAttributes(templateFileConfigModel.getTemplateId());
					templateFileConfigModel.setTemplateFileConfigAttributeModels(
							templateFileConfigAttributeMapper.entityList(templateFileConfigAttributes));
				}
			}
			return templateFileConfigModels;
		} catch (Exception ex) {
			logger.info("Exception getTemplateFileConfigs:", ex);
		}
		return null;
	}

	@Override
	public List<TemplateFileConfigModel> getTemplateFileConfigsByTemplateId(String templateId) {
		try {
			List<TemplateFileConfigModel> templateFileConfigModels = null;
			List<TemplateFileConfig> templateFileConfigs = templateFileConfigDAO
					.getTemplateFileConfigsByTemplateId(templateId);
			if (templateFileConfigs != null && templateFileConfigs.size() > 0) {
				templateFileConfigModels = templateFileConfigMapper.entityList(templateFileConfigs);
				for (TemplateFileConfigModel templateFileConfigModel : templateFileConfigModels) {
					List<TemplateFileConfigAttribute> templateFileConfigAttributes = templateFileConfigDAO
							.getTemplateFileConfigAttributes(templateFileConfigModel.getTemplateId());
					templateFileConfigModel.setTemplateFileConfigAttributeModels(
							templateFileConfigAttributeMapper.entityList(templateFileConfigAttributes));
				}
			}
			return templateFileConfigModels;
		} catch (Exception ex) {
			logger.info("Exception getTemplateFileConfigs:", ex);
		}
		return null;
	}

	@Override
	public Response updateTemplateFileConfig(TemplateFileConfigModel templateFileConfigModel) throws Exception {
		try {
			TemplateFileConfig templateFileConfig = new TemplateFileConfig();
			BeanUtils.copyProperties(templateFileConfigModel, templateFileConfig);
			Response response = templateFileConfigDAO.updateTemplateFileConfig(templateFileConfig);
			if (response.getStatus().equals(StatusCode.SUCCESS.name())) {
				if (templateFileConfigModel.getTemplateFileConfigAttributeModels() != null
						&& templateFileConfigModel.getTemplateFileConfigAttributeModels().size() > 0) {
					templateFileConfigDAO.deleteTemplateFileConfigAttribute(templateFileConfig.getTemplateId());
					for (TemplateFileConfigAttributeModel templateFileConfigAttributeModel : templateFileConfigModel
							.getTemplateFileConfigAttributeModels()) {
						TemplateFileConfigAttribute templateFileConfigAttribute = new TemplateFileConfigAttribute();
						templateFileConfigAttributeModel.setTemplateId(templateFileConfig.getTemplateId());
						BeanUtils.copyProperties(templateFileConfigAttributeModel, templateFileConfigAttribute);
						templateFileConfigDAO.addTemplateFileConfigAttribute(templateFileConfigAttribute);
					}
				}
			}
			return response;
		} catch (Exception ex) {
			logger.info("Exception in addTemplateFileConfig:" + ex.getMessage());
		}
		return null;
	}

	@Override
	public List<TemplateFileConfigModel> getTemplateFileConfigBySupplier(String supplierId) {
		try {
			List<TemplateFileConfigModel> templateFileConfigModels = null;
			List<TemplateFileConfig> templateFileConfigs = templateFileConfigDAO
					.getTemplateFileConfigBySupplier(supplierId);
			if (templateFileConfigs != null && templateFileConfigs.size() > 0) {
				templateFileConfigModels = templateFileConfigMapper.entityList(templateFileConfigs);
				for (TemplateFileConfigModel templateFileConfigModel : templateFileConfigModels) {
					List<TemplateFileConfigAttribute> templateFileConfigAttributes = templateFileConfigDAO
							.getTemplateFileConfigAttributes(templateFileConfigModel.getTemplateId());
					templateFileConfigModel.setTemplateFileConfigAttributeModels(
							templateFileConfigAttributeMapper.entityList(templateFileConfigAttributes));
				}
			}
			return templateFileConfigModels;
		} catch (Exception ex) {
			logger.info("Exception getTemplateFileConfigs:", ex);
		}
		return null;
	}

	@Override
	public TemplateFileConfigModel getTemplateFileConfigBySupplier(String supplierId, String type) {
		try {
			TemplateFileConfigModel templateFileConfigModel = new TemplateFileConfigModel();
			TemplateFileConfig templateFileConfig = templateFileConfigDAO.getTemplateFileConfigBySupplier(supplierId,
					type);
			BeanUtils.copyProperties(templateFileConfig, templateFileConfigModel);
			List<TemplateFileConfigAttribute> templateFileConfigAttributes = templateFileConfigDAO
					.getTemplateFileConfigAttributes(templateFileConfigModel.getTemplateId());
			templateFileConfigModel.setTemplateFileConfigAttributeModels(
					templateFileConfigAttributeMapper.entityList(templateFileConfigAttributes));
			return templateFileConfigModel;
		} catch (Exception ex) {
			logger.info("Exception getTemplateFileConfigs:", ex);
		}
		return null;
	}
}
