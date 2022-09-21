package br.com.phoebus.library.library.cart;

import br.com.phoebus.library.library.cart.v1.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cart")
@AllArgsConstructor
public class CartController {

    private CartService service;

    @GetMapping
    public List<CartOutDTO> findAllCarts(){
        return service.findAllCarts();
    }

    @GetMapping("/{id}")
    public CartOutDTO findCartById(@PathVariable("id") Integer id){
        return service.findCartById(id);
    }

    @GetMapping("/client/{id}")
    public List<CartOutDTO> findCartsByClientId(@PathVariable("id") Integer id){
        return service.findCartsByClientId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody CartInDTO cart){

        return service.save(cart).getId();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id){
        service.delete(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") Integer id, @RequestBody CartInDTO cart){
        service.update(id,cart);
    }

}
