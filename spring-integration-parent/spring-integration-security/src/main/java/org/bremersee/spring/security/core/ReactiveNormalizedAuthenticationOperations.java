package org.bremersee.spring.security.core;

import java.util.function.Function;
import org.jspecify.annotations.NonNull;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The reactive normalized authentication operations.
 */
public interface ReactiveNormalizedAuthenticationOperations {

  /**
   * One with authentication.
   *
   * @param <R> the type parameter
   * @param function the function
   * @return the mono
   */
  <R> Mono<R> oneWithAuthentication(
      @NonNull Function<NormalizedAuthentication, ? extends Mono<R>> function);

  /**
   * Many with authentication.
   *
   * @param <R> the type parameter
   * @param function the function
   * @return the flux
   */
  <R> Flux<R> manyWithAuthentication(
      @NonNull Function<NormalizedAuthentication, ? extends Publisher<R>> function);

}
