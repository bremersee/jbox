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

import org.immutables.value.Value;
import org.jspecify.annotations.NonNull;
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
public interface GetGroupsParameters {

  /**
   * Boolean which defines whether brief representations are returned (default: false).
   *
   * @return the brief representation
   */
  @Nullable Boolean getBriefRepresentation();

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
   * Maximum results size (defaults to 100).
   *
   * @return the max
   */
  @Nullable Integer getMax();

  /**
   * Gets populate hierarchy.
   *
   * @return the populate hierarchy
   */
  @Nullable Boolean getPopulateHierarchy();

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
   * Gets subgroups count.
   *
   * @return the subgroups count
   */
  @Nullable Boolean getSubGroupsCount();

  /**
   * Default get user parameters.
   *
   * @return the get user parameters
   */
  @NonNull
  static GetGroupsParameters defaults() {
    return GetGroupsParameters.builder().build();
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
  class Builder extends ImmutableGetGroupsParameters.Builder {

  }
}
