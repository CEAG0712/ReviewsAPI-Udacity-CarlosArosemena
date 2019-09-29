drop table if exists comment;
drop table if exists review;
drop table if exists product;

create table product
  (
      id          bigint auto_increment primary key,
      product_name     varchar(255) not null,
      product_description     varchar(255)

  );

create table review
(
    id          bigint auto_increment primary key,
    review_summary     varchar(255) not null,
    review_description     varchar(255) not null,
    review_rating ENUM('GREAT', 'GOOD', 'AVERAGE', 'POOR', 'UNACCEPTABLE'),
    product_id bigint not null

);

create table comment
(
    id           bigint auto_increment primary key,
    comment varchar(10000)                      null,
    review_id    bigint  not null
);