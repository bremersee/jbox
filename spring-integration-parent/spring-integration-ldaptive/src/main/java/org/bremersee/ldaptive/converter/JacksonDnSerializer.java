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

import org.bremersee.ldaptive.transcoder.ValueTranscoderFactory;
import org.ldaptive.dn.Dn;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

/**
 * The jackson distinguished name serializer.
 *
 * @author Christian Bremer
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
