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

package org.bremersee.keycloak.api;

import java.time.OffsetDateTime;
import org.immutables.value.Value;
import org.jspecify.annotations.Nullable;

/**
 * The get user parameters.
 *
 * @author Christian Bremer
 */
@Value.Style(
    visibility = Value.Style.ImplementationVisibility.PACKAGE,
    overshadowImplementation = true,
    depluralize = true,
    jdk9Collections = true,
    get = {"get*", "is*"},
    withUnaryOperator = "with*")
@Value.Immutable
public interface GetUsersParameters {

  /**
   * Boolean which defines whether brief representations are returned (default: false).
   *
   * @return the brief representation
   */
  @Nullable Boolean getBriefRepresentation();

  /**
   * Only return users created after (inclusive) the given date.
   *
   * @return the created after
   */
  @Nullable OffsetDateTime getCreatedAfter();

  /**
   * Gets created after as epoch second string.
   *
   * @return the created after as string
   */
  @Value.Auxiliary
  @Nullable
  default String getCreatedAfterAsString() {
    return getCreatedAfter() != null ? String.valueOf(getCreatedAfter().toEpochSecond()) : null;
  }

  /**
   * Only return users created before (inclusive) the given date.
   *
   * @return the created before
   */
  @Nullable OffsetDateTime getCreatedBefore();

  /**
   * Gets created before as epoch second string.
   *
   * @return the created before as string
   */
  @Value.Auxiliary
  @Nullable
  default String getCreatedBeforeAsString() {
    return getCreatedBefore() != null ? String.valueOf(getCreatedBefore().toEpochSecond()) : null;
  }

  /**
   * A String contained in email, or the complete email, if param "exact" is true.
   *
   * @return the email
   */
  @Nullable String getEmail();

  /**
   * Whether the email has been verified.
   *
   * @return the email verified
   */
  @Nullable Boolean getEmailVerified();

  /**
   * Boolean representing if user is enabled or not.
   *
   * @return the enabled
   */
  @Nullable Boolean getEnabled();

  /**
   * Boolean which defines whether the params "last", "firstName", "email" and "username" must match
   * exactly.
   *
   * @return the exact
   */
  @Nullable Boolean getExact();

  /**
   * Gets pagination offset.
   *
   * @return the first
   */
  @Nullable Integer getFirst();

  /**
   * A String contained in firstName, or the complete firstName, if param "exact" is true.
   *
   * @return the first name
   */
  @Nullable String getFirstName();

  /**
   * The alias of an Identity Provider linked to the user.
   *
   * @return the idp alias
   */
  @Nullable String getIdpAlias();

  /**
   * The userId at an Identity Provider linked to the user.
   *
   * @return the idp user id
   */
  @Nullable String getIdpUserId();

  /**
   * A String contained in lastName, or the complete lastName, if param "exact" is true.
   *
   * @return the last name
   */
  @Nullable String getLastName();

  /**
   * Maximum results size (defaults to 100).
   *
   * @return the max
   */
  @Nullable Integer getMax();

  /**
   * A query to search for custom attributes, in the format 'key1:value2 key2:value2'.
   *
   * @return the query
   */
  @Nullable String getQuery();

  /**
   * A String contained in username, first or last name, or email. Default search behavior is
   * prefix-based (e.g., foo or foo*). Use *foo* for infix search and "foo" for exact search.
   *
   * @return the search
   */
  @Nullable String getSearch();

  /**
   * A String contained in username, or the complete username, if param "exact" is true.
   *
   * @return the username
   */
  @Nullable String getUsername();

  /**
   * Default get user parameters.
   *
   * @return the get user parameters
   */
  static GetUsersParameters defaults() {
    return GetUsersParameters.builder().build();
  }

  /**
   * Creates new builder.
   *
   * @return the builder
   */
  static Builder builder() {
    return new Builder();
  }

  /**
   * The builder.
   */
  class Builder extends ImmutableGetUsersParameters.Builder {

  }
}
