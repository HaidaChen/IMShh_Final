var initMtlCategoryModule = function(){
	initCategory();
	initQuery();
	initEdit();
	initSave();
	initDelete();
	
	initFormula();
}

var initCategory = function(){
	var ctgData = [
        {'id': '0', 'parent': '#', 'text': '原材料分类', 'state': { "opened" : true }},

		{'id': '01', 'parent': '0', 'text': '01 化工材料'},
		{'id': '02', 'parent': '0', 'text': '02 引线'},
		{'id': '03', 'parent': '0', 'text': '03 包装材料', 'state': { "opened" : true }},
		{'id': '99', 'parent': '0', 'text': '99 其他'},
		
		{'id': '0301', 'parent': '03', 'text': '0301 纸箱'},
		{'id': '0302', 'parent': '03', 'text': '0302 彩盒'},
		{'id': '0303', 'parent': '03', 'text': '0303 PVC袋'},
		{'id': '0304', 'parent': '03', 'text': '0304 彩印'},
		{'id': '0305', 'parent': '03', 'text': '0305 纸管'},
		{'id': '0306', 'parent': '03', 'text': '0306 纸张'},
		{'id': '0307', 'parent': '03', 'text': '0307 木方'},
		{'id': '0399', 'parent': '03', 'text': '0399 其他'},
		
		{'id': '030401', 'parent': '0304', 'text': '030401 围招'},
		{'id': '030402', 'parent': '0304', 'text': '030402 顶招'},
		{'id': '030403', 'parent': '0304', 'text': '030403 筒招'},
		{'id': '030404', 'parent': '0304', 'text': '030404 弹招'},
		{'id': '030405', 'parent': '0304', 'text': '030405 皮招'},
		{'id': '030406', 'parent': '0304', 'text': '030406 柱形弹混批'},
		{'id': '030407', 'parent': '0304', 'text': '030407 卡头'},
		{'id': '030408', 'parent': '0304', 'text': '030408 内卡'},
		{'id': '030409', 'parent': '0304', 'text': '030409 内架'}
	];
	
	
	$('#panel_category').jstree({
		plugins: ['types', 'state'],
		'types': {
			'default': {
				'icon': false
			}
		},
		'core': {
			'data': ctgData
		}
	});
}

var initQuery = function(){
	$("#tbl_material_category").bootstrapTable({
		url: getProjectName() + "/mtlCtgy/getPageResult.do",
		method: "get",
		pagination: true,
		sidePagination: "server", 
		clickToSelect: true,
		columns: [{
            checkbox: true
        },{
            field: 'name',
            title: '分类名称'
        }, {
            field: 'formula',
            title: '计算公式（面积/体积）'
        }, {
            field: 'remark',
            title: '备注'
        }],
        queryParams: function(params){
        	return {
                pageSize: params.limit,
                pageOffset: params.offset,                    
                name: $("#condition").val()
            }
        }
	});
	
	$(".app-search .fa-search").click(function(){
		$("#tbl_material_category").bootstrapTable("refresh", {url: getProjectName() + "/mtlCtgy/getPageResult.do", cache: false});
	});	
}

var initEdit = function(){
	$("#btn_add").click(function(){
		$("#panel_table").slideToggle();
		$("#panel-new").slideToggle();
		
	});
	
	$("#btn_update").click(function(){
		var selections = $("#tbl_material_category").bootstrapTable('getSelections');
		if (selections.length == 0){
			Ewin.alert({message: "请选择需要修改的原材料分类"}).on(function(e){});
			return;
		}
		if (selections.length > 1){
			Ewin.alert({message: "一次只允许修改一个原材料分类"}).on(function(e){});
			return;
		}
		
		$("#panel_table").slideToggle();
		$("#panel-new").slideToggle();
		
		var id = selections[0].id;
		$.ajax({
			url: getProjectName()+"/mtlCtgy/getCategoryById.do?id="+id,
			type: "get",
			success: function(result){
				var fillForm = new FillForm();
				fillForm.autoFill("#form_new_material_category" , result);
			}
		});
	});
}

