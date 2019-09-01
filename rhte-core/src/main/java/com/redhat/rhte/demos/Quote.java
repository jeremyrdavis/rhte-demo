package com.redhat.rhte.demos;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;

import javax.persistence.Entity;
import javax.swing.text.html.Option;
import java.util.*;

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

  public static Quote randomNewQuote(List<Long> quoteIds) {
    return find("SELECT q FROM Quote WHERE q.id NOT IN ? ORDER BY RAND()", quoteIds).firstResult();
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
