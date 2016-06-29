$(function(){
//	$(document).ajaxStart(function(){
//		 $( "#loading").show();
//	});
//	$(document).ajaxStop(function(){
//		$( "#loading").hide();
//	});
	
     $.validator.addMethod("greaterThan", function(value, element, param) {
    	 console.log(startDate);
    	var startDate=$(param).val();
    	if(startDate==''){
    		return true;
    	}
    	var date1 = new Date(Date.parse(startDate.replace("-", "/")));
        var date2 = new Date(Date.parse(value.replace("-", "/")));
        return date1 <= date2;
    },'结束日期必须大于等于开始日期 ');
     
     //zh-cn
     $.datepicker.regional["zh-CN"] = { closeText: "关闭", prevText: "&#x3c;上月", nextText: "下月&#x3e;", currentText: "今天", monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"], monthNamesShort: ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"], dayNames: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"], dayNamesShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"], dayNamesMin: ["日", "一", "二", "三", "四", "五", "六"], weekHeader: "周", dateFormat: "yy-mm-dd", firstDay: 1, isRTL: !1, showMonthAfterYear: !0, yearSuffix: "年" }
     $.datepicker.setDefaults($.datepicker.regional["zh-CN"]);
	
     //set default css style for validate
	$.validator.setDefaults({
		errorElement : 'div',
		errorClass : 'help-block',
		focusInvalid : false,
		ignore : "",
		highlight : function(e) {
			$(e).closest('.form-group').removeClass('has-info').addClass('has-error');
		},
		success : function(e) {
			$(e).closest('.form-group').removeClass('has-error');//.addClass('has-info');
			$(e).remove();
		}
	});
});
$.ajaxSetup({
	contentType: "application/x-www-form-urlencoded; charset=utf-8",
	//fixed ajax request body content(json) issue
	headers: {"_isAjax":"true"}
});

//------------------------
//ajax 处理全局异常和session过期问题
var _overrideAjax = true;
(function overrideJQeuryAjax(){
	if(!_overrideAjax){
		return;
	}
	var oriAjax = $.ajax;
	// Override jquery ajax to check if session is valid.
	$.ajax = function(options) {
		var dataType = options.dataType;
		var overrideDefault=options.overrideDefault;
		if(typeof(overrideDefault)=='undefined'){
			overrideDefault=false;
		}
		var callback = options["callback"];
		var oriSuccess = options["success"];
		if ($.isFunction(oriSuccess)) {
			options["success"] = function(result) {
				if (!overrideDefault && !_checkSessionTimeout(result)) {
					//ajax session过期刷新当前页面501
					window.location.href=window.location.href;
					return false;
				}
				if (!overrideDefault && !_checkAuthDenied(result)) {
					//没有权限 401
					dialogOk('抱歉！您没有该操作的权限');
					return false;
				}
				if (!overrideDefault && !_checkAjaxError(result)) {
					//全局异常500
					dialogOk('系统繁忙，请稍后重试');
					return false;
				}
				oriSuccess(result);
			};
		}
		var oriComplete = options["complete"];
		if ($.isFunction(oriComplete)) {
			options["complete"] = function(xmlHttpRequest, textStatus) {
				var result = xmlHttpRequest.responseText;
				if (!overrideDefault && !_checkSessionTimeout(result, dataType)) {
					window.location.href=window.location.href;
					return false;
				}
				oriComplete(xmlHttpRequest, textStatus);
			};
		}	
		//if error function is undefined, set default logic
		var orierror = options["error"];		
		if (!$.isFunction(orierror)) {
			options["error"] = function(err) {
				dialogOk('系统繁忙，请稍后重试');
			};
		}		
		oriAjax(options);
	};
})();

function _checkSessionTimeout(result){
	var flag = true;	
	if(typeof(result)!='undefined' && result==501){
		flag = false;
	}
	return flag;
}
function _checkAuthDenied(result){
	var flag = true;	
	if(typeof(result)!='undefined' && result==401){
		flag = false;
	}
	return flag;
}
function _checkAjaxError(result){
	var flag = true;	
	if(typeof(result)!='undefined' && result==500){
		flag = false;
	}
	return flag;
}
//-------------------

//确认框
function dialogConfirm(message,fun){
	$('#dialogConfirmModalBody').text(message);
	
	$('#dialogConfirmokbutton').bind('click',function(){
		$('#dialogConfirmModal').modal('hide');
		if($.isFunction(fun)){
			fun();
		}
		$('#dialogConfirmokbutton').unbind('click');
	});
	$('#dialogConfirmModal').modal('show');

}
//信息提示框
function dialogOk(message,fun){
	$('#dialogOKModalBody').text(message);
	$('#dialogOKbutton').bind('click',function(){
		$('#dialogOKModal').modal('hide');
		if($.isFunction(fun)){
			fun();
		}
		$('#dialogOKbutton').unbind('click');
	});
	$('#dialogOKModal').modal('show');
}


