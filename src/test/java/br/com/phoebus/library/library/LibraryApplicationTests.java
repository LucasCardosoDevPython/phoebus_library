package br.com.phoebus.library.library;

import br.com.phoebus.library.library.domain.entity.*;
import br.com.phoebus.library.library.domain.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class LibraryApplicationTests {

	@Autowired
	ClientRepository repositorioCliente;
	@Autowired
	BookRepository repositorioLivros;
	@Autowired
	CartRepository repositorioCompras;
	@Autowired
	CartItemRepository repositorioItens;
	@Autowired
	CategoryRepository repositorioCategorias;


	@Test
	void clientTest() {
		Client cliente = Client.builder()
				.email("jao@gmail.com")
				.name("Jão")
				.phone("99999999999")
				.sex("M")
				.birthDate(LocalDate.parse("1999-05-15"))
				.build();

		Client clienteCadastrado = repositorioCliente.save(cliente);
		Optional<Client> clientePesquisado = repositorioCliente.findById(clienteCadastrado.getId());

		if(clientePesquisado.isEmpty()){
			fail("Cliente não encontrado quando deveria ter sido cadastrado");
		}else{
			assertEquals(clienteCadastrado.getEmail(),clientePesquisado.get().getEmail());
			assertEquals(clienteCadastrado.getName(),clientePesquisado.get().getName());
			assertEquals(clienteCadastrado.getPhone(),clientePesquisado.get().getPhone());
			assertEquals(clienteCadastrado.getSex(),clientePesquisado.get().getSex());
			assertEquals(clienteCadastrado.getBirthDate(),clientePesquisado.get().getBirthDate());
		}

		cliente.setSex("F");

		Client clienteAtualizado = repositorioCliente.save(cliente);

		Optional<Client> clientePesquisado2 = repositorioCliente.findById(clienteCadastrado.getId());

		if(clientePesquisado2.isEmpty()){
			fail("Cliente não encontrado quando deveria ter sido cadastrado");
		}else{
			assertEquals(clienteAtualizado.getEmail(),clientePesquisado2.get().getEmail());
			assertEquals(clienteAtualizado.getName(),clientePesquisado2.get().getName());
			assertEquals(clienteAtualizado.getPhone(),clientePesquisado2.get().getPhone());
			assertEquals("F",clientePesquisado2.get().getSex());
			assertEquals(clienteAtualizado.getBirthDate(),clientePesquisado2.get().getBirthDate());
		}

		repositorioCliente.delete(repositorioCliente.findById(clienteAtualizado.getId()).get());

		if(!repositorioCliente.findById(clienteAtualizado.getId()).isEmpty()){
			fail("O cliente deveria ter sido deletado.");
		}

	}

	@Test
	void bookTest(){
		Book livro = Book.builder() // 'Nata Mateus', 2002, 783.25, 1351
				.isbn("9584991804130")
				.author("Nata Mateus")
				.price(783.25)
				.stock(1351)
				.title("Funeral Parade of Roses")
				.year(2002)
				.build();

		Book livroCadastrado = repositorioLivros.save(livro);
		Optional<Book> livroPesquisado = repositorioLivros.findById(livroCadastrado.getIsbn());

		if(livroPesquisado.isEmpty()){
			fail("Libro não encontrado quando deveria ter sido cadastrado");
		}else{
			assertEquals(livroCadastrado.getIsbn(),livroPesquisado.get().getIsbn());
			assertEquals(livroCadastrado.getAuthor(),livroPesquisado.get().getAuthor());
			assertEquals(livroCadastrado.getPrice(),livroPesquisado.get().getPrice());
			assertEquals(livroCadastrado.getStock(),livroPesquisado.get().getStock());
			assertEquals(livroCadastrado.getTitle(),livroPesquisado.get().getTitle());
			assertEquals(livroCadastrado.getYear(),livroPesquisado.get().getYear());
		}

		livro.setSinopse("Um Bom Libro.");

		Book livroAtualizado = repositorioLivros.save(livro);
		Optional<Book> livroPesquisado2 = repositorioLivros.findById(livroCadastrado.getIsbn());

		if(livroPesquisado2.isEmpty()){
			fail("Livro não encontrado quando deveria ter sido cadastrado");
		}else{
			assertEquals(livroAtualizado.getIsbn(),livroPesquisado2.get().getIsbn());
			assertEquals(livroAtualizado.getAuthor(),livroPesquisado2.get().getAuthor());
			assertEquals(livroAtualizado.getPrice(),livroPesquisado2.get().getPrice());
			assertEquals(livroAtualizado.getStock(),livroPesquisado2.get().getStock());
			assertEquals(livroAtualizado.getTitle(),livroPesquisado2.get().getTitle());
			assertEquals(livroAtualizado.getYear(),livroPesquisado2.get().getYear());
			assertEquals("Um Bom Libro.",livroPesquisado2.get().getSinopse());
		}

		repositorioLivros.delete(repositorioLivros.findById(livroAtualizado.getIsbn()).get());

		if(!repositorioLivros.findById(livroAtualizado.getIsbn()).isEmpty()){
			fail("O Livro deveria ter sido deletado.");
		}
	}

	@Test
	void allTest(){
		
		Book[] books = new Book[3];

		Client client;

		Cart[] carts = new Cart[3];

		LinkedList<CartItem> items;


	}

}
