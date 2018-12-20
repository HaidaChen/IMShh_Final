CREATE TABLE TBL_SYS_PARAMETER(
    pname varchar(64),
    pvalue varchar(128),
    pvtype varchar(10) default 'String',
    display char(1) default '1'
);

CREATE TABLE TBL_SYS_DICTIONARY(
    dname varchar(64),
    vkey varchar(128),
    vtext varchar(128)
);

CREATE TABLE TBL_MATERIAL_CATEGORY(
    id varchar(20) not null,
    code varchar(20) not null,
    name varchar(64) not null,
    parentId varchar(20),
    remark varchar(1024),
    status char(1) default '1',
    modifyDate datetime
);

CREATE TABLE TBL_MATERIAL(
    id varchar(20) not null,
    name varchar(64) not null,
    specification varchar(128),
    unit varchar(20),
    categoryId varchar(20), 
    storage numeric(10,3),
    remark varchar(1024),
    status char(1) default '1',
    modifyDate datetime
);

CREATE TABLE TBL_BILL_MATERIALIN(
	id varchar(20) not null,
	supplierId varchar(20) not null,
	inDate date not null,
	number varchar(20),
	totalQuantity numeric(12, 4),
	totalAmount numeric(12, 4),
	manager varchar(20),
	accountant varchar(20),
	custodian varchar(20),
	acceptor varchar(20),
	handover varchar(20),
	billStatus char(1) default '0',
	remark varchar(1024),
    status char(1) default '1',
    modifyDate datetime
);

CREATE TABLE TBL_BILL_MATERIALOUT(
	id varchar(20) not null,
	outDate date not null,
	number varchar(20),
	preparedBy varchar(20),
	auditor varchar(20),
	custodian varchar(20),
	outReason char(2),
	totalQuantity numeric(12, 4),
	totalAmount numeric(12, 4),
	billStatus char(1) default '0',
	remark varchar(1024),
    status char(1) default '1',
    modifyDate datetime
);

CREATE TABLE TBL_BILL_DTL_MATERIALIN(
	id varchar(20) not null,
	billId varchar(20) not null,
	materialId varchar(20) not null,
	specification varchar(128),
	quantity numeric(12, 4),
	price numeric(12, 4),
	amount numeric(12, 4),
	remark varchar(1024),
    status char(1) default '1',
    modifyDate datetime
);

CREATE TABLE TBL_BILL_DTL_MATERIALOUT(
	id varchar(20) not null,
	billId varchar(20) not null,
	materialId varchar(20) not null,
	specification varchar(128),
	quantity numeric(12, 4),
	price numeric(12, 4),
	amount numeric(12, 4),
	remark varchar(1024),
    status char(1) default '1',
    modifyDate datetime
);

CREATE TABLE TBL_FIN_SUBJECT(
	id varchar(20) not null,
	code varchar(20) not null,
	name varchar(64) not null,
	fullName varchar(256) not null, 
	category char(2) not null,
	parentId varchar(20),
	initBalance numeric(12, 4),
	
	remark varchar(1024),
    status char(1) default '1',
    modifyDate datetime
);

CREATE TABLE TBL_FIN_VOUCHER(
	id varchar(20) not null,
	billDate date not null,
	word char(1) not null,
	number int not null,
	preparedBy varchar(20),
	auditor varchar(20),
	billStatus char(1) default '0',
	attachments varchar(528),
	
	remark varchar(1024),
    status char(1) default '1',
    modifyDate datetime
);

CREATE TABLE TBL_FIN_VOUCHER_ENTRY(
	id varchar(20) not null,
	voucherId varchar(20) not null,
	summary varchar(256),
	subjectId varchar(20) not null,
	debitAmount numeric(12,2),
	creditAmount numeric(12,2),
	
	remark varchar(1024),
    status char(1) default '1',
    modifyDate datetime
);

/*
CREATE TABLE TBL_FIN_SUBJECT_BALANCE(
	id varchar(20) not null,
	subjectId varchar(20) not null,
	accountPeriod varchar(6) not null,
	voucherId varchar(20),
	voucherEntryId varchar(20),
	balance numeric(12,2),
	
	remark varchar(1024),
    status char(1) default '1',
    modifyDate datetime
);
*/

CREATE TABLE TBL_FIN_ACCOUNT(
	id varchar(20) not null,
	subjectId varchar(20) not null,
	accountDate date not null,
	accountPeriod varchar(6) not null,
	summary varchar(256),
	voucherId varchar(20),
	debitAmount numeric(12,2),
	creditAmount numeric(12,2),
	
	remark varchar(1024),
    status char(1) default '1',
    modifyDate datetime
);

/*------*/
CREATE TABLE TBL_MATERIAL_SUPPLIER(
    id varchar(20) not null,
    materialId varchar(20) not null,
    supplierId varchar(20) not null,
    
    remark varchar(1024),
    status char(1),
    modifyDate datetime
);

