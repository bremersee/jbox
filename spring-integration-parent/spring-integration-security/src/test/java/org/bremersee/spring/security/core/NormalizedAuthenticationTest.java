/*
 * Copyright 2024-2026 the original author or authors.
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
import static org.mockito.Mockito.mock;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The normalized authentication test.
 *
 * @author Christian Bremer
 */
class NormalizedAuthenticationTest {

  private NormalizedAuthentication target;

  /**
   * Sets up.
   */
  @BeforeEach
  void setUp() {
    target = mock(NormalizedAuthentication.class);
  }

  /**
   * Gets granted authority names.
   */
  @Test
  void getGrantedAuthorityNames() {
    Set<String> actual = target.getGrantedAuthorityNames();
    assertThat(actual).isEmpty();
  }

  /**
   * Gets groups.
   */
  @Test
  void getGroups() {
    Collection<Group> actual = target.getGroups();
    assertThat(actual).isEmpty();
  }

  /**
   * Gets group names.
   */
  @Test
  void getGroupNames() {
    Set<String> actual = target.getGroupNames();
    assertThat(actual).isEmpty();
  }

  /**
   * Has granted authority.
   */
  @Test
  void hasGrantedAuthority() {
    boolean actual = target.hasGrantedAuthority("foobar");
    assertThat(actual).isFalse();
  }

  /**
   * Has any granted authority.
   */
  @Test
  void hasAnyGrantedAuthority() {
    boolean actual = target.hasAnyGrantedAuthority("foo", "bar");
    assertThat(actual).isFalse();
  }

  /**
   * Has any granted authority with list.
   */
  @Test
  void hasAnyGrantedAuthorityWithList() {
    boolean actual = target.hasAnyGrantedAuthority(List.of("foo", "bar"));
    assertThat(actual).isFalse();
  }

  /**
   * Is in group.
   */
  @Test
  void isInGroup() {
    boolean actual = target.isInGroup("foobar");
    assertThat(actual).isFalse();
  }

  /**
   * Is in any group.
   */
  @Test
  void isInAnyGroup() {
    boolean actual = target.isInAnyGroup("foo", "bar");
    assertThat(actual).isFalse();
  }

  /**
   * Is in any group with list.
   */
  @Test
  void isInAnyGroupWithList() {
    boolean actual = target.isInAnyGroup(List.of("foo", "bar"));
    assertThat(actual).isFalse();
  }
}