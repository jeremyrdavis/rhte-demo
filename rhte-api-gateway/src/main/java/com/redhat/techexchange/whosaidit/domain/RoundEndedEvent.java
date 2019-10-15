package com.redhat.techexchange.whosaidit.domain;

public class RoundEndedEvent extends BaseEvent {

  Round round;

  public RoundEndedEvent() {
    super(EventType.RoundEndedEvent);
  }

  String winner;

  public RoundEndedEvent(EventType eventType, Round round) {
    super(eventType);
    this.round = round;
  }

  public Round getRound() {
    return round;
  }

  public void setRound(Round round) {
    this.round = round;
  }

  public String getWinner() {
    return winner;
  }

  public void setWinner(String winner) {
    this.winner = winner;
  }
}



