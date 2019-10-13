package com.redhat.techexchange.whosaidit.game.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

  public static Quote randomQuote(Set<Quote> existingQuotes) {

    List<Quote> quotes = listAll();

    if (existingQuotes.isEmpty()) {
      Collections.shuffle(quotes);
      return quotes.get(0);
    }else{
      List<Quote> newQuotes = quotes
        .stream()
        .filter(q -> existingQuotes.contains(q))
        .collect(Collectors.toList());
      Collections.shuffle(newQuotes);
      return newQuotes.get(0);
    }
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
      .append("id", id)
      .append("text", text)
      .append("author", author.name()).toString();
  }
}
