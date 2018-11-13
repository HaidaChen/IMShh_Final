;(function($){
	window.ImportData = function(){
		var defaultOpts = {
			url: '',
			callback: '',
			templaterName: ''
		};
		
		var htmlModal = '<div id="importModal" class="modal fade bs-example-modal-sm" role="dialog" aria-labelledby="modalLabel">' +
					        '<div class="modal-dialog modal-sm"  >' +
								'<div class="modal-content">' +
						          '<div class="modal-header">' +
						            '<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>' +
						            '<h4 class="modal-title" id="myModalLabel">请选择Excel文件</h4>' +
						          '</div>' +  
						          '<div class="modal-body">' +
						            '<form method="POST"  enctype="multipart/form-data" id="form_upload">'+
						              '<a href="[templaterName]">点击下载模板</a>'+
						              '<input id="upfile" name="upfile" type="file" class="file" readonly="true">' +
						              '<button type="button" name="import">导入</button>' +
						            '</form>' +
						          '</div>' +
						          '<div class="modal-footer"></div>' +
						        '</div>' +
						      '</div>' +
					      '</div>';
		var reg = new RegExp("\\[([^\\[\\]]*?)\\]", 'igm');

		var checkData = function(){  
		    var fileDir = $("#upfile").val();  
		    var suffix = fileDir.substr(fileDir.lastIndexOf("."));  
		    if("" == fileDir){  
		        alert("选择需要导入的Excel文件！");  
		        return false;  
		    }  
		    if(".xls" != suffix && ".xlsx" != suffix ){  
		        alert("选择Excel格式的文件导入！");  
		        return false;  
		    }  
		    return true;  
		}  

		var init = function(options){
			var _options = $.extend({}, defaultOpts, options);
			var content = htmlModal.replace(reg, function (node, key) {
                return {
                	templaterName: _options.templaterName
                }[key];
            });
            $('body').append(content);
            
            $('#importModal').modal({
                backdrop: 'static'
            });
            $('#importModal').on('hide.bs.modal', function (e) {
                $('body').find('#importModal').remove();
            });
            $('#importModal .modal-body button[name="import"]').click(function(){
            	
            	if(checkData()){		
        			$("#importModal").find(".modal-footer").html("数据导入中请稍后.....");
        			$("#importModal").find("input,a").attr("disable", "disable");
        			
        		    setTimeout(function(){
        			   var option = {
        				      　　 url : _options.url,
        				     　　  dataType : 'text',
        				      　　 success : function(data) {
        				    	  $("#importModal").find(".modal-footer").html(data);	
        						  $("#importModal").find("input,a").removeAttr("disable");  	 
        						  _options.callback();
        				     },
        				     error : function(XmlHttpRequest, textStatus, errorThrown){
        				    	 $("#importModal").find("input,a").removeAttr("disable");  	
        				    	 $("#importBox").find(".modal-footer").text("数据导入失败");			  	     
        				     }
        				};
        				$("#form_upload").ajaxSubmit(option);
        		    }, 2000);
        	　　　　 
        		}
            });
		};
		
		return {
			show: function(option){
				init(option);
			}
		};
	}();
	
})(jQuery);