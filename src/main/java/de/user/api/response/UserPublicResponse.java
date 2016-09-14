package de.user.api.response;

import de.user.api.BasicDataObject;

public class UserPublicResponse extends BasicDataObject {

	/**
	 * generated id
	 */
	private static final long serialVersionUID = 6427653957044066264L;

	private String firstName;

	private String sex;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

}
