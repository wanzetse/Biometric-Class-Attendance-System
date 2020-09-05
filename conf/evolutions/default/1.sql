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
  course_code                   varchar(255),
  name                          varchar(255),
  constraint uq_course_course_code unique (course_code),
  constraint pk_course primary key (id)
);

create table department (
  id                            integer auto_increment not null,
  name                          varchar(255),
  constraint uq_department_name unique (name),
  constraint pk_department primary key (id)
);

create table lecture_hall (
  id                            integer auto_increment not null,
  room_name                     varchar(255),
  block_name                    varchar(255),
  capacity                      integer,
  constraint uq_lecture_hall_room_name unique (room_name),
  constraint pk_lecture_hall primary key (id)
);

create table lecturer (
  id                            integer auto_increment not null,
  work_id                       varchar(255),
  first_name                    varchar(255),
  sur_name                      varchar(255),
  last_name                     varchar(255),
  email                         varchar(255),
  department_head               tinyint(1) default 0 not null,
  phone                         varchar(255),
  title                         varchar(255),
  constraint uq_lecturer_work_id unique (work_id),
  constraint uq_lecturer_email unique (email),
  constraint pk_lecturer primary key (id)
);

create table student (
  id                            integer auto_increment not null,
  reg_no                        varchar(255),
  first_name                    varchar(255),
  sur_name                      varchar(255),
  last_name                     varchar(255),
  year                          integer not null,
  email                         varchar(255),
  phone                         varchar(255),
  course_id                     integer,
  finger_id                     varchar(255),
  constraint uq_student_reg_no unique (reg_no),
  constraint pk_student primary key (id)
);

create table unit (
  id                            integer auto_increment not null,
  unit_code                     varchar(255),
  name                          varchar(255),
  lecturer_id                   integer,
  constraint uq_unit_unit_code unique (unit_code),
  constraint pk_unit primary key (id)
);

create table user (
  id                            integer auto_increment not null,
  username                      varchar(255),
  email                         varchar(255),
  password                      varchar(255),
  student                       tinyint(1) default 0 not null,
  lecturer                      tinyint(1) default 0 not null,
  admin                         tinyint(1) default 0 not null,
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

create index ix_unit_lecturer_id on unit (lecturer_id);
alter table unit add constraint fk_unit_lecturer_id foreign key (lecturer_id) references lecturer (id) on delete restrict on update restrict;


# --- !Downs

alter table cls drop foreign key fk_cls_unit_id;
drop index ix_cls_unit_id on cls;

alter table cls drop foreign key fk_cls_lecturer_id;
drop index ix_cls_lecturer_id on cls;

alter table student drop foreign key fk_student_course_id;
drop index ix_student_course_id on student;

alter table unit drop foreign key fk_unit_lecturer_id;
drop index ix_unit_lecturer_id on unit;

drop table if exists cls;

drop table if exists course;

drop table if exists department;

drop table if exists lecture_hall;

drop table if exists lecturer;

drop table if exists student;

drop table if exists unit;

drop table if exists user;

