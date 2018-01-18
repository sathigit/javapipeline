package com.etree.rts.dao.orderConfigure;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.etree.rts.constant.Constants;
import com.etree.rts.constant.StatusCode;
import com.etree.rts.domain.orderConfigure.OrderConfigure;
import com.etree.rts.response.Response;
import com.etree.rts.utils.CommonUtils;


@Repository
public class OrderConfigureDAOImpl implements OrderConfigureDAO, Constants {
	private static final Logger logger = LoggerFactory.getLogger(OrderConfigureDAOImpl.class);
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public Response saveOrder(OrderConfigure orderConfigure) {
		Response response = CommonUtils.getResponseObject("Add order data");
		try {
			String sql = "INSERT INTO orderConfigure (id,supplierId,templateName,columnIdentifier,dataIdentifier) VALUES(?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { orderConfigure.getId(),orderConfigure.getSupplierId(), orderConfigure.getTemplateName(),orderConfigure.getDataIdentifier(),orderConfigure.getDataIdentifier()});
			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in addUser", e);
			return CommonUtils.getDuplicateKeyMessage(response, e);
		}
		return response;
	}

	@Override
	public OrderConfigure getOrderConfigure(String id) {
		try {
			String sql = "SELECT * FROM orderConfigure where id=?";
			return (OrderConfigure) jdbcTemplate.queryForObject(sql, new Object[] { id },
					new BeanPropertyRowMapper(OrderConfigure.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			logger.error("Exception in getOrderConfigure", e);
			return null;
		}
	}

	@Override
	public List<OrderConfigure> getOrders() {
		try {
			String sql = "SELECT * FROM orderConfigure";
			List<OrderConfigure> orders = jdbcTemplate.query(sql, new Object[] {}, new BeanPropertyRowMapper<OrderConfigure>(OrderConfigure.class));
			return orders;
		} catch (Exception e) {
			logger.error("Exception in getUsers", e);
		}
		return null;
	}

	@Override
	public Response updateOrder(OrderConfigure order) throws Exception {
		Response response = CommonUtils.getResponseObject("Update OrderConfigure data");
		try {
			String sql = "UPDATE orderConfigure SET templateName=?, columnIdentifier=?,dataIdentifier=? WHERE id=?";

			int res = jdbcTemplate.update(sql, order.getTemplateName(), order.getColumnIdentifier(),
					order.getDataIdentifier(),order.getId());

			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in update OrderConfigure data", e);
			return CommonUtils.getDuplicateKeyMessage(response, e);
		}
		return response;
	}

	@Override
	public Response Orderdelete(String id) {
		Response response = CommonUtils.getResponseObject("Delete OrderConfigure data");
		try {
			String sql = "DELETE FROM orderConfigure  WHERE id=?";

			int res= (int) jdbcTemplate.update(sql, new Object[] { id });

			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in update OrderConfigure data", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
		}
		return response;

	}

	
	

}
