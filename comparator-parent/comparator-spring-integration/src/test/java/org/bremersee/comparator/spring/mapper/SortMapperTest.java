/*
 * Copyright 2019-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.bremersee.comparator.spring.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.BOOLEAN;
import static org.assertj.core.api.InstanceOfAssertFactories.list;

import java.util.List;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.bremersee.comparator.model.SortOrder;
import org.bremersee.comparator.model.SortOrderItem;
import org.bremersee.comparator.model.SortOrderItem.CaseHandling;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.NullHandling;
import org.springframework.data.domain.Sort.Order;

/**
 * The sort mapper tests.
 *
 * @author Christian Bremer
 */
@ExtendWith(SoftAssertionsExtension.class)
class SortMapperTest {

  private static final SortMapper target = SortMapper.defaultSortMapper();

  /**
   * Gets sort order with null and expect unsorted.
   */
  @Test
  void getSortOrderWithNullAndExpectUnsorted() {
    SortOrder actual = target.getSortOrder(null);
    assertThat(actual)
        .extracting(SortOrder::isUnsorted, InstanceOfAssertFactories.BOOLEAN)
        .isTrue();
  }

  /**
   * Gets sort order with no text.
   */
  @Test
  void getSortOrderWithNoText() {
    SortOrder actual = target.getSortOrder("");
    SortOrderItem expectedItem = new SortOrderItem(
        null,
        SortOrderItem.Direction.ASC,
        CaseHandling.INSENSITIVE,
        SortOrderItem.NullHandling.NATIVE);
    assertThat(actual)
        .extracting(SortOrder::getItems, list(SortOrderItem.class))
        .containsExactly(expectedItem);
  }

  /**
   * Gets sort order text with null.
   */
  @Test
  void getSortOrderTextWithNull() {
    String actual = target.getSortOrderText(null);
    assertThat(actual)
        .isNull();
  }

  /**
   * Gets sort order text with empty.
   */
  @Test
  void getSortOrderTextWithEmpty() {
    String actual = target.getSortOrderText(new SortOrder(List.of()));
    assertThat(actual)
        .isEmpty();
  }

  /**
   * Gets sort order text.
   */
  @Test
  void getSortOrderText() {
    String actual = target.getSortOrderText(new SortOrder(List.of(SortOrderItem.by("f0"))));
    assertThat(actual)
        .isEqualTo("f0");
  }

  /**
   * Gets sort order item text with null.
   */
  @Test
  void getSortOrderItemTextWithNull() {
    List<String> actual = target.getSortOrderItemText(null);
    assertThat(actual)
        .isEmpty();
  }

  /**
   * Gets sort order item text with unsorted.
   */
  @Test
  void getSortOrderItemTextWithUnsorted() {
    List<String> actual = target.getSortOrderItemText(SortOrder.unsorted());
    assertThat(actual)
        .isEmpty();
  }

  /**
   * Gets sort order item text.
   */
  @Test
  void getSortOrderItemText() {
    List<String> actual = target.getSortOrderItemText(SortOrder.by(SortOrderItem.by("f0")));
    assertThat(actual)
        .contains("f0");
  }

