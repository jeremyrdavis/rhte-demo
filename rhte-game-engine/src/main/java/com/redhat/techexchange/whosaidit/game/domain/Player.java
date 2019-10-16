package com.redhat.techexchange.whosaidit.game.domain;

public class Player {

  String name;

  public Player(String name) {
    this.name = name;
  }

  public Player() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
