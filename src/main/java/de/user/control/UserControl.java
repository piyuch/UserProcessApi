package de.user.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;

import de.user.exception.user.UserAlreadyExistException;
import de.user.model.UserEntity;

/**
 * Control to access the User object
 * 
 * @author piyush chand
 *
 */
public class UserControl extends BasicCrudControl {

	/**
	 * Find user by his Id
	 * 
	 * @param userId
	 * @return the user with that ID
	 */
	public UserEntity findUserById(final long userId) {
		UserEntity user = this.find(UserEntity.class, userId);
		if (user == null) {
			return null;
		}
		return user;
	}

	/**
	 * Find user by his email
	 * 
	 * @param email
	 * @return the user with that email
	 */
	public UserEntity findUserByEmail(final String email) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("email", email);
		List<UserEntity> users = super.findWithNamedQuery("UserEntity.findByEmail", parameters, UserEntity.class);
		if (!users.isEmpty()) {
			UserEntity user = users.get(0);
			return user;
		} else {
			return null;
		}
	}

	/**
	 * Find user by system generated username
	 * 
	 * @param userName
	 * @return the user
	 */
	public UserEntity findUserByUserName(final String userName) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userName", userName);
		List<UserEntity> users = super.findWithNamedQuery("UserEntity.findByUserName", parameters, UserEntity.class);
		if (!users.isEmpty()) {
			UserEntity user = users.get(0);
			return user;
		} else {
			return null;
		}
	}

	/**
	 * Create an user
	 * 
	 * @param user
	 * @return the created user
	 */
	public UserEntity createUser(final UserEntity user) {
		try {
			return super.create(user);
		} catch (PersistenceException ex) {
			throw new UserAlreadyExistException(ex.getLocalizedMessage());
		}
	}

	/**
	 * Update an user
	 * 
	 * @param user
	 * @return the updated user
	 */
	public UserEntity updateUser(final UserEntity user) {
		return super.update(user);
	}

	/**
	 * Find all users
	 * 
	 * @return the list of all users
	 */
	public List<UserEntity> findAllUsers() {
		return this.findAll(UserEntity.class);
	}

}
