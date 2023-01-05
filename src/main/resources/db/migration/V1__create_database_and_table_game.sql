start transaction;

create database if not exists game;

use game;

create table game
(
    id   bigint auto_increment primary key,
    name varchar(255) null
);

commit;