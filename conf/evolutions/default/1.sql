# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table cls (
  id                            integer auto_increment not null,
  unit_id                       integer,
  date                          datetime(6),
  lecturer_id                   integer,
  duration                      varchar(255),
  start_time                    varchar(255),
  end_time                      varchar(255),
  constraint pk_cls primary key (id)
);

create table course (
  id                            integer auto_increment not null,
  name                          varchar(255),
  constraint pk_course primary key (id)
);

create table lecture_hall (
  id                            integer auto_increment not null,
  name                          varchar(255),
  constraint pk_lecture_hall primary key (id)
);

create table lecturer (
  id                            integer auto_increment not null,
  work_id                       varchar(255),
  first_name                    varchar(255),
  sur_name                      varchar(255),
  last_name                     varchar(255),
  constraint pk_lecturer primary key (id)
);

create table student (
  id                            integer auto_increment not null,
  reg_no                        varchar(255),
  first_name                    varchar(255),
  sur_name                      varchar(255),
  last_name                     varchar(255),
  year                          integer not null,
  course_id                     integer,
  finger_id                     varchar(255),
  constraint pk_student primary key (id)
);

create table unit (
  id                            integer auto_increment not null,
  name                          varchar(255),
  lecturer_id                   integer,
  constraint uq_unit_lecturer_id unique (lecturer_id),
  constraint pk_unit primary key (id)
);

create table user (
  id                            integer auto_increment not null,
  username                      varchar(255),
  email                         varchar(255),
  password                      varchar(255),
  type                          varchar(255),
  constraint uq_user_username unique (username),
  constraint uq_user_email unique (email),
  constraint pk_user primary key (id)
);

create index ix_cls_unit_id on cls (unit_id);
alter table cls add constraint fk_cls_unit_id foreign key (unit_id) references unit (id) on delete restrict on update restrict;

create index ix_cls_lecturer_id on cls (lecturer_id);
alter table cls add constraint fk_cls_lecturer_id foreign key (lecturer_id) references lecturer (id) on delete restrict on update restrict;

create index ix_student_course_id on student (course_id);
alter table student add constraint fk_student_course_id foreign key (course_id) references course (id) on delete restrict on update restrict;

alter table unit add constraint fk_unit_lecturer_id foreign key (lecturer_id) references lecturer (id) on delete restrict on update restrict;


# --- !Downs

alter table cls drop foreign key fk_cls_unit_id;
drop index ix_cls_unit_id on cls;

alter table cls drop foreign key fk_cls_lecturer_id;
drop index ix_cls_lecturer_id on cls;

alter table student drop foreign key fk_student_course_id;
drop index ix_student_course_id on student;

alter table unit drop foreign key fk_unit_lecturer_id;

drop table if exists cls;

drop table if exists course;

drop table if exists lecture_hall;

drop table if exists lecturer;

drop table if exists student;

drop table if exists unit;

drop table if exists user;

