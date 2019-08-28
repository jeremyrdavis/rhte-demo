package com.redhat.rhte.demos;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/guesses")
public class GuessResource {


  @GET
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response allGuesses() {

    return Response.ok(Guess.listAll()).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Transactional
  public Response addGuess(Guess guess) {

    guess.persist();
    return Response.status(201).entity(guess).build();
  }
}
