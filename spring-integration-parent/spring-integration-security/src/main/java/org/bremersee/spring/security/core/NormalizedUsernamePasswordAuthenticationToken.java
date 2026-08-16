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

import static java.util.Objects.requireNonNullElseGet;

import java.io.Serial;
import java.util.Collection;
import java.util.List;
import lombok.EqualsAndHashCode;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * The normalized username password authentication token.
 *
 * @author Christian Bremer
 */
@EqualsAndHashCode(callSuper = true)
public class NormalizedUsernamePasswordAuthenticationToken
    extends UsernamePasswordAuthenticationToken implements NormalizedAuthentication {

  @Serial
  private static final long serialVersionUID = 1L;

  private final Collection<Group> groups;

  /**
   * This constructor can be safely used by any code that wishes to create a
   * <code>UsernamePasswordAuthenticationToken</code>, as the {@link #isAuthenticated()}
   * will return <code>false</code>.
   *
   * @param principal the principal
   * @param credentials the credentials
   */
  public NormalizedUsernamePasswordAuthenticationToken(
      @Nullable NormalizedPrincipal principal,
      @Nullable Object credentials) {
    super(principal, credentials);
    this.groups = List.of();
  }

  /**
   * This constructor should only be used by <code>AuthenticationManager</code> or
   * <code>AuthenticationProvider</code> implementations that are satisfied with
   * producing a trusted (i.e. {@link #isAuthenticated()} = <code>true</code>) authentication
   * token.
   *
   * @param principal the principal
   * @param credentials the credentials
   * @param authorities the authorities
   * @param groups the groups
   */
  public NormalizedUsernamePasswordAuthenticationToken(
      NormalizedPrincipal principal,
      @Nullable Object credentials,
      Collection<? extends GrantedAuthority> authorities,
      Collection<Group> groups) {
    super(principal, credentials, authorities);
    this.groups = requireNonNullElseGet(groups, List::of);
  }

  @Override
  public NormalizedPrincipal getPrincipal() {
    return (NormalizedPrincipal) super.getPrincipal();
  }

  @Override
  public @NonNull Collection<Group> getGroups() {
    return groups;
  }

  /**
   * This factory method can be safely used by any code that wishes to create a authenticated
   * <code>NormalizedUsernamePasswordAuthenticationToken</code>.
   *
   * @param principal the principal
   * @param credentials the credentials
   * @param authorities the authorities
   * @param groups the groups
   * @return NormalizedUsernamePasswordAuthenticationToken with true isAuthenticated() result
   */
  public static NormalizedUsernamePasswordAuthenticationToken authenticated(
      NormalizedPrincipal principal,
      @Nullable Object credentials,
      Collection<? extends GrantedAuthority> authorities,
      Collection<Group> groups) {
    return new NormalizedUsernamePasswordAuthenticationToken(principal, credentials, authorities,
        groups);
  }

  /**
   * This factory method can be safely used by any code that wishes to create a unauthenticated
   * <code>UsernamePasswordAuthenticationToken</code>.
   *
   * @param principal the principal
   * @param credentials the credentials
   * @return NormalizedUsernamePasswordAuthenticationToken with false isAuthenticated() result
   */
  public static NormalizedUsernamePasswordAuthenticationToken unauthenticated(
      @Nullable NormalizedPrincipal principal,
      @Nullable Object credentials) {
    return new NormalizedUsernamePasswordAuthenticationToken(principal, credentials);
  }

}
