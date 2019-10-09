package com.redhat.techexchange.whosaidit.domain;

import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.json.bind.config.PropertyOrderStrategy;

@JsonbPropertyOrder(PropertyOrderStrategy.ANY)
public class Game {

  GameStatus status;

  Round firstRound;

  Round secondRound;

  Round thirdRound;

  Round fourthRound;

  public GameStatus getStatus() {
    return status;
  }

  public Round getFirstRound() {
    return firstRound;
  }

  public Round getSecondRound() {
    return secondRound;
  }

  public Round getThirdRound() {
    return thirdRound;
  }

  public Round getFourthRound() {
    return fourthRound;
  }

  public void setStatus(GameStatus status) {
    this.status = status;
  }

  public void setFirstRound(Round firstRound) {
    this.firstRound = firstRound;
  }

  public void setSecondRound(Round secondRound) {
    this.secondRound = secondRound;
  }

  public void setThirdRound(Round thirdRound) {
    this.thirdRound = thirdRound;
  }

  public void setFourthRound(Round fourthRound) {
    this.fourthRound = fourthRound;
  }
}
