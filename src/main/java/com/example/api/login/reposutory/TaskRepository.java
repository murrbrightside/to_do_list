package com.example.api.login.reposutory;

import com.example.api.login.entity.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface TaskRepository extends CrudRepository<Task, Integer> {
    List<Task> findAllByUsername(String username);

}