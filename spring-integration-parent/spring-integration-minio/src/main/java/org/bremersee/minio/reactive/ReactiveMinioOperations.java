package org.bremersee.minio.reactive;

import static org.springframework.util.ObjectUtils.isEmpty;

import io.minio.BucketExistsArgs;
import io.minio.CloseableIterator;
import io.minio.ComposeObjectArgs;
import io.minio.CopyObjectArgs;
import io.minio.DeleteBucketCorsArgs;
import io.minio.DeleteBucketEncryptionArgs;
import io.minio.DeleteBucketLifecycleArgs;
import io.minio.DeleteBucketNotificationArgs;
import io.minio.DeleteBucketPolicyArgs;
import io.minio.DeleteBucketReplicationArgs;
import io.minio.DeleteBucketTagsArgs;
import io.minio.DeleteObjectLockConfigurationArgs;
import io.minio.DeleteObjectTagsArgs;
import io.minio.DisableObjectLegalHoldArgs;
import io.minio.DownloadObjectArgs;
import io.minio.EnableObjectLegalHoldArgs;
import io.minio.GenericResponse;
import io.minio.GetBucketCorsArgs;
import io.minio.GetBucketEncryptionArgs;
import io.minio.GetBucketLifecycleArgs;
import io.minio.GetBucketNotificationArgs;
import io.minio.GetBucketPolicyArgs;
import io.minio.GetBucketReplicationArgs;
import io.minio.GetBucketTagsArgs;
import io.minio.GetBucketVersioningArgs;
import io.minio.GetObjectAclArgs;
import io.minio.GetObjectArgs;
import io.minio.GetObjectAttributesArgs;
import io.minio.GetObjectAttributesResponse;
import io.minio.GetObjectLockConfigurationArgs;
import io.minio.GetObjectResponse;
import io.minio.GetObjectRetentionArgs;
import io.minio.GetObjectTagsArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.IsObjectLegalHoldEnabledArgs;
import io.minio.ListBucketsArgs;
import io.minio.ListObjectsArgs;
import io.minio.ListenBucketNotificationArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioAsyncClient;
import io.minio.ObjectWriteResponse;
import io.minio.PostPolicy;
import io.minio.PromptObjectArgs;
import io.minio.PromptObjectResponse;
import io.minio.PutObjectArgs;
import io.minio.PutObjectFanOutArgs;
import io.minio.PutObjectFanOutResponse;
import io.minio.RemoveBucketArgs;
import io.minio.RemoveObjectArgs;
import io.minio.RemoveObjectsArgs;
import io.minio.RestoreObjectArgs;
import io.minio.Result;
import io.minio.SelectObjectContentArgs;
import io.minio.SelectResponseStream;
import io.minio.SetBucketCorsArgs;
import io.minio.SetBucketEncryptionArgs;
import io.minio.SetBucketLifecycleArgs;
import io.minio.SetBucketNotificationArgs;
import io.minio.SetBucketPolicyArgs;
import io.minio.SetBucketReplicationArgs;
import io.minio.SetBucketTagsArgs;
import io.minio.SetBucketVersioningArgs;
import io.minio.SetObjectLockConfigurationArgs;
import io.minio.SetObjectRetentionArgs;
import io.minio.SetObjectTagsArgs;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import io.minio.UploadObjectArgs;
import io.minio.messages.AccessControlPolicy;
import io.minio.messages.CORSConfiguration;
import io.minio.messages.DeleteResult;
import io.minio.messages.Item;
import io.minio.messages.LifecycleConfiguration;
import io.minio.messages.ListAllMyBucketsResult.Bucket;
import io.minio.messages.NotificationConfiguration;
import io.minio.messages.NotificationRecords;
import io.minio.messages.ObjectLockConfiguration;
import io.minio.messages.ReplicationConfiguration;
import io.minio.messages.Retention;
import io.minio.messages.SseConfiguration;
import io.minio.messages.Tags;
import io.minio.messages.VersioningConfiguration;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.bremersee.exception.HttpStatusAware;
import org.bremersee.minio.DeleteMode;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveMinioOperations {

  <T> T execute(MinioClientCallback<T> callback);

  <T> Mono<T> executeAsync(MinioAsyncClientCallback<T> callback);

  default <T> Flux<T> toFlux(Iterable<T> iterable) {
    return Flux.fromIterable(iterable);
  }

  // Bucket operations

  /**
   * Checks if a bucket exists.
   *
   * <p>Example:
   * <pre>
   * boolean found = minioClient
   *     .bucketExists(BucketExistsArgs.builder().bucket("my-bucketname").build());
   * if (found) {
   *   System.out.println("my-bucketname exists");
   * } else {
   *   System.out.println("my-bucketname does not exist");
   * }
   * </pre>
   *
   * @param args the bucket exists arguments
   * @return {@code true} if the bucket exists
   */
  default Mono<Boolean> bucketExists(BucketExistsArgs args) {
    return executeAsync(client -> client.bucketExists(args));
  }

  /**
   * Deletes CORS configuration of a bucket.
   *
   * <p>Example:
   * <pre>
   * minioClient.deleteBucketCors(DeleteBucketCorsArgs.builder().bucket("my-bucketname").build());
   * </pre>
   *
   * @param args {@link DeleteBucketCorsArgs} object.
   */
  default Mono<Void> deleteBucketCors(DeleteBucketCorsArgs args) {
    return executeAsync(minioClient -> minioClient.deleteBucketCors(args));
  }

  /**
   * Deletes encryption configuration of a bucket.
   *
   * <p>Example:
   * <pre>
   * minioClient.deleteBucketEncryption(
   *     DeleteBucketEncryptionArgs.builder().bucket("my-bucketname").build());
   * </pre>
   *
   * @param args delete bucket encryption arguments
   */
  default Mono<Void> deleteBucketEncryption(DeleteBucketEncryptionArgs args) {
    return executeAsync(minioClient -> minioClient.deleteBucketEncryption(args));
  }

  /**
   * Deletes lifecycle configuration of a bucket.
   *
   * <p>Example:
   * <pre>
   * deleteBucketLifecycle(DeleteBucketLifecycleArgs.builder().bucket("my-bucketname").build());
   * </pre>
   *
   * @param args {@link DeleteBucketLifecycleArgs} object.
   */
  default Mono<Void> deleteBucketLifecycle(DeleteBucketLifecycleArgs args) {
    return executeAsync(minioClient -> minioClient
        .deleteBucketLifecycle(args));
  }

  /**
   * Deletes tags of a bucket.
   *
   * <p>Example:
   * <pre>
   * minioClient.deleteBucketTags(DeleteBucketTagsArgs.builder().bucket("my-bucketname").build());
   * </pre>
   *
   * @param args the delete bucket tags arguments
   */
  default Mono<Void> deleteBucketTags(DeleteBucketTagsArgs args) {
    return executeAsync(minioClient -> minioClient
        .deleteBucketTags(args));
  }

  /**
   * Deletes bucket policy configuration to a bucket.
   *
   * <p>Example:
   * <pre>
   * minioClient.deleteBucketPolicy(DeleteBucketPolicyArgs.builder().bucket("my-bucketname"));
   * </pre>
   *
   * @param args delete bucket policy arguments
   */
  default Mono<Void> deleteBucketPolicy(DeleteBucketPolicyArgs args) {
    return executeAsync(minioClient -> minioClient
        .deleteBucketPolicy(args));
  }

  /**
   * Deletes bucket replication configuration from a bucket.
   *
   * <p>Example:
   * <pre>
   * minioClient.deleteBucketReplication(
   *     DeleteBucketReplicationArgs.builder().bucket("my-bucketname"));
   * </pre>
   *
   * @param args delete bucket replication arguments
   */
  default Mono<Void> deleteBucketReplication(DeleteBucketReplicationArgs args) {
    return executeAsync(minioClient -> minioClient
        .deleteBucketReplication(args));
  }

  /**
   * Deletes notification configuration of a bucket.
   *
   * <p>Example:
   * <pre>
   * minioClient.deleteBucketNotification(
   *     DeleteBucketNotificationArgs.builder().bucket("my-bucketname").build());
   * </pre>
   *
   * @param args delete bucket notification arguments
   */
  default Mono<Void> deleteBucketNotification(DeleteBucketNotificationArgs args) {
    return executeAsync(minioClient -> minioClient
        .deleteBucketNotification(args));
  }

  /**
   * Deletes default object retention in a bucket.
   *
   * <p>Example:
   * <pre>
   * minioClient.deleteObjectLockConfiguration(
   *     DeleteObjectLockConfigurationArgs.builder().bucket("my-bucketname").build());
   * </pre>
   *
   * @param args delete object retention configuration arguments
   */
  default Mono<Void> deleteObjectLockConfiguration(DeleteObjectLockConfigurationArgs args) {
    return executeAsync(minioClient -> minioClient
        .deleteObjectLockConfiguration(args));
  }

  /**
   * Gets CORS configuration of a bucket.
   *
   * <p>Example:
   * <pre>
   * CORSConfiguration config =
   *     minioClient.getBucketCors(GetBucketCorsArgs.builder().bucket("my-bucketname").build());
   * </pre>
   *
   * @param args {@link GetBucketCorsArgs} object.
   * @return {@link CORSConfiguration} - CORSConfiguration.
   */
  default Mono<CORSConfiguration> getBucketCors(GetBucketCorsArgs args) {
    return executeAsync(minioClient -> minioClient.getBucketCors(args));
  }

  /**
   * Gets encryption configuration of a bucket.
   *
   * <p>Example:
   * <pre>
   * SseConfiguration config =
   *     minioClient.getBucketEncryption(
   *         GetBucketEncryptionArgs.builder().bucket("my-bucketname").build());
   * </pre>
   *
   * @param args get bucket encryption arguments
   * @return server -side encryption configuration
   */
  default Mono<SseConfiguration> getBucketEncryption(GetBucketEncryptionArgs args) {
    return executeAsync(minioClient -> minioClient.getBucketEncryption(args));
  }

  /**
   * Gets lifecycle configuration of a bucket.
   *
   * <p>Example:
   * <pre>
   * LifecycleConfiguration config =
   *     minioClient.getBucketLifecycle(
   *         GetBucketLifecycleArgs.builder().bucket("my-bucketname").build());
   * </pre>
   *
   * @param args get bucket lifecycle arguments
   * @return the lifecycle configuration
   */
  default Mono<LifecycleConfiguration> getBucketLifecycle(GetBucketLifecycleArgs args) {
    return executeAsync(minioClient -> minioClient.getBucketLifecycle(args));
  }

  /**
   * Gets notification configuration of a bucket.
   *
   * <p>Example:
   * <pre>
   * NotificationConfiguration config =
   *     minioClient.getBucketNotification(
   *         GetBucketNotificationArgs.builder().bucket("my-bucketname").build());
   * </pre>
   *
   * @param args get bucket notification arguments
   * @return the notification configuration
   */
  default Mono<NotificationConfiguration> getBucketNotification(GetBucketNotificationArgs args) {
    return executeAsync(minioClient -> minioClient.getBucketNotification(args));
  }

  /**
   * Gets bucket policy configuration of a bucket.
   *
   * <p>Example:
   * <pre>
   * String config =
   *     minioClient.getBucketPolicy(GetBucketPolicyArgs.builder().bucket("my-bucketname").build());
   * </pre>
   *
   * @param args get bucket policy arguments
   * @return bucket policy configuration as JSON string
   */
  default Mono<String> getBucketPolicy(GetBucketPolicyArgs args) {
    return executeAsync(minioClient -> minioClient.getBucketPolicy(args));
  }

  /**
   * Gets bucket replication configuration of a bucket.
   *
   * <p>Example:
   * <pre>
   * ReplicationConfiguration config =
   *     minioClient.getBucketReplication(
   *         GetBucketReplicationArgs.builder().bucket("my-bucketname").build());
   * </pre>
   *
   * @param args get bucket replication arguments
   * @return the replication configuration
   */
  default Mono<ReplicationConfiguration> getBucketReplication(GetBucketReplicationArgs args) {
    return executeAsync(minioClient -> minioClient.getBucketReplication(args));
  }

  /**
   * Gets tags of a bucket.
   *
   * <p>Example:
   * <pre>
   * Tags tags =
   *     minioClient.getBucketTags(GetBucketTagsArgs.builder().bucket("my-bucketname").build());
   * </pre>
   *
   * @param args get bucket tags arguments
   * @return the tags
   */
  default Mono<Tags> getBucketTags(GetBucketTagsArgs args) {
    return executeAsync(minioClient -> minioClient.getBucketTags(args));
  }

  /**
   * Gets versioning configuration of a bucket.
   *
   * <p>Example:
   * <pre>
   * VersioningConfiguration config =
   *     minioClient.getBucketVersioning(
   *         GetBucketVersioningArgs.builder().bucket("my-bucketname").build());
   * </pre>
   *
   * @param args get bucket version arguments
   * @return the versioning configuration.
   */
  default Mono<VersioningConfiguration> getBucketVersioning(GetBucketVersioningArgs args) {
    return executeAsync(minioClient -> minioClient.getBucketVersioning(args));
  }

  /**
   * Gets default object retention in a bucket.
   *
   * <p>Example:
   * <pre>
   * ObjectLockConfiguration config =
   *     minioClient.getObjectLockConfiguration(
   *         GetObjectLockConfigurationArgs.builder().bucket("my-bucketname").build());
   * System.out.println("Mode: " + config.mode());
   * System.out.println(
   *     "Duration: " + config.duration().duration() + " " + config.duration().unit());
   * </pre>
   *
   * @param args get object retention configuration arguments
   * @return the default retention configuration
   */
  default Mono<ObjectLockConfiguration> getObjectLockConfiguration(
      GetObjectLockConfigurationArgs args) {
    return executeAsync(minioClient -> minioClient.getObjectLockConfiguration(args));
  }

  /**
   * Lists bucket information of all buckets.
   *
   * <p>Example:
   * <pre>
   * List&lt;ListAllMyBucketsResult.Bucket&gt; bucketList = minioOperations.listBuckets();
   * for (ListAllMyBucketsResult.Bucket bucket : bucketList) {
   *   System.out.println(bucket.creationDate() + ", " + bucket.name());
   * }
   * </pre>
   *
   * @return list of bucket information
   */
  default Flux<Bucket> listBuckets() {
    return executeAsync(MinioAsyncClient::listBuckets)
        .flatMapMany(Flux::fromIterable);
  }

  /**
   * Lists bucket information of all buckets.
   *
   * <p>Example:
   * <pre>
   * Iterable&lt;Result&lt;ListAllMyBucketsResult.Bucket&gt;&gt; results = minioClient
   *     .listBuckets(ListBucketsArgs.builder().extraHeaders(headers).build());
   * for (Result&lt;ListAllMyBucketsResult.Bucket&gt; result : results) {
   *   System.out.println(result.get().creationDate() + ", " + result.get().name());
   * }
   * </pre>
   *
   * @param args the list buckets arguments
   * @return list of bucket information
   */
  default Flux<Result<Bucket>> listBuckets(ListBucketsArgs args) {
    return toFlux(execute(client -> client.listBuckets(args)));
  }

  /**
   * Listens events of object prefix and suffix of a bucket. The returned closable iterator is
   * lazily evaluated hence its required to iterate to get new records and must be used with
   * try-with-resource to release underneath network resources.
   *
   * <p>Example:
   * <pre>
   * String[] events = {"s3:ObjectCreated:*", "s3:ObjectAccessed:*"};
   * try (CloseableIterator&lt;Result&lt;NotificationRecords&gt;&gt; ci =
   *     minioClient.listenBucketNotification(
   *         ListenBucketNotificationArgs.builder()
   *             .bucket("bucketName")
   *             .prefix("")
   *             .suffix("")
   *             .events(events)
   *             .build())) {
   *   while (ci.hasNext()) {
   *     NotificationRecords records = ci.next().get();
   *     for (Event event : records.events()) {
   *       System.out.println("Event " + event.eventType() + " occurred at "
   *           + event.eventTime() + " for " + event.bucketName() + "/"
   *           + event.objectName());
   *     }
   *   }
   * }
   * </pre>
   *
   * @param args the listen bucket notification arguments
   * @return lazy closable iterator contains event records
   */
  default Flux<Result<NotificationRecords>> listenBucketNotification(
      ListenBucketNotificationArgs args) {

    CloseableIterator<Result<NotificationRecords>> ci = execute(client -> client
        .listenBucketNotification(args));
    List<Result<NotificationRecords>> results = new ArrayList<>();
    try {
      while (ci.hasNext()) {
        results.add(ci.next());
      }
    } finally {
      try {
        ci.close();
      } catch (Exception e) {
        // ignored
      }
    }
    return toFlux(results);
  }

  /**
   * Creates a bucket with region and object lock.
   *
   * <p>Example:
   * <pre>
   * // Create bucket with default region.
   * minioClient.makeBucket(
   *     MakeBucketArgs.builder()
   *         .bucket("my-bucketname")
   *         .build());
   *
   * // Create bucket with specific region.
   * minioClient.makeBucket(
   *     MakeBucketArgs.builder()
   *         .bucket("my-bucketname")
   *         .region("us-west-1")
   *         .build());
   *
   * // Create object-lock enabled bucket with specific region.
   * minioClient.makeBucket(
   *     MakeBucketArgs.builder()
   *         .bucket("my-bucketname")
   *         .region("us-west-1")
   *         .objectLock(true)
   *         .build());
   * </pre>
   *
   * @param args object with bucket name, region and lock functionality
   */
  default Mono<GenericResponse> makeBucket(MakeBucketArgs args) {
    return executeAsync(minioClient -> minioClient
        .makeBucket(args));
  }

  /**
   * Removes an empty bucket using arguments.
   *
   * <p>Example:
   * <pre>
   * minioClient.removeBucket(RemoveBucketArgs.builder().bucket("my-bucketname").build());
   * </pre>
   *
   * @param args the remove bucket arguments
   */
  default Mono<Void> removeBucket(RemoveBucketArgs args) {
    return executeAsync(minioClient -> minioClient
        .removeBucket(args));
  }

  /**
   * Sets CORS configuration to a bucket.
   *
   * <p>Example:
   * <pre>
   * CORSConfiguration config =
   *     new CORSConfiguration(
   *         Arrays.asList(
   *             new CORSConfiguration.CORSRule[] {
   *               // Rule 1
   *               new CORSConfiguration.CORSRule(
   *                   Arrays.asList(new String[] {"*"}), // Allowed headers
   *                   Arrays.asList(new String[] {"PUT", "POST", "DELETE"}), // Allowed methods
   *                   Arrays.asList(new String[] {"https://www.example.com"}), // Allowed origins
   *                   Arrays.asList(
   *                       new String[] {"x-amz-server-side-encryption"}), // Expose headers
   *                   null, // ID
   *                   3000), // Maximum age seconds
   *               // Rule 2
   *               new CORSConfiguration.CORSRule(
   *                   null, // Allowed headers
   *                   Arrays.asList(new String[] {"GET"}), // Allowed methods
   *                   Arrays.asList(new String[] {"*"}), // Allowed origins
   *                   null, // Expose headers
   *                   null, // ID
   *                   null // Maximum age seconds
   *                   )
   *             }));
   * minioClient.setBucketCors(
   *     SetBucketCorsArgs.builder().bucket("my-bucketname").config(config).build());
   * </pre>
   *
   * @param args {@link SetBucketCorsArgs} object.
   */
  @SuppressWarnings("JavadocLinkAsPlainText")
  default Mono<Void> setBucketCors(SetBucketCorsArgs args) {
    return executeAsync(minioClient -> minioClient
        .setBucketCors(args));
  }

  /**
   * Sets encryption configuration of a bucket.
   *
   * <p>Example:
   * <pre>
   * minioClient.setBucketEncryption(
   *     SetBucketEncryptionArgs.builder().bucket("my-bucketname").config(config).build());
   * </pre>
   *
   * @param args bucket encryption arguments
   */
  default Mono<Void> setBucketEncryption(SetBucketEncryptionArgs args) {
    return executeAsync(minioClient -> minioClient
        .setBucketEncryption(args));
  }

  /**
   * Sets lifecycle configuration to a bucket.
   *
   * <p>Example:
   * <pre>
   * List&lt;LifecycleRule&gt; rules = new LinkedList&lt;&gt;();
   * rules.add(
   *     new LifecycleRule(
   *         Status.ENABLED,
   *         null,
   *         new Expiration((ZonedDateTime) null, 365, null),
   *         new RuleFilter("logs/"),
   *         "rule2",
   *         null,
   *         null,
   *         null));
   * LifecycleConfiguration config = new LifecycleConfiguration(rules);
   * minioClient.setBucketLifecycle(
   *     SetBucketLifecycleArgs.builder().bucket("my-bucketname").config(config).build());
   * </pre>
   *
   * @param args set bucket lifecycle arguments
   */
  default Mono<Void> setBucketLifecycle(SetBucketLifecycleArgs args) {
    return executeAsync(minioClient -> minioClient
        .setBucketLifecycle(args));
  }

  /**
   * Sets notification configuration to a bucket.
   *
   * <p>Example:
   * <pre>
   * List&lt;EventType&gt; eventList = new LinkedList&lt;&gt;();
   * eventList.add(EventType.OBJECT_CREATED_PUT);
   * eventList.add(EventType.OBJECT_CREATED_COPY);
   *
   * QueueConfiguration queueConfiguration = new QueueConfiguration();
   * queueConfiguration.setQueue("arn:minio:sqs::1:webhook");
   * queueConfiguration.setEvents(eventList);
   * queueConfiguration.setPrefixRule("images");
   * queueConfiguration.setSuffixRule("pg");
   *
   * List&lt;QueueConfiguration&gt; queueConfigurationList = new LinkedList&lt;&gt;();
   * queueConfigurationList.add(queueConfiguration);
   *
   * NotificationConfiguration config = new NotificationConfiguration();
   * config.setQueueConfigurationList(queueConfigurationList);
   *
   * minioClient.setBucketNotification(
   *     SetBucketNotificationArgs.builder().bucket("my-bucketname").config(config).build());
   * </pre>
   *
   * @param args set bucket notification arguments
   */
  default Mono<Void> setBucketNotification(SetBucketNotificationArgs args) {
    return executeAsync(minioClient -> minioClient
        .setBucketNotification(args));
  }

  /**
   * Sets bucket policy configuration to a bucket.
   *
   * <p>Example:
   * <pre>
   * // Assume policyJson contains below JSON string;
   * // {
   * //     "Statement": [
   * //         {
   * //             "Action": [
   * //                 "s3:GetBucketLocation",
   * //                 "s3:ListBucket"
   * //             ],
   * //             "Effect": "Allow",
   * //             "Principal": "*",
   * //             "Resource": "arn:aws:s3:::my-bucketname"
   * //         },
   * //         {
   * //             "Action": "s3:GetObject",
   * //             "Effect": "Allow",
   * //             "Principal": "*",
   * //             "Resource": "arn:aws:s3:::my-bucketname/myobject*"
   * //         }
   * //     ],
   * //     "Version": "2012-10-17"
   * // }
   * //
   * minioClient.setBucketPolicy(
   *     SetBucketPolicyArgs.builder().bucket("my-bucketname").config(policyJson).build());
   * </pre>
   *
   * @param args set bucket policy arguments
   */
  default Mono<Void> setBucketPolicy(SetBucketPolicyArgs args) {
    return executeAsync(minioClient -> minioClient
        .setBucketPolicy(args));
  }

  /**
   * Sets bucket replication configuration to a bucket.
   *
   * <p>Example:
   * <pre>
   * Map&lt;String, String&gt; tags = new HashMap&lt;&gt;();
   * tags.put("key1", "value1");
   * tags.put("key2", "value2");
   *
   * ReplicationRule rule =
   *     new ReplicationRule(
   *         new DeleteMarkerReplication(Status.DISABLED),
   *         new ReplicationDestination(
   *             null, null, "REPLACE-WITH-ACTUAL-DESTINATION-BUCKET-ARN", null, null, null, null),
   *         null,
   *         new RuleFilter(new AndOperator("TaxDocs", tags)),
   *         "rule1",
   *         null,
   *         1,
   *         null,
   *         Status.ENABLED);
   *
   * List&lt;ReplicationRule&gt; rules = new LinkedList&lt;&gt;();
   * rules.add(rule);
   *
   * ReplicationConfiguration config =
   *     new ReplicationConfiguration("REPLACE-WITH-ACTUAL-ROLE", rules);
   *
   * minioClient.setBucketReplication(
   *     SetBucketReplicationArgs.builder().bucket("my-bucketname").config(config).build());
   * </pre>
   *
   * @param args set bucket replication arguments
   */
  default Mono<Void> setBucketReplication(SetBucketReplicationArgs args) {
    return executeAsync(minioClient -> minioClient
        .setBucketReplication(args));
  }

  /**
   * Sets tags to a bucket.
   *
   * <p>Example:
   * <pre>
   * Map&lt;String, String&gt; map = new HashMap&lt;&gt;();
   * map.put("Project", "Project One");
   * map.put("User", "jsmith");
   * minioClient.setBucketTags(
   *     SetBucketTagsArgs.builder().bucket("my-bucketname").tags(map).build());
   * </pre>
   *
   * @param args the set bucket tags arguments
   */
  default Mono<Void> setBucketTags(SetBucketTagsArgs args) {
    return executeAsync(minioClient -> minioClient
        .setBucketTags(args));
  }

  /**
   * Sets versioning configuration of a bucket.
   *
   * <p>Example:
   * <pre>
   * minioClient.setBucketVersioning(
   *     SetBucketVersioningArgs.builder().bucket("my-bucketname").config(config).build());
   * </pre>
   *
   * @param args set bucket versioning arguments
   */
  default Mono<Void> setBucketVersioning(SetBucketVersioningArgs args) {
    return executeAsync(minioClient -> minioClient
        .setBucketVersioning(args));
  }

  /**
   * Sets default object retention in a bucket.
   *
   * <p>Example:
   * <pre>
   * ObjectLockConfiguration config = new ObjectLockConfiguration(
   *     RetentionMode.COMPLIANCE, new RetentionDurationDays(100));
   * minioClient.setObjectLockConfiguration(
   *     SetObjectLockConfigurationArgs.builder().bucket("my-bucketname").config(config).build());
   * </pre>
   *
   * @param args the default object retention configuration arguments
   */
  default Mono<Void> setObjectLockConfiguration(SetObjectLockConfigurationArgs args) {
    return executeAsync(minioClient -> minioClient
        .setObjectLockConfiguration(args));
  }

  // Object operations

  /**
   * Creates an object by combining data from different source objects using server-side copy.
   *
   * <p>Example:
   * <pre>
   * List<ComposeSource> sourceObjectList = new ArrayList<ComposeSource>();
   *
   * sourceObjectList.add(
   *    ComposeSource.builder().bucket("my-job-bucket").object("my-objectname-part-one").build());
   * sourceObjectList.add(
   *    ComposeSource.builder().bucket("my-job-bucket").object("my-objectname-part-two").build());
   * sourceObjectList.add(
   *    ComposeSource.builder().bucket("my-job-bucket").object("my-objectname-part-three").build());
   *
   * // Create my-bucketname/my-objectname by combining source object list.
   * minioClient.composeObject(
   *    ComposeObjectArgs.builder()
   *        .bucket("my-bucketname")
   *        .object("my-objectname")
   *        .sources(sourceObjectList)
   *        .build());
   *
   * // Create my-bucketname/my-objectname with user metadata by combining source object
   * // list.
   * Map<String, String> userMetadata = new HashMap<>();
   * userMetadata.put("My-Project", "Project One");
   * minioClient.composeObject(
   *     ComposeObjectArgs.builder()
   *        .bucket("my-bucketname")
   *        .object("my-objectname")
   *        .sources(sourceObjectList)
   *        .userMetadata(userMetadata)
   *        .build());
   *
   * // Create my-bucketname/my-objectname with user metadata and server-side encryption
   * // by combining source object list.
   * minioClient.composeObject(
   *   ComposeObjectArgs.builder()
   *        .bucket("my-bucketname")
   *        .object("my-objectname")
   *        .sources(sourceObjectList)
   *        .userMetadata(userMetadata)
   *        .ssec(sse)
   *        .build());
   * </pre>
   *
   * @param args {@link ComposeObjectArgs} object.
   * @return {@link ObjectWriteResponse} object.
   */
  default Mono<ObjectWriteResponse> composeObject(ComposeObjectArgs args) {
    return executeAsync(minioClient -> minioClient.composeObject(args));
  }

  /**
   * Creates an object by server-side copying data from another object.
   *
   * @param args copy object arguments
   * @return the object write response
   */
  default Mono<ObjectWriteResponse> copyObject(CopyObjectArgs args) {
    return executeAsync(minioClient -> minioClient.copyObject(args));
  }

  /**
   * Deletes tags of an object.
   *
   * <p>Example:
   * <pre>
   * minioClient.deleteObjectTags(
   *     DeleteObjectTags.builder().bucket("my-bucketname").object("my-objectname").build());
   * </pre>
   *
   * @param args delete object tags arguments
   */
  default Mono<Void> deleteObjectTags(DeleteObjectTagsArgs args) {
    return executeAsync(minioClient -> minioClient
        .deleteObjectTags(args));
  }

  /**
   * Disables legal hold on an object.
   *
   * <p>Example:
   * <pre>
   * minioClient.disableObjectLegalHold(
   *    DisableObjectLegalHoldArgs.builder()
   *        .bucket("my-bucketname")
   *        .object("my-objectname")
   *        .versionId("object-versionId")
   *        .build());
   * </pre>
   *
   * @param args disable object legal hold arguments
   */
  default Mono<Void> disableObjectLegalHold(DisableObjectLegalHoldArgs args) {
    return executeAsync(minioClient -> minioClient
        .disableObjectLegalHold(args));
  }

  /**
   * Downloads data of a SSE-C encrypted object to file.
   *
   * <p>Example:
   * <pre>
   * minioClient.downloadObject(
   *   GetObjectArgs.builder()
   *     .bucket("my-bucketname")
   *     .object("my-objectname")
   *     .ssec(ssec)
   *     .fileName("my-filename")
   *     .build());
   * </pre>
   *
   * @param args download object arguments
   */
  default Mono<Void> downloadObject(DownloadObjectArgs args) {
    return executeAsync(minioClient -> minioClient
        .downloadObject(args));
  }

  /**
   * Enables legal hold on an object.
   *
   * <p>Example:
   * <pre>
   * minioClient.enableObjectLegalHold(
   *    EnableObjectLegalHoldArgs.builder()
   *        .bucket("my-bucketname")
   *        .object("my-objectname")
   *        .versionId("object-versionId")
   *        .build());
   * </pre>
   *
   * @param args enable object legal hold arguments
   */
  default Mono<Void> enableObjectLegalHold(EnableObjectLegalHoldArgs args) {
    return executeAsync(minioClient -> minioClient
        .enableObjectLegalHold(args));
  }

  /**
   * Gets data from offset to length of a SSE-C encrypted object. Returned {@link InputStream} must
   * be closed after use to release network resources.
   *
   * <p>Example:
   * <pre>
   * try (InputStream stream =
   *     minioClient.getObject(
   *   GetObjectArgs.builder()
   *     .bucket("my-bucketname")
   *     .object("my-objectname")
   *     .offset(offset)
   *     .length(len)
   *     .ssec(ssec)
   *     .build()
   * ) {
   *   // Read data from stream
   * }
   * </pre>
   *
   * @param args the get object arguments
   * @return the input stream
   */
  default Mono<GetObjectResponse> getObject(GetObjectArgs args) {
    return executeAsync(minioClient -> minioClient.getObject(args));
  }

  /**
   * Gets access control policy of an object.
   *
   * <p>Example:
   * <pre>
   * AccessControlPolicy policy =
   *     minioClient.getObjectAcl(
   *         GetObjectAclArgs.builder().bucket("my-bucketname").object("my-objectname").build());
   * </pre>
   *
   * @param args {@link GetObjectAclArgs} object.
   * @return {@link AccessControlPolicy} - Access control policy object.
   */
  default Mono<AccessControlPolicy> getObjectAcl(GetObjectAclArgs args) {
    return executeAsync(minioClient -> minioClient.getObjectAcl(args));
  }

  /**
   * Gets attributes of an object.
   *
   * <p>Example:
   * <pre>Example:
   * GetObjectAttributesResponse response =
   *     minioClient.getObjectAttributes(
   *         GetObjectAttributesArgs.builder()
   *             .bucket("my-bucketname")
   *             .object("my-objectname")
   *             .objectAttributes(
   *                 new String[] {
   *                   "ETag", "Checksum", "ObjectParts", "StorageClass", "ObjectSize"
   *                 })
   *             .build());
   * </pre>
   *
   * @param args {@link GetObjectAttributesArgs} object.
   * @return {@link GetObjectAttributesResponse} - Response object.
   */
  default Mono<GetObjectAttributesResponse> getObjectAttributes(GetObjectAttributesArgs args) {
    return executeAsync(minioClient -> minioClient.getObjectAttributes(args));
  }

  /**
   * Gets retention configuration of an object.
   *
   * <p>Example:
   * <pre>
   * Retention retention =
   *     minioClient.getObjectRetention(GetObjectRetentionArgs.builder()
   *        .bucket(bucketName)
   *        .object(objectName)
   *        .versionId(versionId)
   *        .build()););
   * System.out.println(
   *     "mode: " + retention.mode() + "until: " + retention.retainUntilDate());
   * </pre>
   *
   * @param args get object retention arguments
   * @return object retention configuration
   */
  default Mono<Retention> getObjectRetention(GetObjectRetentionArgs args) {
    return executeAsync(minioClient -> minioClient.getObjectRetention(args));
  }

  /**
   * Gets tags of an object.
   *
   * <p>Example:
   * <pre>
   * Tags tags =
   *     minioClient.getObjectTags(
   *         GetObjectTagsArgs.builder().bucket("my-bucketname").object("my-objectname").build());
   * </pre>
   *
   * @param args get object tags arguments
   * @return the tags
   */
  default Mono<Tags> getObjectTags(GetObjectTagsArgs args) {
    return executeAsync(minioClient -> minioClient.getObjectTags(args));
  }

  /**
   * Gets presigned URL of an object for HTTP method, expiry time and custom request parameters.
   *
   * <p>Example:
   * <pre>
   * // Get presigned URL string to delete 'my-objectname' in 'my-bucketname' and its life time
   * // is one day.
   * String url =
   *    minioClient.getPresignedObjectUrl(
   *        GetPresignedObjectUrlArgs.builder()
   *            .method(Method.DELETE)
   *            .bucket("my-bucketname")
   *            .object("my-objectname")
   *            .expiry(24 * 60 * 60)
   *            .build());
   * System.out.println(url);
   *
   * // Get presigned URL string to upload 'my-objectname' in 'my-bucketname'
   * // with response-content-type as application/json and life time as one day.
   * Map&lt;String, String&gt; reqParams = new HashMap&lt;String, String&gt;();
   * reqParams.put("response-content-type", "application/json");
   *
   * String url =
   *    minioClient.getPresignedObjectUrl(
   *        GetPresignedObjectUrlArgs.builder()
   *            .method(Method.PUT)
   *            .bucket("my-bucketname")
   *            .object("my-objectname")
   *            .expiry(1, TimeUnit.DAYS)
   *            .extraQueryParams(reqParams)
   *            .build());
   * System.out.println(url);
   *
   * // Get presigned URL string to download 'my-objectname' in 'my-bucketname' and its life time
   * // is 2 hours.
   * String url =
   *    minioClient.getPresignedObjectUrl(
   *        GetPresignedObjectUrlArgs.builder()
   *            .method(Method.GET)
   *            .bucket("my-bucketname")
   *            .object("my-objectname")
   *            .expiry(2, TimeUnit.HOURS)
   *            .build());
   * System.out.println(url);
   * </pre>
   *
   * @param args get pre-signed object url arguments
   * @return the pre-signed URL
   */
  default String getPresignedObjectUrl(GetPresignedObjectUrlArgs args) {
    return execute(minioClient -> minioClient.getPresignedObjectUrl(args));
  }

  /**
   * Gets form-data of {@link PostPolicy} of an object to upload its data using POST method.
   *
   * <p>Example:
   * <pre>
   * // Create new post policy for 'my-bucketname' with 7 days expiry from now.
   * PostPolicy policy = new PostPolicy("my-bucketname", ZonedDateTime.now().plusDays(7));
   *
   * // Add condition that 'key' (object name) equals to 'my-objectname'.
   * policy.addEqualsCondition("key", "my-objectname");
   *
   * // Add condition that 'Content-Type' starts with 'image/'.
   * policy.addStartsWithCondition("Content-Type", "image/");
   *
   * // Add condition that 'content-length-range' is between 64kiB to 10MiB.
   * policy.addContentLengthRangeCondition(64 * 1024, 10 * 1024 * 1024);
   *
   * Map&lt;String, String&gt; formData = minioClient.getPresignedPostFormData(policy);
   *
   * // Upload an image using POST object with form-data.
   * MultipartBody.Builder multipartBuilder = new MultipartBody.Builder();
   * multipartBuilder.setType(MultipartBody.FORM);
   * for (Map.Entry&lt;String, String&gt; entry : formData.entrySet()) {
   *   multipartBuilder.addFormDataPart(entry.getKey(), entry.getValue());
   * }
   * multipartBuilder.addFormDataPart("key", "my-objectname");
   * multipartBuilder.addFormDataPart("Content-Type", "image/png");
   *
   * // "file" must be added at last.
   * multipartBuilder.addFormDataPart(
   *     "file", "my-objectname", RequestBody.create(new File("Pictures/avatar.png"), null));
   *
   * Request request =
   *     new Request.Builder()
   *         .url("https://play.min.io/my-bucketname")
   *         .post(multipartBuilder.build())
   *         .build();
   * OkHttpClient httpClient = new OkHttpClient().newBuilder().build();
   * Response response = httpClient.newCall(request).execute();
   * if (response.isSuccessful()) {
   *   System.out.println("Pictures/avatar.png is uploaded successfully using POST object");
   * } else {
   *   System.out.println("Failed to upload Pictures/avatar.png");
   * }
   * </pre>
   *
   * @param policy post policy of an object
   * @return contains form-data to upload an object using POST method
   */
  @SuppressWarnings("JavadocLinkAsPlainText")
  default Map<String, String> getPresignedPostFormData(PostPolicy policy) {
    return execute(minioClient -> minioClient.getPresignedPostFormData(policy));
  }

  /**
   * Returns true if legal hold is enabled on an object.
   *
   * <p>Example:
   * <pre>
   * boolean status =
   *     s3Client.isObjectLegalHoldEnabled(
   *        IsObjectLegalHoldEnabledArgs.builder()
   *             .bucket("my-bucketname")
   *             .object("my-objectname")
   *             .versionId("object-versionId")
   *             .build());
   * if (status) {
   *   System.out.println("Legal hold is on");
   *  } else {
   *   System.out.println("Legal hold is off");
   *  }
   * </pre>
   *
   * @param args is object legel hold enabled arguments
   * @return true if legal hold is enabled
   */
  default Mono<Boolean> isObjectLegalHoldEnabled(IsObjectLegalHoldEnabledArgs args) {
    return executeAsync(minioClient -> minioClient.isObjectLegalHoldEnabled(args));
  }

  /**
   * Lists objects information optionally with versions of a bucket. Supports both the versions 1
   * and 2 of the S3 API. By default, the <a
   * href="https://docs.aws.amazon.com/AmazonS3/latest/API/API_ListObjectsV2.html">version 2</a> API
   * is used. <br>
   * <a href="https://docs.aws.amazon.com/AmazonS3/latest/API/API_ListObjects.html">Version 1</a>
   * can be used by passing the optional argument {@code useVersion1} as {@code true}.
   *
   * <p>Example:
   * <pre>
   * // Lists objects information.
   * Iterable&lt;Result&lt;Item&gt;&gt; results = minioClient.listObjects(
   *     ListObjectsArgs.builder().bucket("my-bucketname").build());
   *
   * // Lists objects information recursively.
   * Iterable&lt;Result&lt;Item&gt;&gt; results = minioClient.listObjects(
   *     ListObjectsArgs.builder().bucket("my-bucketname").recursive(true).build());
   *
   * // Lists maximum 100 objects information those names starts with 'E' and after
   * // 'ExampleGuide.pdf'.
   * Iterable&lt;Result&lt;Item&gt;&gt; results = minioClient.listObjects(
   *     ListObjectsArgs.builder()
   *         .bucket("my-bucketname")
   *         .startAfter("ExampleGuide.pdf")
   *         .prefix("E")
   *         .maxKeys(100)
   *         .build());
   *
   * // Lists maximum 100 objects information with version those names starts with 'E' and after
   * // 'ExampleGuide.pdf'.
   * Iterable&lt;Result&lt;Item&gt;&gt; results = minioClient.listObjects(
   *     ListObjectsArgs.builder()
   *         .bucket("my-bucketname")
   *         .startAfter("ExampleGuide.pdf")
   *         .prefix("E")
   *         .maxKeys(100)
   *         .includeVersions(true)
   *         .build());
   * </pre>
   *
   * @param args list objects arguments
   * @return lazy iterator contains object information
   */
  default Flux<Result<Item>> listObjects(ListObjectsArgs args) {
    return toFlux(execute(minioClient -> minioClient.listObjects(args)));
  }

  /**
   * Check whether an object exists or not.
   *
   * @param args status object arguments
   * @return {@code true} if the object exists, otherwise {@code false}
   */
  default Mono<Boolean> objectExists(StatObjectArgs args) {
    return statObject(args)
        .map(response -> !isEmpty(response))
        .switchIfEmpty(Mono.just(false))
        .onErrorReturn(
            t -> (t instanceof HttpStatusAware e) && e.status() == 404,
            false);
  }

  /**
   * Performs language model inference with the prompt and referenced object as context.
   *
   * @param args {@link PromptObjectArgs} object.
   * @return {@link PromptObjectResponse} object.
   */
  default Mono<PromptObjectResponse> promptObject(PromptObjectArgs args) {
    return executeAsync(minioClient -> minioClient.promptObject(args));
  }

  /**
   * Uploads data from a stream to an object.
   *
   * <p>Example:
   * <pre>
   * // Upload known sized input stream.
   * minioClient.putObject(
   *     PutObjectArgs.builder().bucket("my-bucketname").object("my-objectname").stream(
   *             inputStream, size, -1)
   *         .contentType("video/mp4")
   *         .build());
   *
   * // Upload unknown sized input stream.
   * minioClient.putObject(
   *     PutObjectArgs.builder().bucket("my-bucketname").object("my-objectname").stream(
   *             inputStream, -1, 10485760)
   *         .contentType("video/mp4")
   *         .build());
   *
   * // Create object ends with '/' (also called as folder or directory).
   * minioClient.putObject(
   *     PutObjectArgs.builder().bucket("my-bucketname").object("path/to/").stream(
   *             new ByteArrayInputStream(new byte[] {}), 0, -1)
   *         .build());
   *
   * // Upload input stream with headers and user metadata.
   * Map&lt;String, String&gt; headers = new HashMap&lt;&gt;();
   * headers.put("X-Amz-Storage-Class", "REDUCED_REDUNDANCY");
   * Map&lt;String, String&gt; userMetadata = new HashMap&lt;&gt;();
   * userMetadata.put("My-Project", "Project One");
   * minioClient.putObject(
   *     PutObjectArgs.builder().bucket("my-bucketname").object("my-objectname").stream(
   *             inputStream, size, -1)
   *         .headers(headers)
   *         .userMetadata(userMetadata)
   *         .build());
   *
   * // Upload input stream with server-side encryption.
   * minioClient.putObject(
   *     PutObjectArgs.builder().bucket("my-bucketname").object("my-objectname").stream(
   *             inputStream, size, -1)
   *         .sse(sse)
   *         .build());
   * </pre>
   *
   * @param args put object arguments
   * @return the object write response
   */
  default Mono<ObjectWriteResponse> putObject(PutObjectArgs args) {
    return executeAsync(minioClient -> minioClient.putObject(args));
  }

  /**
   * Uploads multiple objects with same content from single stream with optional metadata and tags.
   *
   * <p>Example:
   * <pre>
   * Map<String, String> map = new HashMap<>();
   * map.put("Project", "Project One");
   * map.put("User", "jsmith");
   * PutObjectFanOutResponse future =
   *     minioClient.putObjectFanOut(
   *         PutObjectFanOutArgs.builder().bucket("my-bucketname").stream(
   *                 new ByteArrayInputStream("somedata".getBytes(StandardCharsets.UTF_8)), 8)
   *             .entries(
   *                 Arrays.asList(
   *                     new PutObjectFanOutEntry[] {
   *                       PutObjectFanOutEntry.builder().key("fan-out.0").build(),
   *                       PutObjectFanOutEntry.builder().key("fan-out.1").tags(map).build()
   *                     }))
   *             .build());
   * </pre>
   *
   * @param args {@link PutObjectFanOutArgs} object.
   * @return {@link PutObjectFanOutResponse} object.
   */
  default Mono<PutObjectFanOutResponse> putObjectFanOut(PutObjectFanOutArgs args) {
    return executeAsync(minioClient -> minioClient.putObjectFanOut(args));
  }

  /**
   * Removes an object.
   *
   * <p>Example:
   * <pre>
   * // Remove object.
   * minioClient.removeObject(
   *     RemoveObjectArgs.builder().bucket("my-bucketname").object("my-objectname").build());
   *
   * // Remove versioned object.
   * minioClient.removeObject(
   *     RemoveObjectArgs.builder()
   *         .bucket("my-bucketname")
   *         .object("my-versioned-objectname")
   *         .versionId("my-versionid")
   *         .build());
   *
   * // Remove versioned object bypassing Governance mode.
   * minioClient.removeObject(
   *     RemoveObjectArgs.builder()
   *         .bucket("my-bucketname")
   *         .object("my-versioned-objectname")
   *         .versionId("my-versionid")
   *         .bypassRetentionMode(true)
   *         .build());
   * </pre>
   *
   * @param args remove object arguments
   */
  default Mono<Void> removeObject(RemoveObjectArgs args) {
    return executeAsync(minioClient -> minioClient
        .removeObject(args));
  }

  /**
   * Removes multiple objects lazily. Its required to iterate the returned Iterable to perform
   * removal.
   *
   * <p>Example:
   * <pre>
   * List&lt;DeleteObject&gt; objects = new LinkedList&lt;&gt;();
   * objects.add(new DeleteObject("my-objectname1"));
   * objects.add(new DeleteObject("my-objectname2"));
   * objects.add(new DeleteObject("my-objectname3"));
   * Iterable&lt;Result&lt;DeleteResult.Error&gt;&gt; results =
   *     minioClient.removeObjects(
   *         RemoveObjectsArgs.builder().bucket("my-bucketname").objects(objects).build());
   * for (Result&lt;DeleteResult.Error&gt; result : results) {
   *   DeleteResult.Error error = errorResult.get();
   *   System.out.println(
   *       "Error in deleting object " + error.objectName() + "; " + error.message());
   * }
   * </pre>
   *
   * @param args the objects to remove
   * @return lazy iterator contains object removal status
   */
  default Flux<Result<DeleteResult.Error>> removeObjects(RemoveObjectsArgs args) {
    return toFlux(execute(minioClient -> minioClient.removeObjects(args)));
  }

  /**
   * Restores an object.
   *
   * <p>Example:
   * <pre>
   * // Restore object.
   * minioClient.restoreObject(
   *     RestoreObjectArgs.builder()
   *         .bucket("my-bucketname")
   *         .object("my-objectname")
   *         .request(new RestoreRequest(null, null, null, null, null, null))
   *         .build());
   *
   * // Restore versioned object.
   * minioClient.restoreObject(
   *     RestoreObjectArgs.builder()
   *         .bucket("my-bucketname")
   *         .object("my-versioned-objectname")
   *         .versionId("my-versionid")
   *         .request(new RestoreRequest(null, null, null, null, null, null))
   *         .build());
   * </pre>
   *
   * @param args {@link RestoreObjectArgs} object.
   */
  default Mono<Void> restoreObject(RestoreObjectArgs args) {
    return executeAsync(minioClient -> minioClient
        .restoreObject(args));
  }

  /**
   * Selects content of an object by SQL expression.
   *
   * <p>Example:
   * <pre>
   * String sqlExpression = "select * from S3Object";
   * InputSerialization is =
   *     new InputSerialization(null, false, null, null, FileHeaderInfo.USE, null, null,
   *         null);
   * OutputSerialization os =
   *     new OutputSerialization(null, null, null, QuoteFields.ASNEEDED, null);
   * SelectResponseStream stream =
   *     minioClient.selectObjectContent(
   *       SelectObjectContentArgs.builder()
   *       .bucket("my-bucketname")
   *       .object("my-objectname")
   *       .sqlExpression(sqlExpression)
   *       .inputSerialization(is)
   *       .outputSerialization(os)
   *       .requestProgress(true)
   *       .build());
   *
   * byte[] buf = new byte[512];
   * int bytesRead = stream.read(buf, 0, buf.length);
   * System.out.println(new String(buf, 0, bytesRead, StandardCharsets.UTF_8));
   *
   * Stats stats = stream.stats();
   * System.out.println("bytes scanned: " + stats.bytesScanned());
   * System.out.println("bytes processed: " + stats.bytesProcessed());
   * System.out.println("bytes returned: " + stats.bytesReturned());
   *
   * stream.close();
   * </pre>
   *
   * @param args the select object content arguments
   * @return the select response stream
   */
  default SelectResponseStream selectObjectContent(SelectObjectContentArgs args) {
    return execute(minioClient -> minioClient.selectObjectContent(args));
  }

  /**
   * Sets retention configuration to an object.
   *
   * <p>Example:
   * <pre>
   *  Retention retention = new Retention(
   *       RetentionMode.COMPLIANCE, ZonedDateTime.now().plusYears(1));
   *  minioClient.setObjectRetention(
   *      SetObjectRetentionArgs.builder()
   *          .bucket("my-bucketname")
   *          .object("my-objectname")
   *          .config(config)
   *          .bypassGovernanceMode(true)
   *          .build());
   * </pre>
   *
   * @param args set object retention arguments
   */
  default Mono<Void> setObjectRetention(SetObjectRetentionArgs args) {
    return executeAsync(minioClient -> minioClient
        .setObjectRetention(args));
  }

  /**
   * Sets tags to an object.
   *
   * <p>Example:
   * <pre>
   * Map&lt;String, String&gt; map = new HashMap&lt;&gt;();
   * map.put("Project", "Project One");
   * map.put("User", "jsmith");
   * minioClient.setObjectTags(
   *     SetObjectTagsArgs.builder()
   *         .bucket("my-bucketname")
   *         .object("my-objectname")
   *         .tags((map)
   *         .build());
   * </pre>
   *
   * @param args set object tags arguments
   */
  default Mono<Void> setObjectTags(SetObjectTagsArgs args) {
    return executeAsync(minioClient -> minioClient
        .setObjectTags(args));
  }

  /**
   * Gets information of an object.
   *
   * <p>Example:
   * <pre>
   * // Get information of an object.
   * ObjectStat objectStat =
   *     minioClient.statObject(
   *         StatObjectArgs.builder().bucket("my-bucketname").object("my-objectname").build());
   *
   * // Get information of SSE-C encrypted object.
   * ObjectStat objectStat =
   *     minioClient.statObject(
   *         StatObjectArgs.builder()
   *             .bucket("my-bucketname")
   *             .object("my-objectname")
   *             .ssec(ssec)
   *             .build());
   *
   * // Get information of a versioned object.
   * ObjectStat objectStat =
   *     minioClient.statObject(
   *         StatObjectArgs.builder()
   *             .bucket("my-bucketname")
   *             .object("my-objectname")
   *             .versionId("version-id")
   *             .build());
   *
   * // Get information of a SSE-C encrypted versioned object.
   * ObjectStat objectStat =
   *     minioClient.statObject(
   *         StatObjectArgs.builder()
   *             .bucket("my-bucketname")
   *             .object("my-objectname")
   *             .versionId("version-id")
   *             .ssec(ssec)
   *             .build());
   * </pre>
   *
   * @param args status object arguments
   * @return populated object information and metadata
   */
  default Mono<StatObjectResponse> statObject(StatObjectArgs args) {
    return executeAsync(minioClient -> minioClient.statObject(args));
  }

  /**
   * Uploads data from a file to an object.
   *
   * <p>Example:
   * <pre>
   * // Upload an JSON file.
   * minioClient.uploadObject(
   *     UploadObjectArgs.builder()
   *         .bucket("my-bucketname").object("my-objectname").filename("person.json").build());
   *
   * // Upload a video file.
   * minioClient.uploadObject(
   *     UploadObjectArgs.builder()
   *         .bucket("my-bucketname")
   *         .object("my-objectname")
   *         .filename("my-video.avi")
   *         .contentType("video/mp4")
   *         .build());
   * </pre>
   *
   * @param args upload object arguments
   * @param deleteMode delete mode
   * @return the object write response
   */
  default Mono<ObjectWriteResponse> uploadObject(UploadObjectArgs args, DeleteMode deleteMode) {
    final Path file = Paths.get(args.filename());
    return executeAsync(minioClient -> minioClient.uploadObject(args))
        .doOnSuccess(response -> {
          if (DeleteMode.NEVER != deleteMode) {
            try {
              Files.delete(file);
            } catch (Exception e) {
              // ignored
            }
          }
        })
        .doOnError(t -> {
          if (DeleteMode.ALWAYS == deleteMode) {
            try {
              Files.delete(file);
            } catch (Exception e) {
              // ignored
            }
          }
        });
  }

}
