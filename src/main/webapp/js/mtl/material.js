var fullSupplier = [];
var cart;

var initMaterialModule = function(){
	
	init();
	initQuery();
	initEdit();
	initSave();
	initDelete();
	initImport();
	initExport();
	
	initMaterialIn();
	initMaterialOut();
	
}

var init = function(){
	$.ajax({
		url : getProjectName() + "/mtlSupp/query.do",
		type: "get",
		success: function(result){
			$.each(result, function(index, supplier){
				$("#search_supplier").append("<option value='"+supplier.id+"'>"+supplier.name+"</option>");
			});
			$("#search_supplier").select2({
				placeholder: "请选择供应商",
				allowClear: true
			});
		}
	});
	
	$.ajax({
		url : getProjectName() + "/mtlCtgy/query.do",
		type: "get",
		success: function(result){
			$.each(result, function(index, category){
				$("#search_category, #new_category").append("<option value='"+category.id+"'>"+category.name+"</option>");
			});
			$("#search_category, #new_category").select2({
				placeholder: "请选择原材料分类",
				allowClear: true
			});
		}
	});
}

var initQuery = function(){
	$("#tbl_material").bootstrapTable({
		url: getProjectName() + "/mtl/getPageResult.do",
		method: "get",
		pagination: true,
		sidePagination: "server", 
		clickToSelect: true,
		columns: [{
            checkbox: true
        },{
            field: 'name',
            title: '品名'
        }, {
            field: 'specification1',
            title: '规格1'
        }, {
            field: 'specification2',
            title: '规格2'
        }, {
            field: 'specification3',
            title: '规格3'
        }, {
            field: 'category',
            title: '分类'
        }, {
            field: 'storage',
            title: '库存'
        }, {
            field: 'unit',
            title: '单位'
        }, {
            field: '',
            title: '供应商',
            formatter: function(value, row, index){
            	if (row.supplierNames){
            		var suppNames = '';
            		$.each(row.supplierNames.split(","), function(i, supplier){
            			suppNames += "," + supplier.split(":")[1];
            		});
            		return suppNames.substring(1);
            	}else{
            		return '';
            	}
            }
        }],
        queryParams: function(params){
        	return {
                pageSize: params.limit,
                pageOffset: params.offset,                    
                name: $("#search_name").val(),
                category: $("#search_category").val(),
                supplier: $("#search_supplier").val()
            }
        }
	});
	
	exchangeWhenInput($("#condition"), $("#search_name"));
	$("#btn_search,.app-search .fa-search").click(function(){
		$("#tbl_material").bootstrapTable("refresh", {url: getProjectName() + "/mtl/getPageResult.do", cache: false});
	});	
}

var initEdit = function(){
	$("#btn_add").click(function(){
		$("#panel-new").slideToggle();
		$("#panel_table").slideToggle();
	});
}

var initSave = function(){
	$("#form_new_material").bootstrapValidator({
		fields: {
			name : {validators: {notEmpty : {}}}
        }
	});
	$("#btn_save_new").click(function(){
		var bv = $("#form_new_material").data('bootstrapValidator');
        bv.validate();
        if(bv.isValid()){
        	var _url = getProjectName()+"/mtl/addMaterial.do";
			$("#form_new_material").ajaxSubmit({
				url: _url,
				type: "post",
				success: function(){
					window.location.reload(true);
				}
			});
		}
	});
	
	$("#btn_cancel_new").click(function(){
		$("#form_new_material").val("");
		$("#panel_table").slideToggle();
		$("#panel-new").slideToggle();		
	});
}

var initDelete = function(){	
	$("#btn_remove").click(function(){
		var selections = $("#tbl_material").bootstrapTable('getSelections');
		
		if (selections.length == 0){
			Ewin.alert({message: "请选择需要删除的原材料"}).on(function(e){});
			return;
		}
		if (selections.length > 1){
			Ewin.alert({message: "一次只允许删除一个原材料"}).on(function(e){});
			return;
		}
		
		var id = selections[0].id;
		
		Ewin.confirm({message: "确定要删除选中的原材料吗？"}).on(function(e){
			if (!e){
				return;
			}
			
			$.ajax({
				url: getProjectName()+"/mtl/deleteMaterial.do?id="+id,
				type: "get",
				success: function(){
					window.location.reload(true);
				}
			});
		});
	});
}

