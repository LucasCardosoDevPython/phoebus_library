package br.com.phoebus.library.library.rest.cotroller;


import br.com.phoebus.library.library.domain.entity.Cart;
import br.com.phoebus.library.library.domain.entity.Client;
import br.com.phoebus.library.library.domain.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(value = "/client")
@AllArgsConstructor
public class ClientController {

    private ClientRepository clients;

    @GetMapping
    public List<Client> listAllClient(){
        return clients.findAll();
    }

    @GetMapping("/{id}")
    public Client getClientById(@PathVariable("id") Integer id){

        return clients
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Não foi encontrado nenhum cliente com o id "+id+" na base de dados."
                ));
    }

    @GetMapping("/email/{email}")
    public Integer getClientId(@PathVariable("email") String email){

        return clients
                .findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Não foi encontrado nenhum cliente com o email "+email+" na base de dados."
                )).getId();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Client save(@RequestBody Client client){
        return clients.save(client);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id){

        clients
                .findById(id)
                .map(client -> {
                     clients.delete(client);
                     return client;
                }).orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Não foi encontrado nenhum cliente com o id "+id+" na base de dados."
                ));

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") Integer id, @RequestBody Client client){

        clients.
                findById(id).
                map(
                    existentClient -> {
                        client.setId(existentClient.getId());
                        clients.save(client);
                        return existentClient;
                }).orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Não foi encontrado nenhum cliente com o id "+id+" na base de dados."
                ));

    }

    @GetMapping("/{id}/carts")
    public Set<Cart> ListClientCarts(@PathVariable("id") Integer id){

        Client client = clients
                .findClientFetchCart(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Não foi encontrado nenhum cliente com o id " + id + " na base de dados."
                ));

        for(Cart k: client.getCarts()){
            k.loadTotal();
        }

        return client.getCarts();

    }

}
