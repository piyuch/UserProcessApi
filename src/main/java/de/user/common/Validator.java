package de.user.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.UrlValidator;

/**
 * General validators
 * 
 * @author hazem
 *
 */
public class Validator {

	private static final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$";
	private static final String PIN_PATTERN = "^[0-9]{4}$";

	/**
	 * Check whether the email address is valid or not
	 * 
	 * @param emailAddress
	 * @return true if valid
	 */
	public static boolean isEmailAddressValid(final String emailAddress) {
		EmailValidator emailValidator = EmailValidator.getInstance();
		return emailValidator.isValid(emailAddress);
	}

	/**
	 * Check whether the password is valid or not (valid example: "Test1234")
	 * 
	 * @param password
	 * @return true if valid
	 */
	public static boolean isPasswordValid(final String password) {
		Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}

	/**
	 * Check whether the pin is valid or not (valid example: "1234")
	 * 
	 * @param pin
	 * @return true if valid
	 */
	public static boolean isPinValid(final String pin) {
		Pattern pattern = Pattern.compile(PIN_PATTERN);
		Matcher matcher = pattern.matcher(pin);
		return matcher.matches();

	}

	/**
	 * Check whether the URL is valid or not
	 * 
	 * @param url
	 * @return true if valid
	 */
	public static boolean isUrlValid(final String url) {
		UrlValidator urlValidator = new UrlValidator();
		return urlValidator.isValid(url);
	}

	/**
	 * Check whether the user sex is valid or not ("MALE" or "FEMALE")
	 * 
	 * @param sex
	 * @return true if valid
	 */
	public static boolean isSexValid(final String sex) {
		if (sex.equals("MALE") || sex.equals("FEMALE")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Check if property is empty or null
	 * 
	 * @param property
	 * @return true if it´s empty or null
	 */
	public static boolean isEmptyOrNull(String property) {
		if (property == null || property.isEmpty()) {
			return true;
		}
		return false;
	}
}
