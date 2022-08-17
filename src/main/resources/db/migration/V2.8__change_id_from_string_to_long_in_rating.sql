ALTER TABLE public.rating DROP CONSTRAINT rating_pkey CASCADE;
ALTER TABLE public.rating ALTER COLUMN id TYPE bigint USING id::bigint;
ALTER TABLE public.rating ADD CONSTRAINT rating_pkey PRIMARY KEY (id);