//详细列表
function dialogView(id,action,title){
	if(typeof(action)=='undefined'){
		var action='view';
	}
	var url=contextPath+'/'+module+'/'+action+'.json';
	var params={id:id};
	ajaxGetRequestFormData(url,params,function(data){
		$('#dialogDetaiModalbody').html(data);
		if(typeof(title)!='undefined'){
			$('#dialogDetailModal').find('h4').text(title);			
		}
		$('#dialogDetailModal').modal('show');
	});
}

//信息提示框倒计时
function dialogOkCD(message,fun){
	var moduleName='';
	if(module=='module'){
		moduleName='模块管理';
	}else if(module=='category'){
		moduleName='商户分类管理';
	}else if(module=='district'){
		moduleName='商户地区管理';
	}else if(module=='admin'){
		moduleName='系统用户管理';
	}
	$('#dialogOKModalBody').html(message+', <span id="dialogOkCountdown">3</span>'+'秒后返回'+moduleName+'首页');
	var j=3;
	var dialogOKInterval=setInterval(function(){
		if (j == 1) {
			$('#dialogOKbutton').click();
		}
		$('#dialogOkCountdown').text(j);
		j--;
	},1000);
	
	$('#dialogOKbutton').bind('click',function(){
		$('#dialogOKModal').modal('hide');
		clearInterval(dialogOKInterval);
		if($.isFunction(fun)){
			fun();
		}
		$('#dialogOKbutton').unbind('click');
	});
	$('#dialogOKModal').modal('show');
}

function dialogformModalHide(){
	$('#dialogformModal').modal('hide');
	go_href(window.location.href);
}
function ajaxButtonAction(module,action,fun,url,funcall){
	if(action=='添加' || action=='编辑'){
		dialogFormvalidate.resetForm();
		if(action=='添加'){
			$('#dialogformModal').find('form')[0].reset();
		}
		$('#dialogformModalHeader').text(action+module);
		$('#dialogformModal').on('show.bs.modal', function (e) {
			if($.isFunction(fun)){
				fun();			
			}			
		});
		$('#dialogformModalOkbutton').unbind('click');
		$('#dialogformModalOkbutton').bind('click',function(){
			var form=$('#dialogformModal').find('form');
			var url=form.attr('action');
			if(action=='添加'){
				url=url.replace('edit.json','add.json');
			}else if(action=='编辑'){
				url=url.replace('add.json','edit.json');
			}
			form.attr('action',url);
			$('#dialogformModal').find('form').submit();
		});
		$('#dialogformModal').modal('show');
	}else if(action=='删除'){
		var checkboxs=$('#resultTable').find('input[type="checkbox"][name="id"]:checked');
		if(checkboxs.length<=0){
			dialogOk("请至少选择一条数据进行操作");
			return;
		}
		dialogConfirm('此操作不可恢复，确认删除？',function(){
			var ids = new Array(); 
			checkboxs.each(function(i){
				ids.push($(this).val()); 
			});
			ajaxRequestFormData(url,{ids:ids},function(data){
				if(data.code==200){
					if($.isFunction(funcall)){
						funcall();			
					}else{
						auto_href();
					}										
				}else{
					dialogOk(data.message);
				}
			});
		});	
	}	
}

function ajaxUpdateButtonAction(type,message,funcall){
	if(type==1){//确认操作
		var checkboxs=$('#resultTable').find('input[type="checkbox"][name="id"]:checked');
		if(checkboxs.length<=0){
			dialogOk("请至少选择一条数据进行操作");
			return;
		}
		dialogConfirm(message,function(){
			var ids = new Array(); 
			checkboxs.each(function(i){
				ids.push($(this).val()); 
			});
			var url=$('#dialog-form-ok').attr('action');
			var params=$('#dialog-form-ok').serializeObject();
			params.ids=ids;
			ajaxRequestFormData(url,params,function(data){
				if(data.code==200){
					ajaxPagingLoad('search-form');										
				}else{
					dialogOk(data.message);
				}
			});
		});		
	}else if(type==2){
		var checkboxs=$('#resultTable').find('input[type="checkbox"][name="id"]:checked');
		if(checkboxs.length<=0){
			dialogOk("请至少选择一条数据进行操作");
			return;
		}
		dialogFormvalidate.resetForm();
		$('#dialogformModalOkbutton').unbind('click');
		$('#dialogformModalOkbutton').bind('click',function(){
			$('#dialogformModal').find('form').submit();
		});
		$('#dialogformModal').modal('show');
	}
}

function ajaxConfirmAction(uaction,params, message, funcall) {
	var checkboxs = $('#resultTable').find('input[type="checkbox"][name="id"]:checked');
	if (checkboxs.length <= 0) {
		dialogOk("请至少选择一条数据进行操作");
		return;
	}
	dialogConfirm(message, function() {
		var ids = new Array();
		checkboxs.each(function(i) {
			ids.push($(this).val());
		});
		var url = contextPath+'/'+module+'/'+uaction+'.json';
		params.ids = ids;
		ajaxRequestFormData(url, params, function(data) {
			if (data.code == 200) {
				ajaxPagingLoad('search-form');
			} else {
				dialogOk(data.message);
			}
		});
	});
}


