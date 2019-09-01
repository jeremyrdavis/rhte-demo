package com.redhat.rhte.demos;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;

import javax.persistence.Entity;
import javax.swing.text.html.Option;
import java.util.*;
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

  public static Quote randomNewQuote(List<Quote> existingQuotes) {

    List<Quote> quotes = listAll();

    List<Quote> newQuotes = quotes
      .stream()
      .filter(q -> existingQuotes.contains(q))
      .collect(Collectors.toList());

    Collections.shuffle(quotes);
    return quotes.get(0);
  }

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
