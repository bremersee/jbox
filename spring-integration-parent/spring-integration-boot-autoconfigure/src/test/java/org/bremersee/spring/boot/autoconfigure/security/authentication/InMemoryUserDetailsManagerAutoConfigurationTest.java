package org.bremersee.spring.boot.autoconfigure.security.authentication;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.bremersee.spring.boot.autoconfigure.security.authentication.AuthenticationProperties.SimpleUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * The in-memory user details manager autoconfiguration test.
 */
class InMemoryUserDetailsManagerAutoConfigurationTest {

  private InMemoryUserDetailsManagerAutoConfiguration target;

  /**
   * Sets up.
   */
  @BeforeEach
  void setUp() {
    AuthenticationProperties properties = new AuthenticationProperties();
    SimpleUser user = new SimpleUser();
    user.setName("junit");
    user.setPassword("changeit");
    user.setFirstName("Test");
    user.setLastName("User");
    user.setEmail("junit@example.org");
    user.setAuthorities(List.of("ROLE_TESTER"));
    properties.getInMemory().setUsers(List.of(user));
    properties.getInMemory().setEnabled(true);
    target = new InMemoryUserDetailsManagerAutoConfiguration(properties);
  }

  /**
   * User details service.
   */
  @Test
  void userDetailsService() {
    UserDetailsService actual = target.userDetailsService();
    assertThat(actual).isNotNull();
  }
}