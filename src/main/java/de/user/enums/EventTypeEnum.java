package de.user.enums;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * This Enum Class represents the Event Types that are associated to the user
 * 
 * @author piyushchand
 *
 */
public enum EventTypeEnum {

	/**
	 * a notification event for the user as Information
	 */
	@Enumerated(EnumType.STRING) INFO,
	/**
	 * an action that needs to be taken by the user
	 */
	@Enumerated(EnumType.STRING) ACTION,
	/**
	 * a notification event for the user as a Message
	 */
	@Enumerated(EnumType.STRING) MESSAGE;

}
