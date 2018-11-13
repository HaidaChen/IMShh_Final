var initMtlSupplierModule = function(){
	initQuery();
	initEdit();
	initSave();
	initDelete();
}

var initQuery = function(){
	$("#tbl_material_supplier").bootstrapTable({
		url: getProjectName() + "/mtlSupp/getPageResult.do",
		method: "get",
		pagination: true,
		sidePagination: "server", 
		clickToSelect: true,
		columns: [{
            checkbox: true
        },{
            field: 'name',
            title: '供应商名称'
        }, {
            field: 'categoryIds',
            title: '供应类型'
        }, {
            field: 'remark',
            title: '备注'
        }],
        queryParams: function(params){
        	return {
                pageSize: params.limit,
                pageOffset: params.offset,                    
                name: $("#search_name").val(),
                category: $("#search_category").val()
            }
        }
	});
	
	exchangeWhenInput($("#condition"), $("#search_name"));
	
	$("#btn_search,.app-search .fa-search").click(function(){
		$("#tbl_material_supplier").bootstrapTable("refresh", {url: getProjectName() + "/mtlSupp/getPageResult.do", cache: false});
	});	
	
	$.ajax({
		url : getProjectName() + "/mtlCtgy/query.do",
		type: "get",
		success: function(result){
			$.each(result, function(index, category){
				$("#search_category").append("<option value='"+category.id+"'>"+category.name+"</option>");
			});
			
		}
	});
}

var initEdit = function(){
	$.ajax({
		url: getProjectName() + "/mtlCtgy/query.do",
		type: "get",
		success: function(result){
			$.each(result, function(index, ctg){
				$("#categoryList").append("<label style='margin-left: 10px'><input name='categoryIds' type='checkbox' value='"+ctg.id+"'> "+ ctg.name +"</label>");
			});
		}
	});
	
	$("#btn_add").click(function(){
		$("#panel_table").slideToggle();
		$("#panel-new").slideToggle();
	});
	
	$("#btn_update").click(function(){
		var selections = $("#tbl_material_supplier").bootstrapTable('getSelections');
		if (selections.length == 0){
			Ewin.alert({message: "请选择需要修改的供应商"}).on(function(e){});
			return;
		}
		if (selections.length > 1){
			Ewin.alert({message: "一次只允许修改一个供应商"}).on(function(e){});
			return;
		}
		
		$("#panel_table").slideToggle();
		$("#panel-new").slideToggle();
		
		var id = selections[0].id;
		$.ajax({
			url: getProjectName()+"/mtlSupp/getSupplierById.do?id="+id,
			type: "get",
			success: function(result){
				var fillForm = new FillForm();
				fillForm.autoFill("#form_new_material_supplier" , result);
			}
		});
	});
	
	
}

var initSave = function(){
	$("#form_new_material_supplier").bootstrapValidator({
		fields: {
			name : {validators: {notEmpty : {}}}
        }
	});
	$("#btn_save_new").click(function(){
		var bv = $("#form_new_material_supplier").data('bootstrapValidator');
        bv.validate();
        if(bv.isValid()){
        	var _url = "";
        	if ($("input[name='id']").val() == ""){
        		_url = getProjectName()+"/mtlSupp/addSupplier.do";
        	}else{
        		_url = getProjectName()+"/mtlSupp/updateSupplier.do";
        	}
        	
			$("#form_new_material_supplier").ajaxSubmit({
				url: _url,
				type: "post",
				success: function(){
					window.location.reload(true);
				}
			});
		}
	});
	
	$("#btn_cancel_new").click(function(){
		$("#form_new_material_supplier input[type!='checkbox']").val("");
		$("#panel_table").slideToggle();
		$("#panel-new").slideToggle();
	});
}

var initDelete = function(){	
	$("#btn_remove").click(function(){
		var selections = $("#tbl_material_supplier").bootstrapTable('getSelections');
		
		if (selections.length == 0){
			Ewin.alert({message: "请选择需要删除的供应商"}).on(function(e){});
			return;
		}
		if (selections.length > 1){
			Ewin.alert({message: "一次只允许删除一个供应商"}).on(function(e){});
			return;
		}
		
		var id = selections[0].id;
		
		Ewin.confirm({message: "确定要删除选中的供应商吗？"}).on(function(e){
			if (!e){
				return;
			}
			
			$.ajax({
				url: getProjectName()+"/mtlSupp/deleteSupplier.do?id="+id,
				type: "get",
				success: function(){
					window.location.reload(true);
				}
			});
		});
	});
}
