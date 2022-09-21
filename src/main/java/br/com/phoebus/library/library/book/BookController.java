package br.com.phoebus.library.library.book;

import br.com.phoebus.library.library.book.v1.BookServiceImplementation;
import br.com.phoebus.library.library.caregory.CategoryDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/book")
@AllArgsConstructor
public class BookController {

    private BookServiceImplementation service;

    @GetMapping
    public List<BookDTO> findAllBooks(){
        return service.findAllBooks();
    }

    @GetMapping("/{isbn}")
    public BookDTO findBookById(@PathVariable("isbn") String isbn){
        return service.findBookById(isbn);
    }

    @GetMapping("/price/{price}")
    public List<BookDTO> findByPriceGreaterThenEqual(@PathVariable("price") double price){
        return service.findByPriceGreaterThenEqual(price);
    }

    @GetMapping("/price/{low}/{high}")
    public List<BookDTO> findByPriceBetween(@PathVariable("high") double high, @PathVariable("low") double low){
        return service.findByPriceBetween(high, low);
    }

    @GetMapping("/author/{name}")
    public List<BookDTO> findByAuthorContaining(@PathVariable("name") String name){
        return service.findByAuthorContaining(name);
    }

    @GetMapping("/title/{title}")
    public List<BookDTO> findByTitleContaining(@PathVariable("title") String title){
        return service.findByTitleContaining(title);
    }

    @GetMapping("/Category/{name}")
    public List<BookDTO> findByCategory(@PathVariable("name") String categoryName){
        return service.findByCategory(categoryName);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String save(@RequestBody BookDTO book){
        return service.save(book).getIsbn();
    }

    @DeleteMapping("/{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("isbn") String isbn){
        service.delete(isbn);
    }

    @PutMapping("/categories/{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addCategory(@PathVariable("isbn") String isbn, @RequestBody List<CategoryDTO> categories){
        service.addCategory(isbn, categories);
    }

    @PutMapping("/{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("isbn") String isbn, @RequestBody BookDTO book){
        service.update(isbn, book);
    }

}
