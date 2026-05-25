/*
 * Copyright 2015-2022 the original author or authors.
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

package org.bremersee.geojson.converter.serialization;

import org.locationtech.jts.geom.Geometry;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

/**
 * A Jackson serializer for a {@link Geometry}.
 *
 * @author Christian Bremer
 */
public class JacksonGeometrySerializer extends StdSerializer<Geometry> {

  /**
   * The geometry to json converter.
   */
  private final GeometryToJsonConverter converter;

  /**
   * Instantiates a new Jackson geometry serializer.
   */
  public JacksonGeometrySerializer() {
    this(false, false);
  }

  /**
   * Instantiates a new Jackson geometry serializer.
   *
   * @param withBoundingBox the with bounding box
   * @param useBigDecimal the use big decimal
   */
  public JacksonGeometrySerializer(boolean withBoundingBox, boolean useBigDecimal) {
    super(Geometry.class);
    this.converter = new GeometryToJsonConverter(withBoundingBox, useBigDecimal);
  }

  @Override
  public void serialize(
      Geometry value,
      JsonGenerator jgen,
      SerializationContext serializationContext) {

    if (value == null) {
      jgen.writeNull();
    } else {
      jgen.writePOJO(converter.convert(value));
    }
  }

}
