/*
 * Copyright 2014 the original author or authors.
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

package org.bremersee.spring.security.ldaptive.authentication.provider;

import java.time.OffsetDateTime;
import java.util.Optional;
import org.bremersee.ldaptive.transcoder.UserAccountControl;
import org.bremersee.ldaptive.transcoder.ValueTranscoderFactory;
import org.bremersee.spring.security.ldaptive.authentication.AccountControlEvaluator;
import org.ldaptive.LdapAttribute;
import org.ldaptive.LdapEntry;

/**
 * Evaluator of the ldap attributes {@code userAccountControl} and {@code accountExpires} of an
 * Active Directory.
 *
 * @author Christian Bremer
 */
public class ActiveDirectoryAccountControlEvaluator implements AccountControlEvaluator {

  @Override
  public boolean isAccountNonExpired(LdapEntry ldapEntry) {
    var valueTranscoder = ValueTranscoderFactory.getFileTimeToOffsetDateTimeValueTranscoder();
    return Optional.ofNullable(ldapEntry)
        .map(entry -> entry.getAttribute("accountExpires"))
        .map(LdapAttribute::getStringValue)
        .map(valueTranscoder::decodeStringValue)
        .map(dateTime -> dateTime.isAfter(OffsetDateTime.now()))
        .orElse(true);
  }

  @Override
  public boolean isAccountNonLocked(LdapEntry ldapEntry) {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired(LdapEntry ldapEntry) {
    return true;
  }

  @Override
  public boolean isEnabled(LdapEntry ldapEntry) {
    var valueTranscoder = ValueTranscoderFactory.getUserAccountControlValueTranscoder();
    return Optional.ofNullable(ldapEntry)
        .map(entry -> entry.getAttribute("userAccountControl"))
        .map(LdapAttribute::getStringValue)
        .map(valueTranscoder::decodeStringValue)
        .map(UserAccountControl::isEnabled)
        .orElse(true);
  }
}
