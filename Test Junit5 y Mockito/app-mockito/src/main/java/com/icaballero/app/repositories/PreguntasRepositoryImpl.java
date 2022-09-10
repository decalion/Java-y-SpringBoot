package com.icaballero.app.repositories;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("preguntasRepositoryImpl")
public class PreguntasRepositoryImpl implements IPreguntasRepository {

	@Override
	public List<String> findPreguntasByExamenId(Long id) {
	
		return Arrays.asList("Pregunta1","Pregunta2","Pregunta3","Pregunta4");
	}

	@Override
	public void save(List<String> preguntas) {
		
		
	}

}
