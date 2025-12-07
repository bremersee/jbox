package org.bremersee.ldaptive.transcoder;

import org.ldaptive.ad.transcode.DeltaTimeValueTranscoder;
import org.ldaptive.ad.transcode.FileTimeValueTranscoder;
import org.ldaptive.ad.transcode.UnicodePwdValueTranscoder;
import org.ldaptive.schema.transcode.AttributeTypeValueTranscoder;
import org.ldaptive.transcode.BigIntegerValueTranscoder;
import org.ldaptive.transcode.BooleanValueTranscoder;
import org.ldaptive.transcode.ByteArrayValueTranscoder;
import org.ldaptive.transcode.DoubleValueTranscoder;
import org.ldaptive.transcode.FloatValueTranscoder;
import org.ldaptive.transcode.GeneralizedTimeValueTranscoder;
import org.ldaptive.transcode.IntegerValueTranscoder;
import org.ldaptive.transcode.LongValueTranscoder;
import org.ldaptive.transcode.ShortValueTranscoder;
import org.ldaptive.transcode.StringValueTranscoder;
import org.ldaptive.transcode.UUIDValueTranscoder;

/**
 * The value transcoder factory.
 */
public abstract class ValueTranscoderFactory {

  private static AttributeTypeValueTranscoder attributeTypeValueTranscoder;

  private static BigIntegerValueTranscoder bigIntegerValueTranscoder;

  private static BooleanValueTranscoder booleanValueTranscoder;

  private static BooleanValueTranscoder booleanPrimitiveValueTranscoder;

  private static ByteArrayValueTranscoder byteArrayValueTranscoder;

  private static DeltaTimeValueTranscoder deltaTimeValueTranscoder;

  private static DoubleValueTranscoder doubleValueTranscoder;

  private static DoubleValueTranscoder doublePrimitiveValueTranscoder;

  private static FileTimeValueTranscoder fileTimeValueTranscoder;

  private static FloatValueTranscoder floatValueTranscoder;

  private static FloatValueTranscoder floatPrimitiveValueTranscoder;

  private static GeneralizedTimeValueTranscoder generalizedTimeValueTranscoder;

  private static IntegerValueTranscoder integerValueTranscoder;

  private static IntegerValueTranscoder integerPrimitiveValueTranscoder;

  private static LongValueTranscoder longValueTranscoder;

  private static LongValueTranscoder longValuePrimitiveTranscoder;

  private static ShortValueTranscoder shortValueTranscoder;

  private static ShortValueTranscoder shortPrimitiveValueTranscoder;

  private static StringValueTranscoder stringValueTranscoder;

  private static UserAccountControlValueTranscoder userAccountControlValueTranscoder;

  private static UUIDValueTranscoder uuidValueTranscoder;

  private static UnicodePwdValueTranscoder unicodePwdValueTranscoder;

  /**
   * Instantiates a new value transcoder factory.
   */
  private ValueTranscoderFactory() {
    super();
  }

  /**
   * Gets attribute type value transcoder.
   *
   * @return the attribute type value transcoder
   */
  public static AttributeTypeValueTranscoder getAttributeTypeValueTranscoder() {
    if (attributeTypeValueTranscoder == null) {
      attributeTypeValueTranscoder = new AttributeTypeValueTranscoder();
    }
    return attributeTypeValueTranscoder;
  }

  /**
   * Gets big integer value transcoder.
   *
   * @return the big integer value transcoder
   */
  public static BigIntegerValueTranscoder getBigIntegerValueTranscoder() {
    if (bigIntegerValueTranscoder == null) {
      bigIntegerValueTranscoder = new BigIntegerValueTranscoder();
    }
    return bigIntegerValueTranscoder;
  }

  /**
   * Gets boolean value transcoder.
   *
   * @return the boolean value transcoder
   */
  public static BooleanValueTranscoder getBooleanValueTranscoder() {
    if (booleanValueTranscoder == null) {
      booleanValueTranscoder = new BooleanValueTranscoder(false);
    }
    return booleanValueTranscoder;
  }

  /**
   * Gets boolean primitive value transcoder.
   *
   * @return the boolean primitive value transcoder
   */
  public static BooleanValueTranscoder getBooleanPrimitiveValueTranscoder() {
    if (booleanPrimitiveValueTranscoder == null) {
      booleanPrimitiveValueTranscoder = new BooleanValueTranscoder(true);
    }
    return booleanPrimitiveValueTranscoder;
  }

  /**
   * Gets byte array value transcoder.
   *
   * @return the byte array value transcoder
   */
  public static ByteArrayValueTranscoder getByteArrayValueTranscoder() {
    if (byteArrayValueTranscoder == null) {
      byteArrayValueTranscoder = new ByteArrayValueTranscoder();
    }
    return byteArrayValueTranscoder;
  }

  /**
   * Gets delta time value transcoder.
   *
   * @return the delta time value transcoder
   */
  public static DeltaTimeValueTranscoder getDeltaTimeValueTranscoder() {
    if (deltaTimeValueTranscoder == null) {
      deltaTimeValueTranscoder = new DeltaTimeValueTranscoder();
    }
    return deltaTimeValueTranscoder;
  }

  /**
   * Gets double value transcoder.
   *
   * @return the double value transcoder
   */
  public static DoubleValueTranscoder getDoubleValueTranscoder() {
    if (doubleValueTranscoder == null) {
      doubleValueTranscoder = new DoubleValueTranscoder(false);
    }
    return doubleValueTranscoder;
  }