var initSave = function(){
	$("#form_new_material_category").bootstrapValidator({
		fields: {
			name : {validators: {notEmpty : {}}}
        }
	});
	$("#btn_save_new").click(function(){
		var bv = $("#form_new_material_category").data('bootstrapValidator');
        bv.validate();
        if(bv.isValid()){
        	var _url = "";
        	if ($("input[name='id']").val() == ""){
        		_url = getProjectName()+"/mtlCtgy/addCategory.do";
        	}else{
        		_url = getProjectName()+"/mtlCtgy/updateCategory.do";
        	}
        	
			$("#form_new_material_category").ajaxSubmit({
				url: _url,
				type: "post",
				success: function(){
					window.location.reload(true);
				}
			});
		}
	});
	
	$("#btn_cancel_new").click(function(){
		$("#form_new_material_category input").val("");
		$("#panel_table").slideToggle();
		$("#panel-new").slideToggle();
	});
}

var initDelete = function(){	
	$("#btn_remove").click(function(){
		var selections = $("#tbl_material_category").bootstrapTable('getSelections');
		
		if (selections.length == 0){
			Ewin.alert({message: "请选择需要删除的原材料分类"}).on(function(e){});
			return;
		}
		if (selections.length > 1){
			Ewin.alert({message: "一次只允许删除一个原材料分类"}).on(function(e){});
			return;
		}
		
		var id = selections[0].id;
		
		Ewin.confirm({message: "确定要删除选中的分类吗？"}).on(function(e){
			if (!e){
				return;
			}
			
			$.ajax({
				url: getProjectName()+"/mtlCtgy/deleteCategory.do?id="+id,
				type: "get",
				success: function(){
					window.location.reload(true);
				}
			});
		});
	});
}

var initFormula = function(){
	$("#btn_showformula").click(function(){
		$("#formulabox").toggle();
		
	});
	
	$("#grp_factor").children().click(function(){
		var content = $("#calculation").text();
		$("#calculation").text(content + "'" + $(this).html() + "'");
	});
	
	$("#grp_number, #grp_operation").children().click(function(){
		var content = $("#calculation").text();
		$("#calculation").text(content + $(this).html());
	});
	
	$("#btn_formula_reback").click(function(){
		var content = $("#calculation").text();
		var iplus = content.lastIndexOf("+");
		var iminus = content.lastIndexOf("-");
		var imultiplication = content.lastIndexOf("*");
		var idivision = content.lastIndexOf("/");
		var near = Math.max.apply(null, [iplus, iminus, imultiplication, idivision]);
		if (near == -1)
			return;
		content = content.substr(0, near);
		$("#calculation").text(content);
	});
	
	$("#btn_formula_clear").click(function(){
		$("#calculation").text("");
	});
	
	$("#btn_formula_save").click(function(){
		$("input[name=formula]").val($("#calculation").html());
		$("#formulabox").hide();
		if ($("input[name=formula]").val() != ''){
			calculateMeterage();
			calculateTotlment();
		}
	});
	
	var calculateMeterage = function(){
		var exp = analysisExp($("input[name=formula]").val());
		$("input[name=meterage]").val(math.eval(exp).toFixed(2));			
	}
	
	var calculateTotlment = function(){
		var meterage = $("input[name=meterage]").val();
		var amount = $("input[name=amount]").val();
		var unitPrice = $("input[name=unitPrice]").val();
		var totlemnt = $("input[name=totlemnt]");
		
		if (amount != '' && unitPrice != ''){
			
			var exp = amount + '*' + unitPrice;
			if (meterage != '')
				exp = exp + '*' + meterage;
			totlemnt.val(math.eval(exp).toFixed(2));
		}
	}
	
	var analysisExp = function(expression){
		var str = expression;
		var index = str.indexOf("'");
		if (index < 0){
			return expression;
		}
		var factor = str.substr(index+1);
		index = factor.indexOf("'");
		factor = factor.substr(0, index);
		var factorVal = $("input[label="+ factor +"]").val();
		var nexpression = expression.replace("'"+factor+"'", factorVal);
		var exp = analysisExp(nexpression);		
		return exp;
	}
}