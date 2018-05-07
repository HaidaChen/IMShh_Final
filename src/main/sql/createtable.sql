CREATE TABLE T_CUSTOMER(
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
    specification varchar(64),
    unit varchar(20),    
    category varchar(20), 
    modifyDate datetime,
    remark varchar(1024),
    status char(1)
);

CREATE TABLE T_ORDER(
    id varchar(20) not null,
    identify varchar(20) not null unique,
    custID varchar(20),
    custName varchar(20),    
    orderDate date,
    amount numeric(10,2),
    state char(1),
    modifyDate datetime,
    remark varchar(1024),
    status char(1)
);

CREATE TABLE T_ORDERDETAIL(
    id varchar(20) not null,
    orderId varchar(20) not null,
    pdtNo varchar(20) not null unique,
    pdtNo varchar(20) not null,
    pdtID varchar(20),
    pdtName varchar(20),   
    content varchar(20), 
    quantity int,
    priceRMB numeric(10,2),
    priceDollar numeric(10,2),
    totlemnt numeric(10,2),
    progress int,    
    
    modifyDate datetime,
    remark varchar(1024),
    status char(1)
);


CREATE TABLE T_PURCHASEPLAN(
    id varchar(20) not null,
    identify varchar(120),
    orderId varchar(20),
    createDate date not null,
    
    money numeric(10,2),
    paid numeric(10,2),  
    
    planStatus char(1),
    
    modifyDate datetime,
    remark varchar(1024),
    status char(1)
);

CREATE TABLE T_PURCHASEDETAIL(
    id varchar(20) not null,
    planId varchar(20) not null,
    materialId varchar(20),
    materialName varchar(20) not null,
    supplierId varchar(20),
    supplierName varchar(20),    
    specification varchar(20),
    unit varchar(20),
    amount int,
    unitPrice numeric(10,2),
    totlemnt numeric(10,2),
    paid numeric(10,2),   
    
    modifyDate datetime,
    status char(1)
);

CREATE TABLE T_DELIVERDETAIL(
    id varchar(20) not null,
    planId varchar(20) not null,
    planDetailId varchar(20) not null,
    materialId varchar(20),
    materialName varchar(20) not null,
    supplierId varchar(20),
    supplierName varchar(20),    
    specification varchar(20),
    unit varchar(20),
    amount int not null,
    deliverDate date not null,
    unitPrice numeric(10,2),
    totlemnt numeric(10,2),
    
    modifyDate datetime,
    remark varchar(1024),
    status char(1)
);

CREATE TABLE T_PAYMENTDETAIL(
    id varchar(20) not null,
    deliverDetailId varchar(20) not null,
    pamentAmount numeric(10,2) not null,
    paymentDate date not null,
    accountId varchar(20),
    
    tranUser  varchar(120), 
    tranBank  varchar(120),
    tranAccountNo varchar(60),
    
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
    
    modifyDate datetime,
    remark varchar(1024),
    status char(1)
);

CREATE TABLE T_ACCOUNT(
    id varchar(20) not null,
    accountNo varchar(60) not null,
    bank varchar(20) not null,
    brachBank varchar(64),
    bankLogo varchar(20),
    accountUser varchar(20) not null,
    accountType char(1) not null,
        
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
    
    orderId varchar(20),
    purchaseId varchar(20),
    
    modifyDate datetime,
    remark varchar(1024),
    status char(1)
);

CREATE TABLE T_STORAGE (
    id varchar(20) not null,
    storageDate date not null,
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
    deliverType varchar(20),
    orderId varchar(20),
    pdtNo varchar(20) not null,
    content varchar(20) not null,
    amount int not null,
    
    modifyDate datetime,
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
	action varchar(128) not null,
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