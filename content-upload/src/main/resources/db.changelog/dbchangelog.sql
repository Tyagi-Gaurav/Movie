--liquibase formatted sql

--changeset movie:1
create SCHEMA IF NOT EXISTS MOVIE_SCHEMA;
USE MOVIE_SCHEMA;

create TABLE IF NOT EXISTS MOVIE (
    ID VARCHAR(40) NOT NULL,
    USER_ID VARCHAR(40) NOT NULL,
    NAME VARCHAR(256) NOT NULL,
    YEAR_PRODUCED SMALLINT NOT NULL,
    RATING DECIMAL(4,2) NOT NULL,
    CREATION_TIMESTAMP BIGINT NOT NULL,
    PRIMARY KEY (id)
) ENGINE=INNODB;

--changeset movie:2
DELETE FROM MOVIE;

--changeset movie:3
create TABLE IF NOT EXISTS EVENTS (
    ID VARCHAR(40) NOT NULL,
    OWNER_USER VARCHAR(40) NOT NULL,
    ORIGINATOR_USER VARCHAR(40) NOT NULL,
    TYPE VARCHAR(256) NOT NULL,
    PAYLOAD BLOB NOT NULL,
    CREATION_TIMESTAMP BIGINT NOT NULL,
    PRIMARY KEY (id)
) ENGINE=INNODB;

--changeset movie:4
create TABLE IF NOT EXISTS MOVIE_STREAM_METADATA (
    ID VARCHAR(40) NOT NULL,
    MOVIE_ID VARCHAR(40) NOT NULL,
    NAME VARCHAR(40) NOT NULL,
    SEQUENCE BIGINT NOT NULL,
    SIZE_IN_BYTES BIGINT NOT NULL,
    CREATION_TIMESTAMP BIGINT NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (MOVIE_ID) REFERENCES MOVIE (ID) ON DELETE CASCADE
) ENGINE=INNODB;

--changeset movie:5
ALTER TABLE MOVIE
    ADD GENRE VARCHAR(20) NOT NULL AFTER CREATION_TIMESTAMP;

--changeset movie:6
ALTER TABLE MOVIE
    ADD CONTENT_TYPE VARCHAR(20) NOT NULL AFTER GENRE;

--changeset movie:7
ALTER TABLE MOVIE
    ADD AGE_RATING VARCHAR(20) NOT NULL AFTER CONTENT_TYPE;

--changeset movie:8
ALTER TABLE MOVIE
    ADD IS_SHAREABLE VARCHAR(20) NOT NULL AFTER AGE_RATING;