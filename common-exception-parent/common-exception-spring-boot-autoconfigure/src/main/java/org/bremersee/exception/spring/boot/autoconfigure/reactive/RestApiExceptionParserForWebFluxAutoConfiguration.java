/*
* Copyright 2020-2026 the original author or authors.
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

package org.bremersee.exception.spring.boot.autoconfigure.reactive;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bremersee.exception.RestApiExceptionParser;
import org.bremersee.exception.RestApiExceptionParserImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.util.ClassUtils;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.dataformat.xml.XmlMapper;

/**
 * The rest api exception parser autoconfiguration.
 *
 * @author Christian Bremer
 */
@ConditionalOnWebApplication(type = Type.REACTIVE)
@ConditionalOnClass(name = {
    "tools.jackson.databind.ObjectMapper",
    "org.bremersee.exception.RestApiExceptionParserImpl"
})
@AutoConfiguration
public class RestApiExceptionParserForWebFluxAutoConfiguration {

  private static final Log log = LogFactory
      .getLog(RestApiExceptionParserForWebFluxAutoConfiguration.class);

  /**
   * Instantiates a new rest api exception parser for web flux autoconfiguration.
   */
  public RestApiExceptionParserForWebFluxAutoConfiguration() {
    super();
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

  /**
   * Creates rest api exception parser for reactive web application.
   *
   * @param jsonMapperBuilder the json mapper builder
   * @param xmlMapperBuilder the xml mapper builder
   * @return the rest api exception parser
   */
  @ConditionalOnWebApplication(type = Type.REACTIVE)
  @ConditionalOnMissingBean
  @Bean
  public RestApiExceptionParser restApiExceptionParser(
      JsonMapper.Builder jsonMapperBuilder,
      XmlMapper.Builder xmlMapperBuilder) {

    return new RestApiExceptionParserImpl(jsonMapperBuilder.build(), xmlMapperBuilder.build());
  }

}
