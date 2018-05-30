TRUNCATE TABLE T_USER;
insert into T_USER(id, username, password, fullname, email, weichat, status, modifyDate)
      value('01', 'admin', 'admin', 'admin', '', '', '1', now());
insert into T_USER(id, username, password, fullname, email, weichat, status, modifyDate)
      value('02', 'mfq', 'mfq', '毛发启', '', '', '1', now());
insert into T_USER(id, username, password, fullname, email, weichat, status, modifyDate)
      value('03', 'ttt', 'ttt', '唐甜甜', '', '', '1', now());

TRUNCATE TABLE T_MENU;
INSERT INTO T_MENU(id, name, parentId, url, icon) VALUES ('01', '日常票据', '0', '', 'fa fa-bookmark');
INSERT INTO T_MENU(id, name, parentId, url, icon) VALUES ('0101', '订单登记', '01', 'order.html', '');
INSERT INTO T_MENU(id, name, parentId, url, icon) VALUES ('0102', '原材料入库', '01', 'material_in_storage.html', '');
INSERT INTO T_MENU(id, name, parentId, url, icon) VALUES ('0103', '原材料出库', '01', 'material_out_storage.html', '');
INSERT INTO T_MENU(id, name, parentId, url, icon) VALUES ('0104', '成品入库', '01', 'product_in_storage.html', '');
INSERT INTO T_MENU(id, name, parentId, url, icon) VALUES ('0105', '成品出库', '01', 'product_out_storage.html', '');
INSERT INTO T_MENU(id, name, parentId, url, icon) VALUES ('0106', '开票', '01', 'invoice.html', '');
INSERT INTO T_MENU(id, name, parentId, url, icon) VALUES ('02', '综合查询', '0', '', 'fa fa-bar-chart-o');
INSERT INTO T_MENU(id, name, parentId, url, icon) VALUES ('0201', '应收', '02', 'receivable.html', '');
INSERT INTO T_MENU(id, name, parentId, url, icon) VALUES ('0202', '应付', '02', 'payment.html', '');
INSERT INTO T_MENU(id, name, parentId, url, icon) VALUES ('03', '账户交易', '0', 'account.html', 'fa fa-money');
INSERT INTO T_MENU(id, name, parentId, url, icon) VALUES ('04', '业务数据', '0', '', 'fa fa-sitemap');
INSERT INTO T_MENU(id, name, parentId, url, icon) VALUES ('0401', '客户信息', '04', 'customer.html', '');
INSERT INTO T_MENU(id, name, parentId, url, icon) VALUES ('0402', '供应商信息', '04', 'supplier.html', '');
INSERT INTO T_MENU(id, name, parentId, url, icon) VALUES ('0403', '产品信息', '04', 'product.html', '');
INSERT INTO T_MENU(id, name, parentId, url, icon) VALUES ('0404', '原材料信息', '04', 'material.html', '');
INSERT INTO T_MENU(id, name, parentId, url, icon) VALUES ('05', '系统管理', '0', '', 'fa fa-cog');
INSERT INTO T_MENU(id, name, parentId, url, icon) VALUES ('0501', '用户管理', '05', 'user.html', '');
INSERT INTO T_MENU(id, name, parentId, url, icon) VALUES ('0502', '角色管理', '05', 'role.html', '');
INSERT INTO T_MENU(id, name, parentId, url, icon) VALUES ('0503', '日志查询', '05', 'operation_log.html', '');

     
TRUNCATE TABLE T_AUTHORITY;
INSERT INTO T_AUTHORITY(id, name, parentId, action, dependents, remark) VALUES ('01', '日常票据', '0', '', '', '');
INSERT INTO T_AUTHORITY(id, name, parentId, action, dependents, remark) VALUES ('0101', '订单登记', '01', 'order/loadorder.do,order/loadallorder.do,order/findById.do,order/findbyidentify.do,order/finddetailbyid.do,,order/save.do', '', '拥有订单管理权限，可以查看，录入，修改和删除订单');
INSERT INTO T_AUTHORITY(id, name, parentId, action, dependents, remark) VALUES ('0102', '原材料入库', '01', 'materialin/loadmaterialin.do,materialin/findById.do,materialin/save.do,materialin/delete.do,materialin/importmaterialin.do,materialin/exportmaterialin.do', '', '拥有原材料入库权限，可以查看，录入，修改和删除原材料入库单');
INSERT INTO T_AUTHORITY(id, name, parentId, action, dependents, remark) VALUES ('0103', '原材料出库', '01', 'materialout/findById.do,materialout/save.do,materialout/return.do,materialout/loadmaterialout.do,materialout/delete.do,materialout/exportmaterialout.do,materialstorage/loadmaterialstorage.do', '', '拥有原材料出库权限，可以查看，录入，修改和删除原材料出库单');
INSERT INTO T_AUTHORITY(id, name, parentId, action, dependents, remark) VALUES ('0104', '成品入库', '01', 'pdtin/loadstorage.do,pdtin/save.do,pdtin/edit.do,pdtin/delete.do,pdtin/importstorage.do,pdtin/exportstorage.do,pdtin/loadstatistics.do', '', '拥有入库单权限，可以查看，录入，修改和删除入库单');
INSERT INTO T_AUTHORITY(id, name, parentId, action, dependents, remark) VALUES ('0105', '成品出库', '01', 'deliver/loaddeliver.do,deliver/edit.do,deliver/save.do,deliver/findbyorder.do,deliver/loadbycust.do,deliver/delete.do,deliver/importdeliver.do,deliver/exportdeliver.do', '', '拥有发货单权限，可以查看，录入，修改和删除发货单');
INSERT INTO T_AUTHORITY(id, name, parentId, action, dependents, remark) VALUES ('0106', '开票', '01', 'invoice/loadinvoice.do,invoice/save.do,invoice/edit.do,invoice/delete.do,invoice/importinvoice.do,invoice/exportinvoice.do', '', '拥有开票权限，可以查看，录入，修改和删除入开票信息');
INSERT INTO T_AUTHORITY(id, name, parentId, action, dependents, remark) VALUES ('02', '综合查询', '0', '', '', '');
INSERT INTO T_AUTHORITY(id, name, parentId, action, dependents, remark) VALUES ('0201', '应收', '02', 'accountrpt/statistics.do,accountrpt/statisticsByCustomer.do', '', '拥有应收权限，可以查看应收情况');
INSERT INTO T_AUTHORITY(id, name, parentId, action, dependents, remark) VALUES ('0202', '应付', '02', 'accountpmt/statistics.do,accountpmt/statisticsBySupplier.do', '', '拥有应付权限，可以查看应付情况');
INSERT INTO T_AUTHORITY(id, name, parentId, action, dependents, remark) VALUES ('03', '账户交易', '0', 'account/query.do,account/querythird.do,account/queryByNo.do,account/save.do,account/delete.do,transaction/findbyid.do,transaction/save.do,transaction/loadtransaction.do,transaction/findbyorder.do', '', '拥有账户交易权限，可以维护银行账户，可以录入交易明细');
INSERT INTO T_AUTHORITY(id, name, parentId, action, dependents, remark) VALUES ('04', '业务数据', '0', '', '', '');
INSERT INTO T_AUTHORITY(id, name, parentId, action, dependents, remark) VALUES ('0401', '客户信息', '04', 'cust/loadcust.do,cust/loadallcust.do,cust/save.do,cust/edit.do,cust/delete.do,cust/importcustomer.do,cust/findbyname.do,cust/exportcustomer.do', '', '拥有客户信息权限，可以查看，录入，修改和删除客户信息');
INSERT INTO T_AUTHORITY(id, name, parentId, action, dependents, remark) VALUES ('0402', '供应商信息', '04', 'supp/loadsupp.do,supp/save.do,supp/edit.do,supp/delete.do,supp/importsupplier.do,supp/exportsupplier.do', '', '拥供应商信息权限，可以查看，录入，修改和删除供应商信息');
INSERT INTO T_AUTHORITY(id, name, parentId, action, dependents, remark) VALUES ('0403', '产品信息', '04', 'pdt/loadpdt.do,pdt/loadpdtbyorder.do,pdt/save.do,pdt/loadallpdt.do,pdt/findbycode.do,pdt/edit.do,pdt/delete.do,pdt/importproduct.do,pdt/exportproduct.do', '', '拥有产品信息权限，可以查看，录入，修改和删除产品信息');
INSERT INTO T_AUTHORITY(id, name, parentId, action, dependents, remark) VALUES ('0404', '原材料信息', '04', 'mtl/loadmtl.do,mtl/save.do,mtl/edit.do,mtl/delete.do,mtl/importmaterial.do,mtl/exportmaterial.do', '', '拥有原材料信息权限，可以查看，录入，修改和删除原材料信息');
INSERT INTO T_AUTHORITY(id, name, parentId, action, dependents, remark) VALUES ('05', '系统管理', '0', '', '', '');
INSERT INTO T_AUTHORITY(id, name, parentId, action, dependents, remark) VALUES ('0501', '用户管理', '05', 'user/loaduser.do,user/loadRoses.do,user/save.do,user/edit.do,user/delete.do', '', '拥有用户管理权限，可以查看，新增，修改和删除用户信息，并为其分配角色');
INSERT INTO T_AUTHORITY(id, name, parentId, action, dependents, remark) VALUES ('0502', '角色管理', '05', 'role/getAllRoles.do,role/allAuthority.do,role/saveAuthority.do,role/saveRole.do,role/delete.do', '', '拥有角色管理权限，可以查看，新增，修改和删除角色信息，并为其分配权限');
INSERT INTO T_AUTHORITY(id, name, parentId, action, dependents, remark) VALUES ('0503', '日志查询', '05', '', '', '');

