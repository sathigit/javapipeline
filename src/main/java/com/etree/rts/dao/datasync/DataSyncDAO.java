package com.etree.rts.dao.datasync;

import java.util.List;

import com.etree.rts.domain.datasync.DataSync;
import com.etree.rts.response.Response;

public interface DataSyncDAO {

	Response saveData(DataSync data) throws Exception;

	List<DataSync> getData() throws Exception;

	List<DataSync> getDataById(String id) throws Exception;

	List<DataSync> getDataByUser(String userId) throws Exception;

	Response updateData(DataSync data) throws Exception;

	Response deleteData(String id);

}
