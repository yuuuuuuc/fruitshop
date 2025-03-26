package com.fruitshop.servlet.fruit;

import com.fruitshop.model.Fruit;
import com.fruitshop.service.FruitService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class FruitListServlet extends HttpServlet {
    private FruitService fruitService;

    @Override
    public void init() throws ServletException {
        this.fruitService = new FruitService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取所有水果
        List<Fruit> fruits = fruitService.getAllFruits();

        // 将水果列表放入请求属性中
        request.setAttribute("fruits", fruits);

        // 转发到水果列表页面
        request.getRequestDispatcher("/fruits/list.jsp").forward(request, response);
    }
}
