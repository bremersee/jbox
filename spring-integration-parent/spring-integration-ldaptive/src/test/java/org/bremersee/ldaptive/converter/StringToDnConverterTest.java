package org.bremersee.ldaptive.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.ldaptive.dn.Dn;

/**
 * The string to distinguished name converter test.
 */
class StringToDnConverterTest {

  /**
   * Convert.
   */
  @Test
  void convert() {
    String source = "CN=Foo,OU=Bar,dc=example,dc=com";
    Dn expected = new Dn(source);
    StringToDnConverter converter = new StringToDnConverter();
    Dn actual = converter.convert(source);
    assertThat(actual).isEqualTo(expected);
  }
}