var initImport = function(){
	$("#btn_import").click(function(){
		ImportData.show({url: getProjectName() + "/mtl/importMaterial.do",
			templaterName: getProjectName() + "/templaters/原材料信息.xlsx",
			callback: function(){
				$("#tbl_material").bootstrapTable("refresh", {url: getProjectName() + "/mtl/getPageResult.do", cache: false});
			}
		});
	});
}

var initExport = function(){
	$("#btn_export").click(function(){
		window.open(getProjectName() + "/mtl/exportMaterial.do?name="+$('#search_name').val()+"&category="+$('#search_category').val()+"&supplierName="+$('#search_supplier').val());
	});
}

var initMaterialIn = function(){	
	$("#btn_instorage").click(function(){
		var selections = $("#tbl_material").bootstrapTable('getSelections');
		if (selections.length == 0){
			Ewin.alert({message: "请选择需要入库的原材料"}).on(function(e){});
			return;
		}
		
		$("#btn_outstorage").attr("disabled",true);
		if (!cart || (cart.isEmpty() && cart.name != 'materialIn')){
			cart = null;
			cart = $("#cart").cart({
				name: 'materialIn',
				title: '原材料入库',
				triggerShow: $("#btn_show_cart"),
				toggle: $("#panel_table"),
				formItems: [{element: 'select', label: '供应商', name: 'supplier', dataSrc: '/mtlSupp/query.do', valueField: 'id', textField: 'name', values: $("#search_supplier"), required: true}, 
					        {element: 'date', label: '入库日期', name: 'inDate', required: true}],
				tableOpts: [{field: 'name', title: '品名'}, 
					        {field: 'specification1', title: '规格1'}, 
					        {field: 'specification2', title: '规格2'}, 
					        {field: 'specification3', title: '规格3'}],
		        submitUrl: '/mtlIn/batchInStorage.do',
				name_tableParam: 'materialStr',
				successCallback: function(){
					$("#tbl_material").bootstrapTable("refresh", {url: getProjectName() + "/mtl/getPageResult.do", cache: false});
			    },
			    clearCallback: function(){
			    	$("#btn_outstorage").attr("disabled",false);
			    }
			});
		}
		
		$.each(selections, function(index, row){
			row.materialId = row.id;
			cart.addToCart(row);
		});
		$("#tbl_material").bootstrapTable("uncheckAll");
	});
}

var initMaterialOut = function(){
	$("#btn_outstorage").click(function(){
		var selections = $("#tbl_material").bootstrapTable('getSelections');
		if (selections.length == 0){
			Ewin.alert({message: "请选择需要出库的原材料"}).on(function(e){});
			return;
		}
		
		$("#btn_instorage").attr("disabled",true);
		if (!cart || (cart.isEmpty() && cart.name != 'materialOut')){
			cart = null;
			cart = $("#cart").cart({
				name: 'materialOut',
				title: '原材料出库',
				triggerShow: $("#btn_show_cart"),
				toggle: $("#panel_table"),
				formItems: [{element: 'date', label: '出库日期', name: 'outDate', required: true}],
				tableOpts: [{field: 'name', title: '品名'}, 
					        {field: 'specification1', title: '规格1'}, 
					        {field: 'specification2', title: '规格2'}, 
					        {field: 'specification3', title: '规格3'},
					        {field: 'storage', title: '库存'}],
			    TIValidator: [{field: 'amount', rule: {name: 'lessThan', target: 'storage', message:'出库数不能大于库存'}}],
		        submitUrl: '/mtlOut/batchOutStorage.do',
				name_tableParam: 'materialStr',
				successCallback: function(){
					$("#tbl_material").bootstrapTable("refresh", {url: getProjectName() + "/mtl/getPageResult.do", cache: false});
			    },
			    clearCallback: function(){
			    	$("#btn_instorage").attr("disabled",false);
			    }
			});
		}
		
		
		$.each(selections, function(index, row){
			row.materialId = row.id;
			cart.addToCart(row);
		});
		$("#tbl_material").bootstrapTable("uncheckAll");
	});
}
