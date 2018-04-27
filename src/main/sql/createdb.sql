create database IMShh;

create user 'imshh'@'localhost' identified by 'imshh';
use IMShh;
grant all privileges on IMShh to imshh@'%';