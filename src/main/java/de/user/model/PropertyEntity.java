package de.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * A {@code Property} describes a value which can be different depending of the
 * environment the application is running in. For example PROD, INT, DEV.
 *
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "PropertyEntity.findValueByKey", query = "SELECT p.value FROM PropertyEntity p WHERE p.key = :key") })
@Table(name = "properties")
public class PropertyEntity extends BasicEntity {

	/**
	 * Generated serialVersionUID .
	 */
	private static final long serialVersionUID = 8934219379158690920L;

	@Id
	@NotNull
	private String key;

	@Column
	private String value;

	/**
	 * Default constructor.
	 */
	public PropertyEntity() {

	}

	/**
	 * Create a new property.
	 * 
	 * @param key
	 *            unique key
	 * @param value
	 *            the value
	 */
	public PropertyEntity(final String key, final String value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * @return the key
	 */
	public final String getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public final void setKey(final String key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public final String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public final void setValue(final String value) {
		this.value = value;
	}

}
