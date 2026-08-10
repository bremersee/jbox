package org.bremersee.spring.security.core;

import java.io.Serializable;
import org.jspecify.annotations.NonNull;

/**
 * The group.
 */
public interface Group extends Serializable {

  /**
   * Gets name of the group.
   *
   * @return the name
   */
  @NonNull
  String getName();

}
