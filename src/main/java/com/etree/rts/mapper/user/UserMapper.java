package com.etree.rts.mapper.user;

import org.springframework.stereotype.Component;

import com.etree.rts.domain.user.User;
import com.etree.rts.mapper.AbstractModelMapper;
import com.etree.rts.model.user.UserModel;

@Component
public class UserMapper extends AbstractModelMapper<UserModel, User> {

	@Override
	public Class<UserModel> entityType() {
		return UserModel.class;
	}

	@Override
	public Class<User> modelType() {
		return User.class;
	}

}
