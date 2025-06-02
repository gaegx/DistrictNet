package com.districtnet.mapper;

import com.districtnet.dto.task.TaskCreateDto;
import com.districtnet.dto.task.TaskDisplayDto;
import com.districtnet.entity.Task;
import com.districtnet.Enum.TaskStatus;

public class TaskMapper {

    public static Task toEntity(TaskCreateDto dto) {
        if (dto == null) {
            return null;
        }

        Task task = new Task();
        task.setName(dto.getName());
        task.setDataPath(dto.getDataPath());
        task.setDockerPath(dto.getDockerPath());
        task.setStatus(TaskStatus.PENDING);
        return task;
    }

    public static TaskDisplayDto toDisplayDto(Task task) {
        if (task == null) {
            return null;
        }

        TaskDisplayDto dto = new TaskDisplayDto();
        dto.setId(task.getId());
        dto.setName(task.getName());
        dto.setDataPath(task.getDataPath());
        dto.setDockerPath(task.getDockerPath());
        dto.setStatus(task.getStatus());
        dto.setCreatedAt(task.getCreatedAt());
        return dto;
    }

    public static Task updateEntity(Task task, TaskCreateDto dto) {
        if (task == null || dto == null) {
            return task;
        }

        task.setName(dto.getName());
        task.setDataPath(dto.getDataPath());
        task.setDockerPath(dto.getDockerPath());
        return task;
    }
}
