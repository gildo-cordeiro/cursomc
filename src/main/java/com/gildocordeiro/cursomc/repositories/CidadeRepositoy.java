package com.gildocordeiro.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gildocordeiro.cursomc.domain.Cidade;

public interface CidadeRepositoy extends JpaRepository<Cidade, Integer>{
	
}
