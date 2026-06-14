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

package org.bremersee.exception.spring.boot.autoconfigure.servlet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.bremersee.exception.RestApiExceptionParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.env.Environment;
import org.springframework.mock.env.MockEnvironment;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.dataformat.xml.XmlMapper;

/**
 * The rest api exception parser auto configuration test.
 *
 * @author Christian Bremer
 */
class RestApiExceptionParserForWebAutoConfigurationTest {

  private RestApiExceptionParserForWebAutoConfiguration target;

  /**
   * Sets .
   */
  @BeforeEach
  void setup() {
    target = new RestApiExceptionParserForWebAutoConfiguration();
  }

  /**
   * Init.
   */
  @Test
  void init() {
    assertThatNoException().isThrownBy(() -> target.init());
  }

  /**
   * Rest api exception parser for servlet.
   */
  @Test
  void restApiExceptionParser() {
    Environment environment = new MockEnvironment();
    RestApiExceptionParser actual = target.restApiExceptionParser(
        environment,
        jsonMapperBuilderProvider(),
        xmlMapperBuilderProvider());
    assertThat(actual)
        .isNotNull();
  }

  private ObjectProvider<JsonMapper.Builder> jsonMapperBuilderProvider() {
    //noinspection unchecked
    ObjectProvider<JsonMapper.Builder> provider = mock(ObjectProvider.class);
    when(provider.getIfAvailable(any())).thenReturn(JsonMapper.builder());
    return provider;
  }

  private ObjectProvider<XmlMapper.Builder> xmlMapperBuilderProvider() {
    //noinspection unchecked
    ObjectProvider<XmlMapper.Builder> provider = mock(ObjectProvider.class);
    when(provider.getIfAvailable(any())).thenReturn(XmlMapper.builder());
    return provider;
  }

}