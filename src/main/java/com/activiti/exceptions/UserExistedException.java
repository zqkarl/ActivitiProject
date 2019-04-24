package com.activiti.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.CONFLICT, reason="user or group has existed")
public class UserExistedException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1967474198526781557L;

}
