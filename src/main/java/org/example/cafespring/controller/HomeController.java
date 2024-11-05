package org.example.cafespring.controller;

import org.example.cafespring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeController {

    private final UserService userService;
    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password) {
        if (userService.registerUser(username, password)) {
            return "redirect:/login";
        } else {
            return "redirect:/register?error";
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model) {
        boolean isAuthenticated = userService.authenticateUser(username, password, model);
        if (isAuthenticated) {
            return "redirect:/home";  // Успішна авторизація
        } else {
            return "login";  // Повертає сторінку з помилкою
        }
    }

    @GetMapping("/home")
    public String showHomePage() {
        return "home";  // Ваш шаблон для домашньої сторінки
    }


}

