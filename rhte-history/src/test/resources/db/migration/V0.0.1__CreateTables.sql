drop table Event if exists;
drop table Game if exists;
drop table Quote if exists;
drop table Round if exists;
drop table round_quotes if exists;
drop sequence if exists hibernate_sequence;
create sequence hibernate_sequence start with 1 increment by 1;
create table Event (
                       id bigint not null,
                       eventType varchar(255),
                       timestamp timestamp,
                       game_id varchar(255),
                       primary key (id)
);
create table Game (
                      id varchar(255) not null,
                      primary key (id)
);
create table Quote (
                       id varchar(255) not null,
                       author varchar(255),
                       text varchar(255),
                       primary key (id)
);
create table Round (
                       id varchar(255) not null,
                       status integer,
                       game_id varchar(255),
                       rounds_KEY integer,
                       primary key (id)
);
create table round_quotes (
                              fk_round varchar(255) not null,
                              fk_quote varchar(255) not null,
                              quotes_KEY integer not null,
                              primary key (fk_round, quotes_KEY)
);
alter table Event
    add constraint FKa5cor2xi5513d24w10b5w6yp8
        foreign key (game_id)
            references Game;
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
