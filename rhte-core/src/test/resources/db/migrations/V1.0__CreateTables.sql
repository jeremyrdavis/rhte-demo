alter table if exists Quote drop constraint if exists FKla52aux1nj5y0spy0anslwudn;
drop table if exists Guess cascade;
drop table if exists Quote cascade;
drop table if exists Round cascade;
drop sequence if exists hibernate_sequence;
CREATE SEQUENCE hibernate_sequence START 1 increment 1;

    create table Guess (
       id int8 not null,
        author varchar(255),
        contestant varchar(255),
        quote_id int8,
        round_id int8,
        primary key (id)
    );
    create table Quote (
       id int8 not null,
        author varchar(255),
        text varchar(255),
        quote_id int8,
        primary key (id)
    );
    create table Round (
       id int8 not null,
        primary key (id)
    );
    alter table if exists Guess
       add constraint FK7fr6h1hn4xtataao5dkyqcjew
       foreign key (quote_id)
       references Quote;
    alter table if exists Guess
       add constraint FKl1jo1w2iw585lkxj7rkilwdus
       foreign key (round_id)
       references Round;
    alter table if exists Quote
       add constraint FKla52aux1nj5y0spy0anslwudn
       foreign key (quote_id)
       references Round;
