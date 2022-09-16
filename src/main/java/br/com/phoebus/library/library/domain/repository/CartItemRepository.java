package br.com.phoebus.library.library.domain.repository;

import br.com.phoebus.library.library.domain.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
}
