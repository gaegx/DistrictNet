package com.districtnet.controller;

import com.districtnet.dto.task.TaskCreateDto;
import com.districtnet.dto.task.TaskDisplayDto;
import com.districtnet.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDisplayDto create(@RequestBody @Valid TaskCreateDto dto) {
        return taskService.createTask(dto);
    }

    @GetMapping
    public List<TaskDisplayDto> getAll() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public TaskDisplayDto getById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @PutMapping("/{id}")
    public TaskDisplayDto update(@PathVariable Long id, @RequestBody @Valid TaskCreateDto dto) {
        return taskService.updateTask(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}
