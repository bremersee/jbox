/*
 * Copyright 2018-2022 the original author or authors.
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

package org.bremersee.geojson.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bremersee.geojson.GeoJsonConstants;

/**
 * GeoJSON Geometry.
 *
 * @author Christian Bremer
 */
@Schema(description = "GeoJSON Geometry.")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = GeoJsonConstants.TYPE, visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = GeometryCollection.class,
        name = GeoJsonConstants.GEOMETRY_COLLECTION),
    @JsonSubTypes.Type(value = MultiPoint.class, name = GeoJsonConstants.MULTI_POINT),
    @JsonSubTypes.Type(value = MultiLineString.class, name = GeoJsonConstants.MULTI_LINESTRING),
    @JsonSubTypes.Type(value = LineString.class, name = GeoJsonConstants.LINESTRING),
    @JsonSubTypes.Type(value = MultiPolygon.class, name = GeoJsonConstants.MULTI_POLYGON),
    @JsonSubTypes.Type(value = Point.class, name = GeoJsonConstants.POINT),
    @JsonSubTypes.Type(value = Polygon.class, name = GeoJsonConstants.POLYGON),
})
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public abstract class Geometry implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * The type.
   */
  @Setter(AccessLevel.PROTECTED)
  @Hidden
  @JsonIgnore
  TypeEnum type;

  /**
   * The bounding box.
   */
  @Setter
  @Schema(description = "The bounding box.")
  @JsonProperty(GeoJsonConstants.BBOX)
  @JsonInclude(Include.NON_EMPTY)
  BoundingBox bbox;

  /**
   * Instantiates a new geometry.
   *
   * @param bbox the bbox
   */
  protected Geometry(BoundingBox bbox) {
    this.bbox = bbox;
  }

  /**
   * To json map.
   *
   * @return the map
   */
  public Map<String, Object> toJson() {
    Map<String, Object> map = new LinkedHashMap<>();
    map.put(GeoJsonConstants.TYPE, getType().getGeometryType());
    Optional.ofNullable(getBbox())
        .map(BoundingBox::toDoubleArray)
        .map(bb -> Arrays.stream(bb).boxed().collect(Collectors.toList()))
        .ifPresent(bb -> map.put(GeoJsonConstants.BBOX, bb));
    map.put(getType().geometryJsonValueAttribute, getGeometryJsonValue());
    return Collections.unmodifiableMap(map);
  }

  /**
   * Gets geometry json value for json attribute 'coordinates' or 'geometries'
   * (GeometryCollection).
   *
   * @return the geometry json value
   */
  @Schema(hidden = true)
  @JsonIgnore
  abstract Object getGeometryJsonValue();

  /**
   * The geometry type.
   */
  @Getter
  public enum TypeEnum {

    /**
     * Point type enum.
     */
    POINT(GeoJsonConstants.POINT, GeoJsonConstants.COORDINATES),

    /**
     * Multipoint type enum.
     */
    MULTIPOINT(GeoJsonConstants.MULTI_POINT, GeoJsonConstants.COORDINATES),

    /**
     * Linestring type enum.
     */
    LINESTRING(GeoJsonConstants.LINESTRING, GeoJsonConstants.COORDINATES),

    /**
     * Multilinestring type enum.
     */
    MULTILINESTRING(GeoJsonConstants.MULTI_LINESTRING, GeoJsonConstants.COORDINATES),

    /**
     * Polygon type enum.
     */
    POLYGON(GeoJsonConstants.POLYGON, GeoJsonConstants.COORDINATES),

    /**
     * Multipolygon type enum.
     */
    MULTIPOLYGON(GeoJsonConstants.MULTI_POLYGON, GeoJsonConstants.COORDINATES),

    /**
     * Geometrycollection type enum.
     */
    GEOMETRYCOLLECTION(GeoJsonConstants.GEOMETRY_COLLECTION, GeoJsonConstants.GEOMETRIES);

    private final String geometryType;

    private final String geometryJsonValueAttribute;

    TypeEnum(String geometryType, String geometryJsonValueAttribute) {
      this.geometryType = geometryType;
      this.geometryJsonValueAttribute = geometryJsonValueAttribute;
    }

    @Override
    @JsonValue
    public String toString() {
      return getGeometryType();
    }

    /**
     * From value type enum.
     *
     * @param geometryType the geometry type
     * @return the type enum
     */
    @JsonCreator
    public static TypeEnum fromGeometryType(String geometryType) {
      for (TypeEnum b : values()) {
        if (String.valueOf(b.geometryType).equals(geometryType)) {
          return b;
        }
      }
      return null;
    }
  }
}

