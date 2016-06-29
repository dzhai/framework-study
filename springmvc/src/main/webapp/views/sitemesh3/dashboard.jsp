<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta charset="utf-8">
<title><sitemesh:write property='title' /> -Demo</title>
<meta name="description" content="demo">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<script type="text/javascript" src="${contextPath}/js/jquery.2.1.1.min.js"></script>
<script type="text/javascript">
	var contextPath='${contextPath}';
</script>
</head>
<body>
	<h1>公共头部</h1>
	<sitemesh:write property='body' />
	<h1>公共底部</h1>
</body>
</html>