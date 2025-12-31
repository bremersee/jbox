package org.bremersee.ldaptive.converter;

import static java.util.Objects.isNull;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import org.bremersee.ldaptive.serializable.SerLdapEntry;
import org.ldaptive.LdapEntry;

/**
 * The jackson ldap entry serializer.
 */
public class JacksonLdapEntrySerializer extends StdSerializer<LdapEntry> {

  /**
   * Instantiates a new jackson ldap entry serializer.
   */
  public JacksonLdapEntrySerializer() {
    super(LdapEntry.class, false);
  }

  @Override
  public void serialize(LdapEntry ldapEntry, JsonGenerator jsonGenerator,
      SerializerProvider serializerProvider) throws IOException {

    if (isNull(ldapEntry)) {
      jsonGenerator.writeNull();
    } else {
      jsonGenerator.writeObject(new SerLdapEntry(ldapEntry));
    }
  }
}
