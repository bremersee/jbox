/*
* Copyright 2020-2026 the original author or authors.
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

package org.bremersee.exception.spring.boot.autoconfigure.reactive;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

import org.bremersee.exception.RestApiExceptionParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.dataformat.xml.XmlMapper;

/**
 * The rest api exception parser autoconfiguration test.
 *
 * @author Christian Bremer
 */
class RestApiExceptionParserForWebFluxAutoConfigurationTest {

  private RestApiExceptionParserForWebFluxAutoConfiguration target;

  /**
   * Sets .
   */
  @BeforeEach
  void setup() {
    target = new RestApiExceptionParserForWebFluxAutoConfiguration();
  }

  /**
   * Init.
   */
  @Test
  void init() {
    assertThatNoException().isThrownBy(() -> target.init());
  }

  /**
   * Rest api exception parser for reactive.
   */
  @Test
  void restApiExceptionParser() {
    RestApiExceptionParser actual = target
        .restApiExceptionParser(JsonMapper.builder(), XmlMapper.builder());
    assertThat(actual)
        .isNotNull();
  }

}