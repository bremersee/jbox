/*
 * Copyright 2019 the original author or authors.
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

package org.bremersee.ldaptive;

import java.util.Optional;
import org.ldaptive.AttributeModification;
import org.ldaptive.LdapEntry;
import org.ldaptive.ModifyRequest;
import org.ldaptive.beans.LdapEntryMapper;

/**
 * The ldap entry mapper.
 *
 * @param <T> the type of the domain object
 * @author Christian Bremer
 */
public interface LdaptiveEntryMapper<T> extends LdapEntryMapper<T> {

  /**
   * Get object classes of the ldap entry. The object classes are only required, if a new ldap entry
   * should be persisted.
   *
   * @return the object classes of the ldap entry
   */
  String[] getObjectClasses();

  /**
   * Get mapped attribute names.
   *
   * @return the mapped attribute names
   */
  default String[] getMappedAttributeNames() {
    return new String[0];
  }

  /**
   * Get binary attribute names.
   *
   * @return the binary attribute names
   */
  default String[] getBinaryAttributeNames() {
    return new String[0];
  }

  @Override
  String mapDn(T domainObject);

  /**
   * Map a ldap entry into a domain object.
   *
   * @param ldapEntry the ldap entry
   * @return the domain object
   */
  T map(LdapEntry ldapEntry);

  @Override
  void map(LdapEntry source, T destination);

  @Override
  default void map(T source, LdapEntry destination) {
    mapAndComputeModifications(source, destination);
  }

  /**
   * Map and compute attribute modifications (see
   * {@link LdapEntry#computeModifications(LdapEntry, LdapEntry)}**).
   *
   * @param source the source (domain object); required
   * @param destination the destination (ldap entry); required
   * @return the attribute modifications
   */
  AttributeModification[] mapAndComputeModifications(
      T source,
      LdapEntry destination);

  /**
   * Map and compute modify request.
   *
   * @param source the source (domain object); required
   * @param destination the destination (ldap entry); required
   * @return the modify request
   */
  default Optional<ModifyRequest> mapAndComputeModifyRequest(
      T source,
      LdapEntry destination) {

    return Optional.ofNullable(mapAndComputeModifications(source, destination))
        .filter(mods -> mods.length > 0)
        .map(mods -> new ModifyRequest(destination.getDn(), mods));
  }

}
