package br.com.phoebus.library.library.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer> {

    Optional<Client> findByEmail(String email);
    List<Client> findByNameLike(String name);

    @Query("select c from Client c left join fetch c.carts where c.id = :id")
    Optional<Client> findClientFetchCart(@Param("id") Integer id);

}
