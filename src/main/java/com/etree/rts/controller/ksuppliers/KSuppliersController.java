package com.etree.rts.controller.ksuppliers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.etree.rts.constant.Constants;
import com.etree.rts.constant.StatusCode;
import com.etree.rts.domain.ksuppliers.KSupplier;
import com.etree.rts.response.ErrorObject;
import com.etree.rts.response.Response;
import com.etree.rts.service.ksuppliers.KSupplierService;
import com.etree.rts.utils.CommonUtils;

@RestController
@RequestMapping("/v1")
public class KSuppliersController implements Constants {

	private static final Logger logger = LoggerFactory.getLogger(KSuppliersController.class);
	@Autowired
	KSupplierService kSupplierService;

	@RequestMapping(value = "/kSuppliers/{organizationId}", method = RequestMethod.POST, produces = "application/json")
	public Response addKSupplierss(@PathVariable(value = "organizationId", required = true) String organizationId,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("addKSuppliers: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("addKSuppliers: Received request: " + CommonUtils.getJson(""));
		return kSupplierService.addKSuppliers(organizationId);
	}
	@RequestMapping(value = "/kSuppliers/sync/{organizationId}", method = RequestMethod.POST, produces = "application/json")
	public Response syncKSuppliers(@PathVariable(value = "organizationId", required = true) String organizationId,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("syncKSuppliers: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("syncKSuppliers: Received request: " + CommonUtils.getJson(""));
		return kSupplierService.syncKSuppliers(organizationId);
	}

	@RequestMapping(value = "/kSuppliers/{organizationId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getKSuppliers(@PathVariable("organizationId") String organizationId,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("getKSuppliers: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<KSupplier> kSuppliers = kSupplierService.getKSuppliers(organizationId);
		Response res = CommonUtils.getResponseObject("List of KSuppliers");
		if (kSuppliers == null) {
			ErrorObject err = CommonUtils.getErrorResponse("KSuppliers Not Found", "KSuppliers Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(kSuppliers);
		}
		logger.info("getKSuppliers: Sent response");
		return CommonUtils.getJson(res);
	}
}
