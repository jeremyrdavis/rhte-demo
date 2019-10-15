package com.redhat.techexchange.whosaidit.domain;

public class GameStartedEvent extends BaseEvent implements Event{

  Game game;

  public GameStartedEvent() {
    super(EventType.GameStartedEvent);
  }

  public GameStartedEvent(EventType eventType, Game game) {
    super(EventType.GameStartedEvent);
    this.game = game;
  }

  public Game getGame() {
    return game;
  }

  public void setGame(Game game) {
    this.game = game;
  }
}



