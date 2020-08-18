-- MySQL dump 10.13  Distrib 5.7.29, for Linux (x86_64)
--
-- Host: localhost    Database: TorVerCar
-- ------------------------------------------------------
-- Server version	5.7.29-0ubuntu0.18.04.1

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
  `userID` varchar(10) DEFAULT NULL,
  PRIMARY KEY `userID` (`userID`)
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
  CONSTRAINT `Cars_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `Users` (`userID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cars`
--

LOCK TABLES `Cars` WRITE;
/*!40000 ALTER TABLE `Cars` DISABLE KEYS */;
INSERT INTO `Cars` VALUES ('Hyundai i20','AA123AA',4,'Bianca','0241118'),('Alfa Romeo GIulia','AB123CD',4,'Rosso','0245061');
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
  `note` text DEFAULT NULL,
  `driverID` varchar(10) NOT NULL,
  `route` text NOT NULL,
  `freeSeats` int(11) NOT NULL,
  PRIMARY KEY (`liftID`),
  KEY `driverID` (`driverID`),
  CONSTRAINT `Lifts_ibfk_1` FOREIGN KEY (`driverID`) REFERENCES `Users` (`userID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Lifts`
--

LOCK TABLES `Lifts` WRITE;
/*!40000 ALTER TABLE `Lifts` DISABLE KEYS */;
INSERT INTO `Lifts` VALUES (6,'2020-08-12 17:23:12','2020-08-12 17:56:12',200,'Ma non so cosa ce nella mia pelle bianca','0241118','{\"duration\":33,\"distance\":27083,\"stops\":[{\"score\":10.2996511459,\"address\":\"Via Prenestina Nuova, 51, 00036 Palestrina\",\"lon\":12.88611,\"lat\":41.83322},{\"score\":8.6688928604,\"address\":\"Via del Politecnico, 00133 Roma\",\"lon\":12.62165,\"lat\":41.85573}]}',4);
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Notifications`
--

LOCK TABLES `Notifications` WRITE;
/*!40000 ALTER TABLE `Notifications` DISABLE KEYS */;
INSERT INTO `Notifications` VALUES (1,'0245061','The lift you booked for: Via del Politecnico, 00133 Roma, departing at: 2020-08-10T15:54:34, has been deleted by the driver.'),(2,'0245061','The lift you booked for: Via del Politecnico, 00133 Roma, departing at: 2020-08-12T17:24:12, has been deleted by the driver.'),(3,'0252379','The lift you booked for: Via del Politecnico, 00133 Roma, departing at: 2020-08-12T17:24:12, has been deleted by the driver.');
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
  `rating` int(11) NOT NULL,
  UNIQUE KEY `userID` (`userID`),
  CONSTRAINT `Students_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `Users` (`userID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Students`
--

LOCK TABLES `Students` WRITE;
/*!40000 ALTER TABLE `Students` DISABLE KEYS */;
INSERT INTO `Students` VALUES ('0245061','Giulia','Desideri','giuls.desideri@gmail.com','3923165944',NULL),('0241118','Marco','Lo Mele','marco.lomele@gmail.com','3336669990',NULL),('0252379','Giuseppe','Marseglia','g.marseglia.it@gmail.com','3336669990',NULL);
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
INSERT INTO `Users` VALUES ('0241118','aaaAAA123@','DRIVER'),('0245061','aaaAAA123@','DRIVER'),('0252379','aaaAAA123@','STUDENT');
/*!40000 ALTER TABLE `Users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-08-12 17:37:21
