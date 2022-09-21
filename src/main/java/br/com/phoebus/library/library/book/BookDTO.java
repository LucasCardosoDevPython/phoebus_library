package br.com.phoebus.library.library.book;

import br.com.phoebus.library.library.caregory.CategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookDTO {
    private String isbn;
    private String title;
    private String author;
    private String sinopse;
    private Integer year;
    private Double price;
    private Integer stock;
    private List<CategoryDTO> categories;
}
