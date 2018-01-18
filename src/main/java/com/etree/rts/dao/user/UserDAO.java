package com.etree.rts.dao.user;

import java.util.List;

import com.etree.rts.domain.user.User;
import com.etree.rts.response.Response;

public interface UserDAO {

	public Response saveUser(User user) throws Exception;

	public Response updateUser(User user) throws Exception;

	public Response deleteUser(String userId) throws Exception;

	public User getUser(String userId) throws Exception;

	public List<User> getUsers() throws Exception;

	public User authenticate(User user) throws Exception;

	public boolean isUserNameExist(String userName) throws Exception;

	public boolean isUserExist(String userId) throws Exception;

	public Response updateOrganizationUnit(String userName, String orgs) throws Exception;

	public List<User> getUsersByOrg(String organizationId) throws Exception;

	public Response updateOrgUser(User user) throws Exception;
}