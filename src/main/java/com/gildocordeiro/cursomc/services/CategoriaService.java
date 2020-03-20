package com.gildocordeiro.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.gildocordeiro.cursomc.domain.Categoria;
import com.gildocordeiro.cursomc.repositories.CategoriaRepository;
import com.gildocordeiro.cursomc.services.exception.DataIntegrityException;
import com.gildocordeiro.cursomc.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;

	public Categoria find(Integer id) {
		Optional<Categoria> categoria = repository.findById(id);
		return categoria.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ". Tipo: " + Categoria.class.getName()));
	}
	
	public Categoria save(Categoria categoria) {
		categoria.setId(null);
		return repository.save(categoria);
	}
	
	public Categoria update(Categoria categoria) {
		find(categoria.getId());
		return repository.save(categoria);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repository.deleteById(id);
		}catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
	}
	
	public List<Categoria> findAll(){
		return repository.findAll();
	}
	
	/**
	 * finPage: metodo do Spring que controle a quantidade de dados que serão pegos no banco de dados. Dessa forma impedira trafego na memoria
	 * A Page encapsula informações e operções sobre a categoria 
	 * 
	 * @param page: qual pagina eu quero;
	 * @param linePerPage: linhas por pagina
	 * @param orderBy: por qual atributo ordenar
	 * @param direction: qual direção ordenar(ascendente ou descendente)
	 * @return
	 */
	public Page<Categoria> findPage(Integer page, Integer linePerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linePerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}
}
