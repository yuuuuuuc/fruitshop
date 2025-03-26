package com.fruitshop.dao;

import com.fruitshop.model.Favorite;
import com.fruitshop.model.Fruit;
import com.fruitshop.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FavoriteDAO {

    public List<Favorite> findByUserId(int userId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Favorite> favorites = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT fav.*, f.* FROM favorites fav " +
                    "JOIN fruits f ON fav.fruit_id = f.id " +
                    "WHERE fav.user_id = ? " +
                    "ORDER BY fav.created_at DESC";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Favorite favorite = new Favorite();
                favorite.setUserId(rs.getInt("user_id"));
                favorite.setFruitId(rs.getInt("fruit_id"));
                favorite.setCreatedAt(rs.getTimestamp("created_at"));

                Fruit fruit = new Fruit();
                fruit.setId(rs.getInt("id"));
                fruit.setName(rs.getString("name"));
                fruit.setPrice(rs.getBigDecimal("price"));
                fruit.setDescription(rs.getString("description"));
                fruit.setStorageInfo(rs.getString("storage_info"));
                fruit.setStock(rs.getInt("stock"));
                fruit.setCreatedAt(rs.getTimestamp("created_at"));
                fruit.setUpdatedAt(rs.getTimestamp("updated_at"));

                favorite.setFruit(fruit);
                favorites.add(favorite);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(conn);
        }

        return favorites;
    }

    public boolean exists(int userId, int fruitId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean exists = false;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT 1 FROM favorites WHERE user_id = ? AND fruit_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, fruitId);
            rs = ps.executeQuery();

            exists = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(conn);
        }

        return exists;
    }

    public boolean insert(Favorite favorite) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;

        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO favorites (user_id, fruit_id) VALUES (?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, favorite.getUserId());
            ps.setInt(2, favorite.getFruitId());

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
            String sql = "DELETE FROM favorites WHERE user_id = ? AND fruit_id = ?";
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
}
