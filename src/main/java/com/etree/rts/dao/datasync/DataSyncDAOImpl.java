package com.etree.rts.dao.datasync;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.etree.rts.constant.Constants;
import com.etree.rts.constant.StatusCode;
import com.etree.rts.domain.datasync.DataSync;
import com.etree.rts.response.Response;
import com.etree.rts.utils.CommonUtils;

@Repository
public class DataSyncDAOImpl implements DataSyncDAO, Constants {
	private static final Logger logger = LoggerFactory.getLogger(DataSyncDAOImpl.class);
	@Autowired
	JdbcTemplate jdbcTemplate;

	public Response saveData(DataSync data) {
		Response response = CommonUtils.getResponseObject("Add Sync data");
		try {
			String sql = "INSERT INTO dataSync ( id,userId,supplierId,name,cronExpression,isActive,createdDate,modifiedDate) VALUES(?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql, new Object[] { data.getId(), data.getUserId(), data.getSupplierId(),
					data.getName(), data.getCronExpression(), data.getIsActive(),new Date(), new Date() });
			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in saveData", e);
			return CommonUtils.getDuplicateKeyMessage(response, e);
		}
		return response;
	}

	@Override
	public List<DataSync> getData() throws Exception {
		try {
			String sql = "SELECT * FROM dataSync";
			return jdbcTemplate.query(sql, new Object[] {}, new BeanPropertyRowMapper<DataSync>(DataSync.class));
		} catch (Exception e) {
			logger.error("Exception in getData", e);
		}
		return null;
	}

	@Override
	public List<DataSync> getDataById(String id) throws Exception {
		try {
			String sql = "SELECT * FROM dataSync where id=?";
			return jdbcTemplate.query(sql, new Object[] { id }, new BeanPropertyRowMapper<DataSync>(DataSync.class));
		} catch (Exception e) {
			logger.error("Exception in getData", e);
		}
		return null;
	}

	@Override
	public List<DataSync> getDataByUser(String userId) throws Exception {
		try {
			String sql = "SELECT * FROM dataSync where userId=?";
			return jdbcTemplate.query(sql, new Object[] { userId },
					new BeanPropertyRowMapper<DataSync>(DataSync.class));
		} catch (Exception e) {
			logger.error("Exception in getDataByUser", e);
		}
		return null;
	}

	@Override
	public Response updateData(DataSync data) throws Exception {
		Response response = CommonUtils.getResponseObject("Update sync data");
		try {
			String sql = "UPDATE dataSync SET userId=?,supplierId=?,name=?,cronExpression=?,isActive=?,modifiedDate=? WHERE id=?";

			int res = jdbcTemplate.update(sql, data.getUserId(), data.getSupplierId(), data.getName(),
					data.getCronExpression(), data.getIsActive(), new Date(), data.getId());

			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in update appointment data", e);
			return CommonUtils.getDuplicateKeyMessage(response, e);
		}
		return response;
	}

	@Override
	public Response deleteData(String id) {
		Response response = CommonUtils.getResponseObject("Delete sync data");
		try {
			String sql = "DELETE dataSync WHERE id=?";

			int res = jdbcTemplate.update(sql, new Object[] { id });
			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}

		} catch (Exception e) {
			logger.error("Exception in delete appointment data", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
		}
		return response;

	}
}
