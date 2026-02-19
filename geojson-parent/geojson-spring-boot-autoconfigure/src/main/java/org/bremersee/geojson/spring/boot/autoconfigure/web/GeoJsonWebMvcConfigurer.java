/*
 * Copyright 2022 the original author or authors.
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

package org.bremersee.geojson.spring.boot.autoconfigure.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bremersee.geojson.GeoJsonGeometryFactory;
import org.bremersee.geojson.converter.GeometryConverters;
import org.bremersee.geojson.spring.boot.autoconfigure.GeoJsonGeometryFactoryAutoConfiguration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.format.FormatterRegistry;
import org.springframework.lang.NonNull;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The GeoJSON web mvc configurer.
 *
 * @author Christian Bremer
 */
@ConditionalOnClass(name = {"org.bremersee.geojson.converter.GeometryConverters"})
@ConditionalOnWebApplication(type = Type.SERVLET)
@AutoConfiguration
@AutoConfigureAfter(GeoJsonGeometryFactoryAutoConfiguration.class)
public class GeoJsonWebMvcConfigurer implements WebMvcConfigurer {

  private static final Log log = LogFactory.getLog(GeoJsonWebMvcConfigurer.class);

  private final GeoJsonGeometryFactory geometryFactory;

  /**
   * Instantiates a new GeoJSON web mvc configurer.
   *
   * @param geometryFactory the geometry factory
   */
  public GeoJsonWebMvcConfigurer(ObjectProvider<GeoJsonGeometryFactory> geometryFactory) {
    this.geometryFactory = geometryFactory.getIfAvailable(GeoJsonGeometryFactory::new);
  }

  /**
   * Init.
   */
  @EventListener(ApplicationReadyEvent.class)
  public void init() {
    log.info(String.format("""

            *********************************************************************************
            * %s
            *********************************************************************************""",
        ClassUtils.getUserClass(getClass()).getSimpleName()));
  }

  @Override
  public void addFormatters(@NonNull FormatterRegistry registry) {
    GeometryConverters.getConvertersToRegister(geometryFactory)
        .forEach(registry::addConverter);
  }

}
