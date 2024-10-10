package com.example.api.login.controllers;

import com.example.api.login.entity.Task;
import com.example.api.login.reposutory.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class MyController {

    @Autowired
    private TaskRepository taskRepository;

    @RequestMapping("/")
    public String showAllParticularUserTasks(Model model) {
        // Получаем имя пользователя из контекста безопасности
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Получаем задачи для данного пользователя
        List<Task> allTasks = taskRepository.findAllByUsername(username);
        model.addAttribute("allTasks", allTasks);
        model.addAttribute("username", username);

        return "view-my-tasks";
    }

    @RequestMapping("/addNewTask")
    public String addNewTask(Model model) {
        // Получаем имя пользователя из контекста безопасности
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Создаем новую задачу и добавляем в модель
        Task task = new Task();
        model.addAttribute("username", username);
        System.out.println(model.getAttribute(username));
        model.addAttribute("task", task);

        return "view-create-task"; // Переход на страницу создания новой задачи
    }

    @PostMapping("/safeTask")
    public String safeTask(@ModelAttribute("task") Task task) {
        // Получаем имя пользователя из контекста безопасности
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();


        // Устанавливаем имя пользователя для задачи
        task.setUsername(username);


        // Сохраняем задачу в репозитории
        taskRepository.save(task);

        return "redirect:/";  // После успешного сохранения возвращаемся на главную страницу
    }

    @RequestMapping("/editTask")
    public String editTask(@RequestParam("taskId") int id, Model model) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        Task task = optionalTask.get();

        // Получаем имя пользователя из контекста безопасности
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        task.setUsername(username);  // Устанавливаем имя пользователя

        model.addAttribute("task", task);

        return "view-create-task";
    }
    @RequestMapping("/deleteTask")
    public String deleteTask(@RequestParam("taskId")int id, Model model){
        taskRepository.deleteById(id);
        return "redirect:/";
    }


}
