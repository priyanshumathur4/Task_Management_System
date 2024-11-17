package com.example.taskmanagementsystem.service.impl;

import com.example.taskmanagementsystem.exception.ResourceNotFoundException;
import com.example.taskmanagementsystem.model.Task;
import com.example.taskmanagementsystem.model.User;
import com.example.taskmanagementsystem.repository.TaskRepository;
import com.example.taskmanagementsystem.repository.UserRepository;
import com.example.taskmanagementsystem.service.TaskService;
import com.example.taskmanagementsystem.dto.ApiResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ApiResponse createTask(Task task, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found, Id: " + userId));
        task.setUser(user);
        task.setCompleted(task.getCompleted() != null ? task.getCompleted() : false);

        Task savedTask = taskRepository.save(task);
        return new ApiResponse("Task Saved", savedTask);
    }

    @Override
    public ApiResponse getTaskById(Integer taskId) {
        Task task = getTaskOrThrow(taskId);
        return new ApiResponse("Found task", task);
    }

    @Override
    public List<Task> getAllTasks(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found, Id: " + userId));
        return taskRepository.findAllByUserId(user.getId());
    }

    @Override
    public ApiResponse updateTask(Task task, Integer id) {
        Task foundTask = getTaskOrThrow(id);
        foundTask.setTask(task.getTask());
        foundTask.setCompleted(task.getCompleted());
        foundTask.setDetails(task.getDetails());

        Task updatedTask = taskRepository.save(foundTask);
        return new ApiResponse("Task updated!", updatedTask);
    }

    @Override
    public void deleteTask(Integer id) {
        Task task = getTaskOrThrow(id);
        taskRepository.delete(task);
    }

    @Override
    public ApiResponse doneTask(Integer id) {
        Task task = getTaskOrThrow(id);
        task.setCompleted(true);
        Task updatedTask = taskRepository.save(task);
        return new ApiResponse("Task marked as done!", updatedTask);
    }

    @Override
    public ApiResponse pendingTask(Integer id) {
        Task task = getTaskOrThrow(id);
        task.setCompleted(false);
        Task updatedTask = taskRepository.save(task);
        return new ApiResponse("Task marked as pending!", updatedTask);
    }

    private Task getTaskOrThrow(Integer id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found, Id: " + id));
    }
}