CREATE TABLE score_composers
(
    score_id    BIGINT NOT NULL,
    composer_id BIGINT NOT NULL,
    CONSTRAINT score_composers_pkey PRIMARY KEY (score_id, composer_id)
);