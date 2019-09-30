package com.redhat.techexchange.whosaidit.game;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Game extends PanacheEntity {

  @OneToMany(fetch = FetchType.EAGER)
  @JoinColumn(name = "game_id")
  @Cascade(CascadeType.ALL)
  Set<Round> rounds = new HashSet<>();


}
