CREATE TABLE public.migrations
(
    id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    media_type character varying(255) COLLATE pg_catalog."default",
    overview character varying(255) COLLATE pg_catalog."default",
    popularity character varying(255) COLLATE pg_catalog."default",
    poster_path character varying(255) COLLATE pg_catalog."default",
    release_date date,
    title character varying(255) COLLATE pg_catalog."default",
    video boolean,
    vote_average real,
    vote_count integer,
    CONSTRAINT migration_pkey PRIMARY KEY (id)
)
