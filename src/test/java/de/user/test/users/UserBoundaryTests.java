package de.user.test.users;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.user.api.request.UserLoginRequest;
import de.user.api.response.UserLoginResponse;
import de.user.boundary.UserBoundary;
import de.user.exception.user.InvalidEmailException;
import de.user.exception.user.InvalidPasswordException;
import de.user.exception.user.InvalidTokenException;
import de.user.exception.user.MissingDataException;
import de.user.exception.user.UserAlreadyExistException;
import de.user.exception.user.UserEmailAlreadyVerifiedException;
import de.user.exception.user.UserNotFoundException;
import de.user.model.UserEntity;
import de.user.security.JWTCreator;
import de.user.test.InContainerTest;

/**
 * This class tests the User Boundary
 * 
 * @author hazem and piyush
 *
 */

@RunWith(Arquillian.class)
public class UserBoundaryTests extends InContainerTest {

	@Inject
	private UserEntity userEntity;

	@Inject
	private UserBoundary userBoundary;

	@Inject
	private JWTCreator jwtCreator;

	/**
	 * Unverified user creation test
	 */
	@Test
	@UsingDataSet("datasets/users/users.yml")
	public void CreateUnverifiedUser_UserLoginResponse() {
		UserLoginRequest userLoginRequest = new UserLoginRequest();
		userLoginRequest.setEmail("mailpiyush10@gmail.com");
		userLoginRequest.setPassword("Test1234");
		userEntity.setDeactivated(false);
		userEntity.setIdentified(false);
		userEntity.setMailVerified(false);
		userEntity.setNeedsIdentification(false);
		userEntity.setRegistered(false);
		UserLoginResponse userLoginResponse = userBoundary.createUnverifiedUser(userLoginRequest);
		assertThat(userLoginResponse, notNullValue());
		assertThat(userLoginResponse.isMailVerified(), is(false));
		assertThat(userLoginResponse.isIdentified(), is(false));
		assertThat(userLoginResponse.isNeedsIdentification(), is(false));
		assertThat(userLoginResponse.isRegistered(), is(false));
		assertThat(userLoginResponse.getEmail(), is("mailpiyush10@gmail.com"));
		assertThat(userLoginResponse.getFirstName(), nullValue());
		assertThat(userLoginResponse.getLastName(), nullValue());
		assertThat(userLoginResponse.getCitizenship(), nullValue());
	}

	/**
	 * Already existing unverified user creation test
	 */
	@Test(expected = UserAlreadyExistException.class)
	@UsingDataSet("datasets/users/users.yml")
	public void CreateUnverifiedUserWhichExists_UserAlreadyExistExceptionThrown() {
		UserLoginRequest userLoginRequest = new UserLoginRequest();
		userLoginRequest.setEmail("p.chand10@paynrelax.de");
		userLoginRequest.setPassword("Test1234");
		userBoundary.createUnverifiedUser(userLoginRequest);
	}

	/**
	 * Missing data unverified user creation test
	 */
	@Test(expected = MissingDataException.class)
	public void CreateUnverifiedUserMissingData_MissingDataExceptionThrown() {
		UserLoginRequest userLoginRequest = new UserLoginRequest();
		userLoginRequest.setEmail("p.chand1045@paynrelax.de");
		userLoginRequest.setPassword("");
		userBoundary.createUnverifiedUser(userLoginRequest);
	}

	/**
	 * Unverified user creation with invalid email test
	 */
	@Test(expected = InvalidEmailException.class)
	public void CreateUnverifiedUserWithInvalidEmail_InvalidEmailExceptionThrown() {
		UserLoginRequest userLoginRequest = new UserLoginRequest();
		userLoginRequest.setEmail("p.chand10@pay");
		userLoginRequest.setPassword("Test1234");
		userBoundary.createUnverifiedUser(userLoginRequest);
	}

	/**
	 * Unverified user creation with invalid password test
	 */
	@Test(expected = InvalidPasswordException.class)
	public void CreateUnverifiedUserWithInvalidPassword_InvalidPasswordExceptionThrown() {
		UserLoginRequest userLoginRequest = new UserLoginRequest();
		userLoginRequest.setEmail("p.chand123@paynrelax.de");
		userLoginRequest.setPassword("testpass");
		userBoundary.createUnverifiedUser(userLoginRequest);
	}

	/**
	 * For now we catch the EJBException This test will be changed when we
	 * implement our own exception while parsing the token
	 */
	@Test(expected = InvalidTokenException.class)
	public void invalidTokenTest() {
		userBoundary.parseEmailFromToken("wrongtoken");
	}

	/**
	 * Verify already verified user email test
	 */
	@Test(expected = UserEmailAlreadyVerifiedException.class)
	@UsingDataSet("datasets/users/users.yml")
	public void VerifyUserEmailAlreadyVerified_UserEmailAlreadyVerifiedExceptionThrown() {
		String token = jwtCreator.createAuthToken("h.bensassi10@paynrelax.de");
		userBoundary.verifyEmail(token);
	}

	/**
	 * Send verification email to unexisting user test
	 */
	@Test(expected = UserNotFoundException.class)
	@UsingDataSet("datasets/users/users.yml")
	public void SendVerificationUserNotFoundEmail_UserNotFoundExceptionThrown() {
		userBoundary.sendVerificationEmail("hereiam102@gmail.com", null);
	}

	/**
	 * Send verification email to already verified user test
	 */
	@Test(expected = UserEmailAlreadyVerifiedException.class)
	@UsingDataSet("datasets/users/users.yml")
	public void SendVerificationEmailAlreadyVerifiedUser_UserEmailAlreadyVerifiedExceptionThrown() {
		userBoundary.sendVerificationEmail("h.bensassi10@paynrelax.de", null);
	}

}
