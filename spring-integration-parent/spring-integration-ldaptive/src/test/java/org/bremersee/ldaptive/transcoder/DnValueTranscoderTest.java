package org.bremersee.ldaptive.transcoder;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.ldaptive.dn.DefaultAttributeValueEscaper;
import org.ldaptive.dn.DefaultRDnNormalizer;
import org.ldaptive.dn.Dn;

/**
 * The distinguished name value transcoder test.
 */
class DnValueTranscoderTest {

  /**
   * Decode string value.
   */
  @Test
  void decodeStringValue() {
    String expected = "cn=foo,ou=bar,dc=example,dc=com";
    DnValueTranscoder transcoder = new DnValueTranscoder();
    Dn actual = transcoder.decodeStringValue(expected);
    assertThat(actual.format()).isEqualTo(expected);
  }

  /**
   * Decode empty string value.
   */
  @Test
  void decodeEmptyStringValue() {
    DnValueTranscoder transcoder = new DnValueTranscoder();
    Dn actual = transcoder.decodeStringValue("");
    assertThat(actual).isNull();
  }

  /**
   * Decode null value.
   */
  @Test
  void decodeNullValue() {
    DnValueTranscoder transcoder = new DnValueTranscoder();
    Dn actual = transcoder.decodeStringValue(null);
    assertThat(actual).isNull();
  }

  /**
   * Encode string value.
   */
  @Test
  void encodeStringValue() {
    String expected = "cn=foo,ou=bar,dc=example,dc=com";
    Dn dn = new Dn("CN=Foo,OU=Bar,dc=example,dc=com");
    DnValueTranscoder transcoder = new DnValueTranscoder();
    String actual = transcoder.encodeStringValue(dn);
    assertThat(actual).isEqualTo(expected);
  }

  /**
   * Encode string value case sensitive.
   */
  @Test
  void encodeStringValueCaseSensitive() {
    String expected = "CN=Foo,OU=Bar,dc=example,dc=com";
    Dn dn = new Dn(expected);
    DnValueTranscoder transcoder = new DnValueTranscoder(
        new DefaultRDnNormalizer(
            new DefaultAttributeValueEscaper(),
            name -> name,
            value -> value));
    String actual = transcoder.encodeStringValue(dn);
    assertThat(actual).isEqualTo(expected);
  }

  /**
   * Encode string value with empty dn.
   */
  @Test
  void encodeStringValueWithEmptyDn() {
    Dn dn = new Dn();
    DnValueTranscoder transcoder = new DnValueTranscoder();
    String actual = transcoder.encodeStringValue(dn);
    assertThat(actual).isNull();
  }

  /**
   * Encode string value with null.
   */
  @Test
  void encodeStringValueWithNull() {
    DnValueTranscoder transcoder = new DnValueTranscoder();
    String actual = transcoder.encodeStringValue(null);
    assertThat(actual).isNull();
  }

  /**
   * Gets type.
   */
  @Test
  void getType() {
    assertThat(new DnValueTranscoder().getType()).isEqualTo(Dn.class);
  }
}