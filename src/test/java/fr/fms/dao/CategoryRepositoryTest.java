package fr.fms.dao;

import fr.fms.entities.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    void save_andFindAll_returnsAllSavedCategories() {
        categoryRepository.save(new Category("Electronics"));
        categoryRepository.save(new Category("Books"));

        List<Category> result = categoryRepository.findAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void findById_returnsCorrectCategory() {
        Category saved = categoryRepository.save(new Category("Electronics"));

        assertThat(categoryRepository.findById(saved.getId())).isPresent();
    }

    @Test
    void existsByName_existingCategory_returnsTrue() {
        categoryRepository.save(new Category("Electronics"));

        assertThat(categoryRepository.existsByName("Electronics")).isTrue();
    }

    @Test
    void existsByName_nonExistingCategory_returnsFalse() {
        assertThat(categoryRepository.existsByName("NonExistent")).isFalse();
    }

    @Test
    void findByName_returnsMatchingCategory() {
        categoryRepository.save(new Category("Electronics"));

        Category result = categoryRepository.findByName("Electronics");

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Electronics");
    }

    @Test
    void findByName_unknownName_returnsNull() {
        Category result = categoryRepository.findByName("Unknown");

        assertThat(result).isNull();
    }

    @Test
    void deleteByName_removesCategory() {
        categoryRepository.save(new Category("ToDelete"));

        categoryRepository.deleteByName("ToDelete");

        assertThat(categoryRepository.existsByName("ToDelete")).isFalse();
    }

    @Test
    void updateName_changesNameInDatabase() {
        categoryRepository.save(new Category("OldName"));

        categoryRepository.updateName("OldName", "NewName");
        em.clear(); // vide le cache L1 pour lire depuis la BD

        assertThat(categoryRepository.existsByName("NewName")).isTrue();
        assertThat(categoryRepository.existsByName("OldName")).isFalse();
    }

    @Test
    void deleteAll_removesAllCategories() {
        categoryRepository.save(new Category("Electronics"));
        categoryRepository.save(new Category("Books"));

        categoryRepository.deleteAll();

        assertThat(categoryRepository.findAll()).isEmpty();
    }
}
