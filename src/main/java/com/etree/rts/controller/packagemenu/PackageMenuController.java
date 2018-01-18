package com.etree.rts.controller.packagemenu;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;





import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.etree.rts.constant.Constants;

import com.etree.rts.model.packagemenu.PackageMenuModel;
import com.etree.rts.model.packagemenu.PackageMenuTree;


import com.etree.rts.response.Response;
import com.etree.rts.service.packagemenu.PackageMenuService;
import com.etree.rts.utils.CommonUtils;

@RestController
@RequestMapping("/v1")
public class PackageMenuController implements Constants {
	

	private static final Logger logger = LoggerFactory.getLogger(PackageMenuController.class);
	
	@Autowired
	PackageMenuService packageMenuService;
	
	@PostMapping("/packagemenu")
	public Response saveOrganization(@RequestBody PackageMenuModel packageMenuModel,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return packageMenuService.saveMenus(packageMenuModel);
	}
	
	
	@GetMapping("/packagemenu/package/{packageId}")
	public PackageMenuTree getPackageMenuByPackageId(
			@PathVariable("packageId") String packageId) throws Exception {
		return packageMenuService.getPackageMenuByPackageId(packageId);
	}

	@GetMapping("/packagemenu")
	public PackageMenuTree getMenus() throws Exception {
		return packageMenuService.getMenus();
	}
	
	@RequestMapping(value = "/packagemenu/package/{packageId}", method = RequestMethod.PUT, produces = "application/json")
	public Response updatePackageMenu(@RequestBody PackageMenuModel packageMenuModel,@PathVariable("packageId") String packageId ,HttpServletRequest request,
			HttpServletResponse response) throws Exception {logger.info("updatePackageMenu: Received request URL: " + request.getRequestURL().toString()
				+ ((request.getQueryString() == null) ? "" : "?" + request.getQueryString().toString()));
		logger.info("updateSupplier: Received request: " + CommonUtils.getJson(packageMenuModel));
		return packageMenuService.updatePackageMenu(packageMenuModel,packageId);
	}



}
