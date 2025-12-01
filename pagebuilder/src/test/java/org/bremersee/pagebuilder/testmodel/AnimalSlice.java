package org.bremersee.pagebuilder.testmodel;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlElementRefs;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bremersee.comparator.model.SortOrder;
import org.bremersee.pagebuilder.model.AbstractSliceDto;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

/**
 * The type Animal slice.
 */
@XmlRootElement(name = "animalSlice")
@XmlType(name = "animalSliceType")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "An animal slice.")
public class AnimalSlice extends AbstractSliceDto<Animal> {

  /**
   * Instantiates a new Animal slice.
   */
  protected AnimalSlice() {
  }

  /**
   * Instantiates a new Animal slice.
   *
   * @param content the content
   * @param number the number
   * @param size the size
   * @param hasNext the has next
   */
  public AnimalSlice(List<? extends Animal> content, int number, int size, boolean hasNext) {
    super(content, number, size, hasNext);
  }

  /**
   * Instantiates a new Animal slice.
   *
   * @param content the content
   * @param number the number
   * @param size the size
   * @param hasNext the has next
   * @param sort the sort
   */
  public AnimalSlice(List<? extends Animal> content, int number, int size, boolean hasNext,
      SortOrder sort) {
    super(content, number, size, hasNext, sort);
  }

  /**
   * Instantiates a new Animal slice.
   *
   * @param content the content
   * @param number the number
   * @param size the size
   * @param hasNext the has next
   * @param sort the sort
   */
  public AnimalSlice(List<? extends Animal> content, int number, int size, boolean hasNext,
      Sort sort) {
    super(content, number, size, hasNext, sort);
  }

  /**
   * Instantiates a new Animal slice.
   *
   * @param slice the slice
   */
  public AnimalSlice(Slice<? extends Animal> slice) {
    super(slice);
  }

  @XmlElementWrapper(name = "animals")
  @XmlElementRefs({
      @XmlElementRef(type = Cat.class),
      @XmlElementRef(type = Dog.class)
  })
  @Override
  public List<Animal> getContent() {
    return List.of();
  }
}
