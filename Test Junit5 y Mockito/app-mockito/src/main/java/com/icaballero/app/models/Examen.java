package com.icaballero.app.models;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Examen {
	
	private Long id;
	private String nombre;
	private List<String> preguntas = new ArrayList<>();
	public Examen(Long id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}
	
	
	
	

}
