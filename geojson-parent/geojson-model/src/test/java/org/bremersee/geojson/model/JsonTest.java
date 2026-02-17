package org.bremersee.geojson.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * The json test.
 */
@ExtendWith(SoftAssertionsExtension.class)
class JsonTest {

  private static final ObjectMapper OM = new ObjectMapper();

  /**
   * Point.
   *
   * @param softly the softly
   * @throws JsonProcessingException the json processing exception
   */
  @Test
  void point(SoftAssertions softly) throws JsonProcessingException {
    Position value = new Position(BigDecimal.ZERO, BigDecimal.ZERO);
    Point model = new Point();
    model.setCoordinates(value);

    String json = OM.writeValueAsString(model);
    String expected = "{\"type\":\"Point\",\"coordinates\":[0,0]}";
    softly
        .assertThat(json)
        .isEqualTo(expected);

    Geometry geometry = OM.readValue(json, Geometry.class);
    softly
        .assertThat(geometry)
        .isEqualTo(model);
  }

  /**
   * Line string.
   *
   * @param softly the softly
   * @throws JsonProcessingException the json processing exception
   */
  @Test
  void lineString(SoftAssertions softly) throws JsonProcessingException {
    List<Position> value = Arrays.asList(
        new Position(BigDecimal.ZERO, BigDecimal.ZERO),
        new Position(BigDecimal.ONE, BigDecimal.ONE));
    LineString model = new LineString();
    model.setCoordinates(value);

    String json = OM.writeValueAsString(model);
    String expected = "{\"type\":\"LineString\",\"coordinates\":[[0,0],[1,1]]}";
    softly
        .assertThat(json)
        .isEqualTo(expected);

    Geometry geometry = OM.readValue(json, Geometry.class);
    softly
        .assertThat(geometry)
        .isEqualTo(model);
  }

  /**
   * Polygon.
   *
   * @param softly the softly
   * @throws JsonProcessingException the json processing exception
   */
  @Test
  void polygon(SoftAssertions softly) throws JsonProcessingException {
    List<Position> e0 = Arrays.asList(
        new Position(BigDecimal.ZERO, BigDecimal.ZERO),
        new Position(BigDecimal.ONE, BigDecimal.ONE));
    List<Position> e1 = Arrays.asList(
        new Position(BigDecimal.ONE, BigDecimal.ONE),
        new Position(BigDecimal.TEN, BigDecimal.TEN));
    List<List<Position>> value = Arrays.asList(e0, e1);
    Polygon model = new Polygon();
    model.setCoordinates(value);

    String json = OM.writeValueAsString(model);
    String expected = "{\"type\":\"Polygon\",\"coordinates\":[[[0,0],[1,1]],[[1,1],[10,10]]]}";
    softly
        .assertThat(json)
        .isEqualTo(expected);

    Geometry geometry = OM.readValue(json, Geometry.class);
    softly
        .assertThat(geometry)
        .isEqualTo(model);
  }

  /**
   * Multi point.
   *
   * @param softly the softly
   * @throws JsonProcessingException the json processing exception
   */
  @Test
  void multiPoint(SoftAssertions softly) throws JsonProcessingException {
    List<Position> value = Arrays.asList(
        new Position(BigDecimal.ZERO, BigDecimal.ZERO),
        new Position(BigDecimal.ONE, BigDecimal.ONE));
    MultiPoint model = new MultiPoint();
    model.setCoordinates(value);

    String json = OM.writeValueAsString(model);
    String expected = "{\"type\":\"MultiPoint\",\"coordinates\":[[0,0],[1,1]]}";
    softly
        .assertThat(json)
        .isEqualTo(expected);

    Geometry geometry = OM.readValue(json, Geometry.class);
    softly
        .assertThat(geometry)
        .isEqualTo(model);
  }

  /**
   * Multi line string.
   *
   * @param softly the softly
   * @throws JsonProcessingException the json processing exception
   */
  @Test
  void multiLineString(SoftAssertions softly) throws JsonProcessingException {
    List<Position> e0 = Arrays.asList(
        new Position(BigDecimal.ZERO, BigDecimal.ZERO),
        new Position(BigDecimal.ONE, BigDecimal.ONE));
    List<Position> e1 = Arrays.asList(
        new Position(BigDecimal.ONE, BigDecimal.ONE),
        new Position(BigDecimal.TEN, BigDecimal.TEN));
    List<List<Position>> value = Arrays.asList(e0, e1);
    MultiLineString model = new MultiLineString();
    model.setCoordinates(value);

    String json = OM.writeValueAsString(model);
    String expected = "{\"type\":\"MultiLineString\","
        + "\"coordinates\":[[[0,0],[1,1]],[[1,1],[10,10]]]}";
    softly
        .assertThat(json)
        .isEqualTo(expected);

    Geometry geometry = OM.readValue(json, Geometry.class);
    softly
        .assertThat(geometry)
        .isEqualTo(model);
  }

