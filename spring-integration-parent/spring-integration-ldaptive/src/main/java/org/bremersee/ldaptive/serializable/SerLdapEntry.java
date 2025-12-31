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

package org.bremersee.ldaptive.serializable;

import static java.util.Objects.nonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.ldaptive.LdapEntry;

/**
 * The serializable ldap entry.
 *
 * @author Christian Bremer
 */
@Getter
@ToString
@EqualsAndHashCode
public class SerLdapEntry implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * The distinguished name.
   */
  @JsonProperty(value = "dn")
  private final String dn;

  /**
   * The attributes.
   */
  @JsonProperty(value = "attributes")
  private final Collection<SerLdapAttr> attributes;

  /**
   * Instantiates a new serializable ldap entry.
   *
   * @param ldapEntry the ldap entry
   */
  public SerLdapEntry(LdapEntry ldapEntry) {
    this.dn = Optional.ofNullable(ldapEntry)
        .map(LdapEntry::getDn)
        .orElse(null);
    this.attributes = Stream.ofNullable(ldapEntry)
        .map(LdapEntry::getAttributes)
        .flatMap(Collection::stream)
        .filter(Objects::nonNull)
        .map(SerLdapAttr::new)
        .toList();
  }

  /**
   * Instantiates a new Ser ldap entry.
   *
   * @param dn the dn
   * @param attributes the attributes
   */
  @JsonCreator
  public SerLdapEntry(
      @JsonProperty(value = "dn") String dn,
      @JsonProperty(value = "attributes") List<SerLdapAttr> attributes) {
    this.dn = dn;
    this.attributes = attributes;
  }

  /**
   * To ldap entry.
   *
   * @return the ldap entry
   */
  public LdapEntry toLdapEntry() {
    LdapEntry ldapEntry = new LdapEntry();
    if (nonNull(dn)) {
      ldapEntry.setDn(dn);
    }
    if (nonNull(attributes) && !attributes.isEmpty()) {
      ldapEntry.addAttributes(attributes
          .stream()
          .map(SerLdapAttr::toLdapAttribute)
          .toList());
    }
    return ldapEntry;
  }

  /**
   * Find attribute.
   *
   * @param attributeName the attribute name
   * @return the optional attribute
   */
  public Optional<SerLdapAttr> findAttribute(String attributeName) {
    return Optional.ofNullable(attributeName)
        .flatMap(name -> Stream.ofNullable(attributes)
            .flatMap(Collection::stream)
            .filter(Objects::nonNull)
            .filter(attr -> name.equalsIgnoreCase(attr.getAttributeName()))
            .findFirst());
  }

}
