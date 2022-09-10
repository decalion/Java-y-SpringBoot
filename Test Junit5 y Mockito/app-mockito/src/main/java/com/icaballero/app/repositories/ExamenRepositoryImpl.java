package com.icaballero.app.repositories;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.icaballero.app.models.Examen;

@Repository
public class ExamenRepositoryImpl implements IExamenRepository {
	
	

	@Override
	public List<Examen> findAll() {
		
		return Arrays.asList(new Examen(5L,"Matematicas"),new Examen(6L,"Lenguaje") ,
			new Examen(7L,"Historia"));
	}

	@Override
	public Examen save(Examen examen) {
		
		return examen;
	}

}
