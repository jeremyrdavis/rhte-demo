package com.redhat.techexchange.whosaidit;

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.websocket.*;
import java.net.URI;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

@QuarkusTest
public class WebSocketTest {

  @TestHTTPResource("/events")
  URI uri;

  private static final LinkedBlockingDeque<String> MESSAGES = new LinkedBlockingDeque<>();

  @Test
  public void testWebsocketChat() throws Exception {
    try(Session session = ContainerProvider.getWebSocketContainer().connectToServer(Client.class, uri)) {
      Assertions.assertEquals("CONNECT", MESSAGES.poll(10, TimeUnit.SECONDS));
      Assertions.assertEquals("User stu joined", MESSAGES.poll(10, TimeUnit.SECONDS));
      session.getAsyncRemote().sendText("hello world");
      Assertions.assertEquals(">> stu: hello world", MESSAGES.poll(10, TimeUnit.SECONDS));
    }
  }

  @ClientEndpoint
  public static class Client {

    @OnOpen
    public void open() {
      MESSAGES.add("CONNECT");
    }

    @OnMessage
    void message(String msg) {
      MESSAGES.add(msg);
    }

  }
}
