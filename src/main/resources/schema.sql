
CREATE DATABASE IF NOT EXISTS `rts`;
USE `rts`;

DROP TABLE IF EXISTS `shelfLabeler`;
CREATE TABLE `shelfLabeler` (
 `shelfLabelerId` varchar(64) NOT NULL,
 `userId` varchar(64) DEFAULT NULL,
 `name` varchar(128) NOT NULL,
  `fileName` varchar(128) DEFAULT NULL,
  `content` blob DEFAULT NULL,
  `version` int(10) DEFAULT NULL,
  `isActive` tinyint(1) DEFAULT NULL,
  `createdDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`shelfLabelerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `shelfLabelerOrg`;
CREATE TABLE `shelfLabelerOrg` (
 `shelfLabelerId` varchar(64) NOT NULL,
 `organizationId` varchar(64) NOT NULL,
 `userId` varchar(64) DEFAULT NULL,
  `isActive` tinyint(1) DEFAULT NULL,
  `createdDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `package`;
CREATE TABLE `package` (
 `packageId` varchar(64) NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  `alias` varchar(64) DEFAULT NULL,
  `menu` blob DEFAULT NULL,
  `isActive` tinyint(1) DEFAULT NULL,
  `createdDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`packageId`),
  UNIQUE KEY `package_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `packageMenu`;
CREATE TABLE `packageMenu` (
  `name` varchar(64) NOT NULL,
   `menu` blob DEFAULT NULL,
  `isActive` tinyint(1) DEFAULT NULL,
  `createdDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `name` varchar(64) NOT NULL,
   `menu` blob DEFAULT NULL,
  `isActive` tinyint(1) DEFAULT NULL,
  `createdDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
 `roleId` varchar(64) NOT NULL,
  `name` varchar(24) DEFAULT NULL,
  `alias` varchar(24) DEFAULT NULL,
   `menu` blob DEFAULT NULL,
   `organizationId` varchar(64) DEFAULT NULL,
   `packageId` varchar(64) DEFAULT NULL,
  `isActive` tinyint(1) DEFAULT NULL,
  `createdDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`roleId`),
  UNIQUE KEY `role_UNIQUE` (`name`,`organizationId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `organization`;
CREATE TABLE `organization` (
  `organizationId` varchar(64) NOT NULL,
  `name` varchar(64) DEFAULT NULL UNIQUE,
  `alias` varchar(64) DEFAULT NULL,
  `labellerKey` varchar(64) DEFAULT NULL,
  `koronaApiId` varchar(64) DEFAULT NULL,
  `koronaSecret` varchar(64) DEFAULT NULL,
  `koronaToken` varchar(64) DEFAULT NULL,
  `lcbApiKey`  varchar(64) DEFAULT NULL,
  `lcbMmeCode` varchar(24) DEFAULT NULL,
  `packageId` varchar(64) DEFAULT NULL,
  `isActive` tinyint(1) DEFAULT 1,
  `createdDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`organizationId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `organizationUnit`;
CREATE TABLE `organizationUnit` (
 `uuid` varchar(64) ,
  `name` varchar(24) DEFAULT NULL,
  `organizationId` varchar(64) NOT NULL,
  `isActive` tinyint(1) DEFAULT NULL,
  `createdDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `ou_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userId` varchar(64) ,
  `userName` varchar(128) DEFAULT NULL UNIQUE,
  `firstName` varchar(64) DEFAULT NULL,
  `lastName` varchar(64) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `email` varchar(64) DEFAULT NULL,
  `mobile` varchar(64) DEFAULT NULL,
  `roleId` varchar(64) DEFAULT NULL,
  `organizationId` varchar(64) DEFAULT NULL,
  `organizationUnits` varchar(1024) DEFAULT NULL,
  `isKoronaUser` tinyint(1) DEFAULT 0,
  `isAdmin` tinyint(1) DEFAULT 0,
  `isActive` tinyint(1) DEFAULT 1,
  `createdDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `supplier`;
CREATE TABLE `supplier` (
  `supplierId` varchar(64) NOT NULL,
  `userId` varchar(64) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `isActive` tinyint(1) DEFAULT 1,
  `createdDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`userId`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `template`;
CREATE TABLE `template` (
  `templateId` varchar(64) NOT NULL,
  `userId` varchar(64) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL UNIQUE,
  `uniqueIdentifier` varchar(64) DEFAULT NULL,
  `dataIdentifier` varchar(1024) DEFAULT NULL,
  `supplierId` varchar(1024) DEFAULT NULL,
  `isActive` tinyint(1) DEFAULT 1,
  `createdDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`templateId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `image`;
CREATE TABLE `image` (
  `imageId` varchar(64) NOT NULL,
  `userId` varchar(64) DEFAULT NULL,
  `supplierId` varchar(64) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `type` varchar(128) DEFAULT NULL,
  `size` bigint DEFAULT '0',
  `path` varchar(512) DEFAULT NULL,
  `category` varchar(24) DEFAULT NULL,
  `isDefault` tinyint(1) DEFAULT NULL,
  `isActive` tinyint(1) DEFAULT NULL,
  `stockOrderPath` varchar(512) DEFAULT NULL,
  `newProductPath` varchar(512) DEFAULT NULL,
  `createdDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`imageId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

use rts;
DROP TABLE IF EXISTS `templateFileConfig`;
CREATE TABLE `templateFileConfig` (
  `templateId` varchar(64) NOT NULL,
   `name` varchar(64) DEFAULT NULL UNIQUE,
   `type`  varchar(64) DEFAULT NULL,
  `userId` varchar(64) DEFAULT NULL,
  `supplierId` varchar(1024) DEFAULT NULL,
  `isActive` tinyint(1) DEFAULT 1,
  `createdDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`templateId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `templateFileConfigAttribute`;
CREATE TABLE `templateFileConfigAttribute` (
  `templateId` varchar(64) NOT NULL,
  `koronaColumn` varchar(128) DEFAULT NULL,
  `supplierColumn` varchar(128) DEFAULT NULL,
  `alias` varchar(128) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `dataSync`;
CREATE TABLE `dataSync` (
 `id` varchar(64) NOT NULL,
 `userId` varchar(64) DEFAULT NULL,
  `supplierId` varchar(64) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL UNIQUE,
  `cronExpression` varchar(64) DEFAULT NULL,
  `isActive` tinyint(1) DEFAULT 1,
  `createdDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
`organizationId` varchar(64) NOT NULL,
 `uuid` varchar(64) DEFAULT NULL,
  `sector` varchar(64) DEFAULT NULL,
  `recommendedRetailPrice` double DEFAULT 0,
  `commodityGroup` varchar(64) DEFAULT NULL,
  `requiresSerialNumber` tinyint(1) DEFAULT NULL,
  `preparationArticle` tinyint(1) DEFAULT NULL,
  `itemSequence` bigint(20) DEFAULT NULL,
  `activeAssortmentFrom` datetime DEFAULT NULL,
  `printTicketsSeparately` tinyint(1) DEFAULT NULL,
  `costs` double DEFAULT 0,
  `alternativeSector` varchar(64) DEFAULT NULL,
  `revision` bigint(20) DEFAULT NULL,
  `ticketValidityDescription` varchar(64) DEFAULT NULL,
  `imageId` varchar(64) DEFAULT NULL,
  `activeAssortment` tinyint(1) DEFAULT NULL,
  `packaging` tinyint(1) DEFAULT NULL,
  `priceChangeable` tinyint(1) DEFAULT NULL,
  `name` varchar(256) DEFAULT NULL,
  `setType` varchar(64) DEFAULT NULL,
  `trackInventory` tinyint(1) DEFAULT NULL,
  `packagingQuantity` int(10) DEFAULT NULL,
  `barcode` varchar(64) DEFAULT NULL,
  `subArticleSelections` varchar(64) DEFAULT NULL,
  `number` varchar(64) DEFAULT NULL,
  `infoTexts` varchar(256) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `discountable` tinyint(1) DEFAULT NULL,
  `assortment` varchar(64) DEFAULT NULL,
  `producer` varchar(64) DEFAULT NULL,
  `basePriceUnit` varchar(64) DEFAULT NULL,
  `basePriceMax` double DEFAULT 0,
  `basePriceMin` double DEFAULT 0,
  `purchasePrice` double DEFAULT 0,
  `createdDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `articleCode`;
CREATE TABLE `articleCode` (
  `uuid` varchar(64) NOT NULL,
  `organizationId` varchar(64) NOT NULL,
  `code` varchar(64) NOT NULL,
  `quantity` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `articleText`;
CREATE TABLE `articleText` (
  `uuid` varchar(64) NOT NULL,
  `type` varchar(64) DEFAULT NULL,
  `text` varchar(256) DEFAULT NULL,
  `language` varchar(256) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `price`;
CREATE TABLE `price` (
  `uuid` varchar(64) NOT NULL,
  `value` double DEFAULT 0,
  `pricelist` varchar(100) DEFAULT NULL,
  `organizationalUnit` varchar(64) DEFAULT NULL,
  `validFrom` datetime DEFAULT NULL,
  `articleCode` varchar(64) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `supplierItemPrice`;
CREATE TABLE `supplierItemPrice` (
  `uuid` varchar(64) NOT NULL,
  `id` bigint(20) DEFAULT NULL,
  `supplier` varchar(64) DEFAULT NULL,
  `orderNumber` varchar(64) DEFAULT NULL,
  `price` double DEFAULT 0,
  `boxSize` bigint(20) DEFAULT NULL,
  `boxDescription` varchar(64) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag` (
  `puuid` varchar(64) NOT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `revision` bigint(20) DEFAULT NULL,
  `uuid` varchar(64) DEFAULT NULL,
  `number` varchar(64) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `receipt`;
CREATE TABLE `receipt` (
 `organizationId` varchar(64) NOT NULL,
 `uuid` varchar(64) DEFAULT NULL,
 `number` varchar(64) DEFAULT NULL,
 `pos`  varchar(64) NOT NULL,
 `posNr`  varchar(64) NOT NULL,
 `customerGroup`  varchar(64) NOT NULL,
 `revision` bigint(20) DEFAULT NULL,
 `organizationalUnit`  varchar(64) NOT NULL,
 `organizationalUnitNr`  varchar(64) NOT NULL,
  `modifiedTime` datetime DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `sale`;
CREATE TABLE `sale` (
  `uuid` varchar(64) DEFAULT NULL,
  `itemNumber` varchar(64) DEFAULT NULL,
  `receiptNumber` varchar(64) DEFAULT NULL,
  `cashier` varchar(64) DEFAULT NULL,
  `article` varchar(64) DEFAULT NULL,
  `articleNr` varchar(64) DEFAULT NULL,
  `articleEAN` varchar(64) DEFAULT NULL,
  `description` varchar(256) DEFAULT NULL,
  `sector` varchar(64) DEFAULT NULL,
  `commodityGroup` varchar(64) DEFAULT NULL,
  `receipt`  varchar(64) NOT NULL,
  `quantity`  int(10) DEFAULT NULL,
  `netItemPrice` double DEFAULT 0,
  `baseItemPrice` double DEFAULT 0,
  `itemPrice` double DEFAULT 0,
  `cost` double DEFAULT 0,
  `grossItemPrice` double DEFAULT 0,
  `purchasePrice` double DEFAULT 0,
  `bookingTime` datetime DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `kSupplier`;
CREATE TABLE `kSupplier` (
  `organizationId` varchar(64) NOT NULL,
  `uuid` varchar(64) DEFAULT NULL,
  `number` varchar(64) DEFAULT NULL,
  `name` varchar(256) DEFAULT NULL,
  `revision` bigint(20) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `commodityGroup`;
CREATE TABLE `commodityGroup` (
  `organizationId` varchar(64) NOT NULL,
  `uuid` varchar(64) DEFAULT NULL,
  `number` varchar(64) DEFAULT NULL,
  `name` varchar(256) DEFAULT NULL,
  `revision` bigint(20) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT 0,
  `parentCommodityGroup` varchar(64)DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;