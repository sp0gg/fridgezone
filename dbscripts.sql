CREATE DATABASE fridgezone_<PROFILE>;
CREATE USER fridgezoneUser@localhost IDENTIFIED BY 'gandalf';
USE fridgezone_<PROFILE>;
GRANT SELECT,INSERT,UPDATE,DELETE ON fridgezone_<PROFILE>.* to fridgezoneUser@localhost;

CREATE TABLE item
(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name VARCHAR(255),
  create_by VARCHAR(25),
  create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE fridgezone_test.item ADD quantity INT NOT NULL;