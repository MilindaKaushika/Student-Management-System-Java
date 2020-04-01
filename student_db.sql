---------------- STUDENT TABLE -------------------

create table student(

sid int primary key,
fname varchar(25),
mname varchar(25),
lname varchar(25),
DOB varchar(40),
phone_num int,
code varchar(20),

foreign key (code) references course(code)

);

--------------- COURSE TABLE ---------------------
create table course(
code varchar(20) primary key,
name varchar(40),
);

---------------------------- MODULE TABLE -------------

create table module(

mod_num int primary key,
name varchar(40),
num_of_units int,
code varchar(20),

foreign key (code) references course(code)

);
 ----------------------STUDENT_MODULES TABLE----------------

 create table student_follows_modules(
 
 sid int,
 mod_num  int,

 primary key(sid,mod_num),
 
foreign key (sid) references student(sid),

foreign key (mod_num) references module(mod_num)
 );

 -----------------------LECTURER TABLE ----------------------

 create table lecturer(
 
 lec_id int primary key,
 fname varchar(20),
 lname varchar(20),
 DOB varchar(40),
 
  );


-----------------------LECTURER_CONDUCT_MODULE-------------------

create table lec_conduct_module(

lec_id int,
mod_num int,

primary key(lec_id,mod_num),

foreign key (lec_id) references lecturer(lec_id),

foreign key (mod_num) references module(mod_num)

);

