package com.icaballero.junitapp.model;

import java.math.BigDecimal;

import com.icaballero.junitapp.exceptions.DineroInsuficienteException;

import lombok.Data;

/**
 * Cuenta
 * @author Ismael Caballero
 *
 */
@Data
public class Cuenta {
	
	private String persona;
	private BigDecimal saldo;
	private Banco banco;
	
	/**
	 * Cuenta
	 */
	public Cuenta() {

	}

	/**
	 * Cuenta
	 * @param persona
	 * @param saldo
	 */
	public Cuenta(String persona, BigDecimal saldo) {
		this.persona = persona;
		this.saldo = saldo;
	}

	
	/**
	 * Debito
	 * @param monto
	 */
	public void debito(BigDecimal monto) {
		BigDecimal nuevoSaldo = this.saldo.subtract(monto);
		
		if(nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
			throw new DineroInsuficienteException("Dinero Insuficiente");
		}
		
		this.saldo = nuevoSaldo;
	}
	
	/**
	 * Credito
	 * @param monto
	 */
	public void credito(BigDecimal monto) {
		this.saldo = this.saldo.add(monto);
		
	}
	
}
