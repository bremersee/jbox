package org.bremersee.ldaptive.converter;

import org.bremersee.ldaptive.transcoder.ValueTranscoderFactory;
import org.ldaptive.dn.Dn;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

/**
 * The jackson distinguished name serializer.
 */
public class JacksonDnSerializer extends StdSerializer<Dn> {

  /**
   * Instantiates a new jackson distinguished name serializer.
   */
  public JacksonDnSerializer() {
    super(Dn.class);
  }

  @Override
  public void serialize(
      Dn dn,
      JsonGenerator jsonGenerator,
      SerializationContext serializationContext) {

    jsonGenerator.writeString(ValueTranscoderFactory
        .getDnValueTranscoderCaseSensitive().encodeStringValue(dn));
  }
}
