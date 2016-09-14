package de.user.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.user.model.UserAddressEntity;
import de.user.model.UserEntity;

/**
 * Control to access the Address object
 * 
 * @author hazem
 *
 */
public class UserAddressControl extends BasicCrudControl {

	/**
	 * Find current (active) address for a given user
	 * 
	 * @param user
	 * @return user´s active address
	 */
	public UserAddressEntity findCurrentUserAddress(final UserEntity user) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("user", user);
		UserAddressEntity currentAddress = super.findSingleResultByNamedQuery(
				"UserAddressEntity.findCurrentUserAddress", parameters, UserAddressEntity.class);
		return currentAddress;
	}

	/**
	 * Find all users´s addresses
	 * 
	 * @return the list of all users´s addresses
	 */
	public List<UserAddressEntity> findAllUsersAddresses() {
		return this.findAll(UserAddressEntity.class);
	}

	/**
	 * Find all addresses for a given user
	 * 
	 * @param user
	 * @return the list of all addresses for a given user
	 */
	public List<UserAddressEntity> findUserAddresses(UserEntity user) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("user", user);
		List<UserAddressEntity> userAddresses = super.findWithNamedQuery("UserAddressEntity.findUserAddresses",
				parameters, UserAddressEntity.class);
		return userAddresses;
	}

}
