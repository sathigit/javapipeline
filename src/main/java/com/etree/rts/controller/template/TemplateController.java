package com.etree.rts.controller.template;

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
import com.etree.rts.model.template.TemplateModel;
import com.etree.rts.response.ErrorObject;
import com.etree.rts.response.Response;
import com.etree.rts.service.template.TemplateService;
import com.etree.rts.utils.CommonUtils;

@RestController
@RequestMapping("/v1")
public class TemplateController implements Constants {

	private static final Logger logger = LoggerFactory.getLogger(TemplateController.class);
	@Autowired
	TemplateService templateService;

	@RequestMapping(value = "/template", method = RequestMethod.POST, produces = "application/json")
	public Response addTemplate(@RequestBody TemplateModel template, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("addTemplate: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("addTemplate: Received request: " + CommonUtils.getJson(template));
		return templateService.addTemplate(template);
	}

	@RequestMapping(value = "/templates", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getTemplates(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("getOrders: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<TemplateModel> templates = templateService.getTemplates();
		Response res = CommonUtils.getResponseObject("List of Templates");
		if (templates == null) {
			ErrorObject err = CommonUtils.getErrorResponse("Templates Not Found", "Templates Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(templates);
		}
		logger.info("getTemplates: Sent response");
		return CommonUtils.getJson(res);
	}

	@RequestMapping(value = "/templatesByUser/{userId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getTemplatesByUser(@PathVariable("userId") String userId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("getOrders: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<TemplateModel> templates = templateService.getTemplatesByUser(userId);
		Response res = CommonUtils.getResponseObject("List of Templates");
		if (templates == null) {
			ErrorObject err = CommonUtils.getErrorResponse("Templates Not Found", "Templates Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(templates);
		}
		logger.info("getTemplates: Sent response");
		return CommonUtils.getJson(res);
	}

	@RequestMapping(value = "/template", method = RequestMethod.PUT, produces = "application/json")
	public Response updateTemplate(@RequestBody TemplateModel template, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("updateTemplate: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("updateTemplate: Received request: " + CommonUtils.getJson(template));
		return templateService.updateTemplate(template);
	}

	@RequestMapping(value = "/template/{templateId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getTemplate(@PathVariable("templateId") String templateId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("getTemplate: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		TemplateModel templateModel = templateService.getTemplate(templateId);
		Response res = CommonUtils.getResponseObject("Template Details");
		if (templateModel == null) {
			ErrorObject err = CommonUtils.getErrorResponse("template Not Found", "template Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(templateModel);
		}
		logger.info("getTemplate: Sent response");
		return CommonUtils.getJson(res);
	}

	@RequestMapping(value = "/template/{templateId}", method = RequestMethod.DELETE, produces = "application/json", consumes = "application/json")
	public Response deleteTemplate(@PathVariable("templateId") String templateId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("deleteTemplate: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("deleteTemplate: Received request: " + CommonUtils.getJson(templateId));
		return templateService.deleteTemplate(templateId);
	}

}
