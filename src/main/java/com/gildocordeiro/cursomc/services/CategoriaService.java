package com.gildocordeiro.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gildocordeiro.cursomc.domain.Categoria;
import com.gildocordeiro.cursomc.repositories.CategoriaRepository;

import com.gildocordeiro.cursomc.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;

	public Categoria buscar(Integer id) {
		Optional<Categoria> categoria = repository.findById(id);
		return categoria.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ". Tipo: " + Categoria.class.getName()));
	}
}
