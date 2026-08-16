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

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bremersee.exception.feign.FeignClientExceptionErrorDecoder;
import org.bremersee.exception.webclient.DefaultWebClientErrorDecoder;
import org.bremersee.keycloak.api.webflux.AdminApi;
import org.bremersee.keycloak.api.webflux.AdminApiMock;
import org.bremersee.keycloak.api.webflux.KeycloakAdminClient;
import org.bremersee.keycloak.api.webflux.KeycloakClientFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatusCode;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import reactivefeign.client.statushandler.ReactiveStatusHandlers;
import reactivefeign.webclient.WebClientFeignCustomizer;

/**
 * The reactive keycloak client autoconfiguration.
 *
 * @author Christian Bremer
 */
@ConditionalOnWebApplication(type = Type.REACTIVE)
@EnableConfigurationProperties(KeycloakProperties.class)
@ConditionalOnClass(name = {"org.bremersee.keycloak.api.webflux.KeycloakClientFactory"})
@AutoConfiguration
public class ReactiveKeycloakClientAutoConfiguration {

  private static final Log log = LogFactory.getLog(ReactiveKeycloakClientAutoConfiguration.class);

  private final KeycloakProperties properties;

  /**
   * Instantiates a new reactive keycloak client autoconfiguration.
   *
   * @param properties the properties
   */
  public ReactiveKeycloakClientAutoConfiguration(KeycloakProperties properties) {
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
    if (properties.getAdminClient().isEnabled()) {
      Assert.hasText(properties.getBaseUri(), "Keycloak Base URI is required.");
      Assert.hasText(properties.getAdminClient().getLoginRealm(),
          "Keycloak Login Realm is required.");
      Assert.hasText(properties.getAdminClient().getClientId(), "Keycloak Client ID is required.");
      Assert.hasText(properties.getAdminClient().getUsername(),
          "Keycloak Client Username is required.");
      Assert.hasText(properties.getAdminClient().getPassword(),
          "Keycloak Client Password is required.");
    }
  }

  /**
   * Creates keycloak admin api.
   *
   * @param errorDecoderProvider the error decoder provider
   * @param feignErrorDecoderProvider the feign error decoder provider
   * @return the admin api
   */
  @ConditionalOnMissingBean
  @Bean
  public AdminApi keycloakAdminApi(
      List<WebClientFeignCustomizer> webClientCustomizers,
      List<KeycloakAdminApiCustomizer> keycloakAdminApiCustomizers,
      ObjectProvider<DefaultWebClientErrorDecoder> errorDecoderProvider,
      ObjectProvider<FeignClientExceptionErrorDecoder> feignErrorDecoderProvider) {

    if (!properties.getAdminClient().isEnabled()) {
      log.warn("Keycloak Admin Client is disabled. Creating admin api MOCK!");
      return new AdminApiMock();
    }

    List<WebClientFeignCustomizer> webClientExtendedCustomizers = new ArrayList<>();
    errorDecoderProvider.ifAvailable(decoder -> webClientExtendedCustomizers
        .add(builder -> builder.defaultStatusHandler(HttpStatusCode::isError, decoder)));
    webClientExtendedCustomizers.addAll(webClientCustomizers);

    List<KeycloakAdminApiCustomizer> keycloakAdminApiExtendedCustomizers = new ArrayList<>();
    feignErrorDecoderProvider.ifAvailable(decoder -> keycloakAdminApiExtendedCustomizers
        .add(builder -> builder.statusHandler(ReactiveStatusHandlers.errorDecoder(decoder))));
    keycloakAdminApiExtendedCustomizers.addAll(keycloakAdminApiCustomizers);

    KeycloakClientFactory factory = createKeycloakClientFactory();

    factory.setWebClientBuilderCustomizer(builder -> webClientExtendedCustomizers
        .forEach(c -> c.accept(builder)));
    factory.setAdminApiCustomizer(builder -> keycloakAdminApiExtendedCustomizers
        .forEach(c -> c.customize(builder)));

    return factory.newClient();
  }

  private KeycloakClientFactory createKeycloakClientFactory() {
    return new KeycloakClientFactory(
        properties.getBaseUri(),
        properties.getAdminClient().getLoginRealm(),
        properties.getAdminClient().getClientId(),
        properties.getAdminClient().getUsername(),
        properties.getAdminClient().getPassword());
  }

  /**
   * Creates keycloak admin client.
   *
   * @param adminApi the admin api
   * @return the keycloak admin client
   */
  @ConditionalOnMissingBean
  @Bean
  public KeycloakAdminClient keycloakAdminClient(AdminApi adminApi) {
    return new KeycloakAdminClient(adminApi);
  }

}
