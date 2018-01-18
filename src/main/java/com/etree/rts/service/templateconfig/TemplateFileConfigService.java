package com.etree.rts.service.templateconfig;

import java.util.List;

import com.etree.rts.model.templateconfig.TemplateFileConfigModel;
import com.etree.rts.response.Response;

public interface TemplateFileConfigService {

	public Response addTemplateFileConfig(TemplateFileConfigModel templateFileConfigModel) throws Exception;

	public List<TemplateFileConfigModel> getTemplateFileConfigs(String userId) throws Exception;

	public List<TemplateFileConfigModel> getTemplateFileConfigsByTemplateId(String templateId) throws Exception;

	public Response updateTemplateFileConfig(TemplateFileConfigModel templateFileConfigModel) throws Exception;

	public List<TemplateFileConfigModel> getTemplateFileConfigBySupplier(String supplierId) throws Exception;
	
	public TemplateFileConfigModel getTemplateFileConfigBySupplier(String supplierId,String type) throws Exception;
}
