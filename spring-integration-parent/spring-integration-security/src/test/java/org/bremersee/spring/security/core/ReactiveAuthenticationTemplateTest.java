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

package org.bremersee.spring.security.core;

import static java.util.Objects.isNull;
import static org.assertj.core.api.Assertions.assertThat;

import java.security.Principal;
import org.bremersee.exception.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * The reactive authentication template test.
 *
 * @author Christian Bremer
 */
@ExtendWith({SpringExtension.class})
@ContextConfiguration
class ReactiveAuthenticationTemplateTest {

  private static final String USERNAME = "junit";

  private ReactiveAuthenticationTemplate<Authentication> target;

  /**
   * Sets up.
   */
  @BeforeEach
  void setUp() {
    target = new ReactiveAuthenticationTemplate<>();
  }

  /**
   * Gets authentication.
   */
  @WithMockUser(username = USERNAME)
  @Test
  void getAuthentication() {
    StepVerifier.create(target.getAuthentication())
        .assertNext(auth -> assertThat(auth)
            .extracting(Principal::getName)
            .isEqualTo(USERNAME))
        .verifyComplete();
  }

  /**
   * One with authentication.
   */
  @WithMockUser(username = USERNAME)
  @Test
  void oneWithAuthentication() {
    StepVerifier.create(target.oneWithAuthentication(auth -> Mono.just(auth.getName())))
        .assertNext(actual -> assertThat(actual).isEqualTo(USERNAME))
        .verifyComplete();
  }

  /**
   * One with authentication but authentication is absence.
   */
  @Test
  void oneWithAuthenticationButAuthenticationIsAbsence() {
    StepVerifier.create(target.oneWithAuthentication(auth -> Mono.just(auth.getName())))
        .verifyError(ServiceException.class);
  }

  /**
   * One with optional authentication.
   */
  @WithMockUser(username = USERNAME)
  @Test
  void oneWithOptionalAuthentication() {
    StepVerifier.create(target.oneWithOptionalAuthentication(auth -> isNull(auth)
            ? Mono.empty()
            : Mono.just(auth.getName())))
        .assertNext(actual -> assertThat(actual).isEqualTo(USERNAME))
        .verifyComplete();
  }

  /**
   * One with optional authentication but authentication is absence.
   */
  @Test
  void oneWithOptionalAuthenticationButAuthenticationIsAbsence() {
    StepVerifier.create(target.oneWithOptionalAuthentication(auth -> isNull(auth)
            ? Mono.empty()
            : Mono.just(auth.getName())))
        .verifyComplete();
  }

  /**
   * Many with authentication.
   */
  @WithMockUser(username = USERNAME)
  @Test
  void manyWithAuthentication() {
    StepVerifier.create(target.manyWithAuthentication(auth -> Flux.just(auth.getName())))
        .assertNext(actual -> assertThat(actual).isEqualTo(USERNAME))
        .verifyComplete();
  }

  /**
   * Many with authentication but authentication is absence.
   */
  @Test
  void manyWithAuthenticationButAuthenticationIsAbsence() {
    StepVerifier.create(target.manyWithAuthentication(auth -> Flux.just(auth.getName())))
        .verifyError(ServiceException.class);
  }

  /**
   * Many with optional authentication.
   */
  @WithMockUser(username = USERNAME)
  @Test
  void manyWithOptionalAuthentication() {
    StepVerifier.create(target.manyWithOptionalAuthentication(auth -> isNull(auth)
            ? Flux.empty()
            : Flux.just(auth.getName())))
        .assertNext(actual -> assertThat(actual).isEqualTo(USERNAME))
        .verifyComplete();
  }

  /**
   * Many with optional authentication but authentication is absence.
   */
  @Test
  void manyWithOptionalAuthenticationButAuthenticationIsAbsence() {
    StepVerifier.create(target.manyWithOptionalAuthentication(auth -> isNull(auth)
            ? Flux.empty()
            : Flux.just(auth.getName())))
        .verifyComplete();
  }

}