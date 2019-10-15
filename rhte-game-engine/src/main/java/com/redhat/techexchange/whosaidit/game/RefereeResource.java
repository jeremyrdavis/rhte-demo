package com.redhat.techexchange.whosaidit.game;

import com.redhat.techexchange.whosaidit.game.domain.*;
import com.redhat.techexchange.whosaidit.game.infrastructure.GameStartedEventHandler;
import com.redhat.techexchange.whosaidit.game.infrastructure.Referee;
import com.redhat.techexchange.whosaidit.game.infrastructure.RoundStartedEventHandler;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

@Path("/game")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RefereeResource {

  @Inject
  Referee referee;

  @POST
  @Path("/start")
  @Transactional
  public Response startGame() {

    Game game = referee.createGame();
    return Response.ok(game).status(Response.Status.CREATED).build();
  }

  @POST
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
  @Path("/test/quote")
  public Response sendTestQuote() {
    referee.testQuote();
    return Response.ok().build();
  }

  @GET
  @Path("/test/{eventType}")
  public Response getEventType(@PathParam("eventType") String eventType) {

    switch (eventType) {
      case "RoundStartedEvent":
        return Response.ok(new RoundStartedEvent(EventType.RoundStartedEvent, mockGame().getCurrentRound())).build();
      case "NextQuoteEvent" :
        return Response.ok(new NextQuoteEvent(EventType.NextQuoteEvent, new Quote("A Test", Author.Shakespeare))).build();
      case "GameStartedEvent" :
        return Response.ok(new GameStartedEvent(EventType.GameStartedEvent, mockGame())).build();
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
