ALTER TABLE public.rating ALTER COLUMN movie_id TYPE bigint USING movie_id::bigint;
