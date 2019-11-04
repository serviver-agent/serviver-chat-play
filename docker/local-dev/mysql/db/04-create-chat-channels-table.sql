drop table if exists `chat_channels`;
create table if not exists `chat_channels`
(
  `channel_id`           char(64) unique not null,
  `channel_name`                 char(32) NOT NULL,
  primary key (`channel_id`)
);
