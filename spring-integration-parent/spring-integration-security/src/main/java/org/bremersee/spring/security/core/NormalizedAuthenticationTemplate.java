package org.bremersee.spring.security.core;

import static java.util.Objects.isNull;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import org.bremersee.exception.ServiceException;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * The normalized authentication template.
 */
public class NormalizedAuthenticationTemplate implements NormalizedAuthenticationOperations {

  private final Supplier<ServiceException> unauthenticatedExceptionSupplier;

  /**
   * Instantiates a new normalized authentication template.
   */
  public NormalizedAuthenticationTemplate() {
    this(null);
  }

  /**
   * Instantiates a new normalized authentication template.
   *
   * @param unauthenticatedExceptionSupplier the unauthenticated exception supplier
   */
  public NormalizedAuthenticationTemplate(
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
  Optional<NormalizedAuthentication> getAuthentication() {
    return Optional.of(SecurityContextHolder.getContext())
        .map(SecurityContext::getAuthentication)
        .filter(Authentication::isAuthenticated)
        .filter(NormalizedAuthentication.class::isInstance)
        .map(NormalizedAuthentication.class::cast);
  }

  @Override
  public <R> R doWithAuthentication(@NonNull Function<NormalizedAuthentication, R> function) {
    return getAuthentication()
        .map(function)
        .orElseThrow(unauthenticatedExceptionSupplier);
  }

  @Override
  public <R> ResponseEntity<R> responseWithAuthentication(
      @NonNull Function<NormalizedAuthentication, R> function) {
    return ResponseEntity.ok(doWithAuthentication(function));
  }

}
