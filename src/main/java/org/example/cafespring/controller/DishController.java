package org.example.cafespring.controller;

import org.example.cafespring.model.Dish;
import org.example.cafespring.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class DishController {

    private final DishService dishService;

    @Autowired
    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/dishes")
    public String showAllDishes(Model model) {
        List<Dish> dishes = dishService.findAll();
        model.addAttribute("dishes", dishes);
        return "dishes";
    }

    @PostMapping("/dishes/delete")
    public String deleteDish(@RequestParam("id") int id) {
        System.out.println("ID страви для видалення: " + id); // Логування ID
        dishService.deleteDish(id); // Викликаєте метод для видалення страви
        return "redirect:/dishes"; // Після видалення перенаправляєте назад до списку страв
    }

    @PostMapping("/dishes/add")
    public String addDish(@ModelAttribute Dish dish) {
        dishService.addDish(dish);
        return "redirect:/dishes"; // Перенаправлення на список страв після додавання
    }

    @GetMapping("/dishes/edit/{id}")
    public String editDish(@PathVariable("id") int id, Model model) {
        Dish dish = dishService.findDishById(id);
        model.addAttribute("dish", dish);
        return "edit-dish";
    }

    @PostMapping("/dishes/edit")
    public String updateDish(@ModelAttribute("dish") Dish dish) {
        dishService.updateDish(dish);
        return "redirect:/dishes";
    }
}
