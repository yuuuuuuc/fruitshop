package com.fruitshop.service;

import com.fruitshop.dao.FavoriteDAO;
import com.fruitshop.model.Favorite;

import java.util.List;

public class FavoriteService {
    private FavoriteDAO favoriteDAO;

    public FavoriteService() {
        this.favoriteDAO = new FavoriteDAO();
    }

    public List<Favorite> getUserFavorites(int userId) {
        return favoriteDAO.findByUserId(userId);
    }

    public boolean isFavorite(int userId, int fruitId) {
        return favoriteDAO.exists(userId, fruitId);
    }

    public boolean addFavorite(int userId, int fruitId) {
        if (favoriteDAO.exists(userId, fruitId)) {
            return true; // 已经是收藏状态
        }

        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        favorite.setFruitId(fruitId);
        return favoriteDAO.insert(favorite);
    }

    public boolean removeFavorite(int userId, int fruitId) {
        return favoriteDAO.delete(userId, fruitId);
    }
}
