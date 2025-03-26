package com.fruitshop.servlet.fruit;

import com.fruitshop.model.Fruit;
import com.fruitshop.model.User;
import com.fruitshop.service.FavoriteService;
import com.fruitshop.service.FruitService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class FruitDetailServlet extends HttpServlet {
    private FruitService fruitService;
    private FavoriteService favoriteService;

    @Override
    public void init() throws ServletException {
        this.fruitService = new FruitService();
        this.favoriteService = new FavoriteService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 从URL路径中获取水果ID
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendRedirect(request.getContextPath() + "/fruits");
            return;
        }

        int fruitId;
        try {
            fruitId = Integer.parseInt(pathInfo.substring(1));
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/fruits");
            return;
        }

        // 获取水果详情
        Fruit fruit = fruitService.getFruitById(fruitId);

        if (fruit == null) {
            response.sendRedirect(request.getContextPath() + "/fruits");
            return;
        }

        // 检查该水果是否已被当前用户收藏
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            boolean isFavorite = favoriteService.isFavorite(user.getId(), fruitId);
            request.setAttribute("isFavorite", isFavorite);
        }

        // 将水果详情放入请求属性中
        request.setAttribute("fruit", fruit);

        // 转发到水果详情页面
        request.getRequestDispatcher("/fruits/detail.jsp").forward(request, response);
    }
}
