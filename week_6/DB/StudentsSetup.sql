CREATE DATABASE  IF NOT EXISTS `students` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `students`;


DROP TABLE IF EXISTS `student`;
DROP TABLE IF EXISTS `module`;
DROP TABLE IF EXISTS `takes`;
CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `address` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

CREATE TABLE `module` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

CREATE TABLE `takes` (
  `moduleID` int(11) NOT NULL,
  `studentID` int(11) NOT NULL,
  `mark` int(100) NOT NULL,
  `credits` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DELIMITER $$
 DROP PROCEDURE IF EXISTS populate_student$$
 CREATE PROCEDURE populate_student()
 BEGIN
 	DECLARE nStudent INT;
  DECLARE n INT;
  SET nStudent = 300;
	SET n= 1;
 	REPEAT
  Insert into student (name, address) VALUES ( FLOOR((RAND() * (200-1+1))+1), 'studentflat');
 		SET  n = n + 1;
    UNTIL n  > nStudent
 	END REPEAT;
END$$
DELIMITER ;

DELIMITER $$
 DROP PROCEDURE IF EXISTS populate_module$$
 CREATE PROCEDURE populate_module()
 BEGIN
 	DECLARE nModule INT;
  DECLARE n INT;
  SET nModule = 50;
	SET n= 1;
 	REPEAT
  Insert into module (name, description) VALUES ( FLOOR((RAND() * (200-1+1))+1), 'really hard');
 		SET  n = n + 1;
    UNTIL n  > nModule
 	END REPEAT;
END$$
DELIMITER ;

DELIMITER $$
 DROP PROCEDURE IF EXISTS populate_takes$$
 CREATE PROCEDURE populate_takes()
 BEGIN
 	DECLARE ntakes INT;
  DECLARE n INT;
  SET nTakes = 5000;
	SET n= 1;
 	REPEAT
  Insert into takes (moduleID, studentID, mark , credits) VALUES ( FLOOR((RAND() * (5000-1+1))+1),FLOOR((RAND() * (30000-1+1))+1),FLOOR((RAND() * (100-1+1))+1), 20);
 		SET  n = n + 1;
    UNTIL n  > nTakes
 	END REPEAT;
END$$
DELIMITER ;

CALL populate_module();
CALL populate_student();
CALL populate_takes();


explain select * from takes where studentID = 15;
select * from takes where studentID = 15;
explain select * from takes left join module on takes.moduleID = module.id where studentID = 15;
select * from takes left join module on takes.moduleID = module.id where studentID = 15;


explain select * from takes left join module on takes.moduleID = module.id where moduleID = 15;
select * from takes left join module on takes.moduleID = module.id where moduleID = 15;
