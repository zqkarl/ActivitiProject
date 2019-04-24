package com.activiti.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="could not find user by userid")
public class UserCouldNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2213176429269396028L;
	
}
