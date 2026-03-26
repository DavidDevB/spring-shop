package fr.fms;

import fr.fms.dao.ArticleRepository;
import fr.fms.dao.CategoryRepository;
import fr.fms.entities.Article;
import fr.fms.entities.Category;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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
    Category smartphone = categoryRepository.save(new Category("Smartphone"));
    Category watch = categoryRepository.save(new Category("Watch"));
    Category food = categoryRepository.save(new Category("Food"));
    articleRepository.save(new Article("Samsung", "S8", 250, smartphone));
    articleRepository.save(new Article("Samsung", "S9", 300, smartphone));
    articleRepository.save(new Article("Samsung", "S10", 400, smartphone));
    articleRepository.save(new Article("Samsung", "S11", 500, smartphone));
    articleRepository.save(new Article("Apple", "IPhone 15", 600, smartphone));
    articleRepository.save(new Article("Apple", "IPhone 16", 700, smartphone));
    articleRepository.save(new Article("Apple", "IPhone 17", 800, smartphone));
    articleRepository.save(new Article("Sony", "Xperia 1", 900, smartphone));
    articleRepository.save(new Article("Sony", "Xperia 2", 1000, smartphone));
    articleRepository.save(new Article("Sony", "Xperia 3", 1100, smartphone));
    articleRepository.save(new Article("Rolex", "Rolex 1", 1200, watch));
    articleRepository.save(new Article("Rolex", "Rolex 2", 1300, watch));
    articleRepository.save(new Article("Maggi", "Maggi 1", 1400, food));
    articleRepository.save(new Article("Maggi", "Maggi 2", 1500, food));
    for (Article article : articleRepository.findByBrand("S9")) {
      logger.info("Article: {}", article.toString());
    }
    System.out.println(articleRepository.findByDescription("S8"));

    Scanner sc = new Scanner(System.in);

    System.out.println("Bienvenue dans notre EShop!");
    System.out.println("*************************");
    System.out.println("1: Afficher les articles sans pagination");
    System.out.println("2: Afficher les articles avec pagination");
    System.out.println("*************************");
    System.out.println("3: Ajouter un article");
    System.out.println("4: Afficher un article");
    System.out.println("5: Supprimer un article");
    System.out.println("6: Modifier un article");
    System.out.println("*************************");
    System.out.println("7: Ajouter une categorie");
    System.out.println("8: Afficher une categorie");
    System.out.println("8: Supprimer une categorie");
    System.out.println("9: Mettre à jour une categorie");
    System.out.println("10: Afficher tous les articles d'une categorie");
    System.out.println("*************************");
    System.out.println("12: Sortir du programme");

    int choix = sc.nextInt();

    System.out.println("*************************");
    System.out.println("EXIT       pour sortir de la pagination");
    System.out.println("PREV       pour aller a la page precedente");
    System.out.println("NEXT       pour aller a la page suivante");
    System.out.println(
      "PAGE puis 7 pour afficher 7 articles par page (5 par défaut)"
    );
    System.out.println("*************************");
    switch (choix) {
      case 1:
        System.out.printf(
          "%-15s %-20s %-20s %-10s %-15s%n",
          "IDENTIFIANT",
          "MARQUE",
          "DESCRIPTION",
          "PRIX",
          "CATEGORIE"
        );
        System.out.println("-".repeat(80));
        for (Article article : articleRepository.findAllWithCategory()) {
          System.out.printf(
            "%-15s %-20s %-20s %-10s %-15s%n",
            article.getId(),
            article.getBrand(),
            article.getDescription(),
            article.getPrice(),
            article.getCategory().getName()
          );
        }
        break;
      case 2:
        int pageNum = 0;
        int pageSize = 5;
        String cmd = "";

        while (!cmd.equals("EXIT")) {
          Page<Article> page = articleRepository.findAll(
            PageRequest.of(pageNum, pageSize)
          );

          System.out.printf(
            "%-15s %-20s %-20s %-10s %-15s%n",
            "IDENTIFIANT",
            "MARQUE",
            "DESCRIPTION",
            "PRIX",
            "CATEGORIE"
          );
          System.out.println("-".repeat(80));
          for (Article article : page.getContent()) {
            System.out.printf(
              "%-15s %-20s %-20s %-10s %-15s%n",
              article.getId(),
              article.getBrand(),
              article.getDescription(),
              article.getPrice(),
              article.getCategory().getName()
            );
          }

          System.out.println(
            "\nPage " + (pageNum + 1) + " / " + page.getTotalPages()
          );
          System.out.print("Commande (NEXT/PREV/EXIT/PAGE n) : ");
          cmd = sc.next().toUpperCase();

          if (cmd.equals("NEXT")) {
            if (page.hasNext()) {
              pageNum++;
            }
          } else if (cmd.equals("PREV")) {
            if (page.hasPrevious()) {
              pageNum--;
            }
          } else if (cmd.startsWith("PAGE")) {
            pageSize = sc.nextInt();
            pageNum = 0;
          }
        }
        break;
      case 3:
        break;
      case 4:
        break;
      case 5:
        break;
      case 6:
        break;
    }
  }
}
