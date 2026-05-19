package fr.fms.dao;

import fr.fms.entities.Article;
import fr.fms.entities.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TestEntityManager em;

    private Category electronics;

    @BeforeEach
    void setUp() {
        electronics = categoryRepository.save(new Category("Electronics"));
    }

    @Test
    void save_andFindById_returnsArticle() {
        Article saved = articleRepository.save(new Article("Sony", "Sony Headphones Premium", 150f, electronics));

        assertThat(articleRepository.findById(saved.getId())).isPresent();
    }

    @Test
    void findByBrand_returnsOnlyMatchingArticles() {
        articleRepository.save(new Article("Sony", "Sony Headphones Premium", 150f, electronics));
        articleRepository.save(new Article("Samsung", "Samsung Galaxy Phone Pro", 200f, electronics));

        List<Article> result = articleRepository.findByBrand("Sony");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getBrand()).isEqualTo("Sony");
    }

    @Test
    void findByDescription_returnsMatchingArticle() {
        articleRepository.save(new Article("Sony", "Sony Headphones Premium", 150f, electronics));

        Article result = articleRepository.findByDescription("Sony Headphones Premium");

        assertThat(result).isNotNull();
        assertThat(result.getBrand()).isEqualTo("Sony");
    }

    @Test
    void findByBrandAndDescription_returnsMatchingArticle() {
        articleRepository.save(new Article("Sony", "Sony Headphones Premium", 150f, electronics));

        Article result = articleRepository.findByBrandAndDescription("Sony", "Sony Headphones Premium");

        assertThat(result).isNotNull();
        assertThat(result.getBrand()).isEqualTo("Sony");
        assertThat(result.getDescription()).isEqualTo("Sony Headphones Premium");
    }

    @Test
    void findByDescriptionContains_withPagination_returnsMatchingPage() {
        articleRepository.save(new Article("Sony", "Sony Headphones Premium", 150f, electronics));
        articleRepository.save(new Article("Apple", "Apple AirPods Premium", 200f, electronics));
        articleRepository.save(new Article("Bose", "Bose QuietComfort Buds", 180f, electronics));

        Page<Article> result = articleRepository.findByDescriptionContains("Premium", PageRequest.of(0, 10));

        assertThat(result.getContent()).hasSize(2);
    }

    @Test
    void findByDescriptionContains_pagination_respectsPageSize() {
        for (int i = 1; i <= 6; i++) {
            articleRepository.save(new Article("Brand" + i, "Article description number " + i, 100f + i, electronics));
        }

        Page<Article> result = articleRepository.findByDescriptionContains("description", PageRequest.of(0, 5));

        assertThat(result.getContent()).hasSize(5);
        assertThat(result.getTotalElements()).isEqualTo(6);
    }

    @Test
    void searchArticles_byKeywordAndMinPrice_returnsMatchingArticles() {
        articleRepository.save(new Article("Sony", "Sony Camera Premium", 300f, electronics));
        articleRepository.save(new Article("Sony", "Sony Headphones Pro", 80f, electronics));
        articleRepository.save(new Article("Apple", "Apple iPhone Pro Max", 800f, electronics));

        List<Article> result = articleRepository.searchArticles("Sony", 100.0);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getDescription()).contains("Camera");
    }

    @Test
    void findAllWithCategory_returnsArticlesWithCategoryLoaded() {
        articleRepository.save(new Article("Sony", "Sony Headphones Premium", 150f, electronics));

        List<Article> result = articleRepository.findAllWithCategory();

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getCategory()).isNotNull();
        assertThat(result.get(0).getCategory().getName()).isEqualTo("Electronics");
    }

    @Test
    void findByCategoryName_withPagination_returnsArticlesInCategory() {
        articleRepository.save(new Article("Sony", "Sony Headphones Premium", 150f, electronics));
        articleRepository.save(new Article("Apple", "Apple AirPods Premium", 200f, electronics));

        Page<Article> result = articleRepository.findByCategoryName(electronics, PageRequest.of(0, 10));

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent()).allMatch(a -> a.getCategory().getName().equals("Electronics"));
    }

    @Test
    void getAllByCategory_returnsAllArticlesInCategory() {
        articleRepository.save(new Article("Sony", "Sony Headphones Premium", 150f, electronics));

        List<Article> result = articleRepository.getAllByCategory(electronics);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCategory().getName()).isEqualTo("Electronics");
    }

    @Test
    void deleteById_removesArticle() {
        Article saved = articleRepository.save(new Article("Sony", "Sony Headphones Premium", 150f, electronics));
        Long id = saved.getId();

        articleRepository.deleteById(id);

        assertThat(articleRepository.findById(id)).isEmpty();
    }

    @Test
    void updateArticle_updatesFieldsInDatabase() {
        articleRepository.save(new Article("OldBrand", "Sony Headphones Premium", 150f, electronics));

        articleRepository.updateArticle("NewBrand", "Sony Headphones Premium", 250f, electronics);
        em.clear(); // vide le cache L1 pour lire depuis la BD

        Article updated = articleRepository.findByDescription("Sony Headphones Premium");
        assertThat(updated.getBrand()).isEqualTo("NewBrand");
        assertThat(updated.getPrice()).isEqualTo(250f);
    }
}
