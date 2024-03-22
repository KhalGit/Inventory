CREATE DATABASE  IF NOT EXISTS `db_inventory` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `db_inventory`;


DROP TABLE IF EXISTS `inventory`;
CREATE TABLE `inventory` (
  `id` bigint NOT NULL,
  `description` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `shelf`;
CREATE TABLE `shelf` (
  `id` bigint NOT NULL,
  `inventoryId` int,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`inventoryId`) REFERENCES inventory(`id`)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `barcode` bigint NOT NULL,
  `description` varchar(100) NOT NULL,
  `price` bigint NOT NULL,
  PRIMARY KEY (`barcode`)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `transaction`;
CREATE TABLE `transaction` (
  `id` bigint NOT NULL,
  `date` bigint NOT NULL,
  `import_export` enum('import', 'export'),
  `totalPrice` bigint NOT NULL,
  `price` bigint NOT NULL,
  `description` varchar(100) NOT NULL,
  `receiver` varchar(45) NOT NULL,
  `deliver` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `stock`;
CREATE TABLE `stock` (
	`id` bigint NOT NULL,
    `shelfId` bigint NOT NULL,
    `productBarcode` bigint NOT NULL,
    `upToDate` DATE NOT NULL,
    `quantity` bigint NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`productBarcode`) REFERENCES product(`barcode`),
	FOREIGN KEY (`shelfId`) REFERENCES shelf(`id`)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `transaction_details`;
CREATE TABLE `transaction_details` (
	`id` bigint NOT NULL,
    `productBarcode` bigint NOT NULL,
    `quantity` bigint NOT NULL,
    `shelfId` bigint NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`productBarcode`) REFERENCES product(`barcode`),
	FOREIGN KEY (`shelfId`) REFERENCES shelf(`id`)
) ENGINE=InnoDB;



/*!40101 SET character_set_client = @saved_cs_client */;
