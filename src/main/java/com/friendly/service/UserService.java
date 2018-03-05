package com.friendly.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.friendly.dao.UserDAO;
import com.friendly.model.User;

/**
 * 
 * @author Altrocks
 * @version 1.0
 *
 */

@Service("userService")
public class UserService {

	@Autowired
	private UserDAO userDAO;

	public User insertUserService(User user) throws Exception {
		return userDAO.insertUserService(user);
	}

	public User findUserOne(User user) throws Exception {
		return userDAO.findUserOne(user);
	}

}
