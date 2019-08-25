package com.redhat.rhte.demos;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class Quote extends PanacheEntity {

  public String author;
  public String text;

}
