/**
 * 
 */
var initMaterialModule = function(){
	initQuery();
	initNew();
	initCopy();
	initImport();
	initExport();
}

var init = function(){
	
}

var initQuery = function(){
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
	
	$("#tbl_materialIn").bootstrapTable({
		url: getProjectName() + "/mtlIn/getPageResult.do",
		method: "get",
		pagination: true,
		sidePagination: "server", 
		clickToSelect: true,
		columns: [{
            checkbox: true
        },{
            field: 'inDate',
            title: '入库日期'
        }, {
            field: 'supplierName',
            title: '供应商'
        }, {
            field: 'materialName',
            title: '品名'
        },{
            field: 'specification1',
            title: '规格1'
        }, {
            field: 'specification2',
            title: '规格2'
        }, {
            field: 'specification3',
            title: '规格3'
        }, {
            field: 'amount',
            title: '入库数量'
        }, {
            field: 'unit',
            title: '单位'
        }, {
            field: 'remark',
            title: '备注'
        }],
        queryParams: function(params){
        	return {
                pageSize: params.limit,
                pageOffset: params.offset,                    
                name: $("#search_name").val(),
                supplier: $("#search_supplier").val(),
                startDate: $("#search_startDate").val(),
                endDate: $("#search_endDate").val()
            }
        }
	});
	
	exchangeWhenInput($("#condition"), $("#search_name"));
	$("#btn_search,.app-search .fa-search").click(function(){
		$("#tbl_materialIn").bootstrapTable("refresh", {url: getProjectName() + "/mtlIn/getPageResult.do", cache: false});
	});	
}
var initNew = function(){
	$.ajax({
		url : getProjectName() + "/mtlSupp/query.do",
		type: "get",
		success: function(result){
			$.each(result, function(index, supplier){
				$("#new_supplier,#search_mtl_supplier").append("<option value='"+supplier.id+"'>"+supplier.name+"</option>");
			});
			$("#new_supplier,#search_mtl_supplier").select2({
				placeholder: "请选择供应商",
				allowClear: true
			});
		}
	});
	
	$("#tbl_material").bootstrapTable({
		url: getProjectName() + "/mtl/getPageResult.do",
		method: "get",
		pagination: true,
		sidePagination: "server", 
		clickToSelect: true,
		singleSelect: true,
		columns: [{
            checkbox: true
        }, {
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
                name: $("#search_mtl_name").val(),
                supplier: $("#search_mtl_supplier").val()
            }
        }
	});
	
	$("#btn_search_mtl").click(function(){
		$("#tbl_material").bootstrapTable("refresh", {url: getProjectName() + "/mtl/getPageResult.do", cache: false});
	});
	
	$("#btn_chose_mtl").click(function(){
		var selections = $("#tbl_material").bootstrapTable('getSelections');
		if (selections.length == 0){
			Ewin.alert({message: "请选择需要入库的原材料"}).on(function(e){});
			return;
		}
		var material = selections[0];
		var materialDes = material.name + '  规格：';
		if (material.specification1 != ''){
			materialDes += material.specification1;
		}
		if (material.specification2 != ''){
			materialDes += '*' + material.specification2;
		}
		if (material.specification3 != ''){
			materialDes += '*' + material.specification3;
		}
		
		$("input[name='materialId']").val(material.id);
		$("#new_material").val(materialDes);
		
		
		$("#modal_material").modal('hide');
		
		
	});
	
	$('#modal_material').on('hide.bs.modal', function () {
		$("#search_mtl_name").val('');
		$("#search_mtl_supplier").select2({placeholder: "请选择供应商",
			allowClear: true}).val('').trigger('change');
		$("#tbl_material").bootstrapTable("refresh", {url: getProjectName() + "/mtl/getPageResult.do", cache: false});
    });
	
	$("#btn_add,#btn_cancel_new").click(function(){
		$("#panel-new").slideToggle();
		$("#panel_table").slideToggle();
	});
	
	$("#form_new_materialIn").bootstrapValidator({
		fields: {
			inDate : {validators: {notEmpty : {}}},
			supplierId : {validators: {notEmpty : {}}},
			material : {validators: {notEmpty : {}}},
			amount : {validators: {notEmpty : {}, numeric:{}}}
        }
	});
	$("#btn_save_new").click(function(){
		var bv = $("#form_new_materialIn").data('bootstrapValidator');
        bv.validate();
        if(bv.isValid()){
        	var _url = getProjectName()+"/mtlIn/inStorage.do";
			$("#form_new_materialIn").ajaxSubmit({
				url: _url,
				type: "post",
				success: function(){
					window.location.reload(true);
				}
			});
		}
	});
}

var cart;
var initCopy = function(){
	$("#btn_copy").click(function(){
		var selections = $("#tbl_materialIn").bootstrapTable('getSelections');
		if (selections.length == 0 && (!cart || cart.isEmpty())){
			Ewin.alert({message: "请选择需要复制的原材料明细"}).on(function(e){});
			return;
		}
		
		if (!cart || (cart.isEmpty() && cart.name != 'materialInCopy')){
			cart = null;
			cart = $("#cart").cart({
				name: 'materialInCopy',
				title: '复制原材料入库明细',
				eventEle: $("#btn_copy"),
				formItems: [{element: 'date', label: '入库日期', name: 'inDate', required: true}],
				tableOpts: [{field: 'materialName', title: '品名'}, 
					        {field: 'specification1', title: '规格1'}, 
					        {field: 'specification2', title: '规格2'}, 
					        {field: 'specification3', title: '规格3'}],
		        submitBtnName: '粘贴',
		        submitUrl: '/mtlIn/pasteInStorage.do',
		        paramName4Cart: 'materialStr',
				successCallback: function(){
					$("#tbl_materialIn").bootstrapTable("refresh", {url: getProjectName() + "/mtlIn/getPageResult.do", cache: false});
			    }
			});
		}
		
		cart.addToCart(selections);
		$("#tbl_materialIn").bootstrapTable("uncheckAll");
		
	});
}

var initImport = function(){
	$("#btn_import").click(function(){
		ImportData.show({url: getProjectName() + "/mtlIn/importMaterialIn.do",
			templaterName: getProjectName() + "/templaters/原材料入库明细.xlsx",
			callback: function(){
				$("#tbl_materialIn").bootstrapTable("refresh", {url: getProjectName() + "/mtlIn/getPageResult.do", cache: false});
			}
		});
	});
}

var initExport = function(){
	$("#btn_export").click(function(){
		window.open(getProjectName() + "/mtlIn/exportMaterialIn.do?name="+$('#search_name').val()+"&supplier="+$('#search_supplier').val());
	});
}