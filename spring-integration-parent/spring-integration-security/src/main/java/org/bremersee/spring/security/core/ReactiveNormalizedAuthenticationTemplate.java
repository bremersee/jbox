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

import java.util.function.Function;
import java.util.function.Supplier;
import org.bremersee.exception.ServiceException;
import org.bremersee.spring.security.core.NormalizedAuthentication.EmptyNormalizedAuthentication;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.reactivestreams.Publisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The reactive normalized authentication template.
 *
 * @author Christian Bremer
 */
public class ReactiveNormalizedAuthenticationTemplate
    implements ReactiveNormalizedAuthenticationOperations {

  private final Supplier<ServiceException> unauthenticatedExceptionSupplier;

  /**
   * Instantiates a new reactive normalized authentication template.
   */
  public ReactiveNormalizedAuthenticationTemplate() {
    this(null);
  }

  /**
   * Instantiates a new reactive normalized authentication template.
   *
   * @param unauthenticatedExceptionSupplier the unauthenticated exception supplier
   */
  public ReactiveNormalizedAuthenticationTemplate(
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
  Mono<NormalizedAuthentication> getAuthentication() {
    return ReactiveSecurityContextHolder.getContext()
        .mapNotNull(SecurityContext::getAuthentication)
        .filter(Authentication::isAuthenticated)
        .filter(NormalizedAuthentication.class::isInstance)
        .cast(NormalizedAuthentication.class);
  }

  @Override
  public <R> Mono<R> oneWithAuthentication(
      @NonNull Function<NormalizedAuthentication, ? extends Mono<R>> function) {
    return getAuthentication()
        .switchIfEmpty(Mono.error(unauthenticatedExceptionSupplier))
        .flatMap(function);
  }

  @Override
  public <R> Mono<R> oneWithOptionalAuthentication(
      @NonNull Function<@Nullable NormalizedAuthentication, ? extends Mono<R>> function) {
    return getAuthentication()
        .defaultIfEmpty(new EmptyNormalizedAuthentication())
        .flatMap(authentication -> {
          if (authentication instanceof EmptyNormalizedAuthentication) {
            return function.apply(null);
          }
          return function.apply(authentication);
        });
  }

  @Override
  public <R> Flux<R> manyWithAuthentication(
      @NonNull Function<NormalizedAuthentication, ? extends Publisher<R>> function) {
    return getAuthentication()
        .switchIfEmpty(Mono.error(unauthenticatedExceptionSupplier))
        .flatMapMany(function);
  }

  @Override
  public <R> Flux<R> manyWithOptionalAuthentication(
      @NonNull Function<@Nullable NormalizedAuthentication, ? extends Publisher<R>> function) {
    return getAuthentication()
        .defaultIfEmpty(new EmptyNormalizedAuthentication())
        .flatMapMany(authentication -> {
          if (authentication instanceof EmptyNormalizedAuthentication) {
            return function.apply(null);
          }
          return function.apply(authentication);
        });
  }

}
