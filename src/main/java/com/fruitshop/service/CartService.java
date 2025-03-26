package com.fruitshop.service;

import com.fruitshop.dao.CartDAO;
import com.fruitshop.dao.FruitDAO;
import com.fruitshop.model.Cart;
import com.fruitshop.model.Fruit;

import java.util.List;

public class CartService {
    private CartDAO cartDAO;
    private FruitDAO fruitDAO;

    public CartService() {
        this.cartDAO = new CartDAO();
        this.fruitDAO = new FruitDAO();
    }

    public List<Cart> getUserCart(int userId) {
        return cartDAO.findByUserId(userId);
    }

    public boolean addToCart(int userId, int fruitId, int quantity) {
        Fruit fruit = fruitDAO.findById(fruitId);

        if (fruit == null || fruit.getStock() < quantity) {
            return false; // 商品不存在或库存不足
        }

        Cart existingCartItem = cartDAO.findCartItem(userId, fruitId);

        if (existingCartItem != null) {
            // 商品已在购物车中，更新数量
            int newQuantity = existingCartItem.getQuantity() + quantity;

            if (fruit.getStock() < newQuantity) {
                return false; // 库存不足以满足新数量
            }

            existingCartItem.setQuantity(newQuantity);
            return cartDAO.update(existingCartItem);
        } else {
            // 添加新商品到购物车
            Cart cart = new Cart();
            cart.setUserId(userId);
            cart.setFruitId(fruitId);
            cart.setQuantity(quantity);
            return cartDAO.insert(cart);
        }
    }

    public boolean updateCartItemQuantity(int userId, int fruitId, int quantity) {
        Fruit fruit = fruitDAO.findById(fruitId);

        if (fruit == null || fruit.getStock() < quantity) {
            return false; // 商品不存在或库存不足
        }

        Cart cart = cartDAO.findCartItem(userId, fruitId);

        if (cart == null) {
            return false; // 购物车中没有该商品
        }

        cart.setQuantity(quantity);
        return cartDAO.update(cart);
    }

    public boolean removeFromCart(int userId, int fruitId) {
        return cartDAO.delete(userId, fruitId);
    }

    public boolean clearCart(int userId) {
        return cartDAO.clearCart(userId);
    }
}
