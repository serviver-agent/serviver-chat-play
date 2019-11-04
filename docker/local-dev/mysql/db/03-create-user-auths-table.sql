drop table if exists `user_auths`;
create table if not exists `user_auths`
(
  `user_id`             char(64) unique not null,
  `email`               VARCHAR(255) NOT NULL,
  `hashed_password`     VARCHAR(255) NOT NULL,
  primary key (`user_id`)
);
