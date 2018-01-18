package com.etree.rts.service.template;

import java.util.List;

import com.etree.rts.model.template.TemplateModel;
import com.etree.rts.response.Response;

public interface TemplateService {

	Response addTemplate(TemplateModel template) throws Exception;

	List<TemplateModel> getTemplates() throws Exception;

	List<TemplateModel> getTemplatesByUser(String userId) throws Exception;

	Response updateTemplate(TemplateModel template) throws Exception;

	TemplateModel getTemplate(String templateId) throws Exception;

	Response deleteTemplate(String templateId);

}
