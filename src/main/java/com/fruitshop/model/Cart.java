package com.fruitshop.model;

import java.sql.Timestamp;

public class Cart {
    private int userId;
    private int fruitId;
    private int quantity;
    private Timestamp addedAt;
    private Timestamp updatedAt;

    // 附加字段，用于前端显示
    private Fruit fruit;

    // 构造函数
    public Cart() {}

    public Cart(int userId, int fruitId, int quantity, Timestamp addedAt, Timestamp updatedAt) {
        this.userId = userId;
        this.fruitId = fruitId;
        this.quantity = quantity;
        this.addedAt = addedAt;
        this.updatedAt = updatedAt;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Timestamp getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Timestamp addedAt) {
        this.addedAt = addedAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public void setFruit(Fruit fruit) {
        this.fruit = fruit;
    }
}
