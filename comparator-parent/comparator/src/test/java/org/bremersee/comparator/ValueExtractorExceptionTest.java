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

package org.bremersee.comparator;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * The value extractor exception test.
 *
 * @author Christian Bremer
 */
class ValueExtractorExceptionTest {

  /**
   * Test with one argument.
   */
  @Test
  void testWithOneArgument() {
    var exception = new ValueExtractorException("Test exception");
    assertThat(exception.getMessage())
        .isEqualTo("Test exception");
  }

  /**
   * Test with two arguments.
   */
  @Test
  void testWithTwoArguments() {
    var cause = new Exception("Cause");
    var exception = new ValueExtractorException("Test exception", cause);
    assertThat(exception.getCause())
        .isEqualTo(cause);
  }

}