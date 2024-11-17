package com.example.taskmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.taskmanagementsystem.model.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAllByUserId(Long id);
}