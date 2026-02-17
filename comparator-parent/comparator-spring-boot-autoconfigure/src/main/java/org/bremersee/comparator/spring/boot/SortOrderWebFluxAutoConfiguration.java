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

package org.bremersee.comparator.spring.boot;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.bremersee.comparator.model.SortOrderTextSeparators;
import org.bremersee.comparator.spring.web.ReactiveSortOrderHandlerMethodArgumentResolver;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.event.EventListener;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;

/**
 * The sort order autoconfiguration for webflux.
 *
 * @author Christian Bremer
 */
@ConditionalOnWebApplication(type = Type.REACTIVE)
@ConditionalOnClass(name = {
    "org.bremersee.comparator.model.SortOrderTextSeparators",
    "org.bremersee.comparator.spring.web.ReactiveSortOrderHandlerMethodArgumentResolver"
})
@AutoConfiguration
@EnableConfigurationProperties(SortOrderConverterProperties.class)
@Slf4j
public class SortOrderWebFluxAutoConfiguration implements WebFluxConfigurer {

  private final SortOrderTextSeparators separators;

  /**
   * Instantiates a new sort order autoconfiguration for webflux.
   *
   * @param properties the properties
   */
  public SortOrderWebFluxAutoConfiguration(SortOrderConverterProperties properties) {
    SortOrderTextSeparators defaults = SortOrderTextSeparators.defaults();
    this.separators = SortOrderTextSeparators.builder()
        .argumentSeparator(Optional.ofNullable(properties.getArgumentSeparator())
            .filter(StringUtils::hasText)
            .orElse(defaults.getArgumentSeparator()))
        .chainSeparator(Optional.ofNullable(properties.getChainSeparator())
            .filter(StringUtils::hasText)
            .orElse(defaults.getChainSeparator()))
        .build();
  }

  /**
   * Init.
   */
  @EventListener(ApplicationReadyEvent.class)
  public void init() {
    log.info("""
            
            *********************************************************************************
            * {}
            *********************************************************************************""",
        ClassUtils.getUserClass(getClass()).getSimpleName());
  }

  @Override
  public void configureArgumentResolvers(ArgumentResolverConfigurer configurer) {
    var resolver = new ReactiveSortOrderHandlerMethodArgumentResolver();
    resolver.setTextSeparators(separators);
    configurer.addCustomResolver(resolver);
  }

}
