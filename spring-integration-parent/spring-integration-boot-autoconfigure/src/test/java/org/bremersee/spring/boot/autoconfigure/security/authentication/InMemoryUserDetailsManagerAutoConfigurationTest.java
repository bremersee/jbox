/*
 * Copyright 2026 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.bremersee.spring.boot.autoconfigure.security.authentication;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.bremersee.spring.boot.autoconfigure.security.authentication.AuthenticationProperties.SimpleUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * The in-memory user details manager autoconfiguration test.
 *
 * @author Christian Bremer
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