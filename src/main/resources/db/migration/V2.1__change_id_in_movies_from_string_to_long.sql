ALTER TABLE public.movie DROP CONSTRAINT movie_pkey CASCADE;
ALTER TABLE public.movie ALTER COLUMN id TYPE bigint USING id::bigint;
ALTER TABLE public.movie ADD CONSTRAINT movie_pkey PRIMARY KEY (id);