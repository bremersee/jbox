package org.bremersee.pagebuilder.testmodel;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.NoArgsConstructor;
import org.bremersee.comparator.model.SortOrder;
import org.bremersee.pagebuilder.model.JsonSliceDto;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

/**
 * The type Address slice.
 */
@Schema(description = "An address slice.")
@NoArgsConstructor
public class AddressSlice extends JsonSliceDto<Address> {

  /**
   * Instantiates a new Address slice.
   *
   * @param content the content
   * @param number the number
   * @param size the size
   * @param hasNext the has next
   */
  public AddressSlice(List<? extends Address> content, int number, int size,
      boolean hasNext) {
    super(content, number, size, hasNext);
  }

  /**
   * Instantiates a new Address slice.
   *
   * @param content the content
   * @param number the number
   * @param size the size
   * @param hasNext the has next
   * @param sort the sort
   */
  public AddressSlice(List<? extends Address> content, int number, int size,
      boolean hasNext, SortOrder sort) {
    super(content, number, size, hasNext, sort);
  }

  /**
   * Instantiates a new Address slice.
   *
   * @param content the content
   * @param number the number
   * @param size the size
   * @param hasNext the has next
   * @param sort the sort
   */
  public AddressSlice(List<? extends Address> content, int number, int size,
      boolean hasNext, Sort sort) {
    super(content, number, size, hasNext, sort);
  }

  /**
   * Instantiates a new Address slice.
   *
   * @param slice the slice
   */
  public AddressSlice(Slice<? extends Address> slice) {
    super(slice);
  }
}
