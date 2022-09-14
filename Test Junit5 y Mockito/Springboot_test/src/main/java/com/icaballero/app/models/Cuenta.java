package com.icaballero.app.models;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.icaballero.app.exceptions.DineroInsuficienteExeption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cuentas")
public class Cuenta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String persona;
	private BigDecimal saldo;
	
	
	public void debito(BigDecimal monto) {
		BigDecimal nuevoSaldo = this.saldo.subtract(monto);
		
		if(nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
			throw new DineroInsuficienteExeption("Dinero Insuficiente");
		}
		
		this.saldo = nuevoSaldo;
	}

	public void credito(BigDecimal monto) {
		
		this.saldo = this.saldo.add(monto);
		
	}
}
