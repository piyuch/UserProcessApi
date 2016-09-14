package de.user.test.users;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
import de.user.exception.user.InvalidPinException;
import de.user.exception.user.MissingDataException;
import de.user.exception.user.UserIsMinorException;
import de.user.exception.user.UserNotFoundException;
import de.user.exception.user.WrongUserSexException;
import de.user.model.UserEntity;
import de.user.test.InContainerTest;

@RunWith(Arquillian.class)
public class FinalRegistrationStepTests extends InContainerTest {

	@Inject
	private UserBoundary userBoundary;

	/**
	 * Register user with missing data test
	 */
	@SuppressWarnings("deprecation")
	@Test(expected = MissingDataException.class)
	@UsingDataSet("datasets/finalRegistrationStep/finalRegistrationStep.yml")
	public void MissingData_MissingDataExceptionThrown() {
		UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
		userRegisterRequest.setFirstName("");
		userRegisterRequest.setLastName("");
		userRegisterRequest.setSex("MALE");
		Date date = new Date();
		date.setDate(1988 - 05 - 22);
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
		userBoundary.updateVerifiedUser("isregistered@gmail.com", userRegisterRequest);
	}

	/**
	 * Register user successfully test
	 * 
	 * @throws ParseException
	 */
	@Test
	@UsingDataSet("datasets/finalRegistrationStep/finalRegistrationStep.yml")
	public void FinishRegistration_ExpectRegistrationFinished() throws ParseException {
		UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
		userRegisterRequest.setFirstName("felix");
		userRegisterRequest.setLastName("hagspiel");
		userRegisterRequest.setSex("MALE");
		// Get current date minus 18 years as date of birth
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(LocalDate.now().minusYears(18).toString());
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
		userBoundary.updateVerifiedUser("isregistered@gmail.com", userRegisterRequest);
		UserEntity userEntity = userBoundary.getByEmail("isregistered@gmail.com");
		assertThat(userEntity.isRegistered(), is(true));
	}

	/**
	 * Register user with response on success test
	 * 
	 * @throws ParseException
	 */
	@Test
	@UsingDataSet("datasets/finalRegistrationStep/finalRegistrationStep.yml")
	public void FinishRegistration_ExpectLoginResponse() throws ParseException {
		UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
		userRegisterRequest.setFirstName("felix");
		userRegisterRequest.setLastName("hagspiel");
		// Get current date minus 18 years as date of birth
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(LocalDate.now().minusYears(18).toString());
		userRegisterRequest.setDateOfBirth(date);
		userRegisterRequest.setPin("1234");
		userRegisterRequest.setCitizenship("Germany");
		userRegisterRequest.setSex("MALE");
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
		UserLoginResponse userLoginResponse = userBoundary.updateVerifiedUser("isregistered@gmail.com",
				userRegisterRequest);
		assertThat(userLoginResponse, notNullValue());
		assertThat(userLoginResponse.isMailVerified(), is(true));
		assertThat(userLoginResponse.isIdentified(), is(false));
		assertThat(userLoginResponse.isNeedsIdentification(), is(false));
		assertThat(userLoginResponse.isRegistered(), is(true));
		assertThat(userLoginResponse.getEmail(), is("isregistered@gmail.com"));
		assertThat(userLoginResponse.getFirstName(), is("felix"));
		assertThat(userLoginResponse.getLastName(), is("hagspiel"));
		assertThat(userLoginResponse.getCitizenship(), is("Germany"));
	}

	/**
	 * Register unexisting user test
	 */
	@SuppressWarnings("deprecation")
	@Test(expected = UserNotFoundException.class)
	@UsingDataSet("datasets/users/users.yml")
	public void FinishRegistrationForNotFoundUser_UserNotFoundExceptionThrown() {
		UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
		userRegisterRequest.setFirstName("felix");
		userRegisterRequest.setLastName("hagspiel");
		userRegisterRequest.setSex("MALE");
		Date date = new Date();
		date.setDate(1988 - 05 - 22);
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
		userBoundary.updateVerifiedUser("hereiam19@gmail.com", userRegisterRequest);
	}

