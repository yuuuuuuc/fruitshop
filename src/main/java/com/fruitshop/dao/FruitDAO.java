package com.fruitshop.dao;

import com.fruitshop.model.Fruit;
import com.fruitshop.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FruitDAO {

    public List<Fruit> findAll() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Fruit> fruitList = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM fruits ORDER BY name";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                fruitList.add(mapResultSetToFruit(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(conn);
        }

        return fruitList;
    }

    public Fruit findById(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Fruit fruit = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM fruits WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                fruit = mapResultSetToFruit(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(conn);
        }

        return fruit;
    }

    public int insert(Fruit fruit) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int generatedId = -1;

        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO fruits (name, price, description, storage_info, stock) VALUES (?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, fruit.getName());
            ps.setBigDecimal(2, fruit.getPrice());
            ps.setString(3, fruit.getDescription());
            ps.setString(4, fruit.getStorageInfo());
            ps.setInt(5, fruit.getStock());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(conn);
        }

        return generatedId;
    }

    public boolean update(Fruit fruit) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;

        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE fruits SET name = ?, price = ?, description = ?, storage_info = ?, stock = ? WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, fruit.getName());
            ps.setBigDecimal(2, fruit.getPrice());
            ps.setString(3, fruit.getDescription());
            ps.setString(4, fruit.getStorageInfo());
            ps.setInt(5, fruit.getStock());
            ps.setInt(6, fruit.getId());

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

    public boolean updateStock(int fruitId, int newStock) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;

        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE fruits SET stock = ? WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, newStock);
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

    public boolean delete(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;

        try {
            conn = DBUtil.getConnection();
            String sql = "DELETE FROM fruits WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

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

    private Fruit mapResultSetToFruit(ResultSet rs) throws SQLException {
        Fruit fruit = new Fruit();
        fruit.setId(rs.getInt("id"));
        fruit.setName(rs.getString("name"));
        fruit.setPrice(rs.getBigDecimal("price"));
        fruit.setDescription(rs.getString("description"));
        fruit.setStorageInfo(rs.getString("storage_info"));
        fruit.setStock(rs.getInt("stock"));
        fruit.setCreatedAt(rs.getTimestamp("created_at"));
        fruit.setUpdatedAt(rs.getTimestamp("updated_at"));
        return fruit;
    }
}
