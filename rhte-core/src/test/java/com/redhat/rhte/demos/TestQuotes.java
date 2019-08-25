package com.redhat.rhte.demos;

public enum TestQuotes {

  ALL_THE_WORLDS_A_STAGE("All the world's a stage, and all the men and women only players");

  private String text;

  private TestQuotes(String text) {

    this.text = text;
  }

  public String text() {

    return this.text;
  }
}
