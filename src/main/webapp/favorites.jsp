<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/includes/header.jsp" %>

<div class="favorites-container">
    <h2>我的收藏</h2>

    <c:if test="${empty favorites}">
        <p class="empty-message">您还没有收藏任何商品</p>
        <a href="${pageContext.request.contextPath}/fruits" class="btn">去选购</a>
    </c:if>

    <c:if test="${not empty favorites}">
        <div class="favorites-grid">
            <c:forEach var="favorite" items="${favorites}">
                <div class="fruit-card">
                    <h3>${favorite.fruit.name}</h3>
                    <p class="price">¥${favorite.fruit.price}</p>
                    <p class="stock">库存: ${favorite.fruit.stock}</p>
                    <div class="buttons">
                        <a href="${pageContext.request.contextPath}/fruit/${favorite.fruitId}" class="btn">查看详情</a>
                        <form action="${pageContext.request.contextPath}/favorite/toggle" method="post">
                            <input type="hidden" name="fruitId" value="${favorite.fruitId}">
                            <button type="submit" class="btn btn-favorited">取消收藏</button>
                        </form>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:if>
</div>

<%@ include file="/includes/footer.jsp" %>
