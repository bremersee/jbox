/*
 * Copyright 2019-2022 the original author or authors.
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

package org.bremersee.garmin;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ServiceLoader;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.bremersee.garmin.gpx.v3.model.ext.DisplayColorT;
import org.bremersee.garmin.gpx.v3.model.ext.TrackExtension;
import org.bremersee.xml.JaxbContextBuilder;
import org.bremersee.xml.JaxbContextDataProvider;
import org.bremersee.xml.SchemaMode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Jaxb context builder test.
 *
 * @author Christian Bremer
 */
@ExtendWith(SoftAssertionsExtension.class)
class JaxbContextBuilderTest {

  private static JaxbContextBuilder jaxbContextBuilder;

  /**
   * Create jaxb context builder.
   */
  @BeforeAll
  static void createJaxbContextBuilder() {
    jaxbContextBuilder = JaxbContextBuilder.newInstance()
        .withSchemaMode(SchemaMode.ALWAYS)
        .withFormattedOutput(false)
        .processAll(ServiceLoader.load(JaxbContextDataProvider.class))
        .initJaxbContext();
  }

  /**
   * Test xml.
   *
   * @param softly the soft assertions
   * @throws Exception the exception
   */
  @Test
  void testXml(SoftAssertions softly) throws Exception {
    TrackExtension model = new TrackExtension();
    model.setDisplayColor(DisplayColorT.CYAN);

    StringWriter sw = new StringWriter();
    jaxbContextBuilder.buildMarshaller().marshal(model, sw);
    String xmlWithAllSchemaLocations = sw.toString();
    softly.assertThat(xmlWithAllSchemaLocations).isNotNull();

    sw = new StringWriter();
    jaxbContextBuilder.buildMarshaller(model).marshal(model, sw);
    String xmlWithOneSchemaLocation = sw.toString();
    softly.assertThat(xmlWithOneSchemaLocation).isNotNull();

    softly.assertThat(xmlWithOneSchemaLocation.length())
        .isLessThan(xmlWithAllSchemaLocations.length());

    TrackExtension actualModel = (TrackExtension) jaxbContextBuilder
        .buildUnmarshaller(TrackExtension.class)
        .unmarshal(new StringReader(xmlWithOneSchemaLocation));
    softly.assertThat(actualModel).isNotNull();
    softly.assertThat(actualModel.getDisplayColor()).isEqualTo(DisplayColorT.CYAN);
  }

}