	/**
	 * Register user with invalid PIN test
	 */
	@SuppressWarnings("deprecation")
	@Test(expected = InvalidPinException.class)
	@UsingDataSet("datasets/users/users.yml")
	public void FinishRegistrationWithInvalidPin_InvalidPinExceptionThrown() {
		UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
		userRegisterRequest.setFirstName("felix");
		userRegisterRequest.setLastName("hagspiel");
		userRegisterRequest.setSex("MALE");
		Date date = new Date();
		date.setDate(1988 - 05 - 22);
		userRegisterRequest.setDateOfBirth(date);
		userRegisterRequest.setPin("1"); // Invalid PIN
		userRegisterRequest.setCitizenship("Germany");

		UserAddressRequest address = new UserAddressRequest();
		address.setAddressInfo("xyz");
		address.setCity("Berlin");
		address.setPostalCode("12345");
		address.setCountry("Germany");
		address.setName("felix");
		address.setStreet("berliner strasse");

		List<UserAddressRequest> addresses = new ArrayList<UserAddressRequest>();
		addresses.add(address);
		userRegisterRequest.setAddresses(addresses);
		userBoundary.updateVerifiedUser("isnotregistered@gmail.com", userRegisterRequest);
	}

	/**
	 * Register user with wrong sex test
	 */
	@SuppressWarnings("deprecation")
	@Test(expected = WrongUserSexException.class)
	@UsingDataSet("datasets/finalRegistrationStep/finalRegistrationStep.yml")
	public void FinishRegistrationWithWrongSex_WrongUserSexExceptionThrown() {
		UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
		userRegisterRequest.setFirstName("felix");
		userRegisterRequest.setLastName("hagspiel");
		userRegisterRequest.setSex("WRONG_SEX");
		Date date = new Date();
		date.setDate(1988 - 05 - 22);
		userRegisterRequest.setDateOfBirth(date);
		userRegisterRequest.setPin("1234");
		userRegisterRequest.setCitizenship("Germany");

		UserAddressRequest address = new UserAddressRequest();
		address.setAddressInfo("xyz");
		address.setCity("Berlin");
		address.setPostalCode("12345");
		address.setCountry("Germany");
		address.setName("felix");
		address.setStreet("berliner strasse");

		List<UserAddressRequest> addresses = new ArrayList<UserAddressRequest>();
		addresses.add(address);
		userRegisterRequest.setAddresses(addresses);
		userBoundary.updateVerifiedUser("wrongsex@gmail.com", userRegisterRequest);
	}

	/**
	 * Register minor user
	 * 
	 * @throws ParseException
	 */
	@Test(expected = UserIsMinorException.class)
	@UsingDataSet("datasets/finalRegistrationStep/finalRegistrationStep.yml")
	public void FinishRegistrationForMinorUser_UserIsMinorExceptionThrown() throws ParseException {
		UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
		userRegisterRequest.setFirstName("felix");
		userRegisterRequest.setLastName("hagspiel");
		userRegisterRequest.setSex("MALE");
		// Get current date as date of birth
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(LocalDate.now().toString());
		userRegisterRequest.setDateOfBirth(date);
		userRegisterRequest.setPin("1234");
		userRegisterRequest.setCitizenship("Germany");
		UserAddressRequest address = new UserAddressRequest();
		address.setAddressInfo("xyz");
		address.setCity("Berlin");
		address.setPostalCode("12345");
		address.setCountry("Germany");
		address.setName("felix");
		address.setStreet("berliner strasse");
		List<UserAddressRequest> addresses = new ArrayList<UserAddressRequest>();
		addresses.add(address);
		userRegisterRequest.setAddresses(addresses);
		userBoundary.updateVerifiedUser("minor@paynrelax.de", userRegisterRequest);
	}
}