TRUNCATE TABLE T_ROLE;
insert into T_ROLE(id, name, remark, status, modifyDate)
     values('01', '系统管理员', '系统管理员角色，主要职责是管理系统用户和对用户设置角色，为角色分配权限', '1', sysdate());
insert into T_ROLE(id, name, remark, status, modifyDate)
     values('02', '业务管理员', '业务管理员角色，是整个系统业务功能模块的管理员，能够访问系统的所有业务模块（不包含系统管理模块）', '1', sysdate());

TRUNCATE TABLE t_user_role;
INSERT INTO `t_user_role` VALUES ('02', '01');
INSERT INTO `t_user_role` VALUES ('02', '02');
INSERT INTO `t_user_role` VALUES ('03', '01');
INSERT INTO `t_user_role` VALUES ('03', '02');
INSERT INTO `t_user_role` VALUES ('01', '01');

TRUNCATE TABLE t_role_authority;
INSERT INTO `t_role_authority` VALUES ('01', '05');
INSERT INTO `t_role_authority` VALUES ('01', '0501');
INSERT INTO `t_role_authority` VALUES ('01', '0502');
INSERT INTO `t_role_authority` VALUES ('01', '0503');
INSERT INTO `t_role_authority` VALUES ('02', '01');
INSERT INTO `t_role_authority` VALUES ('02', '0101');
INSERT INTO `t_role_authority` VALUES ('02', '0102');
INSERT INTO `t_role_authority` VALUES ('02', '0103');
INSERT INTO `t_role_authority` VALUES ('02', '0104');
INSERT INTO `t_role_authority` VALUES ('02', '0105');
INSERT INTO `t_role_authority` VALUES ('02', '0106');
INSERT INTO `t_role_authority` VALUES ('02', '02');
INSERT INTO `t_role_authority` VALUES ('02', '0201');
INSERT INTO `t_role_authority` VALUES ('02', '0202');
INSERT INTO `t_role_authority` VALUES ('02', '03');
INSERT INTO `t_role_authority` VALUES ('02', '04');
INSERT INTO `t_role_authority` VALUES ('02', '0401');
INSERT INTO `t_role_authority` VALUES ('02', '0402');
INSERT INTO `t_role_authority` VALUES ('02', '0403');
INSERT INTO `t_role_authority` VALUES ('02', '0404');