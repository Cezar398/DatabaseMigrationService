ALTER TABLE public.contract ALTER COLUMN start_date TYPE date USING start_date::date;
ALTER TABLE public.contract ALTER COLUMN end_date TYPE date USING end_date::date;
