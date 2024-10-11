package com.example.api.login.controllers;

import com.example.api.login.entity.Authority;
import com.example.api.login.entity.User;
import com.example.api.login.repository.AuthorityRepository;
import com.example.api.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // Импортируем PasswordEncoder
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyUserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Инъекция PasswordEncoder

    @RequestMapping("/registration")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "view-registration";
    }

    @RequestMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        String username = user.getUsername();
        if (checkAvailableUsername(username)) {
            return "error-unavailable-username"; // Возврат ошибки, если имя пользователя недоступно
        } else {
            user.setEnable(1);  // Активируем пользователя

            // Хэшируем пароль перед сохранением
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashedPassword); // Устанавливаем хэшированный пароль

            userRepository.save(user); // Сохраняем пользователя

            // Сохранить роль пользователя
            Authority authority = new Authority();
            authority.setUsername(user.getUsername());
            authority.setAuthority("ROLE_USER");  // Назначаем роль ROLE_USER
            authorityRepository.save(authority); // Сохраняем роль

            return "success"; // Успешное завершение регистрации
        }
    }

    public boolean checkAvailableUsername(String username) {
        return userRepository.existsByUsername(username); // Проверка доступности имени пользователя
    }
}