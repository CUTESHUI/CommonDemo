package com.shui.flowable.service.impl;

import com.shui.exception.BaseException;
import com.shui.flowable.enums.ButtonsEnum;
import com.shui.flowable.service.PermissionService;
import com.shui.flowable.utils.FlowableUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.ExtensionElement;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.identitylink.api.IdentityLink;
import org.flowable.idm.api.Group;
import org.flowable.task.api.DelegationState;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskInfo;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
@Transactional(rollbackFor = Exception.class)
public class PermissionServiceImpl extends FlowableBaseServiceImpl implements PermissionService {

    @Override
    public HistoricTaskInstance validateReadPermissionOnTask(String taskId, String userId, boolean validateReadProcessInstance, boolean validateReadParentTask) {
        HistoricTaskInstanceQuery taskQuery = historyService.createHistoricTaskInstanceQuery().taskId(taskId);
        List<String> groupIds = getGroupIdsForUser(userId);
        if (!groupIds.isEmpty()) {
            taskQuery.or().taskInvolvedUser(userId).taskCandidateGroupIn(groupIds).endOr();
        } else {
            taskQuery.taskInvolvedUser(userId);
        }
        HistoricTaskInstance task = taskQuery.singleResult();
        if (task != null) {
            return task;
        }
        // Last resort: user has access to process instance or parent task -> can see task
        task = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
        if (task != null) {
            if (validateReadProcessInstance && task.getProcessInstanceId() != null && task.getProcessInstanceId().length() > 0) {
                boolean hasReadPermissionOnProcessInstance = hasReadPermissionOnProcessInstance(userId,
                        task.getProcessInstanceId());
                if (hasReadPermissionOnProcessInstance) {
                    return task;
                }
            }
            if (validateReadParentTask && task.getParentTaskId() != null && task.getParentTaskId().length() > 0) {
                validateReadPermissionOnTask(task.getParentTaskId(), userId, validateReadProcessInstance, validateReadParentTask);
                return task;
            }
        }
        throw new BaseException("User does not have permission");
    }

