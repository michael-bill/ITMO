create table person (
    id serial primary key,
    name varchar(50),
    age integer,
    birth_date timestamp with time zone,
    constraint check_age check(age > 0 and age < 100)
);
create table country (
    id serial primary key,
    name text,
    is_nato boolean
);
create table citizenship (
    person_id integer,
    country_id integer,
    foreign key (person_id) references person(id),
    foreign key (country_id) references country(id)
);

insert into person (name, age, birth_date) values ('Боб', 4, to_timestamp('12:03:2019', 'DD:MM:YYYY'));
insert into person (name, age, birth_date) values ('Кевин', 6, to_timestamp('11:04:2017', 'DD:MM:YYYY'));
insert into person (name, age, birth_date) values ('Зеновий', 24, to_timestamp('12:03:2019', 'DD:MM:YYYY'));
insert into person (name, age, birth_date) values ('Сунь Хуй в Чай', 4, to_timestamp('12:03:2019', 'DD:MM:YYYY'));
insert into country (name, is_nato) values ('Россия', false);
insert into country (name, is_nato) values ('Турция', true);
insert into country (name, is_nato) values ('Пендосия', true);
insert into country (name, is_nato) values ('Китай', false);
insert into country (name, is_nato) values ('Япония', true);
insert into citizenship (person_id, country_id) values
((select id from person where name like 'Боб' limit 1), (select id from country where name='Россия')),
((select id from person where name like '_евин' limit 1), (select id from country where name='Россия')),
((select id from person where name='Боб' limit 1), (select id from country where name='Китай')),
((select id from person where name='Боб' limit 1), (select id from country where name='Турция')),
((select id from person where name='Зеновий' limit 1), (select id from country where name='Пендосия')),
((select id from person where name='Сунь Хуй в Чай' limit 1), (select id from country where name='Китай'));

alter table country
add constraint unique_country_name unique (name);
alter table citizenship
alter person_id set not null,
alter country_id set not null;

select name from person
join citizenship on person.id=citizenship.person_id
group by name, person_id having count(*) = 3;

select name from person
where (select count(*) from citizenship where person_id=person.id) = 3;