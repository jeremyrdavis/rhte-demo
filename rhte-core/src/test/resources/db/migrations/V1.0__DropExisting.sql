    alter table if exists Guess
       drop constraint if exists FK7fr6h1hn4xtataao5dkyqcjew;

    alter table if exists Guess
       drop constraint if exists FKl1jo1w2iw585lkxj7rkilwdus;

    alter table if exists Round
       drop constraint if exists FK9pdqsvps1d4vjy5ftnm6j2pek;

    alter table if exists Round_Quote
       drop constraint if exists FKhbhsc7gb9l1r9cqdfkbbf3po9;

    alter table if exists Round_Quote
       drop constraint if exists FKls9jpucuwohlk09bthvfw67hj;

    drop table if exists Game cascade;

    drop table if exists Guess cascade;

    drop table if exists Quote cascade;

    drop table if exists Round cascade;

    drop table if exists Round_Quote cascade;

    drop sequence if exists hibernate_sequence;

