package com.fruitshop.service;

import com.fruitshop.dao.FruitDAO;
import com.fruitshop.model.Fruit;

import java.util.List;

public class FruitService {
    private FruitDAO fruitDAO;

    public FruitService() {
        this.fruitDAO = new FruitDAO();
    }

    public List<Fruit> getAllFruits() {
        return fruitDAO.findAll();
    }

    public Fruit getFruitById(int id) {
        return fruitDAO.findById(id);
    }

    public int addFruit(Fruit fruit) {
        return fruitDAO.insert(fruit);
    }

    public boolean updateFruit(Fruit fruit) {
        return fruitDAO.update(fruit);
    }

    public boolean updateFruitStock(int fruitId, int newStock) {
        return fruitDAO.updateStock(fruitId, newStock);
    }

    public boolean deleteFruit(int id) {
        return fruitDAO.delete(id);
    }
}
