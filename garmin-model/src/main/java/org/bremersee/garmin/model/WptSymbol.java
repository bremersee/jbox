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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The symbol.
 *
 * @author Christian Bremer
 */
@SuppressWarnings("unused")
public enum WptSymbol {

  /**
   * Flag blue symbol.
   */
  FLAG_BLUE("Flag, Blue", WptSymbolCategory.MARKERS),

  /**
   * Flag green symbol.
   */
  FLAG_GREEN("Flag, Green", WptSymbolCategory.MARKERS),

  /**
   * Flag red symbol.
   */
  FLAG_RED("Flag, Red", WptSymbolCategory.MARKERS),

  /**
   * Civil symbol.
   */
  CIVIL("Civil", WptSymbolCategory.MARKERS),

  /**
   * Pin blue symbol.
   */
  PIN_BLUE("Pin, Blue", WptSymbolCategory.MARKERS),

  /**
   * Pin green symbol.
   */
  PIN_GREEN("Pin, Green", WptSymbolCategory.MARKERS),

  /**
   * Pin red symbol.
   */
  PIN_RED("Pin, Red", WptSymbolCategory.MARKERS),

  /**
   * The golf course symbol.
   */
  GOLF_COURSE("Golf Course", WptSymbolCategory.MARKERS),

  /**
   * Block blue symbol.
   */
  BLOCK_BLUE("Block, Blue", WptSymbolCategory.MARKERS),

  /**
   * Block green symbol.
   */
  BLOCK_GREEN("Block, Green", WptSymbolCategory.MARKERS),

  /**
   * Block red symbol.
   */
  BLOCK_RED("Block, Red", WptSymbolCategory.MARKERS),

  /**
   * Stadium symbol.
   */
  STADIUM("Stadium", WptSymbolCategory.MARKERS),

  /**
   * Navaid blue symbol.
   */
  NAVAID_BLUE("Navaid, Blue", WptSymbolCategory.MARKERS),

  /**
   * Navaid green symbol.
   */
  NAVAID_GREEN("Navaid, Green", WptSymbolCategory.MARKERS),

  /**
   * Navaid red symbol.
   */
  NAVAID_RED("Navaid, Red", WptSymbolCategory.MARKERS),

  /**
   * Navaid white symbol.
   */
  NAVAID_WHITE("Navaid, White", WptSymbolCategory.MARKERS),

  /**
   * Navaid amber symbol.
   */
  NAVAID_AMBER("Navaid, Amber", WptSymbolCategory.MARKERS),

  /**
   * Navaid black symbol.
   */
  NAVAID_BLACK("Navaid, Black", WptSymbolCategory.MARKERS),

  /**
   * Navaid orange symbol.
   */
  NAVAID_ORANGE("Navaid, Orange", WptSymbolCategory.MARKERS),

  /**
   * Navaid violet symbol.
   */
  NAVAID_VIOLET("Navaid, Violet", WptSymbolCategory.MARKERS),

  /**
   * City small symbol.
   */
  CITY_SMALL("City (Small)", WptSymbolCategory.MARKERS),

  /**
   * City medium symbol.
   */
  CITY_MEDIUM("City (Medium)", WptSymbolCategory.MARKERS),

  /**
   * City large symbol.
   */
  CITY_LARGE("City (Large)", WptSymbolCategory.MARKERS),

  /**
   * Crossing symbol.
   */
  CROSSING("Crossing", WptSymbolCategory.MARKERS),

  /**
   * Residence symbol.
   */
  RESIDENCE("Residence", WptSymbolCategory.MARKERS),

  /**
   * The fishing hot spot facility symbol.
   */
  FISHING_HOT_SPOT_FACILITY("Fishing Hot Spot Facility", WptSymbolCategory.MARKERS),

  /**
   * Lodge symbol.
   */
  LODGE("Lodge", WptSymbolCategory.MARKERS),

  /**
   * Museum symbol.
   */
  MUSEUM("Museum", WptSymbolCategory.MARKERS),

  /**
   * Campground symbol.
   */
  CAMPGROUND("Campground", WptSymbolCategory.OUTDOORS),

  /**
   * The trail head symbol.
   */
  TRAIL_HEAD("Trail Head", WptSymbolCategory.OUTDOORS),

  /**
   * Park symbol.
   */
  PARK("Park", WptSymbolCategory.OUTDOORS),

  /**
   * Forest symbol.
   */
  FOREST("Forest", WptSymbolCategory.OUTDOORS),

