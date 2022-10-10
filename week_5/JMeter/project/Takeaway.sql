create database takeawayMSc;
use takeawayMSc;
drop table if exists items;
create table items (
	id int auto_increment primary key,
	name varchar (25),
    description varchar (100),
    price int);
