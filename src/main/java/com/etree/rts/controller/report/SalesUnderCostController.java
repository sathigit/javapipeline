package com.etree.rts.controller.report;

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
import com.etree.rts.model.report.ReportDTO;
import com.etree.rts.model.report.SalesModel;
import com.etree.rts.response.ErrorObject;
import com.etree.rts.response.Response;
import com.etree.rts.service.report.SalesUnderCostService;
import com.etree.rts.utils.CommonUtils;

@RestController
@RequestMapping("/v1")
public class SalesUnderCostController implements Constants {

	private static final Logger logger = LoggerFactory.getLogger(SalesUnderCostController.class);
	@Autowired
	SalesUnderCostService salesUnderCostService;

	@RequestMapping(value = "/salesUnderCosts/{organizationId}", method = RequestMethod.POST, produces = "application/json")
	public Response addSalesUnderCosts(@PathVariable(value = "organizationId", required = true) String organizationId,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("addSalesUnderCost: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("addSalesUnderCost: Received request: " + CommonUtils.getJson(""));
		return salesUnderCostService.addSalesUnderCosts(organizationId);
	}

	@RequestMapping(value = "/sales/commodity/group/{organizationId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getCommodityGroupByOrganizationId(@PathVariable("organizationId") String organizationId,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("getSalesOrderCommodityGroup: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<SalesModel> salesModel = salesUnderCostService.getCommodityGroupByOrganizationId(organizationId);
		Response res = CommonUtils.getResponseObject("List of CommodityGroup");
		if (salesModel == null) {
			ErrorObject err = CommonUtils.getErrorResponse("CommodityGroup Not Found", "CommodityGroup Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(salesModel);
		}
		logger.info("getCommodityGroup: Sent response");
		return CommonUtils.getJson(res);
	}

	@RequestMapping(value = "/sales/receipts/{organizationId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getReceiptsByOrganizationId(@PathVariable("organizationId") String organizationId,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("getSalesOrderCommodityGroup: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<SalesModel> salesModel = salesUnderCostService.getReceiptsByOrganizationId(organizationId);
		Response res = CommonUtils.getResponseObject("List of CommodityGroup");
		if (salesModel == null) {
			ErrorObject err = CommonUtils.getErrorResponse("Receipt Not Found", "Receipt Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(salesModel);
		}
		logger.info("getReceipt: Sent response");
		return CommonUtils.getJson(res);
	}

	@RequestMapping(value = "/sales/receipts", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody String getReceiptsByFilter(@RequestBody ReportDTO reportDTO, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("getReceiptsByFilter: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("getReceiptsByFilter :Received request: " + CommonUtils.getJson(reportDTO));
		List<SalesModel> salesModel = salesUnderCostService.getReceiptsByFilter(reportDTO);
		Response res = CommonUtils.getResponseObject("List of CommodityGroup");
		if (salesModel == null) {
			ErrorObject err = CommonUtils.getErrorResponse("Receipt Not Found", "Receipt Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(salesModel);
		}
		logger.info("getReceipt: Sent response");
		return CommonUtils.getJson(res);
	}
}
