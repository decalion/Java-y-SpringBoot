package com.icaballero.junitapp.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Banco
 * @author Ismael Caaballero
 *
 */
@Data
public class Banco {
	
	private List<Cuenta> cuentas = new ArrayList<>();
	private String nombre;
	
	/**
	 * transferir
	 * @param origen
	 * @param destino
	 * @param monto
	 */
	public void transferir(Cuenta origen,Cuenta destino, BigDecimal monto) {
		origen.debito(monto);
		destino.credito(monto);
		
	}
	
	/**
	 * addCuenta
	 * @param cuenta
	 */
	public void addCuenta(Cuenta cuenta) {
		this.cuentas.add(cuenta);
		cuenta.setBanco(this);
	}

}