function ajaxActionConfirm(url,params,message,funcall){
	dialogConfirm(message, function() {
		ajaxRequestFormData(url, params, function(data) {
			if (data.code == 200) {
				if($.isFunction(funcall)){
					funcall(data);
				}
			} else {
				dialogOk(data.message);
			}
		});
	});
}

function getSelectedIds(){
	var checkboxs=$('#resultTable').find('input[type="checkbox"][name="id"]:checked');
	var ids = new Array();
	checkboxs.each(function(i) {
		ids.push($(this).val());
	});
	return ids;
}

function getSelectedTargets(){
	var checkboxs=$('#resultTable').find('input[type="checkbox"][name="id"]:checked');
	var targets = new Array();
	checkboxs.each(function(i) {
		targets.push($(this).attr('target'));
	});
	return targets;
}


$(function(){
	initSelectCheckBox();	
});

function initSelectCheckBox(){
	var selectAllCheckBox=$('#resultTable input.checkboxall');
	$('#resultTable input[type=checkbox][name=id]').bind('click',function(){
		var allSize=$('#resultTable input[type=checkbox][name=id]').length;
		var checkedSize=$('#resultTable input[type=checkbox][name=id]:checked').length;
		if(allSize==checkedSize){
			selectAllCheckBox. prop("checked", true);
		}else{
			selectAllCheckBox. prop("checked", false);
		}
	});
	
	selectAllCheckBox.bind('click',function(){
		var checked=$(this).is(':checked');
		if(checked){
			$('#resultTable').find('input[type=checkbox][name=id]').prop("checked", true);
		}else{
			$('#resultTable').find('input[type=checkbox][name=id]').prop("checked", false);
		}
	});
}


function ajaxRequestJsonDate(url,params,callBack,extraData){
	$.ajax({
		url : url,
		type : "POST",
		async : false,
		data :  JSON.stringify(params),
		cache:false,
		contentType : 'application/json;charset=utf-8',
		success : function(data) {
			callBack(data,params,extraData);	
		}
	});
}

function ajaxRequestFormData(url,params,callBack,extraData){
	$.ajax({
		url : url,
		type : "POST",
		async : false,
		data :  params,
		cache:false,
		success : function(data) {
			callBack(data,params,extraData);	
		}
	});
}

function ajaxGetRequestFormData(url,params,callBack,extraData){
	$.ajax({
		url : url,
		type : "GET",
		async : false,
		data :  params,
		cache:false,
		success : function(data) {
			callBack(data,params,extraData);	
		}
	});
}

function ajaxAutoRequestFormData(id,callBack){
	var url=$('#'+id).attr('action');
	var params=$('#'+id).serialize();
	$.ajax({
		url : url,
		type : "POST",
		async : false,
		data :  params,
		dataType:'json',
		cache:false,
		success : function(data) {
			if($.isFunction(callBack)){
				callBack(data);					
			}else{
				go_href(window.location.href);
			}
		}
	});
}

function go_back(){
	window.location.href=contextPath+'/'+module+'/index.htm';
}

function go_href(url){
	window.location.href=url;
}

function auto_href(){
	window.location.href=window.location.href;
}
// from to object
$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};



//ajx 分页方法 开始
function ajaxPagingLoad(formId,pagenum,pagesize){
	if(typeof(pagenum)=='undefined'){
		pagenum=$('#pagenum').val();
	}
	if(typeof(pagesize)=='undefined'){
		pagesize=$('#pagesize').val();
	}
	if(typeof(pagenum)=='undefined'){
		pagenum=$('#formId').attr('pagenum');
	}
	if(typeof(pagesize)=='undefined'){
		pagesize=$('#formId').attr('pagesize');
	}
	if(typeof(pagenum)=='undefined'){
		pagenum=1;
	}
	if(typeof(pagesize)=='undefined'){
		pagesize=30;
	}
	var contentType=$('#'+formId).attr('contentType');
	if(typeof(contentType)=='undefined'){
		contentType='text';		
	}	
	
	//ajax load html 放置位置
	var ajaxDivId=$('#'+formId).attr('ajaxDivId');
	var url=$('#'+formId).attr('action')+"?pagenum="+pagenum+"&pagesize="+pagesize;
	
	var extraData={formId:formId,ajaxDivId:ajaxDivId};
	if(contentType=='text'){
		var params=$('#'+formId).serialize();		
		ajaxRequestFormData(url,params,ajaxPagingCallBack,extraData);
	}else{
		var params=$('#'+formId).serializeObject();	
		ajaxRequestJsonDate(url,params,ajaxPagingCallBack,extraData);
	}
	
}

function ajaxPagingCallBack(html,params,extraData){	
	var ajaxLoadContent=extraData.ajaxDivId;	
	$('#'+ajaxLoadContent).html(html);	
}

function pageselectCallback(form_id,pagenum,pagesize){
	ajaxPagingLoad(form_id,pagenum,pagesize);	
}