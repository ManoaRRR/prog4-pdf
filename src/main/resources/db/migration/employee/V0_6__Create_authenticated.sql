CREATE TABLE IF NOT EXISTS "session"
(
    id         varchar
        constraint session_pk primary key default uuid_generate_v4(),
    session_id varchar not null,
    timeout    TIMESTAMP,
    user_id    varchar,
    constraint session_user_fk foreign key (user_id) references "user" (id)
);