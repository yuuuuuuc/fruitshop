<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/includes/header.jsp" %>

<div class="cart-container">
  <h2>我的购物车</h2>

  <c:if test="${empty cartItems}">
    <p class="empty-message">您的购物车是空的，快去选购喜欢的水果吧！</p>
    <a href="${pageContext.request.contextPath}/fruits" class="btn">去选购</a>
  </c:if>

  <c:if test="${not empty cartItems}">
    <div class="cart-items">
      <table class="cart-table">
        <thead>
        <tr>
          <th>商品</th>
          <th>单价</th>
          <th>数量</th>
          <th>小计</th>
          <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${cartItems}">
          <tr>
            <td>
              <a href="${pageContext.request.contextPath}/fruit/${item.fruitId}">${item.fruit.name}</a>
            </td>
            <td>¥${item.fruit.price}</td>
            <td>
              <form action="${pageContext.request.contextPath}/cart/update" method="post" class="update-quantity">
                <input type="hidden" name="fruitId" value="${item.fruitId}">
                <input type="number" name="quantity" min="1" max="${item.fruit.stock}" value="${item.quantity}" onchange="this.form.submit()">
              </form>
            </td>
            <td>¥${item.fruit.price * item.quantity}</td>
            <td>
              <form action="${pageContext.request.contextPath}/cart/remove" method="post">
                <input type="hidden" name="fruitId" value="${item.fruitId}">
                <button type="submit" class="btn-remove">删除</button>
              </form>
            </td>
          </tr>
        </c:forEach>
        </tbody>
        <tfoot>
        <tr>
          <td colspan="3" class="text-right">总计:</td>
          <td>¥${totalPrice}</td>
          <td></td>
        </tr>
        </tfoot>
      </table>

      <div class="cart-actions">
        <a href="${pageContext.request.contextPath}/fruits" class="btn">继续购物</a>
        <form action="${pageContext.request.contextPath}/cart/clear" method="post">
          <button type="submit" class="btn btn-clear">清空购物车</button>
        </form>
        <a href="${pageContext.request.contextPath}/order/checkout" class="btn btn-checkout">结算</a>
      </div>
    </div>
  </c:if>
</div>

<%@ include file="/includes/footer.jsp" %>
