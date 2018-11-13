;(function($){
	var cartData = [];
	var init = function(ele, options){
		initTitle(ele, options);
		initForm(ele, options);
		initTable(ele, options);
		initOperation(ele, options);
	};
	
	$.fn.cart = function(options){
		var _cart = this;
		this.empty();
		this.unbind();
		cartData = [];
		
		var defaults = {
			name: '',
			title: '',
			toggle: '',
			formItems: [],
			tableOpts: []
		};
		var setting = $.extend({}, options);
		setting.triggerShow.unbind();
		init(_cart, setting);
		
		return {
			name: setting.name,
			cartData: cartData,
			isEmpty: function(){
				return cartData.length == 0;
			},
			addToCart: function(row){
				var exists = false;
				$.each(cartData, function(index, item){
					if (item.id == row.id){
						exists = true;
						return;
					}						
				});
				if (!exists){
					row.amount = 0;
					row.remark = '';
					cartData.push(row);
					changeCartSize(_cart, setting);
					$("#tbl_cart").bootstrapTable("refreshOptions", {data: cartData, cache: false});
				}
			}
		}
	}
	
	var changeCartSize = function(ele, options){
		if (cartData.length == 0){
			options.triggerShow.hide();
			toggleCart(ele, options);
			options.clearCallback();
		}else{
			options.triggerShow.show();
			$('#cartSize').html(cartData.length);
		}
	}
	
	var initTitle = function(ele, options){
		var title = $('<h3>' + options.title + ' </h3>');
		ele.append(title);
		options.triggerShow.click(function(){
			toggleCart(ele, options);
		});
	};
	
	var initForm = function(ele, options){
		var form = $('<form id="form_cart" class="form-horizontal">');
		$.each(options.formItems, function(index, item){
			var formItemDiv = $('<div class="form-group">');
			var itemLabel = $('<label for="item_"' + item.name + ' class="col-sm-2 control-label">' + item.label + '</label>');
			var itemDiv = $('<div class="col-sm-10">');
			var _item = {};
			if (item.element){
				switch (item.element){
				case 'select':
					_item = $('<select class="form-control" id="item_'+item.name+'" name="' + item.name + '">');
					_item.append('<option value=""></option>');
					$.getJSON(getProjectName() + item.dataSrc, function (res){
						$.each(res, function(i, row){
							var selected = '';
							_item.append('<option value="'+row[item.valueField]+'" '+selected+'>'+row[item.textField]+'</option>');
							
						});
						var slt2 = _item.select2({
							placeholder: "请选择供应商",
							allowClear: true
						});
						if (item.values){
							slt2.val(item.values.val()).trigger('change');
						}
					});
					break;
				case 'date':
					_item = '<input type="date" class="form-control" id="item_'+item.name+'" name="' + item.name + '">';
					break;
				}
			}else{
				_item = '<input class="form-control" type="text" id="item_'+item.name+'" name="' + item.name + '">';
			}
			
			itemDiv.append(_item);
			formItemDiv.append(itemLabel);
			formItemDiv.append(itemDiv);
			form.append(formItemDiv);
		});
		
		ele.append(form);
	};
	
	var initTable = function(ele, options){
		var table = $('<table id="tbl_cart" class="table table-condensed">');
		
		ele.append(table);
		
		var _columns = [];
		$.each(options.tableOpts, function(i, item){
			var _item = {};
			_item.field = item.field;
			_item.title = item.title;
			_columns.push(_item);
		});
		_columns.push({
            width: 10,
			field: '',
            title: '数量',
            formatter: function(value, row, index){
            	return '<input type="number" size="3" name="amount">';
            }});
		_columns.push({
            field: '',
            title: '备注',
            formatter: function(value, row, index){
            	cartData[index].remark = '';
            	return '<input type="text" name="remark" style="width:100%">';
            }
        });
		_columns.push({
			width: 55,
        	field: '',
        	title: '操作',
        	formatter: function(value, row, index){
            	return "<a><i class='fa fa-remove'></i> 删除</a>";
            }
        });
		
		$("#tbl_cart").bootstrapTable({
			data: cartData,
			columns: _columns
		});
		
		ele.on("click", "table a", function(){
			var event_tr = $(this).parents("tr");
			Ewin.confirm({message: "确定要删除当前记录吗？"}).on(function(e){
				if (!e){
					return;
				}
				var index = $("#tbl_cart tbody tr").index(event_tr);
				cartData.splice(index,1);
				event_tr.remove();
				changeCartSize(ele, options);
			});
		});
		
		ele.on("change", "table input", function(){
			$(".cart-list div.error-tip").html("");
			
			var event_src = $(this);
			var index = $("#tbl_cart tbody tr").index(event_src.parents("tr"));
			
			if (event_src.attr("name") == "amount"){
				cartData[index].amount = event_src.val();
			}
			
			if (event_src.attr("name") == "remark"){
				cartData[index].remark = event_src.val();
			}
		});
	};
	
	var initOperation = function(ele, options){
		ele.append('<div class="alert alert-warning" role="alert" style="display:none">...</div>');
		
		var buttonGroup = $('<div class="clearfix">');
		var btn_submit = $('<button class="btn btn-success pull-right m-l-5" id="btn_submit_cart"><i class="fa fa-check"></i> 保存</button>');
		var btn_clear = $('<button class="btn btn-defaul pull-right" id="btn_clear_cart"><i class="fa fa-remove"></i> 清空</button>');
		var btn_close = $('<button class="btn btn-defaul pull-right" id="btn_close_cart"><i class="fa fa-close"></i> 关闭</button>');
		
		buttonGroup.append(btn_submit);
		buttonGroup.append(btn_clear);
		buttonGroup.append(btn_close);
		ele.append(buttonGroup);
		
		btn_clear.click(function(){
			Ewin.confirm({message: "确定要删除所有记录吗？"}).on(function(e){
				if (!e){
					return;
				}
				handleClearCart(ele, options);
			});	
		});
		
		btn_close.click(function(){
			toggleCart(ele, options);
		});
		
		btn_submit.click(function(){
			var returnControl = false; 
			$.each(options.formItems, function(i, formItem){
				if (formItem.required){
					var formEle = $("#form_cart").find("#item_" + formItem.name);
					if ($.trim(formEle.val()) == ""){
						$("div.alert-warning").show();
						$("div.alert-warning").html('<strong>保存失败！</strong>' + formItem.label + "必须填写");
						formEle[0].focus();
						returnControl = true;
						return false;
					}
				}
			});
			
			if (returnControl)
				return;
			
			$.each(cartData, function(i, row){
				if (!row.amount || row.amount == '' || row.amount == 0){
					$("div.alert-warning").show();
					$("div.alert-warning").html('<strong>保存失败！</strong>数量必须填写');
					$("#tbl_cart").find("input[name='amount']")[i].focus();
					returnControl = true;
					return false;
				}
			});
			
			if (options.TIValidator){
				var breakEach = false;
				$.each(options.TIValidator, function(key, val){
					$.each(cartData, function(i, row){
						var value = row[val.field];
						if (val.rule.name == 'lessThan'){
							var target = row[val.rule.target];
							if (value > target){
								breakEach = true;
								$('#tbl_cart').find('input[name="'+val.field+'"]')[i].focus();
								$("div.alert-warning").show();
								$('div.alert-warning').html('<strong>保存失败！</strong>' + val.rule.message);
								return false;
							}
						}
					})
					if (breakEach){
						returnControl = true;
						return false;
					}
				});
			}
			
			if (returnControl)
				return;
			
			var tableParam = {};
			tableParam[options.name_tableParam] = JSON.stringify(cartData);
			$("#form_cart").ajaxSubmit({
				type: "post",
				url: getProjectName() + options.submitUrl,
				data: tableParam,
				success:function(result){
					Ewin.alert({message: options.title+"信息保存成功"}).on(function(e){});
					handleClearCart(ele, options);
					options.successCallback();
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					Ewin.alert({message: options.title+"原信息保存失败！"}).on(function(e){});
				}
				
			});
		});
	}
	
	var handleClearCart = function(ele, options){
		cartData = [];
		changeCartSize(ele, options);
	};
	
	var toggleCart = function(ele, options){
		options.toggle.slideToggle();
		ele.slideToggle();
	}
})(jQuery);