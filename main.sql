drop database praktika_app;

create database praktika_app;

use praktika_app;

create table user(
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email varchar(100),
    nickname varchar(100),
    password varchar(100),
    last_location varchar(100)
);

create table person(
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name varchar(100), 
    surname varchar(100),
    age int CHECK(age > 0),
    user_id int UNIQUE,
    FOREIGN KEY(user_id) REFERENCES `User`(id) ON DELETE SET NULL
);

create table Tag(
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    tag_name varchar(30) unique
);

create table Person_Tag(
	person_id int,
    tag_id int,
    foreign key (person_id) references person(id),
    foreign key (tag_id) references tag(id),
    primary key (person_id, tag_id)
);

create table Data(
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    file_path varchar(100),
    file_type varchar(30),
    person_id int,
    foreign key (person_id) references person(id)
);

create table chat(
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    chat_name varchar(50)
);

create table message(
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    date datetime,
    info varchar(255),
    chat_id int,
    foreign key (chat_id) references chat(id)
);

create table user_chat(
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id int,
    chat_id int,
    foreign key (user_id) references user(id),
    foreign key (chat_id) references chat(id)
);

create table role(
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    role_name varchar(30), -- enum
    person_id int ,
    foreign key(person_id) references person(id)
);

-- insert data
-- values

insert into person(name, surname, age, user_id) values('Dmitry', 'Orlov', 22, 4);
insert into person(name, surname, age, user_id) values('Maxim', 'Ivschenko', 22, 5);

insert into user(email, nickname, password) values('dimas_orlov99@mail.ru', 'dim4eSS', 'QwertY_123');
insert into user(email, nickname, password) values('maxim@mail.ru', 'maxerum', 'zxc');
insert into user(email, nickname, password) values('eugeneGriz@mail.ru', 'ZXC_DEMON', '123');

insert into chat(chat_name) values("Chat1");
insert into chat(chat_name) values("Chat2");
insert into chat(chat_name) values("Chat3");

insert into user_chat(user_id, chat_id) values(4, 1);
insert into user_chat(user_id, chat_id) values(4, 2);
insert into user_chat(user_id, chat_id) values(5, 2);
insert into user_chat(user_id, chat_id) values(20, 1);

select * from chat;

select * from user_chat;

insert into message(date, info, chat_id) values('2023-04-17 13:44:51', 'Hello dim4es', 1);
insert into message(date, info, chat_id) values('2023-04-17 13:46:57', 'Hello eugene', 1);


insert into message(date, info, chat_id) values('2023-04-17 13:44:44', 'Hello maxim', 2);
insert into message(date, info, chat_id) values('2023-04-17 13:46:57', 'Hi domestos', 2);


select * from user;