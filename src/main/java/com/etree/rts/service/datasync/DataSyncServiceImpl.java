package com.etree.rts.service.datasync;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etree.rts.constant.Constants;
import com.etree.rts.constant.StatusCode;
import com.etree.rts.cron.ScheduledCron;
import com.etree.rts.dao.datasync.DataSyncDAO;
import com.etree.rts.domain.datasync.DataSync;
import com.etree.rts.mapper.datasync.DataSyncMapper;
import com.etree.rts.model.datasync.DataSyncModel;
import com.etree.rts.response.Response;
import com.etree.rts.utils.CommonUtils;

@Service("dataSyncService")
public class DataSyncServiceImpl implements DataSyncService, Constants {

	private static final Logger logger = LoggerFactory.getLogger(DataSyncServiceImpl.class);

	@Autowired
	DataSyncDAO dataSyncDAO;

	@Autowired
	DataSyncMapper dataSyncMapper;

	@Autowired
	public ScheduledCron scheduledCron;

	public DataSyncServiceImpl() {
	}

	@Override
	public Response saveData(DataSyncModel dataModel) throws Exception {
		try {
			DataSync data = new DataSync();
			BeanUtils.copyProperties(dataModel, data);
			data.setId(CommonUtils.getRandomUUID());
			data.setIsActive(true);
			Response response = dataSyncDAO.saveData(data);
			if (response.getStatus().equalsIgnoreCase(StatusCode.SUCCESS.name()))
				scheduledCron.processScheduledJob();
			return response;
		} catch (Exception ex) {
			logger.info("Exception Service:" + ex.getMessage());
		}
		return null;
	}

	@Override
	public List<DataSyncModel> getData() throws Exception {
		try {
			List<DataSync> data = dataSyncDAO.getData();
			return dataSyncMapper.entityList(data);
		} catch (Exception ex) {
			logger.info("Exception getData:", ex);
		}
		return null;
	}

	@Override
	public List<DataSyncModel> getDataById(String id) throws Exception {
		try {
			List<DataSync> data = dataSyncDAO.getDataById(id);
			return dataSyncMapper.entityList(data);
		} catch (Exception ex) {
			logger.info("Exception getData:", ex);
		}
		return null;
	}

	@Override
	public List<DataSyncModel> getDataByUser(String userId) throws Exception {
		try {
			List<DataSync> data = dataSyncDAO.getDataByUser(userId);
			return dataSyncMapper.entityList(data);
		} catch (Exception ex) {
			logger.info("Exception getData:", ex);
		}
		return null;
	}

	@Override
	public Response updateData(DataSyncModel dataSyncModel) throws Exception {
		try {
			DataSync data = new DataSync();
			BeanUtils.copyProperties(dataSyncModel, data);
			Response response = dataSyncDAO.updateData(data);
			if (response.getStatus().equalsIgnoreCase(StatusCode.SUCCESS.name()))
				scheduledCron.processScheduledJob();
			return response;
		} catch (Exception ex) {
			logger.info("Exception in updateAppointment:" + ex.getMessage());
		}
		return null;

	}

	@Override
	public Response deleteData(String id) {
		try {
			return dataSyncDAO.deleteData(id);
		} catch (Exception ex) {
			logger.info("Exception in updateAppointment:" + ex.getMessage());
		}
		return null;
	}

}
