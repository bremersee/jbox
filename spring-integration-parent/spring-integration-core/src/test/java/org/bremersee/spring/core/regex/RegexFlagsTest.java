package org.bremersee.spring.core.regex;

import java.util.regex.Pattern;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * The regex flags test.
 */
@ExtendWith(SoftAssertionsExtension.class)
class RegexFlagsTest {

  /**
   * Email regex from <a href="https://emailregex.com/">emailregex.com</a> (RFC 5322 Official
   * Standard).
   *
   * <p>RFC 6530 is not supported.
   */
  private static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+"
      + "(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*"
      + "|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]"
      + "|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+"
      + "[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}"
      + "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:"
      + "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]"
      + "|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";

  /**
   * To regex flags.
   *
   * @param softly the softly
   */
  @Test
  void toRegexFlags(SoftAssertions softly) {
    RegexFlags none = new RegexFlags();
    softly.assertThat(none.toRegexFlags()).isEmpty();
    softly.assertThat(none.toString()).doesNotContain("true");

    RegexFlags one = new RegexFlags();
    one.setUnixLines(true);
    softly.assertThat(one.toRegexFlags()).isPresent();
    softly.assertThat(one.toString()).contains("true");

    RegexFlags some = one.toBuilder()
        .caseInsensitive(true)
        .unicodeCase(true)
        .build();
    softly.assertThat(some.toRegexFlags())
        .hasValue(Pattern.UNIX_LINES | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
    softly.assertThat(some.toString()).contains("true");

    RegexFlags all = new RegexFlags(
        true,
        true,
        true,
        true,
        true,
        true,
        true,
        true,
        true);
    softly.assertThat(all.toRegexFlags())
        .hasValue(Pattern.UNIX_LINES
            | Pattern.CASE_INSENSITIVE
            | Pattern.COMMENTS
            | Pattern.MULTILINE
            | Pattern.LITERAL
            | Pattern.DOTALL
            | Pattern.UNICODE_CASE
            | Pattern.CANON_EQ
            | Pattern.UNICODE_CHARACTER_CLASS);
    softly.assertThat(all.toString()).doesNotContain("false");
  }

  /**
   * Compile.
   *
   * @param softly the softly
   */
  @Test
  void compile(SoftAssertions softly) {
    Pattern caseInsensitivePattern = RegexFlags.builder().caseInsensitive(true).build()
        .compile(EMAIL_REGEX);
    softly.assertThat(caseInsensitivePattern.matcher("FOO@example.com").matches()).isTrue();

    Pattern pattern = new RegexFlags().compile(EMAIL_REGEX);
    softly.assertThat(pattern.matcher("FOO@example.com").matches()).isFalse();

    Pattern empty = new RegexFlags().compile(null);
    softly.assertThat(empty).isNull();
  }

  /**
   * Is unix lines.
   *
   * @param softly the softly
   */
  @Test
  void isUnixLines(SoftAssertions softly) {
    RegexFlags flags = new RegexFlags();
    flags.setUnixLines(true);
    softly.assertThat(flags.toRegexFlags()).hasValue(Pattern.UNIX_LINES);
    softly.assertThat(flags).isEqualTo(RegexFlags.builder().unixLines(true).build());
  }

  /**
   * Is case-insensitive.
   *
   * @param softly the softly
   */
  @Test
  void isCaseInsensitive(SoftAssertions softly) {
    RegexFlags flags = new RegexFlags();
    flags.setCaseInsensitive(true);
    softly.assertThat(flags.toRegexFlags()).hasValue(Pattern.CASE_INSENSITIVE);
    softly.assertThat(flags).isEqualTo(RegexFlags.builder().caseInsensitive(true).build());
  }

  /**
   * Is comments.
   *
   * @param softly the softly
   */
  @Test
  void isComments(SoftAssertions softly) {
    RegexFlags flags = new RegexFlags();
    flags.setComments(true);
    softly.assertThat(flags.toRegexFlags()).hasValue(Pattern.COMMENTS);
    softly.assertThat(flags).isEqualTo(RegexFlags.builder().comments(true).build());
  }

  /**
   * Is multi line.
   *
   * @param softly the softly
   */
  @Test
  void isMultiLine(SoftAssertions softly) {
    RegexFlags flags = new RegexFlags();
    flags.setMultiLine(true);
    softly.assertThat(flags.toRegexFlags()).hasValue(Pattern.MULTILINE);
    softly.assertThat(flags).isEqualTo(RegexFlags.builder().multiLine(true).build());
  }

  /**
   * Is literal.
   *
   * @param softly the softly
   */
  @Test
  void isLiteral(SoftAssertions softly) {
    RegexFlags flags = new RegexFlags();
    flags.setLiteral(true);
    softly.assertThat(flags.toRegexFlags()).hasValue(Pattern.LITERAL);
    softly.assertThat(flags).isEqualTo(RegexFlags.builder().literal(true).build());
  }

  /**
   * Is dot all.
   *
   * @param softly the softly
   */
  @Test
  void isDotAll(SoftAssertions softly) {
    RegexFlags flags = new RegexFlags();
    flags.setDotAll(true);
    softly.assertThat(flags.toRegexFlags()).hasValue(Pattern.DOTALL);
    softly.assertThat(flags).isEqualTo(RegexFlags.builder().dotAll(true).build());
  }

  /**
   * Is unicode-case.
   *
   * @param softly the softly
   */
  @Test
  void isUnicodeCase(SoftAssertions softly) {
    RegexFlags flags = new RegexFlags();
    flags.setUnicodeCase(true);
    softly.assertThat(flags.toRegexFlags()).hasValue(Pattern.UNICODE_CASE);
    softly.assertThat(flags).isEqualTo(RegexFlags.builder().unicodeCase(true).build());
  }

  /**
   * Is canon eq.
   *
   * @param softly the softly
   */
  @Test
  void isCanonEq(SoftAssertions softly) {
    RegexFlags flags = new RegexFlags();
    flags.setCanonEq(true);
    softly.assertThat(flags.toRegexFlags()).hasValue(Pattern.CANON_EQ);
    softly.assertThat(flags).isEqualTo(RegexFlags.builder().canonEq(true).build());
  }

  /**
   * Is unicode-character class.
   *
   * @param softly the softly
   */
  @Test
  void isUnicodeCharacterClass(SoftAssertions softly) {
    RegexFlags flags = new RegexFlags();
    flags.setUnicodeCharacterClass(true);
    softly.assertThat(flags.toRegexFlags()).hasValue(Pattern.UNICODE_CHARACTER_CLASS);
    softly.assertThat(flags).isEqualTo(RegexFlags.builder().unicodeCharacterClass(true).build());
  }

}