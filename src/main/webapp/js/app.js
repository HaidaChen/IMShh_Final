$(function(){
	$.ajaxSetup({
		dataFilter:function() {
			if (arguments[0] == 'has no permission'){
				window.top.location.href = getProjectName();
			}else{
				return arguments[0];
			}
		}
	});
});

var App = function () {
	var nthTabs;
	var handleSidebar = function(){
		$("#siderbar-warper").slimScroll({
			height: "100%",
			color: '#54667a'
		});
		var menuContainer = $("#side-menu");	
		$.ajax({url:getProjectName()+"/login/getUserMemu.do?timestamp="+new Date().getTime(),
			success:function(menus){
				$.each(menus, function(index, obj) {
					var item = $("<li>");
					var itemLink = $("<a href='javascript:;' class=''></a>");
					var icon = obj.icon;
					if (icon && icon != '' && icon.indexOf('+') > -1){
						icon = icon.replace(/\+/g, ' ');
					}
					var itemContent = $("<i class='" + icon + "'></i> <span>" + obj.name +"</span>");
					if (obj.url && obj.url != ''){
						itemLink.attr("link", obj.url);
						itemLink.attr("identify", obj.id);
						itemLink.attr("text", obj.name);
					}
					itemLink.append(itemContent);
					item.append(itemLink);
					menuContainer.append(item);
					if (obj.submenu && obj.submenu.length > 0){
						var subcontainer = $("<ul class='nav-list'>");
						$.each(obj.submenu, function(index, submenu){
							var subItem = $("<li><a class='' href='javascript:;' link='"+submenu.url+"' identify='"+submenu.id+"' text='"+submenu.name+"'>" + submenu.name + "</a></li>");
							subcontainer.append(subItem);
						});
						item.append(subcontainer);
						itemLink.append("<span class='arrow'></span>");
						
					}else{
						itemLink.attr("href", obj.url);
					}				
				});
				
			}
		});
	}

	var handleSidebarCollapse = function(){
		var menuContainer = $("#side-menu");
		menuContainer.on("click", "li", function(){
			var child = $(this).find("ul");
			
			var arrow = $(this).find("span.arrow");
			child.toggle();
			arrow.toggleClass("open");
			menuContainer.find("span.arrow").not(arrow).removeClass("open");
			menuContainer.find("ul").not(child).hide();
			
			var link = $(this).children("a").attr("link");
			if(link && link != ''){
				var identify = $(this).children("a").attr("identify");
				var text = $(this).children("a").attr("text");
				nthTabs.addTab({id:identify,title:text,content:link});
			}
		});
		
	}
	
	var handleMoreCondition = function(){
		if ($("#nav_more_condition")){
			$("#nav_more_condition").click(function(){
				$("#panel_more_condition").toggleClass("open");
			});			
		}
	}
	
	var handleHomePage = function(){
		$("#nav_homePage").click(function(){
			Ewin.load({id: 'homePage_edit', title: '设置个人首页', url: 'fragment/homePage_edit.html', callback: function(){
				$.getJSON(getProjectName() + "/login/loadAllPage.do",
						function(result){
							$.each(result, function(index, menu){
								$('#select_home').append('<option value="'+menu.id+'">'+menu.name+'</option>');
							});
							$("#select_home").select2({
								placeholder: "请选择个人主页",
								allowClear: true
							});
							$.getJSON(getProjectName() + "/login/getHomePage.do",
									function(result){
										$('#select_home').val(result.id);
										$("#select_home").select2();
							});
				});
			}});
		});
	}
	
	var handleChangePWD = function(){
		$("#nav_changePWD").click(function(){
			var html = ''+
			'<form id="pwdForm" class="form-horizontal">' +
               '<div class="form-group">' +
                   '<label for="oldPassword" class="col-sm-4 control-label">当前密码：' + '</label>' +
                   '<div class="col-sm-8">' + 
                   	   '<input type="password" class="form-control" id="oldPassword" name="opassword" >' +
                   '</div>' +
               '</div>' +                
               '<div class="form-group">' +
                   '<label for="newPassword" class="col-sm-4 control-label">新的密码：' + '</label>' +
                   '<div class="col-sm-8">' + 
                   	   '<input type="password" class="form-control" id="newPassword" name="npassword">' +
                   '</div>' +
               '</div>' +               
               '<div class="form-group">' +
                   '<label for="repPassword" class="col-sm-4 control-label">密码确认：' + '</label>' +
                   '<div class="col-sm-8">' + 
                   	   '<input type="password" class="form-control" id="repPassword" name="rpassword">' +
                   '</div>' +
               '</div>' +
           '</form>' +
           '<div id="success_changePWD" class="alert alert-success" role="alert" style="display: none">密码修改成功</div>' +
           '<div id="warn_changePWD" class="alert alert-warning" role="alert" style="display: none"></div>';
			
			Ewin.confirm({message: html, closeAuto: false}).on(function(e){
				if (!e){
					return;
				}
				
				$("#pwdForm").bootstrapValidator({
					fields: {
			        	opassword : {validators: {notEmpty : {}}},
			        	npassword : {validators: {notEmpty : {}, identical: {field: 'rpassword'}}},
			        	rpassword : {validators: {identical: {field: 'npassword'}}}
			        }
				});
				
				var bootstrapValidator = $("#pwdForm").data('bootstrapValidator');
				bootstrapValidator.validate();
				if(!bootstrapValidator.isValid()){
					return;
				}	
				$("#pwdForm").ajaxSubmit({
					type: "post",
					url:getProjectName()+"/login/changePWD.do", 
					success:function(result){
						if (result == -1){
							$("#warn_changePWD").html("密码修改失败！原因：当前密码错误");
							$("#warn_changePWD").show();
							$("#success_changePWD").hide();
						}
						if (result == 1){
							$("#warn_changePWD").html("");
							$("#warn_changePWD").hide();
							$("#success_changePWD").show();		
						}
					},
					error:function(){
						window.location.href = getProjectName() + "/login.html";
					}
					
				});
				
				return false;
			});
		});
	}
	
	var handleSignOut = function(){
		$("#nav_signOut").click(function(){
			Ewin.confirm({message: "确定要退出系统吗？"}).on(function(e){
				if (!e){
					return;
				}
				
				$.ajax({url: getProjectName()+"/login/logout.do", success: function(){
					window.location.href = getProjectName() + "/login.html";
				}});
			});
		})
	}
	
	
	/*-----------------------------------------------------------------------------------*/
	/*	处理登录模块
	/*-----------------------------------------------------------------------------------*/	
	var initLoginModule = function(){
		$("#loginform").bootstrapValidator({
			fields: {
	        	userName : {validators: {notEmpty : {}}},
	        	password : {validators: {notEmpty : {}}}
	        }
		});
		
		var options = {
			url: getProjectName()+"/login/login.do",
			type: 'post',
			success: function(result){
				$("#loginform .alert").remove();
				if (result == 1) {
					window.location.href = getProjectName() + "/page/frame.html";
				} else if (result == 0){
					$("#loginform").append('<div class="alert alert-warning" role="alert" style="margin-top:10px">不存在该用户</div>');
				} else{
					$("#loginform").append('<div class="alert alert-warning" role="alert" style="margin-top:10px">用户名密码不正确</div>');
				}
			}
		};
		
		$("#btn_login").click(function(){
			$("#loginform").bootstrapValidator('validate');
			if (!$("#loginform").data('bootstrapValidator').isValid()) {	        
				return;
			}
			$("#loginform").ajaxSubmit(options);
		});
	}

	/*-----------------------------------------------------------------------------------*/
	/*	加载框架页面
	/*-----------------------------------------------------------------------------------*/	
	var initFrameModule = function(){
		nthTabs = $("#editor-tabs").nthTabs();
		$.getJSON(getProjectName() + "/login/getHomePage.do?timestamp="+new Date().getTime(),
				function(result){
					if (result == "")
						return;
					nthTabs.addTab({
			            id:result.id,
			            title:result.name,
			            active:true,
			            allowClose:false,
			            content:result.url
			        });
		});
	}
	
	
	/*-----------------------------------------------------------------------------------*/
	/*	初始化订单合同录入模块
	/*-----------------------------------------------------------------------------------*/	
	var initInputOrder = function(){
		var bill;
		var initBill = function(){
			$.ajaxSettings.async = false;
			$.getJSON("../templater/bill/dght.json", "", function(data){
        		bill = $('#orderBill').eBill(data);	
			});
			$.ajaxSettings.async = true;
		}
		
		var editBill = function(){
			$('#btn_save').click(function(){
				if ($("input[name='id']").val() == ''){
					bill.commit(getProjectName()+'/order/newOrder.do', '订购合同新增成功');
				}else{
					bill.commit(getProjectName()+'/order/updateOrder.do', '订购合同修改成功', false);
				}
        	});
			
			$('#btn_reset').click(function(){
				bill.resetBill();
			});
			
			$('#btn_print').click(function(){
				$('#panel_orderBill').print({
					globalStyles: true,
					mediaPrint: false,
					stylesheet: null,
					noPrintSelector: ".no-print",
					iframe: true,
					append: null,
					manuallyCopyFormValues: true,
					deferred: $.Deferred()
				});
			});
		}
		
		return {
			init: function(){
				initBill();
				editBill();
				return bill;
			}
		}
	}
	
	/*-----------------------------------------------------------------------------------*/
	/*	初始化订单合同列表模块
	/*-----------------------------------------------------------------------------------*/	
	var initOrderList = function(){
		var initOption = function(){
			$.getJSON(
				getProjectName() + "/cust/loadallcust.do",
				function(result){
					$.each(result, function(index, cust){
						$('#filter_customer').append('<option value="'+cust.id+'">'+cust.name+'</option>');
					});
					$("#filter_customer").select2({
						placeholder: "请选择订购客户",
						allowClear: true
					});
				}
			);
		}
		
		var loadOrderTable = function(){
			$('#tbl_order').bootstrapTable({
				url: getProjectName() + "/order/getPageResult.do",
				method: "get",
				pagination: true,
				sidePagination: "server", 
				clickToSelect: true,
				columns: [{
		            field: 'identify',
		            title: '订单号',
		            formatter: function(value, row, index){
		        		return '<a opt="link" rowid="'+row.id+'">'+value+'</a>';
		        	}
		        }, {
		            field: 'orderDate',
		            title: '订购日期'
		        }, {
		            field: 'orderType',
		            title: '订单类型',
		            formatter: function(value, row, index){
		            	if (value == '1')
		            		return '海外订单';
		            	if (value == '2')
		            		return '国内订单';
		            }
		        }, {
		            field: 'customer.name',
		            title: '订购客户'
		        }, {
		            field: 'totalAmount',
		            title: '合计金额'
		        }, {
		        	field: 'amountRMB',
		        	title: '人民币金额'
		        }, {
		        	field: '',
		        	title: '操作',
		        	formatter: function(value, row, index){
		        		return '<a opt="update" rowid="'+row.id+'">修改</a>&nbsp;<a opt="delete" rowid="'+row.id+'">删除</a>';
		        	}
		        }],
		        queryParams: function(params){
		        	return {
		                pageSize: params.limit,
		                pageOffset: params.offset,                    
		                identify: $("#filter_identify").val(),
		                orderType: $("#filter_orderType").val(),
		                customerId: $("#filter_customer").val(),
		                startDate: $("#filter_startDate").val(),
		                endDate: $("#filter_endDate").val()
		            }
		        }
			});
		}
		
		var doQuery = function(){
			$('#btn_search').click(function(){
				$('#tbl_order').bootstrapTable('refresh', {url: getProjectName() + "/order/getPageResult.do"});
			});
		}
		
		var doAdd = function(){
			$('#btn_add').click(function(){
				var nthTabs = window.parent.getTabs();
		        nthTabs.addTab({
		        	id:'0101',
		            title:'订单录入',
		            active:true,
		            allowClose:true,
		            content:'order_bill.html',
		        });
			});
		}
		
		var doEditOrder = function(){
			$('#tbl_order').on('click', 'a', function(){
				var opt = $(this).attr('opt');
				var id = $(this).attr('rowId');
				if (opt == 'update' || opt == 'link'){
					var nthTabs = window.parent.getTabs();
			        nthTabs.addTab({
			        	id:'0101',
			            title:'订单录入',
			            active:true,
			            allowClose:true,
			            content:'order_bill.html',
			        });
			        
			        setTimeout(function(){
			        	window.parent.$('#if0101')[0].contentWindow.fillBill(id);
			        }, 1000);
				}
				if (opt == 'delete'){
					Ewin.confirm({message: "确定要删除该订购合同吗？"}).on(function(e){
						if (!e){
							return;
						}
						if (window.parent.$('#if0101')[0]){
							var billId = window.parent.$('#if0101')[0].contentWindow.getBillId();
							if (billId == id){
								Ewin.alert("当前待删除订购合同正在修改中，无法删除");
								return;
							}
						}
						$.ajax({
							url: getProjectName() + "/order/deleteOrder.do?id="+id,
							success: function(){
								Ewin.toast('订购合同删除成功');
								$('#tbl_order').bootstrapTable('refresh', {url: getProjectName() + "/order/getPageResult.do"});
							}
						});
					});
				}
			});
		}
		
		var doExport = function(){
            $('#btn_export').click(function(){
            	var identify = $("#filter_identify").val();
                var orderType = $("#filter_orderType").val();
                var customerId = $("#filter_customer").val();
                var startDate = $("#filter_startDate").val();
                var endDate = $("#filter_endDate").val();
            	var url = getProjectName() + "/order/exportOrder.do?identify=" + identify + "&orderType=" + orderType + "&customerId=" + customerId + "&startDate=" + startDate + "&endDate=" + endDate;
            	window.open(url);
            });
		}
		
		return {
			init: function(){
				initOption();
				loadOrderTable();
				doQuery();
				doAdd();
				doEditOrder();
				doExport();
			}
		}
	}
	
	
	/*-----------------------------------------------------------------------------------*/
	/*	初始化成品订购明细模块
	/*-----------------------------------------------------------------------------------*/	
	var initOrderProductList = function(){
		var initFilter = function(){
			$.getJSON(
				getProjectName() + "/pdt/query.do",
				function(result){
					$.each(result, function(index, pdt){
						$('#filter_product').append('<option value="'+pdt.id+'">'+pdt.code+'</option>');
					});
					$("#filter_product").select2({
						placeholder: "请选择成品",
						allowClear: true
					});
				}
			);
			
		}
		
		var loadOrderProductTable = function(){
			$('#tbl_orderProduct').bootstrapTable({
				url: getProjectName() + "/order/getOrderProductPageResult.do",
				method: "get",
				pagination: true,
				sidePagination: "server", 
				clickToSelect: true,
				columns: [{
		            field: 'product.code',
		            title: '成品货号'
		        }, {
		            field: 'product.model',
		            title: '成品含量'
		        }, {
		            field: 'orderQuantity',
		            title: '订购数量'
		        }, {
		            field: 'orderAmount',
		            title: '订购金额'
		        }, {
		            field: 'inQuantity',
		            title: '生产数量'
		        }, {
		        	field: 'outQuantity',
		        	title: '发货数量'
		        }, {
		        	field: 'product.storage',
		        	title: '当前库存'
		        }],
		        queryParams: function(params){
		        	return {
		                pageSize: params.limit,
		                pageOffset: params.offset,                    
		                productId: $("#filter_product").val(),
		                startDate: $("#filter_startDate").val(),
		                endDate: $("#filter_endDate").val()
		            }
		        }
			});
		} 
		
		var doQuery = function(){
			$('#btn_search').click(function(){
				$('#tbl_orderProduct').bootstrapTable('refresh', {url: getProjectName() + "/order/getOrderProductPageResult.do"});
			});
		}
		
		var doExport = function(){
            $('#btn_export').click(function(){
            	var productId = $("#filter_product").val();
                var startDate = $("#filter_startDate").val();
                var endDate = $("#filter_endDate").val();
            	var url = getProjectName() + "/order/exportProdcutOrder.do?productId=" + productId + "&startDate=" + startDate + "&endDate=" + endDate;
            	window.open(url);
            });
		}
		
		return {
			init: function(){
				initFilter();
				loadOrderProductTable();
				doQuery();
				doExport();
			}
		}
	}
	
	/*-----------------------------------------------------------------------------------*/
	/*	初始化原材料品类模块
	/*-----------------------------------------------------------------------------------*/	
	var initMaterialCategory = function(){
		var loadCategoryTree = function(){
			$("#panel_category .panel-body").slimScroll({
				height: "490px",
				color: '#54667a'
			});
			if (window.localStorage){
				localStorage.removeItem('jstree');
			}
			$('#category_tree').data('jstree', false).empty().jstree({
				plugins: ['types', 'state', 'contextmenu', 'wholerow'],
				'types': {
					'default': {
						'icon': false
					}
				},
				"contextmenu":{
			    	select_node:false,
			    	show_at_node:true,
			    	items:{
		                "新增分类":{  
		                    "label":"新增分类",  
		                    "icon"				: "fa fa-plus",
		                    "action": function(data){
		                    	var inst = $.jstree.reference(data.reference),
								obj = inst.get_node(data.reference);
		                    	addCategory(obj.id, obj.text);
		                    }  
		                },
		                "修改分类":{
		                	"label"				: "修改分类",
							"icon"				: "fa fa-edit",
							"action"			: function (data) {
								var inst = $.jstree.reference(data.reference),
								obj = inst.get_node(data.reference);
								updateCategory(obj);
							},
							'_disabled'         : function (data) {
								var inst = $.jstree.reference(data.reference),
								obj = inst.get_node(data.reference);
								if (obj.id == '0'){
									return true;
								}
								return false;
							}
		                },
		                "删除分类":{
		                	"label"				: "删除分类",
							"icon"				:"fa fa-remove",
							"action"			: function (data) {
								var inst = $.jstree.reference(data.reference),
								obj = inst.get_node(data.reference);
								deleteCategory(obj.id);
							},
							'_disabled'         : function (data) {
								var inst = $.jstree.reference(data.reference),
								obj = inst.get_node(data.reference);
								if (obj.id == '0'){
									return true;
								}
								return false;
							}
		                }
			    	}
			      },

				'core': {
					'data': {
						'url': getProjectName()+"/mtlCtgy/getJSTree.do"
					}
				}
			}).bind('select_node.jstree', function(e, data){
				var selectedNode = data.instance.get_node(data.selected[0]);
				var code = selectedNode['a_attr'].ctgCode;
				if (!code){
					code = '';
				}
				filterWin.modal.find('#filter_ctgCode').val(code);
				queryMaterial();
			});
			
		}
		
		var initCategoryValidator = function(_code){
			$("#categoryForm").bootstrapValidator({
				fields: {
					code : {validators: {notEmpty : {}, 
						callback: {
							message: '编码已存在，请重新输入',
							callback: function(value, validator){
								if (value == '' || value == _code){
									return true;
								}else{
									var res;
									$.ajax({
										url: getProjectName()+"/mtlCtgy/validateCategory.do",
										data: {code : $("#input_code").val()},
										async:false, 
										success: function(result){
											res = result.valid;
										}
									});
									return res;
								}
							}
						}}
					},
		        	name : {validators: {notEmpty : {}}}
		        }
			});
		}
		
		var addCategory = function(pid, ptext){
			Ewin.load({id: 'mtl_ctg_edit', title: '新的分类', url: 'fragment/mtl_ctg_edit.html', callback: function(){
				fillForm($("#categoryForm"), {parentId: pid, parent: ptext});
				
				initCategoryValidator();
				$("#btn_save").click(function(){
					var bootstrapValidator = $("#categoryForm").data('bootstrapValidator');
					bootstrapValidator.validate();
					if(!bootstrapValidator.isValid()){
						return;
					}	
					$("#categoryForm").ajaxSubmit({
						type: "post",
						url:getProjectName()+"/mtlCtgy/addCategory.do", 
						dataType: "text",
						success:function(result){
							var ref = $('#category_tree').jstree(true);
							ref.refresh();
							clearForm($("#categoryForm"), ['parentId', 'parent']);
							Ewin.toast('新的分类保存成功');
						}
					});
				});
				$("#btn_close").click(function(){
					$('#mtl_ctg_edit').modal('hide');
				});
			}});
		}
		
		var updateCategory = function(node){
			var ref = $('#category_tree').jstree(true);
			Ewin.load({id: 'mtl_ctg_edit', title: '修改分类', url: 'fragment/mtl_ctg_edit.html', callback: function(){
				fillForm($("#categoryForm"), {id: node.id, 
					                    parentId: node.parent, 
					                      parent: ref.get_node(node.parent).text,
					                        code: node["a_attr"].ctgCode,
					                        name: node["a_attr"].ctgName,
					                      remark: node.a_attr.title});
				var _code = $("#input_code").val();
				initCategoryValidator(_code);
				
				$("#btn_save").click(function(){
					var bootstrapValidator = $("#categoryForm").data('bootstrapValidator');
					bootstrapValidator.validate();
					if(!bootstrapValidator.isValid()){
						return;
					}	
					$("#categoryForm").ajaxSubmit({
						type: "post",
						url:getProjectName()+"/mtlCtgy/updateCategory.do", 
						dataType: "text",
						success:function(result){
							var ref = $('#category_tree').jstree(true);
							ref.refresh();
							Ewin.toast('分类修改成功');
						}
					});
				});
				$("#btn_close").click(function(){
					$('#mtl_ctg_edit').modal('hide');
				});
			}});
		}
		
		var deleteCategory = function(id){
			Ewin.confirm({message: "确定要删除选中的分类吗？"}).on(function(e){
				if (!e){
					return;
				}
				
				$.ajax({url: getProjectName()+"/mtlCtgy/deleteCategory.do?id="+id, success: function(){
					Ewin.toast('分类删除成功');
					var ref = $('#category_tree').jstree(true);
					ref.refresh();
				}});
			});
		}
		
		var loadMaterialTable = function(){
			$("#tbl_material").bootstrapTable({
				url: getProjectName() + "/mtl/getPageResult.do",
				method: "get",
				pagination: true,
				sidePagination: "server", 
				clickToSelect: true,
				columns: [{
		            field: 'name',
		            title: '品名'
		        }, {
		            field: 'specification',
		            title: '规格'
		        }, {
		            field: 'ctg.name',
		            title: '分类'
		        }, {
		            field: 'storage',
		            title: '库存'
		        }, {
		            field: 'unit',
		            title: '单位'
		        }, {
		        	field: 'remark',
		        	title: '备注'
		        }, {
		        	field: '',
		        	title: '操作',
		        	formatter: function(value, row, index){
		        		return '<a opt="update" rowid="'+row.id+'">修改</a>&nbsp;<a opt="delete" rowid="'+row.id+'">删除</a>';
		        	}
		        }],
		        queryParams: function(params){
		        	return {
		                pageSize: params.limit,
		                pageOffset: params.offset,                    
		                name: $("#filter_name").val(),
		                ctgCode: $("#filter_ctgCode").val(),
		                specification: $("#filter_spec").val(),
		                lowerStorage: $("#filter_lStorage").val(),
		                upperStorage: $("#filter_uStorage").val(),
		                remark: $("#filter_remark").val()
		            }
		        }
			});
		}
		
		var initMaterialValidator = function(){
			$("#materialForm").bootstrapValidator({
				fields: {
					name : {
						validators: {
							notEmpty : {},
							callback: {
								message: '原材料已存在(名称+规格+分类唯一则唯一)，请重新输入'
							}
					}},
					'ctg.id' : {validators: {
						notEmpty : {}}},
					storage : {validators: {regexp : { 
						regexp: /^(-)?\d+(\.\d+)?$/,
                        message: '请输入数字类型的库存'}}}
		        }
			});
		}
		
		var loadCtg4Select = function(current){
			$.getJSON(
				getProjectName() + "/mtlCtgy/query.do",
				function(result){
					$.each(result, function(index, ctg){
						var indent = '';
						var num = ctg.code.length - 2
						for (var i = 0; i < num; i++){indent+= '&nbsp;&nbsp;';}
						if (current && current == ctg.id){
							$("#select_ctg").append("<option value='"+ctg.id+"' selected='selected'>"+indent+ctg.code+" "+ctg.name+"</option>");
						}else{
							$("#select_ctg").append("<option value='"+ctg.id+"'>"+indent+ctg.code+" "+ctg.name+"</option>");	
						}						
					});
					$("#select_ctg").select2({
						placeholder: "请选择材料分类",
						allowClear: true
					});
				}
			);
		} 
		
		var filterWin;
		var initMaterialFunction = function(){
			filterWin = Ewin.load({id: 'mtl_filter', title: '更多查询', url: 'fragment/mtl_filter.html', rmvWin: false, initShow: false, callback: function(){
				$("#btn_query").click(function(){
					queryMaterial();
					$('#mtl_filter').modal('hide');
				});
			}});
			
			$('#btn_more').click(function(){
				filterWin.modal.modal('show');
			});
			
			$('#btn_search').click(function(){
				queryMaterial();
			});
			
			$('#btn_add').click(function(){					
				Ewin.load({id: 'mtl_edit', title: '新的原材料', url: 'fragment/mtl_edit.html', callback: function(){
					initMaterialValidator();
					loadCtg4Select();		
					
					$("#btn_save").click(function(){
						var bootstrapValidator = $("#materialForm").data('bootstrapValidator');
						bootstrapValidator.validate();
						if(!bootstrapValidator.isValid()){
							return;
						}
						
						var res;
						$.ajax({
							url: getProjectName()+"/mtl/validateUnique.do",
							data: {name : $("#input_name").val(),
								   specification: $('#input_spe').val(),
								   category: $('#select_ctg').val()
							},
							async:false, 
							success: function(result){
								res = result.valid;
							}
						});
						if (!res){
							$('#materialForm').append('<div class="alert alert-danger" role="alert">原材料已经存在，请修改材料名称或者规格或者分类</div>');
							return;
						}
						
						$("#materialForm").ajaxSubmit({
							type: "post",
							url:getProjectName()+"/mtl/addMaterial.do", 
							dataType: "text",
							success:function(result){
								clearForm($("#materialForm"));
								Ewin.toast('新的原材料保存成功');
								queryMaterial();
							}
						});
					});
				}});
			});
			
			initEditMaterial();
			
			$('#btn_import').click(function(){
				ImportData.show({url: getProjectName() + "/mtl/importMaterial.do",
					templaterName: getProjectName() + "/templater/import/原材料列表.xls",
					callback: function(){
						$("#tbl_material").bootstrapTable("refresh", {url: getProjectName() + "/mtl/getPageResult.do", cache: false});
					}
				});
			});
			
			$('#btn_export').click(function(){
				var param = 'name=' + $("#filter_name").val()
				          + '&ctgCode='+ $("#filter_ctgCode").val()
				          + '&specification=' + $("#filter_spec").val()
				          + '&lowerStorage=' + $("#filter_lStorage").val()
				          + '&upperStorage=' + $("#filter_uStorage").val()
				          + '&remark=' + $("#filter_remark").val();
				window.open(getProjectName() + "/mtl/exportMaterial.do?"+param);
			});
		}
		
		var queryMaterial = function(){
			createFilterTip({
				assWin: $('#mtl_filter'), 
				items: [{assId: 'filter_spec', label: '材料规格', rule: '包含'}, 
					    {assId: 'filter_lStorage', label: '库存', rule: '大于'},
				        {assId: 'filter_uStorage', label: '库存', rule: '小于'},
				        {assId: 'filter_remark', label: '备注', rule: '包含'}],
		        changeCall: function(){
		        	$("#tbl_material").bootstrapTable("refresh", {url: getProjectName() + "/mtl/getPageResult.do", cache: false});
	        }});
			$("#tbl_material").bootstrapTable("refresh", {url: getProjectName() + "/mtl/getPageResult.do", cache: false});
		}
		
		var initEditMaterial = function(id){
			$('#tbl_material').on('click', 'a', function(){
				var opt = $(this).attr('opt');
				var id = $(this).attr('rowId');
				if (opt == 'update'){
					Ewin.load({id: 'mtl_edit', title: '修改原材料', url: 'fragment/mtl_edit.html', callback: function(){
						var _name; 
						var _spec;
						var _ctg;
						$.ajax({
							url: getProjectName()+"/mtl/getMaterialById.do?id="+id, 
							success: function(result){
								_name = result.name; 
								_spec = result.specification;
								_ctg = result.ctg.id;
								initMaterialValidator();
								loadCtg4Select(result.ctg.id);	
								fillForm($("#materialForm"), {
									id: result.id, 
									name: result.name, 
									specification: result.specification, 
									'ctg.id': result.ctg.id, 
									unit: result.unit, 
									remark: result.remark});
							}
						});
						
						$("#btn_save").click(function(){
							var bootstrapValidator = $("#materialForm").data('bootstrapValidator');
							bootstrapValidator.validate();
							if(!bootstrapValidator.isValid()){
								return;
							}
							
							if (_name != $('#input_name').val() ||
									_spec != $('#input_spe').val() ||
									_ctg != $('#select_ctg').val()){
								var res;
								$.ajax({
									url: getProjectName()+"/mtl/validateUnique.do",
									data: {name : $("#input_name").val(),
										   specification: $('#input_spe').val(),
										   category: $('#select_ctg').val()
									},
									async:false, 
									success: function(result){
										res = result.valid;
									}
								});
								if (!res){
									$('#materialForm').append('<div class="alert alert-danger" role="alert">原材料已经存在，请修改材料名称或者规格或者分类</div>');
									return;
								}
							} 
							$("#materialForm").ajaxSubmit({
								type: "post",
								url:getProjectName()+"/mtl/updateMaterial.do", 
								dataType: "text",
								success:function(result){
									Ewin.toast('原材料修改成功');
									queryMaterial();
								}
							});
						});
					}});
				}
				
				if (opt == 'delete'){
					Ewin.confirm({message: "确定要删除选中的原材料吗？"}).on(function(e){
						if (!e){
							return;
						}
						
						$.ajax({
							url: getProjectName()+"/mtl/deleteMaterial.do?id="+id,
							type: "get",
							success: function(){
								queryMaterial();
							}
						});
					});
				}
			});
		}
		return {
			init: function(){
				loadCategoryTree();
				loadMaterialTable();
				initMaterialFunction();
			}
		}
	}
	
	/*-----------------------------------------------------------------------------------*/
	/*	初始化原材料入库单
	/*-----------------------------------------------------------------------------------*/	
	var initMaterialInBill = function(){
		var bill;
		var initBill = function(){
			$.ajaxSettings.async = false;
			$.getJSON("../templater/bill/clrkd.json", "", function(data){
        		bill = $('#eBill').eBill(data);	
			});
			$.ajaxSettings.async = true;
		}
		
		var editBill = function(){
			$('#btn_save').click(function(){
				if ($("input[name='id']").val() == ''){
					bill.commit(getProjectName()+'/mtlin/newMaterialIn.do', '原材料入库单新增成功');
				}else{
					bill.commit(getProjectName()+'/mtlin/updateMaterialIn.do', '原材料入库单修改成功', false);
				}
        	});
			
			$('#btn_reset').click(function(){
				bill.resetBill();
			});
			
			$('#btn_print').click(function(){
				$('#panel_mtlinBill').print({
					globalStyles: true,
					mediaPrint: false,
					stylesheet: null,
					noPrintSelector: ".no-print",
					iframe: true,
					append: null,
					manuallyCopyFormValues: true,
					deferred: $.Deferred()
				});
			});
		}
		
		return {
			init: function(){
				initBill();
				editBill();
				return bill;
			}
		}
	}
	
	/*-----------------------------------------------------------------------------------*/
	/*	初始化原材料出库单
	/*-----------------------------------------------------------------------------------*/	
	var initMaterialOutBill = function(){
		var bill;
		var initBill = function(){
			$.ajaxSettings.async = false;
			$.getJSON("../templater/bill/clckd.json", "", function(data){
        		bill = $('#mtl_out_bill').eBill(data);	
			});
			$.ajaxSettings.async = true;
		}
		
		var editBill = function(){
			$('#btn_save').click(function(){
				if ($("input[name='id']").val() == ''){
					bill.commit(getProjectName()+'/mtlout/newBill.do', '原材料出库单新增成功');
				}else{
					bill.commit(getProjectName()+'/mtlout/updateBill.do', '原材料出库单修改成功', false);
				}
        	});
			
			$('#btn_reset').click(function(){
				bill.resetBill();
			});
			
			$('#btn_print').click(function(){
				$('#panel_mtloutBill').print({
					globalStyles: true,
					mediaPrint: false,
					stylesheet: null,
					noPrintSelector: ".no-print",
					iframe: true,
					append: null,
					manuallyCopyFormValues: true,
					deferred: $.Deferred()
				});
			});
		}
		
		return {
			init: function(){
				initBill();
				editBill();
				return bill;
			}
		}
	}
	
	/*-----------------------------------------------------------------------------------*/
	/*	初始化原材料入库单列表模块
	/*-----------------------------------------------------------------------------------*/	
	var initMaterialInList = function(){
		var initOption = function(){
			/*$.getJSON(
				getProjectName() + "/mtlCtgy/query.do",
				function(result){
					$.each(result, function(index, ctg){
						var indent = '';
						var num = ctg.code.length - 2
						for (var i = 0; i < num; i++){indent+= '&nbsp;&nbsp;';}
						$("#filter_ctgCode").append("<option value='"+ctg.id+"'>"+indent+ctg.code+" "+ctg.name+"</option>");					
					});
					$("#filter_ctgCode").select2({
						placeholder: "请选择材料分类",
						allowClear: true
					});
				}
			);*/
			$.getJSON(
				getProjectName() + "/supp/loadallsupp.do",
				function(result){
					$.each(result, function(index, supp){
						$("#filter_supplier").append("<option value='"+supp.id+"'>"+supp.name+"</option>");					
					});
					$("#filter_supplier").select2({
						placeholder: "请选择供应商",
						allowClear: true,
						width: 180
					});
				}
			);
		}
		
		var loadMaterialInTable = function(){
			$('#tbl_materialIn').bootstrapTable({
				url: getProjectName() + "/mtlin/getPageResult.do",
				method: "get",
				pagination: true,
				sidePagination: "server", 
				clickToSelect: true,
				columns: [{
		            field: 'number',
		            title: '入库单号',
		            formatter: function(value, row, index){
		        		return '<a opt="link" rowid="'+row.id+'">'+value+'</a>';
		        	}
		        }, {
		            field: 'billDate',
		            title: '入库日期'
		        }, {
		            field: 'supplier.name',
		            title: '供应商'
		        }, {
		            field: 'totalAmount',
		            title: '合计金额'
		        }, {
		            field: 'material.name',
		            title: '材料名称'
		        }, {
		            field: 'material.specification',
		            title: '材料规格'
		        }, {
		        	field: 'quantity',
		        	title: '入库数量'
		        }, {
		        	field: '',
		        	title: '操作',
		        	formatter: function(value, row, index){
		        		return '<a opt="update" rowid="'+row.id+'">修改</a>&nbsp;<a opt="delete" rowid="'+row.id+'">删除</a>';
		        	}
		        }],
		        queryParams: function(params){
		        	return {
		                pageSize: params.limit,
		                pageOffset: params.offset,                    
		                number: $("#filter_number").val(),
		                supplier: $("#filter_supplier").val(),
		                ctgCode: $("#filter_ctgCode").val(),
		                startDate: $("#filter_startDate").val(),
		                endDate: $("#filter_endDate").val()
		            }
		        },
		        onLoadSuccess: function (data) {
		        	mergeCells($('#tbl_materialIn'));
	            }
			});
		}
		
		var doQuery = function(){
			$('#btn_search').click(function(){
				$('#tbl_materialIn').bootstrapTable('refresh', {url: getProjectName() + "/mtlin/getPageResult.do"});
			});
		}
		
		var doAdd = function(){
			$('#btn_add').click(function(){
				var nthTabs = window.parent.getTabs();
		        nthTabs.addTab({
		        	id:'0201',
		            title:'材料入库',
		            active:true,
		            allowClose:true,
		            content:'mtl_in_bill.html',
		        });
			});
		}
		
		var doEditBill = function(){
			$('#tbl_materialIn').on('click', 'a', function(){
				var opt = $(this).attr('opt');
				var id = $(this).attr('rowId');
				if (opt == 'update' || opt == 'link'){
					var nthTabs = window.parent.getTabs();
			        nthTabs.addTab({
			        	id:'0201',
			            title:'材料入库',
			            active:true,
			            allowClose:true,
			            content:'mtl_in_bill.html',
			        });
			        
			        setTimeout(function(){
			        	window.parent.$('#if0201')[0].contentWindow.fillBill(id);
			        }, 1000);
				}
				if (opt == 'delete'){
					Ewin.confirm({message: "确定要删除该入库单吗？"}).on(function(e){
						if (!e){
							return;
						}
						if (window.parent.$('#if0201')[0]){
							var billId = window.parent.$('#if0201')[0].contentWindow.getBillId();
							if (billId == id){
								Ewin.alert("当前待删除入库单正在修改中，无法删除");
								return;
							}
						}
						$.ajax({
							url: getProjectName() + "/mtlin/deleteMaterialIn.do?id="+id,
							success: function(){
								Ewin.toast('材料入库单删除成功');
								$('#tbl_materialIn').bootstrapTable('refresh', {url: getProjectName() + "/mtlin/getPageResult.do"});
							}
						});
					});
				}
			});
		}
		
		var mergeCells = function(target) {
			var rows = target.find('tr');
		    var _row;
		    var rowspan = 1;
			$.each(rows, function(i, row){
				if (i == 0){
					return;
				}else if (i == 1){
					_row = row;
					return;
				}else{
					if ($($(row).find('td')[0]).html() == $($(_row).find('td')[0]).html()){
						$($(row).find('td')[0]).remove();
						$($(row).find('td')[0]).remove();
						$($(row).find('td')[0]).remove();
						$($(row).find('td')[0]).remove();
						$($(row).find('td')[3]).remove();
						rowspan++;
						if (i == rows.length - 1){
							$($(_row).find('td')[0]).attr('rowspan', rowspan);
							$($(_row).find('td')[1]).attr('rowspan', rowspan);
							$($(_row).find('td')[2]).attr('rowspan', rowspan);
							$($(_row).find('td')[3]).attr('rowspan', rowspan);
							$($(_row).find('td')[7]).attr('rowspan', rowspan);
						}
					}else{
						if (rowspan > 1){
							$($(_row).find('td')[0]).attr('rowspan', rowspan);
							$($(_row).find('td')[1]).attr('rowspan', rowspan);
							$($(_row).find('td')[2]).attr('rowspan', rowspan);
							$($(_row).find('td')[3]).attr('rowspan', rowspan);
							$($(_row).find('td')[7]).attr('rowspan', rowspan);
							rowspan = 1;
						}
						_row = row;
					}
				}
				
		    });
		}
		
		var doExport = function(){
            $('#btn_export').click(function(){
            	var number = $("#filter_number").val();
                var supplier = $("#filter_supplier").val();
                var ctgCode = $("#filter_ctgCode").val();
                var startDate = $("#filter_startDate").val();
                var endDate = $("#filter_endDate").val();
            	var url = getProjectName() + "/mtlin/exportMaterialIn.do?number=" + number + "&supplier=" + supplier + "&ctgCode=" + ctgCode + "&startDate=" + startDate + "&endDate=" + endDate;
            	window.open(url);
            });
		}
		
		return {
			init: function(){
				initOption();
				loadMaterialInTable();
				doQuery();
				doAdd();
				doEditBill();
				doExport();
			}
		}
	}
	
	/*-----------------------------------------------------------------------------------*/
	/*	初始化原材料出库单列表模块
	/*-----------------------------------------------------------------------------------*/	
	var initMaterialOutList = function(){
		var loadMaterialOutTable = function(){
			$('#tbl_materialOut').bootstrapTable({
				url: getProjectName() + "/mtlout/getPageResult.do",
				method: "get",
				pagination: true,
				sidePagination: "server", 
				clickToSelect: true,
				columns: [{
		            field: 'number',
		            title: '出库单号',
		            formatter: function(value, row, index){
		        		return '<a opt="link" rowid="'+row.id+'">'+value+'</a>';
		        	}
		        }, {
		            field: 'billDate',
		            title: '出库日期'
		        }, {
		            field: 'material.name',
		            title: '材料名称'
		        }, {
		            field: 'material.specification',
		            title: '材料规格'
		        }, {
		        	field: 'quantity',
		        	title: '入库数量'
		        }, {
		        	field: '',
		        	title: '操作',
		        	formatter: function(value, row, index){
		        		return '<a opt="update" rowid="'+row.id+'">修改</a>&nbsp;<a opt="delete" rowid="'+row.id+'">删除</a>';
		        	}
		        }],
		        queryParams: function(params){
		        	return {
		                pageSize: params.limit,
		                pageOffset: params.offset,                    
		                number: $("#filter_number").val(),
		                billReason: $("#filter_billReason").val(),
		                startDate: $("#filter_startDate").val(),
		                endDate: $("#filter_endDate").val()
		            }
		        },
		        onLoadSuccess: function (data) {
		        	mergeCells($('#tbl_materialOut'));
	            }
			});
		}
		
		var doQuery = function(){
			$('#btn_search').click(function(){
				$('#tbl_materialOut').bootstrapTable('refresh', {url: getProjectName() + "/mtlout/getPageResult.do"});
			});
		}
		
		var doAdd = function(){
			$('#btn_add').click(function(){
				var nthTabs = window.parent.getTabs();
		        nthTabs.addTab({
		        	id:'0202',
		            title:'材料出库',
		            active:true,
		            allowClose:true,
		            content:'mtl_out_bill.html',
		        });
			});
		}
		
		var doEditBill = function(){
			$('#tbl_materialOut').on('click', 'a', function(){
				var opt = $(this).attr('opt');
				var id = $(this).attr('rowId');
				if (opt == 'update' || opt == 'link'){
					var nthTabs = window.parent.getTabs();
			        nthTabs.addTab({
			        	id:'0202',
			            title:'材料出库',
			            active:true,
			            allowClose:true,
			            content:'mtl_out_bill.html',
			        });
			        
			        setTimeout(function(){
			        	window.parent.$('#if0202')[0].contentWindow.fillBill(id);
			        }, 1000);
				}
				if (opt == 'delete'){
					Ewin.confirm({message: "确定要删除该出库单吗？"}).on(function(e){
						if (!e){
							return;
						}
						if (window.parent.$('#if0202')[0]){
							var billId = window.parent.$('#if0202')[0].contentWindow.getBillId();
							if (billId == id){
								Ewin.alert("当前待删除出库单正在修改中，无法删除");
								return;
							}
						}
						$.ajax({
							url: getProjectName() + "/mtlout/deleteBill.do?id="+id,
							success: function(){
								Ewin.toast('材料入库单删除成功');
								$('#tbl_materialOut').bootstrapTable('refresh', {url: getProjectName() + "/mtlout/getPageResult.do"});
							}
						});
					});
				}
			});
		}
		
		var mergeCells = function(target) {
			var rows = target.find('tr');
		    var _row;
		    var rowspan = 1;
			$.each(rows, function(i, row){
				if (i == 0){
					return;
				}else if (i == 1){
					_row = row;
					return;
				}else{
					if ($($(row).find('td')[0]).html() == $($(_row).find('td')[0]).html()){
						$($(row).find('td')[0]).remove();
						$($(row).find('td')[0]).remove();
						$($(row).find('td')[3]).remove();
						rowspan++;
						if (i == rows.length - 1){
							$($(_row).find('td')[0]).attr('rowspan', rowspan);
							$($(_row).find('td')[1]).attr('rowspan', rowspan);
							$($(_row).find('td')[5]).attr('rowspan', rowspan);
						}
					}else{
						if (rowspan > 1){
							$($(_row).find('td')[0]).attr('rowspan', rowspan);
							$($(_row).find('td')[1]).attr('rowspan', rowspan);
							$($(_row).find('td')[5]).attr('rowspan', rowspan);
							rowspan = 1;
						}
						_row = row;
					}
				}
				
		    });
		}
		
		var doExport = function(){
            $('#btn_export').click(function(){
            	var number = $("#filter_number").val();
                var billReason = $("#filter_billReason").val();
                var startDate = $("#filter_startDate").val();
                var endDate = $("#filter_endDate").val();
            	var url = getProjectName() + "/mtlout/exportMaterialOut.do?number=" + number + "&billReason=" + billReason + "&startDate=" + startDate + "&endDate=" + endDate;
            	window.open(url);
            });
		}
		
		return {
			init: function(){
				loadMaterialOutTable();
				doQuery();
				doAdd();
				doEditBill();
				doExport();
			}
		}
	}
	
	/*-----------------------------------------------------------------------------------*/
	/*	初始化单个原材料出入明细模块
	/*-----------------------------------------------------------------------------------*/
	var initSingleMaterialInOut = function(){
		var initPeriod = function(){
			var now = Format(new Date(), 'yyyy-MM');
			var befor = increaseMonth(now, -3);
			
			$('#filter_speriod').val(befor);
			$('#filter_eperiod').val(now);
			
			$('.simplemonth span.btn').click(function(){
				var monthEle = $(this).parent().find('input');
				var curMon = monthEle.val();
				
				if (curMon == ''){
					monthEle.val(Format(new Date(), 'yyyy-MM'));
				}else {
					if($(this).hasClass('btn-up')){
						monthEle.val(increaseMonth(curMon, -1));
					}
					if($(this).hasClass('btn-down')){
						monthEle.val(increaseMonth(curMon, 1));
					}
				}
			});
		}
		
		var loadInoutTable = function(){
			$("#tbl_mtl_inout").bootstrapTable({
				url: getProjectName() + "/mtlio/getInOutByMaterial.do",
				method: "get",
				pagination: false,
				columns: [{
		            field: 'genDate',
		            title: '发生日期'
		        }, {
		            field: 'bill.number',
		            title: '关联单据',
		            formatter: function(value, row, index){
		            	if (row.bill){
		            		if (value.indexOf('CLRKD') == 0)
		            			return '<a opt="link_in" rowid="'+row.bill.id+'">'+value+'</a>';
		            		if (value.indexOf('CLCKD') == 0)
		            			return '<a opt="link_out" rowid="'+row.bill.id+'">'+value+'</a>';
		            	}else{
		            		return '';
		            	}
		            }
		        }, {
		            field: 'summary',
		            title: '摘要'
		        }, {
		            field: 'inQuantity',
		            title: '入库数量'
		        },{
		            field: 'outQuantity',
		            title: '出库数量'
		        }],
		        queryParams: function(params){
		        	return {
		        		materialId: $('#filter_materialId').val(),
		        		sPeriod: $('#filter_speriod').val(),
		        		ePeriod: $('#filter_eperiod').val()
		            }
		        }
			});
		}
		
		var doQuery = function(){
			$('#btn_search').click(function(){
				$("#tbl_mtl_inout").bootstrapTable("refresh", {url: getProjectName() + "/mtlio/getInOutByMaterial.do"});
			});
		}
		
		var initOtherOpt = function(){
			$('#btn_chose_mtl').click(function(){
				Ewin.load({id: 'mtl_ref_single', title: '选择原材料', url: 'fragment/mtl_ref_single.html', style: 'width: 800px', callback: function(){
					$('#btn_select_close').click(function(){
						var selections = $("#tbl_material").bootstrapTable('getSelections');
						var mtlId = selections[0].id;
						var mtlName = selections[0].name;
						var mtlUnit = selections[0].unit;
						$('#tbl_mtl_inout').find('th .th-inner').eq(3).text('入库数量('+mtlUnit+')');
						$('#tbl_mtl_inout').find('th .th-inner').eq(4).text('出库数量('+mtlUnit+')');
						$('#filter_materialId').val(mtlId);
						$('#btn_chose_mtl').text(mtlName);
						$('#label_mtlName').text(mtlName);
					});
				}});
			});
			
			$('#tbl_mtl_inout').on('click', 'a', function(){
				var opt = $(this).attr('opt');
				var id = $(this).attr('rowId');
				
				if (opt == 'link_in'){
					var nthTabs = window.parent.getTabs();
			        nthTabs.addTab({
			        	id:'0201',
			            title:'原材料入库',
			            active:true,
			            allowClose:true,
			            content:'mtl_in_bill.html',
			        });
			        
			        setTimeout(function(){
			        	window.parent.$('#if0201')[0].contentWindow.fillBill(id);
			        }, 1000);
				}
				
				if (opt == 'link_out'){
					var nthTabs = window.parent.getTabs();
			        nthTabs.addTab({
			        	id:'0202',
			            title:'原材料出库',
			            active:true,
			            allowClose:true,
			            content:'mtl_out_bill.html',
			        });
			        
			        setTimeout(function(){
			        	window.parent.$('#if0202')[0].contentWindow.fillBill(id);
			        }, 1000);
				}
			});
		}
		
		var doExport = function(){
            $('#btn_export').click(function(){
            	var materialId = $('#filter_materialId').val();
            	var startPeriod = $('#filter_speriod').val();
        		var endPeriod = $('#filter_eperiod').val();
            	var url = getProjectName() + "/mtlio/exportInOutByMaterial.do?materialId=" + materialId + "&startPeriod=" + startPeriod + "&endPeriod=" + endPeriod;
            	window.open(url);
            });
		}
		
		return {
			init: function(){
				initPeriod();
				loadInoutTable();
				doQuery();
				initOtherOpt();
				doExport();
			}
		}
	}
	
	
	/*-----------------------------------------------------------------------------------*/
	/*	初始化原材料出入汇总模块
	/*-----------------------------------------------------------------------------------*/
	var initGlobalMaterialInOut = function(){
		var initPeriod = function(){
			var now = Format(new Date(), 'yyyy-MM');
			var befor = increaseMonth(now, -3);
			
			$('#filter_speriod').val(befor);
			$('#filter_eperiod').val(now);
			
			$('.simplemonth span.btn').click(function(){
				var monthEle = $(this).parent().find('input');
				var curMon = monthEle.val();
				
				if (curMon == ''){
					monthEle.val(Format(new Date(), 'yyyy-MM'));
				}else {
					if($(this).hasClass('btn-up')){
						monthEle.val(increaseMonth(curMon, -1));
					}
					if($(this).hasClass('btn-down')){
						monthEle.val(increaseMonth(curMon, 1));
					}
				}
			});
		}
		
		var thL1 = [];
		var thL2 = [];
		var refreshTabelHead = function(){
			thL1 = [];
			thL2 = [];
			var sPeriod = $('#filter_speriod').val();
			var ePeriod = $('#filter_eperiod').val();
			
			var endMonth = parseInt(ePeriod.substring(0, 4)) * 12 + parseInt(ePeriod.substring(5));
			var startMonth = parseInt(sPeriod.substring(0, 4)) * 12 + parseInt(sPeriod.substring(5));
			
			var numMonth = endMonth - startMonth;
			
			thL1.push({
				field: 'mtlName',
                title: "原材料",
                valign:"middle",
                align:"center",
                colspan: 1,
                rowspan: 2
			});
			for (var i = startMonth; i <= endMonth; i++){
				var td_year = parseInt(i / 12);
				var td_month = i % 12;
				if (td_month == 0){
					td_year = td_year - 1;
					td_month = 12;
				}
				thL1.push({
					title: td_year + '年' + td_month + '月',
                    valign:"middle",
                    align:"center",
                    colspan: 2,
                    rowspan: 1
				});
			}
			thL1.push({
				field: 'storage',
                title: "当前库存",
                valign:"middle",
                align:"center",
                colspan: 1,
                rowspan: 2
			});
			
			for (var i = startMonth; i <= endMonth; i++){
				var td_year = parseInt(i / 12);
				var td_month = i % 12;
				if (td_month == 0){
					td_year = td_year - 1;
					td_month = 12;
				}
				
				var period = td_year + '' + (td_month < 10 ? '0'+ td_month : td_month);
				thL2.push({
					field: 'iomap.'+period+'.inQuantity',
		            title: '入库数'
				});
				thL2.push({
					field: 'iomap.'+period+'.outQuantity',
		            title: '出库数'
				});
			}
		}
		
		var loadInoutTable = function(){
			refreshTabelHead();
			$("#tbl_mtl_inout").bootstrapTable({
				url: getProjectName() + "/mtlio/getGlobalInOutPageResult.do",
				method: "get",
				pagination: true,
				sidePagination : "server",
				pageSize : 50,
				pageList : [50, 100, 200],
				columns: [thL1, thL2],
		        queryParams: function(params){
		        	return {
		        		pageSize: params.limit,
		                pageOffset: params.offset,  
		        		startPeriod: $('#filter_speriod').val(),
		        		endPeriod: $('#filter_eperiod').val()
		            }
		        }
			});
		}
		
		var doQuery = function(){
			$('#btn_search').click(function(){
				refreshTabelHead();
				$("#tbl_mtl_inout").bootstrapTable("refreshOptions", {columns: [thL1, thL2]});
			});
		}
		
		var doExport = function(){
            $('#btn_export').click(function(){
            	var startPeriod = $('#filter_speriod').val();
        		var endPeriod = $('#filter_eperiod').val();
            	var url = getProjectName() + "/mtlio/exportGlobalInOut.do?startPeriod=" + startPeriod + "&endPeriod=" + endPeriod;
            	window.open(url);
            });
		}
		
		return {
			init: function(){
				initPeriod();
				loadInoutTable();
				doQuery();
				doExport();
			}
		}
	}
	
	
	/*-----------------------------------------------------------------------------------*/
	/*	初始化原材料盘点模块
	/*-----------------------------------------------------------------------------------*/
	var initMaterialInventory = function(){
		var loadMtlCtg4Select = function(){
			$.getJSON(
				getProjectName() + "/mtlCtgy/query.do",
				function(result){
					$.each(result, function(index, ctg){
						var indent = '';
						var num = ctg.code.length - 2
						for (var i = 0; i < num; i++){indent+= '&nbsp;&nbsp;';}
						$("#filter_mtlCtg").append("<option value='"+ctg.code+"'>"+indent+ctg.code+" "+ctg.name+"</option>");							
					});
					$("#filter_mtlCtg").select2({
						placeholder: "选择材料分类",
						allowClear: true
					});
				}
			);
		} 
		
		var loadSystemStorage = function(){
			$('#tbl_inventory').bootstrapTable({
				url: getProjectName() + "/mtlinv/loadSystemInventory.do",
				method: "get",
				clickToSelect: true,
				pagination: true,
				sidePagination : "server",
				pageSize : 50,
				pageList : [50, 100, 200],
				columns: [{
		            field: 'material.name',
		            title: '材料名称'
		        }, {
		        	field: 'material.specification',
		            title: '材料规格'
				}, {
		        	field: 'expectQuantity',
		            title: '系统库存'
				}, {
		        	field: 'actualQuantity',
		            title: '盘点库存',
		            formatter: function(value, row, index){
		            	if (value == 0)
		            		return '<span name="cashStorage" mtlId="'+row.material.id+'"></span>';
		            	else
		            	    return '<span name="cashStorage" mtlId="'+row.material.id+'">'+value+'</span>';
		        	},
		        	width: 150
				}, {
		        	field: '',
		            title: '盘盈盘亏',
		            formatter: function(value, row, index){
		            	var result = row.actualQuantity - row.expectQuantity;
		            	if (row.actualQuantity == 0 || result == 0)
		            		return '';
		            	
		            	if (result > 0){
		            		return '<span style="color: green">'+ result +'</span>';
		            	}
		            	
		            	if (result < 0){
		            		return '<span style="color: red">'+ result +'</span>';
		            	}
		        	}
				}],
		        queryParams: function(params){
		        	return {
		        		pageSize: params.limit,
		                pageOffset: params.offset,  
		                name: $('#filter_mtlName').val(),
		                ctgCode: $('#filter_mtlCtg').val(),
		                ignore0storage: $('#filter_ignore0storage:checked').val(),
		                ignoreNullStorage: $('#filter_ignoreNullStorage:checked').val(),
		                profitLoss: $('#filter_profitLoss:checked').val() 
		            }
		        }
			});
		}
		
		var doQuery = function(){
			$('#btn_search').click(function(){
				$("#tbl_inventory").bootstrapTable("refresh", {url: getProjectName() + "/mtlinv/loadSystemInventory.do"});
			});
		}
		
		var cashActualQuantity = function(){
			$('#tbl_inventory').on('click', 'td', function(event){
				var $td = $(this);
				$td.css('padding', '0');
				var width = $td.width();
				var height = $td.height();
				
				var eActualQuantity = $td.children('span[name=\'cashStorage\']');
				if(eActualQuantity.length == 1){
					
					
					var mtlId = eActualQuantity.attr('mtlId');
					var quantity = eActualQuantity.text();
					var input =$('<input type="text" value="'+quantity+'" style="display: inline-block; border: 1px solid gray">');
					input.width(width -2);
					input.height(height -2);
					eActualQuantity.text('');
					eActualQuantity.append(input);
					input.focus();
					input.blur(function(){
						var n_quantity = $(this).val();
						if ($.trim(n_quantity) == '')
							n_quantity = 0;
						if (quantity != n_quantity){
							$.ajax({
								url:getProjectName() + "/mtlinv/saveInventoryItem.do",
								type: 'post',
								data: {materialId: mtlId, quantity: n_quantity},
								success: function(){
									input.remove();
									eActualQuantity.text(n_quantity);
									var eExpectQuantity = $td.prev();
									var eProfitLoss = $td.next();
									var profitLoss = n_quantity - eExpectQuantity.text();
									if (profitLoss > 0){
										eProfitLoss.html('<span style="color: green">'+ profitLoss +'</span>');
									}
									
									if (profitLoss < 0){
										eProfitLoss.html('<span style="color: red">'+ profitLoss +'</span>');
									}
								}
							});
						}else{
							input.remove();
							eActualQuantity.text(quantity);
						}
					});
				}
			});
		}
		
		var doSave = function(){
			$('#btn_save').click(function(){
				Ewin.confirm({message: "盘点信息保存后将不可以修改，确定现在保存盘点信息吗？"}).on(function(e){
					if (!e){
						return;
					}
					$.ajax({
						url: getProjectName() + "/mtlinv/inventory.do",
						success: function(){
							Ewin.toast('原材料盘点数据成功');
							$("#tbl_inventory").bootstrapTable("refresh", {url: getProjectName() + "/mtlinv/loadSystemInventory.do"});
						}
					});
				});
			});
		}
		
		var doExport = function(){
			$('#btn_export').click(function(){
				var name = $('#filter_mtlName').val();
                var ctgCode = $('#filter_mtlCtg').val();
                var ignore0storage = $('#filter_ignore0storage:checked').val();
                if (!ignore0storage)
                	ignore0storage = 0;
            	var url = getProjectName() + "/mtlinv/exportInventory.do?name=" + name + "&ctgCode=" + ctgCode + "&ignore0storage=" + ignore0storage;
            	window.open(url);
			});
		}
		
		var doImport = function(){
			$('#btn_import').click(function(){
				ImportData.show({url: getProjectName() + "/mtlinv/importInventory.do",
					templaterName: getProjectName() + "/templater/import/材料盘点.xls",
					callback: function(){
						$("#tbl_inventory").bootstrapTable("refresh", {url: getProjectName() + "/mtlinv/loadSystemInventory.do", cache: false});
					}
				});
			});
		}
		
		var doReset = function(){
			$('#btn_reset').click(function(){
				Ewin.confirm({message: "确定删除所有盘点库存吗？"}).on(function(e){
					if (!e){
						return;
					}
					$.ajax({
						url: getProjectName() + "/mtlinv/reset.do",
						success: function(){
							$("#tbl_inventory").bootstrapTable("refresh", {url: getProjectName() + "/mtlinv/loadSystemInventory.do"});
						}
					});
				});
			});
		}
		
		return {
			init: function(){
				loadMtlCtg4Select();
				loadSystemStorage();
				doQuery();
				cashActualQuantity();
				doSave();
				doExport();
				doImport();
				doReset();
			}
		}
	}
	
	
	/*-----------------------------------------------------------------------------------*/
	/*	初始化历史盘点记录模块
	/*-----------------------------------------------------------------------------------*/
	var initHisMaterialInventory = function(){
		
		var th = [];
		
		var loadMtlCtg4Select = function(){
			$.getJSON(
				getProjectName() + "/mtlCtgy/query.do",
				function(result){
					$.each(result, function(index, ctg){
						var indent = '';
						var num = ctg.code.length - 2
						for (var i = 0; i < num; i++){indent+= '&nbsp;&nbsp;';}
						$("#filter_mtlCtg").append("<option value='"+ctg.code+"'>"+indent+ctg.code+" "+ctg.name+"</option>");							
					});
					$("#filter_mtlCtg").select2({
						placeholder: "选择材料分类",
						allowClear: true
					});
				}
			);
		} 
		
		var initPeriod = function(){
			var date = new Date();
			var year = date.getFullYear();
			$('#filter_speriod').val(year + '-' + '01');
			$('#filter_eperiod').val(year + '-' + '12');
		}
		
		var loadInventoryTable = function(){			
			$.ajax({
				url: getProjectName() + "/mtlinv/getHistroyInventory.do",
				data: {startPeriod: $('#filter_speriod').val(), endPeriod: $('#filter_eperiod').val()},
				success: function(result){
					th = [];
					th.push({
						field: 'mtlName',
						title: '材料名称'
					});
					th.push({
						field: 'mtlSpec',
						title: '材料规格'
					});
					$.each(result, function(i, inv){
						th.push({
							field: 'storageMap.'+inv.id,
							title: inv.inventoryDate,
		                    valign:"middle",
		                    align:"center"
						});
					});
					
					
					$("#tbl_inventory").bootstrapTable({
						url: getProjectName() + "/mtlinv/getHistroyInventoryDetail.do",
						method: "get",
						pagination: true,
						sidePagination : "server",
						pageSize : 50,
						pageList : [50, 100, 200],
						columns: th,
				        queryParams: function(params){
				        	return {
				        		pageSize: params.limit,
				                pageOffset: params.offset,  
				        		startPeriod: $('#filter_speriod').val(),
				        		endPeriod: $('#filter_eperiod').val(),
				        		name: $('#filter_mtlName').val(),
				        		ctgCode: $('#filter_mtlCtg').val()
				            }
				        }
					});
				}
			});			
		}
		
		var doQuery = function(){
			$('#btn_search').click(function(){
				$.ajax({
					url: getProjectName() + "/mtlinv/getHistroyInventory.do",
					data: {startPeriod: $('#filter_speriod').val(), endPeriod: $('#filter_eperiod').val()},
					success: function(result){
						th = [];
						th.push({
							field: 'mtlName',
							title: '材料名称'
						});
						th.push({
							field: 'mtlSpec',
							title: '材料规格'
						});
						$.each(result, function(i, inv){
							th.push({
								field: 'storageMap.'+inv.id,
								title: inv.inventoryDate,
			                    valign:"middle",
			                    align:"center"
							});
						});
						$("#tbl_inventory").bootstrapTable("refresh", {url: getProjectName() + "/mtlinv/getHistroyInventoryDetail.do"});
					}
				});
			});
		}
		
		return {
			init: function(){
				loadMtlCtg4Select();
				initPeriod();
				loadInventoryTable();
				doQuery();
			}
		}
	}
	
	/*-----------------------------------------------------------------------------------*/
	/*	初始化成品列表模块
	/*-----------------------------------------------------------------------------------*/	
	var initProductList = function(){
		var loadProductTable = function(){
			$("#tbl_product").bootstrapTable({
				url: getProjectName() + "/pdt/getPageResult.do",
				method: "get",
				pagination: true,
				sidePagination: "server", 
				clickToSelect: true,
				columns: [{
		            field: 'code',
		            title: '货号'
		        }, {
		            field: 'name',
		            title: '品名'
		        }, {
		            field: 'specification',
		            title: '规格'
		        }, {
		            field: 'model',
		            title: '含量'
		        }, {
		            field: 'storage',
		            title: '库存'
		        }, {
		        	field: 'remark',
		        	title: '备注'
		        }, {
		        	field: '',
		        	title: '操作',
		        	formatter: function(value, row, index){
		        		return '<a opt="update" rowid="'+row.id+'">修改</a>&nbsp;<a opt="delete" rowid="'+row.id+'">删除</a>';
		        	}
		        }],
		        queryParams: function(params){
		        	return {
		                pageSize: params.limit,
		                pageOffset: params.offset,                    
		                code: $("#filter_code").val()
		            }
		        }
			});
		}
		
		
		var initProductFunction = function(){
			$('#btn_search').click(function(){
				$("#tbl_product").bootstrapTable("refresh", {url: getProjectName() + "/pdt/getPageResult.do", cache: false});
			});
			
			$('#btn_add').click(function(){					
				Ewin.load({id: 'pdt_edit', title: '新的成品', url: 'fragment/pdt_edit.html', callback: function(){
					
				}});
			});
			
			initEditProduct();
			
			$('#btn_import').click(function(){
				ImportData.show({url: getProjectName() + "/pdt/importProduct.do",
					templaterName: getProjectName() + "/templater/import/成品列表.xls",
					callback: function(){
						$("#tbl_product").bootstrapTable("refresh", {url: getProjectName() + "/pdt/getPageResult.do", cache: false});
					}
				});
			});
			
			$('#btn_export').click(function(){
				var param = 'code=' + $("#filter_code").val();
				window.open(getProjectName() + "/pdt/exportProduct.do?"+param);
			});
		}
		
		var initEditProduct = function(id){
			$('#tbl_product').on('click', 'a', function(){
				var opt = $(this).attr('opt');
				var id = $(this).attr('rowId');
				if (opt == 'update'){
					Ewin.load({id: 'pdt_edit', title: '修改成品', url: 'fragment/pdt_edit.html', callback: function(){
						$.ajax({
							url: getProjectName()+"/pdt/getProductById.do?id="+id, 
							success: function(result){
								fillForm($("#productForm"), {
									id: result.id, 
									code: result.code, 
									name: result.name, 
									specification: result.specification, 
									model: result.model, 
									storage: result.storage,
									remark: result.remark});
							}
						});
					}});
				}
				
				if (opt == 'delete'){
					Ewin.confirm({message: "确定要删除选中的成品吗？"}).on(function(e){
						if (!e){
							return;
						}
						
						$.ajax({
							url: getProjectName()+"/pdt/deleteProduct.do?id="+id,
							type: "get",
							success: function(){
								$("#tbl_product").bootstrapTable("refresh", {url: getProjectName() + "/pdt/getPageResult.do", cache: false});
							}
						});
					});
				}
			});
		}
		return {
			init: function(){
				loadProductTable();
				initProductFunction();
			}
		}
	}
	
	/*-----------------------------------------------------------------------------------*/
	/*	初始化成品入库单
	/*-----------------------------------------------------------------------------------*/	
	var initProductInBill = function(){
		var bill;
		var initBill = function(){
			$.ajaxSettings.async = false;
			$.getJSON("../templater/bill/cprkd.json", "", function(data){
        		bill = $('#productInBill').eBill(data);	
			});
			$.ajaxSettings.async = true;
		}
		
		var editBill = function(){
			$('#btn_save').click(function(){
				if ($("input[name='id']").val() == ''){
					bill.commit(getProjectName()+'/pdtin/newProductIn.do', '成品入库单新增成功');
				}else{
					bill.commit(getProjectName()+'/pdtin/updateProductIn.do', '成品入库单修改成功', false);
				}
        	});
			
			$('#btn_reset').click(function(){
				bill.resetBill();
			});
			
			$('#btn_print').click(function(){
				$('#panel_pdtinBill').print({
					globalStyles: true,
					mediaPrint: false,
					stylesheet: null,
					noPrintSelector: ".no-print",
					iframe: true,
					append: null,
					manuallyCopyFormValues: true,
					deferred: $.Deferred()
				});
			});
		}
		
		return {
			init: function(){
				initBill();
				editBill();
				return bill;
			}
		}
	}
	
	
	/*-----------------------------------------------------------------------------------*/
	/*	初始化成品入库单列表
	/*-----------------------------------------------------------------------------------*/	
	var initProductInList = function(){
		var initOption = function(){
			$.getJSON(
				getProjectName() + "/pdt/query.do",
				function(result){
					$.each(result, function(index, pdt){
						$('#filter_product').append('<option value="'+pdt.id+'">'+pdt.name+'</option>');
					});
					$("#filter_product").select2({
						placeholder: "请选择成品",
						allowClear: true
					});
				}
			);
		}
		
		var loadProductInTable = function(){
			$('#tbl_productIn').bootstrapTable({
				url: getProjectName() + "/pdtin/getPageResult.do",
				method: "get",
				pagination: true,
				sidePagination: "server", 
				clickToSelect: true,
				columns: [{
		            field: 'number',
		            title: '入库单号',
		            formatter: function(value, row, index){
		        		return '<a opt="link" rowid="'+row.id+'">'+value+'</a>';
		        	}
		        }, {
		            field: 'billDate',
		            title: '入库日期'
		        }, {
		            field: 'product.code',
		            title: '成品货号'
		        }, {
		            field: 'product.model',
		            title: '成品含量'
		        }, {
		        	field: 'quantity',
		        	title: '入库数量'
		        }, {
		        	field: '',
		        	title: '操作',
		        	formatter: function(value, row, index){
		        		return '<a opt="update" rowid="'+row.id+'">修改</a>&nbsp;<a opt="delete" rowid="'+row.id+'">删除</a>';
		        	}
		        }],
		        queryParams: function(params){
		        	return {
		                pageSize: params.limit,
		                pageOffset: params.offset,                    
		                number: $("#filter_number").val(),
		                //billReason: $("#filter_billReason").val(),
		                //pdtId: $("#filter_product").val(),
		                startDate: $("#filter_startDate").val(),
		                endDate: $("#filter_endDate").val()
		            }
		        },
		        onLoadSuccess: function (data) {
		        	mergeCells($('#tbl_productIn'));
	            }
			});
		}
		
		var doQuery = function(){
			$('#btn_search').click(function(){
				$('#tbl_productIn').bootstrapTable('refresh', {url: getProjectName() + "/pdtin/getPageResult.do"});
			});
		}
		
		var doAdd = function(){
			$('#btn_add').click(function(){
				var nthTabs = window.parent.getTabs();
		        nthTabs.addTab({
		        	id:'0301',
		            title:'成品入库',
		            active:true,
		            allowClose:true,
		            content:'pdt_in_bill.html',
		        });
			});
		}
		
		var doEditBill = function(){
			$('#tbl_productIn').on('click', 'a', function(){
				var opt = $(this).attr('opt');
				var id = $(this).attr('rowId');
				if (opt == 'update' || opt == 'link'){
					var nthTabs = window.parent.getTabs();
			        nthTabs.addTab({
			        	id:'0301',
			            title:'成品入库',
			            active:true,
			            allowClose:true,
			            content:'pdt_in_bill.html',
			        });
			        
			        setTimeout(function(){
			        	window.parent.$('#if0301')[0].contentWindow.fillBill(id);
			        }, 1000);
				}
				if (opt == 'delete'){
					Ewin.confirm({message: "确定要删除该入库单吗？"}).on(function(e){
						if (!e){
							return;
						}
						if (window.parent.$('#if0301')[0]){
							var billId = window.parent.$('#if0301')[0].contentWindow.getBillId();
							if (billId == id){
								Ewin.alert("当前待删除入库单正在修改中，无法删除");
								return;
							}
						}
						$.ajax({
							url: getProjectName() + "/pdtin/deleteProductIn.do?id="+id,
							success: function(){
								Ewin.toast('成品入库单删除成功');
								$('#tbl_productIn').bootstrapTable('refresh', {url: getProjectName() + "/pdtin/getPageResult.do"});
							}
						});
					});
				}
			});
		}
		
		var mergeCells = function(target) {
			var rows = target.find('tr');
		    var _row;
		    var rowspan = 1;
			$.each(rows, function(i, row){
				if (i == 0){
					return;
				}else if (i == 1){
					_row = row;
					return;
				}else{
					if ($($(row).find('td')[0]).html() == $($(_row).find('td')[0]).html()){
						$($(row).find('td')[0]).remove();
						$($(row).find('td')[0]).remove();
						$($(row).find('td')[3]).remove();
						rowspan++;
						if (i == rows.length - 1){
							$($(_row).find('td')[0]).attr('rowspan', rowspan);
							$($(_row).find('td')[1]).attr('rowspan', rowspan);
							$($(_row).find('td')[5]).attr('rowspan', rowspan);
						}
					}else{
						if (rowspan > 1){
							$($(_row).find('td')[0]).attr('rowspan', rowspan);
							$($(_row).find('td')[1]).attr('rowspan', rowspan);
							$($(_row).find('td')[5]).attr('rowspan', rowspan);
							rowspan = 1;
						}
						_row = row;
					}
				}
				
		    });
		}
		
		var doExport = function(){
			$('#btn_export').click(function(){
				var filterStr = "number=" + $("#filter_number").val() +
                               //"&billReason=" + $("#filter_billReason").val() +
                               //"&pdtId=" + $("#filter_product").val() +
                               "&startDate=" + $("#filter_startDate").val() +
                               "&endDate=" + $("#filter_endDate").val();
				window.open(getProjectName() + "/pdtin/exportProductIn.do?" + filterStr);
			});
		}
		
		return {
			init: function(){
				initOption();
				loadProductInTable();
				doQuery();
				doAdd();
				doEditBill();
				doExport();
			}
		}
	}
	
	/*-----------------------------------------------------------------------------------*/
	/*	初始化成品出库单
	/*-----------------------------------------------------------------------------------*/	
	var initProductOutBill = function(){
		var bill;
		var initBill = function(){
			$.ajaxSettings.async = false;
			$.getJSON("../templater/bill/cpckd.json", "", function(data){
        		bill = $('#productOutBill').eBill(data);	
			});
			$.ajaxSettings.async = true;
		}
		
		var editBill = function(){
			$('#btn_save').click(function(){
				if ($("input[name='id']").val() == ''){
					bill.commit(getProjectName()+'/pdtout/newProductOut.do', '成品出库单新增成功');
				}else{
					bill.commit(getProjectName()+'/pdtout/updateProductOut.do', '成品出库单修改成功', false);
				}
        	});
			
			$('#btn_reset').click(function(){
				bill.resetBill();
			});
			
			$('#btn_print').click(function(){
				$('#panel_pdtoutBill').print({
					globalStyles: true,
					mediaPrint: false,
					stylesheet: null,
					noPrintSelector: ".no-print",
					iframe: true,
					append: null,
					manuallyCopyFormValues: true,
					deferred: $.Deferred()
				});
			});
		}
		
		return {
			init: function(){
				initBill();
				editBill();
				return bill;
			}
		}
	}
	
	
	/*-----------------------------------------------------------------------------------*/
	/*	初始化成品出库单列表
	/*-----------------------------------------------------------------------------------*/	
	var initProductOutList = function(){
		var initOption = function(){
			$.getJSON(
				getProjectName() + "/pdt/query.do",
				function(result){
					$.each(result, function(index, pdt){
						$('#filter_product').append('<option value="'+pdt.id+'">'+pdt.name+'</option>');
					});
					$("#filter_product").select2({
						placeholder: "请选择成品",
						allowClear: true
					});
				}
			);
		}
		
		var loadProductOutTable = function(){
			$('#tbl_productOut').bootstrapTable({
				url: getProjectName() + "/pdtout/getPageResult.do",
				method: "get",
				pagination: true,
				sidePagination: "server", 
				clickToSelect: true,
				columns: [{
		            field: 'number',
		            title: '出库单号',
		            formatter: function(value, row, index){
		        		return '<a opt="link" rowid="'+row.id+'">'+value+'</a>';
		        	}
		        }, {
		            field: 'billDate',
		            title: '出库日期'
		        }, {
		            field: 'product.code',
		            title: '成品货号'
		        }, {
		            field: 'product.model',
		            title: '成品含量'
		        }, {
		        	field: 'quantity',
		        	title: '出库数量'
		        }, {
		        	field: '',
		        	title: '操作',
		        	formatter: function(value, row, index){
		        		return '<a opt="update" rowid="'+row.id+'">修改</a>&nbsp;<a opt="delete" rowid="'+row.id+'">删除</a>';
		        	}
		        }],
		        queryParams: function(params){
		        	return {
		                pageSize: params.limit,
		                pageOffset: params.offset,                    
		                number: $("#filter_number").val(),
		                //billReason: $("#filter_billReason").val(),
		                //pdtId: $("#filter_product").val(),
		                startDate: $("#filter_startDate").val(),
		                endDate: $("#filter_endDate").val()
		            }
		        },
		        onLoadSuccess: function (data) {
		        	mergeCells($('#tbl_productOut'));
	            }
			});
		}
		
		var doQuery = function(){
			$('#btn_search').click(function(){
				$('#tbl_productOut').bootstrapTable('refresh', {url: getProjectName() + "/pdtout/getPageResult.do"});
			});
		}
		
		var doAdd = function(){
			$('#btn_add').click(function(){
				var nthTabs = window.parent.getTabs();
		        nthTabs.addTab({
		        	id:'0302',
		            title:'成品出库',
		            active:true,
		            allowClose:true,
		            content:'pdt_out_bill.html',
		        });
			});
		}
		
		var doEditBill = function(){
			$('#tbl_productOut').on('click', 'a', function(){
				var opt = $(this).attr('opt');
				var id = $(this).attr('rowId');
				if (opt == 'update' || opt == 'link'){
					var nthTabs = window.parent.getTabs();
			        nthTabs.addTab({
			        	id:'0302',
			            title:'成品出库',
			            active:true,
			            allowClose:true,
			            content:'pdt_out_bill.html',
			        });
			        
			        setTimeout(function(){
			        	window.parent.$('#if0302')[0].contentWindow.fillBill(id);
			        }, 1000);
				}
				if (opt == 'delete'){
					Ewin.confirm({message: "确定要删除该出库单吗？"}).on(function(e){
						if (!e){
							return;
						}
						if (window.parent.$('#if0302')[0]){
							var billId = window.parent.$('#if0302')[0].contentWindow.getBillId();
							if (billId == id){
								Ewin.alert("当前待删除出库单正在修改中，无法删除");
								return;
							}
						}
						$.ajax({
							url: getProjectName() + "/pdtout/deleteProductOut.do?id="+id,
							success: function(){
								Ewin.toast('成品出库单删除成功');
								$('#tbl_productOut').bootstrapTable('refresh', {url: getProjectName() + "/pdtout/getPageResult.do"});
							}
						});
					});
				}
			});
		}
		
		var mergeCells = function(target) {
			var rows = target.find('tr');
		    var _row;
		    var rowspan = 1;
			$.each(rows, function(i, row){
				if (i == 0){
					return;
				}else if (i == 1){
					_row = row;
					return;
				}else{
					if ($($(row).find('td')[0]).html() == $($(_row).find('td')[0]).html()){
						$($(row).find('td')[0]).remove();
						$($(row).find('td')[0]).remove();
						$($(row).find('td')[3]).remove();
						rowspan++;
						if (i == rows.length - 1){
							$($(_row).find('td')[0]).attr('rowspan', rowspan);
							$($(_row).find('td')[1]).attr('rowspan', rowspan);
							$($(_row).find('td')[5]).attr('rowspan', rowspan);
						}
					}else{
						if (rowspan > 1){
							$($(_row).find('td')[0]).attr('rowspan', rowspan);
							$($(_row).find('td')[1]).attr('rowspan', rowspan);
							$($(_row).find('td')[5]).attr('rowspan', rowspan);
							rowspan = 1;
						}
						_row = row;
					}
				}
				
		    });
		}
		
		var doExport = function(){
			$('#btn_export').click(function(){
				var filterStr = "number=" + $("#filter_number").val() +
                               //"&billReason=" + $("#filter_billReason").val() +
                              // "&pdtId=" + $("#filter_product").val() +
                               "&startDate=" + $("#filter_startDate").val() +
                               "&endDate=" + $("#filter_endDate").val();
				window.open(getProjectName() + "/pdtout/exportProductOut.do?" + filterStr);
			});
		}
		
		return {
			init: function(){
				initOption();
				loadProductOutTable();
				doQuery();
				doAdd();
				doEditBill();
				doExport();
			}
		}
	}
	

	/*-----------------------------------------------------------------------------------*/
	/*	初始化成品盘点模块
	/*-----------------------------------------------------------------------------------*/
	var initProductInventory = function(){
		
		var loadSystemStorage = function(){
			$('#tbl_inventory').bootstrapTable({
				url: getProjectName() + "/pdtinv/loadSystemInventory.do",
				method: "get",
				clickToSelect: true,
				pagination: true,
				sidePagination : "server",
				pageSize : 50,
				pageList : [50, 100, 200],
				columns: [{
		            field: 'product.code',
		            title: '成品货号'
		        }, {
		        	field: 'product.model',
		            title: '成品含量'
				}, {
		        	field: 'expectQuantity',
		            title: '系统库存（件）'
				}, {
		        	field: 'actualQuantity',
		            title: '盘点库存（件）',
		            formatter: function(value, row, index){
		            	if (value == 0)
		            		return '<span name="cashStorage" pdtId="'+row.product.id+'"></span>';
		            	else
		            	    return '<span name="cashStorage" pdtId="'+row.product.id+'">'+value+'</span>';
		        	},
		        	width: 150
				}, {
		        	field: '',
		            title: '盘盈盘亏',
		            formatter: function(value, row, index){
		            	var result = row.actualQuantity - row.expectQuantity;
		            	if (row.actualQuantity == 0 || result == 0)
		            		return '';
		            	
		            	if (result > 0){
		            		return '<span style="color: green">'+ result +'</span>';
		            	}
		            	
		            	if (result < 0){
		            		return '<span style="color: red">'+ result +'</span>';
		            	}
		        	}
				}],
		        queryParams: function(params){
		        	return {
		        		pageSize: params.limit,
		                pageOffset: params.offset,  
		                code: $('#filter_pdtCode').val(),
		                ignore0storage: $('#filter_ignore0storage:checked').val(),
		                ignoreNullStorage: $('#filter_ignoreNullStorage:checked').val(),
		                profitLoss: $('#filter_profitLoss:checked').val() 
		            }
		        }
			});
		}
		
		var doQuery = function(){
			$('#btn_search').click(function(){
				$("#tbl_inventory").bootstrapTable("refresh", {url: getProjectName() + "/pdtinv/loadSystemInventory.do"});
			});
		}
		
		var cashActualQuantity = function(){
			$('#tbl_inventory').on('click', 'td', function(event){
				var $td = $(this);
				$td.css('padding', '0');
				var width = $td.width();
				var height = $td.height();
				
				var eActualQuantity = $td.children('span[name=\'cashStorage\']');
				if(eActualQuantity.length == 1){
					
					
					var pdtId = eActualQuantity.attr('pdtId');
					var quantity = eActualQuantity.text();
					var input =$('<input type="text" value="'+quantity+'" style="display: inline-block; border: 1px solid gray">');
					input.width(width -2);
					input.height(height -2);
					eActualQuantity.text('');
					eActualQuantity.append(input);
					input.focus();
					input.blur(function(){
						var n_quantity = $(this).val();
						if ($.trim(n_quantity) == '')
							n_quantity = 0;
						if (quantity != n_quantity){
							$.ajax({
								url:getProjectName() + "/pdtinv/saveInventoryItem.do",
								type: 'post',
								data: {productId: pdtId, quantity: n_quantity},
								success: function(){
									input.remove();
									eActualQuantity.text(n_quantity);
									var eExpectQuantity = $td.prev();
									var eProfitLoss = $td.next();
									var profitLoss = n_quantity - eExpectQuantity.text();
									if (profitLoss > 0){
										eProfitLoss.html('<span style="color: green">'+ profitLoss +'</span>');
									}
									
									if (profitLoss < 0){
										eProfitLoss.html('<span style="color: red">'+ profitLoss +'</span>');
									}
								}
							});
						}else{
							input.remove();
							eActualQuantity.text(quantity);
						}
					});
				}
			});
		}
		
		var doSave = function(){
			$('#btn_save').click(function(){
				Ewin.confirm({message: "盘点信息保存后将不可以修改，确定现在保存盘点信息吗？"}).on(function(e){
					if (!e){
						return;
					}
					$.ajax({
						url: getProjectName() + "/pdtinv/inventory.do",
						success: function(){
							Ewin.toast('成品盘点数据成功');
							$("#tbl_inventory").bootstrapTable("refresh", {url: getProjectName() + "/pdtinv/loadSystemInventory.do"});
						}
					});
				});
			});
		}
		
		var doExport = function(){
			$('#btn_export').click(function(){
				var code = $('#filter_pdtCode').val();
                var ignore0storage = $('#filter_ignore0storage:checked').val();
                if (!ignore0storage)
                	ignore0storage = 0;
            	var url = getProjectName() + "/pdtinv/exportInventory.do?code=" + code + "&ignore0storage=" + ignore0storage;
            	window.open(url);
			});
		}
		
		var doImport = function(){
			$('#btn_import').click(function(){
				ImportData.show({url: getProjectName() + "/pdtinv/importInventory.do",
					templaterName: getProjectName() + "/templater/import/成品盘点.xls",
					callback: function(){
						$("#tbl_inventory").bootstrapTable("refresh", {url: getProjectName() + "/pdtinv/loadSystemInventory.do", cache: false});
					}
				});
			});
		}
		
		var doReset = function(){
			$('#btn_reset').click(function(){
				Ewin.confirm({message: "确定删除所有盘点库存吗？"}).on(function(e){
					if (!e){
						return;
					}
					$.ajax({
						url: getProjectName() + "/pdtinv/reset.do",
						success: function(){
							$("#tbl_inventory").bootstrapTable("refresh", {url: getProjectName() + "/pdtinv/loadSystemInventory.do"});
						}
					});
				});
			});
		}
		
		return {
			init: function(){
				loadSystemStorage();
				doQuery();
				cashActualQuantity();
				doSave();
				doExport();
				doImport();
				doReset();
			}
		}
	}
	
	
	/*-----------------------------------------------------------------------------------*/
	/*	初始化历史盘点记录模块
	/*-----------------------------------------------------------------------------------*/
	var initHisProductInventory = function(){
		
		var th = [];
		
		var initPeriod = function(){
			var date = new Date();
			var year = date.getFullYear();
			$('#filter_speriod').val(year + '-' + '01');
			$('#filter_eperiod').val(year + '-' + '12');
		}
		
		var loadInventoryTable = function(){			
			$.ajax({
				url: getProjectName() + "/pdtinv/getHistroyInventory.do",
				data: {startPeriod: $('#filter_speriod').val(), endPeriod: $('#filter_eperiod').val()},
				success: function(result){
					th = [];
					th.push({
						field: 'pdtCode',
						title: '成品货号'
					});
					th.push({
						field: 'pdtModel',
						title: '成品含量'
					});
					$.each(result, function(i, inv){
						th.push({
							field: 'storageMap.'+inv.id,
							title: inv.inventoryDate,
		                    valign:"middle",
		                    align:"center"
						});
					});
					
					
					$("#tbl_inventory").bootstrapTable({
						url: getProjectName() + "/pdtinv/getHistroyInventoryDetail.do",
						method: "get",
						pagination: true,
						sidePagination : "server",
						pageSize : 50,
						pageList : [50, 100, 200],
						columns: th,
				        queryParams: function(params){
				        	return {
				        		pageSize: params.limit,
				                pageOffset: params.offset,  
				        		startPeriod: $('#filter_speriod').val(),
				        		endPeriod: $('#filter_eperiod').val(),
				        		code: $('#filter_pdtCode').val()
				            }
				        }
					});
				}
			});			
		}
		
		var doQuery = function(){
			$('#btn_search').click(function(){
				$.ajax({
					url: getProjectName() + "/pdtinv/getHistroyInventory.do",
					data: {startPeriod: $('#filter_speriod').val(), endPeriod: $('#filter_eperiod').val()},
					success: function(result){
						th = [];
						th.push({
							field: 'pdtCode',
							title: '成品货号'
						});
						th.push({
							field: 'pdtModel',
							title: '成品含量'
						});
						$.each(result, function(i, inv){
							th.push({
								field: 'storageMap.'+inv.id,
								title: inv.inventoryDate,
			                    valign:"middle",
			                    align:"center"
							});
						});
						$("#tbl_inventory").bootstrapTable("refresh", {url: getProjectName() + "/pdtinv/getHistroyInventoryDetail.do"});
					}
				});
			});
		}
		
		return {
			init: function(){
				initPeriod();
				loadInventoryTable();
				doQuery();
			}
		}
	}
	
	/*-----------------------------------------------------------------------------------*/
	/*	初始化单个成品出入明细模块
	/*-----------------------------------------------------------------------------------*/
	var initSingleProductInOut = function(){
		var initPeriod = function(){
			var now = Format(new Date(), 'yyyy-MM');
			var befor = increaseMonth(now, -3);
			
			$('#filter_speriod').val(befor);
			$('#filter_eperiod').val(now);
			
			$('.simplemonth span.btn').click(function(){
				var monthEle = $(this).parent().find('input');
				var curMon = monthEle.val();
				
				if (curMon == ''){
					monthEle.val(Format(new Date(), 'yyyy-MM'));
				}else {
					if($(this).hasClass('btn-up')){
						monthEle.val(increaseMonth(curMon, -1));
					}
					if($(this).hasClass('btn-down')){
						monthEle.val(increaseMonth(curMon, 1));
					}
				}
			});
		}
		
		var loadInoutTable = function(){
			$("#tbl_pdt_inout").bootstrapTable({
				url: getProjectName() + "/pdtio/getInOutByProduct.do",
				method: "get",
				pagination: false,
				columns: [{
		            field: 'genDate',
		            title: '发生日期'
		        }, {
		            field: 'bill.number',
		            title: '关联单据',
		            formatter: function(value, row, index){
		            	if (row.bill){
		            		if (value.indexOf('CPRKD') == 0)
		            			return '<a opt="link_in" rowid="'+row.bill.id+'">'+value+'</a>';
		            		if (value.indexOf('CPCKD') == 0)
		            			return '<a opt="link_out" rowid="'+row.bill.id+'">'+value+'</a>';
		            	}else{
		            		return '';
		            	}
		            }
		        }, {
		            field: 'summary',
		            title: '摘要',
		            formatter: function(value, row, index){
		            	if (row.bill){
		            		if (row.bill.number.indexOf('CPCKD') == 0){
		            			if (value == '1')
		            				return '发货出库';
		            			if (value == '2')
		            				return '其他出库';
		            		}
		            			
		            		if (row.bill.number.indexOf('CPRKD') == 0){
		            			if (value == '1')
		            				return '发货出库';
		            			if (value == '2')
		            				return '其他出库';
		            		}
		            			
		            	}else{
		            		return value;
		            	}
		            }
		        }, {
		            field: 'inQuantity',
		            title: '入库数量'
		        },{
		            field: 'outQuantity',
		            title: '出库数量'
		        }],
		        queryParams: function(params){
		        	return {
		        		productId: $('#filter_product').val(),
		        		sPeriod: $('#filter_speriod').val(),
		        		ePeriod: $('#filter_eperiod').val()
		            }
		        }
			});
		}
		
		var doQuery = function(){
			$('#btn_search').click(function(){
				$("#tbl_pdt_inout").bootstrapTable("refresh", {url: getProjectName() + "/pdtio/getInOutByProduct.do"});
			});
		}
		
		var initOtherOpt = function(){
			$.getJSON(
				getProjectName() + "/pdt/query.do",
				function(result){
					$.each(result, function(index, pdt){
						$('#filter_product').append('<option value="'+pdt.id+'">'+pdt.name+'</option>');
					});
					$("#filter_product").select2({
						placeholder: "请选择成品",
						allowClear: true
					});
				}
			);
			
			$("#filter_product").change(function(){
				$('#label_pdtCode').text($(this).find("option:selected").text());
			});
			
			$('#tbl_pdt_inout').on('click', 'a', function(){
				var opt = $(this).attr('opt');
				var id = $(this).attr('rowId');
				
				if (opt == 'link_in'){
					var nthTabs = window.parent.getTabs();
			        nthTabs.addTab({
			        	id:'0301',
			            title:'成品入库',
			            active:true,
			            allowClose:true,
			            content:'pdt_in_bill.html',
			        });
			        
			        setTimeout(function(){
			        	window.parent.$('#if0301')[0].contentWindow.fillBill(id);
			        }, 1000);
				}
				
				if (opt == 'link_out'){
					var nthTabs = window.parent.getTabs();
			        nthTabs.addTab({
			        	id:'0302',
			            title:'成品出库',
			            active:true,
			            allowClose:true,
			            content:'pdt_out_bill.html',
			        });
			        
			        setTimeout(function(){
			        	window.parent.$('#if0302')[0].contentWindow.fillBill(id);
			        }, 1000);
				}
			});
		}
		
		var doExport = function(){
            $('#btn_export').click(function(){
            	var productId = $('#filter_product').val();
            	var startPeriod = $('#filter_speriod').val();
        		var endPeriod = $('#filter_eperiod').val();
            	var url = getProjectName() + "/pdtio/exportInOutByProduct.do?pdtId=" + productId + "&startPeriod=" + startPeriod + "&endPeriod=" + endPeriod;
            	window.open(url);
            });
		}
		
		return {
			init: function(){
				initPeriod();
				loadInoutTable();
				doQuery();
				initOtherOpt();
				doExport();
			}
		}
	}
	
	
	/*-----------------------------------------------------------------------------------*/
	/*	初始化原材料出入汇总模块
	/*-----------------------------------------------------------------------------------*/
	var initGlobalProductInOut = function(){
		var initPeriod = function(){
			var now = Format(new Date(), 'yyyy-MM');
			var befor = increaseMonth(now, -3);
			
			$('#filter_speriod').val(befor);
			$('#filter_eperiod').val(now);
			
			$('.simplemonth span.btn').click(function(){
				var monthEle = $(this).parent().find('input');
				var curMon = monthEle.val();
				
				if (curMon == ''){
					monthEle.val(Format(new Date(), 'yyyy-MM'));
				}else {
					if($(this).hasClass('btn-up')){
						monthEle.val(increaseMonth(curMon, -1));
					}
					if($(this).hasClass('btn-down')){
						monthEle.val(increaseMonth(curMon, 1));
					}
				}
			});
		}
		
		var thL1 = [];
		var thL2 = [];
		var refreshTabelHead = function(){
			thL1 = [];
			thL2 = [];
			var sPeriod = $('#filter_speriod').val();
			var ePeriod = $('#filter_eperiod').val();
			
			var endMonth = parseInt(ePeriod.substring(0, 4)) * 12 + parseInt(ePeriod.substring(5));
			var startMonth = parseInt(sPeriod.substring(0, 4)) * 12 + parseInt(sPeriod.substring(5));
			
			var numMonth = endMonth - startMonth;
			
			thL1.push({
				field: 'pdtCode',
                title: "成品货号",
                valign:"middle",
                align:"center",
                colspan: 1,
                rowspan: 2
			});
			for (var i = startMonth; i <= endMonth; i++){
				var td_year = parseInt(i / 12);
				var td_month = i % 12;
				if (td_month == 0){
					td_year = td_year - 1;
					td_month = 12;
				}
				thL1.push({
					title: td_year + '年' + td_month + '月',
                    valign:"middle",
                    align:"center",
                    colspan: 2,
                    rowspan: 1
				});
			}
			thL1.push({
				field: 'storage',
                title: "当前库存",
                valign:"middle",
                align:"center",
                colspan: 1,
                rowspan: 2
			});
			
			for (var i = startMonth; i <= endMonth; i++){
				var td_year = parseInt(i / 12);
				var td_month = i % 12;
				if (td_month == 0){
					td_year = td_year - 1;
					td_month = 12;
				}
				
				var period = td_year + '' + (td_month < 10 ? '0'+ td_month : td_month);
				thL2.push({
					field: 'iomap.'+period+'.inQuantity',
		            title: '入库数'
				});
				thL2.push({
					field: 'iomap.'+period+'.outQuantity',
		            title: '出库数'
				});
			}
		}
		
		var loadInoutTable = function(){
			refreshTabelHead();
			$("#tbl_pdt_inout").bootstrapTable({
				url: getProjectName() + "/pdtio/getGlobalInOutPageResult.do",
				method: "get",
				pagination: true,
				sidePagination : "server",
				pageSize : 50,
				pageList : [50, 100, 200],
				columns: [thL1, thL2],
		        queryParams: function(params){
		        	return {
		        		pageSize: params.limit,
		                pageOffset: params.offset,  
		        		startPeriod: $('#filter_speriod').val(),
		        		endPeriod: $('#filter_eperiod').val()
		            }
		        }
			});
		}
		
		var doQuery = function(){
			$('#btn_search').click(function(){
				refreshTabelHead();
				$("#tbl_pdt_inout").bootstrapTable("refreshOptions", {columns: [thL1, thL2]});
			});
		}
		
		var doExport = function(){
            $('#btn_export').click(function(){
            	var startPeriod = $('#filter_speriod').val();
        		var endPeriod = $('#filter_eperiod').val();
            	var url = getProjectName() + "/pdtio/exportGlobalInOut.do?startPeriod=" + startPeriod + "&endPeriod=" + endPeriod;
            	window.open(url);
            });
		}
		
		return {
			init: function(){
				initPeriod();
				loadInoutTable();
				doQuery();
				doExport();
			}
		}
	}
	
	
	/*-----------------------------------------------------------------------------------*/
	/*	初始化会计科目模块
	/*-----------------------------------------------------------------------------------*/	
	var initFinSubject = function(){
		var loadSubjectTable = function(){
			$("#tbl_subject").bootstrapTable({
				url: getProjectName() + "/finsub/query.do",
				method: "get",
				pagination: false,
				clickToSelect: true,
				singleSelect: true,   
				columns: [{
                    checkbox: true
                }, {
		            field: '',
		            title: '科目编号',
		            formatter: function(value, row, index){
		            	var indent = '';
		            	
		            	var indentLevel = row.code.length - 4;
		            	for (var i = 0; i< indentLevel; i++){
		            		indent += '&nbsp;&nbsp;';
		            	}
		            	return indent + row.code;
		        	}
		        }, {
		            field: '',
		            title: '科目名称',
		            formatter: function(value, row, index){
		            	var indent = '';
		            	
		            	var indentLevel = row.code.length - 4;
		            	for (var i = 0; i< indentLevel; i++){
		            		indent += '&nbsp;&nbsp;';
		            	}
		            	return indent + row.name;
		        	}
		        }, {
		            field: 'remark',
		            title: '备注'
		        }, {
		        	field: '',
		        	title: '操作',
		        	formatter: function(value, row, index){
		        		return '<a opt="update" rowid="'+row.id+'">修改</a>&nbsp;<a opt="delete" rowid="'+row.id+'">删除</a>';
		        	}
		        }],
		        queryParams: function(params){
		        	return {
		        		subCategory: $("#filter_subCtg").val(),
		        		subName: $("#filter_name").val()
		            }
		        }
			})
		}
		
		var doQuery = function(){
			$('button[name="btn_ctg"]').click(function(){
				var code = $(this).attr('code');
				$(this).addClass('active');
				$('button[name="btn_ctg"]').not(this).removeClass('active');
				$('#filter_subCtg').val(code);
				$("#tbl_subject").bootstrapTable("refresh", {url: getProjectName() + "/finsub/query.do", cache: false});
			});
			$('#btn_search').click(function(){
				$("#tbl_subject").bootstrapTable("refresh", {url: getProjectName() + "/finsub/query.do", cache: false});
			})
		}
		
		var initFinSubValidator = function(_code){
			$("#subjectForm").bootstrapValidator({
				fields: {
					code : {validators: {notEmpty : {}, 
						callback: {
							message: '编码已存在，请重新输入',
							callback: function(value, validator){
								if (value == '' || value == _code){
									return true;
								}else{
									var res;
									$.ajax({
										url: getProjectName()+"/finsub/validateUnique.do",
										data: {code : $("#input_code").val()},
										async:false, 
										success: function(result){
											res = result.valid;
										}
									});
									return res;
								}
							}
						}}
					},
		        	name : {validators: {notEmpty : {}}}
		        }
			});
		}
		
		var doSave = function(code){
			$("#btn_save").click(function(){
				initFinSubValidator(code);
				var bootstrapValidator = $("#subjectForm").data('bootstrapValidator');
				bootstrapValidator.validate();
				if(!bootstrapValidator.isValid()){
					return;
				}	
				
				var url = getProjectName()+"/finsub/addSubject.do";
				if ($('input[name="id"]').val() != ''){
					url = getProjectName()+"/finsub/updateSubject.do";
				}
				$("#subjectForm").ajaxSubmit({
					type: "post",
					url: url, 
					success:function(result){
						Ewin.toast('保存成功');
						$('#fin_sub_edit').modal('hide');
						$("#tbl_subject").bootstrapTable("refresh", {url: getProjectName() + "/finsub/query.do", cache: false});
					}
				});
			});
		}
		
		var initEdit = function(){
		    $('#btn_add').click(function(){
		    	var ctg = $('#filter_subCtg').val();
		    	var pid = '0';
		    	var ptext = '';
		    	
		    	var selectContent = $("#tbl_subject").bootstrapTable('getSelections')[0];
	            if(selectContent) {
	            	pid = selectContent.id;
	            	ptext = selectContent.code + ' ' + selectContent.name;
	            }
		    	Ewin.load({id: 'fin_sub_edit', title: '新的会计科目', url: 'fragment/fin_sub_edit.html', callback: function(){
					fillForm($("#subjectForm"), {category: ctg, 'parent.id': pid, parentDesc: ptext});
					doSave();
				}});
		    });
		    
		    $('#tbl_subject').on('click', 'a', function(){
		    	var opt = $(this).attr('opt');
				var id = $(this).attr('rowId');
				
				if (opt == 'update'){
					var ctg = $('#filter_subCtg').val();
					$.ajax({
						url: getProjectName() + "/finsub/getById.do?id="+id,
						success: function(subject){
							Ewin.load({id: 'fin_sub_edit', title: '修改会计科目', url: 'fragment/fin_sub_edit.html', callback: function(){
								fillForm($("#subjectForm"), {id: subject.id, category: ctg, 'parent.id': subject.parent.id, parentDesc: subject.parent.code + ' ' + subject.parent.name, code: subject.code, name: subject.name, remark: subject.remark});
								doSave(subject.code);
							}});
						}
					});
				}
				if (opt == 'delete'){
					Ewin.confirm({message: "确定要删除该会计科目吗？"}).on(function(e){
						if (!e){
							return;
						}
						$.ajax({
							url: getProjectName() + "/finsub/deleteSubject.do?id="+id,
							success: function(){
								Ewin.toast('会计科目删除成功');
								$("#tbl_subject").bootstrapTable("refresh", {url: getProjectName() + "/finsub/query.do", cache: false});
							}
						});
					});
				}
			});
		}
		
		var doExport = function(){
			$('#btn_export').click(function(){
				var url = getProjectName() + "/finsub/exportSubject.do?subName="+$("#filter_name").val();
            	window.open(url);
			});
		}
		
		var doImport = function(){
			$('#btn_import').click(function(){
				ImportData.show({url: getProjectName() + "/finsub/importSubject.do",
					templaterName: getProjectName() + "/templater/import/会计科目列表.xls",
					callback: function(){
						$("#tbl_subject").bootstrapTable("refresh", {url: getProjectName() + "/finsub/query.do", cache: false});
					}
				});
			});
		}
		
		return {
			init: function(){
				loadSubjectTable();
				doQuery();
				initEdit();
				doExport();
				doImport();
			}
		}
	}
	
	
	/*-----------------------------------------------------------------------------------*/
	/*	初始化会计科目设置模块
	/*-----------------------------------------------------------------------------------*/	
	var initFinSubjectConfig = function(){
		var loadSubjectTable = function(){
			$("#tbl_fin_subject").bootstrapTable({
				url: getProjectName() + "/finsub/queryConfig.do",
				method: "get",
				pagination: false,
				clickToSelect: true,
				singleSelect: true,   
				columns: [{
		            field: '',
		            title: '科目编号',
		            formatter: function(value, row, index){
		            	var indent = '';
		            	
		            	var indentLevel = row.code.length - 4;
		            	for (var i = 0; i< indentLevel; i++){
		            		indent += '&nbsp;&nbsp;';
		            	}
		            	return indent + row.code;
		        	}
		        }, {
		            field: '',
		            title: '科目名称',
		            formatter: function(value, row, index){
		            	var indent = '';
		            	
		            	var indentLevel = row.code.length - 4;
		            	for (var i = 0; i< indentLevel; i++){
		            		indent += '&nbsp;&nbsp;';
		            	}
		            	return indent + row.name;
		        	}
		        }, {
		            field: 'initBalance',
		            title: '初始余额',
	            	formatter: function(value, row, index){
		            	if (value == 0)
		            		return '<span name="initBalance" subId="'+row.id+'"></span>';
		            	else
		            	    return '<span name="initBalance" subId="'+row.id+'">'+value+'</span>';
		        	},
		        	width: 150
		        }, {
		            field: 'privateSubject',
		            title: '非公开科目',
	            	formatter: function(value, row, index){
		            	if (value == 1)
		            		return '<input type="checkbox" name="privateSubject" value="1" subId="'+row.id+'" checked>';
		            	else
		            	    return '<input type="checkbox" name="privateSubject" value="1" subId="'+row.id+'">';
		        	},
		        	width: 150
		        }],
		        queryParams: function(params){
		        	return {
		        		subCategory: $("#filter_subCtg").val(),
		        		subName: $("#filter_name").val()
		            }
		        }
			})
		}
		
		var doQuery = function(){
			$('button[name="btn_ctg"]').click(function(){
				var code = $(this).attr('code');
				$(this).addClass('active');
				$('button[name="btn_ctg"]').not(this).removeClass('active');
				$('#filter_subCtg').val(code);
				$("#tbl_fin_subject").bootstrapTable("refresh", {url: getProjectName() + "/finsub/queryConfig.do", cache: false});
			});
			$('#btn_search').click(function(){
				$("#tbl_fin_subject").bootstrapTable("refresh", {url: getProjectName() + "/finsub/queryConfig.do", cache: false});
			})
		}
		
		var doEditConfig = function(code){
			$('#tbl_fin_subject').on('click', 'input[type="checkbox"]', function(){
				var subjectId = $(this).attr('subId');
				var privateSubject = 0;
				if($(this).is(':checked')){
					privateSubject = 1;
				}
				$.ajax({
					url: getProjectName() + "/finsub/setPrivateSubject.do",
					type: 'post',
					data: {subId: subjectId, privateSubject: parseInt(privateSubject)},
					dataType: 'text',
					success: function(){
						
					}
				});
			});
			
			$('#tbl_fin_subject').on('click', 'td', function(){
				if ($(this).children('span[name="initBalance"]').length == 1){
					if ($(this).children('input').length == 1){
						$(this).children('input').focus();
						return;
					}
					var td = $(this);
					td.css('padding', '1px');
					var width = td.width();
					var height = td.height();
					var text = td.children('span').text();
					var subjectId = td.children('span').attr('subId');
										
					var _input = $('<input type="text" style="height: '+(height-5)+'px; width: '+(width-4)+'px; border: 1px solid dodgerblue; ">');
					if (text!=''){
						_input.val(text);
						td.children('span').remove();
					}
					td.append(_input);
					_input.focus();

					_input.blur(function(){
						var val = $(this).val();
						$(this).remove();
						
						if ($.trim(val) != text){
							var initBalance = 0;
							if (val != ''){
								initBalance = val;
							}
							if (parseInt(val) == 0  && text == ''){
								td.html('<span name="initBalance" subId="'+subjectId+'"></span>');
							}else{
								$.ajax({
									url: getProjectName() + "/finsub/setInitBalance.do",
									type: 'post',
									data: {subId: subjectId, initBalance: initBalance},
									dataType: 'text',
									success: function(){
										if (initBalance == 0){
											td.html('<span name="initBalance" subId="'+subjectId+'"></span>');
										}else{
											td.html('<span name="initBalance" subId="'+subjectId+'">'+initBalance+'</span>');
										}
									}
								});
							}
							
						}else{
							td.html('<span name="initBalance" subId="'+subjectId+'">'+text+'</span>');
						}
					});
				}
			});
		}	
		
		return {
			init: function(){
				loadSubjectTable();
				doQuery();
				doEditConfig();
			}
		}
	}
	
	/*-----------------------------------------------------------------------------------*/
	/*	初始化凭证录入模块
	/*-----------------------------------------------------------------------------------*/	
	var initVoucher = function(){
		var bill;
		var initBill = function(){
			$.ajaxSettings.async = false;
			$.getJSON("../templater/bill/voucher.json", "", function(data){
        		bill = $('#voucherBill').eBill(data);	
			});
			$.ajaxSettings.async = true;
			getNextNumber();
			$('select[name="word"]').change(function(){
				getNextNumber();
			});
			$('input[name="billDate"]').change(function(){
				getNextNumber();
			});
		}
		
		
		var editBill = function(){
			$('#btn_save').click(function(){
				if ($("input[name='id']").val() == ''){
					bill.commit(getProjectName()+'/voc/addVoucher.do', '记账凭证新增成功');
				}else{
					bill.commit(getProjectName()+'/voc/updateVoucher.do', '记账凭证修改成功', false);
				}
        	});
			
			$('#btn_reset').click(function(){
				bill.resetBill();
			});
			
			$('#btn_print').click(function(){
				$('#panel_voucher').print({
					globalStyles: true,
					mediaPrint: false,
					stylesheet: null,
					noPrintSelector: ".no-print",
					iframe: true,
					append: null,
					manuallyCopyFormValues: true,
					deferred: $.Deferred()
				});
			});
		}
		
		var getNextNumber = function(){
			$.ajax({
				url: getProjectName()+'/voc/getNextNumber.do',
				data: {word: $('select[name="word"]').val(), 
					   billDate: $('input[name="billDate"]').val()},
				success: function(result){
					$('input[name="number"]').val(result);
				}
			});
		}
		
		return {
			init: function(){
				initBill();
				editBill();
				return bill;
			}
		}
	}
	
	/*-----------------------------------------------------------------------------------*/
	/*	初始化历史凭证模块
	/*-----------------------------------------------------------------------------------*/	
	var initVoucherList = function(){
		var loadFilter = function(){
			var filterWin = Ewin.load({id: 'voc_filter', title: '更多查询', url: 'fragment/voc_filter.html', rmvWin: false, initShow: false, callback: function(){
				$.getJSON(
					getProjectName() + "/voc/allBillPeriod.do",
					function(result){
						$.each(result, function(key, value){
							$('#filter_sDate').prepend('<option value="'+key+'">'+value+'</option>');
							$('#filter_eDate').prepend('<option value="'+key+'">'+value+'</option>');
						});
						$("#filter_sDate").select2({
							placeholder: "",
							allowClear: true
						});
						$("#filter_eDate").select2({
							placeholder: "",
							allowClear: true
						});
					}
				);
				
				$("#btn_query").click(function(){
					queryVoucher();
					$('#voc_filter').modal('hide');
				});
			}});
			
			$('#btn_more').click(function(){
				filterWin.modal.modal('show');
			});
		}
		
		var loadVoucherTable = function(){
			$("#tbl_voucher").bootstrapTable({
				url: getProjectName() + "/voc/getPageResult.do",
				method: "get",
				pagination: true,
				sidePagination: "server", 
				clickToSelect: true,
				columns: [{
		            field: 'name',
		            title: '凭证字号',
		            formatter: function(value, row, index){
		            	var word = '';
		            	switch(row.word){
		            		case 1:
		            			word = '记';
		            		break;
		            		case 2:
		            			word = '付';
		            		break;
		            		case 3:
		            			word = '收';
		            		break;
		            		case 4:
		            			word = '转';
		            		break;
		            	}
		            	return word+'字'+row.number+'号';
		        	}
		        }, {
		            field: 'billDate',
		            title: '凭证日期'
		        }, {
		            field: 'summary',
		            title: '摘要'
		        }, {
		            field: 'subject.name',
		            title: '科目'
		        }, {
		            field: 'debitAmount',
		            title: '借方金额'
		        }, {
		            field: 'creditAmount',
		            title: '贷方金额'
		        }, {
		            field: 'billStatus',
		            title: '入库单状态',
		            formatter: function(value, row, index){
		            	if (value == 0)
		            		return '未审核';
		            	if (value == 1)
		            		return '已审核';
		            	return '';
		        	}
		        }, {
		        	field: '',
		        	title: '操作',
		        	formatter: function(value, row, index){
		        		return '<a opt="update" rowid="'+row.id+'">修改</a>&nbsp;<a opt="delete" rowid="'+row.id+'">删除</a>';
		        	}
		        }],
		        queryParams: function(params){
		        	return {
		                pageSize: params.limit,
		                pageOffset: params.offset,   
		                billStatus: $("#filter_bStatus").val(),
		                voucherWord: $("#filter_name").val(),
		                startVouchernumber: $("#filter_snum").val(),
		                endVouchernumber: $("#filter_enum").val(),
		                startBillPeriod: $("#filter_sDate").val(),
		                endBillPeriod: $("#filter_eDate").val(),
		                voucherSummary: $("#filter_summary").val(),
		                subName: $("#filter_subName").val()
		            }
		        },
		        onLoadSuccess: function (data) {
		        	mergeCells($('#tbl_voucher'));
	            },
			});
		}
		
		var mergeCells = function(target) {
			var rows = target.find('tr');
		    var _row;
		    var rowspan = 1;
			$.each(rows, function(i, row){
				if (i == 0){
					return;
				}else if (i == 1){
					_row = row;
					return;
				}else{
					if ($($(row).find('td')[0]).html() == $($(_row).find('td')[0]).html()){
						$($(row).find('td')[0]).remove();
						$($(row).find('td')[0]).remove();
						$($(row).find('td')[4]).remove();
						$($(row).find('td')[4]).remove();
						rowspan++;
						if (i == rows.length - 1){
							$($(_row).find('td')[0]).attr('rowspan', rowspan);
							$($(_row).find('td')[1]).attr('rowspan', rowspan);
							$($(_row).find('td')[6]).attr('rowspan', rowspan);
							$($(_row).find('td')[7]).attr('rowspan', rowspan);
						}
					}else{
						if (rowspan > 1){
							$($(_row).find('td')[0]).attr('rowspan', rowspan);
							$($(_row).find('td')[1]).attr('rowspan', rowspan);
							$($(_row).find('td')[6]).attr('rowspan', rowspan);
							$($(_row).find('td')[7]).attr('rowspan', rowspan);
							rowspan = 1;
						}
						_row = row;
					}
				}
				
		    });
		}
		
		var doQuery = function(){
			$('#btn_search').click(function(){
				var period = $('#filter_billPeriod').val();
				$('#filter_sDate').val(period);
				$('#filter_eDate').val(period);
				$('#filter_sDate').select2();
				$('#filter_eDate').select2();
				queryVoucher();
				$('#filter_billPeriod').val('');
			});
		}
		
		var queryVoucher = function(){
			createFilterTip({
				assWin: $('#voc_filter'), 
				items: [{assId: 'filter_bStatus', label: '入库单状态', rule: '等于', ignore: '-1'}, 
					    {assId: 'filter_name', label: '凭证字', rule: '等于', ignore: ''},
				        {assId: 'filter_snum', label: '凭证号', rule: '大于'},
				        {assId: 'filter_enum', label: '凭证号', rule: '小于'},
				        {assId: 'filter_sDate', label: '开始账期', rule: '', eType: 'select2'},
				        {assId: 'filter_eDate', label: '结束账期', rule: '', eType: 'select2'},				        
				        {assId: 'filter_summary', label: '摘要', rule: '包含'},
				        {assId: 'filter_subName', label: '科目名称', rule: '包含'}],
		        changeCall: function(){
		        	$("#tbl_voucher").bootstrapTable("refresh", {url: getProjectName() + "/voc/getPageResult.do", cache: false});
	        }});
			$("#tbl_voucher").bootstrapTable("refresh", {url: getProjectName() + "/voc/getPageResult.do", cache: false});
		}
		
		var initEditVoucher = function(){
			$('#tbl_voucher').on('click', 'a', function(){
				var opt = $(this).attr('opt');
				var id = $(this).attr('rowId');
				if (opt == 'update'){
					var nthTabs = window.parent.getTabs();
			        nthTabs.addTab({
			        	id:'0302',
			            title:'账务.凭证录入',
			            active:true,
			            allowClose:true,
			            content:'voc_bill.html',
			        });
			        
			        setTimeout(function(){
			        	window.parent.$('#if0302')[0].contentWindow.fillBill(id);
			        }, 1000);
				}
				if (opt == 'delete'){
					Ewin.confirm({message: "确定要删除该凭证吗？"}).on(function(e){
						if (!e){
							return;
						}
						if (window.parent.$('#if0302')[0]){
							var billId = window.parent.$('#if0302')[0].contentWindow.getBillId();
							if (billId == id){
								Ewin.alert("当前待删除凭证正在修改中，无法删除");
								return;
							}
						}
						$.ajax({
							url: getProjectName() + "/voc/deleteVoucher.do?id="+id,
							success: function(){
								Ewin.toast('凭证删除成功');
								queryVoucher();
							}
						});
					});
				}
			});
			
			$('#btn_add').click(function(){
				var nthTabs = window.parent.getTabs();
		        nthTabs.addTab({
		        	id:'0302',
		            title:'账务.凭证录入',
		            active:true,
		            allowClose:true,
		            content:'voc_bill.html',
		        });
			});
		}
		
		return {
			init: function(){
				loadFilter();
				loadVoucherTable();
				doQuery();
				initEditVoucher();
			}
		}
	}
	
	/*-----------------------------------------------------------------------------------*/
	/*	初始化明细分类账模块
	/*-----------------------------------------------------------------------------------*/	
	var initSubsidiaryLedger = function(){
		var loadAssciation = function(){
			/*获得第一个会计科目*/
			$.ajax({
				url: getProjectName() + '/finsub/getFirstSubject.do',
				async:false,
				success: function(result){
					$("#filter_subject").val(result.id);
					$("#label_subject").text(result.fullName);
				}
			})
			/*加载账期*/
			$.ajax({
				url: getProjectName() + '/subLedger/allBillPeriod.do',
				async:false,
				success: function(result){
					$.each(result, function(key, value){
						$('#filter_billPeriod').prepend('<option value="'+key+'">'+value+'</option>');
					});
					$('#filter_billPeriod').find('option:eq(0)').attr('selected','selected');
					$("#filter_billPeriod").select2({
						placeholder: "选择账期",
						allowClear: false,
						width: 150
					});
				}
			});
			
			/*加载所有的会计科目*/
			$("#panel-subject").slimScroll({
				height: "280px",
				width: "500px",
				color: '#54667a'
			});
			$('#subject_tree').data('jstree', false).empty().jstree({
				plugins: ['types', 'state', 'wholerow'],
				'types': {
					'default': {
						'icon': false
					}
				},
				'core': {
					'data': {
						'url': getProjectName()+"/finsub/getFullJSTree.do",
						'dataType': 'json'
					}
				}
			}).bind('select_node.jstree', function(e, data){
				var selectedNode = data.instance.get_node(data.selected[0]);
				$("#label_subject").text(selectedNode['a_attr'].fullName);
				$("#filter_subject").val(selectedNode.id);	
				$("#tbl_account").bootstrapTable("refresh", {url: getProjectName() + "/subLedger/getAccount.do", cache: false});
			});
		}
		
		var loadAccountTable = function(){
			$("#tbl_account").bootstrapTable({
				url: getProjectName() + "/subLedger/getAccount.do",
				method: "get",
				pagination: false,  
				columns: [{
					field: 'accountDate',
		            title: '发生日期',
                }, {
		            field: '',
		            title: '凭证字号',
		            formatter: function(value, row, index){
		            	if (row.voucher == null){
		            		return '';
		            	}
		            	return '<a opt="link" rowid="'+row.voucher.id+'">'+row.voucher.word + '字 第' + row.voucher.number + '号'+'<a>';
		        	}
		        }, {
		            field: 'summary',
		            title: '摘要'
		        }, {
		            field: 'debitAmount',
		            title: '借方金额',
		            formatter: function(value, row, index){
		            	if (value == 0){
		            		return '';
		            	}
		            	return value;
		        	}
		        }, {
		            field: 'creditAmount',
		            title: '贷方金额',
	            	formatter: function(value, row, index){
		            	if (value == 0){
		            		return '';
		            	}
		            	return value;
		        	}
		        }, {
		            field: 'balance',
		            title: '余额'
		        }],
		        queryParams: function(params){
		        	return {
		        		subjectId: $("#filter_subject").val(),
		        		billPeriod: $("#filter_billPeriod").val()
		            }
		        }
			})
		}
		
		var initEvent = function(){
			$('#btn_search').click(function(){
				$("#tbl_account").bootstrapTable("refresh", {url: getProjectName() + "/subLedger/getAccount.do", cache: false});
			});
			
			$('#tbl_account').on('click', 'a', function(){
				var opt = $(this).attr('opt');
				var id = $(this).attr('rowId');
				if (opt == 'link'){
					var nthTabs = window.parent.getTabs();
			        nthTabs.addTab({
			        	id:'0302',
			            title:'账务.凭证录入',
			            active:true,
			            allowClose:true,
			            content:'voc_bill.html',
			        });
			        
			        setTimeout(function(){
			        	window.parent.$('#if0302')[0].contentWindow.fillBill(id);
			        }, 1000);
				}
			});
		}
		
		var doExport = function(){
            $('#btn_export').click(function(){
            	var subjectId = $("#filter_subject").val();
        		var billPeriod = $("#filter_billPeriod").val();
            	var url = getProjectName() + "/subLedger/exportAccount.do?subjectId=" + subjectId + "&billPeriod=" + billPeriod;
            	window.open(url);
            });
		}
		
		return {
			init: function(){
				loadAssciation();
				loadAccountTable();
				initEvent();
				doExport();
			}
		};
	}
	
	/*-----------------------------------------------------------------------------------*/
	/*	初始化总分类账模块
	/*-----------------------------------------------------------------------------------*/	
	var initGensidiaryLedger = function(){
		var loadAssciation = function(){
			/*加载账期*/
			$.ajax({
				url: getProjectName() + '/genLedger/allBillPeriod.do',
				async:false,
				success: function(result){
					$.each(result, function(key, value){
						$('#filter_billPeriod').prepend('<option value="'+key+'">'+value+'</option>');
					});
					$('#filter_billPeriod').find('option:eq(0)').attr('selected','selected');
					$("#filter_billPeriod").select2({
						placeholder: "选择账期",
						allowClear: false,
						width: 150
					});
				}
			});
			
		}
		
		var loadAccountTable = function(){
			$("#tbl_account").bootstrapTable({
				url: getProjectName() + "/genLedger/getAccount.do",
				method: "get",
				pagination: false,  
				columns: [{
					field: '',
		            title: '总账科目',
		            formatter: function(value, row, index){
		            	return row.subject.code + ' ' + row.subject.name;
		        	}
                }, {
		            field: 'summary',
		            title: '摘要'
		        }, {
		            field: 'debitAmount',
		            title: '借方金额',
		            formatter: function(value, row, index){
		            	if (value == 0){
		            		return '';
		            	}
		            	return value;
		        	}
		        }, {
		            field: 'creditAmount',
		            title: '贷方金额',
	            	formatter: function(value, row, index){
		            	if (value == 0){
		            		return '';
		            	}
		            	return value;
		        	}
		        }, {
		            field: 'balance',
		            title: '余额'
		        }],
		        queryParams: function(params){
		        	return {
		        		billPeriod: $("#filter_billPeriod").val()
		            }
		        }
			})
		}
		
		var initEvent = function(){
			$('#btn_search').click(function(){
				$("#tbl_account").bootstrapTable("refresh", {url: getProjectName() + "/genLedger/getAccount.do", cache: false});
			});
		}
		
		var doExport = function(){
            $('#btn_export').click(function(){
            	var billPeriod = $("#filter_billPeriod").val();
            	var url = getProjectName() + "/genLedger/exportAccount.do?billPeriod=" + billPeriod;
            	window.open(url);
            });
		}
		
		return {
			init: function(){
				loadAssciation();
				loadAccountTable();
				initEvent();
				doExport();
			}
		};
	}
	
	/*-----------------------------------------------------------------------------------*/
	/*	初始供应商列表模块
	/*-----------------------------------------------------------------------------------*/	
	var initSupplierList = function(){
		var loadSupplierTable = function(){
			$("#tbl_supplier").bootstrapTable({
				url: getProjectName() + "/supp/getPageResult.do",
				method: "get",
				pagination: true,
				sidePagination: "server", 
				clickToSelect: true,
				columns: [{
		            field: 'name',
		            title: '公司名称'
		        }, {
		            field: 'address',
		            title: '公司地址'
		        }, {
		            field: 'fax',
		            title: '传真'
		        }, {
		            field: 'contacts',
		            title: '联系人'
		        }, {
		            field: 'phone',
		            title: '联系电话'
		        }, {
		        	field: 'remark',
		        	title: '备注'
		        }, {
		        	field: '',
		        	title: '操作',
		        	formatter: function(value, row, index){
		        		return '<a opt="update" rowid="'+row.id+'">修改</a>&nbsp;<a opt="delete" rowid="'+row.id+'">删除</a>';
		        	}
		        }],
		        queryParams: function(params){
		        	return {
		                pageSize: params.limit,
		                pageOffset: params.offset,                    
		                suppName: $("#filter_name").val(),
		                suppPhone: $("#filter_phone").val(),
		                suppContacts: $("#filter_contacts").val(),
		                remark: $("#filter_remark").val()
		            }
		        }
			});
		}
		
		var filterWin;
		var initQueryOpt = function(){
			filterWin = Ewin.load({id: 'supp_filter', title: '更多查询', url: 'fragment/supp_filter.html', rmvWin: false, initShow: false, callback: function(){
				$("#btn_query").click(function(){
					querySupplier();
					$('#supp_filter').modal('hide');
				});
			}});
			
			$('#btn_more').click(function(){
				filterWin.modal.modal('show');
			});
			
			$('#btn_search').click(function(){
				querySupplier();
			});
			
		}
		
		var querySupplier = function(){
			createFilterTip({
				assWin: $('#supp_filter'), 
				items: [{assId: 'filter_contacts', label: '联系人', rule: '包含'}, 
					    {assId: 'filter_phone', label: '联系电话', rule: '包含'},
				        {assId: 'filter_remark', label: '备注', rule: '包含'}],
		        changeCall: function(){
		        	$("#tbl_supplier").bootstrapTable("refresh", {url: getProjectName() + "/supp/getPageResult.do", cache: false});
	        }});
			$("#tbl_supplier").bootstrapTable("refresh", {url: getProjectName() + "/supp/getPageResult.do", cache: false});
		}
		
		var doEditSupplier = function(id){
			$('#btn_add').click(function(){					
				Ewin.load({id: 'supp_edit', title: '新的供应商', url: 'fragment/supp_edit.html'});					
			});
			
			$('#tbl_supplier').on('click', 'a', function(){
				var opt = $(this).attr('opt');
				var id = $(this).attr('rowId');
				if (opt == 'update'){
					Ewin.load({id: 'supp_edit', title: '修改供应商', url: 'fragment/supp_edit.html', callback: function(){
						$.ajax({
							url: getProjectName()+"/supp/getById.do?id="+id, 
							success: function(result){
								fillForm($("#supplierForm"), {
									id: result.id, 
									name: result.name, 
									address: result.address, 
									fax: result.fax, 
									contacts: result.contacts, 
									phone: result.phone, 
									remark: result.remark});
							}
						});
					}});
				}
				
				if (opt == 'delete'){
					Ewin.confirm({message: "确定要删除选中的供应商吗？"}).on(function(e){
						if (!e){
							return;
						}
						$.ajax({
							url: getProjectName()+"/supp/delete.do?id="+id,
							type: "get",
							success: function(){
								$("#tbl_supplier").bootstrapTable("refresh", {url: getProjectName() + "/supp/getPageResult.do", cache: false});
							}
						});
					});
				}
			});
		}
		
		var doExport = function(){
			$('#btn_export').click(function(){
				var url = getProjectName() + "/supp/exportSupplier.do?suppName=" + $('#filter_name').val();
            	window.open(url);
			});
		}
		
		var doImport = function(){
			$('#btn_import').click(function(){
				ImportData.show({url: getProjectName() + "/supp/importSupplier.do",
					templaterName: getProjectName() + "/templater/import/供应商列表.xls",
					callback: function(){
						$("#tbl_supplier").bootstrapTable("refresh", {url: getProjectName() + "/supp/getPageResult.do", cache: false});
					}
				});
			});
		}
		
		return {
			init: function(){
				initQueryOpt();
				loadSupplierTable();
				doEditSupplier();
				doExport();
				doImport();
			}
		}
	}
	
	/*-----------------------------------------------------------------------------------*/
	/*	初始客户列表模块
	/*-----------------------------------------------------------------------------------*/	
	var initCustomerList = function(){
		var loadCustomerTable = function(){
			$("#tbl_customer").bootstrapTable({
				url: getProjectName() + "/cust/getPageResult.do",
				method: "get",
				pagination: true,
				sidePagination: "server", 
				clickToSelect: true,
				columns: [{
		            field: 'name',
		            title: '公司名称'
		        }, {
		            field: 'address',
		            title: '公司地址'
		        }, {
		            field: 'fax',
		            title: '传真'
		        }, {
		            field: 'contacts',
		            title: '联系人'
		        }, {
		            field: 'phone',
		            title: '联系电话'
		        }, {
		        	field: 'remark',
		        	title: '备注'
		        }, {
		        	field: '',
		        	title: '操作',
		        	formatter: function(value, row, index){
		        		return '<a opt="update" rowid="'+row.id+'">修改</a>&nbsp;<a opt="delete" rowid="'+row.id+'">删除</a>';
		        	}
		        }],
		        queryParams: function(params){
		        	return {
		                pageSize: params.limit,
		                pageOffset: params.offset,                    
		                custName: $("#filter_name").val(),
		                custPhone: $("#filter_phone").val(),
		                custContacts: $("#filter_contacts").val(),
		                remark: $("#filter_remark").val()
		            }
		        }
			});
		}
		
		var filterWin;
		var initQueryOpt = function(){
			filterWin = Ewin.load({id: 'cust_filter', title: '更多查询', url: 'fragment/cust_filter.html', rmvWin: false, initShow: false, callback: function(){
				$("#btn_query").click(function(){
					queryCustomer();
					$('#cust_filter').modal('hide');
				});
			}});
			
			$('#btn_more').click(function(){
				filterWin.modal.modal('show');
			});
			
			$('#btn_search').click(function(){
				queryCustomer();
			});
			
		}
		
		var queryCustomer = function(){
			createFilterTip({
				assWin: $('#cust_filter'), 
				items: [{assId: 'filter_contacts', label: '联系人', rule: '包含'}, 
					    {assId: 'filter_phone', label: '联系电话', rule: '包含'},
				        {assId: 'filter_remark', label: '备注', rule: '包含'}],
		        changeCall: function(){
		        	$("#tbl_customer").bootstrapTable("refresh", {url: getProjectName() + "/cust/getPageResult.do", cache: false});
	        }});
			$("#tbl_customer").bootstrapTable("refresh", {url: getProjectName() + "/cust/getPageResult.do", cache: false});
		}
		
		var doEditCustomer = function(id){
			$('#btn_add').click(function(){					
				Ewin.load({id: 'cust_edit', title: '新的客户', url: 'fragment/cust_edit.html'});					
			});
			
			$('#tbl_customer').on('click', 'a', function(){
				var opt = $(this).attr('opt');
				var id = $(this).attr('rowId');
				if (opt == 'update'){
					Ewin.load({id: 'cust_edit', title: '修改客户', url: 'fragment/cust_edit.html', callback: function(){
						$.ajax({
							url: getProjectName()+"/cust/getById.do?id="+id, 
							success: function(result){
								fillForm($("#customerForm"), {
									id: result.id, 
									name: result.name, 
									address: result.address, 
									fax: result.fax, 
									contacts: result.contacts, 
									phone: result.phone, 
									remark: result.remark});
							}
						});
					}});
				}
				
				if (opt == 'delete'){
					Ewin.confirm({message: "确定要删除选中的客户吗？"}).on(function(e){
						if (!e){
							return;
						}
						$.ajax({
							url: getProjectName()+"/cust/delete.do?id="+id,
							type: "get",
							success: function(){
								$("#tbl_customer").bootstrapTable("refresh", {url: getProjectName() + "/cust/getPageResult.do", cache: false});
							}
						});
					});
				}
			});
		}
		
		var doExport = function(){
			$('#btn_export').click(function(){
				var url = getProjectName() + "/cust/exportCustomer.do?custName=" + $('#filter_name').val();
            	window.open(url);
			});
		}
		
		var doImport = function(){
			$('#btn_import').click(function(){
				ImportData.show({url: getProjectName() + "/cust/importCustomer.do",
					templaterName: getProjectName() + "/templater/import/客户列表.xls",
					callback: function(){
						$("#tbl_customer").bootstrapTable("refresh", {url: getProjectName() + "/cust/getPageResult.do", cache: false});
					}
				});
			});
		}
		
		return {
			init: function(){
				initQueryOpt();
				loadCustomerTable();
				doEditCustomer();
				doExport();
				doImport();
			}
		}
	}
	
	/*-----------------------------------------------------------------------------------*/
	/*	初始系统用户列表模块
	/*-----------------------------------------------------------------------------------*/	
	var initUserList = function(){
		var loadUserTable = function(){
			$("#tbl_user").bootstrapTable({
				url: getProjectName() + "/user/getPageResult.do",
				method: "get",
				pagination: true,
				sidePagination: "server", 
				clickToSelect: true,
				columns: [{
		            field: 'userName',
		            title: '用户名',
		            width: 100
		        }, {
		            field: 'password',
		            title: '密码',
		            width: 100,
		            formatter: function(value, row, index){
		            	return '******';
		            }
		        }, {
		            field: 'fullName',
		            title: '姓名',
		            width: 100
		        }, {
		            field: '',
		            title: '角色',
		            width: 100,
		            formatter: function(value, row, index){
		            	var roleList = "";
		            	$.each(row.roles, function(i, role){
		            		roleList += "," + role.name;
		            	});
		            	if (roleList.length > 0){
		            		roleList = roleList.substring(1, roleList.length);
		            	}
		        		return roleList;
		        	}
		        }, {
		        	field: '',
		        	title: '操作',
		        	width: 140,
		        	formatter: function(value, row, index){
		        		return '<a opt="update" rowid="'+row.id+'">修改</a>&nbsp;<a opt="delete" rowid="'+row.id+'">删除</a>&nbsp;<a opt="reset" rowid="'+row.id+'">重置密码</a>';
		        	}
		        }, {
		            field: '',
		            title: '',
		            formatter: function(value, row, index){
		            	return '';
		            }
		        }],
		        queryParams: function(params){
		        	return {
		                pageSize: params.limit,
		                pageOffset: params.offset,                    
		                username: $("#filter_name").val()
		            }
		        },
		        onLoadSuccess: function (data) {
		        	var rowNum = $("#tbl_user").find('tr').length - 1;
		        	var firstDataRow = $("#tbl_user").find('tr:nth-child(1)');
		        	var otherDataRow = $("#tbl_user").find('tr').not(firstDataRow);
		        	otherDataRow.find('td:last-child').remove();
		        	firstDataRow.children('td:last-child').attr('rowspan', rowNum);
	            }
			});
		}
		
		var initQueryOpt = function(){
			$('#btn_search').click(function(){
				queryUser();
			});
		}
		
		var loadUserRole = function(user){
			$.ajax({
				url: getProjectName()+"/role/loadRoles.do", 
				success: function(roles){
					var div_roles = $('#div_roles');
					$.each(roles, function(i, role){
						var html = '<div class="checkbox">' +
								     '<label>' +
								       '<input name="rolestr" type="checkbox" value="' + role.id + '"> ' + role.name
								     '</label>' +
								   '</div>';
						div_roles.append(html);
					});
					
					if (user){
						if (user.roles){
							$.each(user.roles, function(i, role){
								div_roles.find('input[name="rolestr"][value="'+role.id+'"]').attr("checked", 'checked');
							});
						}
					}
				}
			});
		}
		
		var queryUser = function(){
			$("#tbl_user").bootstrapTable("refresh", {url: getProjectName() + "/user/getPageResult.do", cache: false});
		}
		
		var doEditUser = function(id){
			$('#btn_add').click(function(){					
				Ewin.load({id: 'user_edit', title: '新的系统用户', url: 'fragment/user_edit.html', callback: function(){
					loadUserRole();
				}});					
			});
			
			$('#tbl_user').on('click', 'a', function(){
				var opt = $(this).attr('opt');
				var id = $(this).attr('rowId');
				if (opt == 'update'){
					Ewin.load({id: 'user_edit', title: '修改用户', url: 'fragment/user_edit.html', callback: function(){
						$.ajax({
							url: getProjectName()+"/user/findById.do?id="+id, 
							success: function(result){
								fillForm($("#userForm"), {
									id: result.id, 
									userName: result.userName, 
									password: result.password, 
									fullName: result.fullName});
								loadUserRole(result);
							}
						});
					}});
				}
				
				if (opt == 'delete'){
					Ewin.confirm({message: "确定要删除选中的用户吗？"}).on(function(e){
						if (!e){
							return;
						}
						$.ajax({
							url: getProjectName()+"/user/delete.do?id="+id,
							type: "get",
							dataType: "text",
							success: function(){
								$("#tbl_user").bootstrapTable("refresh", {url: getProjectName() + "/user/getPageResult.do", cache: false});
							}
						});
					});
				}
				
				if (opt == 'reset'){
					Ewin.confirm({message: "确定要重置选中的用户吗？"}).on(function(e){
						if (!e){
							return;
						}
						$.ajax({
							url: getProjectName()+"/user/resetPassword.do?id="+id,
							type: "get",
							dataType: "text",
							success: function(){
								Ewin.toast("密码重置成功");
							}
						});
					});
				}
			});
		}
		
		var doExport = function(){
			$('#btn_export').click(function(){
				var url = getProjectName() + "/user/exportUser.do?suppName=" + $('#filter_name').val();
            	window.open(url);
			});
		}
		
		var doImport = function(){
			$('#btn_import').click(function(){
				ImportData.show({url: getProjectName() + "/user/importUser.do",
					templaterName: getProjectName() + "/templater/import/系统用户列表.xls",
					callback: function(){
						$("#tbl_user").bootstrapTable("refresh", {url: getProjectName() + "/user/getPageResult.do", cache: false});
					}
				});
			});
		}
		
		return {
			init: function(){
				initQueryOpt();
				loadUserTable();
				doEditUser();
				doExport();
				doImport();
			}
		}
	}

	/*-----------------------------------------------------------------------------------*/
	/*	初始系统角色列表模块
	/*-----------------------------------------------------------------------------------*/	
	var initRoleList = function(){
		var loadRoleTable = function(){
			$("#tbl_role").bootstrapTable({
				url: getProjectName() + "/role/loadRoles.do",
				method: "get",
				pagination: false,
				clickToSelect: true,
				columns: [{
		            field: 'name',
		            title: '角色名',
		            width: 120,
		            formatter: function(value, row, index){
		            	return '<a opt="view" rowid="'+row.id+'">'+value+'</a>';
		        	}
		        }, {
		            field: 'remark',
		            title: '角色说明',
		            width: 300
		        },{
		        	field: '',
		        	title: '操作',
		        	width: 100,
		        	formatter: function(value, row, index){
		        		if (row.buildIn == '1')
		        			return '';
		        		return '<a opt="update" rowid="'+row.id+'">修改</a>&nbsp;<a opt="delete" rowid="'+row.id+'">删除</a>';
		        	}
		        }, {
		            field: '',
		            title: '',
		            formatter: function(value, row, index){
		            	return '';
		            }
		        }],
		        queryParams: function(params){
		        	return {
		                roleName: $("#filter_name").val()
		            }
		        },
		        onLoadSuccess: function (data) {
		        	var rowNum = $("#tbl_role").find('tr').length - 1;
		        	var firstDataRow = $("#tbl_role").find('tr:nth-child(1)');
		        	var otherDataRow = $("#tbl_role").find('tr').not(firstDataRow);
		        	otherDataRow.find('td:last-child').remove();
		        	firstDataRow.children('td:last-child').attr('rowspan', rowNum);
	            }
			});
		}
		
		var initQueryOpt = function(){
			$('#btn_search').click(function(){
				queryRole();
			});
		}
		
		var queryRole = function(){
			$("#tbl_role").bootstrapTable("refresh", {url: getProjectName() + "/role/loadRoles.do", cache: false});
		}
		
		var doEditRole = function(id){
			$('#btn_add').click(function(){					
				Ewin.load({id: 'role_edit', title: '修改角色', url: 'fragment/role_edit.html', callback: function(){
					loadAuthrityTree("");
				}});
			});
			
			$('#tbl_role').on('click', 'a', function(){
				var opt = $(this).attr('opt');
				var id = $(this).attr('rowId');
				if (opt == 'update'){
					Ewin.load({id: 'role_edit', title: '修改角色', url: 'fragment/role_edit.html', callback: function(){
						$.ajax({
							url: getProjectName()+"/role/findById.do?id="+id, 
							success: function(result){
								fillForm($("#roleForm"), {
									id: result.id, 
									name: result.name, 
									remark: result.remark});
								loadAuthrityTree(id);
							}
						});
					}});
				}
				
				if (opt == 'delete'){
					Ewin.confirm({message: "确定要删除选中的角色吗？"}).on(function(e){
						if (!e){
							return;
						}
						$.ajax({
							url: getProjectName()+"/role/delete.do?id="+id,
							type: "get",
							dataType: "text",
							success: function(){
								Ewin.toast("角色删除成功");
								$("#tbl_role").bootstrapTable("refresh", {url: getProjectName() + "/role/loadRoles.do", cache: false});
							}
						});
					});
				}
				if (opt == 'view'){
					Ewin.load({id: 'role_edit', title: $(this).text(), url: 'fragment/role_view.html', callback: function(){
						$.ajax({
							url: getProjectName()+"/role/findById.do?id="+id, 
							success: function(result){
								$('#div_remark').html(result.remark);
								loadAuthrityTree(id);
							}
						});
					}});
				}
			});
			
			var loadAuthrityTree = function(id){
				var tree = $('#authorityTree');
				tree.data('jstree', false).empty();
				tree.jstree({
				    'core' : {
				      'data' : {
				        "url" : getProjectName() + "/role/roleAuthority.do?roleId="+id,
				        "dataType" : "json"
				      }
				    },
				    "plugins" : ["wholerow","checkbox" ]
				});	
			}
		}
		
		return {
			init: function(){
				initQueryOpt();
				loadRoleTable();
				doEditRole();
			}
		}
	}
	
	/*-----------------------------------------------------------------------------------*/
	/*	初始系统设置列表模块
	/*-----------------------------------------------------------------------------------*/	
	var initSystemSetting = function(){
		var loadOptions = function(){
			
		}
		
		return {
			init: function(){
				loadOptions();
			}
		}
	}
	
	return {
		/****************公共模块****************/
        login: function () {
        	initLoginModule();
        },

        main: function () {
        	handleSidebar();
        	handleHomePage();
            handleChangePWD();
			handleSignOut();
			handleSidebarCollapse();
			initFrameModule();
			return nthTabs;
        },
        
        /****************销售模块****************/
        inputOrder: function(){
        	return initInputOrder().init();
        },
        
        orderList: function(){
        	initOrderList().init();
        },
        
        orderProductList: function(){
        	initOrderProductList().init();
        },
        /****************材料仓库模块****************/
        materialCategory: function(){
        	initMaterialCategory().init();
        },
        
        materialIn: function(){
        	return initMaterialInBill().init()
        },
        
        materialOut: function(){
        	return initMaterialOutBill().init()
        },
        
        materialInList: function(){
        	initMaterialInList().init();
        },
        
        materialOutList: function(){
        	initMaterialOutList().init();
        },
        
        singleMaterialInOut: function(){
        	initSingleMaterialInOut().init();
        },
        
        globalMaterialInOut: function(){
        	initGlobalMaterialInOut().init();
        },
        
        materialInventory: function(){
        	initMaterialInventory().init();
        },
        
        hisMaterialInventory: function(){
        	return initHisMaterialInventory().init();
        },        
        
        /****************成品仓库模块****************/
        productList: function(){
        	return initProductList().init();
        },
        
        productInBill: function(){
        	return initProductInBill().init();
        },
        
        productInList: function(){
        	initProductInList().init();
        },
        
        productOutBill: function(){
        	return initProductOutBill().init();
        },
        
        productOutList: function(){
        	initProductOutList().init();
        },
        
        productInventory: function(){
        	initProductInventory().init();
        },
        
        hisProductInventory: function(){
        	return initHisProductInventory().init();
        }, 
        
        singleProductInOut: function(){
        	initSingleProductInOut().init();
        },
        
        globalProductInOut: function(){
        	initGlobalProductInOut().init();
        },
        
        /****************财务模块****************/
        financeSubject: function(){
        	initFinSubject().init();
        },
        
        financeSubjectConfig:function(){
        	initFinSubjectConfig().init();
        },
        
        voucher: function(){
        	return initVoucher().init();
        },
        
        voucherList: function(){
        	initVoucherList().init();
        },
        
        subsidiaryLedger: function(){
        	initSubsidiaryLedger().init();
        },
        
        gensidiaryLedger: function(){
        	initGensidiaryLedger().init();
        },
        
        /****************基础数据模块****************/
        productList: function(){
        	initProductList().init();
        },
        
        supplierList: function(){
        	initSupplierList().init();
        },
        
        customerList: function(){
        	initCustomerList().init();
        },
        
        /****************系统管理模块****************/
        userList: function(){
        	initUserList().init();
        },
        
        roleList: function(){
        	initRoleList().init();
        },
        
        systemSetting: function(){
        	initSystemSetting().init();
        }
    };
}();

