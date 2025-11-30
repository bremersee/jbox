/*
 * Copyright 2022-2025 the original author or authors.
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

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import java.io.Serial;
import java.util.List;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import org.bremersee.comparator.model.SortOrder;
import org.bremersee.comparator.spring.mapper.SortMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;

/**
 * The abstract page transfer object.
 *
 * @param <T> the type of the content
 * @author Christian Bremer
 */
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "abstractPageType")
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "The base page.")
public abstract class AbstractPageDto<T> extends AbstractSliceDto<T> {

  @Serial
  private static final long serialVersionUID = 1;

  /**
   * The size of available elements.
   */
  private long totalElements;

  /**
   * Instantiates a new abstract page transfer object.
   */
  protected AbstractPageDto() {
    this(null, 0, 0, 0, (SortOrder) null);
  }

  /**
   * Instantiates a new abstract page transfer object.
   *
   * @param content the content
   * @param number the number
   * @param size the size
   * @param totalElements the total elements
   */
  public AbstractPageDto(
      List<? extends T> content,
      int number,
      int size,
      long totalElements) {
    this(content, number, size, totalElements, Sort.unsorted());
  }

  /**
   * Instantiates a new abstract page transfer object.
   *
   * @param content the content of the page
   * @param number the number of the page starting with 0
   * @param size the size of the page (not the size of the content)
   * @param totalElements the total elements (the size of available elements)
   * @param sort the sort oder
   */
  public AbstractPageDto(
      List<? extends T> content,
      int number,
      int size,
      long totalElements,
      SortOrder sort) {
    super(content, number, size, false, sort);
    this.totalElements = totalElements;
    setNextAvailable(isNextAvailable());
  }

  /**
   * Instantiates a new abstract page transfer object.
   *
   * @param content the content
   * @param number the number
   * @param size the size
   * @param totalElements the total elements
   * @param sort the sort
   */
  public AbstractPageDto(
      List<? extends T> content,
      int number,
      int size,
      long totalElements,
      Sort sort) {
    this(
        content,
        number,
        size,
        totalElements,
        SortMapper.defaultSortMapper().fromSort(sort));
  }

  /**
   * Instantiates a new abstract page transfer object.
   *
   * @param page the page
   */
  public AbstractPageDto(Page<? extends T> page) {
    this(
        page.getContent(),
        page.getNumber(),
        page.getSize(),
        page.getTotalElements(),
        page.getSort());
  }

  /**
   * Gets total elements.
   *
   * @return the total elements
   */
  @Schema(description = "The size of available elements.")
  @XmlElement(name = "totalElements", required = true)
  public long getTotalElements() {
    return totalElements;
  }

  /**
   * Gets total pages.
   *
   * @return the total pages
   */
  @Schema(description = "The total number of pages.")
  @JsonProperty(required = true)
  @XmlElement(name = "totalPages", required = true)
  public int getTotalPages() {
    return getSize() <= 0
        ? 1
        : (int) Math.ceil((double) getTotalElements() / (double) getSize());
  }

  /**
   * Sets total pages. This method is ignored.
   *
   * @param totalPages the total pages
   */
  protected void setTotalPages(int totalPages) {
  }

  @Override
  public boolean isNextAvailable() {
    return getNumber() + 1 < getTotalPages();
  }

  /**
   * To page.
   *
   * @return the page
   */
  public Page<T> toPage() {
    return new PageImpl<>(getContent(), getPageable(), getTotalElements());
  }

}
