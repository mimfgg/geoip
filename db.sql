CREATE DATABASE `geoip` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `geoip`;

CREATE TABLE `blocks` (
  `startIpNum` bigint(20) NOT NULL,
  `endIpNum` bigint(20) NOT NULL,
  `locId` bigint(20) NOT NULL,
  KEY `start_idx` (`startIpNum`),
  KEY `end_idx` (`endIpNum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `locations` (
  `locId` bigint(20) NOT NULL,
  `country` varchar(45) NOT NULL,
  `region` varchar(45) NOT NULL,
  `city` varchar(255) NOT NULL,
  `postalCode` varchar(45) NOT NULL,
  `latitude` decimal(18,12) NOT NULL,
  `longitude` decimal(18,12) NOT NULL,
  `metroCode` varchar(45) DEFAULT NULL,
  `areaCode` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`locId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
