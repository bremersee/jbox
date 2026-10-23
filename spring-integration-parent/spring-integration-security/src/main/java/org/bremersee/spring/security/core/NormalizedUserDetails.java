package org.bremersee.spring.security.core;

import org.immutables.serial.Serial;
import org.immutables.value.Value;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * The interface Normalized user details.
 */
@Value.Style(
    visibility = Value.Style.ImplementationVisibility.PACKAGE,
    overshadowImplementation = true,
    depluralize = true,
    jdk9Collections = true,
    get = {"get*", "is*"})
@Value.Immutable
@Serial.Version(1L)
public interface NormalizedUserDetails extends NormalizedPrincipalDetails {

  @Nullable
  @Override
  String getPassword();

  @Override
  @NullMarked
  String getUsername();

  @Nullable
  @Override
  String getFirstName();

  @Nullable
  @Override
  String getLastName();

  @Nullable
  @Override
  String getEmail();

  /**
   * Returns empty normalized user details.
   *
   * @return the normalized user details
   */
  static NormalizedUserDetails empty() {
    return builder()
        .username("")
        .build();
  }

  /**
   * Returns the builder.
   *
   * @return the builder
   */
  static Builder builder() {
    return new Builder();
  }

  /**
   * The immutable builder.
   */
  class Builder extends ImmutableNormalizedUserDetails.Builder {

    /**
     * Instantiates a new builder.
     */
    public Builder() {
      super();
    }

  }

}
