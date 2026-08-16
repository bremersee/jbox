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

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import org.bremersee.spring.security.core.Group;
import org.bremersee.spring.security.core.NormalizedGroup;
import org.bremersee.spring.security.core.NormalizedUser;
import org.bremersee.spring.security.core.NormalizedUsernamePasswordAuthenticationToken;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.util.Assert;

/**
 * The normalized user security context factory.
 *
 * @author Christian Bremer
 */
final class WithNormalizedUserSecurityContextFactory
    implements WithSecurityContextFactory<WithNormalizedUser> {

  private SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder
      .getContextHolderStrategy();

  /**
   * Sets security context holder strategy.
   *
   * @param securityContextHolderStrategy the security context holder strategy
   */
  @Autowired(required = false)
  void setSecurityContextHolderStrategy(
      SecurityContextHolderStrategy securityContextHolderStrategy) {
    this.securityContextHolderStrategy = securityContextHolderStrategy;
  }

  @Override
  public @NonNull SecurityContext createSecurityContext(WithNormalizedUser user) {

    String name = user.name();
    Assert.hasText(name, "Name must not be null or empty.");
    NormalizedUser normalizedUser = new NormalizedUser(
        name,
        user.firstName(),
        user.lastName(),
        user.email());
    Collection<GrantedAuthority> authorities = Arrays.stream(user.authorities())
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toSet());
    Collection<Group> groups = Arrays.stream(user.groups())
        .map(NormalizedGroup::of)
        .collect(Collectors.toSet());
    Authentication authentication = NormalizedUsernamePasswordAuthenticationToken.authenticated(
        normalizedUser,
        user.password(),
        authorities,
        groups);
    SecurityContext context = this.securityContextHolderStrategy.createEmptyContext();
    context.setAuthentication(authentication);
    return context;
  }
}
