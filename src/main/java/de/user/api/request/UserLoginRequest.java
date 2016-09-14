package de.user.api.request;

import de.user.api.BasicDataObject;
import de.user.common.Validator;

/**
 * UserLogin API request object
 * 
 * @author hazem
 *
 */
public class UserLoginRequest extends BasicDataObject {

	/**
	 * Generated serialVersionUID .
	 */
	private static final long serialVersionUID = 7450036591598001126L;

	private String password;
	private String email;
	private String attempted;

	public UserLoginRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserLoginRequest(String password, String email) {
		super();

		this.password = password;
		this.email = email;

	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAttempted() {
		return attempted;
	}

	public void setAttempted(String attempted) {
		this.attempted = attempted;
	}

	/**
	 * Check if the UserLoginRequest has missing fields
	 * 
	 * @return true if all fields have non null values
	 */
	public boolean isValid() {
		if (Validator.isEmptyOrNull(this.getEmail()) || Validator.isEmptyOrNull(this.getPassword())) {
			return false;
		}
		return true;

	}
}
