package com.redhat.rhte.demos;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/quotes")
public class QuoteResource {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Quote> allQuotes() {

    return Quote.listAll();
  }
}
