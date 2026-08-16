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

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNullElse;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactivefeign.ReactiveFeign;
import reactivefeign.client.ReactiveHttpRequest;
import reactivefeign.client.ReactiveHttpRequestInterceptor;
import reactivefeign.client.ReactiveHttpRequestInterceptors;
import reactivefeign.webclient.WebClientFeignCustomizer;
import reactivefeign.webclient.WebReactiveFeign;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

/**
 * The keycloak client factory.
 *
 * @author Christian Bremer
 */
public class KeycloakClientFactory {

  private final String keycloakBaseUri;

  private final String loginRealm;

  private final String clientId;

  private final String username;

  private final String password;

  @Getter(AccessLevel.PROTECTED)
  @Setter
  private WebClientFeignCustomizer webClientBuilderCustomizer;

  @Getter(AccessLevel.PROTECTED)
  @Setter
  private Consumer<ReactiveFeign.Builder<AdminApi>> adminApiCustomizer;

  /**
   * Instantiates a new Keycloak client factory.
   *
   * @param keycloakBaseUri the keycloak base uri
   * @param loginRealm the login realm
   * @param clientId the client id
   * @param username the username
   * @param password the password
   */
  public KeycloakClientFactory(
      String keycloakBaseUri,
      String loginRealm,
      String clientId,
      String username,
      String password) {

    Assert.hasText(keycloakBaseUri, "keycloakBaseUri must not be null or empty.");
    Assert.hasText(username, "username must not be null or empty.");
    Assert.hasText(password, "password must not be null or empty.");
    this.keycloakBaseUri = keycloakBaseUri;
    this.loginRealm = requireNonNullElse(loginRealm, "master");
    this.clientId = requireNonNullElse(clientId, "admin-cli");
    this.username = username;
    this.password = password;
  }

  /**
   * New client admin api.
   *
   * @return the admin api
   */
  public AdminApi newClient() {
    ReactiveFeign.Builder<AdminApi> builder = WebReactiveFeign
        .<AdminApi>builder(WebClient.builder(), webClientBuilderCustomizer)
        .contract(new SpringMvcContract())
        .addRequestInterceptor(
            ReactiveHttpRequestInterceptors.addHeader("Cache-Control", "no-cache"))
        .addRequestInterceptor(getLoginInterceptor());
    if (nonNull(adminApiCustomizer)) {
      adminApiCustomizer.accept(builder);
    }
    return builder.target(AdminApi.class, keycloakBaseUri);
  }

  /**
   * Gets login interceptor.
   *
   * @return the login interceptor
   */
  protected ReactiveHttpRequestInterceptor getLoginInterceptor() {
    return new LoginInterceptor(
        keycloakBaseUri, loginRealm, clientId, username, password, webClientBuilderCustomizer);
  }

  /**
   * The login interceptor.
   *
   * @author Christian Bremer
   */
  private static class LoginInterceptor implements ReactiveHttpRequestInterceptor {

    private static final Configuration jsonPathConf = Configuration.builder()
        .options(Option.SUPPRESS_EXCEPTIONS)
        .build();

    private final WebClient webClient;

    private final MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

    private Tuple2<String, Instant> lastAccessToken;

    /**
     * Instantiates a new Login interceptor.
     *
     * @param keycloakBaseUri the keycloak base uri
     * @param loginRealm the login realm
     * @param clientId the client id
     * @param username the username
     * @param password the password
     * @param webClientBuilderCustomizer the web client builder customizer
     */
    private LoginInterceptor(
        String keycloakBaseUri,
        String loginRealm,
        String clientId,
        String username,
        String password,
        Consumer<WebClient.Builder> webClientBuilderCustomizer) {

      WebClient.Builder builder = WebClient.builder()
          .baseUrl(keycloakBaseUri + "/realms/{loginRealm}/protocol/openid-connect/token")
          .defaultUriVariables(Map.of("loginRealm", loginRealm))
          .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);

      if (nonNull(webClientBuilderCustomizer)) {
        webClientBuilderCustomizer.accept(builder);
      }
      webClient = builder.build();
      body.add("client_id", clientId);
      body.add("grant_type", "password");
      body.add("username", username);
      body.add("password", password);
    }

    private synchronized Mono<String> getLastAccessToken() {
      return Mono.justOrEmpty(lastAccessToken)
          .filter(tuple -> tuple.getT2()
              .isAfter(Instant.now().plusSeconds(30L)))
          .map(Tuple2::getT1);
    }

    private synchronized void setLastAccessToken(
        Tuple2<String, Instant> lastAccessToken) {
      this.lastAccessToken = lastAccessToken;
    }

    private Mono<String> getFreshAccessToken() {
      return webClient
          .post()
          .body(BodyInserters.fromFormData(body))
          .retrieve()
          .bodyToMono(String.class)
          .flatMap(json -> {
            DocumentContext documentContext = JsonPath.parse(json, jsonPathConf);
            return Mono.justOrEmpty(documentContext.read("$.access_token", String.class))
                .map(token -> {
                  Optional.ofNullable(documentContext.read("$.expires_in", Long.class))
                      .ifPresent(expiresIn -> setLastAccessToken(
                          Tuples.of(token, Instant.now().plusSeconds(expiresIn))));
                  return token;
                });
          });
    }

    @Override
    public Mono<ReactiveHttpRequest> apply(ReactiveHttpRequest reactiveHttpRequest) {
      return getLastAccessToken()
          .switchIfEmpty(getFreshAccessToken())
          .map(accessToken -> {
            reactiveHttpRequest.headers()
                .put(HttpHeaders.AUTHORIZATION, List.of("Bearer " + accessToken));
            return reactiveHttpRequest;
          });
    }
  }

}
