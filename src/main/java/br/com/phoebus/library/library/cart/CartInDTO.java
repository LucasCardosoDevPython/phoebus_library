package br.com.phoebus.library.library.cart;

import br.com.phoebus.library.library.cartItem.CartItemDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CartInDTO {
    private Integer clientId;
    private boolean done;
    private List<CartItemDTO> items;
}
