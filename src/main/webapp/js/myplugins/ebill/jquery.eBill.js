;(function($){
	var BillTitle = function(ele, opt){
		this.$element = ele;
		this.defaults = {};
		this.options = $.extend({}, this.defaults, opt);
	}
	
	BillTitle.prototype = {
		draw: function(){
			this.$element.append('<h3>' + this.options.name + '</h3>');
		}
	}
	
	var BillTopColumns = function(ele, columns){
		this.$element = ele;
		this.columns = columns;
	}
	
	var createHtmlForElement = function(opt, reference){
		var html = '';
		html += '<div class="bill-column" style="width: '+opt.length+'" >';
		if (opt.label){
			html +=   '<label>' + opt.label + '</label>';
		}
		
		var style = ''
	    if (opt.style){
	    	style = opt.style;
	    }
		switch(opt.type){
			case 'select':
				if (opt.select2){
					html += '<select name="'+opt.name+'" style="'+style+'" select2="true">';
				}else{
					html += '<select name="'+opt.name+'" style="'+style+'">';
				}
			    if (opt.placeholder){
			    	html +=   '<option value="">'+opt.placeholder+'</option>';
			    }
			    
			    if (opt['data-ref']){
			    	var options = reference[opt['data-ref']];
			    	$.each(options, function(i, option){
			    		var _value = option[opt['option-value']];
			    		var _text = option[opt['option-text']];
			    		html += '<option value="'+_value+'">'+_text+'</option>';
			    	});
			    }
			    html += '</select>';
			break;
			case 'date':
			    if (opt.value){
			    	if (opt.value == 'now'){
			    		var now = new Date();
			    		var _value = now.getFullYear() + '-' + (now.getMonth() + 1) + '-' + now.getDate();
			    		html += '<input type="date" name="'+opt.name+'" value="'+_value+'" style="'+style+'">';
			    	}else{
			    		html += '<input type="date" name="'+opt.name+'" value="'+opt.value+'" style="'+style+'">';
			    	}
			    }else{			
			    	html += '<input type="date" name="'+opt.name+'" style="'+style+'">';
			    }break;
		    case 'indentify':
		        var _value = '001';
		        $.ajax({
        			url: getProjectName() + opt.src,
        			async: false,
        			success: function(result){
        				_value = result.code;
        			}
        		});
		        html += '<input type="text" name="'+opt.name+'" value="'+_value+'" readonly="readonly" style="width: 150px; font-weight:bold; color: #FF7F50; border: none">';
			    break;
			case 'text':
				html += '<input type="text" name="'+opt.name+'" style="'+style+'">';
				break;
			case 'increase':
				html += '<input type="text" name="'+opt.name+'" style="'+style+'">';
				break;
		}
		if (opt['label-after']){
			html +=   '<label>' + opt['label-after'] + '</label>';
		}
		html += '</div>';
		return html;
	}
	
	BillTopColumns.prototype = {
		draw: function(reference){
			var html = '<div class="top-columns">';
			$.each(this.columns, function(i, column) {
				html += createHtmlForElement(column, reference);
			});
			html += '</div>';
			this.$element.append(html);
		}
	}
	
	var BillBottomnColumns = function(ele, columns){
		this.$element = ele;
		this.columns = columns;
	}	
		
	BillBottomnColumns.prototype = {
		draw: function(reference){
			var html = '<div class="bottom-columns">';
			$.each(this.columns, function(i, column) {
				html += createHtmlForElement(column, reference);
			});
			html += '</div>';
			this.$element.append(html);
		}
	}	
	
	var BillItemTable = function(ele, tableOpt){
		this.$element = ele;
		this.tableOpt = tableOpt;
		this.tableData = [{},{},{},{},{}];
		this.currentRow = 1;
	}
	
	function isEmptyObject(obj){
        for(var key in obj){
            return false
        };
        return true
    }
	
	BillItemTable.prototype = {
		draw: function(){
			
			var tableContainer = this.$element.find('div.item-table');
			if (!tableContainer || tableContainer.length == 0){
				this.$element.append('<div class="item-table">');
			}else{
				tableContainer.empty();
			}
			
			var _columns = this.tableOpt.columns;
			var html = '<table class="gridtable" style="width:100%">';
			/*构造表头*/
			html += '<tr>';
			$.each(_columns, function(i, column){
				if (column.hide) return true;
				if (column.type == 'money'){
					html += '<th width="'+column.length+'">';
					html +=   '<div class="light-border-bottom">' + column.label + '</div>';
					html +=   '<div class="money-unit"><span>亿</span><span>千</span><span>百</span><span>十</span><span>万</span><span>千</span><span>百</span><span>十</span><span>元</span><span>角</span><span>分</span></div>';
					html += '</th>';
				}else{
					html += '<th style="width: '+column.length+'">' + column.label + '</th>';
				}
				
			});
			html += '</tr>';
			
			/*根据表格对象构造行，默认的表格对象是5个空对象*/
			$.each(this.tableData, function(rowIndex, row){
				html += '<tr>';
				$.each(_columns, function(i, column){
					if (column.hide) return true;
					if (column.type == 'row-no'){
						html += '<td style="text-align: center" type="'+column.type+'" row-no="'+(rowIndex+1)+'">'+(rowIndex+1)+'</td>';	
					}else if (column.type == 'money'){
						html += '<td name="'+column.name+'" type="'+column.type+'"><div class="money-unit">';
						
						var text = row[column.name];
						if (text && text != '' && text != '0'){
							var moneyText = parseFloat(text).toFixed(2) + '';
							moneyText = moneyText.replace('.', '');//12300
							var size = moneyText.length;
							var counter = 11 - size;  //7
							var cursor = 0;
							for (var j = 0; j < 11; j++){
								if (j == counter){
									html += '<span>'+moneyText.charAt(cursor)+'</span>';
									counter++;	
									cursor++;
								}else{
									html += '<span>&nbsp;</span>';
								}						
							}
						}else{
							html += '<span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span>';
						}
						html += '</div></td>';
						$(this).parent().append('<div class="money-unit"><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span></div>');
						
					}
					else{
						html += '<td name="'+column.name+'" type="'+column.type+'"';	
						if (column["data-ref"]) html += ' data-ref="'+column["data-ref"]+'"';
						if (column["ref-field"]) html += ' data-ref="'+column["ref-field"]+'"';
						if (column["ref-tigger"]) html += ' ref-tigger='+column["ref-tigger"];
						if (column["ref-modal-id"]) html += ' ref-modal-id="'+column["ref-modal-id"]+'"';
						if (column["ref-modal-url"]) html += ' ref-modal-url="'+column["ref-modal-url"]+'"';
						if (column["ref-modal-title"]) html += ' ref-modal-title="'+column["ref-modal-title"]+'"';
						if (column["ref-modal-style"]) html += ' ref-modal-style="'+column["ref-modal-style"]+'"';
						html += '>';
						
						if (column.formatter){
							html += column.formatter(row);
						}else{
							if (column.name.indexOf('.') > 0){
								var field = column.name;
								var obj = field.substr(0, field.indexOf('.'));
								var item = field.substr(field.indexOf('.') + 1);
								
								if (row[obj] && row[obj][item])html += row[obj][item];
							}else{
								if (row[column.name])html += row[column.name];
							}
							
						}
						html += '</td>';
					}
				});
				html += '</tr>';
			});
			html += '</tr>';
			
			if (this.tableOpt.total){
				html += '<tr>';
				//html +=   '<td colspan = "8" style="font-size: 1.2em; font-weight: 600">'+this.tableOpt["total-label"]+'<span id="billTotal" total-column="'+this.tableOpt["total-column"]+'"></span><input type="hidden" name="'+this.tableOpt["total-name"]+'"></td>';
				html +=   '<td colspan = "8" style="font-size: 1.2em; font-weight: 600">'+this.tableOpt["total-label"]+'<span id="'+this.tableOpt["total-name"]+'" total-column="'+this.tableOpt["total-column"]+'"></span><input type="hidden" name="'+this.tableOpt["total-name"]+'"></td>';
				html += '</tr>';
			}
			
			html += '</table>';
			this.$element.find('div.item-table').append(html);
			this.eventProgress();
		},
		
		eventProgress: function(){
			var _table = this;
			this.$element.find('table.gridtable td').click(function(){
				if ($(this).children('input').length == 1){
					$(this).children('input').focus();
					return;
				}
				$(this).css('padding', '1px');
				var width = $(this).width();
				var height = $(this).height();
				var name = $(this).attr('name');
				var type = $(this).attr('type');
				var label = $(this).attr('label');
				var dataRef = $(this).attr('data-ref');
				var refValue = $(this).attr('ref-value');
				var refText = $(this).attr('ref-text');
								
				var tableRow = _table.$element.find('table.gridtable tr').index($(this).parent('tr')) -1;
				
				if (type == 'text' || type == 'edit-ref'){
					var _input = $('<input type="text" style="height: '+(height-5)+'px; width: '+(width-4)+'px">');
					if ($(this).text()!=''){
						_input.val($(this).text());
						$(this).text('');
					}
					$(this).append(_input);
					_input.focus();
					_input.blur(function(){
						var text = $(this).val();
						if (text != ''){
							var data = {};
							data[name] = text;
							//_table.updateTableRow(tableRow, data);
							_table.updateTableRowData(tableRow, name, text);
							_table.calculateColumns(tableRow);
							$(this).parent().text($(this).val());
						}
						$(this).remove();
					});
				}
				
				if (type == 'money'){
					var _input = $('<input type="text" style="height: '+(height-5)+'px; width: '+(width-4)+'px">');
					var moneyEles = $(this).find('span');
					var moneyStr = '';
					$.each(moneyEles, function(i, ele){
						if (i == 9)
							moneyStr += '.';
						if ($(ele).html() == '' || $(ele).html() == '&nbsp;'){
							moneyStr += '0';
						}else{
							moneyStr += $(ele).html();
						}
					});
					
					var money = parseFloat(moneyStr);
					if (money != 0)
					_input.val(money);
					$(this).empty();
					$(this).append(_input);
					_input.focus();
					_input.blur(function(){
						$(this).parent().append('<div class="money-unit"><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span><span></span></div>');
						var text = $(this).val();
						if (text != '' && text != '0'){
							var moneyText =  parseFloat(text).toFixed(2) + '';
							
							_table.updateTableRowData(tableRow, name, moneyText);
							_table.calculateColumns(tableRow);
							
							moneyText = moneyText.replace('.', '');
							var size = moneyText.length;
							var current = size -1;
							for (var j = 10; j > -1; j--){
								if (current == -1){
									$(this).parent().find("span").eq(j).html('&nbsp;');
								}else{
									$(this).parent().find("span").eq(j).html(moneyText.charAt(current));
									current--;	
								}
															
							}
						}
						$(this).remove();
					});
					_input.click(function(){
						$(this).focus();
					});
				}
			});	
			
			this.$element.find('table.gridtable td').hover(function(){
				_table.currentRow = $('table.gridtable tr').index($(this).parents('tr'));
				var type = $(this).attr('type');
				var refModalId = $(this).attr('ref-modal-id');
				var refModalUrl = $(this).attr('ref-modal-url');
				var refModalTitle = $(this).attr('ref-modal-title');
				var refModalStyle = $(this).attr('ref-modal-style');
				
				if (type == 'modal-ref' && $(this).attr('ref-tigger')){
					if ($(this).find('.btn').length == 1){
						$(this).find('.btn').remove();
					}else{
						var _btn = $('<span class="btn btn-primary btn-xs" >选择</span>');
						$(this).append(_btn);
						_btn.click(function(){
							//Ewin.load({id: 'mtl_ref', title: '选择原材料', url: 'fragment/mtl_ref.html', style: 'width: 800px'});
							var option = {id: refModalId, title: refModalTitle, url: refModalUrl};
							if (refModalStyle){
								option.style = refModalStyle;
							}
							Ewin.load(option);
						});
					}
				}

				if (type == 'row-no'){
					if ($(this).find('.btn').length > 0){
						$(this).find('.btn').remove();
						$(this).text($(this).attr('row-no'));
					}else{
						$(this).text('');
						var _btnRemove = $('<span class="btn btn-success btn-xs" style="margin-left: 3px"><i class="fa fa-minus"></i></span>');
						var _btnInsert = $('<span class="btn btn-success btn-xs"><i class="fa fa-plus"></i></span>');
						$(this).append(_btnRemove);
						$(this).append(_btnInsert);
						
						_btnRemove.click(function(){
							var index = $(this).parent().attr("row-no") - 1;
							_table.tableData.splice(index, 1);
							_table.draw();
							_table.calculateToal();
						});
						
						_btnInsert.click(function(){
							var index = $(this).parent().attr("row-no");
							_table.tableData.splice(index, 0, {});
							_table.draw();
						});
					}
				}
			});
		},
		/*填充表格数据*/
		fillTable: function(data){
			var _table = this;
			var _tableData = this.tableData;
			var _columns = this.tableOpt.columns;
			var cursor = this.currentRow -1;
			
			var t_rows = [];
			$.each(data, function(i, row){
				var t_row = {};
				$.each(_columns, function(j, column){
					if (column.type == 'modal-ref' ){
						var refName = column['data-ref'];
						if (row[refName]){
							var refData = row[refName];
							var fieldName = column['ref-field'];
							//判断对应的字段中是否包含表达式，有的话则替换表达式
							if (fieldName.indexOf(",") > -1){
								var matches = fieldName.split(",");
								$.each(matches, function(i, field){
									if (refData[field]){
										t_row[field] = refData[field];
									}
								});
							}else{
								if (refData[fieldName]){
									t_row[column.name] = refData[fieldName];
								}
							}
						}
					}else{
						if (row[column.name]){
							t_row[column.name] = row[column.name];
						}
					}					
				});
				t_rows.push(t_row);				
			});
			
			$.each(t_rows, function(i, row){
				if (i == 0){
					_table.updateTableRow(cursor, row);
				}else{
					if (!_tableData[cursor] || isEmptyObject(_tableData[cursor])){
						_table.updateTableRow(cursor, row);
					}else {
						_tableData.splice(cursor, 0, row);
					}
				}
				cursor++;
			});
			this.draw();
		}, 
		updateTableRowData: function(index, key, val){
			var _tableData = this.tableData;
			if (key.indexOf('.') > -1){
				var obj = key.substr(0, key.indexOf('.'));
				var item = key.substr(key.indexOf('.') + 1);
				var _obj = {};
				_obj[item] = value;
				_tableData[index][obj] = _obj;
			}else{
				_tableData[index][key] = val;
			}
			
			
		},
		updateTableRow: function(index, data){
			var _table = this;
			var _tableData = this.tableData;
			$.each(data, function(key, value) {
				if (key.indexOf('.') > -1){
					var obj = key.substr(0, key.indexOf('.'));
					var item = key.substr(key.indexOf('.') + 1);
					//var _obj = {};
					//_obj[item] = value;
					//_tableData[index][obj] = _obj;
					if (!_tableData[index][obj]){
						_tableData[index][obj] ={};
					}
					_tableData[index][obj][item] = value;
					
					
				}else{
					_tableData[index][key] = value;
				}
			});
		},
		
		calculateColumns: function(rowNo){
			var _table = this;
			var _ele = this.$element;
			var _columns = this.tableOpt.columns;
			var _tableData = this.tableData;
			
			$.each(_columns, function(i, column) {
				if (column.type == 'calculation'){
					var exp = column.formula;
					var reg = new RegExp("\\[([^\\[\\]]*?)\\]", 'igm');
					var matches = exp.match(reg);
		        	$.each(matches, function(j, match){
		        		//alert(match.substring(1, match.length-1));
		        		var field = match.substring(1, match.length-1);
		        		
		        		if (!_tableData[rowNo][field]){
		        			return false;
		        		}
		        		var val = _tableData[rowNo][field];
		        		exp = exp.replace(match, val);
		        	});
		        	
		        	if (!exp.match(reg) || exp.match(reg).lenght == 0){
		        		var calRes = math.eval(exp).toFixed(4);
		        		_tableData[rowNo][column.name] = calRes;
		        		var tr = _ele.find('table.gridtable tr')[rowNo+1];
		        		$(tr).find('td[name="'+column.name+'"]').text(calRes);
		        		_table.calculateToal();
		        	}
		        	
				}
			});
		},
		
		calculateToal: function(){
			if (this.tableOpt.total){
				var column = this.tableOpt['total-column'];
				var totalName = this.tableOpt['total-name'];
				var total = 0;
				$.each(this.tableData, function(i, row) {
					if (row[column]){
						total += parseFloat(row[column]);
					}
				});
				this.$element.find('#'+totalName).text(total.toFixed(4));
				this.$element.find('[name=\''+totalName+'\']').val(total.toFixed(4));
			}
		}
	}
	
	var Ebill = function(ele, opt, reference){
		this.$element = ele;
		this.title = new BillTitle(this.$element, opt.title);
		this.form = $('<form id="bill_form"><input type="hidden" name="id"></form>');
		
		this.t_columns = new BillTopColumns(this.form, opt['top-columns']);
		this.b_columns = new BillBottomnColumns(this.form, opt['bottom-columns']);
		this.item_table = new BillItemTable(this.form, opt['item-table']);
		
		this.defaults = {
		   width : '100%',
		};
		this.options = $.extend({}, this.defaults, opt);	
		this.reference = reference;
		
	}
	
	Ebill.prototype = {
		draw: function(){
			
			this.title.draw();
			this.t_columns.draw(this.reference);
			this.item_table.draw();
			this.b_columns.draw(this.reference);
			this.$element.append(this.form);
			this.$element.find("select[select2]").select2({
				placeholder: '',
				allowClear: false
			});
		},
		
		resetBill: function(){
			var ele = this.$element;
			ele.find('[name=\'id\']').val('');
			var items = this.options['top-columns'].concat(this.options['bottom-columns']);
			$.each(items, function(i, item){
				var _ele_item = ele.find('[name=\''+item.name+'\']');
				_ele_item.val('');
				if (item.type == 'select' && item.select2){
					_ele_item.select2();
				}
				if (item.type == 'date'){
					var now = new Date();
		    		var _value = now.getFullYear() + '-' + (now.getMonth() + 1) + '-' + now.getDate();
					_ele_item.val(_value);
				}
				if (item.type == 'indentify'){
					$.ajax({
	        			url: getProjectName() + item.src,
	        			async: false,
	        			success: function(result){
	        				_ele_item.val(result.code);
	        			}
	        		});
				}
			});
			this.item_table.tableData = [{},{},{},{},{}];
			this.item_table.currentRow = 1;
			this.item_table.draw();
			
		},
		
		fillBill: function(data){
			var self = this;
			this.resetBill();
			$.each(data, function(key, value){
				if (value instanceof Array){
					self.item_table.fillTable(value);
				}else{					
					if (typeof value == 'object'){
						$.each(value, function(skey, svalue){
							var ele = self.$element.find('[name=\''+key+'.'+skey+'\']');
							
							if (ele){
								ele.val(svalue);
								if (ele.attr('select2')){
									ele.select2();
								}
							}
						});
					}else {
						var ele = self.$element.find('[name=\''+key+'\']');
						if (ele){
							ele.val(value);
							if (ele.attr('type') == 'hidden'){
								self.$element.find('#'+key).text(value);
							}
						}
					}
				}
			});
		},
		
		fillBillItem: function(data){
			this.item_table.fillTable(data.tableData);
		},
		
		getBillData: function(){
			return JSON.stringify(this.item_table.tableData);
		},
		
		submitBill: function(url, successMsg){
			var self = this;
			var tableParam = {};
			tableParam['billItem'] = this.getBillData();
			
			$('#bill_form').ajaxSubmit({
				type: 'post',
				url: url,
				data: tableParam,
				success:function(result){
					self.resetBill();
					Ewin.toast(successMsg);
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
				}
			});
		}
	}
	
	var bills = [];
	$.fn.eBill = function(options){
		var id = this.attr("id");
		var eBill;
		var exist = false;
		$.each(bills, function(i, bill){
			if (bill.id == id){
				eBill = bill.bill;
				exist = true;
				return false;
			}
		});
		
		if (exist){
			return eBill;
		}
		
		var reference = [];
		$.ajaxSettings.async = false; 
		if (options.reference){
			$.each(options.reference, function(i, refItem) {
				if (refItem.data){
					reference[refItem.name] = refItem.data;
					return true;
				}
				
				var url = refItem.src;
				if (url){
					if (url.indexOf('.do')>-1){
						url = getProjectName() + refItem.src;
					}
					$.getJSON(url, "", function(data){
						reference[refItem.name] = data;
					});
				}
				
				
			});
		}
		$.ajaxSettings.async = true; 
		eBill = new Ebill(this, options, reference);
		eBill.draw();
		
		bills.push({id: id, bill: eBill});
		return {
			bill: eBill,
			fillBillItem: function(selections){
				eBill.fillBillItem(selections);
			},
			commit: function(url, successMsg){
				eBill.submitBill(url, successMsg);
			},
			fillBill: function(obj){
				eBill.fillBill(obj);
			},
			resetBill: function(){
				eBill.resetBill();
			}
		}
	}
})(jQuery);
