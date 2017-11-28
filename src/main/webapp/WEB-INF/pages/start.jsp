<%-- contentType= тип содержимого;кодировка передаваемых данных,
 charset - кодировка страницы полученной в результате работы jsp,
  в случае нескольких страниц - кодировки charset должны совпадать ,
 а pageEncoding это кодировка текущего файла(исходного кода) jsp перед компиляцией,
  если параметры обоих совподают, то можно явно указать только один--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%-- кодировка для возвращаемой страницы, те указывает серверу, что генерировать на основе этой страницы --%>
<%@page pageEncoding="UTF-8" %>
<%-- подключаем ядро jstl --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>--%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title> Очень простая страница JSP </title>
</head>
<body>
<%@ include file="top.jsp" %>

<h1>hello</h1><%@ include file="top.jsp" %>
</body>
</html>