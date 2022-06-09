DROP TABLE IF EXISTS composer CASCADE;
DROP SEQUENCE IF EXISTS composer_id_seq;
CREATE TABLE composer
(
    id            bigserial    NOT NULL PRIMARY KEY,
    name          varchar(255) NOT NULL,
    year_of_birth int          NOT NULL,
    year_of_death int          NOT NULL
);

DROP TABLE IF EXISTS publisher CASCADE;
DROP SEQUENCE IF EXISTS publisher_id_seq;
CREATE TABLE publisher
(
    id   bigserial    NOT NULL PRIMARY KEY,
    name varchar(255) NOT NULL
);

DROP TABLE IF EXISTS score CASCADE;
DROP SEQUENCE IF EXISTS score_id_seq;
CREATE TABLE score
(
    id                    bigserial    NOT NULL PRIMARY KEY,
    title                 varchar(255) NOT NULL,
    instrument_type       varchar(50)  NOT NULL,
    price                 int          NOT NULL,
    year_of_creation      int4         NOT NULL,
    is_available_in_stock boolean      NOT NULL,
    composer_id           int8,
    publisher_id          int8
);

DROP TABLE IF EXISTS score_composers CASCADE;
create table score_composers
(
    score_id    int8 not null,
    composer_id int8 not null,
    primary key (score_id, composer_id)
);

DROP TABLE IF EXISTS publisher_scores CASCADE;
create table publisher_scores
(
    publisher_id int8 not null,
    score_id     int8 not null,
    primary key (publisher_id, score_id)
);

DROP TABLE IF EXISTS publisher_composers CASCADE;
create table publisher_composers
(
    publisher_id int8 not null,
    composer_id  int8 not null,
    primary key (publisher_id, composer_id)
);

ALTER TABLE score
    ADD FOREIGN KEY (composer_id)
        REFERENCES composer (id)
        ON DELETE CASCADE;

ALTER TABLE score
    ADD FOREIGN KEY (publisher_id)
        REFERENCES publisher (id)
        ON DELETE CASCADE;

ALTER TABLE score_composers
    ADD FOREIGN KEY (score_id)
        REFERENCES score (id)
        ON DELETE CASCADE;

ALTER TABLE score_composers
    ADD FOREIGN KEY (composer_id)
        REFERENCES composer (id)
        ON DELETE CASCADE;

ALTER TABLE publisher_scores
    ADD FOREIGN KEY (publisher_id)
        REFERENCES publisher (id)
        ON DELETE CASCADE;

ALTER TABLE publisher_scores
    ADD FOREIGN KEY (score_id)
        REFERENCES score (id)
        ON DELETE CASCADE;

ALTER TABLE publisher_composers
    ADD FOREIGN KEY (publisher_id)
        REFERENCES publisher (id)
        ON DELETE CASCADE;

ALTER TABLE publisher_composers
    ADD FOREIGN KEY (composer_id)
        REFERENCES composer (id)
        ON DELETE CASCADE;




