package com.redhat.techexchange.whosaidit.game;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

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
}
