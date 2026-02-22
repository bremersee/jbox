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
import java.util.Set;
import java.util.stream.Collectors;
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
 * The test group mapper.
 *
 * @author Christian Bremer
 */
@Component
public class GroupMapper implements LdaptiveEntryMapper<Group> {

  @Value("${spring.ldap.embedded.base-dn}")
  private String baseDn;

  private String getBaseDn() {
    return "ou=groups," + baseDn;
  }

  @Override
  public String[] getObjectClasses() {
    return new String[]{"top", "groupOfUniqueNames"};
  }

  @Override
  public String mapDn(Group group) {
    if (group == null || !StringUtils.hasText(group.getCn())) {
      return null;
    }
    return Dn.builder()
        .add(new RDn(new NameValue("cn", group.getCn())))
        .add(new Dn(getBaseDn()))
        .build()
        .format();
  }

  @Override
  public Group map(LdapEntry ldapEntry) {
    if (ldapEntry == null) {
      return null;
    }
    Group group = new Group();
    map(ldapEntry, group);
    return group;
  }

  @Override
  public void map(LdapEntry ldapEntry, Group group) {
    LdaptiveAttribute.define("cn").getValue(ldapEntry)
        .ifPresent(group::setCn);
    LdaptiveAttribute.define("ou").getValue(ldapEntry)
        .ifPresent(group::setOu);
    Set<String> members = LdaptiveAttribute.define("uniqueMember")
        .getValues(ldapEntry)
        .collect(Collectors.toSet());
    group.setMembers(members);
  }

  @Override
  public AttributeModification[] mapAndComputeModifications(Group source, LdapEntry destination) {
    List<AttributeModification> modifications = new ArrayList<>();
    LdaptiveAttribute.define("cn").setValue(destination, source.getCn())
        .ifPresent(modifications::add);
    LdaptiveAttribute.define("ou").setValue(destination, source.getOu())
        .ifPresent(modifications::add);
    LdaptiveAttribute.define("uniqueMember").setValues(destination, source.getMembers())
        .ifPresent(modifications::add);
    return modifications.toArray(new AttributeModification[0]);
  }

}
