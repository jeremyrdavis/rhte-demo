package com.redhat.techexchange.whosaidit.domain;

public class GuessReceivedEvent extends BaseEvent implements Event{

  String player;

  public GuessReceivedEvent() {
    super(EventType.GuessReceivedEvent);
  }

  public GuessReceivedEvent(EventType eventType, String player) {
    this.eventType = EventType.GuessReceivedEvent;
    this.player = player;
  }

  public String getPlayer() {
    return player;
  }

  public void setPlayer(String player) {
    this.player = player;
  }
}


