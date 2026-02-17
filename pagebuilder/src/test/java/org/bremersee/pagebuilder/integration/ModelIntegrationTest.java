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

package org.bremersee.pagebuilder.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.bremersee.pagebuilder.model.CommonPageDto;
import org.bremersee.pagebuilder.testmodel.Address;
import org.bremersee.pagebuilder.testmodel.AddressPage;
import org.bremersee.pagebuilder.testmodel.AnimalPage;
import org.bremersee.pagebuilder.testmodel.Cat;
import org.bremersee.pagebuilder.testmodel.Dog;
import org.bremersee.pagebuilder.testmodel.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * The model integration test.
 *
 * @author Christian Bremer
 */
@SpringBootTest(
    classes = {ModelIntegrationTestConfiguration.class},
    webEnvironment = WebEnvironment.RANDOM_PORT
)
class ModelIntegrationTest {

  /**
   * The rest template builder.
   */
  @Autowired
  TestRestTemplate restTemplate;

  /**
   * Gets common page as xml.
   */
  @Test
  void getCommonPageAsXml() {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setAccept(List.of(MediaType.APPLICATION_XML));
    HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
    ResponseEntity<CommonPageDto> response = restTemplate.exchange(
        "/common-page",
        HttpMethod.GET,
        httpEntity,
        CommonPageDto.class);
    assertThat(response.getBody())
        .isEqualTo(new CommonPageDto(
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
   * Gets common page as json.
   */
  @Test
  void getCommonPageAsJson() {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
    HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
    ResponseEntity<CommonPageDto> response = restTemplate.exchange(
        "/common-page",
        HttpMethod.GET,
        httpEntity,
        CommonPageDto.class);

    // with global 'objectMapper.activateDefaultTypingAsProperty'
    assertThat(response.getBody())
        .isEqualTo(new CommonPageDto(
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
   * Gets address page as json.
   */
  @Test
  void getAddressPageAsJson() {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
    HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
    ResponseEntity<AddressPage> response = restTemplate.exchange(
        "/address-page",
        HttpMethod.GET,
        httpEntity,
        AddressPage.class);
    assertThat(response.getBody())
        .isEqualTo(new AddressPage(
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
   * Gets animal page as json.
   */
  @Test
  void getAnimalPageAsJson() {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
    HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
    ResponseEntity<AnimalPage> response = restTemplate.exchange(
        "/animal-page",
        HttpMethod.GET,
        httpEntity,
        AnimalPage.class);
    assertThat(response.getBody())
        .isEqualTo(new AnimalPage(
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

  /**
   * Gets animal page as xml.
   */
  @Test
  void getAnimalPageAsXml() {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setAccept(List.of(MediaType.APPLICATION_XML));
    HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
    ResponseEntity<AnimalPage> response = restTemplate.exchange(
        "/animal-page",
        HttpMethod.GET,
        httpEntity,
        AnimalPage.class);
    assertThat(response.getBody())
        .isEqualTo(new AnimalPage(
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

  /**
   * Open api.
   */
  @Test
  void openApi() {
    ResponseEntity<String> response = restTemplate.getForEntity(
        "/v3/api-docs.yaml",
        String.class);
    assertThat(response.getBody()).isNotNull();
    System.out.println(response.getBody());
  }

}
