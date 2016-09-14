/**
 * 
 */
package de.user.listener;

import java.util.Date;

import javax.persistence.PrePersist;

import de.user.model.BasicEntity;

/**
 * Set the createdAt field.
 *
 */
public class CreatedAtListener {

	/**
	 * Set the createdAt date only on insert and iff the date is not already
	 * set.
	 * 
	 * @param entity
	 *            the entity to change before insert
	 */
	@PrePersist
	public void setCreated(final BasicEntity entity) {
		entity.setCreatedAt(new Date());
	}
}
