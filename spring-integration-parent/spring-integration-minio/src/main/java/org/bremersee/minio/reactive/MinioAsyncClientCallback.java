package org.bremersee.minio.reactive;

import io.minio.MinioAsyncClient;
import java.util.concurrent.CompletableFuture;

public interface MinioAsyncClientCallback<T> {

  CompletableFuture<T> doWithMinioClient(MinioAsyncClient minioAsyncClient);

}
