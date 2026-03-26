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
    articleRepository.save(new Article("Samsung", "S10", 400));
    articleRepository.save(new Article("Samsung", "S11", 500));
    articleRepository.save(new Article("Apple", "IPhone 15", 600));
    articleRepository.save(new Article("Apple", "IPhone 16", 700));
    articleRepository.save(new Article("Apple", "IPhone 17", 800));
    articleRepository.save(new Article("Sony", "Xperia 1", 900));
    articleRepository.save(new Article("Sony", "Xperia 2", 1000));
    articleRepository.save(new Article("Sony", "Xperia 3", 1100));
    articleRepository.save(new Article("Rolex", "Rolex 1", 1200));
    articleRepository.save(new Article("Rolex", "Rolex 2", 1300));
    articleRepository.save(new Article("Maggi", "Maggi 1", 1400));
    articleRepository.save(new Article("Maggi", "Maggi 2", 1500));
    for (Article article : articleRepository.findByBrand("S9")) {
      logger.info("Article: {}", article.toString());
    }
    System.out.println(articleRepository.findByDescription("S8"));
  }
}
