package org.bremersee.spring.security.ldaptive.authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.bremersee.ldaptive.LdaptiveTemplate;
import org.bremersee.spring.security.ldaptive.authentication.provider.ActiveDirectoryTemplate;
import org.bremersee.spring.security.ldaptive.authentication.provider.OpenLdapTemplate;
import org.bremersee.spring.security.ldaptive.authentication.provider.UserContainsGroupsTemplate;
import org.bremersee.spring.security.ldaptive.userdetails.LdaptiveUser;
import org.bremersee.spring.security.ldaptive.userdetails.LdaptiveUserDetails;
import org.bremersee.spring.security.ldaptive.userdetails.LdaptiveUserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.ldaptive.BindOperation;
import org.ldaptive.BindRequest;
import org.ldaptive.BindResponse;
import org.ldaptive.CompareRequest;
import org.ldaptive.ConnectionConfig;
import org.ldaptive.ConnectionFactory;
import org.ldaptive.DefaultConnectionFactory;
import org.ldaptive.LdapAttribute;
import org.ldaptive.LdapEntry;
import org.ldaptive.LdapException;
import org.ldaptive.SingleConnectionFactory;
import org.mockito.ArgumentCaptor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * The ldaptive authentication manager test.
 */
@ExtendWith({SoftAssertionsExtension.class})
class LdaptiveAuthenticationManagerTest {

  private static final String USER_BASE_DN = "ou=people,dc=bremersee,dc=org";

  private static final String USER_DN = "uid=junit,ou=people,dc=bremersee,dc=org";

  private static final String REMEMBER_ME_KEY = "bremersee";

  /**
   * Init.
   */
  @Test
  void init() {
    UserContainsGroupsTemplate properties = new UserContainsGroupsTemplate();
    properties.setPasswordAttribute("userPassword");
    LdaptiveAuthenticationManager target = new LdaptiveAuthenticationManager(
        new ConnectionConfig("ldap://localhost:389"),
        properties,
        REMEMBER_ME_KEY);
    assertThatExceptionOfType(IllegalStateException.class)
        .isThrownBy(target::init);

    target.setPasswordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
    target.setEmailToUsernameResolver(null); // has no effect
    target.setAccountControlEvaluator(null); // has no effect
    target.init();
  }

  /**
   * Supports.
   */
  @Test
  void supports() {
    LdaptiveAuthenticationManager target = new LdaptiveAuthenticationManager(
        mock(ConnectionFactory.class),
        new OpenLdapTemplate(),
        REMEMBER_ME_KEY);
    target.init();
    boolean actual = target.supports(UsernamePasswordAuthenticationToken.class);
    assertThat(actual)
        .isTrue();
  }

  /**
   * Is refused username.
   *
   * @param softly the softly
   */
  @Test
  void isRefusedUsername(SoftAssertions softly) {
    OpenLdapTemplate properties = new OpenLdapTemplate();
    properties.setRefusedUsernames(List.of("junit"));
    LdaptiveAuthenticationManager target = new LdaptiveAuthenticationManager(
        mock(ConnectionFactory.class),
        properties,
        REMEMBER_ME_KEY);
    target.init();
    boolean actual = target.isRefusedUsername("junit");
    softly.assertThat(actual).isTrue();
    actual = target.isRefusedUsername("");
    softly.assertThat(actual).isTrue();
    actual = target.isRefusedUsername("not-refused");
    softly.assertThat(actual).isFalse();
  }

  /**
   * Gets ldaptive template.
   */
  @Test
  void getLdaptiveTemplate() {
    ConnectionConfig connectionConfig = new ConnectionConfig("ldap://localhost:389");
    ConnectionFactory connectionFactory = new DefaultConnectionFactory(connectionConfig);
    LdaptiveTemplate ldaptiveTemplate = new LdaptiveTemplate(connectionFactory);
    LdaptiveAuthenticationManager target = new LdaptiveAuthenticationManager(
        ldaptiveTemplate,
        new ActiveDirectoryTemplate(),
        REMEMBER_ME_KEY);
    target.init();
    assertThat(target.getApplicationLdaptiveTemplate())
        .isEqualTo(ldaptiveTemplate);
  }

  /**
   * Gets user details service.
   */
  @Test
  void getUserDetailsService() {
    LdaptiveAuthenticationManager target = new LdaptiveAuthenticationManager(
        mock(ConnectionFactory.class),
        new OpenLdapTemplate(),
        REMEMBER_ME_KEY);
    assertThat(target.getUserDetailsService())
        .isNotNull();
  }

