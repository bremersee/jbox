package org.bremersee.spring.security.core;

import org.immutables.serial.Serial;
import org.immutables.value.Value;
import org.jspecify.annotations.NonNull;

/**
 * The normalized group.
 */
@Value.Style(
    visibility = Value.Style.ImplementationVisibility.PACKAGE,
    overshadowImplementation = true,
    depluralize = true,
    jdk9Collections = true,
    get = {"get*", "is*"},
    withUnaryOperator = "with*")
@Value.Immutable
@Serial.Version(1L)
public interface NormalizedGroup extends Group {

  /**
   * Creates a new normalized group with the given name.
   *
   * @param name the name of the group
   * @return the normalized group
   */
  static NormalizedGroup of(@NonNull String name) {
    return builder().name(name).build();
  }

  /**
   * Gets a new builder.
   *
   * @return the builder
   */
  static Builder builder() {
    return new Builder();
  }

  /**
   * The Builder.
   */
  class Builder extends ImmutableNormalizedGroup.Builder {

  }

}
