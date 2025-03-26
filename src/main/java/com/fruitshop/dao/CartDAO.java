package com.fruitshop.dao;

import com.fruitshop.model.Cart;
import com.fruitshop.model.Fruit;
import com.fruitshop.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    public List<Cart> findByUserId(int userId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Cart> cartItems = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT c.*, f.* FROM cart c " +
                    "JOIN fruits f ON c.fruit_id = f.id " +
                    "WHERE c.user_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Cart cart = new Cart();
                cart.setUserId(rs.getInt("user_id"));
                cart.setFruitId(rs.getInt("fruit_id"));
                cart.setQuantity(rs.getInt("quantity"));
                cart.setAddedAt(rs.getTimestamp("added_at"));
                cart.setUpdatedAt(rs.getTimestamp("updated_at"));

                Fruit fruit = new Fruit();
                fruit.setId(rs.getInt("id"));
                fruit.setName(rs.getString("name"));
                fruit.setPrice(rs.getBigDecimal("price"));
                fruit.setDescription(rs.getString("description"));
                fruit.setStorageInfo(rs.getString("storage_info"));
                fruit.setStock(rs.getInt("stock"));
                fruit.setCreatedAt(rs.getTimestamp("created_at"));
                fruit.setUpdatedAt(rs.getTimestamp("updated_at"));

                cart.setFruit(fruit);
                cartItems.add(cart);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(conn);
        }

        return cartItems;
    }

    public Cart findCartItem(int userId, int fruitId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Cart cart = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM cart WHERE user_id = ? AND fruit_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, fruitId);
            rs = ps.executeQuery();

            if (rs.next()) {
                cart = new Cart();
                cart.setUserId(rs.getInt("user_id"));
                cart.setFruitId(rs.getInt("fruit_id"));
                cart.setQuantity(rs.getInt("quantity"));
                cart.setAddedAt(rs.getTimestamp("added_at"));
                cart.setUpdatedAt(rs.getTimestamp("updated_at"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(conn);
        }

        return cart;
    }

    public boolean insert(Cart cart) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;

        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO cart (user_id, fruit_id, quantity) VALUES (?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, cart.getUserId());
            ps.setInt(2, cart.getFruitId());
            ps.setInt(3, cart.getQuantity());

            int affectedRows = ps.executeUpdate();
            success = (affectedRows > 0);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(conn);
        }

        return success;
    }

    public boolean update(Cart cart) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;

        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE cart SET quantity = ? WHERE user_id = ? AND fruit_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, cart.getQuantity());
            ps.setInt(2, cart.getUserId());
            ps.setInt(3, cart.getFruitId());

            int affectedRows = ps.executeUpdate();
            success = (affectedRows > 0);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(conn);
        }

        return success;
    }

    public boolean delete(int userId, int fruitId) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;

        try {
            conn = DBUtil.getConnection();
            String sql = "DELETE FROM cart WHERE user_id = ? AND fruit_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, fruitId);

            int affectedRows = ps.executeUpdate();
            success = (affectedRows > 0);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(conn);
        }

        return success;
    }

    public boolean clearCart(int userId) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;

        try {
            conn = DBUtil.getConnection();
            String sql = "DELETE FROM cart WHERE user_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);

            ps.executeUpdate();
            success = true; // 即使没有删除任何记录，也视为成功
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(conn);
        }

        return success;
    }
}
