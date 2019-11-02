drop table if exists `users`;
create table if not exists `users`
(
  `id`               char(64) unique not null,
  `name`             VARCHAR(32) NOT NULL,
  primary key (`id`)
);
