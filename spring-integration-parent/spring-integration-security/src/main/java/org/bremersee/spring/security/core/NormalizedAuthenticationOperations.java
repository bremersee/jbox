package org.bremersee.spring.security.core;

import java.util.function.Function;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;

/**
 * The interface Normalized authentication operations.
 */
public interface NormalizedAuthenticationOperations {

  /**
   * Do with authentication.
   *
   * @param <R> the type parameter
   * @param function the function
   * @return the r
   */
  @Nullable <R> R doWithAuthentication(@NonNull Function<NormalizedAuthentication, R> function);

  /**
   * Do with optional authentication r.
   *
   * @param <R> the type parameter
   * @param function the function
   * @return the r
   */
  @Nullable <R> R doWithOptionalAuthentication(
      @NonNull Function<@Nullable NormalizedAuthentication, R> function);

  /**
   * Response with authentication.
   *
   * @param <R> the type parameter
   * @param function the function
   * @return the response entity
   */
  @NonNull <R> ResponseEntity<R> responseWithAuthentication(
      @NonNull Function<NormalizedAuthentication, R> function);

  /**
   * Response with optional authentication response entity.
   *
   * @param <R> the type parameter
   * @param function the function
   * @return the response entity
   */
  @NonNull <R> ResponseEntity<R> responseWithOptionalAuthentication(
      @NonNull Function<@Nullable NormalizedAuthentication, R> function);
}
