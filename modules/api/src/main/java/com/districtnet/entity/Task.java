package com.districtnet.entity;

import com.districtnet.Enum.TaskType;
import com.districtnet.Enum.TaskStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_type", nullable = false)
    private TaskType taskType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TaskStatus status;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "command", columnDefinition = "TEXT")
    private String command;

    @Column(name = "data_path")
    private String dataPath;

    @Column(name = "script_url")
    private String scriptUrl;

    @Column(name = "docker_path", nullable = false)
    private String dockerPath;

    @Column(name = "replicas")
    private Integer replicas;

    @Column(name = "parameters", columnDefinition = "TEXT")
    private String parameters;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = TaskStatus.PENDING;
        }
    }


}
