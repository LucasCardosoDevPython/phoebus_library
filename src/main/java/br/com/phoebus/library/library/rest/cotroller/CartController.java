package br.com.phoebus.library.library.rest.cotroller;

import br.com.phoebus.library.library.domain.entity.Cart;
import br.com.phoebus.library.library.domain.repository.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/cart")
@AllArgsConstructor
public class CartController {

    private CartRepository carts;

    @GetMapping
    public List<Cart> findAllCarts(){

        List<Cart> all = carts.findAll();

        for(Cart k: all){
            k.loadTotal();
        }

        return all;
    }

    @GetMapping("/{id}")
    public Cart findCartById(@PathVariable("id") Integer id){
        Cart cart = carts
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Não foi encontrada nehnuma compra com o id " + id + " na base de dados."
                ));

        cart.loadTotal();

        return cart;
    }

    @GetMapping("/client/{id}")
    public Set<Cart> findCartsByClientId(@PathVariable("id") Integer id){
        return carts.findCartsByClientId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cart save(@RequestBody Cart cart){
        return carts.save(cart);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id){
        carts
                .findById(id)
                .map(cart -> {
                    carts.delete(cart);
                    return cart;
                }).orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Não foi encontrada nenhuma compra com o id "+id+" na base de dados."
                ));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") Integer id, @RequestBody Cart cart){
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
