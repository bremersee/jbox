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

import org.ldaptive.LdapEntry;
import org.ldaptive.SearchRequest;

/**
 * The ldaptive entry immutable mapper.
 *
 * @param <T> the type of the immutable domain object
 * @author Christian Bremer
 */
public abstract class LdaptiveEntryImmutableMapper<T> implements LdaptiveEntryMapper<T> {

  /**
   * Instantiates a new ldaptive entry immutable mapper.
   */
  protected LdaptiveEntryImmutableMapper() {
    super();
  }

  /**
   * This method is in this context illegal. It always throws an
   * {@link UnsupportedOperationException}. We need the method to keep this mapper backwards
   * compatible with {@link LdaptiveEntryMapper} that is used in
   * {@link LdaptiveOperations#findOne(SearchRequest, LdaptiveEntryMapper)} and
   * {@link LdaptiveOperations#findAll(SearchRequest, LdaptiveEntryMapper)}.
   *
   * <p>Be aware, that this mapper isn't anymore compatible to
   * {@link org.ldaptive.beans.LdapEntryMapper} for the given reason.
   *
   * @param source the ldap entry
   * @param immutable the immutable
   * @throws UnsupportedOperationException always
   */
  @Override
  public final void map(LdapEntry source, T immutable) {
    throw new UnsupportedOperationException(
        "You can not map a ldap entry into an immutable object.");
  }

}