var createFilterTip = function(options){
	var ele = $('.table-fliter');
	var assWin = options.assWin;
	var items = options.items;
	
	ele.empty();
	$.each(items, function(i, item){
		var assEle = assWin.find('#'+item.assId);
		if (item.ignore && assEle.val() == item.ignore){
			return true;
		}
		if (assEle.val() && assEle.val() != ''){
			var tip =$('<span class="tip" ref="'+item.assId+'">');
			var tipClose = $('<button type="button"><span aria-hidden="true">&times;</span></button>');
			var tipContent;
			if (assEle.get(0).tagName == 'SELECT'){
				tipContent = $('<span class="content">'+item.label+'.'+item.rule+'['+assEle.find("option:selected").text()+'] </span>'); 
			}else{
				tipContent = $('<span class="content">'+item.label+'.'+item.rule+'['+assEle.val()+'] </span>'); 
			}
			tipClose.click(function(){
				if (item.ignore)
					assEle.val(item.ignore);
				else
					assEle.val('');
				if (item.eType && item.eType == 'select2'){
					assEle.trigger("change");
				}
				$(this).parent().remove();
				options.changeCall();
			});
			tip.append(tipClose).append(tipContent);
			ele.append(tip);
		}
	});
}

var fillForm = function(form, data){
	$.each(data, function(key, value){
		var ele = form.find('[name="'+key+'"]');
		if (ele){
			ele.val(value);
		}
	});
}

