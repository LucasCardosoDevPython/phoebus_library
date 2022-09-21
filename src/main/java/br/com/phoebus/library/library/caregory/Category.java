package br.com.phoebus.library.library.caregory;

import br.com.phoebus.library.library.book.Book;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="category")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Category {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "CategorySeq")
    @SequenceGenerator(name = "CaregorySeq", sequenceName = "ISEQ$$_76263", allocationSize = 1)
    private Integer id;
    @Column(length = 20)
    private String name;

}
