package org.bremersee.ldaptive.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.io.Serial;
import org.bremersee.ldaptive.transcoder.ValueTranscoderFactory;
import org.ldaptive.dn.Dn;

/**
 * The jackson distinguished name deserializer.
 */
public class JacksonDnDeserializer extends StdDeserializer<Dn> {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new jackson distinguished name deserializer.
   */
  public JacksonDnDeserializer() {
    super(Dn.class);
  }

  @Override
  public Dn deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {

    String source = jsonParser.readValueAs(String.class);
    return ValueTranscoderFactory.getDnValueTranscoder().decodeStringValue(source);
  }
}
