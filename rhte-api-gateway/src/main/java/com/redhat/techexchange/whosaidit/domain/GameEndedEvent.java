package com.redhat.techexchange.whosaidit.domain;

public class GameEndedEvent extends BaseEvent {

  Game game;

  public GameEndedEvent() {
    super(EventType.GameEndedEvent);
  }

  public GameEndedEvent(EventType eventType, Game game) {
    super(EventType.GameEndedEvent);
    this.game = game;
  }

  public Game getGame() {
    return game;
  }

  public void setGame(Game game) {
    this.game = game;
  }
}


