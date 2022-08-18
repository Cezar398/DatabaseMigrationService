CREATE TABLE public.contract(
    id bigint not null primary key,
    start_date varchar(255) not null,
    end_date varchar(255) not null,
    crew_id bigint not null,
    movie_id bigint not null,
    FOREIGN KEY (crew_id) REFERENCES crew (id),
    FOREIGN KEY (movie_id) REFERENCES movie (id)
);