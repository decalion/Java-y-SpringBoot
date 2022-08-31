package com.icaballero.product.util;

import lombok.Getter;

/**
 * StatusEnum
 * @author Ismael Caballero
 *
 */
@Getter
public enum StatusEnum {
	
	OK("OK"),
	KO("KO");

	private String error;
	
	StatusEnum(String error) {
		this.error = error;
	}
	
	
	

}
