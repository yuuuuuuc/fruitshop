package com.fruitshop.servlet.cart;

import com.fruitshop.model.Cart;
import com.fruitshop.model.User;
import com.fruitshop.service.CartService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class CartServlet extends HttpServlet {
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        this.cartService = new CartService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 检查用户是否已登录
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        String action = request.getPathInfo();

        if (action == null || action.equals("/") || action.equals("/view")) {
            // 查看购物车
            List<Cart> cartItems = cartService.getUserCart(user.getId());
            request.setAttribute("cartItems", cartItems);
            request.getRequestDispatcher("/cart.jsp").forward(request, response);
        } else if (action.equals("/clear")) {
            // 清空购物车
            cartService.clearCart(user.getId());
            response.sendRedirect(request.getContextPath() + "/cart/view");
        } else {
            response.sendRedirect(request.getContextPath() + "/cart/view");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 检查用户是否已登录
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        String action = request.getPathInfo();

        if (action.equals("/add")) {
            // 添加商品到购物车
            try {
                int fruitId = Integer.parseInt(request.getParameter("fruitId"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));

                if (quantity <= 0) {
                    quantity = 1;
                }

                boolean success = cartService.addToCart(user.getId(), fruitId, quantity);

                if (success) {
                    response.sendRedirect(request.getContextPath() + "/cart/view");
                } else {
                    request.setAttribute("error", "添加商品失败，可能库存不足");
                    request.getRequestDispatcher("/fruit/" + fruitId).forward(request, response);
                }
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/fruits");
            }
        } else if (action.equals("/update")) {
            // 更新购物车商品数量
            try {
                int fruitId = Integer.parseInt(request.getParameter("fruitId"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));

                if (quantity <= 0) {
                    // 如果数量为0或负数，则从购物车中删除
                    cartService.removeFromCart(user.getId(), fruitId);
                } else {
                    // 更新商品数量
                    cartService.updateCartItemQuantity(user.getId(), fruitId, quantity);
                }

                response.sendRedirect(request.getContextPath() + "/cart/view");
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/cart/view");
            }
        } else if (action.equals("/remove")) {
            // 从购物车中移除商品
            try {
                int fruitId = Integer.parseInt(request.getParameter("fruitId"));
                cartService.removeFromCart(user.getId(), fruitId);
                response.sendRedirect(request.getContextPath() + "/cart/view");
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/cart/view");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/cart/view");
        }
    }
}
