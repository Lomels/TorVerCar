-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: torvercar
-- ------------------------------------------------------
-- Server version	8.0.18

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
-- Table structure for table `banned`
--

DROP TABLE IF EXISTS `banned`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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

--
-- Table structure for table `cars`
--

DROP TABLE IF EXISTS `cars`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cars` (
  `model` varchar(20) NOT NULL,
  `plate` varchar(10) NOT NULL,
  `seats` int(11) NOT NULL,
  `color` varchar(10) NOT NULL,
  `userID` varchar(10) NOT NULL,
  PRIMARY KEY (`plate`),
  KEY `Cars_ibfk_1` (`userID`),
  CONSTRAINT `Cars_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cars`
--

LOCK TABLES `cars` WRITE;
/*!40000 ALTER TABLE `cars` DISABLE KEYS */;
INSERT INTO `cars` VALUES ('i20','AB123CD',4,'bianca','0241118'),('Fiat Macchina','TV000CR',4,'Arcobaleno','0000000'),('Fiat Macchina','TV100CR',4,'Arcobaleno','0000001'),('Fiat Macchina','TV200CR',4,'Arcobaleno','0000002'),('Fiat Macchina','TV300CR',4,'Arcobaleno','0000003'),('Fiat Macchina','TV400CR',4,'Arcobaleno','0000004');
/*!40000 ALTER TABLE `cars` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lifts`
--

