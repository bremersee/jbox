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

package org.bremersee.spring.security.core;

import static java.util.Objects.isNull;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.bremersee.exception.ServiceException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * The authentication template test.
 *
 * @author Christian Bremer
 */
class AuthenticationTemplateTest {

  private static final String USERNAME = "junit";

  private AuthenticationTemplate<UsernamePasswordAuthenticationToken> target;

  /**
   * Sets up.
   */
  @BeforeEach
  void setUp() {
    UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken
        .authenticated(
            USERNAME,
            "changeit",
            List.of(new SimpleGrantedAuthority("ROLE_TEST")));
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    target = new AuthenticationTemplate<>();
  }

  /**
   * Tear down.
   */
  @AfterEach
  void tearDown() {
    SecurityContextHolder.clearContext();
  }

  /**
   * Gets authentication.
   */
  @Test
  void getAuthentication() {
    Optional<UsernamePasswordAuthenticationToken> actual = target.getAuthentication();
    assertThat(actual).isPresent();
  }

  /**
   * Do with authentication.
   */
  @Test
  void doWithAuthentication() {
    String actual = target.doWithAuthentication(AbstractAuthenticationToken::getName);
    assertThat(actual)
        .isEqualTo(USERNAME);
  }

  /**
   * Do with authentication but authentication is absence.
   */
  @Test
  void doWithAuthenticationButAuthenticationIsAbsence() {
    SecurityContextHolder.clearContext();
    var newTarget = new AuthenticationTemplate<UsernamePasswordAuthenticationToken>(
        ServiceException::badRequest);
    Assertions.assertThatExceptionOfType(ServiceException.class)
        .isThrownBy(() -> newTarget.doWithAuthentication(AbstractAuthenticationToken::getName));
  }

  /**
   * Do with optional authentication.
   */
  @Test
  void doWithOptionalAuthentication() {
    Optional<String> actual = target.doWithOptionalAuthentication(auth -> Optional.ofNullable(auth)
        .map(AbstractAuthenticationToken::getName));
    assertThat(actual)
        .hasValue(USERNAME);
  }

  /**
   * Do with optional authentication but authentication is absence.
   */
  @Test
  void doWithOptionalAuthenticationButAuthenticationIsAbsence() {
    SecurityContextHolder.clearContext();
    Optional<String> actual = target.doWithOptionalAuthentication(auth -> Optional.ofNullable(auth)
        .map(AbstractAuthenticationToken::getName));
    assertThat(actual)
        .isEmpty();
  }

  /**
   * Response with authentication.
   */
  @Test
  void responseWithAuthentication() {
    ResponseEntity<String> actual = target
        .responseWithAuthentication(AbstractAuthenticationToken::getName);
    assertThat(actual.getBody())
        .isEqualTo(USERNAME);
  }

  /**
   * Response with optional authentication.
   */
  @Test
  void responseWithOptionalAuthentication() {
    ResponseEntity<String> actual = target
        .responseWithOptionalAuthentication(auth -> isNull(auth) ? null : auth.getName());
    assertThat(actual.getBody())
        .isEqualTo(USERNAME);
  }

}