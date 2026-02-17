package org.bremersee.ldaptive.converter;

import org.bremersee.ldaptive.transcoder.ValueTranscoderFactory;
import org.ldaptive.dn.Dn;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

/**
 * The string to distinguished name converter.
 */
public class StringToDnConverter implements Converter<String, Dn> {

  /**
   * Instantiates a new string to distinguished name converter.
   */
  public StringToDnConverter() {
    super();
  }

  @Override
  public Dn convert(@NonNull String source) {
    return ValueTranscoderFactory.getDnValueTranscoder().decodeStringValue(source);
  }
}