  /**
   * Summit symbol.
   */
  SUMMIT("Summit", WptSymbolCategory.OUTDOORS),

  /**
   * The fishing area symbol.
   */
  FISHING_AREA("Fishing Area", WptSymbolCategory.OUTDOORS),

  /**
   * Geocache symbol.
   */
  GEOCACHE("Geocache", WptSymbolCategory.OUTDOORS),

  /**
   * The geocache found symbol.
   */
  GEOCACHE_FOUND("Geocache Found", WptSymbolCategory.OUTDOORS),

  /**
   * The picnic area symbol.
   */
  PICNIC_AREA("Picnic Area", WptSymbolCategory.OUTDOORS),

  /**
   * Restroom symbol.
   */
  RESTROOM("Restroom", WptSymbolCategory.OUTDOORS),

  /**
   * Shower symbol.
   */
  SHOWER("Shower", WptSymbolCategory.OUTDOORS),

  /**
   * Beach symbol.
   */
  BEACH("Beach", WptSymbolCategory.OUTDOORS),

  /**
   * The RV park symbol.
   */
  RV_PARK("RV Park", WptSymbolCategory.OUTDOORS),

  /**
   * The scenic area symbol.
   */
  SCENIC_AREA("Scenic Area", WptSymbolCategory.OUTDOORS),

  /**
   * The ski resort symbol.
   */
  SKI_RESORT("Ski Resort", WptSymbolCategory.OUTDOORS),

  /**
   * The swimming area symbol.
   */
  SWIMMING_AREA("Swimming Area", WptSymbolCategory.OUTDOORS),

  /**
   * The skiing area symbol.
   */
  SKIING_AREA("Skiing Area", WptSymbolCategory.OUTDOORS),

  /**
   * The bike trail symbol.
   */
  BIKE_TRAIL("Bike Trail", WptSymbolCategory.OUTDOORS),

  /**
   * The drinking water symbol.
   */
  DRINKING_WATER("Drinking Water", WptSymbolCategory.OUTDOORS),

  /**
   * Tunnel symbol.
   */
  TUNNEL("Tunnel", WptSymbolCategory.OUTDOORS),

  /**
   * The parachute area symbol.
   */
  PARACHUTE_AREA("Parachute Area", WptSymbolCategory.OUTDOORS),

  /**
   * The glider area symbol.
   */
  GLIDER_AREA("Glider Area", WptSymbolCategory.OUTDOORS),

  /**
   * The ultralight area symbol.
   */
  ULTRALIGHT_AREA("Ultralight Area", WptSymbolCategory.OUTDOORS),

  /**
   * The upland game symbol.
   */
  UPLAND_GAME("Upland Game", WptSymbolCategory.HUNTING),

  /**
   * Waterfowl symbol.
   */
  WATERFOWL("Waterfowl", WptSymbolCategory.HUNTING),

  /**
   * Furbearer symbol.
   */
  FURBEARER("Furbearer", WptSymbolCategory.HUNTING),

  /**
   * The big game symbol.
   */
  BIG_GAME("Big Game", WptSymbolCategory.HUNTING),

  /**
   * The small game symbol.
   */
  SMALL_GAME("Small Game", WptSymbolCategory.HUNTING),

  /**
   * Covey symbol.
   */
  COVEY("Covey", WptSymbolCategory.HUNTING),

  /**
   * Cover symbol.
   */
  COVER("Cover", WptSymbolCategory.HUNTING),

  /**
   * The treed quarry symbol.
   */
  TREED_QUARRY("Treed Quarry", WptSymbolCategory.HUNTING),

  /**
   * The water source symbol.
   */
  WATER_SOURCE("Water Source", WptSymbolCategory.HUNTING),

  /**
   * The food source symbol.
   */
  FOOD_SOURCE("Food Source", WptSymbolCategory.HUNTING),

  /**
   * The animal tracks symbol.
   */
  ANIMAL_TRACKS("Animal Tracks", WptSymbolCategory.HUNTING),

  /**
   * The blood trail symbol.
   */
  BLOOD_TRAIL("Blood Trail", WptSymbolCategory.HUNTING),

  /**
   * Truck symbol.
   */
  TRUCK("Truck", WptSymbolCategory.HUNTING),

  /**
   * Atv symbol.
   */
  ATV("ATV", WptSymbolCategory.HUNTING),

  /**
   * Blind symbol.
   */
  BLIND("Blind", WptSymbolCategory.HUNTING),

  /**
   * The tree stand symbol.
   */
  TREE_STAND("Tree Stand", WptSymbolCategory.HUNTING),

