package org.example.cafespring.service;

import org.example.cafespring.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Реєстрація користувача
    public boolean registerUser(String username, String password, Model model) {
        if (userRepository.existsByUsername(username)) {
            model.addAttribute("errorMessage", "Користувач з таким логіном уже існує!");
            System.out.println("Користувач з таким логіном уже існує!");
            return false;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        org.example.cafespring.model.User user = new org.example.cafespring.model.User(username, hashedPassword);
        userRepository.save(user);
        System.out.println("Реєстрація успішна!");
        return true;
    }

//    // Авторизація користувача
//    public boolean authenticateUser(String username, String password, Model model) {
//        System.out.println("Спрацював метод authenticateUser з параметрами: " + username + ", " + password);
//        String storedHash = userRepository.findPasswordHashByUsername(username);
//        if (storedHash == null) {
//            model.addAttribute("errorMessage", "Невірний логін!");
//            System.out.println("Невірний логін для користувача: " + username);
//            return false;
//        }
//        if (!BCrypt.checkpw(password, storedHash)) {
//            model.addAttribute("errorMessage", "Невірний пароль!");
//            System.out.println("Невірний пароль для користувача: " + username);
//            return false;
//        }
//        System.out.println("Аутентифікація успішна для користувача: " + username);
//        return true;
//    }
}
