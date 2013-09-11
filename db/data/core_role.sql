-- MySQL dump 10.13  Distrib 5.5.31, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: localisation_development
-- ------------------------------------------------------
-- Server version	5.5.31-0ubuntu0.12.10.1

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
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES ('Group Village Headman','Group Village Headman',30,'124da92c-04e6-11e3-903b-30f9edafb6df'),('Traditional Authority','Traditional Authority',31,'1256f6e7-04e6-11e3-903b-30f9edafb6df'),('Village Headman','Village Headman',32,'125f7953-04e6-11e3-903b-30f9edafb6df'),('Anonymous','Privileges for non-authenticated users.',NULL,'774b2af3-6437-4e5a-a310-547554c7c65c'),('Provider','Health care provider',NULL,'8d94f280-c2cc-11de-8d13-0010c6dffd0f'),('System Developer','Developers of the OpenMRS .. have additional access to change fundamental structure of the database model.',NULL,'8d94f852-c2cc-11de-8d13-0010c6dffd0f'),('Accompagnateur','Community health worker',NULL,'bc4eaa2a-587c-11d6-861b-0024217bb78e'),('Accompagnateur Leader','Responsible for overseeing up to 30 Accompagnateurs',NULL,'bc4eacaa-587c-11d6-861b-0024217bb78e'),('Adults','OPD user group for adults entries',NULL,'bc4eada4-587c-11d6-861b-0024217bb78e'),('Clinician','Users who are a part of direct patient care.',NULL,'bc4eb06a-587c-11d6-861b-0024217bb78e'),('Data Assistant','Clerks who perform data entry.',NULL,'bc4eb15a-587c-11d6-861b-0024217bb78e'),('Data Element Contributor','Role for users who contribute to the management of data elements in OpenMRS.',NULL,'bc4eb254-587c-11d6-861b-0024217bb78e'),('Data Manager','User who maintains clinical data stored within the OpenMRS repository.',NULL,'bc4eb33a-587c-11d6-861b-0024217bb78e'),('Doctor','People in direct care of patient. According to the Malawian context, this is a user who has acquired medical training to level of MBBS.',NULL,'bc4eb420-587c-11d6-861b-0024217bb78e'),('General Registration Clerk','A General Registration Clerk',NULL,'bc4ebaba-587c-11d6-861b-0024217bb78e'),('HMIS lab order','OPD user group for HMIS lab order clerk entries',NULL,'bc4ebbb4-587c-11d6-861b-0024217bb78e'),('Informatics Manager','User who maintains the local installation of the OpenMRS repository.',NULL,'bc4ebc9a-587c-11d6-861b-0024217bb78e'),('Lab','Lab technicians and assistants',NULL,'bc4ebd8a-587c-11d6-861b-0024217bb78e'),('Medical Assistant','Medical Assistants',NULL,'bc4ebe66-587c-11d6-861b-0024217bb78e'),('Nurse','Nursing Officers and Technicians',NULL,'bc4ebf56-587c-11d6-861b-0024217bb78e'),('Paediatrics','OPD user group for paediatrics entries',NULL,'bc4ec032-587c-11d6-861b-0024217bb78e'),('Pharmacist','A Pharmacist',NULL,'bc4ec118-587c-11d6-861b-0024217bb78e'),('Program Manager','Has permission to view most/all data, but no permission for entry or editing.',29,'bc4ec1f4-587c-11d6-861b-0024217bb78e'),('Registration Clerk','This is a user responsible for registering patients at the patient registration station. mostly used in Baobab applications. This is probably an equivalent of Data Assistant.',NULL,'bc4ec3c0-587c-11d6-861b-0024217bb78e'),('Social Worker','Social Worker',NULL,'bc4ec4ba-587c-11d6-861b-0024217bb78e'),('SPINE clinician','OPD user group for SPINE clinician entries',NULL,'bc4ec5a0-587c-11d6-861b-0024217bb78e'),('Superuser','This is a user who has access to all Baobab developed applications\' functionality',33,'bc4ec686-587c-11d6-861b-0024217bb78e'),('Superuser,Superuser,','Superuser',NULL,'bc4ec76c-587c-11d6-861b-0024217bb78e'),('Therapeutic Feeding Clerk','A clerk who just enters therapeutic feeding data.',NULL,'bc4ec942-587c-11d6-861b-0024217bb78e'),('Vitals Clerk','A user who just enters vitals for patients. ',NULL,'bc4eca32-587c-11d6-861b-0024217bb78e'),('Authenticated','Privileges gained once authentication has been established.',NULL,'f7fd42ef-880e-40c5-972d-e4ae7c990de2');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-08-14 15:39:31
