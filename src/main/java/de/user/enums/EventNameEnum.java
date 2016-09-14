package de.user.enums;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * This class represents the various EventNames associated to the business logic
 * of paylax
 * 
 * @author piyushchand
 *
 */
public enum EventNameEnum {

	/**************************************
	 * Info-Eventnames
	 */

	/**
	 * A contract has been created by payee
	 */
	@Enumerated(EnumType.STRING) INFO_CONTRACT_CREATED_BY_PAYEE_FPAYEE,

	/**
	 * refers to when a payer is invited for a contract
	 */
	@Enumerated(EnumType.STRING) INFO_PAYER_INVITED_FPAYEE,

	/**
	 * refers to when a contract is started
	 */
	@Enumerated(EnumType.STRING) INFO_CONTRACT_STARTED_FPAYEE, @Enumerated(EnumType.STRING) INFO_CONTRACT_STARTED_FPAYER,

	/**
	 * refers to when a contract is finished
	 */
	@Enumerated(EnumType.STRING) INFO_CONTRACT_FINISHED_FPAYEE, @Enumerated(EnumType.STRING) INFO_CONTRACT_FINISHED_FPAYER,

	/**
	 * refers to the info for that the money has arrived
	 */
	@Enumerated(EnumType.STRING) INFO_MONEY_ARRIVED_FPAYEE, @Enumerated(EnumType.STRING) INFO_MONEY_ARRIVED_FPAYER,

	/**
	 * refers to the info when the payee sent the package
	 */
	@Enumerated(EnumType.STRING) INFO_PACKAGE_SENT_FPAYEE, @Enumerated(EnumType.STRING) INFO_PACKAGE_SENT_FPAYER,

	/**
	 * refers to the info when the payer reports a problem
	 */
	@Enumerated(EnumType.STRING) INFO_PROBLEM_REPORTED_BY_PAYER_FPAYEE, @Enumerated(EnumType.STRING) INFO_PROBLEM_REPORTED_BY_PAYER_FPAYER,

	/**
	 * refers to info when the payee sends a charge-back request
	 */
	@Enumerated(EnumType.STRING) INFO_CHARGEBACK_REQUEST_SENT_FPAYEE, @Enumerated(EnumType.STRING) INFO_CHARGEBACK_REQUEST_SENT_FPAYER,

	/**
	 * refers to info when the payer accepts a charge-back request
	 */
	@Enumerated(EnumType.STRING) INFO_CHARGEBACK_REQUEST_ACCEPTED_FPAYEE, @Enumerated(EnumType.STRING) INFO_CHARGEBACK_REQUEST_ACCEPTED_FPAYER,

	/**
	 * refers to info when the payer denies a charge-back request
	 */
	@Enumerated(EnumType.STRING) INFO_CHARGEBACK_REQUEST_DENIED_FPAYEE, @Enumerated(EnumType.STRING) INFO_CHARGEBACK_REQUEST_DENIED_FPAYER,

	/**
	 * refers to info when the payee send a closing request
	 */
	@Enumerated(EnumType.STRING) INFO_CLOSING_REQUEST_SENT_FPAYEE, @Enumerated(EnumType.STRING) INFO_CLOSING_REQUEST_SENT_FPAYER,
	/**
	 * refers to info when the payer accepts a closing request
	 */
	@Enumerated(EnumType.STRING) INFO_CLOSING_REQUEST_ACCEPTED_FPAYEE, @Enumerated(EnumType.STRING) INFO_CLOSING_REQUEST_ACCEPTED_FPAYER,
	/**
	 * refers to info when the contract is closed
	 */
	@Enumerated(EnumType.STRING) INFO_CONTRACT_CLOSED_FPAYEE, @Enumerated(EnumType.STRING) INFO_CONTRACT_CLOSED_FPAYER,
	/**
	 * refers to info when a message is sent
	 */
	@Enumerated(EnumType.STRING) MESSAGE_CONTRACT_SENT, @Enumerated(EnumType.STRING) MESSAGE_CONTRACT_RECEIVED,

	/**
	 * refers to info when the payer denies a closing request
	 */
	@Enumerated(EnumType.STRING) INFO_CLOSING_REQUEST_DENIED_FPAYEE, @Enumerated(EnumType.STRING) INFO_CLOSING_REQUEST_DENIED_FPAYER,

	/**************************************
	 * Action-Eventnames
	 */

	/**
	 * A payer should be invited to a contract
	 */
	@Enumerated(EnumType.STRING) ACTION_INVITE_PAYER_TO_CONTRACT,

	/**
	 * A payer has to react to an invitation
	 */
	@Enumerated(EnumType.STRING) ACTION_REACT_TO_INVITATION_FROM_PAYEE,

	/**
	 * A payee needs to Act to this event as the money has arrived
	 */
	@Enumerated(EnumType.STRING) ACTION_FULFIL_PAYEE,

	/**
	 * A payer needs to Act to this event as the money has arrived
	 */
	@Enumerated(EnumType.STRING) ACTION_FULFIL_PAYER,

	/**
	 * A payee needs to act to this event when the payer report a problem
	 */
	@Enumerated(EnumType.STRING) ACTION_REACT_TO_PROBLEM_REPORT,
	/**
	 * A payer needs to react when payee sends a charge-back request
	 */
	@Enumerated(EnumType.STRING) ACTION_REACT_TO_CHARGEBACK_REQUEST,

	/**
	 * A payer needs to react when payee sends a closing request
	 */
	@Enumerated(EnumType.STRING) ACTION_REACT_TO_CLOSING_REQUEST;

	/**************************************
	 * Message-Eventnames
	 */

}
