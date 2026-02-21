/*
 * Copyright 2024 the original author or authors.
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

package org.bremersee.spring.security.ldaptive.userdetails;

import java.util.Collection;
import java.util.List;
import org.bremersee.spring.security.core.NormalizedPrincipal;
import org.immutables.serial.Serial;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * The ldaptive user details.
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
public interface LdaptiveUserDetails extends UserDetails, NormalizedPrincipal {

  /**
   * Gets the distinguished name.
   *
   * @return the distinguished name
   */
  String getDn();

  @Value.Default
  @Override
  default Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of();
  }

  @Nullable
  @Override
  String getPassword();

  @Override
  String getUsername();

  @Value.Default
  @Override
  default boolean isAccountNonExpired() {
    return true;
  }

  @Value.Default
  @Override
  default boolean isAccountNonLocked() {
    return true;
  }

  @Value.Default
  @Override
  default boolean isCredentialsNonExpired() {
    return true;
  }

  @Value.Default
  @Override
  default boolean isEnabled() {
    return true;
  }

  @Value.Lazy
  @Override
  default String getName() {
    return getUsername();
  }

  @Nullable
  @Override
  String getFirstName();

  @Nullable
  @Override
  String getLastName();

  @Nullable
  @Override
  String getEmail();

  static Builder builder() {
    return new Builder();
  }

  class Builder extends ImmutableLdaptiveUserDetails.Builder {

  }

}
