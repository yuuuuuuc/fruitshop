<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/includes/header.jsp" %>

<div class="form-container">
  <h2>用户注册</h2>

  <c:if test="${not empty error}">
    <div class="error-message">${error}</div>
  </c:if>

  <form action="${pageContext.request.contextPath}/register" method="post">
    <div class="form-group">
      <label for="email">邮箱</label>
      <input type="email" id="email" name="email">
      <small>邮箱和手机号至少填写一个</small>
    </div>

    <div class="form-group">
      <label for="phone">手机号</label>
      <input type="tel" id="phone" name="phone">
    </div>

    <div class="form-group">
      <label for="username">用户名</label>
      <input type="text" id="username" name="uname" required>
    </div>

    <div class="form-group">
      <label for="password">密码</label>
      <input type="password" id="password" name="password" required>
    </div>

    <div class="form-group">
      <label for="confirmPassword">确认密码</label>
      <input type="password" id="confirmPassword" name="confirmPassword" required>
    </div>

    <div class="form-group">
      <button type="submit" class="btn">注册</button>
    </div>

    <div class="form-footer">
      已有账号？<a href="${pageContext.request.contextPath}/login">立即登录</a>
    </div>
  </form>
</div>

<%@ include file="/includes/footer.jsp" %>
