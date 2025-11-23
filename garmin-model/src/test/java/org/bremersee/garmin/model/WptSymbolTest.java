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

package org.bremersee.garmin.model;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * The wpt symbol test.
 */
@ExtendWith(SoftAssertionsExtension.class)
class WptSymbolTest {

  /**
   * Gets category.
   *
   * @param softly the soft assertions
   */
  @Test
  void getCategory(SoftAssertions softly) {
    for (WptSymbol wptSymbol : WptSymbol.values()) {
      softly.assertThat(wptSymbol.getCategory()).isNotNull();
    }
  }

  /**
   * From value.
   *
   * @param softly the soft assertions
   */
  @Test
  void fromValue(SoftAssertions softly) {
    for (WptSymbol expected : WptSymbol.values()) {
      softly.assertThat(WptSymbol.fromValue(expected.toString()))
          .isEqualTo(expected);
    }
    softly.assertThat(WptSymbol.fromValue(null))
        .isEqualTo(WptSymbol.FLAG_BLUE);
  }
}