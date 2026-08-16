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

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import org.bremersee.exception.ServiceException;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * The authentication template.
 *
 * @author Christian Bremer
 */
public class AuthenticationTemplate<A extends Authentication>
    implements AuthenticationOperations<A> {

  private final Supplier<ServiceException> unauthenticatedExceptionSupplier;

  /**
   * Instantiates a new authentication template.
   */
  public AuthenticationTemplate() {
    this(null);
  }

  /**
   * Instantiates a new authentication template.
   *
   * @param unauthenticatedExceptionSupplier the unauthenticated exception supplier
   */
  public AuthenticationTemplate(
      Supplier<ServiceException> unauthenticatedExceptionSupplier) {
    this.unauthenticatedExceptionSupplier = isNull(unauthenticatedExceptionSupplier)
        ? ServiceException::forbidden
        : unauthenticatedExceptionSupplier;
  }

  /**
   * Gets authentication.
   *
   * @return the authentication
   */
  Optional<A> getAuthentication() {
    //noinspection unchecked
    return (Optional<A>) Optional.of(SecurityContextHolder.getContext())
        .map(SecurityContext::getAuthentication)
        .filter(Authentication::isAuthenticated);
  }

  @Override
  public <R> R doWithAuthentication(@NonNull Function<A, R> function) {
    return getAuthentication()
        .map(function)
        .orElseThrow(unauthenticatedExceptionSupplier);
  }

  @Override
  public <R> R doWithOptionalAuthentication(
      @NonNull Function<@Nullable A, R> function) {
    return function.apply(getAuthentication().orElse(null));
  }

  @Override
  public @NonNull <R> ResponseEntity<R> responseWithAuthentication(
      @NonNull Function<A, R> function) {
    return ResponseEntity.of(Optional.ofNullable(doWithAuthentication(function)));
  }

  @Override
  public @NonNull <R> ResponseEntity<R> responseWithOptionalAuthentication(
      @NonNull Function<@Nullable A, R> function) {
    return ResponseEntity.of(Optional.ofNullable(doWithOptionalAuthentication(function)));
  }

}
