package org.bremersee.ldaptive.transcoder;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * The value transcoder factory test.
 */
class ValueTranscoderFactoryTest {

  /**
   * Gets attribute type value transcoder.
   */
  @Test
  void getAttributeTypeValueTranscoder() {
    assertThat(ValueTranscoderFactory.getAttributeTypeValueTranscoder()).isNotNull();
  }

  /**
   * Gets big integer value transcoder.
   */
  @Test
  void getBigIntegerValueTranscoder() {
    assertThat(ValueTranscoderFactory.getBigIntegerValueTranscoder()).isNotNull();
  }

  /**
   * Gets boolean value transcoder.
   */
  @Test
  void getBooleanValueTranscoder() {
    assertThat(ValueTranscoderFactory.getBooleanValueTranscoder()).isNotNull();
  }

  /**
   * Gets boolean primitive value transcoder.
   */
  @Test
  void getBooleanPrimitiveValueTranscoder() {
    assertThat(ValueTranscoderFactory.getBooleanPrimitiveValueTranscoder()).isNotNull();
  }

  /**
   * Gets byte array value transcoder.
   */
  @Test
  void getByteArrayValueTranscoder() {
    assertThat(ValueTranscoderFactory.getByteArrayValueTranscoder()).isNotNull();
  }

  /**
   * Gets delta time value transcoder.
   */
  @Test
  void getDeltaTimeValueTranscoder() {
    assertThat(ValueTranscoderFactory.getDeltaTimeValueTranscoder()).isNotNull();
  }

  /**
   * Gets dn value transcoder.
   */
  @Test
  void getDnValueTranscoder() {
    assertThat(ValueTranscoderFactory.getDnValueTranscoder()).isNotNull();
  }

  /**
   * Gets dn value transcoder case sensitive.
   */
  @Test
  void getDnValueTranscoderCaseSensitive() {
    assertThat(ValueTranscoderFactory.getDnValueTranscoderCaseSensitive()).isNotNull();
  }

  /**
   * Gets double value transcoder.
   */
  @Test
  void getDoubleValueTranscoder() {
    assertThat(ValueTranscoderFactory.getDoubleValueTranscoder()).isNotNull();
  }

  /**
   * Gets double primitive value transcoder.
   */
  @Test
  void getDoublePrimitiveValueTranscoder() {
    assertThat(ValueTranscoderFactory.getDoublePrimitiveValueTranscoder()).isNotNull();
  }

  /**
   * Gets file time value transcoder.
   */
  @Test
  void getFileTimeValueTranscoder() {
    assertThat(ValueTranscoderFactory.getFileTimeValueTranscoder()).isNotNull();
  }

  /**
   * Gets float value transcoder.
   */
  @Test
  void getFloatValueTranscoder() {
    assertThat(ValueTranscoderFactory.getFloatValueTranscoder()).isNotNull();
  }

  /**
   * Gets float primitive value transcoder.
   */
  @Test
  void getFloatPrimitiveValueTranscoder() {
    assertThat(ValueTranscoderFactory.getFloatPrimitiveValueTranscoder()).isNotNull();
  }

  /**
   * Gets generalized time value transcoder.
   */
  @Test
  void getGeneralizedTimeValueTranscoder() {
    assertThat(ValueTranscoderFactory.getGeneralizedTimeValueTranscoder()).isNotNull();
  }

  /**
   * Gets integer value transcoder.
   */
  @Test
  void getIntegerValueTranscoder() {
    assertThat(ValueTranscoderFactory.getIntegerValueTranscoder()).isNotNull();
  }

  /**
   * Gets integer primitive value transcoder.
   */
  @Test
  void getIntegerPrimitiveValueTranscoder() {
    assertThat(ValueTranscoderFactory.getIntegerPrimitiveValueTranscoder()).isNotNull();
  }

  /**
   * Gets long value transcoder.
   */
  @Test
  void getLongValueTranscoder() {
    assertThat(ValueTranscoderFactory.getLongValueTranscoder()).isNotNull();
  }

  /**
   * Gets long primitive value transcoder.
   */
  @Test
  void getLongPrimitiveValueTranscoder() {
    assertThat(ValueTranscoderFactory.getLongPrimitiveValueTranscoder()).isNotNull();
  }

  /**
   * Gets short value transcoder.
   */
  @Test
  void getShortValueTranscoder() {
    assertThat(ValueTranscoderFactory.getShortValueTranscoder()).isNotNull();
  }

  /**
   * Gets short primitive value transcoder.
   */
  @Test
  void getShortPrimitiveValueTranscoder() {
    assertThat(ValueTranscoderFactory.getShortPrimitiveValueTranscoder()).isNotNull();
  }

  /**
   * Gets string value transcoder.
   */
  @Test
  void getStringValueTranscoder() {
    assertThat(ValueTranscoderFactory.getStringValueTranscoder()).isNotNull();
  }

  /**
   * Gets user account control value transcoder.
   */
  @Test
  void getUserAccountControlValueTranscoder() {
    assertThat(ValueTranscoderFactory.getUserAccountControlValueTranscoder()).isNotNull();
  }

  /**
   * Gets uuid value transcoder.
   */
  @Test
  void getUuidValueTranscoder() {
    assertThat(ValueTranscoderFactory.getUuidValueTranscoder()).isNotNull();
  }

  /**
   * Gets unicode pwd value transcoder.
   */
  @Test
  void getUnicodePwdValueTranscoder() {
    assertThat(ValueTranscoderFactory.getUnicodePwdValueTranscoder()).isNotNull();
  }
}