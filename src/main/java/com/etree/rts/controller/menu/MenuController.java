package com.etree.rts.controller.menu;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etree.rts.constant.Constants;
import com.etree.rts.model.menu.MenuModel;
import com.etree.rts.model.menu.MenuTree;
import com.etree.rts.response.Response;
import com.etree.rts.service.menu.MenuService;

@RestController
@RequestMapping("/v1")
public class MenuController implements Constants {

	@Autowired
	MenuService menuService;

	@GetMapping("/menus")
	public MenuTree getMenus() throws Exception {
		return menuService.getMenus();
	}

	@GetMapping("/menus/role/{roleId}")
	public MenuTree getMenuByRole(@PathVariable("roleId") String roleId) throws Exception {
		return menuService.getMenuByRole(roleId,null,null);
	}
	@GetMapping("/menus/{packageId}/{roleId}/{userId}")
	public MenuTree getMenuByPackageRole(@PathVariable("packageId") String packageId,@PathVariable("roleId") String roleId,@PathVariable("userId") String userId) throws Exception {
		return menuService.getMenuByRole(packageId, roleId, userId);
	}
	@PostMapping("/menus")
	public Response saveMenu(@RequestBody MenuModel menuModel, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return menuService.saveMenus(menuModel);
	}
}
