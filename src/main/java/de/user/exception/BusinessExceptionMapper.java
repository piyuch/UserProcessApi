package de.user.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import de.user.common.InternalErrorCodes;
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

/**
 * Map the {@code BusinessException} to HTTP-Responses.
 */
@Provider
public class BusinessExceptionMapper implements ExceptionMapper<BusinessException> {

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
	 */
	@Override
	public Response toResponse(final BusinessException exception) {

		// User not found
		if (exception instanceof UserNotFoundException) {
			return Response.status(InternalErrorCodes.IEC_1000.getStatus())
					.entity(InternalErrorCodes.getError(InternalErrorCodes.IEC_1000.getCode())).build();

			// User already exists
		} else if (exception instanceof UserAlreadyExistException) {
			return Response.status(InternalErrorCodes.IEC_1002.getStatus())
					.entity(InternalErrorCodes.getError(InternalErrorCodes.IEC_1002.getCode())).build();

			// User email could be verified
		} else if (exception instanceof UserEmailCouldNotBeVerifiedException) {
			return Response.status(InternalErrorCodes.IEC_1003.getStatus())
					.entity(InternalErrorCodes.getError(InternalErrorCodes.IEC_1003.getCode())).build();

			// Wrong email/password combination
		} else if (exception instanceof WrongEmailOrPasswordException) {
			return Response.status(InternalErrorCodes.IEC_1006.getStatus())
					.entity(InternalErrorCodes.getError(InternalErrorCodes.IEC_1006.getCode())).build();

			// User already registered
		} else if (exception instanceof UserAlreadyRegisteredException) {
			return Response.status(InternalErrorCodes.IEC_1008.getStatus())
					.entity(InternalErrorCodes.getError(InternalErrorCodes.IEC_1008.getCode())).build();

			// User Mail not verified yet
		} else if (exception instanceof UserMailNotVerifiedYetException) {
			return Response.status(InternalErrorCodes.IEC_1009.getStatus())
					.entity(InternalErrorCodes.getError(InternalErrorCodes.IEC_1009.getCode())).build();

			// Invalid PIN
		} else if (exception instanceof InvalidPinException) {
			return Response.status(InternalErrorCodes.IEC_1010.getStatus())
					.entity(InternalErrorCodes.getError(InternalErrorCodes.IEC_1010.getCode())).build();

			// Invalid password
		} else if (exception instanceof InvalidPasswordException) {
			return Response.status(InternalErrorCodes.IEC_1011.getStatus())
					.entity(InternalErrorCodes.getError(InternalErrorCodes.IEC_1011.getCode())).build();

			// Invalid email
		} else if (exception instanceof InvalidEmailException) {
			return Response.status(InternalErrorCodes.IEC_1012.getStatus())
					.entity(InternalErrorCodes.getError(InternalErrorCodes.IEC_1012.getCode())).build();

			// User email already verified
		} else if (exception instanceof UserEmailAlreadyVerifiedException) {
			return Response.status(InternalErrorCodes.IEC_1013.getStatus())
					.entity(InternalErrorCodes.getError(InternalErrorCodes.IEC_1013.getCode())).build();

			// Invalid Token
		} else if (exception instanceof InvalidTokenException) {
			return Response.status(InternalErrorCodes.IEC_4.getStatus())
					.entity(InternalErrorCodes.getError(InternalErrorCodes.IEC_4.getCode())).build();

			// Expired Token
		} else if (exception instanceof ExpiredTokenException) {
			return Response.status(InternalErrorCodes.IEC_3.getStatus())
					.entity(InternalErrorCodes.getError(InternalErrorCodes.IEC_3.getCode())).build();

			// Missing Data
		} else if (exception instanceof MissingDataException) {
			return Response.status(InternalErrorCodes.IEC_1014.getStatus())
					.entity(InternalErrorCodes.getError(InternalErrorCodes.IEC_1014.getCode())).build();
			// The user has to be at least 18 years of age
		} else if (exception instanceof UserIsMinorException) {
			return Response.status(InternalErrorCodes.IEC_1016.getStatus())
					.entity(InternalErrorCodes.getError(InternalErrorCodes.IEC_1016.getCode())).build();

			// Wrong user sex
		} else if (exception instanceof WrongUserSexException) {
			return Response.status(InternalErrorCodes.IEC_2010.getStatus())
					.entity(InternalErrorCodes.getError(InternalErrorCodes.IEC_2010.getCode())).build();

			// Contract description maximum length has been exceeded
		}

		return null;
	}

}
