package com.redhat.techexchange.whosaidit.domain;

public class GameStartedEvent extends BaseEvent {

  Game game;

  public GameStartedEvent() {
    super(EventType.GameStartedEvent);
  }

  public GameStartedEvent(EventType eventType, Game game) {
    super(EventType.GameStartedEvent);
    this.game = game;
  }
}



