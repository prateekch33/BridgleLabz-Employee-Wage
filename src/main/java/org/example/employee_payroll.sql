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

alter table employee_payroll add department varchar(200) not null;

alter table employee_payroll
add basic_pay int,
add deductions int,
add taxable_pay int,
add income_tax int,
add net_pay int;

insert into employee_payroll
(name,salary,start_date,gender,phone,address,department,basic_pay,deductions,taxable_pay,income_tax,net_pay)
values
('Terissa',20000,'2020-12-08','F','6768690485','hsdhjkwnfdsj','Sales',10000,2000,1000,1200,15000),
('Terissa',20000,'2020-12-08','F','6768690485','hsdhjkwnfdsj','Marketing',15000,1000,2000,1200,20000);

select * from employee_payroll;

create table employee_department (dept_id int auto_increment primary key,emp_id int,foreign key(emp_id) references employee_payroll(id));

alter table employee_payroll drop column department;

alter table employee_payroll add dept_id int after address,
add foreign key(dept_id) references employee_department(dept_id);