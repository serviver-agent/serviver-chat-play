drop table if exists `user_infos`;
create table if not exists `user_infos`
(
  `user_id`             char(64) unique not null,
  `user_name`               VARCHAR(32) NOT NULL,
  `image_url`     VARCHAR(255) NOT NULL,
  primary key (`user_id`)
);
