# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table attendance (
  id                            integer auto_increment not null,
  cls_id                        integer,
  student_id                    integer,
  constraint pk_attendance primary key (id)
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
  user_id                       integer,
  department_id                 integer,
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
  user_id                       integer,
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

create table cls (
  id                            integer auto_increment not null,
  unit_id                       integer,
  date                          varchar(255),
  lecturer_id                   integer,
  lecture_hall_id               integer,
  duration                      double not null,
  start_time                    varchar(255),
  end_time                      varchar(255),
  constraint pk_cls primary key (id)
);

create index ix_attendance_cls_id on attendance (cls_id);
alter table attendance add constraint fk_attendance_cls_id foreign key (cls_id) references cls (id) on delete restrict on update restrict;

create index ix_attendance_student_id on attendance (student_id);
alter table attendance add constraint fk_attendance_student_id foreign key (student_id) references student (id) on delete restrict on update restrict;

create index ix_lecturer_user_id on lecturer (user_id);
alter table lecturer add constraint fk_lecturer_user_id foreign key (user_id) references user (id) on delete restrict on update restrict;

create index ix_lecturer_department_id on lecturer (department_id);
alter table lecturer add constraint fk_lecturer_department_id foreign key (department_id) references department (id) on delete restrict on update restrict;

create index ix_student_user_id on student (user_id);
alter table student add constraint fk_student_user_id foreign key (user_id) references user (id) on delete restrict on update restrict;

create index ix_student_course_id on student (course_id);
alter table student add constraint fk_student_course_id foreign key (course_id) references course (id) on delete restrict on update restrict;

create index ix_unit_lecturer_id on unit (lecturer_id);
alter table unit add constraint fk_unit_lecturer_id foreign key (lecturer_id) references lecturer (id) on delete restrict on update restrict;

create index ix_cls_unit_id on cls (unit_id);
alter table cls add constraint fk_cls_unit_id foreign key (unit_id) references unit (id) on delete restrict on update restrict;

create index ix_cls_lecturer_id on cls (lecturer_id);
alter table cls add constraint fk_cls_lecturer_id foreign key (lecturer_id) references lecturer (id) on delete restrict on update restrict;

create index ix_cls_lecture_hall_id on cls (lecture_hall_id);
alter table cls add constraint fk_cls_lecture_hall_id foreign key (lecture_hall_id) references lecture_hall (id) on delete restrict on update restrict;


# --- !Downs

alter table attendance drop foreign key fk_attendance_cls_id;
drop index ix_attendance_cls_id on attendance;

alter table attendance drop foreign key fk_attendance_student_id;
drop index ix_attendance_student_id on attendance;

alter table lecturer drop foreign key fk_lecturer_user_id;
drop index ix_lecturer_user_id on lecturer;

alter table lecturer drop foreign key fk_lecturer_department_id;
drop index ix_lecturer_department_id on lecturer;

alter table student drop foreign key fk_student_user_id;
drop index ix_student_user_id on student;

alter table student drop foreign key fk_student_course_id;
drop index ix_student_course_id on student;

alter table unit drop foreign key fk_unit_lecturer_id;
drop index ix_unit_lecturer_id on unit;

alter table cls drop foreign key fk_cls_unit_id;
drop index ix_cls_unit_id on cls;

alter table cls drop foreign key fk_cls_lecturer_id;
drop index ix_cls_lecturer_id on cls;

alter table cls drop foreign key fk_cls_lecture_hall_id;
drop index ix_cls_lecture_hall_id on cls;

drop table if exists attendance;

drop table if exists course;

drop table if exists department;

drop table if exists lecture_hall;

drop table if exists lecturer;

drop table if exists student;

drop table if exists unit;

drop table if exists user;

drop table if exists cls;

