package com.redhat.rhte.demos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

  @JsonIgnore
  public static Quote randomNewQuote(List<Quote> existingQuotes) {

    List<Quote> quotes = listAll();

    List<Quote> newQuotes = quotes
      .stream()
      .filter(q -> existingQuotes.contains(q))
      .collect(Collectors.toList());

    Collections.shuffle(quotes);
    return quotes.get(0);
  }

  @JsonIgnore
  public static Quote randomNewQuote() {
    List<Quote> quotes = listAll();
    Collections.shuffle(quotes);
    return quotes.get(0);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Quote)) return false;
    Quote quote = (Quote) o;
    return Objects.equals(author, quote.author) &&
      Objects.equals(text, quote.text);
  }

  @Override
  public int hashCode() {
    return Objects.hash(author, text);
  }
}
