package com.etree.rts.dao.template;

import java.util.List;

import com.etree.rts.domain.template.Template;
import com.etree.rts.response.Response;

public interface TemplateDAO {

	Response addTemplate(Template template) throws Exception;

	List<Template> getTemplates() throws Exception;

	List<Template> getTemplatesByUser(String userId) throws Exception;

	Response updateTemplate(Template template) throws Exception;

	Template getTemplate(String templateId) throws Exception;

	Template getTemplateBySupplier(String supplierId) throws Exception;
	
	Response deleteTemplate(String templateId);

}
