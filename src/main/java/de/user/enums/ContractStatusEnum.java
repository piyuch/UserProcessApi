package de.user.enums;

/**
 * The possible statuses of a contract.
 */
public enum ContractStatusEnum {
	/**
	 * Pending contract. This is the status when a contract was created
	 */
	PENDING,

	/**
	 * Started contract. This is after both payer and payee are present.
	 */
	STARTED,

	/**
	 * Paid contract. This is the status when the money is Paid.
	 */
	PAID,

	/**
	 * Problem Reported. This status will happen as soon as the payer reports a
	 * problem.
	 */
	PROBLEM_REPORTED,

	/**
	 * 
	 * Chargeback Sent. This status will happen as soon as the payee sends a
	 * chargeback request.
	 * 
	 */
	CHARGEBACK_SENT,

	/**
	 * This status will happen when the payee sends a contract closing request
	 */
	CLOSING_REQUEST_SENT,

	/**
	 * This status will happen when the payer accepts the contract closing
	 * request
	 */
	CLOSED,

	/**
	 * Finished contract.
	 */
	FINISHED,

	/**
	 * Deleted contract.
	 */
	DELETED;
}
