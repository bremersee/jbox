/*
 * Copyright 2024-2026 the original author or authors.
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

package org.bremersee.spring.boot.autoconfigure.security.authentication;

import static java.util.Objects.isNull;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bremersee.spring.boot.autoconfigure.security.authentication.AuthenticationProperties.JwtConverterProperties;
import org.bremersee.spring.security.core.mapping.CaseTransformation;
import org.bremersee.spring.security.core.mapping.authority.NormalizedGrantedAuthoritiesMapper;
import org.bremersee.spring.security.core.mapping.group.GroupsMapper;
import org.bremersee.spring.security.core.mapping.group.NormalizedGroupsMapper;
import org.bremersee.spring.security.oauth2.server.resource.authentication.JsonPathJwtConverter;
import org.bremersee.spring.security.oauth2.server.resource.authentication.JsonPathJwtProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.security.autoconfigure.web.reactive.ReactiveWebSecurityAutoConfiguration;
import org.springframework.boot.security.autoconfigure.web.servlet.ServletWebSecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.util.ClassUtils;

/**
 * The jwt converter autoconfiguration.
 *
 * @author Christian Bremer
 */
@ConditionalOnClass(name = {
    "org.bremersee.spring.security.oauth2.server.resource.authentication.JsonPathJwtConverter",
    "org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter",
    "org.springframework.boot.security.autoconfigure.web.reactive.ReactiveWebSecurityAutoConfiguration",
    "org.springframework.boot.security.autoconfigure.web.servlet.ServletWebSecurityAutoConfiguration"
})
@AutoConfiguration(
    before = {
        ServletWebSecurityAutoConfiguration.class,
        ReactiveWebSecurityAutoConfiguration.class
    })
@ConditionalOnProperty(
    prefix = "spring.security.oauth2.resourceserver.jwt",
    name = "jwk-set-uri")
@EnableConfigurationProperties(AuthenticationProperties.class)
public class JwtConverterAutoConfiguration {

  private static final Log log = LogFactory.getLog(JwtConverterAutoConfiguration.class);

  private final JwtConverterProperties properties;

  /**
   * Instantiates a new Jwt converter auto configuration.
   *
   * @param properties the properties
   */
  public JwtConverterAutoConfiguration(AuthenticationProperties properties) {
    this.properties = properties.getJwtConverter();
  }

  /**
   * Init.
   */
  @EventListener(ApplicationReadyEvent.class)
  public void init() {
    log.info(String.format("""
            
            *********************************************************************************
            * %s
            * properties = %s
            *********************************************************************************""",
        ClassUtils.getUserClass(getClass()).getSimpleName(),
        properties));
  }

  /**
   * Creates jwt converter.
   *
   * @param rolesMapperProvider the mapper of roles
   * @param groupsMapperProvider the mapper of groups
   * @return the converter
   */
  @ConditionalOnMissingBean({JwtAuthenticationConverter.class})
  @Bean
  public Converter<Jwt, AbstractAuthenticationToken> jwtConverter(
      ObjectProvider<GrantedAuthoritiesMapper> rolesMapperProvider,
      ObjectProvider<GroupsMapper> groupsMapperProvider) {

    log.info("Creating new jwt authentication converter.");

    GrantedAuthoritiesMapper grantedAuthoritiesMapper = rolesMapperProvider
        .getIfAvailable(() -> new NormalizedGrantedAuthoritiesMapper(
            properties.getDefaultRoles(),
            properties.toRoleMappings(),
            properties.getRolePrefix(),
            getCaseTransformation(properties.getRoleCaseTransformation()),
            properties.toRoleStringReplacements()));

    GroupsMapper groupsMapper = groupsMapperProvider
        .getIfAvailable(() -> new NormalizedGroupsMapper(
            properties.getDefaultGroups(),
            properties.toGroupMappings(),
            properties.getGroupPrefix(),
            getCaseTransformation(properties.getGroupCaseTransformation()),
            properties.toGroupStringReplacements()));

    JsonPathJwtProperties jsonPathJwtProperties = JsonPathJwtProperties.builder()
        .nameJsonPath(properties.getNameJsonPath())
        .firstNameJsonPath(properties.getFirstNameJsonPath())
        .lastNameJsonPath(properties.getLastNameJsonPath())
        .emailJsonPath(properties.getEmailJsonPath())
        .rolesJsonPath(properties.getRolesJsonPath())
        .rolesValueList(properties.isRolesValueList())
        .rolesValueSeparator(properties.getRolesValueSeparator())
        .authoritiesMapper(grantedAuthoritiesMapper)
        .groupsJsonPath(properties.getGroupsJsonPath())
        .groupsValueList(properties.isGroupsValueList())
        .groupsValueSeparator(properties.getGroupsValueSeparator())
        .groupsMapper(groupsMapper)
        .build();

    return new JsonPathJwtConverter(jsonPathJwtProperties);
  }

  private CaseTransformation getCaseTransformation(
      AuthenticationProperties.CaseTransformation source) {
    return isNull(source) ? CaseTransformation.NONE : CaseTransformation.valueOf(source.name());
  }
}
