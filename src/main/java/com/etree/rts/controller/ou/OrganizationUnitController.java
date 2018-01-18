package com.etree.rts.controller.ou;

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
import com.etree.rts.model.ou.OrganizationUnitModel;
import com.etree.rts.response.ErrorObject;
import com.etree.rts.response.Response;
import com.etree.rts.service.ou.OrganizationUnitService;
import com.etree.rts.utils.CommonUtils;

@RestController
@RequestMapping("/v1")

public class OrganizationUnitController implements Constants {
	private static final Logger logger = LoggerFactory.getLogger(OrganizationUnitController.class);

	@Autowired
	OrganizationUnitService organizationUnitService;

	@RequestMapping(value = "/organizationUnits/{organizationId}", method = RequestMethod.POST, produces = "application/json")
	public Response addOrganizationUnits(@PathVariable(value = "organizationId", required = true) String organizationId,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("addOrganizationUnits: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("addOrganizationUnits: Received request: " + CommonUtils.getJson(""));
		return organizationUnitService.addOrganizationUnits(organizationId);
	}

	@RequestMapping(value = "/organizationUnit", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Response saveOrganization(@RequestBody OrganizationUnitModel organization, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("organizationConfigure: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("organizationConfigure: Received request: " + CommonUtils.getJson(organization));
		return organizationUnitService.saveOrganizationUnit(organization);
	}

	@RequestMapping(value = "/organizationUnit", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public Response updateOrganization(@RequestBody OrganizationUnitModel organization, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("organizationConfigure: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("organizationConfigure: Received request: " + CommonUtils.getJson(organization));
		return organizationUnitService.updateOrganizationUnit(organization);
	}

	@RequestMapping(value = "/organizationUnits", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getOrganizations(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("getOrganization: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<OrganizationUnitModel> organizationModels = organizationUnitService.getOrganizationUnits();
		Response res = CommonUtils.getResponseObject("Organization Details");
		if (organizationModels == null) {
			ErrorObject err = CommonUtils.getErrorResponse("Organization Not Found", "Organization Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(organizationModels);
		}
		logger.info("getUser: Sent response");
		return CommonUtils.getJson(res);
	}

	@RequestMapping(value = "/organizationUnit/{uuid}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getOrganization(@PathVariable("uuid") String uuid, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("getOrganization: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		OrganizationUnitModel organizationModel = organizationUnitService.getOrganizationUnit(uuid);
		Response res = CommonUtils.getResponseObject("Organization Details");
		if (organizationModel == null) {
			ErrorObject err = CommonUtils.getErrorResponse("Organization Not Found", "Organization Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(organizationModel);
		}
		logger.info("getUser: Sent response");
		return CommonUtils.getJson(res);
	}

}
