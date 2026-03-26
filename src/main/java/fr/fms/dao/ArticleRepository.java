package fr.fms.dao;

import fr.fms.entities.Article;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<Article, Long> {
  public List<Article> findByBrand(String brand);

  @Query("select A from Article A where A.brand like %:x% and A.price > :y")
  public List<Article> searchArticles(
    @Param("x") String kw,
    @Param("y") double price
  );
}