CREATE TABLE TBL_MATERIAL_PIRCE(
    id varchar(20) not null,
    supplierId varchar(20) not null,
    price numeric(10,2) not null,
    markDate date not null,
    
    remark varchar(1024),
    status char(1) default '1',
    modifyDate datetime
);

CREATE TABLE TBL_MATERIAL_IN(
    id varchar(20) not null,
    inDate date not null,
    materialID varchar(20),
    amount numeric(10,2) not null,
    supplierId  varchar(20),
    
    remark varchar(1024),
    status char(1) default '1',
    modifyDate datetime
);

CREATE TABLE TBL_MATERIAL_OUT(
    id varchar(20) not null,
    outDate date not null,
    materialID varchar(20),
    amount numeric(10,2) not null,
    
    remark varchar(1024),
    status char(1) default '1',
    modifyDate datetime
);

CREATE TABLE TBL_MATERIAL_INVENTORY(
    id varchar(20) not null,
    inventoryDate date not null,
    pricingRules char(1),
    total numeric(10,2),
    
    remark varchar(1024),
    status char(1) default '1',
    modifyDate datetime
);

CREATE TABLE TBL_MATERIAL_INVENTORYDETAIL(
    id varchar(20) not null,
    inventoryId varchar(20) not null,
    materialId varchar(20) not null,
    expectAmount numeric(10,2) not null,
    actualAmount numeric(10,2),
    
    remark varchar(1024),
    status char(1) default '1',
    modifyDate datetime
);

CREATE TABLE TBL_MATERIAL_RETREAT(
    id varchar(20) not null,
    materialinID varchar(20) not null,
    amount numeric(10,2),
    
    remark varchar(1024),
    status char(1) default '1',
    modifyDate datetime
);



CREATE TABLE T_CUSTOMER(
    id varchar(20) not null,
    name varchar(128) not null,
    address varchar(256),
    phone varchar(20),
    eMail varchar(20),
    fax varchar(20),
    contacts varchar(20),
    createDate date,  
    modifyDate datetime,
    remark varchar(1024),
    status char(1)
);




CREATE TABLE T_SUPPLIER(
    id varchar(20) not null,
    name varchar(20) not null,
    address varchar(256),
    phone varchar(20),
    eMail varchar(20),
    fax varchar(20),
    contacts varchar(20),
    createDate date,  
    modifyDate datetime,
    remark varchar(1024),
    status char(1)
);

CREATE TABLE T_Product(
    id varchar(20) not null,
    code varchar(20) not null,
    name varchar(20) not null,
    specification varchar(64),
    model varchar(64),    
    lineDate date, 
    downlineDate date,
    modifyDate datetime,
    remark varchar(1024),
    status char(1)
);

/*
CREATE TABLE T_MATERIAL(
    id varchar(20) not null,
    name varchar(64) not null,
    supplierName varchar(64),
    specification1 varchar(64),
    specification2 varchar(64),
    specification3 varchar(64),
    unit varchar(20),
    formula varchar(256),
    category varchar(20), 
    modifyDate datetime,
    remark varchar(1024),
    status char(1)
);*/



CREATE TABLE T_ORDER(
    id varchar(20) not null,
    identify varchar(64) not null unique,
    orderType varchar(20),
    custName varchar(128),    
    orderDate date,
    deliveryTerm date,
    exchangeRate numeric(14,7),
    amountRMB numeric(14,4),
    amountDollar numeric(14,4),
    state char(1),
    modifyDate datetime,
    remark varchar(1024),
    status char(1)
);

CREATE TABLE T_ORDERDETAIL(
    id varchar(20) not null,
    orderIdentify varchar(64) not null,
    pdtNo varchar(20) not null,
    pdtName varchar(20),   
    content varchar(20), 
    quantity int,
    priceRMB numeric(12,4),
    priceDollar numeric(12,4),
    totlmentRMB numeric(14,4),
    totlmentDollar numeric(14,4),
    
    modifyDate datetime,
    remark varchar(1024),
    status char(1)
);


CREATE TABLE T_MATERIALIN(
    id varchar(20) not null,
    receiveDate date not null,
    orderIdentify varchar(20),
    materialId varchar(20),
    materialName varchar(20) not null,
    supplierId varchar(20),
    supplierName varchar(20),    
    specification1 varchar(20),
    specification2 varchar(20),
    specification3 varchar(20),
    formula varchar(256),
    unit varchar(20),
    meterage numeric(12,4),
    amount numeric(10,3) not null,    
    unitPrice numeric(10,2),
    totlemnt numeric(10,2),
    
    modifyDate datetime,
    remark varchar(1024),
    status char(1)
);

CREATE TABLE T_MATERIALOUT(
    id varchar(20) not null,
    outDate date not null,
    handMan varchar(20),
    orderIdentify varchar(20),
    materialId varchar(20),
    materialName varchar(20) not null,
    specification1 varchar(20),
    specification2 varchar(20),
    specification3 varchar(20),    
    outAmount numeric(10,3) not null,    
    returnAmount numeric(10,3),
    
    modifyDate datetime,
    remark varchar(1024),
    status char(1)
);

