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

  WireMockServer wireMockServer;

  @BeforeEach
  public void setUp() {
    wireMockServer = new WireMockServer(8090);
    wireMockServer.start();
    setupStub();
    System.out.println("WireMock configured");
    WireMock.configureFor("localhost", wireMockServer.port());
  }

  private void setupStub() {
    wireMockServer
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
