create TABLE accidents (
  id serial primary key,
  name varchar,
  text varchar,
  address varchar,
  type_id int not null references accident_types(id)
);