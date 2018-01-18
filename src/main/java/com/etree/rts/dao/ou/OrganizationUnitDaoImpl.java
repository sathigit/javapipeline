package com.etree.rts.dao.ou;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.etree.rts.constant.Constants;
import com.etree.rts.constant.StatusCode;
import com.etree.rts.domain.organization.Organization;
import com.etree.rts.domain.ou.OrganizationUnit;
import com.etree.rts.response.Response;
import com.etree.rts.utils.CommonUtils;

@Repository
public class OrganizationUnitDaoImpl implements OrganizationUnitDAO, Constants {
	private static final Logger logger = LoggerFactory.getLogger(OrganizationUnitDaoImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public Response addOrganizationUnits(OrganizationUnit[] organizationUnits, String organizationId) throws Exception {
		Response response = CommonUtils.getResponseObject("Add OrganizationUnits data");
		try {
			if (organizationUnits != null) {
				for (OrganizationUnit organizationUnit : organizationUnits) {
					organizationUnit.setOrganizationId(organizationId);
					organizationUnit.setIsActive(true);
					if (isOrganizationUnitExist(organizationUnit.getUuid())) {
						updateOrganizationUnit(organizationUnit);
					} else {
						saveOrganizationUnit(organizationUnit);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Exception in addOrganizationUnits", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
		}
		return response;
	}

	@Override
	public Response saveOrganizationUnit(OrganizationUnit org) throws Exception {
		Response response = CommonUtils.getResponseObject("Add organization data");
		try {
			String sql = "INSERT INTO organizationUnit(uuid,name,organizationId,isActive,createdDate,modifiedDate) VALUES(?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql, new Object[] { org.getUuid(), org.getName(), org.getOrganizationId(),
					org.getIsActive(), new Date(), new Date() });
			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
				response.setData(org);
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in addOrganization", e);
			return CommonUtils.getDuplicateKeyMessage(response, e);
		}
		return response;
	}

	@Override
	public Response updateOrganizationUnit(OrganizationUnit org) throws Exception {
		Response response = CommonUtils.getResponseObject("Update organization data");
		try {
			String sql = "UPDATE organizationUnit SET name=?, organizationId=?,isActive=?,modifiedDate=? WHERE uuid=?";

			int res = jdbcTemplate.update(sql, org.getName(), org.getOrganizationId(), org.getIsActive(), new Date(),
					org.getUuid());

			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in update image data", e);
			return CommonUtils.getDuplicateKeyMessage(response, e);
		}
		return response;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public OrganizationUnit getOrganizationUnit(String uuid) throws Exception {
		try {
			String sql = "SELECT * FROM organizationUnit where uuid=?";
			return (OrganizationUnit) jdbcTemplate.queryForObject(sql, new Object[] { uuid },
					new BeanPropertyRowMapper(Organization.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			logger.error("Exception in getOrganization", e);
			return null;
		}
	}

	@Override
	public List<OrganizationUnit> getOrganizationUnits() throws Exception {
		try {
			String sql = "SELECT * FROM organizationUnit";
			List<OrganizationUnit> organizations = jdbcTemplate.query(sql, new Object[] {},
					new BeanPropertyRowMapper<OrganizationUnit>(OrganizationUnit.class));
			return organizations;
		} catch (Exception e) {
			logger.error("Exception in getOrganizations", e);
		}
		return null;
	}

	@Override
	public boolean isOrganizationUnitExist(String uuid) {
		try {
			String sql = "SELECT count(uuid) FROM organizationUnit WHERE uuid=?";
			int count = jdbcTemplate.queryForObject(sql, new Object[] { uuid }, Integer.class);
			boolean isExist = count > 0 ? true : false;
			return isExist;
		} catch (Exception e) {
			logger.error("Exception in isOrganizationExist: ", e);
		}
		return false;
	}

	public List<OrganizationUnit> getOrganizationUnits(String[] uuids) {
		try {
			// long diffTime = new Date().getTime();
			String sql = "SELECT * from organizationUnit  where isActive=1";
			if (uuids != null && uuids.length > 0) {
				sql += " AND(";
				for (String uuid : uuids)
					sql += " FIND_IN_SET( '" + uuid + "',uuid) OR";
				sql = sql.substring(0, sql.length() - 2);
				sql += ")";
			}
			List<OrganizationUnit> ous = namedParameterJdbcTemplate.query(sql,
					new BeanPropertyRowMapper<OrganizationUnit>(OrganizationUnit.class));
			return ous;
		} catch (Exception e) {
			logger.error("Exception in getOrganizationUnits: ", e);
			return null;
		}
	}
}
