ALTER TABLE public.rating
    ADD CONSTRAINT fk_movie_rating FOREIGN KEY (movie_id) REFERENCES movie (id);