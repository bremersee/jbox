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

package org.bremersee.kml.v22;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.xml.bind.JAXBException;
import java.io.StringWriter;
import java.util.ServiceLoader;
import org.bremersee.opengis.kml.v22.Kml;
import org.bremersee.xml.JaxbContextBuilder;
import org.bremersee.xml.JaxbContextDataProvider;
import org.bremersee.xml.SchemaMode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

/**
 * Jaxb context builder test.
 *
 * @author Christian Bremer
 */
class JaxbContextBuilderTest {

  private static final ResourceLoader RESOURCE_LOADER = new DefaultResourceLoader();

  private static JaxbContextBuilder jaxbContextBuilder;

  /**
   * Create jaxb context builder.
   */
  @BeforeAll
  static void createJaxbContextBuilder() {
    jaxbContextBuilder = JaxbContextBuilder.newInstance()
        .withSchemaMode(SchemaMode.ALWAYS)
        .processAll(ServiceLoader.load(JaxbContextDataProvider.class));
  }

  /**
   * Test kml.
   *
   * @throws JAXBException the jaxb exception
   */
  @Test
  void testKml() throws Exception {

    assertTrue(jaxbContextBuilder.canMarshal(Kml.class));
    assertTrue(jaxbContextBuilder.canUnmarshal(Kml.class));

    Kml kml = (Kml) jaxbContextBuilder
        .buildUnmarshaller()
        .unmarshal(RESOURCE_LOADER.getResource("classpath:KML_Samples.kml").getInputStream());

    assertNotNull(kml);

    jaxbContextBuilder
        .buildMarshaller(kml)
        .marshal(kml, new StringWriter());
  }

}
