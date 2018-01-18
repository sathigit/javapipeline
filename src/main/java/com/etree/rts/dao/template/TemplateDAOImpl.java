package com.etree.rts.dao.template;

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
import com.etree.rts.domain.template.Template;
import com.etree.rts.response.Response;
import com.etree.rts.utils.CommonUtils;

@Repository
public class TemplateDAOImpl implements TemplateDAO, Constants {
	private static final Logger logger = LoggerFactory.getLogger(TemplateDAOImpl.class);
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public Response addTemplate(Template template) throws Exception {
		Response response = CommonUtils.getResponseObject("Add template data");
		try {
			String sql = "INSERT INTO template (templateId,userId,name,uniqueIdentifier,dataIdentifier,supplierId,isActive,createdDate,modifiedDate) VALUES(?,?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql, new Object[] { template.getTemplateId(), template.getUserId(),
					template.getName(), template.getUniqueIdentifier(), template.getDataIdentifier(),
					StringUtils.join(template.getSupplierId(), ','), template.getIsActive(), new Date(), new Date() });
			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
				response.setData(template);
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in addTemplate", e);
			return CommonUtils.getDuplicateKeyMessage(response, e);
		}
		return response;
	}

	@Override
	public List<Template> getTemplates() throws Exception {
		try {
			String sql = "SELECT * FROM template";
			List<Template> templates = jdbcTemplate.query(sql, new Object[] {},
					new BeanPropertyRowMapper<Template>(Template.class));
			return templates;
		} catch (Exception e) {
			logger.error("Exception in getTemplates", e);
		}
		return null;
	}

	@Override
	public List<Template> getTemplatesByUser(String userId) throws Exception {
		try {
			String sql = "SELECT * FROM template where userId=?";
			List<Template> templates = jdbcTemplate.query(sql, new Object[] { userId },
					new BeanPropertyRowMapper<Template>(Template.class));
			return templates;
		} catch (Exception e) {
			logger.error("Exception in getTemplates", e);
		}
		return null;
	}

	@Override
	public Response updateTemplate(Template template) throws Exception {
		Response response = CommonUtils.getResponseObject("Update template data");
		try {
			String sql = "UPDATE template SET userId=?, name=?, uniqueIdentifier=?,dataIdentifier=?,supplierId=?,isActive=?,modifiedDate=? WHERE templateId=?";

			int res = jdbcTemplate.update(sql, template.getUserId(), template.getName(), template.getUniqueIdentifier(),
					template.getDataIdentifier(), StringUtils.join(template.getSupplierId(), ','),
					template.getIsActive(), new Date(), template.getTemplateId());

			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in update template data", e);
			return CommonUtils.getDuplicateKeyMessage(response, e);
		}
		return response;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Template getTemplate(String templateId) throws Exception {
		try {
			String sql = "SELECT * FROM template where templateId=?";
			return (Template) jdbcTemplate.queryForObject(sql, new Object[] { templateId },
					new BeanPropertyRowMapper(Template.class));
		} catch (Exception e) {
			logger.error("Exception in getTemplate", e);
			return null;
		}
	}

	@Override
	public Response deleteTemplate(String templateId) {
		Response response = CommonUtils.getResponseObject("Delete Template data");
		try {
			String sql = "DELETE FROM template  WHERE templateId=?";

			int rows = jdbcTemplate.update(sql, templateId);
			if (rows == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in delete Template data", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
		}
		return response;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Template getTemplateBySupplier(String supplierId) throws Exception {
		try {
			String sql = "SELECT * FROM template where ";
			sql += " (";
			sql += " FIND_IN_SET( '" + supplierId + "',supplierId)";
			sql += ")";
			return (Template) jdbcTemplate.queryForObject(sql, new Object[] {},
					new BeanPropertyRowMapper(Template.class));
		} catch (Exception e) {
			logger.error("Exception in getTemplateBySupplier", e);
			return null;
		}
	}
}
