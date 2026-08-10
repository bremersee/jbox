package org.bremersee.spring.security.core;

import static java.util.Objects.isNull;

import java.util.function.Function;
import java.util.function.Supplier;
import org.bremersee.exception.ServiceException;
import org.jspecify.annotations.NonNull;
import org.reactivestreams.Publisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The reactive normalized authentication template.
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
  public <R> Flux<R> manyWithAuthentication(
      @NonNull Function<NormalizedAuthentication, ? extends Publisher<R>> function) {
    return getAuthentication()
        .switchIfEmpty(Mono.error(unauthenticatedExceptionSupplier))
        .flatMapMany(function);
  }
}
