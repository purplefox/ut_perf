package io.vertx.perf.undertow;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import io.undertow.util.Headers;
import org.xnio.Options;

/**
 * Created by tim on 15/06/15.
 */
public class PlaintextServer {

  public static void main(String[] args) {
    new PlaintextServer();
  }

  public PlaintextServer() {
    Undertow.builder()
      .addHttpListener(8080, "localhost")
      .setBufferSize(1024 * 16)
      .setIoThreads(Runtime.getRuntime().availableProcessors() * 2) //this seems slightly faster in some configurations
      .setSocketOption(Options.BACKLOG, 10000)
      .setServerOption(UndertowOptions.ALWAYS_SET_KEEP_ALIVE, false) //don't send a keep-alive header for HTTP/1.1 requests, as it is not required
      .setHandler(Handlers.date(Handlers.header(new PlaintextHandler(), Headers.SERVER_STRING, "U-tow")))
      .setWorkerThreads(200)
      .build()
      .start();
  }
}
