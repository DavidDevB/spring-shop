package fr.fms.entities;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryTest {

    @Test
    void defaultConstructor_createsEmptyCategory() {
        Category category = new Category();
        assertThat(category.getId()).isNull();
        assertThat(category.getName()).isNull();
    }

    @Test
    void parameterizedConstructor_setsName() {
        Category category = new Category("Electronics");
        assertThat(category.getName()).isEqualTo("Electronics");
    }

    @Test
    void setId_andGetId_workCorrectly() {
        Category category = new Category("Books");
        category.setId(42L);
        assertThat(category.getId()).isEqualTo(42L);
    }

    @Test
    void equals_sameIdAndName_returnsTrue() {
        Category c1 = new Category("Tech");
        c1.setId(1L);
        Category c2 = new Category("Tech");
        c2.setId(1L);
        assertThat(c1).isEqualTo(c2);
    }

    @Test
    void equals_differentId_returnsFalse() {
        Category c1 = new Category("Tech");
        c1.setId(1L);
        Category c2 = new Category("Tech");
        c2.setId(2L);
        assertThat(c1).isNotEqualTo(c2);
    }

    @Test
    void equals_sameReference_returnsTrue() {
        Category category = new Category("Tech");
        assertThat(category).isEqualTo(category);
    }

    @Test
    void equals_null_returnsFalse() {
        Category category = new Category("Tech");
        assertThat(category).isNotEqualTo(null);
    }

    @Test
    void equals_differentType_returnsFalse() {
        Category category = new Category("Tech");
        assertThat(category).isNotEqualTo("not a category");
    }

    @Test
    void toString_containsIdAndName() {
        Category category = new Category("Sports");
        category.setId(3L);
        String str = category.toString();
        assertThat(str).contains("Sports").contains("3");
    }
}
