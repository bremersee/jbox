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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.bremersee.exception.RestApiExceptionParserImpl;
import org.bremersee.exception.webclient.DefaultWebClientErrorDecoder;
import org.bremersee.keycloak.api.webflux.AdminApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;

/**
 * The reactive keycloak client autoconfiguration test.
 *
 * @author Christian Bremer
 */
class ReactiveKeycloakClientAutoConfigurationTest {

  private ReactiveKeycloakClientAutoConfiguration target;

  /**
   * Sets up.
   */
  @BeforeEach
  void setUp() {
    KeycloakClientProperties properties = new KeycloakClientProperties();
    properties.setKeycloakBaseUri("https://localhost:8443");
    properties.setLoginRealm("master");
    properties.setClientId("admin-cli");
    properties.setUsername("admin");
    properties.setPassword("change-it");
    target = new ReactiveKeycloakClientAutoConfiguration(properties);
    target.init();
  }

  /**
   * Keycloak admin api.
   */
  @Test
  void keycloakAdminApi() {
    DefaultWebClientErrorDecoder errorDecoder = new DefaultWebClientErrorDecoder(
        new RestApiExceptionParserImpl());
    //noinspection unchecked
    ObjectProvider<DefaultWebClientErrorDecoder> provider = mock(ObjectProvider.class);
    doReturn(errorDecoder)
        .when(provider)
        .getIfAvailable();
    AdminApi actual = target.keycloakAdminApi(provider);
    assertThat(actual).isNotNull();
  }
}