package com.etree.rts.service.orderConfigure;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etree.rts.constant.Constants;
import com.etree.rts.dao.orderConfigure.OrderConfigureDAO;
import com.etree.rts.dao.session.SessionDAO;
import com.etree.rts.domain.orderConfigure.OrderConfigure;
import com.etree.rts.mapper.orderConfigure.OrderConfigureMapper;
import com.etree.rts.model.orderConfigure.OrderConfigureModel;
import com.etree.rts.response.Response;
import com.etree.rts.utils.CommonUtils;


@Service("oredrService")
public class OrderConfigureServiceImpl implements OrderConfigureService, Constants {
	
	@Autowired
	OrderConfigureDAO orderDAO;

	@Autowired
	OrderConfigureMapper orderMapper;
	

	@Autowired
	SessionDAO sessionDAO;
	private static final Logger logger = LoggerFactory.getLogger(OrderConfigureServiceImpl.class);
	
	
	public OrderConfigureServiceImpl() {
		// TODO
	}
	
	
	@Override
	public Response saveOrder(OrderConfigureModel order) {
	
		try {
			OrderConfigure orderConfigure = new OrderConfigure();
			BeanUtils.copyProperties(order, orderConfigure);
			orderConfigure.setId(CommonUtils.getRandomUUID());
			Response response = orderDAO.saveOrder(orderConfigure);
			return response;
		} catch (Exception ex) {
			logger.info("Exception Service:" + ex.getMessage());
		}
		return null;
	}


	@Override
	public OrderConfigureModel getOrderConfigure(String id) {
		try {
			OrderConfigure order = orderDAO.getOrderConfigure(id);
			OrderConfigureModel orderModel = new OrderConfigureModel();
			if (order == null)
				return null;
			BeanUtils.copyProperties(order, orderModel);
			return orderModel;
		} catch (Exception e) {
			logger.info("Exception getUser:", e);
			return null;
		}
	}


	@Override
	public List<OrderConfigureModel> getOrders(){
			try {
				List<OrderConfigure> orders = orderDAO.getOrders();
				return orderMapper.entityList(orders);
			} catch (Exception ex) {
				logger.info("Exception getUsers:", ex);
			}
			return null;
		}


	@Override
	public Response updateOrder(OrderConfigureModel orderModel) throws Exception {
		try {
			OrderConfigure order = new OrderConfigure();
			BeanUtils.copyProperties(orderModel, order);
			return orderDAO.updateOrder(order);
		} catch (Exception ex) {
			logger.info("Exception in updateSupplier:" + ex.getMessage());
		}
		return null;
	}


	@Override
	public Response OrderDelete(String id) {
	
		try{
			return orderDAO.Orderdelete(id);
		} catch (Exception ex) {
			logger.info("Exception in updateSupplier:" + ex.getMessage());
		}
		return null;

		
	}


	
	}
