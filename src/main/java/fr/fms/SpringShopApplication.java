package fr.fms;

import fr.fms.dao.ArticleRepository;
import fr.fms.dao.CategoryRepository;
import fr.fms.entities.Article;
import fr.fms.entities.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringShopApplication implements CommandLineRunner {

  @Autowired
  private ArticleRepository articleRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  public static void main(String[] args) {
    SpringApplication.run(SpringShopApplication.class, args);
  }

  private static final Logger logger = LoggerFactory.getLogger(
    SpringShopApplication.class
  );

  @Override
  public void run(String... args) throws Exception {
    categoryRepository.save(new Category("Smartphone"));
    articleRepository.save(new Article("Samsung", "S8", 250));
    articleRepository.save(new Article("Samsung", "S9", 300));
    for (Article article : articleRepository.findByBrand("S9")) {
      logger.info("Article: {}", article.toString());
    }
  }
}
