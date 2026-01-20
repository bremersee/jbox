package org.bremersee.spring.boot.autoconfigure.data.commons;

import static org.assertj.core.api.Assertions.assertThat;

import org.bremersee.comparator.spring.converter.SortOrderConverter;
import org.bremersee.comparator.spring.converter.SortOrderItemConverter;
import org.bremersee.comparator.spring.mapper.SortMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * The sort order autoconfiguration test.
 *
 * @author Christian Bremer
 */
class SortOrderAutoConfigurationTest {

  private static SortOrderAutoConfiguration target;

  /**
   * Init.
   */
  @BeforeAll
  static void init() {
    target = new SortOrderAutoConfiguration(new SortOrderConverterProperties());
    target.init();
  }

  /**
   * Sort order converter.
   */
  @Test
  void sortOrderConverter() {
    SortOrderConverter actual = target.sortOrderConverter();
    assertThat(actual).isNotNull();
  }

  /**
   * Sort order item converter.
   */
  @Test
  void sortOrderItemConverter() {
    SortOrderItemConverter actual = target.sortOrderItemConverter();
    assertThat(actual).isNotNull();
  }

  /**
   * Default sort mapper.
   */
  @Test
  void defaultSortMapper() {
    SortMapper actual = target.defaultSortMapper(new SortOrderConverter());
    assertThat(actual).isNotNull();
  }
}