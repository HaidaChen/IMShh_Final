/**
 * 
 */
var FillForm = function(){
	var oFillForm = new Object();
	
	oFillForm.fill = function(filter, type){
		var container = $(filter);
		var url = getProjectName() + container.attr("url");
		var attr = container.attr("fieldAttr");
		var perfix = container.attr("fieldPerfix");
		if (container.attr("params"))
			var params = eval(container.attr("params"));
		
		$.ajax({
			url: url+"?"+params,
			success: function(data){
				fillData(data, container, attr, perfix, type);
			}
		});
	}
	
	return oFillForm;
};

var fillData = function(data, container, fieldAttr, fieldPerfix, formType){
	var items = container.find(".formItem");
	$.each(items, function(index, e){		
		var field = $(e).attr(fieldAttr);
		if (fieldPerfix){
			field = $(e).attr(fieldAttr).substring(fieldPerfix.length);
		}		
		var dataValue = getFieldValue(data, field, $(e).attr("valuetype"), $(e).attr("valueformatter"));
		console.log(dataValue+"-");
		if ($(e).type == 'img'){
			$(e).attr("src", data[field]);
		}else{			
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
			$.each(dictionary, function(index, obj){				
				if (data[field] == obj.key){
					alert(obj.value);
					return obj.value;
				}
			});
		}
		
		if (valuetype == 'date'){
			return new Date(data[field]).Format(valueformatter);
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