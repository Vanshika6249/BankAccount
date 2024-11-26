package com.bankaccount.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BankNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public BankNotFoundException(String message)
	{
		super(message);
	}
	

}