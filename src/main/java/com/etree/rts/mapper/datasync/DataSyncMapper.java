package com.etree.rts.mapper.datasync;
import org.springframework.stereotype.Component;

import com.etree.rts.domain.datasync.DataSync;
import com.etree.rts.mapper.AbstractModelMapper;
import com.etree.rts.model.datasync.DataSyncModel;

@Component
public class DataSyncMapper extends AbstractModelMapper<DataSyncModel, DataSync> {

	@Override
	public Class<DataSyncModel> entityType() {
		return DataSyncModel.class;
	}

	@Override
	public Class<DataSync> modelType() {
		return DataSync.class;
	}
}
