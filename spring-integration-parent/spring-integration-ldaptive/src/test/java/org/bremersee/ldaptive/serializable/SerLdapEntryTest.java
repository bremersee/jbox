package org.bremersee.ldaptive.serializable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.bremersee.ldaptive.LdaptiveObjectMapperModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.ldaptive.LdapAttribute;
import org.ldaptive.LdapEntry;

/**
 * The serializable ldap entry test.
 */
@ExtendWith({SoftAssertionsExtension.class})
class SerLdapEntryTest {

  private static ObjectMapper objectMapper;

  /**
   * Init object mapper.
   */
  @BeforeAll
  static void initObjectMapper() {
    objectMapper = new ObjectMapper();
    objectMapper.registerModule(new LdaptiveObjectMapperModule());
  }

  /**
   * Gets dn.
   *
   * @param softly the softly
   */
  @Test
  void getDn(SoftAssertions softly) {
    SerLdapEntry e0 = new SerLdapEntry(null);
    SerLdapEntry e1 = new SerLdapEntry(null);
    softly
        .assertThat(e0)
        .isEqualTo(e1);
    softly
        .assertThat(e0.hashCode())
        .isEqualTo(e1.hashCode());
    softly
        .assertThat(e0.toString())
        .contains("null");
    softly
        .assertThat(e0.getDn())
        .isNull();

    LdapEntry le0 = new LdapEntry();
    le0.setDn("dc=junit");
    e0 = new SerLdapEntry(le0);
    e1 = new SerLdapEntry(le0);
    softly
        .assertThat(e0)
        .isEqualTo(e1);
    softly
        .assertThat(e0.hashCode())
        .isEqualTo(e1.hashCode());
    softly
        .assertThat(e0.toString())
        .contains("dc=junit");
    softly
        .assertThat(e0.getDn())
        .isEqualTo("dc=junit");
  }

  /**
   * Gets attributes.
   *
   * @param softly the softly
   */
  @Test
  void getAttributes(SoftAssertions softly) {
    SerLdapEntry e0 = new SerLdapEntry(null);
    softly
        .assertThat(e0.getAttributes())
        .isEmpty();

    LdapAttribute la0 = new LdapAttribute("say", " Hello world!");
    LdapEntry le0 = new LdapEntry();
    le0.setDn("dc=junit");
    le0.addAttributes(la0);
    e0 = new SerLdapEntry(le0);
    softly
        .assertThat(e0.getAttributes())
        .containsExactly((new SerLdapAttr(la0)));

    softly
        .assertThat(e0.findAttribute("SAY"))
        .hasValue((new SerLdapAttr(la0)));

    LdapEntry le1 = e0.toLdapEntry();
    softly
        .assertThat(le1)
        .isEqualTo(le0);
  }

  /**
   * Json.
   *
   * @param softly the softly
   * @throws JsonProcessingException the json processing exception
   */
  @Test
  void json(SoftAssertions softly) throws JsonProcessingException {
    LdapAttribute la0 = new LdapAttribute("say", "Hello world!", "How are you?");
    LdapAttribute la1 = new LdapAttribute("bin");
    la1.setBinary(true);
    la1.addBinaryValues("I'm a jpeg photo ;-)".getBytes(StandardCharsets.UTF_8));
    LdapEntry le0 = new LdapEntry();
    le0.setDn("dc=junit");
    le0.addAttributes(la0, la1);

    String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(le0);
    LdapEntry readEntry = objectMapper.readValue(json, LdapEntry.class);
    softly
        .assertThat(readEntry)
        .isEqualTo(le0);

    SerLdapEntry expected = new SerLdapEntry(le0);
    json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(expected);
    SerLdapEntry actual = objectMapper.readValue(json, SerLdapEntry.class);
    softly
        .assertThat(actual).isEqualTo(expected);
  }

}