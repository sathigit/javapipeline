package com.etree.rts.mapper.templateconfig;

import org.springframework.stereotype.Component;

import com.etree.rts.domain.templateconfig.TemplateFileConfig;
import com.etree.rts.mapper.AbstractModelMapper;
import com.etree.rts.model.templateconfig.TemplateFileConfigModel;

@Component
public class TemplateFileConfigMapper extends AbstractModelMapper<TemplateFileConfigModel, TemplateFileConfig> {


	@Override
	public Class<TemplateFileConfigModel> entityType() {
		
		return TemplateFileConfigModel.class;
	}

	@Override
	public Class<TemplateFileConfig> modelType() {
		
		return TemplateFileConfig.class;
	}

}

