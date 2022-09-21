package br.com.phoebus.library.library.client.v1;

import br.com.phoebus.library.library.cart.CartOutDTO;
import br.com.phoebus.library.library.client.Client;
import br.com.phoebus.library.library.client.ClientDTO;

import java.util.List;

public interface ClientService {
    List<ClientDTO> listAllClient();
    ClientDTO getClientById(Integer id);
    Integer getClientId(String email);
    Client save(ClientDTO client);
    void delete(Integer id);
    void update(Integer id, ClientDTO client);
    List<CartOutDTO> ListClientCarts(Integer id);
}
