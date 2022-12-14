package br.com.phoebus.library.library.cartItem;

import br.com.phoebus.library.library.book.Book;
import br.com.phoebus.library.library.cart.Cart;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="cart_item")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CartItem {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "KItemSeq")
    @SequenceGenerator(name = "KItemSeq", sequenceName = "ISEQ$$_76260", allocationSize = 1)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private Book book;
    @Column
    private Integer quantity;

    public double getTotal(){
        return book.getPrice()*quantity;
    }

}
