package de.user.api.request;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import de.user.api.BasicDataObject;
import de.user.common.Validator;

/**
 * UserRegister API request object
 * 
 * @author piyush chand
 *
 */
public class UserRegisterRequest extends BasicDataObject {

	/**
	 * generated serialVersionUID
	 */
	private static final long serialVersionUID = 7145914884354907486L;

	private String firstName;

	private String lastName;

	private String pin;

	private String citizenship;

	private String sex;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = dateOfBirthFormat, timezone = "CET")
	private Date dateOfBirth;

	private List<UserAddressRequest> addresses;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public List<UserAddressRequest> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<UserAddressRequest> addresses) {
		this.addresses = addresses;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getCitizenship() {
		return citizenship;
	}

	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * Check if the UserRegisterRequest has missing fields
	 * 
	 * @return true if all fields have non null values
	 */
	public boolean isMissingData() {
		if (this.getDateOfBirth() == null || Validator.isEmptyOrNull(this.getFirstName())
				|| Validator.isEmptyOrNull(this.getLastName()) || Validator.isEmptyOrNull(this.getPin())
				|| !this.getAddresses().get(0).isValid() || Validator.isEmptyOrNull(this.getCitizenship())
				|| Validator.isEmptyOrNull(this.getSex())) {
			return false;
		}
		return true;
	}

}
