package com.icaballero.app.repositories;

import java.util.List;

import com.icaballero.app.models.Examen;


public interface IExamenRepository {
	
	public List<Examen> findAll();
	
	public Examen save(Examen examen);

}
