/*
 * Copyright 2025 the original author or authors.
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

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bremersee.comparator.model.SortOrder;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

/**
 * The json slice data transfer object.
 *
 * @param <T> the content type
 * @author Christian Bremer
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "The slice for json deserialization.")
public abstract class JsonSliceDto<T> extends AbstractSliceDto<T> {

  @Serial
  private static final long serialVersionUID = 1;

  /**
   * Instantiates a new slice data transfer object.
   */
  protected JsonSliceDto() {
  }

  /**
   * Instantiates a new slice data transfer object.
   *
   * @param content the content
   * @param number the number
   * @param size the size
   * @param hasNext the has next
   */
  protected JsonSliceDto(List<? extends T> content, int number, int size, boolean hasNext) {
    super(content, number, size, hasNext);
  }

  /**
   * Instantiates a new slice data transfer object.
   *
   * @param content the content
   * @param number the number
   * @param size the size
   * @param hasNext the has next
   * @param sort the sort
   */
  protected JsonSliceDto(List<? extends T> content, int number, int size, boolean hasNext,
      SortOrder sort) {
    super(content, number, size, hasNext, sort);
  }

  /**
   * Instantiates a new slice data transfer object.
   *
   * @param content the content
   * @param number the number
   * @param size the size
   * @param hasNext the has next
   * @param sort the sort
   */
  protected JsonSliceDto(List<? extends T> content, int number, int size, boolean hasNext,
      Sort sort) {
    super(content, number, size, hasNext, sort);
  }

  /**
   * Instantiates a new slice data transfer object.
   *
   * @param slice the slice
   */
  protected JsonSliceDto(Slice<? extends T> slice) {
    super(slice);
  }

  @Schema(name = "content", description = "The content of the slice.")
  @JsonProperty(value = "content")
  @Override
  public List<T> getContent() {
    if (isNull(content)) {
      content = new ArrayList<>();
    }
    return content;
  }

}
