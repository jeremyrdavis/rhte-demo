create table QUOTE (
        id int8 not null,
        author varchar(255),
        text varchar(255),
        primary key (id)
    );
CREATE SEQUENCE hibernate_sequence START 1;
