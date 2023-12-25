DROP TABLE IF EXISTS "character" CASCADE;
DROP TABLE IF EXISTS "discussion" CASCADE;
DROP TABLE IF EXISTS "discussion_theme" CASCADE;
DROP TABLE IF EXISTS "fall" CASCADE;
DROP TABLE IF EXISTS "object" CASCADE;
DROP TABLE IF EXISTS "object_name" CASCADE;
DROP TABLE IF EXISTS "property_finding" CASCADE;
DROP TABLE IF EXISTS "property_name" CASCADE;

CREATE TABLE "character" (
    id SERIAL PRIMARY KEY,
    name text NOT NULL,
    age integer,
    height double precision,
    weight double precision);

CREATE TABLE "discussion" (
    id SERIAL PRIMARY KEY,
    discussion_theme_id integer NOT NULL,
    character_id integer NOT NULL);

CREATE TABLE "discussion_theme" (
    id SERIAL PRIMARY KEY,
    name text);

CREATE TABLE "fall" (
    id SERIAL PRIMARY KEY,
    character_id integer NOT NULL,
    object_id integer NOT NULL,
    fall_time integer NOT NULL,
    landing_time timestamp with time zone NOT NULL);

CREATE TABLE "object" (
    id SERIAL PRIMARY KEY,
    object_name_id integer NOT NULL,
    tonnage double precision,
    density double precision,
    hollow boolean NOT NULL,
    object_id integer);

CREATE TABLE "object_name" (
    id SERIAL PRIMARY KEY,
    name text NOT NULL);

CREATE TABLE "property_finding" (
    id SERIAL PRIMARY KEY,
    property_name_id integer NOT NULL,
    character_id integer NOT NULL,
    object_id integer NOT NULL,
    result double precision NOT NULL,
    finding_date timestamp with time zone);

CREATE TABLE "property_name" (
    id SERIAL PRIMARY KEY,
    name text NOT NULL);

ALTER TABLE ONLY "discussion"
    ADD CONSTRAINT discussion_character_id_fk FOREIGN KEY (character_id) REFERENCES "character"(id);
ALTER TABLE ONLY "discussion"
    ADD CONSTRAINT discussion_d_id_fk FOREIGN KEY (discussion_theme_id) REFERENCES "discussion_theme"(id);
ALTER TABLE ONLY "fall"
    ADD CONSTRAINT fall_character_id_fk FOREIGN KEY (character_id) REFERENCES "character"(id);
ALTER TABLE ONLY "fall"
    ADD CONSTRAINT fall_object_id_fk FOREIGN KEY (object_id) REFERENCES "object"(id);
ALTER TABLE ONLY "object"
    ADD CONSTRAINT object_name_id_fk FOREIGN KEY (object_name_id) REFERENCES "object_name"(id);
ALTER TABLE ONLY "object"
    ADD CONSTRAINT object_object_id_fk FOREIGN KEY (object_id) REFERENCES "object"(id);
ALTER TABLE ONLY "property_finding"
    ADD CONSTRAINT pf_character_id_fk FOREIGN KEY (character_id) REFERENCES "character"(id);
ALTER TABLE ONLY "property_finding"
    ADD CONSTRAINT pf_object_id_fk FOREIGN KEY (object_id) REFERENCES "object"(id);
ALTER TABLE ONLY "property_finding"
    ADD CONSTRAINT pf_property_name_id_fk FOREIGN KEY (property_name_id) REFERENCES "property_name"(id) ;

INSERT INTO "character"(name, age, height, weight) VALUES ('Нина', '19', '160', '55'), ('Василий', '22', '178', '70');
INSERT INTO "discussion_theme"(name) VALUES ('Предполагаемое содержимое объекта');
INSERT INTO "discussion"(discussion_theme_id, character_id) VALUES
    ((SELECT id FROM "discussion_theme" WHERE name='Предполагаемое содержимое объекта'), (SELECT id FROM "character" WHERE name='Нина')),
    ((SELECT id FROM "discussion_theme" WHERE name='Предполагаемое содержимое объекта'), (SELECT id FROM "character" WHERE name='Василий'));
INSERT INTO "object_name"(name) VALUES ('Большой брат'), ('Воздух');
INSERT INTO "object"(object_name_id, tonnage, density, hollow) VALUES
    ((SELECT id FROM "object_name" WHERE name='Воздух'), '1e-07', '1.2754', 'false' );
INSERT INTO "object"(object_name_id, tonnage, density, object_id, hollow) VALUES
    ((SELECT id FROM "object_name" WHERE name='Большой брат'), '950000', '1.3',
    (SELECT id FROM "object" WHERE object_name_id=(SELECT id FROM "object_name" WHERE name='Воздух')), 'true' );
INSERT INTO "fall"(character_id, object_id, fall_time, landing_time) VALUES
    ((SELECT id FROM "character" WHERE name='Нина'),
    (SELECT id FROM "object" WHERE object_name_id=(SELECT id FROM "object_name" WHERE name='Большой брат')),
    '8', '2023-03-31 18:27:53.701886+03');
INSERT INTO "property_name"(name) VALUES ('масса'), ('плотность');
INSERT INTO "property_finding"(property_name_id, character_id, object_id, result, finding_date) VALUES
    ((SELECT id FROM "property_name" WHERE name='масса'), (SELECT id FROM "character" WHERE name='Василий'),
    (SELECT id FROM "object" WHERE object_name_id=(SELECT id FROM "object_name" WHERE name='Большой брат')),
    '950000', '2023-03-30 18:27:53.701886+03');
INSERT INTO "property_finding"(property_name_id, character_id, object_id, result, finding_date) VALUES
    ((SELECT id FROM "property_name" WHERE name='плотность'), (SELECT id FROM "character" WHERE name='Василий'),
    (SELECT id FROM "object" WHERE object_name_id=(SELECT id FROM "object_name" WHERE name='Большой брат')),
    '1.3', '2023-03-31 18:27:53.701886+03');

cretae table my_table (
    id serial primaty key,
    message text not null
);
alter table my_table
    add column date timestamp with time zone;
-- Ограничение на длину > 10 символов
alter table my_table
    add constraint len_check check(length(message) >= 10)
insert into my_table(message) values('1234567890');
-- Вот тут будет ошибка, так как длина строки 9
insert into my_table(message) values('123456789');
alter table my_table
    drop constraint len_check;

select fall_time from fall where
    character_id=(select id from character where name='Нина') and
    object_id=(select id from object where object_name_id=
    (select id from object_name where name='Большой брат'));