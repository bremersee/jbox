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

import static org.assertj.core.api.InstanceOfAssertFactories.BOOLEAN;

import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.bremersee.comparator.model.SortOrder;
import org.bremersee.comparator.model.SortOrderItem;
import org.bremersee.comparator.model.SortOrderItem.CaseHandling;
import org.bremersee.comparator.spring.converter.SortOrderConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.NullHandling;
import org.springframework.data.domain.Sort.Order;

/**
 * The sort mapper tests.
 *
 * @author Christian Bremer
 */
@ExtendWith(SoftAssertionsExtension.class)
class SortMapperWithSortOrderConverterTest {

  private static final SortOrderConverter CONVERTER = new SortOrderConverter();

  private static final SortMapper TARGET = SortMapper
      .defaultSortMapper(CONVERTER);

  /**
   * To sort with unused default.
   *
   * @param softly the soft assertions
   */
  @Test
  void toSortWithUnusedDefault(SoftAssertions softly) {
    SortOrderItem sortOrderItem0 = new SortOrderItem(
        "f0", SortOrderItem.Direction.ASC, CaseHandling.INSENSITIVE,
        SortOrderItem.NullHandling.NULLS_FIRST);
    SortOrderItem sortOrderItem1 = new SortOrderItem(
        "f1", SortOrderItem.Direction.DESC, CaseHandling.SENSITIVE,
        SortOrderItem.NullHandling.NULLS_LAST);
    SortOrderItem sortOrderItem2 = new SortOrderItem(
        "f2", SortOrderItem.Direction.DESC, CaseHandling.SENSITIVE,
        SortOrderItem.NullHandling.NATIVE);

    SortOrder sortOrder0 = new SortOrder(List.of(sortOrderItem0, sortOrderItem1));
    SortOrder sortOrder1 = new SortOrder(List.of(sortOrderItem2));

    Sort sort = TARGET.toSort(
        SortOrder.by(List.of(sortOrder0, sortOrder1)), sortOrder0);

    softly.assertThat(sort)
        .isNotNull();

    Order sortOrder = sort.getOrderFor("f0");
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::isAscending, BOOLEAN)
        .isTrue();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::isIgnoreCase, BOOLEAN)
        .isTrue();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::getNullHandling)
        .isEqualTo(NullHandling.NULLS_FIRST);

    sortOrder = sort.getOrderFor("f1");
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::isAscending, BOOLEAN)
        .isFalse();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::isIgnoreCase, BOOLEAN)
        .isFalse();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::getNullHandling)
        .isEqualTo(NullHandling.NULLS_LAST);

    sortOrder = sort.getOrderFor("f2");
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::isAscending, BOOLEAN)
        .isFalse();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::isIgnoreCase, BOOLEAN)
        .isFalse();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::getNullHandling)
        .isEqualTo(NullHandling.NATIVE);
  }

  /**
   * To sort with default.
   *
   * @param softly the soft assertions
   */
  @Test
  void toSortWithDefault(SoftAssertions softly) {
    SortOrderItem sortOrderItem0 = new SortOrderItem(
        "f0", SortOrderItem.Direction.ASC, CaseHandling.INSENSITIVE,
        SortOrderItem.NullHandling.NULLS_FIRST);
    SortOrderItem sortOrderItem1 = new SortOrderItem(
        "f1", SortOrderItem.Direction.DESC, CaseHandling.SENSITIVE,
        SortOrderItem.NullHandling.NULLS_LAST);
    SortOrderItem sortOrderItem2 = new SortOrderItem(
        "f2", SortOrderItem.Direction.DESC, CaseHandling.SENSITIVE,
        SortOrderItem.NullHandling.NATIVE);

    SortOrder sortOrder0 = new SortOrder(List.of(sortOrderItem0, sortOrderItem1));
    SortOrder sortOrder1 = new SortOrder(List.of(sortOrderItem2));

    Sort sort = TARGET.toSort(
        SortOrder.by(), SortOrder.by(List.of(sortOrder0, sortOrder1)));

    softly.assertThat(sort)
        .isNotNull();

    Order sortOrder = sort.getOrderFor("f0");
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::isAscending, BOOLEAN)
        .isTrue();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::isIgnoreCase, BOOLEAN)
        .isTrue();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::getNullHandling)
        .isEqualTo(NullHandling.NULLS_FIRST);

    sortOrder = sort.getOrderFor("f1");
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::isAscending, BOOLEAN)
        .isFalse();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::isIgnoreCase, BOOLEAN)
        .isFalse();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::getNullHandling)
        .isEqualTo(NullHandling.NULLS_LAST);

    sortOrder = sort.getOrderFor("f2");
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::isAscending, BOOLEAN)
        .isFalse();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::isIgnoreCase, BOOLEAN)
        .isFalse();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::getNullHandling)
        .isEqualTo(NullHandling.NATIVE);
  }

  /**
   * To sort with text default.
   *
   * @param softly the soft assertions
   */
  @Test
  void toSortWithTextDefault(SoftAssertions softly) {
    SortOrderItem sortOrderItem0 = new SortOrderItem(
        "f0", SortOrderItem.Direction.ASC, CaseHandling.INSENSITIVE,
        SortOrderItem.NullHandling.NULLS_FIRST);
    SortOrderItem sortOrderItem1 = new SortOrderItem(
        "f1", SortOrderItem.Direction.DESC, CaseHandling.SENSITIVE,
        SortOrderItem.NullHandling.NULLS_LAST);
    SortOrderItem sortOrderItem2 = new SortOrderItem(
        "f2", SortOrderItem.Direction.DESC, CaseHandling.SENSITIVE,
        SortOrderItem.NullHandling.NATIVE);

    SortOrder sortOrder0 = new SortOrder(List.of(sortOrderItem0, sortOrderItem1));
    SortOrder sortOrder1 = new SortOrder(List.of(sortOrderItem2));

    Sort sort = TARGET.toSort(
        SortOrder.by(),
        SortOrder.by(List.of(sortOrder0, sortOrder1)).getSortOrderText(CONVERTER.getSeparators()));

    softly.assertThat(sort)
        .isNotNull();

    Order sortOrder = sort.getOrderFor("f0");
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::isAscending, BOOLEAN)
        .isTrue();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::isIgnoreCase, BOOLEAN)
        .isTrue();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::getNullHandling)
        .isEqualTo(NullHandling.NULLS_FIRST);

    sortOrder = sort.getOrderFor("f1");
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::isAscending, BOOLEAN)
        .isFalse();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::isIgnoreCase, BOOLEAN)
        .isFalse();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::getNullHandling)
        .isEqualTo(NullHandling.NULLS_LAST);

    sortOrder = sort.getOrderFor("f2");
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::isAscending, BOOLEAN)
        .isFalse();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::isIgnoreCase, BOOLEAN)
        .isFalse();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::getNullHandling)
        .isEqualTo(NullHandling.NATIVE);
  }

  /**
   * To sort with list and unused default.
   */
  @Test
  void toSortWithListAndUnusedDefault(SoftAssertions softly) {
    SortOrderItem sortOrderItem0 = new SortOrderItem(
        "f0", SortOrderItem.Direction.ASC, CaseHandling.INSENSITIVE,
        SortOrderItem.NullHandling.NULLS_FIRST);
    SortOrderItem sortOrderItem1 = new SortOrderItem(
        "f1", SortOrderItem.Direction.DESC, CaseHandling.SENSITIVE,
        SortOrderItem.NullHandling.NULLS_LAST);
    SortOrderItem sortOrderItem2 = new SortOrderItem(
        "f2", SortOrderItem.Direction.DESC, CaseHandling.SENSITIVE,
        SortOrderItem.NullHandling.NATIVE);

    SortOrder sortOrder0 = new SortOrder(List.of(sortOrderItem0, sortOrderItem1));
    SortOrder sortOrder1 = new SortOrder(List.of(sortOrderItem2));

    Sort sort = TARGET.toSort(
        List.of(sortOrder0, sortOrder1), sortOrder1);

    softly.assertThat(sort)
        .isNotNull();

    Order sortOrder = sort.getOrderFor("f0");
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::isAscending, BOOLEAN)
        .isTrue();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::isIgnoreCase, BOOLEAN)
        .isTrue();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::getNullHandling)
        .isEqualTo(NullHandling.NULLS_FIRST);

    sortOrder = sort.getOrderFor("f1");
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::isAscending, BOOLEAN)
        .isFalse();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::isIgnoreCase, BOOLEAN)
        .isFalse();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::getNullHandling)
        .isEqualTo(NullHandling.NULLS_LAST);

    sortOrder = sort.getOrderFor("f2");
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::isAscending, BOOLEAN)
        .isFalse();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::isIgnoreCase, BOOLEAN)
        .isFalse();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::getNullHandling)
        .isEqualTo(NullHandling.NATIVE);
  }

  /**
   * To sort with empty list and default.
   */
  @Test
  void toSortWithEmptyListAndDefault(SoftAssertions softly) {
    SortOrderItem sortOrderItem0 = new SortOrderItem(
        "f0", SortOrderItem.Direction.ASC, CaseHandling.INSENSITIVE,
        SortOrderItem.NullHandling.NULLS_FIRST);
    SortOrderItem sortOrderItem1 = new SortOrderItem(
        "f1", SortOrderItem.Direction.DESC, CaseHandling.SENSITIVE,
        SortOrderItem.NullHandling.NULLS_LAST);
    SortOrderItem sortOrderItem2 = new SortOrderItem(
        "f2", SortOrderItem.Direction.DESC, CaseHandling.SENSITIVE,
        SortOrderItem.NullHandling.NATIVE);

    SortOrder sortOrder0 = new SortOrder(List.of(sortOrderItem0, sortOrderItem1));
    SortOrder sortOrder1 = new SortOrder(List.of(sortOrderItem2));

    Sort sort = TARGET.toSort(
        List.of(), SortOrder.by(List.of(sortOrder0, sortOrder1)));

    softly.assertThat(sort)
        .isNotNull();

    Order sortOrder = sort.getOrderFor("f0");
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::isAscending, BOOLEAN)
        .isTrue();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::isIgnoreCase, BOOLEAN)
        .isTrue();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::getNullHandling)
        .isEqualTo(NullHandling.NULLS_FIRST);

    sortOrder = sort.getOrderFor("f1");
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::isAscending, BOOLEAN)
        .isFalse();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::isIgnoreCase, BOOLEAN)
        .isFalse();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::getNullHandling)
        .isEqualTo(NullHandling.NULLS_LAST);

    sortOrder = sort.getOrderFor("f2");
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::isAscending, BOOLEAN)
        .isFalse();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::isIgnoreCase, BOOLEAN)
        .isFalse();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::getNullHandling)
        .isEqualTo(NullHandling.NATIVE);
  }

  /**
   * To sort with empty list and text default.
   */
  @Test
  void toSortWithEmptyListAndTextDefault(SoftAssertions softly) {
    SortOrderItem sortOrderItem0 = new SortOrderItem(
        "f0", SortOrderItem.Direction.ASC, CaseHandling.INSENSITIVE,
        SortOrderItem.NullHandling.NULLS_FIRST);
    SortOrderItem sortOrderItem1 = new SortOrderItem(
        "f1", SortOrderItem.Direction.DESC, CaseHandling.SENSITIVE,
        SortOrderItem.NullHandling.NULLS_LAST);
    SortOrderItem sortOrderItem2 = new SortOrderItem(
        "f2", SortOrderItem.Direction.DESC, CaseHandling.SENSITIVE,
        SortOrderItem.NullHandling.NATIVE);

    SortOrder sortOrder0 = new SortOrder(List.of(sortOrderItem0, sortOrderItem1));
    SortOrder sortOrder1 = new SortOrder(List.of(sortOrderItem2));

    Sort sort = TARGET.toSort(
        List.of(),
        SortOrder.by(List.of(sortOrder0, sortOrder1)).getSortOrderText(CONVERTER.getSeparators()));

    softly.assertThat(sort)
        .isNotNull();

    Order sortOrder = sort.getOrderFor("f0");
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::isAscending, BOOLEAN)
        .isTrue();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::isIgnoreCase, BOOLEAN)
        .isTrue();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::getNullHandling)
        .isEqualTo(NullHandling.NULLS_FIRST);

    sortOrder = sort.getOrderFor("f1");
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::isAscending, BOOLEAN)
        .isFalse();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::isIgnoreCase, BOOLEAN)
        .isFalse();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::getNullHandling)
        .isEqualTo(NullHandling.NULLS_LAST);

    sortOrder = sort.getOrderFor("f2");
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::isAscending, BOOLEAN)
        .isFalse();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::isIgnoreCase, BOOLEAN)
        .isFalse();
    softly.assertThat(sortOrder)
        .isNotNull()
        .extracting(Order::getNullHandling)
        .isEqualTo(NullHandling.NATIVE);
  }

}