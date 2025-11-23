/*
 * Copyright 2020-2025 the original author or authors.
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

package org.bremersee.pagebuilder.model;

import static java.util.Objects.isNull;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bremersee.comparator.model.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

/**
 * The json page transfer object.
 *
 * @param <T> the content type
 * @author Christian Bremer
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "The page for json deserialization.")
public abstract class JsonPageDto<T> extends AbstractPageDto<T> {

  @Serial
  private static final long serialVersionUID = 1;

  /**
   * Instantiates a new json page transfer object.
   */
  protected JsonPageDto() {
  }

  /**
   * Instantiates a new json page transfer object.
   *
   * @param content the content
   * @param number the number
   * @param size the size
   * @param totalElements the total elements
   */
  public JsonPageDto(List<? extends T> content, int number, int size, long totalElements) {
    super(content, number, size, totalElements);
  }

  /**
   * Instantiates a new json page transfer object.
   *
   * @param content the content
   * @param number the number
   * @param size the size
   * @param totalElements the total elements
   * @param sort the sort
   */
  public JsonPageDto(List<? extends T> content, int number, int size, long totalElements,
      SortOrder sort) {
    super(content, number, size, totalElements, sort);
  }

  /**
   * Instantiates a new json page transfer object.
   *
   * @param content the content
   * @param number the number
   * @param size the size
   * @param totalElements the total elements
   * @param sort the sort
   */
  public JsonPageDto(List<? extends T> content, int number, int size, long totalElements,
      Sort sort) {
    super(content, number, size, totalElements, sort);
  }

  /**
   * Instantiates a new json page transfer object.
   *
   * @param page the page
   */
  public JsonPageDto(Page<? extends T> page) {
    super(page);
  }

  @Override
  public List<T> getContent() {
    if (isNull(content)) {
      content = new ArrayList<>();
    }
    return content;
  }
}
