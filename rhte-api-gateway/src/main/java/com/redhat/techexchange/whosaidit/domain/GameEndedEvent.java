package com.redhat.techexchange.whosaidit.domain;

public class GameEndedEvent extends BaseEvent {

  public GameEndedEvent() {
    super(EventType.GAME_ENDED);
  }

}


