package com.etree.rts.service.user;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.etree.rts.config.RTSProperties;
import com.etree.rts.constant.Constants;
import com.etree.rts.dao.organization.OrganizationDAO;
import com.etree.rts.dao.ou.OrganizationUnitDAO;
import com.etree.rts.dao.user.UserDAO;
import com.etree.rts.domain.organization.Organization;
import com.etree.rts.domain.ou.OrganizationUnit;
import com.etree.rts.domain.user.User;
import com.etree.rts.mapper.ou.OrganizationUnitMapper;
import com.etree.rts.mapper.user.UserMapper;
import com.etree.rts.model.user.KoronaUser;
import com.etree.rts.model.user.UserModel;
import com.etree.rts.response.Response;
import com.etree.rts.utils.CommonUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("userService")
public class UserServiceImpl implements UserService, Constants {

	@Autowired
	UserDAO userDAO;

	@Autowired
	UserMapper userMapper;

	@Autowired
	OrganizationDAO organizationDAO;

	@Autowired
	RTSProperties rtsProperties;

	@Autowired
	OrganizationUnitDAO organizationUnitDAO;

	@Autowired
	OrganizationUnitMapper organizationUnitMapper;

	RestTemplate restTemplate = new RestTemplate();
	ObjectMapper mapper = new ObjectMapper();
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	public UserServiceImpl() {
		// TODO
	}

	public Response saveUser(UserModel userModel) {
		try {
			User user = new User();
			BeanUtils.copyProperties(userModel, user);
			user.setUserId(CommonUtils.getRandomUUID());
			user.setPassword(CommonUtils.encriptString(user.getPassword()));
			user.setIsActive(true);
			Response response = userDAO.saveUser(user);
			return response;
		} catch (Exception ex) {
			logger.info("Exception Service:" + ex.getMessage());
		}
		return null;
	}

	public UserModel getUser(String userId) {
		try {
			User user = userDAO.getUser(userId);
			UserModel userModel = new UserModel();
			if (user == null)
				return null;
			BeanUtils.copyProperties(user, userModel);
			return userModel;
		} catch (Exception e) {
			logger.info("Exception getUser:", e);
			return null;
		}
	}

	public Response deleteUser(String userId) {
		try {
			return userDAO.deleteUser(userId);
		} catch (Exception e) {
			logger.info("Exception deleteUser:", e);
			return null;
		}
	}

	public List<UserModel> getUsers() throws Exception {
		try {
			List<User> users = userDAO.getUsers();
			List<UserModel> userModels = userMapper.entityList(users);
			for (UserModel userModel : userModels) {
				if (userModel.getOrganizationUnits() != null && userModel.getOrganizationUnits().length > 0) {
					List<OrganizationUnit> ous = organizationUnitDAO
							.getOrganizationUnits(userModel.getOrganizationUnits());
					userModel.setOrganizationUnitList(organizationUnitMapper.entityList(ous));
				}
			}
			return userModels;
		} catch (Exception ex) {
			logger.info("Exception getUsers:", ex);
		}
		return null;
	}

	public UserModel authenticate(UserModel userModel) throws Exception {
		userModel.setPassword(CommonUtils.encriptString(userModel.getPassword()));
		User user = new User();
		BeanUtils.copyProperties(userModel, user);
		user = userDAO.authenticate(user);
		if (user == null)
			return null;
		if (user.getOrganizationId() != null && user.getOrganizationId().length() > 0) {
			Organization organization = organizationDAO.getOrganization(user.getOrganizationId(),
					userModel.getUserName());
			updateOrganizationUnit(organization, userModel.getUserName());
			user.setOrgName(organization.getName());
			user.setOrgAlias(organization.getAlias());
			user.setOrganizationId(organization.getOrganizationId());
			user.setPackageId(organization.getPackageId());
			/**
			 * Get users corresponding organizationUnits
			 */

		}
		BeanUtils.copyProperties(user, userModel);
		return userModel;
	}

	private void updateOrganizationUnit(Organization organization, String userName) {
		try {
			int offset = 0;
			int limit = rtsProperties.getKoronaLimit();
			Date beforeCall = new Date();
			while (true) {
				UriComponents uriComponents = UriComponentsBuilder.fromUriString(rtsProperties.getKoronaUsersUrl())
						.build().expand(organization.getKoronaToken(), rtsProperties.getKoronaLimit(), offset);
				String result = restTemplate.getForObject(uriComponents.toUriString(), String.class);
				JSONObject object = new JSONObject(result);
				JSONArray productsJson = (JSONArray) object.get("resultList");
				mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
				KoronaUser[] koronaUsers = mapper.readValue(productsJson.toString(), KoronaUser[].class);
				if (koronaUsers != null) {
					for (KoronaUser koronaUser : koronaUsers) {
						if (koronaUser.getEmail().equalsIgnoreCase(userName)) {
							userDAO.updateOrganizationUnit(userName, StringUtils.join(koronaUser.getOrgs(), ','));
							break;
						}
					}
					if (koronaUsers.length == limit) {
						offset += limit;
					} else {
						break;
					}
				}
			}
			Date afterCall = new Date();
			long difference = (afterCall.getTime() - beforeCall.getTime()) / 1000;
			logger.info("Total Time to add products is:" + difference + " seconds");

		} catch (Exception e) {
			logger.info("Exception isUserNameExist:", e);
		}
	}

	public boolean isUserNameExist(String userName) {
		try {
			return userDAO.isUserNameExist(userName);
		} catch (Exception e) {
			logger.info("Exception isUserNameExist:", e);
			return false;
		}
	}

	@Override
	public Response updateUser(UserModel userModel) {
		try {
			User user = new User();
			BeanUtils.copyProperties(userModel, user);
			Response response = userDAO.updateUser(user);
			return response;
		} catch (Exception ex) {
			logger.info("Exception Service:" + ex.getMessage());
		}
		return null;
	}

	public List<UserModel> getUsersByOrg(String organizationId) throws Exception {
		try {
			List<User> users = userDAO.getUsersByOrg(organizationId);
			List<UserModel> userModels = userMapper.entityList(users);
			for (UserModel userModel : userModels) {
				if (userModel.getOrganizationUnits() != null && userModel.getOrganizationUnits().length > 0) {
					List<OrganizationUnit> ous = organizationUnitDAO
							.getOrganizationUnits(userModel.getOrganizationUnits());
					userModel.setOrganizationUnitList(organizationUnitMapper.entityList(ous));
				}
			}
			return userModels;
		} catch (Exception ex) {
			logger.info("Exception getUsers:", ex);
		}
		return null;
	}
}
