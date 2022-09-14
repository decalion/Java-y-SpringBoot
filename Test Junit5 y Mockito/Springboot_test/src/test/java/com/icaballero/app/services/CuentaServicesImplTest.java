package com.icaballero.app.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.icaballero.app.Datos;
import com.icaballero.app.exceptions.DineroInsuficienteExeption;
import com.icaballero.app.models.Banco;
import com.icaballero.app.models.Cuenta;
import com.icaballero.app.repositories.IBancoRepository;
import com.icaballero.app.repositories.ICuentaRepository;
import com.icaballero.app.services.impl.CuentaServiceImpl;

@SpringBootTest
public class CuentaServicesImplTest {
	
	@MockBean
	ICuentaRepository cuentaRepo;
	
	@MockBean
	IBancoRepository bancoRepo;
	
	@Autowired
	CuentaServiceImpl cuentaServices;
	
	
	
	
	@Test
	void transferirTest() {
		
		when(cuentaRepo.findById(1L)).thenReturn(Datos.crearCuenta001());
		when(cuentaRepo.findById(2L)).thenReturn(Datos.crearCuenta002());
		when(bancoRepo.findById(1L)).thenReturn(Datos.crearBanco());
		
		
		BigDecimal saldoOrigen = cuentaServices.revisarSaldo(1L);
		
		BigDecimal saldoDestino = cuentaServices.revisarSaldo(2L);
		
		assertEquals("1000",saldoOrigen.toPlainString());
		assertEquals("2000",saldoDestino.toPlainString());
		
		cuentaServices.transferir(1L, 2L, new BigDecimal(100), 1L);
		
		saldoOrigen = cuentaServices.revisarSaldo(1L);
		
		saldoDestino = cuentaServices.revisarSaldo(2L);
		
		assertEquals("900",saldoOrigen.toPlainString());
		assertEquals("2100",saldoDestino.toPlainString());
		
		int total = cuentaServices.revisarTotalTransferencia(1L);
		assertEquals(1,total);
		
		verify(cuentaRepo,times(3)).findById(1L);
		verify(cuentaRepo,times(3)).findById(2L);
		verify(cuentaRepo,times(2)).save(any(Cuenta.class));
		
		verify(bancoRepo,times(2)).findById(1L);
		verify(bancoRepo).save(any(Banco.class));
		
		
		verify(cuentaRepo,times(6)).findById(anyLong());
		verify(cuentaRepo,never()).findAll();
	}
	
	
	@Test
	void dineroInsuficientoExceptionTest() {
		
		when(cuentaRepo.findById(1L)).thenReturn(Datos.crearCuenta001());
		when(cuentaRepo.findById(2L)).thenReturn(Datos.crearCuenta002());
		when(bancoRepo.findById(1L)).thenReturn(Datos.crearBanco());
		
		
		BigDecimal saldoOrigen = cuentaServices.revisarSaldo(1L);
		
		BigDecimal saldoDestino = cuentaServices.revisarSaldo(2L);
		
		assertEquals("1000",saldoOrigen.toPlainString());
		assertEquals("2000",saldoDestino.toPlainString());
		
		
		assertThrows(DineroInsuficienteExeption.class, () -> {
			cuentaServices.transferir(1L, 2L, new BigDecimal(1200), 1L);
		});
		
		
		saldoOrigen = cuentaServices.revisarSaldo(1L);
		
		saldoDestino = cuentaServices.revisarSaldo(2L);
		
		assertEquals("1000",saldoOrigen.toPlainString());
		assertEquals("2000",saldoDestino.toPlainString());
		
		int total = cuentaServices.revisarTotalTransferencia(1L);
		assertEquals(0,total);
		
		verify(cuentaRepo,times(3)).findById(1L);
		verify(cuentaRepo,times(2)).findById(2L);
		verify(cuentaRepo,never()).save(any(Cuenta.class));
		
		verify(bancoRepo,times(1)).findById(1L);
		verify(bancoRepo,never()).save(any(Banco.class));
		
		verify(cuentaRepo,times(5)).findById(anyLong());
		verify(cuentaRepo,never()).findAll();
		
		
		
	}

	@Test
	void assertSameTest() {
		when(cuentaRepo.findById(1L)).thenReturn(Datos.crearCuenta001());
	
		Cuenta cuenta = cuentaServices.findById(1L);
		Cuenta cuenta2 = cuentaServices.findById(1L);
		
		assertSame(cuenta, cuenta2);
		
		verify(cuentaRepo,times(2)).findById(1L);
	}

}
