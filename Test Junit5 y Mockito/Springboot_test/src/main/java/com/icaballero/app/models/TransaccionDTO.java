package com.icaballero.app.models;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TransaccionDTO {
	
	private Long cuentaOrigenId;
	private Long cuentaDestinoId;
	private BigDecimal monto;
	private Long bancoId;

}