DROP TABLE IF EXISTS `lifts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lifts` (
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
  CONSTRAINT `Lifts_ibfk_1` FOREIGN KEY (`driverID`) REFERENCES `users` (`userID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lifts`
--

LOCK TABLES `lifts` WRITE;
/*!40000 ALTER TABLE `lifts` DISABLE KEYS */;
INSERT INTO `lifts` VALUES (1,'2020-08-15 19:00:00','2020-08-15 19:36:00',54,'Ciao Mamma','0000000','{\"distances\":[498,26781,27883],\"durations\":[1,35,36],\"stops\":[{\"score\":8.1408014297,\"address\":\"Via Folcarotonda, 19, 00036 Palestrina\",\"lon\":12.85975,\"lat\":41.83976},{\"score\":4.5659923553,\"address\":\"Via Folcarotonda, 00036 Palestrina\",\"lon\":12.85814,\"lat\":41.84284},{\"score\":4.5659923553,\"address\":\"Via Cambridge, 00133 Roma\",\"lon\":12.62418,\"lat\":41.8539},{\"score\":8.6688928604,\"address\":\"Via del Politecnico, 00133 Roma\",\"lon\":12.62165,\"lat\":41.85573}]}',3),(2,'2020-08-15 19:00:00','2020-08-15 19:36:00',54,'Ciao Mamma','0000000','{\"distances\":[26762],\"durations\":[36],\"stops\":[{\"score\":8.1408014297,\"address\":\"Via Folcarotonda, 19, 00036 Palestrina\",\"lon\":12.85975,\"lat\":41.83976},{\"score\":8.6688928604,\"address\":\"Via del Politecnico, 00133 Roma\",\"lon\":12.62165,\"lat\":41.85573}]}',0),(3,'2020-08-15 19:00:00','2020-08-15 19:36:00',108,'Ciao Mamma','0000000','{\"distances\":[26762],\"durations\":[36],\"stops\":[{\"score\":8.1408014297,\"address\":\"Via Folcarotonda, 19, 00036 Palestrina\",\"lon\":12.85975,\"lat\":41.83976},{\"score\":8.6688928604,\"address\":\"Via del Politecnico, 00133 Roma\",\"lon\":12.62165,\"lat\":41.85573}]}',3),(4,'2020-08-15 19:00:00','2020-08-15 19:36:00',108,'Ciao Mamma','0000000','{\"distances\":[26762],\"durations\":[36],\"stops\":[{\"score\":8.1408014297,\"address\":\"Via Folcarotonda, 19, 00036 Palestrina\",\"lon\":12.85975,\"lat\":41.83976},{\"score\":8.6688928604,\"address\":\"Via del Politecnico, 00133 Roma\",\"lon\":12.62165,\"lat\":41.85573}]}',0),(5,'2020-08-15 20:00:00','2020-08-15 20:35:00',54,'Ciao Mamma','0000000','{\"distances\":[0,26279,27381],\"durations\":[0,34,35],\"stops\":[{\"score\":8.1408014297,\"address\":\"Via Folcarotonda, 19, 00036 Palestrina\",\"lon\":12.85975,\"lat\":41.83976},{\"score\":6.2976007462,\"address\":\"Via Folcarotonda, 19, 00036 Palestrina\",\"lon\":12.85975,\"lat\":41.83976},{\"score\":4.5659923553,\"address\":\"Via Cambridge, 00133 Roma\",\"lon\":12.62418,\"lat\":41.8539},{\"score\":8.6688928604,\"address\":\"Via del Politecnico, 00133 Roma\",\"lon\":12.62165,\"lat\":41.85573}]}',3),(6,'2020-08-15 20:00:00','2020-08-15 20:36:00',54,'Ciao Mamma','0000000','{\"distances\":[26762],\"durations\":[36],\"stops\":[{\"score\":8.1408014297,\"address\":\"Via Folcarotonda, 19, 00036 Palestrina\",\"lon\":12.85975,\"lat\":41.83976},{\"score\":8.6688928604,\"address\":\"Via del Politecnico, 00133 Roma\",\"lon\":12.62165,\"lat\":41.85573}]}',0),(7,'2020-08-15 20:00:00','2020-08-15 20:36:00',108,'Ciao Mamma','0000000','{\"distances\":[26762],\"durations\":[36],\"stops\":[{\"score\":8.1408014297,\"address\":\"Via Folcarotonda, 19, 00036 Palestrina\",\"lon\":12.85975,\"lat\":41.83976},{\"score\":8.6688928604,\"address\":\"Via del Politecnico, 00133 Roma\",\"lon\":12.62165,\"lat\":41.85573}]}',4),(8,'2020-08-15 20:00:00','2020-08-15 20:36:00',108,'Ciao Mamma','0000000','{\"distances\":[26762],\"durations\":[36],\"stops\":[{\"score\":8.1408014297,\"address\":\"Via Folcarotonda, 19, 00036 Palestrina\",\"lon\":12.85975,\"lat\":41.83976},{\"score\":8.6688928604,\"address\":\"Via del Politecnico, 00133 Roma\",\"lon\":12.62165,\"lat\":41.85573}]}',0),(9,'2020-08-30 11:30:00','2020-08-30 12:20:00',65,'Vietato fumare','0241118','{\"distances\":[2001,41312,41312],\"durations\":[4,50,50],\"stops\":[{\"score\":8.5483922958,\"address\":\"Via Folcarotonda, 00036 Palestrina\",\"lon\":12.85814,\"lat\":41.84284},{\"score\":6.617661953,\"address\":\"Via Pedemontana Stella, 00036 Palestrina\",\"lon\":12.86987,\"lat\":41.84158},{\"score\":8.6688928604,\"address\":\"Viale del Policlinico, 00161 Roma\",\"lon\":12.50558,\"lat\":41.90838},{\"score\":4.110601902,\"address\":\"Viale del Policlinico, 00161 Roma\",\"lon\":12.50558,\"lat\":41.90838}]}',3),(10,'2020-09-02 08:00:00','2020-09-02 08:47:00',90,'Sbrigateve','0000000','{\"distances\":[4999,32381,33483],\"durations\":[8,45,47],\"stops\":[{\"score\":4.6116523743,\"address\":\"Via Pedemontana, Gallicano nel Lazio\",\"lon\":12.81849,\"lat\":41.86427},{\"score\":6.2976007462,\"address\":\"Via Folcarotonda, 19, 00036 Palestrina\",\"lon\":12.85975,\"lat\":41.83976},{\"score\":6.7824931145,\"address\":\"Via del Politecnico, 00133 Roma\",\"lon\":12.62165,\"lat\":41.85573},{\"score\":4.5659923553,\"address\":\"Via Cambridge, 00133 Roma\",\"lon\":12.62418,\"lat\":41.8539}]}',3);
/*!40000 ALTER TABLE `lifts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notifications` (
  `notificationsID` int(11) NOT NULL AUTO_INCREMENT,
  `userID` varchar(10) NOT NULL,
  `message` text,
  PRIMARY KEY (`notificationsID`),
  KEY `userID_idx` (`userID`),
  CONSTRAINT `userID` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
/*!40000 ALTER TABLE `notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `passengers`
--

DROP TABLE IF EXISTS `passengers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `passengers` (
  `liftID` int(11) NOT NULL,
  `passengerID` varchar(10) NOT NULL,
  `rated` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`liftID`,`passengerID`),
  KEY `passengerID` (`passengerID`),
  CONSTRAINT `Passengers_ibfk_1` FOREIGN KEY (`liftID`) REFERENCES `lifts` (`liftID`) ON DELETE CASCADE,
  CONSTRAINT `Passengers_ibfk_2` FOREIGN KEY (`passengerID`) REFERENCES `users` (`userID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `passengers`
--

LOCK TABLES `passengers` WRITE;
/*!40000 ALTER TABLE `passengers` DISABLE KEYS */;
INSERT INTO `passengers` VALUES (1,'0241118',1),(2,'00000010',0),(2,'00000011',0),(2,'00000012',0),(2,'00000013',0),(3,'0000005',0),(4,'00000014',0),(4,'00000015',0),(4,'00000016',0),(4,'00000017',0),(5,'0241118',1),(6,'00000018',0),(6,'00000019',0),(6,'00000020',0),(6,'00000021',0),(8,'00000022',0),(8,'00000023',0),(8,'00000024',0),(8,'00000025',0),(9,'0000000',0),(10,'0241118',0);
/*!40000 ALTER TABLE `passengers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `students` (
  `userID` varchar(10) NOT NULL,
  `name` varchar(20) NOT NULL,
  `surname` varchar(20) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone` varchar(10) NOT NULL,
  `rating` int(11) DEFAULT '0',
  UNIQUE KEY `userID` (`userID`),
  CONSTRAINT `Students_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `users` (`userID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students`
--

LOCK TABLES `students` WRITE;
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
INSERT INTO `students` VALUES ('0000000','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000001','Jova','Notti','dummy@torvercar.com','3334445556',0),('00000010','Jova','Notti','dummy@torvercar.com','3334445556',0),('00000011','Jova','Notti','dummy@torvercar.com','3334445556',0),('00000012','Jova','Notti','dummy@torvercar.com','3334445556',0),('00000013','Jova','Notti','dummy@torvercar.com','3334445556',0),('00000014','Jova','Notti','dummy@torvercar.com','3334445556',0),('00000015','Jova','Notti','dummy@torvercar.com','3334445556',0),('00000016','Jova','Notti','dummy@torvercar.com','3334445556',0),('00000017','Jova','Notti','dummy@torvercar.com','3334445556',0),('00000018','Jova','Notti','dummy@torvercar.com','3334445556',0),('00000019','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000002','Jova','Notti','dummy@torvercar.com','3334445556',0),('00000020','Jova','Notti','dummy@torvercar.com','3334445556',0),('00000021','Jova','Notti','dummy@torvercar.com','3334445556',0),('00000022','Jova','Notti','dummy@torvercar.com','3334445556',0),('00000023','Jova','Notti','dummy@torvercar.com','3334445556',0),('00000024','Jova','Notti','dummy@torvercar.com','3334445556',0),('00000025','Jova','Notti','dummy@torvercar.com','3334445556',0),('00000026','Jova','Notti','dummy@torvercar.com','3334445556',0),('00000027','Jova','Notti','dummy@torvercar.com','3334445556',0),('00000028','Jova','Notti','dummy@torvercar.com','3334445556',0),('00000029','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000003','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000004','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000005','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000006','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000007','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000008','Jova','Notti','dummy@torvercar.com','3334445556',0),('0000009','Jova','Notti','dummy@torvercar.com','3334445556',0),('0241118','Marco','Lo Mele','marco.lomele@gmail.com','3336669990',0);
/*!40000 ALTER TABLE `students` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `userID` varchar(10) NOT NULL,
  `password` varchar(20) NOT NULL,
  `role` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('0000000','aaaAAA123@','DRIVER'),('0000001','aaaAAA123@','DRIVER'),('00000010','aaaAAA123@','STUDENT'),('00000011','aaaAAA123@','STUDENT'),('00000012','aaaAAA123@','STUDENT'),('00000013','aaaAAA123@','STUDENT'),('00000014','aaaAAA123@','STUDENT'),('00000015','aaaAAA123@','STUDENT'),('00000016','aaaAAA123@','STUDENT'),('00000017','aaaAAA123@','STUDENT'),('00000018','aaaAAA123@','STUDENT'),('00000019','aaaAAA123@','STUDENT'),('0000002','aaaAAA123@','DRIVER'),('00000020','aaaAAA123@','STUDENT'),('00000021','aaaAAA123@','STUDENT'),('00000022','aaaAAA123@','STUDENT'),('00000023','aaaAAA123@','STUDENT'),('00000024','aaaAAA123@','STUDENT'),('00000025','aaaAAA123@','STUDENT'),('00000026','aaaAAA123@','STUDENT'),('00000027','aaaAAA123@','STUDENT'),('00000028','aaaAAA123@','STUDENT'),('00000029','aaaAAA123@','STUDENT'),('0000003','aaaAAA123@','DRIVER'),('0000004','aaaAAA123@','DRIVER'),('0000005','aaaAAA123@','STUDENT'),('0000006','aaaAAA123@','STUDENT'),('0000007','aaaAAA123@','STUDENT'),('0000008','aaaAAA123@','STUDENT'),('0000009','aaaAAA123@','STUDENT'),('0241118','aaaAAA123@','DRIVER');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-08-30 14:38:10