var clearForm = function(form, igon){
	var eles = form.find('input, select, textarea');
	$.each(eles, function(i, ele){
		var name = $(ele).attr('name');
		if ($.inArray(name, igon) > -1){
			return true;
		}
		
		$(ele).val('');
		if (ele.tagName == 'SELECT'){
			$(ele).select2();
		}
	});
}

var getProjectName = function(){
	var curWwwPath=window.document.location.href;
	var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
	var localhostPaht=curWwwPath.substring(0,pos);
	var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
	return localhostPaht+projectName;
}

var exchangeWhenInput = function(first, second){
	first.on('input', function(){
		second.val($(this).val());
	});
	second.on('input', function(){
		first.val($(this).val());
	});
}

function isNumber(value) {  
    var patrn = /^(-)?\d+(\.\d+)?$/;
    if (patrn.exec(value) == null || value == "") {
        return false
    } else {
        return true
    }
}

function increaseMonth(current, increase){
	var syear = current.substring(0, 4);
	var smonth = current.substring(5);
	
	var year = parseInt(syear);
	var month = parseInt(smonth) + increase;
	
	if (month > 12){
		month = month - 12;
		year = year + 1;
	}
	if (month < 1){
		month = month + 12;
		year = year - 1
	}
	return year + '-' + (month < 10 ? 0 + '' + month : month);
}

