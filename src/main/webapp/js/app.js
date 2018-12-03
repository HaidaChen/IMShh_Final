var App = function () {
	var nthTabs;
	var handleSidebar = function(){
		$("#siderbar-warper").slimScroll({
			height: "100%",
			color: '#54667a'
		});
		var menuContainer = $("#side-menu");		
		$.ajax({url:getProjectName()+"/login/getUserMemu.do",
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
							var subItem = $("<li><a class='' href='javascript:;' link='"+submenu.url+"' identify='"+submenu.id+"' text='"+obj.name+"."+submenu.name+"'>" + submenu.name + "</a></li>");
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
			type: 'get',
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
        nthTabs.addTab({
            id:'0101',
            title:'原材料.品类',
            active:true,
            allowClose:false,
            content:'mtl_category.html',
        });
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
			}).on('changed.jstree', function(e, data){
				if('click'==data.event.type){
					var selectedNode = data.instance.get_node(data.selected[0]);
					var code = selectedNode['a_attr'].ctgCode;
					if (!code){
						code = '';
					}
					filterWin.modal.find('#filter_ctgCode').val(code);
					queryMaterial();
				}
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
				
			});
			
			$('#btn_export').click(function(){
				
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
	
	
	return {
        login: function () {
        	initLoginModule();
        },

        main: function () {
        	handleSidebar();
        	initFrameModule();
            handleChangePWD();
			handleSignOut();
			handleSidebarCollapse();
        },

        materialCategory: function(){
        	initMaterialCategory().init();
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
		if (assEle.val() && assEle.val() != ''){
			var tip =$('<span class="tip" ref="'+item.assId+'">');
			var tipClose = $('<button type="button"><span aria-hidden="true">&times;</span></button>');
			var tipContent = $('<span class="content">'+item.label+'.'+item.rule+'['+assEle.val()+'] </span>'); 
			tipClose.click(function(){
				assEle.val('');
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





