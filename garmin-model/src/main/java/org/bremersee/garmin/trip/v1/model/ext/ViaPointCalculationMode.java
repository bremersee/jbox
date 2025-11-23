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

package org.bremersee.garmin.trip.v1.model.ext;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The via point calculation mode.
 *
 * @author Christian Bremer
 */
@SuppressWarnings("unused")
public enum ViaPointCalculationMode {

  /**
   * Faster time calculation mode.
   */
  FASTER_TIME("FasterTime"),

  /**
   * Shorter distance calculation mode.
   */
  SHORTER_DISTANCE("ShorterDistance"),

  /**
   * Curvy roads calculation mode.
   */
  CURVY_ROADS("CurvyRoads"),

  /**
   * Direct calculation mode.
   */
  DIRECT("Direct"); // transportation mode is then not set

  private final String value;

  ViaPointCalculationMode(String value) {
    this.value = value;
  }

  @JsonValue
  @Override
  public String toString() {
    return value;
  }

  /**
   * From value.
   *
   * @param value the value
   * @return the via point calculation mode
   */
  @JsonCreator
  public static ViaPointCalculationMode fromValue(String value) {
    for (ViaPointCalculationMode e : ViaPointCalculationMode.values()) {
      if (e.value.equalsIgnoreCase(value) || e.name().equalsIgnoreCase(value)) {
        return e;
      }
    }
    return FASTER_TIME;
  }
}
