package com.icaballero.app.controllers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.icaballero.app.models.Cuenta;
import com.icaballero.app.models.TransaccionDTO;
import com.icaballero.app.services.ICuentaService;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {
	
	
	@Autowired
	private ICuentaService cuentaService;
	
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Cuenta> findAll() {
		
		return cuentaService.findAll();
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Cuenta detalle(@PathVariable Long id) {
		
		return cuentaService.findById(id);
		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cuenta save(@RequestBody Cuenta cuenta) {
		
		return cuentaService.save(cuenta);
	}
	
	@PostMapping("/transferir")
	public ResponseEntity<?> transferir(@RequestBody TransaccionDTO dto) {
		
		cuentaService.transferir(dto.getCuentaOrigenId(), dto.getCuentaDestinoId(), dto.getMonto(), dto.getBancoId());
		
		Map<String,Object> response = new HashMap<>();
		response.put("date", LocalDate.now().toString());
		response.put("status", "OK");
		response.put("mensaje", "Transferencia realizada con exito");
		response.put("transaccion", dto);
		
		
		return ResponseEntity.ok(response);
	}
	

}
