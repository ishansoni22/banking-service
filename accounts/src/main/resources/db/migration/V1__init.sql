CREATE TABLE public.fact (
    id varchar(255) NOT NULL,
    aggregate varchar(255),
    aggregate_id varchar(255),
    fact text,
    fact_type varchar(255),
    revision int4 NOT NULL,
    status varchar(255),
    version int4 NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (aggregate_id, revision)
);