  /**
   * Anchor symbol.
   */
  ANCHOR("Anchor", WptSymbolCategory.MARINE),

  /**
   * The man overboard symbol.
   */
  MAN_OVERBOARD("Man Overboard", WptSymbolCategory.MARINE),

  /**
   * The diver down flag 1 symbol.
   */
  DIVER_DOWN_FLAG_1("Diver Down Flag 1", WptSymbolCategory.MARINE),

  /**
   * The diver down flag 2 symbol.
   */
  DIVER_DOWN_FLAG_2("Diver Down Flag 2", WptSymbolCategory.MARINE),

  /**
   * The skull and crossbones symbol.
   */
  SKULL_AND_CROSSBONES("Skull and Crossbones", WptSymbolCategory.MARINE),

  /**
   * Light symbol.
   */
  LIGHT("Light", WptSymbolCategory.MARINE),

  /**
   * Buoy white symbol.
   */
  BUOY_WHITE("Buoy, White", WptSymbolCategory.MARINE),

  /**
   * Shipwreck symbol.
   */
  SHIPWRECK("Shipwreck", WptSymbolCategory.MARINE),

  /**
   * The radio beacon symbol.
   */
  RADIO_BEACON("Radio Beacon", WptSymbolCategory.MARINE),

  /**
   * Horn symbol.
   */
  HORN("Horn", WptSymbolCategory.MARINE),

  /**
   * The controlled area symbol.
   */
  CONTROLLED_AREA("Controlled Area", WptSymbolCategory.MARINE),

  /**
   * The restricted area symbol.
   */
  RESTRICTED_AREA("Restricted Area", WptSymbolCategory.MARINE),

  /**
   * The danger area symbol.
   */
  DANGER_AREA("Danger Area", WptSymbolCategory.MARINE),

  /**
   * Restaurant symbol.
   */
  RESTAURANT("Restaurant", WptSymbolCategory.MARINE),

  /**
   * Bridge symbol.
   */
  BRIDGE("Bridge", WptSymbolCategory.MARINE),

  /**
   * Dam symbol.
   */
  DAM("Dam", WptSymbolCategory.MARINE),

  /**
   * The boat ramp symbol.
   */
  BOAT_RAMP("Boat Ramp", WptSymbolCategory.MARINE),

  /**
   * The gas station symbol.
   */
  GAS_STATION("Gas Station", WptSymbolCategory.MARINE),

  /**
   * Building symbol.
   */
  BUILDING("Building", WptSymbolCategory.CIVIL),

  /**
   * Church symbol.
   */
  CHURCH("Church", WptSymbolCategory.CIVIL),

  /**
   * Cemetery symbol.
   */
  CEMETERY("Cemetery", WptSymbolCategory.CIVIL),

  /**
   * The tall tower symbol.
   */
  TALL_TOWER("Tall Tower", WptSymbolCategory.CIVIL),

  /**
   * The short tower symbol.
   */
  SHORT_TOWER("Short Tower", WptSymbolCategory.CIVIL),

  /**
   * The oil field symbol.
   */
  OIL_FIELD("Oil Field", WptSymbolCategory.CIVIL),

  /**
   * Mine symbol.
   */
  MINE("Mine", WptSymbolCategory.CIVIL),

  /**
   * School symbol.
   */
  SCHOOL("School", WptSymbolCategory.CIVIL),

  /**
   * The police station symbol.
   */
  POLICE_STATION("Police Station", WptSymbolCategory.CIVIL),

  /**
   * Bell symbol.
   */
  BELL("Bell", WptSymbolCategory.CIVIL),

  /**
   * Car symbol.
   */
  CAR("Car", WptSymbolCategory.TRANSPORTATION),

  /**
   * The car rental symbol.
   */
  CAR_RENTAL("Car Rental", WptSymbolCategory.TRANSPORTATION),

  /**
   * The car repair symbol.
   */
  CAR_REPAIR("Car Repair", WptSymbolCategory.TRANSPORTATION),

  /**
   * The convenience store symbol.
   */
  CONVENIENCE_STORE("Convenience Store", WptSymbolCategory.TRANSPORTATION),

  /**
   * Scales symbol.
   */
  SCALES("Scales", WptSymbolCategory.TRANSPORTATION),

  /**
   * Airport symbol.
   */
  AIRPORT("Airport", WptSymbolCategory.TRANSPORTATION),

  /**
   * The truck stop symbol.
   */
  TRUCK_STOP("Truck Stop", WptSymbolCategory.TRANSPORTATION),

