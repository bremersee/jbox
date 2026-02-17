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

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;
import java.io.Serial;
import java.util.List;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import org.bremersee.comparator.model.SortOrder;
import org.bremersee.comparator.spring.mapper.SortMapper;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

/**
 * The abstract slice data transfer object.
 *
 * @param <T> the type of the content
 * @author Christian Bremer
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "abstractSliceType")
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "The base slice.")
public abstract class AbstractSliceDto<T> extends AbstractChunkDto<T> {

  @Serial
  private static final long serialVersionUID = 1;

  /**
   * The slice number starting with 0.
   */
  private int number;

  /**
   * The size of the slice (not the size of the content).
   */
  private int size;

  /**
   * Specifies whether a next slice is available or not.
   */
  private boolean nextAvailable;

  /**
   * Instantiates a new abstract slice data transfer object.
   */
  protected AbstractSliceDto() {
    this(null, 0, 0, false, (SortOrder) null);
  }

  /**
   * Instantiates a new abstract slice data transfer object.
   *
   * @param content the content
   * @param number the number
   * @param size the size
   * @param hasNext the has next
   */
  protected AbstractSliceDto(
      List<? extends T> content,
      int number,
      int size,
      boolean hasNext) {
    this(content, number, size, hasNext, Sort.unsorted());
  }

  /**
   * Instantiates a new abstract slice data transfer object.
   *
   * @param content the content of the slice
   * @param number the number of the slice starting with 0
   * @param size the size of the slice (not the size of the content)
   * @param hasNext the has next
   * @param sort the sort oder
   */
  protected AbstractSliceDto(
      List<? extends T> content,
      int number,
      int size,
      boolean hasNext,
      SortOrder sort) {
    super(content, sort);
    this.number = number;
    this.size = size;
    this.nextAvailable = hasNext;
  }

  /**
   * Instantiates a new abstract slice data transfer object.
   *
   * @param content the content
   * @param number the number
   * @param size the size
   * @param hasNext the has next
   * @param sort the sort
   */
  protected AbstractSliceDto(
      List<? extends T> content,
      int number,
      int size,
      boolean hasNext,
      Sort sort) {
    this(
        content,
        number,
        size,
        hasNext,
        SortMapper.defaultSortMapper().fromSort(sort));
  }

  /**
   * Instantiates a new abstract slice data transfer object.
   *
   * @param slice the slice
   */
  protected AbstractSliceDto(Slice<? extends T> slice) {
    this(
        slice.getContent(),
        slice.getNumber(),
        slice.getSize(),
        slice.hasNext(),
        slice.getSort());
  }

  @Override
  public int getNumber() {
    return number;
  }

  @Override
  public int getSize() {
    return size;
  }

  @Override
  public boolean isNextAvailable() {
    return nextAvailable;
  }
}
