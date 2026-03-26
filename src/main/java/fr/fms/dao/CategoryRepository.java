package fr.fms.dao;

import fr.fms.entities.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
  public List<Category> findAll();

  public boolean existsByName(String name);

  public Category findByName(String name);
}
