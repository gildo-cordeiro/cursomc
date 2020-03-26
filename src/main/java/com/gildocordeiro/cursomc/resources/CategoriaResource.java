package com.gildocordeiro.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.gildocordeiro.cursomc.domain.Categoria;
import com.gildocordeiro.cursomc.dto.CategoriaDTO;
import com.gildocordeiro.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id){
		Categoria categoria = service.find(id);	
		return ResponseEntity.ok().body(categoria);
	}	
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> save(@Valid @RequestBody CategoriaDTO objDTO){
		Categoria obj = service.fromDTO(objDTO);
		obj = service.save(obj);
		//Objeto que ira receber a nova URI gerada a patir da inserção da nova categoria
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO objDto, @PathVariable Integer id){
		Categoria obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();	
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll(){
		List<Categoria> categoria = service.findAll();
		//Stream foi adcionado no Java 8 para trabalhar em conjunto com filter, map, sum etc. Ele facilita a vida de desenvolvedores 
		//no momento  de mexer com listas.<Estudar mais sobre isso depois>
		
		//Essa função foi criada para retornar apenas os nomes das categorias com seus respectivos id's (mapeados pela CategoriaDTO)
		List<CategoriaDTO> listDTO = (List<CategoriaDTO>) categoria.stream().map(
				obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	/**
	 * 
	 * @param page
	 * @param linePerPage
	 * @param orderBy
	 * @param direction
	 * @return
	 */
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findAllByPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "linePerPage", defaultValue = "24") Integer linePerPage, 
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC")String direction){
		Page<Categoria> categorias = service.findPage(page, linePerPage, orderBy, direction);
		
		//Por o Page ser um objeto do Java 8 não é necessario utilizar o stream e o collect;
		Page<CategoriaDTO> listDTO = categorias.map(obj -> new CategoriaDTO(obj));
		
		return ResponseEntity.ok().body(listDTO);
		
	}
	
}