  /**
   * Authenticate with password comparison.
   *
   * @param softly the softly
   */
  @Test
  void authenticateWithPasswordComparison(SoftAssertions softly) {
    ConnectionConfig connectionConfig = new ConnectionConfig("ldap://localhost:389");
    ConnectionFactory connectionFactory = new DefaultConnectionFactory(connectionConfig);
    LdaptiveTemplate ldaptiveTemplate = spy(new LdaptiveTemplate(connectionFactory));
    UserContainsGroupsTemplate properties = new UserContainsGroupsTemplate();
    properties.setUserBaseDn(USER_BASE_DN);
    properties.setPasswordAttribute("userPassword");
    properties.setAccountControlEvaluator(null);
    LdaptiveAuthenticationManager target = spy(new LdaptiveAuthenticationManager(
        ldaptiveTemplate, properties,
        REMEMBER_ME_KEY));
    target.setTokenConverter(LdaptiveAuthenticationToken::new);

    PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    doAnswer(invocationOnMock -> invocationOnMock.getArgument(0))
        .when(passwordEncoder)
        .encode(anyString());
    target.setPasswordEncoder(passwordEncoder);

    target.init();

    LdapEntry user = createUser();
    doReturn(Optional.of(user))
        .when(ldaptiveTemplate)
        .findOne(any());
    ArgumentCaptor<CompareRequest> compareCaptor = ArgumentCaptor.forClass(CompareRequest.class);
    doReturn(true)
        .when(ldaptiveTemplate)
        .compare(compareCaptor.capture());

    LdaptiveAuthentication actual = (LdaptiveAuthentication) target
        .authenticate(new UsernamePasswordAuthenticationToken("junit", "secret"));

    softly
        .assertThat(compareCaptor.getValue())
        .isNotNull();
    verify(target)
        .checkAccountControl(any());
    assertActual(actual, softly);
  }

  /**
   * Authenticate with password comparison and bad credentials.
   */
  @Test
  void authenticateWithPasswordComparisonAndBadCredentials() {
    ConnectionConfig connectionConfig = new ConnectionConfig("ldap://localhost:389");
    ConnectionFactory connectionFactory = new DefaultConnectionFactory(connectionConfig);
    LdaptiveTemplate ldaptiveTemplate = spy(new LdaptiveTemplate(connectionFactory));
    UserContainsGroupsTemplate properties = new UserContainsGroupsTemplate();
    properties.setUserBaseDn(USER_BASE_DN);
    properties.setPasswordAttribute("userPassword");
    LdaptiveAuthenticationManager target = new LdaptiveAuthenticationManager(
        ldaptiveTemplate, properties,
        REMEMBER_ME_KEY);

    PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    doAnswer(invocationOnMock -> invocationOnMock.getArgument(0))
        .when(passwordEncoder)
        .encode(anyString());
    target.setPasswordEncoder(passwordEncoder);

    target.init();

    LdapEntry user = createUser();
    doReturn(Optional.of(user))
        .when(ldaptiveTemplate)
        .findOne(any());
    doReturn(false)
        .when(ldaptiveTemplate)
        .compare(any());

    var token = new UsernamePasswordAuthenticationToken("junit", "secret");
    assertThatExceptionOfType(BadCredentialsException.class)
        .isThrownBy(() -> target.authenticate(token));
  }

  /**
   * Authenticate with simple bind.
   *
   * @param softly the softly
   */
  @Test
  void authenticateWithSimpleBind(SoftAssertions softly) throws LdapException {
    ConnectionConfig connectionConfig = new ConnectionConfig("ldap://localhost:389");
    ConnectionFactory connectionFactory = new DefaultConnectionFactory(connectionConfig);
    LdaptiveTemplate ldaptiveTemplate = spy(new LdaptiveTemplate(connectionFactory));
    UserContainsGroupsTemplate properties = new UserContainsGroupsTemplate();
    properties.setUserBaseDn(USER_BASE_DN);
    LdaptiveAuthenticationManager target = spy(new LdaptiveAuthenticationManager(
        ldaptiveTemplate, properties, REMEMBER_ME_KEY));

    target.init();

    LdapEntry user = createUser();
    doReturn(Optional.of(user))
        .when(ldaptiveTemplate)
        .findOne(any());

    SingleConnectionFactory cf = mock(SingleConnectionFactory.class);
    doReturn(cf)
        .when(target)
        .getSingleConnectionFactory();
    BindOperation bindOperation = mock(BindOperation.class);
    doReturn(bindOperation)
        .when(target)
        .getBindOperation(any());
    BindResponse bindResponse = mock(BindResponse.class);
    doReturn(true)
        .when(bindResponse)
        .isSuccess();
    doReturn(bindResponse)
        .when(bindOperation)
        .execute(any(BindRequest.class));

    LdaptiveAuthentication actual = (LdaptiveAuthentication) target
        .authenticate(new UsernamePasswordAuthenticationToken("junit", "secret"));

    verify(target)
        .checkAccountControl(any());
    assertActual(actual, softly);
  }

