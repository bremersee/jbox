/*
 * Copyright 2025-2026 the original author or authors.
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

package org.bremersee.ldaptive.converter;

import static java.util.Objects.isNull;

import org.bremersee.ldaptive.serializable.SerLdapEntry;
import org.ldaptive.LdapEntry;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.deser.std.StdDeserializer;

/**
 * The jackson ldap entry deserializer.
 *
 * @author Christian Bremer
 */
public class JacksonLdapEntryDeserializer extends StdDeserializer<LdapEntry> {

  /**
   * Instantiates a new jackson ldap entry deserializer.
   */
  public JacksonLdapEntryDeserializer() {
    super(LdapEntry.class);
  }

  @Override
  public LdapEntry deserialize(
      JsonParser jsonParser,
      DeserializationContext deserializationContext) {

    SerLdapEntry serLdapEntry = jsonParser.readValueAs(SerLdapEntry.class);
    return isNull(serLdapEntry) ? null : serLdapEntry.toLdapEntry();
  }
}
