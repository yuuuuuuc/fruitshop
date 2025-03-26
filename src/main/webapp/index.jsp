<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/includes/header.jsp" %>

<div class="hero">
    <h1>欢迎来到水果商城</h1>
    <p>新鲜健康的水果，为您的生活增添活力</p>
    <a href="${pageContext.request.contextPath}/fruits" class="btn">开始购物</a>
</div>

<section class="features">
    <div class="feature">
        <h2>新鲜直达</h2>
        <p>从农场直接送到您的餐桌，保证水果新鲜度。</p>
    </div>
    <div class="feature">
        <h2>品质保证</h2>
        <p>精选优质水果，确保每一口都是美味。</p>
    </div>
    <div class="feature">
        <h2>快速配送</h2>
        <p>下单后最快24小时送达，让您随时享用新鲜水果。</p>
    </div>
</section>

<%@ include file="/includes/footer.jsp" %>
