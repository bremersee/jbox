package org.bremersee.ldaptive.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import org.bremersee.ldaptive.transcoder.ValueTranscoderFactory;
import org.ldaptive.dn.Dn;

/**
 * The jackson distinguished name serializer.
 */
public class JacksonDnSerializer extends StdSerializer<Dn> {

  /**
   * Instantiates a new jackson distinguished name serializer.
   */
  public JacksonDnSerializer() {
    super(Dn.class, false);
  }

  @Override
  public void serialize(Dn dn, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
      throws IOException {

    jsonGenerator.writeString(ValueTranscoderFactory
        .getDnValueTranscoderCaseSensitive().encodeStringValue(dn));
  }
}
