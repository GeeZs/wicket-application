create table usr(
                  id bigint not null,
                  first_name varchar(255) not null,
                  second_name varchar(255) not null,
                  username varchar(255) not null,
                  password varchar(255) not null,
                  constraint usr_pk primary key (id)
);

insert into usr(id, first_name, second_name, username, password) values (1, 'Admin', 'Admin', 'admin', 'admin');