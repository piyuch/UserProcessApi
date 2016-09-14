package de.user.api.request;

import de.user.api.BasicDataObject;
import de.user.common.Validator;

/**
 * UserAddress API request object
 * 
 * @author hazem
 *
 */
public class UserAddressRequest extends BasicDataObject {

	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = 1032386019152827288L;

	private String street;

	private String addressInfo;

	private String postalCode;

	private String city;

	private String name;

	private String country;

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getAddressInfo() {
		return addressInfo;
	}

	public void setAddressInfo(String addressInfo) {
		this.addressInfo = addressInfo;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Check if the UserAddressRequest has missing fields
	 * 
	 * @return true if all fields have non null values
	 */
	public boolean isValid() {
		if (Validator.isEmptyOrNull(this.getCity()) || Validator.isEmptyOrNull(this.getName())
				|| Validator.isEmptyOrNull(this.getPostalCode()) || Validator.isEmptyOrNull(this.getStreet())
				|| Validator.isEmptyOrNull(this.getCountry())) {
			return false;
		}
		return true;
	}

}
