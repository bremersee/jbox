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

package org.bremersee.exception.spring.boot.autoconfigure.servlet;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bremersee.exception.RestApiExceptionParser;
import org.bremersee.exception.RestApiExceptionParserImpl;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.util.ClassUtils;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.dataformat.xml.XmlMapper;

/**
 * The rest api exception parser autoconfiguration.
 *
 * @author Christian Bremer
 */
@ConditionalOnWebApplication(type = Type.SERVLET)
@ConditionalOnClass(name = {
    "tools.jackson.databind.ObjectMapper",
    "org.bremersee.exception.RestApiExceptionParserImpl"
})
@AutoConfiguration
public class RestApiExceptionParserForWebAutoConfiguration {

  private static final Log log = LogFactory
      .getLog(RestApiExceptionParserForWebAutoConfiguration.class);

  /**
   * Instantiates a new rest api exception parser for web autoconfiguration.
   */
  public RestApiExceptionParserForWebAutoConfiguration() {
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
   * Creates rest api exception parser for servlet based web application.
   *
   * @param environment the environment
   * @param jsonMapperBuilderProvider the json mapper builder provider
   * @param xmlMapperBuilderProvider the xml mapper builder provider
   * @return the rest api exception parser
   */
  @ConditionalOnMissingBean
  @Bean
  public RestApiExceptionParser restApiExceptionParser(
      Environment environment,
      ObjectProvider<JsonMapper.Builder> jsonMapperBuilderProvider,
      ObjectProvider<XmlMapper.Builder> xmlMapperBuilderProvider) {

    Charset charset = environment
        .getProperty("spring.servlet.encoding.charset", Charset.class, StandardCharsets.UTF_8);
    JsonMapper jsonMapper = jsonMapperBuilderProvider.getIfAvailable(JsonMapper::builder).build();
    XmlMapper xmlMapper = xmlMapperBuilderProvider.getIfAvailable(XmlMapper::builder).build();
    return new RestApiExceptionParserImpl(jsonMapper, xmlMapper, charset);
  }

}
