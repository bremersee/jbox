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

package org.bremersee.pagebuilder.integration.components;

import java.util.List;
import org.bremersee.pagebuilder.model.CommonPageDto;
import org.bremersee.pagebuilder.testmodel.Address;
import org.bremersee.pagebuilder.testmodel.AddressPage;
import org.bremersee.pagebuilder.testmodel.AnimalPage;
import org.bremersee.pagebuilder.testmodel.Cat;
import org.bremersee.pagebuilder.testmodel.Dog;
import org.bremersee.pagebuilder.testmodel.Person;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The integration rest controller.
 *
 * @author Christian Bremer
 */
@RestController
public class IntegrationRestController {

  /**
   * Gets common page.
   *
   * @return the common page
   */
  @GetMapping(
      path = "/common-page",
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
  )
  @SuppressWarnings("unused")
  public ResponseEntity<CommonPageDto> getCommonPage() {
    return ResponseEntity.ok(new CommonPageDto(
        List.of(
            new Person("Anna Livia", "Plurabelle", new Address("Dublin")),
            new Dog("Lassie", new Address("Yorkshire")),
            new Cat("Tom", new Person("Joseph", "Barbera", null))
        ),
        2,
        3,
        10L));
  }

  /**
   * Gets address page.
   *
   * @return the address page
   */
  @GetMapping(
      path = "/address-page",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @SuppressWarnings("unused")
  public ResponseEntity<AddressPage> getAddressPage() {
    return ResponseEntity.ok(new AddressPage(
        List.of(
            new Address("Aachen"),
            new Address("Berlin"),
            new Address("Chemnitz"),
            new Address("Dortmund")
        ),
        0,
        4,
        11L,
        Sort.by("city")
    ));
  }

  /**
   * Gets animal page.
   *
   * @return the animal page
   */
  @GetMapping(
      path = "/animal-page",
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @SuppressWarnings("unused")
  public ResponseEntity<AnimalPage> getAnimalPage() {
    return ResponseEntity.ok(new AnimalPage(
        List.of(
            new Dog("Lassie", new Address("Yorkshire")),
            new Cat("Tom", new Person("Joseph", "Barbera", null))
        ),
        3,
        2,
        11L,
        Sort.by("city")
    ));
  }

}
