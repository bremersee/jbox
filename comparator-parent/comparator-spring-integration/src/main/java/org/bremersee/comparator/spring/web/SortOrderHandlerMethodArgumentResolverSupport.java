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
import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.bremersee.comparator.model.SortOrder;
import org.bremersee.comparator.model.SortOrderItem;
import org.bremersee.comparator.model.SortOrderTextSeparators;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ValueConstants;

/**
 * The sort order handler method argument resolver support.
 *
 * @author Christian Bremer
 */
public abstract class SortOrderHandlerMethodArgumentResolverSupport {

  private SortOrderTextSeparators textSeparators = SortOrderTextSeparators.defaults();

  /**
   * Instantiates a new sort order handler method argument resolver support.
   */
  protected SortOrderHandlerMethodArgumentResolverSupport() {
    super();
  }

  /**
   * Sets text separators.
   *
   * @param textSeparators the text separators
   */
  public void setTextSeparators(SortOrderTextSeparators textSeparators) {
    if (nonNull(textSeparators)) {
      this.textSeparators = textSeparators;
    }
  }

  /**
   * Gets parameter name.
   *
   * @param parameter the parameter
   * @return the parameter name
   */
  protected String getParameterName(@Nullable MethodParameter parameter) {
    return Optional.ofNullable(parameter)
        .map(p -> getParameterName(p.getParameterAnnotation(SortOrderRequestParam.class)))
        .or(() -> Optional.ofNullable(parameter)
            .map(MethodParameter::getParameterName))
        .filter(name -> !name.isEmpty())
        .orElse("sort");
  }

  private String getParameterName(SortOrderRequestParam requestParam) {
    if (isNull(requestParam)) {
      return null;
    }
    if (!requestParam.name().isEmpty()) {
      return requestParam.name();
    }
    return null;
  }

  /**
   * Gets default from annotation or fallback.
   *
   * @param parameter the parameter
   * @return the default from annotation or fallback
   */
  protected SortOrder getDefaultFromAnnotationOrFallback(MethodParameter parameter) {
    return Optional.ofNullable(parameter.getParameterAnnotation(SortOrderRequestParam.class))
        .map(SortOrderRequestParam::defaultSort)
        .filter(defaultValue -> !ValueConstants.DEFAULT_NONE.equals(defaultValue))
        .map(defaultValue -> SortOrder.fromSortOrderText(defaultValue, textSeparators))
        .orElseGet(SortOrder::unsorted);
  }

  /**
   * Parse parameter values.
   *
   * @param parameterValues the parameter values
   * @return the sort order
   */
  protected SortOrder parseParameterValues(List<String> parameterValues) {
    List<SortOrderItem> sortOrderItems = new ArrayList<>();
    for (String parameterValue : parameterValues) {
      sortOrderItems.addAll(SortOrder.fromSortOrderText(parameterValue, textSeparators).getItems());
    }
    return new SortOrder(sortOrderItems);
  }

}
