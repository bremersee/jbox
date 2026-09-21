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

package org.bremersee.spring.security.test.context.support;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import org.bremersee.spring.security.core.NormalizedAuthentication;
import org.bremersee.spring.security.core.NormalizedPrincipal;
import org.bremersee.spring.security.core.NormalizedUser;
import org.bremersee.spring.security.test.context.support.WithNormalizedUserTest.TestApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * The with normalized user test.
 *
 * @author Christian Bremer
 */
@SpringBootTest(classes = {TestApplication.class})
class WithNormalizedUserTest {

  /**
   * Create security context.
   */
  @WithNormalizedUser(
      name = "tester",
      firstName = "Anna Livia",
      lastName = "Plurabelle",
      email = "anna@example.org",
      password = "1234",
      authorities = {"ROLE_TESTER"},
      groups = {"GROUP_TESTER"})
  @Test
  void createSecurityContext() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    assertThat(authentication)
        .isNotNull()
        .isInstanceOf(NormalizedAuthentication.class);
    NormalizedAuthentication actual = (NormalizedAuthentication) authentication;
    assertThat(actual)
        .extracting(NormalizedAuthentication::getName)
        .isEqualTo("tester");
    assertThat(actual)
        .extracting(NormalizedAuthentication::isAuthenticated)
        .isEqualTo(true);
    NormalizedPrincipal expectedPrinciple = new NormalizedUser(
        "tester",
        "Anna Livia",
        "Plurabelle",
        "anna@example.org");
    assertThat(actual)
        .extracting(NormalizedAuthentication::getPrincipal)
        .isEqualTo(expectedPrinciple);
    assertThat(actual)
        .extracting(NormalizedAuthentication::getCredentials)
        .isEqualTo("1234");
    assertThat(actual)
        .extracting(NormalizedAuthentication::getGrantedAuthorityNames)
        .isEqualTo(Set.of("ROLE_TESTER"));
    assertThat(actual)
        .extracting(NormalizedAuthentication::getGroupNames)
        .isEqualTo(Set.of("GROUP_TESTER"));
  }

  /**
   * The test application.
   *
   * @author Christian Bremer
   */
  @Configuration
  static class TestApplication {

  }
}