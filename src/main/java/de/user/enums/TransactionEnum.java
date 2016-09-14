package de.user.enums;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * The possible statuses of a transaction.
 */
public enum TransactionEnum {
	/**
	 * When a transaction is created
	 */
	@Enumerated(EnumType.STRING) CREATED,
	/**
	 * When a transaction finished.
	 */
	@Enumerated(EnumType.STRING) FINISHED
}
