CREATE TABLE IF NOT EXISTS "cnaps_have_position"
(

    id          varchar
        constraint have_position_pk primary key default uuid_generate_v4(),
    employee_id varchar not null,
    position_id varchar not null,
    constraint have_position_employee_fk foreign key (employee_id)
        references "employee_cnaps" (id),
    constraint have_position_position_fk foreign key (position_id)
        references "cnaps_position" (id)
);