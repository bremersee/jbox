package org.bremersee.ldaptive;

import static org.springframework.util.ObjectUtils.isEmpty;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.stream.Stream;
import lombok.Getter;
import org.bremersee.ldaptive.transcoder.ValueTranscoderFactory;
import org.ldaptive.AttributeModification;
import org.ldaptive.AttributeModification.Type;
import org.ldaptive.LdapAttribute;
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
   * Determines whether the attribute exists in the given ldap entry or not.
   *
   * @param entry the entry
   * @return {@code true} if the attribute exists, otherwise {@code false}
   */
  default boolean exists(LdapEntry entry) {
    return !isEmpty(entry) && !isEmpty(entry.getAttribute(getName()));
  }

  /**
   * Gets value.
   *
   * @param entry the entry
   * @return the value
   */
  default Optional<T> getValue(LdapEntry entry) {
    return getValue(entry, null);
  }

  /**
   * Gets value.
   *
   * @param entry the entry
   * @param defaultValue the default value
   * @return the value
   */
  default Optional<T> getValue(LdapEntry entry, T defaultValue) {
    return Optional.ofNullable(entry)
        .map(e -> e.getAttribute(getName()))
        .map(attr -> attr.getValue(getValueTranscoder().decoder()))
        .or(() -> Optional.ofNullable(defaultValue));
  }

  /**
   * Gets values.
   *
   * @param entry the entry
   * @return the values
   */
  default Stream<T> getValues(LdapEntry entry) {
    return Stream.ofNullable(entry)
        .map(e -> e.getAttribute(getName()))
        .filter(Objects::nonNull)
        .map(attr -> attr.getValues(getValueTranscoder().decoder()))
        .filter(Objects::nonNull)
        .flatMap(Collection::stream);
  }

  /**
   * Sets value.
   *
   * @param entry the entry
   * @param value the value
   * @return the value
   */
  default Optional<AttributeModification> setValue(LdapEntry entry, T value) {
    return setValues(entry, isEmpty(value) ? List.of() : List.of(value));
  }

  /**
   * Sets value.
   *
   * @param entry the entry
   * @param value the value
   * @param condition the condition
   * @return the value
   */
  default Optional<AttributeModification> setValue(
      LdapEntry entry,
      T value,
      BiPredicate<T, T> condition) {
    if (isEmpty(condition) || condition.test(getValue(entry).orElse(null), value)) {
      return setValues(entry, isEmpty(value) ? List.of() : List.of(value));
    }
    return Optional.empty();
  }

  /**
   * Sets values.
   *
   * @param entry the entry
   * @param values the values
   * @return the values
   */
  default Optional<AttributeModification> setValues(LdapEntry entry, Collection<T> values) {
    if (isEmpty(entry)) {
      return Optional.empty();
    }
    if (isEmpty(values)) {
      return remove(entry);
    }
    List<T> newValues = values.stream().filter(v -> !isEmpty(v)).toList();
    if (isEmpty(entry.getAttribute(getName()))) {
      return addValues(entry, newValues);
    }
    List<byte[]> newByteList = newValues.stream()
        .map(v -> getValueTranscoder().encodeBinaryValue(v))
        .toList();
    Collection<byte[]> existingBytes = entry.getAttribute(getName()).getBinaryValues();
    if (equals(existingBytes, newByteList)) {
      return Optional.empty();
    }
    LdapAttribute attribute = createAttribute(newValues);
    entry.addAttributes(attribute);
    return Optional.of(new AttributeModification(Type.REPLACE, attribute));
  }

  private boolean equals(Collection<byte[]> c1, Collection<byte[]> c2) {
    if (c1.size() != c2.size()) {
      return false;
    }
    Iterator<byte[]> iter1 = c1.iterator();
    Iterator<byte[]> iter2 = c2.iterator();
    while (iter1.hasNext() && iter2.hasNext()) {
      if (!Arrays.equals(iter1.next(), iter2.next())) {
        return false;
      }
    }
    return true;
  }

  private Optional<AttributeModification> addValues(LdapEntry entry, Collection<T> values) {
    LdapAttribute attribute = createAttribute(values);
    entry.addAttributes(attribute);
    return Optional.of(new AttributeModification(Type.ADD, attribute));
  }

  private Optional<AttributeModification> remove(LdapEntry entry) {
    if (isEmpty(entry.getAttribute(getName()))) {
      return Optional.empty();
    }
    entry.removeAttribute(getName());
    return Optional.of(new AttributeModification(Type.DELETE, createAttribute()));
  }


  /**
   * Create attribute ldap attribute.
   *
   * @return the ldap attribute
   */
  default LdapAttribute createAttribute() {
    LdapAttribute attribute = new LdapAttribute(getName());
    attribute.setBinary(isBinary());
    return attribute;
  }

  /**
   * Create attribute ldap attribute.
   *
   * @param value the value
   * @return the ldap attribute
   */
  default LdapAttribute createAttribute(T value) {
    if (isEmpty(value)) {
      return createAttribute();
    }
    return createAttribute(List.of(value));
  }

  /**
   * Create attribute ldap attribute.
   *
   * @param values the values
   * @return the ldap attribute
   */
  default LdapAttribute createAttribute(Collection<T> values) {
    if (isEmpty(values)) {
      return createAttribute();
    }
    LdapAttribute attribute = new LdapAttribute(getName());
    attribute.setBinary(isBinary());
    if (!isEmpty(values)) {
      attribute.addValues(getValueTranscoder().encoder(), values);
    }
    return attribute;
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

}
