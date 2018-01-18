package com.etree.rts.service.user;

import java.util.List;

import com.etree.rts.model.user.UserModel;
import com.etree.rts.response.Response;

public interface UserService {

	public Response saveUser(UserModel user) throws Exception;

	public Response updateUser(UserModel user) throws Exception;

	public Response deleteUser(String userId) throws Exception;

	public UserModel getUser(String userId) throws Exception;

	public List<UserModel> getUsers() throws Exception;

	public UserModel authenticate(UserModel user) throws Exception;

	public boolean isUserNameExist(String userName) throws Exception;
	
	public List<UserModel> getUsersByOrg(String organizationId) throws Exception;

}
