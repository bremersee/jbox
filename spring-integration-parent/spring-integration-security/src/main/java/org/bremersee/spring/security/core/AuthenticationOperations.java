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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

/**
 * The authentication operations.
 *
 * @param <A> the type parameter
 * @author Christian Bremer
 */
public interface AuthenticationOperations<A extends Authentication> {

  /**
   * Do with authentication.
   *
   * @param <R> the type parameter
   * @param function the function
   * @return the r
   */
  @Nullable <R> R doWithAuthentication(@NonNull Function<A, R> function);

  /**
   * Do with optional authentication r.
   *
   * @param <R> the type parameter
   * @param function the function
   * @return the r
   */
  @Nullable <R> R doWithOptionalAuthentication(
      @NonNull Function<@Nullable A, R> function);

  /**
   * Response with authentication.
   *
   * @param <R> the type parameter
   * @param function the function
   * @return the response entity
   */
  @NonNull <R> ResponseEntity<R> responseWithAuthentication(
      @NonNull Function<A, R> function);

  /**
   * Response with optional authentication response entity.
   *
   * @param <R> the type parameter
   * @param function the function
   * @return the response entity
   */
  @NonNull <R> ResponseEntity<R> responseWithOptionalAuthentication(
      @NonNull Function<@Nullable A, R> function);
}
