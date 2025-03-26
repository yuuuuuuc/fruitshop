<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>水果商城</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<header>
  <div class="container">
    <div class="logo">
      <a href="${pageContext.request.contextPath}/index.jsp">水果商城</a>
    </div>
    <nav>
      <ul>
        <li><a href="${pageContext.request.contextPath}/index.jsp">首页</a></li>
        <li><a href="${pageContext.request.contextPath}/fruits">所有水果</a></li>
        <c:if test="${empty sessionScope.user}">
          <li><a href="${pageContext.request.contextPath}/login">登录</a></li>
          <li><a href="${pageContext.request.contextPath}/register">注册</a></li>
        </c:if>
        <c:if test="${not empty sessionScope.user}">
          <li><a href="${pageContext.request.contextPath}/cart">购物车</a></li>
          <li><a href="${pageContext.request.contextPath}/favorite">收藏夹</a></li>
          <li>欢迎, ${sessionScope.user.uname} <a href="${pageContext.request.contextPath}/logout">(退出)</a></li>
        </c:if>
      </ul>
    </nav>
  </div>
</header>
<div class="container main-content">
