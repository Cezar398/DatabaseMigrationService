ALTER TABLE public.movie
    alter column overview drop not null;
ALTER TABLE public.movie
    alter column poster_path drop not null;
ALTER TABLE public.movie
    alter column media_type drop not null;
ALTER TABLE public.movie
    alter column popularity drop not null;
ALTER TABLE public.movie
    alter column video drop not null;
ALTER TABLE public.movie
    alter column vote_average drop not null;
ALTER TABLE public.movie
    alter column vote_count drop not null;
ALTER TABLE public.movie
    ADD column external_id varchar(255);
