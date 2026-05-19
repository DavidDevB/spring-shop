package fr.fms.entities;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ArticleTest {

    @Test
    void defaultConstructor_createsEmptyArticle() {
        Article article = new Article();
        assertThat(article.getId()).isNull();
        assertThat(article.getBrand()).isNull();
        assertThat(article.getDescription()).isNull();
        assertThat(article.getPrice()).isEqualTo(0f);
        assertThat(article.getCategory()).isNull();
    }

    @Test
    void parameterizedConstructor_setsAllFields() {
        Category category = new Category("Electronics");
        Article article = new Article("Sony", "Sony Headphones Premium", 150f, category);

        assertThat(article.getBrand()).isEqualTo("Sony");
        assertThat(article.getDescription()).isEqualTo("Sony Headphones Premium");
        assertThat(article.getPrice()).isEqualTo(150f);
        assertThat(article.getCategory()).isEqualTo(category);
    }

    @Test
    void settersAndGetters_workCorrectly() {
        Article article = new Article();
        Category category = new Category("Books");
        article.setId(1L);
        article.setBrand("Samsung");
        article.setDescription("Samsung Galaxy Phone");
        article.setPrice(200f);
        article.setCategory(category);

        assertThat(article.getId()).isEqualTo(1L);
        assertThat(article.getBrand()).isEqualTo("Samsung");
        assertThat(article.getDescription()).isEqualTo("Samsung Galaxy Phone");
        assertThat(article.getPrice()).isEqualTo(200f);
        assertThat(article.getCategory()).isEqualTo(category);
    }

    @Test
    void equals_sameId_returnsTrue() {
        Article a1 = new Article();
        a1.setId(1L);
        Article a2 = new Article();
        a2.setId(1L);
        assertThat(a1).isEqualTo(a2);
    }

    @Test
    void equals_differentId_returnsFalse() {
        Article a1 = new Article();
        a1.setId(1L);
        Article a2 = new Article();
        a2.setId(2L);
        assertThat(a1).isNotEqualTo(a2);
    }

    @Test
    void equals_bothNullId_returnsTrue() {
        Article a1 = new Article();
        Article a2 = new Article();
        assertThat(a1).isEqualTo(a2);
    }

    @Test
    void equals_sameReference_returnsTrue() {
        Article article = new Article();
        assertThat(article).isEqualTo(article);
    }

    @Test
    void equals_null_returnsFalse() {
        Article article = new Article();
        assertThat(article).isNotEqualTo(null);
    }

    @Test
    void equals_differentType_returnsFalse() {
        Article article = new Article();
        assertThat(article).isNotEqualTo("not an article");
    }

    @Test
    void hashCode_nullId_returnsZero() {
        Article article = new Article();
        assertThat(article.hashCode()).isEqualTo(0);
    }

    @Test
    void hashCode_withId_returnsIdHashCode() {
        Article article = new Article();
        article.setId(5L);
        assertThat(article.hashCode()).isEqualTo(Long.valueOf(5L).hashCode());
    }

    @Test
    void toString_containsBrandDescriptionAndPrice() {
        Article article = new Article();
        article.setId(1L);
        article.setBrand("Apple");
        article.setDescription("Apple Macbook Pro");
        article.setPrice(1200f);

        String str = article.toString();
        assertThat(str).contains("Apple").contains("Apple Macbook Pro").contains("1200");
    }
}
