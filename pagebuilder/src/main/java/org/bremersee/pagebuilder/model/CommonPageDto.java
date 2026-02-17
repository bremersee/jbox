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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

/**
 * The common page transfer object.
 *
 * @author Christian Bremer
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "page")
@XmlType(name = "pageType")
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "A page.")
public class CommonPageDto extends AbstractPageDto<Object> {

  @Serial
  private static final long serialVersionUID = 1;

  /**
   * Instantiates a new common page transfer object.
   */
  protected CommonPageDto() {
    super();
  }

  /**
   * Instantiates a new common page transfer object.
   *
   * @param content the content
   * @param number the number
   * @param size the size
   * @param totalElements the total elements
   * @param sort the sort
   */
  public CommonPageDto(
      List<?> content,
      int number,
      int size,
      long totalElements,
      SortOrder sort) {
    super(content, number, size, totalElements, sort);
  }

  /**
   * Instantiates a new common page transfer object.
   *
   * @param content the content
   * @param number the number
   * @param size the size
   * @param totalElements the total elements
   */
  public CommonPageDto(List<?> content, int number, int size, long totalElements) {
    super(content, number, size, totalElements);
  }

  /**
   * Instantiates a new common page transfer object.
   *
   * @param content the content
   * @param number the number
   * @param size the size
   * @param totalElements the total elements
   * @param sort the sort
   */
  public CommonPageDto(
      List<?> content,
      int number,
      int size,
      long totalElements,
      Sort sort) {
    super(content, number, size, totalElements, sort);
  }

  /**
   * Instantiates a new common page transfer object.
   *
   * @param page the page
   */
  public CommonPageDto(Page<?> page) {
    super(page);
  }

  /**
   * Gets content.
   *
   * @return the content
   */
  @XmlElementWrapper(name = "content")
  @XmlAnyElement(lax = true)
  public List<Object> getContent() {
    if (isNull(content)) {
      content = new ArrayList<>();
    }
    return content;
  }

}
