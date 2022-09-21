package br.com.phoebus.library.library.client.v1;

import br.com.phoebus.library.library.cart.Cart;
import br.com.phoebus.library.library.cart.CartOutDTO;
import br.com.phoebus.library.library.cart.v1.CartServiceImplementation;
import br.com.phoebus.library.library.client.Client;
import br.com.phoebus.library.library.client.ClientDTO;
import br.com.phoebus.library.library.client.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class ClientServiceImplementation implements ClientService{

    private ClientRepository clients;

    private ClientDTO client2DTO(Client client){
        return ClientDTO.builder()
                .email(client.getEmail())
                .name(client.getName())
                .birthDate(client.getBirthDate())
                .phone(client.getPhone())
                .sex(client.getSex())
                .build();
    }

    private Client DTO2Client(ClientDTO dto){
        return Client.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .birthDate(dto.getBirthDate())
                .phone(dto.getPhone())
                .sex(dto.getSex())
                .build();
    }

    @Override
    @Transactional
    public List<ClientDTO> listAllClient() {

        LinkedList<ClientDTO> all = new LinkedList<ClientDTO>();

        for(Client c: clients.findAll()){
            all.add(this.client2DTO(c));
        }

        return all;
    }

    @Override
    @Transactional
    public ClientDTO getClientById(Integer id) {
        return this.client2DTO(
                clients
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Não foi encontrado nenhum cliente com o id "+id+" na base de dados."
                ))
        );
    }

    @Override
    @Transactional
    public Integer getClientId(String email) {
        return clients
                .findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Não foi encontrado nenhum cliente com o email "+email+" na base de dados."
                )).getId();
    }

    @Override
    @Transactional
    public Client save(ClientDTO client) {
        return clients.save(this.DTO2Client(client));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
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

    @Override
    @Transactional
    public void update(Integer id, ClientDTO client) {
        clients.
                findById(id).
                map(
                        existentClient -> {
                            Client c = this.DTO2Client(client);
                            c.setId(existentClient.getId());
                            clients.save(c);
                            return existentClient;
                        }).orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Não foi encontrado nenhum cliente com o id "+id+" na base de dados."
                ));
    }

    @Override
    @Transactional
    public List<CartOutDTO> ListClientCarts(Integer id) {
        Client client = clients
                .findClientFetchCart(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Não foi encontrado nenhum cliente com o id " + id + " na base de dados."
                ));

        LinkedList<CartOutDTO> allOut = new LinkedList<CartOutDTO>();

        for(Cart k: client.getCarts()){
            allOut.add(CartServiceImplementation.cart2OutDTO(k));
        }

        return allOut;
    }
}
