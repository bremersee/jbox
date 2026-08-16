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
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

/**
 * The jackson ldap entry serializer.
 *
 * @author Christian Bremer
 */
public class JacksonLdapEntrySerializer extends StdSerializer<LdapEntry> {

  /**
   * Instantiates a new jackson ldap entry serializer.
   */
  public JacksonLdapEntrySerializer() {
    super(LdapEntry.class);
  }

  @Override
  public void serialize(LdapEntry ldapEntry, JsonGenerator jsonGenerator,
      SerializationContext serializationContext) {

    if (isNull(ldapEntry)) {
      jsonGenerator.writeNull();
    } else {
      jsonGenerator.writePOJO(new SerLdapEntry(ldapEntry));
    }
  }
}
