package org.bremersee.spring.boot.autoconfigure.security.authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.bremersee.spring.security.core.mapping.group.GroupsMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;

/**
 * The type Jwt converter autoconfiguration test.
 */
class JwtConverterAutoConfigurationTest {

  /**
   * The Target.
   */
  JwtConverterAutoConfiguration target;

  /**
   * Init.
   */
  @BeforeEach
  void init() {
    AuthenticationProperties properties = new AuthenticationProperties();
    target = new JwtConverterAutoConfiguration(properties);
    target.init();
  }

  /**
   * Jwt converter.
   */
  @Test
  void jwtConverter() {
    @SuppressWarnings("unchecked")
    ObjectProvider<GrantedAuthoritiesMapper> rolesMapper = mock(ObjectProvider.class);
    doReturn(mock(GrantedAuthoritiesMapper.class))
        .when(rolesMapper).getIfAvailable(any());
    @SuppressWarnings("unchecked")
    ObjectProvider<GroupsMapper> groupsMapper = mock(ObjectProvider.class);
    doReturn(mock(GroupsMapper.class))
        .when(groupsMapper).getIfAvailable(any());
    assertThat(target.jwtConverter(rolesMapper, groupsMapper))
        .isNotNull();
  }
}