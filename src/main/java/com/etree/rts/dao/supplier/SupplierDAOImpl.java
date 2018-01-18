package com.etree.rts.dao.supplier;

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
import com.etree.rts.domain.supplier.Supplier;
import com.etree.rts.response.Response;
import com.etree.rts.utils.CommonUtils;

@Repository
public class SupplierDAOImpl implements SupplierDAO, Constants {
	private static final Logger logger = LoggerFactory.getLogger(SupplierDAOImpl.class);
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public Response addSupplier(Supplier supplier) throws Exception {
		Response response = CommonUtils.getResponseObject("Add supplier data");
		try {
			String sql = "INSERT INTO supplier (supplierId,userId,name,description,isActive,createdDate,modifiedDate) VALUES(?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql, new Object[] { supplier.getSupplierId(), supplier.getUserId(),
					supplier.getName(), supplier.getDescription(), supplier.getIsActive(), new Date(), new Date() });
			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in addSupplier", e);
			return CommonUtils.getDuplicateKeyMessage(response, e);
		}
		return response;
	}

	@Override
	public List<Supplier> getSuppliers() throws Exception {
		try {
			String sql = "SELECT * FROM supplier";
			List<Supplier> suppliers = jdbcTemplate.query(sql, new Object[] {},
					new BeanPropertyRowMapper<Supplier>(Supplier.class));
			return suppliers;
		} catch (Exception e) {
			logger.error("Exception in getSuppliers", e);
		}
		return null;
	}

	@Override
	public List<Supplier> getSuppliersByUser(String userId) throws Exception {
		try {
			String sql = "SELECT * FROM supplier where userId=?";
			List<Supplier> suppliers = jdbcTemplate.query(sql, new Object[] { userId },
					new BeanPropertyRowMapper<Supplier>(Supplier.class));
			return suppliers;
		} catch (Exception e) {
			logger.error("Exception in getSuppliers", e);
		}
		return null;
	}

	@Override
	public Response updateSupplier(Supplier supplier) throws Exception {
		Response response = CommonUtils.getResponseObject("Update supplier data");
		try {
			String sql = "UPDATE supplier SET userId=?, name=?, description=?,isActive=?,modifiedDate=? WHERE supplierId=?";

			int res = jdbcTemplate.update(sql, supplier.getUserId(), supplier.getName(), supplier.getDescription(),
					supplier.getIsActive(), new Date(), supplier.getSupplierId());

			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in update supplier data", e);
			return CommonUtils.getDuplicateKeyMessage(response, e);
		}
		return response;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Supplier getSupplier(String supplierId) throws Exception {
		try {
			String sql = "SELECT * FROM supplier where supplierId=?";
			return (Supplier) jdbcTemplate.queryForObject(sql, new Object[] { supplierId },
					new BeanPropertyRowMapper(Supplier.class));
		} catch (Exception e) {
			logger.error("Exception in getSupplier", e);
			return null;
		}
	}

	@Override
	public Response deleteSupplier(String supplierId) {
		Response response = CommonUtils.getResponseObject("Delete Supplier data");
		try {
			String sql = "DELETE FROM supplier  WHERE supplierId=?";

			int rows = jdbcTemplate.update(sql, supplierId);
			if (rows == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in delete Supplier data", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
		}
		return response;
	}

}
