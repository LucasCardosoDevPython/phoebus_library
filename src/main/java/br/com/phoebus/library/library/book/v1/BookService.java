package br.com.phoebus.library.library.book.v1;

import br.com.phoebus.library.library.book.Book;
import br.com.phoebus.library.library.book.BookDTO;
import br.com.phoebus.library.library.caregory.CategoryDTO;

import java.util.List;

interface BookService {
    List<BookDTO> findAllBooks();
    BookDTO findBookById(String isbn);
    List<BookDTO> findByPriceGreaterThenEqual(double price);
    List<BookDTO> findByPriceBetween(double high, double low);
    List<BookDTO> findByAuthorContaining(String name);
    List<BookDTO> findByTitleContaining(String title);
    List<BookDTO> findByCategory(String categoryName);
    Book save(BookDTO book);
    void delete(String isbn);
    void addCategory(String isbn, List<CategoryDTO> categories);
    void update(String isbn, BookDTO book);
}
