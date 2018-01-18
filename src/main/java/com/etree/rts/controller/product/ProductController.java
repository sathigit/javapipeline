package com.etree.rts.controller.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.etree.rts.constant.Constants;
import com.etree.rts.response.Response;
import com.etree.rts.service.product.ProductService;
import com.etree.rts.utils.CommonUtils;

@RestController
@RequestMapping("/v1")
public class ProductController implements Constants {

	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	@Autowired
	ProductService productService;

	@RequestMapping(value = "/products/{organizationId}", method = RequestMethod.POST, produces = "application/json")
	public Response addProducts(@PathVariable(value = "organizationId", required = true) String organizationId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("addProduct: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("addProduct: Received request: " + CommonUtils.getJson(""));
		return productService.addProducts(organizationId);
	}

	@RequestMapping(value = "/products/sync/{organizationId}", method = RequestMethod.POST, produces = "application/json")
	public Response syncProducts(@PathVariable(value = "organizationId", required = true) String organizationId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("syncProducts: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("syncProducts: Received request: " + CommonUtils.getJson(""));
		return productService.syncProducts(organizationId);
	}

}
