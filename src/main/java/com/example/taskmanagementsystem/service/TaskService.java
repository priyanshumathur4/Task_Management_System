package com.example.taskmanagementsystem.service;

import com.example.taskmanagementsystem.dto.ApiResponse;
import com.example.taskmanagementsystem.model.Task;

import java.util.List;

public interface TaskService {
    ApiResponse createTask(Task task, Long userId);

    ApiResponse getTaskById(Integer taskId);

    List<Task> getAllTasks(Long userId);

    ApiResponse updateTask(Task task, Integer id);

    public void deleteTask(Integer id);

    ApiResponse doneTask(Integer id);

    ApiResponse pendingTask(Integer id);
}