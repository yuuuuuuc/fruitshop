<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/includes/header.jsp" %>

<div class="fruit-detail">
    <h2>${fruit.name}</h2>

    <div class="fruit-info">
        <div class="price-stock">
            <p class="price">¥${fruit.price}</p>
            <p class="stock">库存: ${fruit.stock}</p>
        </div>

        <div class="description">
            <h3>描述</h3>
            <p>${fruit.description}</p>
        </div>

        <div class="storage-info">
            <h3>储藏信息</h3>
            <p>${fruit.storageInfo}</p>
        </div>

        <c:if test="${not empty sessionScope.user}">
            <div class="actions">
                <c:if test="${fruit.stock > 0}">
                    <form action="${pageContext.request.contextPath}/cart/add" method="post" class="add-to-cart">
                        <input type="hidden" name="fruitId" value="${fruit.id}">
                        <div class="quantity">
                            <label for="quantity">数量:</label>
                            <input type="number" id="quantity" name="quantity" min="1" max="${fruit.stock}" value="1">
                        </div>
                        <button type="submit" class="btn">加入购物车</button>
                    </form>
                </c:if>

                <form action="${pageContext.request.contextPath}/favorite/toggle" method="post" class="toggle-favorite">
                    <input type="hidden" name="fruitId" value="${fruit.id}">
                    <button type="submit" class="btn ${isFavorite ? 'btn-favorited' : ''}">
                            ${isFavorite ? '取消收藏' : '加入收藏'}
                    </button>
                </form>
            </div>
        </c:if>
    </div>
</div>

<%@ include file="/includes/footer.jsp" %>
