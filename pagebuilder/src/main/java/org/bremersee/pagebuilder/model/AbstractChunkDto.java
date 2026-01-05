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
import static java.util.Objects.nonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import org.bremersee.comparator.model.SortOrder;
import org.bremersee.comparator.spring.mapper.SortMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

/**
 * The abstract chunk dto.
 *
 * @param <T> the type of the content
 */
@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "abstractChunkType")
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
@Schema(description = "The chunk.")
public abstract class AbstractChunkDto<T> implements Serializable {

  @Serial
  private static final long serialVersionUID = 1;

  /**
   * The content.
   */
  protected List<T> content;

  /**
   * The sort order.
   */
  private SortOrder sort;

  /**
   * Instantiates a new chunk dto.
   */
  protected AbstractChunkDto() {
    this(List.of(), new SortOrder(List.of()));
  }

  /**
   * Instantiates a new chunk dto.
   *
   * @param content the content
   */
  protected AbstractChunkDto(List<? extends T> content) {
    this(content, null);
  }

  /**
   * Instantiates a new chunk dto.
   *
   * @param content the content
   * @param sort the sort
   */
  protected AbstractChunkDto(List<? extends T> content, SortOrder sort) {
    this.content = new ArrayList<>(nonNull(content) ? content : List.of());
    this.sort = sort;
  }

  /**
   * Gets content.
   *
   * @return the content
   */
  @Schema(name = "content", description = "The content of the page or slice.")
  @JsonProperty(value = "content")
  public abstract List<T> getContent();

  /**
   * Sets content.
   *
   * @param content the content
   */
  protected void setContent(List<T> content) {
    this.content = new ArrayList<>(nonNull(content) ? content : List.of());
  }

  /**
   * Indicates whether the content is present.
   *
   * @return the boolean
   */
  @Schema(description = "Indicates whether the content is present.")
  @JsonProperty(required = true)
  @XmlElement(name = "contentPresent", required = true)
  public boolean isContentPresent() {
    return nonNull(getContent()) && !getContent().isEmpty();
  }

  /**
   * Sets content present. This method is ignored.
   *
   * @param contentPresent the content present
   */
  protected void setContentPresent(boolean contentPresent) {
    // ignored
  }

  /**
   * Gets sort.
   *
   * @return the sort
   */
  @XmlElementRef(type = SortOrder.class)
  public SortOrder getSort() {
    if (isNull(sort)) {
      return new SortOrder(List.of());
    }
    return sort;
  }

  /**
   * Gets the page or slice number starting with 0.
   *
   * @return the page or slice number starting with 0
   */
  @Schema(description = "The page or slice number starting with 0.")
  @XmlElement(name = "number", required = true)
  public abstract int getNumber();

  /**
   * Sets number.
   *
   * @param number the number
   */
  protected abstract void setNumber(int number);

  /**
   * Gets the size of the page or slice (not the size of the content).
   *
   * @return the size of the page or slice (not the size of the content)
   */
  @Schema(description = "The size of the page or slice (not the size of the content).")
  @XmlElement(name = "size", required = true)
  public abstract int getSize();

  /**
   * Sets size.
   *
   * @param size the size
   */
  protected abstract void setSize(int size);

  /**
   * Gets the number of elements.
   *
   * @return the number of elements
   */
  @Schema(description = "The number of elements.")
  @JsonProperty(required = true)
  @XmlElement(name = "numberOfElements", required = true)
  public int getNumberOfElements() {
    return nonNull(getContent()) ? getContent().size() : 0;
  }

  /**
   * Sets the number of elements. This method is ignored.
   *
   * @param numberOfElements the number of elements
   */
  protected void setNumberOfElements(int numberOfElements) {
    // ignored
  }

  /**
   * Indicates whether the page or slice is the first.
   *
   * @return the boolean
   */
  @Schema(description = "Indicates whether the page or slice is the first.")
  @JsonProperty(required = true)
  @XmlElement(name = "first", required = true)
  public boolean isFirst() {
    return !isPreviousAvailable();
  }

  /**
   * Sets first. This method is ignored.
   *
   * @param first the first
   */
  protected void setFirst(boolean first) {
    // ignored
  }

  /**
   * Indicates whether the page or slice is the last.
   *
   * @return the boolean
   */
  @Schema(description = "Indicates whether the page or slice is the last.")
  @JsonProperty(required = true)
  @XmlElement(name = "last", required = true)
  public boolean isLast() {
    return !isNextAvailable();
  }

  /**
   * Sets last. This method is ignored.
   *
   * @param last the last
   */
  protected void setLast(boolean last) {
    // ignored
  }

  /**
   * Indicates whether a next page or slice is available.
   *
   * @return the boolean
   */
  @Schema(description = "Indicates whether a next page or slice is available.")
  @JsonProperty(required = true)
  @XmlElement(name = "nextAvailable", required = true)
  public abstract boolean isNextAvailable();

  /**
   * Sets next available.
   *
   * @param nextAvailable the next available
   */
  protected abstract void setNextAvailable(boolean nextAvailable);

  /**
   * Indicates whether a previous page or slice is available.
   *
   * @return the boolean
   */
  @Schema(description = "Indicates whether a previous page or slice is available.")
  @JsonProperty(required = true)
  @XmlElement(name = "previousAvailable", required = true)
  public boolean isPreviousAvailable() {
    return getNumber() > 0;
  }

  /**
   * Sets previous available. This method is ignored.
   *
   * @param previousAvailable the previous available
   */
  protected void setPreviousAvailable(boolean previousAvailable) {
    // ignored
  }

  /**
   * Gets pageable.
   *
   * @return the pageable
   */
  @Hidden
  @XmlTransient
  @JsonIgnore
  public Pageable getPageable() {
    if (getSort().isEmpty()) {
      return PageRequest.of(getNumber(), getSize());
    }
    return PageRequest.of(getNumber(), getSize(), SortMapper.defaultSortMapper().toSort(getSort()));
  }

  /**
   * To slice.
   *
   * @return the slice
   */
  public Slice<T> toSlice() {
    return new SliceImpl<>(getContent(), getPageable(), isNextAvailable());
  }

}
