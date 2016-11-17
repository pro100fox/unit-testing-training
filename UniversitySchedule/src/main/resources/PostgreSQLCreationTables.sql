
DROP TABLE IF EXISTS "scheduleItem" CASCADE;
DROP TABLE IF EXISTS "studentsOfGroup" CASCADE;
DROP TABLE IF EXISTS "group" CASCADE;
DROP TABLE IF EXISTS "subject" CASCADE;
DROP TABLE IF EXISTS "audience" CASCADE;
DROP TABLE IF EXISTS "person" CASCADE;

CREATE TABLE "subject"
(
  id serial NOT NULL,
  "title" varchar,
  CONSTRAINT subject_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE "group"
(
  id serial NOT NULL,
  "name" varchar,
  CONSTRAINT group_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE "person"
(
  id serial NOT NULL,
  "name" varchar,
  CONSTRAINT person_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE "audience"
(
  id serial NOT NULL,
  "name" varchar,
  CONSTRAINT audience_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE "studentsOfGroup"
(
  id serial NOT NULL,
  "group" integer references "group"(id) ON DELETE CASCADE,
  "student" integer references person(id) ON DELETE CASCADE,
  CONSTRAINT studentsOfGroup_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE "scheduleItem"
(
  id serial NOT NULL,
  "dateTime" timestamp,
  "lecturer" integer references person(id) ON DELETE CASCADE,
  "group" integer references "group"(id) ON DELETE CASCADE,
  "audience" integer references audience(id) ON DELETE CASCADE,
  "subject" integer references subject(id) ON DELETE CASCADE,
  CONSTRAINT sheduleItem_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);