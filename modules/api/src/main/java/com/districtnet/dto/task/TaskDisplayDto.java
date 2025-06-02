package com.districtnet.dto.task;

import com.districtnet.Enum.TaskStatus;
import com.districtnet.Enum.TaskType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TaskDisplayDto {
    private Long id;
    private TaskType taskType;
    private TaskStatus status;
    private String name;
    private String description;
    private String command;
    private String dataPath;
    private String scriptUrl;
    private String dockerPath;
    private Integer replicas;
    private String parameters;
    private LocalDateTime createdAt;
}
