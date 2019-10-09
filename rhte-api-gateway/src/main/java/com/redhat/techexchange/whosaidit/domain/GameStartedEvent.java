package com.redhat.techexchange.whosaidit.domain;

public class GameStartedEvent extends BaseEvent {

  Game game;

  public GameStartedEvent() {
    super(EventType.GAME_STARTED);
  }
}



