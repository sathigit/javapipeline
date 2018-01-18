package com.etree.rts.dao.ksuppliers;

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
import com.etree.rts.domain.ksuppliers.KSupplier;
import com.etree.rts.response.Response;
import com.etree.rts.utils.CommonUtils;

@Repository
public class KSupplierDAOImpl implements KSupplierDAO, Constants {
	private static final Logger logger = LoggerFactory.getLogger(KSupplierDAOImpl.class);
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public Response addKSuppliers(KSupplier[] kSuppliers, String organizationId) throws Exception {
		Response response = CommonUtils.getResponseObject("Add Recipts data");
		try {
			if (kSuppliers != null) {
				for (KSupplier kSupplier : kSuppliers) {
					kSupplier.setOrganizationId(organizationId);
					if (isKSupplierExist(kSupplier.getUuid())) {
						updateKSupplier(kSupplier);
					} else {
						saveKSupplier(kSupplier);
					}
				}
			}

		} catch (Exception e) {
			logger.error("Exception in addKSupplierss", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
		}
		return response;
	}

	@Override
	public Response saveKSupplier(KSupplier kSupplier) throws Exception {
		Response response = CommonUtils.getResponseObject("Add kSuppliers data");
		try {
			String sql = "INSERT INTO kSupplier(organizationId,uuid,number,name,revision,deleted)"
					+ " VALUES(?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql, new Object[] { kSupplier.getOrganizationId(), kSupplier.getUuid(),
					kSupplier.getNumber(), kSupplier.getName(), kSupplier.getRevision(), kSupplier.getDeleted() });
			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in saveKSupplier", e);
			return CommonUtils.getDuplicateKeyMessage(response, e);
		}
		return response;
	}

	@Override
	public Response updateKSupplier(KSupplier kSupplier) throws Exception {
		Response response = CommonUtils.getResponseObject("Add product data");
		try {
			String sql = "update kSupplier set organizationId=?,number=?,name=?,revision=?,deleted=? where uuid=?";
			int res = jdbcTemplate.update(sql, new Object[] { kSupplier.getOrganizationId(), kSupplier.getNumber(),
					kSupplier.getName(), kSupplier.getRevision(), kSupplier.getDeleted(), kSupplier.getUuid() });
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
			String sql = "SELECT max(revision) FROM kSupplier where organizationId=?";
			Long revision = jdbcTemplate.queryForObject(sql, new Object[] { organizationId }, Long.class);
			return revision;
		} catch (Exception e) {
			logger.error("Exception in getRevision: ", e);
		}
		return 0;
	}

	@Override
	public List<KSupplier> getKSuppliers(String organizationId) {
		try {
			String sql = "SELECT * FROM kSupplier where organizationId=? and deleted=0 order by name";

			return jdbcTemplate.query(sql, new Object[] { organizationId },
					new BeanPropertyRowMapper<KSupplier>(KSupplier.class));
		} catch (Exception e) {
			logger.error("Exception in getKSuppliers", e);

		}
		return null;
	}

	@Override
	public boolean isKSupplierExist(String uuid) {
		try {
			String sql = "SELECT count(uuid) FROM kSupplier WHERE uuid=?";
			int count = jdbcTemplate.queryForObject(sql, new Object[] { uuid }, Integer.class);
			boolean isExist = count > 0 ? true : false;
			return isExist;
		} catch (Exception e) {
			logger.error("Exception in isKSupplierExist: ", e);
		}
		return false;
	}
	
	@Override
	public long getKSupplierCount(String organizationId) {
		try {
			String sql = "SELECT count(*) FROM kSupplier where organizationId=?";
			Long count = jdbcTemplate.queryForObject(sql, new Object[] {organizationId}, Long.class);
			return count;
		} catch (Exception e) {
			logger.error("Exception in getKSupplierCount: ", e);
		}
		return 0;
	}
}
