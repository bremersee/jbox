package org.bremersee.ldaptive;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.util.UUID;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;
import org.ldaptive.BindRequest;
import org.ldaptive.ConnectionFactory;
import org.ldaptive.ModifyRequest;

/**
 * The ldaptive samba template test.
 */
class LdaptiveSambaTemplateTest {

  /**
   * The Connection factory.
   */
  ConnectionFactory connectionFactory = mock(ConnectionFactory.class);
  /**
   * The Target.
   */
  LdaptiveSambaTemplate target = spy(new LdaptiveSambaTemplate(connectionFactory));

  /**
   * Sets password generator.
   */
  @Test
  void setPasswordGenerator() {
    Supplier<String> generator = () -> UUID.randomUUID().toString();
    assertThatNoException()
        .isThrownBy(() -> target.setPasswordGenerator(generator));
  }

  /**
   * Clone template.
   */
  @Test
  void copyTemplate() {
    assertThat(target.copy())
        .isNotNull();
  }

  /**
   * Generate user password.
   */
  @Test
  void generateUserPassword() {
    configureClonedLdaptiveSambaTemplate();
    String newPass = target.generateUserPassword("cn=foobar,cn=users,dc=example,dc=org");
    assertThat(newPass)
        .isNotEmpty();
  }

  /**
   * Modify user password.
   */
  @Test
  void modifyUserPassword() {
    configureClonedLdaptiveSambaTemplate();
    doReturn(true).when(target).bind(any(BindRequest.class));
    String dn = "cn=foobar,cn=users,dc=example,dc=org";
    String oldPwd = "old";
    String newPwd = "new";
    assertThatNoException()
        .isThrownBy(() -> target.modifyUserPassword(dn, oldPwd, newPwd));
  }

  /**
   * Modify user password with invalid old password.
   */
  @Test
  void modifyUserPasswordWithInvalidOldPassword() {
    doReturn(false).when(target).bind(any(BindRequest.class));
    assertThatExceptionOfType(LdaptiveException.class)
        .isThrownBy(() -> target
            .modifyUserPassword("cn=foobar,cn=users,dc=example,dc=org", "old", "new"));
  }

  private void configureClonedLdaptiveSambaTemplate() {
    LdaptiveSambaTemplate cloned = spy(new LdaptiveSambaTemplate(connectionFactory));
    doNothing().when(cloned).modify(any(ModifyRequest.class));
    doAnswer(invocation -> {
      cloned.setErrorHandler(invocation.getArgument(0));
      return cloned;
    }).when(target).copy(any(LdaptiveErrorHandler.class));
  }

}