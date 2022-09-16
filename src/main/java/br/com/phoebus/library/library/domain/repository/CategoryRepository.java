package br.com.phoebus.library.library.domain.repository;

import br.com.phoebus.library.library.domain.entity.Category;
import br.com.phoebus.library.library.domain.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository  extends JpaRepository<Category, Integer> {

    Optional<Category> findByName(String name);

}
