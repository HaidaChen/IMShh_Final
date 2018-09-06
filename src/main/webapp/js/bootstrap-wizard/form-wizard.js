var FormWizard = function () {
    return {
        init: function () {
            if (!jQuery().bootstrapWizard) {
                return;
            }
			/*-----------------------------------------------------------------------------------*/
			/*	Show country list in Uniform style
			/*-----------------------------------------------------------------------------------*/
            /*$("#country_select").select2({
                placeholder: "Select your country"
            });*/

            var wizform = $('#formOrder');
			var alert_success = $('.alert-success', wizform);
            var alert_error = $('.alert-danger', wizform);
            
            
            $("#tbl_new_view_orderItems").bootstrapTable({
    			data: [],
    			cache: false,
    			columns: [
    			{field: 'pdtNo',title: '货号' }, 
    			{field: 'pdtName',title: '品名'}, 
    			{field: 'content',title: '含量'}, 
    			{field: 'priceRMB',title: '￥单价'}, 
    			{field: 'priceDollar',title: '$单价'}, 
    			{field: 'quantity',title: '数量'}]});
            
            
			/*-----------------------------------------------------------------------------------*/
			/*	Validate the form elements
			/*-----------------------------------------------------------------------------------*/
            wizform.validate({
                doNotHideMessage: true,
				errorClass: 'error-span',
                errorElement: 'span',
                rules: {
                    /* Order Info */
                	custName: {required: true},
                    orderDate: {required: true},
                    identify: {required: true},
                    exchangeRate: {number: true},
                    
                    orderDetails: {required: true}
                },

                invalidHandler: function (event, validator) { 
                    alert_success.hide();
                    alert_error.show();
                },

                highlight: function (element) { 
                    $(element)
                        .closest('.form-group').removeClass('has-success').addClass('has-error'); 
                },

                unhighlight: function (element) { 
                    $(element)
                        .closest('.form-group').removeClass('has-error'); 
                },

                success: function (label) {
                    if (label.attr("for") == "gender") { 
                        label.closest('.form-group').removeClass('has-error').addClass('has-success');
                        label.remove(); 
                    } else { 
                        label.addClass('valid') 
                        .closest('.form-group').removeClass('has-error').addClass('has-success'); 
                    }
                }
            });

            /*-----------------------------------------------------------------------------------*/
			/*	Initialize Bootstrap Wizard
			/*-----------------------------------------------------------------------------------*/
            $('#formOrder').bootstrapWizard({
                'nextSelector': '.nextBtn',
                'previousSelector': '.prevBtn',
                onNext: function (tab, navigation, index) {
                	alert_success.hide();
                    alert_error.hide();
                    if (wizform.valid() == false) {
                        return false;
                    }
                    
                    if (index == 2){
                    	var orderDetails = $('#formOrder').find("input[name=orderDetails]").val();
                    	var oorderDetail = JSON.parse(orderDetails);
                    	if (oorderDetail.length == 0){
                    		alert_error.children("#alter_error").html("订单项信息，必须填写！");
                    		alert_error.show();
                    		return false;
                    	}else{
                    		alert_error.children("#alter_error").html("请按照提示，正确的填写订单信息！");
                    		alert_error.hide();
                    		
                    		/*var amountDollar = $('#formOrder').find("input[name=amountDollar]").val();
                    		var exchangeRate = $('#formOrder').find("input[name=exchangeRate]").val();
                    		
                    		if (amountDollar!="" && exchangeRate !=""){
                    			var amountRMB = (parseFloat(amountDollar)*parseFloat(exchangeRate)).toFixed(4);
                    			$("#new_exchange").show();
                    			$("#new_exchange").click(function(){
                    				$('#formOrder').find("p[data-display=amountRMB]").html(amountRMB);
                                });
                    		}
                    		*/
                    		var fillForm = new FillForm();
                        	fillForm.exchange($("#tab_orderInfo"), $("#tab_confirm"), "p", "data-display");
                        	
                        	$("#tbl_new_view_orderItems").bootstrapTable("refreshOptions", {data:oorderDetail});
                    	}
                    }
                    
                    var total = navigation.find('li').length;
                    var current = index + 1;
                    
                    jQuery('li', $('#formOrder')).removeClass("done");
                    var li_list = navigation.find('li');
                    for (var i = 0; i < index; i++) {
                        jQuery(li_list[i]).addClass("done");
                    }
                    
                    
                    if (current == 1) {
                        $('#formOrder').find('.prevBtn').hide();
                    } else {
                        $('#formOrder').find('.prevBtn').show();
                    }
                    if (current >= total) {
                        $('#formOrder').find('.nextBtn').hide();
                        $('#formOrder').find('.submitBtn').show();
                    } else {
                        $('#formOrder').find('.nextBtn').show();
                        $('#formOrder').find('.submitBtn').hide();
                    }
                    
                    
                },
                onPrevious: function (tab, navigation, index) {
                    alert_success.hide();
                    alert_error.hide();
                    var total = navigation.find('li').length;
                    var current = index + 1;
                    jQuery('li', $('#formOrder')).removeClass("done");
                    var li_list = navigation.find('li');
                    for (var i = 0; i < index; i++) {
                        jQuery(li_list[i]).addClass("done");
                    }
                    if (current == 1) {
                        $('#formOrder').find('.prevBtn').hide();
                    } else {
                        $('#formOrder').find('.prevBtn').show();
                    }
                    if (current >= total) {
                        $('#formOrder').find('.nextBtn').hide();
                        $('#formOrder').find('.submitBtn').show();
                    } else {
                        $('#formOrder').find('.nextBtn').show();
                        $('#formOrder').find('.submitBtn').hide();
                    }
                },
				onTabClick: function (tab, navigation, index) {
                    return false;
                },
                onTabShow: function (tab, navigation, index) {
                    var total = navigation.find('li').length;
                    var current = index + 1;
                    var $percent = (current / total) * 100;
                    $('#formOrder').find('.progress-bar').css({
                        width: $percent + '%'
                    });
                }
            });

            $('#formOrder').find('.prevBtn').hide();
            $('#formOrder .submitBtn').click(function () {
            	$("#formOrder").ajaxSubmit({
					url: getProjectName()+"/order/save.do",
					success: function(){
						window.location.reload();
					}
				});
            }).hide();
        }
    };
}();