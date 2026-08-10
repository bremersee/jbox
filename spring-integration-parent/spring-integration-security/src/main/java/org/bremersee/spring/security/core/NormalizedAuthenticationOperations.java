package org.bremersee.spring.security.core;

import java.util.function.Function;
import org.jspecify.annotations.NonNull;
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
  <R> R doWithAuthentication(@NonNull Function<NormalizedAuthentication, R> function);

  /**
   * Response with authentication.
   *
   * @param <R> the type parameter
   * @param function the function
   * @return the response entity
   */
  <R> ResponseEntity<R> responseWithAuthentication(
      @NonNull Function<NormalizedAuthentication, R> function);

}
