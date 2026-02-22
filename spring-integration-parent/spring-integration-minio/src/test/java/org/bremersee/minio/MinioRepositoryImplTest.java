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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.minio.BucketExistsArgs;
import io.minio.GetBucketVersioningArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.RemoveObjectsArgs;
import io.minio.Result;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import io.minio.Time;
import io.minio.http.Method;
import io.minio.messages.DeleteError;
import io.minio.messages.Item;
import io.minio.messages.VersioningConfiguration;
import io.minio.messages.VersioningConfiguration.Status;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

/**
 * The minio repository implementation test.
 *
 * @author Christian Bremer
 */
class MinioRepositoryImplTest {

  private static MinioRepository repository;

  private static final MinioOperations MINIO_OPERATIONS = mock(MinioOperations.class);

  private static final String BUCKET = "testbucket";

  private static final String NAME = "test.txt";

  private static final String ETAG = "1234";

  private static final String VERSION_ID = "5678";

  private static final long SIZE = 15L;

  private static final ZonedDateTime TIME = ZonedDateTime.now();

  /**
   * Sets up.
   */
  @BeforeAll
  static void setUp() {
    repository = new MinioRepositoryImpl(
        MinioClient.builder()
            .endpoint(HttpUrl.get("https://play.min.io"))
            .credentials("Q3AM3UQ867SPQQA43P2F", "zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG")
            .build(),
        null,
        "testbucket",
        false,
        false,
        Duration.ofDays(1L));

    when(MINIO_OPERATIONS.bucketExists(any(BucketExistsArgs.class))).thenReturn(false);

    when(MINIO_OPERATIONS.getBucketVersioning(any(GetBucketVersioningArgs.class)))
        .thenReturn(new VersioningConfiguration(Status.SUSPENDED, false));

    when(MINIO_OPERATIONS.putObject(any(PutObjectArgs.class)))
        .thenReturn(new ObjectWriteResponse(
            Headers.of(Collections.emptyMap()), BUCKET, null, NAME, ETAG, VERSION_ID));

    when(MINIO_OPERATIONS.objectExists(any(StatObjectArgs.class))).thenReturn(true);

    Map<String, String> headers = new LinkedHashMap<>();
    headers.put("Content-Type", MediaType.TEXT_PLAIN_VALUE);
    headers.put("Last-Modified", TIME.format(Time.HTTP_HEADER_DATE_FORMAT));
    headers.put("Content-Length", String.valueOf(SIZE));
    headers.put("ETag", ETAG);
    when(MINIO_OPERATIONS.statObject(any(StatObjectArgs.class))).thenReturn(new StatObjectResponse(
        Headers.of(headers), BUCKET, null, NAME
    ));

    Item item = mock(Item.class);
    when(item.etag()).thenReturn(ETAG);
    when(item.isDeleteMarker()).thenReturn(false);
    when(item.isDir()).thenReturn(false);
    when(item.isLatest()).thenReturn(true);
    when(item.lastModified()).thenReturn(ZonedDateTime.now());
    when(item.objectName()).thenReturn(NAME);
    when(item.size()).thenReturn(SIZE);
    when(item.versionId()).thenReturn(VERSION_ID);

    when(MINIO_OPERATIONS.listObjects(any(ListObjectsArgs.class)))
        .thenReturn(Collections.singletonList(new Result<>(item)));

    when(MINIO_OPERATIONS.removeObjects(any(RemoveObjectsArgs.class)))
        .thenReturn(Collections.singletonList(new Result<>(new DeleteError())));

    when(MINIO_OPERATIONS.getPresignedObjectUrl(any(GetPresignedObjectUrlArgs.class)))
        .thenReturn("https://example.org/somewhere");

    repository = new MinioRepositoryImpl(
        MINIO_OPERATIONS,
        null,
        BUCKET,
        true,
        true,
        Duration.ofDays(1L));
  }

  /**
   * Gets minio operations.
   */
  @Test
  void getMinioOperations() {
    assertNotNull(repository.getMinioOperations());
  }

  /**
   * Gets region.
   */
  @Test
  void getRegion() {
    assertNull(repository.getRegion());
  }

  /**
   * Gets bucket.
   */
  @Test
  void getBucket() {
    assertEquals("testbucket", repository.getBucket());
  }

  /**
   * Versioning.
   */
  @Test
  void versioning() {
    assertTrue(repository.isVersioningEnabled());
  }

  /**
   * Save.
   *
   * @throws Exception the exception
   */
  @Test
  void save() throws Exception {
    Optional<ObjectWriteResponse> response = repository.save(
        MinioObjectId.from("test"),
        FileAwareMultipartFile.empty(),
        DeleteMode.NEVER);
    assertFalse(response.isPresent());

    MultipartFile multipartFile = new FileAwareMultipartFile(
        new ByteArrayInputStream("Hello".getBytes(StandardCharsets.UTF_8)),
        "file",
        "a.txt",
        MediaType.TEXT_PLAIN_VALUE);
    response = repository.save(MinioObjectId.from(NAME), multipartFile, DeleteMode.ALWAYS);
    assertTrue(response.isPresent());
    ObjectWriteResponse obj = response.get();
    assertEquals(NAME, obj.object());
    assertEquals(VERSION_ID, obj.versionId());
    assertEquals(ETAG, obj.etag());
    verify(MINIO_OPERATIONS).putObject(any(PutObjectArgs.class));
  }

  /**
   * Exists.
   */
  @Test
  void exists() {
    assertTrue(repository.exists(MinioObjectId.from(NAME, VERSION_ID)));
    verify(MINIO_OPERATIONS).objectExists(any(StatObjectArgs.class));
  }

  /**
   * Find one.
   */
  @Test
  void findOne() {
    Optional<MinioMultipartFile> response = repository.findOne(MinioObjectId.from(NAME));
    assertTrue(response.isPresent());
    MinioMultipartFile file = response.get();
    assertEquals(NAME, file.getName());
    assertEquals(BUCKET, file.getBucket());
    assertEquals(ETAG, file.getEtag());
    assertEquals(SIZE, file.getSize());
    verify(MINIO_OPERATIONS).statObject(any(StatObjectArgs.class));
  }

  /**
   * Find all.
   */
  @Test
  void findAll() {
    List<MinioMultipartFile> files = repository.findAll();
    assertNotNull(files);
    verify(MINIO_OPERATIONS).listObjects(any(ListObjectsArgs.class));
  }

  /**
   * Delete.
   */
  @Test
  void delete() {
    repository.delete(MinioObjectId.from(NAME, VERSION_ID));
    verify(MINIO_OPERATIONS).removeObject(any(RemoveObjectArgs.class));
  }

  /**
   * Delete all.
   */
  @Test
  void deleteAll() {
    List<DeleteError> results = repository
        .deleteAll(Collections.singletonList(MinioObjectId.from(NAME)));
    assertNotNull(results);
    verify(MINIO_OPERATIONS).removeObjects(any(RemoveObjectsArgs.class));
  }

  /**
   * Gets presigned object url.
   */
  @Test
  void getPresignedObjectUrl() {
    String url = repository.getPresignedObjectUrl(MinioObjectId.from(NAME), Method.GET);
    assertNotNull(url);
    verify(MINIO_OPERATIONS).getPresignedObjectUrl(any(GetPresignedObjectUrlArgs.class));
  }
}