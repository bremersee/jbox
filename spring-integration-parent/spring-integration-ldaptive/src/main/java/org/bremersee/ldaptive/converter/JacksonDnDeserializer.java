package org.bremersee.ldaptive.converter;

//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import org.bremersee.ldaptive.transcoder.ValueTranscoderFactory;
import org.ldaptive.dn.Dn;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.deser.std.StdDeserializer;

/**
 * The jackson distinguished name deserializer.
 */
public class JacksonDnDeserializer extends StdDeserializer<Dn> {

  /**
   * Instantiates a new jackson distinguished name deserializer.
   */
  public JacksonDnDeserializer() {
    super(Dn.class);
  }

  @Override
  public Dn deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {

    String source = jsonParser.readValueAs(String.class);
    return ValueTranscoderFactory.getDnValueTranscoder().decodeStringValue(source);
  }
}
