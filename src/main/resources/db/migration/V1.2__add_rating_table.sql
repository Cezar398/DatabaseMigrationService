CREATE TABLE IF NOT EXISTS public.rating
(
    id      varchar(255) PRIMARY KEY,
    content varchar(255) not null,
    rate    int          not null
);
