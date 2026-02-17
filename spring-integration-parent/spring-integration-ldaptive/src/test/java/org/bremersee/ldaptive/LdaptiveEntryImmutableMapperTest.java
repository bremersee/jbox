/*
 * Copyright 2025 the original author or authors.
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

package org.bremersee.ldaptive;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.ldaptive.LdapEntry;

/**
 * The ldaptive entry immutable mapper test.
 */
class LdaptiveEntryImmutableMapperTest {

  /**
   * Map.
   */
  @Test
  void map() {
    @SuppressWarnings("unchecked")
    LdaptiveEntryImmutableMapper<Object> target = mock(LdaptiveEntryImmutableMapper.class);
    doCallRealMethod()
        .when(target)
        .map(any(LdapEntry.class), any(Object.class));
    LdapEntry ldapEntry = new LdapEntry();
    Object immutable = new Object();
    assertThatExceptionOfType(UnsupportedOperationException.class)
        .isThrownBy(() -> target.map(ldapEntry, immutable));
  }
}