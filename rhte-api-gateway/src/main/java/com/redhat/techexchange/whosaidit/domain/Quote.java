package com.redhat.techexchange.whosaidit.domain;

public class Quote implements WhoSaidItObject{

  public String text;

  Author author;

  public Quote(String text, Author author) {
    this.text = text;
    this.author = author;
  }

  public enum Author {

    Shakespeare, Swarzeneggar, Hamilton;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Author getAuthor() {
    return author;
  }

  public void setAuthor(Author author) {
    this.author = author;
  }
}