  /**
   * Wrecker symbol.
   */
  WRECKER("Wrecker", WptSymbolCategory.TRANSPORTATION),

  /**
   * The toll booth symbol.
   */
  TOLL_BOOTH("Toll Booth", WptSymbolCategory.TRANSPORTATION),

  /**
   * Lodging symbol.
   */
  LODGING("Lodging", WptSymbolCategory.TRANSPORTATION),

  /**
   * The parking area symbol.
   */
  PARKING_AREA("Parking Area", WptSymbolCategory.TRANSPORTATION),

  /**
   * Navaid green white symbol.
   */
  NAVAID_GREEN_WHITE("Navaid, Green/White", WptSymbolCategory.NAVAIDS),

  /**
   * Navaid green red symbol.
   */
  NAVAID_GREEN_RED("Navaid, Green/Red", WptSymbolCategory.NAVAIDS),

  /**
   * Navaid red green symbol.
   */
  NAVAID_RED_GREEN("Navaid, Red/Green", WptSymbolCategory.NAVAIDS),

  /**
   * Navaid red white symbol.
   */
  NAVAID_RED_WHITE("Navaid, Red/White", WptSymbolCategory.NAVAIDS),

  /**
   * Navaid white green symbol.
   */
  NAVAID_WHITE_GREEN("Navaid, White/Green", WptSymbolCategory.NAVAIDS),

  /**
   * Navaid white red symbol.
   */
  NAVAID_WHITE_RED("Navaid, White/Red", WptSymbolCategory.NAVAIDS),

  /**
   * The shopping center symbol.
   */
  SHOPPING_CENTER("Shopping Center", WptSymbolCategory.SIGNS),

  /**
   * Telephone symbol.
   */
  TELEPHONE("Telephone", WptSymbolCategory.SIGNS),

  /**
   * Information symbol.
   */
  INFORMATION("Information", WptSymbolCategory.SIGNS),

  /**
   * The fitness center symbol.
   */
  FITNESS_CENTER("Fitness Center", WptSymbolCategory.SIGNS),

  /**
   * The ice skating symbol.
   */
  ICE_SKATING("Ice Skating", WptSymbolCategory.SIGNS),

  /**
   * The medical facility symbol.
   */
  MEDICAL_FACILITY("Medical Facility", WptSymbolCategory.SIGNS),

  /**
   * Pharmacy symbol.
   */
  PHARMACY("Pharmacy", WptSymbolCategory.SIGNS),

  /**
   * Bank symbol.
   */
  BANK("Bank", WptSymbolCategory.POI),

  /**
   * Bar symbol.
   */
  BAR("Bar", WptSymbolCategory.POI),

  /**
   * The department store symbol.
   */
  DEPARTMENT_STORE("Department Store", WptSymbolCategory.POI),

  /**
   * The movie theater symbol.
   */
  MOVIE_THEATER("Movie Theater", WptSymbolCategory.POI),

  /**
   * The fast food symbol.
   */
  FAST_FOOD("Fast Food", WptSymbolCategory.POI),

  /**
   * Pizza symbol.
   */
  PIZZA("Pizza", WptSymbolCategory.POI),

  /**
   * The live theater symbol.
   */
  LIVE_THEATER("Live Theater", WptSymbolCategory.POI),

  /**
   * The post office symbol.
   */
  POST_OFFICE("Post Office", WptSymbolCategory.POI),

  /**
   * The ball park symbol.
   */
  BALL_PARK("Ball Park", WptSymbolCategory.POI),

  /**
   * Bowling symbol.
   */
  BOWLING("Bowling", WptSymbolCategory.POI),

  /**
   * The amusement park symbol.
   */
  AMUSEMENT_PARK("Amusement Park", WptSymbolCategory.POI),

  /**
   * Zoo symbol.
   */
  ZOO("Zoo", WptSymbolCategory.POI);

  private final String value;

  private final WptSymbolCategory category;

  WptSymbol(String value, WptSymbolCategory category) {
    this.value = value;
    this.category = category;
  }

  /**
   * Gets category.
   *
   * @return the category
   */
  public WptSymbolCategory getCategory() {
    return category;
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
   * @return the wpt symbol
   */
  @JsonCreator
  public static WptSymbol fromValue(String value) {
    for (WptSymbol e : values()) {
      if (e.value.equalsIgnoreCase(value) || e.name().equalsIgnoreCase(value)) {
        return e;
      }
    }
    return FLAG_BLUE;
  }

}
