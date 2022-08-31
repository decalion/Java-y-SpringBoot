package com.icaballero.product.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.icaballero.product.util.StatusEnum;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDTO {
	
	
	private String message;
	private String code;
	private String date;
	private StatusEnum status;

}
