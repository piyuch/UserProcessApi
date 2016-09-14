package de.user.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Registered user for paylax
 * 
 * @author hazem
 *
 */
@XmlRootElement
@Entity
@Table(name = "users")
@NamedQueries({
		@NamedQuery(name = "UserEntity.findByEmail", query = "SELECT u FROM UserEntity u WHERE u.email = :email"),
		@NamedQuery(name = "UserEntity.findByUserName", query = "SELECT u FROM UserEntity u WHERE u.userName = :userName") })
public class UserEntity extends BasicEntity {

	/**
	 * Generated serialVersionUID .
	 */
	private static final long serialVersionUID = -3479331673042789522L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private boolean isDeactivated;

	@Column
	private String firstName;

	@Column
	private String lastName;

	@Column
	private String sex;

	@Column
	private String pin;

	@Column
	private String citizenship;

	@Column(nullable = false)
	private String password;

	@Column(unique = true, nullable = false)
	private String email;

	@Column(unique = true)
	private String userName;

	@Column
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;

	@Column(nullable = false)
	private boolean isIdentified;

	@Column(nullable = false)
	private boolean needsIdentification;

	@Column(nullable = false)
	private boolean isMailVerified;

	@Column(nullable = false)
	private boolean isRegistered;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<UserAddressEntity> addresses;

	/**
	 * Empty constructor to make JPA happy.
	 */
	public UserEntity() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return user´s first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return user´s last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return user´s pin number
	 */
	public String getPin() {
		return pin;
	}

	/**
	 * @param pin
	 */
	public void setPin(String pin) {
		this.pin = pin;
	}

	/**
	 * @return user´s password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return user´s email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return user´s date of birth
	 */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @param dateOfBirth
	 */
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @return user´s addresses list
	 */
	public List<UserAddressEntity> getAddresses() {
		return addresses;
	}

	/**
	 * @param addresses
	 */
	public void setAddresses(List<UserAddressEntity> addresses) {
		this.addresses = addresses;
	}

	/**
	 * @return citizenship
	 */
	public String getCitizenship() {
		return citizenship;
	}

	/**
	 * @param citizenship
	 */
	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}

	/**
	 * @return sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	public boolean isDeactivated() {
		return isDeactivated;
	}

	public void setDeactivated(boolean isDeactivated) {
		this.isDeactivated = isDeactivated;
	}

	public boolean isIdentified() {
		return isIdentified;
	}

	public void setIdentified(boolean isIdentified) {
		this.isIdentified = isIdentified;
	}

	public boolean isNeedsIdentification() {
		return needsIdentification;
	}

	public void setNeedsIdentification(boolean needsIdentification) {
		this.needsIdentification = needsIdentification;
	}

	public boolean isMailVerified() {
		return isMailVerified;
	}

	public void setMailVerified(boolean isMailVerified) {
		this.isMailVerified = isMailVerified;
	}

	public boolean isRegistered() {
		return isRegistered;
	}

	public void setRegistered(boolean isRegistered) {
		this.isRegistered = isRegistered;
	}

	/**
	 * set an address to the user
	 * 
	 * @param address
	 */
	public void setAddress(final UserAddressEntity address) {
		this.addresses.add(address);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