  /**
   * Authenticate with simple bind and username not found.
   */
  @Test
  void authenticateWithSimpleBindAndUsernameNotFound() {
    ConnectionConfig connectionConfig = new ConnectionConfig("ldap://localhost:389");
    ConnectionFactory connectionFactory = new DefaultConnectionFactory(connectionConfig);
    LdaptiveTemplate ldaptiveTemplate = spy(new LdaptiveTemplate(connectionFactory));
    UserContainsGroupsTemplate properties = new UserContainsGroupsTemplate();
    properties.setUserBaseDn(USER_BASE_DN);
    LdaptiveAuthenticationManager target = spy(new LdaptiveAuthenticationManager(
        ldaptiveTemplate, properties, REMEMBER_ME_KEY));

    target.init();

    LdaptiveUserDetailsService userDetailsService = mock(LdaptiveUserDetailsService.class);
    doThrow(new UsernameNotFoundException("junit not found"))
        .when(userDetailsService)
        .loadUserByUsername(anyString());
    doReturn(userDetailsService)
        .when(target)
        .getUserDetailsService();

    var token = new UsernamePasswordAuthenticationToken("junit", "secret");
    assertThatExceptionOfType(UsernameNotFoundException.class)
        .isThrownBy(() -> target.authenticate(token));
  }

  /**
   * Authenticate with simple bind and bad credentials.
   */
  @Test
  void authenticateWithSimpleBindAndBadCredentials() throws LdapException {
    ConnectionConfig connectionConfig = new ConnectionConfig("ldap://localhost:389");
    ConnectionFactory connectionFactory = new DefaultConnectionFactory(connectionConfig);
    LdaptiveTemplate ldaptiveTemplate = spy(new LdaptiveTemplate(connectionFactory));
    UserContainsGroupsTemplate properties = new UserContainsGroupsTemplate();
    properties.setUserBaseDn(USER_BASE_DN);
    LdaptiveAuthenticationManager target = spy(new LdaptiveAuthenticationManager(
        ldaptiveTemplate, properties, REMEMBER_ME_KEY));

    target.init();

    LdapEntry user = createUser();
    doReturn(Optional.of(user))
        .when(ldaptiveTemplate)
        .findOne(any());

    SingleConnectionFactory cf = mock(SingleConnectionFactory.class);
    doReturn(cf)
        .when(target)
        .getSingleConnectionFactory();
    BindOperation bindOperation = mock(BindOperation.class);
    doReturn(bindOperation)
        .when(target)
        .getBindOperation(any());
    BindResponse bindResponse = mock(BindResponse.class);
    doReturn(false)
        .when(bindResponse)
        .isSuccess();
    doReturn(bindResponse)
        .when(bindOperation)
        .execute(any(BindRequest.class));

    var token = new UsernamePasswordAuthenticationToken("junit", "secret");
    assertThatExceptionOfType(BadCredentialsException.class)
        .isThrownBy(() -> target.authenticate(token));
  }

  /**
   * Authenticate with user ldaptive template and refused username.
   */
  @Test
  void authenticateWithRefusedUsername() {
    ConnectionConfig connectionConfig = new ConnectionConfig("ldap://localhost:389");
    ConnectionFactory connectionFactory = new DefaultConnectionFactory(connectionConfig);
    LdaptiveTemplate ldaptiveTemplate = spy(new LdaptiveTemplate(connectionFactory));
    ActiveDirectoryTemplate properties = new ActiveDirectoryTemplate();
    properties.setUserBaseDn(USER_BASE_DN);
    properties.setRefusedUsernames(List.of("refused"));
    LdaptiveAuthenticationManager target = spy(new LdaptiveAuthenticationManager(
        ldaptiveTemplate, properties, REMEMBER_ME_KEY));

    target.init();

    var token = new UsernamePasswordAuthenticationToken("refused", "secret");
    assertThatExceptionOfType(DisabledException.class)
        .isThrownBy(() -> target.authenticate(token));
  }

