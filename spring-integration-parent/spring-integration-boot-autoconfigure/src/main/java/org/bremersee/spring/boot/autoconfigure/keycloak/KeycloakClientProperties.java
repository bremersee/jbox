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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * The keycloak client properties.
 *
 * @author Christian Bremer
 */
@ConfigurationProperties(prefix = "bremersee.keycloak.client")
@Getter
@Setter
@EqualsAndHashCode
@ToString(exclude = {"password"})
public class KeycloakClientProperties {

  /**
   * Instantiates new keycloak client properties.
   */
  public KeycloakClientProperties() {
    super();
  }

  /**
   * The keycloak base uri. For example {@code https://keycloak.example.org}.
   */
  private String keycloakBaseUri;

  /**
   * The login realm. Default is {@code master}.
   */
  private String loginRealm = "master";

  /**
   * The client id. Default is {@code admin-cli}.
   */
  private String clientId = "admin-cli";

  /**
   * The usersame.
   */
  private String username;

  /**
   * The password.
   */
  private String password;

}
