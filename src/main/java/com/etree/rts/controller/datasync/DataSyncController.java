package com.etree.rts.controller.datasync;

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
import com.etree.rts.model.datasync.DataSyncModel;
import com.etree.rts.response.ErrorObject;
import com.etree.rts.response.Response;
import com.etree.rts.service.datasync.DataSyncService;
import com.etree.rts.utils.CommonUtils;

@RestController
@RequestMapping("/v1")
public class DataSyncController implements Constants {
	private static final Logger logger = LoggerFactory.getLogger(DataSyncController.class);
	@Autowired
	DataSyncService dataSyncService;

	@RequestMapping(value = "/syncData", method = RequestMethod.POST, produces = "application/json")
	public Response saveData(@RequestBody DataSyncModel data, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("saveData: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("saveData: Received request: " + CommonUtils.getJson(data));
		return dataSyncService.saveData(data);
	}

	@RequestMapping(value = "/syncData", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("getData: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<DataSyncModel> data = dataSyncService.getData();
		Response res = CommonUtils.getResponseObject("List of DatsSync");
		if (data == null) {
			ErrorObject err = CommonUtils.getErrorResponse("DataSync Not Found", "DataSync Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(data);
		}
		logger.info("getData: Sent response");
		return CommonUtils.getJson(res);
	}

	@RequestMapping(value = "/syncData/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getDataById(@PathVariable("id") String id, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("getData: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<DataSyncModel> data = dataSyncService.getDataById(id);
		Response res = CommonUtils.getResponseObject("List of data");
		if (data == null) {
			ErrorObject err = CommonUtils.getErrorResponse("Data Not Found", "Data Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(data);
		}
		logger.info("getData: Sent response");
		return CommonUtils.getJson(res);
	}

	@RequestMapping(value = "/syncData/user/{userId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getDataByUser(@PathVariable("userId") String userId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("getData: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<DataSyncModel> data = dataSyncService.getDataByUser(userId);
		Response res = CommonUtils.getResponseObject("List of data");
		if (data == null) {
			ErrorObject err = CommonUtils.getErrorResponse("Data Not Found", "Data Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(data);
		}
		logger.info("getData: Sent response");
		return CommonUtils.getJson(res);
	}

	@RequestMapping(value = "/syncData", method = RequestMethod.PUT, produces = "application/json")
	public Response updateData(@RequestBody DataSyncModel data, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("updateData: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("updateData: Received request: " + CommonUtils.getJson(data));
		return dataSyncService.updateData(data);
	}

	@RequestMapping(value = "/deleteSync/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public Response deleteData(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("updateData: Received request URL: " + request.getRequestURI().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("updateData: Received request: " + CommonUtils.getJson(id));
		return dataSyncService.deleteData(id);
	}
}
