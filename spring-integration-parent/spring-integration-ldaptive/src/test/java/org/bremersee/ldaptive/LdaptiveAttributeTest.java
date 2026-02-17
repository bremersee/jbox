/*
 * Copyright 2025 the original author or authors.
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

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.ldaptive.AttributeModification;
import org.ldaptive.AttributeModification.Type;
import org.ldaptive.LdapAttribute;
import org.ldaptive.LdapEntry;
import org.ldaptive.transcode.ByteArrayValueTranscoder;
import org.ldaptive.transcode.StringValueTranscoder;
import org.ldaptive.transcode.ValueTranscoder;

/**
 * The ldaptive attribute test.
 */
@ExtendWith(SoftAssertionsExtension.class)
class LdaptiveAttributeTest {

  private static final LdaptiveAttribute<String> target = LdaptiveAttribute.define("junit");

  /**
   * Gets name.
   */
  @Test
  void getName() {
    String actual = target.getName();
    assertThat(actual)
        .isEqualTo("junit");
  }

  /**
   * Is binary.
   */
  @Test
  void isBinary() {
    boolean actual = target.isBinary();
    assertThat(actual)
        .isFalse();
  }

  /**
   * Gets value transcoder.
   */
  @Test
  void getValueTranscoder() {
    ValueTranscoder<String> actual = target.getValueTranscoder();
    assertThat(actual)
        .isInstanceOf(StringValueTranscoder.class);
  }

  /**
   * Exists.
   *
   * @param softly the softly
   */
  @Test
  void exists(SoftAssertions softly) {
    boolean actual = target.exists(null);
    softly
        .assertThat(actual)
        .isFalse();

    LdapEntry ldapEntry = new LdapEntry();
    actual = target.exists(ldapEntry);
    softly
        .assertThat(actual)
        .isFalse();

    ldapEntry.addAttributes(LdapAttribute.builder()
        .name("junit")
        .values("test")
        .build());
    actual = target.exists(ldapEntry);
    softly
        .assertThat(actual)
        .isTrue();
  }

  /**
   * Gets value.
   *
   * @param softly the softly
   */
  @Test
  void getValue(SoftAssertions softly) {
    LdapEntry ldapEntry = new LdapEntry();
    Optional<String> actual = target.getValue(ldapEntry);
    softly
        .assertThat(actual)
        .isEmpty();

    ldapEntry.addAttributes(LdapAttribute.builder()
        .name("junit")
        .values("test")
        .build());
    actual = target.getValue(ldapEntry);
    softly
        .assertThat(actual)
        .hasValue("test");
  }

  /**
   * Gets value with default.
   */
  @Test
  void getValueWithDefault() {
    LdapEntry ldapEntry = new LdapEntry();
    Optional<String> actual = target.getValue(ldapEntry, "test");
    assertThat(actual)
        .hasValue("test");
  }

  /**
   * Gets values.
   *
   * @param softly the softly
   */
  @Test
  void getValues(SoftAssertions softly) {
    LdapEntry ldapEntry = new LdapEntry();
    Stream<String> actual = target.getValues(ldapEntry);
    softly
        .assertThat(actual)
        .isEmpty();

    ldapEntry.addAttributes(LdapAttribute.builder()
        .name("junit")
        .values("test1", "test2")
        .build());
    actual = target.getValues(ldapEntry);
    softly
        .assertThat(actual)
        .containsExactly("test1", "test2");
  }

  /**
   * Sets value.
   *
   * @param softly the softly
   */
  @Test
  void setValue(SoftAssertions softly) {
    LdapEntry ldapEntry = new LdapEntry();
    AttributeModification actual = target.setValue(ldapEntry, "test").orElse(null);
    AttributeModification expected = new AttributeModification(
        Type.ADD,
        LdapAttribute.builder()
            .name("junit")
            .values("test")
            .build());
    softly
        .assertThat(actual)
        .usingRecursiveComparison()
        .isEqualTo(expected);

    LdapAttribute junit = ldapEntry.getAttribute("junit");
    softly
        .assertThat(junit)
        .extracting(LdapAttribute::getStringValue)
        .isEqualTo("test");
  }

  /**
   * Replace value.
   *
   * @param softly the softly
   */
  @Test
  void replaceValue(SoftAssertions softly) {
    LdapEntry ldapEntry = new LdapEntry();
    ldapEntry.addAttributes(LdapAttribute.builder()
        .name("junit")
        .values("previous test")
        .build());
    AttributeModification actual = target.setValue(ldapEntry, "test").orElse(null);
    AttributeModification expected = new AttributeModification(
        Type.REPLACE,
        LdapAttribute.builder()
            .name("junit")
            .values("test")
            .build());
    softly
        .assertThat(actual)
        .usingRecursiveComparison()
        .isEqualTo(expected);

    LdapAttribute junit = ldapEntry.getAttribute("junit");
    softly
        .assertThat(junit)
        .extracting(LdapAttribute::getStringValue)
        .isEqualTo("test");

    actual = target.setValue(ldapEntry, "test").orElse(null);
    softly
        .assertThat(actual)
        .isNull();
  }

  /**
   * Delete value.
   *
   * @param softly the softly
   */
  @Test
  void deleteValue(SoftAssertions softly) {
    LdapEntry ldapEntry = new LdapEntry();
    ldapEntry.addAttributes(LdapAttribute.builder()
        .name("junit")
        .values("test")
        .build());
    AttributeModification actual = target.setValue(ldapEntry, "").orElse(null);
    AttributeModification expected = new AttributeModification(
        Type.DELETE,
        LdapAttribute.builder()
            .name("junit")
            .build());
    softly
        .assertThat(actual)
        .usingRecursiveComparison()
        .isEqualTo(expected);

    LdapAttribute junit = ldapEntry.getAttribute("junit");
    softly
        .assertThat(junit)
        .isNull();

    actual = target.setValue(ldapEntry, null).orElse(null);
    softly
        .assertThat(actual)
        .isNull();
  }

