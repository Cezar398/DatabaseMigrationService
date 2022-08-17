CREATE TABLE public.movie_country
(
    movie_id   varchar(255) not null,
    country_id bigint not null,
    PRIMARY KEY (movie_id, country_id),
    CONSTRAINT movie_pkey FOREIGN KEY (movie_id) REFERENCES public.movie (id) ON DELETE CASCADE,
    CONSTRAINT country_pkey FOREIGN KEY (country_id) REFERENCES public.country (id) ON DELETE CASCADE
)