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

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import org.bremersee.exception.ServiceException;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.reactivestreams.Publisher;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The reactive normalized authentication template.
 *
 * @param <A> the type parameter
 * @author Christian Bremer
 */
public class ReactiveAuthenticationTemplate<A extends Authentication>
    implements ReactiveAuthenticationOperations<A> {

  private final Supplier<ServiceException> unauthenticatedExceptionSupplier;

  /**
   * Instantiates a new reactive normalized authentication template.
   */
  public ReactiveAuthenticationTemplate() {
    this(null);
  }

  /**
   * Instantiates a new reactive normalized authentication template.
   *
   * @param unauthenticatedExceptionSupplier the unauthenticated exception supplier
   */
  public ReactiveAuthenticationTemplate(
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
  Mono<Authentication> getAuthentication() {
    return ReactiveSecurityContextHolder.getContext()
        .mapNotNull(SecurityContext::getAuthentication)
        .filter(Authentication::isAuthenticated);
  }

  @Override
  public <R> Mono<R> oneWithAuthentication(
      @NonNull Function<A, ? extends Mono<R>> function) {
    //noinspection unchecked
    return getAuthentication()
        .map(auth -> (A) auth)
        .switchIfEmpty(Mono.error(unauthenticatedExceptionSupplier))
        .flatMap(function);
  }

  @Override
  public <R> Mono<R> oneWithOptionalAuthentication(
      @NonNull Function<@Nullable A, ? extends Mono<R>> function) {
    return getAuthentication()
        .defaultIfEmpty(new EmptyAuthentication())
        .flatMap(authentication -> {
          if (authentication instanceof EmptyAuthentication) {
            return function.apply(null);
          }
          //noinspection unchecked
          return function.apply((A) authentication);
        });
  }

  @Override
  public <R> Flux<R> manyWithAuthentication(
      @NonNull Function<A, ? extends Publisher<R>> function) {
    //noinspection unchecked
    return getAuthentication()
        .switchIfEmpty(Mono.error(unauthenticatedExceptionSupplier))
        .flatMapMany(authentication -> function.apply((A) authentication));
  }

  @Override
  public <R> Flux<R> manyWithOptionalAuthentication(
      @NonNull Function<@Nullable A, ? extends Publisher<R>> function) {
    return getAuthentication()
        .defaultIfEmpty(new EmptyAuthentication())
        .flatMapMany(authentication -> {
          if (authentication instanceof EmptyAuthentication) {
            return function.apply(null);
          }
          //noinspection unchecked
          return function.apply((A) authentication);
        });
  }

  private static final class EmptyAuthentication extends AbstractAuthenticationToken {

    /**
     * Instantiates a new empty authentication.
     */
    EmptyAuthentication() {
      super(List.of());
    }

    @Override
    public @Nullable Object getCredentials() {
      return null;
    }

    @Override
    public @Nullable Object getPrincipal() {
      return null;
    }
  }

}
