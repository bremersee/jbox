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

package org.bremersee.garmin.trip.v1.model.ext;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * The trip transportation mode test.
 */
@ExtendWith(SoftAssertionsExtension.class)
class TripTransportationModeTest {

  /**
   * From value.
   *
   * @param softly the soft assertions
   */
  @Test
  void fromValue(SoftAssertions softly) {
    for (TripTransportationMode expected : TripTransportationMode.values()) {
      softly.assertThat(TripTransportationMode.fromValue(expected.toString()))
          .isEqualTo(expected);
    }
    softly.assertThat(TripTransportationMode.fromValue(null))
        .isEqualTo(TripTransportationMode.AUTOMOTIVE);
  }
}