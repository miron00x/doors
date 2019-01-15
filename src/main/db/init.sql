DROP DATABASE IF EXISTS `doors_project_db`;

CREATE DATABASE `doors_project_db` DEFAULT CHARACTER SET utf8;

USE `doors_project_db`;

CREATE TABLE `doors` (
	`id` INTEGER NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(255) NOT NULL,
	`light` BOOLEAN NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARACTER SET utf8;

INSERT INTO `doors`
(`id`, `name`, `light`)
VALUES
(1, "Secret room", true),
(2, "Ordinary room", false),
(3, "Torture room", true),
(4, "Red", true),
(5, "Black", false),
(6, "White", false);