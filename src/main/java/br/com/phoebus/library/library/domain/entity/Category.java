package br.com.phoebus.library.library.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="cart_item")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Category {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "CategorySeq")
    @SequenceGenerator(name = "CaregorySeq", sequenceName = "ISEQ$$_75722", allocationSize = 1)
    private Integer id;
    @Column(length = 20)
    private String name;

}
