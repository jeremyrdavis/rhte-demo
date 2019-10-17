package com.redhat.techexchange.whosaidit.domain;

public class RoundEndedEvent implements Event {

  EventType eventType = EventType.RoundEndedEvent;

  public RoundEndedEvent() {
  }

  String winner;

  public RoundEndedEvent(String winner) {
    this.winner = winner;
  }

  public String getEventType() {
    return eventType.title;
  }

  public String getWinner() {
    return winner;
  }

  public void setWinner(String winner) {
    this.winner = winner;
  }
}



