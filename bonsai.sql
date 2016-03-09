-- MySQL dump 10.13  Distrib 5.5.46, for Win64 (x86)
--
-- Host: localhost    Database: bonsai
-- ------------------------------------------------------
-- Server version	5.5.46

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
-- Current Database: `bonsai`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `bonsai` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `bonsai`;

--
-- Table structure for table `dict_af`
--

DROP TABLE IF EXISTS `dict_af`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_af` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_af`
--

LOCK TABLES `dict_af` WRITE;
/*!40000 ALTER TABLE `dict_af` DISABLE KEYS */;
/*!40000 ALTER TABLE `dict_af` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_ar`
--

DROP TABLE IF EXISTS `dict_ar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_ar` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_ar`
--

LOCK TABLES `dict_ar` WRITE;
/*!40000 ALTER TABLE `dict_ar` DISABLE KEYS */;
INSERT INTO `dict_ar` VALUES (1,1,'ضوء القمر',NULL,NULL,'2016-01-12 15:21:32',NULL,NULL,'0'),(2,2,'القمر الجديد',NULL,NULL,'2016-01-12 15:21:57',NULL,NULL,'0'),(3,3,'جون هو بريطاني',NULL,NULL,'2016-01-12 15:22:20',NULL,NULL,'0'),(4,4,'ذهبت إلى السينما مع أخي.',NULL,NULL,'2016-01-12 15:22:39',NULL,NULL,'0'),(5,5,'ذهبت إلى السينما على دراجة',NULL,NULL,'2016-01-12 15:23:02',NULL,NULL,'0'),(6,6,'جون',NULL,NULL,'2016-01-12 15:23:23',NULL,NULL,'0'),(7,7,'الشعب البريطاني',NULL,NULL,'2016-01-12 15:23:44',NULL,NULL,'0'),(8,8,'أنا',NULL,NULL,'2016-01-12 15:24:05',NULL,NULL,'0'),(9,9,'أخي',NULL,NULL,'2016-01-12 15:24:29',NULL,NULL,'0'),(10,10,'سينما',NULL,NULL,'2016-01-12 15:24:48',NULL,NULL,'0'),(11,11,'ذهبت',NULL,NULL,'2016-01-12 15:25:11',NULL,NULL,'0'),(12,12,'الدراجة',NULL,NULL,'2016-01-12 15:25:31',NULL,NULL,'0'),(13,13,'خسوف القمر',NULL,NULL,'2016-01-12 15:25:51',NULL,NULL,'0'),(14,14,'أخي طبيب.',NULL,NULL,'2016-01-12 15:26:15',NULL,NULL,'0'),(15,15,'لا تقم بإيقاف الدراجة',NULL,NULL,'2016-01-12 15:26:35',NULL,NULL,'0'),(16,16,'المزارات والمتنزهات.',NULL,NULL,'2016-01-12 15:26:52',NULL,NULL,'0'),(17,17,'طبيب',NULL,NULL,'2016-01-12 15:27:14',NULL,NULL,'0'),(18,18,'وقف',NULL,NULL,'2016-01-12 15:27:33',NULL,NULL,'0'),(19,19,'ضريح',NULL,NULL,'2016-01-12 15:27:57',NULL,NULL,'0'),(20,20,'بارك',NULL,NULL,'2016-01-12 15:28:18',NULL,NULL,'0'),(21,21,'مثل هذا',NULL,NULL,'2016-01-12 15:28:39',NULL,NULL,'0'),(22,22,'القمر الأحمر',NULL,NULL,'2016-01-12 15:29:27',NULL,NULL,'0'),(23,23,'أوسشاريماشيتا والغد',NULL,NULL,'2016-01-12 15:29:48',NULL,NULL,'0'),(24,24,'غدا',NULL,NULL,'2016-01-12 15:30:09',NULL,NULL,'0'),(25,25,'أوه',NULL,NULL,'2016-01-12 15:30:30',NULL,NULL,'0'),(26,26,'أوتشاريماشيتا',NULL,NULL,'2016-01-12 15:30:50',NULL,NULL,'0'),(27,27,'جون ليست البريطانية',NULL,NULL,'2016-01-12 15:32:29',NULL,NULL,'0'),(28,28,'جون البريطاني؟',NULL,NULL,'2016-01-12 15:32:49',NULL,NULL,'0'),(29,29,'اذهب إلى السينما مع أخي.',NULL,NULL,'2016-01-12 15:33:12',NULL,NULL,'0'),(30,30,'لقد ذهبت إلى المسارح جون والفيلم.',NULL,NULL,'2016-01-12 15:33:33',NULL,NULL,'0'),(31,31,'أنا الذهاب إلى المسارح جون والفيلم.',NULL,NULL,'2016-01-12 15:33:54',NULL,NULL,'0'),(32,32,'اذهب إلى السينما على دراجة هوائية.',NULL,NULL,'2016-01-12 15:34:12',NULL,NULL,'0'),(33,33,'جون ذهب إلى السينما على دراجة؟',NULL,NULL,'2016-01-12 15:34:31',NULL,NULL,'0'),(34,34,'جون ذهب إلى السينما على دراجة',NULL,NULL,'2016-01-12 15:34:54',NULL,NULL,'0'),(35,36,'جون يذهب إلى السينما على دراجة؟',NULL,NULL,'2016-01-12 15:40:01',NULL,NULL,'0'),(36,37,'جون يذهب إلى السينما على دراجة',NULL,NULL,'2016-01-12 15:40:22',NULL,NULL,'0'),(37,38,'لم اذهب إلى السينما على دراجة جون؟',NULL,NULL,'2016-01-12 15:40:45',NULL,NULL,'0'),(38,39,'لم يكن جون يذهب إلى السينما بالدراجات',NULL,NULL,'2016-01-12 15:41:05',NULL,NULL,'0'),(39,40,'جون الذهاب إلى السينما على دراجة',NULL,NULL,'2016-01-12 15:41:24',NULL,NULL,'0'),(40,41,'جون يذهب إلى السينما بالدراجات',NULL,NULL,'2016-01-12 15:41:45',NULL,NULL,'0'),(41,42,'جون',NULL,NULL,'2016-01-12 15:42:04',NULL,NULL,'0');
/*!40000 ALTER TABLE `dict_ar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_az`
--

DROP TABLE IF EXISTS `dict_az`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_az` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_az`
--

LOCK TABLES `dict_az` WRITE;
/*!40000 ALTER TABLE `dict_az` DISABLE KEYS */;
/*!40000 ALTER TABLE `dict_az` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_be`
--

DROP TABLE IF EXISTS `dict_be`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_be` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_be`
--

LOCK TABLES `dict_be` WRITE;
/*!40000 ALTER TABLE `dict_be` DISABLE KEYS */;
/*!40000 ALTER TABLE `dict_be` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_bg`
--

DROP TABLE IF EXISTS `dict_bg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_bg` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_bg`
--

LOCK TABLES `dict_bg` WRITE;
/*!40000 ALTER TABLE `dict_bg` DISABLE KEYS */;
INSERT INTO `dict_bg` VALUES (1,1,'Лунна светлина',NULL,NULL,'2016-01-12 15:21:32',NULL,NULL,'0'),(2,2,'Нова Луна',NULL,NULL,'2016-01-12 15:21:57',NULL,NULL,'0'),(3,3,'Джон е британски',NULL,NULL,'2016-01-12 15:22:20',NULL,NULL,'0'),(4,4,'Отидох на кино с брат ми.',NULL,NULL,'2016-01-12 15:22:39',NULL,NULL,'0'),(5,5,'Отидох на кино с велосипед',NULL,NULL,'2016-01-12 15:23:02',NULL,NULL,'0'),(6,6,'Джон',NULL,NULL,'2016-01-12 15:23:23',NULL,NULL,'0'),(7,7,'Британците',NULL,NULL,'2016-01-12 15:23:44',NULL,NULL,'0'),(8,8,'Аз',NULL,NULL,'2016-01-12 15:24:05',NULL,NULL,'0'),(9,9,'Брат ми',NULL,NULL,'2016-01-12 15:24:29',NULL,NULL,'0'),(10,10,'Кино',NULL,NULL,'2016-01-12 15:24:48',NULL,NULL,'0'),(11,11,'Отидох',NULL,NULL,'2016-01-12 15:25:11',NULL,NULL,'0'),(12,12,'Байк',NULL,NULL,'2016-01-12 15:25:31',NULL,NULL,'0'),(13,13,'Лунно затъмнение',NULL,NULL,'2016-01-12 15:25:51',NULL,NULL,'0'),(14,14,'Брат ми е лекар.',NULL,NULL,'2016-01-12 15:26:15',NULL,NULL,'0'),(15,15,'Не спирайте Байк',NULL,NULL,'2016-01-12 15:26:35',NULL,NULL,'0'),(16,16,'Храмове и паркове.',NULL,NULL,'2016-01-12 15:26:52',NULL,NULL,'0'),(17,17,'Лекар',NULL,NULL,'2016-01-12 15:27:14',NULL,NULL,'0'),(18,18,'Стоп',NULL,NULL,'2016-01-12 15:27:33',NULL,NULL,'0'),(19,19,'Храм',NULL,NULL,'2016-01-12 15:27:57',NULL,NULL,'0'),(20,20,'Парк',NULL,NULL,'2016-01-12 15:28:18',NULL,NULL,'0'),(21,21,'Такива',NULL,NULL,'2016-01-12 15:28:39',NULL,NULL,'0'),(22,22,'Червена Луна',NULL,NULL,'2016-01-12 15:29:27',NULL,NULL,'0'),(23,23,'Ossharimashita и утре',NULL,NULL,'2016-01-12 15:29:48',NULL,NULL,'0'),(24,24,'Утре',NULL,NULL,'2016-01-12 15:30:09',NULL,NULL,'0'),(25,25,'Отиди.',NULL,NULL,'2016-01-12 15:30:30',NULL,NULL,'0'),(26,26,'Otsharimashita',NULL,NULL,'2016-01-12 15:30:50',NULL,NULL,'0'),(27,27,'Джон не е британски',NULL,NULL,'2016-01-12 15:32:29',NULL,NULL,'0'),(28,28,'Джон е британски?',NULL,NULL,'2016-01-12 15:32:49',NULL,NULL,'0'),(29,29,'Аз отивам на кино с брат ми.',NULL,NULL,'2016-01-12 15:33:12',NULL,NULL,'0'),(30,30,'Отидох до Джон и кино театри.',NULL,NULL,'2016-01-12 15:33:33',NULL,NULL,'0'),(31,31,'Отивам на Джон и кино театри.',NULL,NULL,'2016-01-12 15:33:54',NULL,NULL,'0'),(32,32,'Отивам на кино, на велосипед.',NULL,NULL,'2016-01-12 15:34:12',NULL,NULL,'0'),(33,33,'Джон отиде на кино на мотор?',NULL,NULL,'2016-01-12 15:34:31',NULL,NULL,'0'),(34,34,'Джон отиде на кино с велосипед',NULL,NULL,'2016-01-12 15:34:54',NULL,NULL,'0'),(35,36,'Джон отива на кино на мотор?',NULL,NULL,'2016-01-12 15:40:01',NULL,NULL,'0'),(36,37,'Джон отива на кино на мотор',NULL,NULL,'2016-01-12 15:40:22',NULL,NULL,'0'),(37,38,'Аз не отивам на кино, на мотор Джон?',NULL,NULL,'2016-01-12 15:40:45',NULL,NULL,'0'),(38,39,'Не Джон отиде на кино с велосипед',NULL,NULL,'2016-01-12 15:41:05',NULL,NULL,'0'),(39,40,'Джон отиде на кино с велосипед',NULL,NULL,'2016-01-12 15:41:24',NULL,NULL,'0'),(40,41,'Джон отиде на кино с велосипед',NULL,NULL,'2016-01-12 15:41:45',NULL,NULL,'0'),(41,42,'Джон',NULL,NULL,'2016-01-12 15:42:04',NULL,NULL,'0');
/*!40000 ALTER TABLE `dict_bg` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_bn`
--

DROP TABLE IF EXISTS `dict_bn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_bn` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_bn`
--

LOCK TABLES `dict_bn` WRITE;
/*!40000 ALTER TABLE `dict_bn` DISABLE KEYS */;
/*!40000 ALTER TABLE `dict_bn` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_ca`
--

DROP TABLE IF EXISTS `dict_ca`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_ca` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(11) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_ca`
--

LOCK TABLES `dict_ca` WRITE;
/*!40000 ALTER TABLE `dict_ca` DISABLE KEYS */;
INSERT INTO `dict_ca` VALUES (1,1,'Llum de lluna',NULL,NULL,'2016-01-12 15:21:32',NULL,NULL,'0'),(2,2,'Lluna nova',NULL,NULL,'2016-01-12 15:21:57',NULL,NULL,'0'),(3,3,'John és un britànic',NULL,NULL,'2016-01-12 15:22:20',NULL,NULL,'0'),(4,4,'Vaig anar al cinema amb el meu germà.',NULL,NULL,'2016-01-12 15:22:39',NULL,NULL,'0'),(5,5,'Vaig anar al cinema en bicicleta',NULL,NULL,'2016-01-12 15:23:02',NULL,NULL,'0'),(6,6,'John',NULL,NULL,'2016-01-12 15:23:23',NULL,NULL,'0'),(7,7,'Britànics',NULL,NULL,'2016-01-12 15:23:44',NULL,NULL,'0'),(8,8,'Jo',NULL,NULL,'2016-01-12 15:24:05',NULL,NULL,'0'),(9,9,'El meu germà',NULL,NULL,'2016-01-12 15:24:29',NULL,NULL,'0'),(10,10,'Cinema',NULL,NULL,'2016-01-12 15:24:48',NULL,NULL,'0'),(11,11,'Me n\'anava',NULL,NULL,'2016-01-12 15:25:11',NULL,NULL,'0'),(12,12,'Bicicleta',NULL,NULL,'2016-01-12 15:25:31',NULL,NULL,'0'),(13,13,'Eclipsi de lluna',NULL,NULL,'2016-01-12 15:25:51',NULL,NULL,'0'),(14,14,'El meu germà és un metge.',NULL,NULL,'2016-01-12 15:26:15',NULL,NULL,'0'),(15,15,'No s\'aturi la moto',NULL,NULL,'2016-01-12 15:26:35',NULL,NULL,'0'),(16,16,'Santuaris i parcs.',NULL,NULL,'2016-01-12 15:26:52',NULL,NULL,'0'),(17,17,'Metge',NULL,NULL,'2016-01-12 15:27:14',NULL,NULL,'0'),(18,18,'Parada',NULL,NULL,'2016-01-12 15:27:33',NULL,NULL,'0'),(19,19,'Santuari',NULL,NULL,'2016-01-12 15:27:57',NULL,NULL,'0'),(20,20,'Parc',NULL,NULL,'2016-01-12 15:28:18',NULL,NULL,'0'),(21,21,'Tal',NULL,NULL,'2016-01-12 15:28:39',NULL,NULL,'0'),(22,22,'Lluna Roja',NULL,NULL,'2016-01-12 15:29:27',NULL,NULL,'0'),(23,23,'Ossharimashita i demà',NULL,NULL,'2016-01-12 15:29:48',NULL,NULL,'0'),(24,24,'Demà',NULL,NULL,'2016-01-12 15:30:09',NULL,NULL,'0'),(25,25,'Anar.',NULL,NULL,'2016-01-12 15:30:30',NULL,NULL,'0'),(26,26,'Otsharimashita',NULL,NULL,'2016-01-12 15:30:50',NULL,NULL,'0'),(27,27,'No és britànic John',NULL,NULL,'2016-01-12 15:32:29',NULL,NULL,'0'),(28,28,'John és britànica?',NULL,NULL,'2016-01-12 15:32:49',NULL,NULL,'0'),(29,29,'Vaig anar al cinema amb el meu germà.',NULL,NULL,'2016-01-12 15:33:12',NULL,NULL,'0'),(30,30,'Vaig anar a teatres John i pel·lícula.',NULL,NULL,'2016-01-12 15:33:33',NULL,NULL,'0'),(31,31,'Vaig a teatres John i pel·lícula.',NULL,NULL,'2016-01-12 15:33:54',NULL,NULL,'0'),(32,32,'Vaig anar al cinema en bicicleta.',NULL,NULL,'2016-01-12 15:34:12',NULL,NULL,'0'),(33,33,'John va anar al cinema en una bicicleta?',NULL,NULL,'2016-01-12 15:34:31',NULL,NULL,'0'),(34,34,'John va anar al cinema en bicicleta',NULL,NULL,'2016-01-12 15:34:54',NULL,NULL,'0'),(35,36,'John va al cinema en una bicicleta?',NULL,NULL,'2016-01-12 15:40:01',NULL,NULL,'0'),(36,37,'John va al cinema en bicicleta',NULL,NULL,'2016-01-12 15:40:22',NULL,NULL,'0'),(37,38,'No vaig al cinema en una bicicleta John?',NULL,NULL,'2016-01-12 15:40:45',NULL,NULL,'0'),(38,39,'John no anar al cinema en bicicleta',NULL,NULL,'2016-01-12 15:41:05',NULL,NULL,'0'),(39,40,'John anar al cinema en moto',NULL,NULL,'2016-01-12 15:41:24',NULL,NULL,'0'),(40,41,'John anar al cinema en bicicleta',NULL,NULL,'2016-01-12 15:41:45',NULL,NULL,'0'),(41,42,'John',NULL,NULL,'2016-01-12 15:42:04',NULL,NULL,'0');
/*!40000 ALTER TABLE `dict_ca` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_cs`
--

DROP TABLE IF EXISTS `dict_cs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_cs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_cs`
--

LOCK TABLES `dict_cs` WRITE;
/*!40000 ALTER TABLE `dict_cs` DISABLE KEYS */;
INSERT INTO `dict_cs` VALUES (1,1,'Měsíční svit',NULL,NULL,'2016-01-12 15:21:32',NULL,NULL,'0'),(2,2,'Nový měsíc',NULL,NULL,'2016-01-12 15:21:57',NULL,NULL,'0'),(3,3,'John je britský',NULL,NULL,'2016-01-12 15:22:20',NULL,NULL,'0'),(4,4,'Šel jsem do kina s mým bratrem.',NULL,NULL,'2016-01-12 15:22:39',NULL,NULL,'0'),(5,5,'Šel jsem do kina na kole',NULL,NULL,'2016-01-12 15:23:02',NULL,NULL,'0'),(6,6,'John',NULL,NULL,'2016-01-12 15:23:23',NULL,NULL,'0'),(7,7,'Britové',NULL,NULL,'2016-01-12 15:23:44',NULL,NULL,'0'),(8,8,'Já',NULL,NULL,'2016-01-12 15:24:05',NULL,NULL,'0'),(9,9,'Můj bratr',NULL,NULL,'2016-01-12 15:24:29',NULL,NULL,'0'),(10,10,'Kino',NULL,NULL,'2016-01-12 15:24:48',NULL,NULL,'0'),(11,11,'Já jsem šel',NULL,NULL,'2016-01-12 15:25:11',NULL,NULL,'0'),(12,12,'Kolo',NULL,NULL,'2016-01-12 15:25:31',NULL,NULL,'0'),(13,13,'Zatmění měsíce',NULL,NULL,'2016-01-12 15:25:51',NULL,NULL,'0'),(14,14,'Můj bratr je lékař.',NULL,NULL,'2016-01-12 15:26:15',NULL,NULL,'0'),(15,15,'Není zastavit kolo',NULL,NULL,'2016-01-12 15:26:35',NULL,NULL,'0'),(16,16,'Svatyně a parky.',NULL,NULL,'2016-01-12 15:26:52',NULL,NULL,'0'),(17,17,'Doktor',NULL,NULL,'2016-01-12 15:27:14',NULL,NULL,'0'),(18,18,'Stop',NULL,NULL,'2016-01-12 15:27:33',NULL,NULL,'0'),(19,19,'Svatyně',NULL,NULL,'2016-01-12 15:27:57',NULL,NULL,'0'),(20,20,'Park',NULL,NULL,'2016-01-12 15:28:18',NULL,NULL,'0'),(21,21,'Taková',NULL,NULL,'2016-01-12 15:28:39',NULL,NULL,'0'),(22,22,'Červený měsíc',NULL,NULL,'2016-01-12 15:29:27',NULL,NULL,'0'),(23,23,'Ossharimashita a zítra',NULL,NULL,'2016-01-12 15:29:48',NULL,NULL,'0'),(24,24,'Zítra',NULL,NULL,'2016-01-12 15:30:09',NULL,NULL,'0'),(25,25,'Jděte.',NULL,NULL,'2016-01-12 15:30:30',NULL,NULL,'0'),(26,26,'Otsharimashita',NULL,NULL,'2016-01-12 15:30:50',NULL,NULL,'0'),(27,27,'John není britské',NULL,NULL,'2016-01-12 15:32:29',NULL,NULL,'0'),(28,28,'John je britská?',NULL,NULL,'2016-01-12 15:32:49',NULL,NULL,'0'),(29,29,'Chodím do kina s mým bratrem.',NULL,NULL,'2016-01-12 15:33:12',NULL,NULL,'0'),(30,30,'Šel jsem do divadla Johna a film.',NULL,NULL,'2016-01-12 15:33:33',NULL,NULL,'0'),(31,31,'Chodím do divadla Johna a film.',NULL,NULL,'2016-01-12 15:33:54',NULL,NULL,'0'),(32,32,'Jdu do kina na kole.',NULL,NULL,'2016-01-12 15:34:12',NULL,NULL,'0'),(33,33,'John šel do kina na kole?',NULL,NULL,'2016-01-12 15:34:31',NULL,NULL,'0'),(34,34,'John šel do kina na kole',NULL,NULL,'2016-01-12 15:34:54',NULL,NULL,'0'),(35,36,'John jde do kina na kole?',NULL,NULL,'2016-01-12 15:40:01',NULL,NULL,'0'),(36,37,'John jde do kina na kole',NULL,NULL,'2016-01-12 15:40:22',NULL,NULL,'0'),(37,38,'Nechtěl jsem jít do kina na kole John?',NULL,NULL,'2016-01-12 15:40:45',NULL,NULL,'0'),(38,39,'John nešel do kina na kole',NULL,NULL,'2016-01-12 15:41:05',NULL,NULL,'0'),(39,40,'John jít do kina na kole',NULL,NULL,'2016-01-12 15:41:24',NULL,NULL,'0'),(40,41,'John jít do kina na kole',NULL,NULL,'2016-01-12 15:41:45',NULL,NULL,'0'),(41,42,'John',NULL,NULL,'2016-01-12 15:42:04',NULL,NULL,'0');
/*!40000 ALTER TABLE `dict_cs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_cy`
--

DROP TABLE IF EXISTS `dict_cy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_cy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_cy`
--

LOCK TABLES `dict_cy` WRITE;
/*!40000 ALTER TABLE `dict_cy` DISABLE KEYS */;
/*!40000 ALTER TABLE `dict_cy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_da`
--

DROP TABLE IF EXISTS `dict_da`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_da` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_da`
--

LOCK TABLES `dict_da` WRITE;
/*!40000 ALTER TABLE `dict_da` DISABLE KEYS */;
INSERT INTO `dict_da` VALUES (1,1,'Moonlight',NULL,NULL,'2016-01-12 15:21:32',NULL,NULL,'0'),(2,2,'New Moon',NULL,NULL,'2016-01-12 15:21:57',NULL,NULL,'0'),(3,3,'John er en britisk',NULL,NULL,'2016-01-12 15:22:20',NULL,NULL,'0'),(4,4,'Jeg gik i biografen med min bror.',NULL,NULL,'2016-01-12 15:22:39',NULL,NULL,'0'),(5,5,'Jeg gik i biografen på en cykel',NULL,NULL,'2016-01-12 15:23:02',NULL,NULL,'0'),(6,6,'John',NULL,NULL,'2016-01-12 15:23:23',NULL,NULL,'0'),(7,7,'Briter',NULL,NULL,'2016-01-12 15:23:44',NULL,NULL,'0'),(8,8,'Jeg',NULL,NULL,'2016-01-12 15:24:05',NULL,NULL,'0'),(9,9,'Min bror',NULL,NULL,'2016-01-12 15:24:29',NULL,NULL,'0'),(10,10,'Biograf',NULL,NULL,'2016-01-12 15:24:48',NULL,NULL,'0'),(11,11,'Jeg gik',NULL,NULL,'2016-01-12 15:25:11',NULL,NULL,'0'),(12,12,'Cykel',NULL,NULL,'2016-01-12 15:25:31',NULL,NULL,'0'),(13,13,'Måneformørkelse',NULL,NULL,'2016-01-12 15:25:51',NULL,NULL,'0'),(14,14,'Min bror er en læge.',NULL,NULL,'2016-01-12 15:26:15',NULL,NULL,'0'),(15,15,'Stop ikke cyklen',NULL,NULL,'2016-01-12 15:26:35',NULL,NULL,'0'),(16,16,'Helligdomme og parker.',NULL,NULL,'2016-01-12 15:26:52',NULL,NULL,'0'),(17,17,'Læge',NULL,NULL,'2016-01-12 15:27:14',NULL,NULL,'0'),(18,18,'Stop',NULL,NULL,'2016-01-12 15:27:33',NULL,NULL,'0'),(19,19,'Helligdom',NULL,NULL,'2016-01-12 15:27:57',NULL,NULL,'0'),(20,20,'Park',NULL,NULL,'2016-01-12 15:28:18',NULL,NULL,'0'),(21,21,'Sådanne',NULL,NULL,'2016-01-12 15:28:39',NULL,NULL,'0'),(22,22,'Rød måne',NULL,NULL,'2016-01-12 15:29:27',NULL,NULL,'0'),(23,23,'Ossharimashita og i morgen',NULL,NULL,'2016-01-12 15:29:48',NULL,NULL,'0'),(24,24,'I morgen',NULL,NULL,'2016-01-12 15:30:09',NULL,NULL,'0'),(25,25,'Gå.',NULL,NULL,'2016-01-12 15:30:30',NULL,NULL,'0'),(26,26,'Otsharimashita',NULL,NULL,'2016-01-12 15:30:50',NULL,NULL,'0'),(27,27,'John er ikke British',NULL,NULL,'2016-01-12 15:32:29',NULL,NULL,'0'),(28,28,'John er britiske?',NULL,NULL,'2016-01-12 15:32:49',NULL,NULL,'0'),(29,29,'Jeg går i biografen med min bror.',NULL,NULL,'2016-01-12 15:33:12',NULL,NULL,'0'),(30,30,'Jeg gik til John og filmen biografer.',NULL,NULL,'2016-01-12 15:33:33',NULL,NULL,'0'),(31,31,'Jeg går til John og filmen biografer.',NULL,NULL,'2016-01-12 15:33:54',NULL,NULL,'0'),(32,32,'Jeg går i biografen på en cykel.',NULL,NULL,'2016-01-12 15:34:12',NULL,NULL,'0'),(33,33,'John gik i biografen på en cykel?',NULL,NULL,'2016-01-12 15:34:31',NULL,NULL,'0'),(34,34,'John gik i biografen på en cykel',NULL,NULL,'2016-01-12 15:34:54',NULL,NULL,'0'),(35,36,'John går i biografen på en cykel?',NULL,NULL,'2016-01-12 15:40:01',NULL,NULL,'0'),(36,37,'John går i biografen på en cykel',NULL,NULL,'2016-01-12 15:40:22',NULL,NULL,'0'),(37,38,'Jeg gjorde ikke gå i biografen på en cykel John?',NULL,NULL,'2016-01-12 15:40:45',NULL,NULL,'0'),(38,39,'Ikke gå John i biografen på cykel',NULL,NULL,'2016-01-12 15:41:05',NULL,NULL,'0'),(39,40,'John gå i biografen på en cykel',NULL,NULL,'2016-01-12 15:41:24',NULL,NULL,'0'),(40,41,'John gå i biografen på cykel',NULL,NULL,'2016-01-12 15:41:45',NULL,NULL,'0'),(41,42,'John',NULL,NULL,'2016-01-12 15:42:04',NULL,NULL,'0');
/*!40000 ALTER TABLE `dict_da` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_de`
--

DROP TABLE IF EXISTS `dict_de`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_de` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_de`
--

LOCK TABLES `dict_de` WRITE;
/*!40000 ALTER TABLE `dict_de` DISABLE KEYS */;
INSERT INTO `dict_de` VALUES (1,1,'Mondlicht',NULL,NULL,'2016-01-12 15:21:32',NULL,NULL,'0'),(2,2,'New Moon',NULL,NULL,'2016-01-12 15:21:57',NULL,NULL,'0'),(3,3,'John ist ein britischer',NULL,NULL,'2016-01-12 15:22:20',NULL,NULL,'0'),(4,4,'Ich ging mit meinem Bruder ins Kino.',NULL,NULL,'2016-01-12 15:22:39',NULL,NULL,'0'),(5,5,'Ich ging ins Kino auf dem Fahrrad',NULL,NULL,'2016-01-12 15:23:02',NULL,NULL,'0'),(6,6,'John',NULL,NULL,'2016-01-12 15:23:23',NULL,NULL,'0'),(7,7,'Briten',NULL,NULL,'2016-01-12 15:23:44',NULL,NULL,'0'),(8,8,'Ich',NULL,NULL,'2016-01-12 15:24:05',NULL,NULL,'0'),(9,9,'Mein Bruder',NULL,NULL,'2016-01-12 15:24:29',NULL,NULL,'0'),(10,10,'Kino',NULL,NULL,'2016-01-12 15:24:48',NULL,NULL,'0'),(11,11,'Ich ging',NULL,NULL,'2016-01-12 15:25:11',NULL,NULL,'0'),(12,12,'Fahrrad',NULL,NULL,'2016-01-12 15:25:31',NULL,NULL,'0'),(13,13,'Mondfinsternis',NULL,NULL,'2016-01-12 15:25:51',NULL,NULL,'0'),(14,14,'Mein Bruder ist ein Arzt.',NULL,NULL,'2016-01-12 15:26:15',NULL,NULL,'0'),(15,15,'Hört nicht auf das Fahrrad',NULL,NULL,'2016-01-12 15:26:35',NULL,NULL,'0'),(16,16,'Schreine und Parks.',NULL,NULL,'2016-01-12 15:26:52',NULL,NULL,'0'),(17,17,'Arzt',NULL,NULL,'2016-01-12 15:27:14',NULL,NULL,'0'),(18,18,'Stop',NULL,NULL,'2016-01-12 15:27:33',NULL,NULL,'0'),(19,19,'Schrein',NULL,NULL,'2016-01-12 15:27:57',NULL,NULL,'0'),(20,20,'Park',NULL,NULL,'2016-01-12 15:28:18',NULL,NULL,'0'),(21,21,'Derartige',NULL,NULL,'2016-01-12 15:28:39',NULL,NULL,'0'),(22,22,'Roter Mond',NULL,NULL,'2016-01-12 15:29:27',NULL,NULL,'0'),(23,23,'Ossharimashita und morgen',NULL,NULL,'2016-01-12 15:29:48',NULL,NULL,'0'),(24,24,'Morgen',NULL,NULL,'2016-01-12 15:30:09',NULL,NULL,'0'),(25,25,'Gehen.',NULL,NULL,'2016-01-12 15:30:30',NULL,NULL,'0'),(26,26,'Otsharimashita',NULL,NULL,'2016-01-12 15:30:50',NULL,NULL,'0'),(27,27,'John ist nicht britische',NULL,NULL,'2016-01-12 15:32:29',NULL,NULL,'0'),(28,28,'Ist John British?',NULL,NULL,'2016-01-12 15:32:49',NULL,NULL,'0'),(29,29,'Ich gehe ins Kino mit meinem Bruder.',NULL,NULL,'2016-01-12 15:33:12',NULL,NULL,'0'),(30,30,'Ich ging zu John und Film-Theater.',NULL,NULL,'2016-01-12 15:33:33',NULL,NULL,'0'),(31,31,'Ich gehe in John und Film-Theater.',NULL,NULL,'2016-01-12 15:33:54',NULL,NULL,'0'),(32,32,'Ich gehe ins Kino mit dem Fahrrad.',NULL,NULL,'2016-01-12 15:34:12',NULL,NULL,'0'),(33,33,'Ging John ins Kino auf dem Fahrrad?',NULL,NULL,'2016-01-12 15:34:31',NULL,NULL,'0'),(34,34,'John ging ins Kino auf dem Fahrrad',NULL,NULL,'2016-01-12 15:34:54',NULL,NULL,'0'),(35,36,'Geht John zum Kino auf dem Fahrrad?',NULL,NULL,'2016-01-12 15:40:01',NULL,NULL,'0'),(36,37,'John geht zum Kino auf dem Fahrrad',NULL,NULL,'2016-01-12 15:40:22',NULL,NULL,'0'),(37,38,'Gegangen ich bin nicht auf dem Fahrrad John ins Kino?',NULL,NULL,'2016-01-12 15:40:45',NULL,NULL,'0'),(38,39,'Nicht ins John mit dem Fahrrad Kino',NULL,NULL,'2016-01-12 15:41:05',NULL,NULL,'0'),(39,40,'John auf dem Fahrrad ins Kino gehen',NULL,NULL,'2016-01-12 15:41:24',NULL,NULL,'0'),(40,41,'John mit dem Fahrrad ins Kino gehen',NULL,NULL,'2016-01-12 15:41:45',NULL,NULL,'0'),(41,42,'John',NULL,NULL,'2016-01-12 15:42:04',NULL,NULL,'0');
/*!40000 ALTER TABLE `dict_de` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_el`
--

DROP TABLE IF EXISTS `dict_el`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_el` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` int(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_el`
--

LOCK TABLES `dict_el` WRITE;
/*!40000 ALTER TABLE `dict_el` DISABLE KEYS */;
INSERT INTO `dict_el` VALUES (1,1,'Φως του φεγγαριού',NULL,NULL,'2016-01-12 15:21:32',NULL,NULL,0),(2,2,'Νέα Σελήνη',NULL,NULL,'2016-01-12 15:21:57',NULL,NULL,0),(3,3,'John είναι μια βρετανική',NULL,NULL,'2016-01-12 15:22:20',NULL,NULL,0),(4,4,'Πήγα στον κινηματογράφο με τον αδελφό μου.',NULL,NULL,'2016-01-12 15:22:39',NULL,NULL,0),(5,5,'Πήγα στον κινηματογράφο με ποδήλατο',NULL,NULL,'2016-01-12 15:23:02',NULL,NULL,0),(6,6,'John',NULL,NULL,'2016-01-12 15:23:23',NULL,NULL,0),(7,7,'Βρετανικό λαό',NULL,NULL,'2016-01-12 15:23:44',NULL,NULL,0),(8,8,'Εγώ',NULL,NULL,'2016-01-12 15:24:05',NULL,NULL,0),(9,9,'Ο αδελφός μου',NULL,NULL,'2016-01-12 15:24:29',NULL,NULL,0),(10,10,'Κινηματογράφος',NULL,NULL,'2016-01-12 15:24:48',NULL,NULL,0),(11,11,'Πήγα',NULL,NULL,'2016-01-12 15:25:11',NULL,NULL,0),(12,12,'Ποδήλατο',NULL,NULL,'2016-01-12 15:25:31',NULL,NULL,0),(13,13,'Σεληνιακή έκλειψη',NULL,NULL,'2016-01-12 15:25:51',NULL,NULL,0),(14,14,'Ο αδελφός μου είναι γιατρός.',NULL,NULL,'2016-01-12 15:26:15',NULL,NULL,0),(15,15,'Δεν σταματούν το ποδήλατο',NULL,NULL,'2016-01-12 15:26:35',NULL,NULL,0),(16,16,'Ιερά και πάρκα.',NULL,NULL,'2016-01-12 15:26:52',NULL,NULL,0),(17,17,'Γιατρός',NULL,NULL,'2016-01-12 15:27:14',NULL,NULL,0),(18,18,'Σταμάτα',NULL,NULL,'2016-01-12 15:27:33',NULL,NULL,0),(19,19,'Τέμενος',NULL,NULL,'2016-01-12 15:27:57',NULL,NULL,0),(20,20,'Πάρκο',NULL,NULL,'2016-01-12 15:28:18',NULL,NULL,0),(21,21,'Μια τέτοια',NULL,NULL,'2016-01-12 15:28:39',NULL,NULL,0),(22,22,'Κόκκινο φεγγάρι',NULL,NULL,'2016-01-12 15:29:27',NULL,NULL,0),(23,23,'Ossharimashita και αύριο',NULL,NULL,'2016-01-12 15:29:48',NULL,NULL,0),(24,24,'Αύριο',NULL,NULL,'2016-01-12 15:30:09',NULL,NULL,0),(25,25,'Πάει.',NULL,NULL,'2016-01-12 15:30:30',NULL,NULL,0),(26,26,'Otsharimashita',NULL,NULL,'2016-01-12 15:30:50',NULL,NULL,0),(27,27,'John δεν είναι Άγγλος',NULL,NULL,'2016-01-12 15:32:29',NULL,NULL,0),(28,28,'John είναι Βρετανός;',NULL,NULL,'2016-01-12 15:32:49',NULL,NULL,0),(29,29,'Πηγαίνω στο σινεμά με τον αδελφό μου.',NULL,NULL,'2016-01-12 15:33:12',NULL,NULL,0),(30,30,'Πήγα να John και ταινία θέατρα.',NULL,NULL,'2016-01-12 15:33:33',NULL,NULL,0),(31,31,'Πάω να John και ταινία θέατρα.',NULL,NULL,'2016-01-12 15:33:54',NULL,NULL,0),(32,32,'Πηγαίνω στο σινεμά με ένα ποδήλατο.',NULL,NULL,'2016-01-12 15:34:12',NULL,NULL,0),(33,33,'John πήγε στο σινεμά με ένα ποδήλατο;',NULL,NULL,'2016-01-12 15:34:31',NULL,NULL,0),(34,34,'John πήγε στο σινεμά με ποδήλατο',NULL,NULL,'2016-01-12 15:34:54',NULL,NULL,0),(35,36,'John που πηγαίνει στον κινηματογράφο σε ένα ποδήλατο;',NULL,NULL,'2016-01-12 15:40:01',NULL,NULL,0),(36,37,'John που πηγαίνει στον κινηματογράφο με ποδήλατο',NULL,NULL,'2016-01-12 15:40:22',NULL,NULL,0),(37,38,'Εγώ δεν πάω στο σινεμά με ποδήλατο John;',NULL,NULL,'2016-01-12 15:40:45',NULL,NULL,0),(38,39,'Δεν John πήγε στο σινεμά με ποδήλατο',NULL,NULL,'2016-01-12 15:41:05',NULL,NULL,0),(39,40,'John πάει στον κινηματογράφο με ποδήλατο',NULL,NULL,'2016-01-12 15:41:24',NULL,NULL,0),(40,41,'John πάτε στον κινηματογράφο με ποδήλατο',NULL,NULL,'2016-01-12 15:41:45',NULL,NULL,0),(41,42,'John',NULL,NULL,'2016-01-12 15:42:04',NULL,NULL,0);
/*!40000 ALTER TABLE `dict_el` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_en`
--

DROP TABLE IF EXISTS `dict_en`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_en` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_en`
--

LOCK TABLES `dict_en` WRITE;
/*!40000 ALTER TABLE `dict_en` DISABLE KEYS */;
INSERT INTO `dict_en` VALUES (1,1,'Moonlight',NULL,NULL,'2016-01-12 15:21:32',NULL,NULL,'0'),(2,2,'New Moon',NULL,NULL,'2016-01-12 15:21:57',NULL,NULL,'0'),(3,3,'John is a British',NULL,NULL,'2016-01-12 15:22:20',NULL,NULL,'0'),(4,4,'I went to the cinema with my brother.',NULL,NULL,'2016-01-12 15:22:39',NULL,NULL,'0'),(5,5,'I went to the cinema on a bike',NULL,NULL,'2016-01-12 15:23:02',NULL,NULL,'0'),(6,6,'John',NULL,NULL,'2016-01-12 15:23:23',NULL,NULL,'0'),(7,7,'British people',NULL,NULL,'2016-01-12 15:23:44',NULL,NULL,'0'),(8,8,'I',NULL,NULL,'2016-01-12 15:24:05',NULL,NULL,'0'),(9,9,'My brother',NULL,NULL,'2016-01-12 15:24:29',NULL,NULL,'0'),(10,10,'Cinema',NULL,NULL,'2016-01-12 15:24:48',NULL,NULL,'0'),(11,11,'I went',NULL,NULL,'2016-01-12 15:25:11',NULL,NULL,'0'),(12,12,'Bike',NULL,NULL,'2016-01-12 15:25:31',NULL,NULL,'0'),(13,13,'Lunar Eclipse',NULL,NULL,'2016-01-12 15:25:51',NULL,NULL,'0'),(14,14,'My brother is a doctor.',NULL,NULL,'2016-01-12 15:26:15',NULL,NULL,'0'),(15,15,'Do not stop the bike',NULL,NULL,'2016-01-12 15:26:35',NULL,NULL,'0'),(16,16,'Shrines and parks.',NULL,NULL,'2016-01-12 15:26:52',NULL,NULL,'0'),(17,17,'Doctor',NULL,NULL,'2016-01-12 15:27:14',NULL,NULL,'0'),(18,18,'Stop',NULL,NULL,'2016-01-12 15:27:33',NULL,NULL,'0'),(19,19,'Shrine',NULL,NULL,'2016-01-12 15:27:57',NULL,NULL,'0'),(20,20,'Park',NULL,NULL,'2016-01-12 15:28:18',NULL,NULL,'0'),(21,21,'Such',NULL,NULL,'2016-01-12 15:28:39',NULL,NULL,'0'),(22,22,'Red Moon',NULL,NULL,'2016-01-12 15:29:27',NULL,NULL,'0'),(23,23,'Ossharimashita and tomorrow',NULL,NULL,'2016-01-12 15:29:48',NULL,NULL,'0'),(24,24,'Tomorrow',NULL,NULL,'2016-01-12 15:30:09',NULL,NULL,'0'),(25,25,'Go.',NULL,NULL,'2016-01-12 15:30:30',NULL,NULL,'0'),(26,26,'Otsharimashita',NULL,NULL,'2016-01-12 15:30:50',NULL,NULL,'0'),(27,27,'John is not British',NULL,NULL,'2016-01-12 15:32:29',NULL,NULL,'0'),(28,28,'John is British?',NULL,NULL,'2016-01-12 15:32:49',NULL,NULL,'0'),(29,29,'I go to the cinema with my brother.',NULL,NULL,'2016-01-12 15:33:12',NULL,NULL,'0'),(30,30,'I went to John and movie theaters.',NULL,NULL,'2016-01-12 15:33:33',NULL,NULL,'0'),(31,31,'I go to John and movie theaters.',NULL,NULL,'2016-01-12 15:33:54',NULL,NULL,'0'),(32,32,'I go to the cinema on a bicycle.',NULL,NULL,'2016-01-12 15:34:12',NULL,NULL,'0'),(33,33,'John went to the cinema on a bike?',NULL,NULL,'2016-01-12 15:34:31',NULL,NULL,'0'),(34,34,'John went to the cinema on a bike',NULL,NULL,'2016-01-12 15:34:54',NULL,NULL,'0'),(35,36,'John goes to the cinema on a bike?',NULL,NULL,'2016-01-12 15:40:01',NULL,NULL,'0'),(36,37,'John goes to the cinema on a bike',NULL,NULL,'2016-01-12 15:40:22',NULL,NULL,'0'),(37,38,'I didn\'t go to the cinema on a bike John?',NULL,NULL,'2016-01-12 15:40:45',NULL,NULL,'0'),(38,39,'Didn\'t John go to the cinema by bicycle',NULL,NULL,'2016-01-12 15:41:05',NULL,NULL,'0'),(39,40,'John go to the cinema on a bike',NULL,NULL,'2016-01-12 15:41:24',NULL,NULL,'0'),(40,41,'John go to the cinema by bicycle',NULL,NULL,'2016-01-12 15:41:45',NULL,NULL,'0'),(41,42,'John',NULL,NULL,'2016-01-12 15:42:04',NULL,NULL,'0');
/*!40000 ALTER TABLE `dict_en` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_es`
--

DROP TABLE IF EXISTS `dict_es`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_es` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_es`
--

LOCK TABLES `dict_es` WRITE;
/*!40000 ALTER TABLE `dict_es` DISABLE KEYS */;
INSERT INTO `dict_es` VALUES (1,1,'Luz de la luna',NULL,NULL,'2016-01-12 15:21:32',NULL,NULL,'0'),(2,2,'Luna nueva',NULL,NULL,'2016-01-12 15:21:57',NULL,NULL,'0'),(3,3,'Juan es un británico',NULL,NULL,'2016-01-12 15:22:20',NULL,NULL,'0'),(4,4,'Fui al cine con mi hermano.',NULL,NULL,'2016-01-12 15:22:39',NULL,NULL,'0'),(5,5,'Fui al cine en una moto',NULL,NULL,'2016-01-12 15:23:02',NULL,NULL,'0'),(6,6,'John',NULL,NULL,'2016-01-12 15:23:23',NULL,NULL,'0'),(7,7,'Pueblo británico',NULL,NULL,'2016-01-12 15:23:44',NULL,NULL,'0'),(8,8,'Me',NULL,NULL,'2016-01-12 15:24:05',NULL,NULL,'0'),(9,9,'Mi hermano',NULL,NULL,'2016-01-12 15:24:29',NULL,NULL,'0'),(10,10,'Cine',NULL,NULL,'2016-01-12 15:24:48',NULL,NULL,'0'),(11,11,'Fui',NULL,NULL,'2016-01-12 15:25:11',NULL,NULL,'0'),(12,12,'Bicicleta',NULL,NULL,'2016-01-12 15:25:31',NULL,NULL,'0'),(13,13,'Eclipse de luna',NULL,NULL,'2016-01-12 15:25:51',NULL,NULL,'0'),(14,14,'Mi hermano es médico.',NULL,NULL,'2016-01-12 15:26:15',NULL,NULL,'0'),(15,15,'No deje la bicicleta',NULL,NULL,'2016-01-12 15:26:35',NULL,NULL,'0'),(16,16,'Parques y santuarios.',NULL,NULL,'2016-01-12 15:26:52',NULL,NULL,'0'),(17,17,'Médico',NULL,NULL,'2016-01-12 15:27:14',NULL,NULL,'0'),(18,18,'Parada',NULL,NULL,'2016-01-12 15:27:33',NULL,NULL,'0'),(19,19,'Santuario de',NULL,NULL,'2016-01-12 15:27:57',NULL,NULL,'0'),(20,20,'Parque',NULL,NULL,'2016-01-12 15:28:18',NULL,NULL,'0'),(21,21,'Tales',NULL,NULL,'2016-01-12 15:28:39',NULL,NULL,'0'),(22,22,'Luna Roja',NULL,NULL,'2016-01-12 15:29:27',NULL,NULL,'0'),(23,23,'Ossharimashita y mañana',NULL,NULL,'2016-01-12 15:29:48',NULL,NULL,'0'),(24,24,'Mañana',NULL,NULL,'2016-01-12 15:30:09',NULL,NULL,'0'),(25,25,'Go.',NULL,NULL,'2016-01-12 15:30:30',NULL,NULL,'0'),(26,26,'Otsharimashita',NULL,NULL,'2016-01-12 15:30:50',NULL,NULL,'0'),(27,27,'Juan no es británico',NULL,NULL,'2016-01-12 15:32:29',NULL,NULL,'0'),(28,28,'¿Juan es británico?',NULL,NULL,'2016-01-12 15:32:49',NULL,NULL,'0'),(29,29,'Voy al cine con mi hermano.',NULL,NULL,'2016-01-12 15:33:12',NULL,NULL,'0'),(30,30,'Fui a los cines de la película y John.',NULL,NULL,'2016-01-12 15:33:33',NULL,NULL,'0'),(31,31,'Ir a teatros de la película y John.',NULL,NULL,'2016-01-12 15:33:54',NULL,NULL,'0'),(32,32,'Voy al cine en una bicicleta.',NULL,NULL,'2016-01-12 15:34:12',NULL,NULL,'0'),(33,33,'¿John entró al cine en una moto?',NULL,NULL,'2016-01-12 15:34:31',NULL,NULL,'0'),(34,34,'John entró al cine en una moto',NULL,NULL,'2016-01-12 15:34:54',NULL,NULL,'0'),(35,36,'¿John va al cine en una bicicleta?',NULL,NULL,'2016-01-12 15:40:01',NULL,NULL,'0'),(36,37,'John va al cine en una bicicleta',NULL,NULL,'2016-01-12 15:40:22',NULL,NULL,'0'),(37,38,'¿No fui al cine en una bici de John?',NULL,NULL,'2016-01-12 15:40:45',NULL,NULL,'0'),(38,39,'John no ir al cine en bicicleta',NULL,NULL,'2016-01-12 15:41:05',NULL,NULL,'0'),(39,40,'John ir al cine en una moto',NULL,NULL,'2016-01-12 15:41:24',NULL,NULL,'0'),(40,41,'John ir al cine en bicicleta',NULL,NULL,'2016-01-12 15:41:45',NULL,NULL,'0'),(41,42,'John',NULL,NULL,'2016-01-12 15:42:04',NULL,NULL,'0');
/*!40000 ALTER TABLE `dict_es` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_et`
--

DROP TABLE IF EXISTS `dict_et`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_et` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_et`
--

LOCK TABLES `dict_et` WRITE;
/*!40000 ALTER TABLE `dict_et` DISABLE KEYS */;
INSERT INTO `dict_et` VALUES (1,1,'Moonlight',NULL,NULL,'2016-01-12 15:21:32',NULL,NULL,'0'),(2,2,'Noorkuu',NULL,NULL,'2016-01-12 15:21:57',NULL,NULL,'0'),(3,3,'John on Briti',NULL,NULL,'2016-01-12 15:22:20',NULL,NULL,'0'),(4,4,'Käisin kinos koos oma vennaga.',NULL,NULL,'2016-01-12 15:22:39',NULL,NULL,'0'),(5,5,'Käisin kinos bike',NULL,NULL,'2016-01-12 15:23:02',NULL,NULL,'0'),(6,6,'John',NULL,NULL,'2016-01-12 15:23:23',NULL,NULL,'0'),(7,7,'Britid',NULL,NULL,'2016-01-12 15:23:44',NULL,NULL,'0'),(8,8,'Ma',NULL,NULL,'2016-01-12 15:24:05',NULL,NULL,'0'),(9,9,'Minu vend',NULL,NULL,'2016-01-12 15:24:29',NULL,NULL,'0'),(10,10,'Kino',NULL,NULL,'2016-01-12 15:24:48',NULL,NULL,'0'),(11,11,'Käisin',NULL,NULL,'2016-01-12 15:25:11',NULL,NULL,'0'),(12,12,'Bike',NULL,NULL,'2016-01-12 15:25:31',NULL,NULL,'0'),(13,13,'Lunar Eclipse',NULL,NULL,'2016-01-12 15:25:51',NULL,NULL,'0'),(14,14,'Minu vend on arst.',NULL,NULL,'2016-01-12 15:26:15',NULL,NULL,'0'),(15,15,'Ärge lõpetage bike',NULL,NULL,'2016-01-12 15:26:35',NULL,NULL,'0'),(16,16,'Pühapaikade ja pargid.',NULL,NULL,'2016-01-12 15:26:52',NULL,NULL,'0'),(17,17,'Arst',NULL,NULL,'2016-01-12 15:27:14',NULL,NULL,'0'),(18,18,'Stopp',NULL,NULL,'2016-01-12 15:27:33',NULL,NULL,'0'),(19,19,'Pühapaik',NULL,NULL,'2016-01-12 15:27:57',NULL,NULL,'0'),(20,20,'Park',NULL,NULL,'2016-01-12 15:28:18',NULL,NULL,'0'),(21,21,'Selline',NULL,NULL,'2016-01-12 15:28:39',NULL,NULL,'0'),(22,22,'Punane Moon',NULL,NULL,'2016-01-12 15:29:27',NULL,NULL,'0'),(23,23,'Ossharimashita ja homme',NULL,NULL,'2016-01-12 15:29:48',NULL,NULL,'0'),(24,24,'Homme',NULL,NULL,'2016-01-12 15:30:09',NULL,NULL,'0'),(25,25,'Minna.',NULL,NULL,'2016-01-12 15:30:30',NULL,NULL,'0'),(26,26,'Otsharimashita',NULL,NULL,'2016-01-12 15:30:50',NULL,NULL,'0'),(27,27,'John ei ole Briti',NULL,NULL,'2016-01-12 15:32:29',NULL,NULL,'0'),(28,28,'John on Briti?',NULL,NULL,'2016-01-12 15:32:49',NULL,NULL,'0'),(29,29,'Ma lähen kinno mu vend.',NULL,NULL,'2016-01-12 15:33:12',NULL,NULL,'0'),(30,30,'Käisin John ja filmi teatrid.',NULL,NULL,'2016-01-12 15:33:33',NULL,NULL,'0'),(31,31,'Ma lähen John ja filmi teatrid.',NULL,NULL,'2016-01-12 15:33:54',NULL,NULL,'0'),(32,32,'Ma lähen kinno jalgratas.',NULL,NULL,'2016-01-12 15:34:12',NULL,NULL,'0'),(33,33,'John läks kinno bike?',NULL,NULL,'2016-01-12 15:34:31',NULL,NULL,'0'),(34,34,'John läks kinno bike',NULL,NULL,'2016-01-12 15:34:54',NULL,NULL,'0'),(35,36,'John läheb kinno bike?',NULL,NULL,'2016-01-12 15:40:01',NULL,NULL,'0'),(36,37,'John läheb kinno bike',NULL,NULL,'2016-01-12 15:40:22',NULL,NULL,'0'),(37,38,'Ma ei käinud kinos bike John?',NULL,NULL,'2016-01-12 15:40:45',NULL,NULL,'0'),(38,39,'Ei John käinud kinos jalgrattaga',NULL,NULL,'2016-01-12 15:41:05',NULL,NULL,'0'),(39,40,'John lähed kinno bike',NULL,NULL,'2016-01-12 15:41:24',NULL,NULL,'0'),(40,41,'John lähed kinno jalgrattaga',NULL,NULL,'2016-01-12 15:41:45',NULL,NULL,'0'),(41,42,'John',NULL,NULL,'2016-01-12 15:42:04',NULL,NULL,'0');
/*!40000 ALTER TABLE `dict_et` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_eu`
--

DROP TABLE IF EXISTS `dict_eu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_eu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_eu`
--

LOCK TABLES `dict_eu` WRITE;
/*!40000 ALTER TABLE `dict_eu` DISABLE KEYS */;
/*!40000 ALTER TABLE `dict_eu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_fa`
--

DROP TABLE IF EXISTS `dict_fa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_fa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_fa`
--

LOCK TABLES `dict_fa` WRITE;
/*!40000 ALTER TABLE `dict_fa` DISABLE KEYS */;
INSERT INTO `dict_fa` VALUES (1,1,'مهتاب',NULL,NULL,'2016-01-12 15:21:32',NULL,NULL,'0'),(2,2,'ماه جدید',NULL,NULL,'2016-01-12 15:21:57',NULL,NULL,'0'),(3,3,'جان بریتانیا است.',NULL,NULL,'2016-01-12 15:22:20',NULL,NULL,'0'),(4,4,'سینما با برادرم رفتم.',NULL,NULL,'2016-01-12 15:22:39',NULL,NULL,'0'),(5,5,'رفتم به سینما بر روی دوچرخه',NULL,NULL,'2016-01-12 15:23:02',NULL,NULL,'0'),(6,6,'جان',NULL,NULL,'2016-01-12 15:23:23',NULL,NULL,'0'),(7,7,'بریتانیایی ها',NULL,NULL,'2016-01-12 15:23:44',NULL,NULL,'0'),(8,8,'من',NULL,NULL,'2016-01-12 15:24:05',NULL,NULL,'0'),(9,9,'برادر من',NULL,NULL,'2016-01-12 15:24:29',NULL,NULL,'0'),(10,10,'سینما',NULL,NULL,'2016-01-12 15:24:48',NULL,NULL,'0'),(11,11,'رفتم',NULL,NULL,'2016-01-12 15:25:11',NULL,NULL,'0'),(12,12,'دوچرخه',NULL,NULL,'2016-01-12 15:25:31',NULL,NULL,'0'),(13,13,'ماه گرفتگی',NULL,NULL,'2016-01-12 15:25:51',NULL,NULL,'0'),(14,14,'برادرم دکتر است.',NULL,NULL,'2016-01-12 15:26:15',NULL,NULL,'0'),(15,15,'دوچرخه را متوقف',NULL,NULL,'2016-01-12 15:26:35',NULL,NULL,'0'),(16,16,'زیارتگاه ها و پارک ها.',NULL,NULL,'2016-01-12 15:26:52',NULL,NULL,'0'),(17,17,'دکتر',NULL,NULL,'2016-01-12 15:27:14',NULL,NULL,'0'),(18,18,'بایست',NULL,NULL,'2016-01-12 15:27:33',NULL,NULL,'0'),(19,19,'حرم',NULL,NULL,'2016-01-12 15:27:57',NULL,NULL,'0'),(20,20,'پارک',NULL,NULL,'2016-01-12 15:28:18',NULL,NULL,'0'),(21,21,'چنین',NULL,NULL,'2016-01-12 15:28:39',NULL,NULL,'0'),(22,22,'ماه قرمز',NULL,NULL,'2016-01-12 15:29:27',NULL,NULL,'0'),(23,23,'Ossharimashita و فردا',NULL,NULL,'2016-01-12 15:29:48',NULL,NULL,'0'),(24,24,'فردا',NULL,NULL,'2016-01-12 15:30:09',NULL,NULL,'0'),(25,25,'بروید.',NULL,NULL,'2016-01-12 15:30:30',NULL,NULL,'0'),(26,26,'Otsharimashita',NULL,NULL,'2016-01-12 15:30:50',NULL,NULL,'0'),(27,27,'جان بریتانیا است',NULL,NULL,'2016-01-12 15:32:29',NULL,NULL,'0'),(28,28,'جان بریتانیا است؟',NULL,NULL,'2016-01-12 15:32:49',NULL,NULL,'0'),(29,29,'من رفتن به سینما با برادر من.',NULL,NULL,'2016-01-12 15:33:12',NULL,NULL,'0'),(30,30,'به جان و سینما می رفتم.',NULL,NULL,'2016-01-12 15:33:33',NULL,NULL,'0'),(31,31,'من به جان و سینما بروید.',NULL,NULL,'2016-01-12 15:33:54',NULL,NULL,'0'),(32,32,'من رفتن به سینما در دوچرخه.',NULL,NULL,'2016-01-12 15:34:12',NULL,NULL,'0'),(33,33,'جان رفت به سینما بر روی دوچرخه?',NULL,NULL,'2016-01-12 15:34:31',NULL,NULL,'0'),(34,34,'رفت جان به سینما بر روی دوچرخه',NULL,NULL,'2016-01-12 15:34:54',NULL,NULL,'0'),(35,36,'جان به سینما بر روی دوچرخه می رود؟',NULL,NULL,'2016-01-12 15:40:01',NULL,NULL,'0'),(36,37,'جان به سینما به دوچرخه می رود',NULL,NULL,'2016-01-12 15:40:22',NULL,NULL,'0'),(37,38,'من به سینما بر روی دوچرخه جان رفتن نیست?',NULL,NULL,'2016-01-12 15:40:45',NULL,NULL,'0'),(38,39,'جان رفتن به سینما با دوچرخه نمی کنید؟',NULL,NULL,'2016-01-12 15:41:05',NULL,NULL,'0'),(39,40,'جان رفتن به سینما بر روی دوچرخه',NULL,NULL,'2016-01-12 15:41:24',NULL,NULL,'0'),(40,41,'جان رفتن به سینما با دوچرخه',NULL,NULL,'2016-01-12 15:41:45',NULL,NULL,'0'),(41,42,'جان',NULL,NULL,'2016-01-12 15:42:04',NULL,NULL,'0');
/*!40000 ALTER TABLE `dict_fa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_fi`
--

DROP TABLE IF EXISTS `dict_fi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_fi` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_fi`
--

LOCK TABLES `dict_fi` WRITE;
/*!40000 ALTER TABLE `dict_fi` DISABLE KEYS */;
INSERT INTO `dict_fi` VALUES (1,1,'Moonlight',NULL,NULL,'2016-01-12 15:21:32',NULL,NULL,'0'),(2,2,'Uusikuu',NULL,NULL,'2016-01-12 15:21:57',NULL,NULL,'0'),(3,3,'John on brittiläinen',NULL,NULL,'2016-01-12 15:22:20',NULL,NULL,'0'),(4,4,'Kävin elokuvissa minun veljeni.',NULL,NULL,'2016-01-12 15:22:39',NULL,NULL,'0'),(5,5,'Kävin elokuvissa pyörä',NULL,NULL,'2016-01-12 15:23:02',NULL,NULL,'0'),(6,6,'John',NULL,NULL,'2016-01-12 15:23:23',NULL,NULL,'0'),(7,7,'Britit',NULL,NULL,'2016-01-12 15:23:44',NULL,NULL,'0'),(8,8,'Olen',NULL,NULL,'2016-01-12 15:24:05',NULL,NULL,'0'),(9,9,'Veljeni',NULL,NULL,'2016-01-12 15:24:29',NULL,NULL,'0'),(10,10,'Elokuva',NULL,NULL,'2016-01-12 15:24:48',NULL,NULL,'0'),(11,11,'Menin',NULL,NULL,'2016-01-12 15:25:11',NULL,NULL,'0'),(12,12,'Pyörä',NULL,NULL,'2016-01-12 15:25:31',NULL,NULL,'0'),(13,13,'Kuunpimennys',NULL,NULL,'2016-01-12 15:25:51',NULL,NULL,'0'),(14,14,'Veljeni on lääkäri.',NULL,NULL,'2016-01-12 15:26:15',NULL,NULL,'0'),(15,15,'Älä lopeta pyörä',NULL,NULL,'2016-01-12 15:26:35',NULL,NULL,'0'),(16,16,'Pyhäköt ja puistot.',NULL,NULL,'2016-01-12 15:26:52',NULL,NULL,'0'),(17,17,'Lääkäri',NULL,NULL,'2016-01-12 15:27:14',NULL,NULL,'0'),(18,18,'Seis',NULL,NULL,'2016-01-12 15:27:33',NULL,NULL,'0'),(19,19,'Alttari',NULL,NULL,'2016-01-12 15:27:57',NULL,NULL,'0'),(20,20,'Park',NULL,NULL,'2016-01-12 15:28:18',NULL,NULL,'0'),(21,21,'Tällainen',NULL,NULL,'2016-01-12 15:28:39',NULL,NULL,'0'),(22,22,'Punainen kuu',NULL,NULL,'2016-01-12 15:29:27',NULL,NULL,'0'),(23,23,'Ossharimashita ja huomenna',NULL,NULL,'2016-01-12 15:29:48',NULL,NULL,'0'),(24,24,'Huomenna',NULL,NULL,'2016-01-12 15:30:09',NULL,NULL,'0'),(25,25,'Mene.',NULL,NULL,'2016-01-12 15:30:30',NULL,NULL,'0'),(26,26,'Otsharimashita',NULL,NULL,'2016-01-12 15:30:50',NULL,NULL,'0'),(27,27,'John ei ole Yhdistyneen kuningaskunnan',NULL,NULL,'2016-01-12 15:32:29',NULL,NULL,'0'),(28,28,'John on brittiläinen?',NULL,NULL,'2016-01-12 15:32:49',NULL,NULL,'0'),(29,29,'Menen elokuviin veljeni kanssa.',NULL,NULL,'2016-01-12 15:33:12',NULL,NULL,'0'),(30,30,'Menin John ja elokuvan teattereissa.',NULL,NULL,'2016-01-12 15:33:33',NULL,NULL,'0'),(31,31,'Menen John ja elokuvan teattereissa.',NULL,NULL,'2016-01-12 15:33:54',NULL,NULL,'0'),(32,32,'Menen elokuviin pyörällä.',NULL,NULL,'2016-01-12 15:34:12',NULL,NULL,'0'),(33,33,'John kävi elokuvissa pyörä?',NULL,NULL,'2016-01-12 15:34:31',NULL,NULL,'0'),(34,34,'John kävi elokuvissa pyörä',NULL,NULL,'2016-01-12 15:34:54',NULL,NULL,'0'),(35,36,'John jatkaa elokuva pyörä?',NULL,NULL,'2016-01-12 15:40:01',NULL,NULL,'0'),(36,37,'John jatkaa elokuva pyörä',NULL,NULL,'2016-01-12 15:40:22',NULL,NULL,'0'),(37,38,'En mene elokuvissa pyörä John?',NULL,NULL,'2016-01-12 15:40:45',NULL,NULL,'0'),(38,39,'John mene elokuvissa polkupyörällä',NULL,NULL,'2016-01-12 15:41:05',NULL,NULL,'0'),(39,40,'John mennä elokuviin pyörä',NULL,NULL,'2016-01-12 15:41:24',NULL,NULL,'0'),(40,41,'John mennä elokuviin polkupyörällä',NULL,NULL,'2016-01-12 15:41:45',NULL,NULL,'0'),(41,42,'John',NULL,NULL,'2016-01-12 15:42:04',NULL,NULL,'0');
/*!40000 ALTER TABLE `dict_fi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_fr`
--

DROP TABLE IF EXISTS `dict_fr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_fr` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_fr`
--

LOCK TABLES `dict_fr` WRITE;
/*!40000 ALTER TABLE `dict_fr` DISABLE KEYS */;
INSERT INTO `dict_fr` VALUES (1,1,'Clair de lune',NULL,NULL,'2016-01-12 15:21:32',NULL,NULL,'0'),(2,2,'Nouvelle lune',NULL,NULL,'2016-01-12 15:21:57',NULL,NULL,'0'),(3,3,'John est un Britannique',NULL,NULL,'2016-01-12 15:22:20',NULL,NULL,'0'),(4,4,'Je suis allé au cinéma avec mon frère.',NULL,NULL,'2016-01-12 15:22:39',NULL,NULL,'0'),(5,5,'Je suis allé au cinéma sur un vélo',NULL,NULL,'2016-01-12 15:23:02',NULL,NULL,'0'),(6,6,'John',NULL,NULL,'2016-01-12 15:23:23',NULL,NULL,'0'),(7,7,'Les britanniques',NULL,NULL,'2016-01-12 15:23:44',NULL,NULL,'0'),(8,8,'J\'ai',NULL,NULL,'2016-01-12 15:24:05',NULL,NULL,'0'),(9,9,'Mon frère',NULL,NULL,'2016-01-12 15:24:29',NULL,NULL,'0'),(10,10,'Cinéma',NULL,NULL,'2016-01-12 15:24:48',NULL,NULL,'0'),(11,11,'Je suis allé',NULL,NULL,'2016-01-12 15:25:11',NULL,NULL,'0'),(12,12,'Vélo',NULL,NULL,'2016-01-12 15:25:31',NULL,NULL,'0'),(13,13,'Éclipse lunaire',NULL,NULL,'2016-01-12 15:25:51',NULL,NULL,'0'),(14,14,'Mon frère est un médecin.',NULL,NULL,'2016-01-12 15:26:15',NULL,NULL,'0'),(15,15,'Ne vous arrêtez pas la moto',NULL,NULL,'2016-01-12 15:26:35',NULL,NULL,'0'),(16,16,'Parcs et sanctuaires.',NULL,NULL,'2016-01-12 15:26:52',NULL,NULL,'0'),(17,17,'Médecin',NULL,NULL,'2016-01-12 15:27:14',NULL,NULL,'0'),(18,18,'Arrêter',NULL,NULL,'2016-01-12 15:27:33',NULL,NULL,'0'),(19,19,'Sanctuaire',NULL,NULL,'2016-01-12 15:27:57',NULL,NULL,'0'),(20,20,'Parc',NULL,NULL,'2016-01-12 15:28:18',NULL,NULL,'0'),(21,21,'Ces',NULL,NULL,'2016-01-12 15:28:39',NULL,NULL,'0'),(22,22,'Lune rouge',NULL,NULL,'2016-01-12 15:29:27',NULL,NULL,'0'),(23,23,'Ossharimashita et demain',NULL,NULL,'2016-01-12 15:29:48',NULL,NULL,'0'),(24,24,'Demain',NULL,NULL,'2016-01-12 15:30:09',NULL,NULL,'0'),(25,25,'Aller.',NULL,NULL,'2016-01-12 15:30:30',NULL,NULL,'0'),(26,26,'Otsharimashita',NULL,NULL,'2016-01-12 15:30:50',NULL,NULL,'0'),(27,27,'John n\'est pas Britannique',NULL,NULL,'2016-01-12 15:32:29',NULL,NULL,'0'),(28,28,'John est Britannique ?',NULL,NULL,'2016-01-12 15:32:49',NULL,NULL,'0'),(29,29,'Je vais au cinéma avec mon frère.',NULL,NULL,'2016-01-12 15:33:12',NULL,NULL,'0'),(30,30,'Je suis allé à John et film théâtres.',NULL,NULL,'2016-01-12 15:33:33',NULL,NULL,'0'),(31,31,'Je vais à John et film théâtres.',NULL,NULL,'2016-01-12 15:33:54',NULL,NULL,'0'),(32,32,'Je vais au cinéma sur une bicyclette.',NULL,NULL,'2016-01-12 15:34:12',NULL,NULL,'0'),(33,33,'John est allé au cinéma sur un vélo ?',NULL,NULL,'2016-01-12 15:34:31',NULL,NULL,'0'),(34,34,'John est allé au cinéma sur un vélo',NULL,NULL,'2016-01-12 15:34:54',NULL,NULL,'0'),(35,36,'John va au cinéma, sur un vélo ?',NULL,NULL,'2016-01-12 15:40:01',NULL,NULL,'0'),(36,37,'John va au cinéma sur un vélo',NULL,NULL,'2016-01-12 15:40:22',NULL,NULL,'0'),(37,38,'Je ne suis pas au cinéma sur un vélo de John ?',NULL,NULL,'2016-01-12 15:40:45',NULL,NULL,'0'),(38,39,'John ne va pas au cinéma en vélo',NULL,NULL,'2016-01-12 15:41:05',NULL,NULL,'0'),(39,40,'John aller au cinéma, sur un vélo',NULL,NULL,'2016-01-12 15:41:24',NULL,NULL,'0'),(40,41,'John aller au cinéma à vélo',NULL,NULL,'2016-01-12 15:41:45',NULL,NULL,'0'),(41,42,'John',NULL,NULL,'2016-01-12 15:42:04',NULL,NULL,'0');
/*!40000 ALTER TABLE `dict_fr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_ga`
--

DROP TABLE IF EXISTS `dict_ga`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_ga` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_ga`
--

LOCK TABLES `dict_ga` WRITE;
/*!40000 ALTER TABLE `dict_ga` DISABLE KEYS */;
/*!40000 ALTER TABLE `dict_ga` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_gl`
--

DROP TABLE IF EXISTS `dict_gl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_gl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_gl`
--

LOCK TABLES `dict_gl` WRITE;
/*!40000 ALTER TABLE `dict_gl` DISABLE KEYS */;
/*!40000 ALTER TABLE `dict_gl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_gu`
--

DROP TABLE IF EXISTS `dict_gu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_gu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_gu`
--

LOCK TABLES `dict_gu` WRITE;
/*!40000 ALTER TABLE `dict_gu` DISABLE KEYS */;
/*!40000 ALTER TABLE `dict_gu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_he`
--

DROP TABLE IF EXISTS `dict_he`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_he` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_he`
--

LOCK TABLES `dict_he` WRITE;
/*!40000 ALTER TABLE `dict_he` DISABLE KEYS */;
/*!40000 ALTER TABLE `dict_he` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_hi`
--

DROP TABLE IF EXISTS `dict_hi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_hi` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_hi`
--

LOCK TABLES `dict_hi` WRITE;
/*!40000 ALTER TABLE `dict_hi` DISABLE KEYS */;
INSERT INTO `dict_hi` VALUES (1,1,'चाँदनी',NULL,NULL,'2016-01-12 15:21:32',NULL,NULL,'0'),(2,2,'नया चाँद',NULL,NULL,'2016-01-12 15:21:57',NULL,NULL,'0'),(3,3,'जॉन एक ब्रिटिश है',NULL,NULL,'2016-01-12 15:22:20',NULL,NULL,'0'),(4,4,'मैं अपने भाई के साथ सिनेमा के पास गया।',NULL,NULL,'2016-01-12 15:22:39',NULL,NULL,'0'),(5,5,'मैं एक मोटर साइकिल पर सिनेमा के पास गया',NULL,NULL,'2016-01-12 15:23:02',NULL,NULL,'0'),(6,6,'जॉन',NULL,NULL,'2016-01-12 15:23:23',NULL,NULL,'0'),(7,7,'ब्रिटिश लोग',NULL,NULL,'2016-01-12 15:23:44',NULL,NULL,'0'),(8,8,'मैं',NULL,NULL,'2016-01-12 15:24:05',NULL,NULL,'0'),(9,9,'मेरे भाई',NULL,NULL,'2016-01-12 15:24:29',NULL,NULL,'0'),(10,10,'सिनेमा',NULL,NULL,'2016-01-12 15:24:48',NULL,NULL,'0'),(11,11,'मैं चला गया',NULL,NULL,'2016-01-12 15:25:11',NULL,NULL,'0'),(12,12,'बाइक',NULL,NULL,'2016-01-12 15:25:31',NULL,NULL,'0'),(13,13,'चंद्र ग्रहण',NULL,NULL,'2016-01-12 15:25:51',NULL,NULL,'0'),(14,14,'मेरा भाई एक डॉक्टर है।',NULL,NULL,'2016-01-12 15:26:15',NULL,NULL,'0'),(15,15,'बाइक रोक नहीं',NULL,NULL,'2016-01-12 15:26:35',NULL,NULL,'0'),(16,16,'धार्मिक स्थलों और पार्क।',NULL,NULL,'2016-01-12 15:26:52',NULL,NULL,'0'),(17,17,'डॉक्टर',NULL,NULL,'2016-01-12 15:27:14',NULL,NULL,'0'),(18,18,'रुको',NULL,NULL,'2016-01-12 15:27:33',NULL,NULL,'0'),(19,19,'तीर्थ',NULL,NULL,'2016-01-12 15:27:57',NULL,NULL,'0'),(20,20,'पार्क',NULL,NULL,'2016-01-12 15:28:18',NULL,NULL,'0'),(21,21,'इस तरह',NULL,NULL,'2016-01-12 15:28:39',NULL,NULL,'0'),(22,22,'लाल चंद्रमा',NULL,NULL,'2016-01-12 15:29:27',NULL,NULL,'0'),(23,23,'Ossharimashita और कल',NULL,NULL,'2016-01-12 15:29:48',NULL,NULL,'0'),(24,24,'कल',NULL,NULL,'2016-01-12 15:30:09',NULL,NULL,'0'),(25,25,'जाओ।',NULL,NULL,'2016-01-12 15:30:30',NULL,NULL,'0'),(26,26,'Otsharimashita',NULL,NULL,'2016-01-12 15:30:50',NULL,NULL,'0'),(27,27,'जॉन ब्रिटिश नहीं है',NULL,NULL,'2016-01-12 15:32:29',NULL,NULL,'0'),(28,28,'जॉन ब्रिटिश है?',NULL,NULL,'2016-01-12 15:32:49',NULL,NULL,'0'),(29,29,'मैं अपने भाई के साथ सिनेमा करना जाओ।',NULL,NULL,'2016-01-12 15:33:12',NULL,NULL,'0'),(30,30,'मैं जॉन और फिल्म थिएटर के लिए चला गया।',NULL,NULL,'2016-01-12 15:33:33',NULL,NULL,'0'),(31,31,'मैं जॉन और फिल्म थिएटर करने के लिए जाओ।',NULL,NULL,'2016-01-12 15:33:54',NULL,NULL,'0'),(32,32,'मैं एक साइकिल पर सिनेमा करना जाओ।',NULL,NULL,'2016-01-12 15:34:12',NULL,NULL,'0'),(33,33,'जॉन को सिनेमा में एक मोटर साइकिल पर चला गया?',NULL,NULL,'2016-01-12 15:34:31',NULL,NULL,'0'),(34,34,'जॉन एक बाइक पर सिनेमा के पास गया',NULL,NULL,'2016-01-12 15:34:54',NULL,NULL,'0'),(35,36,'जॉन को सिनेमा में एक मोटर साइकिल पर चला जाता है?',NULL,NULL,'2016-01-12 15:40:01',NULL,NULL,'0'),(36,37,'जॉन को सिनेमा में एक मोटर साइकिल पर चला जाता है',NULL,NULL,'2016-01-12 15:40:22',NULL,NULL,'0'),(37,38,'मैं एक बाइक पर जॉन के सिनेमा के लिए जाना नहीं था?',NULL,NULL,'2016-01-12 15:40:45',NULL,NULL,'0'),(38,39,'जॉन के सिनेमा के लिए साइकिल से जाना था',NULL,NULL,'2016-01-12 15:41:05',NULL,NULL,'0'),(39,40,'जॉन एक बाइक पर सिनेमा करना जाओ',NULL,NULL,'2016-01-12 15:41:24',NULL,NULL,'0'),(40,41,'जॉन साइकिल से सिनेमा करना जाओ',NULL,NULL,'2016-01-12 15:41:45',NULL,NULL,'0'),(41,42,'जॉन',NULL,NULL,'2016-01-12 15:42:04',NULL,NULL,'0');
/*!40000 ALTER TABLE `dict_hi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_hr`
--

DROP TABLE IF EXISTS `dict_hr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_hr` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_hr`
--

LOCK TABLES `dict_hr` WRITE;
/*!40000 ALTER TABLE `dict_hr` DISABLE KEYS */;
/*!40000 ALTER TABLE `dict_hr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_ht`
--

DROP TABLE IF EXISTS `dict_ht`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_ht` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_ht`
--

LOCK TABLES `dict_ht` WRITE;
/*!40000 ALTER TABLE `dict_ht` DISABLE KEYS */;
INSERT INTO `dict_ht` VALUES (1,1,'Moonlight',NULL,NULL,'2016-01-12 15:21:32',NULL,NULL,'0'),(2,2,'Lalin',NULL,NULL,'2016-01-12 15:21:57',NULL,NULL,'0'),(3,3,'John se yon Britanik',NULL,NULL,'2016-01-12 15:22:20',NULL,NULL,'0'),(4,4,'Mwen te ale nan sal teyat la ak frè m\' lan.',NULL,NULL,'2016-01-12 15:22:39',NULL,NULL,'0'),(5,5,'Mwen te al sinema a sou yon bisiklèt',NULL,NULL,'2016-01-12 15:23:02',NULL,NULL,'0'),(6,6,'Jan Batis',NULL,NULL,'2016-01-12 15:23:23',NULL,NULL,'0'),(7,7,'Pèp Britanik',NULL,NULL,'2016-01-12 15:23:44',NULL,NULL,'0'),(8,8,'mwen',NULL,NULL,'2016-01-12 15:24:05',NULL,NULL,'0'),(9,9,'Frè m\' lan',NULL,NULL,'2016-01-12 15:24:29',NULL,NULL,'0'),(10,10,'Sal teyat',NULL,NULL,'2016-01-12 15:24:48',NULL,NULL,'0'),(11,11,'Mwen t\' ap',NULL,NULL,'2016-01-12 15:25:11',NULL,NULL,'0'),(12,12,'Bisiklèt',NULL,NULL,'2016-01-12 15:25:31',NULL,NULL,'0'),(13,13,'Lalin olidey Eclipse',NULL,NULL,'2016-01-12 15:25:51',NULL,NULL,'0'),(14,14,'Frè m\' lan se yon doktè.',NULL,NULL,'2016-01-12 15:26:15',NULL,NULL,'0'),(15,15,'Pa kanpe a bisiklèt',NULL,NULL,'2016-01-12 15:26:35',NULL,NULL,'0'),(16,16,'Portés ak pak.',NULL,NULL,'2016-01-12 15:26:52',NULL,NULL,'0'),(17,17,'Doktè',NULL,NULL,'2016-01-12 15:27:14',NULL,NULL,'0'),(18,18,'Rete',NULL,NULL,'2016-01-12 15:27:33',NULL,NULL,'0'),(19,19,'Mozole',NULL,NULL,'2016-01-12 15:27:57',NULL,NULL,'0'),(20,20,'Pak',NULL,NULL,'2016-01-12 15:28:18',NULL,NULL,'0'),(21,21,'Konsa',NULL,NULL,'2016-01-12 15:28:39',NULL,NULL,'0'),(22,22,'Lalin wouj',NULL,NULL,'2016-01-12 15:29:27',NULL,NULL,'0'),(23,23,'Ossharimashita, demen',NULL,NULL,'2016-01-12 15:29:48',NULL,NULL,'0'),(24,24,'Demen',NULL,NULL,'2016-01-12 15:30:09',NULL,NULL,'0'),(25,25,'Ale non.',NULL,NULL,'2016-01-12 15:30:30',NULL,NULL,'0'),(26,26,'Otsharimashita',NULL,NULL,'2016-01-12 15:30:50',NULL,NULL,'0'),(27,27,'John pa Anglè',NULL,NULL,'2016-01-12 15:32:29',NULL,NULL,'0'),(28,28,'John Britanik lan?',NULL,NULL,'2016-01-12 15:32:49',NULL,NULL,'0'),(29,29,'Mwen ale nan sal teyat la ak frè m\' lan.',NULL,NULL,'2016-01-12 15:33:12',NULL,NULL,'0'),(30,30,'Mwen te ale nan jan ak fim théâtres.',NULL,NULL,'2016-01-12 15:33:33',NULL,NULL,'0'),(31,31,'M ale jan ak fim théâtres.',NULL,NULL,'2016-01-12 15:33:54',NULL,NULL,'0'),(32,32,'Mwen ale nan sal teyat la sou yon bisiklèt.',NULL,NULL,'2016-01-12 15:34:12',NULL,NULL,'0'),(33,33,'Jan Batis te ale nan sal teyat la sou yon bisiklèt?',NULL,NULL,'2016-01-12 15:34:31',NULL,NULL,'0'),(34,34,'Jan Batis te ale nan sal teyat la sou yon bisiklèt',NULL,NULL,'2016-01-12 15:34:54',NULL,NULL,'0'),(35,36,'Jan Batis ale nan sal teyat la sou yon bisiklèt?',NULL,NULL,'2016-01-12 15:40:01',NULL,NULL,'0'),(36,37,'Jan Batis ale nan sal teyat la sou yon bisiklèt',NULL,NULL,'2016-01-12 15:40:22',NULL,NULL,'0'),(37,38,'M pa t al pou sal teyat la sou yon bisiklèt Jan Batis?',NULL,NULL,'2016-01-12 15:40:45',NULL,NULL,'0'),(38,39,'Pa t Jan Batis pase pou sal teyat a bisiklèt',NULL,NULL,'2016-01-12 15:41:05',NULL,NULL,'0'),(39,40,'Jan Batis ale nan sal teyat la sou yon bisiklèt',NULL,NULL,'2016-01-12 15:41:24',NULL,NULL,'0'),(40,41,'Jan Batis ale nan sal teyat la pa bisiklèt',NULL,NULL,'2016-01-12 15:41:45',NULL,NULL,'0'),(41,42,'Jan Batis',NULL,NULL,'2016-01-12 15:42:04',NULL,NULL,'0');
/*!40000 ALTER TABLE `dict_ht` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_hu`
--

DROP TABLE IF EXISTS `dict_hu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_hu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_hu`
--

LOCK TABLES `dict_hu` WRITE;
/*!40000 ALTER TABLE `dict_hu` DISABLE KEYS */;
INSERT INTO `dict_hu` VALUES (1,1,'Holdfény',NULL,NULL,'2016-01-12 15:21:32',NULL,NULL,'0'),(2,2,'Újhold',NULL,NULL,'2016-01-12 15:21:57',NULL,NULL,'0'),(3,3,'John egy brit',NULL,NULL,'2016-01-12 15:22:20',NULL,NULL,'0'),(4,4,'Mentem a moziba, a bátyámmal.',NULL,NULL,'2016-01-12 15:22:39',NULL,NULL,'0'),(5,5,'Elmentem a mozi egy kerékpár',NULL,NULL,'2016-01-12 15:23:02',NULL,NULL,'0'),(6,6,'John',NULL,NULL,'2016-01-12 15:23:23',NULL,NULL,'0'),(7,7,'Brit emberek',NULL,NULL,'2016-01-12 15:23:44',NULL,NULL,'0'),(8,8,'Én',NULL,NULL,'2016-01-12 15:24:05',NULL,NULL,'0'),(9,9,'A bátyám',NULL,NULL,'2016-01-12 15:24:29',NULL,NULL,'0'),(10,10,'Mozi',NULL,NULL,'2016-01-12 15:24:48',NULL,NULL,'0'),(11,11,'Mentem',NULL,NULL,'2016-01-12 15:25:11',NULL,NULL,'0'),(12,12,'Kerékpár',NULL,NULL,'2016-01-12 15:25:31',NULL,NULL,'0'),(13,13,'Holdfogyatkozás',NULL,NULL,'2016-01-12 15:25:51',NULL,NULL,'0'),(14,14,'A bátyám egy orvos.',NULL,NULL,'2016-01-12 15:26:15',NULL,NULL,'0'),(15,15,'Ne hagyja abba a kerékpár',NULL,NULL,'2016-01-12 15:26:35',NULL,NULL,'0'),(16,16,'Szentélyek és parkok.',NULL,NULL,'2016-01-12 15:26:52',NULL,NULL,'0'),(17,17,'Orvos',NULL,NULL,'2016-01-12 15:27:14',NULL,NULL,'0'),(18,18,'állj',NULL,NULL,'2016-01-12 15:27:33',NULL,NULL,'0'),(19,19,'Szentély',NULL,NULL,'2016-01-12 15:27:57',NULL,NULL,'0'),(20,20,'Park',NULL,NULL,'2016-01-12 15:28:18',NULL,NULL,'0'),(21,21,'Ilyen',NULL,NULL,'2016-01-12 15:28:39',NULL,NULL,'0'),(22,22,'Vörös Hold',NULL,NULL,'2016-01-12 15:29:27',NULL,NULL,'0'),(23,23,'Ossharimashita és a holnap',NULL,NULL,'2016-01-12 15:29:48',NULL,NULL,'0'),(24,24,'Holnap',NULL,NULL,'2016-01-12 15:30:09',NULL,NULL,'0'),(25,25,'Megy.',NULL,NULL,'2016-01-12 15:30:30',NULL,NULL,'0'),(26,26,'Otsharimashita',NULL,NULL,'2016-01-12 15:30:50',NULL,NULL,'0'),(27,27,'John nincs brit',NULL,NULL,'2016-01-12 15:32:29',NULL,NULL,'0'),(28,28,'John a brit?',NULL,NULL,'2016-01-12 15:32:49',NULL,NULL,'0'),(29,29,'Megyek a moziba, a bátyámmal.',NULL,NULL,'2016-01-12 15:33:12',NULL,NULL,'0'),(30,30,'Mentem, hogy John és moziktól.',NULL,NULL,'2016-01-12 15:33:33',NULL,NULL,'0'),(31,31,'Megyek, hogy John és moziktól.',NULL,NULL,'2016-01-12 15:33:54',NULL,NULL,'0'),(32,32,'A kerékpár a moziba megy.',NULL,NULL,'2016-01-12 15:34:12',NULL,NULL,'0'),(33,33,'John elment a mozi egy biciklit?',NULL,NULL,'2016-01-12 15:34:31',NULL,NULL,'0'),(34,34,'John elment a mozi egy kerékpár',NULL,NULL,'2016-01-12 15:34:54',NULL,NULL,'0'),(35,36,'John megy a mozi a motor?',NULL,NULL,'2016-01-12 15:40:01',NULL,NULL,'0'),(36,37,'John megy a mozi a motor',NULL,NULL,'2016-01-12 15:40:22',NULL,NULL,'0'),(37,38,'Nem megy a mozi-John kerékpárt?',NULL,NULL,'2016-01-12 15:40:45',NULL,NULL,'0'),(38,39,'Nem John megy a mozi, kerékpárral',NULL,NULL,'2016-01-12 15:41:05',NULL,NULL,'0'),(39,40,'John megy a mozi egy kerékpáros',NULL,NULL,'2016-01-12 15:41:24',NULL,NULL,'0'),(40,41,'John menni a moziba, kerékpárral',NULL,NULL,'2016-01-12 15:41:45',NULL,NULL,'0'),(41,42,'John',NULL,NULL,'2016-01-12 15:42:04',NULL,NULL,'0');
/*!40000 ALTER TABLE `dict_hu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_hy`
--

DROP TABLE IF EXISTS `dict_hy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_hy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_hy`
--

LOCK TABLES `dict_hy` WRITE;
/*!40000 ALTER TABLE `dict_hy` DISABLE KEYS */;
/*!40000 ALTER TABLE `dict_hy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_id`
--

DROP TABLE IF EXISTS `dict_id`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_id` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_id`
--

LOCK TABLES `dict_id` WRITE;
/*!40000 ALTER TABLE `dict_id` DISABLE KEYS */;
INSERT INTO `dict_id` VALUES (1,1,'Moonlight',NULL,NULL,'2016-01-12 15:21:32',NULL,NULL,'0'),(2,2,'Bulan baru',NULL,NULL,'2016-01-12 15:21:57',NULL,NULL,'0'),(3,3,'Yohanes adalah Inggris',NULL,NULL,'2016-01-12 15:22:20',NULL,NULL,'0'),(4,4,'Aku pergi ke bioskop dengan adikku.',NULL,NULL,'2016-01-12 15:22:39',NULL,NULL,'0'),(5,5,'Aku pergi ke bioskop pada sepeda',NULL,NULL,'2016-01-12 15:23:02',NULL,NULL,'0'),(6,6,'John',NULL,NULL,'2016-01-12 15:23:23',NULL,NULL,'0'),(7,7,'Orang-orang Inggris',NULL,NULL,'2016-01-12 15:23:44',NULL,NULL,'0'),(8,8,'Saya',NULL,NULL,'2016-01-12 15:24:05',NULL,NULL,'0'),(9,9,'Adikku',NULL,NULL,'2016-01-12 15:24:29',NULL,NULL,'0'),(10,10,'Bioskop',NULL,NULL,'2016-01-12 15:24:48',NULL,NULL,'0'),(11,11,'Aku pergi',NULL,NULL,'2016-01-12 15:25:11',NULL,NULL,'0'),(12,12,'Sepeda',NULL,NULL,'2016-01-12 15:25:31',NULL,NULL,'0'),(13,13,'Lunar Eclipse',NULL,NULL,'2016-01-12 15:25:51',NULL,NULL,'0'),(14,14,'Kakak saya adalah seorang dokter.',NULL,NULL,'2016-01-12 15:26:15',NULL,NULL,'0'),(15,15,'Jangan berhenti Sepeda',NULL,NULL,'2016-01-12 15:26:35',NULL,NULL,'0'),(16,16,'Kuil dan taman.',NULL,NULL,'2016-01-12 15:26:52',NULL,NULL,'0'),(17,17,'Dokter',NULL,NULL,'2016-01-12 15:27:14',NULL,NULL,'0'),(18,18,'Stop',NULL,NULL,'2016-01-12 15:27:33',NULL,NULL,'0'),(19,19,'Kuil',NULL,NULL,'2016-01-12 15:27:57',NULL,NULL,'0'),(20,20,'Park',NULL,NULL,'2016-01-12 15:28:18',NULL,NULL,'0'),(21,21,'Seperti',NULL,NULL,'2016-01-12 15:28:39',NULL,NULL,'0'),(22,22,'Bulan merah',NULL,NULL,'2016-01-12 15:29:27',NULL,NULL,'0'),(23,23,'Ossharimashita dan besok',NULL,NULL,'2016-01-12 15:29:48',NULL,NULL,'0'),(24,24,'Besok',NULL,NULL,'2016-01-12 15:30:09',NULL,NULL,'0'),(25,25,'Pergi.',NULL,NULL,'2016-01-12 15:30:30',NULL,NULL,'0'),(26,26,'Otsharimashita',NULL,NULL,'2016-01-12 15:30:50',NULL,NULL,'0'),(27,27,'Yohanes tidak Inggris',NULL,NULL,'2016-01-12 15:32:29',NULL,NULL,'0'),(28,28,'Yohanes Inggris?',NULL,NULL,'2016-01-12 15:32:49',NULL,NULL,'0'),(29,29,'Aku pergi ke bioskop dengan adikku.',NULL,NULL,'2016-01-12 15:33:12',NULL,NULL,'0'),(30,30,'Aku pergi ke John dan bioskop.',NULL,NULL,'2016-01-12 15:33:33',NULL,NULL,'0'),(31,31,'Aku pergi ke John dan bioskop.',NULL,NULL,'2016-01-12 15:33:54',NULL,NULL,'0'),(32,32,'Aku pergi ke bioskop di sepeda.',NULL,NULL,'2016-01-12 15:34:12',NULL,NULL,'0'),(33,33,'Yohanes pergi ke bioskop di Sepeda?',NULL,NULL,'2016-01-12 15:34:31',NULL,NULL,'0'),(34,34,'Yohanes pergi ke bioskop pada sepeda',NULL,NULL,'2016-01-12 15:34:54',NULL,NULL,'0'),(35,36,'Yohanes pergi ke bioskop di Sepeda?',NULL,NULL,'2016-01-12 15:40:01',NULL,NULL,'0'),(36,37,'Yohanes pergi ke bioskop pada sepeda',NULL,NULL,'2016-01-12 15:40:22',NULL,NULL,'0'),(37,38,'Aku tidak pergi ke bioskop pada sepeda John?',NULL,NULL,'2016-01-12 15:40:45',NULL,NULL,'0'),(38,39,'Tidak Yohanes pergi ke bioskop dengan sepeda',NULL,NULL,'2016-01-12 15:41:05',NULL,NULL,'0'),(39,40,'Yohanes pergi ke bioskop pada sepeda',NULL,NULL,'2016-01-12 15:41:24',NULL,NULL,'0'),(40,41,'Yohanes pergi ke bioskop dengan sepeda',NULL,NULL,'2016-01-12 15:41:45',NULL,NULL,'0'),(41,42,'John',NULL,NULL,'2016-01-12 15:42:04',NULL,NULL,'0');
/*!40000 ALTER TABLE `dict_id` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_is`
--

DROP TABLE IF EXISTS `dict_is`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_is` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_is`
--

LOCK TABLES `dict_is` WRITE;
/*!40000 ALTER TABLE `dict_is` DISABLE KEYS */;
/*!40000 ALTER TABLE `dict_is` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_it`
--

DROP TABLE IF EXISTS `dict_it`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_it` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_it`
--

LOCK TABLES `dict_it` WRITE;
/*!40000 ALTER TABLE `dict_it` DISABLE KEYS */;
INSERT INTO `dict_it` VALUES (1,1,'Al chiaro di luna',NULL,NULL,'2016-01-12 15:21:32',NULL,NULL,'0'),(2,2,'Luna nuova',NULL,NULL,'2016-01-12 15:21:57',NULL,NULL,'0'),(3,3,'John è un britannico',NULL,NULL,'2016-01-12 15:22:20',NULL,NULL,'0'),(4,4,'Sono andato al cinema con mio fratello.',NULL,NULL,'2016-01-12 15:22:39',NULL,NULL,'0'),(5,5,'Sono andato al cinema in bicicletta',NULL,NULL,'2016-01-12 15:23:02',NULL,NULL,'0'),(6,6,'John',NULL,NULL,'2016-01-12 15:23:23',NULL,NULL,'0'),(7,7,'Popolo britannico',NULL,NULL,'2016-01-12 15:23:44',NULL,NULL,'0'),(8,8,'Ho',NULL,NULL,'2016-01-12 15:24:05',NULL,NULL,'0'),(9,9,'Mio fratello',NULL,NULL,'2016-01-12 15:24:29',NULL,NULL,'0'),(10,10,'Cinema',NULL,NULL,'2016-01-12 15:24:48',NULL,NULL,'0'),(11,11,'Sono andato',NULL,NULL,'2016-01-12 15:25:11',NULL,NULL,'0'),(12,12,'Bici',NULL,NULL,'2016-01-12 15:25:31',NULL,NULL,'0'),(13,13,'Eclissi lunare',NULL,NULL,'2016-01-12 15:25:51',NULL,NULL,'0'),(14,14,'Mio fratello è un medico.',NULL,NULL,'2016-01-12 15:26:15',NULL,NULL,'0'),(15,15,'Non fermare la moto',NULL,NULL,'2016-01-12 15:26:35',NULL,NULL,'0'),(16,16,'Santuari e parchi.',NULL,NULL,'2016-01-12 15:26:52',NULL,NULL,'0'),(17,17,'Medico',NULL,NULL,'2016-01-12 15:27:14',NULL,NULL,'0'),(18,18,'Fermata',NULL,NULL,'2016-01-12 15:27:33',NULL,NULL,'0'),(19,19,'Santuario',NULL,NULL,'2016-01-12 15:27:57',NULL,NULL,'0'),(20,20,'Parco',NULL,NULL,'2016-01-12 15:28:18',NULL,NULL,'0'),(21,21,'Tali',NULL,NULL,'2016-01-12 15:28:39',NULL,NULL,'0'),(22,22,'Luna Rossa',NULL,NULL,'2016-01-12 15:29:27',NULL,NULL,'0'),(23,23,'Ossharimashita e domani',NULL,NULL,'2016-01-12 15:29:48',NULL,NULL,'0'),(24,24,'Domani',NULL,NULL,'2016-01-12 15:30:09',NULL,NULL,'0'),(25,25,'Andare.',NULL,NULL,'2016-01-12 15:30:30',NULL,NULL,'0'),(26,26,'Otsharimashita',NULL,NULL,'2016-01-12 15:30:50',NULL,NULL,'0'),(27,27,'John non è britannico',NULL,NULL,'2016-01-12 15:32:29',NULL,NULL,'0'),(28,28,'John è inglese?',NULL,NULL,'2016-01-12 15:32:49',NULL,NULL,'0'),(29,29,'Vado al cinema con mio fratello.',NULL,NULL,'2016-01-12 15:33:12',NULL,NULL,'0'),(30,30,'Sono andato a John e cinema.',NULL,NULL,'2016-01-12 15:33:33',NULL,NULL,'0'),(31,31,'Vado a John e cinema.',NULL,NULL,'2016-01-12 15:33:54',NULL,NULL,'0'),(32,32,'Vado al cinema in bicicletta.',NULL,NULL,'2016-01-12 15:34:12',NULL,NULL,'0'),(33,33,'John è andato al cinema in bicicletta?',NULL,NULL,'2016-01-12 15:34:31',NULL,NULL,'0'),(34,34,'John è andato al cinema in bicicletta',NULL,NULL,'2016-01-12 15:34:54',NULL,NULL,'0'),(35,36,'John va al cinema su una bici?',NULL,NULL,'2016-01-12 15:40:01',NULL,NULL,'0'),(36,37,'John va al cinema su una bici',NULL,NULL,'2016-01-12 15:40:22',NULL,NULL,'0'),(37,38,'Non sono andato al cinema in bicicletta John?',NULL,NULL,'2016-01-12 15:40:45',NULL,NULL,'0'),(38,39,'John non andare al cinema in bicicletta',NULL,NULL,'2016-01-12 15:41:05',NULL,NULL,'0'),(39,40,'John andare al cinema in bicicletta',NULL,NULL,'2016-01-12 15:41:24',NULL,NULL,'0'),(40,41,'John andare al cinema in bicicletta',NULL,NULL,'2016-01-12 15:41:45',NULL,NULL,'0'),(41,42,'John',NULL,NULL,'2016-01-12 15:42:04',NULL,NULL,'0');
/*!40000 ALTER TABLE `dict_it` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_iw`
--

DROP TABLE IF EXISTS `dict_iw`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_iw` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_iw`
--

LOCK TABLES `dict_iw` WRITE;
/*!40000 ALTER TABLE `dict_iw` DISABLE KEYS */;
/*!40000 ALTER TABLE `dict_iw` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_ja`
--

DROP TABLE IF EXISTS `dict_ja`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_ja` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_ja`
--

LOCK TABLES `dict_ja` WRITE;
/*!40000 ALTER TABLE `dict_ja` DISABLE KEYS */;
INSERT INTO `dict_ja` VALUES (1,'月光',NULL,NULL,'2016-01-12 15:21:13',NULL,NULL,'0'),(2,'新月',NULL,NULL,'2016-01-12 15:21:33',NULL,NULL,'0'),(3,'ジョンさんはイギリス人です',NULL,NULL,'2016-01-12 15:21:57',NULL,NULL,'0'),(4,'私は兄と映画館へ行きました',NULL,NULL,'2016-01-12 15:22:20',NULL,NULL,'0'),(5,'私は自転車で映画館へ行きました',NULL,NULL,'2016-01-12 15:22:40',NULL,NULL,'0'),(6,'ジョン',NULL,NULL,'2016-01-12 15:23:02',NULL,NULL,'0'),(7,'イギリス人',NULL,NULL,'2016-01-12 15:23:23',NULL,NULL,'0'),(8,'私',NULL,NULL,'2016-01-12 15:23:44',NULL,NULL,'0'),(9,'兄',NULL,NULL,'2016-01-12 15:24:05',NULL,NULL,'0'),(10,'映画館',NULL,NULL,'2016-01-12 15:24:29',NULL,NULL,'0'),(11,'行きました',NULL,NULL,'2016-01-12 15:24:49',NULL,NULL,'0'),(12,'自転車',NULL,NULL,'2016-01-12 15:25:12',NULL,NULL,'0'),(13,'日食月',NULL,NULL,'2016-01-12 15:25:31',NULL,NULL,'0'),(14,'兄は医者です',NULL,NULL,'2016-01-12 15:25:51',NULL,NULL,'0'),(15,'自転車を止めてはいけません',NULL,NULL,'2016-01-12 15:26:15',NULL,NULL,'0'),(16,'神社や公園にいった',NULL,NULL,'2016-01-12 15:26:35',NULL,NULL,'0'),(17,'医者',NULL,NULL,'2016-01-12 15:26:52',NULL,NULL,'0'),(18,'止めて',NULL,NULL,'2016-01-12 15:27:14',NULL,NULL,'0'),(19,'神社',NULL,NULL,'2016-01-12 15:27:34',NULL,NULL,'0'),(20,'公園',NULL,NULL,'2016-01-12 15:27:57',NULL,NULL,'0'),(21,'いった',NULL,NULL,'2016-01-12 15:28:18',NULL,NULL,'0'),(22,'赤い月',NULL,NULL,'2016-01-12 15:28:39',NULL,NULL,'0'),(23,'明日行きますとおっしゃりました',NULL,NULL,'2016-01-12 15:29:28',NULL,NULL,'0'),(24,'明日',NULL,NULL,'2016-01-12 15:29:49',NULL,NULL,'0'),(25,'行きます',NULL,NULL,'2016-01-12 15:30:09',NULL,NULL,'0'),(26,'おっしゃりました',NULL,NULL,'2016-01-12 15:30:30',NULL,NULL,'0'),(27,'ジョンさんはイギリス人ではありません',NULL,NULL,'2016-01-12 15:32:13',NULL,NULL,'0'),(28,'ジョンさんはイギリス人ですか',NULL,NULL,'2016-01-12 15:32:31',NULL,NULL,'0'),(29,'私は兄と映画館へ行きます',NULL,NULL,'2016-01-12 15:32:50',NULL,NULL,'0'),(30,'私はジョンさんと映画館へ行きました',NULL,NULL,'2016-01-12 15:33:13',NULL,NULL,'0'),(31,'私はジョンさんと映画館へ行きます',NULL,NULL,'2016-01-12 15:33:33',NULL,NULL,'0'),(32,'私は自転車で映画館へ行きます',NULL,NULL,'2016-01-12 15:33:55',NULL,NULL,'0'),(33,'ジョンさんは自転車で映画館へ行きましたか',NULL,NULL,'2016-01-12 15:34:13',NULL,NULL,'0'),(34,'ジョンさんは自転車で映画館へ行きました',NULL,NULL,'2016-01-12 15:34:32',NULL,NULL,'0'),(36,'ジョンさんは自転車で映画館へ行きますか',NULL,NULL,'2016-01-12 15:39:42',NULL,NULL,'0'),(37,'ジョンさんは自転車で映画館へ行きます',NULL,NULL,'2016-01-12 15:40:03',NULL,NULL,'0'),(38,'ジョンさんは自転車で映画館へ行きませんでしたか',NULL,NULL,'2016-01-12 15:40:23',NULL,NULL,'0'),(39,'ジョンさんは自転車で映画館へ行きませんでした',NULL,NULL,'2016-01-12 15:40:46',NULL,NULL,'0'),(40,'ジョンさんは自転車で映画館へ行きませんか',NULL,NULL,'2016-01-12 15:41:06',NULL,NULL,'0'),(41,'ジョンさんは自転車で映画館へ行きません',NULL,NULL,'2016-01-12 15:41:26',NULL,NULL,'0'),(42,'ジョンさん',NULL,NULL,'2016-01-12 15:41:46',NULL,NULL,'0');
/*!40000 ALTER TABLE `dict_ja` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_ka`
--

DROP TABLE IF EXISTS `dict_ka`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_ka` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_ka`
--

LOCK TABLES `dict_ka` WRITE;
/*!40000 ALTER TABLE `dict_ka` DISABLE KEYS */;
/*!40000 ALTER TABLE `dict_ka` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_kn`
--

DROP TABLE IF EXISTS `dict_kn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_kn` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_kn`
--

LOCK TABLES `dict_kn` WRITE;
/*!40000 ALTER TABLE `dict_kn` DISABLE KEYS */;
/*!40000 ALTER TABLE `dict_kn` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_ko`
--

DROP TABLE IF EXISTS `dict_ko`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_ko` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_ko`
--

LOCK TABLES `dict_ko` WRITE;
/*!40000 ALTER TABLE `dict_ko` DISABLE KEYS */;
INSERT INTO `dict_ko` VALUES (1,1,'달빛',NULL,NULL,'2016-01-12 15:21:32',NULL,NULL,'0'),(2,2,'뉴 문',NULL,NULL,'2016-01-12 15:21:57',NULL,NULL,'0'),(3,3,'존은 영국',NULL,NULL,'2016-01-12 15:22:20',NULL,NULL,'0'),(4,4,'나는 내 동생과 함께 영화관에 갔다.',NULL,NULL,'2016-01-12 15:22:39',NULL,NULL,'0'),(5,5,'나는 자전거에 영화관에가 서',NULL,NULL,'2016-01-12 15:23:02',NULL,NULL,'0'),(6,6,'존',NULL,NULL,'2016-01-12 15:23:23',NULL,NULL,'0'),(7,7,'영국 사람들',NULL,NULL,'2016-01-12 15:23:44',NULL,NULL,'0'),(8,8,'난',NULL,NULL,'2016-01-12 15:24:05',NULL,NULL,'0'),(9,9,'내 동생',NULL,NULL,'2016-01-12 15:24:29',NULL,NULL,'0'),(10,10,'영화관',NULL,NULL,'2016-01-12 15:24:48',NULL,NULL,'0'),(11,11,'내가 서',NULL,NULL,'2016-01-12 15:25:11',NULL,NULL,'0'),(12,12,'자전거',NULL,NULL,'2016-01-12 15:25:31',NULL,NULL,'0'),(13,13,'음력 이클립스',NULL,NULL,'2016-01-12 15:25:51',NULL,NULL,'0'),(14,14,'내 동생 의사 이다입니다.',NULL,NULL,'2016-01-12 15:26:15',NULL,NULL,'0'),(15,15,'자전거를 중지 하지 마십시오',NULL,NULL,'2016-01-12 15:26:35',NULL,NULL,'0'),(16,16,'신사와 공원',NULL,NULL,'2016-01-12 15:26:52',NULL,NULL,'0'),(17,17,'의사',NULL,NULL,'2016-01-12 15:27:14',NULL,NULL,'0'),(18,18,'중지',NULL,NULL,'2016-01-12 15:27:33',NULL,NULL,'0'),(19,19,'신사',NULL,NULL,'2016-01-12 15:27:57',NULL,NULL,'0'),(20,20,'공원',NULL,NULL,'2016-01-12 15:28:18',NULL,NULL,'0'),(21,21,'이러한',NULL,NULL,'2016-01-12 15:28:39',NULL,NULL,'0'),(22,22,'붉은 달',NULL,NULL,'2016-01-12 15:29:27',NULL,NULL,'0'),(23,23,'Ossharimashita 및 내일',NULL,NULL,'2016-01-12 15:29:48',NULL,NULL,'0'),(24,24,'내일',NULL,NULL,'2016-01-12 15:30:09',NULL,NULL,'0'),(25,25,'가.',NULL,NULL,'2016-01-12 15:30:30',NULL,NULL,'0'),(26,26,'Otsharimashita',NULL,NULL,'2016-01-12 15:30:50',NULL,NULL,'0'),(27,27,'존은 영국',NULL,NULL,'2016-01-12 15:32:29',NULL,NULL,'0'),(28,28,'존은 영국?',NULL,NULL,'2016-01-12 15:32:49',NULL,NULL,'0'),(29,29,'나는 내 동생과 함께 영화관에 간다.',NULL,NULL,'2016-01-12 15:33:12',NULL,NULL,'0'),(30,30,'존과 영화 극장에 갔습니다.',NULL,NULL,'2016-01-12 15:33:33',NULL,NULL,'0'),(31,31,'나는 존과 영화 극장으로 이동합니다.',NULL,NULL,'2016-01-12 15:33:54',NULL,NULL,'0'),(32,32,'나는 자전거에 영화관에 간다.',NULL,NULL,'2016-01-12 15:34:12',NULL,NULL,'0'),(33,33,'존은 자전거에 영화관에 갔다?',NULL,NULL,'2016-01-12 15:34:31',NULL,NULL,'0'),(34,34,'자전거에 영화관에가 서 존',NULL,NULL,'2016-01-12 15:34:54',NULL,NULL,'0'),(35,36,'존은 자전거에 영화관에 간다?',NULL,NULL,'2016-01-12 15:40:01',NULL,NULL,'0'),(36,37,'존은 자전거에 영화관에 간다',NULL,NULL,'2016-01-12 15:40:22',NULL,NULL,'0'),(37,38,'난 자전거 존 영화관에 가지 않 았 어?',NULL,NULL,'2016-01-12 15:40:45',NULL,NULL,'0'),(38,39,'가지 않 았 어 존 영화관에 자전거',NULL,NULL,'2016-01-12 15:41:05',NULL,NULL,'0'),(39,40,'자전거에 영화관에가 서 존',NULL,NULL,'2016-01-12 15:41:24',NULL,NULL,'0'),(40,41,'자전거로 영화관에가 서 존',NULL,NULL,'2016-01-12 15:41:45',NULL,NULL,'0'),(41,42,'존',NULL,NULL,'2016-01-12 15:42:04',NULL,NULL,'0');
/*!40000 ALTER TABLE `dict_ko` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_la`
--

DROP TABLE IF EXISTS `dict_la`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_la` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_la`
--

LOCK TABLES `dict_la` WRITE;
/*!40000 ALTER TABLE `dict_la` DISABLE KEYS */;
/*!40000 ALTER TABLE `dict_la` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_ln`
--

DROP TABLE IF EXISTS `dict_ln`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_ln` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_ln`
--

LOCK TABLES `dict_ln` WRITE;
/*!40000 ALTER TABLE `dict_ln` DISABLE KEYS */;
INSERT INTO `dict_ln` VALUES (1,1,'Mėnesiena',NULL,NULL,'2016-01-12 15:21:32',NULL,NULL,'0'),(2,2,'Jaunatis',NULL,NULL,'2016-01-12 15:21:57',NULL,NULL,'0'),(3,3,'John yra Didžiosios Britanijos',NULL,NULL,'2016-01-12 15:22:20',NULL,NULL,'0'),(4,4,'Aš nuėjau į kiną su savo broliu.',NULL,NULL,'2016-01-12 15:22:39',NULL,NULL,'0'),(5,5,'Nuėjau į kiną nuoma',NULL,NULL,'2016-01-12 15:23:02',NULL,NULL,'0'),(6,6,'John',NULL,NULL,'2016-01-12 15:23:23',NULL,NULL,'0'),(7,7,'Britai',NULL,NULL,'2016-01-12 15:23:44',NULL,NULL,'0'),(8,8,'Aš',NULL,NULL,'2016-01-12 15:24:05',NULL,NULL,'0'),(9,9,'Mano brolis',NULL,NULL,'2016-01-12 15:24:29',NULL,NULL,'0'),(10,10,'Kinas',NULL,NULL,'2016-01-12 15:24:48',NULL,NULL,'0'),(11,11,'Nuėjau',NULL,NULL,'2016-01-12 15:25:11',NULL,NULL,'0'),(12,12,'Dviratis',NULL,NULL,'2016-01-12 15:25:31',NULL,NULL,'0'),(13,13,'Mėnulio užtemimas',NULL,NULL,'2016-01-12 15:25:51',NULL,NULL,'0'),(14,14,'Mano brolis yra gydytojas.',NULL,NULL,'2016-01-12 15:26:15',NULL,NULL,'0'),(15,15,'Don\'t stop nuoma',NULL,NULL,'2016-01-12 15:26:35',NULL,NULL,'0'),(16,16,'Šventyklos ir parkų.',NULL,NULL,'2016-01-12 15:26:52',NULL,NULL,'0'),(17,17,'Gydytojas',NULL,NULL,'2016-01-12 15:27:14',NULL,NULL,'0'),(18,18,'sustoti, sustok',NULL,NULL,'2016-01-12 15:27:33',NULL,NULL,'0'),(19,19,'Šventykla',NULL,NULL,'2016-01-12 15:27:57',NULL,NULL,'0'),(20,20,'Park',NULL,NULL,'2016-01-12 15:28:18',NULL,NULL,'0'),(21,21,'Toks',NULL,NULL,'2016-01-12 15:28:39',NULL,NULL,'0'),(22,22,'Raudonas mėnulis',NULL,NULL,'2016-01-12 15:29:27',NULL,NULL,'0'),(23,23,'Ossharimashita ir rytoj',NULL,NULL,'2016-01-12 15:29:48',NULL,NULL,'0'),(24,24,'Rytoj',NULL,NULL,'2016-01-12 15:30:09',NULL,NULL,'0'),(25,25,'Eiti.',NULL,NULL,'2016-01-12 15:30:30',NULL,NULL,'0'),(26,26,'Otsharimashita',NULL,NULL,'2016-01-12 15:30:50',NULL,NULL,'0'),(27,27,'John yra ne britų',NULL,NULL,'2016-01-12 15:32:29',NULL,NULL,'0'),(28,28,'John yra britų?',NULL,NULL,'2016-01-12 15:32:49',NULL,NULL,'0'),(29,29,'Aš einu į kino teatrą su savo broliu.',NULL,NULL,'2016-01-12 15:33:12',NULL,NULL,'0'),(30,30,'Aš nuėjau į John ir kino teatrų.',NULL,NULL,'2016-01-12 15:33:33',NULL,NULL,'0'),(31,31,'Aš einu į John ir kino teatrų.',NULL,NULL,'2016-01-12 15:33:54',NULL,NULL,'0'),(32,32,'Aš einu į kino teatrą ant dviračio.',NULL,NULL,'2016-01-12 15:34:12',NULL,NULL,'0'),(33,33,'Jonas išvyko į kiną nuoma?',NULL,NULL,'2016-01-12 15:34:31',NULL,NULL,'0'),(34,34,'Jonas išvyko į kiną nuoma',NULL,NULL,'2016-01-12 15:34:54',NULL,NULL,'0'),(35,36,'Jonas eina į kiną nuoma?',NULL,NULL,'2016-01-12 15:40:01',NULL,NULL,'0'),(36,37,'Jonas eina į kiną nuoma',NULL,NULL,'2016-01-12 15:40:22',NULL,NULL,'0'),(37,38,'Aš ne eiti į kiną nuoma John?',NULL,NULL,'2016-01-12 15:40:45',NULL,NULL,'0'),(38,39,'Ne John eiti į kiną dviračiu',NULL,NULL,'2016-01-12 15:41:05',NULL,NULL,'0'),(39,40,'John eiti į kiną nuoma',NULL,NULL,'2016-01-12 15:41:24',NULL,NULL,'0'),(40,41,'John eiti į kiną dviračiu',NULL,NULL,'2016-01-12 15:41:45',NULL,NULL,'0'),(41,42,'John',NULL,NULL,'2016-01-12 15:42:04',NULL,NULL,'0');
/*!40000 ALTER TABLE `dict_ln` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_lv`
--

DROP TABLE IF EXISTS `dict_lv`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_lv` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_lv`
--

LOCK TABLES `dict_lv` WRITE;
/*!40000 ALTER TABLE `dict_lv` DISABLE KEYS */;
INSERT INTO `dict_lv` VALUES (1,1,'Mēness gaisma',NULL,NULL,'2016-01-12 15:21:32',NULL,NULL,'0'),(2,2,'Jauns mēness',NULL,NULL,'2016-01-12 15:21:57',NULL,NULL,'0'),(3,3,'John ir britu',NULL,NULL,'2016-01-12 15:22:20',NULL,NULL,'0'),(4,4,'Es devos uz kino kopā ar savu brāli.',NULL,NULL,'2016-01-12 15:22:39',NULL,NULL,'0'),(5,5,'Es devos uz kino ar velosipēdu',NULL,NULL,'2016-01-12 15:23:02',NULL,NULL,'0'),(6,6,'John',NULL,NULL,'2016-01-12 15:23:23',NULL,NULL,'0'),(7,7,'Lielbritānijas iedzīvotājiem',NULL,NULL,'2016-01-12 15:23:44',NULL,NULL,'0'),(8,8,'Es',NULL,NULL,'2016-01-12 15:24:05',NULL,NULL,'0'),(9,9,'Mans brālis',NULL,NULL,'2016-01-12 15:24:29',NULL,NULL,'0'),(10,10,'Kino',NULL,NULL,'2016-01-12 15:24:48',NULL,NULL,'0'),(11,11,'Es devos',NULL,NULL,'2016-01-12 15:25:11',NULL,NULL,'0'),(12,12,'Velosipēds',NULL,NULL,'2016-01-12 15:25:31',NULL,NULL,'0'),(13,13,'Mēness aptumsums',NULL,NULL,'2016-01-12 15:25:51',NULL,NULL,'0'),(14,14,'Mans brālis ir ārste.',NULL,NULL,'2016-01-12 15:26:15',NULL,NULL,'0'),(15,15,'Nepārtrauciet velosipēds',NULL,NULL,'2016-01-12 15:26:35',NULL,NULL,'0'),(16,16,'Svētnīcām un parki.',NULL,NULL,'2016-01-12 15:26:52',NULL,NULL,'0'),(17,17,'Ārsts',NULL,NULL,'2016-01-12 15:27:14',NULL,NULL,'0'),(18,18,'apstājieties',NULL,NULL,'2016-01-12 15:27:33',NULL,NULL,'0'),(19,19,'Ruins',NULL,NULL,'2016-01-12 15:27:57',NULL,NULL,'0'),(20,20,'Parks',NULL,NULL,'2016-01-12 15:28:18',NULL,NULL,'0'),(21,21,'Šāda',NULL,NULL,'2016-01-12 15:28:39',NULL,NULL,'0'),(22,22,'Red Moon',NULL,NULL,'2016-01-12 15:29:27',NULL,NULL,'0'),(23,23,'Ossharimashita un rīt',NULL,NULL,'2016-01-12 15:29:48',NULL,NULL,'0'),(24,24,'Rītdienas',NULL,NULL,'2016-01-12 15:30:09',NULL,NULL,'0'),(25,25,'Aiziet.',NULL,NULL,'2016-01-12 15:30:30',NULL,NULL,'0'),(26,26,'Otsharimashita',NULL,NULL,'2016-01-12 15:30:50',NULL,NULL,'0'),(27,27,'John nav britu',NULL,NULL,'2016-01-12 15:32:29',NULL,NULL,'0'),(28,28,'John ir britu?',NULL,NULL,'2016-01-12 15:32:49',NULL,NULL,'0'),(29,29,'Es iet uz kino kopā ar savu brāli.',NULL,NULL,'2016-01-12 15:33:12',NULL,NULL,'0'),(30,30,'Es devos uz John un filmu teātrus.',NULL,NULL,'2016-01-12 15:33:33',NULL,NULL,'0'),(31,31,'Es dodos uz John un filmu teātrus.',NULL,NULL,'2016-01-12 15:33:54',NULL,NULL,'0'),(32,32,'Es iet uz kino, uz velosipēdu.',NULL,NULL,'2016-01-12 15:34:12',NULL,NULL,'0'),(33,33,'John gāja uz kino ar velosipēdu?',NULL,NULL,'2016-01-12 15:34:31',NULL,NULL,'0'),(34,34,'Džons aizgāja uz kino velosipēds',NULL,NULL,'2016-01-12 15:34:54',NULL,NULL,'0'),(35,36,'John iet uz kino ar velosipēdu?',NULL,NULL,'2016-01-12 15:40:01',NULL,NULL,'0'),(36,37,'John iet uz kino ar velosipēdu',NULL,NULL,'2016-01-12 15:40:22',NULL,NULL,'0'),(37,38,'I didn\'t iet uz kino ar velosipēdu John?',NULL,NULL,'2016-01-12 15:40:45',NULL,NULL,'0'),(38,39,'John negāja uz kino ar velosipēdu',NULL,NULL,'2016-01-12 15:41:05',NULL,NULL,'0'),(39,40,'John iet uz kino ar velosipēdu',NULL,NULL,'2016-01-12 15:41:24',NULL,NULL,'0'),(40,41,'John iet uz kino ar velosipēdu',NULL,NULL,'2016-01-12 15:41:45',NULL,NULL,'0'),(41,42,'John',NULL,NULL,'2016-01-12 15:42:04',NULL,NULL,'0');
/*!40000 ALTER TABLE `dict_lv` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_mk`
--

DROP TABLE IF EXISTS `dict_mk`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_mk` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_mk`
--

LOCK TABLES `dict_mk` WRITE;
/*!40000 ALTER TABLE `dict_mk` DISABLE KEYS */;
/*!40000 ALTER TABLE `dict_mk` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_ms`
--

DROP TABLE IF EXISTS `dict_ms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_ms` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_ms`
--

LOCK TABLES `dict_ms` WRITE;
/*!40000 ALTER TABLE `dict_ms` DISABLE KEYS */;
INSERT INTO `dict_ms` VALUES (1,1,'Moonlight',NULL,NULL,'2016-01-12 15:21:32',NULL,NULL,'0'),(2,2,'New Moon',NULL,NULL,'2016-01-12 15:21:57',NULL,NULL,'0'),(3,3,'John merupakan seorang British',NULL,NULL,'2016-01-12 15:22:20',NULL,NULL,'0'),(4,4,'Saya pergi ke pawagam dengan abang saya.',NULL,NULL,'2016-01-12 15:22:39',NULL,NULL,'0'),(5,5,'Saya pergi ke pawagam pada Basikal',NULL,NULL,'2016-01-12 15:23:02',NULL,NULL,'0'),(6,6,'John',NULL,NULL,'2016-01-12 15:23:23',NULL,NULL,'0'),(7,7,'Orang British',NULL,NULL,'2016-01-12 15:23:44',NULL,NULL,'0'),(8,8,'Saya',NULL,NULL,'2016-01-12 15:24:05',NULL,NULL,'0'),(9,9,'Abang saya',NULL,NULL,'2016-01-12 15:24:29',NULL,NULL,'0'),(10,10,'Pawagam',NULL,NULL,'2016-01-12 15:24:48',NULL,NULL,'0'),(11,11,'Saya pergi',NULL,NULL,'2016-01-12 15:25:11',NULL,NULL,'0'),(12,12,'Basikal',NULL,NULL,'2016-01-12 15:25:31',NULL,NULL,'0'),(13,13,'Gerhana bulan',NULL,NULL,'2016-01-12 15:25:51',NULL,NULL,'0'),(14,14,'Abang saya adalah seorang doktor.',NULL,NULL,'2016-01-12 15:26:15',NULL,NULL,'0'),(15,15,'Jangan berhenti Basikal',NULL,NULL,'2016-01-12 15:26:35',NULL,NULL,'0'),(16,16,'Suci dan Taman-Taman.',NULL,NULL,'2016-01-12 15:26:52',NULL,NULL,'0'),(17,17,'Doktor',NULL,NULL,'2016-01-12 15:27:14',NULL,NULL,'0'),(18,18,'Berhenti',NULL,NULL,'2016-01-12 15:27:33',NULL,NULL,'0'),(19,19,'Tempat Suci',NULL,NULL,'2016-01-12 15:27:57',NULL,NULL,'0'),(20,20,'Park',NULL,NULL,'2016-01-12 15:28:18',NULL,NULL,'0'),(21,21,'Apa-apa',NULL,NULL,'2016-01-12 15:28:39',NULL,NULL,'0'),(22,22,'Bulan merah',NULL,NULL,'2016-01-12 15:29:27',NULL,NULL,'0'),(23,23,'Ossharimashita dan esok',NULL,NULL,'2016-01-12 15:29:48',NULL,NULL,'0'),(24,24,'Esok',NULL,NULL,'2016-01-12 15:30:09',NULL,NULL,'0'),(25,25,'Pergi.',NULL,NULL,'2016-01-12 15:30:30',NULL,NULL,'0'),(26,26,'Otsharimashita',NULL,NULL,'2016-01-12 15:30:50',NULL,NULL,'0'),(27,27,'John bukanlah British',NULL,NULL,'2016-01-12 15:32:29',NULL,NULL,'0'),(28,28,'John adalah British?',NULL,NULL,'2016-01-12 15:32:49',NULL,NULL,'0'),(29,29,'Saya pergi ke pawagam dengan abang saya.',NULL,NULL,'2016-01-12 15:33:12',NULL,NULL,'0'),(30,30,'Saya pergi ke John dan teater filem.',NULL,NULL,'2016-01-12 15:33:33',NULL,NULL,'0'),(31,31,'Aku pergi ke John dan teater filem.',NULL,NULL,'2016-01-12 15:33:54',NULL,NULL,'0'),(32,32,'Saya pergi ke pawagam pada basikal.',NULL,NULL,'2016-01-12 15:34:12',NULL,NULL,'0'),(33,33,'John pergi ke pawagam pada Basikal?',NULL,NULL,'2016-01-12 15:34:31',NULL,NULL,'0'),(34,34,'John pergi ke pawagam pada Basikal',NULL,NULL,'2016-01-12 15:34:54',NULL,NULL,'0'),(35,36,'John pergi ke pawagam dengan Basikal?',NULL,NULL,'2016-01-12 15:40:01',NULL,NULL,'0'),(36,37,'John pergi ke pawagam dengan Basikal',NULL,NULL,'2016-01-12 15:40:22',NULL,NULL,'0'),(37,38,'Saya tidak pergi ke pawagam dengan basikal John?',NULL,NULL,'2016-01-12 15:40:45',NULL,NULL,'0'),(38,39,'Tidak John pergi ke pawagam dengan menaiki basikal',NULL,NULL,'2016-01-12 15:41:05',NULL,NULL,'0'),(39,40,'John pergi ke pawagam pada Basikal',NULL,NULL,'2016-01-12 15:41:24',NULL,NULL,'0'),(40,41,'John pergi ke pawagam dengan Basikal',NULL,NULL,'2016-01-12 15:41:45',NULL,NULL,'0'),(41,42,'John',NULL,NULL,'2016-01-12 15:42:04',NULL,NULL,'0');
/*!40000 ALTER TABLE `dict_ms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_mt`
--

DROP TABLE IF EXISTS `dict_mt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_mt` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_mt`
--

LOCK TABLES `dict_mt` WRITE;
/*!40000 ALTER TABLE `dict_mt` DISABLE KEYS */;
/*!40000 ALTER TABLE `dict_mt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_mww`
--

DROP TABLE IF EXISTS `dict_mww`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_mww` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_mww`
--

LOCK TABLES `dict_mww` WRITE;
/*!40000 ALTER TABLE `dict_mww` DISABLE KEYS */;
/*!40000 ALTER TABLE `dict_mww` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_nl`
--

DROP TABLE IF EXISTS `dict_nl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_nl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_nl`
--

LOCK TABLES `dict_nl` WRITE;
/*!40000 ALTER TABLE `dict_nl` DISABLE KEYS */;
INSERT INTO `dict_nl` VALUES (1,1,'Maanlicht',NULL,NULL,'2016-01-12 15:21:32',NULL,NULL,'0'),(2,2,'Nieuwe maan',NULL,NULL,'2016-01-12 15:21:57',NULL,NULL,'0'),(3,3,'John is een Britse',NULL,NULL,'2016-01-12 15:22:20',NULL,NULL,'0'),(4,4,'Ik ging naar de bioscoop met mijn broer.',NULL,NULL,'2016-01-12 15:22:39',NULL,NULL,'0'),(5,5,'Ik ging naar de bioscoop op een fiets',NULL,NULL,'2016-01-12 15:23:02',NULL,NULL,'0'),(6,6,'John',NULL,NULL,'2016-01-12 15:23:23',NULL,NULL,'0'),(7,7,'Britten',NULL,NULL,'2016-01-12 15:23:44',NULL,NULL,'0'),(8,8,'Ik',NULL,NULL,'2016-01-12 15:24:05',NULL,NULL,'0'),(9,9,'Mijn broer',NULL,NULL,'2016-01-12 15:24:29',NULL,NULL,'0'),(10,10,'Bioscoop',NULL,NULL,'2016-01-12 15:24:48',NULL,NULL,'0'),(11,11,'Ik ging',NULL,NULL,'2016-01-12 15:25:11',NULL,NULL,'0'),(12,12,'Fiets',NULL,NULL,'2016-01-12 15:25:31',NULL,NULL,'0'),(13,13,'Maansverduistering',NULL,NULL,'2016-01-12 15:25:51',NULL,NULL,'0'),(14,14,'Mijn broer is een arts.',NULL,NULL,'2016-01-12 15:26:15',NULL,NULL,'0'),(15,15,'Niet de fiets',NULL,NULL,'2016-01-12 15:26:35',NULL,NULL,'0'),(16,16,'Heiligdommen en parken.',NULL,NULL,'2016-01-12 15:26:52',NULL,NULL,'0'),(17,17,'Arts',NULL,NULL,'2016-01-12 15:27:14',NULL,NULL,'0'),(18,18,'Stop',NULL,NULL,'2016-01-12 15:27:33',NULL,NULL,'0'),(19,19,'Schrijn',NULL,NULL,'2016-01-12 15:27:57',NULL,NULL,'0'),(20,20,'Park',NULL,NULL,'2016-01-12 15:28:18',NULL,NULL,'0'),(21,21,'Dergelijke',NULL,NULL,'2016-01-12 15:28:39',NULL,NULL,'0'),(22,22,'Red Moon',NULL,NULL,'2016-01-12 15:29:27',NULL,NULL,'0'),(23,23,'Ossharimashita en morgen',NULL,NULL,'2016-01-12 15:29:48',NULL,NULL,'0'),(24,24,'Morgen',NULL,NULL,'2016-01-12 15:30:09',NULL,NULL,'0'),(25,25,'Gaan.',NULL,NULL,'2016-01-12 15:30:30',NULL,NULL,'0'),(26,26,'Otsharimashita',NULL,NULL,'2016-01-12 15:30:50',NULL,NULL,'0'),(27,27,'John is niet Britse',NULL,NULL,'2016-01-12 15:32:29',NULL,NULL,'0'),(28,28,'John is Britse?',NULL,NULL,'2016-01-12 15:32:49',NULL,NULL,'0'),(29,29,'Ik ga naar de bioscoop met mijn broer.',NULL,NULL,'2016-01-12 15:33:12',NULL,NULL,'0'),(30,30,'Ik ging naar John en film theaters.',NULL,NULL,'2016-01-12 15:33:33',NULL,NULL,'0'),(31,31,'Ik ga naar John en film theaters.',NULL,NULL,'2016-01-12 15:33:54',NULL,NULL,'0'),(32,32,'Ik ga naar de bioscoop op een fiets.',NULL,NULL,'2016-01-12 15:34:12',NULL,NULL,'0'),(33,33,'John ging naar de bioscoop op een fiets?',NULL,NULL,'2016-01-12 15:34:31',NULL,NULL,'0'),(34,34,'John ging naar de bioscoop op een fiets',NULL,NULL,'2016-01-12 15:34:54',NULL,NULL,'0'),(35,36,'John gaat naar de bioscoop op een fiets?',NULL,NULL,'2016-01-12 15:40:01',NULL,NULL,'0'),(36,37,'John gaat naar de bioscoop op een fiets',NULL,NULL,'2016-01-12 15:40:22',NULL,NULL,'0'),(37,38,'Ik ga niet naar de bioscoop op een fiets John?',NULL,NULL,'2016-01-12 15:40:45',NULL,NULL,'0'),(38,39,'John ga niet naar de bioscoop per fiets',NULL,NULL,'2016-01-12 15:41:05',NULL,NULL,'0'),(39,40,'John gaan naar de bioscoop op een fiets',NULL,NULL,'2016-01-12 15:41:24',NULL,NULL,'0'),(40,41,'John gaan naar de bioscoop per fiets',NULL,NULL,'2016-01-12 15:41:45',NULL,NULL,'0'),(41,42,'John',NULL,NULL,'2016-01-12 15:42:04',NULL,NULL,'0');
/*!40000 ALTER TABLE `dict_nl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_no`
--

DROP TABLE IF EXISTS `dict_no`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_no` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_no`
--

LOCK TABLES `dict_no` WRITE;
/*!40000 ALTER TABLE `dict_no` DISABLE KEYS */;
INSERT INTO `dict_no` VALUES (1,1,'Måneskinn',NULL,NULL,'2016-01-12 15:21:32',NULL,NULL,'0'),(2,2,'Nymåne',NULL,NULL,'2016-01-12 15:21:57',NULL,NULL,'0'),(3,3,'John er en britisk',NULL,NULL,'2016-01-12 15:22:20',NULL,NULL,'0'),(4,4,'Jeg gikk på kino med min bror.',NULL,NULL,'2016-01-12 15:22:39',NULL,NULL,'0'),(5,5,'Jeg gikk på kino på sykkel',NULL,NULL,'2016-01-12 15:23:02',NULL,NULL,'0'),(6,6,'John',NULL,NULL,'2016-01-12 15:23:23',NULL,NULL,'0'),(7,7,'Briter',NULL,NULL,'2016-01-12 15:23:44',NULL,NULL,'0'),(8,8,'Jeg',NULL,NULL,'2016-01-12 15:24:05',NULL,NULL,'0'),(9,9,'Min bror',NULL,NULL,'2016-01-12 15:24:29',NULL,NULL,'0'),(10,10,'Kino',NULL,NULL,'2016-01-12 15:24:48',NULL,NULL,'0'),(11,11,'Jeg gikk',NULL,NULL,'2016-01-12 15:25:11',NULL,NULL,'0'),(12,12,'Sykkel',NULL,NULL,'2016-01-12 15:25:31',NULL,NULL,'0'),(13,13,'Måneformørkelse',NULL,NULL,'2016-01-12 15:25:51',NULL,NULL,'0'),(14,14,'Broren min er lege.',NULL,NULL,'2016-01-12 15:26:15',NULL,NULL,'0'),(15,15,'Ikke stopp sykkelen',NULL,NULL,'2016-01-12 15:26:35',NULL,NULL,'0'),(16,16,'Helligdommer og parker.',NULL,NULL,'2016-01-12 15:26:52',NULL,NULL,'0'),(17,17,'Legen',NULL,NULL,'2016-01-12 15:27:14',NULL,NULL,'0'),(18,18,'stopp',NULL,NULL,'2016-01-12 15:27:33',NULL,NULL,'0'),(19,19,'Helligdommen',NULL,NULL,'2016-01-12 15:27:57',NULL,NULL,'0'),(20,20,'Park',NULL,NULL,'2016-01-12 15:28:18',NULL,NULL,'0'),(21,21,'Slike',NULL,NULL,'2016-01-12 15:28:39',NULL,NULL,'0'),(22,22,'Rød måne',NULL,NULL,'2016-01-12 15:29:27',NULL,NULL,'0'),(23,23,'Ossharimashita og i morgen',NULL,NULL,'2016-01-12 15:29:48',NULL,NULL,'0'),(24,24,'I morgen',NULL,NULL,'2016-01-12 15:30:09',NULL,NULL,'0'),(25,25,'Gå.',NULL,NULL,'2016-01-12 15:30:30',NULL,NULL,'0'),(26,26,'Otsharimashita',NULL,NULL,'2016-01-12 15:30:50',NULL,NULL,'0'),(27,27,'John er ikke britiske',NULL,NULL,'2016-01-12 15:32:29',NULL,NULL,'0'),(28,28,'John er britiske?',NULL,NULL,'2016-01-12 15:32:49',NULL,NULL,'0'),(29,29,'Jeg går på kino med min bror.',NULL,NULL,'2016-01-12 15:33:12',NULL,NULL,'0'),(30,30,'Jeg dro til John og filmen teatre.',NULL,NULL,'2016-01-12 15:33:33',NULL,NULL,'0'),(31,31,'Jeg går til John og filmen teatre.',NULL,NULL,'2016-01-12 15:33:54',NULL,NULL,'0'),(32,32,'Jeg går på kino på sykkel.',NULL,NULL,'2016-01-12 15:34:12',NULL,NULL,'0'),(33,33,'John gikk på kino på en sykkel?',NULL,NULL,'2016-01-12 15:34:31',NULL,NULL,'0'),(34,34,'John gikk på kino på sykkel',NULL,NULL,'2016-01-12 15:34:54',NULL,NULL,'0'),(35,36,'John går på kino på en sykkel?',NULL,NULL,'2016-01-12 15:40:01',NULL,NULL,'0'),(36,37,'John går på kino på en sykkel',NULL,NULL,'2016-01-12 15:40:22',NULL,NULL,'0'),(37,38,'Jeg ikke gå på kino på sykkel John?',NULL,NULL,'2016-01-12 15:40:45',NULL,NULL,'0'),(38,39,'John gikk ikke på kino på sykkel',NULL,NULL,'2016-01-12 15:41:05',NULL,NULL,'0'),(39,40,'John går på kino på sykkel',NULL,NULL,'2016-01-12 15:41:24',NULL,NULL,'0'),(40,41,'John går på kino på sykkel',NULL,NULL,'2016-01-12 15:41:45',NULL,NULL,'0'),(41,42,'John',NULL,NULL,'2016-01-12 15:42:04',NULL,NULL,'0');
/*!40000 ALTER TABLE `dict_no` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_pl`
--

DROP TABLE IF EXISTS `dict_pl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_pl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_pl`
--

LOCK TABLES `dict_pl` WRITE;
/*!40000 ALTER TABLE `dict_pl` DISABLE KEYS */;
INSERT INTO `dict_pl` VALUES (1,1,'Światło księżyca',NULL,NULL,'2016-01-12 15:21:32',NULL,NULL,'0'),(2,2,'Księżyc w nowiu',NULL,NULL,'2016-01-12 15:21:57',NULL,NULL,'0'),(3,3,'John jest brytyjski',NULL,NULL,'2016-01-12 15:22:20',NULL,NULL,'0'),(4,4,'Poszedłem do kina z moim bratem.',NULL,NULL,'2016-01-12 15:22:39',NULL,NULL,'0'),(5,5,'Poszedłem do kina na rowerze',NULL,NULL,'2016-01-12 15:23:02',NULL,NULL,'0'),(6,6,'John',NULL,NULL,'2016-01-12 15:23:23',NULL,NULL,'0'),(7,7,'Brytyjczycy',NULL,NULL,'2016-01-12 15:23:44',NULL,NULL,'0'),(8,8,'Ja',NULL,NULL,'2016-01-12 15:24:05',NULL,NULL,'0'),(9,9,'Mój brat',NULL,NULL,'2016-01-12 15:24:29',NULL,NULL,'0'),(10,10,'Kino',NULL,NULL,'2016-01-12 15:24:48',NULL,NULL,'0'),(11,11,'Poszedłem',NULL,NULL,'2016-01-12 15:25:11',NULL,NULL,'0'),(12,12,'Rower',NULL,NULL,'2016-01-12 15:25:31',NULL,NULL,'0'),(13,13,'Zaćmienie Księżyca',NULL,NULL,'2016-01-12 15:25:51',NULL,NULL,'0'),(14,14,'Mój brat jest lekarz.',NULL,NULL,'2016-01-12 15:26:15',NULL,NULL,'0'),(15,15,'Nie zatrzymuj się rower',NULL,NULL,'2016-01-12 15:26:35',NULL,NULL,'0'),(16,16,'Sanktuaria i parków.',NULL,NULL,'2016-01-12 15:26:52',NULL,NULL,'0'),(17,17,'Lekarz',NULL,NULL,'2016-01-12 15:27:14',NULL,NULL,'0'),(18,18,'Przystanek',NULL,NULL,'2016-01-12 15:27:33',NULL,NULL,'0'),(19,19,'Przybytek',NULL,NULL,'2016-01-12 15:27:57',NULL,NULL,'0'),(20,20,'Park',NULL,NULL,'2016-01-12 15:28:18',NULL,NULL,'0'),(21,21,'Takie',NULL,NULL,'2016-01-12 15:28:39',NULL,NULL,'0'),(22,22,'Czerwony księżyc',NULL,NULL,'2016-01-12 15:29:27',NULL,NULL,'0'),(23,23,'Ossharimashita i jutro',NULL,NULL,'2016-01-12 15:29:48',NULL,NULL,'0'),(24,24,'Jutro',NULL,NULL,'2016-01-12 15:30:09',NULL,NULL,'0'),(25,25,'Przejdź.',NULL,NULL,'2016-01-12 15:30:30',NULL,NULL,'0'),(26,26,'Otsharimashita',NULL,NULL,'2016-01-12 15:30:50',NULL,NULL,'0'),(27,27,'John nie jest brytyjski',NULL,NULL,'2016-01-12 15:32:29',NULL,NULL,'0'),(28,28,'John jest brytyjski?',NULL,NULL,'2016-01-12 15:32:49',NULL,NULL,'0'),(29,29,'Idę do kina z moim bratem.',NULL,NULL,'2016-01-12 15:33:12',NULL,NULL,'0'),(30,30,'Poszedłem do kina Johna i filmu.',NULL,NULL,'2016-01-12 15:33:33',NULL,NULL,'0'),(31,31,'Idę do kina Johna i filmu.',NULL,NULL,'2016-01-12 15:33:54',NULL,NULL,'0'),(32,32,'Idę do kina na rowerze.',NULL,NULL,'2016-01-12 15:34:12',NULL,NULL,'0'),(33,33,'Jan udał się do kina na rowerze?',NULL,NULL,'2016-01-12 15:34:31',NULL,NULL,'0'),(34,34,'Jan udał się do kina na rowerze',NULL,NULL,'2016-01-12 15:34:54',NULL,NULL,'0'),(35,36,'John idzie do kina na rowerze?',NULL,NULL,'2016-01-12 15:40:01',NULL,NULL,'0'),(36,37,'John idzie do kina na rowerze',NULL,NULL,'2016-01-12 15:40:22',NULL,NULL,'0'),(37,38,'Nie poszedłem do kina na rowerze John?',NULL,NULL,'2016-01-12 15:40:45',NULL,NULL,'0'),(38,39,'Nie Anglik iść do kina na rowerze',NULL,NULL,'2016-01-12 15:41:05',NULL,NULL,'0'),(39,40,'Anglik iść do kina na rowerze',NULL,NULL,'2016-01-12 15:41:24',NULL,NULL,'0'),(40,41,'Anglik iść do kina na rowerze',NULL,NULL,'2016-01-12 15:41:45',NULL,NULL,'0'),(41,42,'John',NULL,NULL,'2016-01-12 15:42:04',NULL,NULL,'0');
/*!40000 ALTER TABLE `dict_pl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_pt`
--

DROP TABLE IF EXISTS `dict_pt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_pt` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_pt`
--

LOCK TABLES `dict_pt` WRITE;
/*!40000 ALTER TABLE `dict_pt` DISABLE KEYS */;
INSERT INTO `dict_pt` VALUES (1,1,'Luz da lua',NULL,NULL,'2016-01-12 15:21:32',NULL,NULL,'0'),(2,2,'Lua nova',NULL,NULL,'2016-01-12 15:21:57',NULL,NULL,'0'),(3,3,'John é um britânico',NULL,NULL,'2016-01-12 15:22:20',NULL,NULL,'0'),(4,4,'Eu fui ao cinema com meu irmão.',NULL,NULL,'2016-01-12 15:22:39',NULL,NULL,'0'),(5,5,'Eu fui ao cinema em uma bicicleta',NULL,NULL,'2016-01-12 15:23:02',NULL,NULL,'0'),(6,6,'John',NULL,NULL,'2016-01-12 15:23:23',NULL,NULL,'0'),(7,7,'Ingleses',NULL,NULL,'2016-01-12 15:23:44',NULL,NULL,'0'),(8,8,'Eu',NULL,NULL,'2016-01-12 15:24:05',NULL,NULL,'0'),(9,9,'Meu irmão',NULL,NULL,'2016-01-12 15:24:29',NULL,NULL,'0'),(10,10,'Cinema',NULL,NULL,'2016-01-12 15:24:48',NULL,NULL,'0'),(11,11,'Eu fui',NULL,NULL,'2016-01-12 15:25:11',NULL,NULL,'0'),(12,12,'Bicicleta',NULL,NULL,'2016-01-12 15:25:31',NULL,NULL,'0'),(13,13,'Eclipse Lunar',NULL,NULL,'2016-01-12 15:25:51',NULL,NULL,'0'),(14,14,'Meu irmão é médico.',NULL,NULL,'2016-01-12 15:26:15',NULL,NULL,'0'),(15,15,'Não pare a moto',NULL,NULL,'2016-01-12 15:26:35',NULL,NULL,'0'),(16,16,'Parques e santuários.',NULL,NULL,'2016-01-12 15:26:52',NULL,NULL,'0'),(17,17,'Médico',NULL,NULL,'2016-01-12 15:27:14',NULL,NULL,'0'),(18,18,'Pare',NULL,NULL,'2016-01-12 15:27:33',NULL,NULL,'0'),(19,19,'Santuário',NULL,NULL,'2016-01-12 15:27:57',NULL,NULL,'0'),(20,20,'Parque',NULL,NULL,'2016-01-12 15:28:18',NULL,NULL,'0'),(21,21,'Tais',NULL,NULL,'2016-01-12 15:28:39',NULL,NULL,'0'),(22,22,'Lua vermelha',NULL,NULL,'2016-01-12 15:29:27',NULL,NULL,'0'),(23,23,'Ossharimashita e amanhã',NULL,NULL,'2016-01-12 15:29:48',NULL,NULL,'0'),(24,24,'Amanhã',NULL,NULL,'2016-01-12 15:30:09',NULL,NULL,'0'),(25,25,'Ir.',NULL,NULL,'2016-01-12 15:30:30',NULL,NULL,'0'),(26,26,'Otsharimashita',NULL,NULL,'2016-01-12 15:30:50',NULL,NULL,'0'),(27,27,'John não é britânico',NULL,NULL,'2016-01-12 15:32:29',NULL,NULL,'0'),(28,28,'John é britânico?',NULL,NULL,'2016-01-12 15:32:49',NULL,NULL,'0'),(29,29,'Vou ao cinema com meu irmão.',NULL,NULL,'2016-01-12 15:33:12',NULL,NULL,'0'),(30,30,'Fui para John e cinemas.',NULL,NULL,'2016-01-12 15:33:33',NULL,NULL,'0'),(31,31,'Eu vou para John e cinemas.',NULL,NULL,'2016-01-12 15:33:54',NULL,NULL,'0'),(32,32,'Vou para o cinema em uma bicicleta.',NULL,NULL,'2016-01-12 15:34:12',NULL,NULL,'0'),(33,33,'John foi para o cinema em uma bicicleta?',NULL,NULL,'2016-01-12 15:34:31',NULL,NULL,'0'),(34,34,'John foi para o cinema de bicicleta',NULL,NULL,'2016-01-12 15:34:54',NULL,NULL,'0'),(35,36,'John vai ao cinema em uma moto?',NULL,NULL,'2016-01-12 15:40:01',NULL,NULL,'0'),(36,37,'John vai ao cinema em uma bicicleta',NULL,NULL,'2016-01-12 15:40:22',NULL,NULL,'0'),(37,38,'Não fui ao cinema em uma bicicleta, John?',NULL,NULL,'2016-01-12 15:40:45',NULL,NULL,'0'),(38,39,'John não vai ao cinema de bicicleta',NULL,NULL,'2016-01-12 15:41:05',NULL,NULL,'0'),(39,40,'John ir ao cinema de bicicleta',NULL,NULL,'2016-01-12 15:41:24',NULL,NULL,'0'),(40,41,'John ir ao cinema de bicicleta',NULL,NULL,'2016-01-12 15:41:45',NULL,NULL,'0'),(41,42,'John',NULL,NULL,'2016-01-12 15:42:04',NULL,NULL,'0');
/*!40000 ALTER TABLE `dict_pt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_ro`
--

DROP TABLE IF EXISTS `dict_ro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_ro` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_ro`
--

LOCK TABLES `dict_ro` WRITE;
/*!40000 ALTER TABLE `dict_ro` DISABLE KEYS */;
INSERT INTO `dict_ro` VALUES (1,1,'Lumina lunii',NULL,NULL,'2016-01-12 15:21:32',NULL,NULL,'0'),(2,2,'Luna noua',NULL,NULL,'2016-01-12 15:21:57',NULL,NULL,'0'),(3,3,'John este un britanic',NULL,NULL,'2016-01-12 15:22:20',NULL,NULL,'0'),(4,4,'M-am dus la cinema cu fratele meu.',NULL,NULL,'2016-01-12 15:22:39',NULL,NULL,'0'),(5,5,'M-am dus la cinema pe o motocicleta',NULL,NULL,'2016-01-12 15:23:02',NULL,NULL,'0'),(6,6,'Ioan',NULL,NULL,'2016-01-12 15:23:23',NULL,NULL,'0'),(7,7,'Poporul britanic',NULL,NULL,'2016-01-12 15:23:44',NULL,NULL,'0'),(8,8,'Am',NULL,NULL,'2016-01-12 15:24:05',NULL,NULL,'0'),(9,9,'Fratele meu',NULL,NULL,'2016-01-12 15:24:29',NULL,NULL,'0'),(10,10,'Cinematograf',NULL,NULL,'2016-01-12 15:24:48',NULL,NULL,'0'),(11,11,'M-am dus',NULL,NULL,'2016-01-12 15:25:11',NULL,NULL,'0'),(12,12,'Biciclete',NULL,NULL,'2016-01-12 15:25:31',NULL,NULL,'0'),(13,13,'Eclipsă de lună',NULL,NULL,'2016-01-12 15:25:51',NULL,NULL,'0'),(14,14,'Fratele meu este un medic.',NULL,NULL,'2016-01-12 15:26:15',NULL,NULL,'0'),(15,15,'Opri nu motocicleta',NULL,NULL,'2016-01-12 15:26:35',NULL,NULL,'0'),(16,16,'Altare şi parcuri.',NULL,NULL,'2016-01-12 15:26:52',NULL,NULL,'0'),(17,17,'Doctor',NULL,NULL,'2016-01-12 15:27:14',NULL,NULL,'0'),(18,18,'opreşte-te',NULL,NULL,'2016-01-12 15:27:33',NULL,NULL,'0'),(19,19,'Altar',NULL,NULL,'2016-01-12 15:27:57',NULL,NULL,'0'),(20,20,'Park',NULL,NULL,'2016-01-12 15:28:18',NULL,NULL,'0'),(21,21,'Astfel',NULL,NULL,'2016-01-12 15:28:39',NULL,NULL,'0'),(22,22,'Roşu luna',NULL,NULL,'2016-01-12 15:29:27',NULL,NULL,'0'),(23,23,'Ossharimashita şi de mâine',NULL,NULL,'2016-01-12 15:29:48',NULL,NULL,'0'),(24,24,'Mâine',NULL,NULL,'2016-01-12 15:30:09',NULL,NULL,'0'),(25,25,'Du-te.',NULL,NULL,'2016-01-12 15:30:30',NULL,NULL,'0'),(26,26,'Otsharimashita',NULL,NULL,'2016-01-12 15:30:50',NULL,NULL,'0'),(27,27,'John nu este britanic',NULL,NULL,'2016-01-12 15:32:29',NULL,NULL,'0'),(28,28,'John este britanic?',NULL,NULL,'2016-01-12 15:32:49',NULL,NULL,'0'),(29,29,'Mă duc la cinema cu fratele meu.',NULL,NULL,'2016-01-12 15:33:12',NULL,NULL,'0'),(30,30,'M-am dus la Ioan şi filmul teatre.',NULL,NULL,'2016-01-12 15:33:33',NULL,NULL,'0'),(31,31,'Mă duc la Ioan şi filmul teatre.',NULL,NULL,'2016-01-12 15:33:54',NULL,NULL,'0'),(32,32,'Mă duc la cinema pe o bicicletă.',NULL,NULL,'2016-01-12 15:34:12',NULL,NULL,'0'),(33,33,'John a mers la cinema pe o motocicleta?',NULL,NULL,'2016-01-12 15:34:31',NULL,NULL,'0'),(34,34,'John a mers la cinema pe o motocicleta',NULL,NULL,'2016-01-12 15:34:54',NULL,NULL,'0'),(35,36,'John merge la cinema pe o motocicleta?',NULL,NULL,'2016-01-12 15:40:01',NULL,NULL,'0'),(36,37,'John merge la cinema pe o motocicleta',NULL,NULL,'2016-01-12 15:40:22',NULL,NULL,'0'),(37,38,'Eu nu merg la cinema pe biciclete John?',NULL,NULL,'2016-01-12 15:40:45',NULL,NULL,'0'),(38,39,'Nu John merge la cinema cu bicicleta',NULL,NULL,'2016-01-12 15:41:05',NULL,NULL,'0'),(39,40,'John merge la cinema pe o motocicleta',NULL,NULL,'2016-01-12 15:41:24',NULL,NULL,'0'),(40,41,'John merge la cinema de biciclete',NULL,NULL,'2016-01-12 15:41:45',NULL,NULL,'0'),(41,42,'Ioan',NULL,NULL,'2016-01-12 15:42:04',NULL,NULL,'0');
/*!40000 ALTER TABLE `dict_ro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_ru`
--

DROP TABLE IF EXISTS `dict_ru`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_ru` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_ru`
--

LOCK TABLES `dict_ru` WRITE;
/*!40000 ALTER TABLE `dict_ru` DISABLE KEYS */;
INSERT INTO `dict_ru` VALUES (1,1,'Лунный свет',NULL,NULL,'2016-01-12 15:21:32',NULL,NULL,'0'),(2,2,'Новолуние',NULL,NULL,'2016-01-12 15:21:57',NULL,NULL,'0'),(3,3,'Джон — британский',NULL,NULL,'2016-01-12 15:22:20',NULL,NULL,'0'),(4,4,'Я пошел в кино с моим братом.',NULL,NULL,'2016-01-12 15:22:39',NULL,NULL,'0'),(5,5,'Я пошел в кино на велосипеде',NULL,NULL,'2016-01-12 15:23:02',NULL,NULL,'0'),(6,6,'Джон',NULL,NULL,'2016-01-12 15:23:23',NULL,NULL,'0'),(7,7,'Британский народ',NULL,NULL,'2016-01-12 15:23:44',NULL,NULL,'0'),(8,8,'Я',NULL,NULL,'2016-01-12 15:24:05',NULL,NULL,'0'),(9,9,'Мой брат',NULL,NULL,'2016-01-12 15:24:29',NULL,NULL,'0'),(10,10,'Кино',NULL,NULL,'2016-01-12 15:24:48',NULL,NULL,'0'),(11,11,'Я пошел',NULL,NULL,'2016-01-12 15:25:11',NULL,NULL,'0'),(12,12,'Велосипед',NULL,NULL,'2016-01-12 15:25:31',NULL,NULL,'0'),(13,13,'Лунное затмение',NULL,NULL,'2016-01-12 15:25:51',NULL,NULL,'0'),(14,14,'Мой брат является доктор.',NULL,NULL,'2016-01-12 15:26:15',NULL,NULL,'0'),(15,15,'Не прекращайте велосипед',NULL,NULL,'2016-01-12 15:26:35',NULL,NULL,'0'),(16,16,'Храмы и парки.',NULL,NULL,'2016-01-12 15:26:52',NULL,NULL,'0'),(17,17,'Доктор',NULL,NULL,'2016-01-12 15:27:14',NULL,NULL,'0'),(18,18,'Остановить',NULL,NULL,'2016-01-12 15:27:33',NULL,NULL,'0'),(19,19,'Храм',NULL,NULL,'2016-01-12 15:27:57',NULL,NULL,'0'),(20,20,'Парк',NULL,NULL,'2016-01-12 15:28:18',NULL,NULL,'0'),(21,21,'Такие',NULL,NULL,'2016-01-12 15:28:39',NULL,NULL,'0'),(22,22,'Красная Луна',NULL,NULL,'2016-01-12 15:29:27',NULL,NULL,'0'),(23,23,'Ossharimashita и завтра',NULL,NULL,'2016-01-12 15:29:48',NULL,NULL,'0'),(24,24,'Завтра',NULL,NULL,'2016-01-12 15:30:09',NULL,NULL,'0'),(25,25,'Перейти.',NULL,NULL,'2016-01-12 15:30:30',NULL,NULL,'0'),(26,26,'Otsharimashita',NULL,NULL,'2016-01-12 15:30:50',NULL,NULL,'0'),(27,27,'Джон не британский',NULL,NULL,'2016-01-12 15:32:29',NULL,NULL,'0'),(28,28,'Джон является британской?',NULL,NULL,'2016-01-12 15:32:49',NULL,NULL,'0'),(29,29,'Я иду в кино с моим братом.',NULL,NULL,'2016-01-12 15:33:12',NULL,NULL,'0'),(30,30,'Я пошел к Джон и кино театры.',NULL,NULL,'2016-01-12 15:33:33',NULL,NULL,'0'),(31,31,'Я иду к Джон и кино театры.',NULL,NULL,'2016-01-12 15:33:54',NULL,NULL,'0'),(32,32,'Я иду в кино на велосипеде.',NULL,NULL,'2016-01-12 15:34:12',NULL,NULL,'0'),(33,33,'Джон отправился в кино на велосипеде?',NULL,NULL,'2016-01-12 15:34:31',NULL,NULL,'0'),(34,34,'Джон отправился в кино на велосипеде',NULL,NULL,'2016-01-12 15:34:54',NULL,NULL,'0'),(35,36,'Джон идет в кино на велосипеде?',NULL,NULL,'2016-01-12 15:40:01',NULL,NULL,'0'),(36,37,'Джон идет в кино на велосипеде',NULL,NULL,'2016-01-12 15:40:22',NULL,NULL,'0'),(37,38,'Я не ходил в кино на велосипеде Джон?',NULL,NULL,'2016-01-12 15:40:45',NULL,NULL,'0'),(38,39,'Не Джон ходил в кино на велосипеде',NULL,NULL,'2016-01-12 15:41:05',NULL,NULL,'0'),(39,40,'Джон идут в кино на велосипеде',NULL,NULL,'2016-01-12 15:41:24',NULL,NULL,'0'),(40,41,'Джон идут в кино на велосипеде',NULL,NULL,'2016-01-12 15:41:45',NULL,NULL,'0'),(41,42,'Джон',NULL,NULL,'2016-01-12 15:42:04',NULL,NULL,'0');
/*!40000 ALTER TABLE `dict_ru` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_sk`
--

DROP TABLE IF EXISTS `dict_sk`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_sk` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_sk`
--

LOCK TABLES `dict_sk` WRITE;
/*!40000 ALTER TABLE `dict_sk` DISABLE KEYS */;
INSERT INTO `dict_sk` VALUES (1,1,'Mesačný svit',NULL,NULL,'2016-01-12 15:21:32',NULL,NULL,'0'),(2,2,'New Moon',NULL,NULL,'2016-01-12 15:21:57',NULL,NULL,'0'),(3,3,'John je britský príjemca',NULL,NULL,'2016-01-12 15:22:20',NULL,NULL,'0'),(4,4,'Išiel som do kina s mojím bratom.',NULL,NULL,'2016-01-12 15:22:39',NULL,NULL,'0'),(5,5,'Išiel som do kina na bicykli',NULL,NULL,'2016-01-12 15:23:02',NULL,NULL,'0'),(6,6,'John',NULL,NULL,'2016-01-12 15:23:23',NULL,NULL,'0'),(7,7,'Briti',NULL,NULL,'2016-01-12 15:23:44',NULL,NULL,'0'),(8,8,'Som',NULL,NULL,'2016-01-12 15:24:05',NULL,NULL,'0'),(9,9,'Môj brat',NULL,NULL,'2016-01-12 15:24:29',NULL,NULL,'0'),(10,10,'Kino',NULL,NULL,'2016-01-12 15:24:48',NULL,NULL,'0'),(11,11,'Išiel som',NULL,NULL,'2016-01-12 15:25:11',NULL,NULL,'0'),(12,12,'Kolo',NULL,NULL,'2016-01-12 15:25:31',NULL,NULL,'0'),(13,13,'Zatmenie mesiaca',NULL,NULL,'2016-01-12 15:25:51',NULL,NULL,'0'),(14,14,'Môj brat je lekár.',NULL,NULL,'2016-01-12 15:26:15',NULL,NULL,'0'),(15,15,'Neprestávajte bicykel',NULL,NULL,'2016-01-12 15:26:35',NULL,NULL,'0'),(16,16,'Svätyne a parkov.',NULL,NULL,'2016-01-12 15:26:52',NULL,NULL,'0'),(17,17,'Lekár',NULL,NULL,'2016-01-12 15:27:14',NULL,NULL,'0'),(18,18,'zastaviť',NULL,NULL,'2016-01-12 15:27:33',NULL,NULL,'0'),(19,19,'Svätyňa',NULL,NULL,'2016-01-12 15:27:57',NULL,NULL,'0'),(20,20,'Park',NULL,NULL,'2016-01-12 15:28:18',NULL,NULL,'0'),(21,21,'Takéto',NULL,NULL,'2016-01-12 15:28:39',NULL,NULL,'0'),(22,22,'Červený mesiac',NULL,NULL,'2016-01-12 15:29:27',NULL,NULL,'0'),(23,23,'Ossharimashita a zajtra',NULL,NULL,'2016-01-12 15:29:48',NULL,NULL,'0'),(24,24,'Zajtra',NULL,NULL,'2016-01-12 15:30:09',NULL,NULL,'0'),(25,25,'Ísť.',NULL,NULL,'2016-01-12 15:30:30',NULL,NULL,'0'),(26,26,'Otsharimashita',NULL,NULL,'2016-01-12 15:30:50',NULL,NULL,'0'),(27,27,'John nie je britský',NULL,NULL,'2016-01-12 15:32:29',NULL,NULL,'0'),(28,28,'John je britský?',NULL,NULL,'2016-01-12 15:32:49',NULL,NULL,'0'),(29,29,'Chodím do kina s mojím bratom.',NULL,NULL,'2016-01-12 15:33:12',NULL,NULL,'0'),(30,30,'Išiel som sa John a film divadiel.',NULL,NULL,'2016-01-12 15:33:33',NULL,NULL,'0'),(31,31,'Idem na John a film divadiel.',NULL,NULL,'2016-01-12 15:33:54',NULL,NULL,'0'),(32,32,'Idem do kina na bicykli.',NULL,NULL,'2016-01-12 15:34:12',NULL,NULL,'0'),(33,33,'John šiel do kina na bicykli?',NULL,NULL,'2016-01-12 15:34:31',NULL,NULL,'0'),(34,34,'John šiel do kina na bicykli',NULL,NULL,'2016-01-12 15:34:54',NULL,NULL,'0'),(35,36,'John ide do kina na bicykli?',NULL,NULL,'2016-01-12 15:40:01',NULL,NULL,'0'),(36,37,'John ide do kina na bicykli',NULL,NULL,'2016-01-12 15:40:22',NULL,NULL,'0'),(37,38,'Som nešla do kina na bicykli John?',NULL,NULL,'2016-01-12 15:40:45',NULL,NULL,'0'),(38,39,'John nešla do kina na bicykli',NULL,NULL,'2016-01-12 15:41:05',NULL,NULL,'0'),(39,40,'John ísť do kina na bicykli',NULL,NULL,'2016-01-12 15:41:24',NULL,NULL,'0'),(40,41,'John ísť do kina na bicykli',NULL,NULL,'2016-01-12 15:41:45',NULL,NULL,'0'),(41,42,'John',NULL,NULL,'2016-01-12 15:42:04',NULL,NULL,'0');
/*!40000 ALTER TABLE `dict_sk` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_sl`
--

DROP TABLE IF EXISTS `dict_sl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_sl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_sl`
--

LOCK TABLES `dict_sl` WRITE;
/*!40000 ALTER TABLE `dict_sl` DISABLE KEYS */;
INSERT INTO `dict_sl` VALUES (1,1,'Mesečini',NULL,NULL,'2016-01-12 15:21:32',NULL,NULL,'0'),(2,2,'New Moon',NULL,NULL,'2016-01-12 15:21:57',NULL,NULL,'0'),(3,3,'John je British',NULL,NULL,'2016-01-12 15:22:20',NULL,NULL,'0'),(4,4,'Sem šel v kino z mojim bratom.',NULL,NULL,'2016-01-12 15:22:39',NULL,NULL,'0'),(5,5,'Sem šel v kino na kolesu',NULL,NULL,'2016-01-12 15:23:02',NULL,NULL,'0'),(6,6,'John',NULL,NULL,'2016-01-12 15:23:23',NULL,NULL,'0'),(7,7,'Britanci',NULL,NULL,'2016-01-12 15:23:44',NULL,NULL,'0'),(8,8,'Sem',NULL,NULL,'2016-01-12 15:24:05',NULL,NULL,'0'),(9,9,'Moj brat',NULL,NULL,'2016-01-12 15:24:29',NULL,NULL,'0'),(10,10,'Kino',NULL,NULL,'2016-01-12 15:24:48',NULL,NULL,'0'),(11,11,'Sem šel',NULL,NULL,'2016-01-12 15:25:11',NULL,NULL,'0'),(12,12,'Kolo',NULL,NULL,'2016-01-12 15:25:31',NULL,NULL,'0'),(13,13,'Lunin mrk',NULL,NULL,'2016-01-12 15:25:51',NULL,NULL,'0'),(14,14,'Moj brat je zdravnik.',NULL,NULL,'2016-01-12 15:26:15',NULL,NULL,'0'),(15,15,'Ne prenehajte kolo',NULL,NULL,'2016-01-12 15:26:35',NULL,NULL,'0'),(16,16,'Svetišča in parki.',NULL,NULL,'2016-01-12 15:26:52',NULL,NULL,'0'),(17,17,'Zdravnik',NULL,NULL,'2016-01-12 15:27:14',NULL,NULL,'0'),(18,18,'ustavi se',NULL,NULL,'2016-01-12 15:27:33',NULL,NULL,'0'),(19,19,'Svetišče',NULL,NULL,'2016-01-12 15:27:57',NULL,NULL,'0'),(20,20,'Park',NULL,NULL,'2016-01-12 15:28:18',NULL,NULL,'0'),(21,21,'Take',NULL,NULL,'2016-01-12 15:28:39',NULL,NULL,'0'),(22,22,'Rdeča luna',NULL,NULL,'2016-01-12 15:29:27',NULL,NULL,'0'),(23,23,'Ossharimashita in jutri',NULL,NULL,'2016-01-12 15:29:48',NULL,NULL,'0'),(24,24,'Jutri',NULL,NULL,'2016-01-12 15:30:09',NULL,NULL,'0'),(25,25,'Iti.',NULL,NULL,'2016-01-12 15:30:30',NULL,NULL,'0'),(26,26,'Otsharimashita',NULL,NULL,'2016-01-12 15:30:50',NULL,NULL,'0'),(27,27,'John ni British',NULL,NULL,'2016-01-12 15:32:29',NULL,NULL,'0'),(28,28,'John je britanski?',NULL,NULL,'2016-01-12 15:32:49',NULL,NULL,'0'),(29,29,'Sem šel v kino z mojim bratom.',NULL,NULL,'2016-01-12 15:33:12',NULL,NULL,'0'),(30,30,'Sem šel za John in film gledališča.',NULL,NULL,'2016-01-12 15:33:33',NULL,NULL,'0'),(31,31,'Grem za John in film gledališča.',NULL,NULL,'2016-01-12 15:33:54',NULL,NULL,'0'),(32,32,'Grem v kino, na kolo.',NULL,NULL,'2016-01-12 15:34:12',NULL,NULL,'0'),(33,33,'John odšel v kino na kolesu?',NULL,NULL,'2016-01-12 15:34:31',NULL,NULL,'0'),(34,34,'John odšel v kino na kolesu',NULL,NULL,'2016-01-12 15:34:54',NULL,NULL,'0'),(35,36,'John gre v kino na kolesu?',NULL,NULL,'2016-01-12 15:40:01',NULL,NULL,'0'),(36,37,'John gre v kino na kolesu',NULL,NULL,'2016-01-12 15:40:22',NULL,NULL,'0'),(37,38,'Nisem šel v kino na kolesu John?',NULL,NULL,'2016-01-12 15:40:45',NULL,NULL,'0'),(38,39,'Ni John šel v kino s kolesom',NULL,NULL,'2016-01-12 15:41:05',NULL,NULL,'0'),(39,40,'John iti v kino na kolesu',NULL,NULL,'2016-01-12 15:41:24',NULL,NULL,'0'),(40,41,'John iti v kino s kolesom',NULL,NULL,'2016-01-12 15:41:45',NULL,NULL,'0'),(41,42,'John',NULL,NULL,'2016-01-12 15:42:04',NULL,NULL,'0');
/*!40000 ALTER TABLE `dict_sl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_sq`
--

DROP TABLE IF EXISTS `dict_sq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_sq` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_sq`
--

LOCK TABLES `dict_sq` WRITE;
/*!40000 ALTER TABLE `dict_sq` DISABLE KEYS */;
/*!40000 ALTER TABLE `dict_sq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_sr`
--

DROP TABLE IF EXISTS `dict_sr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_sr` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_sr`
--

LOCK TABLES `dict_sr` WRITE;
/*!40000 ALTER TABLE `dict_sr` DISABLE KEYS */;
/*!40000 ALTER TABLE `dict_sr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_sv`
--

DROP TABLE IF EXISTS `dict_sv`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_sv` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_sv`
--

LOCK TABLES `dict_sv` WRITE;
/*!40000 ALTER TABLE `dict_sv` DISABLE KEYS */;
INSERT INTO `dict_sv` VALUES (1,1,'Moonlight',NULL,NULL,'2016-01-12 15:21:32',NULL,NULL,'0'),(2,2,'New Moon',NULL,NULL,'2016-01-12 15:21:57',NULL,NULL,'0'),(3,3,'John är en brittisk',NULL,NULL,'2016-01-12 15:22:20',NULL,NULL,'0'),(4,4,'Jag gick på bio med min bror.',NULL,NULL,'2016-01-12 15:22:39',NULL,NULL,'0'),(5,5,'Jag gick på bio på cykel',NULL,NULL,'2016-01-12 15:23:02',NULL,NULL,'0'),(6,6,'John',NULL,NULL,'2016-01-12 15:23:23',NULL,NULL,'0'),(7,7,'Brittiska folket',NULL,NULL,'2016-01-12 15:23:44',NULL,NULL,'0'),(8,8,'Jag',NULL,NULL,'2016-01-12 15:24:05',NULL,NULL,'0'),(9,9,'Min bror',NULL,NULL,'2016-01-12 15:24:29',NULL,NULL,'0'),(10,10,'Cinema',NULL,NULL,'2016-01-12 15:24:48',NULL,NULL,'0'),(11,11,'Jag gick',NULL,NULL,'2016-01-12 15:25:11',NULL,NULL,'0'),(12,12,'Cykel',NULL,NULL,'2016-01-12 15:25:31',NULL,NULL,'0'),(13,13,'Månförmörkelse',NULL,NULL,'2016-01-12 15:25:51',NULL,NULL,'0'),(14,14,'Min bror är en läkare.',NULL,NULL,'2016-01-12 15:26:15',NULL,NULL,'0'),(15,15,'Sluta inte cykeln',NULL,NULL,'2016-01-12 15:26:35',NULL,NULL,'0'),(16,16,'Helgedomar och parker.',NULL,NULL,'2016-01-12 15:26:52',NULL,NULL,'0'),(17,17,'Läkare',NULL,NULL,'2016-01-12 15:27:14',NULL,NULL,'0'),(18,18,'Stanna',NULL,NULL,'2016-01-12 15:27:33',NULL,NULL,'0'),(19,19,'Shrine',NULL,NULL,'2016-01-12 15:27:57',NULL,NULL,'0'),(20,20,'Park',NULL,NULL,'2016-01-12 15:28:18',NULL,NULL,'0'),(21,21,'Sådana',NULL,NULL,'2016-01-12 15:28:39',NULL,NULL,'0'),(22,22,'Röd måne',NULL,NULL,'2016-01-12 15:29:27',NULL,NULL,'0'),(23,23,'Ossharimashita och i morgon',NULL,NULL,'2016-01-12 15:29:48',NULL,NULL,'0'),(24,24,'I morgon',NULL,NULL,'2016-01-12 15:30:09',NULL,NULL,'0'),(25,25,'Gå.',NULL,NULL,'2016-01-12 15:30:30',NULL,NULL,'0'),(26,26,'Otsharimashita',NULL,NULL,'2016-01-12 15:30:50',NULL,NULL,'0'),(27,27,'John är inte brittiska',NULL,NULL,'2016-01-12 15:32:29',NULL,NULL,'0'),(28,28,'John är brittiska?',NULL,NULL,'2016-01-12 15:32:49',NULL,NULL,'0'),(29,29,'Jag går på bio med min bror.',NULL,NULL,'2016-01-12 15:33:12',NULL,NULL,'0'),(30,30,'Jag gick till John och film teatrar.',NULL,NULL,'2016-01-12 15:33:33',NULL,NULL,'0'),(31,31,'Jag går till John och film teatrar.',NULL,NULL,'2016-01-12 15:33:54',NULL,NULL,'0'),(32,32,'Jag går på bio på en cykel.',NULL,NULL,'2016-01-12 15:34:12',NULL,NULL,'0'),(33,33,'John gick på bio på en cykel?',NULL,NULL,'2016-01-12 15:34:31',NULL,NULL,'0'),(34,34,'John gick på bio på cykel',NULL,NULL,'2016-01-12 15:34:54',NULL,NULL,'0'),(35,36,'John går på bio på en cykel?',NULL,NULL,'2016-01-12 15:40:01',NULL,NULL,'0'),(36,37,'John går till bion på cykel',NULL,NULL,'2016-01-12 15:40:22',NULL,NULL,'0'),(37,38,'Jag gick inte på bio på cykel John?',NULL,NULL,'2016-01-12 15:40:45',NULL,NULL,'0'),(38,39,'John gick inte på bio med cykel',NULL,NULL,'2016-01-12 15:41:05',NULL,NULL,'0'),(39,40,'John går på bio på cykel',NULL,NULL,'2016-01-12 15:41:24',NULL,NULL,'0'),(40,41,'John går på bio med cykel',NULL,NULL,'2016-01-12 15:41:45',NULL,NULL,'0'),(41,42,'John',NULL,NULL,'2016-01-12 15:42:04',NULL,NULL,'0');
/*!40000 ALTER TABLE `dict_sv` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_sw`
--

DROP TABLE IF EXISTS `dict_sw`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_sw` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_sw`
--

LOCK TABLES `dict_sw` WRITE;
/*!40000 ALTER TABLE `dict_sw` DISABLE KEYS */;
/*!40000 ALTER TABLE `dict_sw` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_ta`
--

DROP TABLE IF EXISTS `dict_ta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_ta` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_ta`
--

LOCK TABLES `dict_ta` WRITE;
/*!40000 ALTER TABLE `dict_ta` DISABLE KEYS */;
/*!40000 ALTER TABLE `dict_ta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_te`
--

DROP TABLE IF EXISTS `dict_te`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_te` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_te`
--

LOCK TABLES `dict_te` WRITE;
/*!40000 ALTER TABLE `dict_te` DISABLE KEYS */;
/*!40000 ALTER TABLE `dict_te` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_th`
--

DROP TABLE IF EXISTS `dict_th`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_th` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_th`
--

LOCK TABLES `dict_th` WRITE;
/*!40000 ALTER TABLE `dict_th` DISABLE KEYS */;
INSERT INTO `dict_th` VALUES (1,1,'แสงจันทร์',NULL,NULL,'2016-01-12 15:21:32',NULL,NULL,'0'),(2,2,'ดวงจันทร์ใหม่',NULL,NULL,'2016-01-12 15:21:57',NULL,NULL,'0'),(3,3,'จอห์นเป็นที่อังกฤษ',NULL,NULL,'2016-01-12 15:22:20',NULL,NULL,'0'),(4,4,'ผมไปโรงภาพยนตร์กับพี่ชายของฉัน',NULL,NULL,'2016-01-12 15:22:39',NULL,NULL,'0'),(5,5,'ผมเดินไปโรงภาพยนตร์บนจักรยาน',NULL,NULL,'2016-01-12 15:23:02',NULL,NULL,'0'),(6,6,'จอห์น',NULL,NULL,'2016-01-12 15:23:23',NULL,NULL,'0'),(7,7,'คน',NULL,NULL,'2016-01-12 15:23:44',NULL,NULL,'0'),(8,8,'ฉัน',NULL,NULL,'2016-01-12 15:24:05',NULL,NULL,'0'),(9,9,'พี่ชายของฉัน',NULL,NULL,'2016-01-12 15:24:29',NULL,NULL,'0'),(10,10,'โรงภาพยนตร์',NULL,NULL,'2016-01-12 15:24:48',NULL,NULL,'0'),(11,11,'ฉันไป',NULL,NULL,'2016-01-12 15:25:11',NULL,NULL,'0'),(12,12,'จักรยาน',NULL,NULL,'2016-01-12 15:25:31',NULL,NULL,'0'),(13,13,'จันทรคราส',NULL,NULL,'2016-01-12 15:25:51',NULL,NULL,'0'),(14,14,'พี่ชายเป็นหมอ',NULL,NULL,'2016-01-12 15:26:15',NULL,NULL,'0'),(15,15,'หยุดจักรยาน',NULL,NULL,'2016-01-12 15:26:35',NULL,NULL,'0'),(16,16,'ศาลเจ้าและสวนสาธารณะ',NULL,NULL,'2016-01-12 15:26:52',NULL,NULL,'0'),(17,17,'แพทย์',NULL,NULL,'2016-01-12 15:27:14',NULL,NULL,'0'),(18,18,'หยุด',NULL,NULL,'2016-01-12 15:27:33',NULL,NULL,'0'),(19,19,'ศาลเจ้า',NULL,NULL,'2016-01-12 15:27:57',NULL,NULL,'0'),(20,20,'สวน',NULL,NULL,'2016-01-12 15:28:18',NULL,NULL,'0'),(21,21,'ดังกล่าว',NULL,NULL,'2016-01-12 15:28:39',NULL,NULL,'0'),(22,22,'ดวงจันทร์สีแดง',NULL,NULL,'2016-01-12 15:29:27',NULL,NULL,'0'),(23,23,'Ossharimashita และวันพรุ่งนี้',NULL,NULL,'2016-01-12 15:29:48',NULL,NULL,'0'),(24,24,'วันพรุ่งนี้',NULL,NULL,'2016-01-12 15:30:09',NULL,NULL,'0'),(25,25,'ไป',NULL,NULL,'2016-01-12 15:30:30',NULL,NULL,'0'),(26,26,'Otsharimashita',NULL,NULL,'2016-01-12 15:30:50',NULL,NULL,'0'),(27,27,'จอห์นไม่ใช่อังกฤษ',NULL,NULL,'2016-01-12 15:32:29',NULL,NULL,'0'),(28,28,'จอห์นเป็นอังกฤษ',NULL,NULL,'2016-01-12 15:32:49',NULL,NULL,'0'),(29,29,'ฉันไปถึงกับพี่ชาย',NULL,NULL,'2016-01-12 15:33:12',NULL,NULL,'0'),(30,30,'ผมไปโรงภาพยนตร์จอห์นและภาพยนตร์',NULL,NULL,'2016-01-12 15:33:33',NULL,NULL,'0'),(31,31,'ฉันไปโรงภาพยนตร์จอห์นและภาพยนตร์',NULL,NULL,'2016-01-12 15:33:54',NULL,NULL,'0'),(32,32,'ผมไปถึงรถ',NULL,NULL,'2016-01-12 15:34:12',NULL,NULL,'0'),(33,33,'จอห์นไปโรงภาพยนตร์บนจักรยาน',NULL,NULL,'2016-01-12 15:34:31',NULL,NULL,'0'),(34,34,'จอห์นไปในโรงภาพยนตร์ในจักรยาน',NULL,NULL,'2016-01-12 15:34:54',NULL,NULL,'0'),(35,36,'จอห์นไปโรงภาพยนตร์บนจักรยาน',NULL,NULL,'2016-01-12 15:40:01',NULL,NULL,'0'),(36,37,'จอห์นไปในโรงภาพยนตร์ในจักรยาน',NULL,NULL,'2016-01-12 15:40:22',NULL,NULL,'0'),(37,38,'ไม่ได้ไปโรงภาพยนตร์บนจักรยานจอห์น',NULL,NULL,'2016-01-12 15:40:45',NULL,NULL,'0'),(38,39,'ไม่จอห์นไปถึงขี่จักรยาน',NULL,NULL,'2016-01-12 15:41:05',NULL,NULL,'0'),(39,40,'จอห์นไปในโรงภาพยนตร์ในจักรยาน',NULL,NULL,'2016-01-12 15:41:24',NULL,NULL,'0'),(40,41,'จอห์นไปในโรงภาพยนตร์ โดยจักรยาน',NULL,NULL,'2016-01-12 15:41:45',NULL,NULL,'0'),(41,42,'จอห์น',NULL,NULL,'2016-01-12 15:42:04',NULL,NULL,'0');
/*!40000 ALTER TABLE `dict_th` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_tl`
--

DROP TABLE IF EXISTS `dict_tl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_tl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_tl`
--

LOCK TABLES `dict_tl` WRITE;
/*!40000 ALTER TABLE `dict_tl` DISABLE KEYS */;
/*!40000 ALTER TABLE `dict_tl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_tr`
--

DROP TABLE IF EXISTS `dict_tr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_tr` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_tr`
--

LOCK TABLES `dict_tr` WRITE;
/*!40000 ALTER TABLE `dict_tr` DISABLE KEYS */;
INSERT INTO `dict_tr` VALUES (1,1,'Ay ışığı',NULL,NULL,'2016-01-12 15:21:32',NULL,NULL,'0'),(2,2,'Yeni ay',NULL,NULL,'2016-01-12 15:21:57',NULL,NULL,'0'),(3,3,'İngiliz John bir',NULL,NULL,'2016-01-12 15:22:20',NULL,NULL,'0'),(4,4,'Kardeşimle sinemaya gittim.',NULL,NULL,'2016-01-12 15:22:39',NULL,NULL,'0'),(5,5,'Bisikletle sinemaya gittim',NULL,NULL,'2016-01-12 15:23:02',NULL,NULL,'0'),(6,6,'John',NULL,NULL,'2016-01-12 15:23:23',NULL,NULL,'0'),(7,7,'İngiliz insanlar',NULL,NULL,'2016-01-12 15:23:44',NULL,NULL,'0'),(8,8,'Ben',NULL,NULL,'2016-01-12 15:24:05',NULL,NULL,'0'),(9,9,'Kardeşim',NULL,NULL,'2016-01-12 15:24:29',NULL,NULL,'0'),(10,10,'Sinema',NULL,NULL,'2016-01-12 15:24:48',NULL,NULL,'0'),(11,11,'Ben gittim',NULL,NULL,'2016-01-12 15:25:11',NULL,NULL,'0'),(12,12,'Bisiklet',NULL,NULL,'2016-01-12 15:25:31',NULL,NULL,'0'),(13,13,'Ay tutulması',NULL,NULL,'2016-01-12 15:25:51',NULL,NULL,'0'),(14,14,'Kardeşim bir doktordur.',NULL,NULL,'2016-01-12 15:26:15',NULL,NULL,'0'),(15,15,'Bisiklet durmak yok',NULL,NULL,'2016-01-12 15:26:35',NULL,NULL,'0'),(16,16,'Türbelerin ve parklar.',NULL,NULL,'2016-01-12 15:26:52',NULL,NULL,'0'),(17,17,'Doktor',NULL,NULL,'2016-01-12 15:27:14',NULL,NULL,'0'),(18,18,'Dur',NULL,NULL,'2016-01-12 15:27:33',NULL,NULL,'0'),(19,19,'Tapınak',NULL,NULL,'2016-01-12 15:27:57',NULL,NULL,'0'),(20,20,'Park',NULL,NULL,'2016-01-12 15:28:18',NULL,NULL,'0'),(21,21,'Böyle',NULL,NULL,'2016-01-12 15:28:39',NULL,NULL,'0'),(22,22,'Red Moon',NULL,NULL,'2016-01-12 15:29:27',NULL,NULL,'0'),(23,23,'Ossharimashita ve yarın',NULL,NULL,'2016-01-12 15:29:48',NULL,NULL,'0'),(24,24,'Yarın',NULL,NULL,'2016-01-12 15:30:09',NULL,NULL,'0'),(25,25,'Git.',NULL,NULL,'2016-01-12 15:30:30',NULL,NULL,'0'),(26,26,'Otsharimashita',NULL,NULL,'2016-01-12 15:30:50',NULL,NULL,'0'),(27,27,'John İngiliz değil',NULL,NULL,'2016-01-12 15:32:29',NULL,NULL,'0'),(28,28,'John İngiliz mi?',NULL,NULL,'2016-01-12 15:32:49',NULL,NULL,'0'),(29,29,'Kardeşimle sinemaya gitmek.',NULL,NULL,'2016-01-12 15:33:12',NULL,NULL,'0'),(30,30,'John ve film sinemalarda gitti.',NULL,NULL,'2016-01-12 15:33:33',NULL,NULL,'0'),(31,31,'John ve film sinemalarda gidin.',NULL,NULL,'2016-01-12 15:33:54',NULL,NULL,'0'),(32,32,'Bisikletle sinemaya gitmek.',NULL,NULL,'2016-01-12 15:34:12',NULL,NULL,'0'),(33,33,'John, bisikletle sinemaya gittin mi?',NULL,NULL,'2016-01-12 15:34:31',NULL,NULL,'0'),(34,34,'John sinemaya bisikletle gitti.',NULL,NULL,'2016-01-12 15:34:54',NULL,NULL,'0'),(35,36,'John sinemaya Kiralama devam ediyor?',NULL,NULL,'2016-01-12 15:40:01',NULL,NULL,'0'),(36,37,'John sinemaya Kiralama devam ediyor.',NULL,NULL,'2016-01-12 15:40:22',NULL,NULL,'0'),(37,38,'Bisikletle John sinemaya gitmedin?',NULL,NULL,'2016-01-12 15:40:45',NULL,NULL,'0'),(38,39,'John Bisiklet tarafından sinemaya gitmedin mi',NULL,NULL,'2016-01-12 15:41:05',NULL,NULL,'0'),(39,40,'John bisikletle sinemaya gitmek',NULL,NULL,'2016-01-12 15:41:24',NULL,NULL,'0'),(40,41,'John Bisiklet tarafından sinemaya gitmek',NULL,NULL,'2016-01-12 15:41:45',NULL,NULL,'0'),(41,42,'John',NULL,NULL,'2016-01-12 15:42:04',NULL,NULL,'0');
/*!40000 ALTER TABLE `dict_tr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_uk`
--

DROP TABLE IF EXISTS `dict_uk`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_uk` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_uk`
--

LOCK TABLES `dict_uk` WRITE;
/*!40000 ALTER TABLE `dict_uk` DISABLE KEYS */;
INSERT INTO `dict_uk` VALUES (1,1,'Місячне сяйво',NULL,NULL,'2016-01-12 15:21:32',NULL,NULL,'0'),(2,2,'Молодий місяць',NULL,NULL,'2016-01-12 15:21:57',NULL,NULL,'0'),(3,3,'Джон є Британської',NULL,NULL,'2016-01-12 15:22:20',NULL,NULL,'0'),(4,4,'Я пішов в кіно з моїм братом.',NULL,NULL,'2016-01-12 15:22:39',NULL,NULL,'0'),(5,5,'Я ходив до кінотеатру на велосипеді',NULL,NULL,'2016-01-12 15:23:02',NULL,NULL,'0'),(6,6,'Джон',NULL,NULL,'2016-01-12 15:23:23',NULL,NULL,'0'),(7,7,'Британський народ',NULL,NULL,'2016-01-12 15:23:44',NULL,NULL,'0'),(8,8,'Я',NULL,NULL,'2016-01-12 15:24:05',NULL,NULL,'0'),(9,9,'Мій брат',NULL,NULL,'2016-01-12 15:24:29',NULL,NULL,'0'),(10,10,'Кіно',NULL,NULL,'2016-01-12 15:24:48',NULL,NULL,'0'),(11,11,'Я пішов',NULL,NULL,'2016-01-12 15:25:11',NULL,NULL,'0'),(12,12,'Велосипед',NULL,NULL,'2016-01-12 15:25:31',NULL,NULL,'0'),(13,13,'Місячне затемнення',NULL,NULL,'2016-01-12 15:25:51',NULL,NULL,'0'),(14,14,'Мій брат — лікар.',NULL,NULL,'2016-01-12 15:26:15',NULL,NULL,'0'),(15,15,'Не зупиняйтеся на велосипеді',NULL,NULL,'2016-01-12 15:26:35',NULL,NULL,'0'),(16,16,'Святині і парків.',NULL,NULL,'2016-01-12 15:26:52',NULL,NULL,'0'),(17,17,'Лікар',NULL,NULL,'2016-01-12 15:27:14',NULL,NULL,'0'),(18,18,'Зупинити',NULL,NULL,'2016-01-12 15:27:33',NULL,NULL,'0'),(19,19,'Храм.',NULL,NULL,'2016-01-12 15:27:57',NULL,NULL,'0'),(20,20,'Парк',NULL,NULL,'2016-01-12 15:28:18',NULL,NULL,'0'),(21,21,'Такі',NULL,NULL,'2016-01-12 15:28:39',NULL,NULL,'0'),(22,22,'Червоний місяць',NULL,NULL,'2016-01-12 15:29:27',NULL,NULL,'0'),(23,23,'Ossharimashita і завтра',NULL,NULL,'2016-01-12 15:29:48',NULL,NULL,'0'),(24,24,'Завтра',NULL,NULL,'2016-01-12 15:30:09',NULL,NULL,'0'),(25,25,'Йти.',NULL,NULL,'2016-01-12 15:30:30',NULL,NULL,'0'),(26,26,'Otsharimashita',NULL,NULL,'2016-01-12 15:30:50',NULL,NULL,'0'),(27,27,'Джон не є британський',NULL,NULL,'2016-01-12 15:32:29',NULL,NULL,'0'),(28,28,'Джон є британський?',NULL,NULL,'2016-01-12 15:32:49',NULL,NULL,'0'),(29,29,'Я піти в кіно з моїм братом.',NULL,NULL,'2016-01-12 15:33:12',NULL,NULL,'0'),(30,30,'Я пішов до Джон і кінотеатри.',NULL,NULL,'2016-01-12 15:33:33',NULL,NULL,'0'),(31,31,'Я йду Джон і кінотеатри.',NULL,NULL,'2016-01-12 15:33:54',NULL,NULL,'0'),(32,32,'Я йду в кінотеатр на велосипеді.',NULL,NULL,'2016-01-12 15:34:12',NULL,NULL,'0'),(33,33,'Джон ходив до кінотеатру на велосипеді?',NULL,NULL,'2016-01-12 15:34:31',NULL,NULL,'0'),(34,34,'Джон ходив до кінотеатру на велосипеді',NULL,NULL,'2016-01-12 15:34:54',NULL,NULL,'0'),(35,36,'Джон піде в кінотеатр на велосипеді?',NULL,NULL,'2016-01-12 15:40:01',NULL,NULL,'0'),(36,37,'Джон піде в кінотеатр на велосипеді',NULL,NULL,'2016-01-12 15:40:22',NULL,NULL,'0'),(37,38,'Я не ходив до кінотеатру на велосипеді Джон?',NULL,NULL,'2016-01-12 15:40:45',NULL,NULL,'0'),(38,39,'Не Джон сходити в кінотеатр на велосипеді',NULL,NULL,'2016-01-12 15:41:05',NULL,NULL,'0'),(39,40,'Джон сходити в кінотеатр на велосипеді',NULL,NULL,'2016-01-12 15:41:24',NULL,NULL,'0'),(40,41,'Джон сходити в кінотеатр на велосипеді',NULL,NULL,'2016-01-12 15:41:45',NULL,NULL,'0'),(41,42,'Джон',NULL,NULL,'2016-01-12 15:42:04',NULL,NULL,'0');
/*!40000 ALTER TABLE `dict_uk` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_ur`
--

DROP TABLE IF EXISTS `dict_ur`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_ur` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_ur`
--

LOCK TABLES `dict_ur` WRITE;
/*!40000 ALTER TABLE `dict_ur` DISABLE KEYS */;
INSERT INTO `dict_ur` VALUES (1,1,'چاندنی',NULL,NULL,'2016-01-12 15:21:32',NULL,NULL,'0'),(2,2,'نئے چاند',NULL,NULL,'2016-01-12 15:21:57',NULL,NULL,'0'),(3,3,'ایک برطانوی جان ہے',NULL,NULL,'2016-01-12 15:22:20',NULL,NULL,'0'),(4,4,'میں اپنے بھائی کے ساتھ سینما گھر کو چلا گیا ۔',NULL,NULL,'2016-01-12 15:22:39',NULL,NULL,'0'),(5,5,'میں ایک موٹر سائیکل پر سینما گھر کو چلا گیا',NULL,NULL,'2016-01-12 15:23:02',NULL,NULL,'0'),(6,6,'جان',NULL,NULL,'2016-01-12 15:23:23',NULL,NULL,'0'),(7,7,'برطانوی قوم',NULL,NULL,'2016-01-12 15:23:44',NULL,NULL,'0'),(8,8,'میں',NULL,NULL,'2016-01-12 15:24:05',NULL,NULL,'0'),(9,9,'میرا بھائی',NULL,NULL,'2016-01-12 15:24:29',NULL,NULL,'0'),(10,10,'سینما',NULL,NULL,'2016-01-12 15:24:48',NULL,NULL,'0'),(11,11,'میں چلا گیا',NULL,NULL,'2016-01-12 15:25:11',NULL,NULL,'0'),(12,12,'موٹر سائیکل',NULL,NULL,'2016-01-12 15:25:31',NULL,NULL,'0'),(13,13,'چاند گرہن',NULL,NULL,'2016-01-12 15:25:51',NULL,NULL,'0'),(14,14,'میرے بھائی ایک ڈاکٹر ہے ۔',NULL,NULL,'2016-01-12 15:26:15',NULL,NULL,'0'),(15,15,'موٹر سائیکل نہیں روک سکتا',NULL,NULL,'2016-01-12 15:26:35',NULL,NULL,'0'),(16,16,'مزارات اور پارک ہے ۔',NULL,NULL,'2016-01-12 15:26:52',NULL,NULL,'0'),(17,17,'ڈاکٹر',NULL,NULL,'2016-01-12 15:27:14',NULL,NULL,'0'),(18,18,'روک',NULL,NULL,'2016-01-12 15:27:33',NULL,NULL,'0'),(19,19,'مزار پر',NULL,NULL,'2016-01-12 15:27:57',NULL,NULL,'0'),(20,20,'پارک',NULL,NULL,'2016-01-12 15:28:18',NULL,NULL,'0'),(21,21,'اس طرح',NULL,NULL,'2016-01-12 15:28:39',NULL,NULL,'0'),(22,22,'سرخ چاند',NULL,NULL,'2016-01-12 15:29:27',NULL,NULL,'0'),(23,23,'عثشآرامشات اور کل',NULL,NULL,'2016-01-12 15:29:48',NULL,NULL,'0'),(24,24,'کل',NULL,NULL,'2016-01-12 15:30:09',NULL,NULL,'0'),(25,25,'جاتے ہیں ۔',NULL,NULL,'2016-01-12 15:30:30',NULL,NULL,'0'),(26,26,'اوٹشآرامشات',NULL,NULL,'2016-01-12 15:30:50',NULL,NULL,'0'),(27,27,'جان برطانوی نہیں ہے',NULL,NULL,'2016-01-12 15:32:29',NULL,NULL,'0'),(28,28,'جان برطانوی ہے؟',NULL,NULL,'2016-01-12 15:32:49',NULL,NULL,'0'),(29,29,'میں اپنے بھائی کے ساتھ سینما کو جاتے ہیں ۔',NULL,NULL,'2016-01-12 15:33:12',NULL,NULL,'0'),(30,30,'میں جان اور فلم تھیٹر کے لئے چلا گیا ۔',NULL,NULL,'2016-01-12 15:33:33',NULL,NULL,'0'),(31,31,'میں جان اور فلم تھیٹر کے لئے جاتے ہیں ۔',NULL,NULL,'2016-01-12 15:33:54',NULL,NULL,'0'),(32,32,'میں ایک سائیکل پر ہی سینما کو جاتے ہیں ۔',NULL,NULL,'2016-01-12 15:34:12',NULL,NULL,'0'),(33,33,'جان پر ایک موٹر سائیکل کو ایک سینما کے لئے چلے گئے؟',NULL,NULL,'2016-01-12 15:34:31',NULL,NULL,'0'),(34,34,'جان پر ایک موٹر سائیکل کو ایک سینما کے لئے چلے گئے ہیں ۔',NULL,NULL,'2016-01-12 15:34:54',NULL,NULL,'0'),(35,36,'جان پر ایک موٹر سائیکل کو سینما گھر جاتا ہے؟',NULL,NULL,'2016-01-12 15:40:01',NULL,NULL,'0'),(36,37,'جان کے لئے سینما گھر پر ایک موٹر سائیکل چلا جاتا ہے ۔',NULL,NULL,'2016-01-12 15:40:22',NULL,NULL,'0'),(37,38,'میں سینما کے لئے ایک سائیکل پر جان didn فوٹ چلے جاؤ؟',NULL,NULL,'2016-01-12 15:40:45',NULL,NULL,'0'),(38,39,'جان کو ایک سینما کے لیے سائیکل کی طرف سے didn فوٹ چلے جاؤ',NULL,NULL,'2016-01-12 15:41:05',NULL,NULL,'0'),(39,40,'جان جانے کے لئے سینما گھر پر ایک موٹر سائیکل',NULL,NULL,'2016-01-12 15:41:24',NULL,NULL,'0'),(40,41,'جان جانے کو ایک سینما کے لیے سائیکل کی طرف سے',NULL,NULL,'2016-01-12 15:41:45',NULL,NULL,'0'),(41,42,'جان',NULL,NULL,'2016-01-12 15:42:04',NULL,NULL,'0');
/*!40000 ALTER TABLE `dict_ur` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_vi`
--

DROP TABLE IF EXISTS `dict_vi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_vi` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_vi`
--

LOCK TABLES `dict_vi` WRITE;
/*!40000 ALTER TABLE `dict_vi` DISABLE KEYS */;
INSERT INTO `dict_vi` VALUES (1,1,'Ánh trăng',NULL,NULL,'2016-01-12 15:21:32',NULL,NULL,'0'),(2,2,'Trăng non',NULL,NULL,'2016-01-12 15:21:57',NULL,NULL,'0'),(3,3,'John là một người Anh',NULL,NULL,'2016-01-12 15:22:20',NULL,NULL,'0'),(4,4,'Tôi đã đi đến các rạp chiếu phim với anh trai của tôi.',NULL,NULL,'2016-01-12 15:22:39',NULL,NULL,'0'),(5,5,'Tôi đã đi đến các rạp chiếu phim trên một chiếc xe đạp',NULL,NULL,'2016-01-12 15:23:02',NULL,NULL,'0'),(6,6,'John',NULL,NULL,'2016-01-12 15:23:23',NULL,NULL,'0'),(7,7,'Người Anh',NULL,NULL,'2016-01-12 15:23:44',NULL,NULL,'0'),(8,8,'Tôi',NULL,NULL,'2016-01-12 15:24:05',NULL,NULL,'0'),(9,9,'Anh trai của tôi',NULL,NULL,'2016-01-12 15:24:29',NULL,NULL,'0'),(10,10,'Rạp chiếu phim',NULL,NULL,'2016-01-12 15:24:48',NULL,NULL,'0'),(11,11,'Tôi đã đi',NULL,NULL,'2016-01-12 15:25:11',NULL,NULL,'0'),(12,12,'Xe đạp',NULL,NULL,'2016-01-12 15:25:31',NULL,NULL,'0'),(13,13,'Lunar Eclipse',NULL,NULL,'2016-01-12 15:25:51',NULL,NULL,'0'),(14,14,'Anh tôi là một bác sĩ.',NULL,NULL,'2016-01-12 15:26:15',NULL,NULL,'0'),(15,15,'Không ngừng xe đạp',NULL,NULL,'2016-01-12 15:26:35',NULL,NULL,'0'),(16,16,'Nơi thiêng liêng và công viên.',NULL,NULL,'2016-01-12 15:26:52',NULL,NULL,'0'),(17,17,'Bác sĩ',NULL,NULL,'2016-01-12 15:27:14',NULL,NULL,'0'),(18,18,'Dừng',NULL,NULL,'2016-01-12 15:27:33',NULL,NULL,'0'),(19,19,'Miếu thờ',NULL,NULL,'2016-01-12 15:27:57',NULL,NULL,'0'),(20,20,'Park',NULL,NULL,'2016-01-12 15:28:18',NULL,NULL,'0'),(21,21,'Như vậy',NULL,NULL,'2016-01-12 15:28:39',NULL,NULL,'0'),(22,22,'Đỏ mặt trăng',NULL,NULL,'2016-01-12 15:29:27',NULL,NULL,'0'),(23,23,'Ossharimashita và ngày mai',NULL,NULL,'2016-01-12 15:29:48',NULL,NULL,'0'),(24,24,'Ngày mai',NULL,NULL,'2016-01-12 15:30:09',NULL,NULL,'0'),(25,25,'Đi.',NULL,NULL,'2016-01-12 15:30:30',NULL,NULL,'0'),(26,26,'Otsharimashita',NULL,NULL,'2016-01-12 15:30:50',NULL,NULL,'0'),(27,27,'John không phải là anh',NULL,NULL,'2016-01-12 15:32:29',NULL,NULL,'0'),(28,28,'John là anh?',NULL,NULL,'2016-01-12 15:32:49',NULL,NULL,'0'),(29,29,'Tôi đi xem phim với anh trai của tôi.',NULL,NULL,'2016-01-12 15:33:12',NULL,NULL,'0'),(30,30,'Tôi đã đi đến rạp chiếu phim John và phim.',NULL,NULL,'2016-01-12 15:33:33',NULL,NULL,'0'),(31,31,'Tôi đi đến rạp chiếu phim John và phim.',NULL,NULL,'2016-01-12 15:33:54',NULL,NULL,'0'),(32,32,'Tôi đi xem phim trên xe đạp.',NULL,NULL,'2016-01-12 15:34:12',NULL,NULL,'0'),(33,33,'John đã đi đến các rạp chiếu phim trên một chiếc xe đạp?',NULL,NULL,'2016-01-12 15:34:31',NULL,NULL,'0'),(34,34,'John đã đi đến các rạp chiếu phim trên một chiếc xe đạp',NULL,NULL,'2016-01-12 15:34:54',NULL,NULL,'0'),(35,36,'John tới các rạp chiếu phim trên một chiếc xe đạp?',NULL,NULL,'2016-01-12 15:40:01',NULL,NULL,'0'),(36,37,'John tới các rạp chiếu phim trên một chiếc xe đạp',NULL,NULL,'2016-01-12 15:40:22',NULL,NULL,'0'),(37,38,'Tôi không đi đến các rạp chiếu phim trên một chiếc xe đạp John?',NULL,NULL,'2016-01-12 15:40:45',NULL,NULL,'0'),(38,39,'Không John đi đến rạp chiếu phim bằng xe đạp',NULL,NULL,'2016-01-12 15:41:05',NULL,NULL,'0'),(39,40,'John đi xem phim trên một chiếc xe đạp',NULL,NULL,'2016-01-12 15:41:24',NULL,NULL,'0'),(40,41,'John đi xem phim bằng xe đạp',NULL,NULL,'2016-01-12 15:41:45',NULL,NULL,'0'),(41,42,'John',NULL,NULL,'2016-01-12 15:42:04',NULL,NULL,'0');
/*!40000 ALTER TABLE `dict_vi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_yi`
--

DROP TABLE IF EXISTS `dict_yi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_yi` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_yi`
--

LOCK TABLES `dict_yi` WRITE;
/*!40000 ALTER TABLE `dict_yi` DISABLE KEYS */;
/*!40000 ALTER TABLE `dict_yi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_zh_cn`
--

DROP TABLE IF EXISTS `dict_zh_cn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_zh_cn` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_zh_cn`
--

LOCK TABLES `dict_zh_cn` WRITE;
/*!40000 ALTER TABLE `dict_zh_cn` DISABLE KEYS */;
INSERT INTO `dict_zh_cn` VALUES (1,1,'月光',NULL,NULL,'2016-01-12 15:21:32',NULL,NULL,'0'),(2,2,'新月',NULL,NULL,'2016-01-12 15:21:57',NULL,NULL,'0'),(3,3,'约翰是个英国人',NULL,NULL,'2016-01-12 15:22:20',NULL,NULL,'0'),(4,4,'我和弟弟一起去看电影。',NULL,NULL,'2016-01-12 15:22:39',NULL,NULL,'0'),(5,5,'我骑自行车去看电影',NULL,NULL,'2016-01-12 15:23:02',NULL,NULL,'0'),(6,6,'约翰 ·',NULL,NULL,'2016-01-12 15:23:23',NULL,NULL,'0'),(7,7,'英国人',NULL,NULL,'2016-01-12 15:23:44',NULL,NULL,'0'),(8,8,'我',NULL,NULL,'2016-01-12 15:24:05',NULL,NULL,'0'),(9,9,'我的兄弟',NULL,NULL,'2016-01-12 15:24:29',NULL,NULL,'0'),(10,10,'看电影',NULL,NULL,'2016-01-12 15:24:48',NULL,NULL,'0'),(11,11,'我去了',NULL,NULL,'2016-01-12 15:25:11',NULL,NULL,'0'),(12,12,'自行车',NULL,NULL,'2016-01-12 15:25:31',NULL,NULL,'0'),(13,13,'月食',NULL,NULL,'2016-01-12 15:25:51',NULL,NULL,'0'),(14,14,'我的哥哥是一名医生。',NULL,NULL,'2016-01-12 15:26:15',NULL,NULL,'0'),(15,15,'不停止自行车',NULL,NULL,'2016-01-12 15:26:35',NULL,NULL,'0'),(16,16,'神龛和公园。',NULL,NULL,'2016-01-12 15:26:52',NULL,NULL,'0'),(17,17,'医生',NULL,NULL,'2016-01-12 15:27:14',NULL,NULL,'0'),(18,18,'停止',NULL,NULL,'2016-01-12 15:27:33',NULL,NULL,'0'),(19,19,'靖国神社',NULL,NULL,'2016-01-12 15:27:57',NULL,NULL,'0'),(20,20,'公园',NULL,NULL,'2016-01-12 15:28:18',NULL,NULL,'0'),(21,21,'这种',NULL,NULL,'2016-01-12 15:28:39',NULL,NULL,'0'),(22,22,'红月亮',NULL,NULL,'2016-01-12 15:29:27',NULL,NULL,'0'),(23,23,'Ossharimashita 和明天',NULL,NULL,'2016-01-12 15:29:48',NULL,NULL,'0'),(24,24,'明天',NULL,NULL,'2016-01-12 15:30:09',NULL,NULL,'0'),(25,25,'去。',NULL,NULL,'2016-01-12 15:30:30',NULL,NULL,'0'),(26,26,'Otsharimashita',NULL,NULL,'2016-01-12 15:30:50',NULL,NULL,'0'),(27,27,'约翰不是英国',NULL,NULL,'2016-01-12 15:32:29',NULL,NULL,'0'),(28,28,'约翰是英国人吗?',NULL,NULL,'2016-01-12 15:32:49',NULL,NULL,'0'),(29,29,'我和弟弟一起去看电影。',NULL,NULL,'2016-01-12 15:33:12',NULL,NULL,'0'),(30,30,'我们去了约翰和电影院。',NULL,NULL,'2016-01-12 15:33:33',NULL,NULL,'0'),(31,31,'我去约翰和电影院。',NULL,NULL,'2016-01-12 15:33:54',NULL,NULL,'0'),(32,32,'我骑着自行车去看电影。',NULL,NULL,'2016-01-12 15:34:12',NULL,NULL,'0'),(33,33,'约翰在一辆自行车去看电影吗?',NULL,NULL,'2016-01-12 15:34:31',NULL,NULL,'0'),(34,34,'约翰在一辆自行车去看电影',NULL,NULL,'2016-01-12 15:34:54',NULL,NULL,'0'),(35,36,'约翰在一辆自行车去看电影吗?',NULL,NULL,'2016-01-12 15:40:01',NULL,NULL,'0'),(36,37,'约翰在一辆自行车去看电影',NULL,NULL,'2016-01-12 15:40:22',NULL,NULL,'0'),(37,38,'我没骑自行车约翰去看电影吗?',NULL,NULL,'2016-01-12 15:40:45',NULL,NULL,'0'),(38,39,'约翰没骑自行车去看电影吗',NULL,NULL,'2016-01-12 15:41:05',NULL,NULL,'0'),(39,40,'约翰在一辆自行车去看电影',NULL,NULL,'2016-01-12 15:41:24',NULL,NULL,'0'),(40,41,'约翰骑自行车去看电影',NULL,NULL,'2016-01-12 15:41:45',NULL,NULL,'0'),(41,42,'约翰 ·',NULL,NULL,'2016-01-12 15:42:04',NULL,NULL,'0');
/*!40000 ALTER TABLE `dict_zh_cn` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dict_zh_tw`
--

DROP TABLE IF EXISTS `dict_zh_tw`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dict_zh_tw` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_ja_id` int(11) NOT NULL,
  `word_1` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `word_2` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `word_3` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dict_zh_tw`
--

LOCK TABLES `dict_zh_tw` WRITE;
/*!40000 ALTER TABLE `dict_zh_tw` DISABLE KEYS */;
INSERT INTO `dict_zh_tw` VALUES (1,1,'月光',NULL,NULL,'2016-01-12 15:21:32',NULL,NULL,'0'),(2,2,'新月',NULL,NULL,'2016-01-12 15:21:57',NULL,NULL,'0'),(3,3,'約翰是個英國人',NULL,NULL,'2016-01-12 15:22:20',NULL,NULL,'0'),(4,4,'我和弟弟一起去看電影。',NULL,NULL,'2016-01-12 15:22:39',NULL,NULL,'0'),(5,5,'我騎自行車去看電影',NULL,NULL,'2016-01-12 15:23:02',NULL,NULL,'0'),(6,6,'約翰 ·',NULL,NULL,'2016-01-12 15:23:23',NULL,NULL,'0'),(7,7,'英國人',NULL,NULL,'2016-01-12 15:23:44',NULL,NULL,'0'),(8,8,'我',NULL,NULL,'2016-01-12 15:24:05',NULL,NULL,'0'),(9,9,'我的兄弟',NULL,NULL,'2016-01-12 15:24:29',NULL,NULL,'0'),(10,10,'看電影',NULL,NULL,'2016-01-12 15:24:48',NULL,NULL,'0'),(11,11,'我去了',NULL,NULL,'2016-01-12 15:25:11',NULL,NULL,'0'),(12,12,'自行車',NULL,NULL,'2016-01-12 15:25:31',NULL,NULL,'0'),(13,13,'月食',NULL,NULL,'2016-01-12 15:25:51',NULL,NULL,'0'),(14,14,'我的哥哥是一名醫生。',NULL,NULL,'2016-01-12 15:26:15',NULL,NULL,'0'),(15,15,'不停止自行車',NULL,NULL,'2016-01-12 15:26:35',NULL,NULL,'0'),(16,16,'神龕和公園。',NULL,NULL,'2016-01-12 15:26:52',NULL,NULL,'0'),(17,17,'醫生',NULL,NULL,'2016-01-12 15:27:14',NULL,NULL,'0'),(18,18,'停止',NULL,NULL,'2016-01-12 15:27:33',NULL,NULL,'0'),(19,19,'靖國神社',NULL,NULL,'2016-01-12 15:27:57',NULL,NULL,'0'),(20,20,'公園',NULL,NULL,'2016-01-12 15:28:18',NULL,NULL,'0'),(21,21,'這種',NULL,NULL,'2016-01-12 15:28:39',NULL,NULL,'0'),(22,22,'紅月亮',NULL,NULL,'2016-01-12 15:29:27',NULL,NULL,'0'),(23,23,'Ossharimashita 和明天',NULL,NULL,'2016-01-12 15:29:48',NULL,NULL,'0'),(24,24,'明天',NULL,NULL,'2016-01-12 15:30:09',NULL,NULL,'0'),(25,25,'去。',NULL,NULL,'2016-01-12 15:30:30',NULL,NULL,'0'),(26,26,'Otsharimashita',NULL,NULL,'2016-01-12 15:30:50',NULL,NULL,'0'),(27,27,'約翰不是英國',NULL,NULL,'2016-01-12 15:32:29',NULL,NULL,'0'),(28,28,'約翰是英國人嗎?',NULL,NULL,'2016-01-12 15:32:49',NULL,NULL,'0'),(29,29,'我和弟弟一起去看電影。',NULL,NULL,'2016-01-12 15:33:12',NULL,NULL,'0'),(30,30,'我們去了約翰和電影院。',NULL,NULL,'2016-01-12 15:33:33',NULL,NULL,'0'),(31,31,'我去約翰和電影院。',NULL,NULL,'2016-01-12 15:33:54',NULL,NULL,'0'),(32,32,'我騎著自行車去看電影。',NULL,NULL,'2016-01-12 15:34:12',NULL,NULL,'0'),(33,33,'約翰在一輛自行車去看電影嗎?',NULL,NULL,'2016-01-12 15:34:31',NULL,NULL,'0'),(34,34,'約翰在一輛自行車去看電影',NULL,NULL,'2016-01-12 15:34:54',NULL,NULL,'0'),(35,36,'約翰在一輛自行車去看電影嗎?',NULL,NULL,'2016-01-12 15:40:01',NULL,NULL,'0'),(36,37,'約翰在一輛自行車去看電影',NULL,NULL,'2016-01-12 15:40:22',NULL,NULL,'0'),(37,38,'我沒騎自行車約翰去看電影嗎?',NULL,NULL,'2016-01-12 15:40:45',NULL,NULL,'0'),(38,39,'約翰沒騎自行車去看電影嗎',NULL,NULL,'2016-01-12 15:41:05',NULL,NULL,'0'),(39,40,'約翰在一輛自行車去看電影',NULL,NULL,'2016-01-12 15:41:24',NULL,NULL,'0'),(40,41,'約翰騎自行車去看電影',NULL,NULL,'2016-01-12 15:41:45',NULL,NULL,'0'),(41,42,'約翰 ·',NULL,NULL,'2016-01-12 15:42:04',NULL,NULL,'0');
/*!40000 ALTER TABLE `dict_zh_tw` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lesson`
--

DROP TABLE IF EXISTS `lesson`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lesson` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lesson_name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `lesson_name_ml_id` int(11) DEFAULT NULL,
  `lesson_type` char(1) COLLATE utf8_unicode_ci NOT NULL,
  `japanese_level` char(1) COLLATE utf8_unicode_ci NOT NULL,
  `percent_complete` double DEFAULT NULL,
  `average_score` double DEFAULT NULL,
  `lesson_range` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `lesson_method_romaji` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `lesson_method_hiragana` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `lesson_method_kanji` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `learning_type` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lesson`
--

LOCK TABLES `lesson` WRITE;
/*!40000 ALTER TABLE `lesson` DISABLE KEYS */;
INSERT INTO `lesson` VALUES (1,'ジョンさんはイギリス人です',3,'0','5',NULL,NULL,'1-3','2','1','3','1','2016-01-12 15:21:13','2016-01-15 10:23:19',NULL,'0'),(2,'太陽',NULL,'1','5',NULL,NULL,'1-3','1','2','3','1','2016-01-27 16:50:32',NULL,NULL,'0'),(3,'事例',NULL,'0','4',NULL,NULL,'1-3','1','2','3','1','2016-02-22 12:30:01',NULL,NULL,'0'),(4,'事例1',NULL,'0','5',NULL,NULL,'1-3','1','2','3','1','2016-02-22 13:29:02',NULL,'2016-02-22 15:28:47','1'),(5,'事例1',NULL,'0','3',NULL,NULL,'1-3','1','2','3','1','2016-02-22 16:04:43',NULL,'2016-02-22 16:15:09','1');
/*!40000 ALTER TABLE `lesson` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lesson_info`
--

DROP TABLE IF EXISTS `lesson_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lesson_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lesson_id` int(11) NOT NULL,
  `lesson_no` int(11) NOT NULL,
  `practice_memory_condition` double NOT NULL,
  `practice_writing_condition` double NOT NULL,
  `practice_conversation_condition` double NOT NULL,
  `test_memory_condition` double NOT NULL,
  `test_writing_condition` double NOT NULL,
  `test_conversation_condition` double NOT NULL,
  `scenario_name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `scenario_name_ml_id` int(11) DEFAULT NULL,
  `scenario_syntax` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `practice_no` int(11) DEFAULT NULL,
  `practice_limit` int(11) NOT NULL,
  `speaker_1_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `speaker_2_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lesson_info`
--

LOCK TABLES `lesson_info` WRITE;
/*!40000 ALTER TABLE `lesson_info` DISABLE KEYS */;
INSERT INTO `lesson_info` VALUES (1,1,1,1,1,1,1,1,1,'新月',2,'ジョンさんはイギリス人です',NULL,3,'山田さん','あなた','2016-01-12 15:21:33','2016-01-15 10:23:19',NULL,'0'),(2,1,2,1,1,1,1,1,1,'日食月',13,'兄は医者です',NULL,3,'山田さん','あなた','2016-01-12 15:25:31','2016-01-15 10:23:19',NULL,'0'),(3,1,3,1,1,1,1,1,1,'赤い月',22,'私は皿を洗いました',NULL,3,'山田さん','あなた','2016-01-12 15:28:39','2016-01-15 10:23:19',NULL,'0'),(4,2,1,1,1,1,1,1,1,'太陽-1',NULL,'',NULL,3,'山田さん','あなた','2016-01-27 16:50:32',NULL,NULL,'0'),(5,2,2,1,1,1,1,1,1,'太陽-2',NULL,'',NULL,2,'山田さん','あなた','2016-01-27 16:50:32',NULL,NULL,'0'),(6,2,3,1,1,1,1,1,1,'太陽-3',NULL,'',NULL,5,'山田さん','あなた','2016-01-27 16:50:32',NULL,NULL,'0'),(7,3,1,90,80,70,55,60,65,'シナリオ例',NULL,'これは鉛筆です',NULL,3,'ヤマト','ジョン','2016-02-22 12:30:01',NULL,NULL,'0'),(8,4,1,50,80,70,55,60,65,'シナリオ例 ',NULL,'これは鉛筆です',NULL,3,'山田さん','あなた','2016-02-22 13:29:02',NULL,'2016-02-22 15:28:47','1'),(9,5,1,90,80,70,55,60,65,'シナリオ例1',NULL,'ジョンさんはイギリス人です',NULL,2,'ヤマト1','ジョン1','2016-02-22 16:04:43',NULL,'2016-02-22 16:15:09','1');
/*!40000 ALTER TABLE `lesson_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scenario`
--

DROP TABLE IF EXISTS `scenario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scenario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lesson_info_id` int(11) NOT NULL,
  `scenario_part` int(11) NOT NULL,
  `scenario_order` int(11) NOT NULL,
  `scenario` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `scenario_ml_id` int(11) DEFAULT NULL,
  `user_input_flg` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `callj_lesson_no` int(2) DEFAULT NULL,
  `callj_question_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `callj_concept_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `callj_word` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ruby_word` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `learning_type` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `category_word` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scenario`
--

LOCK TABLES `scenario` WRITE;
/*!40000 ALTER TABLE `scenario` DISABLE KEYS */;
INSERT INTO `scenario` VALUES (1,1,0,1,'ジョンさんはイギリス人です',3,'1',1,'people','person_is_1','ジョンさん;イギリス人;','人-じん;','1','name@;nationality@;','2016-01-12 15:21:57','2016-01-15 10:23:19',NULL,0),(2,1,0,2,'私は兄と映画館へ行きました',4,'1',2,'standard','to_go_1','私;兄;映画館;行きました;','私-わたし;兄-あに;映画館-えいがかん;行-い;','1','I@;ingroup,personname_female,personname_male@;intown,incountry@;@行く;','2016-01-12 15:22:20','2016-01-15 10:23:19',NULL,0),(3,1,0,3,'私は自転車で映画館へ行きました',5,'1',2,'standard','to_go_2','私;自転車;映画館;行きました;','私-わたし;自転車-じてんしゃ;映画館-えいがかん;行-い;','1','I,name@;transport,transport_S,transport_M,transport_L@;intown,incountry,international@;@行く;','2016-01-12 15:22:40','2016-01-15 10:23:19',NULL,0),(4,1,1,1,'ジョンさんはイギリス人です',3,'1',1,'people','person_is_1','ジョンさん;イギリス人;','人-じん;','1','name@;nationality@;','2016-01-12 15:23:02','2016-01-15 10:23:19',NULL,0),(5,1,1,2,'私は兄と映画館へ行きました',4,'1',2,'standard','to_go_1','私;兄;映画館;行きました;','私-わたし;兄-あに;映画館-えいがかん;行-い;','1','I@;ingroup,personname_female,personname_male@;intown,incountry@;@行く;','2016-01-12 15:23:02','2016-01-15 10:23:19',NULL,0),(6,1,1,3,'私は自転車で映画館へ行きました',5,'1',2,'standard','to_go_2','私;自転車;映画館;行きました;','私-わたし;自転車-じてんしゃ;映画館-えいがかん;行-い;','1','I,name@;transport,transport_S,transport_M,transport_L@;intown,incountry,international@;@行く;','2016-01-12 15:23:02','2016-01-15 10:23:19',NULL,0),(7,2,0,1,'兄は医者です',14,'1',1,'people','person_is_2','兄;医者;','兄-あに;医者-いしゃ;','1','family@;job@;','2016-01-12 15:25:51','2016-01-15 10:23:19',NULL,0),(8,2,0,2,'自転車を止めてはいけません',15,'1',8,'forbidden','simple_stop','自転車;止めて;','自転車-じてんしゃ;止-と;','1','parkable@;@止める;','2016-01-12 15:26:15','2016-01-15 10:23:19',NULL,0),(9,2,0,3,'神社や公園にいった',16,'1',15,'ya','lesson15-7','神社;公園;いった;','神社-じんじゃ;公園-こうえん;','1','@神社,床屋,教会,八百屋,病院,図書館;@公園,銀行,喫茶店,学校,映画館;@行く;','2016-01-12 15:26:35','2016-01-15 10:23:19',NULL,0),(10,2,1,1,'兄は医者です',14,'1',1,'people','person_is_2','兄;医者;','兄-あに;医者-いしゃ;','1','family@;job@;','2016-01-12 15:26:52','2016-01-15 10:23:19',NULL,0),(11,2,1,2,'自転車を止めてはいけません',15,'1',8,'forbidden','simple_stop','自転車;止めて;','自転車-じてんしゃ;止-と;','1','parkable@;@止める;','2016-01-12 15:26:52','2016-01-15 10:23:19',NULL,0),(12,2,1,3,'神社や公園にいった',16,'1',15,'ya','lesson15-7','神社;公園;いった;','神社-じんじゃ;公園-こうえん;','1','@神社,床屋,教会,八百屋,病院,図書館;@公園,銀行,喫茶店,学校,映画館;@行く;','2016-01-12 15:26:52','2016-01-15 10:23:19',NULL,0),(13,3,0,1,'明日行きますとおっしゃりました',23,'1',30,'keigo','lesson30-8','明日;行きます;おっしゃりました;','明日-あした;行-い;','1','@明日,明後日,今日,来週,来月;@行く,来る;@おっしゃる,申し上げる;','2016-01-12 15:29:28','2016-01-15 10:23:19',NULL,0),(14,3,1,1,'明日行きますとおっしゃりました',23,'1',30,'keigo','lesson30-8','明日;行きます;おっしゃりました;','明日-あした;行-い;','1','@明日,明後日,今日,来週,来月;@行く,来る;@おっしゃる,申し上げる;','2016-01-12 15:29:49','2016-01-15 10:23:19',NULL,0),(15,7,0,1,'これは鉛筆です',NULL,'1',1,'objects','this_is','これ;鉛筆;','鉛筆-えんぴつ;','1','definitiveA@;small_object@;','2016-02-22 12:30:01',NULL,NULL,0),(16,7,0,2,'これは一例であり、',NULL,'0',NULL,'','','','','1','','2016-02-22 12:30:01',NULL,NULL,0),(17,7,1,1,'これは鉛筆です',NULL,'1',1,'objects','this_is','これ;鉛筆;','鉛筆-えんぴつ;','1','definitiveA@;small_object@;','2016-02-22 12:30:01',NULL,NULL,0),(18,7,1,2,'これは一例であり、',NULL,'0',NULL,'','','','','1','','2016-02-22 12:30:01',NULL,NULL,0),(19,9,0,1,'ジョンさんはイギリス人です',3,'1',1,'people','person_is_1','ジョンさん;イギリス人;','人-じん;','1','name@;nationality@;','2016-02-22 16:04:43',NULL,'2016-02-22 16:15:09',1),(20,9,0,2,'これは一例であり、1',NULL,'0',NULL,'','','','','1','','2016-02-22 16:04:43',NULL,'2016-02-22 16:15:09',1),(21,9,1,1,'これは鉛筆です',NULL,'1',1,'objects','this_is','これ;鉛筆;','鉛筆-えんぴつ;','1','definitiveA@;small_object@;','2016-02-22 16:04:43',NULL,'2016-02-22 16:15:09',1),(22,9,1,2,'これは一例であり、',NULL,'0',NULL,'','','','','1','','2016-02-22 16:04:43',NULL,'2016-02-22 16:15:09',1);
/*!40000 ALTER TABLE `scenario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `score`
--

DROP TABLE IF EXISTS `score`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `score` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lesson_id` int(11) NOT NULL,
  `lesson_info_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `practice_memory_score` double DEFAULT NULL,
  `practice_writing_score` double DEFAULT NULL,
  `practice_conversation_score` double DEFAULT NULL,
  `test_memory_score` double DEFAULT NULL,
  `test_writing_score` double DEFAULT NULL,
  `test_conversation_score` double DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `lesson_id` (`lesson_id`),
  KEY `lesson_info_id` (`lesson_info_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `score`
--

LOCK TABLES `score` WRITE;
/*!40000 ALTER TABLE `score` DISABLE KEYS */;
INSERT INTO `score` VALUES (8,1,1,1,50,90,10,100,80,10,'2016-01-27 11:24:51','2016-02-19 16:09:41',NULL,'0'),(9,1,2,1,57,10,10,25,10,10,'2016-01-27 11:33:25','2016-01-27 11:46:01',NULL,'0'),(10,1,3,1,50,0,NULL,NULL,NULL,NULL,'2016-01-27 11:51:12','2016-01-27 11:55:06',NULL,'0'),(11,1,1,11,77,80,45,10,NULL,NULL,'2016-01-27 14:17:26','2016-01-27 14:46:13',NULL,'0'),(12,1,1,7,30,NULL,NULL,NULL,NULL,NULL,'2016-01-27 14:38:07',NULL,NULL,'0'),(13,1,1,10,40,NULL,NULL,NULL,NULL,NULL,'2016-01-27 16:14:53',NULL,NULL,'0'),(14,1,1,9,40,0,NULL,NULL,NULL,NULL,'2016-01-27 16:35:16','2016-01-27 16:44:27',NULL,'0'),(15,2,4,11,20,NULL,NULL,NULL,NULL,NULL,'2016-01-27 17:11:43','2016-01-27 17:23:07',NULL,'0'),(16,2,4,1,40,20,20,20,NULL,NULL,'2016-01-27 17:26:11','2016-01-27 17:29:56',NULL,'0'),(17,1,1,8,35,NULL,NULL,NULL,NULL,NULL,'2016-01-28 10:47:59','2016-01-28 10:49:00',NULL,'0');
/*!40000 ALTER TABLE `score` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `display_name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `role` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'demo0','bonsai0','demo 0','2','2015-12-21 17:14:59',NULL,NULL,'0'),(2,'demo1','bonsai1','demo 1','2','2015-12-21 17:15:00',NULL,NULL,'0'),(3,'demo2','bonsai2','demo 2','2','2015-12-21 17:15:53',NULL,NULL,'0'),(4,'demo3','bonsai3','demo 3','2','2015-12-21 17:16:04',NULL,NULL,'0'),(5,'demo4','bonsai4','demo 4','2','2015-12-21 17:16:17',NULL,NULL,'0'),(6,'demo5','bonsai5','demo 5','2','2015-12-21 17:16:38',NULL,NULL,'0'),(7,'demo6','bonsai6','demo 6','2','2015-12-21 17:17:01',NULL,NULL,'0'),(8,'demo7','bonsai7','demo 7','2','2015-12-21 17:17:17',NULL,NULL,'0'),(9,'demo8','bonsai8','demo 8','2','2015-12-21 17:17:42',NULL,NULL,'0'),(10,'demo9','bonsai9','demo 9','2','2015-12-21 17:17:53',NULL,NULL,'0'),(11,'admin','admin','admin','1','2015-12-22 13:53:31',NULL,NULL,'0');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_log`
--

DROP TABLE IF EXISTS `user_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `user_name` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `device_id` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `device_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `device_version` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `location` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `mode_learning` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `lesson_no` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `mode_practice_or_test` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `mode_skill` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `course` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `scenario_or_word` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `practice_times` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `answer` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  `score` double(11,0) DEFAULT NULL,
  `log_flg` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `token` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` char(1) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=229 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_log`
--

LOCK TABLES `user_log` WRITE;
/*!40000 ALTER TABLE `user_log` DISABLE KEYS */;
INSERT INTO `user_log` VALUES (1,11,'admin','','PC','Chrome 46','HCMV, Vietnam','2016-01-27 16:31:54','2016-01-27 16:33:08','構文','1','練習','記憶力','ジョンさんはイギリス人です','scenario','1/3','わたしはあにとえいがかんへいきます-ジョンさんはじてんしゃでえいがかんへいきますか; ジョンさんはじてんしゃでえいがかんへいきません-ジョンさんはじてんしゃでえいがかんへいきますか; ジョンさんはじてんしゃでえいがかんへいきませんでしたか-ジョンさんはじてんしゃでえいがかんへいきません; ジョンさんはじてんしゃでえいがかんへいきますか-ジョンさんはじてんしゃでえいがかんへいきますか; わたしはじてんしゃでえいがかんへいきました-ジョンさんはじてんしゃでえいがかんへいきませんでしたか',20,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ2IiwiZXhwIjoxNDUzODkwMzMyLCJpYXQiOjE0NTM4ODY3MzIsImlkTG9nIjo0MiwibG9jYXRpb24iOiJIQ01WLCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjExIiwidXNlck5hbWUiOiJhZG1pbiIsInV1aWQiOiIifQ.UbJ9UyaEt9VkrEdUzD453KyzHghfPx0KY0PZCDCTXsujan9j2t_89ppsXbF6zrg18SmHo-uFds3k_sGQWQJNYA','2016-01-27 16:32:19',NULL,NULL,'0'),(2,11,'admin','','PC','Chrome 46','HCMV, Vietnam','2016-01-27 16:31:54','2016-01-27 16:33:22','構文','1','練習','記憶力','ジョンさんはイギリス人です','vocabulary','1/3','ジョンさん-あに; わたし-わたし; あに-えいがかん; えいがかん-わたし; じてんしゃ-じてんしゃ',40,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ2IiwiZXhwIjoxNDUzODkwMzMyLCJpYXQiOjE0NTM4ODY3MzIsImlkTG9nIjo0MiwibG9jYXRpb24iOiJIQ01WLCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjExIiwidXNlck5hbWUiOiJhZG1pbiIsInV1aWQiOiIifQ.UbJ9UyaEt9VkrEdUzD453KyzHghfPx0KY0PZCDCTXsujan9j2t_89ppsXbF6zrg18SmHo-uFds3k_sGQWQJNYA','2016-01-27 16:33:22',NULL,NULL,'0'),(4,9,'demo8','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-27 16:33:20','2016-01-27 16:34:29','構文','1','練習','記憶力','ジョンさんはイギリス人です','scenario','1/3','ジョンさんはじてんしゃでえいがかんへいきました-わたしはじてんしゃでえいがかんへいきます; ジョンさんはじてんしゃでえいがかんへいきませんでしたか-ジョンさんはじてんしゃでえいがかんへいきませんか; わたしはじてんしゃでえいがかんへいきました-わたしはじてんしゃでえいがかんへいきます; ジョンさんはじてんしゃでえいがかんへいきませんか-ジョンさんはじてんしゃでえいがかんへいきました; わたしはじてんしゃでえいがかんへいきます-わたしはじてんしゃでえいがかんへいきました',0,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzODkwNzkwLCJpYXQiOjE0NTM4ODcxOTAsImlkTG9nIjozLCJsb2NhdGlvbiI6IkhvIENoaSBNaW5oIENpdHksIFZpZXRuYW0iLCJuYXRpdmVMYW5ndWFnZSI6IkVOIiwidXNlcklkIjoiOSIsInVzZXJOYW1lIjoiZGVtbzgiLCJ1dWlkIjoiIn0.2gfR1WmWxPF1DIRzrOPHAFq7mIXg0moT2mMpr1Xa7onlUncsvwuZ-t7W4m7C1tOX4ggb-RJSdddgkNY79TGxpw','2016-01-27 16:33:55',NULL,NULL,'0'),(5,9,'demo8','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-27 16:33:20','2016-01-27 16:35:16','構文','1','練習','記憶力','ジョンさんはイギリス人です','vocabulary','1/3','ジョンさん-イギリスじん; わたし-じてんしゃ; えいがかん-えいがかん; じてんしゃ-わたし',25,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzODkwNzkwLCJpYXQiOjE0NTM4ODcxOTAsImlkTG9nIjozLCJsb2NhdGlvbiI6IkhvIENoaSBNaW5oIENpdHksIFZpZXRuYW0iLCJuYXRpdmVMYW5ndWFnZSI6IkVOIiwidXNlcklkIjoiOSIsInVzZXJOYW1lIjoiZGVtbzgiLCJ1dWlkIjoiIn0.2gfR1WmWxPF1DIRzrOPHAFq7mIXg0moT2mMpr1Xa7onlUncsvwuZ-t7W4m7C1tOX4ggb-RJSdddgkNY79TGxpw','2016-01-27 16:35:10',NULL,NULL,'0'),(6,9,'demo8','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-27 16:35:16','2016-01-27 16:36:05','構文','1','練習','記憶力','ジョンさんはイギリス人です','scenario','2/3','わたしはあにとえいがかんへいきます-ジョンさんはじてんしゃでえいがかんへいきます; ジョンさんはイギリスじんです-わたしはあにとえいがかんへいきます; ジョンさんはじてんしゃでえいがかんへいきます-ジョンさんはじてんしゃでえいがかんへいきますか; ジョンさんはじてんしゃでえいがかんへいきますか-ジョンさんはイギリスじんです; ジョンさんはじてんしゃでえいがかんへいきませんでした-ジョンさんはじてんしゃでえいがかんへいきます',0,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzODkwNzkwLCJpYXQiOjE0NTM4ODcxOTAsImlkTG9nIjozLCJsb2NhdGlvbiI6IkhvIENoaSBNaW5oIENpdHksIFZpZXRuYW0iLCJuYXRpdmVMYW5ndWFnZSI6IkVOIiwidXNlcklkIjoiOSIsInVzZXJOYW1lIjoiZGVtbzgiLCJ1dWlkIjoiIn0.2gfR1WmWxPF1DIRzrOPHAFq7mIXg0moT2mMpr1Xa7onlUncsvwuZ-t7W4m7C1tOX4ggb-RJSdddgkNY79TGxpw','2016-01-27 16:36:05',NULL,NULL,'0'),(7,9,'demo8','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-27 16:35:16','2016-01-27 16:36:54','構文','1','練習','記憶力','ジョンさんはイギリス人です','vocabulary','2/3','ジョンさん-イギリスじん; イギリスじん-あに; わたし-イギリスじん; あに-わたし; えいがかん-あに',0,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzODkwNzkwLCJpYXQiOjE0NTM4ODcxOTAsImlkTG9nIjozLCJsb2NhdGlvbiI6IkhvIENoaSBNaW5oIENpdHksIFZpZXRuYW0iLCJuYXRpdmVMYW5ndWFnZSI6IkVOIiwidXNlcklkIjoiOSIsInVzZXJOYW1lIjoiZGVtbzgiLCJ1dWlkIjoiIn0.2gfR1WmWxPF1DIRzrOPHAFq7mIXg0moT2mMpr1Xa7onlUncsvwuZ-t7W4m7C1tOX4ggb-RJSdddgkNY79TGxpw','2016-01-27 16:36:54',NULL,NULL,'0'),(8,9,'demo8','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-27 16:36:54','2016-01-27 16:37:37','構文','1','練習','記憶力','ジョンさんはイギリス人です','scenario','3/3','ジョンさんはイギリスじんではありません-わたしはジョンさんとえいがかんへいきました',0,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzODkwNzkwLCJpYXQiOjE0NTM4ODcxOTAsImlkTG9nIjozLCJsb2NhdGlvbiI6IkhvIENoaSBNaW5oIENpdHksIFZpZXRuYW0iLCJuYXRpdmVMYW5ndWFnZSI6IkVOIiwidXNlcklkIjoiOSIsInVzZXJOYW1lIjoiZGVtbzgiLCJ1dWlkIjoiIn0.2gfR1WmWxPF1DIRzrOPHAFq7mIXg0moT2mMpr1Xa7onlUncsvwuZ-t7W4m7C1tOX4ggb-RJSdddgkNY79TGxpw','2016-01-27 16:37:37',NULL,NULL,'0'),(9,9,'demo8','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-27 16:38:11','2016-01-27 16:38:41','構文','1','練習','記憶力','ジョンさんはイギリス人です','scenario','1/3','ジョンさんはイギリスじんではありません-ジョンさんはじてんしゃでえいがかんへいきません; わたしはジョンさんとえいがかんへいきます-わたしはジョンさんとえいがかんへいきました',0,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzODkwNzkwLCJpYXQiOjE0NTM4ODcxOTAsImlkTG9nIjozLCJsb2NhdGlvbiI6IkhvIENoaSBNaW5oIENpdHksIFZpZXRuYW0iLCJuYXRpdmVMYW5ndWFnZSI6IkVOIiwidXNlcklkIjoiOSIsInVzZXJOYW1lIjoiZGVtbzgiLCJ1dWlkIjoiIn0.2gfR1WmWxPF1DIRzrOPHAFq7mIXg0moT2mMpr1Xa7onlUncsvwuZ-t7W4m7C1tOX4ggb-RJSdddgkNY79TGxpw','2016-01-27 16:38:15',NULL,NULL,'0'),(11,9,'demo8','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-27 16:39:04','2016-01-27 16:39:15','構文','1','練習','記憶力','ジョンさんはイギリス人です','scenario','1/3','ジョンさんはイギリスじんですか-ジョンさんはイギリスじんですか; ジョンさんはじてんしゃでえいがかんへいきませんでした-ジョンさんはイギリスじんですか; わたしはじてんしゃでえいがかんへいきました-ジョンさんはじてんしゃでえいがかんへいきませんでしたか; ジョンさんはじてんしゃでえいがかんへいきませんか-わたしはじてんしゃでえいがかんへいきました; ジョンさんはじてんしゃでえいがかんへいきませんでしたか-ジョンさんはじてんしゃでえいがかんへいきませんでしたか',40,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzODkwNzkwLCJpYXQiOjE0NTM4ODcxOTAsImlkTG9nIjozLCJsb2NhdGlvbiI6IkhvIENoaSBNaW5oIENpdHksIFZpZXRuYW0iLCJuYXRpdmVMYW5ndWFnZSI6IkVOIiwidXNlcklkIjoiOSIsInVzZXJOYW1lIjoiZGVtbzgiLCJ1dWlkIjoiIn0.2gfR1WmWxPF1DIRzrOPHAFq7mIXg0moT2mMpr1Xa7onlUncsvwuZ-t7W4m7C1tOX4ggb-RJSdddgkNY79TGxpw','2016-01-27 16:39:15',NULL,NULL,'0'),(12,9,'demo8','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-27 16:39:04','2016-01-27 16:43:23','構文','1','練習','記憶力','ジョンさんはイギリス人です','vocabulary','1/3','ジョンさん-イギリスじん; イギリスじん-イギリスじん; わたし-ジョンさん; えいがかん-じてんしゃ; じてんしゃ-じてんしゃ',40,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzODkwNzkwLCJpYXQiOjE0NTM4ODcxOTAsImlkTG9nIjozLCJsb2NhdGlvbiI6IkhvIENoaSBNaW5oIENpdHksIFZpZXRuYW0iLCJuYXRpdmVMYW5ndWFnZSI6IkVOIiwidXNlcklkIjoiOSIsInVzZXJOYW1lIjoiZGVtbzgiLCJ1dWlkIjoiIn0.2gfR1WmWxPF1DIRzrOPHAFq7mIXg0moT2mMpr1Xa7onlUncsvwuZ-t7W4m7C1tOX4ggb-RJSdddgkNY79TGxpw','2016-01-27 16:41:59',NULL,NULL,'0'),(13,9,'demo8','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-27 16:41:52','2016-01-27 16:44:17','構文','1','練習','筆記力','ジョンさんはイギリス人です','scenario','1/3','ジョンさんはじてんしゃでえいがかんへいきました-a      ; わたしはあにとえいがかんへいきました-a      ; ジョンさんはイギリスじんです-a   ; ジョンさんはじてんしゃでえいがかんへいきませんか-a      ; わたしはジョンさんとえいがかんへいきます-a      ',0,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzODkwNzkwLCJpYXQiOjE0NTM4ODcxOTAsImlkTG9nIjozLCJsb2NhdGlvbiI6IkhvIENoaSBNaW5oIENpdHksIFZpZXRuYW0iLCJuYXRpdmVMYW5ndWFnZSI6IkVOIiwidXNlcklkIjoiOSIsInVzZXJOYW1lIjoiZGVtbzgiLCJ1dWlkIjoiIn0.2gfR1WmWxPF1DIRzrOPHAFq7mIXg0moT2mMpr1Xa7onlUncsvwuZ-t7W4m7C1tOX4ggb-RJSdddgkNY79TGxpw','2016-01-27 16:42:49',NULL,NULL,'0'),(14,9,'demo8','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-27 16:43:22','2016-01-27 16:43:32','構文','1','練習','記憶力','ジョンさんはイギリス人です','scenario','2/3','わたしはあにとえいがかんへいきます-ジョンさんはイギリスじんではありません; ジョンさんはイギリスじんではありません-わたしはあにとえいがかんへいきました; ジョンさんはじてんしゃでえいがかんへいきました-ジョンさんはイギリスじんです; わたしはあにとえいがかんへいきました-ジョンさんはイギリスじんです; ジョンさんはイギリスじんです-ジョンさんはじてんしゃでえいがかんへいきました',0,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzODkwNzkwLCJpYXQiOjE0NTM4ODcxOTAsImlkTG9nIjozLCJsb2NhdGlvbiI6IkhvIENoaSBNaW5oIENpdHksIFZpZXRuYW0iLCJuYXRpdmVMYW5ndWFnZSI6IkVOIiwidXNlcklkIjoiOSIsInVzZXJOYW1lIjoiZGVtbzgiLCJ1dWlkIjoiIn0.2gfR1WmWxPF1DIRzrOPHAFq7mIXg0moT2mMpr1Xa7onlUncsvwuZ-t7W4m7C1tOX4ggb-RJSdddgkNY79TGxpw','2016-01-27 16:43:32',NULL,NULL,'0'),(15,9,'demo8','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-27 16:43:22','2016-01-27 16:43:42','構文','1','練習','記憶力','ジョンさんはイギリス人です','vocabulary','2/3','ジョンさん-ジョンさん; イギリスじん-ジョンさん; わたし-あに; あに-あに; えいがかん-ジョンさん',40,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzODkwNzkwLCJpYXQiOjE0NTM4ODcxOTAsImlkTG9nIjozLCJsb2NhdGlvbiI6IkhvIENoaSBNaW5oIENpdHksIFZpZXRuYW0iLCJuYXRpdmVMYW5ndWFnZSI6IkVOIiwidXNlcklkIjoiOSIsInVzZXJOYW1lIjoiZGVtbzgiLCJ1dWlkIjoiIn0.2gfR1WmWxPF1DIRzrOPHAFq7mIXg0moT2mMpr1Xa7onlUncsvwuZ-t7W4m7C1tOX4ggb-RJSdddgkNY79TGxpw','2016-01-27 16:43:42',NULL,NULL,'0'),(16,9,'demo8','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-27 16:43:41','2016-01-27 16:43:55','構文','1','練習','記憶力','ジョンさんはイギリス人です','scenario','3/3','わたしはジョンさんとえいがかんへいきました-ジョンさんはじてんしゃでえいがかんへいきますか; ジョンさんはじてんしゃでえいがかんへいきますか-ジョンさんはじてんしゃでえいがかんへいきましたか; わたしはじてんしゃでえいがかんへいきます-ジョンさんはじてんしゃでえいがかんへいきましたか; ジョンさんはじてんしゃでえいがかんへいきます-ジョンさんはじてんしゃでえいがかんへいきましたか; ジョンさんはじてんしゃでえいがかんへいきましたか-ジョンさんはじてんしゃでえいがかんへいきますか',0,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzODkwNzkwLCJpYXQiOjE0NTM4ODcxOTAsImlkTG9nIjozLCJsb2NhdGlvbiI6IkhvIENoaSBNaW5oIENpdHksIFZpZXRuYW0iLCJuYXRpdmVMYW5ndWFnZSI6IkVOIiwidXNlcklkIjoiOSIsInVzZXJOYW1lIjoiZGVtbzgiLCJ1dWlkIjoiIn0.2gfR1WmWxPF1DIRzrOPHAFq7mIXg0moT2mMpr1Xa7onlUncsvwuZ-t7W4m7C1tOX4ggb-RJSdddgkNY79TGxpw','2016-01-27 16:43:55',NULL,NULL,'0'),(17,9,'demo8','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-27 16:43:41','2016-01-27 16:44:04','構文','1','練習','記憶力','ジョンさんはイギリス人です','vocabulary','3/3','ジョンさん-ジョンさん; わたし-イギリスじん; えいがかん-イギリスじん; じてんしゃ-えいがかん',25,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzODkwNzkwLCJpYXQiOjE0NTM4ODcxOTAsImlkTG9nIjozLCJsb2NhdGlvbiI6IkhvIENoaSBNaW5oIENpdHksIFZpZXRuYW0iLCJuYXRpdmVMYW5ndWFnZSI6IkVOIiwidXNlcklkIjoiOSIsInVzZXJOYW1lIjoiZGVtbzgiLCJ1dWlkIjoiIn0.2gfR1WmWxPF1DIRzrOPHAFq7mIXg0moT2mMpr1Xa7onlUncsvwuZ-t7W4m7C1tOX4ggb-RJSdddgkNY79TGxpw','2016-01-27 16:44:04',NULL,NULL,'0'),(18,9,'demo8','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-27 16:41:52','2016-01-27 16:44:27','構文','1','練習','筆記力','ジョンさんはイギリス人です','vocabulary','1/3','ジョンさん-a; イギリスじん-a; わたし-a; あに-a; えいがかん-a',0,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzODkwNzkwLCJpYXQiOjE0NTM4ODcxOTAsImlkTG9nIjozLCJsb2NhdGlvbiI6IkhvIENoaSBNaW5oIENpdHksIFZpZXRuYW0iLCJuYXRpdmVMYW5ndWFnZSI6IkVOIiwidXNlcklkIjoiOSIsInVzZXJOYW1lIjoiZGVtbzgiLCJ1dWlkIjoiIn0.2gfR1WmWxPF1DIRzrOPHAFq7mIXg0moT2mMpr1Xa7onlUncsvwuZ-t7W4m7C1tOX4ggb-RJSdddgkNY79TGxpw','2016-01-27 16:44:27',NULL,NULL,'0'),(33,1,'demo0','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-27 17:25:55','2016-01-27 17:29:35','単語','1','練習','記憶力','太陽','vocabulary','1/2','asa-asa; asagohan-atama; ame-aki; aki-asagohan; atama-ame',20,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzODkzOTQ5LCJpYXQiOjE0NTM4OTAzNDksImlkTG9nIjozMSwibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.qgM--rCEdfwJwhBIx3GfXyNp1p4Fj8FEOkHq9WpIw8iW-HCn5MdeU9E5TAaQYcY6z5tpDEl5fvrO2ugFWOWkVQ','2016-01-27 17:26:12',NULL,NULL,'0'),(34,1,'demo0','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-27 17:25:58','2016-01-27 17:29:56','単語','1','練習','筆記力','太陽','vocabulary','1/2','uchi-uchi; asa-asd; aki-asd; imouto-asd; ame-ad',20,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzODkzOTQ5LCJpYXQiOjE0NTM4OTAzNDksImlkTG9nIjozMSwibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.qgM--rCEdfwJwhBIx3GfXyNp1p4Fj8FEOkHq9WpIw8iW-HCn5MdeU9E5TAaQYcY6z5tpDEl5fvrO2ugFWOWkVQ','2016-01-27 17:26:18',NULL,NULL,'0'),(35,1,'demo0','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-27 17:25:59','2016-01-27 17:26:45','単語','1','練習','会話力','太陽','vocabulary','1/2','atama-atama',20,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzODkzOTQ5LCJpYXQiOjE0NTM4OTAzNDksImlkTG9nIjozMSwibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.qgM--rCEdfwJwhBIx3GfXyNp1p4Fj8FEOkHq9WpIw8iW-HCn5MdeU9E5TAaQYcY6z5tpDEl5fvrO2ugFWOWkVQ','2016-01-27 17:26:45',NULL,NULL,'0'),(37,1,'demo0','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-27 17:27:19','2016-01-27 17:29:14','単語','1','テスト','記憶力','太陽','vocabulary','1/1','ike-ame; aki-ame; ame-ike; ashi-ike; asagohan-asagohan',20,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzODkzOTQ5LCJpYXQiOjE0NTM4OTAzNDksImlkTG9nIjozMSwibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.qgM--rCEdfwJwhBIx3GfXyNp1p4Fj8FEOkHq9WpIw8iW-HCn5MdeU9E5TAaQYcY6z5tpDEl5fvrO2ugFWOWkVQ','2016-01-27 17:29:14',NULL,NULL,'0'),(38,1,'demo0','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-27 17:29:34','2016-01-27 17:29:42','単語','1','練習','記憶力','太陽','vocabulary','2/2','uchi-uchi; ashi-ashi; ike-uchi; imouto-oniisan; oniisan-ike',40,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzODkzOTQ5LCJpYXQiOjE0NTM4OTAzNDksImlkTG9nIjozMSwibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.qgM--rCEdfwJwhBIx3GfXyNp1p4Fj8FEOkHq9WpIw8iW-HCn5MdeU9E5TAaQYcY6z5tpDEl5fvrO2ugFWOWkVQ','2016-01-27 17:29:42',NULL,NULL,'0'),(39,1,'demo0','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-27 17:32:09','2016-01-27 17:32:17','単語','1','練習','記憶力','太陽','vocabulary','1/2','ame-uchi; imouto-ame; uchi-aki; atama-aki; aki-aki',20,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzODkzOTQ5LCJpYXQiOjE0NTM4OTAzNDksImlkTG9nIjozMSwibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.qgM--rCEdfwJwhBIx3GfXyNp1p4Fj8FEOkHq9WpIw8iW-HCn5MdeU9E5TAaQYcY6z5tpDEl5fvrO2ugFWOWkVQ','2016-01-27 17:32:17',NULL,NULL,'0'),(40,1,'demo0','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-27 17:32:07','2016-01-27 17:32:29','単語','1','練習','筆記力','太陽','vocabulary','1/2','ike-asd; ame-asd; asa-asd; ashi-asd; oniisan-asd',0,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzODkzOTQ5LCJpYXQiOjE0NTM4OTAzNDksImlkTG9nIjozMSwibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.qgM--rCEdfwJwhBIx3GfXyNp1p4Fj8FEOkHq9WpIw8iW-HCn5MdeU9E5TAaQYcY6z5tpDEl5fvrO2ugFWOWkVQ','2016-01-27 17:32:29',NULL,NULL,'0'),(41,1,'demo0','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-27 17:32:29','2016-01-27 17:32:35','単語','1','練習','筆記力','太陽','vocabulary','2/2','aki-asd; asagohan-asd; imouto-ads; uchi-asd; atama-ad',0,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzODkzOTQ5LCJpYXQiOjE0NTM4OTAzNDksImlkTG9nIjozMSwibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.qgM--rCEdfwJwhBIx3GfXyNp1p4Fj8FEOkHq9WpIw8iW-HCn5MdeU9E5TAaQYcY6z5tpDEl5fvrO2ugFWOWkVQ','2016-01-27 17:32:35',NULL,NULL,'0'),(42,1,'demo0','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-27 17:32:17','2016-01-27 17:32:40','単語','1','練習','記憶力','太陽','vocabulary','2/2','asagohan-ashi; asa-ashi; ashi-asa; oniisan-ike; ike-asa',0,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzODkzOTQ5LCJpYXQiOjE0NTM4OTAzNDksImlkTG9nIjozMSwibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.qgM--rCEdfwJwhBIx3GfXyNp1p4Fj8FEOkHq9WpIw8iW-HCn5MdeU9E5TAaQYcY6z5tpDEl5fvrO2ugFWOWkVQ','2016-01-27 17:32:40',NULL,NULL,'0'),(43,1,'demo0','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-27 17:32:38','2016-01-27 17:33:34','単語','1','練習','筆記力','太陽','vocabulary','3/2','imouto-a; uchi-a; ame-a; aki-a; asa-a',0,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzODkzOTQ5LCJpYXQiOjE0NTM4OTAzNDksImlkTG9nIjozMSwibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.qgM--rCEdfwJwhBIx3GfXyNp1p4Fj8FEOkHq9WpIw8iW-HCn5MdeU9E5TAaQYcY6z5tpDEl5fvrO2ugFWOWkVQ','2016-01-27 17:33:34',NULL,NULL,'0'),(44,1,'demo0','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-27 17:32:43','2016-01-27 17:33:50','単語','1','練習','記憶力','太陽','vocabulary','3/2','asagohan-asagohan; ike-atama; atama-ike; ame-asa; asa-asagohan',20,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzODkzOTQ5LCJpYXQiOjE0NTM4OTAzNDksImlkTG9nIjozMSwibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.qgM--rCEdfwJwhBIx3GfXyNp1p4Fj8FEOkHq9WpIw8iW-HCn5MdeU9E5TAaQYcY6z5tpDEl5fvrO2ugFWOWkVQ','2016-01-27 17:33:50',NULL,NULL,'0'),(45,1,'demo0','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-27 17:42:50','2016-01-27 17:51:10','単語','1','テスト','記憶力','太陽','vocabulary','1/1','ike-asa; asagohan-oniisan; oniisan-asagohan; asa-asagohan; uchi-ike',0,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzODkzOTQ5LCJpYXQiOjE0NTM4OTAzNDksImlkTG9nIjozMSwibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.qgM--rCEdfwJwhBIx3GfXyNp1p4Fj8FEOkHq9WpIw8iW-HCn5MdeU9E5TAaQYcY6z5tpDEl5fvrO2ugFWOWkVQ','2016-01-27 17:51:10',NULL,NULL,'0'),(46,1,'demo0','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-27 17:55:58','2016-01-27 17:56:05','構文','1','テスト','筆記力','ジョンさんはイギリス人です','scenario','1/1','ジョンさんはイギリスじんですか-a   ; ジョンさんはじてんしゃでえいがかんへいきません-a      ; ジョンさんはじてんしゃでえいがかんへいきませんでした-a      ; わたしはあにとえいがかんへいきます-a      ; わたしはあにとえいがかんへいきました-a      ',0,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzODkzOTQ5LCJpYXQiOjE0NTM4OTAzNDksImlkTG9nIjozMSwibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.qgM--rCEdfwJwhBIx3GfXyNp1p4Fj8FEOkHq9WpIw8iW-HCn5MdeU9E5TAaQYcY6z5tpDEl5fvrO2ugFWOWkVQ','2016-01-27 17:56:05',NULL,NULL,'0'),(47,1,'demo0','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-27 17:55:58','2016-01-27 17:56:10','構文','1','テスト','筆記力','ジョンさんはイギリス人です','vocabulary','1/1','ジョンさん-a; イギリスじん-a; わたし-a; あに-a; えいがかん-a',0,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzODkzOTQ5LCJpYXQiOjE0NTM4OTAzNDksImlkTG9nIjozMSwibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.qgM--rCEdfwJwhBIx3GfXyNp1p4Fj8FEOkHq9WpIw8iW-HCn5MdeU9E5TAaQYcY6z5tpDEl5fvrO2ugFWOWkVQ','2016-01-27 17:56:10',NULL,NULL,'0'),(48,1,'demo0','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-27 17:56:12','2016-01-27 17:56:18','構文','1','テスト','筆記力','ジョンさんはイギリス人です','scenario','1/1','わたしはじてんしゃでえいがかんへいきます-a      ; わたしはあにとえいがかんへいきました-a      ; わたしはジョンさんとえいがかんへいきました-a      ; ジョンさんはじてんしゃでえいがかんへいきますか-a      ; わたしはジョンさんとえいがかんへいきます-a      ',0,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzODkzOTQ5LCJpYXQiOjE0NTM4OTAzNDksImlkTG9nIjozMSwibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.qgM--rCEdfwJwhBIx3GfXyNp1p4Fj8FEOkHq9WpIw8iW-HCn5MdeU9E5TAaQYcY6z5tpDEl5fvrO2ugFWOWkVQ','2016-01-27 17:56:18',NULL,NULL,'0'),(49,1,'demo0','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-27 17:56:12','2016-01-27 17:56:22','構文','1','テスト','筆記力','ジョンさんはイギリス人です','vocabulary','1/1','ジョンさん-a; わたし-a; あに-a; えいがかん-a; じてんしゃ-a',0,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzODkzOTQ5LCJpYXQiOjE0NTM4OTAzNDksImlkTG9nIjozMSwibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.qgM--rCEdfwJwhBIx3GfXyNp1p4Fj8FEOkHq9WpIw8iW-HCn5MdeU9E5TAaQYcY6z5tpDEl5fvrO2ugFWOWkVQ','2016-01-27 17:56:22',NULL,NULL,'0'),(50,1,'demo0','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-27 17:57:07','2016-01-27 18:06:04','構文','1','練習','記憶力','ジョンさんはイギリス人です','scenario','1/3','ジョンさんはじてんしゃでえいがかんへいきません-わたしはジョンさんとえいがかんへいきました; わたしはジョンさんとえいがかんへいきました-わたしはあにとえいがかんへいきました; わたしはあにとえいがかんへいきました-ジョンさんはじてんしゃでえいがかんへいきません; ジョンさんはイギリスじんではありません-わたしはあにとえいがかんへいきました; ジョンさんはじてんしゃでえいがかんへいきました-ジョンさんはじてんしゃでえいがかんへいきません',0,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzODkzOTQ5LCJpYXQiOjE0NTM4OTAzNDksImlkTG9nIjozMSwibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.qgM--rCEdfwJwhBIx3GfXyNp1p4Fj8FEOkHq9WpIw8iW-HCn5MdeU9E5TAaQYcY6z5tpDEl5fvrO2ugFWOWkVQ','2016-01-27 18:06:04',NULL,NULL,'0'),(51,1,'demo0','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-27 17:57:07','2016-01-27 18:06:15','構文','1','練習','記憶力','ジョンさんはイギリス人です','vocabulary','1/3','ジョンさん-わたし; イギリスじん-ジョンさん; わたし-わたし; あに-あに; えいがかん-ジョンさん',40,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzODkzOTQ5LCJpYXQiOjE0NTM4OTAzNDksImlkTG9nIjozMSwibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.qgM--rCEdfwJwhBIx3GfXyNp1p4Fj8FEOkHq9WpIw8iW-HCn5MdeU9E5TAaQYcY6z5tpDEl5fvrO2ugFWOWkVQ','2016-01-27 18:06:15',NULL,NULL,'0'),(52,1,'demo0','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-27 18:06:15','2016-01-27 18:06:40','構文','1','練習','記憶力','ジョンさんはイギリス人です','scenario','2/3','わたしはあにとえいがかんへいきます-ジョンさんはじてんしゃでえいがかんへいきましたか; ジョンさんはじてんしゃでえいがかんへいきませんか-ジョンさんはイギリスじんです; ジョンさんはイギリスじんです-ジョンさんはじてんしゃでえいがかんへいきませんか; ジョンさんはじてんしゃでえいがかんへいきますか-ジョンさんはじてんしゃでえいがかんへいきましたか; ジョンさんはじてんしゃでえいがかんへいきましたか-わたしはあにとえいがかんへいきます',0,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzODkzOTQ5LCJpYXQiOjE0NTM4OTAzNDksImlkTG9nIjozMSwibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.qgM--rCEdfwJwhBIx3GfXyNp1p4Fj8FEOkHq9WpIw8iW-HCn5MdeU9E5TAaQYcY6z5tpDEl5fvrO2ugFWOWkVQ','2016-01-27 18:06:40',NULL,NULL,'0'),(53,1,'demo0','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-27 18:06:15','2016-01-27 18:06:49','構文','1','練習','記憶力','ジョンさんはイギリス人です','vocabulary','2/3','ジョンさん-イギリスじん; イギリスじん-ジョンさん; わたし-ジョンさん; あに-イギリスじん; えいがかん-えいがかん',20,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzODkzOTQ5LCJpYXQiOjE0NTM4OTAzNDksImlkTG9nIjozMSwibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.qgM--rCEdfwJwhBIx3GfXyNp1p4Fj8FEOkHq9WpIw8iW-HCn5MdeU9E5TAaQYcY6z5tpDEl5fvrO2ugFWOWkVQ','2016-01-27 18:06:49',NULL,NULL,'0'),(56,1,'demo0','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-27 18:28:33','2016-01-27 18:43:33','構文','1','練習','記憶力','ジョンさんはイギリス人です','scenario','1/3','ジョンさんはじてんしゃでえいがかんへいきませんでした-わたしはあにとえいがかんへいきました; ジョンさんはイギリスじんですか-ジョンさんはじてんしゃでえいがかんへいきました; わたしはあにとえいがかんへいきました-わたしはあにとえいがかんへいきました; わたしはあにとえいがかんへいきます-ジョンさんはじてんしゃでえいがかんへいきました; ジョンさんはじてんしゃでえいがかんへいきました-わたしはあにとえいがかんへいきます',20,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzODk3NTU2LCJpYXQiOjE0NTM4OTM5NTYsImlkTG9nIjo1NSwibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.5zsq8SMlTcqcKXY0qA0HbAdTTXoTJi-y9JTWYpwnbC7vzaZc2X9M1Si2sRlxtg0KaqFc16nI5EFx427ZQzJvUQ','2016-01-27 18:32:14',NULL,NULL,'0'),(57,11,'admin','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-27 18:38:59','2016-01-27 18:39:04',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzODk4MzM5LCJpYXQiOjE0NTM4OTQ3MzksImlkTG9nIjo1NywibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjExIiwidXNlck5hbWUiOiJhZG1pbiIsInV1aWQiOiIifQ.XpZqPaKbwIBssQlvkI-yYaXZkDmsqIIrDm_i4SDAXidJdH6xgPTU5EQrN-8AOBwyjLQO4hvww4mT_I9KLZnsOQ','2016-01-27 18:38:59',NULL,NULL,'0'),(59,1,'demo0','7fc8338c26c1d6d8d38023348c756872b4274b6e','iOS','9.1','116/10/4 Đường Hoàng Hoa Thám, Phường 12, Tân Bình, Hồ Chí Minh, Vietnam','2016-01-27 22:02:12','2016-01-27 22:02:30','構文','1','練習','記憶力','ジョンさんはイギリス人です','scenario','1/3','ジョンさんはじてんしゃでえいがかんへいきませんか-ジョンさんはじてんしゃでえいがかんへいきますか; ジョンさんはじてんしゃでえいがかんへいきますか-ジョンさんはイギリスじんです; ジョンさんはじてんしゃでえいがかんへいきました-ジョンさんはじてんしゃでえいがかんへいきませんか; ジョンさんはイギリスじんです-ジョンさんはじてんしゃでえいがかんへいきませんでしたか; ジョンさんはじてんしゃでえいがかんへいきませんでしたか-ジョンさんはイギリスじんです',0,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjkuMSIsImV4cCI6MTQ1MzkwMzMxMiwiaWF0IjoxNDUzODk5NzEyLCJpZExvZyI6NTgsImxvY2F0aW9uIjoiMTE2LzEwLzQgxJDGsOG7nW5nIEhvw6BuZyBIb2EgVGjDoW0sIFBoxrDhu51uZyAxMiwgVMOibiBCw6xuaCwgSOG7kyBDaMOtIE1pbmgsIFZpZXRuYW0iLCJuYXRpdmVMYW5ndWFnZSI6IkVOIiwidXNlcklkIjoiMSIsInVzZXJOYW1lIjoiZGVtbzAiLCJ1dWlkIjoiN2ZjODMzOGMyNmMxZDZkOGQzODAyMzM0OGM3NTY4NzJiNDI3NGI2ZSJ9._CrHnI9NU5itpk5DXcfK4ATTOKtGC-N2tW1uYNR3p6HMzEBfNY0g1Krj9WrKCMOv0-d_7hm0gaFjMKnKfgzL8Q','2016-01-27 22:02:31',NULL,NULL,'0'),(60,1,'demo0','7fc8338c26c1d6d8d38023348c756872b4274b6e','iOS','9.1','116/10/4 Đường Hoàng Hoa Thám, Phường 12, Tân Bình, Hồ Chí Minh, Vietnam','2016-01-27 22:02:12','2016-01-27 22:03:07','構文','1','練習','記憶力','ジョンさんはイギリス人です','vocabulary','1/3','ジョンさん-じてんしゃ; イギリスじん-わたし; えいがかん-じてんしゃ; じてんしゃ-わたし',0,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjkuMSIsImV4cCI6MTQ1MzkwMzMxMiwiaWF0IjoxNDUzODk5NzEyLCJpZExvZyI6NTgsImxvY2F0aW9uIjoiMTE2LzEwLzQgxJDGsOG7nW5nIEhvw6BuZyBIb2EgVGjDoW0sIFBoxrDhu51uZyAxMiwgVMOibiBCw6xuaCwgSOG7kyBDaMOtIE1pbmgsIFZpZXRuYW0iLCJuYXRpdmVMYW5ndWFnZSI6IkVOIiwidXNlcklkIjoiMSIsInVzZXJOYW1lIjoiZGVtbzAiLCJ1dWlkIjoiN2ZjODMzOGMyNmMxZDZkOGQzODAyMzM0OGM3NTY4NzJiNDI3NGI2ZSJ9._CrHnI9NU5itpk5DXcfK4ATTOKtGC-N2tW1uYNR3p6HMzEBfNY0g1Krj9WrKCMOv0-d_7hm0gaFjMKnKfgzL8Q','2016-01-27 22:03:07',NULL,NULL,'0'),(61,1,'demo0','7fc8338c26c1d6d8d38023348c756872b4274b6e','iOS','9.1','116/10/4 Đường Hoàng Hoa Thám, Phường 12, Tân Bình, Hồ Chí Minh, Vietnam','2016-01-27 22:04:12','2016-01-27 22:04:25','構文','1','練習','記憶力','ジョンさんはイギリス人です','scenario','1/3','ジョンさんはじてんしゃでえいがかんへいきませんか-ジョンさんはイギリスじんです; わたしはじてんしゃでえいがかんへいきました-ジョンさんはじてんしゃでえいがかんへいきませんでした; ジョンさんはじてんしゃでえいがかんへいきませんでしたか-ジョンさんはじてんしゃでえいがかんへいきませんか; ジョンさんはじてんしゃでえいがかんへいきませんでした-ジョンさんはイギリスじんです; ジョンさんはイギリスじんです-ジョンさんはじてんしゃでえいがかんへいきませんか',0,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjkuMSIsImV4cCI6MTQ1MzkwMzMxMiwiaWF0IjoxNDUzODk5NzEyLCJpZExvZyI6NTgsImxvY2F0aW9uIjoiMTE2LzEwLzQgxJDGsOG7nW5nIEhvw6BuZyBIb2EgVGjDoW0sIFBoxrDhu51uZyAxMiwgVMOibiBCw6xuaCwgSOG7kyBDaMOtIE1pbmgsIFZpZXRuYW0iLCJuYXRpdmVMYW5ndWFnZSI6IkVOIiwidXNlcklkIjoiMSIsInVzZXJOYW1lIjoiZGVtbzAiLCJ1dWlkIjoiN2ZjODMzOGMyNmMxZDZkOGQzODAyMzM0OGM3NTY4NzJiNDI3NGI2ZSJ9._CrHnI9NU5itpk5DXcfK4ATTOKtGC-N2tW1uYNR3p6HMzEBfNY0g1Krj9WrKCMOv0-d_7hm0gaFjMKnKfgzL8Q','2016-01-27 22:04:25',NULL,NULL,'0'),(62,1,'demo0','7fc8338c26c1d6d8d38023348c756872b4274b6e','iOS','9.1','116/10/4 Đường Hoàng Hoa Thám, Phường 12, Tân Bình, Hồ Chí Minh, Vietnam','2016-01-27 22:04:12','2016-01-27 22:04:45','構文','1','練習','記憶力','ジョンさんはイギリス人です','vocabulary','1/3','ジョンさん-わたし; イギリスじん-えいがかん; わたし-わたし; えいがかん-イギリスじん; じてんしゃ-わたし',20,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjkuMSIsImV4cCI6MTQ1MzkwMzMxMiwiaWF0IjoxNDUzODk5NzEyLCJpZExvZyI6NTgsImxvY2F0aW9uIjoiMTE2LzEwLzQgxJDGsOG7nW5nIEhvw6BuZyBIb2EgVGjDoW0sIFBoxrDhu51uZyAxMiwgVMOibiBCw6xuaCwgSOG7kyBDaMOtIE1pbmgsIFZpZXRuYW0iLCJuYXRpdmVMYW5ndWFnZSI6IkVOIiwidXNlcklkIjoiMSIsInVzZXJOYW1lIjoiZGVtbzAiLCJ1dWlkIjoiN2ZjODMzOGMyNmMxZDZkOGQzODAyMzM0OGM3NTY4NzJiNDI3NGI2ZSJ9._CrHnI9NU5itpk5DXcfK4ATTOKtGC-N2tW1uYNR3p6HMzEBfNY0g1Krj9WrKCMOv0-d_7hm0gaFjMKnKfgzL8Q','2016-01-27 22:04:45',NULL,NULL,'0'),(64,1,'demo0','','PC','Chrome 48','Ho Chi Minh City, Vietnam','2016-01-27 22:05:40','2016-01-27 22:05:48','構文','1','練習','記憶力','ジョンさんはイギリス人です','scenario','1/3','ジョンさんはイギリスじんですか-ジョンさんはイギリスじんですか; わたしはあにとえいがかんへいきました-ジョンさんはじてんしゃでえいがかんへいきますか; わたしはあにとえいがかんへいきます-ジョンさんはイギリスじんですか; ジョンさんはじてんしゃでえいがかんへいきますか-ジョンさんはじてんしゃでえいがかんへいきますか; ジョンさんはじてんしゃでえいがかんへいきました-ジョンさんはイギリスじんですか',40,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDUzOTAzNTMwLCJpYXQiOjE0NTM4OTk5MzAsImlkTG9nIjo2MywibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.Xi1JZv8yOmCj3MgAnln6oB1ktSiNltVSNT1JEqfY0w_n1Mk2qqZYz5UPZNgClWdREa1YLPnlb5n9H9x5j0xQmA','2016-01-27 22:05:48',NULL,NULL,'0'),(65,1,'demo0','','PC','Chrome 48','Ho Chi Minh City, Vietnam','2016-01-27 22:05:40','2016-01-27 22:05:55','構文','1','練習','記憶力','ジョンさんはイギリス人です','vocabulary','1/3','ジョンさん-イギリスじん; イギリスじん-えいがかん; わたし-イギリスじん; あに-イギリスじん; えいがかん-ジョンさん',0,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDUzOTAzNTMwLCJpYXQiOjE0NTM4OTk5MzAsImlkTG9nIjo2MywibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.Xi1JZv8yOmCj3MgAnln6oB1ktSiNltVSNT1JEqfY0w_n1Mk2qqZYz5UPZNgClWdREa1YLPnlb5n9H9x5j0xQmA','2016-01-27 22:05:55',NULL,NULL,'0'),(67,1,'demo0','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-27 20:03:25','2016-01-27 20:03:33','構文','1','練習','記憶力','ジョンさんはイギリス人です','scenario','1/3','ジョンさんはじてんしゃでえいがかんへいきますか-わたしはじてんしゃでえいがかんへいきました; わたしはじてんしゃでえいがかんへいきました-ジョンさんはじてんしゃでえいがかんへいきますか; ジョンさんはじてんしゃでえいがかんへいきました-ジョンさんはじてんしゃでえいがかんへいきました; ジョンさんはじてんしゃでえいがかんへいきませんでした-ジョンさんはじてんしゃでえいがかんへいきますか; わたしはジョンさんとえいがかんへいきます-ジョンさんはじてんしゃでえいがかんへいきますか',20,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzOTAzMzg3LCJpYXQiOjE0NTM4OTk3ODcsImlkTG9nIjo2NiwibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.5YRDFYFmJiEz6BQ6CHHYoF4pLpNdUGWgMjh7ToX62ZvQyp1x4p5RZd0yy5UMedQgZvhmoof8t_QPBv4WYh5s2w','2016-01-27 20:03:33',NULL,NULL,'0'),(68,1,'demo0','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-27 20:03:25','2016-01-27 20:03:42','構文','1','練習','記憶力','ジョンさんはイギリス人です','vocabulary','1/3','ジョンさん-ジョンさん; わたし-じてんしゃ; えいがかん-えいがかん; じてんしゃ-じてんしゃ',75,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzOTAzMzg3LCJpYXQiOjE0NTM4OTk3ODcsImlkTG9nIjo2NiwibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.5YRDFYFmJiEz6BQ6CHHYoF4pLpNdUGWgMjh7ToX62ZvQyp1x4p5RZd0yy5UMedQgZvhmoof8t_QPBv4WYh5s2w','2016-01-27 20:03:42',NULL,NULL,'0'),(69,1,'demo0','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-27 20:04:54','2016-01-27 20:05:03','構文','1','練習','記憶力','ジョンさんはイギリス人です','scenario','1/3','ジョンさんはじてんしゃでえいがかんへいきませんでしたか-ジョンさんはじてんしゃでえいがかんへいきます; ジョンさんはじてんしゃでえいがかんへいきましたか-ジョンさんはじてんしゃでえいがかんへいきます; ジョンさんはじてんしゃでえいがかんへいきます-ジョンさんはじてんしゃでえいがかんへいきます; わたしはジョンさんとえいがかんへいきました-ジョンさんはじてんしゃでえいがかんへいきましたか; ジョンさんはじてんしゃでえいがかんへいきますか-ジョンさんはじてんしゃでえいがかんへいきませんでしたか',20,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzOTAzMzg3LCJpYXQiOjE0NTM4OTk3ODcsImlkTG9nIjo2NiwibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.5YRDFYFmJiEz6BQ6CHHYoF4pLpNdUGWgMjh7ToX62ZvQyp1x4p5RZd0yy5UMedQgZvhmoof8t_QPBv4WYh5s2w','2016-01-27 20:05:03',NULL,NULL,'0'),(70,1,'demo0','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-27 20:04:54','2016-01-27 20:05:10','構文','1','練習','記憶力','ジョンさんはイギリス人です','vocabulary','1/3','ジョンさん-あに; わたし-わたし; えいがかん-えいがかん; じてんしゃ-じてんしゃ',75,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzOTAzMzg3LCJpYXQiOjE0NTM4OTk3ODcsImlkTG9nIjo2NiwibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.5YRDFYFmJiEz6BQ6CHHYoF4pLpNdUGWgMjh7ToX62ZvQyp1x4p5RZd0yy5UMedQgZvhmoof8t_QPBv4WYh5s2w','2016-01-27 20:05:10',NULL,NULL,'0'),(75,1,'demo0','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-28 08:35:05','2016-01-28 08:35:12','構文','1','練習','記憶力','ジョンさんはイギリス人です','scenario','1/3','わたしはジョンさんとえいがかんへいきます-ジョンさんはじてんしゃでえいがかんへいきませんか; ジョンさんはじてんしゃでえいがかんへいきませんか-ジョンさんはじてんしゃでえいがかんへいきませんか; ジョンさんはじてんしゃでえいがかんへいきましたか-わたしはあにとえいがかんへいきました; ジョンさんはじてんしゃでえいがかんへいきませんでした-ジョンさんはじてんしゃでえいがかんへいきませんでした; わたしはあにとえいがかんへいきました-わたしはあにとえいがかんへいきました',60,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzOTQ4NDgyLCJpYXQiOjE0NTM5NDQ4ODIsImlkTG9nIjo3NCwibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.tUpRuCDV7Wxcx6_F1TZHxTmSI15DNmi8DD6tHJGGfy1HvHEkeL59IuQ8OXuJXuHWBDZmgnjNjl7vkEXcmd3s-g','2016-01-28 08:35:12',NULL,NULL,'0'),(76,1,'demo0','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-28 08:35:05','2016-01-28 08:35:21','構文','1','練習','記憶力','ジョンさんはイギリス人です','vocabulary','1/3','ジョンさん-じてんしゃ; わたし-わたし; あに-じてんしゃ; えいがかん-あに; じてんしゃ-ジョンさん',20,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzOTQ4NDgyLCJpYXQiOjE0NTM5NDQ4ODIsImlkTG9nIjo3NCwibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.tUpRuCDV7Wxcx6_F1TZHxTmSI15DNmi8DD6tHJGGfy1HvHEkeL59IuQ8OXuJXuHWBDZmgnjNjl7vkEXcmd3s-g','2016-01-28 08:35:21',NULL,NULL,'0'),(77,1,'demo0','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-28 08:35:25','2016-01-28 08:35:39','構文','1','練習','筆記力','ジョンさんはイギリス人です','scenario','1/3','わたしはあにとえいがかんへいきます-a      ; ジョンさんはじてんしゃでえいがかんへいきませんでした-aa      ; ジョンさんはイギリスじんです-a   ; ジョンさんはじてんしゃでえいがかんへいきませんでしたか-a      ; ジョンさんはじてんしゃでえいがかんへいきますか-a      ',0,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzOTQ4NDgyLCJpYXQiOjE0NTM5NDQ4ODIsImlkTG9nIjo3NCwibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.tUpRuCDV7Wxcx6_F1TZHxTmSI15DNmi8DD6tHJGGfy1HvHEkeL59IuQ8OXuJXuHWBDZmgnjNjl7vkEXcmd3s-g','2016-01-28 08:35:39',NULL,NULL,'0'),(78,1,'demo0','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-28 08:35:25','2016-01-28 08:35:47','構文','1','練習','筆記力','ジョンさんはイギリス人です','vocabulary','1/3','ジョンさん-a; イギリスじん-a; わたし-a; あに-a; えいがかん-a',0,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzOTQ4NDgyLCJpYXQiOjE0NTM5NDQ4ODIsImlkTG9nIjo3NCwibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.tUpRuCDV7Wxcx6_F1TZHxTmSI15DNmi8DD6tHJGGfy1HvHEkeL59IuQ8OXuJXuHWBDZmgnjNjl7vkEXcmd3s-g','2016-01-28 08:35:47',NULL,NULL,'0'),(79,1,'demo0','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-28 08:36:03','2016-01-28 08:36:15','構文','1','テスト','記憶力','ジョンさんはイギリス人です','vocabulary','1/1','ジョンさん-わたし; イギリスじん-ジョンさん; わたし-わたし; あに-えいがかん; えいがかん-えいがかん',40,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzOTQ4NDgyLCJpYXQiOjE0NTM5NDQ4ODIsImlkTG9nIjo3NCwibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.tUpRuCDV7Wxcx6_F1TZHxTmSI15DNmi8DD6tHJGGfy1HvHEkeL59IuQ8OXuJXuHWBDZmgnjNjl7vkEXcmd3s-g','2016-01-28 08:36:15',NULL,NULL,'0'),(80,1,'demo0','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-28 08:36:03','2016-01-28 08:36:18','構文','1','テスト','記憶力','ジョンさんはイギリス人です','scenario','1/1','わたしはじてんしゃでえいがかんへいきました-ジョンさんはイギリスじんです; わたしはじてんしゃでえいがかんへいきます-わたしはじてんしゃでえいがかんへいきます; ジョンさんはイギリスじんです-わたしはあにとえいがかんへいきました; わたしはあにとえいがかんへいきました-わたしはじてんしゃでえいがかんへいきます; ジョンさんはじてんしゃでえいがかんへいきませんでしたか-ジョンさんはじてんしゃでえいがかんへいきませんでしたか',40,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzOTQ4NDgyLCJpYXQiOjE0NTM5NDQ4ODIsImlkTG9nIjo3NCwibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.tUpRuCDV7Wxcx6_F1TZHxTmSI15DNmi8DD6tHJGGfy1HvHEkeL59IuQ8OXuJXuHWBDZmgnjNjl7vkEXcmd3s-g','2016-01-28 08:36:18',NULL,NULL,'0'),(82,1,'demo0','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-28 08:39:14','2016-01-28 08:39:24','構文','1','練習','記憶力','ジョンさんはイギリス人です','scenario','1/3','ジョンさんはじてんしゃでえいがかんへいきませんか-ジョンさんはイギリスじんですか; ジョンさんはイギリスじんですか-わたしはあにとえいがかんへいきます; ジョンさんはじてんしゃでえいがかんへいきませんでした-ジョンさんはイギリスじんですか; わたしはあにとえいがかんへいきました-ジョンさんはじてんしゃでえいがかんへいきませんでした; わたしはあにとえいがかんへいきます-ジョンさんはイギリスじんですか',0,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzOTQ4NzQzLCJpYXQiOjE0NTM5NDUxNDMsImlkTG9nIjo4MSwibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.poV86rfuW1YBJowoauKzr6MEOd29WGiGWr9Ygd_q8EmayT_sQxR1b3PWKakCfd0ABhHuribIiNvmSJfLobolcQ','2016-01-28 08:39:24',NULL,NULL,'0'),(83,1,'demo0','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-28 08:39:14','2016-01-28 08:39:35','構文','1','練習','記憶力','ジョンさんはイギリス人です','vocabulary','1/3','ジョンさん-えいがかん; イギリスじん-わたし; わたし-ジョンさん; あに-あに; えいがかん-あに',20,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzOTQ4NzQzLCJpYXQiOjE0NTM5NDUxNDMsImlkTG9nIjo4MSwibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.poV86rfuW1YBJowoauKzr6MEOd29WGiGWr9Ygd_q8EmayT_sQxR1b3PWKakCfd0ABhHuribIiNvmSJfLobolcQ','2016-01-28 08:39:35',NULL,NULL,'0'),(85,1,'demo0','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-28 10:46:00','2016-01-28 10:46:09','構文','1','練習','記憶力','ジョンさんはイギリス人です','scenario','1/3','わたしはあにとえいがかんへいきました-わたしはジョンさんとえいがかんへいきます; ジョンさんはじてんしゃでえいがかんへいきませんか-ジョンさんはイギリスじんです; わたしはジョンさんとえいがかんへいきます-ジョンさんはイギリスじんです; わたしはじてんしゃでえいがかんへいきます-わたしはジョンさんとえいがかんへいきます; ジョンさんはイギリスじんです-わたしはじてんしゃでえいがかんへいきます',0,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzOTQ5MTQxLCJpYXQiOjE0NTM5NDU1NDEsImlkTG9nIjo4NCwibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.mMPUw5cc1_17mWRQ_bFalOycpe65-6LM8UsSuXl73s0bMtno0QINhC2Ge6zZCIptf5fsBYoPfARu92FJ83dPrg','2016-01-28 10:46:10',NULL,NULL,'0'),(86,1,'demo0','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-28 10:46:00','2016-01-28 10:46:19','構文','1','練習','記憶力','ジョンさんはイギリス人です','vocabulary','1/3','ジョンさん-ジョンさん; イギリスじん-イギリスじん; わたし-ジョンさん; あに-えいがかん; えいがかん-あに',40,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDUzOTQ5MTQxLCJpYXQiOjE0NTM5NDU1NDEsImlkTG9nIjo4NCwibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.mMPUw5cc1_17mWRQ_bFalOycpe65-6LM8UsSuXl73s0bMtno0QINhC2Ge6zZCIptf5fsBYoPfARu92FJ83dPrg','2016-01-28 10:46:19',NULL,NULL,'0'),(87,8,'demo7','7fc8338c26c1d6d8d38023348c756872b4274b6e','iOS','9.1','116/10/6 Đường Hoàng Hoa Thám, Phường 12, Tân Bình, Hồ Chí Minh, Vietnam','2016-01-28 10:47:23','2016-01-28 10:49:18',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjkuMSIsImV4cCI6MTQ1Mzk0OTI0MiwiaWF0IjoxNDUzOTQ1NjQyLCJpZExvZyI6ODcsImxvY2F0aW9uIjoiMTE2LzEwLzYgxJDGsOG7nW5nIEhvw6BuZyBIb2EgVGjDoW0sIFBoxrDhu51uZyAxMiwgVMOibiBCw6xuaCwgSOG7kyBDaMOtIE1pbmgsIFZpZXRuYW0iLCJuYXRpdmVMYW5ndWFnZSI6IkVOIiwidXNlcklkIjoiOCIsInVzZXJOYW1lIjoiZGVtbzciLCJ1dWlkIjoiN2ZjODMzOGMyNmMxZDZkOGQzODAyMzM0OGM3NTY4NzJiNDI3NGI2ZSJ9.2Zt-Lbe5px6T1C_2reqprI73YWjmCgQYBbE4QhRucIWnEX_BlMYcwxhUggqlfdQBoI9k3zRF9lt7YwCYpHhQkQ','2016-01-28 10:47:23',NULL,NULL,'0'),(88,8,'demo7','7fc8338c26c1d6d8d38023348c756872b4274b6e','iOS','9.1','116/10/6 Đường Hoàng Hoa Thám, Phường 12, Tân Bình, Hồ Chí Minh, Vietnam','2016-01-28 10:47:32','2016-01-28 10:47:45','構文','1','練習','記憶力','ジョンさんはイギリス人です','scenario','1/3','わたしはあにとえいがかんへいきます-わたしはあにとえいがかんへいきました; ジョンさんはイギリスじんです-ジョンさんはイギリスじんです; ジョンさんはじてんしゃでえいがかんへいきます-ジョンさんはじてんしゃでえいがかんへいきます; ジョンさんはじてんしゃでえいがかんへいきました-わたしはあにとえいがかんへいきます; わたしはあにとえいがかんへいきました-わたしはあにとえいがかんへいきます',40,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjkuMSIsImV4cCI6MTQ1Mzk0OTI0MiwiaWF0IjoxNDUzOTQ1NjQyLCJpZExvZyI6ODcsImxvY2F0aW9uIjoiMTE2LzEwLzYgxJDGsOG7nW5nIEhvw6BuZyBIb2EgVGjDoW0sIFBoxrDhu51uZyAxMiwgVMOibiBCw6xuaCwgSOG7kyBDaMOtIE1pbmgsIFZpZXRuYW0iLCJuYXRpdmVMYW5ndWFnZSI6IkVOIiwidXNlcklkIjoiOCIsInVzZXJOYW1lIjoiZGVtbzciLCJ1dWlkIjoiN2ZjODMzOGMyNmMxZDZkOGQzODAyMzM0OGM3NTY4NzJiNDI3NGI2ZSJ9.2Zt-Lbe5px6T1C_2reqprI73YWjmCgQYBbE4QhRucIWnEX_BlMYcwxhUggqlfdQBoI9k3zRF9lt7YwCYpHhQkQ','2016-01-28 10:47:45',NULL,NULL,'0'),(89,8,'demo7','7fc8338c26c1d6d8d38023348c756872b4274b6e','iOS','9.1','116/10/6 Đường Hoàng Hoa Thám, Phường 12, Tân Bình, Hồ Chí Minh, Vietnam','2016-01-28 10:47:32','2016-01-28 10:47:59','構文','1','練習','記憶力','ジョンさんはイギリス人です','vocabulary','1/3','ジョンさん-ジョンさん; イギリスじん-あに; わたし-あに; あに-えいがかん; えいがかん-イギリスじん',20,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjkuMSIsImV4cCI6MTQ1Mzk0OTI0MiwiaWF0IjoxNDUzOTQ1NjQyLCJpZExvZyI6ODcsImxvY2F0aW9uIjoiMTE2LzEwLzYgxJDGsOG7nW5nIEhvw6BuZyBIb2EgVGjDoW0sIFBoxrDhu51uZyAxMiwgVMOibiBCw6xuaCwgSOG7kyBDaMOtIE1pbmgsIFZpZXRuYW0iLCJuYXRpdmVMYW5ndWFnZSI6IkVOIiwidXNlcklkIjoiOCIsInVzZXJOYW1lIjoiZGVtbzciLCJ1dWlkIjoiN2ZjODMzOGMyNmMxZDZkOGQzODAyMzM0OGM3NTY4NzJiNDI3NGI2ZSJ9.2Zt-Lbe5px6T1C_2reqprI73YWjmCgQYBbE4QhRucIWnEX_BlMYcwxhUggqlfdQBoI9k3zRF9lt7YwCYpHhQkQ','2016-01-28 10:47:59',NULL,NULL,'0'),(90,8,'demo7','7fc8338c26c1d6d8d38023348c756872b4274b6e','iOS','9.1','116/10/6 Đường Hoàng Hoa Thám, Phường 12, Tân Bình, Hồ Chí Minh, Vietnam','2016-01-28 10:47:58','2016-01-28 10:48:18','構文','1','練習','記憶力','ジョンさんはイギリス人です','scenario','2/3','ジョンさんはじてんしゃでえいがかんへいきません-ジョンさんはイギリスじんではありません; わたしはじてんしゃでえいがかんへいきました-ジョンさんはじてんしゃでえいがかんへいきませんか; ジョンさんはイギリスじんではありません-ジョンさんはじてんしゃでえいがかんへいきません; ジョンさんはじてんしゃでえいがかんへいきましたか-わたしはじてんしゃでえいがかんへいきました; ジョンさんはじてんしゃでえいがかんへいきませんか-ジョンさんはじてんしゃでえいがかんへいきませんか',20,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjkuMSIsImV4cCI6MTQ1Mzk0OTI0MiwiaWF0IjoxNDUzOTQ1NjQyLCJpZExvZyI6ODcsImxvY2F0aW9uIjoiMTE2LzEwLzYgxJDGsOG7nW5nIEhvw6BuZyBIb2EgVGjDoW0sIFBoxrDhu51uZyAxMiwgVMOibiBCw6xuaCwgSOG7kyBDaMOtIE1pbmgsIFZpZXRuYW0iLCJuYXRpdmVMYW5ndWFnZSI6IkVOIiwidXNlcklkIjoiOCIsInVzZXJOYW1lIjoiZGVtbzciLCJ1dWlkIjoiN2ZjODMzOGMyNmMxZDZkOGQzODAyMzM0OGM3NTY4NzJiNDI3NGI2ZSJ9.2Zt-Lbe5px6T1C_2reqprI73YWjmCgQYBbE4QhRucIWnEX_BlMYcwxhUggqlfdQBoI9k3zRF9lt7YwCYpHhQkQ','2016-01-28 10:48:18',NULL,NULL,'0'),(91,8,'demo7','7fc8338c26c1d6d8d38023348c756872b4274b6e','iOS','9.1','116/10/6 Đường Hoàng Hoa Thám, Phường 12, Tân Bình, Hồ Chí Minh, Vietnam','2016-01-28 10:47:58','2016-01-28 10:48:32','構文','1','練習','記憶力','ジョンさんはイギリス人です','vocabulary','2/3','ジョンさん-じてんしゃ; イギリスじん-わたし; わたし-わたし; えいがかん-ジョンさん; じてんしゃ-ジョンさん',20,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjkuMSIsImV4cCI6MTQ1Mzk0OTI0MiwiaWF0IjoxNDUzOTQ1NjQyLCJpZExvZyI6ODcsImxvY2F0aW9uIjoiMTE2LzEwLzYgxJDGsOG7nW5nIEhvw6BuZyBIb2EgVGjDoW0sIFBoxrDhu51uZyAxMiwgVMOibiBCw6xuaCwgSOG7kyBDaMOtIE1pbmgsIFZpZXRuYW0iLCJuYXRpdmVMYW5ndWFnZSI6IkVOIiwidXNlcklkIjoiOCIsInVzZXJOYW1lIjoiZGVtbzciLCJ1dWlkIjoiN2ZjODMzOGMyNmMxZDZkOGQzODAyMzM0OGM3NTY4NzJiNDI3NGI2ZSJ9.2Zt-Lbe5px6T1C_2reqprI73YWjmCgQYBbE4QhRucIWnEX_BlMYcwxhUggqlfdQBoI9k3zRF9lt7YwCYpHhQkQ','2016-01-28 10:48:33',NULL,NULL,'0'),(92,8,'demo7','7fc8338c26c1d6d8d38023348c756872b4274b6e','iOS','9.1','116/10/6 Đường Hoàng Hoa Thám, Phường 12, Tân Bình, Hồ Chí Minh, Vietnam','2016-01-28 10:48:32','2016-01-28 10:48:46','構文','1','練習','記憶力','ジョンさんはイギリス人です','scenario','3/3','わたしはジョンさんとえいがかんへいきます-ジョンさんはじてんしゃでえいがかんへいきますか; わたしはジョンさんとえいがかんへいきました-わたしはジョンさんとえいがかんへいきました; ジョンさんはじてんしゃでえいがかんへいきませんでしたか-わたしはジョンさんとえいがかんへいきました; ジョンさんはじてんしゃでえいがかんへいきますか-ジョンさんはじてんしゃでえいがかんへいきませんでした; ジョンさんはじてんしゃでえいがかんへいきませんでした-ジョンさんはじてんしゃでえいがかんへいきませんでしたか',20,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjkuMSIsImV4cCI6MTQ1Mzk0OTI0MiwiaWF0IjoxNDUzOTQ1NjQyLCJpZExvZyI6ODcsImxvY2F0aW9uIjoiMTE2LzEwLzYgxJDGsOG7nW5nIEhvw6BuZyBIb2EgVGjDoW0sIFBoxrDhu51uZyAxMiwgVMOibiBCw6xuaCwgSOG7kyBDaMOtIE1pbmgsIFZpZXRuYW0iLCJuYXRpdmVMYW5ndWFnZSI6IkVOIiwidXNlcklkIjoiOCIsInVzZXJOYW1lIjoiZGVtbzciLCJ1dWlkIjoiN2ZjODMzOGMyNmMxZDZkOGQzODAyMzM0OGM3NTY4NzJiNDI3NGI2ZSJ9.2Zt-Lbe5px6T1C_2reqprI73YWjmCgQYBbE4QhRucIWnEX_BlMYcwxhUggqlfdQBoI9k3zRF9lt7YwCYpHhQkQ','2016-01-28 10:48:46',NULL,NULL,'0'),(93,8,'demo7','7fc8338c26c1d6d8d38023348c756872b4274b6e','iOS','9.1','116/10/6 Đường Hoàng Hoa Thám, Phường 12, Tân Bình, Hồ Chí Minh, Vietnam','2016-01-28 10:48:32','2016-01-28 10:49:00','構文','1','練習','記憶力','ジョンさんはイギリス人です','vocabulary','3/3','ジョンさん-じてんしゃ; わたし-じてんしゃ; えいがかん-えいがかん; じてんしゃ-じてんしゃ',50,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjkuMSIsImV4cCI6MTQ1Mzk0OTI0MiwiaWF0IjoxNDUzOTQ1NjQyLCJpZExvZyI6ODcsImxvY2F0aW9uIjoiMTE2LzEwLzYgxJDGsOG7nW5nIEhvw6BuZyBIb2EgVGjDoW0sIFBoxrDhu51uZyAxMiwgVMOibiBCw6xuaCwgSOG7kyBDaMOtIE1pbmgsIFZpZXRuYW0iLCJuYXRpdmVMYW5ndWFnZSI6IkVOIiwidXNlcklkIjoiOCIsInVzZXJOYW1lIjoiZGVtbzciLCJ1dWlkIjoiN2ZjODMzOGMyNmMxZDZkOGQzODAyMzM0OGM3NTY4NzJiNDI3NGI2ZSJ9.2Zt-Lbe5px6T1C_2reqprI73YWjmCgQYBbE4QhRucIWnEX_BlMYcwxhUggqlfdQBoI9k3zRF9lt7YwCYpHhQkQ','2016-01-28 10:49:00',NULL,NULL,'0'),(94,11,'admin','7fc8338c26c1d6d8d38023348c756872b4274b6e','iOS','9.1','116/10/6 Đường Hoàng Hoa Thám, Phường 12, Tân Bình, Hồ Chí Minh, Vietnam','2016-01-28 10:49:28','2016-01-28 10:51:05',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjkuMSIsImV4cCI6MTQ1Mzk0OTM2NywiaWF0IjoxNDUzOTQ1NzY3LCJpZExvZyI6OTQsImxvY2F0aW9uIjoiMTE2LzEwLzYgxJDGsOG7nW5nIEhvw6BuZyBIb2EgVGjDoW0sIFBoxrDhu51uZyAxMiwgVMOibiBCw6xuaCwgSOG7kyBDaMOtIE1pbmgsIFZpZXRuYW0iLCJuYXRpdmVMYW5ndWFnZSI6IkVOIiwidXNlcklkIjoiMTEiLCJ1c2VyTmFtZSI6ImFkbWluIiwidXVpZCI6IjdmYzgzMzhjMjZjMWQ2ZDhkMzgwMjMzNDhjNzU2ODcyYjQyNzRiNmUifQ.Qly4fBCGvP_RhMNswK9xj3I4OizsyUhuIO-j8oKCehqjFmaLQl4M1A7LBaIhzXr_spBZEBAJ4DGota3rXe8SkQ','2016-01-28 10:49:28',NULL,NULL,'0'),(95,11,'admin','','PC','Firefox 43','Ho Chi Minh City, Vietnam','2016-01-28 11:04:36','2016-01-28 11:06:43',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiRmlyZWZveCA0MyIsImV4cCI6MTQ1Mzk1MDI3NSwiaWF0IjoxNDUzOTQ2Njc1LCJpZExvZyI6OTUsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0.Nveqobg2O797pV_0FBzQXPldzIIDBnNPtlXEyqTHdDomaau2Ypfx3zX5ydRcJ69PT5DqIgSbvBr9hswxI-7XHQ','2016-01-28 11:04:36',NULL,NULL,'0'),(96,1,'demo0','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-29 10:05:59',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDU0MDQwMzU4LCJpYXQiOjE0NTQwMzY3NTgsImlkTG9nIjo5NiwibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.ftdZ96cw22Vg65Hruaf5RAc_ItlHIkt7OkoMAQoeLzz9ulZU1sobKnoQTYW37UDv7DQmQ9AQ4_bCslsh_QcRuQ','2016-01-29 10:05:59',NULL,NULL,'0'),(97,11,'admin','','PC','Chrome 46','HCMV, Vietnam','2016-01-29 10:06:30',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ2IiwiZXhwIjoxNDU0MDQwMzg5LCJpYXQiOjE0NTQwMzY3ODksImlkTG9nIjo5NywibG9jYXRpb24iOiJIQ01WLCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjExIiwidXNlck5hbWUiOiJhZG1pbiIsInV1aWQiOiIifQ.EZL8EhZ9osAQh7Tjr4lEKzavJqmP7kRfGFEQIqAjYx2EoceKhxIjEt-aAKxw13o9IT1KNx__AULoMhUemr1sVQ','2016-01-29 10:06:30',NULL,NULL,'0'),(98,1,'demo0','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-29 10:09:50',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDU0MDQwNTkwLCJpYXQiOjE0NTQwMzY5OTAsImlkTG9nIjo5OCwibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.iCmg1MXGnslT2zpxh83visWYbk1KuXMNITncKy0449GR59ReyhM38FkGgkzIM8TPmgryVBxUG6DYS1SoQEj58A','2016-01-29 10:09:50',NULL,NULL,'0'),(99,10,'demo9','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-29 10:11:06',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDU0MDQwNjY1LCJpYXQiOjE0NTQwMzcwNjUsImlkTG9nIjo5OSwibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEwIiwidXNlck5hbWUiOiJkZW1vOSIsInV1aWQiOiIifQ.iSX-KR3nZgEorr-YGZXFgal-hVEn1JcP-bZ_VTAilr9cvR8l-kSFInuG1unhDNcbCrryInrHPk1JO5kizNVNZQ','2016-01-29 10:11:06',NULL,NULL,'0'),(100,11,'admin','Website',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJleHAiOjE0NTQwNTUzMjQsImlhdCI6MTQ1NDA1MTcyNCwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjExIiwidXNlck5hbWUiOiJhZG1pbiIsInV1aWQiOiJXZWJzaXRlIn0.G2D9gflHjALeFksLv_nqTdWkG6LNef_x-KZEq7KCcEyl7q43G29RhpOZd5Ho06q3tRaq8WFr4XSaf94qjltEzw','2016-01-29 14:15:25',NULL,NULL,'0'),(101,10,'demo9','','PC','Chrome 47','','2016-01-29 15:40:45',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDU0MDYwNDQ1LCJpYXQiOjE0NTQwNTY4NDUsImlkTG9nIjoxMDEsImxvY2F0aW9uIjoiIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEwIiwidXNlck5hbWUiOiJkZW1vOSIsInV1aWQiOiIifQ.90NxRKBAUbz_l5AXy6p-lYCBYP9MJO4h7PeE-eyZ43NKibZnodzXcEaUbbS6qh-CZ8x74rfnrqk0MettumJvWA','2016-01-29 15:40:45',NULL,NULL,'0'),(102,10,'demo9','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-01-29 17:57:05',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDU0MDY4NjI1LCJpYXQiOjE0NTQwNjUwMjUsImlkTG9nIjoxMDIsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMCIsInVzZXJOYW1lIjoiZGVtbzkiLCJ1dWlkIjoiIn0.yUzyunHuiygNmZOkLliyNkToWXtTXLsL03unwOoM3OynXoLJcmsFTNKHMtaTqpZ7yhrvR1VjWg2hlwtvg0srlQ','2016-01-29 17:57:05',NULL,NULL,'0'),(103,10,'demo9','','PC','Chrome 48','','2016-02-01 08:42:49',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU0Mjk0NTY5LCJpYXQiOjE0NTQyOTA5NjksImlkTG9nIjoxMDMsImxvY2F0aW9uIjoiIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEwIiwidXNlck5hbWUiOiJkZW1vOSIsInV1aWQiOiIifQ.GRJzlirvC4AyNSpVjSt1ji0Tm9XL83Ncj1vX5qS7xZkIUIwYIkrUCFBM-SO3nK3NNEfcPgbQ48NDmeR9GuETJQ','2016-02-01 08:42:49',NULL,NULL,'0'),(104,11,'admin','','PC','Chrome 46','HCMV, Vietnam','2016-02-01 09:49:20',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ2IiwiZXhwIjoxNDU0Mjk4NTYwLCJpYXQiOjE0NTQyOTQ5NjAsImlkTG9nIjoxMDQsImxvY2F0aW9uIjoiSENNViwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0.SNym8DY6czBokrXHd_CxUjvhDgaTMGp3oObdHzVJHwA22Q0twgp-2rsMjCte_5n8qdForJ8a5Q9uEArs0QSUkw','2016-02-01 09:49:20',NULL,NULL,'0'),(105,11,'admin','','PC','Chrome 46','HCMV, Vietnam','2016-02-01 09:56:16',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ2IiwiZXhwIjoxNDU0Mjk4OTc1LCJpYXQiOjE0NTQyOTUzNzUsImlkTG9nIjoxMDUsImxvY2F0aW9uIjoiSENNViwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0.in_-sSIQ0YxJ9A2Sj1upBGra2aVl3aiZswE8-2jexRSGTCJFpUNAYMovPsDzxl5EaDJLGdM0uO1DGMUG5r_R8A','2016-02-01 09:56:16',NULL,NULL,'0'),(106,10,'demo9','','PC','Chrome 48','Ho Chi Minh City, Vietnam','2016-02-01 09:53:43',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU0Mjk4ODIyLCJpYXQiOjE0NTQyOTUyMjIsImlkTG9nIjoxMDYsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMCIsInVzZXJOYW1lIjoiZGVtbzkiLCJ1dWlkIjoiIn0.XIpVnfdKTZYfDMR21W3HN47ot73sT2vzcUlmOy4Es8MV-K-dANZ_4XUAbE-JYl5gU2-AVuevq0Oh-t1CfzJ5Yg','2016-02-01 09:53:43',NULL,NULL,'0'),(107,11,'admin','','PC','Chrome 46','HCMV, Vietnam','2016-02-01 09:56:29','2016-02-01 10:03:30','構文','1','練習','記憶力','ジョンさんはイギリス人です','scenario','1/3','ジョンさんはじてんしゃでえいがかんへいきました-ジョンさんはじてんしゃでえいがかんへいきませんでした; わたしはあにとえいがかんへいきます-ジョンさんはじてんしゃでえいがかんへいきました; ジョンさんはじてんしゃでえいがかんへいきませんでしたか-ジョンさんはじてんしゃでえいがかんへいきませんでしたか',20,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ2IiwiZXhwIjoxNDU0Mjk4OTc1LCJpYXQiOjE0NTQyOTUzNzUsImlkTG9nIjoxMDUsImxvY2F0aW9uIjoiSENNViwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0.in_-sSIQ0YxJ9A2Sj1upBGra2aVl3aiZswE8-2jexRSGTCJFpUNAYMovPsDzxl5EaDJLGdM0uO1DGMUG5r_R8A','2016-02-01 10:03:30',NULL,NULL,'0'),(108,11,'admin','','PC','Chrome 46','HCMV, Vietnam','2016-02-01 10:05:05','2016-02-01 10:09:05','構文','1','練習','記憶力','ジョンさんはイギリス人です','scenario','1/3','わたしはジョンさんとえいがかんへいきます-ジョンさんはじてんしゃでえいがかんへいきませんか; ジョンさんはじてんしゃでえいがかんへいきません-わたしはジョンさんとえいがかんへいきます; ジョンさんはじてんしゃでえいがかんへいきました-ジョンさんはじてんしゃでえいがかんへいきました',20,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ2IiwiZXhwIjoxNDU0Mjk4OTc1LCJpYXQiOjE0NTQyOTUzNzUsImlkTG9nIjoxMDUsImxvY2F0aW9uIjoiSENNViwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0.in_-sSIQ0YxJ9A2Sj1upBGra2aVl3aiZswE8-2jexRSGTCJFpUNAYMovPsDzxl5EaDJLGdM0uO1DGMUG5r_R8A','2016-02-01 10:09:05',NULL,NULL,'0'),(109,1,'demo0','','PC','Chrome 47','HCMV, Vietnam','2016-02-01 10:49:24',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDU0MzAyMTYzLCJpYXQiOjE0NTQyOTg1NjMsImlkTG9nIjoxMDksImxvY2F0aW9uIjoiSENNViwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxIiwidXNlck5hbWUiOiJkZW1vMCIsInV1aWQiOiIifQ.VRADLaLuqMk1mtVDpDNEZPVDPJnQ6uLdoxtnihIPxjhZTSCt5BdfNnn-33ykiNZY8EbPGZp72LTJMks0NOoqTw','2016-02-01 10:49:24',NULL,NULL,'0'),(110,1,'demo0','','PC','Chrome 47','HCMV, Vietnam','2016-02-01 10:57:27',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDU0MzAyNjQ2LCJpYXQiOjE0NTQyOTkwNDYsImlkTG9nIjoxMTAsImxvY2F0aW9uIjoiSENNViwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxIiwidXNlck5hbWUiOiJkZW1vMCIsInV1aWQiOiIifQ.jsr0jwpxedLicU8ZwGjkERh9x74XI0sNS4l2R9DKy9elh4dGymz_D5lqas2RqKypMLTHZ2f9zxf1cZeLUvCWnA','2016-02-01 10:57:27',NULL,NULL,'0'),(111,10,'demo9','','PC','Chrome 48','','2016-02-01 11:38:08',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU0MzA1MDg3LCJpYXQiOjE0NTQzMDE0ODcsImlkTG9nIjoxMTEsImxvY2F0aW9uIjoiIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEwIiwidXNlck5hbWUiOiJkZW1vOSIsInV1aWQiOiIifQ.aYUVaR4OfTpBM9B7YH9I3zKHkM3YEraMv2KUoVZM6CtbYmo6vxBVPygFjcAUH1e2MnhEhnXtYAlgn5uIx40NuA','2016-02-01 11:38:08',NULL,NULL,'0'),(112,11,'admin','','PC','Chrome 46','Ho Chi Minh City, Vietnam','2016-02-01 13:55:35',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ2IiwiZXhwIjoxNDU0MzEzMzM0LCJpYXQiOjE0NTQzMDk3MzQsImlkTG9nIjoxMTIsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0.Lzi1GNv_aMARBzLh-h2nRxgC70kFg6C5XI_UO2KwCRyM423cofM8Cclu_Qz8jVWCuk04jnDFIsY9c3kV5mJ4nA','2016-02-01 13:55:35',NULL,NULL,'0'),(113,11,'admin','','PC','Chrome 46','HCMV, Vietnam','2016-02-01 14:12:13',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ2IiwiZXhwIjoxNDU0MzE0MzMzLCJpYXQiOjE0NTQzMTA3MzMsImlkTG9nIjoxMTMsImxvY2F0aW9uIjoiSENNViwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0.NpFQIgRbBSGEQxtRcwLLnIxoMWGc6DIJS0EzSeQqdcFSsOgDX9L1pIF41sFphUHNzl6YFclPSyJnofbs9B1Vyw','2016-02-01 14:12:13',NULL,NULL,'0'),(114,11,'admin','','PC','Chrome 46','HCMV, Vietnam','2016-02-01 14:12:28','2016-02-01 14:12:48','構文','1','練習','記憶力','ジョンさんはイギリス人です','scenario','1/3','ジョンさんはじてんしゃでえいがかんへいきません-ジョンさんはじてんしゃでえいがかんへいきません; わたしはジョンさんとえいがかんへいきました-わたしはジョンさんとえいがかんへいきました; ジョンさんはじてんしゃでえいがかんへいきましたか-ジョンさんはじてんしゃでえいがかんへいきません; ジョンさんはイギリスじんですか-ジョンさんはじてんしゃでえいがかんへいきません; ジョンさんはじてんしゃでえいがかんへいきます-ジョンさんはイギリスじんですか',40,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ2IiwiZXhwIjoxNDU0MzE0MzMzLCJpYXQiOjE0NTQzMTA3MzMsImlkTG9nIjoxMTMsImxvY2F0aW9uIjoiSENNViwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0.NpFQIgRbBSGEQxtRcwLLnIxoMWGc6DIJS0EzSeQqdcFSsOgDX9L1pIF41sFphUHNzl6YFclPSyJnofbs9B1Vyw','2016-02-01 14:12:44',NULL,NULL,'0'),(115,11,'admin','','PC','Chrome 46','HCMV, Vietnam','2016-02-01 14:12:28','2016-02-01 14:13:41','構文','1','練習','記憶力','ジョンさんはイギリス人です','vocabulary','1/3','ジョンさん-ジョンさん; イギリスじん-ジョンさん; わたし-イギリスじん; えいがかん-えいがかん; じてんしゃ-じてんしゃ',60,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ2IiwiZXhwIjoxNDU0MzE0MzMzLCJpYXQiOjE0NTQzMTA3MzMsImlkTG9nIjoxMTMsImxvY2F0aW9uIjoiSENNViwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0.NpFQIgRbBSGEQxtRcwLLnIxoMWGc6DIJS0EzSeQqdcFSsOgDX9L1pIF41sFphUHNzl6YFclPSyJnofbs9B1Vyw','2016-02-01 14:13:21',NULL,NULL,'0'),(116,11,'admin','','PC','Chrome 46','HCMV, Vietnam','2016-02-01 14:14:19','2016-02-01 14:14:42','構文','1','練習','記憶力','ジョンさんはイギリス人です','vocabulary','1/3','ジョンさん-ジョンさん; わたし-わたし; えいがかん-わたし; じてんしゃ-イギリスじん',50,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ2IiwiZXhwIjoxNDU0MzE0MzMzLCJpYXQiOjE0NTQzMTA3MzMsImlkTG9nIjoxMTMsImxvY2F0aW9uIjoiSENNViwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0.NpFQIgRbBSGEQxtRcwLLnIxoMWGc6DIJS0EzSeQqdcFSsOgDX9L1pIF41sFphUHNzl6YFclPSyJnofbs9B1Vyw','2016-02-01 14:14:28',NULL,NULL,'0'),(117,11,'admin','','PC','Chrome 46','HCMV, Vietnam','2016-02-01 14:14:54','2016-02-01 14:20:02','構文','1','練習','記憶力','ジョンさんはイギリス人です','vocabulary','1/3','ジョンさん-ジョンさん; イギリスじん-イギリスじん; わたし-わたし; えいがかん-イギリスじん; じてんしゃ-イギリスじん',60,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ2IiwiZXhwIjoxNDU0MzE0MzMzLCJpYXQiOjE0NTQzMTA3MzMsImlkTG9nIjoxMTMsImxvY2F0aW9uIjoiSENNViwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0.NpFQIgRbBSGEQxtRcwLLnIxoMWGc6DIJS0EzSeQqdcFSsOgDX9L1pIF41sFphUHNzl6YFclPSyJnofbs9B1Vyw','2016-02-01 14:20:02',NULL,NULL,'0'),(118,11,'admin','','PC','Chrome 46','','2016-02-01 15:13:09',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ2IiwiZXhwIjoxNDU0MzE3OTg5LCJpYXQiOjE0NTQzMTQzODksImlkTG9nIjoxMTgsImxvY2F0aW9uIjoiIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjExIiwidXNlck5hbWUiOiJhZG1pbiIsInV1aWQiOiIifQ.p9yYlpHf9TjENwFZHOK-ZIw09e15G14ojimhfEZYuz0zQjPfgE67DIbjGf4ckFBjLknuZMiApv6VULyP7xqO7Q','2016-02-01 15:13:09',NULL,NULL,'0'),(119,10,'demo9','','PC','Chrome 48','Ho Chi Minh City, Vietnam','2016-02-02 09:15:43',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU0MzgyOTQyLCJpYXQiOjE0NTQzNzkzNDIsImlkTG9nIjoxMTksImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMCIsInVzZXJOYW1lIjoiZGVtbzkiLCJ1dWlkIjoiIn0.StS0aRaTtk0UR3mot3GLz4knRfIGOiDdB6ozTpicr5clouITsaRqToknlMk6GoaVD6HQn05ip8b_OIGIOVFA8Q','2016-02-02 09:15:43',NULL,NULL,'0'),(120,11,'admin','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-02-02 16:26:11',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDU0NDA4NzcxLCJpYXQiOjE0NTQ0MDUxNzEsImlkTG9nIjoxMjAsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0.g9sTjQpGWAmguSFBBMKuofwuaBNLjqLe6pD49lv9D7wavQ97LfAW3ohWQfATBBUcp-gdapT23cISNGNFC9bDyw','2016-02-02 16:26:11',NULL,NULL,'0'),(121,1,'demo0','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-02-02 16:30:52',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDU0NDA5MDUxLCJpYXQiOjE0NTQ0MDU0NTEsImlkTG9nIjoxMjEsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxIiwidXNlck5hbWUiOiJkZW1vMCIsInV1aWQiOiIifQ.IoldX9gF4xil46maKiUZZEi7QC_K8uNlDjn419_BTsYiTNa756Ayvkn9wiMVomi2GM013hkcHWUO7un4Ggt9pA','2016-02-02 16:30:52',NULL,NULL,'0'),(122,11,'admin','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-02-03 11:32:19',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDU0NDc3NTM4LCJpYXQiOjE0NTQ0NzM5MzgsImlkTG9nIjoxMjIsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0.Gk10Rfy0K1T4NO9BFkltk_xcUF6_QkPSvIAGOX8Uk-ADGeTUhbyVm3HDK5SLC5te1RoJtaiMrQcaMcPwDZB8dg','2016-02-03 11:32:19',NULL,NULL,'0'),(123,1,'demo0','','PC','Chrome 47','Ho Chi Minh City, Vietnam','2016-02-03 16:40:10',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDU0NDk2MDA5LCJpYXQiOjE0NTQ0OTI0MDksImlkTG9nIjoxMjMsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxIiwidXNlck5hbWUiOiJkZW1vMCIsInV1aWQiOiIifQ.zNbCt6QPC6D3OIIdXgdRgKf-gX6ruXcldaAd92q2G2Oi66qn1NIfHby_Wt3umWqhEaI8hpQ1FjjUDj5EgrKmsg','2016-02-03 16:40:10',NULL,NULL,'0'),(124,11,'admin','','PC','Chrome 48','','2016-02-16 19:40:20',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU1NjIyODIwLCJpYXQiOjE0NTU2MTkyMjAsImlkTG9nIjoxMjQsImxvY2F0aW9uIjoiIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjExIiwidXNlck5hbWUiOiJhZG1pbiIsInV1aWQiOiIifQ.0Zwt5rS9MKXYSOuQbmossV6eSxjaLVtfn0VqCGbkQRPBqFpv4GYMarrFOHL5p8s2G4OsCzUcFefK2wqIBoACFA','2016-02-16 19:40:20',NULL,NULL,'0'),(125,11,'admin','','PC','Chrome 48','','2016-02-16 19:40:54',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU1NjIyODU0LCJpYXQiOjE0NTU2MTkyNTQsImlkTG9nIjoxMjUsImxvY2F0aW9uIjoiIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjExIiwidXNlck5hbWUiOiJhZG1pbiIsInV1aWQiOiIifQ.BOG0W1eVslSueO9W0ZkbXPGYHpoeni2bmzCxV9-HxPu2kGonEOUJ5GGbxcIpyCckpfhP8iH4fhfzmcMJacj00A','2016-02-16 19:40:54',NULL,NULL,'0'),(126,11,'admin','','PC','Chrome 48','','2016-02-16 19:44:38',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU1NjIzMDc4LCJpYXQiOjE0NTU2MTk0NzgsImlkTG9nIjoxMjYsImxvY2F0aW9uIjoiIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjExIiwidXNlck5hbWUiOiJhZG1pbiIsInV1aWQiOiIifQ.naSH0KHOCCe0x0UOws80Va3Edpb6g_uDOIKQ-MK6qQV3i8z9DLzvyRWKsqoamNAydQn6qvMEXob2fzhBEC5lWw','2016-02-16 19:44:38',NULL,NULL,'0'),(127,2,'demo1','','PC','Chrome 48','Ho Chi Minh City, Vietnam','2016-02-16 19:45:50',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU1NjIzMTUwLCJpYXQiOjE0NTU2MTk1NTAsImlkTG9nIjoxMjcsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIyIiwidXNlck5hbWUiOiJkZW1vMSIsInV1aWQiOiIifQ.M4MH30dc30AkDqEd-q3Fzj1Y6is-okfbycP214-A_1V8bIWFxKOtz2PSlvdyXXI2NTDt4eqfgZVUfcs-c-iiDg','2016-02-16 19:45:50',NULL,NULL,'0'),(128,11,'admin','','PC','Chrome 48','','2016-02-17 13:50:24',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU1Njg4MjI0LCJpYXQiOjE0NTU2ODQ2MjQsImlkTG9nIjoxMjgsImxvY2F0aW9uIjoiIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjExIiwidXNlck5hbWUiOiJhZG1pbiIsInV1aWQiOiIifQ.TaKpyADKnwGK-8vgSpzLOJCEEw7lGj9omPMn7JWL-9NwBTucEkCVayjc4G-WR4l8wJltURCWueT4a2-G8LptOg','2016-02-17 13:50:24',NULL,NULL,'0'),(129,11,'admin','','PC','Chrome 48','','2016-02-17 13:51:08',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU1Njg4MjY4LCJpYXQiOjE0NTU2ODQ2NjgsImlkTG9nIjoxMjksImxvY2F0aW9uIjoiIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjExIiwidXNlck5hbWUiOiJhZG1pbiIsInV1aWQiOiIifQ.Z2DYSK4TCiQC89f7HtoVe49quzFvTnAsfrR331aiAKFdCktDAOgn8GKzSRMdconLvSRDU4ou9k_P0BFw_CnRLA','2016-02-17 13:51:08',NULL,NULL,'0'),(130,11,'admin','','PC','Chrome 48','Ho Chi Minh City, Vietnam','2016-02-17 16:47:49',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU1Njk4ODY5LCJpYXQiOjE0NTU2OTUyNjksImlkTG9nIjoxMzAsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0.MS_VVfHVugyW5vnRvmnA5VhyGZzWOWH9sdHGRGFUIMik-Moff5cVmu29olZPx0FQjYz7JCC8_hAzO4Olk9ZTiA','2016-02-17 16:47:49',NULL,NULL,'0'),(131,11,'admin','','PC','Chrome 48','Ho Chi Minh City, Vietnam','2016-02-17 17:13:51',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU1NzAwNDMxLCJpYXQiOjE0NTU2OTY4MzEsImlkTG9nIjoxMzEsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0.fWtP1fkQ_SwYzNBgB5x8zEIjpexa-tDHGuZVYk7CU-2G2VXVqDlHsGDn2ig-B1qKRbxoslu6jwv_x7Ljl-aivg','2016-02-17 17:13:51',NULL,NULL,'0'),(132,11,'admin','','PC','Chrome 48','Ho Chi Minh City, Vietnam','2016-02-18 11:26:12',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU1NzY1OTcyLCJpYXQiOjE0NTU3NjIzNzIsImlkTG9nIjoxMzIsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0.3CQALj7m5YLNisNCCnvcpRNI1H1h4ui8crTHLMlgWG_GcnmJy7a6sX1njRVwWSEdMsMPmRReIF9alzQkbLzIbw','2016-02-18 11:26:12',NULL,NULL,'0'),(133,11,'admin','','PC','Chrome 48','Ho Chi Minh City, Vietnam','2016-02-18 13:28:59',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU1NzczMzM5LCJpYXQiOjE0NTU3Njk3MzksImlkTG9nIjoxMzMsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0.ouvQ872Uj0Er7mADm4lVHV7AP0CzaqFad2kP2OkCOXlgZlmeVSUHSl7Loezl-U-AO6KNuZyafqpfgBf8rdM7pA','2016-02-18 13:28:59',NULL,NULL,'0'),(134,11,'admin','','PC','Chrome 48','Ho Chi Minh City, Vietnam','2016-02-18 13:47:39',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU1Nzc0NDU5LCJpYXQiOjE0NTU3NzA4NTksImlkTG9nIjoxMzQsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0._T9sles0qtK214z04ABUTEhxwo36-nUr82cA_sK2ZxMrgSO4QuffWI6apf587veznvHRzannEBy48FDyvGeF0g','2016-02-18 13:47:39',NULL,NULL,'0'),(135,11,'admin','','PC','Chrome 48','Ho Chi Minh City, Vietnam','2016-02-18 17:10:03',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU1Nzg2NjAzLCJpYXQiOjE0NTU3ODMwMDMsImlkTG9nIjoxMzUsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0.lRZ8axgQFkObZ9zqd665oXtIwlwdbS_OeS7JyPcr_WQlKPjxE21xddpdmVNGtPnRLsasOH23ThEPYsGoQ5PwLw','2016-02-18 17:10:03',NULL,NULL,'0'),(136,11,'admin','','PC','Chrome 47','HCMV, Vietnam','2016-02-18 17:10:56',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ3IiwiZXhwIjoxNDU1Nzg2NjU2LCJpYXQiOjE0NTU3ODMwNTYsImlkTG9nIjoxMzYsImxvY2F0aW9uIjoiSENNViwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0.g9Wy1e2q_X8jxoY_QeHiybONl3rda4tPKYO5IIs8fsztRFer-Q6Zu3BMupiDIbfR5TRxN0VnWiSFYGSS-aTxkQ','2016-02-18 17:10:56',NULL,NULL,'0'),(137,1,'demo0','','PC','Chrome 48','Ho Chi Minh City, Vietnam','2016-02-18 17:58:41',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU1Nzg5NTIyLCJpYXQiOjE0NTU3ODU5MjIsImlkTG9nIjoxMzcsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxIiwidXNlck5hbWUiOiJkZW1vMCIsInV1aWQiOiIifQ.aNFG2_hByRLgmbBSzr3AHrPirktgl73minPImF0iMQm6C1flep3SDC4-Ij50RNSRm_HRudtp2Oshe6aml6RJ6A','2016-02-18 17:58:41',NULL,NULL,'0'),(138,11,'admin','','PC','Chrome 46','Ho Chi Minh City, Vietnam','2016-02-18 18:07:49',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ2IiwiZXhwIjoxNDU1NzkwMDY5LCJpYXQiOjE0NTU3ODY0NjksImlkTG9nIjoxMzgsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0.PY4qLNmBVzbPCQDJ8OBRt5fjjqgQs5TAwfphO-ZIMy4EhVPT8idzCQwbAh1dJERMlLPvTTQQeixO_V2Oe9PR4w','2016-02-18 18:07:49',NULL,NULL,'0'),(139,11,'admin','','PC','Chrome 46','Ho Chi Minh City, Vietnam','2016-02-18 19:39:15',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ2IiwiZXhwIjoxNDU1Nzk1NTU1LCJpYXQiOjE0NTU3OTE5NTUsImlkTG9nIjoxMzksImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0.Cw7lNa95OnN8Sgc7mUg8VFIGa_0qN4xaN3QjiEevG5-FwihzqK8yqoF6IsH5gAaSQx_G1AqvthdUlLsBUMIIvg','2016-02-18 19:39:15',NULL,NULL,'0'),(140,1,'demo0','','PC','Chrome 48','Ho Chi Minh City, Vietnam','2016-02-19 11:08:24',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU1ODUxMzA0LCJpYXQiOjE0NTU4NDc3MDQsImlkTG9nIjoxNDAsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxIiwidXNlck5hbWUiOiJkZW1vMCIsInV1aWQiOiIifQ.L73LRf51eU3Z1Y9nBYdKLSbw225CBbE8YU9sdzcnEbxjaCHE4zl1FU9LJjDTSneM4P7KXuVxvNMm8XfxaFct9A','2016-02-19 11:08:24',NULL,NULL,'0'),(141,11,'admin','','PC','Chrome 48','Ho Chi Minh City, Vietnam','2016-02-19 11:08:25',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU1ODUxMzA1LCJpYXQiOjE0NTU4NDc3MDUsImlkTG9nIjoxNDEsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0.sF3oXo79P8kvwjQ90Jy8LRRq80va6ZNF8qzX1jfE4UGy42IbmUviH2LXp-ZbcpBSFn39JDQ_PHDQBNs4ou__Hg','2016-02-19 11:08:25',NULL,NULL,'0'),(142,11,'admin','','PC','Chrome 48','Ho Chi Minh City, Vietnam','2016-02-19 11:54:30',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU1ODU0MDcwLCJpYXQiOjE0NTU4NTA0NzAsImlkTG9nIjoxNDIsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0.gDr_pQauWAOdNk5yA4-Jj6d0CLE4ouxQeavaRf2-zrTEqbTZU0tNC4YO1wn-R3YgW8pGOM00NdF20_a3GnROvg','2016-02-19 11:54:30',NULL,NULL,'0'),(143,11,'admin','','PC','Chrome 48','Ho Chi Minh City, Vietnam','2016-02-19 11:55:28',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU1ODU0MTI4LCJpYXQiOjE0NTU4NTA1MjgsImlkTG9nIjoxNDMsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0.77VpuCj2OaQCn-kfdXjY9NmMaK0Zblqz4RZDXZA5PxhjjNwpWFhODF9pcZQetRfyDFtN_ubCuS62nQgOLA8c5w','2016-02-19 11:55:28',NULL,NULL,'0'),(144,1,'demo0','','PC','Chrome 48','Ho Chi Minh City, Vietnam','2016-02-19 11:57:40','2016-02-19 12:04:11','構文','1','練習','記憶力','ジョンさんはイギリス人です','vocabulary','1/3','ジョンさん-ジョンさん; イギリスじん-イギリスじん; わたし-わたし; えいがかん-じてんしゃ; じてんしゃ-イギリスじん',60,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU1ODUxMzA0LCJpYXQiOjE0NTU4NDc3MDQsImlkTG9nIjoxNDAsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxIiwidXNlck5hbWUiOiJkZW1vMCIsInV1aWQiOiIifQ.L73LRf51eU3Z1Y9nBYdKLSbw225CBbE8YU9sdzcnEbxjaCHE4zl1FU9LJjDTSneM4P7KXuVxvNMm8XfxaFct9A','2016-02-19 12:04:11',NULL,NULL,'0'),(145,1,'demo0','','PC','Chrome 48','Ho Chi Minh City, Vietnam','2016-02-19 11:57:40','2016-02-19 12:08:02','構文','1','練習','記憶力','ジョンさんはイギリス人です','scenario','1/3','ジョンさんはイギリスじんです-ジョンさんはイギリスじんですか; ジョンさんはイギリスじんですか-わたしはじてんしゃでえいがかんへいきました; ジョンさんはじてんしゃでえいがかんへいきませんでした-ジョンさんはじてんしゃでえいがかんへいきませんでした; わたしはじてんしゃでえいがかんへいきます-わたしはじてんしゃでえいがかんへいきます; わたしはじてんしゃでえいがかんへいきました-ジョンさんはじてんしゃでえいがかんへいきませんでした',40,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU1ODUxMzA0LCJpYXQiOjE0NTU4NDc3MDQsImlkTG9nIjoxNDAsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxIiwidXNlck5hbWUiOiJkZW1vMCIsInV1aWQiOiIifQ.L73LRf51eU3Z1Y9nBYdKLSbw225CBbE8YU9sdzcnEbxjaCHE4zl1FU9LJjDTSneM4P7KXuVxvNMm8XfxaFct9A','2016-02-19 12:08:02',NULL,NULL,'0'),(146,1,'demo0','','PC','Chrome 48','','2016-02-19 12:18:38',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU1ODU1NTE4LCJpYXQiOjE0NTU4NTE5MTgsImlkTG9nIjoxNDYsImxvY2F0aW9uIjoiIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.qspy0RGewVI9Jmfbrt9gi7w79LJFkquE66QAj7vp5lEXRAu43P5_WvTdsyx0QpDpYvtt8sBEnwJu9Q-w_sOLnw','2016-02-19 12:18:38',NULL,NULL,'0'),(147,1,'demo0','','PC','Chrome 48','','2016-02-19 12:18:55','2016-02-19 12:33:05','構文','1','練習','筆記力','ジョンさんはイギリス人です','scenario','1/3','わたしはジョンさんとえいがかんへいきました-わたしはジョンさんとえいがかんへいきました; ジョンさんはじてんしゃでえいがかんへいきません-ジョンさんはじてんしゃでえいがかんへいきません; わたしはあにとえいがかんへいきます-わたしはあにとえいがかんへいきます; ジョンさんはじてんしゃでえいがかんへいきます-ジョンさんはじてんしゃでえいがかんへいきます; ジョンさんはイギリスじんですか-ジョンさんはイギリスじんですか',100,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU1ODU1NTE4LCJpYXQiOjE0NTU4NTE5MTgsImlkTG9nIjoxNDYsImxvY2F0aW9uIjoiIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.qspy0RGewVI9Jmfbrt9gi7w79LJFkquE66QAj7vp5lEXRAu43P5_WvTdsyx0QpDpYvtt8sBEnwJu9Q-w_sOLnw','2016-02-19 12:33:05',NULL,NULL,'0'),(148,1,'demo0','','PC','Chrome 48','','2016-02-19 12:18:55','2016-02-19 12:36:14','構文','1','練習','筆記力','ジョンさんはイギリス人です','vocabulary','1/3','ジョンさん-ジョンさん; イギリスじん-イギリスじん; わたし-わたし; あに-あし; えいがかん-えいがかん',80,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU1ODU1NTE4LCJpYXQiOjE0NTU4NTE5MTgsImlkTG9nIjoxNDYsImxvY2F0aW9uIjoiIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.qspy0RGewVI9Jmfbrt9gi7w79LJFkquE66QAj7vp5lEXRAu43P5_WvTdsyx0QpDpYvtt8sBEnwJu9Q-w_sOLnw','2016-02-19 12:36:14',NULL,NULL,'0'),(149,11,'admin','','PC','Chrome 48','Ho Chi Minh City, Vietnam','2016-02-19 12:55:49',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU1ODU3NzQ5LCJpYXQiOjE0NTU4NTQxNDksImlkTG9nIjoxNDksImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0.UrrWuzhL-YDGXUhqVu4qAwFJtQXHc2UKlWzWBPLvH7VbkEm2fG9_d1FeFgHCLbF1PzrMJD8SkPhthI56JN25Gg','2016-02-19 12:55:49',NULL,NULL,'0'),(150,1,'demo0','','PC','Chrome 48','','2016-02-19 13:03:00','2016-02-19 13:11:20','構文','1','テスト','記憶力','ジョンさんはイギリス人です','scenario','1/1','ジョンさんはイギリスじんです-ジョンさんはイギリスじんです; ジョンさんはじてんしゃでえいがかんへいきましたか-ジョンさんはじてんしゃでえいがかんへいきましたか; わたしはあにとえいがかんへいきました-わたしはあにとえいがかんへいきました; わたしはジョンさんとえいがかんへいきました-わたしはジョンさんとえいがかんへいきました; わたしはじてんしゃでえいがかんへいきます-わたしはじてんしゃでえいがかんへいきます',100,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU1ODU1NTE4LCJpYXQiOjE0NTU4NTE5MTgsImlkTG9nIjoxNDYsImxvY2F0aW9uIjoiIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.qspy0RGewVI9Jmfbrt9gi7w79LJFkquE66QAj7vp5lEXRAu43P5_WvTdsyx0QpDpYvtt8sBEnwJu9Q-w_sOLnw','2016-02-19 13:11:20',NULL,NULL,'0'),(151,1,'demo0','','PC','Chrome 48','','2016-02-19 13:03:00','2016-02-19 13:13:24','構文','1','テスト','記憶力','ジョンさんはイギリス人です','vocabulary','1/1','ジョンさん-ジョンさん; イギリスじん-イギリスじん; わたし-わたし; あに-あに; えいがかん-えいがかん',100,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU1ODU1NTE4LCJpYXQiOjE0NTU4NTE5MTgsImlkTG9nIjoxNDYsImxvY2F0aW9uIjoiIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.qspy0RGewVI9Jmfbrt9gi7w79LJFkquE66QAj7vp5lEXRAu43P5_WvTdsyx0QpDpYvtt8sBEnwJu9Q-w_sOLnw','2016-02-19 13:13:24',NULL,NULL,'0'),(152,11,'admin','','PC','Chrome 48','Ho Chi Minh City, Vietnam','2016-02-19 13:58:19','2016-02-19 13:58:26',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU1ODYxNDk5LCJpYXQiOjE0NTU4NTc4OTksImlkTG9nIjoxNTIsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0.yZvAlkPzzv8Lv8_J-QfPknCUQvlL3tsKkCZEy_wSkTOIZnlmARfl608N53227sW_IBCf4b8H4J-dWtUIbx4Zow','2016-02-19 13:58:19',NULL,NULL,'0'),(153,1,'demo0','','PC','Chrome 48','Ho Chi Minh City, Vietnam','2016-02-19 14:03:25',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU1ODYxODA1LCJpYXQiOjE0NTU4NTgyMDUsImlkTG9nIjoxNTMsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxIiwidXNlck5hbWUiOiJkZW1vMCIsInV1aWQiOiIifQ.QwNekbTTOeu4Ev6aahity9jAUlVzaDDCJ9WeEKI41Zfauf6K-K4SPeMbAXYlcejeg8W6HvU8BL1tP6n_VqNC7Q','2016-02-19 14:03:25',NULL,NULL,'0'),(154,1,'demo0','','PC','Chrome 48','Ho Chi Minh City, Vietnam','2016-02-19 15:17:04',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU1ODY2MjI0LCJpYXQiOjE0NTU4NjI2MjQsImlkTG9nIjoxNTQsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxIiwidXNlck5hbWUiOiJkZW1vMCIsInV1aWQiOiIifQ.t6ZIVaVuq9n1rb0P2nD_K8E24k85WrszC7Oe2VMcmfUWsv03ltwmxmfZymql2-8eznzUg4jFcQuO1ZXNAiS9mQ','2016-02-19 15:17:04',NULL,NULL,'0'),(155,11,'admin','','PC','Chrome 48','Ho Chi Minh City, Vietnam','2016-02-19 15:36:10',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU1ODY3MzcwLCJpYXQiOjE0NTU4NjM3NzAsImlkTG9nIjoxNTUsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0.5smg2THatvtIEpgO0TZe_bds_wC1pIz2T9WW7KvpljNwEbjmha2IiqLh3E5go5tjPGy_Ixq0O-fM1o2pi4TybQ','2016-02-19 15:36:10',NULL,NULL,'0'),(156,11,'admin','','PC','Chrome 48','Ho Chi Minh City, Vietnam','2016-02-19 15:58:33',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU1ODY4NzEzLCJpYXQiOjE0NTU4NjUxMTMsImlkTG9nIjoxNTYsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0.09O-YBidwa-TFSv7ueMiXcKfyySbrsJJdwncVHBGBkrLDeGXZKrzfNIE8obKADsZw9sy20t_ES0EZJgqJg0Z8g','2016-02-19 15:58:33',NULL,NULL,'0'),(157,1,'demo0','','PC','Chrome 48','Ho Chi Minh City, Vietnam','2016-02-19 15:58:39',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU1ODY4NzE5LCJpYXQiOjE0NTU4NjUxMTksImlkTG9nIjoxNTcsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxIiwidXNlck5hbWUiOiJkZW1vMCIsInV1aWQiOiIifQ.boJZ3fY4nAqxyuP943SVIVdymhUhiEU4cTVbmDrooWBBaaxT73PxRLzeg2XRA2MyHOZWKpxLs5i7IyuQbnJsmQ','2016-02-19 15:58:39',NULL,NULL,'0'),(158,1,'demo0','','PC','Chrome 48','Ho Chi Minh City, Vietnam','2016-02-19 15:58:55','2016-02-19 16:07:15','構文','1','テスト','筆記力','ジョンさんはイギリス人です','scenario','1/1','ジョンさんはじてんしゃでえいがかんへいきません-ジョンさんはじてんしゃでえいがかんへいきません; ジョンさんはじてんしゃでえいがかんへいきました-ジョンさんはじてんしゃでえいがかんへいきました; ジョンさんはイギリスじんです-ジョンさんはイギリスじんです; ジョンさんはじてんしゃでえいがかんへいきます-ジョンさんはじてんしゃでえいがかんへいきます; わたしはジョンさんとえいがかんへいきました-Tôiđã đi đếnđến rạp chiếu phim Johnvà phim',80,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU1ODY4NzE5LCJpYXQiOjE0NTU4NjUxMTksImlkTG9nIjoxNTcsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxIiwidXNlck5hbWUiOiJkZW1vMCIsInV1aWQiOiIifQ.boJZ3fY4nAqxyuP943SVIVdymhUhiEU4cTVbmDrooWBBaaxT73PxRLzeg2XRA2MyHOZWKpxLs5i7IyuQbnJsmQ','2016-02-19 16:07:15',NULL,NULL,'0'),(159,1,'demo0','','PC','Chrome 48','Ho Chi Minh City, Vietnam','2016-02-19 15:58:55','2016-02-19 16:09:41','構文','1','テスト','筆記力','ジョンさんはイギリス人です','vocabulary','1/1','ジョンさん-John; イギリスじん-イギリスじん; わたし-わたし; えいがかん-えいがかん; じてんしゃ-じてんしゃ',80,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU1ODY4NzE5LCJpYXQiOjE0NTU4NjUxMTksImlkTG9nIjoxNTcsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxIiwidXNlck5hbWUiOiJkZW1vMCIsInV1aWQiOiIifQ.boJZ3fY4nAqxyuP943SVIVdymhUhiEU4cTVbmDrooWBBaaxT73PxRLzeg2XRA2MyHOZWKpxLs5i7IyuQbnJsmQ','2016-02-19 16:09:41',NULL,NULL,'0'),(160,11,'admin','','PC','Chrome 48','Ho Chi Minh City, Vietnam','2016-02-19 17:16:58',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU1ODczNDE4LCJpYXQiOjE0NTU4Njk4MTgsImlkTG9nIjoxNjAsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0.OWiebaoyPGmtMdfYSMZ5sBXfmwj2DmcDONaENdFZOs57YztGd3kv1k1eqNhrFvDaB9nQ3TERp840wx-sVK5hZA','2016-02-19 17:16:58',NULL,NULL,'0'),(161,11,'admin','','PC','Chrome 48','Ho Chi Minh City, Vietnam','2016-02-19 17:20:16',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU1ODczNjE2LCJpYXQiOjE0NTU4NzAwMTYsImlkTG9nIjoxNjEsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0.JVVJyOMIVr6I8wy89WksNhotqMVV2Eq7gl2FVXyZWWu-UFmDyAft8jlIWIK5tjIP9cQriZGMmGer44LGpcOLSg','2016-02-19 17:20:16',NULL,NULL,'0'),(162,1,'demo0','','PC','Chrome 48','','2016-02-19 17:46:50',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU1ODc1MjEwLCJpYXQiOjE0NTU4NzE2MTAsImlkTG9nIjoxNjIsImxvY2F0aW9uIjoiIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.RsJkw-HRbDPI_fWVCzdbQaHZ1opAW6pXlqDtGpt4WKWW6JnP54pNw7T-YNInvnwxhzH-pFhryNpFnroOzw4uqQ','2016-02-19 17:46:50',NULL,NULL,'0'),(163,11,'admin','','PC','Chrome 48','Ho Chi Minh City, Vietnam','2016-02-19 18:46:26',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU1ODc4Nzg2LCJpYXQiOjE0NTU4NzUxODYsImlkTG9nIjoxNjMsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0.mlQlJbvneBAkSzfXpNy1F3aMlR050U-X4EqHG0eOUQu9kpvOcFV8MbwXhS1fUxdf3jB1Ng2lqpRXZlrwQIek2A','2016-02-19 18:46:26',NULL,NULL,'0'),(164,11,'admin','f7242f21-038b-40ca-b8c1-81876c2a98bd','Android','Android 4.1.2','Indonesia','2016-02-19 19:51:28',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiQW5kcm9pZCIsImRldmljZVZlcnNpb24iOiJBbmRyb2lkIDQuMS4yIiwiZXhwIjoxNDU1ODgyNjg4LCJpYXQiOjE0NTU4NzkwODgsImlkTG9nIjoxNjQsImxvY2F0aW9uIjoiSW5kb25lc2lhIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjExIiwidXNlck5hbWUiOiJhZG1pbiIsInV1aWQiOiJmNzI0MmYyMS0wMzhiLTQwY2EtYjhjMS04MTg3NmMyYTk4YmQifQ._rx2NHJWJl9YsK5x1YczYRz4qEoXCjsfIfZNHnm4NR-sefpS0a6YocYKTb2vZILLhn7JWmuiCWfMFJMVFnqqUQ','2016-02-19 19:51:28',NULL,NULL,'0'),(165,11,'admin','f7242f21-038b-40ca-b8c1-81876c2a98bd','Android','Android 4.1.2','Indonesia','2016-02-19 19:52:52',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiQW5kcm9pZCIsImRldmljZVZlcnNpb24iOiJBbmRyb2lkIDQuMS4yIiwiZXhwIjoxNDU1ODgyNzcyLCJpYXQiOjE0NTU4NzkxNzIsImlkTG9nIjoxNjUsImxvY2F0aW9uIjoiSW5kb25lc2lhIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjExIiwidXNlck5hbWUiOiJhZG1pbiIsInV1aWQiOiJmNzI0MmYyMS0wMzhiLTQwY2EtYjhjMS04MTg3NmMyYTk4YmQifQ.Q5da2UxfEvQ00FIf0pdyMFsgocIssQJiGX9iXiRRb5zoJYZAM80mRp6UOEZNccn04bSLuIcOFpCsr4eRnBTg6Q','2016-02-19 19:52:52',NULL,NULL,'0'),(166,11,'admin','a1627f3706a709aeca20c59b9e51045c5defb86d','iOS','7.0','116/10/8 Đường Hoàng Hoa Thám, Phường 12, Tân Bình, Hồ Chí Minh, Vietnam','2016-02-19 20:05:09','2016-02-19 20:09:28',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjcuMCIsImV4cCI6MTQ1NTg4MzUwOSwiaWF0IjoxNDU1ODc5OTA5LCJpZExvZyI6MTY2LCJsb2NhdGlvbiI6IjExNi8xMC84IMSQxrDhu51uZyBIb8OgbmcgSG9hIFRow6FtLCBQaMaw4budbmcgMTIsIFTDom4gQsOsbmgsIEjhu5MgQ2jDrSBNaW5oLCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjExIiwidXNlck5hbWUiOiJhZG1pbiIsInV1aWQiOiJhMTYyN2YzNzA2YTcwOWFlY2EyMGM1OWI5ZTUxMDQ1YzVkZWZiODZkIn0.latCnyQN2BX_KUa2k6vn3l9tnpvuSgzpKeu82S8APNhiOvwl6vh0-g2cSgciQBkHWXOY0988NFY-JlV0YEtHgw','2016-02-19 20:05:09',NULL,NULL,'0'),(167,1,'demo0','a1627f3706a709aeca20c59b9e51045c5defb86d','iOS','7.0','116/10/8 Đường Hoàng Hoa Thám, Phường 12, Tân Bình, Hồ Chí Minh, Vietnam','2016-02-19 20:09:48',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjcuMCIsImV4cCI6MTQ1NTg4Mzc4OCwiaWF0IjoxNDU1ODgwMTg4LCJpZExvZyI6MTY3LCJsb2NhdGlvbiI6IjExNi8xMC84IMSQxrDhu51uZyBIb8OgbmcgSG9hIFRow6FtLCBQaMaw4budbmcgMTIsIFTDom4gQsOsbmgsIEjhu5MgQ2jDrSBNaW5oLCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6ImExNjI3ZjM3MDZhNzA5YWVjYTIwYzU5YjllNTEwNDVjNWRlZmI4NmQifQ.6bnm09xiScF1uMcjkdcLqEh699rVtJdKKIk_amewRkv1cztDeJVTHh8MBeFxwNln39yz8n-nfHojpvNMj_4rnA','2016-02-19 20:09:48',NULL,NULL,'0'),(168,1,'demo0','a1627f3706a709aeca20c59b9e51045c5defb86d','iOS','7.0','116/10/6 Đường Hoàng Hoa Thám, Phường 12, Tân Bình, Hồ Chí Minh, Vietnam','2016-02-19 20:11:03',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjcuMCIsImV4cCI6MTQ1NTg4Mzg2MywiaWF0IjoxNDU1ODgwMjYzLCJpZExvZyI6MTY4LCJsb2NhdGlvbiI6IjExNi8xMC82IMSQxrDhu51uZyBIb8OgbmcgSG9hIFRow6FtLCBQaMaw4budbmcgMTIsIFTDom4gQsOsbmgsIEjhu5MgQ2jDrSBNaW5oLCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6ImExNjI3ZjM3MDZhNzA5YWVjYTIwYzU5YjllNTEwNDVjNWRlZmI4NmQifQ.Sn5j4KDKvfyN_ZrX9K5RKOz45esHeIsNowzaf9Cj7WsrFzHrQcA55zWtcjPudnQEuoZZuH_apYeNBIlSTMG0aA','2016-02-19 20:11:03',NULL,NULL,'0'),(169,1,'demo0','a1627f3706a709aeca20c59b9e51045c5defb86d','iOS','7.0','116/10/6 Đường Hoàng Hoa Thám, Phường 12, Tân Bình, Hồ Chí Minh, Vietnam','2016-02-19 20:15:53',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjcuMCIsImV4cCI6MTQ1NTg4NDE1MywiaWF0IjoxNDU1ODgwNTUzLCJpZExvZyI6MTY5LCJsb2NhdGlvbiI6IjExNi8xMC82IMSQxrDhu51uZyBIb8OgbmcgSG9hIFRow6FtLCBQaMaw4budbmcgMTIsIFTDom4gQsOsbmgsIEjhu5MgQ2jDrSBNaW5oLCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6ImExNjI3ZjM3MDZhNzA5YWVjYTIwYzU5YjllNTEwNDVjNWRlZmI4NmQifQ._yO8UsG0mQaVP5y1k1IPVFCiTLKibPtrZT51O0G41UO8GhgzohQFVW1316WAEHDWPBHgF3TggfM26M-bnYEmiA','2016-02-19 20:15:53',NULL,NULL,'0'),(170,1,'demo0','','PC','Safari 600','Ho Chi Minh City, Vietnam','2016-02-19 20:17:57',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiU2FmYXJpIDYwMCIsImV4cCI6MTQ1NTg4NDI3NywiaWF0IjoxNDU1ODgwNjc3LCJpZExvZyI6MTcwLCJsb2NhdGlvbiI6IkhvIENoaSBNaW5oIENpdHksIFZpZXRuYW0iLCJuYXRpdmVMYW5ndWFnZSI6IkVOIiwidXNlcklkIjoiMSIsInVzZXJOYW1lIjoiZGVtbzAiLCJ1dWlkIjoiIn0.Anyry42ZBguAC9V7Nd5w_3srMyc-7dA6UuXDtFvSo7GMjkd6xEnTRrwaeVeLi39npoKmhhKLoj34ohMwZWQTwA','2016-02-19 20:17:57',NULL,NULL,'0'),(171,1,'demo0','','PC','Chrome 48','Ho Chi Minh City, Vietnam','2016-02-19 20:19:09',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU1ODg0MzQ5LCJpYXQiOjE0NTU4ODA3NDksImlkTG9nIjoxNzEsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxIiwidXNlck5hbWUiOiJkZW1vMCIsInV1aWQiOiIifQ.OIzxNZvRuER4ngENFaESlLzX5weJ6c3flZekVeaklkyDE0TAPZOSBFr0xSd64XQRX_hh4Ttpzzt-t2g4bfMe9Q','2016-02-19 20:19:09',NULL,NULL,'0'),(172,1,'demo0','','PC','Chrome 48','Ho Chi Minh City, Vietnam','2016-02-19 20:24:41',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU1ODg0NjgxLCJpYXQiOjE0NTU4ODEwODEsImlkTG9nIjoxNzIsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxIiwidXNlck5hbWUiOiJkZW1vMCIsInV1aWQiOiIifQ.SDVrXu_A6IhErSOuv5V1Z5WCYqJF-nTIBx5VbiCTbhmUFnS2HvoHLltvl5hZxdXifM2oW7EPjjVjV82_BbbP-Q','2016-02-19 20:24:41',NULL,NULL,'0'),(173,1,'demo0','','PC','Chrome 43','Ho Chi Minh City, Vietnam','2016-02-19 20:35:38',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQzIiwiZXhwIjoxNDU1ODg1MzM4LCJpYXQiOjE0NTU4ODE3MzgsImlkTG9nIjoxNzMsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxIiwidXNlck5hbWUiOiJkZW1vMCIsInV1aWQiOiIifQ.2qQh9l8Y9CM7caHLE1mAMg-X_UeOKnfnslFTu1DR_RmBFYLfaaLEdZRYvtl20mAYDpmbIhTcqt620_1RHTvhPg','2016-02-19 20:35:38',NULL,NULL,'0'),(174,1,'demo0','','PC','Firefox 43','','2016-02-19 20:37:45',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiRmlyZWZveCA0MyIsImV4cCI6MTQ1NTg4NTQ2NSwiaWF0IjoxNDU1ODgxODY1LCJpZExvZyI6MTc0LCJsb2NhdGlvbiI6IiIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxIiwidXNlck5hbWUiOiJkZW1vMCIsInV1aWQiOiIifQ.REV62mzud5Grnw6tF2VRfeD_C3MHBnDD2tv3KoFTXILf9mFLirC7Php7A12u3xoZSsHms5N_kyeOh8CAP7DS3A','2016-02-19 20:37:45',NULL,NULL,'0'),(175,11,'admin','','PC','UNKNOWN NaN','Ho Chi Minh City, Vietnam','2016-02-19 20:43:56',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiVU5LTk9XTiBOYU4iLCJleHAiOjE0NTU4ODU4MzYsImlhdCI6MTQ1NTg4MjIzNiwiaWRMb2ciOjE3NSwibG9jYXRpb24iOiJIbyBDaGkgTWluaCBDaXR5LCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjExIiwidXNlck5hbWUiOiJhZG1pbiIsInV1aWQiOiIifQ.dYfyh8Mzj3gYpQ8GC_NovWJX20wz6y-3V3UH-8Cd5-_Joh40AViPcN6ekqjfVah8BUfNcbSog9Yoy9K0ZKMZag','2016-02-19 20:43:56',NULL,NULL,'0'),(176,11,'admin','','PC','Chrome 48','Ho Chi Minh City, Vietnam','2016-02-22 10:35:51',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU2MTA4NTUxLCJpYXQiOjE0NTYxMDQ5NTEsImlkTG9nIjoxNzYsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0.LkjQZr7k23Uwi6hHGIZAUFjlzljbN_Zo9fxnf5rtp5VGi2rVFMk-PtT8oI31y8NRkHgmvZuwwkptX8DZZCSwAw','2016-02-22 10:35:51',NULL,NULL,'0'),(177,1,'demo0','','PC','Chrome 48','Ho Chi Minh City, Vietnam','2016-02-22 10:39:10',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU2MTA4NzUxLCJpYXQiOjE0NTYxMDUxNTEsImlkTG9nIjoxNzcsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxIiwidXNlck5hbWUiOiJkZW1vMCIsInV1aWQiOiIifQ.md_ExChAoOXaoyQNco5kPC4m4XuhDXf3mfsrsWSIEWxQx7pXYVpdXtBg4lYXmyA53bp1-BwqYVi7AEAPctc74g','2016-02-22 10:39:10',NULL,NULL,'0'),(178,1,'demo0','','PC','Chrome 48','Ho Chi Minh City, Vietnam','2016-02-22 11:01:04',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU2MTEwMDY0LCJpYXQiOjE0NTYxMDY0NjQsImlkTG9nIjoxNzgsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxIiwidXNlck5hbWUiOiJkZW1vMCIsInV1aWQiOiIifQ.AUH9BMb3pQJVuKG3MNk62OfkNRYhTa66cwfnx5wDv0nzTqqTu8jVqE-2NNHng_a8VNhylxr5mZWJ32v_gAXzGw','2016-02-22 11:01:04',NULL,NULL,'0'),(179,1,'demo0','a1627f3706a709aeca20c59b9e51045c5defb86d','iOS','7.0','232/14 Cộng Hòa, Phường 12, Tân Bình, Hồ Chí Minh, Vietnam','2016-02-22 11:22:03',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjcuMCIsImV4cCI6MTQ1NjExMTMyMywiaWF0IjoxNDU2MTA3NzIzLCJpZExvZyI6MTc5LCJsb2NhdGlvbiI6IjIzMi8xNCBD4buZbmcgSMOyYSwgUGjGsOG7nW5nIDEyLCBUw6JuIELDrG5oLCBI4buTIENow60gTWluaCwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxIiwidXNlck5hbWUiOiJkZW1vMCIsInV1aWQiOiJhMTYyN2YzNzA2YTcwOWFlY2EyMGM1OWI5ZTUxMDQ1YzVkZWZiODZkIn0.Yis5Lu8LLusJE7HCGPJYj0AvHCkAav28rnMFJaJ07wUVZE7b4KWHJkeOEcuIWwnusotQBwpbj5IzbVfCXVv7Qg','2016-02-22 11:22:03',NULL,NULL,'0'),(180,11,'admin','','PC','Chrome 48','Ho Chi Minh City, Vietnam','2016-02-22 11:42:31',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU2MTEyNTUxLCJpYXQiOjE0NTYxMDg5NTEsImlkTG9nIjoxODAsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0.NjKjSfBvhr25lVKxCUKaVXcPJpgl_jH830ARcWs0FoQcNWihqFICL6Oq7r7f6EDMut4oV1vXQHRZKiZjaAo3qg','2016-02-22 11:42:31',NULL,NULL,'0'),(181,11,'admin','','PC','Chrome 48','Ho Chi Minh City, Vietnam','2016-02-22 12:42:42',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU2MTE2MTYzLCJpYXQiOjE0NTYxMTI1NjMsImlkTG9nIjoxODEsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0.zpRjCciM9xseSFAQRTYmjZIFKUsSTW3Jr9_48a4bE7zqeGETiqp_DjGvpxKuuvkhaX37pA3I1kefi6EDhog-RA','2016-02-22 12:42:42',NULL,NULL,'0'),(182,11,'admin','','PC','Chrome 48','Ho Chi Minh City, Vietnam','2016-02-22 14:03:20',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU2MTIxMDAwLCJpYXQiOjE0NTYxMTc0MDAsImlkTG9nIjoxODIsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0.ZCqnxJcXV-epLUKT57VHw-wnp6HFYcFaSi9GZZ2cQx525ln9d17ev4sRVRfpwuTVVSRKLohT0wAL1G9oRZMUNQ','2016-02-22 14:03:20',NULL,NULL,'0'),(183,11,'admin','','PC','Chrome 48','Ho Chi Minh City, Vietnam','2016-02-22 15:28:28',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU2MTI2MTA4LCJpYXQiOjE0NTYxMjI1MDgsImlkTG9nIjoxODMsImxvY2F0aW9uIjoiSG8gQ2hpIE1pbmggQ2l0eSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0.G9pq0wNwv2PYXwqAgbtqfiyUmb_ri9EYizsd99wnbVCRnyywpzJGXH7MEMubsuR0zYefIlYylzrGBo0RvH2aZg','2016-02-22 15:28:28',NULL,NULL,'0'),(184,1,'demo0','087aef9d780733cfd00d11fdbdda8a5eabd4b721','iOS','7.1','Tan Binh , Ho Chi Minh City','2016-02-23 11:15:26',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjcuMSIsImV4cCI6MTQ1NjE5NzMyNiwiaWF0IjoxNDU2MTkzNzI2LCJpZExvZyI6MTg0LCJsb2NhdGlvbiI6IlRhbiBCaW5oICwgSG8gQ2hpIE1pbmggQ2l0eSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxIiwidXNlck5hbWUiOiJkZW1vMCIsInV1aWQiOiIwODdhZWY5ZDc4MDczM2NmZDAwZDExZmRiZGRhOGE1ZWFiZDRiNzIxIn0.QGmhRByuLlQL2vXj-_RkfFP3bIko-Wb8uYmf7YvNmPmGMHLC8ps1j0sJhT5xojG-woJigs7uRiPWh9VjuHewbQ','2016-02-23 11:15:26',NULL,NULL,'0'),(185,1,'demo0','','PC','Safari 600','','2016-02-23 11:16:36',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiU2FmYXJpIDYwMCIsImV4cCI6MTQ1NjE5NzM5NiwiaWF0IjoxNDU2MTkzNzk2LCJpZExvZyI6MTg1LCJsb2NhdGlvbiI6IiIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxIiwidXNlck5hbWUiOiJkZW1vMCIsInV1aWQiOiIifQ.mwQR7Fo3vhI9eVcVNqI7d0RN0vrH-4OG2CWogl8jTR23At5WAFStTu4cYtwP_Ydyzg-_F_1xEOoabSDJXpE4Kg','2016-02-23 11:16:36',NULL,NULL,'0'),(186,1,'demo0','087aef9d780733cfd00d11fdbdda8a5eabd4b721','iOS','7.1','116/10/6 Đường Hoàng Hoa Thám, Phường 12, Tân Bình, Hồ Chí Minh, Vietnam','2016-02-23 11:19:18',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjcuMSIsImV4cCI6MTQ1NjE5NzU1OSwiaWF0IjoxNDU2MTkzOTU5LCJpZExvZyI6MTg2LCJsb2NhdGlvbiI6IjExNi8xMC82IMSQxrDhu51uZyBIb8OgbmcgSG9hIFRow6FtLCBQaMaw4budbmcgMTIsIFTDom4gQsOsbmgsIEjhu5MgQ2jDrSBNaW5oLCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IjA4N2FlZjlkNzgwNzMzY2ZkMDBkMTFmZGJkZGE4YTVlYWJkNGI3MjEifQ.qsR96FMMJ_ZcVipVwcc-jSSn8QRqHw9C8kTre18xFwir0ZO3HO-MVnarBpW4uNjUpu1Qu2VlVWN_j4whFevmHg','2016-02-23 11:19:18',NULL,NULL,'0'),(187,1,'demo0','087aef9d780733cfd00d11fdbdda8a5eabd4b721','iOS','7.1','116/10/10 Đường Hoàng Hoa Thám, Phường 12, Tân Bình, Hồ Chí Minh, Vietnam','2016-02-23 11:21:12',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjcuMSIsImV4cCI6MTQ1NjE5NzY3MiwiaWF0IjoxNDU2MTk0MDcyLCJpZExvZyI6MTg3LCJsb2NhdGlvbiI6IjExNi8xMC8xMCDEkMaw4budbmcgSG_DoG5nIEhvYSBUaMOhbSwgUGjGsOG7nW5nIDEyLCBUw6JuIELDrG5oLCBI4buTIENow60gTWluaCwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxIiwidXNlck5hbWUiOiJkZW1vMCIsInV1aWQiOiIwODdhZWY5ZDc4MDczM2NmZDAwZDExZmRiZGRhOGE1ZWFiZDRiNzIxIn0.33_mmIfJZwU7ymby0w4kAbMbZsNzKNKkfiNIoqY96AjUHujj2kP9c7i2Y6p3F1FW8iVNnPxmI9akGt9-xbhGWw','2016-02-23 11:21:12',NULL,NULL,'0'),(188,1,'demo0','087aef9d780733cfd00d11fdbdda8a5eabd4b721','iOS','7.1','116/10/2 Đường Hoàng Hoa Thám, Phường 12, Tân Bình, Hồ Chí Minh, Vietnam','2016-02-23 11:36:26',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjcuMSIsImV4cCI6MTQ1NjE5ODU4NiwiaWF0IjoxNDU2MTk0OTg2LCJpZExvZyI6MTg4LCJsb2NhdGlvbiI6IjExNi8xMC8yIMSQxrDhu51uZyBIb8OgbmcgSG9hIFRow6FtLCBQaMaw4budbmcgMTIsIFTDom4gQsOsbmgsIEjhu5MgQ2jDrSBNaW5oLCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IjA4N2FlZjlkNzgwNzMzY2ZkMDBkMTFmZGJkZGE4YTVlYWJkNGI3MjEifQ.7SwijLn79dIKiLRzeWpaz-_Vyvg9bZkdVOEo83d3YhrdXXr3SogDBWgma2k785hRBNBaxvl9TUxQGechTlIpzA','2016-02-23 11:36:26',NULL,NULL,'0'),(189,1,'demo0','087aef9d780733cfd00d11fdbdda8a5eabd4b721','iOS','7.1','116/10/2 Đường Hoàng Hoa Thám, Phường 12, Tân Bình, Hồ Chí Minh, Vietnam','2016-02-23 11:36:41','2016-02-23 11:40:11','構文','1','練習','会話力','ジョンさんはイギリス人です','scenario','1/3','わたしはじてんしゃでえいがかんへいきました-おれがじてんしゃでえがかんへいく',0,'1','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjcuMSIsImV4cCI6MTQ1NjE5ODU4NiwiaWF0IjoxNDU2MTk0OTg2LCJpZExvZyI6MTg4LCJsb2NhdGlvbiI6IjExNi8xMC8yIMSQxrDhu51uZyBIb8OgbmcgSG9hIFRow6FtLCBQaMaw4budbmcgMTIsIFTDom4gQsOsbmgsIEjhu5MgQ2jDrSBNaW5oLCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IjA4N2FlZjlkNzgwNzMzY2ZkMDBkMTFmZGJkZGE4YTVlYWJkNGI3MjEifQ.7SwijLn79dIKiLRzeWpaz-_Vyvg9bZkdVOEo83d3YhrdXXr3SogDBWgma2k785hRBNBaxvl9TUxQGechTlIpzA','2016-02-23 11:40:11',NULL,NULL,'0'),(190,1,'demo0','af1c7a683159f12593b2ac4bde51c41a1b61e1a9','iOS','7.1','116/10/2 Đường Hoàng Hoa Thám, Phường 12, Tân Bình, Hồ Chí Minh, Vietnam','2016-02-23 11:44:14',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjcuMSIsImV4cCI6MTQ1NjE5OTA1NCwiaWF0IjoxNDU2MTk1NDU0LCJpZExvZyI6MTkwLCJsb2NhdGlvbiI6IjExNi8xMC8yIMSQxrDhu51uZyBIb8OgbmcgSG9hIFRow6FtLCBQaMaw4budbmcgMTIsIFTDom4gQsOsbmgsIEjhu5MgQ2jDrSBNaW5oLCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6ImFmMWM3YTY4MzE1OWYxMjU5M2IyYWM0YmRlNTFjNDFhMWI2MWUxYTkifQ.6xisuRfEwip_N0gudzZq_kq8xSKzhsJbS7bxMb_BwvBI_yvP7NH_tZiuoDKY0dcmGAgtfVTnxFyOUjWBWUDzrA','2016-02-23 11:44:14',NULL,NULL,'0'),(191,11,'admin','f7242f21-038b-40ca-b8c1-81876c2a98bd','Android','Android 4.1.2','Indonesia','2016-02-23 11:47:19',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiQW5kcm9pZCIsImRldmljZVZlcnNpb24iOiJBbmRyb2lkIDQuMS4yIiwiZXhwIjoxNDU2MTk5MjM5LCJpYXQiOjE0NTYxOTU2MzksImlkTG9nIjoxOTEsImxvY2F0aW9uIjoiSW5kb25lc2lhIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjExIiwidXNlck5hbWUiOiJhZG1pbiIsInV1aWQiOiJmNzI0MmYyMS0wMzhiLTQwY2EtYjhjMS04MTg3NmMyYTk4YmQifQ.zg3tpgbOMn4Ra9eO_-SxS7z6KP95AB4sILHDfjiahYS5qb41zHMfIjwilkLF8XTyqFcHCHJO5YdNxkfC4fPGjw','2016-02-23 11:47:19',NULL,NULL,'0'),(192,1,'demo0','a1627f3706a709aeca20c59b9e51045c5defb86d','iOS','7.0','125 Đường Hoàng Hoa Thám, Tân Bình, Hồ Chí Minh, Vietnam','2016-02-23 11:58:20',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjcuMCIsImV4cCI6MTQ1NjE5OTkwMCwiaWF0IjoxNDU2MTk2MzAwLCJpZExvZyI6MTkyLCJsb2NhdGlvbiI6IjEyNSDEkMaw4budbmcgSG_DoG5nIEhvYSBUaMOhbSwgVMOibiBCw6xuaCwgSOG7kyBDaMOtIE1pbmgsIFZpZXRuYW0iLCJuYXRpdmVMYW5ndWFnZSI6IkVOIiwidXNlcklkIjoiMSIsInVzZXJOYW1lIjoiZGVtbzAiLCJ1dWlkIjoiYTE2MjdmMzcwNmE3MDlhZWNhMjBjNTliOWU1MTA0NWM1ZGVmYjg2ZCJ9.a84CRhZk0WwZ8t6Tym7GTGfHBgQ3ltpdibgvu5fcfwWYA--SQeBgHKmhip3fHwEQwmxGSU5_9wDJGReTypAwAQ','2016-02-23 11:58:20',NULL,NULL,'0'),(193,11,'admin','f7242f21-038b-40ca-b8c1-81876c2a98bd','Android','Android 4.1.2','Indonesia','2016-02-23 12:13:58','2016-02-23 12:14:01',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiQW5kcm9pZCIsImRldmljZVZlcnNpb24iOiJBbmRyb2lkIDQuMS4yIiwiZXhwIjoxNDU2MjAwODM4LCJpYXQiOjE0NTYxOTcyMzgsImlkTG9nIjoxOTMsImxvY2F0aW9uIjoiSW5kb25lc2lhIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjExIiwidXNlck5hbWUiOiJhZG1pbiIsInV1aWQiOiJmNzI0MmYyMS0wMzhiLTQwY2EtYjhjMS04MTg3NmMyYTk4YmQifQ.ELUYaw0YVQfdj8FGN_sK2r1YKDS4EqftYtzZO4paCBgeTPdICdZrjfnRK0wj2jvhdOQq415wQxeuNnt-cCfTog','2016-02-23 12:13:58',NULL,NULL,'0'),(194,2,'demo1','60815e87-e790-4395-9001-00fa2c12239f','Android','Android 4.1.2','ĐT607, Điện Ngọc, Điện Bàn, Quảng Nam, Vietnam','2016-02-23 12:14:17',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiQW5kcm9pZCIsImRldmljZVZlcnNpb24iOiJBbmRyb2lkIDQuMS4yIiwiZXhwIjoxNDU2MjAwODU3LCJpYXQiOjE0NTYxOTcyNTcsImlkTG9nIjoxOTQsImxvY2F0aW9uIjoixJBUNjA3LCDEkGnhu4duIE5n4buNYywgxJBp4buHbiBCw6BuLCBRdeG6o25nIE5hbSwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIyIiwidXNlck5hbWUiOiJkZW1vMSIsInV1aWQiOiI2MDgxNWU4Ny1lNzkwLTQzOTUtOTAwMS0wMGZhMmMxMjIzOWYifQ.GpjvZ4MIc9NRYjtlopqtrAll26T49pimcOUmLrp1b_6OYLI9U5mYSbJTRmOFH38YFxzuRKsx10E1HQQyDE-W1Q','2016-02-23 12:14:17',NULL,NULL,'0'),(195,1,'demo0','087aef9d780733cfd00d11fdbdda8a5eabd4b721','iOS','7.1','116/10/2A Đường Hoàng Hoa Thám, Phường 12, Tân Bình, Hồ Chí Minh, Vietnam','2016-02-23 19:37:12',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjcuMSIsImV4cCI6MTQ1NjIyNzQzMiwiaWF0IjoxNDU2MjIzODMyLCJpZExvZyI6MTk1LCJsb2NhdGlvbiI6IjExNi8xMC8yQSDEkMaw4budbmcgSG_DoG5nIEhvYSBUaMOhbSwgUGjGsOG7nW5nIDEyLCBUw6JuIELDrG5oLCBI4buTIENow60gTWluaCwgVmlldG5hbSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxIiwidXNlck5hbWUiOiJkZW1vMCIsInV1aWQiOiIwODdhZWY5ZDc4MDczM2NmZDAwZDExZmRiZGRhOGE1ZWFiZDRiNzIxIn0.K15wTJuNzrEQe1Q3jc36RZB9qhmAGP3wolW1UkllhdoyC4rHGJdp_nrDrXLbPCDfmFjtfZJM-fkxt1RFVAR44Q','2016-02-23 19:37:12',NULL,NULL,'0'),(196,11,'admin','ffbee450964d5706fb49afe1e4f0425fc587ad7a','iOS','9.2','日本, 〒160-0022 東京都新宿区新宿６丁目２７−１７','2016-02-24 00:32:43','2016-02-24 00:33:02',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjkuMiIsImV4cCI6MTQ1NjI0NTE2MywiaWF0IjoxNDU2MjQxNTYzLCJpZExvZyI6MTk2LCJsb2NhdGlvbiI6IuaXpeacrCwg44CSMTYwLTAwMjIg5p2x5Lqs6YO95paw5a6_5Yy65paw5a6_77yW5LiB55uu77yS77yX4oiS77yR77yXIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjExIiwidXNlck5hbWUiOiJhZG1pbiIsInV1aWQiOiJmZmJlZTQ1MDk2NGQ1NzA2ZmI0OWFmZTFlNGYwNDI1ZmM1ODdhZDdhIn0.FJn3rcDRQU_WpHla7NDLIUxJRm8j3HtcGAHFNdccnPkdBqlQQ252lmi6P_OA3jo3X9Kh1LI3x9SZl5zWAs5RHQ','2016-02-24 00:32:43',NULL,NULL,'0'),(197,6,'demo5','42b4aff728595ef2341d510ba5886a972e731efd','iOS','9.2','日本, 〒315-0001 茨城県石岡市石岡２丁目８−９','2016-02-24 18:32:34',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjkuMiIsImV4cCI6MTQ1NjMwOTk1NCwiaWF0IjoxNDU2MzA2MzU0LCJpZExvZyI6MTk3LCJsb2NhdGlvbiI6IuaXpeacrCwg44CSMzE1LTAwMDEg6Iyo5Z-O55yM55-z5bKh5biC55-z5bKh77yS5LiB55uu77yY4oiS77yZIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjYiLCJ1c2VyTmFtZSI6ImRlbW81IiwidXVpZCI6IjQyYjRhZmY3Mjg1OTVlZjIzNDFkNTEwYmE1ODg2YTk3MmU3MzFlZmQifQ.DHt4BMqj1pv3Mhj4AKtGbnkDNM4ZjMtkO64LP4tL9fSoSAYfYukYkIMYj6lUtQGQ3Bx1_7YVStjk4CaUgTE-Cg','2016-02-24 18:32:34',NULL,NULL,'0'),(198,6,'demo5','42b4aff728595ef2341d510ba5886a972e731efd','iOS','9.2','日本, 〒350-1313 埼玉県狭山市上赤坂 県道6号線','2016-02-25 10:43:27',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjkuMiIsImV4cCI6MTQ1NjM2ODIwNywiaWF0IjoxNDU2MzY0NjA3LCJpZExvZyI6MTk4LCJsb2NhdGlvbiI6IuaXpeacrCwg44CSMzUwLTEzMTMg5Z-8546J55yM54ut5bGx5biC5LiK6LWk5Z2CIOecjOmBkzblj7fnt5oiLCJuYXRpdmVMYW5ndWFnZSI6IkVOIiwidXNlcklkIjoiNiIsInVzZXJOYW1lIjoiZGVtbzUiLCJ1dWlkIjoiNDJiNGFmZjcyODU5NWVmMjM0MWQ1MTBiYTU4ODZhOTcyZTczMWVmZCJ9.478_VIG0NAqrbxGa4ZBtlEwZqTsFz7_Xeuda6I2ibrdtoiXRADG6c4zlwvRfjvzP_krQUp2xgKg6mcFA1f1tug','2016-02-25 10:43:27',NULL,NULL,'0'),(199,1,'demo0','','PC','Chrome 48','','2016-02-26 18:20:07',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQ4IiwiZXhwIjoxNDU2NDgyMDA3LCJpYXQiOjE0NTY0Nzg0MDcsImlkTG9nIjoxOTksImxvY2F0aW9uIjoiIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6IiJ9.Lp-f_2GOD-sebSRzzdDN4O9_eWfUcoe0L5fkzLRMH__8lGQoeKEZqJ6woKBhi4kbEmMcpDbQfZgD54hHhWI7vA','2016-02-26 18:20:07',NULL,NULL,'0'),(200,1,'demo0','ffbee450964d5706fb49afe1e4f0425fc587ad7a','iOS','9.2','日本, 〒220-0073 神奈川県横浜市西区岡野１丁目６−２８','2016-02-26 18:41:03','2016-02-26 18:41:06',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjkuMiIsImV4cCI6MTQ1NjQ4MzI2MywiaWF0IjoxNDU2NDc5NjYzLCJpZExvZyI6MjAwLCJsb2NhdGlvbiI6IuaXpeacrCwg44CSMjIwLTAwNzMg56We5aWI5bed55yM5qiq5rWc5biC6KW_5Yy65bKh6YeO77yR5LiB55uu77yW4oiS77yS77yYIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6ImZmYmVlNDUwOTY0ZDU3MDZmYjQ5YWZlMWU0ZjA0MjVmYzU4N2FkN2EifQ._QmrG6PCQxt4LznP3xdaf7veHErSNDGSv6Zel7oYW7s4mMO4PTSjBNmJVQDkk5-NO6vYJLBuBZxe6bNcdaNtLQ','2016-02-26 18:41:03',NULL,NULL,'0'),(201,1,'demo0','ffbee450964d5706fb49afe1e4f0425fc587ad7a','iOS','9.2','日本, 〒220-0073 神奈川県横浜市西区岡野１丁目６−２８','2016-02-26 18:45:27','2016-02-26 18:45:31',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjkuMiIsImV4cCI6MTQ1NjQ4MzUyNywiaWF0IjoxNDU2NDc5OTI3LCJpZExvZyI6MjAxLCJsb2NhdGlvbiI6IuaXpeacrCwg44CSMjIwLTAwNzMg56We5aWI5bed55yM5qiq5rWc5biC6KW_5Yy65bKh6YeO77yR5LiB55uu77yW4oiS77yS77yYIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjEiLCJ1c2VyTmFtZSI6ImRlbW8wIiwidXVpZCI6ImZmYmVlNDUwOTY0ZDU3MDZmYjQ5YWZlMWU0ZjA0MjVmYzU4N2FkN2EifQ.KBg2yd8voV9NHmzjSg_yVr-aEqrIwfwCMXnxL75-ahL2mtgKho1Vmkp_H4pTtwU2OtPben9dffArme6evVkdKw','2016-02-26 18:45:27',NULL,NULL,'0'),(202,11,'admin','','PC','Firefox 44','','2016-02-29 16:01:31',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiRmlyZWZveCA0NCIsImV4cCI6MTQ1NjczMjg5MSwiaWF0IjoxNDU2NzI5MjkxLCJpZExvZyI6MjAyLCJsb2NhdGlvbiI6IiIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0.EUSp5BWyldSk9LU9Qo2x9FboXWV91akkjUoay2kmq8m6ciDYvh5AwVmdR6M9TKFiQkidLRZT1urX4n5vkVqfKg','2016-02-29 16:01:31',NULL,NULL,'0'),(203,11,'admin','','PC','Firefox 44','','2016-02-29 16:01:53',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiRmlyZWZveCA0NCIsImV4cCI6MTQ1NjczMjkxMywiaWF0IjoxNDU2NzI5MzEzLCJpZExvZyI6MjAzLCJsb2NhdGlvbiI6IiIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0.Oa1foB2XdATPIZiCk4RLvesloW_voyJ4HKdmBVdImBFEZI5hC_7mCtMsRlTzpw19W-gxW2RYDJsI8_wehma2Ig','2016-02-29 16:01:53',NULL,NULL,'0'),(204,11,'admin','ffbee450964d5706fb49afe1e4f0425fc587ad7a','iOS','9.2','日本, 〒220-0073 神奈川県横浜市西区岡野２丁目１３−３ ヴェルディ岡野公園','2016-02-29 16:06:04','2016-02-29 16:08:59',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjkuMiIsImV4cCI6MTQ1NjczMzE2NCwiaWF0IjoxNDU2NzI5NTY0LCJpZExvZyI6MjA0LCJsb2NhdGlvbiI6IuaXpeacrCwg44CSMjIwLTAwNzMg56We5aWI5bed55yM5qiq5rWc5biC6KW_5Yy65bKh6YeO77yS5LiB55uu77yR77yT4oiS77yTIOODtOOCp-ODq-ODh-OCo-WyoemHjuWFrOWckiIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiZmZiZWU0NTA5NjRkNTcwNmZiNDlhZmUxZTRmMDQyNWZjNTg3YWQ3YSJ9.PSNTCNZyHiMkPuG-iZAv9stlNgJZetQt9Br75Mo8Qlu2tv7CdIAyZdFm-CQFJ9T4vNl1lWQ2SU-TEtJKUsW_VQ','2016-02-29 16:06:04',NULL,NULL,'0'),(205,11,'admin','27d337af-8105-44f9-a881-4542ec5d0fd7','Android','Android 5.1.1','Tôn Thất Thuyết, Mỹ Đình 2, Cầu Giấy, Hà Nội, Vietnam','2016-02-29 16:17:50',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiQW5kcm9pZCIsImRldmljZVZlcnNpb24iOiJBbmRyb2lkIDUuMS4xIiwiZXhwIjoxNDU2NzMzODcwLCJpYXQiOjE0NTY3MzAyNzAsImlkTG9nIjoyMDUsImxvY2F0aW9uIjoiVMO0biBUaOG6pXQgVGh1eeG6v3QsIE3hu7kgxJDDrG5oIDIsIEPhuqd1IEdp4bqleSwgSMOgIE7hu5lpLCBWaWV0bmFtIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjExIiwidXNlck5hbWUiOiJhZG1pbiIsInV1aWQiOiIyN2QzMzdhZi04MTA1LTQ0ZjktYTg4MS00NTQyZWM1ZDBmZDcifQ.8iNSvWhYVn843PCENo-CbfqiA8aqSVnfZpEjX6Y_k7_03s4qE_CbdM-QmEkQo2AeWbqeO4ANQ6r0uMdiAUKaOg','2016-02-29 16:17:50',NULL,NULL,'0'),(206,11,'admin','27d337af-8105-44f9-a881-4542ec5d0fd7','Android','Android 5.1.1','Indonesia','2016-02-29 17:04:30',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiQW5kcm9pZCIsImRldmljZVZlcnNpb24iOiJBbmRyb2lkIDUuMS4xIiwiZXhwIjoxNDU2NzM2NjcwLCJpYXQiOjE0NTY3MzMwNzAsImlkTG9nIjoyMDYsImxvY2F0aW9uIjoiSW5kb25lc2lhIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjExIiwidXNlck5hbWUiOiJhZG1pbiIsInV1aWQiOiIyN2QzMzdhZi04MTA1LTQ0ZjktYTg4MS00NTQyZWM1ZDBmZDcifQ.L0ZJ6XgA3I_zgP2ZchH2WCUTG0T2tOMQye0fooJ-EhdXhpCoSyT96V_BFiBhl4AkQjrYdICGxY6LZH8TUIdBKQ','2016-02-29 17:04:30',NULL,NULL,'0'),(207,11,'admin','','PC','Safari 601','','2016-02-29 17:12:07',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiU2FmYXJpIDYwMSIsImV4cCI6MTQ1NjczNzEyNywiaWF0IjoxNDU2NzMzNTI3LCJpZExvZyI6MjA3LCJsb2NhdGlvbiI6IiIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0.MOGwzPBGjvkHEdOQmSvdgTWWX4oj6DM9WuQ2JQSq8kflM9lirLL5XmntXDyRPRLi6ZlBZYayoRaTSJAXNLS0xw','2016-02-29 17:12:07',NULL,NULL,'0'),(208,11,'admin','ffbee450964d5706fb49afe1e4f0425fc587ad7a','iOS','9.2','日本, 〒220-0073 神奈川県横浜市西区岡野２丁目１３−３ ヴェルディ岡野公園','2016-02-29 17:21:44','2016-02-29 17:21:49',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjkuMiIsImV4cCI6MTQ1NjczNzcwNCwiaWF0IjoxNDU2NzM0MTA0LCJpZExvZyI6MjA4LCJsb2NhdGlvbiI6IuaXpeacrCwg44CSMjIwLTAwNzMg56We5aWI5bed55yM5qiq5rWc5biC6KW_5Yy65bKh6YeO77yS5LiB55uu77yR77yT4oiS77yTIOODtOOCp-ODq-ODh-OCo-WyoemHjuWFrOWckiIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiZmZiZWU0NTA5NjRkNTcwNmZiNDlhZmUxZTRmMDQyNWZjNTg3YWQ3YSJ9.OOrCXozCmQvGFEr_2Lq__18SpU0raGuchm_MuqbsG2roTM8jJyTg6yzdUgkl6jCwPmqbPRIeob98dTHCAVb0pQ','2016-02-29 17:21:44',NULL,NULL,'0'),(209,1,'demo0','ffbee450964d5706fb49afe1e4f0425fc587ad7a','iOS','9.2','日本, 〒220-0073 神奈川県横浜市西区岡野２丁目１３−３ ヴェルディ岡野公園','2016-02-29 17:22:05',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjkuMiIsImV4cCI6MTQ1NjczNzcyNSwiaWF0IjoxNDU2NzM0MTI1LCJpZExvZyI6MjA5LCJsb2NhdGlvbiI6IuaXpeacrCwg44CSMjIwLTAwNzMg56We5aWI5bed55yM5qiq5rWc5biC6KW_5Yy65bKh6YeO77yS5LiB55uu77yR77yT4oiS77yTIOODtOOCp-ODq-ODh-OCo-WyoemHjuWFrOWckiIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxIiwidXNlck5hbWUiOiJkZW1vMCIsInV1aWQiOiJmZmJlZTQ1MDk2NGQ1NzA2ZmI0OWFmZTFlNGYwNDI1ZmM1ODdhZDdhIn0.kSO9_U7RUov7N7SYaMsa5qKxUj_QasVKXc7KQ-MhzwSO38LH0iknzWQbV5aXDnhfAAt838IBCV1OvDpBdGsk5w','2016-02-29 17:22:05',NULL,NULL,'0'),(210,11,'admin','8150f1f136a0560df3ffae92d1a400ec0bd97f44','iOS','9.2','Tan Binh , Ho Chi Minh City','2016-02-29 17:24:20',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjkuMiIsImV4cCI6MTQ1NjczNzg2MCwiaWF0IjoxNDU2NzM0MjYwLCJpZExvZyI6MjEwLCJsb2NhdGlvbiI6IlRhbiBCaW5oICwgSG8gQ2hpIE1pbmggQ2l0eSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiODE1MGYxZjEzNmEwNTYwZGYzZmZhZTkyZDFhNDAwZWMwYmQ5N2Y0NCJ9.YFsE8K3PSKlKMPVYkXFTAm0E1hVRyJ8cy5AtSgVEny-Hh7RIzvPU8bK3r0oerJN602P6Zu515ATDSKlDx8Dkyg','2016-02-29 17:24:20',NULL,NULL,'0'),(211,11,'admin','65a92d435184c2d493ada4095dcee5755eb6ce80','iOS','9.2','Tan Binh , Ho Chi Minh City','2016-02-29 17:26:04',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjkuMiIsImV4cCI6MTQ1NjczNzk2NCwiaWF0IjoxNDU2NzM0MzY0LCJpZExvZyI6MjExLCJsb2NhdGlvbiI6IlRhbiBCaW5oICwgSG8gQ2hpIE1pbmggQ2l0eSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiNjVhOTJkNDM1MTg0YzJkNDkzYWRhNDA5NWRjZWU1NzU1ZWI2Y2U4MCJ9.bnIs2gZMDVeCjvhcx0lhbn1bik-1vUIoWZZsr_iTDK13030nu02oWHmvn2WFVftUwggPFaLXG3iGigZNHWDXuQ','2016-02-29 17:26:04',NULL,NULL,'0'),(212,2,'demo1','96bbf7e3-c5e1-4576-b62a-dc69dc57a614','Android','Android 4.1.1','1 Chome-3-1 Motoakasaka, Minato-ku, Tōkyō-to 107-0051, Japan','2016-02-29 18:32:07',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiQW5kcm9pZCIsImRldmljZVZlcnNpb24iOiJBbmRyb2lkIDQuMS4xIiwiZXhwIjoxNDU2NzQxOTI3LCJpYXQiOjE0NTY3MzgzMjcsImlkTG9nIjoyMTIsImxvY2F0aW9uIjoiMSBDaG9tZS0zLTEgTW90b2FrYXNha2EsIE1pbmF0by1rdSwgVMWNa3nFjS10byAxMDctMDA1MSwgSmFwYW4iLCJuYXRpdmVMYW5ndWFnZSI6IkVOIiwidXNlcklkIjoiMiIsInVzZXJOYW1lIjoiZGVtbzEiLCJ1dWlkIjoiOTZiYmY3ZTMtYzVlMS00NTc2LWI2MmEtZGM2OWRjNTdhNjE0In0.JaijA3JlXT3thqNwnYdaL5F0Fs6q-poKRvkji6vjXGEDMFOFx90XQQ1L0FUg0OhMT9z6MjHPIJ8JVCp7Q3k1Xg','2016-02-29 18:32:07',NULL,NULL,'0'),(213,11,'admin','','PC','Firefox 44','','2016-03-01 16:59:09',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiRmlyZWZveCA0NCIsImV4cCI6MTQ1NjgyMjc0OSwiaWF0IjoxNDU2ODE5MTQ5LCJpZExvZyI6MjEzLCJsb2NhdGlvbiI6IiIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiIn0.3d5qujfK5QyxhFvF5KXf47L6qM2W6myeCCJX5IcMvtOs-uQOOdLzZMqtT2vRsCTqo6rKpYZBEQQ3I1YUx9rz7A','2016-03-01 16:59:09',NULL,NULL,'0'),(214,11,'admin','27d337af-8105-44f9-a881-4542ec5d0fd7','Android','Android 5.1.1','Indonesia','2016-03-01 16:59:53',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiQW5kcm9pZCIsImRldmljZVZlcnNpb24iOiJBbmRyb2lkIDUuMS4xIiwiZXhwIjoxNDU2ODIyNzkzLCJpYXQiOjE0NTY4MTkxOTMsImlkTG9nIjoyMTQsImxvY2F0aW9uIjoiSW5kb25lc2lhIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjExIiwidXNlck5hbWUiOiJhZG1pbiIsInV1aWQiOiIyN2QzMzdhZi04MTA1LTQ0ZjktYTg4MS00NTQyZWM1ZDBmZDcifQ.j47xT9bQhu6p2aX_krsdONfGt2UD-bEq8COxmRXec1D0V2UW2SJkUv00yqo6DAgeUhQxaYcAAA5XNKCMyQ87KA','2016-03-01 16:59:53',NULL,NULL,'0'),(215,11,'admin','','PC','Chrome 42','','2016-03-01 17:01:16',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiUEMiLCJkZXZpY2VWZXJzaW9uIjoiQ2hyb21lIDQyIiwiZXhwIjoxNDU2ODIyODc2LCJpYXQiOjE0NTY4MTkyNzYsImlkTG9nIjoyMTUsImxvY2F0aW9uIjoiIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjExIiwidXNlck5hbWUiOiJhZG1pbiIsInV1aWQiOiIifQ._NyViLuAE18DczdqTXWLZ1cG6xbaezBwLp0ce5L-Umyumrhk-u-W-R4vXLNwt7l3-MqofpwTb02egcq4F8oMLw','2016-03-01 17:01:16',NULL,NULL,'0'),(216,1,'demo0','1ab1a2f0100e90399b233731e83878a4a4813bd3','iOS','9.2','日本, 〒220-0073 神奈川県横浜市西区岡野２丁目１３−３ ヴェルディ岡野公園','2016-03-01 18:01:49',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjkuMiIsImV4cCI6MTQ1NjgyNjUwOSwiaWF0IjoxNDU2ODIyOTA5LCJpZExvZyI6MjE2LCJsb2NhdGlvbiI6IuaXpeacrCwg44CSMjIwLTAwNzMg56We5aWI5bed55yM5qiq5rWc5biC6KW_5Yy65bKh6YeO77yS5LiB55uu77yR77yT4oiS77yTIOODtOOCp-ODq-ODh-OCo-WyoemHjuWFrOWckiIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxIiwidXNlck5hbWUiOiJkZW1vMCIsInV1aWQiOiIxYWIxYTJmMDEwMGU5MDM5OWIyMzM3MzFlODM4NzhhNGE0ODEzYmQzIn0.yuAxstlnjnMk-I_wjHYI8m5Q4TiKD5ilf_FFFg3_fzJAnkEfT_oH62QwPcS47OqKFLkKRyX-AvNYSIFpo5olpw','2016-03-01 18:01:49',NULL,NULL,'0'),(217,1,'demo0','ffbee450964d5706fb49afe1e4f0425fc587ad7a','iOS','9.2','Tan Binh , Ho Chi Minh City','2016-03-03 11:45:31',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjkuMiIsImV4cCI6MTQ1Njk3NjczMSwiaWF0IjoxNDU2OTczMTMxLCJpZExvZyI6MjE3LCJsb2NhdGlvbiI6IlRhbiBCaW5oICwgSG8gQ2hpIE1pbmggQ2l0eSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxIiwidXNlck5hbWUiOiJkZW1vMCIsInV1aWQiOiJmZmJlZTQ1MDk2NGQ1NzA2ZmI0OWFmZTFlNGYwNDI1ZmM1ODdhZDdhIn0.-ObfCL3PP8CpxhnGV8ZmsxxgDnyg7roBbmyB_WgyOltNkGqmiM5URPD-3AkbnTLHryoMGfWC6j14qFw3dbyfmg','2016-03-03 11:45:31',NULL,NULL,'0'),(218,1,'demo0','ffbee450964d5706fb49afe1e4f0425fc587ad7a','iOS','9.2','Tan Binh , Ho Chi Minh City','2016-03-03 12:10:23',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjkuMiIsImV4cCI6MTQ1Njk3ODIyMywiaWF0IjoxNDU2OTc0NjIzLCJpZExvZyI6MjE4LCJsb2NhdGlvbiI6IlRhbiBCaW5oICwgSG8gQ2hpIE1pbmggQ2l0eSIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxIiwidXNlck5hbWUiOiJkZW1vMCIsInV1aWQiOiJmZmJlZTQ1MDk2NGQ1NzA2ZmI0OWFmZTFlNGYwNDI1ZmM1ODdhZDdhIn0.lhp0h-zxW6ruYB8msR_2DeU6C6sb_QMbJBYCJHrmvTBL1e6eS5_C_PPdeZcIOhhNINrPOIuA5DhHj_c7ql9P4w','2016-03-03 12:10:23',NULL,NULL,'0'),(219,1,'demo0','ffbee450964d5706fb49afe1e4f0425fc587ad7a','iOS','9.2','Ngõ 12 Núi Trúc, Giảng Võ, Ba Đình, Hà Nội, ベトナム','2016-03-03 17:39:06',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjkuMiIsImV4cCI6MTQ1Njk5Nzk0NiwiaWF0IjoxNDU2OTk0MzQ2LCJpZExvZyI6MjE5LCJsb2NhdGlvbiI6Ik5nw7UgMTIgTsO6aSBUcsO6YywgR2nhuqNuZyBWw7UsIEJhIMSQw6xuaCwgSMOgIE7hu5lpLCDjg5njg4jjg4rjg6AiLCJuYXRpdmVMYW5ndWFnZSI6IkVOIiwidXNlcklkIjoiMSIsInVzZXJOYW1lIjoiZGVtbzAiLCJ1dWlkIjoiZmZiZWU0NTA5NjRkNTcwNmZiNDlhZmUxZTRmMDQyNWZjNTg3YWQ3YSJ9.8lYuU6CAWsj104IHOV6OuhRVO4BoQYb91OWC9ktIoEjP-dSQnyilUnGKsYvT5Dy0ulVxPKnxGjasuhIP4DZX_g','2016-03-03 17:39:06',NULL,NULL,'0'),(220,1,'demo0','1ab1a2f0100e90399b233731e83878a4a4813bd3','iOS','9.2','日本, 〒220-0073 神奈川県横浜市西区岡野２丁目１３−３ ヴェルディ岡野公園','2016-03-04 04:46:17','2016-03-04 04:50:19',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjkuMiIsImV4cCI6MTQ1NzAzNzk3NywiaWF0IjoxNDU3MDM0Mzc3LCJpZExvZyI6MjIwLCJsb2NhdGlvbiI6IuaXpeacrCwg44CSMjIwLTAwNzMg56We5aWI5bed55yM5qiq5rWc5biC6KW_5Yy65bKh6YeO77yS5LiB55uu77yR77yT4oiS77yTIOODtOOCp-ODq-ODh-OCo-WyoemHjuWFrOWckiIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxIiwidXNlck5hbWUiOiJkZW1vMCIsInV1aWQiOiIxYWIxYTJmMDEwMGU5MDM5OWIyMzM3MzFlODM4NzhhNGE0ODEzYmQzIn0.F0FHIeDT1Qjhz4BDNWrX8O62pGkkDLbWggYJbsXx5VmaHkiAykJ0fGtQilu9tBzlzf9qEXTZERcv6mlpnrKbGw','2016-03-04 04:46:17',NULL,NULL,'0'),(221,11,'admin','1ab1a2f0100e90399b233731e83878a4a4813bd3','iOS','9.2','日本, 〒220-0073 神奈川県横浜市西区岡野２丁目１３−３ ヴェルディ岡野公園','2016-03-04 04:50:41',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjkuMiIsImV4cCI6MTQ1NzAzODI0MSwiaWF0IjoxNDU3MDM0NjQxLCJpZExvZyI6MjIxLCJsb2NhdGlvbiI6IuaXpeacrCwg44CSMjIwLTAwNzMg56We5aWI5bed55yM5qiq5rWc5biC6KW_5Yy65bKh6YeO77yS5LiB55uu77yR77yT4oiS77yTIOODtOOCp-ODq-ODh-OCo-WyoemHjuWFrOWckiIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiMWFiMWEyZjAxMDBlOTAzOTliMjMzNzMxZTgzODc4YTRhNDgxM2JkMyJ9.60pMSw7rbqV6JH7D9Vuk0-Hx08Qnotrb8TUE9qBQBOPpHTxILvsT7ZSSlznRra8xP-K5WjtP6x_rNicYa5nv5w','2016-03-04 04:50:41',NULL,NULL,'0'),(222,11,'admin','1ab1a2f0100e90399b233731e83878a4a4813bd3','iOS','9.2','日本, 〒220-0073 神奈川県横浜市西区岡野２丁目１３−３ ヴェルディ岡野公園','2016-03-04 05:41:26','2016-03-04 05:41:45',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjkuMiIsImV4cCI6MTQ1NzA0MTI4NiwiaWF0IjoxNDU3MDM3Njg2LCJpZExvZyI6MjIyLCJsb2NhdGlvbiI6IuaXpeacrCwg44CSMjIwLTAwNzMg56We5aWI5bed55yM5qiq5rWc5biC6KW_5Yy65bKh6YeO77yS5LiB55uu77yR77yT4oiS77yTIOODtOOCp-ODq-ODh-OCo-WyoemHjuWFrOWckiIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiMWFiMWEyZjAxMDBlOTAzOTliMjMzNzMxZTgzODc4YTRhNDgxM2JkMyJ9.tGPml-8Qs3Q-PAlssf7Krszj1Xd51SyuldJxlG5XNQ-_K22GU7aqclfHL_md_Z_MaH0ca5sRRsehuJcU47W9Eg','2016-03-04 05:41:26',NULL,NULL,'0'),(223,11,'admin','1ab1a2f0100e90399b233731e83878a4a4813bd3','iOS','9.2','日本, 〒220-0073 神奈川県横浜市西区岡野２丁目１３−３ ヴェルディ岡野公園','2016-03-04 05:47:18','2016-03-04 06:36:36',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjkuMiIsImV4cCI6MTQ1NzA0MTYzOCwiaWF0IjoxNDU3MDM4MDM4LCJpZExvZyI6MjIzLCJsb2NhdGlvbiI6IuaXpeacrCwg44CSMjIwLTAwNzMg56We5aWI5bed55yM5qiq5rWc5biC6KW_5Yy65bKh6YeO77yS5LiB55uu77yR77yT4oiS77yTIOODtOOCp-ODq-ODh-OCo-WyoemHjuWFrOWckiIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiMWFiMWEyZjAxMDBlOTAzOTliMjMzNzMxZTgzODc4YTRhNDgxM2JkMyJ9.oOu20QM-RikTRADld14uX7BTVQ6JDP4bhMlYvf3fFUOXIR_ILDpUfrkDQoVwV3b3oi2nvrjHi-H-jTwX8-qu7w','2016-03-04 05:47:18',NULL,NULL,'0'),(224,11,'admin','1ab1a2f0100e90399b233731e83878a4a4813bd3','iOS','9.2','日本, 〒220-0073 神奈川県横浜市西区岡野２丁目１３−３ ヴェルディ岡野公園','2016-03-04 06:36:37',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiaU9TIiwiZGV2aWNlVmVyc2lvbiI6IjkuMiIsImV4cCI6MTQ1NzA0NDU5NywiaWF0IjoxNDU3MDQwOTk3LCJpZExvZyI6MjI0LCJsb2NhdGlvbiI6IuaXpeacrCwg44CSMjIwLTAwNzMg56We5aWI5bed55yM5qiq5rWc5biC6KW_5Yy65bKh6YeO77yS5LiB55uu77yR77yT4oiS77yTIOODtOOCp-ODq-ODh-OCo-WyoemHjuWFrOWckiIsIm5hdGl2ZUxhbmd1YWdlIjoiRU4iLCJ1c2VySWQiOiIxMSIsInVzZXJOYW1lIjoiYWRtaW4iLCJ1dWlkIjoiMWFiMWEyZjAxMDBlOTAzOTliMjMzNzMxZTgzODc4YTRhNDgxM2JkMyJ9.6GGT8afceqRHGg_gMxrI929jPsdPx7X6DY_fMyWRgA2mzCMKGLbBwXrXvj4QAhMY0J-rVjLxF_8ldIgVZu9Jtw','2016-03-04 06:36:37',NULL,NULL,'0'),(225,11,'admin','1ab1a2f0100e90399b233731e83878a4a4813bd3','iOS','9.2','日本, 〒220-0073 神奈川県横浜市西区岡野２丁目１３−３ ヴェルディ岡野公園','2016-03-04 12:42:54',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','','2016-03-04 12:42:54',NULL,NULL,'0'),(226,11,'admin','1ab1a2f0100e90399b233731e83878a4a4813bd3','iOS','9.2','日本, 〒220-0073 神奈川県横浜市西区岡野２丁目１３−３ ヴェルディ岡野公園','2016-03-04 12:47:50',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','','2016-03-04 12:47:50',NULL,NULL,'0'),(227,11,'admin','27d337af-8105-44f9-a881-4542ec5d0fd7','Android','Android 5.1.1','Indonesia','2016-03-08 16:53:55',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiQW5kcm9pZCIsImRldmljZVZlcnNpb24iOiJBbmRyb2lkIDUuMS4xIiwiZXhwIjoxNDU3NDI3MjM1LCJpYXQiOjE0NTc0MjM2MzUsImlkTG9nIjoyMjcsImxvY2F0aW9uIjoiSW5kb25lc2lhIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjExIiwidXNlck5hbWUiOiJhZG1pbiIsInV1aWQiOiIyN2QzMzdhZi04MTA1LTQ0ZjktYTg4MS00NTQyZWM1ZDBmZDcifQ.LCsCRghvWBVYacYAjzLHrDx6AJITyw5kNr8fbNhzGMGRXAiCVvxl_yWhQnf9359ci18eetx8I2jF-_ecDTGP5A','2016-03-08 16:53:55',NULL,NULL,'0'),(228,11,'admin','27d337af-8105-44f9-a881-4542ec5d0fd7','Android','Android 5.1.1','Indonesia','2016-03-08 16:54:48',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJkZXZpY2VOYW1lIjoiQW5kcm9pZCIsImRldmljZVZlcnNpb24iOiJBbmRyb2lkIDUuMS4xIiwiZXhwIjoxNDU3NDI3Mjg4LCJpYXQiOjE0NTc0MjM2ODgsImlkTG9nIjoyMjgsImxvY2F0aW9uIjoiSW5kb25lc2lhIiwibmF0aXZlTGFuZ3VhZ2UiOiJFTiIsInVzZXJJZCI6IjExIiwidXNlck5hbWUiOiJhZG1pbiIsInV1aWQiOiIyN2QzMzdhZi04MTA1LTQ0ZjktYTg4MS00NTQyZWM1ZDBmZDcifQ.uBf0F9JApWlTESgBbvv7gLb89rSj3q3kNWjxL3bx7pNe7KCn8ArFaGSdHAKNryCvEaKiNOtQ5hrdKfbsDO1sYg','2016-03-08 16:54:48',NULL,NULL,'0');
/*!40000 ALTER TABLE `user_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vocabulary`
--

DROP TABLE IF EXISTS `vocabulary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vocabulary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lesson_info_id` int(11) NOT NULL,
  `vocabulary_order` int(11) NOT NULL,
  `vocabulary` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `vocabulary_ml_id` int(11) DEFAULT NULL,
  `user_input_flg` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `vocabulary_english_name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `vocabulary_kana_name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `vocabulary_categories` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ruby_word` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `learning_type` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `delete_at` datetime DEFAULT NULL,
  `delete_flg` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vocabulary`
--

LOCK TABLES `vocabulary` WRITE;
/*!40000 ALTER TABLE `vocabulary` DISABLE KEYS */;
INSERT INTO `vocabulary` VALUES (1,1,1,'ジョン',6,'1','John','ジョン','personname_male,outgroup,person,name,person-equal','','1','2016-01-12 15:23:02','2016-01-15 10:23:19',NULL,0),(2,1,2,'イギリス人',7,'1','British','イギリスじん','nationality,british','人-じん;','1','2016-01-12 15:23:23','2016-01-15 10:23:19',NULL,0),(3,1,3,'私',8,'1','I','わたし','I','私-わたし;','1','2016-01-12 15:23:44','2016-01-15 10:23:19',NULL,0),(4,1,4,'兄',9,'1','older brother(your own)','あに','person,family,ingroup,person-equal,myfamily','兄-あに;','1','2016-01-12 15:24:05','2016-01-15 10:23:19',NULL,0),(5,1,5,'映画館',10,'1','movie theater','えいがかん','location,intown,act_loc,getin_loc','映画館-えいがかん;','1','2016-01-12 15:24:29','2016-01-15 10:23:19',NULL,0),(6,1,6,'行きました',11,'1','go','いきました','to_go_to','行-い;','1','2016-01-12 15:24:49','2016-01-15 10:23:19',NULL,0),(7,1,7,'自転車',12,'1','bicycle','じてんしゃ','transport,transport_S,parkable','自転車-じてんしゃ;','1','2016-01-12 15:25:12','2016-01-15 10:23:19',NULL,0),(8,2,1,'兄',9,'1','older brother(your own)','あに','person,family,ingroup,person-equal,myfamily','兄-あに;','1','2016-01-12 15:26:52','2016-01-15 10:23:19',NULL,0),(9,2,2,'医者',17,'1','doctor','いしゃ','job,doctor','医者-いしゃ;','1','2016-01-12 15:26:52','2016-01-15 10:23:19',NULL,0),(10,2,3,'自転車',12,'1','bicycle','じてんしゃ','transport,transport_S,parkable','自転車-じてんしゃ;','1','2016-01-12 15:27:14','2016-01-15 10:23:19',NULL,0),(11,2,4,'止めて',18,'1','stop (transitive)','とめて','type1','止-と;','1','2016-01-12 15:27:14','2016-01-15 10:23:19',NULL,0),(12,2,5,'神社',19,'1','Shinto shrine','じんじゃ','location,intown','神社-じんじゃ;','1','2016-01-12 15:27:34','2016-01-15 10:23:19',NULL,0),(13,2,6,'公園',20,'1','park','こうえん','location,intown','公園-こうえん;','1','2016-01-12 15:27:57','2016-01-15 10:23:19',NULL,0),(14,2,7,'いった',21,'1','go','いった','to_go_to','','1','2016-01-12 15:28:18','2016-01-15 10:23:19',NULL,0),(15,3,1,'明日',24,'1','tomorrow','あした','time_rel','明日-あした;','1','2016-01-12 15:29:49','2016-01-15 10:23:19',NULL,0),(16,3,2,'行きます',25,'1','go','いきます','to_go_to','行-い;','1','2016-01-12 15:30:09','2016-01-15 10:23:19',NULL,0),(17,3,3,'おっしゃりました',26,'1','to say (polite)','おっしゃりました','','','1','2016-01-12 15:30:30','2016-01-15 10:23:19',NULL,0),(18,4,1,'秋',NULL,'1','autumn','あき','time_nonrel,fourseason,autumn','秋-あき;','1','2016-01-27 16:50:32',NULL,NULL,0),(19,4,2,'朝',NULL,'1','morning','あさ','time_nonrel','朝-あさ;','1','2016-01-27 16:50:32',NULL,NULL,0),(20,4,3,'朝御飯',NULL,'1','breakfast','あさごはん','edible,cook_obj','朝御飯-あさごはん;','1','2016-01-27 16:50:32',NULL,NULL,0),(21,4,4,'足',NULL,'1','leg','あし','','足-あし;','1','2016-01-27 16:50:32',NULL,NULL,0),(22,4,5,'頭',NULL,'1','head','あたま','','頭-あたま;','1','2016-01-27 16:50:32',NULL,NULL,0),(23,4,6,'お兄さん',NULL,'1','older brother','おにいさん','person,family,outgroup,person-equal,yourfamily','兄-にいさ;','1','2016-01-27 16:50:32',NULL,NULL,0),(24,4,7,'内',NULL,'1','home','うち','','内-うち;','1','2016-01-27 16:50:32',NULL,NULL,0),(25,4,8,'池',NULL,'1','pond','いけ','location','池-いけ;','1','2016-01-27 16:50:32',NULL,NULL,0),(26,4,9,'雨',NULL,'1','rain','あめ','','雨-あめ;','1','2016-01-27 16:50:32',NULL,NULL,0),(27,4,10,'妹',NULL,'1','younger sister(your own)','いもうと','person,family,ingroup,person-equal,younger,myfamily','妹-いもうと;','1','2016-01-27 16:50:32',NULL,NULL,0),(28,5,1,'魚',NULL,'1','Fish','さかな','learn_obj','魚-さかな;','1','2016-01-27 16:50:32',NULL,NULL,0),(29,5,2,'水筒',NULL,'1','Water bottle ','すいとう ','small_object','水筒-すいとう ;','1','2016-01-27 16:50:32',NULL,NULL,0),(30,5,3,'果敢ない',NULL,'1','果敢ない','果敢ない','definitiveA','','1','2016-01-27 16:50:32',NULL,NULL,0),(31,5,4,'無駄な',NULL,'1','in vain','むだな','','無駄-むだ;','1','2016-01-27 16:50:32',NULL,NULL,0),(32,5,5,'レストラン',NULL,'1','resteruant','レストラン','','','1','2016-01-27 16:50:32',NULL,NULL,0),(33,5,6,'五時',NULL,'1','five o\'clock','ごじ','time_real','五時-ごじ;','1','2016-01-27 16:50:32',NULL,NULL,0),(34,5,7,'入る',NULL,'1','enter','はいる','type5,type1','入-はい;','1','2016-01-27 16:50:32',NULL,NULL,0),(35,6,1,'自動車',NULL,'1','car','じどうしゃ','','自動車-じどうしゃ;','1','2016-01-27 16:50:32',NULL,NULL,0),(36,6,2,'練習',NULL,'1','practice','れんしゅう','','練習-れんしゅう;','1','2016-01-27 16:50:32',NULL,NULL,0),(37,6,3,'指',NULL,'1','finger','ゆび','','指-ゆび;','1','2016-01-27 16:50:32',NULL,NULL,0),(38,6,4,'引き出し',NULL,'1','drawer','ひきだし','','引-ひ;出-だし;','1','2016-01-27 16:50:32',NULL,NULL,0),(39,6,5,'おかげさまで',NULL,'1','Thanks to you','おかげさまで','','','1','2016-01-27 16:50:32',NULL,NULL,0),(40,6,6,'みそ',NULL,'1','miso','みそ','','','1','2016-01-27 16:50:32',NULL,NULL,0),(41,6,7,'以内',NULL,'1','within','いない','','以内-いない;','1','2016-01-27 16:50:32',NULL,NULL,0),(42,6,8,'時',NULL,'1','o’clock','じ','time_nonrel','時-じ;','1','2016-01-27 16:50:32',NULL,NULL,0),(43,6,9,'返事',NULL,'1','response','へんじ','','返事-へんじ;','1','2016-01-27 16:50:32',NULL,NULL,0),(44,6,10,'輸出',NULL,'1','export','ゆしゅつ','','輸出-ゆしゅつ;','1','2016-01-27 16:50:32',NULL,NULL,0),(45,6,11,'娘',NULL,'1','daughter','むすめ','','娘-むすめ;','1','2016-01-27 16:50:32',NULL,NULL,0),(46,6,12,'鶏肉',NULL,'1','chicken','とりにく','','鶏肉-とりにく;','1','2016-01-27 16:50:32',NULL,NULL,0),(47,6,13,'私',8,'1','I','わたし','I','私-わたし;','1','2016-01-27 16:50:32',NULL,NULL,0),(48,6,14,'暑い',NULL,'1','hot(tempature)','あつい','adjective_temp','暑-あつ;','1','2016-01-27 16:50:32',NULL,NULL,0),(49,6,15,'品物',NULL,'1','article/goods','しなもの','','品物-しなもの;','1','2016-01-27 16:50:32',NULL,NULL,0),(50,6,16,'並べる',NULL,'1','display','ならべる','','並-なら;','1','2016-01-27 16:50:32',NULL,NULL,0),(51,6,17,'です',NULL,'1','is','です','','','1','2016-01-27 16:50:32',NULL,NULL,0),(52,6,18,'ちゃん',NULL,'1','Miss','ちゃん','postfix,title','','1','2016-01-27 16:50:32',NULL,NULL,0),(53,6,19,'本棚',NULL,'1','bookshelf','ほんだな','','本棚-ほんだな;','1','2016-01-27 16:50:32',NULL,NULL,0),(54,6,20,'入る',NULL,'1','enter','はいる','type5,type1','入-はい;','1','2016-01-27 16:50:32',NULL,NULL,0),(55,7,1,'これ',NULL,'1','this','これ','definitiveA','','1','2016-02-22 12:30:01',NULL,NULL,0),(56,7,2,'開始',NULL,'0','example','スタート','small_object','鉛筆-えんぴつ;','1','2016-02-22 12:30:01',NULL,NULL,0),(57,7,3,'鉛筆',NULL,'1','pencil','えんぴつ','small_object,shopping,counter_hon,ontable,onbookshelf,belowtable,writetool','鉛筆-えんぴつ;','1','2016-02-22 12:30:01',NULL,NULL,0),(58,9,1,'これ',NULL,'1','this','これ','definitiveA','','1','2016-02-22 16:04:43',NULL,'2016-02-22 16:15:09',1),(59,9,2,'開始',NULL,'0','example','スタート','small_object','鉛筆-えんぴつ;','1','2016-02-22 16:04:43',NULL,'2016-02-22 16:15:09',1),(60,9,3,'鉛筆',NULL,'1','pencil','えんぴつ','small_object,shopping,counter_hon,ontable,onbookshelf,belowtable,writetool','鉛筆-えんぴつ;','1','2016-02-22 16:04:43',NULL,'2016-02-22 16:15:09',1),(61,9,4,'ジョン',6,'1','John','ジョン','personname_male,outgroup,person,name,person-equal','','1','2016-02-22 16:04:43',NULL,'2016-02-22 16:15:09',1),(62,9,5,'開始1',NULL,'0','start1','スタート1','nationality','','1','2016-02-22 16:04:43',NULL,'2016-02-22 16:15:09',1),(63,9,6,'イギリス人',7,'1','British','イギリスじん','nationality,british','人-じん;','1','2016-02-22 16:04:43',NULL,'2016-02-22 16:15:09',1);
/*!40000 ALTER TABLE `vocabulary` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-03-08 17:17:05
