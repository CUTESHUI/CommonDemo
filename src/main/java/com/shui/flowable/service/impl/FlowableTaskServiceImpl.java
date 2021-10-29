package com.shui.flowable.service.impl;

import com.shui.exception.BaseException;
import com.shui.flowable.dto.TaskRequestDTO;
import com.shui.flowable.dto.TaskResponseDTO;
import com.shui.flowable.enums.CommentTypeEnum;
import com.shui.flowable.service.FlowableTaskService;
import com.shui.flowable.service.PermissionService;
import com.shui.flowable.utils.FlowableUtils;
import com.shui.wx.utils.common.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.UserTask;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.task.Comment;
import org.flowable.identitylink.api.IdentityLink;
import org.flowable.identitylink.api.IdentityLinkType;
import org.flowable.task.api.DelegationState;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskInfo;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FlowableTaskServiceImpl extends FlowableBaseServiceImpl implements FlowableTaskService {

    @Autowired
    private PermissionService permissionService;

    @Override
    public TaskResponseDTO getTask(String taskId) {
        Task task = getTaskNotNull(taskId);
        return new TaskResponseDTO(task);
    }

    @Override
    public TaskResponseDTO updateTask(TaskRequestDTO taskRequestDTO) {
        Task task = getTaskNotNull(taskRequestDTO.getTaskId());
        task.setName(taskRequestDTO.getName());
        task.setDescription(taskRequestDTO.getDescription());
        task.setAssignee(taskRequestDTO.getAssignee());
        task.setOwner(taskRequestDTO.getOwner());
        task.setDueDate(taskRequestDTO.getDueDate());
        task.setPriority(taskRequestDTO.getPriority());
        task.setCategory(taskRequestDTO.getCategory());
        taskService.saveTask(task);
        return new TaskResponseDTO(task);
    }

    @Override
    public void deleteTask(String taskId) {
        HistoricTaskInstance task = getHistoricTaskInstanceNotNull(taskId);
        if (task.getEndTime() == null) {
            throw new BaseException("任务正在进行");
        }
        historyService.deleteHistoricTaskInstance(task.getId());
    }

    @Override
    public void assignTask(TaskRequestDTO taskRequest) {
        String taskId = taskRequest.getTaskId();
        String assignee = taskRequest.getUserId();
        String processInstanceId = taskRequest.getProcessInstanceId();
        String userId = taskRequest.getUserId();
        permissionService.validateAssignPermissionOnTask(taskId, userId, assignee);
        addComment(taskId, processInstanceId, userId, CommentTypeEnum.ZB, taskRequest.getMessage());
    }

    @Override
    public void involveUser(String taskId, String involveUserId) {
//        Task task = getTaskNotNull(taskId);
//        String userId = taskRequest.getUserId();
//        TaskInfo task = permissionService.validateReadPermissionOnTask2(taskId, userId, false, false);
        if (StringUtils.isNotBlank(involveUserId)) {
            taskService.addUserIdentityLink(taskId, involveUserId, IdentityLinkType.PARTICIPANT);
        } else {
            throw new BaseException("User id is required");
        }
    }

    @Override
    public void removeInvolvedUser(String taskId, String involveUserId) {
        Task task = getTaskNotNull(taskId);
        String userId = "";
        permissionService.validateReadPermissionOnTask(task.getId(), userId, false, false);
        if (StringUtils.isNotBlank(involveUserId)) {
            taskService.deleteUserIdentityLink(taskId, involveUserId, IdentityLinkType.PARTICIPANT);
        } else {
            throw new BaseException("User id is required");
        }
    }

    @Override
    public void claimTask(TaskRequestDTO taskRequest) {
        String taskId = taskRequest.getTaskId();
        String userId = taskRequest.getUserId();
        TaskInfo task = permissionService.validateReadPermissionOnTask2(taskId, userId, false, false);
        if (task.getAssignee() != null && task.getAssignee().length() > 0) {
            throw new BaseException("用户没有权限");
        }
        this.addComment(taskId, task.getProcessInstanceId(), userId, CommentTypeEnum.RL, taskRequest.getMessage());
        taskService.claim(taskId, userId);
    }

    @Override
    public void unclaimTask(TaskRequestDTO taskRequest) {
        String taskId = taskRequest.getTaskId();
        String userId = "";
        Task task = getTaskNotNull(taskId);
        if (!userId.equals(task.getAssignee())) {
            throw new BaseException("用户不能取消认领");
        }
        if ("toRead".equals(task.getCategory())) {
            throw new BaseException("用户不能取消认领");
        }

        addComment(taskId, task.getProcessInstanceId(), userId, CommentTypeEnum.QXRL, taskRequest.getMessage());
        taskService.unclaim(taskId);
    }


    @Override
    public void addIdentiyLinkForUser(Task task, String userId, String linkType) {
        List<IdentityLink> identityLinks = taskService.getIdentityLinksForTask(task.getId());
        boolean isOldUserInvolved = false;
        for (IdentityLink identityLink : identityLinks) {
            isOldUserInvolved = userId.equals(identityLink.getUserId()) && (identityLink.getType().equals(IdentityLinkType.PARTICIPANT) || identityLink.getType().equals(IdentityLinkType.CANDIDATE));
            if (isOldUserInvolved) {
                break;
            }
        }
        if (!isOldUserInvolved) {
            taskService.addUserIdentityLink(task.getId(), userId, linkType);
        }
    }

    @Override
    public void delegateTask(TaskRequestDTO taskRequest) {
        String taskId = taskRequest.getTaskId();
        String delegater = taskRequest.getUserId();
        String userId = taskRequest.getUserId();
        Task task = permissionService.validateDelegatePermissionOnTask(taskId, userId, delegater);
        this.addComment(taskId, task.getProcessInstanceId(), userId, CommentTypeEnum.WP, taskRequest.getMessage());
        taskService.delegateTask(task.getId(), delegater);
    }

    @Override
    public void addComment(String taskId, String processInstanceId, String userId, CommentTypeEnum type, String message) {
        identityService.setAuthenticatedUserId(userId);
        type = type == null ? CommentTypeEnum.SP : type;
        message = (message == null || message.length() == 0) ? type.getName() : message;
        taskService.addComment(taskId, processInstanceId, type.toString(), message);
    }

    @Override
    public List<Comment> getComments(String taskId, String processInstanceId, String type, String userId) {
        return null;
    }

    @Override
    public void saveTaskIdentityLink(TaskRequestDTO taskIdentityRequest) {

    }

    @Override
    public void deleteTaskIdentityLink(String taskId, String identityId, String identityType) {

    }

    @Override
    public Task getTaskNotNull(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new BaseException("任务已完成或者不存在");
        }
        return task;
    }

    @Override
    public HistoricTaskInstance getHistoricTaskInstanceNotNull(String taskId) {
        HistoricTaskInstance task = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new BaseException("任务已完成或者不存在");
        }
        return task;
    }

}
