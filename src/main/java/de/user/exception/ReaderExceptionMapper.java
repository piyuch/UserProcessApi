package de.user.exception;

import java.util.Collection;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.resteasy.spi.ReaderException;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import de.user.api.ErrorCode;
import de.user.common.InternalErrorCodes;

/**
 * Map the Exception from the JSON <-> Object mapping to an {@code ErrorCode}.
 *
 */
@Provider
public class ReaderExceptionMapper implements ExceptionMapper<ReaderException> {

	private static final Logger LOG = LogManager.getLogger(ReaderExceptionMapper.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
	 */
	@Override
	public Response toResponse(final ReaderException exception) {
		LOG.info("Mapping org.jboss.resteasy.spi.ReaderException to ErrorCode.");
		if (exception.getCause() instanceof UnrecognizedPropertyException) {
			UnrecognizedPropertyException upe = (UnrecognizedPropertyException) exception.getCause();
			Collection<Object> knownPropertyIds = upe.getKnownPropertyIds();
			String properties = (String) knownPropertyIds.stream().reduce((a, b) -> a + ", " + b).get();
			String message = "Unrecognized field '" + upe.getPropertyName() + "'. Known properties: " + properties
					+ ".";
			return Response.status(InternalErrorCodes.IEC_2005.getStatus())
					.entity(new ErrorCode(InternalErrorCodes.IEC_2005.getStatus(),
							InternalErrorCodes.IEC_2005.getCode(), message, InternalErrorCodes.IEC_2005.getMoreInfo()))
					.build();
		}
		return null;
	}

}
