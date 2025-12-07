package org.bremersee.ldaptive.transcoder;

import static java.util.Objects.isNull;

import java.util.Objects;
import org.ldaptive.dn.DefaultRDnNormalizer;
import org.ldaptive.dn.Dn;
import org.ldaptive.dn.RDnNormalizer;
import org.ldaptive.transcode.AbstractStringValueTranscoder;

/**
 * The distinguished name value transcoder.
 */
public class DnValueTranscoder extends AbstractStringValueTranscoder<Dn> {

  private final RDnNormalizer rdnNormalizer;

  /**
   * Instantiates a new distinguished name value transcoder.
   */
  public DnValueTranscoder() {
    this(new DefaultRDnNormalizer());
  }

  /**
   * Instantiates a new distinguished name value transcoder.
   *
   * @param rdnNormalizer the rdn normalizer
   */
  public DnValueTranscoder(RDnNormalizer rdnNormalizer) {
    this.rdnNormalizer = Objects.requireNonNullElseGet(rdnNormalizer, DefaultRDnNormalizer::new);
  }

  @Override
  public Dn decodeStringValue(String dn) {
    if (isNull(dn) || dn.trim().isEmpty()) {
      return null;
    }
    return new Dn(dn.trim());
  }

  @Override
  public String encodeStringValue(Dn dn) {
    if (isNull(dn) || dn.isEmpty()) {
      return null;
    }
    return dn.format(rdnNormalizer);
  }

  @Override
  public Class<Dn> getType() {
    return Dn.class;
  }
}
