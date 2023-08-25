CREATE TABLE IF NOT EXISTS "phone"
(
    id                     varchar
        constraint phone_pk primary key default uuid_generate_v4(),
    employee_id varchar,
    value                  varchar not null unique,
    constraint phone_employee foreign key (employee_id) references "employee" (id)
);
