package com.etree.rts.dao.orderConfigure;

import java.util.List;

import com.etree.rts.domain.orderConfigure.OrderConfigure;
import com.etree.rts.response.Response;

public interface OrderConfigureDAO {

	Response saveOrder(OrderConfigure orderConfigure) throws Exception;

	OrderConfigure getOrderConfigure(String id) throws Exception;

	List<OrderConfigure> getOrders() throws Exception;

	Response updateOrder(OrderConfigure order) throws Exception;

	Response Orderdelete(String id);

	

}
