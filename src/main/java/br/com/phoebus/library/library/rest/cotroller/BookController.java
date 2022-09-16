package br.com.phoebus.library.library.rest.cotroller;

import br.com.phoebus.library.library.domain.entity.Book;
import br.com.phoebus.library.library.domain.entity.Category;
import br.com.phoebus.library.library.domain.repository.BookRepository;
import br.com.phoebus.library.library.domain.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/book")
@AllArgsConstructor
public class BookController {

    private BookRepository books;
    private CategoryRepository categories;

    @GetMapping
    public List<Book> findAllBooks(){
        return books.findAll();
    }

    @GetMapping("/{isbn}")
    public Book findBookById(@PathVariable("isbn") String isbn){
        return books
                .findById(isbn)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Não foi encontrado nehnum livro com o isbn " + isbn + " na base de dados."
                ));
    }

    @GetMapping("/{price}")
    public Set<Book> findByPriceGreaterThenEqual(@PathVariable("price") double price){
        return books.findByPriceGreaterThenEqual(price);
    }

    @GetMapping("/{low}/{high}")
    public Set<Book> findByPriceBetween(@PathVariable("high") double high,@PathVariable("low") double low){
        return books.findByPriceBetween(low, high);
    }

    @GetMapping("/author/{name}")
    public Set<Book> findByAuthorContaining(@PathVariable("name") String name){
        return books.findByAuthorContaining(name);
    }

    @GetMapping("/title/{title}")
    public Set<Book> findByTitleContaining(@PathVariable("title") String title){
        return books.findByTitleContaining(title);
    }

    @GetMapping("/Category/{name}")
    public Set<Book> findByCategory(@PathVariable("name") String categoryName){
        Category category = findCategoryId(categoryName);
        return books.findByCategory(category.getId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book save(@RequestBody Book book){
        return books.save(book);
    }

    @DeleteMapping("/{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("isbn") String isbn){
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

    @PutMapping("/{isbn}/{categories}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addCategory(@PathVariable("isbn") String isbn, @PathVariable("categories") String categories){
        String[] categoriesNames = categories.split("|");
        Category category;
        Book book = this.findBookById(isbn);

        for(int i=0; i< categoriesNames.length; i++){
            category = this.findCategoryId(categoriesNames[i]);
            book.addCategory(category);
        }

        books.save(book);

    }

    @PutMapping("/{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("isbn") String isbn, @RequestBody Book book){
        books
                .findById(isbn)
                .map(existentBook -> {
                    book.setIsbn(existentBook.getIsbn());
                    books.save(book);
                    return existentBook;
                }).orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Não foi encontrado nehnum livro com o isbn " + isbn + " na base de dados."
                ));
    }

    private Category findCategoryId(String name){
        return categories
                .findByName(name)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Não foi encontrada nehnuma categoria " + name + " na base de dados."
                ));
    }

}
