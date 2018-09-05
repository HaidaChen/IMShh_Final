create or replace view view_product as select odr.pdtNo, 
       odr.orderQuantity, 
       odr.orderAmount, 
       odr.year, 
       (case when production.productionQuantity is null then 0 else production.productionQuantity end) productionQuantity, 
       (case when dlr.deliverQuantity is null then 0 else dlr.deliverQuantity end) deliverQuantity, 
       (case when dlr.deliverAmount is null then 0 else dlr.deliverAmount end) deliverAmount  
  from 
             (select * from (select od.pdtNo, sum(od.quantity) as orderquantity, sum(od.totlmentRMB) as orderamount, year(o.orderDate) as year 
                      from t_orderdetail od, t_order  o 
                     where od.orderIdentify = o.identify 
               group by od.pdtNo, year) o) odr
         left join
             (select pdtNo, sum(amount) productionQuantity, year(storageDate) year
              from t_productin group by pdtNo, year) production
           on odr.pdtNo = production.pdtNo and odr.year = production.year
         left join
             (select year(po.deliverDate) year, po.pdtNo pdtNo, sum(po.amount) deliverQuantity, sum(po.amount*od.priceDollar*o.exchangeRate) deliverAmount 
                from t_productout po, t_order o, t_orderdetail od 
               where po.orderIdentify = o.identify and po.pdtno = od.pdtno and o.identify = od.orderIdentify 
               group by pdtNo, year) dlr
           on odr.pdtNo = dlr.pdtNo and odr.year = dlr.year;
           
           
create or replace view view_material as   
select odr.materialName, 
       odr.specification1, 
	   odr.specification2, 
	   odr.specification3, 
	   orderquantity, 
	   orderamount, 
	   (case when usequantity is null then 0 else usequantity end)usequantity, 
	   odr.year 
  from (select sum(amount) as orderquantity, 
               sum(totlemnt) as orderamount, 
			   year(receivedate) year, 
			   materialName, 
			   specification1, 
			   specification2, 
			   specification3 
		  from t_materialin 
		  group by year, materialName, specification1, specification2, specification3)odr 
  left join (select sum(outamount - returnamount) usequantity, 
                    year(outdate) year, 
					materialName, 
					specification1, 
					specification2, 
					specification3 
			   from t_materialout 
			  group by year, materialName, specification1, specification2, specification3)consume 
	on odr.materialName = consume.materialName 
	and odr.specification1 = consume.specification1 
	and odr.specification2 = consume.specification2 
	and odr.specification3 = consume.specification3 
	and odr.year = consume.year;
	
/**
create or replace view view_payment as
select payable, 
       (case when paid is null then 0 else paid end) paid, 
	   supplierName, 
	   mtlin.month 
  from (select sum(totlemnt) payable, 
               supplierName, 
			   concat(year(receiveDate), '-', month(receiveDate)) month 
	      from t_materialin 
		 group by supplierName, month)mtlin 
   left join (select sum(tranAmount) paid, 
                     tranUser, 
					 concat(year(tranDate), '-', month(tranDate)) month 
				from t_transaction 
			   group by tranUser, month)tran 
			      on mtlin.supplierName = tran.tranUser 
				 and mtlin.month = tran.month;
**/
	

create or replace view view_materialin as
select sum(totlemnt) payable, 
       supplierName, 
	   concat(year(receiveDate), '-', month(receiveDate)) month 
  from t_materialin 
 group by supplierName, month;
 

create or replace view view_transactionout as
select sum(ABS(tranAmount)) paid, 
       tranUser as supplierName, 
       concat(year(tranDate), '-', month(tranDate)) month 
  from t_transaction
 where tranUser is not null 
 group by tranUser, month;
 

create or replace view view_productout as	
select po.orderIdentify, 
       concat(year(deliverDate), '-', month(deliverDate)) as delivermonth, 
	   sum(po.amount * od.priceRMB) as debt 
  from t_productout po, 
	   t_orderDetail od 
 where po.pdtNo = od.pdtNo 
   and po.orderIdentify = od.orderIdentify 
 group by orderIdentify, delivermonth;
 
create or replace view view_transactionin as
select sum(ABS(tranAmount)) reception, 
       orderIdentify, 
	   concat(year(tranDate), '-', month(tranDate)) tranmonth 
  from t_transaction
 where orderIdentify is not null  
 group by orderIdentify, tranmonth;
		 