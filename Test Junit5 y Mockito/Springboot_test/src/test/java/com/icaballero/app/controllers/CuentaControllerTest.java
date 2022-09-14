package com.icaballero.app.controllers;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icaballero.app.Datos;
import com.icaballero.app.models.Cuenta;
import com.icaballero.app.models.TransaccionDTO;
import com.icaballero.app.services.ICuentaService;

@WebMvcTest(CuentaController.class)
class CuentaControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private ICuentaService cuentaService;
	
	
	
	ObjectMapper mapper;
	
	@BeforeEach
	void setUp() {
		mapper = new ObjectMapper();
	}
	
	@Test
	void detalleTest() throws Exception {
		
		when(cuentaService.findById(1L)).thenReturn(Datos.crearCuenta001().orElseThrow());
		
		mvc.perform(MockMvcRequestBuilders.get("/api/cuentas/1").contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.jsonPath("$.persona").value("Ismael"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.saldo").value("1000"));
		
		verify(cuentaService).findById(1L);
		
	}
	
	@Test
	void transferirTest() throws Exception {
		
		TransaccionDTO dto = new TransaccionDTO();
		dto.setCuentaOrigenId(1L);
		dto.setCuentaDestinoId(2L);
		dto.setMonto(new BigDecimal("100"));
		dto.setBancoId(1L);
		
		Map<String,Object> response = new HashMap<>();
		response.put("date", LocalDate.now().toString());
		response.put("status", "OK");
		response.put("mensaje", "Transferencia realizada con exito");
		response.put("transaccion", dto);
		
		
		mvc.perform(MockMvcRequestBuilders.post("/api/cuentas/transferir")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(dto)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.jsonPath("$.mensaje").value("Transferencia realizada con exito"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.transaccion.cuentaOrigenId").value(dto.getCuentaOrigenId()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.date").value(LocalDate.now().toString()))
		.andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(response)));
		
		
	}
	
	@Test
	void findAllTest() throws Exception {
		List<Cuenta> cuentas = Arrays.asList(Datos.crearCuenta001().orElseThrow(),
				Datos.crearCuenta002().orElseThrow());
		
		when(cuentaService.findAll()).thenReturn(cuentas);
		
		mvc.perform(MockMvcRequestBuilders.get("/api/cuentas")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].persona").value("Ismael"))
		.andExpect(MockMvcResultMatchers.jsonPath("$[1].persona").value("Cristian"))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].saldo").value("1000"))
		.andExpect(MockMvcResultMatchers.jsonPath("$[1].saldo").value("2000"))
		.andExpect(MockMvcResultMatchers.jsonPath("$",Matchers.hasSize(2)))
		.andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(cuentas)));
	}
	
	
	@Test
	void saveTest() throws Exception {
		
		Cuenta cuenta = new Cuenta(null, "pepe", new BigDecimal("3000"));
		
		when(cuentaService.save(any())).thenReturn(cuenta);
		
		mvc.perform(MockMvcRequestBuilders.post("/api/cuentas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(cuenta)))
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.jsonPath("$.persona").value("pepe"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.saldo").value("3000"))
		.andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(cuenta)));
	}

}
