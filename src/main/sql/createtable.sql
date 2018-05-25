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

CREATE TABLE T_MATERIAL(
    id varchar(20) not null,
    name varchar(20) not null,
    specification1 varchar(64),
    specification2 varchar(64),
    specification3 varchar(64),
    unit varchar(20),
    formula varchar(256),
    category varchar(20), 
    modifyDate datetime,
    remark varchar(1024),
    status char(1)
);

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
    amount int not null,    
    unitPrice numeric(10,2),
    totlemnt numeric(10,2),
    
    modifyDate datetime,
    remark varchar(1024),
    status char(1)
);

CREATE TABLE T_INVOICE(
    id varchar(20) not null,
    invoiceDate date not null,
    customerId varchar(20),
    customerName varchar(20) not null,
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

CREATE TABLE T_ACCOUNT_RECEPTION(
    customerId varchar(20),
    customerName varchar(128) not null,
    month varchar(20) not null,
    reception numeric(10,2),
    payment numeric(10,2)
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

CREATE TABLE T_STORAGE (
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

CREATE TABLE T_STORAGE_DELIVER (
    id varchar(20) not null,
    deliverDate date not null,
    orderIdentify varchar(20),
    customerId varchar(20),
    customerName varchar(64),
    pdtNo varchar(20) not null,
    content varchar(20) not null,
    amount int not null,
    price numeric(10,2),
    totlment numeric(10,2)
    remark varchar(1024),
    status char(1)
);

CREATE TABLE T_USER(
	id varchar(20) not null,
	username varchar(60) not null,
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

CREATE TABLE T_ROLE(
	id varchar(20) not null,
	name varchar(60) not null,
	remark varchar(1024),
	
	status char(1),
	modifyDate datetime
);

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