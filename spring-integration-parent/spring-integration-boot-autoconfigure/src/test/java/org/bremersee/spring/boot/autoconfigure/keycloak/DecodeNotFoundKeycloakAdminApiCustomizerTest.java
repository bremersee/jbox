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

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.bremersee.keycloak.api.webflux.AdminApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactivefeign.ReactiveFeign.Builder;

/**
 * Decode not found keycloak admin api customizer test.
 *
 * @author Christian Bremer
 */
class DecodeNotFoundKeycloakAdminApiCustomizerTest {

  private DecodeNotFoundKeycloakAdminApiCustomizer target;

  /**
   * Sets up.
   */
  @BeforeEach
  void setUp() {
    target = new DecodeNotFoundKeycloakAdminApiCustomizer();
  }

  /**
   * Customize.
   */
  @Test
  void customize() {
    //noinspection unchecked
    Builder<AdminApi> builder = mock(Builder.class);
    doReturn(builder)
        .when(builder)
        .decode404();
    target.customize(builder);
    verify(builder).decode404();
  }
}