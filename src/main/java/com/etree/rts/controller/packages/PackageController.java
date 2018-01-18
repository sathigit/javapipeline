package com.etree.rts.controller.packages;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.etree.rts.constant.Constants;
import com.etree.rts.constant.StatusCode;
import com.etree.rts.model.packages.PackagesModel;
import com.etree.rts.response.ErrorObject;
import com.etree.rts.response.Response;
import com.etree.rts.service.packages.PackageService;
import com.etree.rts.utils.CommonUtils;

@RestController
@RequestMapping("/v1")
public class PackageController implements Constants {

	private static final Logger logger = LoggerFactory.getLogger(PackageController.class);
	@Autowired
	PackageService packageService;

	@PostMapping("/package")
	public Response savePackage(@RequestBody PackagesModel packagesModel, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("savePackage: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("savePackage: Received request: " + CommonUtils.getJson(packagesModel));
		return packageService.savePackage(packagesModel);
	}
	@GetMapping("/packages")
	public @ResponseBody String getPackages(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("getPackages: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		List<PackagesModel> packages = packageService.getPackages();
		Response res = CommonUtils.getResponseObject("List of packages");
		if (packages == null) {
			ErrorObject err = CommonUtils.getErrorResponse("Packages Not Found", "Packages Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(packages);
		}
		logger.info("getPackages: Sent response");
		return CommonUtils.getJson(res);
	}

	@GetMapping("/package/{packageId}")
	public @ResponseBody String getPackage(@PathVariable("packageId") String packageId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("getPackage: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		PackagesModel packagesModel = packageService.getPackage(packageId,null);
		Response res = CommonUtils.getResponseObject("Package Details");
		if (packagesModel == null) {
			ErrorObject err = CommonUtils.getErrorResponse("package Not Found", "package Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(packagesModel);
		}
		logger.info("getPackage: Sent response");
		return CommonUtils.getJson(res);
	}
	@GetMapping("/package/{packageId}/{userId}")
	public @ResponseBody String getPackageByUser(@PathVariable("packageId") String packageId,@PathVariable("userId") String userId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("getPackage: Received request: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		PackagesModel packagesModel = packageService.getPackage(packageId,userId);
		Response res = CommonUtils.getResponseObject("Package Details");
		if (packagesModel == null) {
			ErrorObject err = CommonUtils.getErrorResponse("package Not Found", "package Not Found");
			res.setErrors(err);
			res.setStatus(StatusCode.ERROR.name());
		} else {
			res.setData(packagesModel);
		}
		logger.info("getPackage: Sent response");
		return CommonUtils.getJson(res);
	}

	@PutMapping("/package")
	public Response updatePackage(@RequestBody PackagesModel packagesModel, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("updatePackage: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("updatePackage: Received request: " + CommonUtils.getJson(packagesModel));
		return packageService.updatePackage(packagesModel);
	}

	@DeleteMapping("/package/{packageId}")
	public Response deletePackage(@PathVariable("packageId") String packageId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("deletePackage: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("deletePackage: Received request: " + CommonUtils.getJson(packageId));
		return packageService.deletePackage(packageId);
	}

}
