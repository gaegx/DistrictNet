package com.districtnet.mapper;

import com.districtnet.dto.task.TaskCreateDto;
import com.districtnet.dto.task.TaskDisplayDto;
import com.districtnet.entity.Task;
import com.districtnet.Enum.TaskStatus;

public class TaskMapper {

    public static Task toEntity(TaskCreateDto dto) {
        Task task = new Task();
        task.setTaskType(dto.getTaskType());
        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        task.setCommand(dto.getCommand());
        task.setDataPath(dto.getDataPath());
        task.setScriptUrl(dto.getScriptUrl());
        task.setDockerPath(dto.getDockerPath());
        task.setReplicas(dto.getReplicas());
        task.setParameters(dto.getParameters());
        task.setStatus(TaskStatus.PENDING);
        return task;
    }

    public static TaskDisplayDto toDisplayDto(Task task) {
        TaskDisplayDto dto = new TaskDisplayDto();
        dto.setId(task.getId());
        dto.setTaskType(task.getTaskType());
        dto.setStatus(task.getStatus());
        dto.setName(task.getName());
        dto.setDescription(task.getDescription());
        dto.setCommand(task.getCommand());
        dto.setDataPath(task.getDataPath());
        dto.setScriptUrl(task.getScriptUrl());
        dto.setDockerPath(task.getDockerPath());
        dto.setReplicas(task.getReplicas());
        dto.setParameters(task.getParameters());
        dto.setCreatedAt(task.getCreatedAt());
        return dto;
    }

    public static Task updateEntity(Task task, TaskCreateDto dto) {
        task.setTaskType(dto.getTaskType());
        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        task.setCommand(dto.getCommand());
        task.setDataPath(dto.getDataPath());
        task.setScriptUrl(dto.getScriptUrl());
        task.setDockerPath(dto.getDockerPath());
        task.setReplicas(dto.getReplicas());
        task.setParameters(dto.getParameters());
        return task;
    }
}
