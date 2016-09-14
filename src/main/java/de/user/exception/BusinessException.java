package de.user.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 34112876942344341L;

	private String message;
	private Throwable parent;

	public BusinessException(String message) {
		this.message = message;
	}

	public BusinessException(String message, Throwable parent) {
		this.message = message;
		this.parent = parent;
	}

	public BusinessException(Throwable parent) {
		this.parent = parent;
	}

	public String toString() {
		return "Control exception: " + message + (parent != null ? ", parent: " + parent.toString() : "");
	}

}
