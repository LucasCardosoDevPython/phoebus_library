package br.com.phoebus.library.library.cart.v1;

import br.com.phoebus.library.library.book.BookRepository;
import br.com.phoebus.library.library.cart.Cart;
import br.com.phoebus.library.library.cart.CartInDTO;
import br.com.phoebus.library.library.cart.CartOutDTO;
import br.com.phoebus.library.library.cart.CartRepository;
import br.com.phoebus.library.library.cartItem.CartItem;
import br.com.phoebus.library.library.cartItem.CartItemDTO;
import br.com.phoebus.library.library.cartItem.CartItemRepository;
import br.com.phoebus.library.library.client.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class CartServiceImplementation implements CartService{

    private CartRepository carts;
    private CartItemRepository items;
    private ClientRepository clients;
    private BookRepository books;

    public static CartOutDTO cart2OutDTO(Cart cart){
        cart.loadTotal();
        LinkedList<CartItemDTO> items;
        boolean done;
        if(cart.getDone() == 0){ done = false;}
        else{ done = true;}
        items = new LinkedList<CartItemDTO>();
        for(CartItem i: cart.getCartItems()){
            items.add(item2DTO(i));
        }
        return new CartOutDTO(
                cart.getClient().getId(),
                done,
                cart.getTranDate(),
                items,
                cart.getTotal()
        );
    }

    private Cart inDTO2Cart(CartInDTO inDTO){
        Integer done;
        if(inDTO.isDone()){
            done = 1;
        }else{
            done = 0;
        }
        Cart cart = Cart.builder()
                .client(clients.findById(inDTO.getClientId())
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Não foi encontrada nenhum cliente com o id "+inDTO.getClientId()+" na base de dados."))
                )
                .done(done)
                .tranDate(LocalDate.now())
                .cartItems(new LinkedList<CartItem>())
                .build();
        for(CartItemDTO i: inDTO.getItems()){
            cart.getCartItems().add(this.DTO2item(i, cart));
        };
        return cart;
    }

    public static CartItemDTO item2DTO(CartItem item){
        return new CartItemDTO(
                item.getBook().getIsbn(),
                item.getQuantity()
        );
    }

    private CartItem DTO2item(CartItemDTO itemDTO, Cart cart){
        return CartItem.builder()
                .book(books.findById(itemDTO.getBookId())
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Não foi encontrada nenhum libro com o id "+itemDTO.getBookId()+" na base de dados."))
                )
                .quantity(itemDTO.getQuantity())
                .cart(cart)
                .build();
    }

    @Override
    @Transactional
    public List<CartOutDTO> findAllCarts() {

        List<Cart> all = carts.findAll();

        LinkedList<CartOutDTO> allOut = new LinkedList<CartOutDTO>();

        for(Cart k: all){
            allOut.add(this.cart2OutDTO(k));
        }

        return allOut;
    }

    @Override
    @Transactional
    public CartOutDTO findCartById(Integer id) {
        Cart cart = carts
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Não foi encontrada nenhuma compra com o id " + id + " na base de dados."
                ));

        return this.cart2OutDTO(cart);
    }

    @Override
    @Transactional
    public List<CartOutDTO> findCartsByClientId(Integer id) {

        LinkedList<CartOutDTO> allOut = new LinkedList<CartOutDTO>();

        for(Cart k: carts.findCartsByClientId(id)){
            allOut.add(this.cart2OutDTO(k));
        }

        return allOut;
    }

    @Override
    @Transactional
    public Cart save(CartInDTO cartDTO) {
        Cart cart = this.inDTO2Cart(cartDTO);
        cart = carts.save(cart);
        for(CartItem i: cart.getCartItems()){
            items.save(i);
        }
        return cart;
    }

    @Override
    @Transactional
    public void delete(Integer id) {

        Cart cart = carts
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Não foi encontrada nenhuma compra com o id " + id + " na base de dados."
                ));
        for(CartItem i: cart.getCartItems()){
            items.delete(i);
        }
        carts.delete(cart);
    }

    @Override
    @Transactional
    public void update(Integer id, CartInDTO cartDTO) {
        Cart cart = this.inDTO2Cart(cartDTO);
        carts
                .findById(id)
                .map(existentCart -> {
                    cart.setId(existentCart.getId());
                    carts.save(cart);
                    return existentCart;
                }).orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Não foi encontrada nenhuma compra com o id "+id+" na base de dados."
                ));
    }
}
