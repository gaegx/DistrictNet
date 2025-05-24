package com.districtnet.service;

import com.districtnet.dto.task.TaskCreateDto;
import com.districtnet.dto.task.TaskDisplayDto;
import com.districtnet.entity.Task;
import com.districtnet.exception.NotFoundException;
import com.districtnet.mapper.TaskMapper;
import com.districtnet.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final KafkaSender kafkaSender;

    private static final String TASK_TOPIC = "task-events";


    @Transactional
    public TaskDisplayDto createTask(TaskCreateDto dto) {
        Task task = TaskMapper.toEntity(dto);
        Task saved = taskRepository.save(task);
        kafkaSender.send(dto);
        TaskDisplayDto displayDto = TaskMapper.toDisplayDto(saved);
        return displayDto;
    }


    public List<TaskDisplayDto> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(TaskMapper::toDisplayDto)
                .collect(Collectors.toList());
    }


    public TaskDisplayDto getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Задача с id=" + id + " не найдена"));
        return TaskMapper.toDisplayDto(task);
    }


    @Transactional
    public TaskDisplayDto updateTask(Long id, TaskCreateDto dto) {
        Task existing = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Задача с id=" + id + " не найдена"));


        Task updated = TaskMapper.updateEntity(existing, dto);
        Task saved = taskRepository.save(updated);
        return TaskMapper.toDisplayDto(saved);
    }


    @Transactional
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Задача с id=" + id + " не найдена"));
        taskRepository.delete(task);
    }
}
