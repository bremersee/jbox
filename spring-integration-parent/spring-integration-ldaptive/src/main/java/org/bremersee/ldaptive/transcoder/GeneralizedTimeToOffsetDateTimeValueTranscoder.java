package org.bremersee.ldaptive.transcoder;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Optional;
import org.ldaptive.transcode.AbstractStringValueTranscoder;
import org.ldaptive.transcode.GeneralizedTimeValueTranscoder;

/**
 * The generalized time value transcoder.
 *
 * @author Christian Bremer
 */
public class GeneralizedTimeToOffsetDateTimeValueTranscoder
    extends AbstractStringValueTranscoder<OffsetDateTime> {

  private static final GeneralizedTimeValueTranscoder transcoder
      = new GeneralizedTimeValueTranscoder();

  /**
   * Instantiates a new generalized time to offset date time value transcoder.
   */
  public GeneralizedTimeToOffsetDateTimeValueTranscoder() {
    super();
  }

  @Override
  public OffsetDateTime decodeStringValue(String value) {
    return Optional.ofNullable(value)
        .filter(timeStr -> !timeStr.isEmpty())
        .map(transcoder::decodeStringValue)
        .map(ZonedDateTime::toOffsetDateTime)
        .orElse(null);
  }

  @Override
  public String encodeStringValue(OffsetDateTime value) {
    return Optional.ofNullable(value)
        .map(OffsetDateTime::toZonedDateTime)
        .map(transcoder::encodeStringValue)
        .orElse(null);
  }

  @Override
  public Class<OffsetDateTime> getType() {
    return OffsetDateTime.class;
  }
}
