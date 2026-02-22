/*
 * Copyright 2025 the original author or authors.
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

package org.bremersee.pagebuilder.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.bremersee.comparator.model.SortOrder;
import org.bremersee.comparator.model.SortOrderItem;
import org.bremersee.comparator.model.SortOrderItem.CaseHandling;
import org.bremersee.pagebuilder.testmodel.Address;
import org.bremersee.pagebuilder.testmodel.AddressSlice;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;

/**
 * The json slice dto test.
 *
 * @author Christian Bremer
 */
class JsonSliceDtoTest {

  /**
   * Default constructor.
   */
  @Test
  void defaultConstructor() {
    assertThat(new AddressSlice()).isEqualTo(new AddressSlice());
  }

  /**
   * Gets content with no sort.
   */
  @Test
  void getContentWithNoSort() {
    List<Address> content = List.of(
        new Address("Berlin"),
        new Address("London"),
        new Address("New York"));
    AddressSlice actual = new AddressSlice(content, 0, 4, false);
    assertThat(actual)
        .extracting(AddressSlice::getContent, InstanceOfAssertFactories.list(Address.class))
        .containsExactly(
            new Address("Berlin"),
            new Address("London"),
            new Address("New York"));
  }

  /**
   * Gets content with sort.
   */
  @Test
  void getContentWithSort() {
    List<Address> content = List.of(
        new Address("Berlin"),
        new Address("London"),
        new Address("New York"));
    AddressSlice actual = new AddressSlice(content, 0, 4, false, Sort.by("city"));
    assertThat(actual)
        .extracting(AddressSlice::getContent, InstanceOfAssertFactories.list(Address.class))
        .containsExactly(
            new Address("Berlin"),
            new Address("London"),
            new Address("New York"));
  }

  /**
   * Gets content with sort orders.
   */
  @Test
  void getContentWithSortOrders() {
    List<Address> content = List.of(
        new Address("Berlin"),
        new Address("London"),
        new Address("New York"));
    AddressSlice actual = new AddressSlice(content, 0, 3, true,
        new SortOrder(List.of(SortOrderItem.by("city"))));
    assertThat(actual)
        .extracting(AddressSlice::getContent, InstanceOfAssertFactories.list(Address.class))
        .containsExactly(
            new Address("Berlin"),
            new Address("London"),
            new Address("New York"));
  }

  /**
   * Gets content with slice.
   */
  @Test
  void getContentWithSlice() {
    List<Address> content = List.of(
        new Address("Berlin"),
        new Address("London"),
        new Address("New York"));
    Slice<Address> slice = new SliceImpl<>(content, PageRequest.of(0, 4, Sort.by("city")), false);
    AddressSlice actual = new AddressSlice(slice);
    assertThat(actual)
        .extracting(AddressSlice::getContent, InstanceOfAssertFactories.list(Address.class))
        .containsExactly(
            new Address("Berlin"),
            new Address("London"),
            new Address("New York"));
  }

  /**
   * Gets sort wit sort.
   */
  @Test
  void getSortWitSort() {
    List<Address> content = List.of(
        new Address("Berlin"),
        new Address("London"),
        new Address("New York"));
    AddressSlice actual = new AddressSlice(content, 0, 4, false,
        SortOrder.by(SortOrderItem.by("city").with(CaseHandling.SENSITIVE)));
    assertThat(actual)
        .extracting(AddressSlice::getSort)
        .isNotNull();
    assertThat(actual)
        .extracting(AddressSlice::getSort)
        .isEqualTo(SortOrder.by(SortOrderItem.by("city").with(CaseHandling.SENSITIVE)));
  }

  /**
   * Gets pageable without sort.
   */
  @Test
  void getSliceableWithoutSort() {
    List<Address> content = List.of(
        new Address("Berlin"),
        new Address("London"),
        new Address("New York"));
    AddressSlice slice = new AddressSlice(content, 0, 4, false);
    Pageable actual = slice.getPageable();
    assertThat(actual.getPageNumber())
        .isZero();
    assertThat(actual.getPageSize())
        .isEqualTo(4);
    assertThat(actual.getSort().isUnsorted())
        .isTrue();
  }

  /**
   * Gets pageable with sort.
   */
  @Test
  void getPageableWithSort() {
    List<Address> content = List.of(
        new Address("Berlin"),
        new Address("London"),
        new Address("New York"));
    AddressSlice slice = new AddressSlice(content, 0, 4, false,
        SortOrder.by(SortOrderItem.by("city").with(CaseHandling.SENSITIVE)));
    Pageable actual = slice.getPageable();
    assertThat(actual.getPageNumber())
        .isZero();
    assertThat(actual.getPageSize())
        .isEqualTo(4);
    assertThat(actual.getSort().isSorted())
        .isTrue();
  }

  /**
   * To slice.
   */
  @Test
  void toSlice() {
    List<Address> content = List.of(
        new Address("Berlin"),
        new Address("London"),
        new Address("New York"));
    AddressSlice slice = new AddressSlice(content, 0, 4, false);
    Slice<Address> actual = slice.toSlice();
    assertThat(actual.getContent())
        .containsExactlyElementsOf(content);
  }

}