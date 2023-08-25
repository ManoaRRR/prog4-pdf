create table if not exists "cnaps_position"
(
    id          varchar
        constraint position_pk primary key default uuid_generate_v4(),
    name        varchar not null unique
);