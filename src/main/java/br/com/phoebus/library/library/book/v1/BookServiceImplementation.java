package br.com.phoebus.library.library.book.v1;

import br.com.phoebus.library.library.book.Book;
import br.com.phoebus.library.library.book.BookDTO;
import br.com.phoebus.library.library.book.BookRepository;
import br.com.phoebus.library.library.caregory.Category;
import br.com.phoebus.library.library.caregory.CategoryDTO;
import br.com.phoebus.library.library.caregory.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImplementation implements BookService{

    private BookRepository books;
    private CategoryRepository categories;

    private Category findCategoryByName(String name){
        return categories
                .findByName(name)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Não foi encontrada nehnuma categoria " + name + " na base de dados."
                ));
    }

    private CategoryDTO cat2DTO(Category category){
        return CategoryDTO.builder()
                .name(category.getName())
                .build();
    }

    private Category DTO2Cat(CategoryDTO dto){
        return this.findCategoryByName(dto.getName());
    }

    private Book DTO2Book(BookDTO dto){
        LinkedList<Category> categories = new LinkedList<Category>();

        for(CategoryDTO c: dto.getCategories()){
            categories.add(this.DTO2Cat(c));
        }

        return Book.builder()
                .isbn(dto.getIsbn())
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .year(dto.getYear())
                .sinopse(dto.getSinopse())
                .categories(categories)
                .price(dto.getPrice())
                .stock(dto.getStock())
                .build();
    }

    private BookDTO book2DTO(Book book){
        LinkedList<CategoryDTO> categories = new LinkedList<CategoryDTO>();

        for(Category c: book.getCategories()){
            categories.add(this.cat2DTO(c));
        }

        return BookDTO.builder()
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .author(book.getAuthor())
                .year(book.getYear())
                .sinopse(book.getSinopse())
                .categories(categories)
                .price(book.getPrice())
                .stock(book.getStock())
                .build();
    }

    private List<BookDTO> books2DTOs(Collection<Book> bookList){
        LinkedList<BookDTO> all = new LinkedList<BookDTO>();

        for(Book b: bookList){
            all.add(this.book2DTO(b));
        }

        return all;
    }

    @Override
    @Transactional
    public List<BookDTO> findAllBooks() {
        return this.books2DTOs(books.findAll());
    }

    @Override
    @Transactional
    public BookDTO findBookById(String isbn) {
        return book2DTO(books.findById(isbn)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Não foi encontrado nehnum livro com o isbn " + isbn + " na base de dados.")
                )
        );
    }

    @Override
    @Transactional
    public List<BookDTO> findByPriceGreaterThenEqual(double price) {
        return this.books2DTOs(books.findByPriceGreaterThenEqual(price));
    }

    @Override
    @Transactional
    public List<BookDTO> findByPriceBetween(double high, double low) {
        return this.books2DTOs(books.findByPriceBetween(high, low));
    }

    @Override
    @Transactional
    public List<BookDTO> findByAuthorContaining(String name) {
        return this.books2DTOs(books.findByAuthorContaining(name));
    }

    @Override
    @Transactional
    public List<BookDTO> findByTitleContaining(String title) {
        return this.books2DTOs(books.findByTitleContaining(title));
    }

    @Override
    @Transactional
    public List<BookDTO> findByCategory(String categoryName) {
        return this.books2DTOs(
                books.findByCategory(this.findCategoryByName(categoryName).getId())
        );
    }

    @Override
    @Transactional
    public Book save(BookDTO book) {
        return books.save(this.DTO2Book(book));
    }

    @Override
    @Transactional
    public void delete(String isbn) {
        books
                .findById(isbn)
                .map(book -> {
                    books.delete(book);
                    return book;
                }).orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Não foi encontrado nehnum livro com o isbn " + isbn + " na base de dados."
                ));
    }

    @Override
    @Transactional
    public void addCategory(String isbn, List<CategoryDTO> categories) {
        Book book = books.findById(isbn)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Não foi encontrado nehnum livro com o isbn " + isbn + " na base de dados.")
                );

        for(CategoryDTO c: categories){
            if(!book.getCategories().contains(c)){
                book.addCategory(this.DTO2Cat(c));
            }
        }

        books.save(book);
    }

    @Override
    @Transactional
    public void update(String isbn, BookDTO book) {
        books
                .findById(isbn)
                .map(existentBook -> {
                    book.setIsbn(existentBook.getIsbn());
                    books.save(this.DTO2Book(book));
                    return existentBook;
                }).orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Não foi encontrado nehnum livro com o isbn " + isbn + " na base de dados."
                ));
    }
}
