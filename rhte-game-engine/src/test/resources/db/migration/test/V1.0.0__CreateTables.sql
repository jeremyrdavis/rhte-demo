alter table if exists round
    drop constraint if exists FKppxonwn9e98lccy46m2eve67m;
alter table if exists round_quotes
    drop constraint if exists FKmaye551bmf4137n3ssfa8th12;
alter table if exists round_quotes
    drop constraint if exists FK4c5hwk5dg72b12exw7381weqa;
drop table if exists game cascade;
drop table if exists quotes cascade;
drop table if exists round cascade;
drop table if exists round_quotes cascade;
drop sequence if exists hibernate_sequence;
create sequence hibernate_sequence start 1 increment 1;
create table game (
                      id int8 not null,
                      status int4,
                      primary key (id)
);
create table quotes (
                        id int8 not null,
                        author varchar(255),
                        text varchar(255),
                        primary key (id)
);
create table round (
                       id int8 not null,
                       status varchar(255),
                       winner varchar(255),
                       game_id int8,
                       rounds_KEY int4,
                       primary key (id)
);
create table round_quotes (
                              fk_round int8 not null,
                              fk_quote int8 not null,
                              quotes_KEY int4 not null,
                              primary key (fk_round, quotes_KEY)
);
alter table if exists round
    add constraint FKppxonwn9e98lccy46m2eve67m
        foreign key (game_id)
            references game;
alter table if exists round_quotes
    add constraint FKmaye551bmf4137n3ssfa8th12
        foreign key (fk_quote)
            references quotes;
alter table if exists round_quotes
    add constraint FK4c5hwk5dg72b12exw7381weqa
        foreign key (fk_round)
            references round;
