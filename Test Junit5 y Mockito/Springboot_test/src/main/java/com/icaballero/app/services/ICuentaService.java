package com.icaballero.app.services;

import java.math.BigDecimal;
import java.util.List;

import com.icaballero.app.models.Cuenta;

public interface ICuentaService {
	
	public Cuenta findById(Long id);
	public Integer revisarTotalTransferencia(Long id);
	public BigDecimal revisarSaldo(Long cuentaId);
	public void transferir(Long numCuentaOrigen,Long numCuentaDestino,BigDecimal monto,Long bancoId);
	public List<Cuenta> findAll();
	public Cuenta save(Cuenta cuenta);
}
