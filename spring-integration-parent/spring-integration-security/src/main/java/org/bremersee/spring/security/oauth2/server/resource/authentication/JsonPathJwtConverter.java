/*
 * Copyright 2020-2026 the original author or authors.
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

package org.bremersee.spring.security.oauth2.server.resource.authentication;

import static org.springframework.util.ObjectUtils.isEmpty;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.bremersee.spring.security.core.Group;
import org.bremersee.spring.security.core.NormalizedGroup;
import org.bremersee.spring.security.core.NormalizedUser;
import org.jspecify.annotations.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.Assert;

/**
 * The json path jwt converter.
 *
 * @author Christian Bremer
 */
@Getter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
@EqualsAndHashCode
public class JsonPathJwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {

  private final JsonPathJwtProperties properties;

  /**
   * Instantiates a new Json path jwt converter.
   *
   * @param properties the properties
   */
  public JsonPathJwtConverter(JsonPathJwtProperties properties) {
    Assert.notNull(properties, "Properties must not be null.");
    this.properties = properties;
  }

  @NonNull
  @Override
  public NormalizedJwtAuthenticationToken convert(@NonNull Jwt source) {
    JsonPathJwtParser parser = new JsonPathJwtParser(source);
    return new NormalizedJwtAuthenticationToken(
        source,
        new NormalizedUser(
            getUsername(source, parser),
            getFirstName(parser),
            getLastName(parser),
            getEmail(parser)),
        getGrantedAuthorities(parser),
        getGroups(parser));
  }

  /**
   * Gets groups.
   *
   * @param parser the parser
   * @return the groups
   */
  protected Collection<? extends GrantedAuthority> getGrantedAuthorities(JsonPathJwtParser parser) {
    Stream<String> values = properties.isRolesValueList()
        ? getAuthoritiesFromList(parser)
        : getAuthoritiesFromValue(parser);
    Set<GrantedAuthority> authorities = values.map(SimpleGrantedAuthority::new)
        .collect(Collectors.toSet());
    return properties.getAuthoritiesMapper().mapAuthorities(authorities);
  }

  /**
   * Gets authorities from list.
   *
   * @param parser the parser
   * @return the authorities from list
   */
  protected Stream<String> getAuthoritiesFromList(JsonPathJwtParser parser) {
    //noinspection unchecked
    return Stream.ofNullable(properties.getRolesJsonPath())
        .map(path -> parser.read(path, List.class))
        .filter(Objects::nonNull)
        .map(list -> (List<String>) list)
        .flatMap(Collection::stream);
  }

  /**
   * Gets authorities from value.
   *
   * @param parser the parser
   * @return the authorities from value
   */
  protected Stream<String> getAuthoritiesFromValue(JsonPathJwtParser parser) {
    return Stream.ofNullable(properties.getRolesJsonPath())
        .filter(path -> !isEmpty(properties.getRolesValueSeparator()))
        .map(path -> parser.read(path, String.class))
        .filter(Objects::nonNull)
        .map(value -> value.split(Pattern.quote(properties.getRolesValueSeparator())))
        .flatMap(Arrays::stream)
        .map(String::valueOf);
  }

  /**
   * Gets groups.
   *
   * @param parser the parser
   * @return the groups
   */
  protected Collection<Group> getGroups(JsonPathJwtParser parser) {
    Stream<String> values = properties.isGroupsValueList()
        ? getGroupsFromList(parser)
        : getGroupsFromValue(parser);
    Set<Group> groups = values.map(NormalizedGroup::of)
        .collect(Collectors.toSet());
    return properties.getGroupsMapper().mapGroups(groups);
  }

  /**
   * Gets groups from list.
   *
   * @param parser the parser
   * @return the groups from list
   */
  protected Stream<String> getGroupsFromList(JsonPathJwtParser parser) {
    //noinspection unchecked
    return Stream.ofNullable(properties.getGroupsJsonPath())
        .map(path -> parser.read(path, List.class))
        .filter(Objects::nonNull)
        .map(list -> (List<String>) list)
        .flatMap(Collection::stream);
  }

  /**
   * Gets groups from value.
   *
   * @param parser the parser
   * @return the groups from value
   */
  protected Stream<String> getGroupsFromValue(JsonPathJwtParser parser) {
    return Stream.ofNullable(properties.getGroupsJsonPath())
        .filter(path -> !isEmpty(properties.getGroupsValueSeparator()))
        .map(path -> parser.read(path, String.class))
        .filter(Objects::nonNull)
        .map(value -> value.split(Pattern.quote(properties.getGroupsValueSeparator())))
        .flatMap(Arrays::stream)
        .map(String::valueOf);
  }

  /**
   * Gets username.
   *
   * @param source the source
   * @param parser the parser
   * @return the username
   */
  protected String getUsername(Jwt source, JsonPathJwtParser parser) {
    return Optional.ofNullable(properties.getNameJsonPath())
        .filter(jsonPath -> !jsonPath.isBlank())
        .map(jsonPath -> parser.read(jsonPath, String.class))
        .orElseGet(source::getSubject);
  }

  /**
   * Gets first name.
   *
   * @param parser the parser
   * @return the first name
   */
  protected String getFirstName(JsonPathJwtParser parser) {
    return parser.read(properties.getFirstNameJsonPath(), String.class);
  }

  /**
   * Gets last name.
   *
   * @param parser the parser
   * @return the last name
   */
  protected String getLastName(JsonPathJwtParser parser) {
    return parser.read(properties.getLastNameJsonPath(), String.class);
  }

  /**
   * Gets email.
   *
   * @param parser the parser
   * @return the email
   */
  protected String getEmail(JsonPathJwtParser parser) {
    return parser.read(properties.getEmailJsonPath(), String.class);
  }

}
