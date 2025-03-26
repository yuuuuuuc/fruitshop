package com.fruitshop.servlet.favorite;

import com.fruitshop.model.Favorite;
import com.fruitshop.model.User;
import com.fruitshop.service.FavoriteService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class FavoriteServlet extends HttpServlet {
    private FavoriteService favoriteService;

    @Override
    public void init() throws ServletException {
        this.favoriteService = new FavoriteService();
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

        if (action == null || action.equals("/") || action.equals("/list")) {
            // 查看收藏列表
            List<Favorite> favorites = favoriteService.getUserFavorites(user.getId());
            request.setAttribute("favorites", favorites);
            request.getRequestDispatcher("/favorites.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/favorite/list");
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
            // 添加收藏
            try {
                int fruitId = Integer.parseInt(request.getParameter("fruitId"));
                favoriteService.addFavorite(user.getId(), fruitId);

                // 重定向回水果详情页面
                response.sendRedirect(request.getContextPath() + "/fruit/" + fruitId);
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/fruits");
            }
        } else if (action.equals("/remove")) {
            // 取消收藏
            try {
                int fruitId = Integer.parseInt(request.getParameter("fruitId"));
                favoriteService.removeFavorite(user.getId(), fruitId);

                // 判断请求来自哪里，决定重定向到哪里
                String referer = request.getHeader("Referer");
                if (referer != null && referer.contains("/favorite/list")) {
                    response.sendRedirect(request.getContextPath() + "/favorite/list");
                } else {
                    response.sendRedirect(request.getContextPath() + "/fruit/" + fruitId);
                }
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/favorite/list");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/favorite/list");
        }
    }
}
