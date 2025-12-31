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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.ToString;
import org.ldaptive.LdapAttribute;
import org.ldaptive.LdapUtils;

/**
 * A serializable ldap attribute.
 *
 * @author Christian Bremer
 */
@ToString(onlyExplicitlyIncluded = true)
public class SerLdapAttr implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * Attribute name.
   */
  @JsonProperty(value = "name", required = true)
  @ToString.Include
  @Getter
  private final String attributeName;

  /**
   * Attribute values.
   */
  @JsonIgnore
  private final Collection<byte[]> attributeValues;

  /**
   * Whether this attribute is binary and string representations should be base64 encoded.
   */
  @JsonProperty(value = "binary", required = true)
  @ToString.Include
  @Getter
  private final boolean binary;

  /**
   * Instantiates a new serializable ldap attribute.
   *
   * @param ldapAttribute the ldap attribute
   */
  public SerLdapAttr(LdapAttribute ldapAttribute) {
    this.attributeName = ldapAttribute.getName();
    this.attributeValues = ldapAttribute.getBinaryValues();
    this.binary = ldapAttribute.isBinary();
  }

  /**
   * Instantiates a new Ser ldap attr.
   *
   * @param name the name
   * @param binary the binary
   * @param values the values
   */
  @JsonCreator
  public SerLdapAttr(
      @JsonProperty(value = "name", required = true) String name,
      @JsonProperty(value = "binary", required = true) boolean binary,
      @JsonProperty(value = "values") List<String> values) {
    this.attributeName = name;
    this.binary = binary;
    this.attributeValues = Stream.ofNullable(values)
        .flatMap(Collection::stream)
        .filter(Objects::nonNull)
        .map(this::toByteArray)
        .filter(bytes -> bytes.length > 0)
        .toList();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SerLdapAttr that = (SerLdapAttr) o;
    return binary == that.binary
        && Objects.equals(attributeName.toLowerCase(), that.attributeName.toLowerCase())
        && attributeValues.size() == that.attributeValues.size()
        && attributeValues.stream().allMatch(that::hasValue);
  }

  @Override
  public int hashCode() {
    int hash = 10223;
    int index = 1;
    for (byte[] b : attributeValues) {
      hash = hash * 113 + Arrays.hashCode(b) + index++;
    }
    return hash * 113 + Objects.hash(attributeName, binary);
  }

  /**
   * Returns the number of values in this ldap attribute.
   *
   * @return number of values in this ldap attribute
   */
  public int size() {
    return attributeValues.size();
  }

  /**
   * Returns a single string value of this attribute.
   *
   * @return single string attribute value or null if this attribute is empty
   */
  @JsonIgnore
  public String getStringValue() {
    if (attributeValues.isEmpty()) {
      return null;
    }
    final byte[] val = attributeValues.iterator().next();
    return binary ? LdapUtils.base64Encode(val) : LdapUtils.utf8Encode(val);
  }


  /**
   * Returns the values of this attribute as strings. Binary data is base64 encoded. The return
   * collection cannot be modified.
   *
   * @return collection of string attribute values
   */
  @JsonProperty(value = "values")
  @ToString.Include
  public Collection<String> getStringValues() {
    return attributeValues.stream()
        .map(v -> {
          if (binary) {
            return LdapUtils.base64Encode(v);
          }
          return LdapUtils.utf8Encode(v, false);
        })
        .toList();
  }

  /**
   * Returns a single byte array value of this attribute.
   *
   * @return single byte array attribute value or null if this attribute is empty
   */
  @JsonIgnore
  public byte[] getBinaryValue() {
    return attributeValues.isEmpty() ? null : attributeValues.iterator().next();
  }

  /**
   * Returns the values of this attribute as byte arrays. The return collection cannot be modified.
   *
   * @return collection of string attribute values
   */
  @JsonIgnore
  public Collection<byte[]> getBinaryValues() {
    return attributeValues.stream().toList();
  }

  /**
   * Returns whether the supplied value exists in this attribute.
   *
   * @param value to find
   * @return whether value exists
   */
  public boolean hasValue(byte[] value) {
    return attributeValues.stream()
        .anyMatch(attrValueBytes -> Arrays.equals(attrValueBytes, value));
  }

  /**
   * Returns whether the supplied value exists in this attribute.
   *
   * @param value to find
   * @return whether value exists
   */
  public boolean hasValue(String value) {
    return attributeValues.stream()
        .anyMatch(attrValueBytes -> {
          byte[] valueBytes = toByteArray(value);
          return valueBytes.length > 0 && Arrays.equals(valueBytes, attrValueBytes);
        });
  }

  /**
   * To ldap attribute.
   *
   * @return the ldap attribute
   */
  public LdapAttribute toLdapAttribute() {
    LdapAttribute ldapAttribute = new LdapAttribute();
    if (nonNull(attributeName)) {
      ldapAttribute.setName(attributeName);
    }
    ldapAttribute.setBinary(binary);
    if (nonNull(attributeValues) && !attributeValues.isEmpty()) {
      ldapAttribute.addBinaryValues(attributeValues);
    }
    return ldapAttribute;
  }

  private byte[] toByteArray(String value) {
    if (binary) {
      try {
        return LdapUtils.base64Decode(value);
      } catch (IllegalArgumentException e) {
        return new byte[0];
      }
    }
    return LdapUtils.utf8Encode(value, false);
  }

}
