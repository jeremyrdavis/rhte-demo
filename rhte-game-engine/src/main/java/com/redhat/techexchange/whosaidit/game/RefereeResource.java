package com.redhat.techexchange.whosaidit.game;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

  @GET
  @Path("/{gameId}")
  public Game getGame(@PathParam("gameId") long id) {
    Game game = Game.findById(id);
    System.out.println(game);
//    return Response.ok(game).status(Response.Status.FOUND).build();
    return game;
  }

  @GET
  @Path("/{roundId}")
  public Round getCurrentRound(@PathParam("roundId") long id){
    return referee.getCurrentRound();
  }

}
