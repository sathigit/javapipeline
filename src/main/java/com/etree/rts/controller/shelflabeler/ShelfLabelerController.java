package com.etree.rts.controller.shelflabeler;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.etree.rts.constant.StatusCode;
import com.etree.rts.model.shelfLabeler.ShelfLabelerModel;
import com.etree.rts.response.ErrorObject;
import com.etree.rts.response.Response;
import com.etree.rts.service.shelflabeler.ShelfLabelerService;
import com.etree.rts.utils.CommonUtils;

@RestController
@RequestMapping("/v1")
public class ShelfLabelerController {
	private static final Logger logger = LoggerFactory.getLogger(ShelfLabelerController.class);
	@Autowired
	ShelfLabelerService shelflabelerService;

	@PostMapping("/shelflabel")
	public Response saveShelfLabel(@RequestParam("file") MultipartFile file, @RequestParam("name") String name,
			@RequestParam("userId") String userId, RedirectAttributes redirectAttributes) throws Exception {
		logger.debug("saveShelfLabel: Received request: " + file);
		if (file.isEmpty()) {
			Response res = CommonUtils.getResponseObject("Upload File Error");
			ErrorObject err = CommonUtils.getErrorResponse("File Not Found", "Please select a file to upload");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
			return res;
		}
		ShelfLabelerModel shelfLabelerModel = new ShelfLabelerModel();
		shelfLabelerModel.setUserId(userId);
		shelfLabelerModel.setName(name);
		return shelflabelerService.saveShelfLabel(shelfLabelerModel, file);
	}

	@RequestMapping(value = "/shelflabels/{name}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getShelfLabelsByName(@PathVariable("name") String name, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("getShelflabels: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<ShelfLabelerModel> shelfLabelerModels = shelflabelerService.getShelfLabels(name);
		Response res = CommonUtils.getResponseObject("ShelfLabelerModel Details");
		if (shelfLabelerModels == null) {
			ErrorObject err = CommonUtils.getErrorResponse("ShelfLabelerModel Not Found",
					"ShelfLabelerModel Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(shelfLabelerModels);
		}
		logger.info("getShelflabels: Sent response");
		return CommonUtils.getJson(res);
	}

	@RequestMapping(value = "/shelflabels", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getShelfLabels(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("getShelflabels: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<ShelfLabelerModel> shelfLabelerModels = shelflabelerService.getShelfLabels();
		Response res = CommonUtils.getResponseObject("ShelfLabelerModel Details");
		if (shelfLabelerModels == null) {
			ErrorObject err = CommonUtils.getErrorResponse("ShelfLabelerModel Not Found",
					"ShelfLabelerModel Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(shelfLabelerModels);
		}
		logger.info("getShelflabels: Sent response");
		return CommonUtils.getJson(res);
	}

	@RequestMapping(value = "/shelflabel/{name}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getShelfLabel(@PathVariable("name") String name, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("getFiles: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		ShelfLabelerModel shelfLabelerModels = shelflabelerService.getShelfLabel(name);
		Response res = CommonUtils.getResponseObject("ShelfLabelerModel Details");
		if (shelfLabelerModels == null) {
			ErrorObject err = CommonUtils.getErrorResponse("ShelfLabelerModel Not Found",
					"ShelfLabelerModel Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(shelfLabelerModels);
		}
		logger.info("getShelfLabel: Sent response");
		return CommonUtils.getJson(res);
	}

	@RequestMapping(value = "/shelflabels/version", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getShelfLabelsByVersion(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("getShelflabels: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<ShelfLabelerModel> shelfLabelerModels = shelflabelerService.getShelfLabelsByVersion();
		Response res = CommonUtils.getResponseObject("ShelfLabelerModel Details");
		if (shelfLabelerModels == null) {
			ErrorObject err = CommonUtils.getErrorResponse("ShelfLabelerModel Not Found",
					"ShelfLabelerModel Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(shelfLabelerModels);
		}
		logger.info("getShelflabels: Sent response");
		return CommonUtils.getJson(res);
	}

	@RequestMapping(value = "/{labellerKey}/printTemplates", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getShelfLabelsByLabellerKey(@PathVariable("labellerKey") String labellerKey,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("getShelfLabelsByLabellerKey: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<ShelfLabelerModel> shelfLabelerModels = shelflabelerService.getShelfLabelsByLabellerKey(labellerKey);
		Response res = CommonUtils.getResponseObject("ShelfLabelerModel Details");
		if (shelfLabelerModels == null) {
			ErrorObject err = CommonUtils.getErrorResponse("ShelfLabelerModel Not Found",
					"ShelfLabelerModel Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(shelfLabelerModels);
		}
		logger.info("getShelfLabelsByLabellerKey: Sent response");
		return CommonUtils.getJson(res);
	}

	@RequestMapping(value = "/shelfLabellerName/exist/{name}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Response isShelfLabellerNameExist(@PathVariable("name") String name,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("getCampaignHistoryExist: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		boolean isNameExist = shelflabelerService.isShelfLabellerNameExist(name);
		Response res = CommonUtils.getResponseObject("Shelf Labeller Name Details");
		/*res.setIsNameExist(isNameExist);
		return res;*/
		if (isNameExist == false) {
			ErrorObject err = CommonUtils.getErrorResponse("ShelfLabelerModel Not Found",
					"ShelfLabelerModel Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setIsNameExist(isNameExist);;
		}
		logger.info("getShelfLabelsByLabellerKey: Sent response");
		 
		 return res;
		
	}
}
