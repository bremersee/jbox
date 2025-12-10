package org.bremersee.ldaptive;

import org.ldaptive.LdapEntry;
import org.ldaptive.SearchRequest;

/**
 * The ldaptive entry immutable mapper.
 *
 * @param <T> the type of the immutable domain object
 */
public abstract class LdaptiveEntryImmutableMapper<T> implements LdaptiveEntryMapper<T> {

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
