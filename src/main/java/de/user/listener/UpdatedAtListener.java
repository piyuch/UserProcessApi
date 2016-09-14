package de.user.listener;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import de.user.model.BasicEntity;

/**
 * Set the updatedAt field.
 *
 */
public class UpdatedAtListener {

	/**
	 * Set the updatedAt on insert and update.
	 * 
	 * @param entity
	 *            the entity to change before update and insert
	 */
	@PreUpdate
	@PrePersist
	public void setLastUpdate(final BasicEntity entity) {
		entity.setUpdatedAt(new Date());
	}
}
