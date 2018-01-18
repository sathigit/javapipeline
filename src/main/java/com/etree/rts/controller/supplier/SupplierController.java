package com.etree.rts.controller.supplier;

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
import com.etree.rts.model.supplier.SupplierModel;
import com.etree.rts.response.ErrorObject;
import com.etree.rts.response.Response;
import com.etree.rts.service.supplier.SupplierService;
import com.etree.rts.utils.CommonUtils;

@RestController
@RequestMapping("/v1")
public class SupplierController implements Constants {

	private static final Logger logger = LoggerFactory.getLogger(SupplierController.class);
	@Autowired
	SupplierService supplierService;

	@RequestMapping(value = "/supplier", method = RequestMethod.POST, produces = "application/json")
	public Response addSupplier(@RequestBody SupplierModel supplier, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("addSupplier: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("addSupplier: Received request: " + CommonUtils.getJson(supplier));
		return supplierService.addSupplier(supplier);
	}

	@RequestMapping(value = "/suppliers", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getSuppliers(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("getOrders: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<SupplierModel> suppliers = supplierService.getSuppliers();
		Response res = CommonUtils.getResponseObject("List of Suppliers");
		if (suppliers == null) {
			ErrorObject err = CommonUtils.getErrorResponse("Suppliers Not Found", "Suppliers Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(suppliers);
		}
		logger.info("getSuppliers: Sent response");
		return CommonUtils.getJson(res);
	}

	@RequestMapping(value = "/suppliers/user/{userId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getSuppliersByUser(@PathVariable("userId") String userId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("getOrders: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<SupplierModel> suppliers = supplierService.getSuppliersByUser(userId);
		Response res = CommonUtils.getResponseObject("List of Suppliers");
		if (suppliers == null) {
			ErrorObject err = CommonUtils.getErrorResponse("Suppliers Not Found", "Suppliers Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(suppliers);
		}
		logger.info("getSuppliers: Sent response");
		return CommonUtils.getJson(res);
	}

	@RequestMapping(value = "/supplier", method = RequestMethod.PUT, produces = "application/json")
	public Response updateSupplier(@RequestBody SupplierModel supplier, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("updateSupplier: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("updateSupplier: Received request: " + CommonUtils.getJson(supplier));
		return supplierService.updateSupplier(supplier);
	}

	@RequestMapping(value = "/supplier/{supplierId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getSupplier(@PathVariable("supplierId") String supplierId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("getSupplier: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		SupplierModel supplierModel = supplierService.getSupplier(supplierId);
		Response res = CommonUtils.getResponseObject("Supplier Details");
		if (supplierModel == null) {
			ErrorObject err = CommonUtils.getErrorResponse("supplier Not Found", "supplier Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(supplierModel);
		}
		logger.info("getSupplier: Sent response");
		return CommonUtils.getJson(res);
	}

	@RequestMapping(value = "/supplier/{supplierId}", method = RequestMethod.DELETE, produces = "application/json", consumes = "application/json")
	public Response deleteSupplier(@PathVariable("supplierId") String supplierId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("deleteSupplier: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("deleteSupplier: Received request: " + CommonUtils.getJson(supplierId));
		return supplierService.deleteSupplier(supplierId);
	}

}
