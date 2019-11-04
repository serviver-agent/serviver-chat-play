drop table if exists `user_tokens`;
create table if not exists `user_tokens`
(
  `user_token`           char(255) unique not null,
  `user_id`              char(64) NOT NULL,
  primary key (`user_token`)
);