  /**
   * Test to sort.
   *
   * @param softly the soft assertions
   */
  @Test
  void toSort(SoftAssertions softly) {
    SortOrderItem sortOrderItem0 = new SortOrderItem(
        "f0", SortOrderItem.Direction.ASC, CaseHandling.INSENSITIVE,
        SortOrderItem.NullHandling.NULLS_FIRST);
    SortOrderItem sortOrderItem1 = new SortOrderItem(
        "f1", SortOrderItem.Direction.DESC, CaseHandling.SENSITIVE,
        SortOrderItem.NullHandling.NULLS_LAST);
    SortOrderItem sortOrderItem2 = new SortOrderItem(
        "f2", SortOrderItem.Direction.DESC, CaseHandling.SENSITIVE,
        SortOrderItem.NullHandling.NATIVE);

    SortOrder sortOrderSource = SortOrder.by(sortOrderItem0, sortOrderItem1, sortOrderItem2);

    Sort sort = target.toSort(sortOrderSource);

    softly.assertThat(sort)
        .isNotNull();

    Sort.Order sortOrder = sort.getOrderFor("f0");
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Sort.Order::isAscending, BOOLEAN)
        .isTrue();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Sort.Order::isIgnoreCase, BOOLEAN)
        .isTrue();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Sort.Order::getNullHandling)
        .isEqualTo(NullHandling.NULLS_FIRST);

    sortOrder = sort.getOrderFor("f1");
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Sort.Order::isAscending, BOOLEAN)
        .isFalse();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Sort.Order::isIgnoreCase, BOOLEAN)
        .isFalse();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Sort.Order::getNullHandling)
        .isEqualTo(NullHandling.NULLS_LAST);

    sortOrder = sort.getOrderFor("f2");
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Sort.Order::isAscending, BOOLEAN)
        .isFalse();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Sort.Order::isIgnoreCase, BOOLEAN)
        .isFalse();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Sort.Order::getNullHandling)
        .isEqualTo(NullHandling.NATIVE);
  }

  /**
   * To sort with empty sort order.
   */
  @Test
  void toSortWithEmptySortOrder() {
    Sort sort = target.toSort(SortOrder.unsorted());
    assertThat(sort.isUnsorted()).isTrue();
  }

  /**
   * To sort with null.
   */
  @Test
  void toSortWithNull() {
    Sort sort = target.toSort(null);
    assertThat(sort.isUnsorted()).isTrue();
  }

  /**
   * To sort order with null.
   *
   * @param softly the softly
   */
  @Test
  void toSortOrderWithNull(SoftAssertions softly) {
    softly.assertThat(target.toSortOrder(null)).isNull();
    softly.assertThat(target.toSortOrder(SortOrderItem.by(null))).isNull();
    softly.assertThat(target.toSortOrder(SortOrderItem.by(""))).isNull();
  }

  /**
   * From sort.
   */
  @Test
  void fromSort() {
    SortOrderItem sortOrderItem0 = new SortOrderItem(
        "f0", SortOrderItem.Direction.ASC, CaseHandling.INSENSITIVE,
        SortOrderItem.NullHandling.NULLS_FIRST);
    SortOrderItem sortOrderItem1 = new SortOrderItem(
        "f1", SortOrderItem.Direction.DESC, CaseHandling.SENSITIVE,
        SortOrderItem.NullHandling.NULLS_LAST);
    SortOrderItem sortOrderItem2 = new SortOrderItem(
        "f2", SortOrderItem.Direction.DESC, CaseHandling.SENSITIVE,
        SortOrderItem.NullHandling.NATIVE);
    SortOrder expected = new SortOrder(List.of(sortOrderItem0, sortOrderItem1, sortOrderItem2));

    Sort sort = Sort.by(
        Order.by("f0").with(Direction.ASC).with(NullHandling.NULLS_FIRST).ignoreCase(),
        Order.by("f1").with(Direction.DESC).with(NullHandling.NULLS_LAST),
        Order.by("f2").with(Direction.DESC).with(NullHandling.NATIVE));

    SortOrder actual = target.fromSort(sort);

    assertThat(actual).isEqualTo(expected);
  }

  /**
   * From sort order with null.
   */
  @Test
  void fromSortOrderWithNull() {
    assertThat(target.fromSortOrder(null)).isNull();
  }

  /**
   * Apply defaults for pageable.
   */
  @Test
  void applyDefaultsForPageable() {
    Pageable pageable = PageRequest.of(0, 10)
        .withSort(Sort.by("f0", "f1"));
    Pageable actual = target
        .applyDefaults(pageable, Direction.DESC, true, NullHandling.NULLS_LAST, "f0");
    assertThat(actual)
        .extracting(Pageable::getSort)
        .isEqualTo(Sort.by(
            Order.by("f0")
                .with(Direction.DESC)
                .with(NullHandling.NULLS_LAST)
                .ignoreCase(),
            Order.by("f1")));
  }

  /**
   * Apply defaults for pageable of null.
   */
  @Test
  void applyDefaultsForPageableOfNull() {
    Pageable actual = target
        .applyDefaults((Pageable) null, Direction.DESC, true, NullHandling.NULLS_LAST);
    assertThat(actual).isNull();
  }

  /**
   * Apply defaults for sort.
   */
  @Test
  void applyDefaultsForSort() {
    Sort sort = Sort.by("f0");
    Sort actual = target
        .applyDefaults(sort, Direction.DESC, true, NullHandling.NULLS_LAST);
    assertThat(actual)
        .isEqualTo(Sort.by(Order.by("f0")
            .with(Direction.DESC)
            .with(NullHandling.NULLS_LAST)
            .ignoreCase()));
  }

  /**
   * Apply defaults for sort of null.
   */
  @Test
  void applyDefaultsForSortOfNull() {
    Sort actual = target
        .applyDefaults((Sort) null, Direction.DESC, true, NullHandling.NULLS_LAST);
    assertThat(actual)
        .isEqualTo(Sort.unsorted());
  }

  /**
   * Apply defaults for sort with no changes.
   */
  @Test
  void applyDefaultsForSortWithNoChanges() {
    Sort sort = Sort.by("f0");
    Sort actual = target
        .applyDefaults(sort, null, null, null);
    assertThat(actual)
        .isEqualTo(Sort.by(Order.by("f0")
            .with(Direction.ASC)
            .with(NullHandling.NATIVE)));
  }

}