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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * The normalized username password authentication token test.
 *
 * @author Christian Bremer
 */
class NormalizedUsernamePasswordAuthenticationTokenTest {

  /**
   * Gets principal.
   */
  @Test
  void getPrincipal() {
    var principal = NormalizedUser.empty();
    var target = new NormalizedUsernamePasswordAuthenticationToken(
        principal,
        null,
        List.of(),
        List.of()
    );
    NormalizedPrincipal actual = target.getPrincipal();
    assertThat(actual)
        .isEqualTo(principal);
    assertThat(target.isAuthenticated()).isTrue();
  }

  /**
   * Gets groups.
   */
  @Test
  void getGroups() {
    NormalizedPrincipal principal = NormalizedUser.empty();
    List<Group> groups = List.of(NormalizedGroup.of("junit"));
    var target = new NormalizedUsernamePasswordAuthenticationToken(
        principal,
        null,
        List.of(),
        groups
    );
    assertThat(target.isAuthenticated()).isTrue();
    Collection<Group> actual = target.getGroups();
    assertThat(actual)
        .isEqualTo(groups);
  }

  /**
   * Authenticated.
   */
  @Test
  void authenticated() {
    var principal = NormalizedUser.empty();
    var actual = NormalizedUsernamePasswordAuthenticationToken.authenticated(
        principal,
        null,
        List.of(),
        List.of());
    assertThat(actual).isNotNull();
    assertThat(actual.isAuthenticated()).isTrue();
  }

  /**
   * Unauthenticated.
   */
  @Test
  void unauthenticated() {
    var principal = NormalizedUser.empty();
    var actual = NormalizedUsernamePasswordAuthenticationToken.unauthenticated(
        principal,
        null);
    assertThat(actual).isNotNull();
    assertThat(actual.isAuthenticated()).isFalse();
  }
}