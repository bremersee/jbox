/*
* Copyright 2019-2026 the original author or authors.
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

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bremersee.exception.RestApiExceptionMapper;
import org.bremersee.exception.servlet.ApiExceptionResolver;
import org.bremersee.exception.servlet.HttpServletRequestIdProvider;
import org.bremersee.exception.spring.boot.autoconfigure.RestApiExceptionMapperBootProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.event.EventListener;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.dataformat.xml.XmlMapper;

/**
 * The api exception resolver autoconfiguration.
 */
@ConditionalOnWebApplication(type = Type.SERVLET)
@ConditionalOnClass(name = {
    "tools.jackson.databind.ObjectMapper",
    "org.bremersee.exception.RestApiExceptionMapperProperties",
    "org.bremersee.exception.servlet.ApiExceptionResolver"
})
@AutoConfigureAfter({
    RestApiExceptionMapperForWebAutoConfiguration.class
})
@AutoConfiguration
@EnableConfigurationProperties({RestApiExceptionMapperBootProperties.class})
public class ApiExceptionResolverAutoConfiguration implements WebMvcConfigurer {

  private static final Log log = LogFactory
      .getLog(ApiExceptionResolverAutoConfiguration.class);

  private final RestApiExceptionMapperBootProperties properties;

  private final ApiExceptionResolver apiExceptionResolver;

  /**
   * Instantiates a new api exception resolver autoconfiguration.
   *
   * @param properties the properties
   * @param apiExceptionMapper the api exception mapper
   * @param jsonMapperBuilderProvider the json mapper builder provider
   * @param xmlMapperBuilderProvider the xml mapper builder provider
   * @param restApiIdProvider the rest api id provider
   */
  public ApiExceptionResolverAutoConfiguration(
      RestApiExceptionMapperBootProperties properties,
      ObjectProvider<RestApiExceptionMapper> apiExceptionMapper,
      ObjectProvider<JsonMapper.Builder> jsonMapperBuilderProvider,
      ObjectProvider<XmlMapper.Builder> xmlMapperBuilderProvider,
      ObjectProvider<HttpServletRequestIdProvider> restApiIdProvider) {

    RestApiExceptionMapper mapper = apiExceptionMapper.getIfAvailable();
    Assert.notNull(mapper, "Api exception resolver must be present.");
    this.properties = properties;
    this.apiExceptionResolver = new ApiExceptionResolver(
        properties.getApiPaths(),
        mapper,
        jsonMapperBuilderProvider.getIfAvailable(),
        xmlMapperBuilderProvider.getIfAvailable());
    this.apiExceptionResolver.setRestApiExceptionIdProvider(restApiIdProvider.getIfAvailable());
  }

  /**
   * Init.
   */
  @EventListener(ApplicationReadyEvent.class)
  public void init() {
    log.info(String.format("""
            
            *********************************************************************************
            * %s
            *********************************************************************************
            * apiPaths = %s
            *********************************************************************************""",
        ClassUtils.getUserClass(getClass()).getSimpleName(),
        properties.getApiPaths()));
  }

  @Override
  public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
    log.info(String.format("Adding exception resolver [%s] to registry.",
        ClassUtils.getUserClass(apiExceptionResolver).getSimpleName()));
    exceptionResolvers.addFirst(apiExceptionResolver);
  }

}
