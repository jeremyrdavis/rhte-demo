package com.redhat.techexchange.whosaidit.game.domain;

public class GameStartedEvent implements DomainEvent {

  DomainEventType domainEventType = DomainEventType.GameStartedEvent;

  Game game;

  public GameStartedEvent() {
  }

  public GameStartedEvent(Game game) {
    this.game = game;
  }

  public GameStartedEvent(DomainEventType domainEventType, Game game) {
    this.game = game;
  }

  public DomainEventType getDomainEventType() {
    return domainEventType;
  }

  public void setDomainEventType(DomainEventType domainEventType) {
    this.domainEventType = domainEventType;
  }

  public Game getGame() {
    return game;
  }

  public void setGame(Game game) {
    this.game = game;
  }
}
