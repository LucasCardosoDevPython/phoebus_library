package br.com.phoebus.library.library.client;

import br.com.phoebus.library.library.cart.Cart;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="client")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Client {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ClientSeq")
    @SequenceGenerator(name = "ClientSeq", sequenceName = "ISEQ$$_76253", allocationSize = 1)
    @JsonIgnore
    private Integer id;
    @Column(length = 50, unique = true)
    private String email;
    @Column(length = 50)
    private String name;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @Column(length = 11)
    private String phone;
    @Column(length = 1)
    private String sex;
    @JsonIgnore
    @OneToMany(mappedBy = "client")
    private List<Cart> carts;

}
