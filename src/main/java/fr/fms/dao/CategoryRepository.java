package fr.fms.dao;

import fr.fms.entities.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
  public List<Category> findAll();

  public boolean existsByName(String name);

  public Category findByName(String name);

  @Transactional
  public void deleteByName(String name);

  @Modifying
  @Transactional
  @Query("UPDATE Category c SET c.name = :newName WHERE c.name = :oldName")
  void updateName(@Param("oldName") String oldName, @Param("newName") String newName);
}
