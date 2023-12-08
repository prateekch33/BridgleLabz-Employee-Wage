create database payroll_service;

show databases;

--Output
information_schema
mysql
payroll_service
performance_schema
sys

use payroll_service;

create table employee_payroll(id integer auto_increment primary key, name varchar(20), salary float, start_date date);

insert into employee_payroll(id,name,salary,start_date) values (1,"test",20000,'2023-12-07');

select * from employee_payroll;

--Output
1	test	20000	2023-12-07


select salary from employee_payroll where name='test';

--Output
20000

select salary from employee_payroll where start_date between cast('2020-01-01' as date) and date(now());

--Output
20000

select sum(salary) from employee_payroll where gender='M';
-- Output
20000

select avg(salary) from employee_payroll where gender='M';
--Output
20000

alter table employee_payroll add gender char;

update employee_payroll set gender='M' where id='1';

select * from employee_payroll;