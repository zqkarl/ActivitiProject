package com.activiti.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.CONFLICT, reason="membership has existed")
public class MembershipHasExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -658023702813592188L;

}
