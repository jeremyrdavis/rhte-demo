package com.redhat.techexchange.whosaidit;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PublishStatusCommand {

  @JsonProperty("status")
  String status;

  @JsonProperty("newRound")
  boolean newRound;

  public PublishStatusCommand() {

  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public boolean isNewRound() {
    return newRound;
  }

  public void setNewRound(boolean newRound) {
    this.newRound = newRound;
  }


}
