package com.etree.rts.mapper.template;

import org.springframework.stereotype.Component;

import com.etree.rts.domain.template.Template;
import com.etree.rts.mapper.AbstractModelMapper;
import com.etree.rts.model.template.TemplateModel;



@Component
public class TemplateMapper extends AbstractModelMapper<TemplateModel, Template> {


	@Override
	public Class<TemplateModel> entityType() {
		
		return TemplateModel.class;
	}

	@Override
	public Class<Template> modelType() {
		
		return Template.class;
	}

}

