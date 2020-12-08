-- MySQL dump 10.13  Distrib 8.0.22, for Linux (x86_64)
--
-- Host: localhost    Database: GamifyDB
-- ------------------------------------------------------
-- Server version	8.0.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Answer`
--

DROP TABLE IF EXISTS `Answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Answer` (
  `answer_id` int NOT NULL,
  `review_id` int NOT NULL,
  `question_id` int NOT NULL,
  `content` varchar(45) NOT NULL,
  PRIMARY KEY (`answer_id`),
  UNIQUE KEY `unique` (`review_id`,`question_id`),
  KEY `fk_Answer_Question_idx` (`question_id`),
  CONSTRAINT `fk_Answer_Question` FOREIGN KEY (`question_id`) REFERENCES `Question` (`question_id`),
  CONSTRAINT `fk_Answer_Review` FOREIGN KEY (`review_id`) REFERENCES `Review` (`review_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Log`
--

DROP TABLE IF EXISTS `Log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Log` (
  `log_id` int NOT NULL,
  `questionaire_id` int NOT NULL,
  `user_id` int NOT NULL,
  `datetime` datetime NOT NULL,
  PRIMARY KEY (`log_id`),
  KEY `fk_Log_Questionaire_idx` (`questionaire_id`),
  KEY `fk_Log_User_idx` (`user_id`),
  CONSTRAINT `fk_Log_Questionaire` FOREIGN KEY (`questionaire_id`) REFERENCES `Questionaire` (`questionaire_id`),
  CONSTRAINT `fk_Log_User` FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Offensive_word`
--

DROP TABLE IF EXISTS `Offensive_word`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Offensive_word` (
  `word` varchar(45) NOT NULL,
  PRIMARY KEY (`word`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Question`
--

DROP TABLE IF EXISTS `Question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Question` (
  `question_id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(45) NOT NULL,
  `questionaire_id` int NOT NULL,
  PRIMARY KEY (`question_id`),
  KEY `fk_Question_Questionaire_idx` (`questionaire_id`),
  CONSTRAINT `fk_Question_Questionaire` FOREIGN KEY (`questionaire_id`) REFERENCES `Questionaire` (`questionaire_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Questionaire`
--

DROP TABLE IF EXISTS `Questionaire`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Questionaire` (
  `questionaire_id` int NOT NULL AUTO_INCREMENT,
  `image` varchar(100) NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`questionaire_id`),
  UNIQUE KEY `image_UNIQUE` (`image`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Review`
--

DROP TABLE IF EXISTS `Review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Review` (
  `review_id` int NOT NULL AUTO_INCREMENT,
  `questionaire_id` int NOT NULL,
  `user_id` int NOT NULL,
  `can_access_sex` int NOT NULL,
  `can_access_age` int NOT NULL,
  `expertise` enum('low','medium','high') DEFAULT NULL,
  `datetime` datetime NOT NULL,
  `points` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`review_id`),
  UNIQUE KEY `index2` (`user_id`,`questionaire_id`),
  KEY `fk_Review_Questionaire_idx` (`questionaire_id`),
  CONSTRAINT `fk_Review_Questionaire` FOREIGN KEY (`questionaire_id`) REFERENCES `Questionaire` (`questionaire_id`),
  CONSTRAINT `fk_Review_User` FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `User` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password_hash` varchar(45) NOT NULL,
  `password_salt` varchar(45) NOT NULL,
  `sex` enum('male','female') DEFAULT NULL,
  `birth` date DEFAULT NULL,
  `blocked` int NOT NULL DEFAULT '0',
  `admin` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-12-08 23:05:54
