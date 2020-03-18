package com.gildocordeiro.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gildocordeiro.cursomc.domain.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer>{

}
