package org.bremersee.spring.security.oauth2.server.resource.authentication;

import static java.util.Objects.isNull;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.bremersee.spring.security.core.mapping.group.GroupsMapper;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;

/**
 * The json path jwt properties.
 */
@Data
@Builder(toBuilder = true)
public class JsonPathJwtProperties {

  /**
   * The json path to the username.
   */
  private final String nameJsonPath;

  /**
   * The json path to the first name.
   */
  private final String firstNameJsonPath;

  /**
   * The json path to the last name.
   */
  private final String lastNameJsonPath;

  /**
   * The json path to the email.
   */
  private final String emailJsonPath;

  /**
   * The json path to the roles.
   */
  private final String rolesJsonPath;

  /**
   * Specifies whether the roles are represented as a json array or as a list separated by
   * {@link #getRolesValueSeparator()}.
   */
  private final boolean rolesValueList;

  /**
   * The roles separator to use if {@link #isRolesValueList()} is set to {@code false}.
   */
  private final String rolesValueSeparator;

  /**
   * The mapper of authorities.
   */
  private final GrantedAuthoritiesMapper authoritiesMapper;


  /**
   * The json path to the groups.
   */
  private final String groupsJsonPath;

  /**
   * Specifies whether the groups are represented as a json array or as a list separated by
   * {@link #getGroupsValueSeparator()}.
   */
  private final boolean groupsValueList;

  /**
   * The groups separator to use if {@link #isGroupsValueList()} is set to {@code false}.
   */
  private final String groupsValueSeparator;

  /**
   * The mapper of groups.
   */
  private final GroupsMapper groupsMapper;

  /**
   * Gets authorities mapper.
   *
   * @return the authorities mapper
   */
  public GrantedAuthoritiesMapper getAuthoritiesMapper() {
    if (isNull(authoritiesMapper)) {
      return col -> col;
    }
    return authoritiesMapper;
  }

  /**
   * Gets groups mapper.
   *
   * @return the groups mapper
   */
  public GroupsMapper getGroupsMapper() {
    if (isNull(groupsMapper)) {
      return List::copyOf;
    }
    return groupsMapper;
  }

  /**
   * Gets groups value separator.
   *
   * @return the groups value separator
   */
  public String getGroupsValueSeparator() {
    if (isNull(groupsValueSeparator) || groupsValueSeparator.isEmpty()) {
      return " ";
    }
    return groupsValueSeparator;
  }

  /**
   * Gets roles value separator.
   *
   * @return the roles value separator
   */
  public String getRolesValueSeparator() {
    if (isNull(rolesValueSeparator) || rolesValueSeparator.isEmpty()) {
      return " ";
    }
    return rolesValueSeparator;
  }

}
