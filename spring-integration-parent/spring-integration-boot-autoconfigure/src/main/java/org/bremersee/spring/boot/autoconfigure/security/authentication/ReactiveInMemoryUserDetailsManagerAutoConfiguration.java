package org.bremersee.spring.boot.autoconfigure.security.authentication;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bremersee.spring.boot.autoconfigure.security.authentication.AuthenticationProperties.InMemoryProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * The reactive in-memory user details manager autoconfiguration.
 */
@ConditionalOnWebApplication(type = Type.REACTIVE)
@EnableConfigurationProperties(AuthenticationProperties.class)
@ConditionalOnBooleanProperty(prefix = "bremersee.authentication.in-memory", name = "enabled")
@ConditionalOnClass({ReactiveAuthenticationManager.class})
@AutoConfiguration
public class ReactiveInMemoryUserDetailsManagerAutoConfiguration {

  private static final Log log = LogFactory
      .getLog(ReactiveInMemoryUserDetailsManagerAutoConfiguration.class);

  private final InMemoryProperties properties;

  /**
   * Instantiates a new reactive in-memory user details manager autoconfiguration.
   *
   * @param properties the properties
   */
  ReactiveInMemoryUserDetailsManagerAutoConfiguration(AuthenticationProperties properties) {
    this.properties = properties.getInMemory();
  }

  /**
   * Creates reactive user details service.
   *
   * @return the reactive user details service
   */
  @Bean
  MapReactiveUserDetailsService reactiveUserDetailsService() {
    log.info(String.format("Building reactive user details service with %s", properties));
    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    UserDetails[] userDetails = properties.buildUserDetails(encoder).toArray(UserDetails[]::new);
    return new MapReactiveUserDetailsService(userDetails);
  }

}
