package com.redhat.techexchange.whosaidit.domain;

public class Round {

  Quote firstQuote;

  Quote secondQuote;

  Quote thirdQuote;

  Quote fourthQuote;

  public Quote getFirstQuote() {
    return firstQuote;
  }

  public void setFirstQuote(Quote firstQuote) {
    this.firstQuote = firstQuote;
  }

  public Quote getSecondQuote() {
    return secondQuote;
  }

  public void setSecondQuote(Quote secondQuote) {
    this.secondQuote = secondQuote;
  }

  public Quote getThirdQuote() {
    return thirdQuote;
  }

  public void setThirdQuote(Quote thirdQuote) {
    this.thirdQuote = thirdQuote;
  }

  public Quote getFourthQuote() {
    return fourthQuote;
  }

  public void setFourthQuote(Quote fourthQuote) {
    this.fourthQuote = fourthQuote;
  }
}