  /**
   * Gets double primitive value transcoder.
   *
   * @return the double primitive value transcoder
   */
  public static DoubleValueTranscoder getDoublePrimitiveValueTranscoder() {
    if (doublePrimitiveValueTranscoder == null) {
      doublePrimitiveValueTranscoder = new DoubleValueTranscoder(true);
    }
    return doublePrimitiveValueTranscoder;
  }

  /**
   * Gets file time value transcoder.
   *
   * @return the file time value transcoder
   */
  public static FileTimeValueTranscoder getFileTimeValueTranscoder() {
    if (fileTimeValueTranscoder == null) {
      fileTimeValueTranscoder = new FileTimeValueTranscoder();
    }
    return fileTimeValueTranscoder;
  }

  /**
   * Gets float value transcoder.
   *
   * @return the float value transcoder
   */
  public static FloatValueTranscoder getFloatValueTranscoder() {
    if (floatValueTranscoder == null) {
      floatValueTranscoder = new FloatValueTranscoder(false);
    }
    return floatValueTranscoder;
  }

  /**
   * Gets float primitive value transcoder.
   *
   * @return the float primitive value transcoder
   */
  public static FloatValueTranscoder getFloatPrimitiveValueTranscoder() {
    if (floatPrimitiveValueTranscoder == null) {
      floatPrimitiveValueTranscoder = new FloatValueTranscoder(true);
    }
    return floatPrimitiveValueTranscoder;
  }

  /**
   * Gets generalized time value transcoder.
   *
   * @return the generalized time value transcoder
   */
  public static GeneralizedTimeValueTranscoder getGeneralizedTimeValueTranscoder() {
    if (generalizedTimeValueTranscoder == null) {
      generalizedTimeValueTranscoder = new GeneralizedTimeValueTranscoder();
    }
    return generalizedTimeValueTranscoder;
  }

  /**
   * Gets integer value transcoder.
   *
   * @return the integer value transcoder
   */
  public static IntegerValueTranscoder getIntegerValueTranscoder() {
    if (integerValueTranscoder == null) {
      integerValueTranscoder = new IntegerValueTranscoder(false);
    }
    return integerValueTranscoder;
  }

  /**
   * Gets integer primitive value transcoder.
   *
   * @return the integer primitive value transcoder
   */
  public static IntegerValueTranscoder getIntegerPrimitiveValueTranscoder() {
    if (integerPrimitiveValueTranscoder == null) {
      integerPrimitiveValueTranscoder = new IntegerValueTranscoder(true);
    }
    return integerPrimitiveValueTranscoder;
  }

  /**
   * Gets long value transcoder.
   *
   * @return the long value transcoder
   */
  public static LongValueTranscoder getLongValueTranscoder() {
    if (longValueTranscoder == null) {
      longValueTranscoder = new LongValueTranscoder(false);
    }
    return longValueTranscoder;
  }

  /**
   * Gets long primitive value transcoder.
   *
   * @return the long primitive value transcoder
   */
  public static LongValueTranscoder getLongPrimitiveValueTranscoder() {
    if (longValuePrimitiveTranscoder == null) {
      longValuePrimitiveTranscoder = new LongValueTranscoder(true);
    }
    return longValuePrimitiveTranscoder;
  }

  /**
   * Gets short value transcoder.
   *
   * @return the short value transcoder
   */
  public static ShortValueTranscoder getShortValueTranscoder() {
    if (shortValueTranscoder == null) {
      shortValueTranscoder = new ShortValueTranscoder(false);
    }
    return shortValueTranscoder;
  }

  /**
   * Gets short primitive value transcoder.
   *
   * @return the short primitive value transcoder
   */
  public static ShortValueTranscoder getShortPrimitiveValueTranscoder() {
    if (shortPrimitiveValueTranscoder == null) {
      shortPrimitiveValueTranscoder = new ShortValueTranscoder(true);
    }
    return shortPrimitiveValueTranscoder;
  }

  /**
   * Gets string value transcoder.
   *
   * @return the string value transcoder
   */
  public static StringValueTranscoder getStringValueTranscoder() {
    if (stringValueTranscoder == null) {
      stringValueTranscoder = new StringValueTranscoder();
    }
    return stringValueTranscoder;
  }

  /**
   * Gets user account control value transcoder.
   *
   * @return the user account control value transcoder
   */
  public static UserAccountControlValueTranscoder getUserAccountControlValueTranscoder() {
    if (userAccountControlValueTranscoder == null) {
      userAccountControlValueTranscoder = new UserAccountControlValueTranscoder();
    }
    return userAccountControlValueTranscoder;
  }

  /**
   * Gets uuid value transcoder.
   *
   * @return the uuid value transcoder
   */
  public static UUIDValueTranscoder getUuidValueTranscoder() {
    if (uuidValueTranscoder == null) {
      uuidValueTranscoder = new UUIDValueTranscoder();
    }
    return uuidValueTranscoder;
  }

  /**
   * Gets unicode pwd value transcoder.
   *
   * @return the unicode pwd value transcoder
   */
  public static UnicodePwdValueTranscoder getUnicodePwdValueTranscoder() {
    if (unicodePwdValueTranscoder == null) {
      unicodePwdValueTranscoder = new UnicodePwdValueTranscoder();
    }
    return unicodePwdValueTranscoder;
  }

}
