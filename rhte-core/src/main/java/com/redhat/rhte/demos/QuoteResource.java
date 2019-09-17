package com.redhat.rhte.demos;

import com.redhat.rhte.demos.domain.Quote;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/quotes")
public class QuoteResource {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Quote> allQuotes() {

    return Quote.listAll();
  }

  @Transactional
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response addQuote(Quote quote) {

    quote.persist();
    return Response.status(201).entity(quote).build();
  }
}
