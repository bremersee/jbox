/*
 * Copyright 2014 the original author or authors.
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

package org.bremersee.spring.security.ldaptive.authentication;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNullElseGet;
import static org.springframework.util.ObjectUtils.isEmpty;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bremersee.ldaptive.DefaultLdaptiveErrorHandler;
import org.bremersee.ldaptive.LdaptiveTemplate;
import org.bremersee.spring.security.core.EmailToUsernameResolver;
import org.bremersee.spring.security.ldaptive.authentication.provider.NoAccountControlEvaluator;
import org.bremersee.spring.security.ldaptive.userdetails.LdaptiveRememberMeTokenProvider;
import org.bremersee.spring.security.ldaptive.userdetails.LdaptiveUserDetails;
import org.bremersee.spring.security.ldaptive.userdetails.LdaptiveUserDetailsService;
import org.ldaptive.BindOperation;
import org.ldaptive.BindResponse;
import org.ldaptive.CompareRequest;
import org.ldaptive.ConnectionConfig;
import org.ldaptive.ConnectionFactory;
import org.ldaptive.DefaultConnectionFactory;
import org.ldaptive.LdapException;
import org.ldaptive.SimpleBindRequest;
import org.ldaptive.SingleConnectionFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

/**
 * The ldaptive authentication manager.
 *
 * @author Christian Bremer
 */
