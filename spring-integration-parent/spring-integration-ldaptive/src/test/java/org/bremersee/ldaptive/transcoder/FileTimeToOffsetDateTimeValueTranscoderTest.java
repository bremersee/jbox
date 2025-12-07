package org.bremersee.ldaptive.transcoder;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The file time to offset date time value transcoder test.
 */
class FileTimeToOffsetDateTimeValueTranscoderTest {

  private FileTimeToOffsetDateTimeValueTranscoder target;

  private OffsetDateTime dateTime;

  private String dateTimeStr;

  /**
   * Sets up.
   */
  @BeforeEach
  void setUp() {
    target = new FileTimeToOffsetDateTimeValueTranscoder();
    dateTime = OffsetDateTime.parse("2025-12-07T14:16:10.437Z");
    dateTimeStr = "134095905704370000";
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