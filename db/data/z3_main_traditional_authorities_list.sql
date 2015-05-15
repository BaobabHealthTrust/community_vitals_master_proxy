-- MySQL dump 10.13  Distrib 5.5.43, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: openmrs_chalasa_new
-- ------------------------------------------------------
-- Server version	5.5.43-0ubuntu0.14.04.1

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
-- Table structure for table `traditional_authority`
--

DROP TABLE IF EXISTS `traditional_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `traditional_authority` (
  `traditional_authority_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `district_id` int(11) NOT NULL DEFAULT '0',
  `creator` int(11) NOT NULL DEFAULT '0',
  `date_created` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `retired_by` int(11) DEFAULT NULL,
  `date_retired` datetime DEFAULT NULL,
  `retire_reason` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`traditional_authority_id`),
  KEY `district_for_ta` (`district_id`),
  KEY `user_who_created_traditional_authority` (`creator`),
  KEY `user_who_retired_traditional_authority` (`retired_by`),
  KEY `retired_status` (`retired`),
  CONSTRAINT `district_for_ta` FOREIGN KEY (`district_id`) REFERENCES `district` (`district_id`),
  CONSTRAINT `user_who_created_traditional_authority` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_who_retired_traditional_authority` FOREIGN KEY (`retired_by`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=520 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `traditional_authority`
--

LOCK TABLES `traditional_authority` WRITE;
/*!40000 ALTER TABLE `traditional_authority` DISABLE KEYS */;
INSERT INTO `traditional_authority` VALUES (1,'Mwabulambya',1,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(2,'Mwenemisuku',1,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(3,'Mwenewenya',1,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(4,'Nthalire',1,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(5,'Kameme',1,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(6,'Chitipa Boma',1,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(7,'Kilipula',4,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(8,'Mwakaboko',4,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(9,'Kyungu',4,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(10,'Wasambo',4,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(11,'Mwirang\'ombe',4,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(12,'Karonga Town',4,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(13,'Kabunduli',7,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(14,'Fukamapiri',7,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(15,'Malenga Mzoma',7,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(16,'S/C Malanda',7,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(17,'S/C Zilakoma',7,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(18,' Mankhambira',7,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(19,'S/C Fukamalaza',7,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(20,'S/C Mkumbira',7,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(21,'Musisya',7,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(22,'S/C Nyaluwanga',7,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(23,'S/C Mkondowe',7,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(24,'Timbiri',7,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(25,'Boghoyo',7,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(26,'Nkhata-bay Boma',7,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(27,'Chikulamayembe',13,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(28,'Mwamlowe',13,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(29,'S/C Mwahenga',13,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(30,'S/C Mwalweni',13,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(31,'S/C Kachulu',13,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(32,'S/C Chapinduka',13,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(33,'S/C Mwankhunikira',13,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(34,'Katumbi',13,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(35,'S/C Zolokere',13,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(36,'Nyika National Park (A)',13,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(37,'Rumphi Boma',13,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(38,'M\'mbelwa',15,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(39,'Mtwalo',15,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(40,'S/C Kampingo Sibande',15,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(41,'S/C Jaravikuba Munthali',15,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(42,'Chindi',15,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(43,'Mzikubola',15,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(44,'Mabulabo',15,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(45,'S/C Khosolo Gwaza Jere',15,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(46,'Mpherembe',15,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(47,'Mzukuzuku',15,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(48,'Mzimba Boma',15,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(49,'Nkhorongo Ward',21,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(50,'Lupaso Ward',21,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(51,'Zolozolo Ward',21,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(52,'Chiputula Ward',21,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(53,'Mchengautuwa Ward',21,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(54,'Katoto Ward',21,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(55,'Jombo Ward',21,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(56,'Mzilawayingwe Ward',21,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(57,'Chasefu Ward',21,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(58,'Katawa Ward',21,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(59,'Masasa Ward',21,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(60,'Kaning\'ina Ward',21,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(61,'Vipya Ward',21,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(62,'Msongwe Ward',21,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(63,'New Airport Site',21,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(64,'Mkumpha',17,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(65,'Kasungu Boma',2,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(66,'TA Chilowamatambe',2,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(67,'TA Chisikwa',2,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(68,'TA Chulu',2,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(69,'TA Kaluluma',2,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(70,'TA Kaomba',2,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(71,'TA Kapelula',2,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(72,'TA Kawamba',2,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(73,'S/C Lukwa',2,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(74,'S/C Mnyanja',2,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(75,'S/C Njombwa',2,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(76,'TA Santhe',2,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(77,'S/C Simlemba',2,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(78,'TA Wimbe',2,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(79,'Kasungu National Park',2,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(80,'TA Kanyenda',6,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(81,'SC Kafuzila',6,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(82,'TA Malengachanzi',6,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(83,'STA Mphonde',6,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(84,'TA Mwadzama',6,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(85,'TA Mwansambo',6,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(86,'Nkhotakota Boma',6,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(87,'Ta Kasakula',8,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(88,'TA Chikho',8,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(89,'S/C Nthondo',8,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(90,'TA Kalumo',8,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(91,'S/C Chilooko',8,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(92,'Ntchisi Boma',8,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(93,'TA Dzoole',12,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(94,'S/C Chakhadza',12,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(95,'TA Kayembe',12,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(96,'TA Chiwere',12,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(97,'SC Mkukula',12,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(98,'TA Msakambewa',12,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(99,'TA Mponela',12,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(100,'Mponela Urban',12,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(101,'Dowa Boma',12,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(102,'Maganga',16,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(103,'Kalonga',16,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(104,'Pemba',16,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(105,'SC Kambwiri',16,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(106,'Ndindi',16,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(107,'SC Kambalame',16,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(108,'Khombedza',16,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(109,'SC Mwanza',16,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(110,'Kuluunda',16,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(111,'SC Msosa',16,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(112,'Salima Boma',16,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(113,'Chadza',18,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(114,'Kalolo',18,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(115,'Chiseka',18,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(116,'Mazengera',18,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(117,'Chitekwere',18,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(118,'Khongoni',18,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(119,'Chimutu',18,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(120,'Area 1 (Falls)',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(121,'Area 2 (Old town)',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(122,'Area 3',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(123,'Area 4',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(124,'Area 5',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(125,'Area 6',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(126,'Area 7 (Kawale)',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(127,'Area 8 (Biwi)',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(128,'Area 9',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(129,'Area 10',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(130,'Area 11',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(131,'Area 12',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(132,'Area 13',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(133,'Area 14 ',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(134,'Area 15',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(135,'Area 16',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(136,'Area 17',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(137,'Area 18',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(138,'Area 19',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(139,'Area 20 (Chilinde 1)',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(140,'Area 21 (Chilinde)',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(141,'Area 22',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(142,'Area 23',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(143,'Area 24',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(144,'Area 25',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(145,'Area 26 (Manyenje)',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(146,'Area 27 (Liwewe)',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(147,'Area 28',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(148,'Area 29 (Kanengo)',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(149,'Area 30',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(150,'Area 31',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(151,'Area 32',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(152,'Area 33',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(153,'Area 34',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(154,'Area 35',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(155,'Area 36',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(156,'Area 37',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(157,'Area 38 (Chimutu)',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(158,'Area 39 (Chatata)',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(159,'Area 40',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(160,'Area 41',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(161,'Area 42',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(162,'Area 43',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(163,'Area 44',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(164,'Area 45 (Katete)',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(165,'Area 46 (Njewa)',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(166,'Area 47',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(167,'Area 48',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(168,'Area 49',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(169,'Area 50 (Senti)',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(170,'Area 51 (M\'gona)',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(171,'Area 52 (Lumbadzi TC)',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(172,'Area 53 (Lumbadzi)',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(173,'Area 54',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(174,'Area 55 (Chitukula)',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(175,'Area 56 (Ntandire)',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(176,'Area 57(Chinsapo)',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(177,'Area 58 (Likuni)',28,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(178,'Mchinji Boma',20,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(179,'Mduwa',20,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(180,'Mkanda',20,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(181,'Dambe',20,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(182,'Mavwere',20,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(183,'Mlonyeni',20,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(184,'Zulu',20,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(185,'Dedza Town',23,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(186,'Kamenya Gwaza',23,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(187,'Kaphuka',23,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(188,'Pemba',23,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(189,'Chauma',23,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(190,'Kachindamoto',23,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(191,'Chilikumwendo',23,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(192,'Kasumbu',23,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(193,'Tambala',23,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(194,'Ntcheu Boma',25,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(195,'Chakhumbira',25,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(196,'Champiti',25,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(197,'Goodson Ganya',25,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(198,'Kwataine',25,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(199,'Makwangwala',25,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(200,'Masasa',25,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(201,'Mpando',25,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(202,'Njolomole',25,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(203,'Phambala',25,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(204,'Mangochi Boma',3,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(205,'Chimwala',3,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(206,'Jalasi',3,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(207,'Makanjila',3,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(208,'Mponda',3,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(209,'Nankumba',3,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(210,'Lake Malawi National Park',3,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(211,'Chowe',3,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(212,'Katuli',3,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(213,'Mbwananyambi',3,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(214,'Namabvi',3,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(215,'Liwonde National Park',5,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(216,'Machinga Boma',5,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(217,'Chamba',5,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(218,'Chiwalo',5,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(219,'Liwonde',5,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(220,'Mposa',5,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(221,'Nyambi',5,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(222,'Liwonde Town',5,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(223,'Chikweo',5,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(224,'Kawinga',5,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(225,'Mlomba',5,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(226,'Ngokwe',5,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(227,'Sitola',5,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(228,'Mbiza',9,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(229,'Kuntumanji',9,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(230,'Mkumbira',9,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(231,'Mwambo',9,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(232,'Chikowi',9,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(233,'Malemia',9,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(234,'Mlumbe',9,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(235,'Chambo Ward',10,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(236,'Chhikamveka Ward',10,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(237,'Chilunga East Ward',10,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(238,'Likangala Central Ward',10,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(239,'Masongola Ward',10,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(240,'Mtiya Ward',10,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(241,'Zakazaka Ward',10,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(242,'Chikamveka North Ward',10,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(243,'Chirunga Ward',10,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(244,'Likangala South Ward',10,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(245,'Likangala North Ward',10,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(246,'Mbedza Ward',10,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(247,'Sadzi Ward',10,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(248,'Zomba Central Ward',10,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(249,'Chitera',11,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(250,'Kadewere',11,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(251,'Likoswe',11,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(252,'Mchema',11,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(253,'Nkalo',11,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(254,'Mpama',11,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(255,'Chiradzulu Boma',11,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(256,'Kuntaja',14,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(257,'Lunzu TPA',14,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(258,'Nkula TPA',14,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(259,'Kapeni',14,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(260,'Lundu',14,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(261,'Makata',14,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(262,'Somba',14,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(263,'Chigaru',14,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(264,'Kunthembwe',14,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(265,'Machinjili',14,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(266,'Bangwe Ward',32,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(267,'Blantyre West Ward',32,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(268,'Chigumula Ward',32,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(269,'Likhubula Ward',32,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(270,'Limbe Central Ward',32,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(271,'Mapanga Ward',32,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(272,'Misesa Ward',32,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(273,'Mzedi Ward',32,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(274,'Nancholi Ward',32,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(275,'Ndirande South Ward',32,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(276,'Nkolokoti Ward',32,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(277,'Soche East Ward',32,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(278,'South Lunzu Ward',32,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(279,'Soche West Ward',32,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(280,'Nyambadwe Ward',32,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(281,'Ndirande West Ward',32,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(282,'Ndirande North Ward',32,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(283,'Namiyango Ward',32,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(284,'Msamba Ward',32,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(285,'Michiru Ward',32,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(286,'Limbe East Ward',32,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(287,'Limbe West Ward',32,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(288,'Chilomoni Ward',32,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(289,'Chichiri Ward',32,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(290,'Blantrye East Ward',32,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(291,'Blantrye Central Ward',32,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(292,'Kanduku',19,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(293,'Nthache',19,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(294,'Mwanza Boma',19,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(295,'Khwethemule',22,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(296,'Kapichi',22,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(297,'Chimaliro',22,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(298,'Changata',22,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(299,'Bvumbwe',22,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(300,'Thukuta',22,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(301,'Mbawela',22,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(302,'Luchenza Town',22,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(303,'Mphuka',22,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(304,'Nsabwe',22,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(305,'Thyolo Boma',22,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(306,'Nchilamwela',22,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(307,'Thomasi',22,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(308,'Mulanje Boma',24,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(309,'Mulanje Mountain',24,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(310,'Chikumbu',24,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(311,'Juma',24,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(312,'Njema',24,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(313,'Laston Njema',24,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(314,'Mabuka',24,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(315,'Nkanda',24,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(316,'Nthiramanja',24,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(317,'Phalombe Boma',26,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(318,'Chiwalo',26,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(319,'Mkhumba',26,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(320,'Nazombe',26,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(321,'Chapananga',27,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(322,'Kasisi',27,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(323,'Katunga',27,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(324,'Lundu',27,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(325,'Makhuwira',27,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(326,'Maseya',27,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(327,'Ngabu',27,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(328,'Chikwawa Boma',27,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(329,'Mwabvi Game Reserve',29,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(330,'Nsanje Boma',29,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(331,'Chimombo',29,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(332,'Malemia',29,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(333,'Mlolo',29,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(334,'Nyachikadza',29,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(335,'Ngabu',29,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(336,'Makoko',29,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(337,'Mbenje',29,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(338,'Ndamera',29,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(339,'Tengani',29,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(340,'Balaka Boma',30,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(341,'Kalembo',30,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(342,'Nsamala',30,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(343,'Dambe',31,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(344,'Mlauli',31,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(345,'Neno Boma',31,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(346,'Ngozi',31,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(347,'Symon',31,1,'2011-09-27 00:00:00',0,NULL,NULL,NULL),(512,'Tsabango',18,1,'2012-10-03 10:17:16',0,NULL,NULL,NULL),(513,'Njewa',18,1,'2012-10-03 10:17:21',0,NULL,NULL,NULL),(514,'Kabudula',18,1,'2012-10-03 10:17:27',0,NULL,NULL,NULL),(515,'Malili',18,1,'2012-10-03 10:17:39',0,NULL,NULL,NULL),(516,'Chitukula',18,1,'2012-10-03 10:17:49',0,NULL,NULL,NULL),(517,'Masumbankhunda',18,1,'2012-10-03 10:17:55',0,NULL,NULL,NULL),(518,'Masula',18,1,'2012-10-03 10:18:11',0,NULL,NULL,NULL),(519,'Mtema',18,1,'2012-10-03 10:17:39',0,NULL,NULL,NULL);
/*!40000 ALTER TABLE `traditional_authority` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-05-15 14:26:57
