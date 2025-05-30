package com.districtnet.dto.task;

import com.districtnet.Enum.TaskType;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskCreateDto {

    @NotNull(message = "Тип задачи обязателен")
    private TaskType taskType;

    @NotBlank(message = "Имя задачи не должно быть пустым")
    @Size(max = 255, message = "Имя задачи не должно превышать 255 символов")
    private String name;

    @Size(max = 10000, message = "Описание слишком длинное")
    private String description;

    @Size(max = 5000, message = "Команда слишком длинная")
    private String command;

    @Size(max = 1024, message = "Путь к данным слишком длинный")
    private String dataPath;

    @Pattern(regexp = "", message = "scriptUrl должен быть корректной ссылкой")
    private String scriptUrl;

    @NotBlank(message = "Путь к Docker образу обязателен")
    @Size(max = 1024, message = "Путь к Docker слишком длинный")
    private String dockerPath;

    @Min(value = 1, message = "Минимум 1 реплика")
    @Max(value = 1000, message = "Слишком много реплик")
    private Integer replicas;

    @Size(max = 10000, message = "Слишком много параметров")
    private String parameters;

}
