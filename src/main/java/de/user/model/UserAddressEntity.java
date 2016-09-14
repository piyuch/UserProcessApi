package de.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The address of an user.
 *
 */
@XmlRootElement
@Entity
@Table(name = "user_addresses")
@NamedQueries({
		@NamedQuery(name = "UserAddressEntity.findCurrentUserAddress", query = "SELECT a FROM UserAddressEntity a WHERE a.isDeactivated = false and a.user = :user"),
		@NamedQuery(name = "UserAddressEntity.findUserAddresses", query = "SELECT a FROM UserAddressEntity a WHERE a.user = :user") })
public class UserAddressEntity extends BasicEntity {

	/**
	 * Generated serialVersionUID .
	 */
	private static final long serialVersionUID = -2437355612472630228L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean isDeactivated;

	@ManyToOne
	private UserEntity user;

	@Column(nullable = false)
	private String street;

	@Column
	private String addressInfo;

	@Column(nullable = false)
	private String postalCode;

	@Column(nullable = false)
	private String city;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String country;

	/**
	 * Make JPA happy.
	 */
	public UserAddressEntity() {
		// default constructor for @Entity
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street
	 *            the street to set
	 */
	public void setStreet(final String street) {
		this.street = street;
	}

	/**
	 * @return the addressInfo
	 */
	public String getAddressInfo() {
		return addressInfo;
	}

	/**
	 * @param addressInfo
	 *            the addressInfo to set
	 */
	public void setAddressInfo(final String addressInfo) {
		this.addressInfo = addressInfo;
	}

	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @param postalCode
	 *            the postalCode to set
	 */
	public void setPostalCode(final String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(final String city) {
		this.city = city;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return true if the address is no longer valid
	 */
	public boolean isDeactivated() {
		return isDeactivated;
	}

	/**
	 * @param isDeactivated
	 */
	public void setDeactivated(boolean isDeactivated) {
		this.isDeactivated = isDeactivated;
	}

	/**
	 * @return the user living in this address
	 */
	public UserEntity getUser() {
		return user;
	}

	/**
	 * @param user
	 */
	public void setUser(UserEntity user) {
		this.user = user;
	}

}
