INSERT INTO `t_authority` VALUES ('01', '日常票据', '0', '', '', '');
INSERT INTO `t_authority` VALUES ('0101', '订单', '01', 'order/loadorder.do,order/loadallorder.do,order/findById.do,order/findbyidentify.do,order/finddetailbyid.do,order/save.do,order/updatestate.do,order/exportorder.do,order/importorder.do', '', '拥有订单管理权限，可以查看，录入，修改和删除订单');
INSERT INTO `t_authority` VALUES ('0102', '采购计划', '01', 'reception/loadreception.do,reception/loadbysupplier.do,reception/edit.do,reception/save.do,reception/delete.do,reception/importreception.do,reception/exportreception.do', '', '拥有采购计划权限，可以查看，录入，修改和删除采购计划');
INSERT INTO `t_authority` VALUES ('0103', '发货单', '01', 'deliver/loaddeliver.do,deliver/findbyorder.do,deliver/edit.do,deliver/save.do,deliver/findbyorder.do,deliver/loadbycust.do,deliver/delete.do,deliver/importdeliver.do,deliver/exportdeliver.do', '', '拥有发货单权限，可以查看，录入，修改和删除发货单');
INSERT INTO `t_authority` VALUES ('0104', '入库单', '01', 'storage/loadstorage.do,storage/save.do,storage/edit.do,storage/delete.do,storage/importstorage.do,storage/exportstorage.do,storage/loadstatistics.do', '', '拥有入库单权限，可以查看，录入，修改和删除入库单');
INSERT INTO `t_authority` VALUES ('0105', '开票', '01', 'invoice/loadinvoice.do,invoice/save.do,invoice/edit.do,invoice/delete.do,invoice/importinvoice.do,invoice/exportinvoice.do', '', '拥有开票权限，可以查看，录入，修改和删除入开票信息');

INSERT INTO `t_authority` VALUES ('02', '账务管理', '0', '', '', '');
INSERT INTO `t_authority` VALUES ('0201', '应收', '02', 'accountrpt/statistics.do,accountrpt/statisticsByCustomer.do', '', '拥有应收权限，可以查看应收情况');
INSERT INTO `t_authority` VALUES ('0202', '应付', '02', 'accountpmt/statistics.do,accountpmt/statisticsBySupplier.do', '', '拥有应付权限，可以查看应付情况');
INSERT INTO `t_authority` VALUES ('0203', '账户交易', '02', 'account/query.do,account/querythird.do,account/queryByNo.do,account/save.do,account/delete.do,transaction/findbyid.do,transaction/loadbyuser.do,transaction/save.do,transaction/loadtransaction.do,transaction/findbyorder.do', '', '拥有账户交易权限，可以维护银行账户，可以录入交易明细');
                                                          
INSERT INTO `t_authority` VALUES ('03', '业务数据', '0', '', '', '');
INSERT INTO `t_authority` VALUES ('0301', '客户信息', '03', 'cust/loadcust.do,cust/loadallcust.do,cust/save.do,cust/edit.do,cust/delete.do,cust/importcustomer.do,cust/findbyname.do,cust/exportcustomer.do', '', '拥有客户信息权限，可以查看，录入，修改和删除客户信息');
INSERT INTO `t_authority` VALUES ('0302', '供应商信息', '03', 'supp/loadsupp.do,supp/save.do,supp/edit.do,supp/delete.do,supp/importsupplier.do,supp/exportsupplier.do', '', '拥供应商信息权限，可以查看，录入，修改和删除供应商信息');
INSERT INTO `t_authority` VALUES ('0303', '产品信息', '03', 'pdt/loadpdt.do,pdt/save.do,pdt/loadallpdt.do,pdt/findbycode.do,pdt/edit.do,pdt/delete.do,pdt/importproduct.do,pdt/exportproduct.do', '', '拥有产品信息权限，可以查看，录入，修改和删除产品信息');
INSERT INTO `t_authority` VALUES ('0304', '原材料信息', '03', 'mtl/loadmtl.do,mtl/loadallmtl.do,mtl/save.do,mtl/edit.do,mtl/delete.do,mtl/importmaterial.do,mtl/exportmaterial.do', '', '拥有原材料信息权限，可以查看，录入，修改和删除原材料信息');
INSERT INTO `t_authority` VALUES ('04', '系统管理', '0', '', '', '');
INSERT INTO `t_authority` VALUES ('0401', '用户管理', '04', 'user/loaduser.do,user/loadRoses.do,user/save.do,user/edit.do,user/delete.do', '', '拥有用户管理权限，可以查看，新增，修改和删除用户信息，并为其分配角色');
INSERT INTO `t_authority` VALUES ('0402', '角色管理', '04', 'role/getAllRoles.do,role/allAuthority.do,role/saveAuthority.do,role/saveRole.do,role/delete.do', '', '拥有角色管理权限，可以查看，新增，修改和删除角色信息，并为其分配权限');
