/*
 * Copyright 2020-2022 the original author or authors.
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

package org.bremersee.pagebuilder.testmodel;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlElementRefs;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bremersee.pagebuilder.model.AbstractPageDto;
import org.springframework.data.domain.Sort;

/**
 * The animal page.
 *
 * @author Christian Bremer
 */
@XmlRootElement(name = "animalPage")
@XmlType(name = "animalPageType")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "An animal page.")
public class AnimalPage extends AbstractPageDto<Animal> {

  /**
   * Instantiates a new animal page.
   */
  protected AnimalPage() {
  }

  /**
   * Instantiates a new animal page.
   *
   * @param content the content
   * @param number the number
   * @param size the size
   * @param totalElements the total elements
   * @param sort the sort
   */
  public AnimalPage(List<? extends Animal> content, int number, int size, long totalElements,
      Sort sort) {
    super(content, number, size, totalElements, sort);
  }

  @XmlElementWrapper(name = "animals")
  @XmlElementRefs({
      @XmlElementRef(type = Cat.class),
      @XmlElementRef(type = Dog.class)
  })
  @Override
  public List<Animal> getContent() {
    return content;
  }
}
