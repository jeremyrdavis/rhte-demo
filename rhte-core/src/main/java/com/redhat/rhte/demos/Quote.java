package com.redhat.rhte.demos;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import java.util.Random;

@Entity
public class Quote extends PanacheEntity {

  public String author;
  public String text;

  public Quote() {
  }

  public Quote(String author, String text) {

    this.author = author;
    this.text = text;
  }

  public Quote findRandomQuote() {

    return Quote.find("from Quote order by rand()").singleResult();
  }
}
