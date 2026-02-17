package org.bremersee.ldaptive.converter;

import org.bremersee.ldaptive.transcoder.ValueTranscoderFactory;
import org.ldaptive.dn.Dn;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

/**
 * The distinguished name to string converter.
 */
public class DnToStringConverter implements Converter<Dn, String> {

  /**
   * Instantiates a new distinguished name to string converter.
   */
  public DnToStringConverter() {
    super();
  }

  @Override
  public String convert(@NonNull Dn source) {
    return ValueTranscoderFactory.getDnValueTranscoderCaseSensitive().encodeStringValue(source);
  }
}
