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
 * The trip transportation mode.
 *
 * @author Christian Bremer
 */
@SuppressWarnings("unused")
public enum TripTransportationMode {

  /**
   * Automotive transportation mode.
   */
  AUTOMOTIVE("Automotive"),

  /**
   * Motorcycling transportation mode.
   */
  MOTORCYCLING("Motorcycling"),

  /**
   * Walking transportation mode.
   */
  WALKING("Walking"),

  /**
   * Hiking transportation mode.
   */
  HIKING("Hiking"),

  /**
   * Mountaineering transportation mode.
   */
  MOUNTAINEERING("Mountaineering"),

  /**
   * Bicycling transportation mode.
   */
  BICYCLING("Bicycling"),

  /**
   * Tour cycling transportation mode.
   */
  TOUR_CYCLING("TourCycling"), //

  /**
   * Mountain biking transportation mode.
   */
  MOUNTAIN_BIKING("MountainBiking"), // Mountain Bike

  /**
   * Atv transportation mode.
   */
  ATV("ATV"), // Geländefahrzeug

  /**
   * Dirt biking transportation mode.
   */
  DIRT_BIKING("DirtBiking"),

  /**
   * Trucking transportation mode.
   */
  TRUCKING("Trucking"),

  /**
   * Rv transportation mode.
   */
  RV("RV"), // Wohnmobil

  /**
   * Offroad transportation mode.
   */
  OFFROAD("OffRoad");

  private final String value;

  TripTransportationMode(String value) {
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
   * @return the trip transportation mode
   */
  @JsonCreator
  public static TripTransportationMode fromValue(String value) {
    for (TripTransportationMode e : values()) {
      if (e.value.equalsIgnoreCase(value) || e.name().equalsIgnoreCase(value)) {
        return e;
      }
    }
    return AUTOMOTIVE;
  }
}
