package com.icaballero.product.util;

import lombok.Getter;

/**
 * GeneralSystemFailureExceptionEnum
 * @author Ismael Caballero
 *
 */
@Getter
public enum GeneralSystemFailureExceptionEnum {
	
	UNXPECTED_ERROR_OCCURED("Error has ocurred");

	
	private String message;
	
	GeneralSystemFailureExceptionEnum(String message) {
		this.message = message;
		
	}
	
	
	

}
