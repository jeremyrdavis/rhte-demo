package com.redhat.techexchange.whosaidit.game.domain;

/**
 * Marker interface for all domain Events
 */
public interface DomainEvent {

  public DomainEventType getDomainEventType();

}
