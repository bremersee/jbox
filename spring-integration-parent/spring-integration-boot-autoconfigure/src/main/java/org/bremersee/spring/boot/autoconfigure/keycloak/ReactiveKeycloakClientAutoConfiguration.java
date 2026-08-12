/*
 * Copyright 2026 the original author or authors.
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

package org.bremersee.spring.boot.autoconfigure.keycloak;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bremersee.exception.webclient.DefaultWebClientErrorDecoder;
import org.bremersee.keycloak.api.webflux.AdminApi;
import org.bremersee.keycloak.api.webflux.KeycloakClientFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatusCode;
import org.springframework.util.ClassUtils;

/**
 * The reactive keycloak client autoconfiguration.
 *
 * @author Christian Bremer
 */
@ConditionalOnWebApplication(type = Type.REACTIVE)
@ConditionalOnProperty(name = "bremersee.keycloak.client.keycloak-base-uri")
@EnableConfigurationProperties(KeycloakClientProperties.class)
@ConditionalOnClass(name = {"org.bremersee.keycloak.api.webflux.KeycloakClientFactory"})
@AutoConfiguration
public class ReactiveKeycloakClientAutoConfiguration {

  private static final Log log = LogFactory.getLog(ReactiveKeycloakClientAutoConfiguration.class);

  private final KeycloakClientProperties properties;

  /**
   * Instantiates a new reactive keycloak client autoconfiguration.
   *
   * @param properties the properties
   */
  public ReactiveKeycloakClientAutoConfiguration(KeycloakClientProperties properties) {
    this.properties = properties;
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
            * properties = %s
            *********************************************************************************""",
        ClassUtils.getUserClass(getClass()).getSimpleName(), properties));
  }

  /**
   * Keycloak admin api admin api.
   *
   * @param errorDecoderProvider the error decoder provider
   * @return the admin api
   */
  @Bean
  public AdminApi keycloakAdminApi(
      ObjectProvider<DefaultWebClientErrorDecoder> errorDecoderProvider) {

    KeycloakClientFactory factory = new KeycloakClientFactory(
        properties.getKeycloakBaseUri(),
        properties.getLoginRealm(),
        properties.getClientId(),
        properties.getUsername(),
        properties.getPassword());
    errorDecoderProvider.ifAvailable(decoder -> factory
        .setWebClientBuilderCustomizer(builder -> builder
            .defaultStatusHandler(HttpStatusCode::isError, decoder)));
    return factory.newClient();
  }

}
