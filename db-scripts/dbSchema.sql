DROP DATABASE myexaminer;
CREATE DATABASE myexaminer;
USE myexaminer;

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema myexaminer
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `myexaminer` DEFAULT CHARACTER SET utf8 ;
USE `myexaminer` ;

-- -----------------------------------------------------
-- Table `myexaminer`.`account`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `myexaminer`.`account` ;

CREATE TABLE IF NOT EXISTS `myexaminer`.`account` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `email` VARCHAR(100) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `is_verified` BOOLEAN NOT NULL,
  `recovery_question` VARCHAR(100) NOT NULL,
  `recovery_answer` VARCHAR(45) NOT NULL,
  `is_lecturer` BOOLEAN NOT NULL,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `myexaminer`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `myexaminer`.`role` ;

CREATE TABLE IF NOT EXISTS `myexaminer`.`role` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  UNIQUE INDEX `role_id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `name` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `myexaminer`.`lecturer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `myexaminer`.`lecturer` ;

CREATE TABLE IF NOT EXISTS `myexaminer`.`lecturer` (
  `fk_account_id` BIGINT PRIMARY KEY,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `home_page` VARCHAR(100) NOT NULL,
  `contact_email` VARCHAR(100) NOT NULL,
  INDEX `fk_Lecturer_Account_idx` (`fk_account_id` ASC) VISIBLE,
  CONSTRAINT `fk_Lecturer_Account`
    FOREIGN KEY (`fk_account_id`)
    REFERENCES `myexaminer`.`account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `myexaminer`.`student`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `myexaminer`.`student` ;

CREATE TABLE IF NOT EXISTS `myexaminer`.`student` (
  `fk_account_id` BIGINT PRIMARY KEY,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `student_index` VARCHAR(6) NOT NULL,
  `faculty` VARCHAR(45) NOT NULL,
  `field_of_study` VARCHAR(45) NOT NULL,
  UNIQUE INDEX `student_index_UNIQUE` (`student_index` ASC) VISIBLE,
  CONSTRAINT `fk_Student_Account1`
    FOREIGN KEY (`fk_account_id`)
    REFERENCES `myexaminer`.`account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `myexaminer`.`teaching_group`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `myexaminer`.`teaching_group` ;

CREATE TABLE IF NOT EXISTS `myexaminer`.`teaching_group` (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  group_name VARCHAR(100) NOT NULL,
  access_code VARCHAR(45) NOT NULL,
  starting_date DATETIME NOT NULL,
  fk_lecturer_account_id BIGINT NOT NULL,
  UNIQUE (`group_name`),
  FOREIGN KEY (`fk_lecturer_account_id`) REFERENCES `myexaminer`.`lecturer`(`fk_account_id`)
  )
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `myexaminer`.`student_teaching_group`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `myexaminer`.`student_teaching_group` ;

CREATE TABLE IF NOT EXISTS `myexaminer`.`student_teaching_group` (
  `fk_teaching_group_id` BIGINT NOT NULL,
  `fk_student_account_id` BIGINT NOT NULL,
  PRIMARY KEY (`fk_teaching_group_id`, `fk_student_account_id`),
  KEY `fk_teaching_group_id` (`fk_teaching_group_id`),
  CONSTRAINT `student_teaching_group_ibfk_2`
  FOREIGN KEY (`fk_teaching_group_id`) REFERENCES `myexaminer`.`teaching_group`(`id`),
  KEY `fk_student_account_id` (`fk_student_account_id`),
  CONSTRAINT `student_teaching_group_ibfk_1`
  FOREIGN KEY (`fk_student_account_id`) REFERENCES `myexaminer`.`student`(`fk_account_id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `myexaminer`.`lesson`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `myexaminer`.`lesson` ;

CREATE TABLE IF NOT EXISTS `myexaminer`.`lesson` (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  date DATETIME NOT NULL,
  topic VARCHAR(255) NOT NULL,
  description VARCHAR(255) NOT NULL,
  teaching_group_id BIGINT NOT NULL,
  FOREIGN KEY (`teaching_group_id`) REFERENCES `myexaminer`.`teaching_group`(`id`)
  )
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `myexaminer`.`chapter`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `myexaminer`.`chapter` ;

CREATE TABLE IF NOT EXISTS `myexaminer`.`chapter` (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL,
  content LONGTEXT NOT NULL,
  lesson_id BIGINT NOT NULL,
  FOREIGN KEY (`lesson_id`) REFERENCES `myexaminer`.`lesson`(`id`)
  )
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `myexaminer`.`account_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `myexaminer`.`account_role` ;

CREATE TABLE IF NOT EXISTS `myexaminer`.`account_role` (
  `fk_account_id` BIGINT NOT NULL,
  `fk_role_id` BIGINT NOT NULL,
  PRIMARY KEY (`fk_account_id`, `fk_role_id`),
  KEY `fk_role_id` (`fk_role_id`),
  CONSTRAINT `account_roles_ibfk_2`
  FOREIGN KEY (`fk_role_id`) REFERENCES `myexaminer`.`role`(`id`),
  KEY `fk_account_id` (`fk_account_id`),
  CONSTRAINT `account_roles_ibfk_1`
  FOREIGN KEY (`fk_account_id`) REFERENCES `myexaminer`.`account`(`id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `myexaminer`.`exam`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `myexaminer`.`exam` ;

CREATE TABLE IF NOT EXISTS `myexaminer`.`exam` (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  description VARCHAR(500),
  available_from DATETIME,
  duration BIGINT NOT NULL,
  state varchar(50),
  fk_teaching_group_id BIGINT,
  FOREIGN KEY (`fk_teaching_group_id`) REFERENCES `myexaminer`.`teaching_group`(`id`)
  )
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `myexaminer`.`exercise`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `myexaminer`.`exercise` ;

CREATE TABLE IF NOT EXISTS `myexaminer`.`exercise` (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  content JSON NOT NULL,
  fk_exam_id BIGINT NOT NULL,
  FOREIGN KEY (`fk_exam_id`) REFERENCES `myexaminer`.`exam`(`id`)
  )
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `myexaminer`.`individual_exam`
-- -----------------------------------------------------

DROP TABLE IF EXISTS `myexaminer`.`individual_exam` ;

CREATE TABLE IF NOT EXISTS `myexaminer`.`individual_exam` (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  fk_exam_id BIGINT NOT NULL,
  fk_student_id BIGINT NOT NULL,
  `is_checked` BOOLEAN NOT NULL,
  FOREIGN KEY (`fk_exam_id`) REFERENCES `myexaminer`.`exam`(`id`),
  FOREIGN KEY (`fk_student_id`) REFERENCES `myexaminer`.`student`(`fk_account_id`)
  )
ENGINE = InnoDB;

DROP TABLE IF EXISTS `myexaminer`.`archive_exercise` ;

CREATE TABLE IF NOT EXISTS `myexaminer`.`archive_exercise` (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  fk_exercise_id BIGINT NOT NULL,
  fk_individual_exam_id BIGINT NOT NULL,
  gained_points INT,
  answer JSON,
  lecturer_comment VARCHAR(500),
  FOREIGN KEY (`fk_exercise_id`) REFERENCES `myexaminer`.`exercise`(`id`),
  FOREIGN KEY (`fk_individual_exam_id`) REFERENCES `myexaminer`.`individual_exam`(`id`)
  )
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `myexaminer`.`notebook`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `myexaminer`.`notebook` ;

CREATE TABLE IF NOT EXISTS `myexaminer`.`notebook` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `content` LONGTEXT NULL,
  `fk_account_id` BIGINT NOT NULL,
  UNIQUE INDEX `fk_account_id_UNIQUE` (`fk_account_id` ASC) VISIBLE,
  CONSTRAINT `fk_Student_Account2`
    FOREIGN KEY (`fk_account_id`)
    REFERENCES `myexaminer`.`account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
