package com.icaballero.app;

import java.math.BigDecimal;
import java.util.Optional;

import com.icaballero.app.models.Banco;
import com.icaballero.app.models.Cuenta;

public class Datos {
	
	public static final Cuenta CUENTA_001 = new Cuenta(1L,"Ismael",new BigDecimal(1000));
	public static final Cuenta CUENTA_002 = new Cuenta(2L,"Cristian",new BigDecimal(2000));
	public static final Banco BANCO = new Banco(1L,"BBVA",0);
	
	
	public static Optional<Cuenta> crearCuenta001() {
		
		
		return Optional.of(new Cuenta(1L,"Ismael",new BigDecimal(1000)));
	}

	public static Optional<Cuenta> crearCuenta002() {
		return Optional.of (new Cuenta(2L,"Cristian",new BigDecimal(2000)));
	}
	
	public static Optional<Banco> crearBanco() {
		return Optional.of (new Banco(1L,"BBVA",0));
	}
}
