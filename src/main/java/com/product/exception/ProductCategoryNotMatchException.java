package com.product.exception;

public class ProductCategoryNotMatchException extends RuntimeException{

	public ProductCategoryNotMatchException(String msg) {
	   super(msg);
	}
}