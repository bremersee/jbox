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

import java.util.function.Function;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.reactivestreams.Publisher;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The reactive normalized authentication operations.
 *
 * @author Christian Bremer
 */
public interface ReactiveAuthenticationOperations<A extends Authentication> {

  /**
   * One with authentication.
   *
   * @param <R> the type parameter
   * @param function the function
   * @return the mono
   */
  <R> Mono<R> oneWithAuthentication(
      @NonNull Function<A, ? extends Mono<R>> function);

  /**
   * One with optional authentication mono.
   *
   * @param <R> the type parameter
   * @param function the function
   * @return the mono
   */
  <R> Mono<R> oneWithOptionalAuthentication(
      @NonNull Function<@Nullable A, ? extends Mono<R>> function);

  /**
   * Many with authentication.
   *
   * @param <R> the type parameter
   * @param function the function
   * @return the flux
   */
  <R> Flux<R> manyWithAuthentication(
      @NonNull Function<A, ? extends Publisher<R>> function);

  /**
   * Many with optional authentication flux.
   *
   * @param <R> the type parameter
   * @param function the function
   * @return the flux
   */
  <R> Flux<R> manyWithOptionalAuthentication(
      @NonNull Function<@Nullable A, ? extends Publisher<R>> function);

}