  /**
   * Check account control.
   *
   * @param softly the softly
   */
  @Test
  void checkAccountControl(SoftAssertions softly) {
    ConnectionConfig connectionConfig = new ConnectionConfig("ldap://localhost:389");
    ConnectionFactory connectionFactory = new DefaultConnectionFactory(connectionConfig);
    LdaptiveTemplate ldaptiveTemplate = new LdaptiveTemplate(connectionFactory);
    UserContainsGroupsTemplate properties = new UserContainsGroupsTemplate();
    properties.setUserBaseDn(USER_BASE_DN);
    LdaptiveAuthenticationManager target = new LdaptiveAuthenticationManager(
        ldaptiveTemplate, properties, REMEMBER_ME_KEY);

    LdapEntry user = createUser();

    LdaptiveUserDetails d0 = new LdaptiveUser(
        user, "junit", null, null, null,
        List.of(new SimpleGrantedAuthority("ROLE_tester")),
        "secret",
        false, true, true, true);
    softly.assertThatExceptionOfType(AccountExpiredException.class)
        .isThrownBy(() -> target.checkAccountControl(d0));

    LdaptiveUserDetails d1 = new LdaptiveUser(
        user, "junit", null, null, null,
        List.of(new SimpleGrantedAuthority("ROLE_tester")),
        "secret",
        true, false, true, true);
    softly.assertThatExceptionOfType(LockedException.class)
        .isThrownBy(() -> target.checkAccountControl(d1));

    LdaptiveUserDetails d2 = new LdaptiveUser(
        user, "junit", null, null, null,
        List.of(new SimpleGrantedAuthority("ROLE_tester")),
        "secret",
        true, true, false, true);
    softly.assertThatExceptionOfType(CredentialsExpiredException.class)
        .isThrownBy(() -> target.checkAccountControl(d2));

    LdaptiveUserDetails d3 = new LdaptiveUser(
        user, "junit", null, null, null,
        List.of(new SimpleGrantedAuthority("ROLE_tester")),
        "secret",
        true, true, true, false);
    softly.assertThatExceptionOfType(DisabledException.class)
        .isThrownBy(() -> target.checkAccountControl(d3));
  }

  private void assertActual(LdaptiveAuthentication authentication, SoftAssertions softly) {
    List<GrantedAuthority> actualAuthorities = new ArrayList<>(authentication.getAuthorities());
    List<GrantedAuthority> expectedAuthorities = List.of(new SimpleGrantedAuthority("ROLE_tester"));
    softly
        .assertThat(actualAuthorities)
        .containsExactlyInAnyOrderElementsOf(expectedAuthorities);
    softly
        .assertThat(authentication.getName())
        .isEqualTo("junit");
    softly
        .assertThat(authentication.getPrincipal().getFirstName())
        .isEqualTo("Test");
    softly
        .assertThat(authentication.getPrincipal().getLastName())
        .isEqualTo("User");
    softly
        .assertThat(authentication.getPrincipal().getEmail())
        .isEqualTo("junit@example.com");

    assertThat(authentication.getPrincipal())
        .isInstanceOf(LdaptiveUserDetails.class);
    LdaptiveUserDetails principal = authentication.getPrincipal();
    softly
        .assertThat(principal.getDn())
        .isEqualTo(USER_DN);
    softly
        .assertThat(principal.getName())
        .isEqualTo("junit");
    softly
        .assertThat(principal.getName())
        .isEqualTo("junit");
    softly
        .assertThat(principal.getUsername())
        .isEqualTo("junit");
    softly
        .assertThat(principal.getPassword())
        .isNotEmpty();
    softly
        .assertThat(principal.isEnabled())
        .isTrue();
    softly
        .assertThat(principal.isAccountNonLocked())
        .isTrue();
    softly
        .assertThat(principal.isAccountNonExpired())
        .isTrue();
    softly
        .assertThat(principal.isCredentialsNonExpired())
        .isTrue();
  }

  /**
   * Remember me authentication throws bad credentials exception with invalid key.
   */
  @Test
  void rememberMeAuthenticationThrowsBadCredentialsExceptionWithInvalidKey() {
    LdaptiveAuthenticationManager target = new LdaptiveAuthenticationManager(
        mock(ConnectionFactory.class),
        new OpenLdapTemplate(),
        REMEMBER_ME_KEY);

    RememberMeAuthenticationToken token = mock(RememberMeAuthenticationToken.class);
    doReturn(0)
        .when(token)
        .getKeyHash();

    Assertions.assertThatExceptionOfType(BadCredentialsException.class)
        .isThrownBy(() -> target.authenticate(token));
  }

  private LdapEntry createUser() {
    LdapEntry entry = new LdapEntry();
    entry.setDn(USER_DN);
    entry.addAttributes(LdapAttribute.builder().name("uid").values("junit").build());
    entry.addAttributes(LdapAttribute.builder().name("givenName").values("Test").build());
    entry.addAttributes(LdapAttribute.builder().name("sn").values("User").build());
    entry.addAttributes(LdapAttribute.builder().name("mail").values("junit@example.com").build());
    entry.addAttributes(LdapAttribute.builder()
        .name("memberOf").values("cn=tester," + USER_BASE_DN).build());
    return entry;
  }

}