package com.shui.flowable.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;


@Data
public class TaskRequestDTO implements Serializable {
    private String name;
    private String assignee;
    private String owner;
    private Date dueDate;
    private String category;
    private String description;
    private Integer priority;
    private String taskId;
    private String userId;
    private String message;
    private String activityId;
    private String activityName;
    private Map<String, Object> values;
    private String[] taskIds;
    private String processInstanceId;
}
