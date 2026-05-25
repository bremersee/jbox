package org.bremersee.ldaptive.converter;

import static java.util.Objects.isNull;

import org.bremersee.ldaptive.serializable.SerLdapEntry;
import org.ldaptive.LdapEntry;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

/**
 * The jackson ldap entry serializer.
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
