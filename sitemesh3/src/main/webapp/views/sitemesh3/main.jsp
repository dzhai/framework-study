<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title><sitemesh:write property='title' /> -sitemesh</title>
<sitemesh:write property='head' />
</head>
<body>
	<header>header - <sitemesh:write property='header' /></header>
	<sitemesh:write property='body' />
	<footer>footer - <sitemesh:write property='footer' /></footer>
</body>
</html>