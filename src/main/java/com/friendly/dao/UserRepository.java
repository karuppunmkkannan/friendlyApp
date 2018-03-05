/**
 * 
 */
package com.friendly.dao;

import org.springframework.data.repository.CrudRepository;

import com.friendly.model.User;

/**
 * @author karuppusamy
 * @version 1.0
 *
 */

public interface UserRepository extends CrudRepository<User, String> {

}
