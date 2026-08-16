/*
 * Copyright 2024-2026 the original author or authors.
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

package org.bremersee.spring.security.core.mapping;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNullElse;
import static org.springframework.util.ObjectUtils.isEmpty;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jspecify.annotations.NonNull;
import org.springframework.util.Assert;

/**
 * The abstract normalized mapper.
 *
 * @param <T> the type parameter
 * @author Christian Bremer
 */
public abstract class AbstractNormalizedMapper<T> {

  /**
   * The default values.
   */
  private final List<String> defaultValues;

  /**
   * The value mapping.
   */
  private final Map<String, String> valueMapping;

  /**
   * The value prefix.
   */
  private final String valuePrefix;

  /**
   * The value case transformation.
   */
  private final CaseTransformation valueCaseTransformation;

  /**
   * The value string replacements.
   */
  private final Map<String, String> valueStringReplacements;

  /**
   * Instantiates a new normalized mapper.
   *
   * @param defaultValues the default values
   * @param valueMapping the value mapping
   * @param valuePrefix the value prefix
   * @param valueCaseTransformation the value case transformation
   * @param valueStringReplacements the value string replacements
   */
  protected AbstractNormalizedMapper(
      List<String> defaultValues,
      Map<String, String> valueMapping,
      String valuePrefix,
      CaseTransformation valueCaseTransformation,
      Map<String, String> valueStringReplacements) {
    this.defaultValues = defaultValues;
    this.valueMapping = valueMapping;
    this.valuePrefix = requireNonNullElse(valuePrefix, getDefaultPrefix());
    this.valueCaseTransformation = valueCaseTransformation;
    this.valueStringReplacements = valueStringReplacements;
  }

  /**
   * Map collection.
   *
   * @param sourceCollection the source collection
   * @return the target collection
   */
  @NonNull
  protected Collection<T> map(
      @NonNull Collection<? extends T> sourceCollection) {
    Stream<T> defaultTargets = defaultValues.stream()
        .filter(target -> !isEmpty(target))
        .map(this::createTarget);
    Stream<T> givenTargets = Stream.ofNullable(sourceCollection)
        .flatMap(Collection::stream)
        .filter(Objects::nonNull)
        .map(this::getStringValue)
        .filter(target -> !isEmpty(target))
        .map(this::normalize);
    return Stream.concat(defaultTargets, givenTargets)
        .collect(Collectors.toSet());
  }

  /**
   * Normalize source.
   *
   * @param source the source
   * @return the target
   */
  @NonNull
  protected T normalize(@NonNull String source) {
    Assert.hasText(source, "Source must be present.");
    Optional<T> mappedValue = Stream.ofNullable(valueMapping)
        .flatMap(m -> m.entrySet().stream())
        .filter(e -> source.equalsIgnoreCase(e.getKey()))
        .findFirst()
        .map(Entry::getValue)
        .map(this::createTarget);
    if (mappedValue.isPresent()) {
      return mappedValue.get();
    }
    String value = source;
    if (nonNull(valueCaseTransformation)) {
      value = switch (valueCaseTransformation) {
        case TO_LOWER_CASE -> value.toLowerCase();
        case TO_UPPER_CASE -> value.toUpperCase();
        case NONE -> value;
      };
    }
    if (!isEmpty(valueStringReplacements)) {
      for (Entry<String, String> replacement : valueStringReplacements.entrySet()) {
        value = value.replaceAll(replacement.getKey(), replacement.getValue());
      }
    }
    if (!isEmpty(valuePrefix) && !value.startsWith(valuePrefix)) {
      value = valuePrefix + value;
    }
    return createTarget(value);
  }

  /**
   * Gets string value.
   *
   * @param value the value
   * @return the string value
   */
  @NonNull
  protected abstract String getStringValue(@NonNull T value);

  /**
   * Create target t.
   *
   * @param mappedValue the mapped value
   * @return the t
   */
  @NonNull
  protected abstract T createTarget(@NonNull String mappedValue);

  /**
   * Gets default prefix.
   *
   * @return the default prefix
   */
  @NonNull
  protected abstract String getDefaultPrefix();

}
