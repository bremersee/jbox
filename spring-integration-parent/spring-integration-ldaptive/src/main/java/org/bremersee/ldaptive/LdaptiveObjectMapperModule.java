package org.bremersee.ldaptive;

import java.io.Serial;
import java.util.regex.Pattern;
import org.bremersee.ldaptive.converter.JacksonDnDeserializer;
import org.bremersee.ldaptive.converter.JacksonDnSerializer;
import org.bremersee.ldaptive.converter.JacksonLdapEntryDeserializer;
import org.bremersee.ldaptive.converter.JacksonLdapEntrySerializer;
import org.ldaptive.LdapEntry;
import org.ldaptive.dn.Dn;
import tools.jackson.core.Version;
import tools.jackson.databind.module.SimpleModule;

/**
 * The ldaptive object mapper module.
 */
public class LdaptiveObjectMapperModule extends SimpleModule {

  @Serial
  private static final long serialVersionUID = 1L;

  private static final String VERSION = "6.1.7";

  /**
   * The constant TYPE_ID.
   */
  public static final String TYPE_ID = LdaptiveObjectMapperModule.class.getName();

  /**
   * Instantiates a new ldaptive object mapper module.
   */
  public LdaptiveObjectMapperModule() {
    super(TYPE_ID, getVersion());
    addDeserializer(Dn.class, new JacksonDnDeserializer());
    addDeserializer(LdapEntry.class, new JacksonLdapEntryDeserializer());
    addSerializer(Dn.class, new JacksonDnSerializer());
    addSerializer(LdapEntry.class, new JacksonLdapEntrySerializer());
  }

  private static Version getVersion(String version) {
    try {
      String snapshotInfo;
      int i = version.indexOf('-');
      if (i > 0) {
        snapshotInfo = version.substring(i + 1);
      } else {
        snapshotInfo = null;
        i = version.length();
      }
      String[] a = version.substring(0, i).split(Pattern.quote("."));
      int major = Integer.parseInt(a[0]);
      int minor = Integer.parseInt(a[1]);
      int patchLevel = Integer.parseInt(a[2]);
      return new Version(major, minor, patchLevel, snapshotInfo, "org.bremersee",
          "spring-integration-ldaptive");

    } catch (RuntimeException e) {
      return getVersion(VERSION);
    }
  }

  private static Version getVersion() {
    String version = LdaptiveObjectMapperModule.class.getPackage().getImplementationVersion();
    return getVersion(version);
  }

}
