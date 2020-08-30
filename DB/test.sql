-- MySQL dump 10.13  Distrib 5.7.31, for Linux (x86_64)
--
-- Host: localhost    Database: TorVerCar
-- ------------------------------------------------------
-- Server version	5.7.31-0ubuntu0.18.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Banned`
--

DROP TABLE IF EXISTS `Banned`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Banned` (
  `userID` varchar(10) NOT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Banned`
--

LOCK TABLES `Banned` WRITE;
/*!40000 ALTER TABLE `Banned` DISABLE KEYS */;
/*!40000 ALTER TABLE `Banned` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Cars`
--

DROP TABLE IF EXISTS `Cars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Cars` (
  `model` varchar(20) NOT NULL,
  `plate` varchar(10) NOT NULL,
  `seats` int(11) NOT NULL,
  `color` varchar(10) NOT NULL,
  `userID` varchar(10) NOT NULL,
  PRIMARY KEY (`plate`),
  KEY `Cars_ibfk_1` (`userID`),
  CONSTRAINT `Cars_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `Users` (`userID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cars`
--

LOCK TABLES `Cars` WRITE;
/*!40000 ALTER TABLE `Cars` DISABLE KEYS */;
INSERT INTO `Cars` VALUES ('Fiat Macchina','TV000CR',4,'Arcobaleno','0000000'),('Fiat Macchina','TV100CR',4,'Arcobaleno','0000001'),('Fiat Macchina','TV200CR',4,'Arcobaleno','0000002'),('Fiat Macchina','TV300CR',4,'Arcobaleno','0000003'),('Fiat Macchina','TV400CR',4,'Arcobaleno','0000004');
/*!40000 ALTER TABLE `Cars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Lifts`
--

DROP TABLE IF EXISTS `Lifts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Lifts` (
  `liftID` int(11) NOT NULL AUTO_INCREMENT,
  `startDateTime` datetime NOT NULL,
  `stopDateTime` datetime NOT NULL,
  `maxDuration` int(11) NOT NULL,
  `note` text,
  `driverID` varchar(10) NOT NULL,
  `route` text NOT NULL,
  `freeSeats` int(11) NOT NULL,
  PRIMARY KEY (`liftID`),
  KEY `driverID` (`driverID`),
  CONSTRAINT `Lifts_ibfk_1` FOREIGN KEY (`driverID`) REFERENCES `Users` (`userID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Lifts`
--

LOCK TABLES `Lifts` WRITE;
/*!40000 ALTER TABLE `Lifts` DISABLE KEYS */;
INSERT INTO `Lifts` VALUES (1,'2020-08-15 19:00:00','2020-08-15 19:36:00',54,'Ciao Mamma','0000000','{\"distances\":[26762],\"durations\":[36],\"stops\":[{\"score\":8.1408014297,\"address\":\"Via Folcarotonda, 19, 00036 Palestrina\",\"lon\":12.85975,\"lat\":41.83976},{\"score\":8.6688928604,\"address\":\"Via del Politecnico, 00133 Roma\",\"lon\":12.62165,\"lat\":41.85573}]}',4),(2,'2020-08-15 19:00:00','2020-08-15 19:36:00',54,'Ciao Mamma','0000000','{\"distances\":[26762],\"durations\":[36],\"stops\":[{\"score\":8.1408014297,\"address\":\"Via Folcarotonda, 19, 00036 Palestrina\",\"lon\":12.85975,\"lat\":41.83976},{\"score\":8.6688928604,\"address\":\"Via del Politecnico, 00133 Roma\",\"lon\":12.62165,\"lat\":41.85573}]}',0),(3,'2020-08-15 19:00:00','2020-08-15 19:36:00',108,'Ciao Mamma','0000000','{\"distances\":[26762],\"durations\":[36],\"stops\":[{\"score\":8.1408014297,\"address\":\"Via Folcarotonda, 19, 00036 Palestrina\",\"lon\":12.85975,\"lat\":41.83976},{\"score\":8.6688928604,\"address\":\"Via del Politecnico, 00133 Roma\",\"lon\":12.62165,\"lat\":41.85573}]}',4),(4,'2020-08-15 19:00:00','2020-08-15 19:36:00',108,'Ciao Mamma','0000000','{\"distances\":[26762],\"durations\":[36],\"stops\":[{\"score\":8.1408014297,\"address\":\"Via Folcarotonda, 19, 00036 Palestrina\",\"lon\":12.85975,\"lat\":41.83976},{\"score\":8.6688928604,\"address\":\"Via del Politecnico, 00133 Roma\",\"lon\":12.62165,\"lat\":41.85573}]}',0),(5,'2020-08-15 20:00:00','2020-08-15 20:36:00',54,'Ciao Mamma','0000000','{\"distances\":[26762],\"durations\":[36],\"stops\":[{\"score\":8.1408014297,\"address\":\"Via Folcarotonda, 19, 00036 Palestrina\",\"lon\":12.85975,\"lat\":41.83976},{\"score\":8.6688928604,\"address\":\"Via del Politecnico, 00133 Roma\",\"lon\":12.62165,\"lat\":41.85573}]}',4),(6,'2020-08-15 20:00:00','2020-08-15 20:36:00',54,'Ciao Mamma','0000000','{\"distances\":[26762],\"durations\":[36],\"stops\":[{\"score\":8.1408014297,\"address\":\"Via Folcarotonda, 19, 00036 Palestrina\",\"lon\":12.85975,\"lat\":41.83976},{\"score\":8.6688928604,\"address\":\"Via del Politecnico, 00133 Roma\",\"lon\":12.62165,\"lat\":41.85573}]}',0),(7,'2020-08-15 20:00:00','2020-08-15 20:36:00',108,'Ciao Mamma','0000000','{\"distances\":[26762],\"durations\":[36],\"stops\":[{\"score\":8.1408014297,\"address\":\"Via Folcarotonda, 19, 00036 Palestrina\",\"lon\":12.85975,\"lat\":41.83976},{\"score\":8.6688928604,\"address\":\"Via del Politecnico, 00133 Roma\",\"lon\":12.62165,\"lat\":41.85573}]}',4),(8,'2020-08-15 20:00:00','2020-08-15 20:36:00',108,'Ciao Mamma','0000000','{\"distances\":[26762],\"durations\":[36],\"stops\":[{\"score\":8.1408014297,\"address\":\"Via Folcarotonda, 19, 00036 Palestrina\",\"lon\":12.85975,\"lat\":41.83976},{\"score\":8.6688928604,\"address\":\"Via del Politecnico, 00133 Roma\",\"lon\":12.62165,\"lat\":41.85573}]}',0);
/*!40000 ALTER TABLE `Lifts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Notifications`
--

DROP TABLE IF EXISTS `Notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Notifications` (
  `notificationsID` int(11) NOT NULL AUTO_INCREMENT,
  `userID` varchar(10) NOT NULL,
  `message` text,
  PRIMARY KEY (`notificationsID`),
  KEY `userID_idx` (`userID`),
  CONSTRAINT `userID` FOREIGN KEY (`userID`) REFERENCES `Users` (`userID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Notifications`
--

LOCK TABLES `Notifications` WRITE;
/*!40000 ALTER TABLE `Notifications` DISABLE KEYS */;
/*!40000 ALTER TABLE `Notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Passengers`
--

DROP TABLE IF EXISTS `Passengers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Passengers` (
  `liftID` int(11) NOT NULL,
  `passengerID` varchar(10) NOT NULL,
  `rated` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`liftID`,`passengerID`),
  KEY `passengerID` (`passengerID`),
  CONSTRAINT `Passengers_ibfk_1` FOREIGN KEY (`liftID`) REFERENCES `Lifts` (`liftID`) ON DELETE CASCADE,
  CONSTRAINT `Passengers_ibfk_2` FOREIGN KEY (`passengerID`) REFERENCES `Users` (`userID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Passengers`
--

LOCK TABLES `Passengers` WRITE;
/*!40000 ALTER TABLE `Passengers` DISABLE KEYS */;
INSERT INTO `Passengers` VALUES (2,'0000006',0),(2,'0000007',0),(2,'0000008',0),(2,'0000009',0),(4,'0000010',0),(4,'0000011',0),(4,'0000012',0),(4,'0000013',0),(6,'0000014',0),(6,'0000015',0),(6,'0000016',0),(6,'0000017',0),(8,'0000018',0),(8,'0000019',0),(8,'0000020',0),(8,'0000021',0);
/*!40000 ALTER TABLE `Passengers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Students`
--

DROP TABLE IF EXISTS `Students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Students` (
  `userID` varchar(10) NOT NULL,
  `name` varchar(20) NOT NULL,
  `surname` varchar(20) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone` varchar(10) NOT NULL,
  `rating` int(11) DEFAULT '0',
  UNIQUE KEY `userID` (`userID`),
  CONSTRAINT `Students_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `Users` (`userID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Students`
--

LOCK TABLES `Students` WRITE;
/*!40000 ALTER TABLE `Students` DISABLE KEYS */;
INSERT INTO `Students` VALUES ('0000000','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000001','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000002','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000003','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000004','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000005','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000006','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000007','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000008','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000009','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000010','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000011','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000012','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000013','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000014','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000015','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000016','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000017','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000018','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000019','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000020','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000021','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000022','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000023','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000024','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000025','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000026','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000027','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000028','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000029','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000030','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000031','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000032','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000033','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000034','Jova','Notti','dummy@torvercar.com','3334445556',0);
/*!40000 ALTER TABLE `Students` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS `Users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Users` (
  `userID` varchar(10) NOT NULL,
  `password` varchar(20) NOT NULL,
  `role` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Users`
--

LOCK TABLES `Users` WRITE;
/*!40000 ALTER TABLE `Users` DISABLE KEYS */;
INSERT INTO `Users` VALUES ('0000000','aaaAAA123@','DRIVER'),('0000001','aaaAAA123@','DRIVER'),('0000002','aaaAAA123@','DRIVER'),('0000003','aaaAAA123@','DRIVER'),('0000004','aaaAAA123@','DRIVER'),('0000005','aaaAAA123@','STUDENT'),('0000006','aaaAAA123@','STUDENT'),('0000007','aaaAAA123@','STUDENT'),('0000008','aaaAAA123@','STUDENT'),('0000009','aaaAAA123@','STUDENT'),('0000010','aaaAAA123@','STUDENT'),('0000011','aaaAAA123@','STUDENT'),('0000012','aaaAAA123@','STUDENT'),('0000013','aaaAAA123@','STUDENT'),('0000014','aaaAAA123@','STUDENT'),('0000015','aaaAAA123@','STUDENT'),('0000016','aaaAAA123@','STUDENT'),('0000017','aaaAAA123@','STUDENT'),('0000018','aaaAAA123@','STUDENT'),('0000019','aaaAAA123@','STUDENT'),('0000020','aaaAAA123@','STUDENT'),('0000021','aaaAAA123@','STUDENT'),('0000022','aaaAAA123@','STUDENT'),('0000023','aaaAAA123@','STUDENT'),('0000024','aaaAAA123@','STUDENT'),('0000025','aaaAAA123@','STUDENT'),('0000026','aaaAAA123@','STUDENT'),('0000027','aaaAAA123@','STUDENT'),('0000028','aaaAAA123@','STUDENT'),('0000029','aaaAAA123@','STUDENT'),('0000030','aaaAAA123@','STUDENT'),('0000031','aaaAAA123@','STUDENT'),('0000032','aaaAAA123@','STUDENT'),('0000033','aaaAAA123@','STUDENT'),('0000034','aaaAAA123@','STUDENT');
/*!40000 ALTER TABLE `Users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `banned`
--

DROP TABLE IF EXISTS `banned`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `banned` (
  `userID` varchar(10) NOT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `banned`
--

LOCK TABLES `banned` WRITE;
/*!40000 ALTER TABLE `banned` DISABLE KEYS */;
/*!40000 ALTER TABLE `banned` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-08-30 15:01:11
