drop table if exists `channels`;
create table if not exists `channels`
(
  `id`               char(64) unique not null,
  `name`             VARCHAR(32) NOT NULL,
  primary key (`id`)
);
