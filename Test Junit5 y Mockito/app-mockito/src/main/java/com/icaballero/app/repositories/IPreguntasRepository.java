package com.icaballero.app.repositories;

import java.util.List;

public interface IPreguntasRepository {
	
	List<String> findPreguntasByExamenId(Long id);
	
	void save(List<String> preguntas);

}
