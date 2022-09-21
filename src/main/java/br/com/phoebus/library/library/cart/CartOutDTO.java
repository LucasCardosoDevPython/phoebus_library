package br.com.phoebus.library.library.cart;

import br.com.phoebus.library.library.cartItem.CartItemDTO;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CartOutDTO{
    private Integer clientId;
    private boolean done;
    private LocalDate tranDate;
    private List<CartItemDTO> items;
    private double total;
}
