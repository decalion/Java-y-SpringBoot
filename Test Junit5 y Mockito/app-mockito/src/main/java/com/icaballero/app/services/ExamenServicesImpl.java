package com.icaballero.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.icaballero.app.models.Examen;
import com.icaballero.app.repositories.IExamenRepository;
import com.icaballero.app.repositories.IPreguntasRepository;

@Service
public class ExamenServicesImpl implements IExamenService {

	@Autowired
	private IExamenRepository examenRepo;
	
	@Autowired
	@Qualifier("preguntasRepositoryImpl")
	private IPreguntasRepository preguntasRepo;

	@Override
	public Optional<Examen> findExamenPorNombre(String nombre) {

		return examenRepo.findAll().stream().
				filter(e -> e.getNombre().equals(nombre)).
				findFirst();
	}

	@Override
	public Examen findExamenByNameWithPreguntas(String nombre) {
		Optional<Examen> examenOptional = findExamenPorNombre(nombre);
		Examen examen = null;
		if(examenOptional.isPresent()) {
			examen = examenOptional.get();
			List<String> preguntas = preguntasRepo.findPreguntasByExamenId(examen.getId());
			examen.setPreguntas(preguntas);
		}
		
		
		return examen;
	}

	@Override
	public Examen save(Examen examen) {
		if(!examen.getPreguntas().isEmpty()) {
			
			preguntasRepo.save(examen.getPreguntas());
		}
		
		
		return examenRepo.save(examen);
	}

}
