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
		
		if ($(e).type == 'img'){
			$(e).attr("src", data[field]);
		}else{
			if (formType == 0){
				if($(e).attr("valueformatter")){
					
				}else{
					$(e).html(data[field]);
				}
			}else{
				$(e).val(data[field]);
			}
		}		
	});
}