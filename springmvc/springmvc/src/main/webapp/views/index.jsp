<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<title>Index</title>

<h2>Hello World!</h2>
<input type="button" onclick="json()" value="JSON">
<input type="button" onclick="jsonp()" value="JSONP">
<script type="text/javascript">
function json(){
	var url=contextPath+'/getUser.json';
	$.ajax({
		url : url,
		type : "POST",
		async : false,
		cache:false,
		contentType : 'application/json;charset=utf-8',
		success : function(data) {
			console.log('json:'+data);	
		}
	});
}
function jsonp(){
	var url=contextPath+'/getUser.jsonp';
	$.ajax({
		type: "GET",
		async: false,
		url: url,
		data: '',
		cache: false,
		dataType: "jsonp",
		jsonp: "callback",
		success: function(data) {
			console.log('jsonp:'+data);
		}
	});
}
	
</script>	


