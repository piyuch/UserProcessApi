package de.user.boundary;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import de.user.api.PasswordTokenContainer;
import de.user.api.request.UserAddressRequest;
import de.user.api.request.UserLoginRequest;
import de.user.api.request.UserRegisterRequest;
import de.user.api.response.UserLoginResponse;
import de.user.common.ObjectMapping;
import de.user.common.Validator;
import de.user.control.MailControl;
import de.user.control.UserControl;
import de.user.exception.user.ExpiredTokenException;
import de.user.exception.user.InvalidEmailException;
import de.user.exception.user.InvalidPasswordException;
import de.user.exception.user.InvalidPinException;
import de.user.exception.user.InvalidTokenException;
import de.user.exception.user.MissingDataException;
import de.user.exception.user.UserAlreadyExistException;
import de.user.exception.user.UserAlreadyRegisteredException;
import de.user.exception.user.UserEmailAlreadyVerifiedException;
import de.user.exception.user.UserEmailCouldNotBeVerifiedException;
import de.user.exception.user.UserIsMinorException;
import de.user.exception.user.UserMailNotVerifiedYetException;
import de.user.exception.user.UserNotFoundException;
import de.user.exception.user.WrongEmailOrPasswordException;
import de.user.exception.user.WrongUserSexException;
import de.user.model.UserAddressEntity;
import de.user.model.UserEntity;
import de.user.properties.PropertiesHandler;
import de.user.security.JWTCreator;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

/**
 * This Class provides the business logic functionalities of paylax users
 * 
 * @author piyush chand
 *
 */
@Stateless
public class UserBoundary {

	private static final Logger LOG = LogManager.getLogger(UserBoundary.class);

	@Inject
	private UserControl userControl;

	@Inject
	private MailControl mailControl;

	@Inject
	private ObjectMapping objectMapping;

	@Inject
	private JWTCreator jwtCreator;

	@Inject
	private PropertiesHandler propertiesHandler;

	private static final int USERNAME_SUFFIX_LENGTH = 4;

	private static final int DEFAULT_MAXIMUM_LIMIT_READ_EVENTS = 10;

	/**
	 * Create unverified user
	 * 
	 * @param userLoginRequest
	 * @return userResponse object on success.
	 */
	public UserLoginResponse createUnverifiedUser(final UserLoginRequest userLoginRequest) {
		if (userControl.findUserByEmail(userLoginRequest.getEmail()) != null) {
			throw new UserAlreadyExistException("User with same email already exists.");
		}
		if (!userLoginRequest.isValid()) {
			throw new MissingDataException("Invalid Request, one or many fields are missing!");
		}
		if (!Validator.isEmailAddressValid(userLoginRequest.getEmail())) {
			throw new InvalidEmailException("Email is invalid.");
		}
		if (!Validator.isPasswordValid(new String(userLoginRequest.getPassword()))) {
			throw new InvalidPasswordException("Password is invalid.");
		}
		LOG.entry(userLoginRequest);
		UserEntity user = new UserEntity();
		createUnverifiedUserEntity(userLoginRequest, user);
		UserLoginResponse userResponse = objectMapping.mapObject(user, UserLoginResponse.class);
		sendVerificationEmail(userResponse.getEmail(), userLoginRequest.getAttempted());
		LOG.exit(userResponse);
		return userResponse;

	}

	/**
	 * Set user request values to the entity user to create
	 * 
	 * @param userLoginRequest
	 * @param userEntity
	 */
	private UserEntity createUnverifiedUserEntity(final UserLoginRequest userLoginRequest, UserEntity userEntity) {
		LOG.entry(userLoginRequest);
		userEntity.setEmail(userLoginRequest.getEmail());
		userEntity.setPassword(BCrypt.hashpw(new String(userLoginRequest.getPassword()), BCrypt.gensalt()));
		userEntity.setDeactivated(false);
		userEntity.setIdentified(false);
		userEntity.setMailVerified(false);
		userEntity.setNeedsIdentification(false);
		userEntity.setRegistered(false);
		UserEntity user = userControl.createUser(userEntity);
		LOG.exit(user);
		return user;

	}

