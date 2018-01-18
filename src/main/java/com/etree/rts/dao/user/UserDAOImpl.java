package com.etree.rts.dao.user;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.etree.rts.constant.Constants;
import com.etree.rts.constant.StatusCode;
import com.etree.rts.domain.user.User;
import com.etree.rts.response.Response;
import com.etree.rts.utils.CommonUtils;

@Repository
public class UserDAOImpl implements UserDAO, Constants {
	private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
	@Autowired
	JdbcTemplate jdbcTemplate;

	public Response saveUser(User user) {
		Response response = CommonUtils.getResponseObject("Add user data");
		try {
			String sql = "INSERT INTO user (userId,userName,firstName,lastName,password,email,mobile,roleId,organizationId,organizationUnits,isKoronaUser,isActive,isAdmin,createdDate,modifiedDate) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { user.getUserId(), user.getUserName(), user.getFirstName(), user.getLastName(),
							user.getPassword(), user.getEmail(), user.getMobile(), user.getRoleId(),
							user.getOrganizationId(), StringUtils.join(user.getOrganizationUnits(), ','),
							user.getIsKoronaUser(), user.getIsActive(),user.getIsAdmin(), new Date(), new Date() });
			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in addUser", e);
			return CommonUtils.getDuplicateKeyMessage(response, e);
		}
		return response;
	}

	public Response updateUser(User user) {
		Response response = CommonUtils.getResponseObject("Update user data");
		try {
			String sql = "UPDATE user SET userName=?,firstName=?,lastName=?,email=?,mobile=?,roleId=?,organizationId=?,organizationUnits=?,isKoronaUser=?,isActive=?,modifiedDate=? WHERE userId=?";
			int res = jdbcTemplate.update(sql, user.getUserName(), user.getFirstName(), user.getLastName(),
					user.getEmail(), user.getMobile(), user.getRoleId(), user.getOrganizationId(),
					StringUtils.join(user.getOrganizationUnits(), ','), user.getIsKoronaUser(), user.getIsActive(),
					new Date(), user.getUserId());
			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in updateUser", e);
			return CommonUtils.getDuplicateKeyMessage(response, e);
		}
		return response;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public User getUser(String userId) {
		try {
			String sql = "SELECT * FROM user where userId=?";
			return (User) jdbcTemplate.queryForObject(sql, new Object[] { userId },
					new BeanPropertyRowMapper(User.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			logger.error("Exception in getUser", e);
			return null;
		}
	}

	public List<User> getUsers() throws Exception {
		try {
			String sql = "SELECT * FROM user";
			List<User> users = jdbcTemplate.query(sql, new Object[] {}, new BeanPropertyRowMapper<User>(User.class));
			return users;
		} catch (Exception e) {
			logger.error("Exception in getUsers", e);
		}
		return null;
	}

	@Override
	public boolean isUserNameExist(String userName) {
		try {
			String sql = "SELECT count(userName) FROM user WHERE userName=?";
			int count = jdbcTemplate.queryForObject(sql, new Object[] { userName }, Integer.class);
			boolean isExist = count > 0 ? true : false;
			return isExist;
		} catch (Exception e) {
			logger.error("Exception in isUserNameExist: ", e);
		}
		return false;
	}

	@Override
	public boolean isUserExist(String userId) {
		try {
			String sql = "SELECT count(userId) FROM user WHERE userId=?";
			int count = jdbcTemplate.queryForObject(sql, new Object[] { userId }, Integer.class);
			boolean isExist = count > 0 ? true : false;
			return isExist;
		} catch (Exception e) {
			logger.error("Exception in isUserExist: ", e);
		}
		return false;
	}

	@Override
	public Response deleteUser(String userId) throws Exception {
		Response response = CommonUtils.getResponseObject("Delete user data");
		try {
			String sql = "delete from user WHERE userId=?";
			int rows = jdbcTemplate.update(sql, userId);
			if (rows > 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in updateUser", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
		}
		return response;
	}

	@Override
	public User authenticate(User user) throws Exception {
		try {
			String sql = "SELECT * FROM user where userName=? and password=? and isActive=1";
			return (User) jdbcTemplate.queryForObject(sql, new Object[] { user.getUserName(), user.getPassword() },
					new BeanPropertyRowMapper(User.class));
		} catch (Exception e) {
			logger.error("Exception in authenticate", e);
		}
		return null;
	}

	public Response updateOrganizationUnit(String userName, String orgs) {
		Response response = CommonUtils.getResponseObject("Update user organization units");
		try {
			String sql = "UPDATE user SET organizationUnits=?,modifiedDate=? WHERE userName=?";
			int res = jdbcTemplate.update(sql, orgs, new Date(), userName);
			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in updateOrganizationUnit", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
		}
		return response;
	}

	@Override
	public List<User> getUsersByOrg(String organizationId) throws Exception {
		try {
			String sql = "SELECT * FROM user where organizationId=?";
			List<User> users = jdbcTemplate.query(sql, new Object[] { organizationId },
					new BeanPropertyRowMapper<User>(User.class));
			return users;
		} catch (Exception e) {
			logger.error("Exception in getUsers", e);
		}
		return null;
	}
	
	@Override
	public Response updateOrgUser(User user) {
		Response response = CommonUtils.getResponseObject("Update user org data");
		try {
			String sql = "UPDATE user SET userName=?,firstName=?,lastName=?,modifiedDate=? WHERE userId=?";
			int res = jdbcTemplate.update(sql, user.getUserName(), user.getFirstName(), user.getLastName(),
					 new Date(), user.getUserId());
			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in updateUser", e);
			return CommonUtils.getDuplicateKeyMessage(response, e);
		}
		return response;
	}
}