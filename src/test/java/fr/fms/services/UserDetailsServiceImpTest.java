package fr.fms.services;

import fr.fms.dao.UserRepository;
import fr.fms.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImpTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImp userDetailsService;

    @Test
    void loadUserByUsername_existingUser_returnsCorrectUserDetails() {
        User user = new User("alice", "$2a$10$hashedPassword", "ROLE_USER");
        when(userRepository.findByUsername("alice")).thenReturn(user);

        UserDetails result = userDetailsService.loadUserByUsername("alice");

        assertThat(result.getUsername()).isEqualTo("alice");
        assertThat(result.getPassword()).isEqualTo("$2a$10$hashedPassword");
        assertThat(result.getAuthorities()).hasSize(1);
        assertThat(result.getAuthorities().iterator().next().getAuthority()).isEqualTo("ROLE_USER");
    }

    @Test
    void loadUserByUsername_adminUser_returnsAdminAuthority() {
        User user = new User("admin", "$2a$10$adminPwd", "ROLE_ADMIN");
        when(userRepository.findByUsername("admin")).thenReturn(user);

        UserDetails result = userDetailsService.loadUserByUsername("admin");

        assertThat(result.getAuthorities().iterator().next().getAuthority()).isEqualTo("ROLE_ADMIN");
    }

    @Test
    void loadUserByUsername_userNotFound_throwsNullPointerException() {
        when(userRepository.findByUsername("unknown")).thenReturn(null);

        assertThatThrownBy(() -> userDetailsService.loadUserByUsername("unknown"))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void loadUserByUsername_delegatesToRepository() {
        User user = new User("alice", "pwd", "ROLE_USER");
        when(userRepository.findByUsername("alice")).thenReturn(user);

        userDetailsService.loadUserByUsername("alice");

        verify(userRepository, times(1)).findByUsername("alice");
    }

    @Test
    void loadUserByUsername_returnsAccountEnabled() {
        User user = new User("alice", "pwd", "ROLE_USER");
        when(userRepository.findByUsername("alice")).thenReturn(user);

        UserDetails result = userDetailsService.loadUserByUsername("alice");

        assertThat(result.isEnabled()).isTrue();
        assertThat(result.isAccountNonExpired()).isTrue();
        assertThat(result.isAccountNonLocked()).isTrue();
        assertThat(result.isCredentialsNonExpired()).isTrue();
    }
}
