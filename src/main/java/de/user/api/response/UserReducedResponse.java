package de.user.api.response;

import de.user.api.BasicDataObject;

/**
 * User participant in a contract API response object
 * 
 * @author hazem
 *
 */
public class UserReducedResponse extends BasicDataObject {

	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = -5337996746432371641L;

	private String userName;

	private String firstName;

	private String lastName;

	private String sex;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
