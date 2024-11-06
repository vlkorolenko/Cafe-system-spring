package org.example.cafespring.controller;

import org.example.cafespring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
        System.out.println("Показати форму реєстрації");
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, Model model) {
        System.out.println("Реєстрація користувача: " + username);
        if (userService.registerUser(username, password, model)) {
            System.out.println("Реєстрація успішна для користувача: " + username);
            return "redirect:/login";
        } else {
            System.out.println("Реєстрація не вдалася для користувача: " + username);
            // Залишаємо на сторінці реєстрації з повідомленням про помилку
            return "register";
        }
    }


    @GetMapping("/login")
    public String showLoginForm() {
        System.out.println("Показати форму логіна");
        return "login";
    }

    @GetMapping("/home")
    public String showHomePage() {
        System.out.println("Показати домашню сторінку");
        return "home";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login?logout";
    }
}