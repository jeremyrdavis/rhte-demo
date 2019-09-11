# Red Hat Tech Exchange Core Service

A Reactive Demo

This service currently implements the following endpoints:


## Getting Started

```bash

./mvnw clean quarkus:dev

```

Open http://localhost:8080/swagger-ui

## Currently implemented endpoints:
* GET ​/games
* POST /games
* POST ​/games​/rounds​/start​/{gameId}
* PUT ​/games​/rounds​/stop​/{gameId}​/{roundId}
* PUT ​/games​/start​/{gameId}
* PUT ​/games​/stop​/{gameId}
* GET ​/games​/{gameId}

