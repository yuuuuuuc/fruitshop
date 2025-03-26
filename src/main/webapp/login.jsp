<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/includes/header.jsp" %>

<div class="form-container">
    <h2>用户登录</h2>

    <c:if test="${not empty error}">
        <div class="error-message">${error}</div>
    </c:if>

    <c:if test="${not empty message}">
        <div class="success-message">${message}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <div class="form-group">
            <label for="emailOrPhone">邮箱或手机号</label>
            <input type="text" id="emailOrPhone" name="emailOrPhone" required>
        </div>

        <div class="form-group">
            <label for="password">密码</label>
            <input type="password" id="password" name="password" required>
        </div>

        <div class="form-group">
            <button type="submit" class="btn">登录</button>
        </div>

        <div class="form-footer">
            还没有账号？<a href="${pageContext.request.contextPath}/register">立即注册</a>
        </div>
    </form>
</div>

<%@ include file="/includes/footer.jsp" %>
