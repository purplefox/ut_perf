package io.vertx.perf.undertow;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import java.nio.ByteBuffer;

/**
 * Created by tim on 15/06/15.
 */
final class PlaintextHandler implements HttpHandler {
  private static final ByteBuffer buffer;
  private static final String MESSAGE = "Hello, World!";
  private static final String TEXT_PLAIN = "text/plain";


  static {
    buffer = ByteBuffer.allocateDirect(MESSAGE.length());
    try {
      buffer.put(MESSAGE.getBytes("US-ASCII"));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    buffer.flip();
  }

  @Override
  public void handleRequest(HttpServerExchange exchange) throws Exception {
    exchange.getResponseHeaders().put(
      Headers.CONTENT_TYPE, TEXT_PLAIN);
    exchange.getResponseSender().send(buffer.duplicate());
  }
}
