drop table Game if exists;
drop table Quote if exists;
drop table Round if exists;
drop table round_quotes if exists;
drop sequence if exists hibernate_sequence;
create sequence hibernate_sequence start with 1 increment by 1;
create table Game (
                      id bigint not null,
                      primary key (id)
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