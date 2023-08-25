CREATE TABLE IF NOT EXISTS "user"
(

    id       varchar
        constraint user_pk primary key default uuid_generate_v4(),
    username varchar not null unique,
    password text    not null
);