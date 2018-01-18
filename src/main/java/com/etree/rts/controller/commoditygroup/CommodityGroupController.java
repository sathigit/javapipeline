package com.etree.rts.controller.commoditygroup;

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
import com.etree.rts.domain.commoditygroup.CommodityGroup;
import com.etree.rts.response.ErrorObject;
import com.etree.rts.response.Response;
import com.etree.rts.service.commoditygroup.CommodityGroupService;
import com.etree.rts.utils.CommonUtils;

@RestController
@RequestMapping("/v1")
public class CommodityGroupController implements Constants {

	private static final Logger logger = LoggerFactory.getLogger(CommodityGroupController.class);
	@Autowired
	CommodityGroupService commodityGroupService;

	@RequestMapping(value = "/commodityGroups/{organizationId}", method = RequestMethod.POST, produces = "application/json")
	public Response addCommodityGroupss(@PathVariable(value = "organizationId", required = true) String organizationId,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("addCommodityGroups: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("addCommodityGroups: Received request: " + CommonUtils.getJson(""));
		return commodityGroupService.addCommodityGroups(organizationId);
	}
	@RequestMapping(value = "/commodityGroups/sync/{organizationId}", method = RequestMethod.POST, produces = "application/json")
	public Response syncCommodityGroups(@PathVariable(value = "organizationId", required = true) String organizationId,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("syncCommodityGroups: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("syncCommodityGroups: Received request: " + CommonUtils.getJson(""));
		return commodityGroupService.syncCommodityGroups(organizationId);
	}

	@RequestMapping(value = "/commodityGroups/{organizationId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getCommodityGroups(@PathVariable("organizationId") String organizationId,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("getCommodityGroups: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<CommodityGroup> commodityGroups = commodityGroupService.getCommodityGroups(organizationId);
		Response res = CommonUtils.getResponseObject("List of CommodityGroups");
		if (commodityGroups == null) {
			ErrorObject err = CommonUtils.getErrorResponse("CommodityGroups Not Found", "CommodityGroups Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(commodityGroups);
		}
		logger.info("getCommodityGroups: Sent response");
		return CommonUtils.getJson(res);
	}
}
