package com.redhat.techexchange.whosaidit;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PublishStatusCommand {

  @JsonProperty("status")
  String status;

  public PublishStatusCommand() {
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}
