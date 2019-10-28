package com.redhat.techexchange.whosaidit.game.domain;

public class NextQuoteEvent implements DomainEvent {

  DomainEventType domainEventType;

  Quote quote;

  public NextQuoteEvent() {
  }

  public NextQuoteEvent(DomainEventType domainEventType, Quote quote) {
    this.domainEventType = domainEventType;
    this.quote = quote;
  }

  public DomainEventType getDomainEventType() {
    return domainEventType;
  }

  public void setDomainEventType(DomainEventType domainEventType) {
    this.domainEventType = domainEventType;
  }

  public Quote getQuote() {
    return quote;
  }

  public void setQuote(Quote quote) {
    this.quote = quote;
  }

  @Override
  public String toString() {
    return new StringBuilder()
      .append("NextQuoteEvent[eventType=")
      .append(domainEventType.title)
      .append(",quote=[")
      .append(quote.text)
      .append(",author=")
      .append(quote.author.name()).toString();
  }
}
