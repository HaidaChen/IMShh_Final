create database IMShh;

create user 'imshh'@'localhost' identified by 'imshh';
use IMShh;
grant all on *.* to 'imshh'@'%' identified by 'imshh';
flush privileges;
grant all privileges on IMShh to imshh@'%';
flush privileges;