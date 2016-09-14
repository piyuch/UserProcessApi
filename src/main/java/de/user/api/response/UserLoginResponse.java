package de.user.api.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import de.user.api.BasicDataObject;

/**
 * UserLogin API response object
 * 
 * @author hazem
 *
 */
public class UserLoginResponse extends BasicDataObject {

	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = 1595402433264548627L;

	private String userName;

	private String email;

	private String firstName;

	private String lastName;

	private String citizenship;

	private String sex;

	private String commissionMinimum;

	private String commissionPercentage;

	private boolean isIdentified;

	private boolean needsIdentification;

	private boolean isMailVerified;

	private boolean isRegistered;

	@JsonIgnore
	private String token;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = dateFormat)
	private Date createdAt;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = dateFormat)
	private Date updatedAt;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getCommissionMinimum() {
		return commissionMinimum;
	}

	public void setCommissionMinimum(String commissionMinimum) {
		this.commissionMinimum = commissionMinimum;
	}

	public String getCommissionPercentage() {
		return commissionPercentage;
	}

	public void setCommissionPercentage(String commissionPercentage) {
		this.commissionPercentage = commissionPercentage;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the createdAt
	 */
	public final Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt
	 *            the createdAt to set
	 */
	public final void setCreatedAt(final Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the updatedAt
	 */
	public final Date getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * @param updatedAt
	 *            the updatedAt to set
	 */
	public final void setUpdatedAt(final Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
