package com.etree.rts.dao.commoditygroup;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.etree.rts.constant.Constants;
import com.etree.rts.constant.StatusCode;
import com.etree.rts.domain.commoditygroup.CommodityGroup;
import com.etree.rts.response.Response;
import com.etree.rts.utils.CommonUtils;

@Repository
public class CommodityGroupDAOImpl implements CommodityGroupDAO, Constants {
	private static final Logger logger = LoggerFactory.getLogger(CommodityGroupDAOImpl.class);
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public Response addCommodityGroups(CommodityGroup[] commodityGroups, String organizationId) throws Exception {
		Response response = CommonUtils.getResponseObject("Add CommodityGroup data");
		try {
			if (commodityGroups != null) {
				for (CommodityGroup commodityGroup : commodityGroups) {
					commodityGroup.setOrganizationId(organizationId);
					if (isCommodityGroupExist(commodityGroup.getUuid())) {
						updateCommodityGroup(commodityGroup);
					} else {
						saveCommodityGroup(commodityGroup);
					}
				}
			}

		} catch (Exception e) {
			logger.error("Exception in addCommodityGroups", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
		}
		return response;
	}

	@Override
	public Response saveCommodityGroup(CommodityGroup commodityGroup) throws Exception {
		Response response = CommonUtils.getResponseObject("Add CommodityGroup data");
		try {
			String sql = "INSERT INTO commodityGroup(organizationId,uuid,number,name,revision,deleted,parentCommodityGroup)"
					+ " VALUES(?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { commodityGroup.getOrganizationId(), commodityGroup.getUuid(),
							commodityGroup.getNumber(), commodityGroup.getName(), commodityGroup.getRevision(),
							commodityGroup.getDeleted(), commodityGroup.getParentCommodityGroup() });
			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in saveCommodityGroup", e);
			return CommonUtils.getDuplicateKeyMessage(response, e);
		}
		return response;
	}

	@Override
	public Response updateCommodityGroup(CommodityGroup commodityGroup) throws Exception {
		Response response = CommonUtils.getResponseObject("Add CommodityGroup data");
		try {
			String sql = "update commodityGroup set organizationId=?,number=?,name=?,revision=?,deleted=?,parentCommodityGroup=? where uuid=?";
			int res = jdbcTemplate.update(sql,
					new Object[] { commodityGroup.getOrganizationId(), commodityGroup.getNumber(),
							commodityGroup.getName(), commodityGroup.getRevision(), commodityGroup.getDeleted(),
							commodityGroup.getParentCommodityGroup(), commodityGroup.getUuid() });
			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in updateProduct", e);
			return CommonUtils.getDuplicateKeyMessage(response, e);
		}
		return response;
	}

	@Override
	public long getRevision(String organizationId) {
		try {
			String sql = "SELECT max(revision) FROM commodityGroup where organizationId=?";
			Long revision = jdbcTemplate.queryForObject(sql, new Object[] { organizationId }, Long.class);
			return revision;
		} catch (Exception e) {
			logger.error("Exception in getRevision: ", e);
		}
		return 0;
	}

	@Override
	public List<CommodityGroup> getCommodityGroups(String organizationId) {
		try {
			String sql = "SELECT * FROM commodityGroup where organizationId=? and deleted=0 order by name";

			return jdbcTemplate.query(sql, new Object[] { organizationId },
					new BeanPropertyRowMapper<CommodityGroup>(CommodityGroup.class));
		} catch (Exception e) {
			logger.error("Exception in getCommodityGroups", e);

		}
		return null;
	}

	@Override
	public boolean isCommodityGroupExist(String uuid) {
		try {
			String sql = "SELECT count(uuid) FROM commodityGroup WHERE uuid=?";
			int count = jdbcTemplate.queryForObject(sql, new Object[] { uuid }, Integer.class);
			boolean isExist = count > 0 ? true : false;
			return isExist;
		} catch (Exception e) {
			logger.error("Exception in isCommodityGroupExist: ", e);
		}
		return false;
	}

	@Override
	public long getCommodityGroupCount(String organizationId) {
		try {
			String sql = "SELECT count(*) FROM commodityGroup where organizationId=?";
			Long count = jdbcTemplate.queryForObject(sql, new Object[] { organizationId }, Long.class);
			return count;
		} catch (Exception e) {
			logger.error("Exception in getCommodityGroupCount: ", e);
		}
		return 0;
	}
}