	/**
	 * Update an user account to complete registration after verification
	 * 
	 * @param email
	 * @param userRegisterRequest
	 * @return userResponse object on success.
	 */
	public UserLoginResponse updateVerifiedUser(final String email, final UserRegisterRequest userRegisterRequest) {
		LOG.entry(email);
		UserEntity userToUpdate = userControl.findUserByEmail(email);
		if (userToUpdate != null) {
			LOG.info("User found.");
			if (!userToUpdate.isMailVerified()) {
				// exit if user has not verified his mail yet
				throw new UserMailNotVerifiedYetException("User`s email is not verified yet!");
			}
			if (userToUpdate.isRegistered()) {
				// exit if user is already registered
				throw new UserAlreadyRegisteredException("User is already registered!");
			}
			if (!userRegisterRequest.isMissingData()) {
				throw new MissingDataException("Invalid Request, one or many fields are missing!");
			}
			if (!Validator.isPinValid(userRegisterRequest.getPin())) {
				// exit if pin is invalid
				throw new InvalidPinException("Pin is invalid!");
			}
			if (!Validator.isSexValid(userRegisterRequest.getSex().toUpperCase())) {
				// exit if user sex is wrong
				throw new WrongUserSexException("Wrong user sex!");
			}
			if (userIsMinor(userRegisterRequest)) {
				// exit if user is minor
				throw new UserIsMinorException("The user has to be at least 18 years of age.");
			}
			// set the user name of the user.
			String userName = getUniqueUsername(userRegisterRequest.getFirstName(), userRegisterRequest.getLastName());
			userToUpdate.setUserName(userName);
			UserEntity userEntity = updateVerifiedUserEntity(userRegisterRequest, userToUpdate);
			UserLoginResponse userResponse = objectMapping.mapObject(userEntity, UserLoginResponse.class);
			LOG.exit(userResponse);
			return userResponse;
		} else {
			throw new UserNotFoundException("User not found!");
		}
	}

	/**
	 * Send verification email for unverifiedUser
	 * 
	 * @param email
	 * @param attempted
	 *            the link attempted by the user before being redirected to the
	 *            registration
	 */
	public void sendVerificationEmail(final String email, final String attempted) {
		LOG.entry(email);
		UserEntity unverifiedUser = getByEmail(email);
		if (unverifiedUser == null) {
			throw new UserNotFoundException("User not found.");
		}
		LOG.entry(unverifiedUser);
		if (unverifiedUser.isMailVerified() != true) {
			String token = jwtCreator.createEmailVerificationToken(unverifiedUser.getEmail());
			// Check if there is an attempted link,then we add it to the token
			if (attempted != null) {
				token += "?attempted=" + attempted;
			}
			mailControl.sendVerificationMailToUser(unverifiedUser, token);
			LOG.exit();
		} else {
			throw new UserEmailAlreadyVerifiedException("User email already verfied.");
		}
	}

	/**
	 * Re-send verification email for unverifiedUser
	 * 
	 * @param email
	 */
	public void resendVerificationEmail(final String email) {
		LOG.entry(email);
		UserEntity unverifiedUser = getByEmail(email);
		if (unverifiedUser == null) {
			throw new UserNotFoundException("User not found.");
		}
		LOG.entry(unverifiedUser);
		if (unverifiedUser.isMailVerified() != true) {
			String token = jwtCreator.createEmailVerificationToken(unverifiedUser.getEmail());
			mailControl.sendVerificationMailToUser(unverifiedUser, token);
			LOG.exit();
		} else {
			throw new UserEmailAlreadyVerifiedException("User email already verfied.");
		}
	}

	/**
	 * Authenticate an user by email and password.
	 * 
	 * @param userLoginRequest
	 * @param maxLimitRead
	 *            maximum limit for the read events
	 * @return the user Login Response that contains the token
	 */
	public UserLoginResponse authenticate(final UserLoginRequest userLoginRequest, int maxLimitRead) {
		LOG.entry(userLoginRequest);
		UserEntity userEntity = userControl.findUserByEmail(userLoginRequest.getEmail());
		if (!userLoginRequest.isValid()) {
			throw new MissingDataException("Invalid Request, one or many fields are missing!");
		}
		if (userEntity != null
				&& BCrypt.checkpw(new String(userLoginRequest.getPassword()), new String(userEntity.getPassword()))) {
			LOG.info("Password check successful");
			String token = jwtCreator.createAuthToken(userLoginRequest.getEmail());
			UserLoginResponse userLoginResponse = getUserLoginResponse(userEntity, maxLimitRead);
			userLoginResponse.setToken(token);
			userLoginResponse.setCommissionMinimum(propertiesHandler.getProperty("commission.paylax.min"));
			userLoginResponse.setCommissionPercentage(propertiesHandler.getProperty("commission.paylax.percentage"));
			return userLoginResponse;
		} else {
			throw new WrongEmailOrPasswordException("Login failed in relation to an wrong email/password combination.");
		}
	}

