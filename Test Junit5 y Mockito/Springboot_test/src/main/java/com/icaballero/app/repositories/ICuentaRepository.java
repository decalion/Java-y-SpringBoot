package com.icaballero.app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.icaballero.app.models.Cuenta;


public interface ICuentaRepository extends JpaRepository<Cuenta, Long> {
	
	@Query("select c from Cuenta c where c.persona=?1")
	Optional<Cuenta> findByPersona(String persona);

}
