insert into directory (id, name)
values (0, 'root')
on conflict do nothing;

insert into directory (id, parent_id, name)
values (1, 0, 'shared')
on conflict do nothing;