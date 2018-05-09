insert into T_USER(id, username, password, fullname, email, weichat, status, modifyDate)
      value('01', 'admin', 'admin', 'admin', '', '', '1', now());
insert into T_USER(id, username, password, fullname, email, weichat, status, modifyDate)
      value('02', 'mfq', 'mfq', '毛发启', '', '', '1', now());

insert into T_MENU(id, name, parentId, url, icon)
     values('01', '日常票据', '0', '', 'glyphicon glyphicon-th-list');
insert into T_MENU(id, name, parentId, url, icon)
     values('0101', '订单', '01', 'order/main.do', '');
insert into T_MENU(id, name, parentId, url, icon)
     values('0102', '采购计划', '01', 'purchase/main.do', '');
insert into T_MENU(id, name, parentId, url, icon)
     values('0103', '发货单', '01', 'deliver/main.do', '');
insert into T_MENU(id, name, parentId, url, icon)
     values('0104', '入库单', '01', 'storage/main.do', '');
insert into T_MENU(id, name, parentId, url, icon)
     values('0105', '开票', '01', 'invoice/main.do', '');
insert into T_MENU(id, name, parentId, url, icon)
     values('02', '账务管理', '0', '', 'glyphicon glyphicon-usd');
insert into T_MENU(id, name, parentId, url, icon)
     values('0201', '应收', '02', 'http://localhost:8080/IMShh/jsp/finance/teller/receivable.html', '');
insert into T_MENU(id, name, parentId, url, icon)
     values('0202', '应付', '02', 'http://localhost:8080/IMShh/jsp/finance/teller/payable.html', '');
insert into T_MENU(id, name, parentId, url, icon)
     values('0203', '账户交易', '02', 'http://localhost:8080/IMShh/jsp/finance/account/index.html', '');
insert into T_MENU(id, name, parentId, url, icon)
     values('03', '业务数据', '0', '', 'glyphicon glyphicon-list-alt');  
insert into T_MENU(id, name, parentId, url, icon)
     values('0301', '客户信息', '03', 'cust/main.do', '');
insert into T_MENU(id, name, parentId, url, icon)
     values('0302', '供应商信息', '03', 'supp/main.do', '');
insert into T_MENU(id, name, parentId, url, icon)
     values('0303', '产品信息', '03', 'pdt/main.do', '');
insert into T_MENU(id, name, parentId, url, icon)
     values('0304', '原材料信息', '03', 'mtl/main.do', '');
insert into T_MENU(id, name, parentId, url, icon)
     values('04', '系统管理', '0', '', 'glyphicon glyphicon-cog');  
insert into T_MENU(id, name, parentId, url, icon)
     values('0401', '用户管理', '04', 'user.html', '');
insert into T_MENU(id, name, parentId, url, icon)
     values('0402', '角色管理', '04', 'role.html', '');
     

insert into T_AUTHORITY(id, name, parentId, action, dependents, remark)
     values('01', '日常票据', '0', '', '', '');
insert into T_AUTHORITY(id, name, parentId, action, dependents, remark)
     values('0101', '订单', '01', 'order/main.do', '', '拥有订单管理权限，可以查看，录入，修改和删除订单');
insert into T_AUTHORITY(id, name, parentId, action, dependents, remark)
     values('0102', '采购计划', '01', 'purchase/main.do', '', '拥有采购计划权限，可以查看，录入，修改和删除采购计划');
insert into T_AUTHORITY(id, name, parentId, action, dependents, remark)
     values('0103', '发货单', '01', 'deliver/main.do', '', '拥有发货单权限，可以查看，录入，修改和删除发货单');
insert into T_AUTHORITY(id, name, parentId, action, dependents, remark)
     values('0104', '入库单', '01', 'storage/main.do', '', '拥有入库单权限，可以查看，录入，修改和删除入库单');
insert into T_AUTHORITY(id, name, parentId, action, dependents, remark)
     values('0105', '开票', '01', 'invoice/main.do', '', '拥有开票权限，可以查看，录入，修改和删除入开票信息');
insert into T_AUTHORITY(id, name, parentId, action, dependents, remark)
     values('02', '账务管理', '0', '', '', '');
