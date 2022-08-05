CREATE TABLE IF NOT EXISTS public.movie
(
    id           varchar(255) PRIMARY KEY,
    title        varchar(255) not null,
    overview     varchar(255) not null,
    poster_path  varchar(255) not null,
    media_type   varchar(255) not null,
    popularity   varchar(255) not null,
    release_date varchar(255) not null,
    video        bool         not null,
    vote_average float        not null,
    vote_count   int4         not null
);

CREATE TABLE IF NOT EXISTS public.rating
(
    id      varchar(255) PRIMARY KEY,
    content varchar(255) not null,
    rate    int          not null,
    movie_id varchar(255) not null,
    FOREIGN KEY (movie_id) REFERENCES movie(id)
);

