package com.icaballero.junitapp.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Properties;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.icaballero.junitapp.exceptions.DineroInsuficienteException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CuentaTest {
	Cuenta cuenta;
	
	@BeforeAll
	static void initTest() {
		log.info("Se ejecuta al inicio de los test");
	}
	
	
	@AfterAll
	static void endTest() {
		log.info("Se ejecuta al final de los test");
	}
	
	
	
	@BeforeEach
	void initMetodTest() {
		log.info("Se ejecuta al inicio de cada metodo");
		this.cuenta = new Cuenta("John", new BigDecimal("1000.12345"));
		
	}

	
	@AfterEach
	void tearDown() {
		log.info("Se ejecuta al termninar cada metodo");
		
	}
	@Test
	@DisplayName("Testing Nombre cuenta")
	void nombreCuentaTest() {
		Cuenta cuenta = new Cuenta();

		String nombre = "Ismael";
		cuenta.setPersona(nombre);

		assertNotNull(cuenta.getPersona());
		assertEquals(nombre, cuenta.getPersona());
		assertTrue(cuenta.getPersona().equals(nombre));

	}

	@Test
	@DisplayName("Testing Saldo cuenta")
	void saldoCuentaTest() {
		Cuenta cuenta = new Cuenta();

		BigDecimal saldo = new BigDecimal("1000.12345");
		cuenta.setSaldo(saldo);

		assertNotNull(cuenta.getSaldo());
		assertEquals(saldo.doubleValue(), cuenta.getSaldo().doubleValue());
		assertTrue(cuenta.getSaldo().equals(saldo));

	}

	@Test
	@DisplayName("Testing Rrferencia cuenta")
	void referenciaCuentaTest() {
		Cuenta cuenta = new Cuenta("John", new BigDecimal("8900.9997"));
		Cuenta cuenta2 = new Cuenta("John", new BigDecimal("8900.9997"));

		// assertNotEquals(cuenta, cuenta2);
		assertEquals(cuenta, cuenta2);

	}

	@Test
	void debitoCuentaTest() {
		cuenta.debito(new BigDecimal(100));

		assertNotNull(cuenta.getSaldo());
		assertEquals(900, cuenta.getSaldo().intValue());
		assertEquals("900.12345", cuenta.getSaldo().toPlainString());

	}
	
	@DisplayName("Repeticion Test")
	@RepeatedTest(value = 5, name = "{displayName} - Repetion numero {currentRepetition} de {totalRepetitions}")
	void debitoCuentaRepetirTest(RepetitionInfo  info) {
		
		if(info.getCurrentRepetition() == 3) {
				log.info("Estamos en la repeticion 3");
		}
		
		cuenta.debito(new BigDecimal(100));

		assertNotNull(cuenta.getSaldo());
		assertEquals(900, cuenta.getSaldo().intValue());
		assertEquals("900.12345", cuenta.getSaldo().toPlainString());

	}

	
	@ParameterizedTest(name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
	@ValueSource(strings = {"100","200","300","500","700","1000"})
	void debitoCuentaParametrizedTest(String monto) {

		cuenta.debito(new BigDecimal(monto));

		assertNotNull(cuenta.getSaldo());
		
		assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);

	}
	
	
	@ParameterizedTest(name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
	@CsvSource({"1,100","2,200","3,300","4,500","5,700","6,1000"})
	void debitoCuentaParametrizedTest2(String index, String monto) {

		log.info(index + " - " + monto);
		cuenta.debito(new BigDecimal(monto));

		assertNotNull(cuenta.getSaldo());
		
		assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);

	}
	
	
	@Test
	void creditoCuentaTest() {

		cuenta.credito(new BigDecimal(100));

		assertNotNull(cuenta.getSaldo());
		assertEquals(1100, cuenta.getSaldo().intValue());
		assertEquals("1100.12345", cuenta.getSaldo().toPlainString());

	}

	@Test
	@Disabled
	void dineroInsuficienteCuenta_exceptionTest() {

		assertThrows(DineroInsuficienteException.class, () -> {

			cuenta.debito(new BigDecimal(1500));
		});

	}

	@Test
	void transferirDineroCuentaTest() {
		Cuenta cuenta = new Cuenta("John", new BigDecimal("2500"));
		Cuenta cuenta2 = new Cuenta("Pepe", new BigDecimal("1500.8989"));

		Banco banco = new Banco();
		banco.setNombre("BBVA");
		banco.transferir(cuenta2, cuenta, new BigDecimal(500));

		assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
		assertEquals("3000", cuenta.getSaldo().toPlainString());

	}

	@Test
	void relacionBancoCuentasTest() {
		String nBanco = "BBVA";
		String john = "John";
		Cuenta cuenta = new Cuenta(john, new BigDecimal("2500"));
		Cuenta cuenta2 = new Cuenta("Pepe", new BigDecimal("1500.8989"));

		Banco banco = new Banco();
		banco.addCuenta(cuenta);
		banco.addCuenta(cuenta2);
		banco.setNombre(nBanco);
		banco.transferir(cuenta2, cuenta, new BigDecimal(500));

		assertAll(() -> assertEquals(2, banco.getCuentas().size()),
				() -> assertEquals(nBanco, cuenta.getBanco().getNombre()),
				() -> assertEquals(john,
						banco.getCuentas().stream().filter(c -> c.getPersona().equals(john)).findFirst().get()
								.getPersona()),
				() -> assertTrue(
						banco.getCuentas().stream().filter(c -> c.getPersona().equals(john)).findFirst().isPresent()),
				() -> assertTrue(banco.getCuentas().stream().anyMatch(c -> c.getPersona().equals(john))));

	}
	
	
	@Nested
	class SistemasOperativosTest {
		@Test
		@EnabledOnOs(OS.WINDOWS)
		void testSoloWindows() {
			//SOLO WINDOWS
		}

		@Test
		@EnabledOnOs({OS.LINUX, OS.MAC})
		void testSoloLinuxMac() {
			//SOLO LINUX Y MAC
		}
		
		
		@Test
		@DisabledOnOs(OS.WINDOWS)
		void testNoWindows() {
			//NO EN WINDOWS
		}
	}
	
	@Nested
	class JavaVersionTest {
		
		@Test
		@EnabledOnJre(JRE.JAVA_8)
		void testSoloJdk8() {
			//SOLO PARA JDK8
		}
		
		
		@Test
		@EnabledOnJre(JRE.JAVA_11)
		void testSoloJdk11() {
			//SOLO PARA JDK11
		}
	}
	
    @Nested
	class SistemPropertiesTest {
		@Test
		void getProperties() {
			Properties properties = System.getProperties();
			properties.forEach( (k,v) -> log.info(k + " : " +v) );
		}
		
		@Test
		@EnabledIfSystemProperty(named = "java.version" , matches = "14.0.2")
		void testJavaVersion() {
			
		}
		
	}

	

	
	@Test
	void getEnviorement() {
		Map<String, String> env= System.getenv();
		env.forEach( (k,v) -> log.info(k + " = " +v) );
	}
	
	@Test
	@DisplayName("Testing Saldo cuenta DEV")
	void saldoCuentaDevTest() {
		boolean esDev = "DEV".equals(System.getProperty("ENV"));
		//Deshabilita la prueba
		assumeTrue(esDev);
		
		Cuenta cuenta = new Cuenta();

		BigDecimal saldo = new BigDecimal("1000.12345");
		cuenta.setSaldo(saldo);

		assertNotNull(cuenta.getSaldo());
		assertEquals(saldo.doubleValue(), cuenta.getSaldo().doubleValue());
		assertTrue(cuenta.getSaldo().equals(saldo));

	}
}
