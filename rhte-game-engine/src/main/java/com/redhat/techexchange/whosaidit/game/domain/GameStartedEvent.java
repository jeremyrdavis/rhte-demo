package com.redhat.techexchange.whosaidit.game.domain;

public class GameStartedEvent implements Event{

  EventType eventType;

  Game game;

  public GameStartedEvent() {
  }

  public GameStartedEvent(EventType eventType, Game game) {
    this.eventType = eventType;
    this.game = game;
  }

  public EventType getEventType() {
    return eventType;
  }

  public void setEventType(EventType eventType) {
    this.eventType = eventType;
  }

  public Game getGame() {
    return game;
  }

  public void setGame(Game game) {
    this.game = game;
  }
}
