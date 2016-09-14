package de.user.test.users;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.user.api.request.UserAddressRequest;
import de.user.api.request.UserRegisterRequest;
import de.user.api.response.UserLoginResponse;
import de.user.boundary.UserBoundary;
import de.user.exception.user.UserMailNotVerifiedYetException;
import de.user.security.JWTCreator;
import de.user.test.InContainerTest;

@RunWith(Arquillian.class)
public class UserEmailVerificationTests extends InContainerTest {

	@Inject
	private UserBoundary userBoundary;

	@Inject
	private JWTCreator jwtCreator;

	/**
	 * Verify user email from his token
	 */
	@Test
	@UsingDataSet("datasets/usersVerification/usersVerification.yml")
	public void VerifyEmailFromToken_UserLoginResponse() {
		String token = jwtCreator.createAuthToken("hereiam26@gmail.com");
		UserLoginResponse userLoginResponse = userBoundary.verifyEmail(token);
		assertThat(userLoginResponse, notNullValue());
		assertThat(userLoginResponse.isMailVerified(), is(true));
		assertThat(userLoginResponse.isIdentified(), is(false));
		assertThat(userLoginResponse.isNeedsIdentification(), is(false));
		assertThat(userLoginResponse.isRegistered(), is(false));
		assertThat(userLoginResponse.getEmail(), is("hereiam26@gmail.com"));
		assertThat(userLoginResponse.getFirstName(), nullValue());
		assertThat(userLoginResponse.getLastName(), nullValue());
		assertThat(userLoginResponse.getCitizenship(), nullValue());
	}

	/**
	 * Unverified user registration test
	 */
	@SuppressWarnings("deprecation")
	@Test(expected = UserMailNotVerifiedYetException.class)
	@UsingDataSet("datasets/usersVerification/usersVerification.yml")
	public void UpdateUserWithEmailNotVerifiedYet_UserMailNotVerifiedYetExceptionThrown() {
		UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
		userRegisterRequest.setFirstName("felix");
		userRegisterRequest.setLastName("hagspiel");
		Date date = new Date();
		date.setDate(1984 - 01 - 01);
		userRegisterRequest.setDateOfBirth(date);
		userRegisterRequest.setPin("1234");
		userRegisterRequest.setCitizenship("Germany");
		UserAddressRequest address = new UserAddressRequest();
		address.setAddressInfo("xyz");
		address.setCity("Berlin");
		address.setCountry("Germany");
		address.setName("felix");
		address.setPostalCode("10559");
		address.setStreet("berliner strasse");
		List<UserAddressRequest> addresses = new ArrayList<UserAddressRequest>();
		addresses.add(address);
		userRegisterRequest.setAddresses(addresses);
		userBoundary.updateVerifiedUser("hereiam27@gmail.com", userRegisterRequest);
	}

}
