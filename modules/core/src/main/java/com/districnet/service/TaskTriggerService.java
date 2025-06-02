package com.districnet.service;

import com.districnet.dto.TaskCreateDto;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface TaskTriggerService {
    void triggerTasks(TaskCreateDto task, Map<String, List<File>> nodeToChunks);
}