    @Override
    public Task validateReadPermissionOnTask2(String taskId, String userId, boolean validateReadProcessInstance, boolean validateReadParentTask) {

        Task task = taskService.createTaskQuery().taskId(taskId).or().taskCandidateOrAssigned(userId).taskOwner(userId).endOr().singleResult();
        if (task != null) {
            return task;
        }

        // Last resort: user has access to process instance or parent task -> can see task
        task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task != null) {
            if (validateReadProcessInstance && task.getProcessInstanceId() != null && task.getProcessInstanceId().length() > 0) {
                boolean hasReadPermissionOnProcessInstance = hasReadPermissionOnProcessInstance(userId,
                        task.getProcessInstanceId());
                if (hasReadPermissionOnProcessInstance) {
                    return task;
                }

            }
            if (validateReadParentTask && task.getParentTaskId() != null && task.getParentTaskId().length() > 0) {
                validateReadPermissionOnTask2(task.getParentTaskId(), userId, validateReadProcessInstance,
                        validateReadParentTask);
                return task;
            }
        }
        throw new BaseException("User does not have permission");
    }

    private List<String> getGroupIdsForUser(String userId) {
        List<String> groupIds = new ArrayList<>();

        List<Group> userGroups = identityService.createGroupQuery().groupMember(userId).list();
        for (Group group : userGroups) {
            groupIds.add(String.valueOf(group.getId()));
        }
        return groupIds;
    }

    @Override
    public boolean isTaskOwnerOrAssignee(String currentUser, Task task) {
        return currentUser.equals(task.getOwner()) || currentUser.equals(task.getAssignee());
    }

    @Override
    public boolean isTaskOwnerOrAssignee(String currentUser, String taskId) {
        return isTaskOwnerOrAssignee(currentUser, taskService.createTaskQuery().taskId(taskId).singleResult());
    }

    @Override
    public boolean validateIfUserIsInitiatorAndCanCompleteTask(String userId, TaskInfo task) {
        boolean canCompleteTask = false;
        if (task.getProcessInstanceId() != null) {
            HistoricProcessInstance historicProcessInstance =
                    historyService.createHistoricProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
            if (historicProcessInstance != null && StringUtils.isNotEmpty(historicProcessInstance.getStartUserId())) {
                String processInstanceStartUserId = historicProcessInstance.getStartUserId();
                if (userId.equals(processInstanceStartUserId) && validateIfInitiatorCanCompleteTask(task)) {
                    return true;
                }
            }

        }
        return canCompleteTask;
    }

    @Override
    public boolean validateIfInitiatorCanCompleteTask(TaskInfo task) {
        boolean canCompleteTask = false;
        BpmnModel bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());
        FlowElement flowElement = bpmnModel.getFlowElement(task.getTaskDefinitionKey());
        if (flowElement instanceof UserTask) {
            UserTask userTask = (UserTask) flowElement;
            List<ExtensionElement> extensionElements = userTask.getExtensionElements().get("initiator-can-complete");
            if (extensionElements != null && !extensionElements.isEmpty()) {
                String value = extensionElements.get(0).getElementText();
                if (StringUtils.isNotEmpty(value) && Boolean.valueOf(value)) {
                    canCompleteTask = true;
                }
            }
        }
        return canCompleteTask;
    }

    @Override
    public boolean isInvolved(String userId, String taskId) {
        return historyService.createHistoricTaskInstanceQuery().taskId(taskId).taskInvolvedUser(userId).count() == 1;
    }

    @Override
    public HistoricProcessInstance validateReadPermissionOnProcessInstance(String userId, String processInstanceId) {
        HistoricProcessInstance historicProcessInstance =
                historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        boolean hasPermission = hasReadPermissionOnProcessInstance(userId, historicProcessInstance, processInstanceId);
        if (hasPermission) {
            return historicProcessInstance;
        }
        throw new BaseException("User does not have permission");
    }

    @Override
    public boolean hasReadPermissionOnProcessInstance(String userId, String processInstanceId) {
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        return hasReadPermissionOnProcessInstance(userId, historicProcessInstance, processInstanceId);
    }

    @Override
    public boolean hasReadPermissionOnProcessInstance(String userId, HistoricProcessInstance historicProcessInstance, String processInstanceId) {
        if (historicProcessInstance == null) {
            throw new BaseException("ProcessInstance with id: " + processInstanceId + " does not " + "exist");
        }

        // Start user check
        if (historicProcessInstance.getStartUserId() != null && historicProcessInstance.getStartUserId().equals(userId)) {
            return true;
        }

        // check if the user is involved in the task
        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();
        historicProcessInstanceQuery.processInstanceId(processInstanceId);
        historicProcessInstanceQuery.involvedUser(userId);
        if (historicProcessInstanceQuery.count() > 0) {
            return true;
        }

        // Visibility: check if there are any tasks for the current user
        HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery();
        historicTaskInstanceQuery.processInstanceId(processInstanceId);
        historicTaskInstanceQuery.taskInvolvedUser(userId);
        if (historicTaskInstanceQuery.count() > 0) {
            return true;
        }

        List<String> groupIds = getGroupIdsForUser(userId);
        if (!groupIds.isEmpty()) {
            historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery();
            historicTaskInstanceQuery.processInstanceId(processInstanceId).taskCandidateGroupIn(groupIds);
            return historicTaskInstanceQuery.count() > 0;
        }

        return false;
    }

    public List<String> getPotentialStarterGroupIds(List<IdentityLink> identityLinks) {
        List<String> groupIds = new ArrayList<>();
        for (IdentityLink identityLink : identityLinks) {
            if (identityLink.getGroupId() != null && identityLink.getGroupId().length() > 0) {
                if (!groupIds.contains(identityLink.getGroupId())) {
                    groupIds.add(identityLink.getGroupId());
                }
            }
        }
        return groupIds;
    }

    public List<String> getPotentialStarterUserIds(List<IdentityLink> identityLinks) {
        List<String> userIds = new ArrayList<>();
        for (IdentityLink identityLink : identityLinks) {
            if (identityLink.getUserId() != null && identityLink.getUserId().length() > 0) {

                if (!userIds.contains(identityLink.getUserId())) {
                    userIds.add(identityLink.getUserId());
                }
            }
        }
        return userIds;
    }

    @Override
    public boolean isTaskPending(Task task) {
        return DelegationState.PENDING.equals(task.getDelegationState());
    }

    @Override
    public Task validateAssignPermissionOnTask(String taskId, String userId, String assignee) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new BaseException("Task with id: " + taskId + " does not exist");
        }
        validateTaskHasButtonPermission(task, ButtonsEnum.ASSIGN);
        String owner = task.getOwner();
        String oldAssignee = task.getAssignee();
        boolean canAssignFlag = userId.equals(owner) || (userId.equals(oldAssignee) && !isTaskPending(task));
        if (canAssignFlag) {
            if (assignee == null || assignee.length() == 0) {
                throw new BaseException("Assignee cannot be empty");
            } else if (assignee.equals(oldAssignee)) {
                throw new BaseException("The assignee is already " + assignee);
            }
            return task;
        }
        throw new BaseException("User does not have permission");
    }

    @Override
    public Task validateDelegatePermissionOnTask(String taskId, String userId, String delegater) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new BaseException("Task with id: " + taskId + " does not exist");
        }
        validateTaskHasButtonPermission(task, ButtonsEnum.DELEGATE);
        if (isTaskOwnerOrAssignee(userId, task)) {
            String owner = task.getOwner();
            String oldAssignee = task.getAssignee();
            if (delegater == null || delegater.length() == 0) {
                throw new BaseException("Assignee cannot be empty");
            } else if (delegater.equals(owner)) {
                throw new BaseException("Cannot delegate to owner");
            } else if (delegater.equals(oldAssignee)) {
                throw new BaseException("The executor is already " + delegater);
            }
            return task;
        }
        throw new BaseException("User does not have permission");
    }

    @Override
    public Task validateExcutePermissionOnTask(String taskId, String userId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new BaseException("Task with id: " + taskId + " does not exist");
        }
        if (isTaskOwnerOrAssignee(userId, taskId)) {
            return task;
        }
        throw new BaseException("User does not have permission");
    }

    @Override
    public ProcessInstance validateStopProcessInstancePermissionOnTask(String taskId, String userId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new BaseException("Task with id: " + taskId + " does not exist");
        }
        validateTaskHasButtonPermission(task, ButtonsEnum.STOP);
        ProcessInstance processInstance =
                runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        if (processInstance == null) {
            throw new BaseException("ProcessInstance with id: " + task.getProcessInstanceId() + " " + "does not exist");
        }
        boolean canStopFlag = (userId != null && userId.length() > 0 && userId.equals(processInstance.getStartUserId()));
        if (canStopFlag) {
            return processInstance;
        }
        throw new BaseException("User does not have permission");
    }

    public void validateTaskHasButtonPermission(Task task, ButtonsEnum buttonsEnum) {
        UserTask userTask = (UserTask) FlowableUtils.getFlowElement(repositoryService, task.getProcessDefinitionId(), task.getTaskDefinitionKey());
        if (userTask == null) {
            throw new BaseException("Can not find userTask by id " + task.getTaskDefinitionKey());
        }
        String buttons = FlowableUtils.getFlowableAttributeValue(userTask, "buttons");
        if (buttons != null) {
            boolean hasButtonPermission = Arrays.stream(buttons.split(",")).anyMatch(button -> button.equals(buttonsEnum.name()));
            if (!hasButtonPermission) {
                throw new BaseException("User does not have permission");
            }
        }
    }
}
