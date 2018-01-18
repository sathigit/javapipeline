package com.etree.rts.controller.templateconfig;

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
import com.etree.rts.model.templateconfig.TemplateFileConfigModel;
import com.etree.rts.response.ErrorObject;
import com.etree.rts.response.Response;
import com.etree.rts.service.templateconfig.TemplateFileConfigService;
import com.etree.rts.utils.CommonUtils;

@RestController
@RequestMapping("/v1")
public class TemplateFileConfigController implements Constants {

	private static final Logger logger = LoggerFactory.getLogger(TemplateFileConfigController.class);
	@Autowired
	TemplateFileConfigService templateSupplierService;

	@RequestMapping(value = "/templateFileConfig", method = RequestMethod.POST, produces = "application/json")
	public Response addTemplateFileConfig(@RequestBody TemplateFileConfigModel templateFileConfigModel,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("addTemplateFileConfig: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("addTemplateFileConfig: Received request: " + CommonUtils.getJson(templateFileConfigModel));
		return templateSupplierService.addTemplateFileConfig(templateFileConfigModel);
	}

	@RequestMapping(value = "/templateFileConfig/{userId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getTemplateFileConfigsByUser(@PathVariable("userId") String userId,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("getTemplateFileConfigsByUser: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<TemplateFileConfigModel> templateSupplierModels = templateSupplierService.getTemplateFileConfigs(userId);
		Response res = CommonUtils.getResponseObject("List of TemplateFileConfigsByUser");
		if (templateSupplierModels == null) {
			ErrorObject err = CommonUtils.getErrorResponse("TemplateFileConfigsByUser Not Found",
					"TemplateFileConfigsByUser Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(templateSupplierModels);
		}
		logger.info("getTemplateFileConfigsByUser: Sent response");
		return CommonUtils.getJson(res);
	}

	@RequestMapping(value = "/templateFileConfigs/{templateId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getTemplateFileConfigsByTemplateId(@PathVariable("templateId") String templateId,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("getTemplateFileConfigsByTempalteId: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<TemplateFileConfigModel> templateSupplierModels = templateSupplierService
				.getTemplateFileConfigsByTemplateId(templateId);
		Response res = CommonUtils.getResponseObject("List of TemplateFileConfigsByTemplateId");
		if (templateSupplierModels == null) {
			ErrorObject err = CommonUtils.getErrorResponse("TemplateFileConfigsByTemplateId Not Found",
					"TemplateFileConfigsByTemplateId Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(templateSupplierModels);
		}
		logger.info("getTemplateFileConfigsByTemplateId: Sent response");
		return CommonUtils.getJson(res);
	}

	@RequestMapping(value = "/templateFileConfig", method = RequestMethod.PUT, produces = "application/json")
	public Response updateTemplateFileConfig(@RequestBody TemplateFileConfigModel templateFileConfigModel,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("updateTemplateFileConfig: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("updateTemplateFileConfig: Received request: " + CommonUtils.getJson(templateFileConfigModel));
		return templateSupplierService.updateTemplateFileConfig(templateFileConfigModel);
	}

	@RequestMapping(value = "/templateFileConfigBySupplier/{supplierId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getTemplateFileConfigBySupplier(@PathVariable("supplierId") String supplierId,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("getTemplateFileConfigBySupplier: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<TemplateFileConfigModel> templateSupplierModels = templateSupplierService
				.getTemplateFileConfigBySupplier(supplierId);
		Response res = CommonUtils.getResponseObject("List of TemplateFileConfigBySupplier");
		if (templateSupplierModels == null) {
			ErrorObject err = CommonUtils.getErrorResponse("TemplateFileConfigBySupplier Not Found",
					"TemplateFileConfigBySupplier Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(templateSupplierModels);
		}
		logger.info("getTemplateFileConfigBySupplier: Sent response");
		return CommonUtils.getJson(res);
	}
}
