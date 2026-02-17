package org.bremersee.ldaptive.converter;

import static java.util.Objects.isNull;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.io.Serial;
import org.bremersee.ldaptive.serializable.SerLdapEntry;
import org.ldaptive.LdapEntry;

/**
 * The jackson ldap entry deserializer.
 */
public class JacksonLdapEntryDeserializer extends StdDeserializer<LdapEntry> {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new jackson ldap entry deserializer.
   */
  public JacksonLdapEntryDeserializer() {
    super(LdapEntry.class);
  }

  @Override
  public LdapEntry deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {

    SerLdapEntry serLdapEntry = jsonParser.readValueAs(SerLdapEntry.class);
    return isNull(serLdapEntry) ? null : serLdapEntry.toLdapEntry();
  }
}
