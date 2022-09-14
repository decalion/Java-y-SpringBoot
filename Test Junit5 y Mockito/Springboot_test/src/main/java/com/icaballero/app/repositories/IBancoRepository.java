package com.icaballero.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.icaballero.app.models.Banco;


public interface IBancoRepository extends JpaRepository<Banco, Long> {
	


}
