package com.redhat.rhte.demos;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Round extends PanacheEntity {

  @Transient
  public RoundStatus status;

  @OneToMany
  @JoinColumn(name = "quote_id")
  public Set<Quote> quotes;

  public Round() {

    this.quotes = new HashSet<Quote>();
  }

  public void start() {

    this.status = RoundStatus.ACTIVE;
  }

  public void stop() {

    this.status = RoundStatus.COMPLETED;
  }

  public boolean addQuote(Quote quote) {

    return this.quotes.add(quote);
  }

  public Set<Quote> getQuotes() {
    return this.quotes;
  }

  public enum RoundStatus {

    ACTIVE, COMPLETED;
  }

}
