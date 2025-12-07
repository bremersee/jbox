package org.bremersee.ldaptive.transcoder;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * The user account control test.
 */
@ExtendWith(SoftAssertionsExtension.class)
class UserAccountControlTest {

  private UserAccountControl normalEnabledNoPassExpiration;

  private UserAccountControl normalDisabledNoPassExpiration;

  private UserAccountControl normalEnabledPassExpiration;

  private UserAccountControl normalDisabledPassExpiration;

  private UserAccountControl specialEnabledNoPassExpiration;

  private UserAccountControl specialDisabledNoPassExpiration;

  private UserAccountControl specialEnabledPassExpiration;

  private UserAccountControl specialDisabledPassExpiration;

  /**
   * Sets up.
   */
  @BeforeEach
  void setUp() {
    normalEnabledNoPassExpiration = new UserAccountControl();

    normalDisabledNoPassExpiration = new UserAccountControl();
    normalDisabledNoPassExpiration.setEnabled(false);

    normalEnabledPassExpiration = new UserAccountControl();
    normalEnabledPassExpiration.setPasswordExpirationEnabled(true);

    normalDisabledPassExpiration = new UserAccountControl();
    normalDisabledPassExpiration.setEnabled(false);
    normalDisabledPassExpiration.setPasswordExpirationEnabled(true);

    specialEnabledNoPassExpiration = new UserAccountControl();
    specialEnabledNoPassExpiration.setNormalAccount(false);

    specialDisabledNoPassExpiration = new UserAccountControl();
    specialDisabledNoPassExpiration.setNormalAccount(false);
    specialDisabledNoPassExpiration.setEnabled(false);

    specialEnabledPassExpiration = new UserAccountControl();
    specialEnabledPassExpiration.setNormalAccount(false);
    specialEnabledPassExpiration.setPasswordExpirationEnabled(true);

    specialDisabledPassExpiration = new UserAccountControl();
    specialDisabledPassExpiration.setNormalAccount(false);
    specialDisabledPassExpiration.setEnabled(false);
    specialDisabledPassExpiration.setPasswordExpirationEnabled(true);
  }

  /**
   * Is normal account.
   *
   * @param softly the softly
   */
  @Test
  void isNormalAccount(SoftAssertions softly) {
    softly.assertThat(normalEnabledNoPassExpiration.isNormalAccount()).isTrue();
    softly.assertThat(normalDisabledNoPassExpiration.isNormalAccount()).isTrue();
    softly.assertThat(normalEnabledPassExpiration.isNormalAccount()).isTrue();
    softly.assertThat(normalDisabledPassExpiration.isNormalAccount()).isTrue();

    softly.assertThat(specialDisabledNoPassExpiration.isNormalAccount()).isFalse();
    softly.assertThat(specialEnabledNoPassExpiration.isNormalAccount()).isFalse();
    softly.assertThat(specialEnabledPassExpiration.isNormalAccount()).isFalse();
    softly.assertThat(specialDisabledPassExpiration.isNormalAccount()).isFalse();
  }

  /**
   * Sets normal account.
   */
  @Test
  void setNormalAccount() {
    specialEnabledNoPassExpiration.setNormalAccount(true);
    assertThat(specialEnabledNoPassExpiration).isEqualTo(normalEnabledNoPassExpiration);
  }

  /**
   * Is enabled.
   *
   * @param softly the softly
   */
  @Test
  void isEnabled(SoftAssertions softly) {
    softly.assertThat(normalEnabledNoPassExpiration.isEnabled()).isTrue();
    softly.assertThat(normalDisabledNoPassExpiration.isEnabled()).isFalse();
    softly.assertThat(normalEnabledPassExpiration.isEnabled()).isTrue();
    softly.assertThat(normalDisabledPassExpiration.isEnabled()).isFalse();

    softly.assertThat(specialDisabledNoPassExpiration.isEnabled()).isFalse();
    softly.assertThat(specialEnabledNoPassExpiration.isEnabled()).isTrue();
    softly.assertThat(specialEnabledPassExpiration.isEnabled()).isTrue();
    softly.assertThat(specialDisabledPassExpiration.isEnabled()).isFalse();
  }

  /**
   * Sets enabled.
   */
  @Test
  void setEnabled() {
    normalDisabledNoPassExpiration.setEnabled(true);
    assertThat(normalDisabledNoPassExpiration).isEqualTo(normalEnabledNoPassExpiration);
  }

  /**
   * Is password expiration enabled.
   *
   * @param softly the softly
   */
  @Test
  void isPasswordExpirationEnabled(SoftAssertions softly) {
    softly.assertThat(normalEnabledNoPassExpiration.isPasswordExpirationEnabled()).isFalse();
    softly.assertThat(normalDisabledNoPassExpiration.isPasswordExpirationEnabled()).isFalse();
    softly.assertThat(normalEnabledPassExpiration.isPasswordExpirationEnabled()).isTrue();
    softly.assertThat(normalDisabledPassExpiration.isPasswordExpirationEnabled()).isTrue();

    softly.assertThat(specialDisabledNoPassExpiration.isPasswordExpirationEnabled()).isFalse();
    softly.assertThat(specialEnabledNoPassExpiration.isPasswordExpirationEnabled()).isFalse();
    softly.assertThat(specialEnabledPassExpiration.isPasswordExpirationEnabled()).isTrue();
    softly.assertThat(specialDisabledPassExpiration.isPasswordExpirationEnabled()).isTrue();
  }

  /**
   * Sets password expiration enabled.
   */
  @Test
  void setPasswordExpirationEnabled() {
    normalEnabledPassExpiration.setPasswordExpirationEnabled(false);
    assertThat(normalEnabledPassExpiration).isEqualTo(normalEnabledNoPassExpiration);
  }

  /**
   * Gets value.
   *
   * @param softly the softly
   */
  @Test
  void getValue(SoftAssertions softly) {
    softly.assertThat(normalEnabledNoPassExpiration.getValue()).isEqualTo(66048);
    softly.assertThat(normalDisabledNoPassExpiration.getValue()).isEqualTo(66050);
    softly.assertThat(normalEnabledPassExpiration.getValue()).isEqualTo(512);
    softly.assertThat(normalDisabledPassExpiration.getValue()).isEqualTo(514);

    softly.assertThat(specialDisabledNoPassExpiration.getValue()).isEqualTo(65538);
    softly.assertThat(specialEnabledNoPassExpiration.getValue()).isEqualTo(65536);
    softly.assertThat(specialEnabledPassExpiration.getValue()).isEqualTo(0);
    softly.assertThat(specialDisabledPassExpiration.getValue()).isEqualTo(2);
  }

  /**
   * Test to string.
   */
  @Test
  void testToString() {
    assertThat(normalEnabledNoPassExpiration.toString()).contains("66048");
  }
}