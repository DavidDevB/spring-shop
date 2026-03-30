package fr.fms.dao;

import fr.fms.entities.Article;
import fr.fms.entities.Category;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
  public List<Article> findByBrand(String brand);

  @Query("select A from Article A where A.brand like %:x% and A.price > :y")
  public List<Article> searchArticles(
    @Param("x") String kw,
    @Param("y") double price
  );

  @Query("SELECT a FROM Article a JOIN FETCH a.category")
  List<Article> findAllWithCategory();

  public Article findByDescription(String description);

  public Page findByDescriptionContains(String description, Pageable pageable);

  public Article findByBrandAndDescription(String brand, String description);

  public void deleteById(Long id);

  @Modifying
  @Transactional
  @Query(
    "UPDATE Article a SET a.brand = :brand, a.price = :price, a.category = :category WHERE a.description = :description"
  )
  void updateArticle(
    @Param("brand") String brand,
    @Param("description") String description,
    @Param("price") float price,
    @Param("category") Category category
  );

  @Query(
    "SELECT a FROM Article a JOIN FETCH a.category WHERE a.category = :category"
  )
  List<Article> getAllByCategory(@Param("category") Category category);
}
