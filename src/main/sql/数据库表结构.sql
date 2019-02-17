/*
Navicat MySQL Data Transfer

Source Server         : imshh
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : imshh

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2019-02-17 14:35:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tbl_bill_dtl_materialin
-- ----------------------------
DROP TABLE IF EXISTS `tbl_bill_dtl_materialin`;
CREATE TABLE `tbl_bill_dtl_materialin` (
  `id` varchar(20) NOT NULL,
  `billId` varchar(20) NOT NULL,
  `materialId` varchar(20) NOT NULL,
  `specification` varchar(128) DEFAULT NULL,
  `quantity` decimal(12,4) DEFAULT NULL,
  `price` decimal(12,4) DEFAULT NULL,
  `amount` decimal(12,4) DEFAULT NULL,
  `remark` varchar(1024) DEFAULT NULL,
  `status` char(1) DEFAULT '1',
  `modifyDate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_bill_dtl_materialout
-- ----------------------------
DROP TABLE IF EXISTS `tbl_bill_dtl_materialout`;
CREATE TABLE `tbl_bill_dtl_materialout` (
  `id` varchar(20) NOT NULL,
  `billId` varchar(20) NOT NULL,
  `materialId` varchar(20) NOT NULL,
  `specification` varchar(128) DEFAULT NULL,
  `quantity` decimal(12,4) DEFAULT NULL,
  `price` decimal(12,4) DEFAULT NULL,
  `amount` decimal(12,4) DEFAULT NULL,
  `remark` varchar(1024) DEFAULT NULL,
  `status` char(1) DEFAULT '1',
  `modifyDate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_bill_dtl_productin
-- ----------------------------
DROP TABLE IF EXISTS `tbl_bill_dtl_productin`;
CREATE TABLE `tbl_bill_dtl_productin` (
  `id` varchar(20) NOT NULL,
  `billId` varchar(20) NOT NULL,
  `productId` varchar(20) NOT NULL,
  `quantity` int(12) DEFAULT NULL,
  `remark` varchar(1024) DEFAULT NULL,
  `status` char(1) DEFAULT '1',
  `modifyDate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_bill_dtl_productout
-- ----------------------------
DROP TABLE IF EXISTS `tbl_bill_dtl_productout`;
CREATE TABLE `tbl_bill_dtl_productout` (
  `id` varchar(20) NOT NULL,
  `billId` varchar(20) NOT NULL,
  `productId` varchar(20) NOT NULL,
  `quantity` decimal(12,4) DEFAULT NULL,
  `remark` varchar(1024) DEFAULT NULL,
  `status` char(1) DEFAULT '1',
  `modifyDate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_bill_materialin
-- ----------------------------
DROP TABLE IF EXISTS `tbl_bill_materialin`;
CREATE TABLE `tbl_bill_materialin` (
  `id` varchar(20) NOT NULL,
  `supplierId` varchar(20) NOT NULL,
  `billDate` date NOT NULL,
  `number` varchar(20) DEFAULT NULL,
  `totalQuantity` decimal(12,4) DEFAULT NULL,
  `totalAmount` decimal(12,4) DEFAULT NULL,
  `manager` varchar(20) DEFAULT NULL,
  `accountant` varchar(20) DEFAULT NULL,
  `custodian` varchar(20) DEFAULT NULL,
  `acceptor` varchar(20) DEFAULT NULL,
  `handover` varchar(20) DEFAULT NULL,
  `remark` varchar(1024) DEFAULT NULL,
  `billStatus` char(1) DEFAULT '0',
  `status` char(1) DEFAULT '1',
  `modifyDate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_bill_materialout
-- ----------------------------
DROP TABLE IF EXISTS `tbl_bill_materialout`;
CREATE TABLE `tbl_bill_materialout` (
  `id` varchar(20) NOT NULL,
  `billDate` date NOT NULL,
  `number` varchar(20) DEFAULT NULL,
  `preparedBy` varchar(20) DEFAULT NULL,
  `auditor` varchar(20) DEFAULT NULL,
  `custodian` varchar(20) DEFAULT NULL,
  `billReason` char(2) DEFAULT NULL,
  `totalQuantity` decimal(12,4) DEFAULT NULL,
  `totalAmount` decimal(12,4) DEFAULT NULL,
  `billStatus` char(1) DEFAULT '0',
  `remark` varchar(1024) DEFAULT NULL,
  `status` char(1) DEFAULT '1',
  `modifyDate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_bill_productin
-- ----------------------------
DROP TABLE IF EXISTS `tbl_bill_productin`;
CREATE TABLE `tbl_bill_productin` (
  `id` varchar(20) NOT NULL,
  `number` varchar(20) DEFAULT NULL,
  `billDate` date NOT NULL,
  `billReason` char(2) DEFAULT NULL,
  `orderId` varchar(20) DEFAULT NULL,
  `totalQuantity` decimal(12,4) DEFAULT NULL,
  `preparedBy` varchar(20) DEFAULT NULL,
  `auditor` varchar(20) DEFAULT NULL,
  `custodian` varchar(20) DEFAULT NULL,
  `remark` varchar(1024) DEFAULT NULL,
  `status` char(1) DEFAULT '1',
  `modifyDate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_bill_productout
-- ----------------------------
DROP TABLE IF EXISTS `tbl_bill_productout`;
CREATE TABLE `tbl_bill_productout` (
  `id` varchar(20) NOT NULL,
  `number` varchar(20) DEFAULT NULL,
  `billDate` date NOT NULL,
  `billReason` char(2) DEFAULT NULL,
  `orderId` varchar(20) DEFAULT NULL,
  `totalQuantity` decimal(12,4) DEFAULT NULL,
  `preparedBy` varchar(20) DEFAULT NULL,
  `auditor` varchar(20) DEFAULT NULL,
  `custodian` varchar(20) DEFAULT NULL,
  `remark` varchar(1024) DEFAULT NULL,
  `status` char(1) DEFAULT '1',
  `modifyDate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_customer
-- ----------------------------
DROP TABLE IF EXISTS `tbl_customer`;
CREATE TABLE `tbl_customer` (
  `id` varchar(20) NOT NULL,
  `name` varchar(128) NOT NULL,
  `address` varchar(256) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `eMail` varchar(20) DEFAULT NULL,
  `fax` varchar(20) DEFAULT NULL,
  `contacts` varchar(20) DEFAULT NULL,
  `createDate` date DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL,
  `remark` varchar(1024) DEFAULT NULL,
  `status` char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_fin_account
-- ----------------------------
DROP TABLE IF EXISTS `tbl_fin_account`;
CREATE TABLE `tbl_fin_account` (
  `id` varchar(20) NOT NULL,
  `subjectId` varchar(20) NOT NULL,
  `accountDate` date NOT NULL,
  `accountPeriod` varchar(6) NOT NULL,
  `summary` varchar(256) DEFAULT NULL,
  `voucherId` varchar(20) DEFAULT NULL,
  `debitAmount` decimal(12,2) DEFAULT NULL,
  `creditAmount` decimal(12,2) DEFAULT NULL,
  `remark` varchar(1024) DEFAULT NULL,
  `status` char(1) DEFAULT '1',
  `modifyDate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_fin_subject
-- ----------------------------
DROP TABLE IF EXISTS `tbl_fin_subject`;
CREATE TABLE `tbl_fin_subject` (
  `id` varchar(20) NOT NULL,
  `code` varchar(20) NOT NULL,
  `name` varchar(64) NOT NULL,
  `fullName` varchar(256) DEFAULT NULL,
  `category` char(2) NOT NULL,
  `parentId` varchar(20) DEFAULT NULL,
  `initBalance` decimal(12,4) DEFAULT NULL,
  `privateSubject` char(1) DEFAULT '0',
  `remark` varchar(1024) DEFAULT NULL,
  `status` char(1) DEFAULT '1',
  `modifyDate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_fin_voucher
-- ----------------------------
DROP TABLE IF EXISTS `tbl_fin_voucher`;
CREATE TABLE `tbl_fin_voucher` (
  `id` varchar(20) NOT NULL,
  `billDate` date NOT NULL,
  `word` char(1) NOT NULL,
  `number` int(11) NOT NULL,
  `preparedBy` varchar(20) DEFAULT NULL,
  `auditor` varchar(20) DEFAULT NULL,
  `billStatus` char(1) DEFAULT '0',
  `attachments` varchar(528) DEFAULT NULL,
  `remark` varchar(1024) DEFAULT NULL,
  `status` char(1) DEFAULT '1',
  `modifyDate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_fin_voucher_entry
-- ----------------------------
DROP TABLE IF EXISTS `tbl_fin_voucher_entry`;
CREATE TABLE `tbl_fin_voucher_entry` (
  `id` varchar(20) NOT NULL,
  `voucherId` varchar(20) NOT NULL,
  `summary` varchar(256) DEFAULT NULL,
  `subjectId` varchar(20) NOT NULL,
  `debitAmount` decimal(12,2) DEFAULT NULL,
  `creditAmount` decimal(12,2) DEFAULT NULL,
  `remark` varchar(1024) DEFAULT NULL,
  `status` char(1) DEFAULT '1',
  `modifyDate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_material
-- ----------------------------
DROP TABLE IF EXISTS `tbl_material`;
CREATE TABLE `tbl_material` (
  `id` varchar(20) NOT NULL,
  `name` varchar(64) NOT NULL,
  `specification` varchar(128) DEFAULT NULL,
  `unit` varchar(20) DEFAULT NULL,
  `categoryId` varchar(20) DEFAULT NULL,
  `storage` decimal(10,3) DEFAULT NULL,
  `remark` varchar(1024) DEFAULT NULL,
  `status` char(1) DEFAULT '1',
  `modifyDate` datetime DEFAULT NULL,
  KEY `categoryId` (`categoryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_material_category
-- ----------------------------
DROP TABLE IF EXISTS `tbl_material_category`;
CREATE TABLE `tbl_material_category` (
  `id` varchar(20) NOT NULL,
  `code` varchar(20) NOT NULL,
  `name` varchar(64) NOT NULL,
  `parentId` varchar(20) DEFAULT NULL,
  `remark` varchar(1024) DEFAULT NULL,
  `status` char(1) DEFAULT '1',
  `modifyDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_material_inventory
-- ----------------------------
DROP TABLE IF EXISTS `tbl_material_inventory`;
CREATE TABLE `tbl_material_inventory` (
  `id` varchar(20) NOT NULL,
  `inventoryDate` date NOT NULL,
  `pricingRules` char(1) DEFAULT NULL,
  `total` decimal(12,4) DEFAULT NULL,
  `remark` varchar(1024) DEFAULT NULL,
  `status` char(1) DEFAULT '1',
  `modifyDate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_material_inventorydetail
-- ----------------------------
DROP TABLE IF EXISTS `tbl_material_inventorydetail`;
CREATE TABLE `tbl_material_inventorydetail` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `inventoryId` varchar(20) NOT NULL,
  `materialId` varchar(20) NOT NULL,
  `expectQuantity` decimal(12,4) DEFAULT NULL,
  `actualQuantity` decimal(12,4) DEFAULT NULL,
  `price` decimal(12,4) DEFAULT NULL,
  `status` char(1) DEFAULT '1',
  `modifyDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2838 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_mtl_inout
-- ----------------------------
DROP TABLE IF EXISTS `tbl_mtl_inout`;
CREATE TABLE `tbl_mtl_inout` (
  `id` varchar(20) NOT NULL,
  `billId` varchar(20) DEFAULT NULL,
  `materialId` varchar(20) NOT NULL,
  `genDate` date NOT NULL,
  `billPeriod` varchar(6) NOT NULL,
  `inQuantity` decimal(12,4) DEFAULT NULL,
  `inAmount` decimal(12,4) DEFAULT NULL,
  `outQuantity` decimal(12,4) DEFAULT NULL,
  `outAmount` decimal(12,4) DEFAULT NULL,
  `balanceQuantity` decimal(12,4) DEFAULT NULL,
  `balanceAmount` decimal(12,4) DEFAULT NULL,
  `summary` varchar(1024) DEFAULT NULL,
  `inventoryed` char(1) DEFAULT '0',
  `status` char(1) DEFAULT '1',
  `modifyDate` datetime DEFAULT NULL,
  KEY `billPeriod` (`billPeriod`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_order
-- ----------------------------
DROP TABLE IF EXISTS `tbl_order`;
CREATE TABLE `tbl_order` (
  `id` varchar(20) NOT NULL,
  `identify` varchar(20) NOT NULL,
  `orderType` char(1) NOT NULL,
  `orderDate` date DEFAULT NULL,
  `amountRMB` decimal(12,4) DEFAULT NULL,
  `totalAmount` decimal(12,4) DEFAULT NULL,
  `billStatus` char(1) DEFAULT '1',
  `customerId` varchar(20) DEFAULT NULL,
  `remark` varchar(1024) DEFAULT NULL,
  `status` char(1) DEFAULT '1',
  `modifyDate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_order_appointment
-- ----------------------------
DROP TABLE IF EXISTS `tbl_order_appointment`;
CREATE TABLE `tbl_order_appointment` (
  `orderId` varchar(20) NOT NULL,
  `deliveryTerm` date DEFAULT NULL,
  `shippingAddress` varchar(256) DEFAULT NULL,
  `needInvoice` char(1) DEFAULT '0',
  `invoiceCategory` varchar(64) DEFAULT NULL,
  `paymentMethod` varchar(64) DEFAULT NULL,
  `exchangeRate` decimal(12,4) DEFAULT NULL,
  `paymentAppointment` varchar(512) DEFAULT NULL,
  `otherAppointment` varchar(1024) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_order_item
-- ----------------------------
DROP TABLE IF EXISTS `tbl_order_item`;
CREATE TABLE `tbl_order_item` (
  `id` varchar(20) NOT NULL,
  `orderId` varchar(20) NOT NULL,
  `productId` varchar(20) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `price` decimal(12,4) DEFAULT NULL,
  `amount` decimal(12,4) DEFAULT NULL,
  `remark` varchar(1024) DEFAULT NULL,
  `status` char(1) DEFAULT '1',
  `modifyDate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_product
-- ----------------------------
DROP TABLE IF EXISTS `tbl_product`;
CREATE TABLE `tbl_product` (
  `id` varchar(20) NOT NULL,
  `code` varchar(20) NOT NULL,
  `name` varchar(20) NOT NULL,
  `specification` varchar(64) DEFAULT NULL,
  `model` varchar(64) DEFAULT NULL,
  `lineDate` date DEFAULT NULL,
  `downlineDate` date DEFAULT NULL,
  `storage` int(11) DEFAULT '0',
  `modifyDate` datetime DEFAULT NULL,
  `remark` varchar(1024) DEFAULT NULL,
  `status` char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_product_inout
-- ----------------------------
DROP TABLE IF EXISTS `tbl_product_inout`;
CREATE TABLE `tbl_product_inout` (
  `id` varchar(20) NOT NULL,
  `billId` varchar(20) NOT NULL,
  `productId` varchar(20) NOT NULL,
  `genDate` date NOT NULL,
  `billPeriod` varchar(6) NOT NULL,
  `inQuantity` int(11) DEFAULT NULL,
  `outQuantity` int(11) DEFAULT NULL,
  `balanceQuantity` int(11) DEFAULT NULL,
  `summary` varchar(1024) DEFAULT NULL,
  `status` char(1) DEFAULT '1',
  `inventoryed` char(1) DEFAULT '',
  `modifyDate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_product_inventory
-- ----------------------------
DROP TABLE IF EXISTS `tbl_product_inventory`;
CREATE TABLE `tbl_product_inventory` (
  `id` varchar(20) NOT NULL,
  `inventoryDate` date NOT NULL,
  `pricingRules` char(1) DEFAULT NULL,
  `total` decimal(12,4) DEFAULT NULL,
  `remark` varchar(1024) DEFAULT NULL,
  `status` char(1) DEFAULT '1',
  `modifyDate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_product_inventorydetail
-- ----------------------------
DROP TABLE IF EXISTS `tbl_product_inventorydetail`;
CREATE TABLE `tbl_product_inventorydetail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `inventoryId` varchar(20) NOT NULL,
  `productId` varchar(20) NOT NULL,
  `expectQuantity` int(11) DEFAULT NULL,
  `actualQuantity` int(11) DEFAULT NULL,
  `price` decimal(12,4) DEFAULT NULL,
  `status` char(1) DEFAULT '1',
  `modifyDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2262 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_supplier
-- ----------------------------
DROP TABLE IF EXISTS `tbl_supplier`;
CREATE TABLE `tbl_supplier` (
  `id` varchar(20) NOT NULL,
  `name` varchar(20) NOT NULL,
  `address` varchar(256) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `eMail` varchar(20) DEFAULT NULL,
  `fax` varchar(20) DEFAULT NULL,
  `contacts` varchar(20) DEFAULT NULL,
  `createDate` date DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL,
  `remark` varchar(1024) DEFAULT NULL,
  `status` char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for tbl_sys_parameter
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sys_parameter`;
CREATE TABLE `tbl_sys_parameter` (
  `pname` varchar(64) DEFAULT NULL,
  `pvalue` varchar(128) DEFAULT NULL,
  `pvtype` varchar(10) DEFAULT 'String',
  `display` char(1) DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_tmp_material_inventorydetail
-- ----------------------------
DROP TABLE IF EXISTS `tbl_tmp_material_inventorydetail`;
CREATE TABLE `tbl_tmp_material_inventorydetail` (
  `materialId` varchar(20) NOT NULL,
  `actualQuantity` decimal(12,4) DEFAULT '0.0000',
  `price` decimal(12,4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_tmp_product_inventorydetail
-- ----------------------------
DROP TABLE IF EXISTS `tbl_tmp_product_inventorydetail`;
CREATE TABLE `tbl_tmp_product_inventorydetail` (
  `productId` varchar(20) NOT NULL,
  `actualQuantity` int(11) DEFAULT '0',
  `price` decimal(12,4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_authority
-- ----------------------------
DROP TABLE IF EXISTS `t_authority`;
CREATE TABLE `t_authority` (
  `id` varchar(20) NOT NULL,
  `name` varchar(60) NOT NULL,
  `parentId` varchar(20) NOT NULL,
  `action` varchar(256) NOT NULL,
  `dependents` varchar(1024) DEFAULT NULL,
  `remark` varchar(1024) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_invoice
-- ----------------------------
DROP TABLE IF EXISTS `t_invoice`;
CREATE TABLE `t_invoice` (
  `id` varchar(20) NOT NULL,
  `invoiceDate` date NOT NULL,
  `orderIdentify` varchar(20) NOT NULL,
  `customerName` varchar(128) NOT NULL,
  `amountWithTax` decimal(10,2) DEFAULT NULL,
  `valueAddTax` decimal(10,2) DEFAULT NULL,
  `exciseTax` decimal(10,2) DEFAULT NULL,
  `constructionTax` decimal(10,2) DEFAULT NULL,
  `educationFee` decimal(10,2) DEFAULT NULL,
  `totalTax` decimal(10,2) DEFAULT NULL,
  `drawback` decimal(10,2) DEFAULT NULL,
  `valueAddTaxCal` varchar(256) DEFAULT '''价税合计''/1.17*0.09',
  `exciseTaxCal` varchar(256) DEFAULT '''价税合计''/1.17*0.15',
  `constructionTaxCal` varchar(256) DEFAULT '(''应交增值税''+''应交消费税'')*0.07',
  `educationFeeCal` varchar(256) DEFAULT '(''应交增值税''+''应交消费税'')*0.05',
  `totalTaxCal` varchar(256) DEFAULT '''应交增值税''+''应交消费税''+''城建税''+''教育费附加''',
  `drawbackCal` varchar(256) DEFAULT '''价税合计''/1.17*0.28',
  `modifyDate` datetime DEFAULT NULL,
  `remark` varchar(1024) DEFAULT NULL,
  `status` char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
  `id` varchar(20) NOT NULL,
  `name` varchar(60) NOT NULL,
  `parentId` varchar(20) NOT NULL,
  `url` varchar(128) NOT NULL,
  `icon` varchar(128) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` varchar(20) NOT NULL,
  `name` varchar(60) NOT NULL,
  `buildIn` char(1) NOT NULL DEFAULT '0',
  `remark` varchar(1024) DEFAULT NULL,
  `status` char(1) DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_role_authority
-- ----------------------------
DROP TABLE IF EXISTS `t_role_authority`;
CREATE TABLE `t_role_authority` (
  `roleId` varchar(20) NOT NULL,
  `authorityId` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` varchar(20) NOT NULL,
  `username` varchar(60) NOT NULL,
  `password` varchar(40) NOT NULL,
  `fullname` varchar(60) NOT NULL,
  `homePage` varchar(20) DEFAULT '',
  `remark` varchar(1024) DEFAULT NULL,
  `status` char(1) DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `userId` varchar(20) NOT NULL,
  `roleId` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- View structure for view_material
-- ----------------------------
DROP VIEW IF EXISTS `view_material`;
CREATE ALGORITHM=UNDEFINED DEFINER=`imshh`@`localhost` SQL SECURITY DEFINER VIEW `view_material` AS select `m`.`id` AS `id`,`m`.`name` AS `name`,`m`.`specification1` AS `specification1`,`m`.`specification2` AS `specification2`,`m`.`specification3` AS `specification3`,`m`.`unit` AS `unit`,`m`.`formula` AS `formula`,`m`.`category` AS `category`,`m`.`modifyDate` AS `modifyDate`,`m`.`remark` AS `remark`,`m`.`status` AS `status`,`s`.`amount` AS `storage` from (`tbl_material` `m` left join `view_storage` `s` on(((`m`.`name` = `s`.`materialname`) and (`m`.`specification1` = `s`.`specification1`) and (`m`.`specification2` = `s`.`specification2`) and (`m`.`specification3` = `s`.`specification3`)))) ;

-- ----------------------------
-- View structure for view_materialin
-- ----------------------------
DROP VIEW IF EXISTS `view_materialin`;
CREATE ALGORITHM=UNDEFINED DEFINER=`imshh`@`localhost` SQL SECURITY DEFINER VIEW `view_materialin` AS select sum(`t_materialin`.`totlemnt`) AS `payable`,`t_materialin`.`supplierName` AS `supplierName`,concat(year(`t_materialin`.`receiveDate`),'-',month(`t_materialin`.`receiveDate`)) AS `month` from `t_materialin` group by `t_materialin`.`supplierName`,`month` ;

-- ----------------------------
-- View structure for view_payment
-- ----------------------------
DROP VIEW IF EXISTS `view_payment`;
CREATE ALGORITHM=UNDEFINED DEFINER=`imshh`@`localhost` SQL SECURITY DEFINER VIEW `view_payment` AS select `mtlin`.`payable` AS `payable`,(case when isnull(`tran`.`paid`) then 0 else `tran`.`paid` end) AS `paid`,`mtlin`.`supplierName` AS `supplierName`,`mtlin`.`month` AS `month` from (((select sum(`imshh`.`t_materialin`.`totlemnt`) AS `payable`,`imshh`.`t_materialin`.`supplierName` AS `supplierName`,concat(year(`imshh`.`t_materialin`.`receiveDate`),'-',month(`imshh`.`t_materialin`.`receiveDate`)) AS `month` from `imshh`.`t_materialin` group by `imshh`.`t_materialin`.`supplierName`,`month`)) `mtlin` left join (select sum(`imshh`.`t_transaction`.`tranAmount`) AS `paid`,`imshh`.`t_transaction`.`tranUser` AS `tranUser`,concat(year(`imshh`.`t_transaction`.`tranDate`),'-',month(`imshh`.`t_transaction`.`tranDate`)) AS `month` from `imshh`.`t_transaction` group by `imshh`.`t_transaction`.`tranUser`,`month`) `tran` on(((`mtlin`.`supplierName` = `tran`.`tranUser`) and (`mtlin`.`month` = `tran`.`month`)))) ;

-- ----------------------------
-- View structure for view_product
-- ----------------------------
DROP VIEW IF EXISTS `view_product`;
CREATE ALGORITHM=UNDEFINED DEFINER=`imshh`@`localhost` SQL SECURITY DEFINER VIEW `view_product` AS select `odr`.`pdtNo` AS `pdtNo`,`odr`.`orderquantity` AS `orderQuantity`,`odr`.`orderamount` AS `orderAmount`,`odr`.`year` AS `year`,(case when isnull(`production`.`productionQuantity`) then 0 else `production`.`productionQuantity` end) AS `productionQuantity`,(case when isnull(`dlr`.`deliverQuantity`) then 0 else `dlr`.`deliverQuantity` end) AS `deliverQuantity`,(case when isnull(`dlr`.`deliverAmount`) then 0 else `dlr`.`deliverAmount` end) AS `deliverAmount` from ((((select `o`.`pdtNo` AS `pdtNo`,`o`.`orderquantity` AS `orderquantity`,`o`.`orderamount` AS `orderamount`,`o`.`year` AS `year` from (select `od`.`pdtNo` AS `pdtNo`,sum(`od`.`quantity`) AS `orderquantity`,sum(`od`.`totlmentRMB`) AS `orderamount`,year(`o`.`orderDate`) AS `year` from (`imshh`.`t_orderdetail` `od` join `imshh`.`t_order` `o`) where (`od`.`orderIdentify` = `o`.`identify`) group by `od`.`pdtNo`,`year`) `o`)) `odr` left join (select `imshh`.`t_productin`.`pdtNo` AS `pdtNo`,sum(`imshh`.`t_productin`.`amount`) AS `productionQuantity`,year(`imshh`.`t_productin`.`storageDate`) AS `year` from `imshh`.`t_productin` group by `imshh`.`t_productin`.`pdtNo`,`year`) `production` on(((`odr`.`pdtNo` = `production`.`pdtNo`) and (`odr`.`year` = `production`.`year`)))) left join (select year(`po`.`deliverDate`) AS `year`,`po`.`pdtNo` AS `pdtNo`,sum(`po`.`amount`) AS `deliverQuantity`,sum(((`po`.`amount` * `od`.`priceDollar`) * `o`.`exchangeRate`)) AS `deliverAmount` from ((`imshh`.`t_productout` `po` join `imshh`.`t_order` `o`) join `imshh`.`t_orderdetail` `od`) where ((`po`.`orderIdentify` = `o`.`identify`) and (`po`.`pdtNo` = `od`.`pdtNo`) and (`o`.`identify` = `od`.`orderIdentify`)) group by `po`.`pdtNo`,`year`) `dlr` on(((`odr`.`pdtNo` = `dlr`.`pdtNo`) and (`odr`.`year` = `dlr`.`year`)))) ;

-- ----------------------------
-- View structure for view_productout
-- ----------------------------
DROP VIEW IF EXISTS `view_productout`;
CREATE ALGORITHM=UNDEFINED DEFINER=`imshh`@`localhost` SQL SECURITY DEFINER VIEW `view_productout` AS select `po`.`orderIdentify` AS `orderIdentify`,concat(year(`po`.`deliverDate`),'-',month(`po`.`deliverDate`)) AS `delivermonth`,sum((`po`.`amount` * `od`.`priceRMB`)) AS `debt` from (`t_productout` `po` join `t_orderdetail` `od`) where ((`po`.`pdtNo` = `od`.`pdtNo`) and (`po`.`orderIdentify` = `od`.`orderIdentify`)) group by `po`.`orderIdentify`,`delivermonth` ;

-- ----------------------------
-- View structure for view_storage
-- ----------------------------
DROP VIEW IF EXISTS `view_storage`;
CREATE ALGORITHM=UNDEFINED DEFINER=`imshh`@`localhost` SQL SECURITY DEFINER VIEW `view_storage` AS select `view_storage_base`.`materialname` AS `materialname`,`view_storage_base`.`specification1` AS `specification1`,`view_storage_base`.`specification2` AS `specification2`,`view_storage_base`.`specification3` AS `specification3`,sum(`view_storage_base`.`amount`) AS `amount` from `view_storage_base` group by `view_storage_base`.`materialname`,`view_storage_base`.`specification1`,`view_storage_base`.`specification2`,`view_storage_base`.`specification3` ;

-- ----------------------------
-- View structure for view_storage_base
-- ----------------------------
DROP VIEW IF EXISTS `view_storage_base`;
CREATE ALGORITHM=UNDEFINED DEFINER=`imshh`@`localhost` SQL SECURITY DEFINER VIEW `view_storage_base` AS select `t_materialin`.`materialName` AS `materialname`,`t_materialin`.`specification1` AS `specification1`,`t_materialin`.`specification2` AS `specification2`,`t_materialin`.`specification3` AS `specification3`,sum(`t_materialin`.`amount`) AS `amount` from `t_materialin` where ((`t_materialin`.`status` = '1') and (`t_materialin`.`inventory` = '0')) group by `t_materialin`.`materialName`,`t_materialin`.`specification1`,`t_materialin`.`specification2`,`t_materialin`.`specification3` union all select `t_materialout`.`materialName` AS `materialname`,`t_materialout`.`specification1` AS `specification1`,`t_materialout`.`specification2` AS `specification2`,`t_materialout`.`specification3` AS `specification3`,((0 - sum(`t_materialout`.`outAmount`)) + sum(`t_materialout`.`returnAmount`)) AS `amount` from `t_materialout` group by `t_materialout`.`materialName`,`t_materialout`.`specification1`,`t_materialout`.`specification2`,`t_materialout`.`specification3` union all select `m`.`name` AS `materialname`,`m`.`specification1` AS `specification1`,`m`.`specification2` AS `specification2`,`m`.`specification3` AS `specification3`,(0 - sum(`r`.`amount`)) AS `amount` from ((`tbl_material_retreat` `r` join `tbl_material` `m`) join `t_materialin` `mi`) where ((`r`.`materialinID` = `mi`.`id`) and (`mi`.`materialName` = `m`.`name`)) group by `m`.`name`,`m`.`specification1`,`m`.`specification2`,`m`.`specification3` union all select `m`.`name` AS `materialname`,`m`.`specification1` AS `specification1`,`m`.`specification2` AS `specification2`,`m`.`specification3` AS `specification3`,sum(`ind`.`actualAmount`) AS `amount` from (`tbl_material_inventorydetail` `ind` join `tbl_material` `m`) where (`ind`.`materialId` = `m`.`id`) group by `m`.`name`,`m`.`specification1`,`m`.`specification2`,`m`.`specification3` ;

-- ----------------------------
-- View structure for view_transactionin
-- ----------------------------
DROP VIEW IF EXISTS `view_transactionin`;
CREATE ALGORITHM=UNDEFINED DEFINER=`imshh`@`localhost` SQL SECURITY DEFINER VIEW `view_transactionin` AS select sum(abs(`t_transaction`.`tranAmount`)) AS `reception`,`t_transaction`.`orderIdentify` AS `orderIdentify`,concat(year(`t_transaction`.`tranDate`),'-',month(`t_transaction`.`tranDate`)) AS `tranmonth` from `t_transaction` where (`t_transaction`.`orderIdentify` is not null) group by `t_transaction`.`orderIdentify`,`tranmonth` ;

-- ----------------------------
-- View structure for view_transactionout
-- ----------------------------
DROP VIEW IF EXISTS `view_transactionout`;
CREATE ALGORITHM=UNDEFINED DEFINER=`imshh`@`localhost` SQL SECURITY DEFINER VIEW `view_transactionout` AS select sum(abs(`t_transaction`.`tranAmount`)) AS `paid`,`t_transaction`.`tranUser` AS `supplierName`,concat(year(`t_transaction`.`tranDate`),'-',month(`t_transaction`.`tranDate`)) AS `month` from `t_transaction` where (`t_transaction`.`tranUser` is not null) group by `t_transaction`.`tranUser`,`month` ;
