/*
 * Copyright 2019-2022 the original author or authors.
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

package org.bremersee.garmin.model;

/**
 * The common phone number interface.
 *
 * @author Christian Bremer
 */
public interface CommonPhoneNumberT {

  /**
   * Gets phone number.
   *
   * @return the phone number
   */
  String getValue();

  /**
   * Sets phone number.
   *
   * @param value the phone number
   */
  void setValue(String value);

  /**
   * Gets category.
   *
   * @return the category
   */
  String getCategory();

  /**
   * Sets category.
   *
   * @param value the value
   */
  void setCategory(String value);

}
