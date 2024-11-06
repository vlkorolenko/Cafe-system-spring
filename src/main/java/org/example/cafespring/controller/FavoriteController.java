package org.example.cafespring.controller;

import org.example.cafespring.model.Favorite;
import org.example.cafespring.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class FavoriteController {
    private final FavoriteService favoriteService;

    @Autowired
    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping("/favorites")
    public String showFavorites(Model model) {
        model.addAttribute("favorites", favoriteService.findAll());
        return "favorites";  // Цей шаблон має бути створений для відображення списку улюблених страв
    }

    @PostMapping("/favorites/add")
    public String addFavorite(@ModelAttribute Favorite favorite) {
        favoriteService.save(favorite);
        return "redirect:/favorites";
    }

    @PostMapping("/favorites/delete")
    public String deleteFavorite(@RequestParam("clientId") int clientId, @RequestParam("dishId") int dishId) {
        favoriteService.delete(clientId, dishId);
        return "redirect:/favorites";
    }

    @GetMapping("/favorites/edit/{clientId}/{dishId}")
    public String editFavorite(@PathVariable int clientId, @PathVariable int dishId, Model model) {
        Favorite favorite = favoriteService.findByIds(clientId, dishId);
        model.addAttribute("favorite", favorite);
        return "edit-favorite";  // Цей шаблон має бути створений для редагування улюблених страв
    }

    @PostMapping("/favorites/edit")
    public String editFavorite(@ModelAttribute Favorite favorite) {
        favoriteService.update(favorite);
        return "redirect:/favorites";
    }
}
