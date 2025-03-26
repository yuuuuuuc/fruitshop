package com.fruitshop.servlet.user;

import com.fruitshop.model.User;
import com.fruitshop.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
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

        // 转发到注册页面
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String uname = request.getParameter("uname");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // 基本验证
        if ((email == null || email.trim().isEmpty()) && (phone == null || phone.trim().isEmpty())) {
            request.setAttribute("error", "邮箱和手机号至少提供一个");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        if (password == null || password.trim().length() < 6) {
            request.setAttribute("error", "密码至少需要6个字符");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "两次输入的密码不一致");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        // 创建新用户
        User user = new User();
        user.setEmail(email.trim().isEmpty() ? null : email);
        user.setPhone(phone.trim().isEmpty() ? null : phone);
        user.setUname(uname);
        System.out.println("注册用户名: " + uname);
        // 注册用户
        int userId = userService.register(user, password);

        if (userId > 0) {
            // 注册成功，跳转到登录页面
            request.setAttribute("success", "注册成功，请登录");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else if (userId == -1) {
            request.setAttribute("error", "该邮箱已被注册");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else if (userId == -2) {
            request.setAttribute("error", "该手机号已被注册");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "注册失败，请稍后再试");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
}
