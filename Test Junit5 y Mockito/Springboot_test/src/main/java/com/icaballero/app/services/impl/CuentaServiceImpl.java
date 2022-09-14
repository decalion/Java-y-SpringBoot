package com.icaballero.app.services.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.icaballero.app.models.Banco;
import com.icaballero.app.models.Cuenta;
import com.icaballero.app.repositories.IBancoRepository;
import com.icaballero.app.repositories.ICuentaRepository;
import com.icaballero.app.services.ICuentaService;

@Service
public class CuentaServiceImpl implements ICuentaService {
	
	@Autowired
	private ICuentaRepository cuentaRepository;
	
	@Autowired
	private IBancoRepository bancoRepository;
	

	
	@Override
	@Transactional(readOnly = true)
	public Cuenta findById(Long id) {

		return cuentaRepository.findById(id).orElseThrow();
	}

	@Override
	@Transactional(readOnly = true)
	public Integer revisarTotalTransferencia(Long id) {
		Banco banco = bancoRepository.findById(id).orElseThrow();

		return banco.getTotalTransferencia();
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal revisarSaldo(Long cuentaId) {
		Cuenta cuenta = cuentaRepository.findById(cuentaId).orElseThrow();
		return cuenta.getSaldo();
	}

	@Override
	@Transactional()
	public void transferir(Long numCuentaOrigen, Long numCuentaDestino, BigDecimal monto,Long bancoId) {
		
		
		Cuenta cuentaOrigen = cuentaRepository.findById(numCuentaOrigen).orElseThrow();
		cuentaOrigen.debito(monto);
		cuentaRepository.save(cuentaOrigen);
		
		Cuenta cuentaDestino = cuentaRepository.findById(numCuentaDestino).orElseThrow();
		cuentaDestino.credito(monto);
		cuentaRepository.save(cuentaDestino);
		
		Banco banco = bancoRepository.findById(bancoId).orElseThrow();
		Integer totalTransferencia = banco.getTotalTransferencia();
		banco.setTotalTransferencia(++totalTransferencia);
		bancoRepository.save(banco);
		
	}

	@Override
	public List<Cuenta> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cuenta save(Cuenta cuenta) {
		// TODO Auto-generated method stub
		return null;
	}

}
