package de.user.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Defines the internal error codes for various Fail-Responses.
 * 
 * 400 - Bad Request / Bad Syntax. For example for "Unrecognized Field" or a
 * wrong IBAN number.
 * 
 * 401 - Unauthorized: I.e. a missing logintoken or missing HMAC
 * 
 * 403 - Forbidden: I.e. a missing permissions which is required for an
 * particular action.
 * 
 * 404 - Resource not Found: If you request a resource by id but it cannot be
 * found. Example: GET to /contracts/0717A2RT returns null results → the
 * response status should be 404. This should NOT be the case when we get a
 * list! In that case just return an empty list (I.e. GET to /contracts).
 * 
 * 409 -Conflict: For example when you try to register with an email that is
 * already registered or try to pick a username that is already taken.
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum InternalErrorCodes {

	// **************** standard api IEC 1-999 ***********************
	/**
	 * unauthorized
	 */
	IEC_1(401, "1", "This request needs an authorization token.", "http....de/1"),

	/**
	 * Route could not be found.
	 */
	IEC_2(404, "2", "Not a valid endpoint.", "http....de/2"),

	/**
	 * Token expired.
	 */
	IEC_3(401, "3", "Your token has expired.", "http....de/3"),

	/**
	 * Token is not valid.
	 */
	IEC_4(401, "4", "Your token is not valid.", "http....de/4"),

	// **************** users api IEC 1000-1999 **********************
	/**
	 * No User found with given ID.
	 */
	IEC_1000(404, "1000", "There is no user with that ID.", "http....de/1000"),

	/**
	 * No User found with given email.
	 */
	IEC_1001(404, "1001", "There is no user with that email.", "http....de/1001"),

	/**
	 * User already exists.
	 */
	IEC_1002(409, "1002", "There is already a user with same email.", "http....de/1002"),

	/**
	 * Token Could not be parsed.
	 */
	IEC_1003(409, "1003", "Token could not be parsed.", "http....de/1003"),

	/**
	 * Verification-mail could not be sent.
	 */
	IEC_1004(409, "1004", "Verification-mail could not be sent.", "http....de/1004"),

	/**
	 * Password reset mail could not be sent.
	 */
	IEC_1005(409, "1005", "Password reset mail could not be sent.", "http....de/1005"),

	/**
	 * Login failed in relation to an wrong email/password combination.
	 */
	IEC_1006(404, "1006", "No user found with that email/password combination.", "http....de/1006"),

	/**
	 * User already registered.
	 */
	IEC_1008(409, "1008", "User is already registered!", "http....de/1008"),

	/**
	 * User email not verified yet.
	 */
	IEC_1009(409, "1009", "User email not verified yet!", "http....de/1009"),

	/**
	 * Invalid PIN
	 */
	IEC_1010(400, "1010", "Invalid PIN!", "http....de/1010"),

	/**
	 * Invalid password
	 */
	IEC_1011(400, "1011", "Invalid password!", "http....de/1011"),

	/**
	 * Invalid email
	 */
	IEC_1012(400, "1012", "Invalid email!", "http....de/1012"),

	/**
	 * User email already verified
	 */
	IEC_1013(409, "1013", "User email already verified!", "http....de/1013"),

	/**
	 * Missing Data for the request
	 */
	IEC_1014(400, "1014", "Missing Data!", "http....de/1014"),

	/**
	 * User not registered yet.
	 */
	IEC_1015(409, "1015", "User not registered yet!", "http....de/1015"),

	/**
	 * The user has to be at least 18 years of age
	 */
	IEC_1016(409, "1016", "The user has to be at least 18 years of age!", "http....de/1016"),

	// **************** transactions api IEC 2000-2999 ***************

	/**
	 * No participant found with given ID.
	 */
	IEC_2003(404, "2003", "There is no participant for the given id.", "http....de/2003"),

	/**
	 * No client found with given ID.
	 */
	IEC_2004(404, "2004", "No client found with given ID.", "http....de/2004"),

	/**
	 * Wrong field naming in the request body
	 */
	IEC_2005(400, "2005", "Unrecognized field.", "http....de/2005"),

	/**
	 * Invalid Image Link
	 */
	IEC_2006(400, "2006", "Invalid Image Link.", "http....de/2006"),

	/**
	 * No contract found
	 */
	IEC_2007(404, "2007", "No contract found.", "http....de/2007"),

	/**
	 * Contract cannot be joined
	 */
	IEC_2008(409, "2008", "The contract cannot be joined.", "http....de/2008"),

	/**
	 * Same payee and payer
	 */
	IEC_2009(409, "2009", "The payee and the payer cannot be the same.", "http....de/2009"),

	/**
	 * Wrong user sex
	 */
	IEC_2010(409, "2010", "Wrong user sex.", "http....de/2010"),

	/**
	 * No contract event found
	 */
	IEC_2011(404, "2011", "No contract event found.", "http....de/2011"),

	/**
	 * Contract description has exceeded maximum length.
	 */
	IEC_2012(409, "2012", "Contract description must not exceed 2000 characters.", "http....de/2012"),

	/**
	 * Contract title has exceeded maximum length.
	 */
	IEC_2013(409, "2013", "Contract title must not exceed 255 characters.", "http....de/2013"),

	/**
	 * Action can only be proceeded by the payer
	 */
	IEC_2014(409, "2014", "This action can only be proceeded after the payer approval", "http....de/2014"),

	/**
	 * Contract has no payer
	 */
	IEC_2015(409, "2015", "Contract has no payer!", "http....de/2015"),

	/**
	 * Contract without transactions
	 */
	IEC_2016(409, "2016", "Contract has no transaction!", "http....de/2016"),

	/**
	 * Contract not started
	 */
	IEC_2017(409, "2017", "Contract not started.", "http....de/2017"),

	/**
	 * The package can only be sent by the contract´s payee
	 */
	IEC_2018(409, "2018", "The package can only be sent by the contract´s payee.", "http....de/2018"),

	/**
	 * The package is already sent by payee
	 */
	IEC_2019(409, "2019", "The package is already sent by payee.", "http....de/2019"),

	/**
	 * The problem can only be reported by the payer
	 */
	IEC_2020(409, "2020", "The problem can only be reported by the payer.", "http....de/2020"),

	/**
	 * The problem description cannot be empty or null.
	 */
	IEC_2021(400, "2021", "The problem description cannot be empty or null", "http....de/2021"),

	/**
	 * Problem already reported.
	 */
	IEC_2022(409, "2022", "Problem already reported.", "http....de/2022"),

	/**
	 * The charge-back can only be sent by the contract´s payee.
	 */
	IEC_2023(409, "2023", "The charge-back can only be sent by the contract´s payee.", "http....de/2023"),

	/**
	 * The charge-back amount should be less than the original amount.
	 */
	IEC_2024(409, "2024", "The charge-back amount should be less than the original amount.", "http....de/2024"),

	/**
	 * Charge-back request already sent.
	 */
	IEC_2025(409, "2025", "payer has not reacted to the charge-back request yet.", "http....de/2025"),

	/**
	 * No action needed for this event
	 */
	IEC_2026(409, "2026", "No action is needed from the user for this event.", "http....de/2026"),

	/**
	 * Only the contract´s payer can react to the request.
	 */
	IEC_2027(409, "2027", "Only the contract´s payer can react to this request.", "http....de/2027"),

	/**
	 * Money Not Paid Yet
	 * 
	 */
	IEC_2028(409, "2028", "Contract Money Not Paid Yet", "http....de/2028"),

	/**
	 * Problem not reported yet
	 */
	IEC_2029(409, "2029", "Contract Money Not Paid Yet", "http....de/2029"),

	/**
	 * Wrong contract status
	 */
	IEC_2030(409, "2030", "Wrong contract status.", "http....de/2030"),

	/**
	 * Charge-back can only be denied by the contract´s payer.
	 */
	IEC_2031(409, "2031", "Charge-back can only be denied by the contract´s payer.", "http....de/2031"),

	/**
	 * when the amount is less than the paylax commission fees.
	 */
	IEC_2032(409, "2032", "Amount is less than paylax commission fees", "http....de/2032"),

	/**
	 * Closing request can only be sent by the contract´s payee
	 */
	IEC_2033(409, "2033", "Closing request can only be sent by the contract´s payee.", "http....de/2033"),
	/**
	 * Deleting the contract can only be proceeded by the payee
	 */
	IEC_2034(409, "2034", "Deleting the contract can only be proceeded by the payee.", "http....de/2034");

	private final int status;
	private final String code;
	private final String message;
	private final String moreInfo;

	private InternalErrorCodes(int status, String code, String message, String moreInfo) {
		this.status = status;
		this.code = code;
		this.message = message;
		this.moreInfo = moreInfo;
	}

	@JsonProperty("status")
	public int getStatus() {
		return status;
	}

	@JsonProperty("code")
	public String getCode() {
		return code;
	}

	@JsonProperty("message")
	public String getMessage() {
		return message;
	}

	@JsonProperty("moreInfo")
	public String getMoreInfo() {
		return moreInfo;
	}

	public static InternalErrorCodes getError(final String code) {
		for (InternalErrorCodes type : InternalErrorCodes.values()) {
			if (code.equalsIgnoreCase(type.code)) {
				return type;
			}
		}
		return null;
	}
}
