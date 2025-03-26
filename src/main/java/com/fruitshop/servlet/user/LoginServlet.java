package com.fruitshop.servlet.user;

import com.fruitshop.model.User;
import com.fruitshop.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 如果用户已登录，重定向到首页
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        // 转发到登录页面
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String emailOrPhone = request.getParameter("emailOrPhone");
        String password = request.getParameter("password");

        // 验证登录信息
        User user = userService.login(emailOrPhone, password);

        if (user != null) {
            // 登录成功，设置session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // 重定向到首页
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        } else {
            System.out.println("尝试登录: " + emailOrPhone + ", 密码长度: " + password);
            // 登录失败，返回登录页面并显示错误信息
            request.setAttribute("error", "邮箱/手机号或密码错误");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}
