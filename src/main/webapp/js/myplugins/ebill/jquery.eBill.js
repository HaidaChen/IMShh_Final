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
		html +=   '<label>' + opt.label + '</label>';
		
		switch(opt.type){
			case 'select':
			    html += '<select name="'+opt.name+'" class="select">';
			    html +=   '<option value=""></option>';
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
			    		html += '<input type="date" name="'+opt.name+'" value="'+_value+'">';
			    	}else{
			    		html += '<input type="date" name="'+opt.name+'" value="'+opt.value+'">';
			    	}
			    }else{
			    	html += '<input type="date" name="'+opt.name+'">';
			    }break;
		    case 'indentify':
		        var _value = '';
		        if (opt.formatter){
		        	var reg = new RegExp("\\[([^\\[\\]]*?)\\]", 'igm');
		        	var _formatter = opt.formatter;
		        	var now = new Date();
		        	_value = _formatter.replace(reg, function (node, key) {
		                return {
		                	yyyyMMdd: now.getFullYear()+''+(now.getMonth() + 1)+''+now.getDate(),
		                	'increase-3': '001'
		                }[key];
		            });
		        }
		        html += '<input type="text" name="'+opt.name+'" value="'+_value+'">';
			    break;
			case 'text':
				html += '<input type="text" name="'+opt.name+'">';
				break;
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
				html += '<th style="width: '+column.length+'">' + column.label + '</th>';
			});
			html += '</tr>';
			
			/*根据表格对象构造行，默认的表格对象是5个空对象*/
			$.each(this.tableData, function(rowIndex, row){
				html += '<tr>';
				$.each(_columns, function(i, column){
					if (column.hide) return true;
					if (column.type == 'row-no'){
						html += '<td style="text-align: center" type="'+column.type+'" row-no="'+(rowIndex+1)+'">'+(rowIndex+1)+'</td>';	
					}else{
						html += '<td name="'+column.name+'" type="'+column.type+'"';	
						if (column["data-ref"]) html += ' data-ref="'+column["data-ref"]+'"';
						if (column["ref-field"]) html += ' data-ref="'+column["ref-field"]+'"';
						if (column["ref-tigger"]) html += ' ref-tigger='+column["ref-tigger"];
						html += '>';
						
						if (column.formatter){
							html += column.formatter(row);
						}else{
							if (row[column.name])html += row[column.name];
						}
						html += '</td>';
					}
				});
				html += '</tr>';
			});
			html += '</tr>';
			
			if (this.tableOpt.total){
				html += '<tr>';
				html +=   '<td colspan = "8" style="font-size: 1.2em; font-weight: 600">'+this.tableOpt["total-label"]+'<span id="billTotal" total-column="'+this.tableOpt["total-column"]+'"></span></td>';
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
					_input.click(function(){
						$(this).focus();
					});
				}
			});	
			
			this.$element.find('table.gridtable td').hover(function(){
				_table.currentRow = $('table.gridtable tr').index($(this).parents('tr'));
				var type = $(this).attr('type');
				
				if (type == 'ref' && $(this).attr('ref-tigger')){
					if ($(this).find('.btn').length == 1){
						$(this).find('.btn').remove();
					}else{
						var _btn = $('<span class="btn btn-primary btn-xs" >选择</span>');
						$(this).append(_btn);
						_btn.click(function(){
							//$('#modal_material').modal({backdrop: 'static'});
							Ewin.load({id: 'mtl_ref', title: '选择原材料', url: 'fragment/mtl_ref.html', style: 'width: 800px'});
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
					if (column.type !== 'ref'){
						if (row[column.name]){
							t_row[column.name] = row[column.name];
						}
					}else{
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
					var _obj = {};
					_obj[item] = value;
					_tableData[index][obj] = _obj;
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
		        		var calRes = math.eval(exp).toFixed(2);
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
				var total = 0;
				$.each(this.tableData, function(i, row) {
					if (row[column]){
						total += parseFloat(row[column]);
					}
				});
				this.$element.find('#billTotal').text(total.toFixed(2));
			}
		}
	}
	
	var Ebill = function(ele, opt, reference){
		this.$element = ele;
		this.title = new BillTitle(this.$element, opt.title);
		this.form = $('<form id="bill_form"></form>');
		
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
			this.$element.find("select").select2({
				placeholder: '',
				allowClear: false
			});
		},
		
		fillBill: function(data){
			this.item_table.fillTable(data.tableData);
		},
		
		getBillData: function(){
			return JSON.stringify(this.item_table.tableData);
		},
		
		submitBill: function(){
			var tableParam = {};
			tableParam['billItem'] = this.getBillData();
			
			$('#bill_form').ajaxSubmit({
				type: 'post',
				url: getProjectName()+'/mtlin/newMaterialIn.do',
				data: tableParam,
				success:function(result){
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
				var url = refItem.src;
				if (url.indexOf('.do')>-1){
					url = getProjectName() + refItem.src;
				}
				$.getJSON(url, "", function(data){
					reference[refItem.name] = data;
				});
			});
		}
		$.ajaxSettings.async = true; 
		eBill = new Ebill(this, options, reference);
		eBill.draw();
		
		bills.push({id: id, bill: eBill});
		return {
			fillBill: function(selections){
				eBill.fillBill(selections);
			},
			commit: function(){
				eBill.submitBill();
			}
		}
	}
})(jQuery);
