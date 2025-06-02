package com.districnet.model;

import com.districnet.Enum.TaskStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "shard_task")
@Data
public class ShardTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long taskId;

    @Column(nullable = false)
    private Integer shardIndex;

    @Column(nullable = false)
    private Long nodeId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    @Column(columnDefinition = "TEXT")
    private String result;
}





