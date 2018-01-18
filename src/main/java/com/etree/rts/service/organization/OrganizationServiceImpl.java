package com.etree.rts.service.organization;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etree.rts.constant.Constants;
import com.etree.rts.constant.StatusCode;
import com.etree.rts.dao.organization.OrganizationDAO;
import com.etree.rts.dao.packages.PackageDAO;
import com.etree.rts.dao.role.RoleDAO;
import com.etree.rts.dao.user.UserDAO;
import com.etree.rts.domain.organization.Organization;
import com.etree.rts.domain.packages.Packages;
import com.etree.rts.domain.role.Role;
import com.etree.rts.domain.user.User;
import com.etree.rts.mapper.organization.OrganizationMapper;
import com.etree.rts.model.organization.OrganizationModel;
import com.etree.rts.response.Response;
import com.etree.rts.utils.CommonUtils;

@Service("organizationService")
public class OrganizationServiceImpl implements OrganizationService, Constants {

	private static final Logger logger = LoggerFactory.getLogger(OrganizationServiceImpl.class);
	@Autowired
	OrganizationDAO organizationDAO;

	@Autowired
	OrganizationMapper organizationMapper;

	@Autowired
	UserDAO userDAO;

	@Autowired
	RoleDAO roleDAO;

	@Autowired
	PackageDAO packageDAO;

	@Override
	public Response saveOrganization(OrganizationModel oModel) throws Exception {
		try {
			Organization organization = new Organization();
			BeanUtils.copyProperties(oModel, organization);
			organization.setOrganizationId(CommonUtils.getRandomUUID());
			organization.setIsActive(true);
			Response response = organizationDAO.saveOrganization(organization);
			if (response.getStatus().equalsIgnoreCase(StatusCode.SUCCESS.name())) {

				Role role = new Role();
				role.setIsActive(true);
				role.setAlias("Admin");
				role.setName("Admin");
				role.setOrganizationId(organization.getOrganizationId());
				role.setRoleId(CommonUtils.getRandomUUID());
				/**
				 * Get Package and add to default role
				 */
				Packages packages = packageDAO.getPackage(organization.getPackageId());
				role.setMenu(packages.getMenu());
				role.setPackageId(organization.getPackageId());
				response = roleDAO.saveAdminRole(role);
				if (response.getStatus().equalsIgnoreCase(StatusCode.SUCCESS.name())) {
					User user = new User();
					user.setOrganizationId(organization.getOrganizationId());
					user.setOrganizationUnits(null);// set actual values
					user.setUserId(CommonUtils.getRandomUUID());
					user.setUserName(oModel.getUserName());
					user.setFirstName(oModel.getFirstName());
					user.setLastName(oModel.getLastName());
					user.setPassword(CommonUtils.encriptString(oModel.getPassword()));
					user.setIsActive(true);
					user.setIsAdmin(true);// Set here for default admin
					user.setRoleId(role.getRoleId());
					response = userDAO.saveUser(user);
					if (response.getStatus().equalsIgnoreCase(StatusCode.ERROR.name())) {
						organizationDAO.deleteOrganization(organization.getOrganizationId());
						roleDAO.deleteRole(role.getRoleId());
					}
				}else{
					roleDAO.deleteRole(role.getRoleId());
				}
			}
			return response;
		} catch (Exception ex) {
			logger.info("Exception in saveOrganization:" + ex.getMessage());
		}
		return null;
	}

	@Override
	public Response updateOrganization(OrganizationModel oModel) throws Exception {
		try {
			Organization organization = new Organization();
			BeanUtils.copyProperties(oModel, organization);
			Response response = organizationDAO.updateOrganization(organization);

			User user = new User();
			user.setUserId(oModel.getUserId());
			user.setUserName(oModel.getUserName());
			user.setFirstName(oModel.getFirstName());
			user.setLastName(oModel.getLastName());
			userDAO.updateOrgUser(user);
			return response;
		} catch (Exception ex) {
			logger.info("Exception in updateOrganization:" + ex.getMessage());
		}
		return null;
	}

	@Override
	public OrganizationModel getOrganization(String organizationId) throws Exception {
		try {
			Organization organization = organizationDAO.getOrganization(organizationId);
			OrganizationModel organizationModel = new OrganizationModel();
			if (organization == null)
				return null;
			BeanUtils.copyProperties(organization, organizationModel);
			return organizationModel;
		} catch (Exception e) {
			logger.info("Exception in getOrganization:", e);
			return null;
		}

	}

	@Override
	public List<OrganizationModel> getOrganizations() {
		try {
			List<Organization> organizations = organizationDAO.getOrganizations();
			return organizationMapper.entityList(organizations);
		} catch (Exception ex) {
			logger.info("Exception getTemplates:", ex);
		}
		return null;
	}

	@Override
	public Response updateOrganizationStatus(OrganizationModel organizationModel) {
		try {
			Organization organization = new Organization();
			BeanUtils.copyProperties(organizationModel, organization);
			Response response = organizationDAO.updateOrganizationStatus(organization);
			return response;
		} catch (Exception ex) {
			logger.info("Exception in updateOrganization:" + ex.getMessage());
		}
		return null;
	}
}
