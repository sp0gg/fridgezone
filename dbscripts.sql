CREATE USER fridgezoneUser@localhost IDENTIFIED BY 'gandalf';
use fridgezone_test;
GRANT SELECT,INSERT,UPDATE,DELETE ON fridgezone_test.* to fridgezoneUser@localhost;

alter table item add create_by varchar(25);
alter table item add create_date timestamp default current_timestamp;
alter table item modify id int(10)auto_increment;

