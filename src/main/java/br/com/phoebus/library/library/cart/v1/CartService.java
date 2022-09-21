package br.com.phoebus.library.library.cart.v1;

import br.com.phoebus.library.library.cart.Cart;
import br.com.phoebus.library.library.cart.CartInDTO;
import br.com.phoebus.library.library.cart.CartOutDTO;

import java.util.List;

public interface CartService {
    List<CartOutDTO> findAllCarts();
    CartOutDTO findCartById(Integer id);
    List<CartOutDTO> findCartsByClientId(Integer id);
    Cart save(CartInDTO cartDTO);
    void delete(Integer id);
    void update(Integer id, CartInDTO cartDTO);
}
