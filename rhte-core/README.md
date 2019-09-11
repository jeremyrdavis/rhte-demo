# Red Hat Tech Exchange Core Service

A Reactive Demo

This service currently implements the following endpoints:


## Getting Started

### PostgreSQL

The Quarkus app is using PostgreSQL 10.10-1

### Docker

docker run -d --name my_postgres -v my_dbdata:/var/lib/postgresql/data -p 54320:5432 postgres:10.10
docker exec -it my_postgres psql -U postgres -c "create database rhte_db"

### Quarkus

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

