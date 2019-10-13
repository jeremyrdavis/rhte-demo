package com.redhat.techexchange.whosaidit.historyservice.domain;

import com.redhat.techexchange.whosaidit.historyservice.domain.Author;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Quote {

  @Id
  public String id;

  public String text;

  @Enumerated(EnumType.STRING)
  Author author;

  public Quote() {
  }

  public Quote(String text, Author author) {
    this.text = text;
    this.author = author;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
      .append("id", id)
      .append("text", text)
      .append("author", author.name()).toString();
  }
}
