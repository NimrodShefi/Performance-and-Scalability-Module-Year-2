CREATE DATABASE  IF NOT EXISTS `employees` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `employees`;


DROP TABLE IF EXISTS `employee`;

CREATE TABLE `employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `department` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `address` varchar(20) NOT NULL,
  `pc_make` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `address`;

CREATE TABLE `address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(200) NOT NULL,
  `info` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `computer`;

CREATE TABLE `computer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pc_make` varchar(20) NOT NULL,
  `info` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

Insert into address (address, info) VALUES ( 'home','cosy');
Insert into address (address, info) VALUES ( 'cardiff','boozy');
Insert into address (address, info) VALUES ( 'monmouth','snoozy');



DELIMITER $$
 DROP PROCEDURE IF EXISTS mysql_populate_employee_table$$
 CREATE PROCEDURE mysql_populate_employee_table()
 BEGIN
 	DECLARE nEmployees INT;
  DECLARE n INT;

  -- SET nEmployees = 30000;
  SET nEmployees = 20000;
	SET n= 1;

 	REPEAT
  Insert into employee (department, name,address, pc_make) VALUES ( FLOOR((RAND() * (200-1+1))+1),'bob','home', 'dell');
  Insert into employee (department, name,address, pc_make) VALUES ( FLOOR((RAND() * (200-1+1))+1),'bob','home', 'mac');
  Insert into employee (department, name,address, pc_make) VALUES ( FLOOR((RAND() * (200-1+1))+1),'bob','home', 'hp');
  Insert into computer (pc_make, info) VALUES ('dell' , "'computer'+n");
  Insert into computer (pc_make, info) VALUES ('hp' , "'computer'+n");
  Insert into computer (pc_make, info) VALUES ('mac' , "'computer'+n");
 		SET  n = n + 1;
    UNTIL n  > nEmployees
 	END REPEAT;
END$$
DELIMITER ;

DELIMITER $$
 DROP PROCEDURE IF EXISTS mysql_populate_address_table$$
 CREATE PROCEDURE mysql_populate_address_table()
 BEGIN
 	DECLARE nAddress INT;
  DECLARE n INT;

  -- SET nEmployees = 30000;
  SET naddress = 200;
	SET n= 1;

 	REPEAT
  Insert into address (address, info) VALUES ( 'temp', FLOOR((RAND() * (200-1+1))+1));
 		SET  n = n + 1;
    UNTIL n  > nAddress
 	END REPEAT;
END$$
DELIMITER ;

Insert into employee (department, name,address, pc_make) VALUES ( FLOOR((RAND() * (200-1+1))+1),'ian','cardiff', 'dell');
Insert into employee (department, name,address, pc_make) VALUES ( FLOOR((RAND() * (200-1+1))+1),'James','monmouth', 'hp');
CALL mysql_populate_employee_table();
CALL mysql_populate_address_table();

select count(*) from employee where department = 2;
