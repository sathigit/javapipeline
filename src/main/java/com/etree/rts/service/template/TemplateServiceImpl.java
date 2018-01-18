package com.etree.rts.service.template;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etree.rts.constant.Constants;
import com.etree.rts.dao.template.TemplateDAO;
import com.etree.rts.domain.template.Template;
import com.etree.rts.mapper.template.TemplateMapper;
import com.etree.rts.model.template.TemplateModel;
import com.etree.rts.response.Response;
import com.etree.rts.utils.CommonUtils;

@Service("templateService")
public class TemplateServiceImpl implements TemplateService, Constants {

	private static final Logger logger = LoggerFactory.getLogger(TemplateServiceImpl.class);

	@Autowired
	TemplateDAO templateDAO;

	@Autowired
	TemplateMapper templateMapper;

	public TemplateServiceImpl() {
		// TODO
	}

	@Override
	public Response addTemplate(TemplateModel templateModel) throws Exception {
		try {
			Template template = new Template();
			BeanUtils.copyProperties(templateModel, template);
			template.setTemplateId(CommonUtils.getRandomUUID());
			template.setIsActive(true);
			Response response = templateDAO.addTemplate(template);
			return response;
		} catch (Exception ex) {
			logger.info("Exception in addTemplate:" + ex.getMessage());
		}
		return null;
	}

	@Override
	public List<TemplateModel> getTemplates() {
		try {
			List<Template> templates = templateDAO.getTemplates();
			return templateMapper.entityList(templates);
		} catch (Exception ex) {
			logger.info("Exception getTemplates:", ex);
		}
		return null;
	}

	@Override
	public List<TemplateModel> getTemplatesByUser(String userId) {
		try {
			List<Template> templates = templateDAO.getTemplatesByUser(userId);
			return templateMapper.entityList(templates);
		} catch (Exception ex) {
			logger.info("Exception getTemplates:", ex);
		}
		return null;
	}

	@Override
	public Response updateTemplate(TemplateModel templateModel) throws Exception {
		try {
			Template template = new Template();
			BeanUtils.copyProperties(templateModel, template);
			return templateDAO.updateTemplate(template);
		} catch (Exception ex) {
			logger.info("Exception in updateTemplate:" + ex.getMessage());
		}
		return null;
	}

	@Override
	public TemplateModel getTemplate(String templateId) throws Exception {
		try {
			Template template = templateDAO.getTemplate(templateId);
			TemplateModel templateModel = new TemplateModel();
			if (template == null)
				return null;
			BeanUtils.copyProperties(template, templateModel);
			return templateModel;
		} catch (Exception e) {
			logger.info("Exception in getTemplate:", e);
			return null;
		}
	}

	@Override
	public Response deleteTemplate(String templateId) {
		try {
			return templateDAO.deleteTemplate(templateId);
		} catch (Exception ex) {
			logger.info("Exception in deleteTemplate:" + ex.getMessage());
		}
		return null;

	}

}
