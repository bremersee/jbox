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

  private static Version getVersion() {

    int defaultMajor = 6;
    int defaultMinor = 0;
    int defaultPatchLevel = 0;
    String defaultSnapshotInfo = "SNAPSHOT";

    int major = defaultMajor;
    int minor = defaultMinor;
    int patchLevel = defaultPatchLevel;
    String snapshotInfo = defaultSnapshotInfo;

    String version = LdaptiveObjectMapperModule.class.getPackage().getImplementationVersion();
    if (version != null) {
      try {
        int i = version.indexOf('-');
        if (i < 0) {
          snapshotInfo = null;
        } else {
          snapshotInfo = version.substring(i + 1);
          String[] a = version.substring(0, i).split(Pattern.quote("."));
          major = Integer.parseInt(a[0]);
          minor = Integer.parseInt(a[1]);
          patchLevel = Integer.parseInt(a[2]);
        }

      } catch (RuntimeException e) {
        major = defaultMajor;
        minor = defaultMinor;
        snapshotInfo = defaultSnapshotInfo;
      }
    }

    return new Version(major, minor, patchLevel, snapshotInfo, "org.bremersee",
        "spring-integration-ldaptive");
  }

}