function Format(now,mask)
{
    var d = now;
    var zeroize = function (value, length)
    {
        if (!length) length = 2;
        value = String(value);
        for (var i = 0, zeros = ''; i < (length - value.length); i++)
        {
            zeros += '0';
        }
        return zeros + value;
    };
 
    return mask.replace(/"[^"]*"|'[^']*'|\b(?:d{1,4}|m{1,4}|yy(?:yy)?|([hHMstT])\1?|[lLZ])\b/g, function ($0)
    {
        switch ($0)
        {
            case 'd': return d.getDate();
            case 'dd': return zeroize(d.getDate());
            case 'ddd': return ['Sun', 'Mon', 'Tue', 'Wed', 'Thr', 'Fri', 'Sat'][d.getDay()];
            case 'dddd': return ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'][d.getDay()];
            case 'M': return d.getMonth() + 1;
            case 'MM': return zeroize(d.getMonth() + 1);
            case 'MMM': return ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'][d.getMonth()];
            case 'MMMM': return ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'][d.getMonth()];
            case 'yy': return String(d.getFullYear()).substr(2);
            case 'yyyy': return d.getFullYear();
            case 'h': return d.getHours() % 12 || 12;
            case 'hh': return zeroize(d.getHours() % 12 || 12);
            case 'H': return d.getHours();
            case 'HH': return zeroize(d.getHours());
            case 'm': return d.getMinutes();
            case 'mm': return zeroize(d.getMinutes());
            case 's': return d.getSeconds();
            case 'ss': return zeroize(d.getSeconds());
            case 'l': return zeroize(d.getMilliseconds(), 3);
            case 'L': var m = d.getMilliseconds();
                if (m > 99) m = Math.round(m / 10);
                return zeroize(m);
            case 'tt': return d.getHours() < 12 ? 'am' : 'pm';
            case 'TT': return d.getHours() < 12 ? 'AM' : 'PM';
            case 'Z': return d.toUTCString().match(/[A-Z]+$/);
            // Return quoted strings with the surrounding quotes removed
            default: return $0.substr(1, $0.length - 2);
        }
    });
};


