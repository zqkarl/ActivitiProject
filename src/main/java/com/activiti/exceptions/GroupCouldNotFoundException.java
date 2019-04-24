package com.activiti.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="could not find group by groupid")
public class GroupCouldNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8889753816719938687L;

}
