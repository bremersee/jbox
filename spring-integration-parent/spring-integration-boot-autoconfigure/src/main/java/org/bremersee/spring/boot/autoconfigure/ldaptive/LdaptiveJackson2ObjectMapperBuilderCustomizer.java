package org.bremersee.spring.boot.autoconfigure.ldaptive;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bremersee.ldaptive.LdaptiveObjectMapperModule;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.util.ClassUtils;

/**
 * The ldaptive jackson 2 object mapper builder customizer.
 */
@ConditionalOnWebApplication
@ConditionalOnClass(name = {
    "org.bremersee.ldaptive.LdaptiveObjectMapperModule",
    "org.springframework.http.converter.json.Jackson2ObjectMapperBuilder",
    "com.fasterxml.jackson.databind.ObjectMapper"})
@AutoConfiguration
public class LdaptiveJackson2ObjectMapperBuilderCustomizer
    implements Jackson2ObjectMapperBuilderCustomizer {

  private static final Log log = LogFactory
      .getLog(LdaptiveJackson2ObjectMapperBuilderCustomizer.class);

  /**
   * Instantiates a new ldaptive jackson 2 object mapper builder customizer.
   */
  public LdaptiveJackson2ObjectMapperBuilderCustomizer() {
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

  @Override
  public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
    jacksonObjectMapperBuilder.postConfigurer(objectMapper -> objectMapper
        .registerModule(new LdaptiveObjectMapperModule()));

  }
}
