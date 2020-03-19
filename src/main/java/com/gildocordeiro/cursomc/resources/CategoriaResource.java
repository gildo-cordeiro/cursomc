package com.gildocordeiro.cursomc.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.gildocordeiro.cursomc.domain.Categoria;
import com.gildocordeiro.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService servico;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id){
		Categoria categoria = servico.buscar(id);	
		return ResponseEntity.ok().body(categoria);
	}	
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> save(@RequestBody Categoria obj){
		obj = servico.save(obj);
		//Objeto que ira receber a nova URI gerada a patir da inserção da nova categoria
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
}
