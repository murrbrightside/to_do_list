package com.example.api.login.reposutory;

import com.example.api.login.entity.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;



public interface TaskRepository extends CrudRepository<Task, Integer> {
    List<Task> findAllByUsername(String username);
    Task findTopByOrderByIdDesc();

}