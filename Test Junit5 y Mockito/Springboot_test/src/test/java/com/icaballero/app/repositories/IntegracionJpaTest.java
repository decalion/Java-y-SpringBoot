package com.icaballero.app.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.icaballero.app.models.Cuenta;

@DataJpaTest
public class IntegracionJpaTest {
	
	@Autowired
	ICuentaRepository cuentaRepo;
	
	@Test
	void cuentaFindByIdTest() {
		Optional<Cuenta> cuenta = cuentaRepo.findById(1L);
		
		assertTrue(cuenta.isPresent());
		assertEquals("Ismael",cuenta.orElseThrow().getPersona());
		
		
	}
	
	
	@Test
	void cuentaFindByPersonaTest() {
		Optional<Cuenta> cuenta = cuentaRepo.findByPersona("Ismael");
		
		assertTrue(cuenta.isPresent());
		assertEquals("Ismael",cuenta.orElseThrow().getPersona());
		assertEquals("1000.00",cuenta.orElseThrow().getSaldo().toPlainString());
		
		
	}
	
	
	@Test
	void cuentaFindByPersonaThrowExceptionTest() {
		Optional<Cuenta> cuenta = cuentaRepo.findByPersona("pepe");
		
		assertThrows(NoSuchElementException.class, cuenta::orElseThrow);
		
		assertFalse(cuenta.isPresent());
	}
	
	@Test
	void cuentaFindAllTest() {
		List<Cuenta> cuenta = cuentaRepo.findAll();
		
		assertFalse(cuenta.isEmpty());
		assertEquals(2, cuenta.size());
	}
	
	@Test
	void saveTest() {
		
		//INSERT
		Cuenta cuentaPepe = new Cuenta(null,"Juan",new BigDecimal(3000));
		Cuenta cuenta = cuentaRepo.save(cuentaPepe);
		assertEquals("Juan",cuenta.getPersona());
		assertEquals("3000",cuenta.getSaldo().toPlainString());
		
		
		//UPDATE
		cuenta.setSaldo(new BigDecimal(3800));
		Cuenta cuentaUpdate = cuentaRepo.save(cuenta);
		assertEquals("3800",cuentaUpdate.getSaldo().toPlainString());
		
		
		//DELETE
		cuentaRepo.delete(cuentaUpdate);
		Optional<Cuenta> cuentaDelete = cuentaRepo.findByPersona("Juan");
		assertThrows(NoSuchElementException.class, cuentaDelete::orElseThrow);
		
		
		
	}
	
	
}
