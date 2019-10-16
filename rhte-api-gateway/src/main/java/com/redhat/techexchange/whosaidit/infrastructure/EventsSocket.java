package com.redhat.techexchange.whosaidit.infrastructure;

import com.redhat.techexchange.whosaidit.domain.Event;
import io.quarkus.vertx.ConsumeEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/stream")
@ApplicationScoped
public class EventsSocket {

  Map<String, Session> sessions = new ConcurrentHashMap<>();

  @OnOpen
  public void onOpen(Session session) {
    if (sessions == null) {
      sessions = new HashMap<>();
    }
    sessions.put("client", session);
  }

  @OnError
  public void onError(Session session, Throwable throwable) {
    System.out.println(throwable);
  }

  @OnClose
  public void onClose(Session session) {
    sessions.remove("client");
  }

  public void broadcast(String event) {
    System.out.println("Broadcasting: " + event + " to " + sessions.size() + " sessions");
      sessions.values().forEach(s -> {
      s.getAsyncRemote().sendObject(event, result ->  {
        if (result.getException() != null) {
          System.out.println("Unable to send message: " + result.getException());
        }
      });
    });
  }
}
