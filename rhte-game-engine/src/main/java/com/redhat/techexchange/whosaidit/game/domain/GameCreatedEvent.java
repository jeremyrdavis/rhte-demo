package com.redhat.techexchange.whosaidit.game.domain;

public class GameCreatedEvent implements DomainEvent {

  DomainEventType eventType = DomainEventType.GameCreatedEvent;

  Game game;

  public GameCreatedEvent() {
  }

  public GameCreatedEvent(Game game) {
    this.game = game;
  }

  public GameCreatedEvent(DomainEventType domainEventType, Game game) {
    this.game = game;
  }

  public DomainEventType getDomainEventType() {
    return eventType;
  }

  public Game getGame() {
    return game;
  }

  public void setGame(Game game) {
    this.game = game;
  }
}
