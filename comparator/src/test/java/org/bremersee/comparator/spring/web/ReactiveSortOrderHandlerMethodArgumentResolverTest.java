/*
 * Copyright 2019-2026 the original author or authors.
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

package org.bremersee.comparator.spring.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.bremersee.comparator.model.SortOrder;
import org.bremersee.comparator.model.SortOrderTextSeparators;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.server.ServerWebExchange;

/**
 * The reactive sort order handler method argument resolver test.
 *
 * @author Christian Bremer
 */
@ExtendWith(SoftAssertionsExtension.class)
class ReactiveSortOrderHandlerMethodArgumentResolverTest {

  private static final ReactiveSortOrderHandlerMethodArgumentResolver target
      = new ReactiveSortOrderHandlerMethodArgumentResolver();

  /**
   * Init.
   */
  @BeforeAll
  static void init() {
    target.setTextSeparators(SortOrderTextSeparators.defaults());
  }

  /**
   * Supports parameter sort order.
   */
  @Test
  void supportsParameterSortOrder() {
    MethodParameter methodParameter = mock(MethodParameter.class);
    doReturn(SortOrder.class)
        .when(methodParameter)
        .getParameterType();
    boolean actual = target.supportsParameter(methodParameter);
    assertThat(actual).isTrue();
  }

  /**
   * Supports parameter other.
   */
  @Test
  void supportsParameterOther() {
    MethodParameter methodParameter = mock(MethodParameter.class);
    doReturn(Object.class)
        .when(methodParameter)
        .getParameterType();
    boolean actual = target.supportsParameter(methodParameter);
    assertThat(actual).isFalse();
  }

  /**
   * Resolve argument with annotation.
   */
  @Test
  void resolveArgumentWithAnnotation() {
    MethodParameter parameter = mock(MethodParameter.class);
    SortOrderRequestParam requestParam = mock(SortOrderRequestParam.class);
    doReturn("")
        .when(requestParam)
        .name();
    doReturn(requestParam)
        .when(parameter)
        .getParameterAnnotation(SortOrderRequestParam.class);

    ServerWebExchange exchange = mock(ServerWebExchange.class);
    ServerHttpRequest req = mock(ServerHttpRequest.class);
    doReturn(req)
        .when(exchange)
        .getRequest();
    MultiValueMap<String, String> values = new LinkedMultiValueMap<>();
    values.add("sort", "lastName");
    doReturn(values)
        .when(req)
        .getQueryParams();

    SortOrder actual = target.resolveArgumentValue(parameter, mock(BindingContext.class), exchange);

    SortOrder expected = SortOrder.fromSortOrderText("lastName");
    assertThat(actual)
        .isEqualTo(expected);
  }

  /**
   * Resolve argument with annotation and default.
   *
   * @param softly the softly
   */
  @Test
  void resolveArgumentWithAnnotationAndDefault(SoftAssertions softly) {
    MethodParameter parameter = mock(MethodParameter.class);
    SortOrderRequestParam requestParam = mock(SortOrderRequestParam.class);
    doReturn("sort")
        .when(requestParam)
        .name();
    doReturn("lastName")
        .when(requestParam)
        .defaultSort();
    doReturn(requestParam)
        .when(parameter)
        .getParameterAnnotation(SortOrderRequestParam.class);

    ServerWebExchange exchange = mock(ServerWebExchange.class);
    ServerHttpRequest req = mock(ServerHttpRequest.class);
    doReturn(req)
        .when(exchange)
        .getRequest();
    MultiValueMap<String, String> values = new LinkedMultiValueMap<>();
    doReturn(values)
        .when(req)
        .getQueryParams();

    SortOrder actual = target.resolveArgumentValue(parameter, mock(BindingContext.class), exchange);

    SortOrder expected = SortOrder.fromSortOrderText("lastName");
    softly
        .assertThat(actual)
        .isEqualTo(expected);

    req = mock(ServerHttpRequest.class);
    doReturn(req)
        .when(exchange)
        .getRequest();
    values = new LinkedMultiValueMap<>();
    values.add("sort", "");
    doReturn(values)
        .when(req)
        .getQueryParams();

    actual = target.resolveArgumentValue(parameter, mock(BindingContext.class), exchange);

    softly
        .assertThat(actual)
        .isEqualTo(expected);
  }

  /**
   * Resolve argument with no annotation.
   */
  @Test
  void resolveArgumentWithNoAnnotation() {
    MethodParameter parameter = mock(MethodParameter.class);

    ServerWebExchange exchange = mock(ServerWebExchange.class);
    ServerHttpRequest req = mock(ServerHttpRequest.class);
    doReturn(req)
        .when(exchange)
        .getRequest();
    MultiValueMap<String, String> values = new LinkedMultiValueMap<>();
    values.add("sort", "lastName");
    doReturn(values)
        .when(req)
        .getQueryParams();

    SortOrder actual = target.resolveArgumentValue(parameter, mock(BindingContext.class), exchange);

    SortOrder expected = SortOrder.fromSortOrderText("lastName");
    assertThat(actual)
        .isEqualTo(expected);
  }

}