CREATE TABLE T_PRODUCTIN (
    id varchar(20) not null,
    storageDate date not null,
    orderIdentify varchar(20),
    pdtNo varchar(20) not null,
    content varchar(20) not null,
    amount int not null,
    
    modifyDate datetime,
    remark varchar(1024),
    status char(1)
);

CREATE TABLE T_PRODUCTOUT (
    id varchar(20) not null,
    deliverDate date not null,
    orderIdentify varchar(20),
    customerName varchar(128),
    pdtNo varchar(20) not null,
    content varchar(20) not null,
    amount int not null,
    
    modifyDate datetime,
    remark varchar(1024),
    status char(1)
);

CREATE TABLE T_INVOICE(
    id varchar(20) not null,
    invoiceDate date not null,
    orderIdentify varchar(20) not null,
    customerName varchar(128) not null,    
    amountWithTax  numeric(10,2),
    valueAddTax  numeric(10,2),
    exciseTax  numeric(10,2),
    constructionTax  numeric(10,2),
    educationFee  numeric(10,2),
    totalTax  numeric(10,2),
    drawback  numeric(10,2),
    
    valueAddTaxCal  varchar(256) default '\'价税合计\'/1.17*0.09',
    exciseTaxCal  varchar(256) default '\'价税合计\'/1.17*0.15',
    constructionTaxCal  varchar(256) default '(\'应交增值税\'+\'应交消费税\')*0.07',
    educationFeeCal  varchar(256) default '(\'应交增值税\'+\'应交消费税\')*0.05',
    totalTaxCal  varchar(256) default '\'应交增值税\'+\'应交消费税\'+\'城建税\'+\'教育费附加\'',
    drawbackCal  varchar(256) default '\'价税合计\'/1.17*0.28',
    
    modifyDate datetime,
    remark varchar(1024),
    status char(1)
);

CREATE TABLE T_RECEPTION_SETTLEMENT(
    id varchar(20) not null,
    settlementDate date not null,
    lastSettlement date,
    reception numeric(10,2) not null,
    payment numeric(10,2),
    modifyDate datetime,
    remark varchar(1024),
    status char(1)
);

CREATE TABLE T_RECEPTION_SETTLEMENT_DETAIL(
    settlementId varchar(20),
    orderIdentify varchar(20)
);

CREATE TABLE T_ACCOUNT_PAYMENT(
    supplierId varchar(20) not null,
    supplierName varchar(128) not null,
    month varchar(20) not null,
    debt numeric(10,2),
    payment numeric(10,2)
);

CREATE TABLE T_ACCOUNT(
    id varchar(20) not null,
    accountNo varchar(60) not null,
    bank varchar(20) not null,
    brachBank varchar(64),
    bankLogo varchar(20),
    accountUser varchar(20) not null,
    accountType char(1) not null,
    balance numeric(10,2),
        
    modifyDate datetime,
    status char(1)
);

CREATE TABLE T_TRANSACTION (
    id varchar(20) not null,
    accountNo varchar(60) not null,
    tranDate datetime not null,
    tranType char(1) not null,
    tranAmount numeric(10,2) not null,
    balance numeric(10,2) not null,
    
    tranUser  varchar(120), 
    tranBank  varchar(120),
    tranAccountNo varchar(60),
    
    orderIdentify varchar(20),
    purchaseId varchar(20),
    
    modifyDate datetime,
    remark varchar(1024),
    status char(1)
);



CREATE TABLE T_USER(
	id varchar(20) not null,
	username varchar(60) not null unique,
	password varchar(20) not null,
	fullname varchar(60) not null,
	birthday date,
	gender char(1),
	workNo varchar(20),
	dept varchar(60),
	position varchar(60),
	phone varchar(60),
	email varchar(128),
	qq varchar(20),
	weichat varchar(60),
	head varchar(60),
	remark varchar(1024),
	status char(1),
	modifyDate datetime
);

DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` varchar(20) NOT NULL,
  `name` varchar(60) NOT NULL,
  `admin` int(11) NOT NULL DEFAULT '0',
  `remark` varchar(1024) DEFAULT NULL,
  `status` char(1) DEFAULT NULL,
  `modifyDate` datetime);

CREATE TABLE T_AUTHORITY(
	id varchar(20) not null,
	name varchar(60) not null,
	parentId varchar(20) not null,
	action varchar(256) not null,
	dependents varchar(1024),
	remark varchar(1024)
);

CREATE TABLE T_USER_ROLE(
	userId varchar(20) not null,
	roleId varchar(20) not null
);

CREATE TABLE T_ROLE_AUTHORITY(
	roleId varchar(20) not null,
	authorityId varchar(20) not null
);

CREATE TABLE T_MENU(
	id varchar(20) not null,
	name varchar(60) not null,
	parentId varchar(20) not null,
	url varchar(128) not null,
	icon varchar(128)
);

CREATE TABLE TBL_FUNCTION(
	id varchar(20) not null,
	name varchar(60) not null,
	MENUID varchar(20) not null
);