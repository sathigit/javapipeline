package com.etree.rts.service.orderConfigure;

import java.util.List;

import com.etree.rts.model.orderConfigure.OrderConfigureModel;
import com.etree.rts.response.Response;

public interface OrderConfigureService {

	Response saveOrder(OrderConfigureModel user) throws Exception;

	OrderConfigureModel getOrderConfigure(String id) throws Exception;

	List<OrderConfigureModel> getOrders() throws Exception;

	Response updateOrder(OrderConfigureModel orderModel) throws Exception;

	Response OrderDelete(String id);

	}
