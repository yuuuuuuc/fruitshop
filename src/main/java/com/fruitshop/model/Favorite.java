package com.fruitshop.model;

import java.sql.Timestamp;

public class Favorite {
    private int userId;
    private int fruitId;
    private Timestamp createdAt;

    // 附加字段，用于前端显示
    private Fruit fruit;

    // 构造函数
    public Favorite() {}

    public Favorite(int userId, int fruitId, Timestamp createdAt) {
        this.userId = userId;
        this.fruitId = fruitId;
        this.createdAt = createdAt;
    }

    // Getter和Setter
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFruitId() {
        return fruitId;
    }

    public void setFruitId(int fruitId) {
        this.fruitId = fruitId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public void setFruit(Fruit fruit) {
        this.fruit = fruit;
    }
}
