/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.bremersee.minio;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.minio.GetObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import io.minio.messages.Item;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;

/**
 * The minio multipart file implementation test.
 *
 * @author Christian Bremer
 */
class MinioMultipartFileImplTest {

  private static final String BUCKET = UUID.randomUUID().toString();

  private static final String NAME = UUID.randomUUID().toString();

  private static final String VERSION_ID = UUID.randomUUID().toString();

  private static final ZonedDateTime TIME = ZonedDateTime.now();

  private static final String ETAG = UUID.randomUUID().toString();

  private static final MinioOperations MINIO_OPERATIONS = mock(MinioOperations.class);

  private static final StatObjectResponse STAT_OBJECT_RESPONSE = mock(StatObjectResponse.class);

  private static final StatObjectResponse OBJECT_STAT = mock(StatObjectResponse.class);

  private static final byte[] CONTENT = "Hello".getBytes(StandardCharsets.UTF_8);

  private static final MinioObjectInfo OBJECT_INFO = new MinioObjectInfo() {
    @Override
    public String getBucket() {
      return BUCKET;
    }

    @Override
    public String getRegion() {
      return null;
    }

    @Override
    public String getEtag() {
      return ETAG;
    }

    @Override
    public OffsetDateTime getLastModified() {
      return OffsetDateTime.ofInstant(TIME.toInstant(), ZoneOffset.UTC);
    }

    @Override
    public String getName() {
      return NAME;
    }

    @Override
    public String getVersionId() {
      return VERSION_ID;
    }
  };

  /**
   * Sets up.
   */
  @BeforeAll
  static void setUp() {
    when(STAT_OBJECT_RESPONSE.object()).thenReturn(NAME);
    when(STAT_OBJECT_RESPONSE.bucket()).thenReturn(BUCKET);
    when(STAT_OBJECT_RESPONSE.contentType()).thenReturn(MediaType.TEXT_PLAIN_VALUE);
    when(STAT_OBJECT_RESPONSE.lastModified()).thenReturn(TIME);
    when(STAT_OBJECT_RESPONSE.etag()).thenReturn(ETAG);
    when(STAT_OBJECT_RESPONSE.size()).thenReturn(0L);
    when(STAT_OBJECT_RESPONSE.versionId()).thenReturn(VERSION_ID);

    when(OBJECT_STAT.object()).thenReturn(NAME);
    when(OBJECT_STAT.bucket()).thenReturn(BUCKET);
    when(OBJECT_STAT.contentType()).thenReturn(MediaType.TEXT_PLAIN_VALUE);
    when(OBJECT_STAT.lastModified()).thenReturn(TIME);
    when(OBJECT_STAT.etag()).thenReturn(ETAG);
    when(OBJECT_STAT.size()).thenReturn((long) CONTENT.length);
    when(OBJECT_STAT.versionId()).thenReturn(VERSION_ID);

    when(MINIO_OPERATIONS.statObject(any(StatObjectArgs.class))).thenReturn(OBJECT_STAT);
    when(MINIO_OPERATIONS.getObject(any(GetObjectArgs.class)))
        .then(invocationOnMock -> new ByteArrayInputStream(CONTENT));
  }

  /**
   * Illegal constructors.
   */
  @Test
  void illegalConstructors() {
    assertThrows(IllegalArgumentException.class,
        () -> new MinioMultipartFileImpl(null, OBJECT_INFO));
    assertThrows(IllegalArgumentException.class,
        () -> new MinioMultipartFileImpl(MINIO_OPERATIONS, null));
    assertThrows(IllegalArgumentException.class,
        () -> new MinioMultipartFileImpl(null, null, STAT_OBJECT_RESPONSE));
    assertThrows(IllegalArgumentException.class,
        () -> new MinioMultipartFileImpl(MINIO_OPERATIONS, null, null));
    assertThrows(IllegalArgumentException.class,
        () -> new MinioMultipartFileImpl(null, null, BUCKET, mock(Item.class)));
    assertThrows(IllegalArgumentException.class,
        () -> new MinioMultipartFileImpl(MINIO_OPERATIONS, null, BUCKET, null));
  }

  /**
   * Gets object status.
   */
  @Test
  void getObjectStatus() {
    MinioMultipartFileImpl file = new MinioMultipartFileImpl(
        MINIO_OPERATIONS,
        OBJECT_INFO);
    assertNotNull(file.getObjectStatus());
  }

  /**
   * Gets name.
   */
  @Test
  void getName() {
    MinioMultipartFileImpl file = new MinioMultipartFileImpl(
        MINIO_OPERATIONS,
        null,
        OBJECT_STAT);
    assertEquals(NAME, file.getName());
    assertEquals(NAME, file.getOriginalFilename());
  }

  /**
   * Gets content type.
   */
  @Test
  void getContentType() {
    MinioMultipartFileImpl file = new MinioMultipartFileImpl(
        MINIO_OPERATIONS,
        null,
        OBJECT_STAT);
    assertEquals(MediaType.TEXT_PLAIN_VALUE, file.getContentType());
  }

