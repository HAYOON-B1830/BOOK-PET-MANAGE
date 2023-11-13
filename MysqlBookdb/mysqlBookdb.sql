CREATE DATABASE  IF NOT EXISTS `bookdb_schema` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `bookdb_schema`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: bookdb_schema
-- ------------------------------------------------------
-- Server version	8.0.34

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
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `books` (
  `BOOK_NO` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `BOOK_TITLE` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `AUTHOR` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `PUBLISHER` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `PUBLISHER_YEAR` date NOT NULL,
  `IS_RENT` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '대출가능',
  PRIMARY KEY (`BOOK_NO`,`BOOK_TITLE`)
) ENGINE=InnoDB DEFAULT CHARSET=ucs2;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES ('10','토익기출 공식입문서RC','YBM','YBM','2022-02-22','대출가능'),('128058CE0000060717','GRIT 그릿','앤젤라 더크워스','비즈니스 북스','2022-12-15','대출가능'),('2','관계의 힘','레이먼드 조','한국경제신문','2013-09-10','대출중'),('23','매운 너구리 컵라면','농심','농심출판','2022-02-02','대출중'),('3','미움받을 용기','기시미 이치로','인플루엔셜','2014-11-17','대출중'),('4','프레임','최인철','21세기북스','2021-03-02','대출중'),('5','궁극의 생명 Life','리처드 도킨스','와이즈베리','2017-01-11','대출중'),('6','데이터베이스 개론','김연희','한빛아카데미','2022-01-15','대출중'),('7','총 균 쇠','재레드 다이아몬드','김영사','2023-05-10','대출중'),('8','정의란 무엇인가','마이클 샌델','와이즈베리','2014-11-20','대출중'),('9','안드로이드프로그래밍','장용식','인피니티북스','2019-07-12','대출중'),('E99805D','일과인권','인권','인권','2022-02-22','대출중'),('EMH000009849','쉽게 배우는 리코더','박미경 ','음악세계','2003-01-01','대출가능'),('EMH000083326','쉽게 찾는 날씨','스톰 던롭','현암사','2008-01-01','대출가능'),('EMH000102105','쉽게 읽는 언어철학','박병철 ','서광사','2010-01-01','대출가능'),('EMH000103500','쉽게 찾는 야생화','김태정 ','현암사','2010-01-01','대출가능'),('EMH000114482','쉽게 배우는 재봉틀','미즈노 요시코','싸이플렉스','2011-01-01','대출가능'),('EMH000125012','데이터베이스 개론','김연희','김연희','2013-01-01','대출가능'),('EMH000149625','쉽게 배우는 천문학 = Astronomy','채동현','교육과학사','2016-01-01','대출가능'),('EMH000155343','데이터베이스 시스템','Ramez Elmasri','홍릉과학출판사','2011-01-01','대출가능'),('EMH000205854','데이터베이스 시스템 개론과 MySQL 실습','이용주 ','홍릉','2022-01-01','대출가능'),('JUH000013392','쉽게 찾는 우리 곤충','김진일 ','현암사','2004-01-01','대출가능');
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `members`
--

DROP TABLE IF EXISTS `members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `members` (
  `USER_ID` varchar(20) NOT NULL,
  `USER_NAME` varchar(20) NOT NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `members`
--

LOCK TABLES `members` WRITE;
/*!40000 ALTER TABLE `members` DISABLE KEYS */;
INSERT INTO `members` VALUES ('BM','manager_name'),('eden','tomoe hiyori'),('hyuna2398','hyuna');
/*!40000 ALTER TABLE `members` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rent`
--

DROP TABLE IF EXISTS `rent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rent` (
  `RENT_NUMBER` varchar(20) NOT NULL,
  `RENT_BOOK_NUMBER` varchar(20) DEFAULT NULL,
  `RENT_BOOK_TITLE` varchar(30) DEFAULT NULL,
  `RENT_USER_ID` varchar(20) DEFAULT NULL,
  `RENT_DATE` date DEFAULT NULL,
  `RETURN_DATE` date DEFAULT NULL,
  `IS_RETURN` tinyint DEFAULT NULL,
  `DAYS` int NOT NULL DEFAULT '14',
  PRIMARY KEY (`RENT_NUMBER`),
  KEY `book_no_idx` (`RENT_BOOK_NUMBER`),
  KEY `RENT_USER_ID_idx` (`RENT_USER_ID`),
  KEY `RENT_BOOK_TITLE_idx` (`RENT_BOOK_TITLE`),
  CONSTRAINT `RENT_BOOK_NUMBER` FOREIGN KEY (`RENT_BOOK_NUMBER`) REFERENCES `books` (`BOOK_NO`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `RENT_USER_ID` FOREIGN KEY (`RENT_USER_ID`) REFERENCES `members` (`USER_ID`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rent`
--

LOCK TABLES `rent` WRITE;
/*!40000 ALTER TABLE `rent` DISABLE KEYS */;
INSERT INTO `rent` VALUES ('R128058CE0000060717','128058CE0000060717','GRIT 그릿','hyuna2398','2023-11-12','2023-11-12',NULL,14),('R2','2','관계의 힘','hyuna2398','2023-11-02',NULL,NULL,14),('R23','23','매운 너구리 컵라면','hyuna2398','2023-11-12',NULL,NULL,14),('R3','3','미움받을 용기','hyuna2398','2023-11-12',NULL,NULL,14),('R5','5','궁극의 생명 Life','hyuna2398','2023-11-08','2023-11-08',NULL,14),('R6','6','데이터베이스 개론','hyuna2398','2023-11-12','2023-11-12',NULL,14),('R7','7','총 균 쇠','hyuna2398','2023-11-07','2023-11-07',NULL,14),('R9','9','안드로이드프로그래밍','hyuna2398','2023-11-12','2023-11-12',NULL,14),('RE99805D','E99805D','일과인권','hyuna2398','2023-11-12',NULL,NULL,14);
/*!40000 ALTER TABLE `rent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'bookdb_schema'
--

--
-- Dumping routines for database 'bookdb_schema'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-12  2:43:38
