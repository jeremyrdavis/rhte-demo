package com.redhat.techexchange.whosaidit.game.infrastructure;

import com.redhat.techexchange.whosaidit.game.domain.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.*;

@ApplicationScoped
public class Referee {

  @Inject
  GameStartedEventHandler gameStartedEventHandler;

  @Inject
  RoundStartedHandler roundStartedHandler;

  @Inject
  RoundEndedEventHandler roundEndedEventHandler;

  @Inject
  NextQuestionHandler nextQuoteEventHandler;

  Long currentGameId;

  Integer currentRound = 1;

  Set<String> players = new HashSet<>();

  @Transactional
  public GameCreatedEvent createGame() {

    List<Quote> allQuotes = Quote.listAll();
    Collections.shuffle(allQuotes);
    Map<Integer,Round> rounds = new HashMap<>();

    for (int i = 0; i < 4; i++) {
      Round r = new Round();
      r.status = RoundStatus.CREATED;
      for (int j = 0; j < 4; j++) {
        Quote quote = allQuotes.remove(j);
        r.addQuote(quote);
      }
      rounds.put(i,r);
    }

    GameCreatedEvent gameCreatedEvent = Game.createGame(rounds);
    gameCreatedEvent.getGame().persist();
    onGameStart(gameCreatedEvent.getGame());
    currentGameId = gameCreatedEvent.getGame().id;
    return gameCreatedEvent;
  }

  public void startRound() {

    if (currentGameId == null) {
      createGame();
    }

    Game game = Game.findById(currentGameId);
    Round round = game.rounds.get(currentRound);

    onRoundStart(round);

    for (int i = 0; i < 4; i++) {
      Quote q = round.quotes.get(i + 1);
      System.out.println(q.text);
      onNextQuote(q);
      try {
        Thread.sleep(30000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    game.completeRound(currentRound);
    currentRound++;
    addWinner();
  }

  @Transactional
  void addWinner() {
    Game game = Game.findById(currentGameId);
    Round round = game.getRounds().get(4);
    round.winner = pickWinner();
    game.persist();
    onRoundEnd(round);
  }

  private String pickWinner() {

    List<String> playersList = new ArrayList<>(players);
    // add a couple of people to make sure we have someone when testing
    if (playersList.isEmpty()) {
      playersList.add("@Tortugasl");
      playersList.add("@thedesignatic");
    }
    Collections.shuffle(playersList);
    return playersList.get(0);
  }

  public Round getCurrentRound() {
    Game game = Game.findById(currentGameId);
    return game.getCurrentRound();
  }

  @Transactional
  private Game initializeRound() {
    Game game = Game.findById(currentGameId);
    game.startRound(currentRound);
    game.persist();
    return game;
  }

  public void addPlayer(String player) {
    this.players.add(player);
  }

  void onNextQuote(Quote quote) {
    NextQuoteEvent event = new NextQuoteEvent(DomainEventType.NextQuoteEvent, quote);
    nextQuoteEventHandler.handle(event);
    System.out.println("onNextQuote");
  }

  void onGameStart(Game game) {
    gameStartedEventHandler.handle(game);
    System.out.println("onGameStart");
  }

  private void onRoundStart(Round round) {
    System.out.println("onRoundStart");
    roundStartedHandler.handle(new RoundStartedEvent(DomainEventType.RoundStartedEvent, round));
  }

  void onRoundEnd(Round round) {
    System.out.println("onRoundEnd");
    roundEndedEventHandler.handle(round);
  }

  public void testQuote() {
    onNextQuote(new Quote("Test " + UUID.randomUUID().toString(), Author.Shakespeare));
  }


  public GameStartedEvent startGame(Long id) {

    Game game = Game.findById(id);
    game.status = GameStatus.IN_PROGRESS;
    game.persist();
    return new GameStartedEvent(game);
  }
}
