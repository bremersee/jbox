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

import org.immutables.serial.Serial;
import org.immutables.value.Value;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * The normalized user details.
 *
 * @author Christian Bremer
 */
@Value.Style(
    visibility = Value.Style.ImplementationVisibility.PACKAGE,
    overshadowImplementation = true,
    depluralize = true,
    jdk9Collections = true,
    get = {"get*", "is*"})
@Value.Immutable
@Serial.Version(1L)
public interface NormalizedUserDetails extends NormalizedPrincipalDetails {

  @Nullable
  @Override
  String getPassword();

  @Override
  @NullMarked
  String getUsername();

  @Nullable
  @Override
  String getFirstName();

  @Nullable
  @Override
  String getLastName();

  @Nullable
  @Override
  String getEmail();

  /**
   * Returns empty normalized user details.
   *
   * @return the normalized user details
   */
  static NormalizedUserDetails empty() {
    return builder()
        .username("")
        .build();
  }

  /**
   * Returns the builder.
   *
   * @return the builder
   */
  static Builder builder() {
    return new Builder();
  }

  /**
   * The immutable builder.
   */
  class Builder extends ImmutableNormalizedUserDetails.Builder {

    /**
     * Instantiates a new builder.
     */
    public Builder() {
      super();
    }

  }

}
