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

package org.bremersee.spring.security.core.mapping.authority;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.bremersee.spring.security.core.mapping.AbstractNormalizedMapper;
import org.bremersee.spring.security.core.mapping.CaseTransformation;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;

/**
 * The normalized mapper of granted authorities.
 *
 * @author Christian Bremer
 */
public class NormalizedGrantedAuthoritiesMapper extends AbstractNormalizedMapper<GrantedAuthority>
    implements GrantedAuthoritiesMapper {

  /**
   * Instantiates a new normalized mapper of granted authorities.
   *
   * @param defaultRoles the default roles
   * @param roleMapping the role mapping
   * @param rolePrefix the role prefix
   * @param roleCaseTransformation the role case transformation
   * @param roleStringReplacements the role string replacements
   */
  public NormalizedGrantedAuthoritiesMapper(
      List<String> defaultRoles,
      Map<String, String> roleMapping,
      String rolePrefix,
      CaseTransformation roleCaseTransformation,
      Map<String, String> roleStringReplacements) {
    super(defaultRoles, roleMapping, rolePrefix, roleCaseTransformation, roleStringReplacements);
  }

  @Override
  protected @NonNull String getStringValue(@NonNull GrantedAuthority value) {
    String authority = value.getAuthority();
    return Objects.isNull(authority) ? "" : authority;
  }

  @Override
  protected @NonNull GrantedAuthority createTarget(@NonNull String mappedValue) {
    return new SimpleGrantedAuthority(mappedValue);
  }

  @Override
  protected @NonNull String getDefaultPrefix() {
    return "ROLE_";
  }

  @NonNull
  @Override
  public Collection<GrantedAuthority> mapAuthorities(
      @NonNull Collection<? extends GrantedAuthority> authorities) {

    return map(authorities);
  }

}