  /**
   * Is empty.
   */
  @Test
  void isEmpty() {
    MinioMultipartFileImpl file = new MinioMultipartFileImpl(
        MINIO_OPERATIONS,
        null,
        STAT_OBJECT_RESPONSE);
    assertTrue(file.isEmpty());

    file = new MinioMultipartFileImpl(
        MINIO_OPERATIONS,
        OBJECT_INFO);
    assertFalse(file.isEmpty());
  }

  /**
   * Gets size.
   */
  @Test
  void getSize() {
    MinioMultipartFileImpl file = new MinioMultipartFileImpl(
        MINIO_OPERATIONS,
        null,
        STAT_OBJECT_RESPONSE);
    assertEquals(0L, file.getSize());

    file = new MinioMultipartFileImpl(
        MINIO_OPERATIONS,
        OBJECT_INFO);
    assertEquals(CONTENT.length, (int) file.getSize());
  }

  /**
   * Gets bytes.
   *
   * @throws Exception the exception
   */
  @Test
  void getBytes() throws Exception {
    MinioMultipartFileImpl file = new MinioMultipartFileImpl(
        MINIO_OPERATIONS,
        null,
        STAT_OBJECT_RESPONSE);
    assertArrayEquals(new byte[0], file.getBytes());

    file = new MinioMultipartFileImpl(
        MINIO_OPERATIONS,
        OBJECT_INFO);
    byte[] bytes = file.getBytes();
    assertArrayEquals(CONTENT, bytes);
  }

  /**
   * Gets input stream.
   *
   * @throws Exception the exception
   */
  @Test
  void getInputStream() throws Exception {
    MinioMultipartFileImpl file = new MinioMultipartFileImpl(
        MINIO_OPERATIONS,
        null,
        STAT_OBJECT_RESPONSE);
    assertNotNull(file.getInputStream());

    file = new MinioMultipartFileImpl(
        MINIO_OPERATIONS,
        OBJECT_INFO);
    try (InputStream in = file.getInputStream()) {
      assertArrayEquals(CONTENT, FileCopyUtils.copyToByteArray(in));
    }
  }

  /**
   * Gets etag.
   */
  @Test
  void getEtag() {
    MinioMultipartFileImpl file = new MinioMultipartFileImpl(
        MINIO_OPERATIONS,
        null,
        STAT_OBJECT_RESPONSE);
    assertEquals(ETAG, file.getEtag());

    file = new MinioMultipartFileImpl(
        MINIO_OPERATIONS,
        OBJECT_INFO);
    assertEquals(ETAG, file.getEtag());
  }

  /**
   * Gets created time.
   */
  @Test
  void getCreatedTime() {
    MinioMultipartFileImpl file = new MinioMultipartFileImpl(
        MINIO_OPERATIONS,
        null,
        STAT_OBJECT_RESPONSE);
    assertEquals(TIME.toInstant(), file.getLastModified().toInstant());
  }

  /**
   * Gets region.
   */
  @Test
  void getRegion() {
    MinioMultipartFileImpl file = new MinioMultipartFileImpl(
        MINIO_OPERATIONS,
        null,
        STAT_OBJECT_RESPONSE);
    assertNull(file.getRegion());
  }

  /**
   * Gets bucket.
   */
  @Test
  void getBucket() {
    MinioMultipartFileImpl file = new MinioMultipartFileImpl(
        MINIO_OPERATIONS,
        null,
        STAT_OBJECT_RESPONSE);
    assertEquals(BUCKET, file.getBucket());
  }

  /**
   * Gets version id.
   */
  @Test
  void getVersionId() {
    MinioMultipartFileImpl file = new MinioMultipartFileImpl(
        MINIO_OPERATIONS,
        null,
        STAT_OBJECT_RESPONSE);
    assertEquals(VERSION_ID, file.getVersionId());
  }

  /**
   * Transfer to.
   *
   * @throws Exception the exception
   */
  @Test
  void transferTo() throws Exception {
    File destFile = null;
    try {
      destFile = File
          .createTempFile("junit", ".txt", new File(System.getProperty("java.io.tmpdir")));
      MinioMultipartFileImpl file = new MinioMultipartFileImpl(
          MINIO_OPERATIONS,
          OBJECT_INFO);
      file.transferTo(destFile);
      assertArrayEquals(CONTENT, FileCopyUtils.copyToByteArray(destFile));

    } finally {
      if (destFile != null) {
        Files.delete(destFile.toPath());
      }
    }
  }

  /**
   * Equals and hash code.
   */
  @SuppressWarnings({"SimplifiableJUnitAssertion", "ConstantConditions", "EqualsWithItself"})
  @Test
  void equalsAndHashCode() {
    MinioMultipartFileImpl file0 = new MinioMultipartFileImpl(
        MINIO_OPERATIONS,
        null,
        STAT_OBJECT_RESPONSE);
    assertFalse(file0.equals(null));
    assertFalse(file0.equals(new Object()));
    assertTrue(file0.equals(file0));
    MinioMultipartFileImpl file1 = new MinioMultipartFileImpl(
        MINIO_OPERATIONS,
        file0);
    assertTrue(file0.equals(file1));
    assertEquals(file0.hashCode(), file1.hashCode());
    assertEquals(file0.toString(), file1.toString());
  }
}