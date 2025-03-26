<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/includes/header.jsp" %>

<div class="fruits-container">
  <h2>所有水果</h2>

  <div class="fruits-grid">
    <c:forEach var="fruit" items="${fruits}">
      <div class="fruit-card">
        <h3>${fruit.name}</h3>
        <p class="price">¥${fruit.price}</p>
        <p class="stock">库存: ${fruit.stock}</p>
        <div class="buttons">
          <a href="${pageContext.request.contextPath}/fruit/${fruit.id}" class="btn">查看详情</a>
          <c:if test="${not empty sessionScope.user && fruit.stock > 0}">
            <form action="${pageContext.request.contextPath}/cart/add" method="post">
              <input type="hidden" name="fruitId" value="${fruit.id}">
              <input type="hidden" name="quantity" value="1">
              <button type="submit" class="btn btn-cart">加入购物车</button>
            </form>
          </c:if>
        </div>
      </div>
    </c:forEach>
  </div>
</div>

<%@ include file="/includes/footer.jsp" %>