	/**
	 * Method to get a UserLoginResponse contains the events that are read,
	 * unread and needsAction
	 * 
	 * @param userEntity
	 * @return maxLimitRead limit for the read events
	 */
	private UserLoginResponse getUserLoginResponse(UserEntity userEntity, int maxLimitRead) {
		UserLoginResponse mappedUser = objectMapping.mapObject(userEntity, UserLoginResponse.class);
		if (maxLimitRead == 0) {
			maxLimitRead = DEFAULT_MAXIMUM_LIMIT_READ_EVENTS;
		}

		return mappedUser;
	}

	/**
	 * Get user by userId.
	 * 
	 * @param userId
	 * @return userEntity on success.
	 */
	public UserEntity getByUserId(final long userId) {
		LOG.entry(userId);
		UserEntity userEntity = userControl.findUserById(userId);
		LOG.exit(userEntity);
		return userEntity;
	}

	/**
	 * Get user by email.
	 * 
	 * @param email
	 * @return user entity on success.
	 */
	public UserEntity getByEmail(final String email) {
		LOG.entry(email);
		UserEntity user = userControl.findUserByEmail(email);
		LOG.exit(user);
		return user;
	}

	/**
	 * Update user´s data
	 * 
	 * @param userEntity
	 * @return user entity on success.
	 */
	public UserEntity updateUser(final UserEntity userEntity) {
		LOG.entry(userEntity);
		UserEntity userUpdated = userControl.updateUser(userEntity);
		LOG.exit(userUpdated);
		return userUpdated;
	}

	/**
	 * Set user email as verified
	 * 
	 * @param token
	 * @return verified user object
	 */
	public UserLoginResponse verifyEmail(final String token) {
		LOG.entry(token);
		String email = this.parseEmailFromToken(token);
		UserEntity userEntity = this.getByEmail(email);
		if (userEntity == null) {
			throw new UserNotFoundException("User not found");
		}
		if (userEntity.isMailVerified() != true) {
			userEntity.setMailVerified(true);
			UserLoginResponse userLoginResponse = getUserLoginResponse(userEntity, 0);
			LOG.exit(userLoginResponse);
			return userLoginResponse;
		} else {
			throw new UserEmailAlreadyVerifiedException("User email already verfied.");

		}

	}

	/**
	 * Parse unverifiedUser´s email from his sent token
	 * 
	 * @param token
	 * @return email
	 */
	public String parseEmailFromToken(final String token) {
		LOG.entry(token);
		try {
			String email = jwtCreator.parse(token).getBody().getSubject();
			if (email != null) {
				LOG.exit(email);
				return email;
			} else {
				throw new UserEmailCouldNotBeVerifiedException("E-Mail could not be verified by token.");
			}
		} catch (SignatureException e) {
			throw new InvalidTokenException("Your token is not valid.");
		} catch (ExpiredJwtException e) {
			throw new ExpiredTokenException("Your token has expired.");
		}
	}

	/**
	 * Send token to user to reset his password
	 * 
	 * @param userEntity
	 * @throws IOException
	 */
	public void sendPasswordTokenMail(final UserEntity userEntity) {
		LOG.entry(userEntity);
		String token = jwtCreator.createResetPasswordToken(userEntity.getEmail());
		mailControl.sendPasswordResetMailToUser(userEntity, token);
		LOG.exit();
	}

	/**
	 * Reset password for user
	 * 
	 * @param passwordAndToken:
	 *            token with the new password
	 * @return user entity on success.
	 */
	public UserEntity resetPassword(final PasswordTokenContainer passwordAndToken) {
		LOG.entry(passwordAndToken);
		String email = this.parseEmailFromToken(passwordAndToken.getToken());
		UserEntity userEntity = this.getByEmail(email);

		if (userEntity == null) {
			throw new UserNotFoundException("User not found");
		}
		LOG.info(userEntity);
		userEntity.setPassword(BCrypt.hashpw(new String(passwordAndToken.getPassword()), BCrypt.gensalt()));
		this.updateUser(userEntity);
		LOG.exit(userEntity);
		return userEntity;
	}

	/**
	 * Get userId from the token
	 * 
	 * @param token
	 * @return userId
	 */
	public long getUserIdFromToken(final String token) {
		LOG.entry(token);
		String email = parseEmailFromToken(token);
		UserEntity userEntity = getByEmail(email);
		if (userEntity == null) {
			throw new UserNotFoundException("User not found");
		}
		LOG.info(userEntity);
		long userId = userEntity.getId();
		LOG.exit(userId);
		return userId;
	}

