create TABLE accident_rules (
  id serial primary key,
  accident_id int not null references accidents(id),
  rules_id int not null references rules_table(id)
);