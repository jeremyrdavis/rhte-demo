alter table if exists Round drop constraint if exists FK9pdqsvps1d4vjy5ftnm6j2pek;
alter table if exists round_quotes drop constraint if exists FK4e0q12a7w00714yas97fvypsk;
alter table if exists round_quotes drop constraint if exists FKp6519m9dxcvj87beu3tlkwxve;
drop table if exists Game;
drop table if exists Quote;
drop table if exists Round;
drop table if exists round_quotes;
drop sequence if exists hibernate_sequence;
create sequence hibernate_sequence start with 1 increment by 1;
create table Game (
                      id bigint not null,
                      primary key (id),
                      status varchar(15)
);
create table Quote (
                       id bigint not null,
                       author varchar(255),
                       text varchar(255),
                       primary key (id)
);
create table Round (
                       id bigint not null,
                       status varchar(15),
                       game_id bigint,
                       rounds_KEY integer,
                       winner varchar(255),
                       primary key (id)
);
create table round_quotes (
                              fk_round bigint not null,
                              fk_quote bigint not null,
                              quotes_KEY integer not null,
                              primary key (fk_round, quotes_KEY)
);
alter table Round
    add constraint FK9pdqsvps1d4vjy5ftnm6j2pek
        foreign key (game_id)
            references Game;
alter table round_quotes
    add constraint FK4e0q12a7w00714yas97fvypsk
        foreign key (fk_quote)
            references Quote;
alter table round_quotes
    add constraint FKp6519m9dxcvj87beu3tlkwxve
        foreign key (fk_round)
            references Round;
