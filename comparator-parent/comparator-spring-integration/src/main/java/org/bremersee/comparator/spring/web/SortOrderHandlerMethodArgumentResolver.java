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

import static java.util.Objects.isNull;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.bremersee.comparator.model.SortOrder;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.util.UriUtils;

/**
 * The sort order handler method argument resolver.
 *
 * @author Christian Bremer
 */
public class SortOrderHandlerMethodArgumentResolver
    extends SortOrderHandlerMethodArgumentResolverSupport
    implements HandlerMethodArgumentResolver {

  /**
   * Instantiates a new sort order handler method argument resolver.
   */
  public SortOrderHandlerMethodArgumentResolver() {
    super();
  }

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return SortOrder.class.equals(parameter.getParameterType());
  }

  @Nullable
  @Override
  public SortOrder resolveArgument(
      @NonNull MethodParameter parameter,
      @Nullable ModelAndViewContainer mavContainer,
      @NonNull NativeWebRequest webRequest,
      @Nullable WebDataBinderFactory binderFactory) {

    String[] rawParameterValues = webRequest.getParameterValues(getParameterName(parameter));

    // No parameter
    if (isNull(rawParameterValues) || rawParameterValues.length == 0) {
      return getDefaultFromAnnotationOrFallback(parameter);
    }

    // Single empty parameter, e.g "sort="
    if (rawParameterValues.length == 1 && !StringUtils.hasText(rawParameterValues[0])) {
      return getDefaultFromAnnotationOrFallback(parameter);
    }

    var decoded = Arrays.stream(rawParameterValues)
        .map(it -> UriUtils.decode(it, StandardCharsets.UTF_8))
        .toList();

    return parseParameterValues(decoded);
  }
}