	/**
	 * Get authenticated userId
	 * 
	 * @param principal
	 * @return userId
	 */
	public long getPrincipalUserId(Principal principal) {
		LOG.entry(principal);
		String username = principal.getName();
		UserEntity userEntity = getByEmail(username);
		if (userEntity == null) {
			throw new UserNotFoundException("User not found");
		}
		LOG.info(userEntity);
		long userId = userEntity.getId();
		LOG.exit(userId);
		return userId;
	}

	/**
	 * Get authenticated user
	 * 
	 * @param principal
	 * @return userLoginResponse
	 */
	public UserLoginResponse getPrincipalUser(Principal principal) {
		LOG.entry(principal);
		String username = principal.getName();
		UserEntity userEntity = getByEmail(username);
		if (userEntity == null) {
			throw new UserNotFoundException("User not found");
		}
		LOG.info(userEntity);
		UserLoginResponse userLoginResponse = getUserLoginResponse(userEntity, 0);
		LOG.exit(userLoginResponse);
		return userLoginResponse;
	}

	/**
	 * This method creates the unique Username
	 * 
	 * @param userToUpdate
	 *            the user to be updated
	 * 
	 * @param userRegisterRequest
	 *            the user request to finish registration
	 */
	private String getUniqueUsername(String firstName, String lastName) {
		// Set new username for the first time
		String userName = generateUserName(firstName, lastName);
		UserEntity userEntity = userControl.findUserByUserName(userName);
		// check if the user already exists
		while (userEntity != null) {
			// user with that username already exists; create new username
			userName = generateUserName(firstName, lastName);
		}
		return userName;
	}

	/**
	 * Generates a username with a randomSuffix alphanumeric in the end, for
	 * example piyush.chand.8dx6 If there is any space in the firstname or
	 * lastname is will replaced by "."
	 * 
	 * @param firstName
	 * @param lastName
	 * @return the generated userName
	 */
	private String generateUserName(String firstName, String lastName) {
		firstName = firstName.toLowerCase().replace(" ", ".");
		lastName = lastName.toLowerCase().replace(" ", ".");
		return firstName + "." + lastName + "." + randomNumberSuffix();
	}

	/**
	 * creates the randomNumber for the userName Suffix.
	 * 
	 * @return
	 */
	private String randomNumberSuffix() {
		String rand = RandomStringUtils.randomAlphanumeric(USERNAME_SUFFIX_LENGTH);
		return rand.toLowerCase();
	}

	/**
	 * Set user request values to the entity user to update
	 * 
	 * @param userRegisterRequest
	 * @param userToUpdate
	 */
	private UserEntity updateVerifiedUserEntity(final UserRegisterRequest userRegisterRequest,
			UserEntity userToUpdate) {
		LOG.entry(userRegisterRequest);
		userToUpdate.setPin(userRegisterRequest.getPin());
		userToUpdate.setCitizenship(userRegisterRequest.getCitizenship());
		userToUpdate.setSex(userRegisterRequest.getSex().toUpperCase());
		userToUpdate.setDateOfBirth(userRegisterRequest.getDateOfBirth());
		userToUpdate.setFirstName(userRegisterRequest.getFirstName());
		userToUpdate.setLastName(userRegisterRequest.getLastName());
		userToUpdate.setRegistered(true);
		updateUserAddressEntity(userRegisterRequest, userToUpdate);
		userToUpdate = userControl.updateUser(userToUpdate);
		LOG.exit(userToUpdate);
		return userToUpdate;
	}

	/**
	 * Set user request´s address to the entity user to update
	 * 
	 * @param userRegisterRequest
	 * @param userToUpdate
	 */
	private void updateUserAddressEntity(final UserRegisterRequest userRegisterRequest, UserEntity userToUpdate) {
		LOG.entry(userRegisterRequest);
		List<UserAddressRequest> addresses = userRegisterRequest.getAddresses();
		UserAddressEntity userAddressEntity = objectMapping.mapObject(addresses.get(0), UserAddressEntity.class);
		LOG.info("User´s address {}", userAddressEntity);
		userAddressEntity.setUser(userToUpdate);
		userToUpdate.setAddress(userAddressEntity);
		LOG.exit();
	}

	/**
	 * Check if the user is minor
	 * 
	 * @param user
	 *            the user to check
	 * @return true if the user is minor
	 */
	private boolean userIsMinor(final UserRegisterRequest user) {
		LocalDate today = LocalDate.now();
		LocalDate userBirthday = user.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		Period userAge = Period.between(userBirthday, today);
		LOG.info("user age: " + userAge.getYears());

		if (userAge.getYears() < 18) {
			return true;
		}
		return false;
	}
}