  /**
   * Sets value with condition.
   *
   * @param softly the softly
   */
  @Test
  void setValueWithCondition(SoftAssertions softly) {
    LdapEntry ldapEntry = new LdapEntry();
    AttributeModification actual = target
        .setValue(ldapEntry, "test", (oldValue, newValue) -> nonNull(oldValue))
        .orElse(null);
    softly
        .assertThat(actual)
        .isNull();

    actual = target
        .setValue(ldapEntry, "test", (oldValue, newValue) -> isNull(oldValue))
        .orElse(null);
    AttributeModification expected = new AttributeModification(
        Type.ADD,
        LdapAttribute.builder()
            .name("junit")
            .values("test")
            .build());
    softly
        .assertThat(actual)
        .usingRecursiveComparison()
        .isEqualTo(expected);

    actual = target
        .setValue(ldapEntry, "next", null)
        .orElse(null);
    expected = new AttributeModification(
        Type.REPLACE,
        LdapAttribute.builder()
            .name("junit")
            .values("next")
            .build());
    softly
        .assertThat(actual)
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }

  /**
   * Sets values.
   *
   * @param softly the softly
   */
  @Test
  void setValues(SoftAssertions softly) {
    AttributeModification actual = target
        .setValues(null, List.of("test1", "test2"))
        .orElse(null);
    softly
        .assertThat(actual)
        .isNull();

    LdapEntry ldapEntry = new LdapEntry();
    actual = target
        .setValues(ldapEntry, List.of("test1", "test2", "test3"))
        .orElse(null);
    AttributeModification expected = new AttributeModification(
        Type.ADD,
        LdapAttribute.builder()
            .name("junit")
            .values("test1", "test2", "test3")
            .build());
    softly
        .assertThat(actual)
        .usingRecursiveComparison()
        .isEqualTo(expected);

    LdapAttribute junit = ldapEntry.getAttribute("junit");
    softly
        .assertThat(junit)
        .extracting(LdapAttribute::getStringValues)
        .isEqualTo(List.of("test1", "test2", "test3"));

    actual = target
        .setValues(ldapEntry, List.of("test1", "test2", "test3"))
        .orElse(null);
    softly
        .assertThat(actual)
        .isNull();
  }

  /**
   * Replace values.
   *
   * @param softly the softly
   */
  @Test
  void replaceValues(SoftAssertions softly) {
    LdapEntry ldapEntry = new LdapEntry();
    ldapEntry.addAttributes(LdapAttribute.builder()
        .name("junit")
        .values("test")
        .build());

    AttributeModification actual = target
        .setValues(ldapEntry, List.of("test1", "test2"))
        .orElse(null);
    AttributeModification expected = new AttributeModification(
        Type.REPLACE,
        LdapAttribute.builder()
            .name("junit")
            .values("test1", "test2")
            .build());
    softly
        .assertThat(actual)
        .usingRecursiveComparison()
        .isEqualTo(expected);

    LdapAttribute junit = ldapEntry.getAttribute("junit");
    softly
        .assertThat(junit)
        .extracting(LdapAttribute::getStringValues)
        .isEqualTo(List.of("test1", "test2"));

    actual = target
        .setValues(ldapEntry, List.of("test1", "test2"))
        .orElse(null);
    softly
        .assertThat(actual)
        .isNull();
  }

  /**
   * Create attribute.
   *
   * @param softly the softly
   */
  @Test
  void createAttribute(SoftAssertions softly) {
    LdapAttribute actual = target.createAttribute();
    LdapAttribute expected = LdapAttribute.builder()
        .name("junit")
        .build();
    softly
        .assertThat(actual)
        .usingRecursiveComparison()
        .isEqualTo(expected);

    actual = target.createAttribute((String) null);
    expected = LdapAttribute.builder()
        .name("junit")
        .build();
    softly
        .assertThat(actual)
        .usingRecursiveComparison()
        .isEqualTo(expected);

    actual = target.createAttribute(List.of());
    expected = LdapAttribute.builder()
        .name("junit")
        .build();
    softly
        .assertThat(actual)
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }

  /**
   * Create attribute with value.
   */
  @Test
  void createAttributeWithValue() {
    LdapAttribute actual = target.createAttribute("test");
    LdapAttribute expected = LdapAttribute.builder()
        .name("junit")
        .values("test")
        .build();
    assertThat(actual)
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }

  /**
   * Create attribute with values.
   */
  @Test
  void createAttributeWithValues() {
    LdapAttribute actual = target.createAttribute(List.of("test1", "test2"));
    LdapAttribute expected = LdapAttribute.builder()
        .name("junit")
        .values("test1", "test2")
        .build();
    assertThat(actual)
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }

  /**
   * Define binary.
   *
   * @param softly the softly
   */
  @Test
  void defineBinary(SoftAssertions softly) {
    LdaptiveAttribute<byte[]> binTarget = LdaptiveAttribute
        .define("junit", true, new ByteArrayValueTranscoder());
    softly
        .assertThat(binTarget.isBinary())
        .isTrue();

    byte[] value = "test".getBytes(StandardCharsets.UTF_8);
    LdapEntry ldapEntry = new LdapEntry();
    ldapEntry.addAttributes(LdapAttribute.builder()
        .name("junit")
        .binary(true)
        .values(value)
        .build());

    Optional<byte[]> actual = binTarget.getValue(ldapEntry);
    softly
        .assertThat(actual)
        .hasValue(value);
  }

}