CREATE SEQUENCE hibernate_sequence START 1;
create table Guess (
        id int8 not null,
        author varchar(255),
        contestant varchar(255),
        quote_id int8,
        primary key (id)
    );
create table QUOTE (
        id int8 not null,
        author varchar(255),
        text varchar(255),
        primary key (id)
    );
alter table if exists Guess
       add constraint FK7fr6h1hn4xtataao5dkyqcjew
       foreign key (quote_id)
       references Quote;
