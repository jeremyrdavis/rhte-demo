package com.redhat.techexchange.whosaidit.game;

import com.redhat.techexchange.whosaidit.game.domain.*;
import com.redhat.techexchange.whosaidit.game.infrastructure.Referee;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/game")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RefereeResource {

  @Inject
  Referee referee;

  @POST
  @Path("/create")
  @Transactional
  public Response createGame() {

    GameCreatedEvent gameCreatedEvent = referee.createGame();
    return Response.ok(gameCreatedEvent).status(Response.Status.CREATED).build();
  }


  @POST
  @Path("/start/{gameId}")
  @Transactional
  public Response startGame(@PathParam("gameId") Long gameId) {

    GameStartedEvent gameStartedEvent = referee.startGame(gameId);
    return Response.ok(gameStartedEvent).status(Response.Status.OK).build();
  }

  @GET
  @Path("/rounds/start")
  @Transactional
  public Response startRound() {

    referee.startRound();
    return Response.ok().build();
  }


  @GET
  @Path("/{gameId}")
  public Game getGame(@PathParam("gameId") long id) {
    Game game = Game.findById(id);
    System.out.println(game);
    return game;
  }

  @GET
  @Path("/rounds/{roundId}")
  public Response getCurrentRound(@PathParam("roundId") long id){

    Round round = Round.findById(id);

    // Get the quotes
    JsonObjectBuilder quotesBuilder = Json.createObjectBuilder();
    for(Map.Entry<Integer, Quote> quote : round.quotes.entrySet()){
      quotesBuilder.add(quote.getKey().toString(), quote.getValue().text);
    }

    JsonObjectBuilder roundBuilder = Json.createObjectBuilder();
    roundBuilder
      .add("id", round.id)
      .add("game_id", round.game.id)
      .add("round_status", round.status.name)
      .add("quotes", quotesBuilder.build());

    return Response.ok(roundBuilder.build()).build();
  }

  @POST
  @Path("/mentions")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response addMention(String player) {
    referee.addPlayer(player);
    System.out.println("adding " + player);
    return Response.ok().build();
  }

  @POST
  @Path("/test/quote")
  public Response sendTestQuote() {
    referee.testQuote();
    return Response.ok().build();
  }

  @GET
  @Path("/test/{eventType}")
  public Response getEventType(@PathParam("eventType") String eventType) {

    switch (eventType) {
      case "RoundEndedEvent":
        Round round = mockGame().getCurrentRound();
        round.status = RoundStatus.COMPLETED;
        round.winner = "@winner";
        return Response.ok(new RoundEndedEvent(DomainEventType.RoundStartedEvent, round)).build();
      case "RoundStartedEvent":
        return Response.ok(new RoundStartedEvent(DomainEventType.RoundStartedEvent, mockGame().getCurrentRound())).build();
      case "NextQuoteEvent" :
        return Response.ok(new NextQuoteEvent(DomainEventType.NextQuoteEvent, new Quote("A Test", Author.Shakespeare))).build();
      case "GameStartedEvent" :
        return Response.ok(new GameStartedEvent(DomainEventType.GameStartedEvent, mockGame())).build();
      case "GameCreatedEvent" :
        return Response.ok(new GameCreatedEvent(mockGame())).build();
      default:
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
  }

  private Game mockGame() {

    Game retVal = new Game();
    HashMap<Integer, Round> rounds = new HashMap<>(4);

    for (int i = 1; i < 4; i++) {
      Round round = new Round();
      round.quotes = new HashMap<Integer, Quote>();
      for(int j = 0; j < 4; j++){
        if (j % 2 == 0) {
          round.quotes.put(j, new Quote("Quote #" + j, Author.Hamilton));
        } else {
          round.quotes.put(j, new Quote("Quote #" + j, Author.Shakespeare));
        }
      }
//      round.setWinner("@winningplayer#" + i);
//      rounds.put(i, round);
      retVal.addRound(round);
    }
    return retVal;
  }

}
