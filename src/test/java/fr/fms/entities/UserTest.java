package fr.fms.entities;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void defaultConstructor_createsEmptyUser() {
        User user = new User();
        assertThat(user.getUsername()).isNull();
        assertThat(user.getPassword()).isNull();
        assertThat(user.getType()).isNull();
    }

    @Test
    void parameterizedConstructor_setsAllFields() {
        User user = new User("alice", "$2a$10$encodedPassword", "ROLE_USER");
        assertThat(user.getUsername()).isEqualTo("alice");
        assertThat(user.getPassword()).isEqualTo("$2a$10$encodedPassword");
        assertThat(user.getType()).isEqualTo("ROLE_USER");
    }

    @Test
    void settersAndGetters_workCorrectly() {
        User user = new User();
        user.setUsername("bob");
        user.setPassword("secret");
        user.setType("ROLE_ADMIN");

        assertThat(user.getUsername()).isEqualTo("bob");
        assertThat(user.getPassword()).isEqualTo("secret");
        assertThat(user.getType()).isEqualTo("ROLE_ADMIN");
    }

    @Test
    void setUsername_updatesValue() {
        User user = new User("old", "pwd", "ROLE_USER");
        user.setUsername("new");
        assertThat(user.getUsername()).isEqualTo("new");
    }

    @Test
    void setPassword_updatesValue() {
        User user = new User("alice", "oldPwd", "ROLE_USER");
        user.setPassword("newPwd");
        assertThat(user.getPassword()).isEqualTo("newPwd");
    }

    @Test
    void setType_updatesValue() {
        User user = new User("alice", "pwd", "ROLE_USER");
        user.setType("ROLE_ADMIN");
        assertThat(user.getType()).isEqualTo("ROLE_ADMIN");
    }

    @Test
    void toString_containsUsernameAndType() {
        User user = new User("charlie", "pwd", "ROLE_USER");
        String str = user.toString();
        assertThat(str).contains("charlie").contains("ROLE_USER");
    }
}
