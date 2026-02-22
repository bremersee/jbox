/*
 * Copyright 2020 the original author or authors.
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

package org.bremersee.xml.test.app;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bremersee.xml.test.model.xml1.Person;
import org.bremersee.xml.test.model.xml2.Vehicle;
import org.bremersee.xml.test.model.xml2.Vehicles;
import org.bremersee.xml.test.model.xml3.Company;
import org.bremersee.xml.test.model.xml4.Address;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * The xml controller.
 *
 * @author Christian Bremer
 */
@RestController
public class XmlController {

  private static final Log log = LogFactory.getLog(XmlController.class);

  /**
   * Post person flux.
   *
   * @param model the model
   * @return the flux
   */
  @PostMapping(
      path = "/person",
      consumes = MediaType.APPLICATION_XML_VALUE,
      produces = MediaType.APPLICATION_XML_VALUE)
  public Flux<Person> postPerson(@RequestBody Person model) {
    log.info(String.format("Echo person = %s", model));
    return Flux.just(model);
  }

  /**
   * Post vehicle flux.
   *
   * @param model the model
   * @return the flux
   */
  @PostMapping(
      path = "/vehicle",
      consumes = MediaType.APPLICATION_XML_VALUE,
      produces = MediaType.APPLICATION_XML_VALUE)
  public Flux<Vehicle> postVehicle(@RequestBody Vehicle model) {
    log.info(String.format("Echo vehicle = %s", model));
    return Flux.just(model);
  }

  /**
   * Post vehicles flux.
   *
   * @param model the model
   * @return the flux
   */
  @PostMapping(
      path = "/vehicles",
      consumes = MediaType.APPLICATION_XML_VALUE,
      produces = MediaType.APPLICATION_XML_VALUE)
  public Flux<Vehicles> postVehicles(@RequestBody Vehicles model) {
    log.info(String.format("Echo vehicles = %s", model));
    return Flux.just(model);
  }

  /**
   * Post company flux.
   *
   * @param model the model
   * @return the flux
   */
  @PostMapping(
      path = "/company",
      consumes = MediaType.APPLICATION_XML_VALUE,
      produces = MediaType.APPLICATION_XML_VALUE)
  public Flux<Company> postCompany(@RequestBody Company model) {
    log.info(String.format("Echo company = %s", model));
    return Flux.just(model);
  }

  /**
   * Post address flux.
   *
   * @param model the model
   * @return the flux
   */
  @PostMapping(
      path = "/address",
      consumes = MediaType.APPLICATION_XML_VALUE,
      produces = MediaType.APPLICATION_XML_VALUE)
  public Flux<Address> postAddress(@RequestBody Address model) {
    log.info(String.format("Echo address = %s", model));
    return Flux.just(model);
  }

}
