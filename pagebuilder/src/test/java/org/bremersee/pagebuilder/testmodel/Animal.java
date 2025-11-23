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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * The animal.
 *
 * @author Christian Bremer
 */
@XmlType(name = "animalType")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonTypeInfo(
    use = Id.CLASS,
    property = "_type")
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@Schema(
    description = "Base object of an animal",
    discriminatorProperty = "_type",
    discriminatorMapping = {
        @DiscriminatorMapping(
            value = "org.bremersee.pagebuilder.testmodel.Cat", schema = Cat.class),
        @DiscriminatorMapping(
            value = "org.bremersee.pagebuilder.testmodel.Dog", schema = Dog.class)
    })
public abstract class Animal {

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
  @NonNull
  private String name;

}
