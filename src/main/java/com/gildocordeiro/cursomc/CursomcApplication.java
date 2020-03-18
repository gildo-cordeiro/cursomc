package com.gildocordeiro.cursomc;

import java.text.SimpleDateFormat;
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
import com.gildocordeiro.cursomc.domain.ItemPedido;
import com.gildocordeiro.cursomc.domain.Pagamento;
import com.gildocordeiro.cursomc.domain.PagamentoComBoleto;
import com.gildocordeiro.cursomc.domain.PagamentoComCartão;
import com.gildocordeiro.cursomc.domain.Pedido;
import com.gildocordeiro.cursomc.domain.Produto;
import com.gildocordeiro.cursomc.domain.enums.EstadoPagamento;
import com.gildocordeiro.cursomc.domain.enums.TipoCliente;
import com.gildocordeiro.cursomc.repositories.CategoriaRepository;
import com.gildocordeiro.cursomc.repositories.CidadeRepositoy;
import com.gildocordeiro.cursomc.repositories.ClienteRepository;
import com.gildocordeiro.cursomc.repositories.EnderecoRepository;
import com.gildocordeiro.cursomc.repositories.EstadoRepository;
import com.gildocordeiro.cursomc.repositories.ItemPedidoRepository;
import com.gildocordeiro.cursomc.repositories.PedidoRepository;
import com.gildocordeiro.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CidadeRepositoy cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
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
		
		estadoRepository.saveAll(Arrays.asList(est1,est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "70754568490", TipoCliente.PESSOAFISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("84991557032","84991044155"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 301", "Jardim", "59076400", cli1, c1);
		
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1,e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
		
		Pagamento pagTo1 = new PagamentoComCartão(null, EstadoPagamento.QUITADADO, ped1, 6);
		ped1.setPagamento(pagTo1);
		
		Pagamento pagTo2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagTo2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
		
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.0, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.0, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1,ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p2.getItens().addAll(Arrays.asList(ip2));	
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
		
	}

}
