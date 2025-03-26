package com.fruitshop.servlet.user;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取当前会话并使其无效
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // 重定向到登录页面
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }
}
