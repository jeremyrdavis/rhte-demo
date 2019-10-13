package com.redhat.techexchange.whosaidit.historyservice.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Quote extends PanacheEntity {

  public String text;

  @Enumerated(EnumType.STRING)
  Author author;

  public Quote() {
  }

  public Quote(String text, Author author) {
    this.text = text;
    this.author = author;
  }
}
