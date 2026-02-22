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

package org.bremersee.ldaptive.app;

import java.util.ArrayList;
import java.util.List;
import org.bremersee.ldaptive.LdaptiveAttribute;
import org.bremersee.ldaptive.LdaptiveEntryMapper;
import org.ldaptive.AttributeModification;
import org.ldaptive.LdapEntry;
import org.ldaptive.dn.Dn;
import org.ldaptive.dn.NameValue;
import org.ldaptive.dn.RDn;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * The test person mapper.
 *
 * @author Christian Bremer
 */
@Component
public class PersonMapper implements LdaptiveEntryMapper<Person> {

  @Value("${spring.ldap.embedded.base-dn}")
  private String baseDn;

  private String getBaseDn() {
    return "ou=people," + baseDn;
  }

  @Override
  public String[] getObjectClasses() {
    return new String[]{"top", "person", "organizationalPerson", "inetOrgPerson"};
  }

  @Override
  public String mapDn(Person person) {
    if (person == null || !StringUtils.hasText(person.getUid())) {
      return null;
    }
    return Dn.builder()
        .add(new RDn(new NameValue("uid", person.getUid())))
        .add(new Dn(getBaseDn()))
        .build()
        .format();
  }

  @Override
  public Person map(LdapEntry ldapEntry) {
    if (ldapEntry == null) {
      return null;
    }
    Person person = new Person();
    map(ldapEntry, person);
    return person;
  }

  @Override
  public void map(LdapEntry ldapEntry, Person person) {
    LdaptiveAttribute.define("cn").getValue(ldapEntry)
        .ifPresent(person::setCn);
    LdaptiveAttribute.define("uid").getValue(ldapEntry)
        .ifPresent(person::setUid);
    LdaptiveAttribute.define("sn").getValue(ldapEntry)
        .ifPresent(person::setSn);
  }

  @Override
  public AttributeModification[] mapAndComputeModifications(
      Person source,
      LdapEntry destination) {
    List<AttributeModification> modifications = new ArrayList<>();
    LdaptiveAttribute.define("cn").setValue(destination, source.getCn())
        .ifPresent(modifications::add);
    LdaptiveAttribute.define("uid").setValue(destination, source.getUid())
        .ifPresent(modifications::add);
    LdaptiveAttribute.define("sn").setValue(destination, source.getSn())
        .ifPresent(modifications::add);
    return modifications.toArray(new AttributeModification[0]);
  }

}
