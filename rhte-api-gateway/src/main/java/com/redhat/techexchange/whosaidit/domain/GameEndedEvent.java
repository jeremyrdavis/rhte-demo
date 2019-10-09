package com.redhat.techexchange.whosaidit.domain;

public class GameEndedEvent extends Event {

  public GameEndedEvent() {
    super(EventType.GAME_ENDED);
  }
}


