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
  `barcode` varchar(45),
  `inventoryId` bigint NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`inventoryId`) REFERENCES inventory (`id`)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` bigint NOT NULL,
  `barcode` varchar(45),
  `description` varchar(100) NOT NULL,
  `price` bigint NOT NULL,
  `measure` enum('KILOS', 'PIECES'),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `transaction`;
CREATE TABLE `transaction` (
  `id` bigint NOT NULL,
  `date` date NOT NULL,
  `import_export` enum('import', 'export'),
  `totalPrice` bigint NOT NULL,
  `description` varchar(100) NOT NULL,
  `receiver` varchar(45) NOT NULL,
  `deliver` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `stock`;
CREATE TABLE `stock` (
	`id` bigint NOT NULL,
    `shelf_id` bigint NOT NULL,
    `product_id` bigint NOT NULL,
    `up_to_date` DATE NOT NULL,
    `quantity` bigint NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`product_id`) REFERENCES product(`id`),
	FOREIGN KEY (`shelf_id`) REFERENCES shelf(`id`)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `stock_history`;
CREATE TABLE `stock` (
	`id` bigint NOT NULL,
    `stock_id` bigint NOT NULL,
    `quantity` bigint NOT NULL,
    `dates` DATE NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`stock_id`) REFERENCES stock(`id`)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS `transaction_details`;
CREATE TABLE `transaction_details` (
	`id` bigint NOT NULL,
    `product_id` bigint NOT NULL,
    `quantity` bigint NOT NULL,
    `shelf_id` bigint NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`product_id`) REFERENCES product(`id`),
	FOREIGN KEY (`shelf_id`) REFERENCES shelf(`id`)
) ENGINE=InnoDB;


#/*!40101 SET character_set_client = @saved_cs_client */;#