public class LdaptiveAuthenticationManager
    implements AuthenticationManager, AuthenticationProvider, MessageSourceAware {

  /**
   * The Logger.
   */
  protected final Log logger = LogFactory.getLog(this.getClass());

  /**
   * The authentication properties.
   */
  @Getter(AccessLevel.PROTECTED)
  private final LdaptiveAuthenticationProperties authenticationProperties;

  /**
   * The remember-me key.
   */
  @Getter(AccessLevel.PROTECTED)
  private final String rememberMeKey;

  /**
   * The application ldaptive template.
   */
  @Getter(AccessLevel.PROTECTED)
  private final LdaptiveTemplate applicationLdaptiveTemplate;

  /**
   * The email to username resolver.
   */
  @Getter(AccessLevel.PROTECTED)
  private EmailToUsernameResolver emailToUsernameResolver;

  /**
   * The password encoder.
   */
  @Getter(AccessLevel.PROTECTED)
  @Setter
  private PasswordEncoder passwordEncoder;

  /**
   * The account control evaluator.
   */
  @Getter(AccessLevel.PROTECTED)
  private AccountControlEvaluator accountControlEvaluator;

  /**
   * The granted authorities mapper.
   */
  @Getter(AccessLevel.PROTECTED)
  @Setter
  private GrantedAuthoritiesMapper grantedAuthoritiesMapper;

  /**
   * The remember-me token provider.
   */
  @Getter(AccessLevel.PROTECTED)
  @Setter
  private LdaptiveRememberMeTokenProvider passwordProvider;

  /**
   * The token converter.
   */
  @Getter(AccessLevel.PROTECTED)
  @Setter
  private Converter<LdaptiveUserDetails, LdaptiveAuthentication> tokenConverter;

  /**
   * The message source.
   */
  @Getter(AccessLevel.PROTECTED)
  private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

  /**
   * Instantiates a new ldaptive authentication manager.
   *
   * @param connectionConfig the connection config
   * @param authenticationProperties the authentication properties
   * @param rememberMeKey the remember me key
   */
  public LdaptiveAuthenticationManager(
      ConnectionConfig connectionConfig,
      LdaptiveAuthenticationProperties authenticationProperties,
      String rememberMeKey) {
    this(new DefaultConnectionFactory(connectionConfig), authenticationProperties, rememberMeKey);
  }

  /**
   * Instantiates a new ldaptive authentication manager.
   *
   * @param connectionFactory the connection factory
   * @param authenticationProperties the authentication properties
   * @param rememberMeKey the remember me key
   */
  public LdaptiveAuthenticationManager(
      ConnectionFactory connectionFactory,
      LdaptiveAuthenticationProperties authenticationProperties,
      String rememberMeKey) {
    this(new LdaptiveTemplate(connectionFactory), authenticationProperties, rememberMeKey);
  }

  /**
   * Instantiates a new ldaptive authentication manager.
   *
   * @param applicationLdaptiveTemplate the application ldaptive template
   * @param authenticationProperties the authentication properties
   * @param rememberMeKey the remember me key
   */
  public LdaptiveAuthenticationManager(
      LdaptiveTemplate applicationLdaptiveTemplate,
      LdaptiveAuthenticationProperties authenticationProperties,
      String rememberMeKey) {

    this.applicationLdaptiveTemplate = applicationLdaptiveTemplate;
    Assert.notNull(getApplicationLdaptiveTemplate(), "Application ldaptive template is required.");
    this.authenticationProperties = authenticationProperties;
    Assert.notNull(getAuthenticationProperties(), "Authentication properties are required.");
    this.rememberMeKey = rememberMeKey;

    // emailToUsernameResolver
    setEmailToUsernameResolver(new EmailToUsernameResolverByLdapAttribute(
        getAuthenticationProperties(), getApplicationLdaptiveTemplate()));

    // accountControlEvaluator
    if (isNull(getAuthenticationProperties().getAccountControlEvaluator())) {
      setAccountControlEvaluator(new NoAccountControlEvaluator());
    } else {
      setAccountControlEvaluator(getAuthenticationProperties().getAccountControlEvaluator().get());
    }
  }

  /**
   * Sets email to username resolver.
   *
   * @param emailToUsernameResolver the email to username resolver
   */
  public void setEmailToUsernameResolver(
      EmailToUsernameResolver emailToUsernameResolver) {
    if (nonNull(emailToUsernameResolver)) {
      this.emailToUsernameResolver = emailToUsernameResolver;
    }
  }

  /**
   * Sets account control evaluator.
   *
   * @param accountControlEvaluator the account control evaluator
   */
  public void setAccountControlEvaluator(
      AccountControlEvaluator accountControlEvaluator) {
    if (nonNull(accountControlEvaluator)) {
      this.accountControlEvaluator = accountControlEvaluator;
    }
  }

  @Override
  public void setMessageSource(@NonNull MessageSource messageSource) {
    this.messages = new MessageSourceAccessor(messageSource);
  }

  /**
   * Init.
   */
  public void init() {
    if (!isSimpleBindAuthentication() && isNull(getPasswordEncoder())) {
      throw new IllegalStateException(String.format("A password attribute is set (%s) but no "
              + "password encoder is present. Either delete the password attribute to perform a "
              + "bind to authenticate or set a password encoder.",
          getAuthenticationProperties().getPasswordAttribute()));
    }
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication)
        || isRememberMeAuthentication(authentication);
  }

  private boolean isRememberMeAuthentication(Class<?> authentication) {
    return !isEmpty(getRememberMeKey())
        && RememberMeAuthenticationToken.class.isAssignableFrom(authentication);
  }

  /**
   * Remember me key matches given authentication.
   *
   * @param authentication the authentication
   * @return the boolean
   */
  protected boolean rememberMeKeyMatches(RememberMeAuthenticationToken authentication) {
    return Optional.ofNullable(getRememberMeKey())
        .filter(key -> key.hashCode() == authentication.getKeyHash())
        .isPresent();
  }

  @Override
  public Authentication authenticate(Authentication authentication)
      throws AuthenticationException {

    if (!supports(authentication.getClass())) {
      logger.debug(String.format("Authentication [%s] is not supported.",
          authentication.getClass().getName()));
      return null;
    }
    if (authentication instanceof RememberMeAuthenticationToken rma) {
      if (!rememberMeKeyMatches(rma)) {
        throw new BadCredentialsException(getMessages().getMessage(
            "RememberMeAuthenticationProvider.incorrectKey",
            "The presented RememberMeAuthenticationToken does not contain the expected key"));
      }
      return rma;
    }

    String name = getName(authentication);
    logger.debug("Authenticating user '" + name + "' ...");
    String password = Optional.ofNullable(authentication.getCredentials())
        .map(String::valueOf)
        .orElseThrow(() -> new BadCredentialsException("Password is required."));
    String username = getEmailToUsernameResolver()
        .getUsernameByEmail(name)
        .orElse(name);
    if (isRefusedUsername(username)) {
      throw new DisabledException(String
          .format("Username '%s' is refused by configuration.", username));
    }

    LdaptiveUserDetails userDetails = getUserDetailsService().loadUserByUsername(username);
    checkPassword(userDetails, password);
    checkAccountControl(userDetails);

    if (nonNull(getTokenConverter())) {
      return getTokenConverter().convert(userDetails);
    }
    return new LdaptiveAuthenticationToken(userDetails);
  }

  /**
   * Determines whether the username is refused by configuration.
   *
   * @param username the username
   * @return {@code true} if the username is refused, otherwise {@code false}
   */
  protected boolean isRefusedUsername(String username) {
    if (isEmpty(username)) {
      return true;
    }
    return Stream.ofNullable(getAuthenticationProperties().getRefusedUsernames())
        .flatMap(Collection::stream)
        .filter(refusedUsername -> !isEmpty(refusedUsername))
        .anyMatch(refusedUsername -> refusedUsername.equalsIgnoreCase(username));
  }

  /**
   * Gets name.
   *
   * @param authentication the authentication
   * @return the name
   */
  protected String getName(Authentication authentication) {
    Object principal = authentication.getPrincipal();
    if (principal instanceof LdaptiveUserDetails ldaptiveUserDetails) {
      return requireNonNullElseGet(ldaptiveUserDetails.getDn(), authentication::getName);
    }
    return authentication.getName();
  }

  /**
   * Gets user details service.
   *
   * @return the user details service
   */
  public LdaptiveUserDetailsService getUserDetailsService() {
    LdaptiveUserDetailsService userDetailsService = new LdaptiveUserDetailsService(
        getAuthenticationProperties(), getApplicationLdaptiveTemplate());
    userDetailsService.setAccountControlEvaluator(getAccountControlEvaluator());
    userDetailsService.setGrantedAuthoritiesMapper(getGrantedAuthoritiesMapper());
    userDetailsService.setRememberMeTokenProvider(getPasswordProvider());
    return userDetailsService;
  }

  /**
   * Determines whether to bind with username and password or to compare the passwords.
   *
   * @return the boolean
   */
  protected boolean isSimpleBindAuthentication() {
    return isNull(getAuthenticationProperties().getPasswordAttribute())
        || getAuthenticationProperties().getPasswordAttribute().isBlank();
  }

  /**
   * Check password.
   *
   * @param user the user
   * @param password the password
   */
  protected void checkPassword(LdaptiveUserDetails user, String password) {
    if (isSimpleBindAuthentication()) {
      checkPasswordWithSimpleBind(user, password);
    } else {
      checkPasswordWithCompareRequest(user, password);
    }
  }

  protected void checkPasswordWithCompareRequest(LdaptiveUserDetails user, String password) {
    Assert.notNull(getPasswordEncoder(), "No password encoder is present.");
    boolean matches = getApplicationLdaptiveTemplate().compare(CompareRequest.builder()
        .dn(user.getDn())
        .name(getAuthenticationProperties().getPasswordAttribute())
        .value(getPasswordEncoder().encode(password))
        .build());
    if (!matches) {
      throw new BadCredentialsException("Password doesn't match.");
    }
  }

  protected void checkPasswordWithSimpleBind(LdaptiveUserDetails user, String password) {
    SingleConnectionFactory connectionFactory = getSingleConnectionFactory();
    try {
      connectionFactory.initialize();
      BindOperation bind = getBindOperation(connectionFactory);
      BindResponse response = bind.execute(SimpleBindRequest.builder()
          .dn(user.getDn())
          .password(password)
          .build());
      if (!response.isSuccess()) {
        throw new BadCredentialsException("Password doesn't match.");
      }

    } catch (LdapException ldapException) {
      new DefaultLdaptiveErrorHandler().handleError(ldapException);
    } finally {
      connectionFactory.close();
    }
  }

  SingleConnectionFactory getSingleConnectionFactory() {
    ConnectionConfig connectionConfig = ConnectionConfig
        .copy(getApplicationLdaptiveTemplate().getConnectionFactory().getConnectionConfig());
    connectionConfig.setConnectionInitializers();
    return new SingleConnectionFactory(connectionConfig);
  }

  BindOperation getBindOperation(SingleConnectionFactory cf) {
    return new BindOperation(cf);
  }

  /**
   * Check account control.
   *
   * @param user the user
   */
  protected void checkAccountControl(LdaptiveUserDetails user) {
    if (!user.isEnabled()) {
      throw new DisabledException("Account is disabled.");
    }
    if (!user.isAccountNonLocked()) {
      throw new LockedException("Account is locked.");
    }
    if (!user.isAccountNonExpired()) {
      throw new AccountExpiredException("Account is expired.");
    }
    if (!user.isCredentialsNonExpired()) {
      throw new CredentialsExpiredException("Credentials are expired.");
    }
  }

}
