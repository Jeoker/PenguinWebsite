-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema PenguinWeb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema PenguinWeb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `PenguinWeb` ;
USE `PenguinWeb` ;

-- -----------------------------------------------------
-- Table `PenguinWeb`.`Users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `PenguinWeb`.`Users` ;

CREATE TABLE IF NOT EXISTS `PenguinWeb`.`Users` (
    `UserId` INT NOT NULL AUTO_INCREMENT,
    `UserName` VARCHAR(255) NULL,
    `Password` VARCHAR(255) NULL,
    `Status` ENUM("Administrator", "Researcher", "User") NULL,
    PRIMARY KEY (`UserId`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `PenguinWeb`.`Researchers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `PenguinWeb`.`Researchers` ;

CREATE TABLE IF NOT EXISTS `PenguinWeb`.`Researchers` (
    `UserId` INT NOT NULL,
    `FirstName` VARCHAR(255) NULL,
    `LastName` VARCHAR(255) NULL,
    `Gender` TINYINT NULL,
    `AcademicPaper` LONGTEXT NULL,
    `Institute` VARCHAR(255) NULL,
    PRIMARY KEY (`UserId`),
    INDEX `Researcher_Users1_idx` (`UserId` ASC) VISIBLE,
    CONSTRAINT `Researcher_Users1`
        FOREIGN KEY (`UserId`)
            REFERENCES `PenguinWeb`.`Users` (`UserId`)
            ON DELETE CASCADE
            ON UPDATE CASCADE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `PenguinWeb`.`Posts`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `PenguinWeb`.`Posts` ;

CREATE TABLE IF NOT EXISTS `PenguinWeb`.`Posts` (
    `PostId` INT NOT NULL AUTO_INCREMENT,
    `Title` VARCHAR(255) NOT NULL,
    `Picture` LONGBLOB NULL,
    `Content` LONGTEXT NULL,
    `Published` TINYINT NULL DEFAULT 0,
    `Created` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    `UserId` INT NULL,
    PRIMARY KEY (`PostId`),
    INDEX `Posts_Users1_idx` (`UserId` ASC) VISIBLE,
    CONSTRAINT `Posts_Users1`
        FOREIGN KEY (`UserId`)
            REFERENCES `PenguinWeb`.`Users` (`UserId`)
            ON DELETE SET NULL
            ON UPDATE CASCADE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `PenguinWeb`.`Comments`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `PenguinWeb`.`Comments` ;

CREATE TABLE IF NOT EXISTS `PenguinWeb`.`Comments` (
    `CommentId` INT NOT NULL AUTO_INCREMENT,
    `Content` VARCHAR(255) NOT NULL,
    `Created` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    `UserId` INT NULL,
    `PostId` INT NULL,
    `FatherCommentId` INT NULL,
    PRIMARY KEY (`CommentId`),
    INDEX `Comments_Users1_idx` (`UserId` ASC) VISIBLE,
    INDEX `Comments_Posts1_idx` (`PostId` ASC) VISIBLE,
    CONSTRAINT `Comments_Users1`
        FOREIGN KEY (`UserId`)
            REFERENCES `PenguinWeb`.`Users` (`UserId`)
            ON DELETE SET NULL
            ON UPDATE CASCADE,
    CONSTRAINT `Comments_Posts1`
        FOREIGN KEY (`PostId`)
            REFERENCES `PenguinWeb`.`Posts` (`PostId`)
            ON DELETE CASCADE
            ON UPDATE CASCADE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `PenguinWeb`.`Reshares`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `PenguinWeb`.`Reshares` ;

CREATE TABLE IF NOT EXISTS `PenguinWeb`.`Reshares` (
    `ReshareId` INT NOT NULL AUTO_INCREMENT,
    `UserId` INT NULL,
    `PostId` INT NULL,
    `CommentId` INT NULL,
    PRIMARY KEY (`ReshareId`),
    INDEX `Reshares_Users1_idx` (`UserId` ASC) VISIBLE,
    INDEX `Reshares_Posts1_idx` (`PostId` ASC) VISIBLE,
    INDEX `Reshares_Comments1_idx` (`CommentId` ASC) VISIBLE,
    CONSTRAINT `Reshares_Users1`
        FOREIGN KEY (`UserId`)
            REFERENCES `PenguinWeb`.`Users` (`UserId`)
            ON DELETE SET NULL
            ON UPDATE CASCADE,
    CONSTRAINT `Reshares_Posts1`
        FOREIGN KEY (`PostId`)
            REFERENCES `PenguinWeb`.`Posts` (`PostId`)
            ON DELETE SET NULL
            ON UPDATE CASCADE,
    CONSTRAINT `Reshares_Comments1`
        FOREIGN KEY (`CommentId`)
            REFERENCES `PenguinWeb`.`Comments` (`CommentId`)
            ON DELETE SET NULL
            ON UPDATE CASCADE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `PenguinWeb`.`Collections`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `PenguinWeb`.`Collections` ;

CREATE TABLE IF NOT EXISTS `PenguinWeb`.`Collections` (
    `CollectionId` INT NOT NULL AUTO_INCREMENT,
    `UserId` INT NULL,
    `PostId` INT NULL,
    `CommentId` INT NULL,
    PRIMARY KEY (`CollectionId`),
    INDEX `Collections_Users1_idx` (`UserId` ASC) VISIBLE,
    INDEX `Collections_Posts1_idx` (`PostId` ASC) VISIBLE,
    INDEX `Collections_Comments1_idx` (`CommentId` ASC) VISIBLE,
    CONSTRAINT `Collections_Users1`
        FOREIGN KEY (`UserId`)
            REFERENCES `PenguinWeb`.`Users` (`UserId`)
            ON DELETE SET NULL
            ON UPDATE CASCADE,
    CONSTRAINT `Collections_Posts1`
        FOREIGN KEY (`PostId`)
            REFERENCES `PenguinWeb`.`Posts` (`PostId`)
            ON DELETE SET NULL
            ON UPDATE CASCADE,
    CONSTRAINT `Collections_Comments1`
        FOREIGN KEY (`CommentId`)
            REFERENCES `PenguinWeb`.`Comments` (`CommentId`)
            ON DELETE SET NULL
            ON UPDATE CASCADE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `PenguinWeb`.`Likes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `PenguinWeb`.`Likes` ;

CREATE TABLE IF NOT EXISTS `PenguinWeb`.`Likes` (
    `LikeId` INT NOT NULL AUTO_INCREMENT,
    `UserId` INT NULL,
    `PostId` INT NULL,
    `CommentId` INT NULL,
    PRIMARY KEY (`LikeId`),
    INDEX `Likes_Users1_idx` (`UserId` ASC) VISIBLE,
    INDEX `Likes_Posts1_idx` (`PostId` ASC) VISIBLE,
    INDEX `Likes_Comments1_idx` (`CommentId` ASC) VISIBLE,
    CONSTRAINT `Likes_Users1`
        FOREIGN KEY (`UserId`)
            REFERENCES `PenguinWeb`.`Users` (`UserId`)
            ON DELETE SET NULL
            ON UPDATE CASCADE,
    CONSTRAINT `Likes_Posts1`
        FOREIGN KEY (`PostId`)
            REFERENCES `PenguinWeb`.`Posts` (`PostId`)
            ON DELETE SET NULL
            ON UPDATE CASCADE,
    CONSTRAINT `Likes_Comments1`
        FOREIGN KEY (`CommentId`)
            REFERENCES `PenguinWeb`.`Comments` (`CommentId`)
            ON DELETE SET NULL
            ON UPDATE CASCADE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `PenguinWeb`.`Cameras`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `PenguinWeb`.`Cameras` ;

CREATE TABLE IF NOT EXISTS `PenguinWeb`.`Cameras` (
  `CameraId` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NULL,
  PRIMARY KEY (`CameraId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `PenguinWeb`.`Sites`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `PenguinWeb`.`Sites` ;

CREATE TABLE IF NOT EXISTS `PenguinWeb`.`Sites` (
  `SiteId` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NOT NULL,
  `Date` DATE NOT NULL,
  PRIMARY KEY (`SiteId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `PenguinWeb`.`Images`
-- -----------------------------------------------------

DROP TABLE IF EXISTS `PenguinWeb`.`Images` ;

CREATE TABLE IF NOT EXISTS `PenguinWeb`.`Images` (
  `ImageId` INT NOT NULL AUTO_INCREMENT,
  `FileName` VARCHAR(45) NOT NULL,
  `FileType` ENUM("jpg", "png", "tif", "tiff", "dng") NOT NULL,
  `SiteId` INT NULL,
  `Size` INT NULL,
  `MediaLink` VARCHAR(100) NULL,
  `TimeStamp` TIMESTAMP NULL,
  `Width` INT NULL,
  `Height` INT NULL,
  `Longitude` DOUBLE NULL,
  `Latitude` DOUBLE NULL,
  `CameraId` INT NULL,
  PRIMARY KEY (`ImageId`),
  INDEX `camID_idx` (`CameraId` ASC) VISIBLE,
  INDEX `siteID_idx` (`SiteId` ASC) VISIBLE,
  CONSTRAINT `Image_Cam_fk`
    FOREIGN KEY (`CameraId`)
    REFERENCES `PenguinWeb`.`Cameras` (`CameraId`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  INDEX `SiteId1_idx` (`SiteId` ASC),
  CONSTRAINT `SiteId1`
    FOREIGN KEY (`SiteId`)
    REFERENCES `PenguinWeb`.`Sites` (`SiteId`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `PenguinWeb`.`UAVs`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `PenguinWeb`.`UAVs` ;

CREATE TABLE IF NOT EXISTS `PenguinWeb`.`UAVs` (
  `UavId` INT NOT NULL AUTO_INCREMENT,
  `Model` VARCHAR(45) NOT NULL,
  `CameraId` INT NULL,
  `Weight` FLOAT NOT NULL,
  PRIMARY KEY (`UavId`),
  INDEX `idx_Cam` (`CameraId` ASC) VISIBLE,
  CONSTRAINT `fk_Uav_Camera`
    FOREIGN KEY (`CameraId`)
    REFERENCES `PenguinWeb`.`Cameras` (`CameraId`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `PenguinWeb`.`Models`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `PenguinWeb`.`Models` ;

CREATE TABLE IF NOT EXISTS `PenguinWeb`.`Models` (
  `ModelId` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NOT NULL,
  `CreateTime` TIMESTAMP NOT NULL,
  PRIMARY KEY (`ModelId`))
ENGINE = InnoDB;



-- -----------------------------------------------------
-- Table `PenguinWeb`.`Detections`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `PenguinWeb`.`Detections` ;

CREATE TABLE IF NOT EXISTS `PenguinWeb`.`Detections` (
  `DetectionId` INT NOT NULL AUTO_INCREMENT,
  `ImageId` INT NOT NULL,
  `Count` INT NOT NULL,
  `PathOnCloud` VARCHAR(99) NOT NULL,
  `ModelId` INT NOT NULL,
  PRIMARY KEY (`DetectionId`),
  INDEX `ImageId1_idx` (`ImageId` ASC),
  CONSTRAINT `ImageId1`
    FOREIGN KEY (`ImageId`)
    REFERENCES `PenguinWeb`.`Images` (`ImageId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  INDEX `ModelId1_idx` (`ModelId` ASC),
  CONSTRAINT `ModelId1`
    FOREIGN KEY (`ModelId`)
    REFERENCES `PenguinWeb`.`Models` (`ModelId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `PenguinWebsite`.`Participates`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `PenguinWeb`.`Participates` ;

CREATE TABLE IF NOT EXISTS `PenguinWeb`.`Participates` (
  `ParticipateId` INT NOT NULL AUTO_INCREMENT,
  `SiteId` INT NULL,
  `ResearcherId` INT NULL,
  PRIMARY KEY (`ParticipateId`),
  INDEX `Participate_Site_fk_idx` (`SiteId` ASC) VISIBLE,
  INDEX `Participate_Researcher_fk_idx` (`ResearcherId` ASC) VISIBLE,
  CONSTRAINT `fk_Participate_Site`
    FOREIGN KEY (`SiteId`)
    REFERENCES `PenguinWeb`.`Sites` (`SiteId`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `Participate_Researcher_fk`
    FOREIGN KEY (`ResearcherId`)
    REFERENCES `PenguinWeb`.`Researchers` (`UserId`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `PenguinWebsite`.`Weathers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `PenguinWeb`.`Weathers` ;

CREATE TABLE IF NOT EXISTS `PenguinWeb`.`Weathers` (
  `WeatherId` INT NOT NULL AUTO_INCREMENT,
  `Time` DATETIME NULL,
  `TmpOut` FLOAT NULL,
  `TmpH` FLOAT NULL,
  `TmpL` FLOAT NULL,
  `WindSpeed` FLOAT NULL,
  `WindDir` VARCHAR(45) NULL,
  `WindRun` FLOAT NULL,
  `HiSpeed` FLOAT NULL,
  `WindChill` VARCHAR(45) NULL,
  `Bar` VARCHAR(45) NULL,
  `HeatDD` VARCHAR(45) NULL,
  `CoolDD` VARCHAR(45) NULL,
  `TmpIn` VARCHAR(45) NULL,
  `HumIn` VARCHAR(45) NULL,
  `ArcInt` VARCHAR(45) NULL,
  `Longitude` DOUBLE NULL,
  `Latitude` DOUBLE NULL,
  PRIMARY KEY (`WeatherId`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


INSERT INTO Users(UserName,Password,Status)
VALUES('SanZhang','88888','Researcher');
INSERT INTO Users(UserName,Password,Status)
VALUES('SiLi','66666','Researcher');
INSERT INTO Users(UserName, Password, Status)
VALUES('researcher1','123','Researcher');
INSERT INTO Users(UserName, Password, Status)
VALUES('researcher2','123','Researcher');
INSERT INTO Users(UserName, Password, Status)
VALUES('researcher3','123','researcher');
