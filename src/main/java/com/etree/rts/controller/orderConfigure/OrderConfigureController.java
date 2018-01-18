package com.etree.rts.controller.orderConfigure;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.etree.rts.constant.Constants;
import com.etree.rts.constant.StatusCode;
import com.etree.rts.model.orderConfigure.OrderConfigureModel;
import com.etree.rts.response.ErrorObject;
import com.etree.rts.response.Response;
import com.etree.rts.service.orderConfigure.OrderConfigureService;
import com.etree.rts.utils.CommonUtils;

@RestController
@RequestMapping("/v1")
public class OrderConfigureController implements Constants{
	

	private static final Logger logger = LoggerFactory.getLogger(OrderConfigureController.class);
	@Autowired
	OrderConfigureService orderService;
	
	
	@RequestMapping(value = "/saveOrder", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Response saveOredr(@RequestBody OrderConfigureModel order, HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.info("addOrderConfigure: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("addOrderConfigure: Received request: " + CommonUtils.getJson(order));
		return orderService.saveOrder(order);
	}
	
	@RequestMapping(value = "/getOrder/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getOrder(@PathVariable("id") String id, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("getOrder: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		OrderConfigureModel orderModel = orderService.getOrderConfigure(id);
		Response res = CommonUtils.getResponseObject("OrderConfigure Details");
		if (orderModel == null) {
			ErrorObject err = CommonUtils.getErrorResponse("Order Not Found", "Order Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(orderModel);
		}
		logger.info("getOrderconfigure: Sent response");
		return CommonUtils.getJson(res);
	}
	
	@RequestMapping(value = "/updateOrder", method = RequestMethod.PUT, produces = "application/json")
	public Response updateOrder(@RequestBody OrderConfigureModel order, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("updateOrder: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("updateOrder: Received request: " + CommonUtils.getJson(order));
		return orderService.updateOrder(order);
	}
	
	@RequestMapping(value = "/getOrders", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getOrders(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("getOrders: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<OrderConfigureModel> orders = orderService.getOrders();
		Response res = CommonUtils.getResponseObject("List of Orders");
		if (orders == null) {
			ErrorObject err = CommonUtils.getErrorResponse("Orders Not Found", "Orders Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(orders);
		}
		logger.info("getUsers: Sent response");
		return CommonUtils.getJson(res);
	}
	
	@RequestMapping(value = "/orderDelete/{id}", method = RequestMethod.DELETE, produces = "application/json", consumes = "application/json")
	public Response deleteOredr(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.info("deleteOrderConfigure: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("addOrderConfigure: Received request: " + CommonUtils.getJson(id));
		return orderService.OrderDelete(id);
	}
	
	
}
