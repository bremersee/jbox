package org.bremersee.ldaptive.converter;

import static java.util.Objects.isNull;

import org.bremersee.ldaptive.serializable.SerLdapEntry;
import org.ldaptive.LdapEntry;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.deser.std.StdDeserializer;

/**
 * The jackson ldap entry deserializer.
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
