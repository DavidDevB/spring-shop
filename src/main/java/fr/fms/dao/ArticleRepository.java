package fr.fms.dao;

import fr.fms.entities.Article;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
  public List<Article> findByBrand(String brand);
}
