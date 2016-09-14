package de.user.test.validators;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.user.common.Validator;
import de.user.test.InContainerTest;

@RunWith(Arquillian.class)
public class ValidatorsTests extends InContainerTest {

	/**
	 * Test email validator with invalid email (missing the domain name)
	 */
	@Test
	public void EmailValidationWithInvalidEmailTest() {
		boolean isValid = Validator.isEmailAddressValid("invalid@email");
		assertThat(isValid, is(false));
	}

	/**
	 * Test email validator with valid email
	 */
	@Test
	public void EmailValidationWithValidEmailTest() {
		boolean isValid = Validator.isEmailAddressValid("valid@email.de");
		assertThat(isValid, is(true));
	}

	/**
	 * Test password validator with invalid password (missing special characters
	 * or numbers. )
	 */
	@Test
	public void PasswordValidationWithInvalidPasswordTest() {
		boolean isValid = Validator.isPasswordValid("invalidPassword");
		assertThat(isValid, is(false));
	}

	/**
	 * Test password validator with valid password
	 */
	@Test
	public void PasswordValidationWithValidPasswordTest() {
		boolean isValid = Validator.isPasswordValid("Valid123");
		assertThat(isValid, is(true));
	}

	/**
	 * Test pin validator with invalid pin (missing a digit)
	 */
	@Test
	public void PinValidationWithInvalidPinTest() {
		boolean isValid = Validator.isPinValid("023");
		assertThat(isValid, is(false));
	}

	/**
	 * Test pin validator with valid pin
	 */
	@Test
	public void PinValidationWithValidPinTest() {
		boolean isValid = Validator.isPinValid("1234");
		assertThat(isValid, is(true));
	}

	/**
	 * Test url validator with invalid url (missing the domain name)
	 */
	@Test
	public void UrlValidationWithInvalidUrlTest() {
		boolean isValid = Validator.isUrlValid("http://www.paylax.");
		assertThat(isValid, is(false));
	}

	/**
	 * Test url validator with valid url
	 */
	@Test
	public void UrlValidationWithValidUrlTest() {
		boolean isValid = Validator.isUrlValid("http://www.paylax.de");
		assertThat(isValid, is(true));
	}

	/**
	 * Test sex validator with invalid sex (missing the domain name)
	 */
	@Test
	public void SexValidationWithInvalidSexTest() {
		boolean isValid = Validator.isSexValid("INVALIDSEX");
		assertThat(isValid, is(false));
	}

	/**
	 * Test sex validator with valid sex
	 */
	@Test
	public void SexValidationWithValidSexTest() {
		boolean isValid = Validator.isSexValid("MALE");
		assertThat(isValid, is(true));
	}
}
