package org.bremersee.spring.boot.autoconfigure.security.authentication;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bremersee.spring.security.core.NormalizedAuthenticationTemplate;
import org.bremersee.spring.security.core.ReactiveNormalizedAuthenticationTemplate;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.util.ClassUtils;

/**
 * The authentication operations autoconfiguration.
 */
@AutoConfiguration
@ConditionalOnClass(name = {
    "org.bremersee.spring.security.core.NormalizedAuthenticationTemplate",
    "org.bremersee.spring.security.core.ReactiveNormalizedAuthenticationTemplate"
})
public class AuthenticationOperationsAutoConfiguration {

  private static final Log log = LogFactory.getLog(AuthenticationOperationsAutoConfiguration.class);

  /**
   * Instantiates a new authentication operations autoconfiguration.
   */
  public AuthenticationOperationsAutoConfiguration() {
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
   * Creates the ormalized authentication template.
   *
   * @return the normalized authentication template
   */
  @ConditionalOnWebApplication(type = Type.SERVLET)
  @Bean
  public NormalizedAuthenticationTemplate normalizedAuthenticationTemplate() {
    return new NormalizedAuthenticationTemplate();
  }

  /**
   * Creates the reactive normalized authentication template.
   *
   * @return the reactive normalized authentication template
   */
  @ConditionalOnWebApplication(type = Type.REACTIVE)
  @Bean
  public ReactiveNormalizedAuthenticationTemplate reactiveNormalizedAuthenticationTemplate() {
    return new ReactiveNormalizedAuthenticationTemplate();
  }

}
