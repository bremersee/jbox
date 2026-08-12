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

package org.bremersee.keycloak.api.webflux;

import static org.assertj.core.api.Assertions.assertThat;

import org.bremersee.exception.RestApiExceptionParserImpl;
import org.bremersee.exception.ServiceException;
import org.bremersee.exception.webclient.DefaultWebClientErrorDecoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;

/**
 * The keycloak client factory test.
 *
 * @author Christian Bremer
 */
class KeycloakClientFactoryTest {

  private KeycloakClientFactory target;

  /**
   * Sets up.
   */
  @BeforeEach
  void setUp() {
    target = new KeycloakClientFactory(
        "https://localhost:8443",
        "junit",
        "admin-cli",
        "admin",
        "test");
    DefaultWebClientErrorDecoder errorDecoder = new DefaultWebClientErrorDecoder(
        new RestApiExceptionParserImpl());
    target.setWebClientBuilderCustomizer(builder -> builder
        .defaultStatusHandler(HttpStatusCode::isError, errorDecoder));
    target.setAdminApiCustomizer(adminApiBuilder -> adminApiBuilder
        .errorMapper((r, t) -> ServiceException
            .internalServerError("Failed to access admin api.", t)));
  }

  /**
   * New client.
   */
  @Test
  void newClient() {
    AdminApi actual = target.newClient();
    assertThat(actual).isNotNull();
  }
}