package com.etree.rts.mapper.image;

import org.springframework.stereotype.Component;

import com.etree.rts.domain.image.Image;
import com.etree.rts.mapper.AbstractModelMapper;
import com.etree.rts.model.image.ImageModel;

@Component
public class ImageMapper extends AbstractModelMapper<ImageModel, Image> {

	@Override
	public Class<ImageModel> entityType() {
		return ImageModel.class;
	}

	@Override
	public Class<Image> modelType() {
		return Image.class;
	}

}
