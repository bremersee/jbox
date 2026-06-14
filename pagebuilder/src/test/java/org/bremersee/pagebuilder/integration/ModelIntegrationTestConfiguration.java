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

package org.bremersee.pagebuilder.integration;

import java.util.ServiceLoader;
import org.bremersee.pagebuilder.integration.components.IntegrationRestController;
import org.bremersee.pagebuilder.testmodel.ObjectFactory;
import org.bremersee.xml.JaxbContextBuilder;
import org.bremersee.xml.JaxbContextDataProvider;
import org.bremersee.xml.JaxbContextMember;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tools.jackson.databind.DefaultTyping;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;

/**
 * The model integration test configuration.
 *
 * @author Christian Bremer
 */
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = {IntegrationRestController.class})
public class ModelIntegrationTestConfiguration implements WebMvcConfigurer {

  /**
   * Creates jaxb context builder bean.
   *
   * @return the jaxb context builder
   */
  @Bean
  public JaxbContextBuilder jaxbContextBuilder() {
    return JaxbContextBuilder.newInstance()
        .processAll(ServiceLoader.load(JaxbContextDataProvider.class))
        .add(JaxbContextMember.byPackage(ObjectFactory.class.getPackage()).build());
  }

  /**
   * Creates xml http message converter bean.
   *
   * @param jaxbContextBuilder the jaxb context builder
   * @return the jaxb 2 http message converter
   */
  @Bean
  public Jaxb2HttpMessageConverter xmlHttpMessageConverter(JaxbContextBuilder jaxbContextBuilder) {
    return new Jaxb2HttpMessageConverter(jaxbContextBuilder);
  }

  /**
   * Creates customizer for jackson 2 object mapper builder.
   *
   * @return the jackson 2 object mapper builder customizer
   */
  @Bean
  public JsonMapperBuilderCustomizer jsonCustomizer() {
    return builder -> {
      builder.enable(SerializationFeature.INDENT_OUTPUT);
      builder.activateDefaultTypingAsProperty(
          BasicPolymorphicTypeValidator.builder()
              .allowIfSubType("org.bremersee.pagebuilder.")
              .build(),
          DefaultTyping.JAVA_LANG_OBJECT,
          "_type"
      );
    };
  }

}
