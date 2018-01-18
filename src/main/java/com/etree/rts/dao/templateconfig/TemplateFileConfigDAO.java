package com.etree.rts.dao.templateconfig;

import java.util.List;

import com.etree.rts.domain.templateconfig.TemplateFileConfig;
import com.etree.rts.domain.templateconfig.TemplateFileConfigAttribute;
import com.etree.rts.response.Response;

public interface TemplateFileConfigDAO {
	public Response addTemplateFileConfig(TemplateFileConfig templateFileConfig) throws Exception;

	public Response addTemplateFileConfigAttribute(TemplateFileConfigAttribute templateFileConfigAttribute)
			throws Exception;

	public List<TemplateFileConfig> getTemplateFileConfigs(String userId) throws Exception;

	public List<TemplateFileConfigAttribute> getTemplateFileConfigAttributes(String templateId) throws Exception;

	public List<TemplateFileConfig> getTemplateFileConfigsByTemplateId(String templateId) throws Exception;

	public Response updateTemplateFileConfig(TemplateFileConfig templateFileConfig) throws Exception;

	public Response deleteTemplateFileConfigAttribute(String templateId) throws Exception;

	public List<TemplateFileConfig> getTemplateFileConfigBySupplier(String supplierId) throws Exception;
	
	public TemplateFileConfig getTemplateFileConfigBySupplier(String supplierId, String type) throws Exception;
}
