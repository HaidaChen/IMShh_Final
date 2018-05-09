/**
 * 
 */
var FillForm = function(){
	var oFillForm = new Object();
	
	oFillForm.fill = function(filter, type, urlparam){
		var container = $(filter);
		var url = getProjectName() + container.attr("url");
		if (urlparam)
			url = url + "?" + urlparam;
		var attr = container.attr("fieldAttr");
		var perfix = container.attr("fieldPerfix");
		
		$.ajax({
			url: url,
			success: function(data){
				fillData(data, container, attr, perfix, type);
			}
		});
	}
	
	return oFillForm;
};

var fillData = function(data, container, fieldAttr, fieldPerfix, formType){
	var items = container.find(".formItem, input[formItem=true]");
	$.each(items, function(index, e){		
		var field = $(e).attr(fieldAttr);
		if (fieldPerfix){
			field = $(e).attr(fieldAttr).substring(fieldPerfix.length);
		}		
		var dataValue = getFieldValue(data, field, $(e).attr("valuetype"), $(e).attr("valueformatter"));
		
		if (e.tagName == 'IMG'){	
			$(e).attr("src", getProjectName() + dataValue);
		}
		else if (e.tagName == 'INPUT' && $(e).attr('type') == 'radio'){
			if ($(e).val() == dataValue){
				$(e).prop("checked","true");
			}else{
				$(e).removeAttr("checked");
			}
		}else if (e.tagName == 'INPUT' && $(e).attr('type') == 'checkbox'){			
			var values = dataValue.split(";");
			
			$.each(values, function(index, value){
				if ($(e).val() == value){	
					$(e).prop("checked","checked");
					return false;
				}else{
					$(e).removeAttr("checked");
				}
			})
		}
		else{			
			if (formType == 0){				
				$(e).html(dataValue);
			}else{
				$(e).val(dataValue);
			}
		}		
	});
}

var getFieldValue = function(data, field, valuetype, valueformatter){
	if (valuetype){
		if (valuetype == 'dictionary'){
			var dictionary = JSON.parse(valueformatter);
			var value = '';
			$.each(dictionary, function(index, obj){				
				if (data[field] == obj.key){
					value = obj.value;
					return false;
				}
			});
			return value;
		}
		
		if (valuetype == 'date'){
			if (data[field] == null)
				return "";
			return new Date(data[field]).Format(valueformatter);
		}
		
		if (valuetype == 'list'){
			var value = '';
			
			var fullfield = field.split('_');
			var parentName = fullfield[0];
			var subName = fullfield[1];
			
			if (data[parentName] != null){
				$.each(data[parentName], function(index, subObject){
					value += subObject[subName] + ';';
				});
			}
			
			if (value != ''){
				value = value.substring(0, value.length - 1);
			}
			
			return value;
		}
	}
	return data[field];
}

Date.prototype.Format = function (fmt) { //author: meizz   
    var o = {  
        "M+": this.getMonth() + 1, //月份   
        "d+": this.getDate(), //日   
        "H+": this.getHours(), //小时   
        "m+": this.getMinutes(), //分   
        "s+": this.getSeconds(), //秒   
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度   
        "S": this.getMilliseconds() //毫秒   
    };  
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));  
    for (var k in o)  
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));  
    return fmt;  
}  