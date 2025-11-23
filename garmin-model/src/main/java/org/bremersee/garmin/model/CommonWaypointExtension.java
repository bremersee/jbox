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

import java.util.List;

/**
 * The common waypoint extension interface.
 *
 * @author Christian Bremer
 */
@SuppressWarnings("unused")
public interface CommonWaypointExtension {

  /**
   * Gets proximity.
   *
   * @return the proximity
   */
  Double getProximity();

  /**
   * Sets proximity.
   *
   * @param value the value
   */
  void setProximity(Double value);

  /**
   * Gets temperature.
   *
   * @return the temperature
   */
  Double getTemperature();

  /**
   * Sets temperature.
   *
   * @param value the value
   */
  void setTemperature(Double value);

  /**
   * Gets depth.
   *
   * @return the depth
   */
  Double getDepth();

  /**
   * Sets depth.
   *
   * @param value the value
   */
  void setDepth(Double value);

  /**
   * Gets display mode.
   *
   * @return the display mode
   */
  CommonDisplayModeT getDisplayMode();

  /**
   * Gets categories.
   *
   * @return the categories
   */
  CommonCategoriesT getCategories();

  /**
   * Gets address.
   *
   * @return the address
   */
  CommonAddressT getAddress();

  /**
   * Gets phone numbers.
   *
   * @return the phone numbers
   */
  List<? extends CommonPhoneNumberT> getPhoneNumbers();

}
