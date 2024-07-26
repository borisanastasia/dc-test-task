drop table if exists employee;
drop table if exists department;

create table department(
	id integer primary key,
	name varchar(50) not null unique
);

create table employee(
	id integer primary key,
	name varchar(50) not null,
	salary integer not null,
	role varchar(50) not null,
	department_id integer references department(id)
);