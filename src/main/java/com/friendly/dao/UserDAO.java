/**
 * 
 */
package com.friendly.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.friendly.model.User;

/**
 * @author Altrocks
 * @version 1.0
 *
 */

@Service("userDAO")
public class UserDAO {

	@Autowired
	private UserRepository userRepo;

	public User insertUserService(User user) throws Exception {
		return userRepo.save(user);
	}

	public User findUserOne(User user) throws Exception {
		return userRepo.findOne(user.getEmail());
	}

}
