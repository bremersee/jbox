package org.bremersee.minio.reactive;

import io.minio.MinioAsyncClient;
import java.util.concurrent.CompletionException;
import org.bremersee.minio.DefaultMinioErrorHandler;
import org.springframework.util.ErrorHandler;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The reactive minio template.
 */
public class ReactiveMinioTemplate implements ReactiveMinioOperations {

  private final MinioAsyncClient client;

  private ErrorHandler errorHandler = new DefaultMinioErrorHandler();

  /**
   * Instantiates a new reactive minio template.
   *
   * @param client the async client
   */
  public ReactiveMinioTemplate(MinioAsyncClient client) {
    this.client = client;
  }

  /**
   * Sets error handler.
   *
   * @param errorHandler the error handler
   */
  public void setErrorHandler(ErrorHandler errorHandler) {
    if (errorHandler != null) {
      this.errorHandler = errorHandler;
    }
  }

  /**
   * Copies minio template.
   *
   * @return the minio template
   */
  public ReactiveMinioTemplate copy() {
    return copy(null);
  }

  /**
   * Copies minio template.
   *
   * @param errorHandler the error handler
   * @return the minio template
   */
  public ReactiveMinioTemplate copy(ErrorHandler errorHandler) {
    ReactiveMinioTemplate clone = new ReactiveMinioTemplate(client);
    clone.setErrorHandler(errorHandler);
    return clone;
  }

  @Override
  public <T> T execute(MinioClientCallback<T> callback) {

    try {
      return executeInternal(callback);

    } catch (Exception e) {
      errorHandler.handleError(e);
      return null;
    }
  }

  private <T> T executeInternal(MinioClientCallback<T> callback) throws Exception {
    try {
      return callback.doWithMinioClient(client);
    } catch (CompletionException e) {
      client.throwMinioException(e);
      return null;
    }
  }

  @Override
  public <T> Mono<T> executeAsync(MinioAsyncClientCallback<T> callback) {
    return Mono.fromFuture(callback.doWithMinioClient(client))
        .onErrorMap(e -> {
          try {
            errorHandler.handleError(e);
          } catch (Exception ex) {
            return ex;
          }
          return e;
        });
  }

  @Override
  public <T> Flux<T> toFlux(Iterable<T> iterable) {
    return Flux.fromIterable(iterable)
        .onErrorMap(e -> {
          try {
            errorHandler.handleError(e);
          } catch (Exception ex) {
            return ex;
          }
          return e;
        });
  }

}
