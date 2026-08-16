/*
 * Copyright 2024-2026 the original author or authors.
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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.ObjectUtils;

/**
 * The normalized authentication token.
 *
 * @author Christian Bremer
 */
public interface NormalizedAuthentication extends Authentication {

  /**
   * Returns the normalized principal.
   *
   * @return the normalized principal
   */
  @Override
  NormalizedPrincipal getPrincipal();

  /**
   * Gets granted authority names.
   *
   * @return the granted authority names
   */
  @NonNull
  default Set<String> getGrantedAuthorityNames() {
    Collection<? extends GrantedAuthority> authorities = getAuthorities();
    if (ObjectUtils.isEmpty(authorities)) {
      return Set.of();
    }
    return authorities.stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toSet());
  }

  /**
   * Gets groups.
   *
   * @return the groups
   */
  @NonNull
  default Collection<Group> getGroups() {
    return List.of();
  }

  /**
   * Gets group names.
   *
   * @return the group names
   */
  @NonNull
  default Set<String> getGroupNames() {
    Collection<Group> groups = getGroups();
    if (ObjectUtils.isEmpty(groups)) {
      return Set.of();
    }
    return groups.stream()
        .map(Group::getName)
        .collect(Collectors.toSet());
  }

  /**
   * Has granted authority.
   *
   * @param authority the authority
   * @return the boolean
   */
  default boolean hasGrantedAuthority(@Nullable String authority) {
    return Optional.ofNullable(authority)
        .map(a -> getGrantedAuthorityNames().contains(a))
        .orElse(false);
  }

  /**
   * Has any granted authority.
   *
   * @param authorities the authorities
   * @return the boolean
   */
  default boolean hasAnyGrantedAuthority(@Nullable Collection<String> authorities) {
    return Optional.ofNullable(authorities)
        .map(col -> col.stream().anyMatch(this::hasGrantedAuthority))
        .orElse(false);
  }

  /**
   * Has any granted authority.
   *
   * @param authorities the authorities
   * @return the boolean
   */
  default boolean hasAnyGrantedAuthority(@Nullable String... authorities) {
    return Optional.ofNullable(authorities)
        .map(arr -> hasAnyGrantedAuthority(Arrays.asList(arr)))
        .orElse(false);
  }

  /**
   * Is in group.
   *
   * @param groupName the group name
   * @return the boolean
   */
  default boolean isInGroup(@Nullable String groupName) {
    return Optional.ofNullable(groupName)
        .map(group -> getGroupNames().contains(group))
        .orElse(false);
  }

  /**
   * Is in any group.
   *
   * @param groupNames the group names
   * @return the boolean
   */
  default boolean isInAnyGroup(@Nullable Collection<String> groupNames) {
    return Optional.ofNullable(groupNames)
        .map(col -> col.stream().anyMatch(this::isInGroup))
        .orElse(false);
  }

  /**
   * Is in any group.
   *
   * @param groupNames the group names
   * @return the boolean
   */
  default boolean isInAnyGroup(@Nullable String... groupNames) {
    return Optional.ofNullable(groupNames)
        .map(arr -> isInAnyGroup(Arrays.asList(arr)))
        .orElse(false);
  }

}
