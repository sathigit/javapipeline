package com.etree.rts.mapper.shelflabeler;

import org.springframework.stereotype.Component;

import com.etree.rts.domain.shelfLabeler.ShelfLabeler;
import com.etree.rts.mapper.AbstractModelMapper;
import com.etree.rts.model.shelfLabeler.ShelfLabelerModel;
@Component
public class ShelfLabelerMapper extends AbstractModelMapper<ShelfLabelerModel, ShelfLabeler> {

	@Override
	public Class<ShelfLabelerModel> entityType() {
		
		return ShelfLabelerModel.class;
	}

	@Override
	public Class<ShelfLabeler> modelType() {
		
		return ShelfLabeler.class;
	}
}
