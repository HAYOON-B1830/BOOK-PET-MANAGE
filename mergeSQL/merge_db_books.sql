-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: merge_db
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
  `BOOK_NO` varchar(20) NOT NULL,
  `BOOK_TITLE` varchar(30) NOT NULL,
  `AUTHOR` varchar(20) NOT NULL,
  `PUBLISHER` varchar(30) NOT NULL,
  `PUBLISHER_YEAR` date NOT NULL,
  `IS_RENT` varchar(10) NOT NULL DEFAULT '대출 가능',
  PRIMARY KEY (`BOOK_NO`,`BOOK_TITLE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES ('10','토익기출 공식입문서RC','YBM','YBM','2022-02-22','대출가능'),('128058CE0000060717','GRIT 그릿','앤젤라 더크워스','비즈니스 북스','2022-12-15','대출가능'),('2','관계의 힘','레이먼드 조','한국경제신문','2013-09-10','대출가능'),('23','매운 너구리 컵라면','농심너구리','농심출판','2022-02-02','대출가능'),('3','미움받을 용기','기시미 이치로','인플루엔셜','2014-11-17','대출중'),('4','프레임','최인철','21세기북스','2021-03-02','대출가능'),('5','궁극의 생명 Life','리처드 도킨스','와이즈베리','2017-01-11','대출중'),('6','데이터베이스 개론','김연희','한빛아카데미','2022-01-15','대출중'),('7','총 균 쇠','재레드 다이아몬드','김영사','2023-05-10','대출중'),('8','정의란 무엇인가','마이클 샌델','와이즈베리','2014-11-20','대출중'),('9','안드로이드프로그래밍','장용식','인피니티북스','2019-07-12','대출중'),('E99805D','일과인권','인권','인권','2022-02-22','대출중'),('EMH000009849','쉽게 배우는 리코더','박미경 ','음악세계','2003-01-01','대출중'),('EMH000083326','쉽게 찾는 날씨','스톰 던롭','현암사','2008-01-01','대출가능'),('EMH000102105','쉽게 읽는 언어철학','박병철 ','서광사','2010-01-01','대출가능'),('EMH000103500','쉽게 찾는 야생화','김태정 ','현암사','2010-01-01','대출가능'),('EMH000114482','쉽게 배우는 재봉틀','미즈노 요시코','싸이플렉스','2011-01-01','대출가능'),('EMH000125012','데이터베이스 개론','김연희','김연희','2013-01-01','대출가능'),('EMH000149625','쉽게 배우는 천문학 = Astronomy','채동현','교육과학사','2016-01-01','대출가능'),('EMH000155343','데이터베이스 시스템','Ramez Elmasri','홍릉과학출판사','2011-01-01','대출중'),('EMH000205854','데이터베이스 시스템 개론과 MySQL 실습','이용주 ','홍릉','2022-01-01','대출가능'),('JUH000013392','쉽게 찾는 우리 곤충','김진일 ','현암사','2004-01-01','대출가능');
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-17 21:07:48
