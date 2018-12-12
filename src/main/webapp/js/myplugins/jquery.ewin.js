(function ($) {
    window.Ewin = function () {
        var html = '<div id="[Id]" class="modal fade bs-example-modal-sm" role="dialog" aria-labelledby="modalLabel">' +
                              '<div class="modal-dialog modal-sm">' +
                                  '<div class="modal-content">' +
                                      '<div class="modal-header">' +
                                          '<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>' +
                                          '<h4 class="modal-title" id="modalLabel">[Title]</h4>' +
                                      '</div>' +
                                      '<div class="modal-body">' +
                                      '<p>[Message]</p>' +
                                      '</div>' +
                                       '<div class="modal-footer">' +
        '<button type="button" class="btn btn-default cancel" data-dismiss="modal">[BtnCancel]</button>' +
        '<button type="button" class="btn btn-primary ok" data-dismiss="modal">[BtnOk]</button>' +
    '</div>' +
                                  '</div>' +
                              '</div>' +
                          '</div>';
        var html_modal_frame = '<div id="[Id]" class="modal fade bs-example-modal-lg" role="dialog" aria-labelledby="myLargeModalLabel">' +
        			'<div class="modal-dialog modal-lg" role="document" style="[Style]">' +
        				'<div class="modal-content">' +
			                '<div class="modal-header">' +
			                    '<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>' +
			                    '<h5 class="modal-title" id="modalLabel">[Title]</h5>' +
			                '</div>' +
			                '<div class="modal-body">' +
			                	'<div class="load-Content"></div>' +
			                '</div>' +
			            '</div>' +
			        '</div>' +
			    '</div>';

        var dialogdHtml = '<div id="[Id]" class="modal fade" role="dialog" aria-labelledby="modalLabel">' +
                              '<div class="modal-dialog">' +
                                  '<div class="modal-content">' +
                                      '<div class="modal-header">' +
                                          '<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>' +
                                          '<h4 class="modal-title" id="modalLabel">[Title]</h4>' +
                                      '</div>' +
                                      '<div class="modal-body">' +
                                      '</div>' +
                                  '</div>' +
                              '</div>' +
                          '</div>';
        
        var reg = new RegExp("\\[([^\\[\\]]*?)\\]", 'igm');
        var generateId = function () {
            var date = new Date();
            return 'mdl' + date.valueOf();
        }
        var init = function (options) {
            options = $.extend({}, {
                title: "操作提示",
                message: "提示内容",
                btnok: "确定",
                btncl: "取消",
                width: 200,
                auto: false,
                closeAuto: true
            }, options || {});
            var modalId = generateId();
            var content = html.replace(reg, function (node, key) {
                return {
                    Id: modalId,
                    Title: options.title,
                    Message: options.message,
                    BtnOk: options.btnok,
                    BtnCancel: options.btncl
                }[key];
            });
            $('body').append(content);
            if (!options.closeAuto){
            	$("button.ok").removeAttr("data-dismiss");
            }
            $('#' + modalId).modal({
                width: options.width,
                backdrop: 'static'
            });
            $('#' + modalId).on('hide.bs.modal', function (e) {
                $('body').find('#' + modalId).remove();
            });
            return modalId;
        }

        var initLoad = function(options){
        	options = $.extend({}, {
                id: 'loadWin',
        		title: '新的窗口',
                width: 800,
                style: '',
                rmvWin: true,
                initShow: true
            }, options || {});
            
        	if (!$('body').find('#' + options.id) || $('body').find('#' + options.id).length == 0){
	            var content = html_modal_frame.replace(reg, function (node, key) {
	                return {
	                    Id: options.id,
	                    Title: options.title,
	                    Style: options.style
	                }[key];
	            });
	            var contentEle = $(content);
	            $('body').append(contentEle);
	            
	            contentEle.find('.load-Content').load(options.url, options.callback);
	            
	            if (options.initShow){
	            	$('#' + options.id).modal({
		                width: options.width,
		                backdrop: 'static'
		            });
	            }
        	}
        	
            if (options.rmvWin){
            	$('#' + options.id).on('hide.bs.modal', function (e) {
                	$('body').find('#' + options.id).remove();
                });
            }            
            return $('#' + options.id);
        }
        
        return {
        	toast: function (message, millisec){
        		var duration = 2000;
        		if (millisec) duration = millisec;
        		var str='<div class="toast"><span></span></div>';
        	    $("body").append(str);
        	    $(".toast").fadeIn().find("span").html(message);
        	    setTimeout(function(){
        	        $(".toast").fadeOut();
        	        $(".toast").remove();
        	    },duration);
        	},
            alert: function (options) {
                if (typeof options == 'string') {
                    options = {
                        message: options
                    };
                }
                var modal = init(options);
                modal.find('.ok').removeClass('btn-success').addClass('btn-primary');
                modal.find('.cancel').hide();

                return {
                    modal: modal,
                    on: function (callback) {
                        if (callback && callback instanceof Function) {
                            modal.find('.ok').click(function () { callback(true); });
                        }
                    },
                    hide: function (callback) {
                        if (callback && callback instanceof Function) {
                            modal.on('hide.bs.modal', function (e) {
                                callback(e);
                            });
                        }
                    }
                };
            },
            
            confirm: function (options) {
            	var id = init(options);
                var modal = $('#' + id);
                
                modal.find('.ok').removeClass('btn-primary').addClass('btn-success');
                modal.find('.cancel').show();
                return {
                	id: id,
                    on: function (callback) {
                        if (callback && callback instanceof Function) {
                            modal.find('.ok').click(function () { callback(true); });
                            modal.find('.cancel').click(function () { callback(false); });
                        }
                    },
                    hide: function (callback) {
                        if (callback && callback instanceof Function) {
                            modal.on('hide.bs.modal', function (e) {
                                callback(e);
                            });
                        }
                    }
                };
            },
            load: function(options){
            	var modal = initLoad(options);
            	
                return {
                	modal: modal,
                    on: function (callback) {
                        if (callback && callback instanceof Function) {
                        	callback(id);
                        }
                    },
                    hide: function (callback) {
                        if (callback && callback instanceof Function) {
                            modal.on('hide.bs.modal', function (e) {
                                callback(e);
                            });
                        }
                    }
                };
            },
            confirm: function (options) {
                var id = init(options);
                var modal = $('#' + id);
                modal.find('.ok').removeClass('btn-primary').addClass('btn-success');
                modal.find('.cancel').show();
                return {
                    id: id,
                    on: function (callback) {
                        if (callback && callback instanceof Function) {
                            modal.find('.ok').click(function () { callback(true); });
                            modal.find('.cancel').click(function () { callback(false); });
                        }
                    },
                    hide: function (callback) {
                        if (callback && callback instanceof Function) {
                            modal.on('hide.bs.modal', function (e) {
                                callback(e);
                            });
                        }
                    }
                };
            },
            dialog: function (options) {
                options = $.extend({}, {
                    title: 'title',
                    url: '',
                    width: 800,
                    height: 550,
                    onReady: function () { },
                    onShown: function (e) { }
                }, options || {});
                var modalId = generateId();

                var content = dialogdHtml.replace(reg, function (node, key) {
                    return {
                        Id: modalId,
                        Title: options.title
                    }[key];
                });
                $('body').append(content);
                var target = $('#' + modalId);
                target.find('.modal-body').load(options.url);
                if (options.onReady())
                    options.onReady.call(target);
                target.modal();
                target.on('shown.bs.modal', function (e) {
                    if (options.onReady(e))
                        options.onReady.call(target, e);
                });
                target.on('hide.bs.modal', function (e) {
                    $('body').find(target).remove();
                });
            }
        }
    }();
})(jQuery);