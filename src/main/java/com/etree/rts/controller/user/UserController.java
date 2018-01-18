package com.etree.rts.controller.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.etree.rts.dao.user.UserDAO;
import com.etree.rts.model.user.UserModel;
import com.etree.rts.response.ErrorObject;
import com.etree.rts.response.Response;
import com.etree.rts.service.user.UserService;
import com.etree.rts.utils.CommonUtils;

@RestController
@RequestMapping("/v1")
public class UserController implements Constants {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	UserService userService;
	@Autowired
	UserDAO userDAO;

	@RequestMapping(value = "/user", method = RequestMethod.POST, produces = "application/json")
	public Response saveUser(@RequestBody UserModel user, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("addUser: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("addUser: Received request: " + CommonUtils.getJson(user));
		return userService.saveUser(user);
	}

	@RequestMapping(value = "/user", method = RequestMethod.PUT, produces = "application/json")
	public Response updateUser(@RequestBody UserModel user, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("updateUser: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("updateUser: Received request: " + CommonUtils.getJson(user));
		return userService.updateUser(user);
	}

	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getUser(@PathVariable("userId") String userId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("getUser: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		UserModel userModel = userService.getUser(userId);
		Response res = CommonUtils.getResponseObject("User Details");
		if (userModel == null) {
			ErrorObject err = CommonUtils.getErrorResponse("Users Not Found", "Users Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(userModel);
		}
		logger.info("getUser: Sent response");
		return CommonUtils.getJson(res);
	}

	@RequestMapping(value = "/user/{userId}", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody Response deleteUser(@PathVariable("userId") String userId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("getUser: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		return userService.deleteUser(userId);
	}

	@RequestMapping(value = "/userExist/{userName}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String isUserNameExist(@PathVariable("userName") String userName, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("getUser: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		boolean isUserNameExist = userService.isUserNameExist(userName);
		Response res = CommonUtils.getResponseObject("User Exist");
		Map<String, Boolean> obj = new HashMap<String, Boolean>();
		obj.put("isUserNameExist", isUserNameExist);
		res.setData(obj);
		if (!isUserNameExist) {
			res.setStatus(StatusCode.ERROR.name());
		}
		logger.info("getUser: Sent response");
		return CommonUtils.getJson(res);
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getUsers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("getUsers: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<UserModel> users = userService.getUsers();
		Response res = CommonUtils.getResponseObject("List of Users");
		if (users == null) {
			ErrorObject err = CommonUtils.getErrorResponse("Users Not Found", "Users Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(users);
		}
		logger.info("getUsers: Sent response");
		return CommonUtils.getJson(res);
	}
	
	@RequestMapping(value = "/users/{organizationId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getUsersByOrg(@PathVariable("organizationId") String organizationId,HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("getUsersByOrg: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<UserModel> users = userService.getUsersByOrg(organizationId);
		Response res = CommonUtils.getResponseObject("List of Users");
		if (users == null) {
			ErrorObject err = CommonUtils.getErrorResponse("Users Not Found", "Users Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(users);
		}
		logger.info("getUsersByOrg: Sent response");
		return CommonUtils.getJson(res);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String authenticate(@RequestBody UserModel user, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("authenticate: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("authenticate :Received request: " + CommonUtils.getJson(user));
		user = userService.authenticate(user);
		Response res = CommonUtils.getResponseObject("authenticate user");
		if (user == null) {
			ErrorObject err = CommonUtils.getErrorResponse("Invalid Username or Password",
					"Invalid Username or Password");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(user);
		}
		logger.info("authenticate: Sent response");
		return CommonUtils.getJson(res);
	}
}
