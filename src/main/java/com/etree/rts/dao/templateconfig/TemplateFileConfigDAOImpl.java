package com.etree.rts.dao.templateconfig;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.etree.rts.constant.Constants;
import com.etree.rts.constant.StatusCode;
import com.etree.rts.domain.templateconfig.TemplateFileConfig;
import com.etree.rts.domain.templateconfig.TemplateFileConfigAttribute;
import com.etree.rts.response.Response;
import com.etree.rts.utils.CommonUtils;

@Repository
public class TemplateFileConfigDAOImpl implements TemplateFileConfigDAO, Constants {
	private static final Logger logger = LoggerFactory.getLogger(TemplateFileConfigDAOImpl.class);
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public Response addTemplateFileConfig(TemplateFileConfig templateFileConfig) throws Exception {
		Response response = CommonUtils.getResponseObject("Add templateFileConfig data");
		try {
			String sql = "INSERT INTO templateFileConfig (templateId,name,type,userId,supplierId,isActive,createdDate,modifiedDate) VALUES(?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { templateFileConfig.getTemplateId(), templateFileConfig.getName(),
							templateFileConfig.getType(), templateFileConfig.getUserId(),
							StringUtils.join(templateFileConfig.getSupplierId(), ','), templateFileConfig.getIsActive(),
							new Date(), new Date() });
			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
				return response;
			}
		} catch (Exception e) {
			logger.error("Exception in addTemplateFileConfig", e);
			return CommonUtils.getDuplicateKeyMessage(response, e);
		}
		return response;
	}

	@Override
	public Response addTemplateFileConfigAttribute(TemplateFileConfigAttribute templateFileConfigAttribute)
			throws Exception {
		Response response = CommonUtils.getResponseObject("Add templateFileConfigAttribute data");
		try {
			String sql = "INSERT INTO templateFileConfigAttribute (templateId,koronaColumn,supplierColumn,alias) VALUES(?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { templateFileConfigAttribute.getTemplateId(),
							templateFileConfigAttribute.getKoronaColumn(),
							templateFileConfigAttribute.getSupplierColumn(), templateFileConfigAttribute.getAlias() });
			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
				return response;
			}
		} catch (Exception e) {
			logger.error("Exception in addTemplateFileConfigAttribute", e);
			return CommonUtils.getDuplicateKeyMessage(response, e);
		}
		return response;
	}

	@Override
	public List<TemplateFileConfig> getTemplateFileConfigs(String userId) throws Exception {
		try {
			String sql = "SELECT * FROM templateFileConfig where userId=?";
			List<TemplateFileConfig> suppliers = jdbcTemplate.query(sql, new Object[] { userId },
					new BeanPropertyRowMapper<TemplateFileConfig>(TemplateFileConfig.class));
			return suppliers;
		} catch (Exception e) {
			logger.error("Exception in getTemplateFileConfigs", e);
		}
		return null;
	}

	@Override
	public List<TemplateFileConfigAttribute> getTemplateFileConfigAttributes(String templateId) throws Exception {
		try {
			String sql = "SELECT * FROM templateFileConfigAttribute where templateId=?";
			List<TemplateFileConfigAttribute> templateFileConfigAttributes = jdbcTemplate.query(sql,
					new Object[] { templateId },
					new BeanPropertyRowMapper<TemplateFileConfigAttribute>(TemplateFileConfigAttribute.class));
			return templateFileConfigAttributes;
		} catch (Exception e) {
			logger.error("Exception in getTemplateFileConfigAttributes", e);
		}
		return null;
	}

	@Override
	public List<TemplateFileConfig> getTemplateFileConfigsByTemplateId(String templateId) {
		try {
			String sql = "SELECT * FROM templateFileConfig where templateId=?";
			List<TemplateFileConfig> suppliers = jdbcTemplate.query(sql, new Object[] { templateId },
					new BeanPropertyRowMapper<TemplateFileConfig>(TemplateFileConfig.class));
			return suppliers;
		} catch (Exception e) {
			logger.error("Exception in getTemplateFileConfigs", e);
		}
		return null;
	}

	@Override
	public Response deleteTemplateFileConfigAttribute(String templateId) {
		Response response = CommonUtils.getResponseObject("Delete TemplateFileConfigAttribute data");
		try {
			String sql = "DELETE FROM templateFileConfigAttribute  WHERE templateId=?";

			int rows = jdbcTemplate.update(sql, templateId);
			if (rows > 0) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in delete TemplateFileConfigAttribute data", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
		}
		return response;

	}

	@Override
	public Response updateTemplateFileConfig(TemplateFileConfig templateFileConfig) {
		Response response = CommonUtils.getResponseObject("Update templateFileConfig data");
		try {
			String sql = "UPDATE templateFileConfig SET name=?, type=?,isActive=?,modifiedDate=? WHERE templateId=?";

			int res = jdbcTemplate.update(sql, templateFileConfig.getName(), templateFileConfig.getType(),
					templateFileConfig.getIsActive(), new Date(), templateFileConfig.getTemplateId());

			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in update templateFileConfig data", e);
			return CommonUtils.getDuplicateKeyMessage(response, e);
		}
		return response;

	}

	@Override
	public List<TemplateFileConfig> getTemplateFileConfigBySupplier(String supplierId) throws Exception {
		try {
			String sql = "SELECT * FROM templateFileConfig where ";
			sql += " (";
			sql += " FIND_IN_SET( '" + supplierId + "',supplierId)";
			sql += ")";
			List<TemplateFileConfig> suppliers = jdbcTemplate.query(sql, new Object[] {},
					new BeanPropertyRowMapper<TemplateFileConfig>(TemplateFileConfig.class));
			return suppliers;
		} catch (Exception e) {
			logger.error("Exception in getTemplateFileConfigs", e);
		}
		return null;
	}

	@Override
	public TemplateFileConfig getTemplateFileConfigBySupplier(String supplierId, String type) throws Exception {
		try {
			String sql = "SELECT * FROM templateFileConfig where type=? and ";
			sql += " (";
			sql += " FIND_IN_SET( '" + supplierId + "',supplierId)";
			sql += ")";
			TemplateFileConfig templateFileConfig =  (TemplateFileConfig) jdbcTemplate.queryForObject(sql, new Object[] {type  },
					new BeanPropertyRowMapper(TemplateFileConfig.class));
			return templateFileConfig;
		} catch (Exception e) {
			logger.error("Exception in getTemplateFileConfigs", e);
		}
		return null;
	}
}
