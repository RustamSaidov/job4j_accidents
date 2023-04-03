create TABLE accident_rules (
  id serial primary key,
  accident_id int not null references accidents(id),
  rule_id int not null references rules_table(id)
);