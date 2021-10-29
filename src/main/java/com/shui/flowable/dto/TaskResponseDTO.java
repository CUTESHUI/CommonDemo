package com.shui.flowable.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.task.api.DelegationState;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskInfo;
import org.flowable.task.api.history.HistoricTaskInstance;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
public class TaskResponseDTO implements Serializable {
    protected String id;
    protected String name;
    protected String description;
    protected String category;
    protected String owner;
    protected String assignee;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected Date endTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected Date claimTime;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    protected Date dueDate;
    protected Long durationInMillis;
    protected Long workTimeInMillis;
    protected Integer priority;
    protected String taskDefinitionKey;
    protected String processInstanceId;
    protected String processInstanceName;

    protected String processDefinitionId;
    protected String processDefinitionName;
    protected String processDefinitionDescription;
    protected String processDefinitionKey;
    protected String processDefinitionCategory;
    protected Integer processDefinitionVersion;
    protected String processDefinitionDeploymentId;

    protected String parentTaskId;
    protected String parentTaskName;

    protected String formKey;
    protected String processInstanceStartUserId;
    protected String initiatorCanCompleteTask;
    protected String memberOfCandidateGroup;
    protected String memberOfCandidateUsers;

    protected String delegationState;
    protected Boolean suspended;

    @JsonInclude(Include.NON_NULL)
    protected List<String> involvedPeople;

    protected String ownerName;
    protected String assigneeName;

    protected String tenantId;

    protected Object renderedTaskForm;

    public TaskResponseDTO() {
    }

    public TaskResponseDTO(TaskInfo task) {
        initializeTaskDetails(task);
    }

    public TaskResponseDTO(TaskInfo task, String processInstanceName) {
        initializeTaskDetails(task);
        this.processInstanceName = processInstanceName;
    }

    private TaskResponseDTO(TaskInfo taskInfo, ProcessDefinition processDefinition) {
        initializeTaskDetails(taskInfo);

        if (processDefinition != null) {
            this.processDefinitionName = processDefinition.getName();
            this.processDefinitionDescription = processDefinition.getDescription();
            this.processDefinitionKey = processDefinition.getKey();
            this.processDefinitionCategory = processDefinition.getCategory();
            this.processDefinitionVersion = processDefinition.getVersion();
            this.processDefinitionDeploymentId = processDefinition.getDeploymentId();
        }
    }

    public TaskResponseDTO(TaskInfo taskInfo, TaskInfo parentTaskInfo) {
        initializeTaskDetails(taskInfo);
        if (parentTaskInfo != null) {
            this.parentTaskId = parentTaskInfo.getId();
            this.parentTaskName = parentTaskInfo.getName();
        }
    }

    public TaskResponseDTO(TaskInfo taskInfo, ProcessDefinition processDefinition, String processInstanceName) {
        this(taskInfo, processDefinition);
        this.processInstanceName = processInstanceName;
    }

    public TaskResponseDTO(TaskInfo taskInfo, ProcessDefinition processDefinition, TaskInfo parentTaskInfo, String processInstanceName) {
        initializeTaskDetails(taskInfo);

        if (processDefinition != null) {
            this.processDefinitionName = processDefinition.getName();
            this.processDefinitionDescription = processDefinition.getDescription();
            this.processDefinitionKey = processDefinition.getKey();
            this.processDefinitionCategory = processDefinition.getCategory();
            this.processDefinitionVersion = processDefinition.getVersion();
            this.processDefinitionDeploymentId = processDefinition.getDeploymentId();
        }

        if (parentTaskInfo != null) {
            this.parentTaskId = parentTaskInfo.getId();
            this.parentTaskName = parentTaskInfo.getName();
        }
        this.processInstanceName = processInstanceName;
    }

    private void initializeTaskDetails(TaskInfo taskInfo) {
        this.id = taskInfo.getId();
        this.name = taskInfo.getName();
        this.description = taskInfo.getDescription();
        this.category = taskInfo.getCategory();
        this.createTime = taskInfo.getCreateTime();
        this.claimTime = taskInfo.getClaimTime();
        this.dueDate = taskInfo.getDueDate();
        this.priority = taskInfo.getPriority();
        this.processInstanceId = taskInfo.getProcessInstanceId();
        this.processDefinitionId = taskInfo.getProcessDefinitionId();
        this.taskDefinitionKey = taskInfo.getTaskDefinitionKey();
        this.owner = taskInfo.getOwner();
        this.assignee = taskInfo.getAssignee();
        if (taskInfo instanceof HistoricTaskInstance) {
            this.endTime = ((HistoricTaskInstance) taskInfo).getEndTime();
            this.durationInMillis = ((HistoricTaskInstance) taskInfo).getDurationInMillis();
            this.workTimeInMillis = ((HistoricTaskInstance) taskInfo).getWorkTimeInMillis();
        } else if (taskInfo instanceof Task) {
            this.suspended = ((Task) taskInfo).isSuspended();
            this.delegationState = this.getDelegationStateString(((Task) taskInfo).getDelegationState());
        }
    }

    public void setDelegationState(String delegationState) {
        this.delegationState = delegationState;
    }

    public void setDelegationState(DelegationState delegationState) {
        this.delegationState = getDelegationStateString(delegationState);
    }

    private String getDelegationStateString(DelegationState state) {
        String result = null;
        if (state != null) {
            result = state.toString().toUpperCase();
        }
        return result;
    }
}
