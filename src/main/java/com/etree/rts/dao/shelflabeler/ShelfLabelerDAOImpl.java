package com.etree.rts.dao.shelflabeler;

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
import com.etree.rts.dao.image.ImageDAOImpl;
import com.etree.rts.domain.shelfLabeler.ShelfLabeler;
import com.etree.rts.response.Response;
import com.etree.rts.utils.CommonUtils;

@Repository
public class ShelfLabelerDAOImpl implements ShelfLabelerDAO, Constants {

	private static final Logger logger = LoggerFactory.getLogger(ImageDAOImpl.class);
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public Response saveShelfLabel(ShelfLabeler shelfLabeler) {
		Response response = CommonUtils.getResponseObject("Add shelfLabeler data");
		try {
			String sql = "INSERT INTO shelfLabeler (shelfLabelerId,userId,name,fileName,content,version,isActive,createdDate,modifiedDate) VALUES(?,?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { shelfLabeler.getShelfLabelerId(),shelfLabeler.getUserId(), shelfLabeler.getName(),
							shelfLabeler.getFileName(), shelfLabeler.getContent(), shelfLabeler.getVersion(),
							shelfLabeler.getIsActive(), new Date(), new Date() });
			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
				response.setData(shelfLabeler);
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in saveShelfLabel", e);
			return CommonUtils.getDuplicateKeyMessage(response, e);
		}
		return response;
	}

	@Override
	public ShelfLabeler getShelfLabel(String name) {
		try {

			String sql = "SELECT fileName,version,content FROM shelfLabeler where name=? and version="
					+ getFileMaxVersion(name) + "";
			return (ShelfLabeler) jdbcTemplate.queryForObject(sql, new Object[] { name },
					new BeanPropertyRowMapper(ShelfLabeler.class));
		} catch (Exception e) {
			logger.error("Exception in getShelfLabel", e);
			return null;
		}
	}

	@Override
	public int getFileMaxVersion(String name) {
		try {
			String sql = "SELECT max(version) FROM shelfLabeler where name=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { name }, Integer.class);
		} catch (Exception e) {
			logger.error("Exception in getFileMaxVersion", e);
			return 0;
		}
	}

	@Override
	public List<ShelfLabeler> getShelfLabels(String name) throws Exception {
		try {
			String sql = "SELECT name,fileName,version, modifiedDate FROM shelfLabeler where name=? order by modifiedDate desc";
			List<ShelfLabeler> shelfLabelers = jdbcTemplate.query(sql, new Object[] { name },
					new BeanPropertyRowMapper<ShelfLabeler>(ShelfLabeler.class));
			return shelfLabelers;
		} catch (Exception e) {
			logger.error("Exception in getShelfLabels", e);
		}
		return null;
	}
	@Override
	public List<ShelfLabeler> getShelfLabels() throws Exception {
		try {
			String sql = "SELECT name,fileName,version, modifiedDate FROM shelfLabeler order by modifiedDate desc";
			List<ShelfLabeler> shelfLabelers = jdbcTemplate.query(sql, new Object[] { },
					new BeanPropertyRowMapper<ShelfLabeler>(ShelfLabeler.class));
			return shelfLabelers;
		} catch (Exception e) {
			logger.error("Exception in getShelfLabels", e);
		}
		return null;
	}
	
	@Override
	public List<ShelfLabeler> getShelfLabelsByVersion() throws Exception {
		try {
			String sql = "SELECT shelfLabelerId,name,fileName, MAX(version) AS version, modifiedDate FROM shelfLabeler GROUP BY name ORDER BY modifiedDate";
			List<ShelfLabeler> shelfLabelers = jdbcTemplate.query(sql, new Object[] { },
					new BeanPropertyRowMapper<ShelfLabeler>(ShelfLabeler.class));
			return shelfLabelers;
		} catch (Exception e) {
			logger.error("Exception in getShelfLabelsByVersion", e);
		}
		return null;
	}
	
	@Override
	public List<ShelfLabeler> getShelfLabelsByOrg(String organizationId) throws Exception {
		try {
			String sql = "SELECT shelfLabelerId,name,fileName,content, MAX(version) AS version, modifiedDate FROM shelfLabeler where name in(select shelfLabelerId from shelfLabelerOrg where organizationId=?) GROUP BY name ORDER BY modifiedDate";
			List<ShelfLabeler> shelfLabelers = jdbcTemplate.query(sql, new Object[] { organizationId },
					new BeanPropertyRowMapper<ShelfLabeler>(ShelfLabeler.class));
			return shelfLabelers;
		} catch (Exception e) {
			logger.error("Exception in getShelfLabels", e);
		}
		return null;
	}
	
	@Override
	public List<ShelfLabeler> getShelfLabelsByLabellerKey(String labellerKey) throws Exception {
		try {
			String sql = "SELECT shelfLabelerId,name,fileName,content, MAX(version) AS version, modifiedDate FROM shelfLabeler where name in(select shelfLabelerId from shelfLabelerOrg where organizationId in(select organizationId from organization where labellerKey=? )) GROUP BY name ORDER BY modifiedDate";
			List<ShelfLabeler> shelfLabelers = jdbcTemplate.query(sql, new Object[] { labellerKey },
					new BeanPropertyRowMapper<ShelfLabeler>(ShelfLabeler.class));
			return shelfLabelers;
		} catch (Exception e) {
			logger.error("Exception in getShelfLabels", e);
		}
		return null;
	}
	
	@Override
	public boolean isShelfLabellerNameExist(String name) {
		try {
			String sql = "SELECT count(name) FROM shelfLabeler WHERE name=?";
			int count = jdbcTemplate.queryForObject(sql, new Object[] { name }, Integer.class);
			boolean isExist = count > 0 ? true : false;
			return isExist;
		} catch (Exception e) {
			logger.error("Exception in isUserNameExist: ", e);
		}
		return false;
	}
}
