package com.etree.rts.mapper.slo;

import org.springframework.stereotype.Component;

import com.etree.rts.domain.slo.ShelfLabelerOrg;
import com.etree.rts.mapper.AbstractModelMapper;
import com.etree.rts.model.slo.ShelfLabelerOrgModel;

@Component
public class ShelfLabelerOrgMapper extends AbstractModelMapper<ShelfLabelerOrgModel, ShelfLabelerOrg> {

	@Override
	public Class<ShelfLabelerOrgModel> entityType() {
		
		return ShelfLabelerOrgModel.class;
	}

	@Override
	public Class<ShelfLabelerOrg> modelType() {
		
		return ShelfLabelerOrg.class;
	}

}
