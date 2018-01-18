package com.etree.rts.controller.organization;

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

import com.etree.rts.model.organization.OrganizationModel;

import com.etree.rts.response.ErrorObject;
import com.etree.rts.response.Response;
import com.etree.rts.service.organization.OrganizationService;
import com.etree.rts.utils.CommonUtils;

@RestController
@RequestMapping("/v1")

public class OrganizationController implements Constants {
	private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);

	@Autowired
	OrganizationService organizationService;

	@RequestMapping(value = "/organization", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Response saveOrganization(@RequestBody OrganizationModel organization, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("organizationConfigure: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("organizationConfigure: Received request: " + CommonUtils.getJson(organization));
		return organizationService.saveOrganization(organization);
	}

	@RequestMapping(value = "/organization", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public Response updateOrganization(@RequestBody OrganizationModel organization, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("organizationConfigure: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("organizationConfigure: Received request: " + CommonUtils.getJson(organization));
		return organizationService.updateOrganization(organization);
	}

	@RequestMapping(value = "/organizationStatus", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
	public Response updateOrganizationStatus(@RequestBody OrganizationModel organization, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("organizationConfigure: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("organizationConfigure: Received request: " + CommonUtils.getJson(organization));
		return organizationService.updateOrganizationStatus(organization);
	}
	
	@RequestMapping(value = "/organizations", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getOrganizations(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("getOrganization: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<OrganizationModel> organizationModels = organizationService.getOrganizations();
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

	@RequestMapping(value = "/organization/{organizationId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getOrganization(@PathVariable("organizationId") String organizationId,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("getOrganization: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		OrganizationModel organizationModel = organizationService.getOrganization(organizationId);
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
