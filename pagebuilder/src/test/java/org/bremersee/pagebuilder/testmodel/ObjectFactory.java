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

import jakarta.xml.bind.annotation.XmlRegistry;

/**
 * The xml object factory og the test model.
 *
 * @author Christian Bremer
 */
@SuppressWarnings("unused")
@XmlRegistry
public class ObjectFactory {

  /**
   * Create address.
   *
   * @return the address
   */
  public Address createAddress() {
    return new Address();
  }

  /**
   * Create cat.
   *
   * @return the cat
   */
  public Cat createCat() {
    return new Cat();
  }

  /**
   * Create dog.
   *
   * @return the dog
   */
  public Dog createDog() {
    return new Dog();
  }

  /**
   * Create person.
   *
   * @return the person
   */
  public Person createPerson() {
    return new Person();
  }

  /**
   * Create animal page.
   *
   * @return the animal page
   */
  public AnimalPage createAnimalPage() {
    return new AnimalPage();
  }

  /**
   * Create animal slice.
   *
   * @return the animal slice
   */
  public AnimalSlice createAnimalSlice() {
    return new AnimalSlice();
  }

}
