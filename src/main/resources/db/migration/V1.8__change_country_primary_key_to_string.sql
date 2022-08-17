ALTER TABLE public.country DROP CONSTRAINT country_pkey CASCADE;
ALTER TABLE public.country ALTER COLUMN id TYPE varchar(255);
ALTER TABLE public.country ADD CONSTRAINT country_pkey PRIMARY KEY (id);