package com.etree.rts.mapper.templateconfig;

import org.springframework.stereotype.Component;

import com.etree.rts.domain.templateconfig.TemplateFileConfigAttribute;
import com.etree.rts.mapper.AbstractModelMapper;
import com.etree.rts.model.templateconfig.TemplateFileConfigAttributeModel;

@Component
public class TemplateFileConfigAttributeMapper extends AbstractModelMapper<TemplateFileConfigAttributeModel, TemplateFileConfigAttribute> {


	@Override
	public Class<TemplateFileConfigAttributeModel> entityType() {
		
		return TemplateFileConfigAttributeModel.class;
	}

	@Override
	public Class<TemplateFileConfigAttribute> modelType() {
		
		return TemplateFileConfigAttribute.class;
	}

}

