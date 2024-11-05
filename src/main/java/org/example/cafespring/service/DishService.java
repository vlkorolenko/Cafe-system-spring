package org.example.cafespring.service;

import org.example.cafespring.model.Dish;
import org.example.cafespring.repository.DishDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishService {
    private final DishDAO dishDAO;

    @Autowired
    public DishService(DishDAO dishDAO) {
        this.dishDAO = dishDAO;
    }

    public List<Dish> findAll() {
        return dishDAO.getAllDishes();
    }

    public void addDish(Dish dish) {
        dishDAO.addDish(dish);
    }

    public void deleteDish(int id) {
        dishDAO.deleteDish(id);
    }

    public void updateDish(Dish dish) {
        dishDAO.updateDish(dish);
    }

    public Dish findDishById(int id) {
        return dishDAO.findById(id);
    }
}
