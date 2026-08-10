package org.bremersee.spring.security.core.mapping.group;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bremersee.spring.security.core.Group;
import org.bremersee.spring.security.core.NormalizedGroup;
import org.bremersee.spring.security.core.mapping.CaseTransformation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * The type Normalized groups mapper test.
 */
class NormalizedGroupsMapperTest {

  /**
   * Gets string value.
   */
  @Test
  void getStringValue() {
    NormalizedGroupsMapper target = createTarget(CaseTransformation.TO_LOWER_CASE);
    Group source = NormalizedGroup.of("a-group");
    String actual = target.getStringValue(source);
    assertThat(actual).isEqualTo("a-group");
  }

  /**
   * Create target.
   */
  @Test
  void createTarget() {
    NormalizedGroupsMapper target = createTarget(CaseTransformation.TO_LOWER_CASE);
    Group actual = target.createTarget("a-group");
    Group expected = NormalizedGroup.of("a-group");
    assertThat(actual).isEqualTo(expected);
  }

  /**
   * Gets default prefix.
   */
  @Test
  void getDefaultPrefix() {
    NormalizedGroupsMapper target = createTarget(CaseTransformation.TO_LOWER_CASE);
    String actual = target.getDefaultPrefix();
    assertThat(actual).isEmpty();
  }

  /**
   * Map groups.
   *
   * @param transformation the transformation
   */
  @ParameterizedTest
  @ValueSource(strings = {"TO_UPPER_CASE", "TO_LOWER_CASE"})
  void mapGroups(String transformation) {
    CaseTransformation caseTransformation = CaseTransformation.valueOf(transformation);
    NormalizedGroupsMapper target = createTarget(caseTransformation);
    Collection<? extends Group> authorities = List.of(
        NormalizedGroup.of("junit-developers"),
        NormalizedGroup.of("junit-developers"),
        NormalizedGroup.of("foo"));
    Collection<Group> actual = new ArrayList<>(target.mapGroups(authorities));
    String normalizedValue = CaseTransformation.TO_UPPER_CASE.equals(caseTransformation)
        ? "JUNIT_DEVELOPERS"
        : "junit_developers";
    assertThat(actual)
        .containsExactlyInAnyOrderElementsOf(Set.of(
            NormalizedGroup.of("GROUP_" + normalizedValue),
            NormalizedGroup.of("GROUP_BAR"),
            NormalizedGroup.of("GROUP_LDAP")));
  }

  private NormalizedGroupsMapper createTarget(CaseTransformation caseTransformation) {
    return new NormalizedGroupsMapper(
        List.of("GROUP_LDAP"),
        Map.of("foo", "GROUP_BAR"),
        "GROUP_",
        caseTransformation,
        Map.of("[-]", "_"));
  }

}