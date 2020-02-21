package com.gildocordeiro.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gildocordeiro.cursomc.domain.Categoria;
import com.gildocordeiro.cursomc.domain.Cidade;
import com.gildocordeiro.cursomc.domain.Cliente;
import com.gildocordeiro.cursomc.domain.Endereco;
import com.gildocordeiro.cursomc.domain.Estado;
import com.gildocordeiro.cursomc.domain.Produto;
import com.gildocordeiro.cursomc.domain.enums.TipoCliente;
import com.gildocordeiro.cursomc.repositories.CategoriaRepository;
import com.gildocordeiro.cursomc.repositories.ProdutoRepository;
import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");
		
		Produto p1 = new Produto(null, "Computador", 200.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		
		
		
//		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "70754568490", TipoCliente.PESSOAFISICA);
//		
//		cli1.getTelefones().addAll(Arrays.asList("84991557032","84991044155"));
//		
//		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 301", "Jardim", "59076400", cli1,  )
		
	}

}
