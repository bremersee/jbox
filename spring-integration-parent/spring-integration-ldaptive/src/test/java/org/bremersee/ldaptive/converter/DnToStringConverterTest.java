package org.bremersee.ldaptive.converter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.ldaptive.dn.Dn;

/**
 * The distinguished name to string converter test.
 */
class DnToStringConverterTest {

  /**
   * Convert.
   */
  @Test
  void convert() {
    String expected = "CN=Foo,OU=Bar,dc=example,dc=com";
    Dn dn = new Dn(expected);
    DnToStringConverter converter = new DnToStringConverter();
    String actual = converter.convert(dn);
    assertThat(actual).isEqualTo(expected);
  }
}