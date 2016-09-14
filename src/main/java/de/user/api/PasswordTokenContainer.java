package de.user.api;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PasswordTokenContainer {

	private String token;
	private char[] password;

	public String getToken() {
		return token;
	}

	public void setToken(final String token) {
		this.token = token;
	}

	public char[] getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password.toCharArray();
	}
}
