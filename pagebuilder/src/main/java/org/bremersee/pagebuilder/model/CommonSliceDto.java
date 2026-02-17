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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAnyElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bremersee.comparator.model.SortOrder;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

/**
 * The common slice data transfer object.
 *
 * @author Christian Bremer
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "slice")
@XmlType(name = "sliceType")
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "A slice.")
public class CommonSliceDto extends AbstractSliceDto<Object> {

  @Serial
  private static final long serialVersionUID = 1;

  /**
   * Instantiates a new common slice data transfer object.
   */
  protected CommonSliceDto() {
  }

  /**
   * Instantiates a new common slice data transfer object.
   *
   * @param content the content
   * @param number the number
   * @param size the size
   * @param hasNext the has next
   */
  public CommonSliceDto(List<?> content, int number, int size, boolean hasNext) {
    super(content, number, size, hasNext);
  }

  /**
   * Instantiates a new common slice data transfer object.
   *
   * @param content the content
   * @param number the number
   * @param size the size
   * @param hasNext the has next
   * @param sort the sort
   */
  public CommonSliceDto(List<?> content, int number, int size, boolean hasNext, SortOrder sort) {
    super(content, number, size, hasNext, sort);
  }

  /**
   * Instantiates a new common slice data transfer object.
   *
   * @param content the content
   * @param number the number
   * @param size the size
   * @param hasNext the has next
   * @param sort the sort
   */
  public CommonSliceDto(List<?> content, int number, int size, boolean hasNext, Sort sort) {
    super(content, number, size, hasNext, sort);
  }

  /**
   * Instantiates a new common slice data transfer object.
   *
   * @param slice the slice
   */
  public CommonSliceDto(Slice<?> slice) {
    super(slice);
  }

  /**
   * Gets content.
   *
   * @return the content
   */
  @XmlElementWrapper(name = "content")
  @XmlAnyElement(lax = true)
  @Override
  public List<Object> getContent() {
    if (isNull(content)) {
      content = new ArrayList<>();
    }
    return content;
  }

}
