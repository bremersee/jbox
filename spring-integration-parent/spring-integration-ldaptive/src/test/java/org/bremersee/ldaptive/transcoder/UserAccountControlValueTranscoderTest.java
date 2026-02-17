/*
 * Copyright 2014 the original author or authors.
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

package org.bremersee.ldaptive.transcoder;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The user account control value transcoder test.
 *
 * @author Christian Bremer
 */
class UserAccountControlValueTranscoderTest {

  private UserAccountControlValueTranscoder target;

  /**
   * Sets up.
   */
  @BeforeEach
  void setUp() {
    target = new UserAccountControlValueTranscoder();
  }

  /**
   * Decode string value.
   */
  @Test
  void decodeStringValue() {
    UserAccountControl expected = new UserAccountControl();
    expected.setEnabled(true);
    expected.setPasswordExpirationEnabled(true);
    UserAccountControl actual = target.decodeStringValue(String.valueOf(expected.getValue()));
    assertThat(actual).isEqualTo(expected);
  }

  /**
   * Encode string value.
   */
  @Test
  void encodeStringValue() {
    UserAccountControl expected = new UserAccountControl();
    expected.setEnabled(false);
    expected.setPasswordExpirationEnabled(false);
    String actual = target.encodeStringValue(expected);
    assertThat(actual).isEqualTo(String.valueOf(expected.getValue()));
  }

  /**
   * Gets type.
   */
  @Test
  void getType() {
    assertThat(target.getType()).isEqualTo(UserAccountControl.class);
  }

}