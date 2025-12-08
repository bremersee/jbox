package org.bremersee.ldaptive;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import lombok.Getter;
import org.bremersee.ldaptive.transcoder.ValueTranscoderFactory;
import org.ldaptive.LdapEntry;
import org.ldaptive.transcode.ValueTranscoder;
import org.springframework.util.Assert;

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
   * Gets value.
   *
   * @param entry the entry
   * @return the value
   */
  default Value<T> getValue(LdapEntry entry) {
    return getValue(entry, null);
  }

  /**
   * Gets value.
   *
   * @param entry the entry
   * @param defaultValue the default value
   * @return the value
   */
  default Value<T> getValue(LdapEntry entry, T defaultValue) {
    T value = Optional.ofNullable(entry)
        .map(e -> e.getAttribute(getName()))
        .map(attr -> attr.getValue(getValueTranscoder().decoder()))
        .orElse(defaultValue);
    return new ValueWrapper<>(value);
  }

  /**
   * Gets values.
   *
   * @param entry the entry
   * @return the values
   */
  default Values<T> getValues(LdapEntry entry) {
    Collection<T> values = Optional.ofNullable(entry)
        .map(e -> e.getAttribute(getName()))
        .map(attr -> attr.getValues(getValueTranscoder().decoder()))
        .orElseGet(Collections::emptyList);
    return new ValuesWrapper<>(values);
  }

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
      Assert.hasText(name, "Name must not be null or empty.");
      Assert.notNull(valueTranscoder, "ValueTranscoder must not be null.");
      this.name = name;
      this.binary = binary;
      this.valueTranscoder = valueTranscoder;
    }
  }

  /**
   * The interface Value.
   *
   * @param <T> the type parameter
   */
  interface Value<T> extends Supplier<T> {

    /**
     * Consume.
     *
     * @param consumer the consumer
     */
    void consume(Consumer<? super T> consumer);

    /**
     * Consume non null.
     *
     * @param consumer the consumer
     */
    default void consumeNonNull(Consumer<? super T> consumer) {
      Optional.ofNullable(get())
          .ifPresent(consumer);
    }
  }

  /**
   * The type Value wrapper.
   *
   * @param <T> the type parameter
   */
  @SuppressWarnings("ClassCanBeRecord")
  class ValueWrapper<T> implements Value<T> {

    /**
     * The Attribute value.
     */
    final T attributeValue;

    /**
     * Instantiates a new Value wrapper.
     *
     * @param attributeValue the attribute value
     */
    ValueWrapper(T attributeValue) {
      this.attributeValue = attributeValue;
    }

    @Override
    public T get() {
      return attributeValue;
    }

    @Override
    public void consume(Consumer<? super T> consumer) {
      consumer.accept(attributeValue);
    }
  }

  /**
   * The interface Values.
   *
   * @param <T> the type parameter
   */
  interface Values<T> extends Supplier<List<T>> {

    /**
     * Consume.
     *
     * @param consumer the consumer
     */
    void consume(Consumer<? super List<T>> consumer);

    /**
     * Consume non empty.
     *
     * @param consumer the consumer
     */
    default void consumeNonEmpty(Consumer<? super List<T>> consumer) {
      if (!get().isEmpty()) {
        consumer.accept(get());
      }
    }
  }

  /**
   * The type Values wrapper.
   *
   * @param <T> the type parameter
   */
  @SuppressWarnings("ClassCanBeRecord")
  class ValuesWrapper<T> implements Values<T> {

    /**
     * The Attribute values.
     */
    final List<T> attributeValues;

    /**
     * Instantiates a new Values wrapper.
     *
     * @param attributeValues the attribute values
     */
    ValuesWrapper(Collection<T> attributeValues) {
      this.attributeValues = List.copyOf(attributeValues);
    }

    @Override
    public List<T> get() {
      return attributeValues;
    }

    @Override
    public void consume(Consumer<? super List<T>> consumer) {
      consumer.accept(attributeValues);
    }
  }

}
