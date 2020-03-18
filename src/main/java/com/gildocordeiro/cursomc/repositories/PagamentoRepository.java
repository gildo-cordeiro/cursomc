package com.gildocordeiro.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gildocordeiro.cursomc.domain.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer>{

}
