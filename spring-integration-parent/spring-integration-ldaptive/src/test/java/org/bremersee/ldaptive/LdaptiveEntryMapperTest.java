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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.ldaptive.AttributeModification;
import org.ldaptive.LdapAttribute;
import org.ldaptive.LdapEntry;
import org.ldaptive.ModifyRequest;
import org.ldaptive.dn.Dn;
import org.ldaptive.dn.NameValue;
import org.ldaptive.dn.RDn;
import org.ldaptive.transcode.ByteArrayValueTranscoder;
import org.springframework.util.StringUtils;

/**
 * The ldaptive entry mapper test.
 *
 * @author Christian Bremer
 */
class LdaptiveEntryMapperTest {

  private static final PersonMapper mapper = new PersonMapper();

  /**
   * Gets object classes.
   */
  @Test
  void getObjectClasses() {
    assertArrayEquals(new String[0], mapper.getObjectClasses());
  }

  /**
   * Gets mapped attribute names.
   */
  @Test
  void getMappedAttributeNames() {
    assertArrayEquals(new String[0], mapper.getMappedAttributeNames());
  }

  /**
   * Gets binary attribute names.
   */
  @Test
  void getBinaryAttributeNames() {
    assertArrayEquals(new String[0], mapper.getBinaryAttributeNames());
  }

  /**
   * Map dn.
   */
  @Test
  void mapDn() {
    assertEquals(
        "cn=anna,dc=example,dc=org",
        mapper.mapDn(Person.builder().name("anna").build()));
  }

  /**
   * Map.
   */
  @Test
  void map() {
    LdapEntry entry = new LdapEntry();
    entry.addAttributes(new LdapAttribute("name", "hans"));
    entry.addAttributes(new LdapAttribute(
        "photo",
        "xyz".getBytes(StandardCharsets.UTF_8)));
    entry.addAttributes(new LdapAttribute(
        "mail",
        "hans@example.org", "castorp@example.org"));
    Person person = mapper.map(entry);
    assertNotNull(person);
    assertEquals("hans", person.getName());
    assertArrayEquals("xyz".getBytes(StandardCharsets.UTF_8), person.getPhoto());
    assertTrue(person.getMailAddresses().contains("hans@example.org"));
    assertTrue(person.getMailAddresses().contains("castorp@example.org"));
  }

  /**
   * Map and compute modify request.
   */
  @Test
  void mapAndComputeModifyRequest() {
    Person person = Person.builder()
        .name("hans")
        .photo("zzz".getBytes(StandardCharsets.UTF_8))
        .mailAddresses(Arrays.asList("hans@example.org", "castorp@example.org"))
        .build();
    LdapEntry entry = new LdapEntry();
    entry.setDn("cn=hans,dc=example,dc=org");
    entry.addAttributes(new LdapAttribute("name", "hans"));
    entry.addAttributes(new LdapAttribute(
        "photo",
        "xyz".getBytes(StandardCharsets.UTF_8)));
    entry.addAttributes(new LdapAttribute(
        "mail",
        "hans.castorp@example.org"));
    ModifyRequest request = mapper.mapAndComputeModifyRequest(person, entry).orElse(null);
    assertNotNull(request);
    assertEquals(2, request.getModifications().length);
    assertEquals("hans", entry.getAttribute("name").getStringValue());
    assertArrayEquals(
        "zzz".getBytes(StandardCharsets.UTF_8),
        entry.getAttribute("photo").getBinaryValue());
    assertTrue(entry.getAttribute("mail").getStringValues().contains("hans@example.org"));
    assertTrue(entry.getAttribute("mail").getStringValues().contains("castorp@example.org"));
    assertFalse(entry.getAttribute("mail").getStringValues()
        .contains("hans.castorp@example.org"));
  }

  /**
   * The test person.
   */
  @Data
  @Builder
  static class Person {

    private String name;

    private List<String> mailAddresses;

    private byte[] photo;

  }

  /**
   * The test mapper.
   */
  static class PersonMapper implements LdaptiveEntryMapper<Person> {

    @Override
    public String[] getObjectClasses() {
      return new String[0];
    }

    @Override
    public String mapDn(Person person) {
      if (person == null || !StringUtils.hasText(person.getName())) {
        return null;
      }
      return Dn.builder()
          .add(new RDn(new NameValue("cn", person.getName())))
          .add(new Dn("dc=example,dc=org"))
          .build()
          .format();
    }

    @Override
    public void map(LdapEntry ldapEntry, Person person) {
      person.setMailAddresses(LdaptiveAttribute.define("mail").getValues(ldapEntry).toList());
      LdaptiveAttribute.define("name").getValue(ldapEntry)
          .ifPresent(person::setName);
      LdaptiveAttribute.define("photo", true, new ByteArrayValueTranscoder())
          .getValue(ldapEntry)
          .ifPresent(person::setPhoto);
    }

    @Override
    public Person map(LdapEntry ldapEntry) {
      if (ldapEntry == null) {
        return null;
      }
      Person person = Person.builder().build();
      map(ldapEntry, person);
      return person;
    }

    @Override
    public AttributeModification[] mapAndComputeModifications(
        Person source,
        LdapEntry destination) {
      List<AttributeModification> modifications = new ArrayList<>();
      LdaptiveAttribute.define("name").setValue(destination, source.getName())
          .ifPresent(modifications::add);
      LdaptiveAttribute.define("mail").setValues(destination, source.getMailAddresses())
          .ifPresent(modifications::add);
      LdaptiveAttribute.define("photo", true, new ByteArrayValueTranscoder())
          .setValue(destination, source.getPhoto())
          .ifPresent(modifications::add);
      return modifications.toArray(new AttributeModification[0]);
    }
  }
}
