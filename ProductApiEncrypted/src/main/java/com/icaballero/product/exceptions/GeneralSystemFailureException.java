package com.icaballero.product.exceptions;

import com.icaballero.product.util.GeneralSystemFailureExceptionEnum;

/**
 * GeneralSystemFailureException
 * @author Ismael Caballero
 *
 */
public class GeneralSystemFailureException extends RuntimeException {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param GeneralSystemFailureExceptionEnum 
	 */
	public GeneralSystemFailureException(GeneralSystemFailureExceptionEnum error) {
		super(error.getMessage());
	}
}
