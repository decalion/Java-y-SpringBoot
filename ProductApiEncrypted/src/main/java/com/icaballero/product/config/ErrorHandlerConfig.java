package com.icaballero.product.config;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.icaballero.product.dto.ErrorResponseDTO;
import com.icaballero.product.exceptions.GeneralSystemFailureException;
import com.icaballero.product.util.StatusEnum;

import lombok.extern.slf4j.Slf4j;

/**
 * Clase para controlar las excepciones
 * @author Ismael Caballero
 *
 */
@ControllerAdvice
@Slf4j
public class ErrorHandlerConfig extends ResponseEntityExceptionHandler {
	
	
	private static final String DATE_PATTERN ="dd/MM/yyyy HH:mm:ss";
	
	/**
	 * all exception
	 * @param e
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> all(Exception e, WebRequest request) {
		
		log.error(e.getMessage(),e);
		
		ErrorResponseDTO response = new ErrorResponseDTO();
		response.setDate(getDate());
		response.setMessage(e.getMessage());
		response.setStatus(StatusEnum.KO);
		response.setCode("500");
		
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	
	/**
	 * generalSystemFailureException
	 * @param e
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@ExceptionHandler(GeneralSystemFailureException.class)
	public ResponseEntity<?> generalSystemFailureException(GeneralSystemFailureException e, WebRequest request) {
		
		log.error(e.getMessage(),e);
		
		ErrorResponseDTO response = new ErrorResponseDTO();
		response.setDate(getDate());
		response.setMessage(e.getMessage());
		response.setStatus(StatusEnum.KO);
		response.setCode("500");
		
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	
	
	/**
	 * Get Date from LocalDateTime
	 * @return
	 * @throws ParseException 
	 */
	private String getDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
		LocalDateTime localDateTime = LocalDateTime.now();
		String dateFormatter = localDateTime.format(formatter);
	
		
		return dateFormatter;
	}

}
