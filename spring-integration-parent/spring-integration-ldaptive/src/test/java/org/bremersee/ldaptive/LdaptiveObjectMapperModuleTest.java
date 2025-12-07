package org.bremersee.ldaptive;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ldaptive.dn.Dn;

/**
 * The ldative object mapper module test.
 */
class LdaptiveObjectMapperModuleTest {

  private ObjectMapper mapper;

  private SomeLdapEntry someLdapEntry;

  private String someLdapEntryJson;

  /**
   * Sets up.
   */
  @BeforeEach
  void setUp() {
    mapper = new ObjectMapper();
    mapper.registerModule(new LdaptiveObjectMapperModule());
    someLdapEntry = new SomeLdapEntry();
    someLdapEntry.setDn(new Dn("CN=Foo,OU=Bar,dc=example,dc=com"));
    someLdapEntry.setCn("junit");
    someLdapEntryJson = "{\"dn\":\"CN=Foo,OU=Bar,dc=example,dc=com\",\"cn\":\"junit\"}";
  }

  /**
   * Deserialize.
   *
   * @throws JsonProcessingException the json processing exception
   */
  @Test
  void deserialize() throws JsonProcessingException {
    SomeLdapEntry actual = mapper.readValue(someLdapEntryJson, SomeLdapEntry.class);
    assertThat(actual).isEqualTo(someLdapEntry);
  }

  /**
   * Serialize.
   *
   * @throws JsonProcessingException the json processing exception
   */
  @Test
  void serialize() throws JsonProcessingException {
    String actual = mapper.writeValueAsString(someLdapEntry);
    assertThat(actual).isEqualTo(someLdapEntryJson);
  }

  /**
   * The type Some ldap entry.
   */
  @Data
  static class SomeLdapEntry {

    private Dn dn;

    private String cn;
  }

}
