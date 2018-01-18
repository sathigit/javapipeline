package com.etree.rts.controller.role;

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
import com.etree.rts.dao.role.RoleDAO;
import com.etree.rts.model.role.RoleModel;
import com.etree.rts.response.ErrorObject;
import com.etree.rts.response.Response;
import com.etree.rts.service.role.RoleService;
import com.etree.rts.utils.CommonUtils;

@RestController
@RequestMapping("/v1")
public class RoleController implements Constants {

	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
	@Autowired
	RoleService roleService;

	@Autowired
	RoleDAO roleDAO;

	@RequestMapping(value = "/role", method = RequestMethod.POST, produces = "application/json")
	public Response saveRole(@RequestBody RoleModel roleModel, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("addRole: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("addRole: Received request: " + CommonUtils.getJson(roleModel));
		return roleService.saveRole(roleModel);
	}

	@RequestMapping(value = "/roles", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getRoles(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("getOrders: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<RoleModel> roles = roleService.getRoles();
		Response res = CommonUtils.getResponseObject("List of roles");
		if (roles == null) {
			ErrorObject err = CommonUtils.getErrorResponse("roles Not Found", "roles Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(roles);
		}
		logger.info("getRoles: Sent response");
		return CommonUtils.getJson(res);
	}

	@RequestMapping(value = "/role/{roleId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getrole(@PathVariable("roleId") String roleId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("getRole: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		RoleModel roleModel = roleService.getRole(roleId);
		Response res = CommonUtils.getResponseObject("Supplier Details");
		if (roleModel == null) {
			ErrorObject err = CommonUtils.getErrorResponse("role Not Found", "role Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(roleModel);
		}
		logger.info("getRole: Sent response");
		return CommonUtils.getJson(res);
	}

	@RequestMapping(value = "/roles/org/{organizationId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getRolesByOrg(@PathVariable("organizationId") String organizationId,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("getOrders: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<RoleModel> roles = roleService.getRolesByOrg(organizationId);
		Response res = CommonUtils.getResponseObject("List of roles By Org");
		if (roles == null) {
			ErrorObject err = CommonUtils.getErrorResponse("roles Not Found", "roles Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(roles);
		}
		logger.info("getRoles: Sent response");
		return CommonUtils.getJson(res);
	}

	@RequestMapping(value = "/role", method = RequestMethod.PUT, produces = "application/json")
	public Response updateRole(@RequestBody RoleModel roleModel, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("updateRole: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("updateSupplier: Received request: " + CommonUtils.getJson(roleModel));
		return roleService.updateRole(roleModel);
	}

	@RequestMapping(value = "/role/{roleId}", method = RequestMethod.DELETE, produces = "application/json", consumes = "application/json")
	public Response deleteRole(@PathVariable("roleId") String roleId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("deleteRole: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("deleteRole: Received request: " + CommonUtils.getJson(roleId));
		return roleService.deleteRole(roleId);
	}

}
