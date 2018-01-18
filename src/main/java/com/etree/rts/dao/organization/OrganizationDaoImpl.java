package com.etree.rts.dao.organization;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.etree.rts.constant.Constants;
import com.etree.rts.constant.StatusCode;
import com.etree.rts.domain.organization.Organization;
import com.etree.rts.response.Response;
import com.etree.rts.utils.CommonUtils;

@Repository
public class OrganizationDaoImpl implements OrganizationDAO, Constants {
	private static final Logger logger = LoggerFactory.getLogger(OrganizationDaoImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public Response saveOrganization(Organization org) throws Exception {
		Response response = CommonUtils.getResponseObject("Add organization data");
		try {
			String sql = "INSERT INTO organization(organizationId,name,alias,labellerKey,koronaApiId,koronaSecret,koronaToken,lcbApiKey,lcbMmeCode,packageId,isActive,createdDate,modifiedDate) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { org.getOrganizationId(), org.getName(), org.getAlias(), org.getLabellerKey(),
							org.getKoronaApiId(), org.getKoronaSecret(), org.getKoronaToken(), org.getLcbApiKey(),
							org.getLcbMmeCode(), org.getPackageId(), org.getIsActive(), new Date(), new Date() });
			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
				response.setData(org);
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in saveOrganization", e);
			return CommonUtils.getDuplicateKeyMessage(response, e);
		}
		return response;
	}

	@Override
	public Response updateOrganization(Organization org) throws Exception {
		Response response = CommonUtils.getResponseObject("Update organization data");
		try {
			String sql = "UPDATE organization SET name=?, alias=?,labellerKey=?,koronaApiId=?, koronaSecret=?, koronaToken=?, lcbMmeCode=?, lcbMmeCode=?,packageId=?,isActive=?,modifiedDate=? WHERE organizationId=?";

			int res = jdbcTemplate.update(sql, org.getName(), org.getAlias(), org.getLabellerKey(),
					org.getKoronaApiId(), org.getKoronaSecret(), org.getKoronaToken(), org.getLcbApiKey(),
					org.getLcbMmeCode(), org.getPackageId(), org.getIsActive(), new Date(), org.getOrganizationId());

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
	public Organization getOrganization(String organizationId) throws Exception {
		try {
			String sql = "SELECT o.* ,u.userId,u.firstName,u.lastName,u.userName,u.password FROM organization o,user u where o.organizationId=? and o.organizationId=u.organizationId and u.isAdmin=1";
			return (Organization) jdbcTemplate.queryForObject(sql, new Object[] { organizationId },
					new BeanPropertyRowMapper(Organization.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			logger.error("Exception in getOrganization", e);
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Organization getOrg(String organizationId) throws Exception {
		try {
			String sql = "SELECT * FROM organization where organizationId=?";
			return (Organization) jdbcTemplate.queryForObject(sql, new Object[] { organizationId },
					new BeanPropertyRowMapper(Organization.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			logger.error("Exception in getOrg", e);
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Organization getOrganization(String organizationId, String userName) throws Exception {
		try {
			String sql = "SELECT * FROM organization o,user u where o.organizationId=? and o.organizationId=u.organizationId and u.userName=?";
			return (Organization) jdbcTemplate.queryForObject(sql, new Object[] { organizationId, userName },
					new BeanPropertyRowMapper(Organization.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			logger.error("Exception in getOrganization", e);
			return null;
		}
	}

	@Override
	public List<Organization> getOrganizations() throws Exception {
		try {
			String sql = "SELECT o.* ,u.userId,u.firstName,u.lastName,u.userName,u.password FROM organization o,user u where o.organizationId=u.organizationId and u.isAdmin=1";
			List<Organization> organizations = jdbcTemplate.query(sql, new Object[] {},
					new BeanPropertyRowMapper<Organization>(Organization.class));
			return organizations;
		} catch (Exception e) {
			logger.error("Exception in getOrganizations", e);
		}
		return null;
	}

	@Override
	public boolean isOrganizationExist(String organizationId) {
		try {
			String sql = "SELECT count(organizationId) FROM organization WHERE organizationId=?";
			int count = jdbcTemplate.queryForObject(sql, new Object[] { organizationId }, Integer.class);
			boolean isExist = count > 0 ? true : false;
			return isExist;
		} catch (Exception e) {
			logger.error("Exception in isOrganizationExist: ", e);
		}
		return false;
	}

	@Override
	public Response deleteOrganization(String organizationId) throws Exception {
		Response response = CommonUtils.getResponseObject("Delete organization data");
		try {
			String sql = "delete from organization WHERE organizationId=?";
			int rows = jdbcTemplate.update(sql, organizationId);
			if (rows > 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in deleteOrganization", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
		}
		return response;
	}

	@Override
	public Response updateOrganizationStatus(Organization organization) {
		Response response = CommonUtils.getResponseObject("Update organization data");
		try {
			String sql = "UPDATE organization SET isActive=?,modifiedDate=? WHERE organizationId=?";

			int res = jdbcTemplate.update(sql,organization.getIsActive(), new Date(), organization.getOrganizationId());

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
}
