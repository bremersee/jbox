package org.bremersee.ldaptive.transcoder;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The generalized time to offset date time value transcoder test.
 */
class GeneralizedTimeToOffsetDateTimeValueTranscoderTest {

  private GeneralizedTimeToOffsetDateTimeValueTranscoder target;

  private OffsetDateTime dateTime;

  private String dateTimeStr;

  /**
   * Sets up.
   */
  @BeforeEach
  void setUp() {
    target = new GeneralizedTimeToOffsetDateTimeValueTranscoder();
    dateTime = OffsetDateTime.parse("2025-12-07T14:16:10.437Z");
    dateTimeStr = "20251207141610.437Z";
  }

  /**
   * Decode string value.
   */
  @Test
  void decodeStringValue() {
    OffsetDateTime actual = target.decodeStringValue(dateTimeStr);
    assertThat(actual).isEqualTo(dateTime);
  }

  /**
   * Encode string value.
   */
  @Test
  void encodeStringValue() {
    String actual = target.encodeStringValue(dateTime);
    assertThat(actual).isEqualTo(dateTimeStr);
  }

  /**
   * Gets type.
   */
  @Test
  void getType() {
    assertThat(target.getType()).isEqualTo(OffsetDateTime.class);
  }
}