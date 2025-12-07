package org.bremersee.ldaptive;

import lombok.Getter;
import org.bremersee.ldaptive.transcoder.ValueTranscoderFactory;
import org.ldaptive.transcode.ValueTranscoder;

/**
 * The ldaptive attribute.
 *
 * @param <T> the type parameter
 */
public interface LdaptiveAttribute<T> {

  /**
   * Gets name.
   *
   * @return the name
   */
  String getName();

  /**
   * Is binary.
   *
   * @return the boolean
   */
  boolean isBinary();

  /**
   * Gets value transcoder.
   *
   * @return the value transcoder
   */
  ValueTranscoder<T> getValueTranscoder();

  /**
   * Define ldaptive attribute.
   *
   * @param name the name
   * @return the ldaptive attribute
   */
  static LdaptiveAttribute<String> define(String name) {
    return define(name, false, ValueTranscoderFactory.getStringValueTranscoder());
  }

  /**
   * Define ldaptive attribute.
   *
   * @param <T> the type parameter
   * @param name the name
   * @param binary the binary
   * @param valueTranscoder the value transcoder
   * @return the ldaptive attribute
   */
  static <T> LdaptiveAttribute<T> define(
      String name,
      boolean binary,
      ValueTranscoder<T> valueTranscoder) {
    return new Specification<>(name, binary, valueTranscoder);
  }

  /**
   * The ldaptive attribute specification.
   *
   * @param <T> the type parameter
   */
  @SuppressWarnings("ClassCanBeRecord")
  class Specification<T> implements LdaptiveAttribute<T> {

    @Getter
    private final String name;

    @Getter
    private final boolean binary;

    @Getter
    private final ValueTranscoder<T> valueTranscoder;

    /**
     * Instantiates a new specification.
     *
     * @param name the name
     * @param binary the binary
     * @param valueTranscoder the value transcoder
     */
    public Specification(String name, boolean binary, ValueTranscoder<T> valueTranscoder) {
      this.name = name;
      this.binary = binary;
      this.valueTranscoder = valueTranscoder;
    }
  }

}
