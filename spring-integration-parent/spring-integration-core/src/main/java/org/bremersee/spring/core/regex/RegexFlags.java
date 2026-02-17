package org.bremersee.spring.core.regex;

import static java.util.Objects.isNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.Optional;
import java.util.regex.Pattern;
import lombok.Builder;
import lombok.Data;

/**
 * The regex flags.
 */
@Data
public class RegexFlags implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * Flag unix lines.
   */
  private boolean unixLines;

  /**
   * Flag case-insensitive.
   */
  private boolean caseInsensitive;

  /**
   * Flag comments.
   */
  private boolean comments;

  /**
   * Flag multi-line.
   */
  private boolean multiLine;

  /**
   * Flag literal.
   */
  private boolean literal;

  /**
   * Flag dot-all.
   */
  private boolean dotAll;

  /**
   * Flag unicode-case.
   */
  private boolean unicodeCase;

  /**
   * Flag canon-eq.
   */
  private boolean canonEq;

  /**
   * Flag unicode-character class
   */
  private boolean unicodeCharacterClass;

  /**
   * Instantiates new regex flags.
   */
  public RegexFlags() {
    super();
  }

  /**
   * Instantiates new regex flags.
   *
   * @param unixLines the unix lines flag
   * @param caseInsensitive the case-insensitive flag
   * @param comments the comments flag
   * @param multiLine the multi line flag
   * @param literal the literal flag
   * @param dotAll the dot all flag
   * @param unicodeCase the unicode-case flag
   * @param canonEq the canon eq flag
   * @param unicodeCharacterClass the unicode-character class flag
   */
  @Builder(toBuilder = true)
  RegexFlags(boolean unixLines, boolean caseInsensitive, boolean comments, boolean multiLine,
      boolean literal, boolean dotAll, boolean unicodeCase, boolean canonEq,
      boolean unicodeCharacterClass) {
    this.unixLines = unixLines;
    this.caseInsensitive = caseInsensitive;
    this.comments = comments;
    this.multiLine = multiLine;
    this.literal = literal;
    this.dotAll = dotAll;
    this.unicodeCase = unicodeCase;
    this.canonEq = canonEq;
    this.unicodeCharacterClass = unicodeCharacterClass;
  }

  /**
   * To regex flags.
   *
   * @return the optional
   */
  public Optional<Integer> toRegexFlags() {
    int flags = 0;
    if (isUnixLines()) {
      flags = Pattern.UNIX_LINES;
    }
    if (isCaseInsensitive()) {
      flags = flags | Pattern.CASE_INSENSITIVE;
    }
    if (isComments()) {
      flags = flags | Pattern.COMMENTS;
    }
    if (isMultiLine()) {
      flags = flags | Pattern.MULTILINE;
    }
    if (isLiteral()) {
      flags = flags | Pattern.LITERAL;
    }
    if (isDotAll()) {
      flags = flags | Pattern.DOTALL;
    }
    if (isUnicodeCase()) {
      flags = flags | Pattern.UNICODE_CASE;
    }
    if (isCanonEq()) {
      flags = flags | Pattern.CANON_EQ;
    }
    if (isUnicodeCharacterClass()) {
      flags = flags | Pattern.UNICODE_CHARACTER_CLASS;
    }
    return Optional.of(flags)
        .filter(v -> v > 0);
  }

  /**
   * Compile pattern.
   *
   * @param regex the regex
   * @return the pattern
   */
  @SuppressWarnings("MagicConstant")
  public Pattern compile(String regex) {
    if (isNull(regex)) {
      return null;
    }
    return toRegexFlags()
        .map(flags -> Pattern.compile(regex, flags))
        .orElseGet(() -> Pattern.compile(regex));
  }

}