insert into T_AUTHORITY(id, name, parentId, action, dependents, remark)
     values('0201', '应收', '02', 'http://localhost:8080/IMShh/jsp/finance/teller/receivable.html', '', '拥有应收权限，可以查看应收情况');
insert into T_AUTHORITY(id, name, parentId, action, dependents, remark)
     values('0202', '应付', '02', 'http://localhost:8080/IMShh/jsp/finance/teller/payable.html', '', '拥有应付权限，可以查看应付情况');
insert into T_AUTHORITY(id, name, parentId, action, dependents, remark)
     values('0203', '账户交易', '02', 'http://localhost:8080/IMShh/jsp/finance/account/index.html', '', '拥有账户交易权限，可以维护银行账户，可以录入交易明细');
insert into T_AUTHORITY(id, name, parentId, action, dependents, remark)
     values('03', '业务数据', '0', '', '', '');
insert into T_AUTHORITY(id, name, parentId, action, dependents, remark)
     values('0301', '客户信息', '03', 'cust/main.do', '', '拥有客户信息权限，可以查看，录入，修改和删除客户信息');
insert into T_AUTHORITY(id, name, parentId, action, dependents, remark)
     values('0302', '供应商信息', '03', 'supp/main.do', '', '拥供应商信息权限，可以查看，录入，修改和删除供应商信息');
insert into T_AUTHORITY(id, name, parentId, action, dependents, remark)
     values('0303', '产品信息', '03', 'pdt/main.do', '', '拥有产品信息权限，可以查看，录入，修改和删除产品信息');
insert into T_AUTHORITY(id, name, parentId, action, dependents, remark)
     values('0304', '原材料信息', '03', 'mtl/main.do', '', '拥有原材料信息权限，可以查看，录入，修改和删除原材料信息');
insert into T_AUTHORITY(id, name, parentId, action, dependents, remark)
     values('04', '系统管理', '0', '', '', '');
insert into T_AUTHORITY(id, name, parentId, action, dependents, remark)
     values('0401', '用户管理', '04', 'user/main.do', '', '拥有用户管理权限，可以查看，新增，修改和删除用户信息，并为其分配角色');
insert into T_AUTHORITY(id, name, parentId, action, dependents, remark)
     values('0402', '角色管理', '04', 'role/main.do', '', '拥有角色管理权限，可以查看，新增，修改和删除角色信息，并为其分配权限');

insert into T_ROLE(id, name, remark, status, modifyDate)
     values('01', '系统管理员', '系统管理员角色，主要职责是管理系统用户和对用户设置角色，为角色分配权限', '1', sysdate());
insert into T_ROLE(id, name, remark, status, modifyDate)
     values('02', '业务管理员', '业务管理员角色，是整个系统业务功能模块的管理员，能够访问系统的所有业务模块（不包含系统管理模块）', '1', sysdate());
     
INSERT INTO `t_user_role` VALUES ('02', '01');
INSERT INTO `t_user_role` VALUES ('02', '02');
INSERT INTO `t_user_role` VALUES ('01', '01');
     
INSERT INTO `t_role_authority` VALUES ('01', '04');
INSERT INTO `t_role_authority` VALUES ('01', '0401');
INSERT INTO `t_role_authority` VALUES ('01', '0402');
INSERT INTO `t_role_authority` VALUES ('02', '01');
INSERT INTO `t_role_authority` VALUES ('02', '0101');
INSERT INTO `t_role_authority` VALUES ('02', '0102');
INSERT INTO `t_role_authority` VALUES ('02', '0103');
INSERT INTO `t_role_authority` VALUES ('02', '0104');
INSERT INTO `t_role_authority` VALUES ('02', '0105');
INSERT INTO `t_role_authority` VALUES ('02', '02');
INSERT INTO `t_role_authority` VALUES ('02', '0201');
INSERT INTO `t_role_authority` VALUES ('02', '0202');
INSERT INTO `t_role_authority` VALUES ('02', '0203');
INSERT INTO `t_role_authority` VALUES ('02', '03');
INSERT INTO `t_role_authority` VALUES ('02', '0301');
INSERT INTO `t_role_authority` VALUES ('02', '0302');
INSERT INTO `t_role_authority` VALUES ('02', '0303');
INSERT INTO `t_role_authority` VALUES ('02', '0304');