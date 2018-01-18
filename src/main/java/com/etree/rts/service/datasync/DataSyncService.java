package com.etree.rts.service.datasync;

import java.util.List;

import com.etree.rts.model.datasync.DataSyncModel;
import com.etree.rts.response.Response;

public interface DataSyncService {

	Response saveData(DataSyncModel data) throws Exception;

	List<DataSyncModel> getDataById(String id) throws Exception;

	List<DataSyncModel> getDataByUser(String userId) throws Exception;
	
	List<DataSyncModel> getData() throws Exception;

	Response updateData(DataSyncModel data) throws Exception;

	Response deleteData(String id);

}
