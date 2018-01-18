package com.etree.rts.dao.slo;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.etree.rts.constant.StatusCode;
import com.etree.rts.dao.image.ImageDAOImpl;
import com.etree.rts.domain.slo.ShelfLabelerOrg;
import com.etree.rts.response.Response;
import com.etree.rts.utils.CommonUtils;

@Repository
public class ShelfLabelerOrgDAOImpl implements ShelfLabelerOrgDAO {

	private static final Logger logger = LoggerFactory.getLogger(ImageDAOImpl.class);
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public Response saveShelfLabelerOrg(ShelfLabelerOrg shelfLabelerOrg) {
		Response response = CommonUtils.getResponseObject("Add organization data");
		try {
			String sql = "INSERT INTO shelfLabelerOrg(shelfLabelerId,organizationId,userId,isActive,createdDate,modifiedDate) VALUES(?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { shelfLabelerOrg.getShelfLabelerId(), shelfLabelerOrg.getOrganizationId(),
							shelfLabelerOrg.getUserId(), shelfLabelerOrg.getIsActive(), new Date(), new Date() });
			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
				response.setData(shelfLabelerOrg);
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in addShelfLabelerOrg", e);
			return CommonUtils.getDuplicateKeyMessage(response, e);
		}
		return response;
	}

	@Override
	public List<ShelfLabelerOrg> getShelfLabelerOrgs() throws Exception {
		try {
			String sql = "SELECT sl.name,sl.fileName, MAX(sl.version) AS version,o.name as orgName, sl.modifiedDate FROM shelfLabelerOrg slo,shelfLabeler sl, organization o where slo.organizationId = o.organizationId and slo.shelfLabelerId=sl.name GROUP BY sl.name,o.name order by o.name,sl.version";
			List<ShelfLabelerOrg> shelfLabelers = jdbcTemplate.query(sql, new Object[] {},
					new BeanPropertyRowMapper<ShelfLabelerOrg>(ShelfLabelerOrg.class));
			return shelfLabelers;
		} catch (Exception e) {
			logger.error("Exception in getShelfLabelerOrgs", e);
		}
		return null;
	}

	@Override
	public Response deleteShelfLabelerOrgs(String name) {
		Response response = CommonUtils.getResponseObject("Delete ShelfLabelerOrg data");
		try {
			String sql = "DELETE FROM shelfLabelerOrg  WHERE ShelfLabelerId=?";

			int rows = jdbcTemplate.update(sql, name);
			if (rows == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in delete ShelfLabelerOrg data", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
		}
		return response;
	}

	@Override
	public String getOrgIdByName(String name) throws Exception {
		try {
			String sql = "SELECT distinct(organizationId) FROM shelfLabelerOrg slo where shelfLabelerId=?";
			return jdbcTemplate.queryForObject(sql, new Object[] { name },
					new BeanPropertyRowMapper<String>(String.class));
		} catch (Exception e) {
			logger.error("Exception in getOrgIdByName", e);
		}
		return null;
	}
}