  /**
   * Multi polygon.
   *
   * @param softly the softly
   * @throws JsonProcessingException the json processing exception
   */
  @Test
  void multiPolygon(SoftAssertions softly) throws JsonProcessingException {
    List<Position> e0 = Arrays.asList(
        new Position(BigDecimal.ZERO, BigDecimal.ZERO),
        new Position(BigDecimal.ONE, BigDecimal.ONE));
    List<Position> e1 = Arrays.asList(
        new Position(BigDecimal.ONE, BigDecimal.ONE),
        new Position(BigDecimal.TEN, BigDecimal.TEN));
    List<List<Position>> l0 = Arrays.asList(e0, e1);
    List<List<Position>> l1 = Arrays.asList(e1, e0);
    List<List<List<Position>>> value = Arrays.asList(l0, l1);
    MultiPolygon model = new MultiPolygon();
    model.setCoordinates(value);

    String json = OM.writeValueAsString(model);
    String expected = "{\"type\":\"MultiPolygon\","
        + "\"coordinates\":[[[[0,0],[1,1]],[[1,1],[10,10]]],[[[1,1],[10,10]],[[0,0],[1,1]]]]}";
    softly
        .assertThat(json)
        .isEqualTo(expected);

    Geometry geometry = OM.readValue(json, Geometry.class);
    softly
        .assertThat(geometry)
        .isEqualTo(model);
  }

  /**
   * Geometry collection.
   *
   * @param softly the softly
   * @throws JsonProcessingException the json processing exception
   */
  @Test
  void geometryCollection(SoftAssertions softly) throws JsonProcessingException {
    List<Geometry> value = Arrays.asList(
        Point.builder().coordinates(new Position(BigDecimal.ONE, BigDecimal.ONE)).build(),
        Point.builder().coordinates(new Position(BigDecimal.TEN, BigDecimal.ZERO)).build());
    GeometryCollection model = new GeometryCollection();
    model.setGeometries(value);

    String json = OM.writeValueAsString(model);
    String expected = "{\"type\":\"GeometryCollection\",\"geometries\":["
        + "{\"type\":\"Point\",\"coordinates\":[1,1]},"
        + "{\"type\":\"Point\",\"coordinates\":[10,0]}]}";
    softly
        .assertThat(json)
        .isEqualTo(expected);

    Geometry geometry = OM.readValue(json, Geometry.class);
    softly
        .assertThat(geometry)
        .isEqualTo(model);
  }

  private Feature getFeature() {
    BoundingBox boundingBox = new BoundingBox(
        List.of(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO));
    Geometry geometry = Point.builder()
        .coordinates(new Position(BigDecimal.ONE, BigDecimal.ZERO))
        .build();
    return new Feature("my-id", boundingBox, geometry, "my-property");
  }

  /**
   * Feature.
   *
   * @param softly the softly
   * @throws JsonProcessingException the json processing exception
   */
  @Test
  void feature(SoftAssertions softly) throws JsonProcessingException {
    Feature model = getFeature();

    String json = OM.writeValueAsString(model);
    String expected = "{\"type\":\"Feature\","
        + "\"id\":\"my-id\","
        + "\"bbox\":[0,0,0,0],"
        + "\"geometry\":{\"type\":\"Point\",\"coordinates\":[1,0]},"
        + "\"properties\":\"my-property\"}";
    softly
        .assertThat(json)
        .isEqualTo(expected);

    Feature feature = OM.readValue(json, Feature.class);
    softly
        .assertThat(feature)
        .isEqualTo(model);
  }

  /**
   * Feature collection.
   *
   * @param softly the softly
   * @throws JsonProcessingException the json processing exception
   */
  @Test
  void featureCollection(SoftAssertions softly) throws JsonProcessingException {
    BoundingBox boundingBox = new BoundingBox(
        List.of(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO));
    FeatureCollection model = new FeatureCollection(boundingBox, List.of(getFeature()));

    String json = OM.writeValueAsString(model);
    String expected = "{\"type\":\"FeatureCollection\","
        + "\"bbox\":[0,0,0,0],"
        + "\"features\":[{"
        + "\"type\":\"Feature\","
        + "\"id\":\"my-id\","
        + "\"bbox\":[0,0,0,0],"
        + "\"geometry\":{\"type\":\"Point\",\"coordinates\":[1,0]},"
        + "\"properties\":\"my-property\"}]}";
    softly
        .assertThat(json)
        .isEqualTo(expected);

    FeatureCollection featureCollection = OM.readValue(json, FeatureCollection.class);
    softly
        .assertThat(featureCollection)
        .isEqualTo(model);
  }

}
