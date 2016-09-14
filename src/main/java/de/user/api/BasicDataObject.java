package de.user.api;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Basic Data Object Class that implements common methods like equals, hashcode
 * or toString Methods. If there is a need for special implementations of this
 * common features you can override the implementations.
 * 
 * 
 */
@XmlRootElement
public abstract class BasicDataObject implements Serializable {

	/**
	 * The generated serialVersionUID.
	 */
	private static final long serialVersionUID = 2876242398874879466L;

	public static final String dateFormat = "yyyy-MM-dd HH:mm:ss";

	public static final String dateOfBirthFormat = "yyyy-MM-dd";

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// return super.toString();
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int initial = 31;
		final int multiplier = 7;
		return HashCodeBuilder.reflectionHashCode(initial, multiplier, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}
}
