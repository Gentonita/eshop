package com.axians.eshop.exception;

public class ProductNotFoundException extends RuntimeException {
	
	public ProductNotFoundException(String message) {
		super(message);
	}

}
