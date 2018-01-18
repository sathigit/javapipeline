package com.etree.rts.controller.slo;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.etree.rts.constant.StatusCode;
import com.etree.rts.controller.shelflabeler.ShelfLabelerController;
import com.etree.rts.model.slo.ShelfLabelerOrgModel;
import com.etree.rts.response.ErrorObject;
import com.etree.rts.response.Response;
import com.etree.rts.service.slo.ShelfLabelerOrgService;
import com.etree.rts.utils.CommonUtils;

@RestController
@RequestMapping("/v1")
public class ShelfLabelerOrgController {
	private static final Logger logger = LoggerFactory.getLogger(ShelfLabelerController.class);
	@Autowired
	ShelfLabelerOrgService shelflabelerOrgService;
	
	@RequestMapping(value = "/shelflabelerOrg", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public Response saveShelfLabelerOrg(@RequestBody ShelfLabelerOrgModel shelfLabelerOrgModel, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("addShelfLabelerOrg: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("addShelfLabelerOrg: Received request: " + CommonUtils.getJson(shelfLabelerOrgModel));
		return shelflabelerOrgService.saveShelfLabelerOrg(shelfLabelerOrgModel);
	}
	@RequestMapping(value = "/shelflabelerOrgs", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getShelfLabelerOrgs(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("getShelfLabelerOrgs: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<ShelfLabelerOrgModel> shelfLabelerOrgModels = shelflabelerOrgService.getShelfLabelerOrgs();
		Response res = CommonUtils.getResponseObject("ShelfLabelerModel Details");
		if (shelfLabelerOrgModels == null) {
			ErrorObject err = CommonUtils.getErrorResponse("ShelfLabelerOrgs Not Found",
					"ShelfLabelerOrgs Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(shelfLabelerOrgModels);
		}
		logger.info("getShelfLabelerOrgs: Sent response");
		return CommonUtils.getJson(res);
	}
}
