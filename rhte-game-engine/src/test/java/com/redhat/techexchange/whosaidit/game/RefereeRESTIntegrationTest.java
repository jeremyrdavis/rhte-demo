package com.redhat.techexchange.whosaidit.game;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@QuarkusTest
public class RefereeRESTIntegrationTest {

  @Inject
  Referee referee;

  WireMockServer twitterWireMockServer;
  WireMockServer apiGatewayWireMockServer;

  @BeforeEach
  public void setUp() {
    twitterWireMockServer = new WireMockServer(8090);
    twitterWireMockServer.start();
    setupStub();
    WireMock.configureFor("localhost", twitterWireMockServer.port());
    System.out.println("Twitter WireMock configured");

    apiGatewayWireMockServer = new WireMockServer(8098);
    apiGatewayWireMockServer.start();
    setupStub();
    WireMock.configureFor("localhost", apiGatewayWireMockServer.port());
    System.out.println("ApiGateway WireMock configured");
  }

  private void setupStub() {
    twitterWireMockServer
      .stubFor(post(urlEqualTo("/status"))
      .willReturn(aResponse().withHeader("Content-Type", "application/json")
        .withStatus(200)));
  }

  @Test
  public void testOnNextQuote() {

    referee.onNextQuote(new Quote("test", Author.Hamilton));

    verify(postRequestedFor(urlMatching("/status"))
      .withRequestBody(equalToJson("{\"status\":\"Test Status\"}"))
      .withHeader("Content-Type", matching("application/json")));
  }




}
