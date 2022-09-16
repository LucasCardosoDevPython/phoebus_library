package br.com.phoebus.library.library.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name="cart")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Cart {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "CartSeq")
    @SequenceGenerator(name = "CartSeq", sequenceName = "ISEQ$$_75716", allocationSize = 1)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @Column(name = "trans_date")
    private LocalDate tranDate;
    @Column
    private Integer done;
    @OneToMany(mappedBy = "cart",fetch = FetchType.EAGER)
    private Set<CartItem> cartItems;
    @Column
    private double total;

    public void loadTotal(){
        total = getTotal();
    }

    public double getTotal(){
        if (cartItems == null){
            return 0;
        }else{
            double total = 0;
            for(CartItem item: cartItems){
                total += item.getTotal();
            }
            return total;
        }
    }

}
