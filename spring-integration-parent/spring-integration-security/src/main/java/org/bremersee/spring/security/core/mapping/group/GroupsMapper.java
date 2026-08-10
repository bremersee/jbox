package org.bremersee.spring.security.core.mapping.group;

import java.util.Collection;
import org.bremersee.spring.security.core.Group;
import org.jspecify.annotations.NonNull;

/**
 * The mapper of groups.
 */
public interface GroupsMapper {

  /**
   * Map groups.
   *
   * @param groups the groups
   * @return the collection
   */
  @NonNull
  Collection<Group> mapGroups(@NonNull Collection<? extends Group> groups);

}
