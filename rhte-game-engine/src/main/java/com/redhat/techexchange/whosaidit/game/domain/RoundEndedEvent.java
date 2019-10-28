package com.redhat.techexchange.whosaidit.game.domain;

public class RoundEndedEvent implements DomainEvent {

  DomainEventType domainEventType = DomainEventType.RoundEndedEvent;

  Round round;

  public RoundEndedEvent() {
  }

  public RoundEndedEvent(DomainEventType domainEventType, Round round) {
    this.domainEventType = domainEventType;
    this.round = round;
  }

  public DomainEventType getDomainEventType() {
    return domainEventType;
  }

  public void setDomainEventType(DomainEventType domainEventType) {
    this.domainEventType = domainEventType;
  }

  public Round getRound() {
    return round;
  }

  public void setRound(Round round) {
    this.round = round;
  }
}
