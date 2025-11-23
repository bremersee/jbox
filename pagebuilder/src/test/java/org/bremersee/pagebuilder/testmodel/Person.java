/*
 * Copyright 2022 the original author or authors.
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * The person.
 *
 * @author Christian Bremer
 */
@XmlRootElement(name = "person")
@XmlType(name = "personType")
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode
@ToString
@NoArgsConstructor
// To keep global typing and schema in sync
@JsonTypeInfo(
    use = Id.CLASS,
    property = "_type")
@Schema(description = "A person", discriminatorProperty = "_type")
public class Person {

  // The xml schema needs this property, because it is required in the OpenApi schema.
  @JsonIgnore
  @XmlAttribute(name = "_type")
  @Schema(hidden = true) // It is defined as discriminator property.
  @SuppressWarnings("unused")
  private String type;

  @XmlAttribute(required = true)
  @JsonProperty(required = true)
  @Getter
  @Setter
  private String firstname;

  @XmlAttribute(required = true)
  @JsonProperty(required = true)
  @Getter
  @Setter
  private String lastname;

  @JsonInclude(Include.NON_NULL)
  @Getter
  @Setter
  private Address address;

  /**
   * Instantiates a new person.
   *
   * @param firstname the firstname
   * @param lastname the lastname
   * @param address the address
   */
  public Person(String firstname, String lastname, Address address) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.address = address;
  }
}
