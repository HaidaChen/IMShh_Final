/**
 * 
 */
var initMaterialOutModule = function(){
	initQuery();
	initNew();
	initCopy();
	initImport();
	initExport();
}

var initQuery = function(){
	$("#tbl_materialOut").bootstrapTable({
		url: getProjectName() + "/mtlOut/getPageResult.do",
		method: "get",
		pagination: true,
		sidePagination: "server", 
		clickToSelect: true,
		columns: [{
            checkbox: true
        },{
            field: 'outDate',
            title: '出库日期'
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
            title: '出库数量'
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
                startDate: $("#search_startDate").val(),
                endDate: $("#search_endDate").val()
            }
        }
	});
	
	exchangeWhenInput($("#condition"), $("#search_name"));
	$("#btn_search,.app-search .fa-search").click(function(){
		$("#tbl_materialOut").bootstrapTable("refresh", {url: getProjectName() + "/mtlOut/getPageResult.do", cache: false});
	});	
}
var initNew = function(){
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
            field: 'storage',
            title: '库存'
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
	
	$("#form_new_materialOut").bootstrapValidator({
		fields: {
			outDate : {validators: {notEmpty : {}}},
			material : {validators: {notEmpty : {}}},
			amount : {validators: {notEmpty : {}, numeric:{}}}
        }
	});
	$("#btn_save_new").click(function(){
		var bv = $("#form_new_materialOut").data('bootstrapValidator');
        bv.validate();
        if(bv.isValid()){
        	var _url = getProjectName()+"/mtlOut/outStorage.do";
			$("#form_new_materialOut").ajaxSubmit({
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
		var selections = $("#tbl_materialOut").bootstrapTable('getSelections');
		if (selections.length == 0 && (!cart || cart.isEmpty())){
			Ewin.alert({message: "请选择需要复制的原材料出库明细"}).on(function(e){});
			return;
		}
		
		if (!cart || (cart.isEmpty() && cart.name != 'materialOutCopy')){
			cart = null;
			cart = $("#cart").cart({
				name: 'materialOutCopy',
				title: '复制原材料出库明细',
				eventEle: $("#btn_copy"),
				formItems: [{element: 'date', label: '出库日期', name: 'outDate', required: true}],
				tableOpts: [{field: 'materialName', title: '品名'}, 
					        {field: 'specification1', title: '规格1'}, 
					        {field: 'specification2', title: '规格2'}, 
					        {field: 'specification3', title: '规格3'}],
		        submitBtnName: '粘贴',
		        submitUrl: '/mtlOut/pasteOutStorage.do',
		        paramName4Cart: 'materialStr',
				successCallback: function(){
					$("#tbl_materialOut").bootstrapTable("refresh", {url: getProjectName() + "/mtlOut/getPageResult.do", cache: false});
			    }
			});
		}
		
		cart.addToCart(selections);
		$("#tbl_materialOut").bootstrapTable("uncheckAll");
		
	});
}

var initImport = function(){
	$("#btn_import").click(function(){
		ImportData.show({url: getProjectName() + "/mtlOut/importMaterialOut.do",
			templaterName: getProjectName() + "/templaters/原材料出库明细.xlsx",
			callback: function(){
				$("#tbl_materialOut").bootstrapTable("refresh", {url: getProjectName() + "/mtlOut/getPageResult.do", cache: false});
			}
		});
	});
}

var initExport = function(){
	$("#btn_export").click(function(){
		window.open(getProjectName() + "/mtlOut/exportMaterialOut.do?name="+$('#search_name').val());
	});
}

initMaterialOutModule();