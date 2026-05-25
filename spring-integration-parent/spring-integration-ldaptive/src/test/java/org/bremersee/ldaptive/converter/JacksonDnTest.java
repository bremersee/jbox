package org.bremersee.ldaptive.converter;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ldaptive.dn.Dn;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.json.JsonMapper;

/**
 * The jackson distinguished name test.
 */
class JacksonDnTest {

  private JsonMapper mapper;

  private SomeLdapEntry someLdapEntry;

  private String someLdapEntryJson;

  /**
   * Sets up.
   */
  @BeforeEach
  void setUp() {
    mapper = JsonMapper.builder().build();
    someLdapEntry = new SomeLdapEntry();
    someLdapEntry.setDn(new Dn("CN=Foo,OU=Bar,dc=example,dc=com"));
    someLdapEntry.setCn("junit");
    someLdapEntryJson = "{\"cn\":\"junit\",\"dn\":\"CN=Foo,OU=Bar,dc=example,dc=com\"}";
  }

  /**
   * Deserialize.
   */
  @Test
  void deserialize() {
    SomeLdapEntry actual = mapper.readValue(someLdapEntryJson, SomeLdapEntry.class);
    assertThat(actual).isEqualTo(someLdapEntry);
  }

  /**
   * Serialize.
   */
  @Test
  void serialize() {
    String actual = mapper.writeValueAsString(someLdapEntry);
    assertThat(actual).isEqualTo(someLdapEntryJson);
  }

  /**
   * The type Some ldap entry.
   */
  @Data
  @JsonPropertyOrder(alphabetic = true)
  static class SomeLdapEntry {

    @JsonDeserialize(using = JacksonDnDeserializer.class)
    @JsonSerialize(using = JacksonDnSerializer.class)
    private Dn dn;

    private String cn;
  }

}
