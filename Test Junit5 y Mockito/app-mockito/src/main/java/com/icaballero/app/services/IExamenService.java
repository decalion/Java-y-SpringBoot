package com.icaballero.app.services;

import java.util.Optional;

import com.icaballero.app.models.Examen;


public interface IExamenService {
	
	
	public Optional<Examen> findExamenPorNombre(String nombre);
	
	public Examen findExamenByNameWithPreguntas(String nombre);
	
	public Examen save(Examen examen);

}
